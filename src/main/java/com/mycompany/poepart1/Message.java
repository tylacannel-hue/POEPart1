/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


/**
 *
 * @author Student
 */
public class Message {
    
     private static int totalMessagesSent = 0;
    private static String sentMessages = "";
    private static String storedJson = "";
    private static final String JSON_FILE = "messages.json";

    private final String messageID;
    private final int numMessagesSent;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Normal constructor: ID is auto generated, number is the next message number
    public Message(String recipient, String messageText) {
        this(createMessageID(), totalMessagesSent, recipient, messageText);
    }

    // Constructor that lets unit tests supply a known ID and message number
    public Message(String messageID, int numMessagesSent, String recipient, String messageText) {
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Creates a random ten-digit ID
    public static String createMessageID() {
        Random random = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id += random.nextInt(10);
        }
        return id;
    }

    // Ensures the message ID is not more than ten characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Reuses the cell phone regex from Part 1 (international code + number)
    public static boolean isValidCell(String cell) {
        String cellNumberRegex = "^\\+\\d{1,3}\\d{8,10}$";
        return cell != null && cell.matches(cellNumberRegex);
    }

    public String checkRecipientCell() {
        if (isValidCell(recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Returns "" when valid, otherwise how many characters too long
    public static String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        int over = text.length() - 250;
        return "Message exceeds 250 characters by " + over + ", please reduce the size.";
    }

    // Hash = first two digits of ID : message number : FIRSTWORDLASTWORD
    public String createMessageHash() {
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

        String firstTwo = messageID.substring(0, 2);
        return (firstTwo + ":" + numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages += getMessageDetails() + "\n";
                return "Message successfully sent";
            case 2:
                return "Press 0 to delete the message";
            case 3:
                storeMessage();
                return "Message successfully stored";
            default:
                return "Invalid option";
        }
    }

    public String getMessageDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // All messages sent while the program is running
    public static String printMessages() {
        return sentMessages;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    } 
   
    public void storeMessage() {
        String entry = "  {\n"
                + "    \"messageID\": \"" + escape(messageID) + "\",\n"
                + "    \"messageHash\": \"" + escape(messageHash) + "\",\n"
                + "    \"recipient\": \"" + escape(recipient) + "\",\n"
                + "    \"message\": \"" + escape(messageText) + "\"\n"
                + "  }";

        if (storedJson.isEmpty()) {
            storedJson = entry;
        } else {
            storedJson += ",\n" + entry;
        }

        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write("[\n" + storedJson + "\n]");
        } catch (IOException e) {
            System.out.println("Could not write to " + JSON_FILE + ": " + e.getMessage());
        }
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    
    public static void resetAll() {
        totalMessagesSent = 0;
        sentMessages = "";
        storedJson = "";
    }
     // ===================== PART 3: ARRAYS =====================
    // Maximum number of items each array can hold (arrays have a fixed size)
    private static final int MAX_MESSAGES = 100;

    // These four arrays are PARALLEL arrays: index 0 in each array belongs to the
    // same sent message, index 1 to the next sent message, and so on.
    private static String[] sentMessageArray = new String[MAX_MESSAGES];   // Sent Message array
    private static String[] sentRecipientArray = new String[MAX_MESSAGES]; // recipient of each sent message
    private static String[] messageHashArray = new String[MAX_MESSAGES];   // Message Hash array
    private static String[] messageIDArray = new String[MAX_MESSAGES];     // Message ID array

    private static String[] disregardedMessageArray = new String[MAX_MESSAGES]; // Disregarded Messages array
    private static String[] storedMessageArray = new String[MAX_MESSAGES];      // Stored Messages array (filled from the JSON file)

    // Counters: how many items are currently in each array
    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;
    
    // ===================== PART 3: ARRAY METHODS =====================

    // Adds this message to the sent arrays. All four arrays use the SAME index (parallel arrays).
    public void addToSentArrays(){
        if (sentCount < MAX_MESSAGES){
            sentMessageArray[sentCount] = messageText;
            sentRecipientArray[sentCount] = recipient;
            messageHashArray[sentCount] = messageHash;
            messageIDArray[sentCount] = messageID;
            sentCount++; // move to the next empty position
        }
    }

    // Adds this message to the disregarded messages array
    public void addToDisregardedArray(){
        if (disregardedCount < MAX_MESSAGES){
            disregardedMessageArray[disregardedCount] = messageText;
            disregardedCount++;
        }
    }

    // Reads the JSON file line by line and copies every "message" value into the stored messages array.
    // Scanner and File are written out in full (java.util / java.io) so no new import is needed.
    // Reference: add your source here (e.g. the website or the textbook chapter on reading files)
    public static void readJsonIntoArray(){
        storedCount = 0; // start from empty so messages are not added twice
        try {
            java.util.Scanner fileScanner = new java.util.Scanner(new java.io.File(JSON_FILE));
            while (fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                // only lines that hold the message text look like:   "message": "text here"
                if (line.contains("\"message\":") && storedCount < MAX_MESSAGES){
                    int start = line.indexOf(": \"") + 3;   // position just after the opening quote
                    int end = line.lastIndexOf("\"");       // position of the closing quote
                    String text = line.substring(start, end);
                    // undo the escaping that storeMessage() did
                    text = text.replace("\\\"", "\"").replace("\\\\", "\\");
                    storedMessageArray[storedCount] = text;
                    storedCount++;
                }
            }
            fileScanner.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("No stored messages file found yet.");
        }
    }

    // Returns the stored messages (the ones read from the JSON file) as text to display
    public static String displayStoredMessages(){
        if (storedCount == 0){
            return "There are no stored messages.";
        }
        String output = "";
        for (int i = 0; i < storedCount; i++){
            output += (i + 1) + ". " + storedMessageArray[i] + "\n";
        }
        return output;
    }

    // Finds the longest sent message by looping through the array and remembering the biggest length
    public static String displayLongestMessage(){
        if (sentCount == 0){
            return "There are no sent messages.";
        }
        int longestIndex = 0; // assume the first message is the longest
        for (int i = 1; i < sentCount; i++){
            if (sentMessageArray[i].length() > sentMessageArray[longestIndex].length()){
                longestIndex = i; // found a longer one
            }
        }
        return "Longest message: " + sentMessageArray[longestIndex];
    }

    // Searches the parallel arrays for every message sent to the recipient the user typed in
    public static String searchByRecipient(String searchRecipient){
        String output = "";
        for (int i = 0; i < sentCount; i++){
            if (sentRecipientArray[i].equals(searchRecipient)){
                output += sentMessageArray[i] + "\n";
            }
        }
        if (output.isEmpty()){
            return "No messages found for " + searchRecipient;
        }
        return output;
    }

    // Searches the hash array for the hash the user typed in and deletes that message.
    // Because the arrays are parallel, the item must be removed from ALL of them.
    public static String deleteByHash(String searchHash){
        int foundIndex = -1; // -1 means "not found yet"
        for (int i = 0; i < sentCount; i++){
            if (messageHashArray[i].equals(searchHash.toUpperCase())){
                foundIndex = i;
                break;
            }
        }
        if (foundIndex == -1){
            return "Message hash not found.";
        }
        String deletedText = sentMessageArray[foundIndex];
        // shift every item after the deleted one back by one position
        for (int i = foundIndex; i < sentCount - 1; i++){
            sentMessageArray[i] = sentMessageArray[i + 1];
            sentRecipientArray[i] = sentRecipientArray[i + 1];
            messageHashArray[i] = messageHashArray[i + 1];
            messageIDArray[i] = messageIDArray[i + 1];
        }
        sentCount--; // one less item in the arrays
        return "Message \"" + deletedText + "\" successfully deleted.";
    }

    // Report of all sent messages: hash, ID, recipient and message
    public static String displayReport(){
        if (sentCount == 0){
            return "There are no sent messages.";
        }
        String output = "";
        for (int i = 0; i < sentCount; i++){
            output += "Message " + (i + 1)
                    + "\nMessage Hash: " + messageHashArray[i]
                    + "\nMessage ID: " + messageIDArray[i]
                    + "\nRecipient: " + sentRecipientArray[i]
                    + "\nMessage: " + sentMessageArray[i] + "\n\n";
        }
        return output;
    }

    public static void resetAll(){
        totalMessagesSent = 0;
        sentMessages = "" ;
        storedJson = "";
        sentCount = 0;          // Part 3: empty the arrays
        disregardedCount = 0;
        storedCount = 0;
    }
    
}

    



 //reference for Login & Message class
 // Standard E.164 International Phone Number Format Pattern:
 // Sourced from OWASP Regex Repository / Standard Java Pattern matching for international numbers.
// Farrell, J. (2022). Java Programming. 10th ed. S.L.: Cengage Learning.
// GeeksforGeeks (2019). Java assignment operators with examples. [online] GeeksforGeeks. Available at: https://www.geeksforgeeks.org/java/java-assignment-operator-with-examples/ [Accessed 18 October. 2026].
// GeeksforGeeks (2021). Java Program to Check For a Valid Mobile Number. [online] GeeksforGeeks. Available at: https://www.geeksforgeeks.org/java/java-program-to-check-for-a-valid-mobile-number/ [Accessed 17 October. 2026].
// Stack Overflow. (n.d.). javascript - Regex for password must contain at least eight characters, at least one number and both lower and uppercase letters and special characters. [online] Available at: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a [Accessed 18 October. 2026].