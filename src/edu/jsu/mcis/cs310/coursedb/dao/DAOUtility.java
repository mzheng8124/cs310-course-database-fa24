package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;


public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;

    public static String getResultSetAsJson(ResultSet resultSet) {
        
        JsonArray jsonRecords = new JsonArray(); // Array to hold JSON records

        try {
            ResultSetMetaData metaData = resultSet.getMetaData(); // Get metadata from ResultSet
            int totalColumns = metaData.getColumnCount(); 

            while (resultSet.next()) {
                JsonObject jsonRecord = new JsonObject(); // Create a new JSON object for each row
                for (int columnIndex = 1; columnIndex <= totalColumns; columnIndex++) {
                    String columnName = metaData.getColumnName(columnIndex); 
                    Object columnValue = resultSet.getObject(columnIndex); 
                    jsonRecord.put(columnName, columnValue); 
                }
                jsonRecords.add(jsonRecord); // Add JSON object to array
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 
        }

        String jsonResult = Jsoner.serialize(jsonRecords); // Serialize JSON array to string
        System.out.println("Generated JSON: " + jsonResult);  // Debugging 
        return jsonResult; 
    }
        }
        
        

     
        
