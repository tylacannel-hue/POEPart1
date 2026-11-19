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

        // ===================== PART ONE: REGISTRATION =====================
        System.out.println("==================================================");
        System.out.println("Welcome To Account Registration");
        System.out.println("==================================================");

        System.out.println("Enter your First Name: ");
        String firstName = scanner.nextLine();

        System.out.println("Enter your Last Name: ");
        String lastName = scanner.nextLine();

        System.out.println("Enter your Username: ");
        String username = scanner.nextLine();

        System.out.println("Enter your Password: ");
        String password = scanner.nextLine();

        System.out.println("Enter your Cell Phone Number: ");
        String cellPhoneNumber = scanner.nextLine();

        Login obj = new Login(firstName, lastName, username, password, cellPhoneNumber);

        String registrationStatus = obj.reisterUser(username, password);
        System.out.println("\nRegistration Status: " + registrationStatus);

        // ===================== PART ONE: LOGIN =====================
        System.out.println("==================================================");
        System.out.println("ACCOUNT LOGIN");
        System.out.println("==================================================");

        System.out.println("Please Enter your Username: ");
        String loginUser = scanner.nextLine();

        System.out.println("Please Enter your Password: ");
        String loginPass = scanner.nextLine();

        boolean isLoginSuccess;
        if (username.equals(loginUser) && password.equals(loginPass)) {
            isLoginSuccess = true;
            obj.loginUser(username, password, loginUser, loginPass);
        } else {
            isLoginSuccess = false;
        }
        System.out.println(obj.returnLoginStatus(firstName, lastName, isLoginSuccess));

        // Users may only send messages if they have logged in successfully
        if (!isLoginSuccess) {
            System.out.println("Login failed. You cannot access QuickChat.");
            scanner.close();
            return;
        }  
        // ===================== PART TWO: SENDING MESSAGES =====================
        System.out.println("\n==================================================");
        System.out.println("Welcome to QuickChat.");
        System.out.println("==================================================");

        // User defines how many messages they wish to enter when the app starts
        int totalToEnter = readInt(scanner, "How many messages do you wish to enter? ", 1, Integer.MAX_VALUE);
        int messagesEntered = 0;

        boolean exitChat = false;
        while (!exitChat) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            int option = readInt(scanner, "Option: ", 1, 3);

            switch (option) {
                case 1:
                    if (messagesEntered >= totalToEnter) {
                        System.out.println("You have already entered all " + totalToEnter + " message(s).");
                        break;
                    }

                    for (int i = messagesEntered; i < totalToEnter; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + totalToEnter + " ---");

                        // Recipient: reuse the cell number check from Part 1
                        String recipient;
                        while (true) {
                            System.out.println("Enter the recipient cell number (with international code): ");
                            recipient = scanner.nextLine().trim();
                            if (Message.isValidCell(recipient)) {
                                System.out.println("Cell phone number successfully captured.");
                                break;
                            }
                            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        }

                        // Message: must not exceed 250 characters
                        String text;
                        while (true) {
                            System.out.println("Enter your message: ");
                            text = scanner.nextLine();
                            if (text.length() <= 250) {
                                System.out.println("Message sent");
                                break;
                            }
                            System.out.println("Please enter a message of less than 250 characters.");
                        }

                        Message message = new Message(recipient, text);
                        System.out.println("Message ID generated: " + message.getMessageID());

                        System.out.println("\nChoose what to do with this message:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        int choice = readInt(scanner, "Choice: ", 1, 3);

                        System.out.println(message.SentMessage(choice));

                        if (choice == 1) { 
                             // Full details shown after sending
                            System.out.println("\n" + message.getMessageDetails());
                        } else if (choice == 2) {
                            readInt(scanner, "", 0, 0);
                            System.out.println("Message deleted.");
                        }

                        // Sent and stored messages count towards the limit; discarded ones do too
                        messagesEntered++;
                    }

                    // Total gathered and displayed once all messages are done
                    System.out.println("\nTotal number of messages sent: " + Message.returnTotalMessages());
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println("Goodbye.");
                    exitChat = true;
                    break;
            }
        }

        scanner.close();
    }

    // Reads an int in [min, max]; keeps asking until valid
    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            if (!prompt.isEmpty()) {
                System.out.println(prompt);
            }
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
               
            }
            System.out.println("Invalid input, please try again" + (min == max ? " (enter " + min + ")." : "."));
        }
    }
}