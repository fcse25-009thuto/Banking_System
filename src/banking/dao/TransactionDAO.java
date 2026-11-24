package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransactionDAO {
    private Connection connection;
    public TransactionDAO(Connection connection){
        this.connection = connection;
    }

    //Record a transaction

    public boolean addTransaction(int accountNumber, String transactionType, double amount){
        String sql = "INSERT INTO BankTransaction" +
                "(AccountNumber,TransactionType, Amount)" +
                "VALUES (?, ?, ?)";
        try{
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            statement.setInt(1, accountNumber);
            statement.setString(2, transactionType);
            statement.setDouble(3, amount);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Transaction error:" + e.getMessage());
            return false;
        }
    }
}
