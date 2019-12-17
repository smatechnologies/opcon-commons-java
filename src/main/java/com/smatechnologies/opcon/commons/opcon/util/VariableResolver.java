package com.smatechnologies.opcon.commons.opcon.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class can substitute variables in a string with the form ${myvar}
 */
public class VariableResolver {

    /**
     * Prefix for variable expression
     */
    public static final String VARIABLE_EXPRESSION_PREFIX = "${";
    /**
     * Suffix for variable expression
     */
    public static final String VARIABLE_EXPRESSION_SUFFIX = "}";
    /**
     * Prefix for variable substitution
     */
    public static final String VARIABLE_PREFIX = "\\$\\{";
    /**
     * Suffix for variable substitution
     */
    public static final String VARIABLE_SUFFIX = "\\}";

    private static final Pattern PATTERN = Pattern.compile(VARIABLE_PREFIX + "(.+?)" + VARIABLE_SUFFIX);

    /**
     * Return the expression
     *
     * @param variableName
     * @return
     */
    public static String getVariableExpression(String variableName) {
        if (variableName == null || variableName.isEmpty()) {
            return variableName;
        }

        return VARIABLE_EXPRESSION_PREFIX + variableName + VARIABLE_EXPRESSION_SUFFIX;
    }

    /**
     * Get all needed variables that are used in the specified string.
     * 
     * @param inputList
     *            the string to analyze
     * @return a set of variables used in the string.
     */
    public static Set<String> getVariableNames(Collection<String> inputList) {
        Set<String> variables = new LinkedHashSet<>();
        // collect variable names
        for (String input : inputList) {
            variables.addAll(VariableResolver.getVariableNames(input));
        }
        return variables;
    }

    /**
     * Get all needed variables that are used in the specified string.
     * 
     * @param input
     *            the string to analyze
     * @return a set of variables used in the string.
     */
    public static Set<String> getVariableNames(String input) {
        Matcher m = PATTERN.matcher(input);
        Set<String> varSet = new LinkedHashSet<>();
        while (m.find()) {
            varSet.add(m.group(1));
        }

        return varSet;
    }

    /**
     * Substitute strings (variables) in the original string.<br>
     * example code
     * <p>
     * <code> new HashMap() { {("dude", "BiGuP"); ("typeof","JAVA");} };</code>
     *
     * <pre>
     * string : &quot;Hey [[dude]] this [[typeof]] code rocks!&quot;;
     * result : &quot;Hey BiGuP this JAVA code rocks!&quot;;
     * </pre>
     *
     * @param original
     *            the string where replacements will occur
     * @param substitutions
     *            the Variables=>Values that will be replaced.
     * @return the replaced string
     */
    public static String substituteVariables(final String original, final Map<String, String> substitutions) {
        Matcher m = PATTERN.matcher(original);
        StringBuilder replaced = new StringBuilder();
        int s = 0;
        while (m.find()) {
            final String varName = m.group(1);
            if (substitutions.containsKey(varName)) { // has a value for this variable
                String value = substitutions.get(m.group(1));
                replaced.append(original, s, m.start());
                replaced.append(value);
                s = m.end();
            }
        }
        replaced.append(original, s, original.length());
        return replaced.toString();
    }

    /**
     * Substitute strings (variables) in the original List<String>.<br>
     *
     * @param list
     *            the string list where replacements will occur
     * @param substitutions
     *            the Variables=>Values that will be replaced.
     * @return the replaced string list
     */
    public static List<String> substituteVariables(final List<String> list, final Map<String, String> substitutions) {
        List<String> substitutedList = new ArrayList<>();

        //Replace variables for each event
        for (String item : list) {
            final String substitutedItem = VariableResolver.substituteVariables(item, substitutions);

            substitutedList.add(substitutedItem);
        }

        return substitutedList;
    }
}
