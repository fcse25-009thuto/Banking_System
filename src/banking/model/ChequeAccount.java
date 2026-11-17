package banking.model;

import java.util.Date;

public class ChequeAccount extends Account{
    private String employmentDetails;
    private String company;
    private String  companyAddress;
    private boolean employed;

    public ChequeAccount  (int accountNumber, double balance, String branch, Date transactions, String employmentDetails, String company, String companyAddress, boolean employed, Date dateOpened){
        super(accountNumber, balance,branch, transactions, String.valueOf(dateOpened));

        if(!employed){
            throw new IllegalArgumentException(
                    "Customer must be employed to open a cheque account."
            );
        }

        this.employmentDetails = employmentDetails ;
        this.company = company;
        this.companyAddress = companyAddress;
        this.employed = true;
    }
    public String getEmploymentDetails(){
        return employmentDetails;
    }
    public String getCompany(){
        return company;
    }
    public String getCompanyAddress(){
        return companyAddress;
    }
    public boolean getEmployed(){
        return employed;
    }

    public void setEmploymentDetails(String employeeDetails){
        this.employmentDetails = employmentDetails;
    }
    public void setCompany(String company){
        this.company = company;
    }
    public void setCompanyAddress(String companyAddress){
        this.companyAddress = companyAddress;
    }
    public void setEmployed(boolean emplooyee){
        if(! employed){
            throw new IllegalArgumentException(
                    "Cheque account requires an employed customer"
            );

        }
    }
    //Withdrawal method
    public boolean withdraw (double amount){
        deduct(amount);
        return false;
    }

    private void deduct(double amount) {
    }

}

