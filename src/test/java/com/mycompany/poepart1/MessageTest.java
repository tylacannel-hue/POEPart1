/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poepart1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Student
 */
public class MessageTest {

    private static final String MSG1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String MSG2 = "Hi James, did you receive the payment?";

    @BeforeEach
    public void setUp() {
        Message.resetAll();
    }

    // ---- Message length (success and failure) ----
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", Message.checkMessageLength(MSG1));
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "a".repeat(260);
        assertEquals("Message exceeds 250 characters by 10, please reduce the size.",
                Message.checkMessageLength(longMsg));
    }

    // ---- Recipient number (success and failure) ----
    @Test
    public void testRecipientSuccess() {
        Message m = new Message("0123456789", 0, "+27718693002", MSG1);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message m = new Message("0123456789", 1, "08575975889", MSG2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                m.checkRecipientCell());
    }

    // ---- Message hash ----
    @Test
    public void testMessageHashTestCase1() {
        Message m = new Message("0012345678", 0, "+27718693002", MSG1);
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }

    @Test
    public void testMessageHashTestCase2() {
        Message m = new Message("0012345678", 1, "08575975889", MSG2);
        assertEquals("00:1:HIPAYMENT", m.createMessageHash());
    }

    @Test
    public void testMessageHashLoop() {
        // first word + last word of the message, with the message number counting up from 0
        int number = 0;
        for (int i = 0; i < 3; i++) {
            Message m = new Message("9912345678", number, "+27718693002", "Hello there friend");
            assertEquals("99:" + number + ":HELLOFRIEND", m.createMessageHash());
            number++;
        }
    }

    // ---- Message ID ----
    @Test
    public void testMessageIdCreated() {
        String id = Message.createMessageID();
        assertEquals(10, id.length());
        Message m = new Message("0123456789", 0, "+27718693002", MSG1);
        assertTrue(m.checkMessageID());
        System.out.println("Message ID generated: " + id);
    }

    // ---- Send / Disregard / Store ----
    @Test
    public void testSendMessage() {
        Message m = new Message("0012345678", 0, "+27718693002", MSG1);
        assertEquals("Message successfully sent", m.SentMessage(1));
        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testDisregardMessage() {
        Message m = new Message("0012345678", 1, "08575975889", MSG2);
        assertEquals("Press 0 to delete the message", m.SentMessage(2));
        assertEquals(0, Message.returnTotalMessages());
    }

    @Test
    public void testStoreMessage() {
        Message m = new Message("0012345678", 0, "+27718693002", MSG1);
        assertEquals("Message successfully stored", m.SentMessage(3));
    }

    // ---- Total messages ----
    @Test
    public void testReturnTotalMessages() {
        new Message("0012345678", 0, "+27718693002", MSG1).SentMessage(1);
        new Message("0112345678", 1, "+27718693002", MSG2).SentMessage(1);
        assertEquals(2, Message.returnTotalMessages());
    }
}