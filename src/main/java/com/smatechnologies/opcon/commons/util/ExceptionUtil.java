package com.smatechnologies.opcon.commons.util;

/**
 * @author Pierre PINON
 */
public class ExceptionUtil {
    /**
     * Return the throwable which caused the exception.<br/>
     * If the exception has no cause, return the passed throwable.
     *
     * @param exception
     *            the exception which caused the error.
     * @return a throwable
     */
    public static Throwable getSourceCause(Throwable exception) {
        if (exception == null) {
            return null;
        }

        Throwable last = exception;

        while (last.getCause() != null) {
            last = last.getCause();
        }

        return last;
    }

    /**
     * Return the Localized message which cause the exception.<br/>
     * If the exception has no cause, return the exception's message.
     *
     * @param exception
     *            the exception which caused the error.
     * @return a localized message.
     */
    public static String getCauseError(Throwable exception) {
        Throwable cause = getSourceCause(exception);

        if (cause != null) {
            String message = cause.getMessage();
            // if there's no message print the exception's name
            if (message == null) {
                return cause.getClass().getSimpleName();
            }
            return message;
        }

        return "";
    }
}
