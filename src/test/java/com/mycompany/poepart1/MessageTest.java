/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poepart1;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Student
 */
public class MessageTest {

    private Message msg1;
    private Message msg2;
    private Message msg3;
    private Message msg4;
    private Message msg5;

    @BeforeEach
    public void setUp() {
        Message.getSentMessagesList().clear();
        Message.getStoredMessagesList().clear();
        Message.getDisregardedMessagesList().clear();

        msg1 = new Message("+27834557896", "Did you get the cake?", "Sent");
        Message.populateMessage(msg1);

        msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        Message.populateMessage(msg2);

        msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        Message.populateMessage(msg3);

        msg4 = new Message("08388884567", "It is dinner time !", "Sent");
        Message.populateMessage(msg4);

        msg5 = new Message("+27838884567", "Ok, I am leaving without you.", "Stored");
        Message.populateMessage(msg5);
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        List<Message> sent = Message.getSentMessagesList();
        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0).getMessageText());
        assertEquals("It is dinner time !", sent.get(1).getMessageText());
    }

    @Test
    public void testDisplayLongestMessage() {
        String longest = Message.getLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        String result = Message.searchByMessageID(msg4.getMessageID());
        assertEquals("It is dinner time !", result);
    }

    @Test
    public void testSearchByRecipient() {
        List<String> results = Message.searchByRecipient("+27838884567");
        assertEquals(2, results.size());
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageUsingMessageHash() {
        String hash = msg2.getMessageHash();
        String result = Message.deleteByMessageHash(hash);
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
    }

    @Test
    public void testDisplayReport() {
        String report = Message.displayMessageReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
    }
}