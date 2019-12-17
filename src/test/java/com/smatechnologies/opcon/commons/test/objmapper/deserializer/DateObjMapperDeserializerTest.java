package com.smatechnologies.opcon.commons.test.objmapper.deserializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.objmapper.deserializer.DateObjMapperDeserializer;
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
public class DateObjMapperDeserializerTest {

    private static TimeZone defaultTimeZone;

    private DateObjMapperDeserializer deserializer;

    @BeforeClass
    public static void beforeClass() {
        defaultTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @AfterClass
    public static void afterClass() {
        TimeZone.setDefault(defaultTimeZone);
    }

    @Before
    public void before() {
        deserializer = new DateObjMapperDeserializer();
    }

    @Test
    public void test01Null() throws ObjMapperException {
        Assert.assertEquals(null, deserializer.deserialize(null, null));
    }

    @Test
    public void test02Date() throws ParseException, ObjMapperException {
        Assert.assertEquals(getDate(1985, 10, 26, 21, 0, 0), deserializer.deserialize(null, "1985-11-26T21:00:00Z"));

        Assert.assertEquals(getDate(2015, 10, 21, 19, 28, 0), deserializer.deserialize(null, "2015-11-21T19:28:00Z"));

        Assert.assertEquals(getDate(1955, 11, 12, 6, 38, 0), deserializer.deserialize(null, "1955-12-12T06:38:00Z"));
    }

    private Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month, date, hourOfDay, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
