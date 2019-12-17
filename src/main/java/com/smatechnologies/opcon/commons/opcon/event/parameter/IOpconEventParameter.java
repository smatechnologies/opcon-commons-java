package com.smatechnologies.opcon.commons.opcon.event.parameter;

/**
 * Base of all OpconEventParameter
 * <p>
 * Prefer to use AbstractOpconEventParameter as base of OpconParameter
 * 
 * @author Pierre PINON
 * @param <T>
 */
public interface IOpconEventParameter<T> {

    /**
     * Returns the string representation of parameter
     * 
     * @return string
     */
    String toString();

    /**
     * Sets the string representation of parameter
     * 
     * @param string
     */
    void setString(String string);

    /**
     * Gets the value of parameter
     * 
     * @return value
     * @throws OpconEventParameterException
     */
    T getValue() throws OpconEventParameterException;

    /**
     * Sets the value of parameter
     * 
     * @param value
     */
    void setValue(T value) throws OpconEventParameterException;

    /**
     * Checks if parameter is valid
     *
     * @throws OpconEventParameterException
     */
    void validate() throws OpconEventParameterException;

    /**
     * Checks if string is valid
     * 
     * @param string
     * @throws OpconEventParameterException
     */
    void validateString(String string) throws OpconEventParameterException;

    /**
     * Checks if value is valid
     * 
     * @param value
     * @throws OpconEventParameterException
     */
    void validateValue(T value) throws OpconEventParameterException;

    /*
     * Checks if structure is valid
     *
     * @throws OpconEventParameterException
     */
    void validateStructure() throws OpconEventParameterException;
}
