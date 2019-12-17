package com.smatechnologies.opcon.commons.test.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import com.smatechnologies.opcon.commons.deserializer.LocalDateDeserializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalDateDeserializerTest {

    private LocalDateDeserializer deserializer;

    @Before
    public void before() {
        deserializer = new LocalDateDeserializer();
    }

    @Test
    public void test01Null() throws DeserializeException {
        Assert.assertEquals(null, deserializer.deserialize(null));
    }

    @Test
    public void test02Date() throws DeserializeException {
        Assert.assertEquals(getLocalDate(1985, 10, 26), deserializer.deserialize("1985-11-26"));
        Assert.assertEquals(getLocalDate(2015, 10, 21), deserializer.deserialize("2015-11-21"));
        Assert.assertEquals(getLocalDate(1955, 11, 12), deserializer.deserialize("1955-12-12"));

        Assert.assertEquals(getLocalDate(1985, 10, 26), deserializer.deserialize("1985-11-26T21:00:00.0000000+01:00"));
        Assert.assertEquals(getLocalDate(2015, 10, 21), deserializer.deserialize("2015-11-21T19:28:00.0150000+01:00"));
        Assert.assertEquals(getLocalDate(1955, 11, 12), deserializer.deserialize("1955-12-12T06:38:00.1500000+01:00"));
    }

    private LocalDate getLocalDate(int year, int month, int date) {
        return LocalDate.of(year, month + 1, date);
    }
}
