/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.james.server;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MySQLAccess() {
        String url = "jdbc:mysql://localhost/feedback?user=sqluser&password=sqluserpw";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url);      
        } catch(Exception e) {
            exit(-1);
        }
    }

    public MySQLAccess(String host, String username, String password, String database) {
        String url = "";
        url = "jdbc:mysql://" + host + "/" + database + "?" + "user=" + username + "&password=" + password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url);
        } catch(Exception e) {
            exit(-1);
        }
    }
  
    public String retrieve(String SQLCmd) {
        String result = "";
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(SQLCmd);
            result = writeResultSet(resultSet);
        } catch(Exception e) {
            return "ERROR";
        }
        return result;
    }
    
    public String update(String SQLCmd) {
        try {
            preparedStatement = connect.prepareStatement(SQLCmd);
            int rows = preparedStatement.executeUpdate();
            return "UPDATED " + rows + " ROWS.";
        } catch(Exception e) {
            return "ERROR";
        }
    }
    
    public String insert(String SQLCmd) {
        try {
            preparedStatement = connect.prepareStatement(SQLCmd);
            int rows = preparedStatement.executeUpdate();
            return "INSERTED " + rows + " ROWS.";
        } catch(Exception e) {
            return "ERROR";
        }
    }
    
    public String remove(String SQLCmd) {
        try {
            preparedStatement = connect.prepareStatement(SQLCmd);
            int rows = preparedStatement.executeUpdate();
            return "REMOVED " + rows + " ROWS.";
        } catch(Exception e) {
            return "ERROR";
        }
    }
  
    private String writeMetaData(ResultSet resultSet) throws SQLException {
        String result = "";
        result += "The columns in the table are: ";
        result += "Table: " + resultSet.getMetaData().getTableName(1);
        for(int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++) {
            result += "Column " +i  + " "+ resultSet.getMetaData().getColumnName(i);
        }
        return result;
    }

    private String writeResultSet(ResultSet resultSet) throws SQLException {
        String result = ""; 
        /*switch(query) {
            case "login":
                while(resultSet.next()) {
                    int userID = resultSet.getInt("autokey");
                    result += userID + "\n";
                }
            break;
                
        } */
        int cols = resultSet.getMetaData().getColumnCount();
        int rows = 0;
        while(resultSet.next()) {
            if(rows > 0) {
                result += "\n";
            }
            for(int i=1;i<=cols;i++) {
                result += resultSet.getObject(i).toString();
                result += ",";
            }
            int len = result.length();
            result = result.substring(0, (len - 1));
            rows++;
        }
        return result;
    }

    private void close() {
        try {
            if(resultSet != null) {
                resultSet.close();
            }

            if(statement != null) {
                statement.close();
            }

            if(connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }
} 