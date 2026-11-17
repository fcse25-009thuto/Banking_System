package banking.model;
    import java.util.Date;

public class InvestmentAccount extends Account {
    private static final double INTEREST_RATE = 0.05;
    private static final double MINIMUM_DEPOSIT = 500.00;

    public InvestmentAccount(int accountNumber, double initialDeposit, String branch, Date openingDate, String customerID){
        super(accountNumber, validateDeposit(initialDeposit),branch, openingDate, customerID);
    }

    private static double validateDeposit(double amount){
        if (amount< MINIMUM_DEPOSIT ){
            throw new IllegalArgumentException(
                    "Minimum opening deposit is BWP500.");
        }
        return amount;
    }

    public boolean withdraw (double amount){
        deduct(amount);
        return false;
    }

    private void deduct(double amount) {
    }

    @Override
    public double calculateInterest(){
        return getBalance()* INTEREST_RATE;
    }

    public void applyMonthlyInterest() {
        creditInterest(calculateInterest());
    }

    private void creditInterest(double v) {
    }

    public AccountType getAccountType(){
        return AccountType.INVESTMENT;
    }
}
