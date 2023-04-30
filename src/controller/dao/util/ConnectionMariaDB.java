package controller.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionMariaDB {
	private static String serverName = "localhost";
    private static String database = "gestaolocadora";
    private static String url = "jdbc:mariadb://" + serverName + "/" + database;
    private static String username = "root";      
    private static String password = "135790";
    
    public static Connection connection;
    
    private ConnectionMariaDB(){
    }

    public static void getConnection() throws SQLException {
        if (connection == null) {
            try {
                String driverName = "org.mariadb.jdbc.Driver";
                Class.forName(driverName);

                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException ex) {
            	System.out.println("O driver expecificado nao foi encontrado.");
            }
        }
    }
    
    public static void closeConnection() throws SQLException{
    	if (connection != null) {
	        connection.close();
	        connection = null;
    	}
    }
    
    public static int nextId(String tabela, String primaryKeyName) throws SQLException{        
        ResultSet rs = connection.prepareStatement("select max(" + primaryKeyName + ") from " + tabela).executeQuery();
        if(rs.next()){
            int id = rs.getInt(1) + 1;
            rs.close();
            return id;
        } 
        return 1;
    }

}
