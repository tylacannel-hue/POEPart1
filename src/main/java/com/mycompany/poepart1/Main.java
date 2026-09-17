/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.poepart1;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("Welcome To Account Registration");
        System.out.println("===========================================");

        System.out.println("Enter your First Name: ");   //capturing the users first name
        String firstName = scanner.nextLine();

        System.out.println("Enter your Last Name: ");    // capturin the users last name
        String lastName = scanner.nextLine();

        System.out.println("Enter your Usernme: ");    // capturin the users last name
        String username = scanner.nextLine();

        System.out.println("Enter your Password: ");    // capturin the users last name
        String password = scanner.nextLine();

        System.out.println("Enter your Cell Phone Number: ");    // capturin the users last name
        String cellPhoneNumber = scanner.nextLine();

        Login obj = new Login(firstName, lastName, username, password, cellPhoneNumber);

        /*  boolean validUsername = false;                  //loop for the users username
        while (!validUsername) {
            System.out.println("Enter Username(must contain '_' and be <=5 chars)");
            String username = scanner.nextLine();
            if (userLogin.checkUserName(username)) {
                userLogin.setusername(username);
                System.out.println("Username was successfully captured");
                validUsername = true;

            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is not more than 5 characters");

            }
        }
       boolean validPassword = false;        // loop for the users password
        while (!validPassowrd) {
            System.out.println("Enter password (minimun 8 chars, 1 uppercase, 1 digit, 1 special char): ");
            String password = scanner.nextLine();
            if (userLogin.checkPasswordComplexity(password)) {
                userLogin.setPassword(password);
                System.out.println("Password was successfully captured");
                validPassword = true;
            } else {
                System.out.println("Password is incorrectly formmatted; plese ensure that your password contains 8 characters, a captial letter, a number, and a special character");
            }
        }
        boolean validCell = false;
        while (!validCell) {
            System.out.println("Enter cell phone number (with international code, eg +27987562417):");
            String cellPhone = scanner.nextLine();
            if (userLogin.checkCellPhoneNumber()) {
                userLogin.setcellPhoneNumber(cellPhone);
                System.out.println("cell phone number is successfully captured");
                validCell = true;
            } else {
                System.out.println("cell phone number is incorrectly formmated or does not contain an international code; please correct the number and try again");
            }
        } */
        System.out.println("\nRegistration Status: " + obj.reisterUser(username, password));

        System.out.println("=================================================");
        System.out.println("ACCOUNT LOGIN");
        System.out.println("=================================================");

        System.out.println("Enter your Username: ");
        String loginUser = scanner.nextLine();

        System.out.println("Enter your Password: ");
        String loginPass = scanner.nextLine();
        boolean isLoginSuccess;
        if (username.equals(loginUser) && password.equals(loginPass)) {
            isLoginSuccess = true;
            obj.loginUser(username, password, loginUser, loginPass);
            obj.returnLoginStatus(firstName, lastName, isLoginSuccess);
        } else {
            isLoginSuccess = false;

            obj.returnLoginStatus(firstName, lastName, isLoginSuccess);
        }

        scanner.close();
    }

}
