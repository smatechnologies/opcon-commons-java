package com.smatechnologies.opcon.commons.deserializer;

import java.time.DateTimeException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


/**
 * @author Pierre PINON
 */
public class OffsetTimeDeserializer implements IDeserializer<OffsetTime> {

    private static final String DATE_TIME_PATTERN = "HH:mm:ss";
    private static final String ZONE_PATTERN = "XXX";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_TIME_PATTERN)
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 7, true)
            .appendPattern(ZONE_PATTERN)
            .toFormatter();

    public OffsetTime deserialize(String value) throws DeserializeException {
        if (value == null) {
            return null;
        }

        try {
            return OffsetTime.parse(value, DATE_TIME_FORMATTER);
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new DeserializeException(e);
        }
    }
}
