package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    private Connection connection;
    public CustomerDAO(Connection connection){
        this.connection = connection;
    }
    //Register a customer
    public boolean addCustomer(String firstName, String surname, String email, String password){
        String sql = "INSERT INTO Customer"+
                "(FirstName, Surname, Email, Password)"+
                "VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, surname);
            statement.setString(3, email);
            statement.setString(4, password);

            return statement.executeUpdate() > 0;
        } catch (SQLException  e){
            System.out.println("Could not save customer:"
            + e.getMessage());
            return false;
        }
    }

    //Check customer login details
    public int login(String email, String password){
        String sql = "SELECT CustomerID FROM Customer"+
                "WHERE Email = ? AND Password = ?";
        try{
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()){
                return result.getInt("CustomerID");
            }
        }catch{
            System.out.println("Login error:"+ e.getMessage());
        }
        return -1;
    }

    //Find customer using email
    public boolean customerExists(String email){
        String sql = "SELECT CustomerID FROM Customer WHERE Email = ?";

        try{
            preparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            System.out.println("Search error:"+ e.getMessage());
            return false;
        }
    }
}
