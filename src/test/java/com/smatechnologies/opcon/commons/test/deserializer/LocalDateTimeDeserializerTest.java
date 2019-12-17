package com.smatechnologies.opcon.commons.test.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import com.smatechnologies.opcon.commons.deserializer.LocalDateTimeDeserializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalDateTimeDeserializerTest {

    private LocalDateTimeDeserializer deserializer;

    @Before
    public void before() {
        deserializer = new LocalDateTimeDeserializer();
    }

    @Test
    public void test01Null() throws DeserializeException {
        Assert.assertEquals(null, deserializer.deserialize(null));
    }

    @Test
    public void test02Date() throws DeserializeException {
        Assert.assertEquals(getLocalDateTime(1985, 10, 26, 21, 0, 0, 0), deserializer.deserialize("1985-11-26T21:00:00.0000000Z"));
        Assert.assertEquals(getLocalDateTime(2015, 10, 21, 19, 28, 0, 15), deserializer.deserialize("2015-11-21T19:28:00.0150000Z"));
        Assert.assertEquals(getLocalDateTime(1955, 11, 12, 6, 38, 0, 150), deserializer.deserialize("1955-12-12T06:38:00.1500000Z"));

        Assert.assertEquals(getLocalDateTime(1985, 10, 26, 21, 0, 0, 0), deserializer.deserialize("1985-11-26T21:00:00.0000000+01:00"));
        Assert.assertEquals(getLocalDateTime(2015, 10, 21, 19, 28, 0, 15), deserializer.deserialize("2015-11-21T19:28:00.0150000+01:00"));
        Assert.assertEquals(getLocalDateTime(1955, 11, 12, 6, 38, 0, 150), deserializer.deserialize("1955-12-12T06:38:00.1500000+01:00"));
    }

    private LocalDateTime getLocalDateTime(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond) {
        return LocalDateTime.of(year, month + 1, date, hourOfDay, minute, second, millisecond * 1000000);
    }
}
