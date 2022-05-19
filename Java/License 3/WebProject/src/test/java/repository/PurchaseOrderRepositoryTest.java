package repository;

import entity.Customer;
import entity.Product;
import entity.PurchaseOrder;
import exception.RepositoryException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PurchaseOrderRepositoryTest {
    
    private DataSource dataSource;
    private PurchaseOrderRepository purchaseOrderRepository;
    private static Connection connection;
    
    public static DataSource getDataSource() {
        JDBCDataSource ds = new JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        
        return ds;
    }
    
    private void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
        String sqlFilePath = ManufacturerRepositoryTest.class.getResource(filename).getFile();
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

        sqlFile.setConnection(connexion);
        sqlFile.execute();
        sqlFile.closeReader();		
    }
    
    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        dataSource = getDataSource();
        connection = dataSource.getConnection();
        
        executeSQLScript(connection, "schema.sql");
        executeSQLScript(connection, "datas.sql");		

        purchaseOrderRepository = new PurchaseOrderRepository(dataSource);
    }

    @After
    public void tearDown() throws IOException, SqlToolError, SQLException {
        connection.close();
        purchaseOrderRepository = null;
    }

    @Test
    public void testFindOneWith() throws RepositoryException {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOneWith(Arrays.asList(
            new QueryParameter("po.order_num", 10398001)
        ));
        
        assertNotEquals(null, purchaseOrder);
        assertEquals(980001, (int)purchaseOrder.getProduct().getId());
    }
    
    @Test
    public void testFindOneWithFail() throws RepositoryException {
        assertEquals(null, purchaseOrderRepository.findOneWith(Arrays.asList(
            new QueryParameter("po.order_num", 4)
        )));
    }
    
    @Test
    public void testFindAllWith() throws RepositoryException {
        assertEquals(4, purchaseOrderRepository.findAllWith(Arrays.asList(
            new QueryParameter("po.order_num", ">=", 10398003)
        )).size());
    }
    
    @Test
    public void testSaveCreate() {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .setCustomer(new Customer().setId(3))
            .setProduct(new Product().setId(980025))
            .setQuantity(3)
            .setSalesDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingCost(9.56f)
            .setFreightCompany("TestCompany")
        ;
        
        try {
            purchaseOrderRepository.save(purchaseOrder);
        } catch (RepositoryException e) {
            fail();
        }
    }
    
    @Test
    public void testSaveCreateFail() {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .setCustomer(new Customer().setId(99)) // Unknown customer
            .setProduct(new Product().setId(980025))
            .setQuantity(8)
            .setSalesDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingCost(9.56f)
            .setFreightCompany("TestCompany")
        ;
        
        try {
            purchaseOrderRepository.save(purchaseOrder);
            fail();
        } catch (RepositoryException e) {}
    }
    
    @Test
    public void testSaveUpdate() {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .setNum(10398001)
            .setCustomer(new Customer().setId(2))
            .setProduct(new Product().setId(980025))
            .setQuantity(8)
            .setSalesDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingCost(9.56f)
            .setFreightCompany("TestCompany")
        ;
        
        try {
            purchaseOrderRepository.save(purchaseOrder);
        } catch (RepositoryException e) {
            fail();
        }
    }
    
    @Test
    public void testSaveUpdateFail() {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .setNum(66) // Unknown Purchase Order
            .setCustomer(new Customer().setId(2))
            .setProduct(new Product().setId(980025))
            .setQuantity(8)
            .setSalesDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingDate(new Date(Calendar.getInstance().getTimeInMillis()))
            .setShippingCost(9.56f)
            .setFreightCompany("TestCompany")
        ;
        
        try {
            purchaseOrderRepository.save(purchaseOrder);
            fail();
        } catch (RepositoryException e) {}
    }
    
    @Test
    public void testDelete() {        
        try {
            purchaseOrderRepository.delete(new PurchaseOrder().setNum(10398006));
        } catch (RepositoryException e) {
            fail();
        }
    }
    
    @Test
    public void testDeleteFail() {        
        try {
            purchaseOrderRepository.delete(new PurchaseOrder().setNum(0));
            fail();
        } catch (RepositoryException e) {}
    }
    
    @Test
    public void testFindAllGroupByProductCode() throws RepositoryException {
        assertEquals(4, purchaseOrderRepository.findAllGroupByProductCode(
            Date.valueOf("2005-11-05"), 
            Date.valueOf("2018-12-01")
        ).size());
    }
    
    @Test
    public void testFindAllGroupByCustomer() throws RepositoryException {
        assertEquals(4, purchaseOrderRepository.findAllGroupByCustomer(
            Date.valueOf("2005-11-05"), 
            Date.valueOf("2018-12-01")
        ).size());
    }
    
    @Test
    public void testFindAllGroupByLocation() throws RepositoryException {
        assertEquals(3, purchaseOrderRepository.findAllGroupByLocation(
            Date.valueOf("2005-11-05"), 
            Date.valueOf("2018-12-01")
        ).size());
    }
}
