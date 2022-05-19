package repository;

import java.sql.SQLException;


public class RepositoryFactory {
    
    public static CustomerRepository getCustomerRepository() throws SQLException {
        return new CustomerRepository(DataSourceFactory.getDataSource());
    }
    
    public static MicroMarketRepository getMicroMarketRepository() throws SQLException {
        return new MicroMarketRepository(DataSourceFactory.getDataSource());
    }
    
    public static PurchaseOrderRepository getPurchaseOrderRepository() throws SQLException {
        return new PurchaseOrderRepository(DataSourceFactory.getDataSource());
    }
    
    public static ProductRepository getProductRepository() throws SQLException {
        return new ProductRepository(DataSourceFactory.getDataSource());
    }
    
    public static ManufacturerRepository getManufacturerRepository() throws SQLException {
        return new ManufacturerRepository(DataSourceFactory.getDataSource());
    }
    
    public static ProductCodeRepository getProductCodeRepository() throws SQLException {
        return new ProductCodeRepository(DataSourceFactory.getDataSource());
    }
}
