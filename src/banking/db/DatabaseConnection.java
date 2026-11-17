package banking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:sqlite:banking.db";
    try {
        return DriverManager.getConnection(URL);
    } catch(SQLException e){
        System.out.println("Database connection failed:" + e.getMessage());
        return null;
    }

}
