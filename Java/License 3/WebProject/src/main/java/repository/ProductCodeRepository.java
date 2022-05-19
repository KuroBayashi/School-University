package repository;

import entity.DiscountCode;
import entity.ProductCode;
import exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;


public class ProductCodeRepository extends AbstractRepository {
    
    private DataSource dataSource;
    
    private final static String SQL_SELECT = ""
        + " SELECT "
        + "     pc.prod_code, pc.description, "
        + "     dc.discount_code, dc.rate"
        + " FROM product_code pc "
        + " LEFT JOIN discount_code dc ON dc.discount_code = pc.discount_code "
    ;
    
    // Constructor
    public ProductCodeRepository(DataSource dataSource) {
        
        this.dataSource = dataSource;
    }
    
    // Private Methods
    private ProductCode getProductCode(ResultSet rs) throws SQLException {
        
        return new ProductCode(
            rs.getString("prod_code"),
            new DiscountCode(
                rs.getString("discount_code").charAt(0),
                rs.getFloat("rate")
            ),
            rs.getString("description")
        );
    }
    
    // Public Methods
    public ProductCode findOneWith(List<QueryParameter> parameters) throws RepositoryException {
        
        ProductCode productCode = null;
        
        String sql = this.buildQueryWith(SQL_SELECT, parameters);
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            this.setQueryParameters(stmt, parameters);

            try (
                ResultSet rs = stmt.executeQuery();
            ) {
                if (rs.next())
                    productCode = this.getProductCode(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryException("ProductCodeRepository:findOneWith - " + e.getMessage());
        }
        
        return productCode;
    }
    
    public List<ProductCode> findAll() throws RepositoryException {
        
        List<ProductCode> productCodes = new LinkedList<>();
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next())
                productCodes.add(this.getProductCode(rs));
        } 
        catch (SQLException e) {
            throw new RepositoryException("ProductCodeRepository:findAll - " + e.getMessage());
        }
            
        return productCodes;
    }
}
