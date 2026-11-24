

package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {

    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    // Register a customer and return the generated Customer ID
    public int addCustomer(String firstName, String surname,
                           String phoneNumber, String email,
                           String password, boolean employed,
                           String companyName, String companyAddress) {

        String sql = "INSERT INTO customer " +
                "(first_name, surname, phone_number, email, password, " +
                "is_employed, company_name, company_address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, firstName);
            statement.setString(2, surname);
            statement.setString(3, phoneNumber);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setBoolean(6, employed);
            statement.setString(7, companyName);
            statement.setString(8, companyAddress);

            int rows = statement.executeUpdate();

            if (rows > 0) {

                try (ResultSet keys = statement.getGeneratedKeys()) {

                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println("Could not save customer: "
                    + e.getMessage());
        }

        return -1;
    }

    // Check customer login details
    public int login(String email, String password) {

        String sql = "SELECT customer_id FROM customer " +
                "WHERE email = ? AND password = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return result.getInt("customer_id");
                }
            }

        } catch (SQLException e) {

            System.out.println("Login error: " + e.getMessage());
        }

        return -1;
    }

    // Find customer using email
    public boolean customerExists(String email) {

        String sql = "SELECT customer_id FROM customer WHERE email = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }

        } catch (SQLException e) {

            System.out.println("Search error: " + e.getMessage());
            return false;
        }
    }

    // Check whether a customer qualifies for a Cheque Account
    public boolean isEligibleForChequeAccount(int customerId) {

        String sql = "SELECT is_employed, company_name, company_address " +
                "FROM customer WHERE customer_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    boolean employed =
                            result.getBoolean("is_employed");

                    String company =
                            result.getString("company_name");

                    String companyAddress =
                            result.getString("company_address");

                    return employed &&
                            company != null && !company.trim().isEmpty() &&
                            companyAddress != null &&
                            !companyAddress.trim().isEmpty();
                }
            }

        } catch (SQLException e) {

            System.out.println("Employment verification error: "
                    + e.getMessage());
        }

        return false;
    }
}
