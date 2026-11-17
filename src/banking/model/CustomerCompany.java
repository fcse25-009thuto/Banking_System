package banking.model;
public class CustomerCompany extends Customer{
    private String companyName;
    private String registrationNumber;
    private String contactPerson;
    private String companyAddress;
    private String industryType;

    public CustomerCompany (int customerId,  String address,int phoneNumber,String email, String password, String companyName, String registrationNumber, String contactPerson, String companyAddress, String industryType){
        super (customerId, address, phoneNumber, email, password);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.contactPerson = contactPerson;
        this.companyAddress = companyAddress;
        this.industryType = industryType;
    }

    public String getCompanyName (){
        return companyName;
    }
    public String getRegistrationNumber(){
        return registrationNumber;
    }
    public String getCompanyAddress(){
        return companyAddress;
    }
    public String getIndustryType(){
        return industryType;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public void setRegistrationNumber(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }
    public void setContactPerson(String con5tactPerson){
        this.contactPerson = contactPerson;
    }
    public void setCompanyAddress(String companyAddress){
        this.companyAddress = companyAddress;
    }
    public void setIndustryType(String industryType){
        this.industryType = industryType;
    }
}
