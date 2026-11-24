package banking;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import banking.db.DatabaseConnection;
import banking.dao.AccountDAO;
import banking.dao.CustomerDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;


public class BankingGUI extends Application {
    private Stage stage ;
    private Connection connection;
    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;
    private int loggedInCustomerID;

    //System colours
    private final String NAVY ="#0B1F3A";
    private final String WHITE = "#FFFFFF";

     @Override
    public void start(Stage primaryStage) {
         stage = primaryStage;
         stage.setTitle("BANKING SYSTEM");

         connection =DatabaseConnection.getConnection();

         if(connection == null){
             showAlert("Database Error",
                     "Could not connect to the database.");
             return;
         }
         accountDAO = new AccountDAO(connection);
         customerDAO = new CustomerDAO(connection);

         showLoginScreen();
         stage.show();
     }

         //Creates the heading for each screen
         private Label createHeader(String heading){
         Label title = new Label(heading);
         title.setLayoutX(0);
         title.setLayoutY(0);
         title.setPrefWidth(600);
         title.setPrefHeight(85);

         title.setStyle(
                 "-fx-background-color: "+ NAVY + ";" +
                 "-fx-text-fill: white;"+
                 "-fx-font-size: 25px;"+
                 "-fx-font-weight:bold;" +
                 "-fx-alignment: center;"
         );
         return title;
         }

         //Creates labels
        private Label createLabel(String text, double x, double y){
         Label label = new Label(text);
         label.setLayoutX(x);
         label.setLayoutY(y);

         label.setStyle(
          "-fx-text-fill: "+ NAVY + ";"+
          "-fx-font-size: 14px;"
         );
         return label;
        }

        //Creates text fields
      private TextField createTextField(String text,double x, double y){
         TextField field = new TextField();
         field.setPromptText(text);
         field.setLayoutX(x);
          field.setLayoutY(y);
          field.setPrefWidth(250);

          return field;
      }

   //Creates navy blue buttons
    private  Button createButton(String text,double x, double y){
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(250);
        button.setPrefHeight(40);

        button.setStyle(
                "-fx-background-color:"+ NAVY + ";"+
                        "-fx-text-fill: white;"+
                        "-fx-font-size: 14px;"+
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius: 5;"

        );
        return button;
    }

    //LOGIN SCREEN
    public void showLoginScreen(){
         Pane pane = new Pane();
         pane.setStyle("-fx-background-color: "+ WHITE + ";");

         Label title = createHeader("BANKING SYSTEM");
         Label welcome = createLabel(
                 "Welcome ! to our bank.",
                 170,125
         );

         Label emailLabel = createLabel(
                 "Email Address:",175,180
         );

         TextField emailField = createTextField(
               "Enter your email",175,205
         );

         Label passwordLabel = createLabel(
                 "Password:", 175,255
         );

         PasswordField passwordField = new PasswordField();

         passwordField.setPromptText("Enter your password");
        passwordField.setLayoutX(175);
        passwordField.setLayoutY(280);
        passwordField.setPrefWidth(250);

        Button loginButton = createButton(
                "LOGIN",175,345
        );
        Button registerButton = createButton(
                "REGISTER",175,400
        );

        registerButton.setOnAction(
                e -> showRegistrationScreen());
        loginButton.setOnAction(e->{
            String email = emailField.getText();
            String password = passwordField.getText();

            if(email.isEmpty() || password.isEmpty()){
                showAlert("Login Error ",
                        "Please enter your email and password. "
                );
                return;
            }
            int customer_id = customerDAO.login(email, password);
            if (customer_id != -1){
                loggedInCustomerID = customer_id;

                showAlert("Login Successfully ",
                        "Welcome! You have logged in successfully."
                );

                showMainMenu();
            } else {
                showAlert("Login Failed ",
                        "Incorrect email or password. "
                );
            }
                });
            pane.getChildren().addAll(
                  title,
                  welcome,
                  emailLabel,
                  emailField,
                  passwordLabel,
                  passwordField,
                  loginButton,
                    registerButton
          );
       stage.setScene(new Scene(pane, 600,500));

    }


