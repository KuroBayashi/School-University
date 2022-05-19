package repository;

import exception.RepositoryException;
import entity.MicroMarket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;


public class MicroMarketRepository extends AbstractRepository {
    
    private final DataSource dataSource;
    
    private final static String SQL_SELECT = ""
        + " SELECT "
        + "     zip_code, radius, area_length, area_width "
        + " FROM micro_market "
    ;
    
    // Constructor
    public MicroMarketRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // Private Methods
    private MicroMarket getMicroMarket(ResultSet rs) throws SQLException {
        return new MicroMarket(
            rs.getString("zip_code"),
            rs.getFloat("radius"),
            rs.getFloat("area_length"),
            rs.getFloat("area_width")
        );
    }
    
    // Public Methods
    public MicroMarket findOneWith(List<QueryParameter> parameters) throws RepositoryException {
        
        MicroMarket microMarket = null;
        
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
                    microMarket = this.getMicroMarket(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryException("MicroMarketRepository:findOneWith - " + e.getMessage());
        }
        
        return microMarket;
    }
    
    public List<MicroMarket> findAll() throws RepositoryException {
        
        List<MicroMarket> microMarkets = new LinkedList<>();
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SELECT);   
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next())
                microMarkets.add(this.getMicroMarket(rs));
            
        } catch (SQLException e) {
            throw new RepositoryException("MicroMarketRepository:findAll - " + e.getMessage());
        }

        return microMarkets;
    }
}
