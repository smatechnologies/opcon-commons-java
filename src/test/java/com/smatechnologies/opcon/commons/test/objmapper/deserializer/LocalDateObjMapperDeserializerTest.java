package com.smatechnologies.opcon.commons.test.objmapper.deserializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.objmapper.deserializer.LocalDateObjMapperDeserializer;
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
public class LocalDateObjMapperDeserializerTest {

    private LocalDateObjMapperDeserializer deserializer;

    @Before
    public void before() {
        deserializer = new LocalDateObjMapperDeserializer();
    }

    @Test
    public void test01Null() throws ObjMapperException {
        Assert.assertEquals(null, deserializer.deserialize(null, null));
    }

    @Test
    public void test02Date() throws ParseException, ObjMapperException {
        Assert.assertEquals(getLocalDate(1985, 10, 26), deserializer.deserialize(null, "1985-11-26"));

        Assert.assertEquals(getLocalDate(2015, 10, 21), deserializer.deserialize(null, "2015-11-21"));

        Assert.assertEquals(getLocalDate(1955, 11, 12), deserializer.deserialize(null, "1955-12-12"));
    }

    private LocalDate getLocalDate(int year, int month, int date) {
        return LocalDate.of(year, month + 1, date);
    }
}
