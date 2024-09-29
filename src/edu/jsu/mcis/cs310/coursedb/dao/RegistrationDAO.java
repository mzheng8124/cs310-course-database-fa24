package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import com.github.cliftonlabs.json_simple.*;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    private static final String INSERT_REGISTRATION_QUERY = "INSERT INTO registration (studentId, termId, crn) VALUES (?, ?, ?)";  

    public RegistrationDAO(DAOFactory daoFactory) {
    this.daoFactory = daoFactory; // Initialize DAO factory
}

    public boolean create(int studentId, int termId, int crn) {
    
        boolean isCreated = false; 
    
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
        
            Connection connection = daoFactory.getConnection(); // database connection
        
                if (connection.isValid(0)) {
                    preparedStatement = connection.prepareStatement(INSERT_REGISTRATION_QUERY); 
                    // setting studentid, termid, and crn
                    preparedStatement.setInt(1, studentId); 
                    preparedStatement.setInt(2, termId);    
                    preparedStatement.setInt(3, crn);       

            int updateCount = preparedStatement.executeUpdate(); // update

            if (updateCount > 0) {
                isCreated = true; 
            }
        }
    } catch (Exception e) {
        e.printStackTrace(); 
    } finally {
        if (resultSet != null) { 
            try { 
                resultSet.close(); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        }
        if (preparedStatement != null) { 
            try { 
                preparedStatement.close(); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        }
    }
    
    return isCreated; 
}


    public boolean delete(int studentId, int termId, int crn) {
        
        boolean result = false;
        
        PreparedStatement preparedStatement = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                 String query = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                 // setting studentid, termid, and crn in query
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, termId);
            preparedStatement.setInt(3, crn);

            }
            
        }
        
        catch (Exception e) {  }
        
        finally {

            if (preparedStatement != null) { try { preparedStatement.close(); } catch (SQLException e) {} }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
               
            }
            
        }
        
        catch (SQLException e) { }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
            
        }
        
        return result;
        
    }

    public String list(int studentId, int termId) {
        
        String result = null;
        
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                   
             // Prepare and execute the query
            String query = "SELECT * FROM registration WHERE studentid = ? AND termid = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, termId);
            rs = preparedStatement.executeQuery();

            // Converting result to Json
            result = DAOUtility.getResultSetAsJson(rs);
            }
            
        }
        
        catch (SQLException e) {}
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
            if (preparedStatement != null) { try { preparedStatement.close(); } catch (SQLException e) { } }
            
        }
        
        return result;
        
    }
    
}
