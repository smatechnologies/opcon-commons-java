package com.smatechnologies.opcon.commons.opcon.event;

import com.smatechnologies.opcon.commons.constants.SystemConstants;
import com.smatechnologies.opcon.commons.opcon.util.ExpressionUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parser of Event
 * 
 * @author Pierre PINON
 */
public class OpconEventParser {

    public static final String EVENT_PARAMETERS_DELIMITER = SystemConstants.COMMA;
    private static final String EVENT_FIRST_CHARACTER = SystemConstants.DOLLAR;
    private static final String EVENT_COMMAND_ACTION_DELIMITER = SystemConstants.COLON;
    private static final String EVENT_MANDATORY_PARAM_OPEN = SystemConstants.CHEVRON_OPEN;
    private static final String EVENT_MANDATORY_PARAM_CLOSE = SystemConstants.CHEVRON_CLOSE;
    private static final String EVENT_OPTIONAL_PARAM_OPEN = SystemConstants.BRACKET_OPEN;
    private static final String EVENT_OPTIONAL_PARAM_CLOSE = SystemConstants.BRACKET_CLOSE;

    private static final String COMMAND_PART_REGEX = "^\\" + EVENT_FIRST_CHARACTER + "([^" + EVENT_COMMAND_ACTION_DELIMITER + "]+)" + EVENT_COMMAND_ACTION_DELIMITER + "([^" +
        EVENT_COMMAND_ACTION_DELIMITER + "]+)$";
    private static final Pattern COMMAND_PART_PATTERN = Pattern.compile(COMMAND_PART_REGEX);

    private String category;
    private String action;
    private List<String> parameters;

    /**
     * Parse event string
     * 
     * @param eventString
     * @throws OpconEventException
     *             if eventString is incorrect
     */
    public OpconEventParser(String eventString) throws OpconEventException {
        Objects.requireNonNull(eventString, "EventString can not be null");

        parseEventString(eventString);
    }

    /**
     * Parse event string
     * 
     * @param eventString
     * @throws OpconEventException
     *             if eventString is incorrect
     */
    private void parseEventString(String eventString) throws OpconEventException {
        List<String> eventStringParts = ExpressionUtil.splitStringWithExpressions(eventString, EVENT_PARAMETERS_DELIMITER.charAt(0));

        if (eventStringParts.size() < 2) {
            parseEventCommandString(eventString);
            parameters = null;
        } else {
            parseEventCommandString(eventStringParts.get(0));
            parameters = eventStringParts.subList(1, eventStringParts.size());
        }
    }

    /**
     * Parse event command string
     * 
     * @param eventCommandString
     * @throws OpconEventException
     *             if eventCommandString is incorrect
     */
    private void parseEventCommandString(String eventCommandString) throws OpconEventException {
        Matcher matcher = COMMAND_PART_PATTERN.matcher(eventCommandString);

        if (!matcher.find() || matcher.groupCount() != 2) {
            throw new OpconEventException(OpconEventException.TYPE_ERROR_INVALID_COMMAND, "Event Command is not correct");
        }

        category = matcher.group(1);
        action = matcher.group(2);
    }

    /**
     * Get the command of Event
     * 
     * @return command
     */
    public String getCommand() {
        return categoryAndActionToCommand(category, action);
    }

    /**
     * Get string parameters of Event
     * 
     * @return collection of Strings
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Get the string representation of category and action
     * 
     * @param category
     * @param action
     * @return string
     */
    public static String categoryAndActionToCommand(String category, String action) {
        if (category == null || action == null || category.isEmpty() || action.isEmpty()) {
            throw new IllegalArgumentException("Category or Action can not be null or empty");
        }
        return EVENT_FIRST_CHARACTER + category + EVENT_COMMAND_ACTION_DELIMITER + action;
    }

    /**
     * Get the event string representation of command and parameters
     * 
     * @param command
     * @param parameters
     * @return string
     */
    public static <T> String commandAndParametersToString(String command, List<T> parameters) {
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Command can not be null or empty");
        }

        StringBuilder eventStringBuilder = new StringBuilder(command);

        if (parameters != null) {
            for (T eventParameter : parameters) {
                eventStringBuilder.append(OpconEventParser.EVENT_PARAMETERS_DELIMITER);
                eventStringBuilder.append(eventParameter.toString());
            }
        }

        return eventStringBuilder.toString();
    }

    /**
     * Get then event string representation of parameter
     * 
     * @param name
     * @param isOptional
     * @return string
     */
    public static String parameterToString(String name, boolean isOptional) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter can not be null or empty");
        }

        if (isOptional) {
            return new StringBuilder(EVENT_OPTIONAL_PARAM_OPEN).append(name).append(EVENT_OPTIONAL_PARAM_CLOSE).toString();
        } else {
            return new StringBuilder(EVENT_MANDATORY_PARAM_OPEN).append(name).append(EVENT_MANDATORY_PARAM_CLOSE).toString();
        }
    }
}
