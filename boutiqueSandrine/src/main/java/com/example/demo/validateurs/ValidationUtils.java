package com.example.demo.validateurs;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationUtils {

    // Valider un email
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Valider une date (format : "yyyy-MM-dd")
    public static boolean isValidDate(String dateStr) {
        if (dateStr == null) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Valider un timestamp (format : "yyyy-MM-dd HH:mm:ss")
    public static boolean isValidTimestamp(String timestampStr) {
        if (timestampStr == null) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        try {
            sdf.parse(timestampStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

     // Valider un mot de passe (au moins 8 caractères, une majuscule et un chiffre)
     public static boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    // Valider un nombre long (pour les IDs)
    public static boolean isValidLong(String longStr) {
        if (longStr == null) return false;
        try {
            Long.parseLong(longStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Valider un nombre (double)
    public static boolean isValidDouble(String doubleStr) {
        if (doubleStr == null) return false;
        try {
            Double.parseDouble(doubleStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}