/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

import model.DiscountCode;

/**
 *
 * @author pedago
 */
public class DiscountCodeDAO {
    
    private final DataSource dataSource;
    
    public DiscountCodeDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<DiscountCode> findAll() throws DAOException {
        List<DiscountCode> discountCodeList = new LinkedList<>();
        
        String sql = "SELECT discount_code, rate FROM discount_code";
        try (
            Connection connection = this.dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                discountCodeList.add(new DiscountCode(rs.getString("discount_code"), rs.getFloat("rate")));
            }
        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        
        return discountCodeList;
    }
    
    public void save(DiscountCode discountCode) throws DAOException {
        if (!discountCode.isValid())
            throw new DAOException("DiscountCode.save : Invalid DiscountCode.");
        
        String sql = "INSERT INTO discount_code(discount_code, rate) VALUES (?, ?)";
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setString(1, discountCode.getCode());
            stmt.setFloat(2, discountCode.getTaux());
            
            if (stmt.executeUpdate() != 1)
                throw new DAOException("DiscountCode.save : Failed (already exists ?).");
            
        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }
    
    public void delete(String code) throws DAOException {
        String sql = "DELETE FROM discount_code WHERE discount_code = ?";
        try (
            Connection connection = this.dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setString(1, code);
            
            if (stmt.executeUpdate() != 1)
                throw new DAOException("DiscountCode.delete : Failed (doesn't exists ?).");
            
        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }
    
    
}