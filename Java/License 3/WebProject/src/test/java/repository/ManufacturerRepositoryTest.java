package repository;

import entity.Manufacturer;
import exception.RepositoryException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ManufacturerRepositoryTest {
    
    private DataSource dataSource;
    private ManufacturerRepository manufacturerRepository;
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

        manufacturerRepository = new ManufacturerRepository(dataSource);
    }

    @After
    public void tearDown() throws IOException, SqlToolError, SQLException {
        connection.close();
        manufacturerRepository = null;
    }

    @Test
    public void testFindAll() throws RepositoryException {
        assertEquals(7, manufacturerRepository.findAll().size());
    }
    
    @Test
    public void testFindOneWith() throws RepositoryException {
        Manufacturer manufacturer = manufacturerRepository.findOneWith(Arrays.asList(
            new QueryParameter("m.name", "All Sushi")
        ));
        
        assertNotEquals(null, manufacturer);
        assertEquals(19978451, (int)manufacturer.getId());
    }
    
    @Test
    public void testFindOneWithFail() throws RepositoryException {
        Manufacturer manufacturer = manufacturerRepository.findOneWith(Arrays.asList(
            new QueryParameter("m.name", "All Sushi")
        ));
        
        assertEquals(null, manufacturerRepository.findOneWith(Arrays.asList(
            new QueryParameter("m.manufacturer_id", 42)
        )));
    }
}
