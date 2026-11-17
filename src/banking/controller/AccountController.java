package banking.controller;
import banking.dao.AccountDAO;
import banking.dao.TransactionDAO;

public class AccountController {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public AccountController (AccountDAO accountDAO,
                              TransactionDAO transactionDAO){
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    //Handle deposits
    public boolean deposit(int accountNumber, double amount){
        if (amount <= 0){
            System.out.println("Enter a valid deposit amount.");
            return false;
        }

        boolean success = accountDAO.deposit(accountNumber, amount);
        if (success){
            transactionDAO.addTransaction(accountNumber, "DEPOSIT",  amount);
            System.out.println("Deposit successful.");
            return true;
        }
        System.out.println("Deposit failed.");
        return false;
    }

    // Handle withdrawals
    public boolean withdraw(int accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Enter a valid withdrawal amount.");
            return false;
        }
        boolean success = accountDAO.withdraw(accountNumber, amount);

        if (success) {
            transactionDAO.addTransaction(accountNumber, "WITHDRAW", amount);
            System.out.println("Withdrawal success");
            return true;
        }
        System.out.println("Insufficient funds or account not found.");
        return false;

    }

    //Check account balance
    public double checkBalance(int accountNumber){
        return accountDAO.getBalance(accountNumber);
    }
}
