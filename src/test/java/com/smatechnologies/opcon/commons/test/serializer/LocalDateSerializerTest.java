package com.smatechnologies.opcon.commons.test.serializer;

import com.smatechnologies.opcon.commons.serializer.LocalDateSerializer;
import com.smatechnologies.opcon.commons.serializer.SerializeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.text.ParseException;
import java.time.LocalDate;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalDateSerializerTest {

    private LocalDateSerializer serializer;

    @Before
    public void before() {
        serializer = new LocalDateSerializer();
    }

    @Test
    public void test01Null() throws SerializeException {
        Assert.assertEquals(null, serializer.serialize(null));
    }

    @Test
    public void test02Date() throws ParseException, SerializeException {
        Assert.assertEquals("1985-11-26", serializer.serialize(getLocalDate(1985, 10, 26)));
        Assert.assertEquals("2015-11-21", serializer.serialize(getLocalDate(2015, 10, 21)));
        Assert.assertEquals("1955-12-12", serializer.serialize(getLocalDate(1955, 11, 12)));
    }

    private LocalDate getLocalDate(int year, int month, int date) {
        return LocalDate.of(year, month + 1, date);
    }
}
