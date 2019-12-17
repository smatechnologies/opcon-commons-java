package com.smatechnologies.opcon.commons.test.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.objmapper.serializer.DateObjMapperSerializer;
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
public class DateObjMapperSerializerTest {

    private static TimeZone defaultTimeZone;

    private DateObjMapperSerializer serializer;

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
        serializer = new DateObjMapperSerializer();
    }

    @Test
    public void test01Null() throws ObjMapperException {
        Assert.assertEquals(null, serializer.serialize(null, null));
    }

    @Test
    public void test02Date() throws ParseException, ObjMapperException {
        Assert.assertEquals("1985-11-26T21:00:00Z", serializer.serialize(null, getDate(1985, 10, 26, 21, 0, 0)));

        Assert.assertEquals("2015-11-21T19:28:00Z", serializer.serialize(null, getDate(2015, 10, 21, 19, 28, 0)));

        Assert.assertEquals("1955-12-12T06:38:00Z", serializer.serialize(null, getDate(1955, 11, 12, 6, 38, 0)));
    }

    private Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month, date, hourOfDay, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
