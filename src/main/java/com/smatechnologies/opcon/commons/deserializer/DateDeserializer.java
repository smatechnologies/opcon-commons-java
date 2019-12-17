package com.smatechnologies.opcon.commons.deserializer;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;


/**
 * @author Pierre PINON
 */
public class DateDeserializer implements IDeserializer<Date> {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String ZONE_PATTERN = "XXX";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_TIME_PATTERN)
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 7, true)
            .appendPattern(ZONE_PATTERN)
            .toFormatter();

    public Date deserialize(String value) throws DeserializeException {
        if (value == null) {
            return null;
        }

        try {
            return Date.from(DATE_TIME_FORMATTER.parse(value, Instant::from));
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new DeserializeException(e);
        }
    }
}
