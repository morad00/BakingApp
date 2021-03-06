package com.example.android.bakingapp;

/**
 * Created by Mourad on 12/31/2017.
 */
public class TextUtils {

    public static String capitalizeEachWords(String text) {
        String[] stringArray = text.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            stringBuilder.append(cap).append(" ");
        }
        return stringBuilder.toString();
    }

    public static String removeTrailingZero(String stringNumber) {
        return !stringNumber.contains(".") ? stringNumber :
                stringNumber.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static String getExtension(String string) {
        if (string.length() == 3) {
            return string;
        } else if (string.length() > 3) {
            return string.substring(string.length() - 3);
        } else {
            throw new IllegalArgumentException("Word has less than 3 characters!");
        }
    }

}
