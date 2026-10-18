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
        if (!isLoginSuccess){
            System.out.println("Login Failed. You cannot access QuickChat.");
        }

               

 
     //part two
    
        System.out.println("\n===========================================");
        System.out.println("WELCOME TO QUICKCHAT");
        System.out.println("\n===========================================");
        
            int totalToEnter = readInt(scanner, "How many messages do you wish to enter?",1,Integer.MAX_VALUE);
            int messagesEntered = 0;
            
            boolean exitChat = false;
            while (!exitChat){
                System.out.println("/n---QUICKCHAT MAIN MENU---");
                    System.out.println("Choose an option (1-3: ");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Display Total Sent Messages and Details");
                    System.out.println("3. Quit QuickChat");
                    
                    
            int option = readInt(scanner, "Option: ", 1,3); 
            
               
               switch (option){
                   case 1:
                       if (messagesEntered >= totalToEnter){
                           System.out.println("You have already entered all" + totalToEnter+ "message(s).");
                           break;
                       }
                       for (int i = messagesEntered; i < totalToEnter; i++) {
                           System.out.println("\n---Message" + (i+1) + "of" + totalToEnter + "---");
                       
                       String recipient;
                       while (true){
                           System.out.println("Enter the recipient cell phone number (withthe international code) ");
                           recipient = scanner.nextLine().trim();
                           if (Message.isValidCell(recipient)){
                               System.out.println("Cell phone number captured successfuly");
                               break;
                           }
                           System.out.println("Cell Phone number is incorrecly formatted or does not contain an international code. Please correct the number and try again.");
                           
                       }
                       String text;
                       while (true){
                           System.out.println("Enter your message: ");
                           text = scanner.nextLine() ;
                           if (text.length() <=250) {
                               System.out.println("Message Sent");
                               break;
                           }
                           System.out.println("Please ener a message of less than 250 characthers.");
                       }
                       Message message = new Message(recipient, text);
                       System.out.println("Message ID generated: " + message.getMessageID());
                       
                       System.out.println("\nChoose what to do with this message.");
                       System.out.println("1) Send Message");
                       System.out.println("2) Disregard Message");
                       System.out.println("3) store Message to send later");
                       int choice = readInt(scanner,"Choice: ", 1, 3);
                       
                       System.out.println(message.SentMessage(choice));
                       
                       if (choice == 1) {
                           System.out.println("\n" +message.getMessageDetails());
                       }else if (choice == 2) {
                           readInt(scanner, "", 0, 0);
                           System.out.println("Message deleted.");
                       }
                       messagesEntered++;
                }
                       
               
                System.out.println("\nTotal number of messages sent: " + Message.returnTotalMessages());
                break;
               
                  case 2 :{ 
                        System.out.println("Coming Soon.");
                        break;
            }
                    case 3 : 
                        System.out.println("Goodbye.");
                        exitChat = true;
                        break;
            }
             
            }
    }
    
    
    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            if (!prompt.isEmpty()) {
                System.out.println(prompt);
            } 
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if  (value >= min && value <= max) {
                    return value;
                }
            }catch (NumberFormatException e) {
                }
             System.out.println("Invalid input, please try agin");   
        }
    }
}

               
               
               
               
               
     


