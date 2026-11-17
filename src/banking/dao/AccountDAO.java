package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {
    private Connection connection;
     public AccountDAO(Connection connection){
         this.connection = connection;
     }

     // Create a new account
    public boolean AddAccount(int CustomerID, String accountType, double balance){
         String sql = "INSERT INTO Account"+
                 "(CustomerID, AccountType, Balance)"+
                 "VALUES (?, ?, ?, ?)";
         try{
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             statement.setString(1, customerID);
             statement.setString(2, accountType);
             statement.setDouble(3, balance);

             return statement.executeUpdate() > 0;
         } catch (Exception e) {
             System.out.println("Account error:"+ e.getMessage());
             return false;
         }
    }

    //Get account balance
    public double getBalance(int accountNumber){
         String sql = "SELECT Balance FROM Account" +
                 "WHERE AccountNumber = ?";
         try{
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             statement.setInt(1, accountNumber);
             ResultSet result = statement.executeQuery();

             if(result.next()){
                 return result.getDouble("Balance");
             }
         } catch (Exception e) {
             System.out.print("Balance error:" + e.getMessage());
         }
         return -1;
    }
    //Deposit money
    public boolean deposit (int accountNumber, double amount){
         String sql = "UPDATE Account SET Balance = Balance + ?"+
                 "WHERE AccountNumber = ?";
         try{
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             statement.setDouble(1, amount);
             statement.setInt(2, accountNumber);
         } catch (Exception e) {
             System.out.print("Deposit error:" + e.getMessage());
             return false;
         }
    }

    //Withdraw money
    public boolean withdraw (int accountNumber, double amount){
         String sql = "Update Account SET Balance = Balance -?"+
                 "WHERE AccountNumber = ? AND BALANCE >= ?";
         try{
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             statement.setDouble(1, amount);
             statement.setInt(2, accountNumber);
             statement.setDouble(3, amount);

             return statement.executeUpdate() > 0;
         } catch (Exception e) {
             System.out.println("Withdraw error:" + e.getMessage());
             return false;

         }
    }
}
