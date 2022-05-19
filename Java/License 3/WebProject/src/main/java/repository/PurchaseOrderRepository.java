package repository;

import exception.RepositoryException;
import entity.Contact;
import entity.Customer;
import entity.DiscountCode;
import entity.Location;
import entity.Manufacturer;
import entity.MicroMarket;
import entity.Product;
import entity.ProductCode;
import entity.PurchaseOrder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;


public class PurchaseOrderRepository extends AbstractRepository {
    
    private final DataSource dataSource;
    
    private final static String SQL_SELECT = ""
        + " SELECT "
        + "     po.order_num, po.quantity, po.shipping_cost, po.sales_date, po.shipping_date, po.freight_company, "
        + "     c.customer_id, c.name, c.addressline1, c.addressline2, c.city, c.state, c.phone, c.fax, c.email, c.credit_limit, "
        + "     p.product_id, p.purchase_cost, p.quantity_on_hand, p.markup, p.available, p.description, "
        + "     c_dc.discount_code, c_dc.rate, "
        + "     c_mm.zip_code, c_mm.radius, c_mm.area_length, c_mm.area_width, "
        + "     m.manufacturer_id, m.name AS m_name, m.addressline1 AS m_addressline1, m.addressline2 as m_addressline2, m.city AS m_city, "
        + "         m.state AS m_state, m.phone AS m_phone, m.fax AS m_fax, m.email AS m_email, m.rep, "
        + "     pc.prod_code, pc.description AS pc_description, "
        + "     m_mm.zip_code AS m_mm_zip_code, m_mm.radius AS m_mm_radius, m_mm.area_length AS m_mm_area_length, "
        + "         m_mm.area_width AS m_mm_area_width, "
        + "     pc_dc.discount_code AS pc_dc_discount_code, pc_dc.rate AS pc_dc_rate "
        + " FROM      purchase_order  po "
        + " LEFT JOIN customer          c ON c.customer_id      = po.customer_id "
        + " LEFT JOIN product           p ON p.product_id       = po.product_id "
        + " LEFT JOIN discount_code  c_dc ON c_dc.discount_code = c.discount_code "
        + " LEFT JOIN micro_market   c_mm ON c_mm.zip_code      = c.zip "
        + " LEFT JOIN manufacturer      m ON m.manufacturer_id  = p.manufacturer_id "
        + " LEFT JOIN product_code     pc ON pc.prod_code       = p.product_code "
        + " LEFT JOIN micro_market   m_mm ON m_mm.zip_code      = m.zip "
        + " LEFT JOIN discount_code pc_dc ON pc_dc.discount_code = pc.discount_code "
    ;
    
    private final static String SQL_INSERT = ""
        + " INSERT INTO purchase_order "
        + "     (order_num, customer_id, product_id, quantity, shipping_cost, sales_date, shipping_date, freight_company) "
        + " VALUES "
        + "     ((SELECT MAX(order_num) +1 FROM purchase_order), ?, ?, ?, ?, ?, ?, ?)"
    ;
    
    private final static String SQL_UPDATE = ""
        + " UPDATE purchase_order "
        + " SET "
        + "     customer_id = ?, product_id = ?, quantity = ?, shipping_cost = ?, "
        + "     sales_date = ?, shipping_date = ?, freight_company = ? "
    ;
    
    private final static String SQL_DELETE = ""
        + " DELETE FROM purchase_order "  
    ;
    
