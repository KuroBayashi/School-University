package repository;

import exception.RepositoryException;
import entity.Contact;
import entity.DiscountCode;
import entity.Location;
import entity.MicroMarket;
import entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;


public class CustomerRepository extends AbstractRepository {
    
    private final DataSource dataSource;
    
    private final static String SQL_SELECT = ""
        + " SELECT "
        + "     c.customer_id, c.name, c.addressline1, c.addressline2, c.city, c.state, c.phone, c.fax, c.email, c.credit_limit, "
        + "     dc.discount_code, dc.rate, "
        + "     mm.zip_code, mm.radius, mm.area_length, mm.area_width "
        + " FROM customer c "
        + " LEFT JOIN discount_code dc ON dc.discount_code = c.discount_code "
        + " LEFT JOIN micro_market  mm ON mm.zip_code = c.zip "
    ;
    
    private final static String SQL_INSERT = ""
        + " INSERT INTO customer "
        + "     (discount_code, zip, name, addressline1, addresline2, city, state, phone, fax, email, credit_limit) "
        + " VALUES "
        + "     (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    ;
    
    private final static String SQL_UPDATE = ""
        + " UPDATE customer "
        + " SET "
        + "     discount_code = ?, zip = ?, name = ?, addressline1 = ?, addressline2 = ?, city = ?, state = ?,"
        + "     phone = ?, fax = ?, email = ?, credit_limit = ?"
    ;
    
    // Constructor
    public CustomerRepository(DataSource dataSource) {
        
        this.dataSource = dataSource;
    }
    
    
    // Private Methods
    private Customer getCustomer(ResultSet rs) throws SQLException {
        
        return new Customer(
            rs.getInt("customer_id"), 
            rs.getString("name"), 
            new DiscountCode(
                rs.getString("discount_code").charAt(0),
                rs.getFloat("rate")
            ), 
            rs.getInt("credit_limit"), 
            new Location(
                rs.getString("addressline1"), 
                rs.getString("addressline2"), 
                new MicroMarket(
                    rs.getString("zip_code"),
                    rs.getFloat("radius"),
                    rs.getFloat("area_length"),
                    rs.getFloat("area_width")
                ),
                rs.getString("city"),
                rs.getString("state")
            ), 
            new Contact(
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("fax")
            )
        );
    }
    
    // Public Methods
    public Customer findOneWith(List<QueryParameter> parameters) throws RepositoryException {
        
        Customer customer = null;
        
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
                    customer = this.getCustomer(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryException("CustomerRepository:findOneWith - " + e.getMessage());
        }
        
        return customer;
    }
    
    public void save(Customer customer) throws RepositoryException {
        
        String sql;
        List<QueryParameter> parameters = new ArrayList<>();
        
        // Query type : Insert or Update
        if (-1 == customer.getId())
            sql = SQL_INSERT;
        else {
            parameters = Arrays.asList(
                new QueryParameter("customer_id", customer.getId())
            ); 
            
            sql = this.buildQueryWith(SQL_UPDATE, parameters);
        }
        
        // Run query
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            int i = 0;
            
            stmt.setString(++i, String.valueOf(customer.getDiscountCode().getCode()));        
            stmt.setString(++i, customer.getLocation().getMicroMarket().getZipCode());
            stmt.setString(++i, customer.getName());
            stmt.setString(++i, customer.getLocation().getAddressLine_1());
            stmt.setString(++i, customer.getLocation().getAddressLine_2());
            stmt.setString(++i, customer.getLocation().getCity());
            stmt.setString(++i, customer.getLocation().getState());
            stmt.setString(++i, customer.getContact().getPhone());
            stmt.setString(++i, customer.getContact().getFax());
            stmt.setString(++i, customer.getContact().getEmail());
            stmt.setInt(   ++i, customer.getCredit());
            
            this.setQueryParameters(stmt, parameters, ++i);

            if (1 != stmt.executeUpdate())
                throw new SQLException("Failed to save Customer.");
            
        } catch (SQLException e) {
            throw new RepositoryException("CustomerRepository:save - " + e.getMessage());
        }
    }
}
