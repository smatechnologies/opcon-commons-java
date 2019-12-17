package com.smatechnologies.opcon.commons.test.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.objmapper.serializer.LocalDateObjMapperSerializer;
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
public class LocalDateObjMapperSerializerTest {

    private LocalDateObjMapperSerializer serializer;

    @Before
    public void before() {
        serializer = new LocalDateObjMapperSerializer();
    }

    @Test
    public void test01Null() throws ObjMapperException {
        Assert.assertEquals(null, serializer.serialize(null, null));
    }

    @Test
    public void test02Date() throws ParseException, ObjMapperException {
        Assert.assertEquals("1985-11-26", serializer.serialize(null, getLocalDate(1985, 10, 26)));

        Assert.assertEquals("2015-11-21", serializer.serialize(null, getLocalDate(2015, 10, 21)));

        Assert.assertEquals("1955-12-12", serializer.serialize(null, getLocalDate(1955, 11, 12)));
    }

    private LocalDate getLocalDate(int year, int month, int date) {
        return LocalDate.of(year, month + 1, date);
    }
}
