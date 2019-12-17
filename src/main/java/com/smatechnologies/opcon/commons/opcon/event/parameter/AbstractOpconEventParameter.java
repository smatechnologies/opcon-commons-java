package com.smatechnologies.opcon.commons.opcon.event.parameter;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventParser;
import com.smatechnologies.opcon.commons.opcon.util.ExpressionUtil;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Stream;


/**
 * Convenience abstract that facilities implementation of a OpconEventParameter
 * <p>
 * To create a new type, we only needs to inherit of this class and to define "valueToString(value)" and "stringToValue(string)" methods
 * 
 * @author Pierre PINON
 * @param <T>
 */
abstract public class AbstractOpconEventParameter<T> implements IOpconEventParameter<T> {

    private static final String[] INVALID_CHARS = {
        OpconEventParser.EVENT_PARAMETERS_DELIMITER,
        Character.toString(ExpressionUtil.EVALUATION_EXPRESSION_OPEN) + Character.toString(ExpressionUtil.EVALUATION_EXPRESSION_OPEN),
        Character.toString(ExpressionUtil.EVALUATION_EXPRESSION_CLOSE) + Character.toString(ExpressionUtil.EVALUATION_EXPRESSION_CLOSE)
    };

    private String string = "";

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#toString()
     */
    @Override
    public final String toString() {
        return string;
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#setString(java.lang.String)
     */
    @Override
    public final void setString(String string) {
        this.string = (string == null ? "" : string);
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#getValue()
     */
    @Override
    public final T getValue() throws OpconEventParameterException {
        return stringToValue(toString());
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#setValue(java.lang.Object)
     */
    @Override
    public final void setValue(T value) throws OpconEventParameterException {
        setString(valueToString(value));
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#validate()
     */
    @Override
    public final void validate() throws OpconEventParameterException {
        validateString(toString());
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#validateString(java.lang.String)
     */
    @Override
    public void validateString(String string) throws OpconEventParameterException {
        validateSymmetricConversion(string);
        validateChars(string);
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#validateValue(java.lang.Object)
     *
     * Override validateString instead of this method because validateString it call by this method
     */
    @Override
    public final void validateValue(T value) throws OpconEventParameterException {
        validateString(valueToString(value));
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter#validateStructure()
     */
    @Override
    public final void validateStructure() throws OpconEventParameterException {
        String string = toString();

        validateSymmetricConversion(string);
        validateChars(string);
    }

    protected void validateSymmetricConversion(String string) throws OpconEventParameterException {
        //Checks if conversion is possible in both way and if values are equals
        if (! valueToString(stringToValue(string)).equals(string)) {
            throw new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_ASYMMETRIC_CONVERSION);
        }
    }

    protected final void validateChars(String string, String... additionalInvalidChars) throws OpconEventParameterException {
        String[] invalidChars = INVALID_CHARS;
        if (additionalInvalidChars != null && additionalInvalidChars.length > 0) {
            invalidChars = Stream.concat(Arrays.stream(invalidChars), Arrays.stream(additionalInvalidChars)).toArray(String[]::new);
        }

        String stringWithoutExpressions = ExpressionUtil.removeExpressions(string);

        StringJoiner stringJoiner = new StringJoiner("|", "(", ")");
        for (String invalidChar : invalidChars) {
            stringJoiner.add(Pattern.quote(invalidChar));
        }
        String invalidCharsPattern = stringJoiner.toString();

        if (stringWithoutExpressions.matches(".*" + invalidCharsPattern + ".*")) {
            OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_INVALID_CHAR);
            e.setRuleReference(String.join(" ", invalidChars));
            e.setReference(string);
            throw e;
        }
    }

    /**
     * Method to define that allows to convert a string to a value
     * 
     * @param string
     * @return value
     * @throws OpconEventParameterException
     */
    abstract protected T stringToValue(String string) throws OpconEventParameterException;

    /**
     * Method to define that allows to convert a value to a string
     * 
     * @param value
     * @return string
     */
    abstract protected String valueToString(T value) throws OpconEventParameterException;
}