    // Constructor
    public PurchaseOrderRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // Private Methods
    private PurchaseOrder getPurchaseOrder(ResultSet rs) throws SQLException {
        return new PurchaseOrder(
            rs.getInt("order_num"), 
            new Customer(
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
            ),
            new Product(
                rs.getInt("product_id"),
                new Manufacturer(
                    rs.getInt("manufacturer_id"),
                    rs.getString("m_name"),
                    new Location(
                        rs.getString("m_addressline1"),
                        rs.getString("m_addressline2"),
                        new MicroMarket(
                            rs.getString("m_mm_zip_code"),
                            rs.getFloat("m_mm_radius"),
                            rs.getFloat("m_mm_area_length"),
                            rs.getFloat("m_mm_area_width")
                        ), 
                        rs.getString("m_city"),
                        rs.getString("m_state")
                    ), 
                    new Contact(
                        rs.getString("m_email"),
                        rs.getString("m_phone"),
                        rs.getString("m_fax")
                    ),
                    rs.getString("rep")
                ),
                new ProductCode(
                    rs.getString("prod_code"),
                    new DiscountCode(
                        rs.getString("pc_dc_discount_code").charAt(0),
                        rs.getFloat("pc_dc_rate")
                    ),
                    rs.getString("pc_description")
                ),
                rs.getFloat("purchase_cost"),
                rs.getInt("quantity_on_hand"),
                rs.getFloat("markup"),
                rs.getBoolean("available"),
                rs.getString("description")
            ), 
            rs.getInt("quantity"),
            rs.getFloat("shipping_cost"),
            rs.getDate("sales_date"),
            rs.getDate("shipping_date"),
            rs.getString("freight_company")
        );
    }
    
    
    // Public Methods
    public PurchaseOrder findOneWith(List<QueryParameter> parameters) throws RepositoryException {
        
        PurchaseOrder purchaseOrder = null;
        
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
                    purchaseOrder = this.getPurchaseOrder(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryException("PurchaseOrderRepository:findOneWith - " + e.getMessage());
        }
        
        return purchaseOrder;
    }
    
    public List<PurchaseOrder> findAllWith(List<QueryParameter> parameters) throws RepositoryException {
        
        List<PurchaseOrder> purchaseOrders = new LinkedList<>();
        
        String sql = this.buildQueryWith(SQL_SELECT, parameters);
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            this.setQueryParameters(stmt, parameters);
            
            try (
                ResultSet rs = stmt.executeQuery();
            ) {
                while (rs.next())
                    purchaseOrders.add(this.getPurchaseOrder(rs));
            }
            
        } catch (SQLException e) {
            throw new RepositoryException("PurchaseOrderRepository:findAllWith - " + e.getMessage());
        }

