package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.StripUtil;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StripUtilTest {

    @Test
    public void test01stripNull() {
        Assert.assertEquals(null, (String) StripUtil.strip(null));
        Assert.assertEquals(null, (String)StripUtil.stripAndKeepId(null));
        Assert.assertEquals(null, (String)StripUtil.stripAndKeepIdAndName(null));
    }

    @Test
    public void test02stripNothing() {
        TestObject expected = new TestObject("myId", "myName", "myOther");
        TestObject actual = StripUtil.strip(expected);

        Assert.assertEquals(null, actual.getId());
        Assert.assertEquals(null, actual.getName());
        Assert.assertEquals(null, actual.getOther());
    }

    @Test
    public void test03stripAndKeepId() {
        TestObject expected = new TestObject("myId", "myName", "myOther");
        TestObject actual = StripUtil.stripAndKeepId(expected);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(null, actual.getName());
        Assert.assertEquals(null, actual.getOther());
    }

    @Test
    public void test04stripAndKeepIdAndName() {
        TestObject expected = new TestObject("myId", "myName", "myOther");
        TestObject actual = StripUtil.stripAndKeepIdAndName(expected);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(null, actual.getOther());
    }


    @Test
    public void test05stripAndKeepIdAndOther() {
        TestObject expected = new TestObject("myId", "myName", "myOther");
        TestObject actual = StripUtil.strip(expected, "id", "other");

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(null, actual.getName());
        Assert.assertEquals(expected.getOther(), actual.getOther());
    }

    @Test
    public void test06stripAndKeepIdAndNameAndOther() {
        TestObject expected = new TestObject("myId", "myName", "myOther");
        TestObject actual = StripUtil.strip(expected, "id", "name", "other");

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getOther(), actual.getOther());
    }

    @Test
    public void test07stripAndKeepIdAndNameAndOther2() {
        TestObject expected = new TestObject(null, null, null);
        TestObject actual = StripUtil.strip(expected, "id", "name", "other");

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getOther(), actual.getOther());
    }

    @Test
    public void test08stripSet() {
        TestObject testObject1 = new TestObject(null, null, null);
        TestObject testObject2 = new TestObject("id", null, null);
        TestObject testObject3 = new TestObject("id", "name", null);
        TestObject testObject4 = new TestObject("id", "name", "other");

        Set<TestObject> testObjects = new HashSet<>();
        testObjects.add(testObject1);
        testObjects.add(testObject2);
        testObjects.add(testObject3);
        testObjects.add(testObject4);

        TestObject expectedTestObject1 = new TestObject(null, null, null);
        TestObject expectedTestObject2 = new TestObject("id", null, null);
        TestObject expectedTestObject3 = new TestObject("id", "name", null);
        TestObject expectedTestObject4 = new TestObject("id", "name", null);

        Set<TestObject> expected = new HashSet<>();
        expected.add(expectedTestObject1);
        expected.add(expectedTestObject2);
        expected.add(expectedTestObject3);
        expected.add(expectedTestObject4);

        Set<TestObject> actual = StripUtil.stripSetAndKeepIdAndName(testObjects);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test09stripList() {
        TestObject testObject1 = new TestObject(null, null, null);
        TestObject testObject2 = new TestObject("id", null, null);
        TestObject testObject3 = new TestObject("id", "name", null);
        TestObject testObject4 = new TestObject("id", "name", "other");

        TestObject expectedTestObject1 = new TestObject(null, null, null);
        TestObject expectedTestObject2 = new TestObject("id", null, null);
        TestObject expectedTestObject3 = new TestObject("id", "name", null);
        TestObject expectedTestObject4 = new TestObject("id", "name", null);

        List<TestObject> actual = StripUtil.stripListAndKeepIdAndName(Arrays.asList(testObject1, testObject2, testObject3, testObject4));
        List<TestObject> expected = Arrays.asList(expectedTestObject1, expectedTestObject2, expectedTestObject3, expectedTestObject4);

        Assert.assertEquals(expected, actual);
    }

    public static class TestObject {

        private String id;
        private String name;
        private String other;

        public TestObject() {

        }

        public TestObject(String id, String name, String other) {
            this.id = id;
            this.name = name;
            this.other = other;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getOther() {
            return other;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestObject that = (TestObject) o;

            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            return other != null ? other.equals(that.other) : that.other == null;

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (other != null ? other.hashCode() : 0);
            return result;
        }
    }
}
