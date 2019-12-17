package com.smatechnologies.opcon.commons.constants;

public interface SystemConstants {

	String EMPTY_STRING = "";
	String SPACE = " ";
	String UNDERSCORE = "_";
	String QUOTE_SINGLE = "'";
	String QUOTE = "\"";
	String PARENTHESE_OPEN = "(";
	String PARENTHESE_CLOSE = ")";
	String BRACKET_OPEN = "[";
	String BRACKET_CLOSE = "]";
	String BRACE_OPEN = "{";
	String BRACE_CLOSE = "}";
	String CHEVRON_OPEN = "<";
	String CHEVRON_CLOSE = ">";
	String SIGN_MINUS = "-";
	String SIGN_PLUS = "+";
	String DOLLAR = "$";
	String DOT = ".";
	String COMMA = ",";
	String COLON = ":";
	String COLON_SPACED = " : ";
	String SEMI_COLON = ";";
	String SLASH = "/";
	String BACK_SLASH = "\\";
	String PIPE = "|";
	String ASTERISK = "*";
	String PERCENT = "%";
	String TOKEN_OPEN = "[[";
	String TOKEN_CLOSE = "]]";
	String EQUAL = "\u003D"; // "="
	String NOT_EQUAL = "\u2260"; // "≠"
	String GREAT_OR_EQUAL = "\u2265"; // "≥"
	String LESS_OR_EQUAL = "\u2264"; // "≤"
	String LF = "\n";
	String CRLF = "\r\n";
	String TAB = "\t";
	String AT = "@";
	String AMPERSAND = "&";
	String UNKNOWN = "Unknown";
	String VERTICAL_TAB = "\u000B"; // Character cannot be displayed
	String QUESTION_MARK = "?";
	String TILDE = "~";
	
	/**
	 * The system dependent carriage return.
	 * <code>System.getProperty("line.separator");</code>
	 */
	String EOL = System.getProperty("line.separator");
}
