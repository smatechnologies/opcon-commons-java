package com.smatechnologies.opcon.commons.test.serializer;

import com.smatechnologies.opcon.commons.serializer.SerializeException;
import com.smatechnologies.opcon.commons.serializer.ZonedDateTimeSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZonedDateTimeSerializerTest {

    private ZonedDateTimeSerializer serializer;

    @Before
    public void before() {
        serializer = new ZonedDateTimeSerializer();
    }

    @Test
    public void test01Null() throws SerializeException {
        Assert.assertEquals(null, serializer.serialize(null));
    }

    @Test
    public void test02Date() throws ParseException, SerializeException {
        Assert.assertEquals("1985-11-26T21:00:00Z", serializer.serialize(getZonedDateTime(1985, 10, 26, 21, 0, 0, 0, ZoneOffset.UTC)));
        Assert.assertEquals("2015-11-21T19:28:00.015Z", serializer.serialize(getZonedDateTime(2015, 10, 21, 19, 28, 0, 15, ZoneOffset.UTC)));
        Assert.assertEquals("1955-12-12T06:38:00.15Z", serializer.serialize(getZonedDateTime(1955, 11, 12, 6, 38, 0, 150, ZoneOffset.UTC)));

        Assert.assertEquals("1985-11-26T21:00:00+01:00", serializer.serialize(getZonedDateTime(1985, 10, 26, 21, 0, 0, 0, ZoneOffset.ofHours(1))));
        Assert.assertEquals("2015-11-21T19:28:00.015+01:00", serializer.serialize(getZonedDateTime(2015, 10, 21, 19, 28, 0, 15, ZoneOffset.ofHours(1))));
        Assert.assertEquals("1955-12-12T06:38:00.15+01:00", serializer.serialize(getZonedDateTime(1955, 11, 12, 6, 38, 0, 150, ZoneOffset.ofHours(1))));
    }

    private ZonedDateTime getZonedDateTime(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond, ZoneId zoneId) {
        return ZonedDateTime.of(year, month + 1, date, hourOfDay, minute, second, millisecond * 1000000, zoneId);
    }
}
