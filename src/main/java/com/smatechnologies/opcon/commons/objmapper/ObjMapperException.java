package com.smatechnologies.opcon.commons.objmapper;

/**
 * @author Pierre PINON
 */
public class ObjMapperException extends Exception {

    private static final long serialVersionUID = 1L;

    public ObjMapperException() {
        super();
    }

    public ObjMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjMapperException(String message) {
        super(message);
    }

    public ObjMapperException(Throwable cause) {
        super(cause);
    }
}