        return purchaseOrders;
    }
    
    public void save(PurchaseOrder purchaseOrder) throws RepositoryException {
        
        String sql;
        List<QueryParameter> parameters = new ArrayList<>();
        
        // Query type : Insert or Update
        if (-1 == purchaseOrder.getNum())
            sql = SQL_INSERT;
        else {
            parameters = Arrays.asList(
                new QueryParameter("order_num", purchaseOrder.getNum())
            );
            
            sql = this.buildQueryWith(SQL_UPDATE, parameters);
        }
        
        // Run query
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            int i = 0;
            
            stmt.setInt(   ++i, purchaseOrder.getCustomer().getId());
            stmt.setInt(   ++i, purchaseOrder.getProduct().getId());
            stmt.setInt(   ++i, purchaseOrder.getQuantity());
            stmt.setFloat( ++i, purchaseOrder.getShippingCost());
            stmt.setDate(  ++i, purchaseOrder.getSalesDate());
            stmt.setDate(  ++i, purchaseOrder.getShippingDate());
            stmt.setString(++i, purchaseOrder.getFreightCompany());
            
            this.setQueryParameters(stmt, parameters, ++i);

            if (1 != stmt.executeUpdate())
                throw new SQLException("Failed to save PurchaseOrder.");
                    
        } catch (SQLException e) {
            throw new RepositoryException("PurchaseOrderRepository:save - " + e.getMessage());
        }
    }
    
    public void delete(PurchaseOrder purchaseOrder) throws RepositoryException {
        
        List<QueryParameter> parameters = Arrays.asList(
            new QueryParameter("order_num", purchaseOrder.getNum())
        );
        
        String sql = this.buildQueryWith(SQL_DELETE, parameters);
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            this.setQueryParameters(stmt, parameters);

            if (0 == stmt.executeUpdate())
                throw new SQLException("Failed to delete PurchaseOrder.");
                    
        } catch (SQLException e) {
            throw new RepositoryException("PurchaseOrderRepository:delete - " + e.getMessage());
        }
    }

    // Specials API
    public List<Pair<Float, ProductCode>> findAllGroupByProductCode(Date dateStart, Date dateEnd) throws RepositoryException {
        
        List<Pair<Float, ProductCode>> productCodeList = new LinkedList<>();
        
        String sql = "SELECT SUM(shipping_cost + quantity * purchase_cost) AS total, prod_code, product_code.discount_code, product_code.description, rate"
                + " FROM purchase_order"
                + " LEFT JOIN product ON purchase_order.product_id = product.product_id"
                + " LEFT JOIN product_code ON product.product_code = product_code.prod_code"
                + " LEFT JOIN discount_code ON product_code.discount_code = discount_code.discount_code"
                + " WHERE sales_date BETWEEN ? AND ?"
                + " GROUP BY prod_code, product_code.discount_code, product_code.description, rate";
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setDate(1, dateStart);
            stmt.setDate(2, dateEnd);
            
            try (
                ResultSet rs = stmt.executeQuery();
            ) {
                while (rs.next()) {
                    productCodeList.add(
                        new Pair<>(
                            rs.getFloat("total"),
                            new ProductCode(
                                rs.getString("prod_code"),
                                new DiscountCode(
                                    rs.getString("discount_code").charAt(0),
                                    rs.getFloat("rate")
                                ),
                                rs.getString("description")
                            )
                        )
                    );
                }
                
                return productCodeList;
            }
        } catch (SQLException e) {
            throw new RepositoryException("MicroMarketRepository:findAllGroupByProductCode - " + e.getMessage());
        }
    }
    
    public List<Pair<Float, Customer>> findAllGroupByCustomer(Date dateStart, Date dateEnd) throws RepositoryException {
        
        List<Pair<Float, Customer>> customerList = new LinkedList<>();
        
        String sql = "SELECT SUM(shipping_cost + quantity * purchase_cost) AS total, customer.customer_id, name"
                + " FROM purchase_order"
                + " LEFT JOIN product ON purchase_order.product_id = product.product_id"
                + " LEFT JOIN customer ON customer.customer_id = purchase_order.customer_id"
                + " WHERE sales_date BETWEEN ? AND ?"
                + " GROUP BY customer.customer_id, name";
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setDate(1, dateStart);
            stmt.setDate(2, dateEnd);
            
            try (
                ResultSet rs = stmt.executeQuery();
            ) {
                while (rs.next()) {
                    customerList.add(
                        new Pair<>(
                            rs.getFloat("total"),
                            (new Customer())
                                .setId(rs.getInt("customer_id"))
                                .setName(rs.getString("name"))
                        )
                    );
                }
                
                return customerList;
            }
        } catch (SQLException e) {
            throw new RepositoryException("MicroMarketRepository:findAllGroupByCustomer - " + e.getMessage());
        }
    }
    
    public List<Pair<Float, Location>> findAllGroupByLocation(Date dateStart, Date dateEnd) throws RepositoryException {
        
        List<Pair<Float, Location>> locationList = new LinkedList<>();
        
        String sql = "SELECT SUM(shipping_cost + quantity * purchase_cost) AS total, state"
                + " FROM purchase_order"
                + " LEFT JOIN product ON purchase_order.product_id = product.product_id"
                + " LEFT JOIN customer ON customer.customer_id = purchase_order.customer_id"
                + " WHERE sales_date BETWEEN ? AND ?"
                + " GROUP BY state";
        
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setDate(1, dateStart);
            stmt.setDate(2, dateEnd);
            
            try (
                ResultSet rs = stmt.executeQuery();
            ) {
                while (rs.next()) {
                    locationList.add(
                        new Pair<>(
                            rs.getFloat("total"),
                            (new Location())
                                .setState(rs.getString("state"))
                        )
                    );
                }
                
                return locationList;
            }
        } catch (SQLException e) {
            throw new RepositoryException("MicroMarketRepository:findAllGroupByLocation - " + e.getMessage());
        }
    }
    
}
