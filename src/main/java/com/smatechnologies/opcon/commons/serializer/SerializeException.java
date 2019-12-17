package com.smatechnologies.opcon.commons.serializer;

/**
 * @author Pierre PINON
 */
public class SerializeException extends Exception {

    private static final long serialVersionUID = 1L;

    public SerializeException() {
        super();
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(Throwable cause) {
        super(cause);
    }
}
