package com.smatechnologies.opcon.commons.serializer;

import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


/**
 * @author Pierre PINON
 */
public class ZonedDateTimeSerializer implements ISerializer<ZonedDateTime> {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String ZONE_PATTERN = "XXX";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_TIME_PATTERN)
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 7, true)
            .appendPattern(ZONE_PATTERN)
            .toFormatter();

    public String serialize(ZonedDateTime value) throws SerializeException {
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
