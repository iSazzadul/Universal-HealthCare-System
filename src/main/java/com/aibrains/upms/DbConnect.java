package com.aibrains.upms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    public String DBNAME = "UPDMS";
    public String USERNAME = "root";
    public String PASSWORD = "";
    public String URL = "jdbc:mysql://localhost:3306/"+DBNAME;
    Connection connection;

    public Connection getConnect() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

}
