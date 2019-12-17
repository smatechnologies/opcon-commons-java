package com.smatechnologies.opcon.commons.test.serializer;

import com.smatechnologies.opcon.commons.serializer.OffsetTimeSerializer;
import com.smatechnologies.opcon.commons.serializer.SerializeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.text.ParseException;
import java.time.OffsetTime;
import java.time.ZoneOffset;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OffsetTimeSerializerTest {

    private OffsetTimeSerializer serializer;

    @Before
    public void before() {
        serializer = new OffsetTimeSerializer();
    }

    @Test
    public void test01Null() throws SerializeException {
        Assert.assertEquals(null, serializer.serialize(null));
    }

    @Test
    public void test02Time() throws ParseException, SerializeException {
        Assert.assertEquals("21:00:00Z", serializer.serialize(getOffsetTime(21, 0, 0, 0, ZoneOffset.UTC)));
        Assert.assertEquals("19:28:00.015Z", serializer.serialize(getOffsetTime(19, 28, 0, 15, ZoneOffset.UTC)));
        Assert.assertEquals("06:38:00.15Z", serializer.serialize(getOffsetTime(6, 38, 0, 150, ZoneOffset.UTC)));

        Assert.assertEquals("21:00:00+01:00", serializer.serialize(getOffsetTime(21, 0, 0, 0, ZoneOffset.ofHours(1))));
        Assert.assertEquals("19:28:00.015+01:00", serializer.serialize(getOffsetTime(19, 28, 0, 15, ZoneOffset.ofHours(1))));
        Assert.assertEquals("06:38:00.15+01:00", serializer.serialize(getOffsetTime(6, 38, 0, 150, ZoneOffset.ofHours(1))));
    }

    private OffsetTime getOffsetTime(int hourOfDay, int minute, int second, int millisecond, ZoneOffset zoneOffset) {
        return OffsetTime.of(hourOfDay, minute, second, millisecond * 1000000, zoneOffset);
    }
}
