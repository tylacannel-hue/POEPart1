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
 
        System.out.println("\n===========================================");
        System.out.println("WELCOME TO QUICKCHAT");
        System.out.println("\n===========================================");
        
            // Ask first whether the user wants to create messages (teacher feedback)
            System.out.println("Would you like to create messages? (yes/no)");
            String createAnswer = scanner.nextLine().trim();
            if (!createAnswer.equalsIgnoreCase("yes")){
                System.out.println("Goodbye.");
                return;
            }
            int totalToEnter = readInt(scanner, "How many messages do you wish to enter?",1,Integer.MAX_VALUE);
            int messagesEntered = 0;
            
            boolean exitChat = false;
            while (!exitChat){
                System.out.println("\n---QUICKCHAT MAIN MENU---");
                    System.out.println("Choose an option (1-7:) ");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Display Total Sent Messages and Details");
                    System.out.println("3. Display the Longest Sent Message");
                    System.out.println("4. Search Messages by Recipient");
                    System.out.println("5. Delete a Message using its Hash");
                    System.out.println("6. Display Stored Messages (from JSON file)");
                    System.out.println("7. Quit QuickChat");
                    
                    
            int option = readInt(scanner, "Option: ", 1,7); 
            
               
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
                           System.out.println("Enter the recipient cell phone number (with the international code) ");
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
                        // Part 3: report of every sent message
                        System.out.println(Message.displayReport());
                        break;
            }
                    case 3 :
                        // Part 3: longest sent message
                        System.out.println(Message.displayLongestMessage());
                        break;
                    case 4 :
                        // Part 3: search the arrays for a recipient typed in by the user
                        System.out.println("Enter the recipient cell phone number to search for: ");
                        String searchRecipient = scanner.nextLine().trim();
                        System.out.println(Message.searchByRecipient(searchRecipient));
                        break;
                    case 5 :
                        // Part 3: delete a sent message using the hash typed in by the user
                        System.out.println("Enter the message hash to delete: ");
                        String searchHash = scanner.nextLine().trim();
                        System.out.println(Message.deleteByHash(searchHash));
                        break;
                    case 6 :
                        // Part 3: read the JSON file into the stored messages array, then show it
                        Message.readJsonIntoArray();
                        System.out.println(Message.displayStoredMessages());
                        break;
                    case 7 : 
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