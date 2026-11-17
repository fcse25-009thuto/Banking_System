package banking.controller;

import banking.dao.CustomerDAO;

public class LoginController {
    private CustomerDAO customerDAO;
    public LoginController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    //Handle customer login
    public boolean login(String email, String password){
        if (email == null || email.isEmpty()
        || password == null || password.isEmpty()){
            System.out.println("Please enter email and password.");
            return false;
        }

        int customerID = customerDAO.login(email, password);

        if(customerID != -1){
            System.out.println("Login Successful.");
            return true;
        }
        System.out.println("Invalid email or password.");
        return false;
    }
}
