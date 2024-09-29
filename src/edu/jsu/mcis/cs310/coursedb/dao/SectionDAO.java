package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;



public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
     public String find(int termId, String subjectId, String courseNum) {
        
        String jsonResult = "[]"; // Default to empty JSON array
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonArray sectionArray = new JsonArray(); // Array to hold section JSON objects
        
        try {
            Connection connection = daoFactory.getConnection();
            
            if (connection.isValid(0)) {
                preparedStatement = connection.prepareStatement(QUERY_FIND);
                preparedStatement.setInt(1, termId);
                preparedStatement.setString(2, subjectId);
                preparedStatement.setString(3, courseNum);

                boolean hasResults = preparedStatement.execute();
                if (hasResults) {
                    resultSet = preparedStatement.getResultSet();

                    while (resultSet.next()) {
                        JsonObject sectionObject = new JsonObject(); // Create JSON object for each section
                        sectionObject.put("termid", resultSet.getInt("termid")); // Add term ID
                        sectionObject.put("subjectid", resultSet.getString("subjectid")); // Add subject ID
                        sectionObject.put("num", resultSet.getString("num")); // Add course number

                        sectionArray.add(sectionObject); // Add to array

                        jsonResult = sectionArray.toString(); // Update result to JSON string
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
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
        
        return jsonResult; // Return the JSON result
    }
}