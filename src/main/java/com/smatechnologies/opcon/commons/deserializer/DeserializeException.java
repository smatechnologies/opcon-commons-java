package com.smatechnologies.opcon.commons.deserializer;

/**
 * @author Pierre PINON
 */
public class DeserializeException extends Exception {

    private static final long serialVersionUID = 1L;

    public DeserializeException() {
        super();
    }

    public DeserializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializeException(String message) {
        super(message);
    }

    public DeserializeException(Throwable cause) {
        super(cause);
    }
}
