package listener;

import entity.Product;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import repository.DataSourceFactory;
import org.apache.derby.tools.ij;
import repository.ProductRepository;

/**
 * Web application lifecycle listener, initialise la base de données au démarrage de l'application si nécessaire
 */
@WebListener()
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (!databaseExists()) {
            initializeDatabase();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private boolean databaseExists() {
        boolean result = false;

        try {
            ProductRepository dao = new ProductRepository(DataSourceFactory.getDataSource());

            List<Product> allCodes = dao.findAll();
            Logger.getLogger("WebShop").log(Level.INFO, "Database already exists");
            result = true;
        } catch (Exception ex) {
            Logger.getLogger("WebShop").log(Level.INFO, "Database does not exist");
        }
        return result;
    }

    private void initializeDatabase() {
        OutputStream nowhere = new OutputStream() {
            @Override
            public void write(int b) {
            }
        };

        Logger.getLogger("WebShop").log(Level.INFO, "Creating databse from SQL script");
        try {
            Connection connection = DataSourceFactory.getDataSource().getConnection();
            int result = ij.runScript(connection, this.getClass().getResourceAsStream("export.sql"), "UTF-8", System.out /* nowhere */ , "UTF-8");
            if (result == 0)
                Logger.getLogger("WebShop").log(Level.INFO, "Database succesfully created");
            else
                Logger.getLogger("WebShop").log(Level.SEVERE, "Errors creating database");

        } catch (Exception e) {
            Logger.getLogger("WebShop").log(Level.SEVERE, null, e);
        }
    }
}