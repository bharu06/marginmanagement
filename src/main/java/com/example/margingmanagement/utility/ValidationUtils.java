package com.example.margingmanagement.utility;

public class ValidationUtils {


    public static void checkIfNotNull(String fieldName, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " should not be null");
        }
    }

    public static void checkStringLength(String fieldName, Object value, int length) {
        checkIfNotNull(fieldName, value);
        String valStr = (String) value;

        //Exclude Star
        if (valStr.equals("*")) {
            return;
        }
        if (valStr.length() != length) {
            throw new IllegalArgumentException(fieldName + " value length should be " + length + " Actual length is " + valStr.length() + " Value is " + valStr);
        }
    }

    public static void isInteger(String fieldName, Object value) {
        checkIfNotNull(fieldName, value);
        if (!(value instanceof Integer)) {
            throw new IllegalArgumentException(fieldName + " value type should be Integer Actual value is " + value);
        }
    }

    public static void isDouble(String fieldName, Object value) {
        checkIfNotNull(fieldName, value);
        if (!(value instanceof Double || value instanceof Integer)) {
            throw new IllegalArgumentException(fieldName + " value type should be Double/Int Actual value is " + value);
        }
    }
}
