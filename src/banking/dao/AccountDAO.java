package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection connection;
     public AccountDAO(Connection connection){
         this.connection = connection;
     }

    // Create a new account and return its generated account number
    public int AddAccount(int customerID, String accountType,
                          double balance, String branch) {

        String sql = "INSERT INTO Account " +
                "(customer_id, account_type, balance, branch, date_opened) " +
                "VALUES (?, ?, ?, ?, CURRENT_DATE)";

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, customerID);
            statement.setString(2, accountType);
            statement.setDouble(3, balance);
            statement.setString(4, branch);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                ResultSet keys = statement.getGeneratedKeys();

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println("Account error: " + e.getMessage());
        }

        return -1;
    }

    //Get account balance
    public double getBalance(int accountNumber){
         String sql = "SELECT balance FROM account" +
                 "WHERE account_number = ?";
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
         String sql = "UPDATE account SET balance = balance + ?"+
                 " WHERE   account_number = ?";
         try{
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             statement.setDouble(1, amount);
             statement.setInt(2, accountNumber);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

         } catch (Exception e) {
             System.out.print("Deposit error:" + e.getMessage());
             return false;
         }
    }

    //Withdraw money
    public boolean withdraw (int accountNumber, double amount){
         String sql = "Update Account SET balance = balance -?"+
                 " WHERE  account_number = ? AND BALANCE >= ?";
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
    // get balance by customer and account type
    public double getBalanceByType(int customerId, String accountType){
        String sql = "SELECT balance FROM account  " +
                "WHERE customer_id = ? AND account_type = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, accountType);

            ResultSet result = statement.executeQuery();
            if (result.next()){
                return result.getDouble("balance");
            }

        } catch (Exception e) {
            System.out.print("Balance error:" + e.getMessage());
        }
        return -1;
    }
    // Deposit by customer and account type
    public boolean depositByType(int customerId, String accountType, double amount) {
        String sql = "UPDATE account SET balance = Balance + ? " +
                "WHERE customer_id = ? AND account_type = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, amount);
            statement.setInt(2, customerId);
            statement.setString(3, accountType);

            if(statement.executeUpdate()> 0){
                logTransaction(customerId, accountType,"DEPOSIT", amount, null);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Deposit error:" + e.getMessage());
            return false;
        }
    }

    // Withdraw by customer and account type
    public boolean withdrawByType(int customerId, String accountType, double amount) {
        String sql = "UPDATE account SET balance = Balance - ? " +
                "WHERE customer_id = ? AND account_type = ? AND balance >= ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, amount);
            statement.setInt(2, customerId);
            statement.setString(3, accountType);
            statement.setDouble(4, amount);

            if (statement.executeUpdate()> 0){
                logTransaction(customerId, accountType, "WITHDRAWAL", amount, null);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Withdraw error:" + e.getMessage());
            return false;
        }
    }

    // Transfer between two accounts of the same customer
    public boolean transferByType(int customerId, String fromType, String toType, double amount) {
        String withdrawSql = "UPDATE account SET balance = balance - ?   " +
                "WHERE customer_id  = ? AND account_type = ? AND balance >= ?";
        String depositSql  = "UPDATE account SET balance = balance + ? " +
                "WHERE customer_id = ? AND account_type = ?";
        try {
            connection.setAutoCommit(false);

            PreparedStatement out = connection.prepareStatement(withdrawSql);
            out.setDouble(1, amount);
            out.setInt(2, customerId);
            out.setString(3, fromType);
            out.setDouble(4, amount);

            PreparedStatement in = connection.prepareStatement(depositSql);
            in.setDouble(1, amount);
            in.setInt(2, customerId);
            in.setString(3, toType);

            if (out.executeUpdate() > 0 && in.executeUpdate() > 0) {
                logTransaction(customerId, fromType, "TRANSFER_OUT", amount, toType);
                logTransaction(customerId, toType, "TRANSFER_IN", amount, fromType);
                connection.commit();
                return true;
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            System.out.println("Transfer error:" + e.getMessage());
            try { connection.rollback(); } catch (Exception ignored) {}
            return false;
        } finally {
            try { connection.setAutoCommit(true); } catch (Exception ignored) {}
        }
    }

    // Saves one history row
    private void logTransaction(int customerId, String accountType, String type,
                                double amount, String related) throws Exception {
        double balanceAfter = getBalanceByType(customerId, accountType);
        String sql = "INSERT INTO transaction_history  " +
                "(customer_id, account_type, transaction_type, amount, balance_after, related_account)  " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, customerId);
            st.setString(2, accountType);
            st.setString(3, type);
            st.setDouble(4, amount);
            st.setDouble(5, balanceAfter);
            st.setString(6, related);
            st.executeUpdate();
        }
    }

    // Returns history rows: date, account, type, amount, balance after
    public List<String[]> getHistory(int customerId) {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT created_at, account_type, transaction_type, amount, balance_after " +
                "FROM transaction_history WHERE customer_id = ?  " +
                "ORDER BY created_at DESC, transaction_id DESC";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, customerId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("created_at"),
                        rs.getString("account_type"),
                        rs.getString("transaction_type"),
                        String.format("%.2f", rs.getDouble("amount")),
                        String.format("%.2f", rs.getDouble("balance_after"))
                });
            }
        } catch (Exception e) {
            System.out.println("History error:" + e.getMessage());
        }
        return list;
    }
    //Get all accounts belonging to a customer
    public List<String> getAccountTypes(int customerId) {

        List<String> accountTypes = new ArrayList<>();

        String sql = "SELECT account_type FROM account   " +
                "WHERE customer_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                accountTypes.add(result.getString("account_type"));
            }

        } catch (Exception e) {
            System.out.println("Account retrieval error: " + e.getMessage());
        }

        return accountTypes;
    }
}

