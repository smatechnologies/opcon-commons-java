package com.smatechnologies.opcon.commons.test.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DateDeserializer;
import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateDeserializerTest {

    private static TimeZone defaultTimeZone;

    private DateDeserializer deserializer;

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
        deserializer = new DateDeserializer();
    }

    @Test
    public void test01Null() throws DeserializeException {
        Assert.assertEquals(null, deserializer.deserialize(null));
    }

    @Test
    public void test02Date() throws DeserializeException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Assert.assertEquals(getDate(1985, 10, 26, 21, 0, 0, 0), deserializer.deserialize("1985-11-26T21:00:00.0000000Z"));
        Assert.assertEquals(getDate(2015, 10, 21, 19, 28, 0, 15), deserializer.deserialize("2015-11-21T19:28:00.0150000Z"));
        Assert.assertEquals(getDate(1955, 11, 12, 6, 38, 0, 150), deserializer.deserialize("1955-12-12T06:38:00.1500000Z"));

        TimeZone.setDefault(TimeZone.getTimeZone("CET"));

        Assert.assertEquals(getDate(1985, 10, 26, 21, 0, 0, 0), deserializer.deserialize("1985-11-26T21:00:00.0000000+01:00"));
        Assert.assertEquals(getDate(2015, 10, 21, 19, 28, 0, 15), deserializer.deserialize("2015-11-21T19:28:00.0150000+01:00"));
        Assert.assertEquals(getDate(1955, 11, 12, 6, 38, 0, 150), deserializer.deserialize("1955-12-12T06:38:00.1500000+01:00"));
    }

    private Date getDate(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month, date, hourOfDay, minute, second);
        calendar.set(Calendar.MILLISECOND, millisecond);

        return calendar.getTime();
    }
}
