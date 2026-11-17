package banking.model;

import java.util.Date;

public class Account{
    private int accountNumber;
    private double balance;
    private String branch;
    private Date dateOpened;
    private String transactions;

    public Account (int accountNumber, double balance, String branch,Date dateOpened,String transactions){
        if (balance< 0){
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative"
            );

        }
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;
        this.dateOpened = dateOpened;
        this.transactions = transactions;
    }
    public void deposit (double amount){
        if(amount<=0){
            throw new IllegalArgumentException(
                    "Deposit must be greater than zero."
            );
        }

        balance += amount;
    }

    public boolean withdraw(double amount){
        if (amount <= 0 || amount>balance){
            return false;
        }
        balance-=amount;
        return true;
    }
    public int getAccountNumber(){
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public String getBranch(){
        return branch;
    }
    public Date getDateOpened(){
        return dateOpened;
    }
    public String getTransactions(){
        return transactions;
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
    }
    public void setBalance(double balance){
        this.balance = balance ;
    }
    public void setDateOpened(Date dateOpened){
        this.dateOpened = dateOpened;
    }
    public void  setBranch(String branch){
        this.branch = branch;
    }
    private Customer customer;

    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getCustomerId(){
        return customer == null ? -1 : customer.customerId();
    }

    public double calculateInterest() {
        return 0; // placeholder
    }


}
