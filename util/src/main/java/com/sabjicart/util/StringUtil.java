package com.sabjicart.util;

public class StringUtil
{
    public static final String EMPTY_STRING = "";

    public static boolean nullOrBlankOrEmptyString (String value)
    {
        return (value == null || value.trim().equals(EMPTY_STRING)
            || value.equalsIgnoreCase("null"));
    }

    public static String trimByLength (String message, int length)
    {

        if (!nullOrBlankOrEmptyString(message)) {
            if (message.length() > length)
                message.substring(0, length);
            else
                return message;
        }
        return message;
    }

    public static String toCamelCase (String str)
    {

        if (!nullOrBlankOrEmptyString(str) && str.length() > 1) {
            return str.substring(0, 1).toUpperCase() + str.substring(1)
                                                          .toLowerCase();
        }
        return str;
    }
}
