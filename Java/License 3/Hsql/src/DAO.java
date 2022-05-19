package testingwithhsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DAO {
	private final DataSource myDataSource;
	
	public DAO(DataSource dataSource) {
		myDataSource = dataSource;
	}

	/**
	 * Renvoie le nom d'un client à partir de son ID
	 * @param id la clé du client à chercher
	 * @return le nom du client (LastName) ou null si pas trouvé
	 * @throws SQLException 
	 */
	public String nameOfCustomer(int id) throws SQLException {
            String result = null;

            String sql = "SELECT LastName FROM Customer WHERE ID = ?";
            try (Connection myConnection = myDataSource.getConnection(); 
                 PreparedStatement statement = myConnection.prepareStatement(sql)) {
                    statement.setInt(1, id); // On fixe le 1° paramètre de la requête
                    try ( ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                    // est-ce qu'il y a un résultat ? (pas besoin de "while", 
                                    // il y a au plus un enregistrement)
                                    // On récupère les champs de l'enregistrement courant
                                    result = resultSet.getString("LastName");
                            }
                    }
            }
            // dernière ligne : on renvoie le résultat
            return result;
	}
        
        public int insertNewProduct(Product product) throws SQLException {
            String sql = "INSERT INTO Product(Name, Price) VALUES (?, ?)";
            try (
                Connection myConnection = myDataSource.getConnection(); 
                PreparedStatement statement = myConnection.prepareStatement(sql)
            ) {
                statement.setString(1, product.getName());
                statement.setInt(2, product.getPrice());

                if (statement.executeUpdate() == 1) {
                    try (ResultSet rs = statement.getGeneratedKeys()) {
                        if (rs.next())
                            return rs.getInt(1);
                    }
                }
            }
            
            return product.getId();
	}
        
        public Product getProduct(int id) throws SQLException {
            Product result = null;

            String sql = "SELECT Name, Price FROM Product WHERE ID = ?";
            try (
                Connection myConnection = myDataSource.getConnection(); 
                PreparedStatement statement = myConnection.prepareStatement(sql)
            ) {
                statement.setInt(1, id);
                try ( ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result = new Product(
                            id,
                            resultSet.getString("Name"),
                            resultSet.getInt("Price")
                        );
                    }
                }
            }

            return result;
	}
	
}
