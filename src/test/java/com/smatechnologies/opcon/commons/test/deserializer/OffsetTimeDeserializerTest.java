package com.smatechnologies.opcon.commons.test.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import com.smatechnologies.opcon.commons.deserializer.OffsetTimeDeserializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.OffsetTime;
import java.time.ZoneOffset;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OffsetTimeDeserializerTest {

    private OffsetTimeDeserializer deserializer;

    @Before
    public void before() {
        deserializer = new OffsetTimeDeserializer();
    }

    @Test
    public void test01Null() throws DeserializeException {
        Assert.assertEquals(null, deserializer.deserialize(null));
    }

    @Test
    public void test02Time() throws DeserializeException {
        Assert.assertEquals(getOffsetTime(21, 0, 0, 0, ZoneOffset.UTC), deserializer.deserialize("21:00:00.0000000Z"));
        Assert.assertEquals(getOffsetTime(19, 28, 0, 15, ZoneOffset.UTC), deserializer.deserialize("19:28:00.0150000Z"));
        Assert.assertEquals(getOffsetTime(6, 38, 0, 150, ZoneOffset.UTC), deserializer.deserialize("06:38:00.1500000Z"));
        Assert.assertEquals(getOffsetTime(21, 0, 0, 0, ZoneOffset.ofHours(1)), deserializer.deserialize("21:00:00.0000000+01:00"));
        Assert.assertEquals(getOffsetTime(19, 28, 0, 15, ZoneOffset.ofHours(1)), deserializer.deserialize("19:28:00.0150000+01:00"));
        Assert.assertEquals(getOffsetTime(6, 38, 0, 150, ZoneOffset.ofHours(1)), deserializer.deserialize("06:38:00.1500000+01:00"));
    }

    private OffsetTime getOffsetTime(int hourOfDay, int minute, int second, int millisecond, ZoneOffset zoneOffset) {
        return OffsetTime.of(hourOfDay, minute, second, millisecond * 1000000, zoneOffset);
    }
}
