/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.poepart1;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
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

        System.out.println("Enter your Usernme: ");    // capturin the users username
        String username = scanner.nextLine();

        System.out.println("Enter your Password: ");    // capturin the users password
        String password = scanner.nextLine();

        System.out.println("Enter your Cell Phone Number: ");    // capturin the users cell phone number 
        String cellPhoneNumber = scanner.nextLine();

        Login obj = new Login(firstName, lastName, username, password, cellPhoneNumber);

        
        System.out.println("\nRegistration Status: " + obj.reisterUser(username, password));

        System.out.println("=================================================");
        System.out.println("ACCOUNT LOGIN");
        System.out.println("=================================================");

        System.out.println("Please Enter your Username: ");
        String loginUser = scanner.nextLine();

        System.out.println("Please Enter your Password: ");
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
 
     //part two
    {
        System.out.println("\n===========================================");
        System.out.println("WELCOME TO QUICKCHAT");
        System.out.println("\n===========================================");
        
            List<message> sentMessageList = new ArrayList<>();
            List<String> storedJsonMessageList = new ArrayList<>();
            
            boolean exitChat = false;
            while (!exitChat){
                System.out.println("/n---QUICKCHAT MAIN MENU---");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Display Total Sent Messages and Details");
                    System.out.println("3. Quit QuickChat");
                    System.out.println("Choose an option (1-3: ");
                    
               int option = 0;
               try{
                   option = Integer.parseInt(scanner.nextLine());
               }catch ()
               
                
            }
        
    }

}
