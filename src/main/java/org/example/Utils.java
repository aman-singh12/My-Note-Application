package org.example;

import java.util.regex.Pattern;

public class Utils {

    public static boolean isValidInput(String input) {
        String pattern = "[a-zA-Z0-9]{4,20}";
        return Pattern.matches(pattern, input);
    }
}
