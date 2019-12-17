package com.smatechnologies.opcon.commons.serializer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


/**
 * @author Pierre PINON
 */
public class LocalDateSerializer implements ISerializer<LocalDate> {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_TIME_PATTERN)
            .toFormatter();

    public String serialize(LocalDate value) throws SerializeException {
        if (value == null) {
            return null;
        }

        try {
            return DATE_TIME_FORMATTER.format(value);
        } catch (DateTimeException e) {
            throw new SerializeException(e);
        }
    }
}
