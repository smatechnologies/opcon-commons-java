package com.smatechnologies.opcon.commons.opcon.event;

public class OpconEventException extends Exception {

    private static final long serialVersionUID = 1L;

    public final static int TYPE_ERROR_CREATE_PARAMETER = 0;
    public final static int TYPE_ERROR_BAD_NUMBER_OF_PARAMETER = 1;
    public final static int TYPE_ERROR_INVALID_COMMAND = 2;
    public final static int TYPE_ERROR_UNREACHABLE_COMMAND = 3;
    public final static int TYPE_ERROR_UNREADABLE_EVENTS_FILE = 4;
    public final static int TYPE_ERROR_INVALID_PARAMETER_STRUCTURE = 5;

    private final int type;

    public OpconEventException(int type) {
        super();
        this.type = type;
    }

    public OpconEventException(int type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public OpconEventException(int type, String message) {
        super(message);
        this.type = type;
    }

    public OpconEventException(int type, Throwable cause) {
        super(cause);
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
