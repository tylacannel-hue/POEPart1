/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.poepart1;

import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("Welcome To Account Registration");
        System.out.println("==========================================");

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

        System.out.println("\n==========================================");
        System.out.println("WELCOME TO QUICKCHAT");
        System.out.println("==========================================");

        int totalToEnter = readInt(scanner, "How many messages do you wish to enter?", 1, Integer.MAX_VALUE);
        int messagesEntered = 0;

        boolean exitChat = false;
        while (!exitChat) {
            System.out.println("\n--- QUICKCHAT MAIN MENU ---");
            System.out.println("1) Send messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Store messages");

            int option = readInt(scanner, "Option: ", 1, 4);

            switch (option) {
                case 1 -> {
                    if (messagesEntered >= totalToEnter) {
                        System.out.println("You have already entered all " + totalToEnter + " message(s).");
                        break;
                    }
                    for (int i = messagesEntered; i < totalToEnter; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + totalToEnter + " ---");

                        String recipient;
                        while (true) {
                            System.out.println("Enter the recipient cell number (with the international code): ");
                            recipient = scanner.nextLine().trim();
                            if (Message.isValidCell(recipient)) {
                                System.out.println("Cell phone number captured successfully");
                                break;
                            }
                            System.out.println("Cell Phone number is incorrectly formatted or does not contain an international code. Please correct the number.");
                        }

                        String text;
                        while (true) {
                            System.out.println("Enter your message: ");
                            text = scanner.nextLine();
                            if (text.length() <= 250) {
                                System.out.println("Message Sent");
                                break;
                            }
                            System.out.println("Please enter a message of less than 250 characters.");
                        }

                        Message message = new Message(recipient, text);
                        System.out.println("Message ID generated: " + message.getMessageID());

                        System.out.println("\nChoose what to do with this message.");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        int choice = readInt(scanner, "Choice: ", 1, 3);

                        System.out.println(message.SentMessage(choice));

                        if (choice == 1) {
                            System.out.println("\n" + message.getMessageDetails());
                        } else if (choice == 2) {
                            readInt(scanner, "", 0, 0);
                            System.out.println("Message deleted.");
                        }
                        messagesEntered++;
                    }
                    System.out.println("\nTotal number of messages sent: " + Message.returnTotalMessages());
                }
                case 2 -> {
                    System.out.println("Coming soon.");
                }
                case 3 -> {
                    System.out.println("Goodbye.");
                    exitChat = true;
                }
                case 4 -> {
                    boolean exitSubMenu = false;
                    while (!exitSubMenu) {
                        System.out.println("\n--- STORED MESSAGES MENU ---");
                        System.out.println("1) Display Sent Messages Report");
                        System.out.println("2) Display Details for Longest Message");
                        System.out.println("3) Search Message by ID");
                        System.out.println("4) Search Messages by Recipient");
                        System.out.println("5) Delete Message using Message Hash");
                        System.out.println("6) Read and Display Stored JSON Messages");
                        System.out.println("7) Exit to Main Menu");

                        int subOption = readInt(scanner, "Select option (1-7): ", 1, 7);

                        switch (subOption) {
                            case 1 -> System.out.println(Message.displayMessageReport());
                            case 2 -> System.out.println("Longest Message: " + Message.getLongestMessage());
                            case 3 -> {
                                System.out.print("Enter Message ID to search: ");
                                String id = scanner.nextLine().trim();
                                System.out.println("Result: " + Message.searchByMessageID(id));
                            }
                            case 4 -> {
                                System.out.print("Enter Recipient Cell Number: ");
                                String cell = scanner.nextLine().trim();
                                List<String> results = Message.searchByRecipient(cell);
                                System.out.println("Messages for " + cell + ":");
                                if (results.isEmpty()) {
                                    System.out.println("No messages found.");
                                } else {
                                    results.forEach(m -> System.out.println("- " + m));
                                }
                            }
                            case 5 -> {
                                System.out.print("Enter Message Hash to delete: ");
                                String hash = scanner.nextLine().trim();
                                System.out.println(Message.deleteByMessageHash(hash));
                            }
                            case 6 -> System.out.println(Message.readJsonFile());
                            case 7 -> {
                                System.out.println("Exiting sub-menu and returning to Main Menu...");
                                exitSubMenu = true;
                            }
                        }
                    }
                }
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
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Ignore and prompt again
            }
            System.out.println("Invalid input, please try again");
        }
    }
}