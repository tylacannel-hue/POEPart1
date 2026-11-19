/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 * @author Student
 */

public class Message {

    private static int totalMessagesSent = 0;
    private static String sentMessagesText = "";
    private static final String JSON_FILE = "messages.json";

    // Part 3 Arrays & Dynamic Lists
    private static List<Message> sentMessagesList = new ArrayList<>();
    private static List<Message> disregardedMessagesList = new ArrayList<>();
    private static List<Message> storedMessagesList = new ArrayList<>();
    private static List<String> messageHashList = new ArrayList<>();
    private static List<String> messageIDList = new ArrayList<>();

    // Instance attributes
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag;

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public Message(String recipient, String messageText) {
        this(createMessageID(), totalMessagesSent + 1, recipient, messageText);
    }

    public Message(String messageID, int numMessagesSent, String recipient, String messageText) {
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.flag = "Sent";
    }

    public Message(String recipient, String messageText, String flag) {
        this.messageID = createMessageID();
        this.numMessagesSent = totalMessagesSent + 1;
        this.recipient = recipient;
        this.messageText = messageText;
        this.flag = flag;
        this.messageHash = createMessageHash();
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }

    public static String createMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public static boolean isValidCell(String cell) {
        String cellNumberRegex = "^\\+\\d{1,3}\\d{8,10}$";
        return cell != null && cell.matches(cellNumberRegex);
    }

    public String checkRecipientCell() {
        if (isValidCell(recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number.";
    }

    public static String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        int over = text.length() - 250;
        return "Message exceeds 250 characters by " + over + ", please reduce the size.";
    }

    public String createMessageHash() {
        if (messageText == null || messageText.trim().isEmpty()) {
            return "";
        }
        String trimmed = messageText.trim();
        int firstSpace = trimmed.indexOf(" ");
        int lastSpace = trimmed.lastIndexOf(" ");

        String firstWord;
        String lastWord;
        if (firstSpace == -1) {
            firstWord = trimmed;
            lastWord = trimmed;
        } else {
            firstWord = trimmed.substring(0, firstSpace);
            lastWord = trimmed.substring(lastSpace + 1);
        }

        firstWord = firstWord.replaceAll("[^A-Za-z0-9]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");

        String firstTwo = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : "00";
        return (firstTwo + ":" + numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
    }

    public String getMessageDetails() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                this.flag = "Sent";
                sentMessagesList.add(this);
                messageHashList.add(this.messageHash);
                messageIDList.add(this.messageID);
                sentMessagesText += getMessageDetails() + "\n---\n";
                return "Message successfully sent";
            case 2:
                this.flag = "Disregard";
                disregardedMessagesList.add(this);
                return "Press 0 to delete the message";
            case 3:
                this.flag = "Stored";
                storedMessagesList.add(this);
                storeMessageJson();
                return "Message successfully stored";
            default:
                return "Invalid choice";
        }
    }

    public static void populateMessage(Message msg) {
        if ("Sent".equalsIgnoreCase(msg.getFlag())) {
            sentMessagesList.add(msg);
            totalMessagesSent++;
        } else if ("Stored".equalsIgnoreCase(msg.getFlag())) {
            storedMessagesList.add(msg);
        } else if ("Disregard".equalsIgnoreCase(msg.getFlag())) {
            disregardedMessagesList.add(msg);
        }
        messageHashList.add(msg.getMessageHash());
        messageIDList.add(msg.getMessageID());
    }

    public void storeMessageJson() {
        try (FileWriter writer = new FileWriter(JSON_FILE, true)) {
            String jsonEntry = "{\"messageID\":\"" + messageID + "\",\"recipient\":\"" + recipient +
                               "\",\"messageText\":\"" + messageText + "\",\"hash\":\"" + messageHash + "\"}\n";
            writer.write(jsonEntry);
        } catch (IOException e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    public static String readJsonFile() {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(JSON_FILE)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (IOException e) {
            return "No stored JSON messages found.";
        }
        return sb.toString();
    }

    public static String getLongestMessage() {
        String longest = "";
        for (Message msg : sentMessagesList) {
            if (msg.getMessageText().length() > longest.length()) {
                longest = msg.getMessageText();
            }
        }
        for (Message msg : storedMessagesList) {
            if (msg.getMessageText().length() > longest.length()) {
                longest = msg.getMessageText();
            }
        }
        return longest;
    }

    public static String searchByMessageID(String id) {
        for (Message msg : sentMessagesList) {
            if (msg.getMessageID().equals(id)) {
                return msg.getMessageText();
            }
        }
        for (Message msg : storedMessagesList) {
            if (msg.getMessageID().equals(id)) {
                return msg.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    public static List<String> searchByRecipient(String recipientCell) {
        List<String> results = new ArrayList<>();
        for (Message msg : sentMessagesList) {
            if (msg.getRecipient().equals(recipientCell)) {
                results.add(msg.getMessageText());
            }
        }
        for (Message msg : storedMessagesList) {
            if (msg.getRecipient().equals(recipientCell)) {
                results.add(msg.getMessageText());
            }
        }
        return results;
    }

    public static String deleteByMessageHash(String hash) {
        for (int i = 0; i < sentMessagesList.size(); i++) {
            if (sentMessagesList.get(i).getMessageHash().equalsIgnoreCase(hash)) {
                String deletedText = sentMessagesList.get(i).getMessageText();
                sentMessagesList.remove(i);
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        for (int i = 0; i < storedMessagesList.size(); i++) {
            if (storedMessagesList.get(i).getMessageHash().equalsIgnoreCase(hash)) {
                String deletedText = storedMessagesList.get(i).getMessageText();
                storedMessagesList.remove(i);
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message Hash not found.";
    }

    public static String displayMessageReport() {
        if (sentMessagesList.isEmpty()) {
            return "No sent messages to display.";
        }
        StringBuilder report = new StringBuilder("=== SENT MESSAGES REPORT ===\n");
        for (Message msg : sentMessagesList) {
            report.append("Message Hash: ").append(msg.getMessageHash()).append("\n")
                  .append("Recipient: ").append(msg.getRecipient()).append("\n")
                  .append("Message: ").append(msg.getMessageText()).append("\n---------------------------\n");
        }
        return report.toString();
    }

    public static List<Message> getSentMessagesList() { return sentMessagesList; }
    public static List<Message> getDisregardedMessagesList() { return disregardedMessagesList; }
    public static List<Message> getStoredMessagesList() { return storedMessagesList; }
}


 //reference for Login & Message class
 // Standard E.164 International Phone Number Format Pattern:
 // Sourced from OWASP Regex Repository / Standard Java Pattern matching for international numbers.
// Farrell, J. (2022). Java Programming. 10th ed. S.L.: Cengage Learning.
// GeeksforGeeks (2019). Java assignment operators with examples. [online] GeeksforGeeks. Available at: https://www.geeksforgeeks.org/java/java-assignment-operator-with-examples/ [Accessed 18 October. 2026].
// GeeksforGeeks (2021). Java Program to Check For a Valid Mobile Number. [online] GeeksforGeeks. Available at: https://www.geeksforgeeks.org/java/java-program-to-check-for-a-valid-mobile-number/ [Accessed 17 October. 2026].
// Stack Overflow. (n.d.). javascript - Regex for password must contain at least eight characters, at least one number and both lower and uppercase letters and special characters. [online] Available at: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a [Accessed 18 October. 2026].