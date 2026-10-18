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
    private static int totalMessagesSent=0;
    private static String sentMessages = "";
    private static String storedJson ="";
    private static final String JSON_FILE= "messages.json";

    public static int returnTotalMessages(){
        return totalMessagesSent;
    }
    
   private String messageID;
   private int numMessagesSent;
   private String recipient;
   private String messageText;
   private String messageHash;
   
   
   public Message(String recipient,String messageText){
    this (createMessageID(),totalMessagesSent, recipient, messageText);
}
   public Message(String messageID,int numMessagesSent, String recipient, String messageText){
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
   }
   
   public static String createMessageID(){
       Random random = new Random();
       String id = "";
       for (int i = 0; i < 10; i++){
           id += random.nextInt(10);
       }
       return id;
   }
   
   public boolean checkMessageID(){
       return messageID != null && messageID.length() <=10;
       
   }
   
   public static boolean isValidCell (String cell){
       String cellNumberRegex = "\\+\\d{1,3}\\d{8,10}$"; 
       return cell != null && cell.matches(cellNumberRegex);
   }
   
   public String checkRecipientCell(){
       if (isValidCell(recipient)){
           return "Cell phone number successfully captured.";           
       }
       return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
   }
   
   public static String checkMessageLength(String text){
       if (text.length() <=250) {
           return "Message ready to send.";
       }
       int over =text.length()-250;
       return "Message exceeds 250 characters by "+ over + ", please reduce the size.";
   }
   
   public String createMessageHash(){
       String trimmed = messageText.trim();
       int firstSpace = trimmed.indexOf(" ");
       int lastSpace = trimmed.lastIndexOf(" ");
       
       String firstWord;
       String lastWord;
       if (firstSpace == -1){
           firstWord = trimmed;
           lastWord = trimmed;
       }else{
           firstWord = trimmed.substring(0,firstSpace);
           lastWord = trimmed.substring(lastSpace + 1);
       }
       
       firstWord = firstWord.replaceAll("[^A-Za-z0-9]", "");
       lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");
       
       String firstTwo = messageID.substring(0, 2);
       return (firstTwo + ":" + numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
   }
    public String SentMessage (int choice){
        switch (choice){
            case 1 -> {
                totalMessagesSent++; //(GeeksforGeeks, 2019)
                sentMessages += getMessageDetails() + "\n"; //(GeeksforGeeks, 2019) this reference was used to find an alternative way to do these lines
                return "Message successfully sent";
            }
            case 2 -> {
                return "Press 0 to delete the message";
            }
            case 3 -> {
                storeMessage();
                return"Message successfully stored";
            }
            default -> {
                return "Invalid option";
            }
        }
    }    
    public String getMessageDetails(){
        return "Message ID: " + messageID
        +"\nMessageHash: " + messageHash
        + "\nRecipient: " + recipient
        + "\nMessage: " + messageText;
    }
    
    public static String PrintMessages(){
        return sentMessages;
    }
    
    public void storeMessage(){
        String entry = "  {\n" 
                + "   \"messageID\": \"" + escape(messageID)+"\",\n"
                + "   \"messageHash\": \"" + escape(messageHash)+"\",\n"
                + "   \"recipient\": \"" + escape(recipient)+"\",\n"
                + "   \"message\": \"" + escape(messageText)+"\"\n"
                + " }";
    
    if (storedJson.isEmpty()){
        storedJson = entry;
   }else {
        storedJson += ",\n" + entry;
    }
    try (FileWriter writer = new FileWriter(JSON_FILE)){
        writer.write("[\n" + storedJson + "\n");       
    }catch (IOException e) {
        System.out.println("Could not write to " + JSON_FILE + ":" + e.getMessage());
    }
    }
    private static String escape (String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    public String getMessageID() { 
        return messageID; }
    
    public String getMessageHash() { 
        return messageHash; }
    
    public String getRecipient() { 
        return recipient; }
    
    public String getMessageText() { 
        return messageText; }
    
    public static void resetAll(){
        totalMessagesSent = 0;
        sentMessages = "" ;
        storedJson = "";
    }
    
}
