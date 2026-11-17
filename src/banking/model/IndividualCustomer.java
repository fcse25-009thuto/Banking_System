package banking.model;

public class IndividualCustomer extends Customer {
    private String firstName;
    private String surname;
    private String dateOfBirth;
    private String nationalId;

    public IndividualCustomer (int customerId,  String address, int phoneNumber,String email,String password,String firstName, String surname, String dateOfBirth, String nationalId){
        super (customerId, address, phoneNumber, email, password);
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;

    }
    public String getFirstName(){
        return firstName;
    }
    public String getSurname(){
        return surname;
    }
    public String getDateOfBirth(){
        return dateOfBirth;
    }
    public String getNationalId(){
        return nationalId;
    }

    public void setFirstName(){
        this.firstName = firstName;
    }
    public void setSurname(){
        this.surname = surname;
    }
    public void setDateOfBirth(){
        this.dateOfBirth = dateOfBirth;
    }
    public void setNationalId(){
        this.nationalId = nationalId;
    }
}
