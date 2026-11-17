package banking.model;
import java.time.LocalDate;
import java.util.Date;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.0005;

    public SavingsAccount (Date dateOpened ,int accountNumber, double balance, String branch, LocalDate openingDate, int customerID, String transactions){
        super(accountNumber, balance,branch,dateOpened ,transactions);
    }
    @Override
    public double calculateInterest(){
        return getBalance()*INTEREST_RATE;
    }

    public void applyMonthlyInterest(){
        creditInterest(calculateInterest());
    }
    private void creditInterest(double v) {
    }

    public boolean withdraw (double amount) {
        throw new UnsupportedOperationException(
                "Savings accounts do not allow withdrawals.");
    }

    public AccountType getAccountType(){
        return AccountType.SAVINGS;
    }
}
