package com.employee.demo.utils;

import java.util.regex.Pattern;

public class Validation {
    // Email validation helper
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // Phone number validation helper
    public static boolean isValidEGPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        return phoneNumber.matches("\\+20\\d{10}"); // validate EG numbers (with +20 and have exactly 13 characters)
    }
}
