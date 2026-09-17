/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Student
 */
public class LoginTest {

    /**
     * Unit test class for Login.java Validates checkUserName,
     * checkPasswordComplexity, checkCellPhoneNumber, loginUser, and
     * returnLoginStatus.
     */
    private Login login;

    @BeforeEach

    public void setUp() {
        // Initialize a Login instance before each test with valid initial data
        login = new Login("tyla", "cannel", "tyl_", "Drop081!", "+27098765432");

    }
// ==========================================
// 1. checkUserName() Tests
// ==========================================

    @Test

    public void testCheckUserNameSuccess() {

        // Positive Case: Username contains '_' and length <= 5
        boolean actual = login.checkUserName("kyl_1");
        assertTrue(actual, "Username should be correctly formatted.");

    }

    @Test

    public void testCheckUserNameFailure() {

        // Negative Case: Username missing '_' or exceeds 5 characters
        boolean actual = login.checkUserName("kyle!!!!!!");
        assertFalse(actual, "Username should fail formatting check.");

    }

// ==========================================
// 2. checkPasswordComplexity() Tests
// ==========================================
    @Test

    public void testCheckPasswordComplexitySuccess() {
        // Positive Case: Contains uppercase, lowercase, digit, special char, length >= 8
        boolean actual = login.checkPasswordComplexity("Ch3&secuR3!");
        assertTrue(actual, "Password meets complexity requirements.");

    }

    @Test

    public void testCheckPasswordComplexityFailure() {
        // Negative Case: Lacks uppercase, digits, and special characters
        boolean actual = login.checkPasswordComplexity("password");
        assertFalse(actual, "Password should fail complexity check.");

    }

// ==========================================
// 3. checkCellPhoneNumber() Tests
// ==========================================
    @Test

    public void testCheckCellPhoneNumberSuccess() {
        // Positive Case: International format with leading '+' and valid digit length
        boolean actual = login.checkCellPhoneNumber("+27831234567");
        assertTrue(actual, "Cell phone number should be correctly formatted.");

    }

    @Test

    public void testCheckCellPhoneNumberFailure() {
        // Negative Case: Missing international '+' prefix
        boolean actual = login.checkCellPhoneNumber("0831234567");
        assertFalse(actual, "Cell phone number should fail formatting check.");

    }

// ==========================================
// 4. loginUser() Tests
// ==========================================
    @Test

    public void testLoginUserSuccess() {
        // Positive Case: Entered credentials match registered credentials
        boolean actual = login.loginUser("kyl_1", "Ch3&secuR3!", "kyl_1", "Ch3&secuR3!");
        assertTrue(actual, "Login should succeed with matching credentials.");

    }

    @Test

    public void testLoginUserFailure() {
// Negative Case: Entered password does not match registered password
        boolean actual = login.loginUser("kyl_1", "Ch3&secuR3!", "kyl_1", "wrongpassword");
        assertFalse(actual, "Login should fail with mismatched credentials.");

    }

// ==========================================
// 5. returnLoginStatus() Tests
// ==========================================
    @Test

    public void testReturnLoginStatusSuccess() {
        // Positive Case: Welcome message on successful login
        String expected = "Welcome, Kylse Smith, it is great to see you again";
        String actual = login.returnLoginStatus("Kylse", "Smith", true);
        assertEquals(expected, actual);

    }

    @Test

    public void testReturnLoginStatusFailure() {
        // Negative Case: Error message on failed login
        String expected = "Username or password incorrect, please try again";
        String actual = login.returnLoginStatus("Kylse", "Smith", false);
        assertEquals(expected, actual, "Failure message should match expected string.");

    }
}
