package banking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer{
    private int customerId;
    private String address;
    private int phoneNumber;
    private String email;
    private String password;
    private String accountType;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(int customerID, String address, int phoneNumber, String email, String password){
        this.customerId = customerID;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }
    public int customerId(){
        return customerId;
    }
    public String address(){
        return address;
    }
    public int phoneNumber(){
        return phoneNumber;
    }
    public String email(){
        return email;
    }
    public String password(){
        return password;
    }
    public String accountType(){
        return accountType;
    }
    public void addAccount(Account account){
        if (account == null){
            throw new IllegalArgumentException(
                    "Account cannot be null."
            );
        }
        if (account.getCustomer() != null && account.getCustomer() != this) {
            throw new IllegalArgumentException(
                    "Account must belong to this customer."
            );
        }
        account.setCustomer(this);
        accounts.add(account);
    }

    public void removeAccount(int accountNumber){
        accounts.removeIf(account->
                account.getAccountNumber() == accountNumber
        );
    }
    public List<Account>getAccounts(){
        return Collections.unmodifiableList(accounts);
    }
    public double viewBalance(int accountNumber){
        return findAccount(accountNumber).getBalance();
    }
    public double calculateTotalInterest(){
        double totalInterest = 0;

        for(Account account : accounts){
            totalInterest += account.calculateInterest();
        }
        return totalInterest;
    }
    private Account findAccount(int accountNumber){
        for(Account account : accounts){
            if (account.getAccountNumber() == accountNumber){
                return account;
            }
        }

        throw new IllegalArgumentException(
                "Account not found:"+ accountNumber);
    }
}
