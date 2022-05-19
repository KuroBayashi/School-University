package repository;

import entity.Contact;
import entity.Location;
import entity.Manufacturer;
import entity.MicroMarket;
import exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;


public class ManufacturerRepository extends AbstractRepository {
    
    private final DataSource dataSource;
    
    private final static String SQL_SELECT = ""
        + " SELECT "
        + "     m.manufacturer_id, m.name, m.addressline1, m.addressline2, m.city, m.state, m.zip, m.phone, m.fax, m.email, m.rep, "
        + "     mm.radius, mm.area_length, mm.area_width "
        + " FROM manufacturer m "
        + " LEFT JOIN micro_market mm ON mm.zip_code = m.zip "
    ;
    
    // Constructor
    public ManufacturerRepository(DataSource dataSource) {
        
        this.dataSource = dataSource;
    }
    
    // Private Methods
    private Manufacturer getManufacturer(ResultSet rs) throws SQLException {
        return new Manufacturer (
            rs.getInt("manufacturer_id"),
            rs.getString("name"),
            new Location (
                rs.getString("addressline1"),
                rs.getString("addressline2"),
                new MicroMarket(
                    rs.getString("zip"),
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
            ),
            rs.getString("rep")
        );
    }
    
    public Manufacturer findOneWith(List<QueryParameter> parameters) throws RepositoryException {
        
        Manufacturer manufacturer = null;
        
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
                    manufacturer = this.getManufacturer(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryException("ManufacturerRepository:findOneWith - " + e.getMessage());
        }
        
        return manufacturer;
    }
    
    public List<Manufacturer> findAll() throws RepositoryException {
        
        List<Manufacturer> manufacturers = new LinkedList<>();
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next())
                manufacturers.add(this.getManufacturer(rs));
        } 
        catch (SQLException e) {
            throw new RepositoryException("ManufacturerRepository:findAll - " + e.getMessage());
        }
            
        return manufacturers;
    }
}
