package com.smatechnologies.opcon.commons.deserializer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


/**
 * @author Pierre PINON
 */
public class LocalDateDeserializer implements IDeserializer<LocalDate> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "'T'HH:mm:ss";
    private static final String ZONE_PATTERN = "XXX";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_PATTERN)
            .optionalStart()
            .appendPattern(TIME_PATTERN)
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 7, true)
            .appendPattern(ZONE_PATTERN)
            .optionalEnd()
            .toFormatter();

    public LocalDate deserialize(String value) throws DeserializeException {
        if (value == null) {
            return null;
        }

        try {
            return LocalDate.parse(value, DATE_TIME_FORMATTER);
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new DeserializeException(e);
        }
    }
}
