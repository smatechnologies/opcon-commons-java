package com.smatechnologies.opcon.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * Class to facilitate String manipulations.
 */
public class StringUtil {

    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String EMPTY = "";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = CR + LF;

    private static final String DOS_WILDCARD_STRICT = "=";
    private static final String DOS_WILDCARD_ALL = "*";
    private static final String DOS_WILDCARD_CHAR = "?";
    private static final String REGEX_WILDCARD_ALL = ".*";
    private static final String REGEX_WILDCARD_CHAR = ".";
    private static final String SQL_WILDCARD_ALL = "%";
    private static final String SQL_WILDCARD_CHAR = "_";

    /**
     * Checks whether or not the given String has content, <code>null</code> safe.
     *
     * @return <code>true</code> if the string is not <code>null</code> and is not an empty string
     */
    public static boolean hasContent(final String string) {
        return !isNullOrEmpty(string);
    }

    /**
     * @return <code>true</code> if the string is <code>null</code> or is an empty String
     */
    public static boolean isNullOrEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    public static String nullToEmpty(final String string) {
        if (string == null) {
            return EMPTY;
        } else {
            return string;
        }
    }

    public static String emptyToNull(final String string) {
        if (string != null && ! string.isEmpty()) {
            return string;
        } else {
            return null;
        }
    }

    /**
     * Test if a string contains invalid characters.
     * 
     * @param string
     *            the string to test
     * @param illegal
     *            the list of illegal characters as a string
     * @return <code>true</code> if the string contains illegal characters, <code>false</code> otherwise.
     */
    public static boolean containsIllegals(final String string, final String illegal) {
        Objects.requireNonNull(string, "String should be not null");
        if (illegal == null) {
            return false;
        }

        char[] invalid = illegal.toCharArray();
        char[] stringChars = string.toCharArray();
        for (char sChar : stringChars) {
            for (char inv : invalid) {
                if (sChar == inv) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Capitalize the first letter of the given String, <code>null</code> safe.
     *
     * @param string
     * @return String
     */
    public static String capitalizeFirstLetter(final String string) {
        if (string == null || string.length() == 0) {
            return string;
        } else {
            return string.substring(0, 1).toUpperCase() + string.substring(1);
        }
    }

    /**
     * @param string
     * @param forceStrict
     *
     * @return <code>true</code> if string contain at least one Dos Wildcard
     */
    public static boolean containsDosWildcard(String string, boolean forceStrict) {
        if (string == null || string.isEmpty()) {
            return false;
        }

        return (!forceStrict && string.startsWith(DOS_WILDCARD_STRICT)) || string.contains(DOS_WILDCARD_ALL) || string.contains(DOS_WILDCARD_CHAR);
    }

    /**
     * Convert a given DOS like pattern with wildcard(s) into a SQL LIKE string pattern (Transact-SQL).
     *
     * @param string the DOS kind of pattern (support stars (*), question marks (?) and strict (=))
     * @param escapeChar the escape character to use for SQL LIKE, needs to be specified in the query
     * @param forceStrict
     *
     * @return the SQL LIKE compliant string pattern
     */
    public static String convertDosWildcardStringToSqlServerLikeString(String string, final char escapeChar, boolean forceStrict) {
        if (string == null) {
            return null;
        }
        if (string.isEmpty()) {
            return forceStrict ? EMPTY : SQL_WILDCARD_ALL;
        }

        boolean strict = forceStrict;
        if (!forceStrict && string.startsWith(DOS_WILDCARD_STRICT)) {
            string = string.substring(1);
            strict = true;
        }
        if (string.isEmpty()) {
            return string;
        }

        final String escapeCharString = Character.toString(escapeChar);
        String newString = string;

        newString = newString.replace(escapeCharString, escapeCharString + escapeChar);
        newString = newString.replace(SQL_WILDCARD_ALL, escapeCharString + SQL_WILDCARD_ALL.charAt(0));
        newString = newString.replace(SQL_WILDCARD_CHAR, escapeCharString + SQL_WILDCARD_CHAR.charAt(0));
        newString = newString.replace("[", escapeCharString + '[');
        newString = newString.replace("]", escapeCharString + ']');

        newString = newString.replace(DOS_WILDCARD_ALL.charAt(0), SQL_WILDCARD_ALL.charAt(0));
        newString = newString.replace(DOS_WILDCARD_CHAR.charAt(0), SQL_WILDCARD_CHAR.charAt(0));

        if (!strict) {
            newString = SQL_WILDCARD_ALL + newString + SQL_WILDCARD_ALL;
        }

        return newString;
    }

    /**
     * Convert a given DOS like pattern with wildcard(s) into a regex string pattern
     *
     * @param string the DOS kind of pattern (support starts (*), question marks (?) and strict (=))
     * @param forceStrict
     *
     * @return the regex compliant string pattern
     */
    public static String convertDosWildcardStringToRegex(String string, boolean forceStrict) {
        if (string == null) {
            return null;
        }
        if (string.isEmpty()) {
            return forceStrict ? EMPTY : REGEX_WILDCARD_ALL;
        }

        boolean strict = forceStrict;
        if (!forceStrict && string.startsWith(DOS_WILDCARD_STRICT)) {
            string = string.substring(1);
            strict = true;
        }
        if (string.isEmpty()) {
            return string;
        }

        String newString = Pattern.quote(string);

        newString = newString.replace(DOS_WILDCARD_ALL, "\\E" + REGEX_WILDCARD_ALL + "\\Q");
        newString = newString.replace(DOS_WILDCARD_CHAR, "\\E" + REGEX_WILDCARD_CHAR + "\\Q");

        if (!strict) {
            newString = REGEX_WILDCARD_ALL + newString + REGEX_WILDCARD_ALL;
        }

        return newString;
    }

    /**
     * Convert a given DOS like pattern with wildcard(s) into a Opcon Api string pattern
     *
     * @param string the DOS kind of pattern (support starts (*), question marks (?) and strict (=))
     * @param forceStrict
     * @return
     */
    public static String convertDosWildcardStringToOpconApiString(String string, boolean forceStrict) {
        if (string == null) {
            return null;
        }
        if (string.isEmpty()) {
            return forceStrict ? EMPTY : DOS_WILDCARD_ALL;
        }

        boolean strict = forceStrict;
        if (!forceStrict && string.startsWith(DOS_WILDCARD_STRICT)) {
            string = string.substring(1);
            strict = true;
        }

        if (!strict) {
            string = DOS_WILDCARD_ALL + string + DOS_WILDCARD_ALL;
        }

        return string;
    }

    /**
     * Convenient method to url encode the given string.
     * @param toEncode
     * @return encode String
     */
    public static String urlEncode(String toEncode) {
        if (toEncode == null) {
            return null;
        }
        try {
            return URLEncoder.encode(toEncode, CHARSET_UTF8).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            return toEncode;
        }
    }

    /**
     * Convenient method to url encode curly braces { } on the given string. This method does not url encode the entire string!
     *
     * @param toEncode
     * @return the string with ONLY curly braces url encoded
     */
    public static String urlEncodeCurlyBraces(String toEncode) {
        if (toEncode == null || (!toEncode.contains("{") && !toEncode.contains("}"))) {
            return toEncode;
        }

        try {
            toEncode = toEncode.replaceAll("\\{", URLEncoder.encode("{", CHARSET_UTF8));
            toEncode = toEncode.replaceAll("\\}", URLEncoder.encode("}", CHARSET_UTF8));
            return toEncode;
        } catch (UnsupportedEncodingException e) {
            return toEncode;
        }
    }

    /**
     * Checks if value is integer
     *
     * @param value
     * @return
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
