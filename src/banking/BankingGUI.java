package banking;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;


public class BankingGUI extends Application {
    private Stage stage ;

    //System colours
    private final String NAVY ="#0B1F3A";
    private final String WHITE = "#FFFFFF";

     @Override
    public void start(Stage primaryStage) {
         stage = primaryStage;
         stage.setTitle("BANKING SYSTEM");

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

        loginButton.setOnAction(e->{
            if (emailField.getText().isEmpty()
            || passwordField.getText().isEmpty()){
                showAlert(
                  "Login Error",
                  "Please enter your email and password."
                );
            }else{
                showMainMenu();

            }
        });
            pane.getChildren().addAll(
                  title,
                  welcome,
                  emailLabel,
                  emailField,
                  passwordLabel,
                  passwordField,
                  loginButton
          );
       stage.setScene(new Scene(pane, 600,500));

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
        Button balanceButton = createButton(
                "CHECK BALANCE",175,335
        );
        Button logoutButton = createButton(
                "LOG OUT",175,405
        );

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
                logoutButton
        );
        stage.setScene(new Scene(pane,600,500));

    }
    //DEPOSIT, TRANSFER, WITHDRAW AND CHECK BALANCE SCREENS
    public void showTransactionScreen(String transactionType){
         Pane pane = new Pane();
         pane.setStyle("-fx-background-color: "+ WHITE + ";");

         Label title = createHeader(
                 transactionType.toUpperCase()
         );

         //Account selection label
        String accountText;
        if (transactionType.equals("Deposit")){
            accountText = "Deposit into:";
        }else if (transactionType.equals("Withdraw")) {
            accountText = "withdraw from:";
        } else if (transactionType.equals("Transfer")) {
            accountText = "Transfer from:";
        }else{
            accountText="Select account";

        }
        Label accountLabel = createLabel(
                accountText,100,130
        );

        //Source account selection
        ComboBox<String> accountType = new ComboBox<>();

        accountType.getItems().addAll(
                "Savings Account",
                "Investment Account",
                "Cheque Account"
        );

        accountType.setPromptText("Select Account");
        accountType.setLayoutX(250);
        accountType.setLayoutY(125);
        accountType.setPrefWidth(220);

        //Destination account selection
         Label destinationLabel = createLabel(
                 "Transfer to:",100,190
         );
        ComboBox<String> destinationAccount = new ComboBox<>();

        destinationAccount.getItems().addAll(
                "Savings Account",
                "Investment Account",
                "Cheque Account"
        );
        destinationAccount.setPromptText("Select Destination");
        destinationAccount.setLayoutX(250);
        destinationAccount.setLayoutY(185);
        destinationAccount.setPrefWidth(220);

        boolean isTransfer = transactionType.equals("Transfer");
        destinationLabel.setVisible(isTransfer);
        destinationAccount.setVisible(isTransfer);

        //Amount field
        Label amountLabel = createLabel("Amount(BWP):",100,250
        );
        TextField amountField = createTextField(
                "Enter amount",250,245
        );

        boolean isBalance = transactionType.equals("Check Balance");

        amountLabel.setVisible(!isBalance);
        amountField.setVisible(!isBalance);

        //Result Label
        Label resultLabel =createLabel(
                "",100,310
        );

        Button submitButton = createButton(
                transactionType.toUpperCase(),175,350
        );

        Button backButton = createButton(
                "BACK TO MAIN MENU",175,405
        );

        //Handles user selections
        submitButton.setOnAction(e->{
            if (accountType.getValue()== null){
                showAlert(
                        "Error",
                        "Please select an account."
                );
                return;
            }
            //Validate transfer destination
            if (isTransfer){
                if(destinationAccount.getValue()==null){
                    showAlert(
                            "Error",
                            "Please select a destination account."
                    );
                    return;
                }
                if(accountType.getValue().equals(
                        destinationAccount.getValue())){
                    showAlert(
                            "Error",
                            "Source and destination accounts must be different."
                    );
                    return;
                }
            }
            // Savings account withdrawals are not allowed
            if (transactionType.equals("Withdraw")
            && accountType.getValue().equals("Savings Account")){
                showAlert(
                        "Withdrawal Not Allowed",
                        "Savings accounts do not allow withdrawals."
                );
                return;
            }
            //Check balance
            if (isBalance){
                resultLabel.setText(
                        "Selected :"+ accountType.getValue()
                );
                showAlert(
                        "Account Balance",
                        "Selected "+ accountType.getValue()
                        + ".Connect the account model to display its actual balance."
                );
                return;
            }
            // Validate amount
            if (amountField.getText().isEmpty()){
                showAlert(
                        "Error",
                        "Please enter an amount."
                );
                return;
            }

            try {
                double amount = Double.parseDouble(
                        amountField.getText()
                );
                if (amount <= 0){
                    showAlert(
                            "Error",
                            "Amount must be greater than zero."
                    );
                    return;
                }
                String message;
                if (isTransfer){
                    message = "Transfer from"
                              + accountType.getValue()
                              +" to "
                              +destinationAccount.getValue()
                              +" | BWP " + amount;
                } else {
                    message = transactionType
                            + " | Account:"
                            + accountType.getValue()
                            + " | BWP " + amount;
                    }
                    resultLabel.setText (message);

                    showAlert(
                            "Transaction",
                            message
                    );

                }catch (NumberFormatException ex){
                    showAlert(
                            "Error",
                            "Please enter a valid numeric amount."
                    );
                }
            });
         backButton.setOnAction(
                e -> showMainMenu()
         );
         pane.getChildren().addAll(
                 title,
                 accountLabel,
                 accountType,
                 destinationLabel,
                 destinationAccount,
                 amountLabel,
                 amountField,
                 resultLabel,
                 submitButton,
                 backButton
         );
         stage.setScene(new Scene(pane,600,500));

        }
        // Displays alert messages
        private void showAlert(String heading,String message){
            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION
            );
            alert.setTitle("BANKING SYSTEM");
            alert.setHeaderText(heading);
            alert.setContentText(message);

            alert.showAndWait();
        }
        public static void main (String[] args){
            launch(args);
        }
    }