package com.smatechnologies.opcon.commons.serializer;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;


/**
 * @author Pierre PINON
 */
public class DateSerializer implements ISerializer<Date> {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String ZONE_PATTERN = "XXX";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_TIME_PATTERN)
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 7, true)
            .appendPattern(ZONE_PATTERN)
            .toFormatter();

    public String serialize(Date value) throws SerializeException {
        if (value == null) {
            return null;
        }

        try {
            ZonedDateTime zonedDateTime = value.toInstant().atZone(ZoneId.systemDefault());

            return DATE_TIME_FORMATTER.format(zonedDateTime);
        } catch (DateTimeException e) {
            throw new SerializeException(e);
        }
    }
}
