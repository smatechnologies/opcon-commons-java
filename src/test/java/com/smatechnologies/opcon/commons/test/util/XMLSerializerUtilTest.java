package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.XMLSerializer;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XMLSerializerUtilTest {

    private final static String SERIALIZED_MY_OBJECT = "<myObject><field>myValue</field></myObject>";

    @XmlRootElement(name = "myObject")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class MyObject implements Serializable {

        @XmlElement(name = "field")
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            MyObject myObject = (MyObject) o;

            return field != null ? field.equals(myObject.field) : myObject.field == null;
        }

        @Override
        public int hashCode() {
            return field != null ? field.hashCode() : 0;
        }
    }

    @Test
    public void test01serialize() throws JAXBException, IOException {
        MyObject myObject = new MyObject();
        myObject.setField("myValue");

        Assert.assertTrue(XMLSerializer.serialize(myObject).contains(SERIALIZED_MY_OBJECT));
    }

    @Test
    public void test02deserialize() throws JAXBException, IOException {
        MyObject myObject = new MyObject();
        myObject.setField("myValue");

        Assert.assertEquals(myObject, XMLSerializer.deserialize(MyObject.class, SERIALIZED_MY_OBJECT));
    }

    @Test
    public void test03copy() throws JAXBException, IOException {
        MyObject myObject = new MyObject();
        myObject.setField("myValue");

        MyObject copy = XMLSerializer.copy(myObject);

        Assert.assertFalse(myObject == copy);
        Assert.assertEquals(myObject, copy);
    }
}