    // CUSTOMER REGISTRATION SCREEN
    public void showRegistrationScreen() {

        Pane pane = new Pane();
        pane.setPrefSize(600, 950);
        pane.setStyle("-fx-background-color: " + WHITE + ";");

        Label title = createHeader("REGISTER CUSTOMER");

        // First name
        Label firstNameLabel = createLabel("First Name:", 175, 100);
        TextField firstNameField = createTextField(
                "Enter first name", 175, 125);

        // Surname
        Label surnameLabel = createLabel("Surname:", 175, 170);
        TextField surnameField = createTextField(
                "Enter surname", 175, 195);

        // Phone number
        Label phoneLabel = createLabel("Phone Number:", 175, 240);
        TextField phoneField = createTextField(
                "Enter phone number", 175, 265);

        // Email
        Label emailLabel = createLabel("Email Address:", 175, 310);
        TextField emailField = createTextField(
                "Enter email address", 175, 335);

        // Password
        Label passwordLabel = createLabel("Password:", 175, 380);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create password");
        passwordField.setLayoutX(175);
        passwordField.setLayoutY(405);
        passwordField.setPrefWidth(250);

        // Employment status
        Label employmentLabel = createLabel(
                "Employment Status:", 175, 450);

        ToggleGroup employmentGroup = new ToggleGroup();

        RadioButton employedButton = new RadioButton("Employed");
        employedButton.setToggleGroup(employmentGroup);
        employedButton.setLayoutX(175);
        employedButton.setLayoutY(475);

        RadioButton unemployedButton = new RadioButton("Unemployed");
        unemployedButton.setToggleGroup(employmentGroup);
        unemployedButton.setLayoutX(285);
        unemployedButton.setLayoutY(475);

        unemployedButton.setSelected(true);

        // Company details
        Label companyLabel = createLabel("Company Name:", 175, 510);
        TextField companyField = createTextField(
                "Enter company name", 175, 535);

        Label addressLabel = createLabel(
                "Company Address:", 175, 580);
        TextField addressField = createTextField(
                "Enter company address", 175, 605);

        companyField.setDisable(true);
        addressField.setDisable(true);

        // Account type
        Label accountTypeLabel = createLabel(
                "Account Type:", 175, 650);

        ComboBox<String> accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll(
                "Savings Account",
                "Investment Account"
        );

        accountTypeBox.setPromptText("Select account type");
        accountTypeBox.setLayoutX(175);
        accountTypeBox.setLayoutY(675);
        accountTypeBox.setPrefWidth(250);

        // Branch
        Label branchLabel = createLabel("Branch:", 175, 720);
        TextField branchField = createTextField(
                "Enter branch", 175, 745);

        // Enable company details and Cheque Account
        // only when the customer is employed
        employedButton.setOnAction(e -> {

            companyField.setDisable(false);
            addressField.setDisable(false);

            if (!accountTypeBox.getItems().contains("Cheque Account")) {
                accountTypeBox.getItems().add("Cheque Account");
            }
        });

        // Disable company details and remove Cheque Account
        // when the customer is unemployed
        unemployedButton.setOnAction(e -> {

            companyField.clear();
            addressField.clear();

            companyField.setDisable(true);
            addressField.setDisable(true);

            accountTypeBox.getItems().remove("Cheque Account");

            if ("Cheque Account".equals(accountTypeBox.getValue())) {
                accountTypeBox.setValue(null);
            }
        });

        // Register button
        Button registerButton = createButton(
                "REGISTER CUSTOMER", 175, 790);

        registerButton.setOnAction(e -> {

            // Collect customer information
            String firstName = firstNameField.getText().trim();
            String surname = surnameField.getText().trim();
            String phoneNumber = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            boolean employed = employedButton.isSelected();

            String companyName = companyField.getText().trim();
            String companyAddress = addressField.getText().trim();

            // Collect account information
            String accountType = accountTypeBox.getValue();
            String branch = branchField.getText().trim();

            // Validate required information
            if (firstName.isEmpty() || surname.isEmpty()
                    || phoneNumber.isEmpty() || email.isEmpty()
                    || password.isEmpty() || accountType == null
                    || branch.isEmpty()) {

                showAlert("Registration Error",
                        "Please complete all required details.");
                return;
            }

            // Validate employment information
            if (employed &&
                    (companyName.isEmpty() || companyAddress.isEmpty())) {

                showAlert("Registration Error",
                        "Employed customers must provide their company name and address.");
                return;
            }

            // Check whether email already exists
            if (customerDAO.customerExists(email)) {

                showAlert("Registration Error",
                        "A customer with this email already exists.");
                return;
            }

            // Save customer and create their first account
            try {

                connection.setAutoCommit(false);

                int customerID = customerDAO.addCustomer(
                        firstName,
                        surname,
                        phoneNumber,
                        email,
                        password,
                        employed,
                        employed ? companyName : null,
                        employed ? companyAddress : null
                );

                // Check whether customer was saved
                if (customerID == -1) {

                    connection.rollback();

                    showAlert("Registration Failed",
                            "Could not register customer. Please try again.");
                    return;
                }

                // Create the customer's first bank account
                int accountNumber = accountDAO.AddAccount(
                        customerID,
                        accountType,
                        0.0,
                        branch
                );

                // Check whether account was created
                if (accountNumber == -1) {

                    connection.rollback();

                    showAlert("Registration Failed",
                            "Customer could not be given a bank account.");
                    return;
                }

                // Save both operations
                connection.commit();

                showAlert("Registration Successful",
                        "Customer registered successfully."
                                + "\nCustomer ID: " + customerID
                                + "\nAccount Number: " + accountNumber
                                + "\nInitial Balance: P0.00");

                // Return to login screen
                showLoginScreen();

            } catch (SQLException ex) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }

                showAlert("Registration Failed", ex.getMessage());

            } finally {

                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Back button
        Button backButton = createButton(
                "BACK TO LOGIN", 175, 840);

        backButton.setOnAction(e -> showLoginScreen());

        // Add all controls to the pane
        pane.getChildren().addAll(
                title,

                firstNameLabel,
                firstNameField,

                surnameLabel,
                surnameField,

                phoneLabel,
                phoneField,

                emailLabel,
                emailField,

                passwordLabel,
                passwordField,

                employmentLabel,
                employedButton,
                unemployedButton,

                companyLabel,
                companyField,

                addressLabel,
                addressField,

                accountTypeLabel,
                accountTypeBox,

                branchLabel,
                branchField,

                registerButton,
                backButton
        );

        // Allow the user to scroll through the registration form
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        stage.setScene(new Scene(scrollPane, 600, 700));
    }


    //MAIN MENU
    public void showMainMenu(){
         Pane pane = new Pane();
         pane.setStyle("-fx-background-color: "+ WHITE + ";");

         Label title = createHeader("BANKING SYSTEM");
         Label welcome = createLabel(
                 "MAIN MENU- Select an Option",
                 190,120
         );

         Button depositButton = createButton(
                 "DEPOSIT",175,170
         );

        Button transferButton = createButton(
                "TRANSFER",175,225
        );
        Button withdrawButton = createButton(
                "WITHDRAW",175,280
        );
        Button historyButton = createButton(
                "TRANSACTION HISTORY",175,390
        );
        Button balanceButton = createButton(
                "CHECK BALANCE",175,335
        );
        Button registerButton = createButton(
                "REGISTER CUSTOMER", 175, 400
        );
        Button logoutButton = createButton(
                "LOG OUT",175,450
        );
        registerButton.setOnAction(
                e -> showRegistrationScreen());

        depositButton.setOnAction(
                e-> showTransactionScreen("Deposit")
        );
        transferButton.setOnAction(
                e -> showTransactionScreen("Transfer")
        );
        withdrawButton.setOnAction(
                e-> showTransactionScreen("Withdraw")
        );
        balanceButton.setOnAction(
                e->showTransactionScreen("Check Balance")
        );
        historyButton.setOnAction(
                e -> showHistoryScreen());

        logoutButton.setOnAction(
                e -> showLoginScreen()
        );

        pane.getChildren().addAll(
                title,
                welcome,
                depositButton,
                transferButton,
                withdrawButton,
                balanceButton,
                historyButton,
                registerButton,
                logoutButton
        );
        stage.setScene(new Scene(pane,600,500));

    }

    // DEPOSIT, TRANSFER, WITHDRAW AND CHECK BALANCE SCREENS
    public void showTransactionScreen(String transactionType) {

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: " + WHITE + ";");

        Label title = createHeader(transactionType.toUpperCase());

        boolean isTransfer = transactionType.equals("Transfer");
        boolean isBalance = transactionType.equals("Check Balance");

        // Retrieve the customer's actual accounts
        List<String> customerAccounts =new ArrayList<>(
                List.of ("Savings Account", "Investment Account", "Cheque Account")
                );
        // Check employment eligibility
        boolean eligible =
                customerDAO.isEligibleForChequeAccount(loggedInCustomerID);

// Remove Cheque Account if customer is not eligible
        if (!eligible) {
            customerAccounts.removeIf(
                    account -> account.equalsIgnoreCase("Cheque Account")
            );
        }

        if (customerAccounts.isEmpty()) {
            showAlert("No Accounts Found",
                    "You do not have any accounts registered.");
            showMainMenu();
            return;
        }


// SOURCE ACCOUNT SELECTION
        Label accountLabel = createLabel(
                isTransfer ? "Transfer from:" :
                        transactionType.equals("Deposit") ? "Deposit into:" :
                                transactionType.equals("Withdraw") ? "Withdraw from:" :
                                        "Select account:", 40, 130);

        ToggleGroup sourceGroup = new ToggleGroup();

// Container for separate source account cards
        javafx.scene.layout.HBox sourceCards =
                new javafx.scene.layout.HBox(10);

        sourceCards.setLayoutX(200);
        sourceCards.setLayoutY(115);

        for (String type : customerAccounts) {

            ToggleButton accountButton = new ToggleButton(type);

            accountButton.setToggleGroup(sourceGroup);
            accountButton.setPrefSize(135, 60);
            accountButton.setStyle(
                    "-fx-background-color: " + NAVY + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;" +
                            "-fx-border-radius: 8;"
            );

            accountButton.selectedProperty().addListener(
                    (observable, oldValue, selected) -> {

                        if (selected) {
                            accountButton.setStyle(
                                    "-fx-background-color: #2874A6;" +
                                            "-fx-text-fill: white;" +
                                            "-fx-font-weight: bold;" +
                                            "-fx-background-radius: 8;" +
                                            "-fx-border-color: #0B1F3A;" +
                                            "-fx-border-width: 2;"
                            );
                        } else {
                            accountButton.setStyle(
                                    "-fx-background-color: " + NAVY + ";" +
                                            "-fx-text-fill: white;" +
                                            "-fx-font-weight: bold;" +
                                            "-fx-background-radius: 8;"
                            );
                        }
                    }
            );

            sourceCards.getChildren().add(accountButton);
        }

        pane.getChildren().add(sourceCards);


// DESTINATION ACCOUNT SELECTION
        Label destinationLabel = createLabel(
                "Transfer to:", 40, 220);

        ToggleGroup destinationGroup = new ToggleGroup();

        javafx.scene.layout.HBox destinationCards =
                new javafx.scene.layout.HBox();

        destinationCards.setLayoutX(140);
        destinationCards.setLayoutY(205);

        for (String type : customerAccounts) {

            ToggleButton destinationButton = new ToggleButton(type);

            destinationButton.setToggleGroup(destinationGroup);
            destinationButton.setPrefSize(135, 60);
            destinationButton.setStyle(
                    "-fx-background-color: " + NAVY + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;"
            );

            destinationButton.selectedProperty().addListener(
                    (observable, oldValue, selected) -> {

                        if (selected) {
                            destinationButton.setStyle(
                                    "-fx-background-color: #2874A6;" +
                                            "-fx-text-fill: white;" +
                                            "-fx-font-weight: bold;" +
                                            "-fx-background-radius: 8;" +
                                            "-fx-border-color: #0B1F3A;" +
                                            "-fx-border-width: 2;"
                            );
                        } else {
                            destinationButton.setStyle(
                                    "-fx-background-color: " + NAVY + ";" +
                                            "-fx-text-fill: white;" +
                                            "-fx-font-weight: bold;" +
                                            "-fx-background-radius: 8;"
                            );
                        }
                    }
            );

            destinationCards.getChildren().add(destinationButton);
        }

        pane.getChildren().add(destinationCards);

// Only show destination cards during transfers
        destinationLabel.setVisible(isTransfer);
        destinationCards.setVisible(isTransfer);


        // AMOUNT FIELD
        Label amountLabel = createLabel(
                "Amount (BWP):", 100, 305);

        TextField amountField = createTextField(
                "Enter amount", 250, 300);

        amountLabel.setVisible(!isBalance);
        amountField.setVisible(!isBalance);

        // RESULT LABEL
        Label resultLabel = createLabel("", 100, 350);

        Button submitButton = createButton(
                transactionType.toUpperCase(), 175, 390);

        Button backButton = createButton(
                "BACK TO MAIN MENU", 175, 445);

        // HANDLE TRANSACTIONS
        submitButton.setOnAction(e -> {

            ToggleButton selectedSource =
                    (ToggleButton) sourceGroup.getSelectedToggle();

            if (selectedSource == null) {
                showAlert("Error", "Please select an account.");
                return;
            }

            String selectedAccount = selectedSource.getText();

            String destinationAccount = null;

            if (isTransfer) {

                ToggleButton selectedDestination =
                        (ToggleButton) destinationGroup.getSelectedToggle();

                if (selectedDestination == null) {
                    showAlert("Error",
                            "Please select a destination account.");
                    return;
                }

                destinationAccount = selectedDestination.getText();

                if (selectedAccount.equals(destinationAccount)) {
                    showAlert("Error",
                            "Choose two different accounts.");
                    return;
                }
            }

            // CHECK BALANCE
            if (isBalance) {

                double balance = accountDAO.getBalanceByType(
                        loggedInCustomerID, selectedAccount);

                if (balance >= 0) {
                    resultLabel.setText(
                            selectedAccount + " balance: P" +
                                    String.format("%.2f", balance));
                } else {
                    showAlert("Balance Error",
                            "Could not retrieve this account's balance.");
                }

                return;
            }

            // VALIDATE AMOUNT
            if (amountField.getText().trim().isEmpty()) {
                showAlert("Error", "Please enter an amount.");
                return;
            }

            try {

                double amount =
                        Double.parseDouble(amountField.getText().trim());

                if (!Double.isFinite(amount) || amount <= 0) {
                    showAlert("Error",
                            "Enter an amount greater than zero.");
                    return;
                }

                // DEPOSIT
                if (transactionType.equals("Deposit")) {

                    boolean success = accountDAO.depositByType(
                            loggedInCustomerID, selectedAccount, amount);

                    if (success) {

                        double newBalance = accountDAO.getBalanceByType(
                                loggedInCustomerID, selectedAccount);

                        resultLabel.setText("New balance: P" +
                                String.format("%.2f", newBalance));

                        showAlert("Deposit Successful",
                                "Money deposited into " + selectedAccount +
                                        ".\nNew balance: P" +
                                        String.format("%.2f", newBalance));

                    } else {
                        showAlert("Deposit Failed",
                                "Could not complete the deposit.");
                    }

                    return;
                }

                // WITHDRAWAL
                if(transactionType.equals("Withdraw")) {

                    if (selectedAccount.equalsIgnoreCase("Savings Account")) {
                        showAlert("Withdrawal Not Allowed",
                                "Savings accounts do not allow withdrawals.");
                        return;
                    }

                    boolean success = accountDAO.withdrawByType(
                            loggedInCustomerID, selectedAccount, amount);

                    if (success) {

                        double newBalance = accountDAO.getBalanceByType(
                                loggedInCustomerID, selectedAccount);

                        resultLabel.setText("New balance: P" +
                                String.format("%.2f", newBalance));

                        showAlert("Withdrawal Successful",
                                "Money withdrawn from " + selectedAccount +
                                        ".\nNew balance: P" +
                                        String.format("%.2f", newBalance));

                    } else {
                        showAlert("Withdrawal Failed",
                                "Insufficient funds or account not found.");
                    }

                    return;
                }

                // TRANSFER
                boolean transferSuccess = accountDAO.transferByType(
                        loggedInCustomerID,
                        selectedAccount,
                        destinationAccount,
                        amount);

                if (transferSuccess) {

                    double fromBalance = accountDAO.getBalanceByType(
                            loggedInCustomerID, selectedAccount);

                    double toBalance = accountDAO.getBalanceByType(
                            loggedInCustomerID, destinationAccount);

                    String message =
                            "From " + selectedAccount + ": P" +
                                    String.format("%.2f", fromBalance) +
                                    "\nTo " + destinationAccount + ": P" +
                                    String.format("%.2f", toBalance);

                    resultLabel.setText("Transfer Successful");

                    showAlert("Transfer Successful", message);

                } else {
                    showAlert("Transfer Failed",
                            "Insufficient funds or account not found.");
                }

            } catch (NumberFormatException ex) {

                showAlert("Invalid Amount",
                        "Please enter a valid numeric amount.");
            }
        });

        backButton.setOnAction(e -> showMainMenu());

        pane.getChildren().addAll(
                title,
                accountLabel,
                destinationLabel,
                amountLabel,
                amountField,
                resultLabel,
                submitButton,
                backButton
        );

        stage.setScene(new Scene(pane, 600, 500));
    }

    //TRANSACTION HISTORY SCREEN
    public void showHistoryScreen(){
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: "+ WHITE + ";");

        Label title = createHeader("TRANSACTION HISTORY");

        TableView<String[]> table = new TableView<>();
        table.setLayoutX(20);
        table.setLayoutY(100);
        table.setPrefSize(560, 290);

        String[] names = {"Date", "Account", "Type", "Amount (P)", "Balance (P)"};
        double[] widths = {130, 120, 100, 100, 100};
        for (int i = 0; i < names.length; i++){
            final int col = i;
            TableColumn<String[], String> c = new TableColumn<>(names[i]);
            c.setCellValueFactory(r -> new SimpleStringProperty(r.getValue()[col]));
            c.setPrefWidth(widths[i]);
            table.getColumns().add(c);
        }

        List<String[]> rows = accountDAO.getHistory(loggedInCustomerID);
        table.getItems().addAll(rows);

        Button backButton = createButton("BACK TO MAIN MENU",175,420);
        backButton.setOnAction(e -> showMainMenu());

        pane.getChildren().addAll(title, table, backButton);
        stage.setScene(new Scene(pane, 600, 500));
    }
    // Displays alert messages
    private void showAlert(String heading, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BANKING SYSTEM");
        alert.setHeaderText(heading);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args){
        launch(args);
    }
}
