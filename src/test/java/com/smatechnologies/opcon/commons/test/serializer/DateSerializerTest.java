package com.smatechnologies.opcon.commons.test.serializer;

import com.smatechnologies.opcon.commons.serializer.DateSerializer;
import com.smatechnologies.opcon.commons.serializer.SerializeException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateSerializerTest {

    private static TimeZone defaultTimeZone;

    private DateSerializer serializer;

    @BeforeClass
    public static void beforeClass() {
        defaultTimeZone = TimeZone.getDefault();
    }

    @AfterClass
    public static void afterClass() {
        TimeZone.setDefault(defaultTimeZone);
    }

    @Before
    public void before() {
        serializer = new DateSerializer();
    }

    @Test
    public void test01Null() throws SerializeException {
        Assert.assertEquals(null, serializer.serialize(null));
    }

    @Test
    public void test02Date() throws ParseException, SerializeException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Assert.assertEquals("1985-11-26T21:00:00Z", serializer.serialize(getDate(1985, 10, 26, 21, 0, 0, 0)));
        Assert.assertEquals("2015-11-21T19:28:00.015Z", serializer.serialize(getDate(2015, 10, 21, 19, 28, 0, 15)));
        Assert.assertEquals("1955-12-12T06:38:00.15Z", serializer.serialize(getDate(1955, 11, 12, 6, 38, 0, 150)));

        TimeZone.setDefault(TimeZone.getTimeZone("CET"));

        Assert.assertEquals("1985-11-26T21:00:00+01:00", serializer.serialize(getDate(1985, 10, 26, 21, 0, 0, 0)));
        Assert.assertEquals("2015-11-21T19:28:00.015+01:00", serializer.serialize(getDate(2015, 10, 21, 19, 28, 0, 15)));
        Assert.assertEquals("1955-12-12T06:38:00.15+01:00", serializer.serialize(getDate(1955, 11, 12, 6, 38, 0, 150)));
    }

    private Date getDate(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month, date, hourOfDay, minute, second);
        calendar.set(Calendar.MILLISECOND, millisecond);

        return calendar.getTime();
    }
}
