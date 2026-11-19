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
        // Clear static lists before each test run
        Message.getSentMessagesList().clear();
        Message.getStoredMessagesList().clear();
        Message.getDisregardedMessagesList().clear();

        // Message 1 - Sent
        msg1 = new Message("+27834557896", "Did you get the cake?", "Sent");
        Message.populateMessage(msg1);

        // Message 2 - Stored
        msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        Message.populateMessage(msg2);

        // Message 3 - Disregard
        msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        Message.populateMessage(msg3);

        // Message 4 - Sent
        msg4 = new Message("08388884567", "It is dinner time !", "Sent");
        Message.populateMessage(msg4);

        // Message 5 - Stored
        msg5 = new Message("+27838884567", "Ok, I am leaving without you.", "Stored");
        Message.populateMessage(msg5);
    }

    // =========================================================================
    // 11 SEPARATE UNIT TESTS
    // =========================================================================

    // Test 1: Check Message ID validity (ID generated is <= 10 digits)
    @Test
    public void test1_CheckMessageID() {
        assertTrue(msg1.checkMessageID());
        assertNotNull(msg1.getMessageID());
        assertTrue(msg1.getMessageID().length() <= 10);
    }

    // Test 2: Check Recipient Cell Number - Valid Format (+27...)
    @Test
    public void test2_CheckRecipientCell_Valid() {
        String expected = "Cell phone number successfully captured.";
        assertEquals(expected, msg1.checkRecipientCell());
    }

    // Test 3: Check Recipient Cell Number - Invalid Format
    @Test
    public void test3_CheckRecipientCell_Invalid() {
        Message invalidMsg = new Message("0831234567", "Hello");
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number.";
        assertEquals(expected, invalidMsg.checkRecipientCell());
    }

    // Test 4: Check Message Length - Valid (<= 250 characters)
    @Test
    public void test4_CheckMessageLength_Valid() {
        String result = Message.checkMessageLength("Did you get the cake?");
        assertEquals("Message ready to send.", result);
    }

    // Test 5: Check Message Length - Exceeded (> 250 characters)
    @Test
    public void test5_CheckMessageLength_Exceeded() {
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("a");
        }
        String result = Message.checkMessageLength(longText.toString());
        assertEquals("Message exceeds 250 characters by 10, please reduce the size.", result);
    }

    // Test 6: Verify Create Message Hash generation
    @Test
    public void test6_CreateMessageHash() {
        String hash = msg1.getMessageHash();
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
        assertTrue(hash.contains("DIDCAKE"));
    }

    // Test 7: Sent Messages Array correctly populated (Developer entry for Test data 1-4)
    @Test
    public void test7_SentMessagesArrayCorrectlyPopulated() {
        List<Message> sent = Message.getSentMessagesList();
        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0).getMessageText());
        assertEquals("It is dinner time !", sent.get(1).getMessageText());
    }

    // Test 8: Display Details for Longest Message (Test data 1-4)
    @Test
    public void test8_DisplayLongestMessage() {
        String longest = Message.getLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    // Test 9: Search for Message ID (Test data message 4)
    @Test
    public void test9_SearchByMessageID() {
        String result = Message.searchByMessageID(msg4.getMessageID());
        assertEquals("It is dinner time !", result);
    }

    // Test 10: Search all messages sent or stored for recipient (+27838884567)
    @Test
    public void test10_SearchByRecipient() {
        List<String> results = Message.searchByRecipient("+27838884567");
        assertEquals(2, results.size());
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    // Test 11: Delete a message using Message Hash (Test Message 2)
    @Test
    public void test11_DeleteMessageUsingMessageHash() {
        String hash = msg2.getMessageHash();
        String result = Message.deleteByMessageHash(hash);
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
    }
}