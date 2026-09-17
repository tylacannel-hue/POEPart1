/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart1;

/**
 *
 * @author Student
 */
public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    public Login(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

    }

    /* 
    public String getFirstName(){
        return firstName;
    }
    public String setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String setlastName(String lastName){
        this.lastName = lastName;
    }
    public String getusername(){
        return username;
    }
    public String setusername(String username){
        this.username = username;
    }
    public String getpassword(){
        return password;
    }
    public String setpassword(String password){
        this.password = password;
    }
    public String getCellPhoneNumber(){
        return cellPhoneNumber;
    }
    public String setcellPhoneNumber(String cellPhoneNumber){
        this.cellPhoneNumber = cellPhoneNumber;
    }
    
     */

    

    public boolean checkUserName(String username) {
        boolean CU;

        if (username.contains("_") && username.length() <= 5) {
            System.out.println("Username successfully captured");
            CU = true;
        } else {
            System.out.println("Username is not correctly formatted; username needs to be at least 5 characters long and needs to contain an underscore.");
            CU = false;
        }
        return CU;

    }

    public boolean checkPasswordComplexity(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        boolean CP;

        if (password.matches(passwordRegex)) {
            CP = true;
            System.out.println("Password succesfully captured");
        } else {
            CP = false;
            System.out.println("Password is not correctly formatted; your password needs at least 8 characters, a number, a upper case and a special character");
        }
        return CP;
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {

        String cellNumberRegex = "\\+\\d{1,3}\\d{8,10}$";
        boolean CN;

        if (cellPhoneNumber.matches(cellNumberRegex)) {
            System.out.println("Cell phone number successfully captured");
            CN = true;
        } else {
            System.out.println("Cell phone number incorrectly formatted; or does not contain international code");
            CN = false;
        }
        return CN;
    }

    public String reisterUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more tan five characters";

        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that your username contains more than eight letters, a capital letter, a number, and a special character.";

        }

        return "The user has been registered successfully";

    }

    public boolean loginUser(String username, String password, String enteredUsername, String enteredPassword) {
       
        boolean LU;
        if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
            LU = true;
            System.out.println("You have entered your username and password correctly");
        } else {
            LU = false;
            System.out.println("Ypu have not entered your username or password correctly");
        }
      return LU;
    }

    public String returnLoginStatus(String name, String surname, boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome," + name + "  " + surname + ",it is great to see you again";
        } else {
            return "Username or password incorrect, please try again";
        }
    }
}
