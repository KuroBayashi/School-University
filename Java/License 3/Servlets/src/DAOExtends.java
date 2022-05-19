/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejdbcservlets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import simplejdbc.DAO;
import simplejdbc.DAOException;

/**
 *
 * @author pedago
 */
public class DAOExtends extends DAO {
    
    public DAOExtends(DataSource dataSource) {
        super(dataSource);
    }
    
    public List<String> getStates() {
        List<String> states = new LinkedList<>();

        String sql = "SELECT DISTINCT state FROM CUSTOMER";
        try (   Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
                while(rs.next()) {
                    states.add(rs.getString("STATE"));
                }
        } catch (SQLException ex) {
                Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
                System.out.println("GETSTATES ERROR");
        }

        return states;
    }
    
}
