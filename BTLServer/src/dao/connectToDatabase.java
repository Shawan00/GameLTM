
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectToDatabase {
    protected Connection con;

    public connectToDatabase() {
        final String DATABASE_NAME = "btl2"; 
        final String jdbcURL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useSSL=false";
        final String JDBC_USER = "root"; 
        final String JDBC_PASSWORD = "Viet@@2003."; 
        try {
            con = DriverManager.getConnection(jdbcURL, JDBC_USER, JDBC_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to database failed");
        }
    }
}