package com.smatechnologies.opcon.commons.test.objmapper;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.objmapper.UrlParameterMapper;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperCollectionField;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperCreator;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperField;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperValue;
import com.smatechnologies.opcon.commons.objmapper.deserializer.CollectionObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.deserializer.IObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.modifier.DosWildcardObjMapperModifier;
import com.smatechnologies.opcon.commons.objmapper.serializer.CollectionObjMapperSerializer;
import com.smatechnologies.opcon.commons.objmapper.serializer.IObjMapperSerializer;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlParameterMapperTest {

    @Test
    public void test01ParametersPart() {
        Assert.assertNull(UrlParameterMapper.getUrlParameters(null, false));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("", false));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("?", false));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("#", false));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("?#", false));
        Assert.assertEquals("param1=value1&param2=value2", UrlParameterMapper.getUrlParameters("http://example?param1=value1&param2=value2", false));
        Assert.assertEquals("param1=value1&param2=value2", UrlParameterMapper.getUrlParameters("http://example?param1=value1&param2=value2#param3=value3&param4=value4", false));
        Assert.assertEquals("param1=value1&param2=value?", UrlParameterMapper.getUrlParameters("http://example?param1=value1&param2=value?", false));
        Assert.assertEquals("param1=value1&param2=value2", UrlParameterMapper.getUrlParameters("param1=value1&param2=value2", false));
        Assert.assertEquals("param1=value1&param2=value2", UrlParameterMapper.getUrlParameters("param1=value1&param2=value2#param3=value3&param4=value4", false));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("param1=value1&param2=value?", false));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("param1=value1&param2=value?#param3=value3&param4=value4", false));

        Assert.assertNull(UrlParameterMapper.getUrlParameters(null, true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("", true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("?", true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("#", true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("?#", true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("http://example?param1=value1&param2=value2", true));
        Assert.assertEquals("param3=value3&param4=value4", UrlParameterMapper.getUrlParameters("http://example?param1=value1&param2=value2#param3=value3&param4=value4", true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("http://example?param1=value1&param2=value?", true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("param1=value1&param2=value2", true));
        Assert.assertEquals("param3=value3&param4=value4", UrlParameterMapper.getUrlParameters("param1=value1&param2=value2#param3=value3&param4=value4", true));
        Assert.assertEquals("", UrlParameterMapper.getUrlParameters("param1=value1&param2=value?", true));
        Assert.assertEquals("param3=value3&param4=value4", UrlParameterMapper.getUrlParameters("param1=value1&param2=value?#param3=value3&param4=value4", true));
    }

    @Test
    public void test02InlineParametersToMap() {
        Assert.assertNull(UrlParameterMapper.urlParametersToMap(null, true));
        Assert.assertEquals(toMap(), UrlParameterMapper.urlParametersToMap("", true));
        assertMapEquals(toMap("aaa", null), UrlParameterMapper.urlParametersToMap("aaa", true));
        assertMapEquals(toMap("aaa", null, "aaa", null), UrlParameterMapper.urlParametersToMap("aaa&aaa", true));
        assertMapEquals(toMap("aaa", null, "aaa", "0"), UrlParameterMapper.urlParametersToMap("aaa&aaa=0", true));
        assertMapEquals(toMap("aaa", ""), UrlParameterMapper.urlParametersToMap("aaa=", true));
        assertMapEquals(toMap("aaa", "0"), UrlParameterMapper.urlParametersToMap("aaa=0", true));
        assertMapEquals(toMap("aaa", "0", "aaa", "1"), UrlParameterMapper.urlParametersToMap("aaa=0&aaa=1", true));
        assertMapEquals(toMap("aaa", null, "bbb", null), UrlParameterMapper.urlParametersToMap("aaa&bbb", true));
        assertMapEquals(toMap("aaa", "0", "bbb", null), UrlParameterMapper.urlParametersToMap("aaa=0&bbb", true));
        assertMapEquals(toMap("aaa", null, "bbb", "1"), UrlParameterMapper.urlParametersToMap("aaa&bbb=1", true));
        assertMapEquals(toMap("aaa", "0", "bbb", "1"), UrlParameterMapper.urlParametersToMap("aaa=0&bbb=1", true));
        assertMapEquals(toMap("aaa&=", "0&=", "bbb", "1"), UrlParameterMapper.urlParametersToMap("aaa%26%3D=0%26%3D&bbb=1", true));
        assertMapEquals(toMap("aaa%26%3D", "0%26%3D", "bbb", "1"), UrlParameterMapper.urlParametersToMap("aaa%26%3D=0%26%3D&bbb=1", false));
    }

    @Test
    public void test03MapToInlineParameters() {
        Assert.assertNull(UrlParameterMapper.mapToUrlParameters(null, true));
        Assert.assertEquals("", UrlParameterMapper.mapToUrlParameters(toMap(), true));
        Assert.assertEquals("aaa", UrlParameterMapper.mapToUrlParameters(toMap("aaa", null), true));
        Assert.assertEquals("aaa&aaa", UrlParameterMapper.mapToUrlParameters(toMap("aaa", null, "aaa", null), true));
        Assert.assertEquals("aaa&aaa=0", UrlParameterMapper.mapToUrlParameters(toMap("aaa", null, "aaa", "0"), true));
        Assert.assertEquals("aaa=", UrlParameterMapper.mapToUrlParameters(toMap("aaa", ""), true));
        Assert.assertEquals("aaa=0", UrlParameterMapper.mapToUrlParameters(toMap("aaa", "0"), true));
        Assert.assertEquals("aaa=0&aaa=1", UrlParameterMapper.mapToUrlParameters(toMap("aaa", "0", "aaa", "1"), true));
        Assert.assertEquals("aaa&bbb", UrlParameterMapper.mapToUrlParameters(toMap("aaa", null, "bbb", null), true));
        Assert.assertEquals("aaa=0&bbb", UrlParameterMapper.mapToUrlParameters(toMap("aaa", "0", "bbb", null), true));
        Assert.assertEquals("aaa&bbb=1", UrlParameterMapper.mapToUrlParameters(toMap("aaa", null, "bbb", "1"), true));
        Assert.assertEquals("aaa=0&bbb=1", UrlParameterMapper.mapToUrlParameters(toMap("aaa", "0", "bbb", "1"), true));
        Assert.assertEquals("aaa%26%3D=0%26%3D&bbb=1", UrlParameterMapper.mapToUrlParameters(toMap("aaa&=", "0&=", "bbb", "1"), true));
        Assert.assertEquals("aaa&==0&=&bbb=1", UrlParameterMapper.mapToUrlParameters(toMap("aaa&=", "0&=", "bbb", "1"), false));
    }

    @Test
    public void test04Serialize() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = new TestObject();
        testObject.setString("aaa&=");
        testObject.setCustomVariable("customValue");
        testObject.setInteger(42);
        testObject.setPrimInt(123);
        testObject.setBoolean(true);
        testObject.setPrimBoolean(false);
        testObject.setCustomObject2(new CustomObject("customObjectValue2"));
        testObject.setTestEnum(TestEnum.FIRST);

        Assert.assertEquals("string=aaa%26%3D&customName=customValue&integer=42&primInt=123&bool=true&primBoolean=false&customObject2=*customObjectValue2*&testEnum=1", urlParameterMapper.serialize(testObject));
    }

    @Test
    public void test05Deserialize() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = urlParameterMapper.deserialize(TestObject.class, "string=aaa%26%3D&customName=customValue&integer=42&primInt=123&bool=true&primBoolean=false&testEnum=1");

        Assert.assertEquals("aaa&=", testObject.getString());
        Assert.assertEquals("customValue", testObject.getCustomVariable());
        Assert.assertEquals(Integer.valueOf(42), testObject.getInteger());
        Assert.assertEquals(123, testObject.getPrimInt());
        Assert.assertEquals(true, testObject.getBoolean());
        Assert.assertEquals(false, testObject.getPrimBoolean());
        Assert.assertEquals(TestEnum.FIRST, testObject.getTestEnum());
    }

    @Test
    public void test06Deserialize2() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = urlParameterMapper.deserialize(TestObject.class, "string=aaa%26%3D&customName=customValue&customName=customValue2&integer=42&primInt=123&bool=true&primBoolean=false&testEnum=1");

        Assert.assertEquals("aaa&=", testObject.getString());
        Assert.assertEquals("customValue", testObject.getCustomVariable());
        Assert.assertEquals(Integer.valueOf(42), testObject.getInteger());
        Assert.assertEquals(123, testObject.getPrimInt());
        Assert.assertEquals(true, testObject.getBoolean());
        Assert.assertEquals(false, testObject.getPrimBoolean());
        Assert.assertEquals(TestEnum.FIRST, testObject.getTestEnum());
    }

    @Test
    public void test07SerializeCustomWithoutSerializer() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = new TestObject();
        testObject.setCustomObject(new CustomObject("customObjectValue"));

        Assert.assertEquals("primInt=0&primBoolean=false&customObject=_customObjectValue_", urlParameterMapper.serialize(testObject));
    }

    @Test
    public void test08SerializeCustomWithSerializerByAnnotation() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = new TestObject();
        testObject.setCustomObject2(new CustomObject("customObjectValue2"));

        Assert.assertEquals("primInt=0&primBoolean=false&customObject2=*customObjectValue2*", urlParameterMapper.serialize(testObject));
    }

    @Test
    public void test09SerializeCustomWithSerializerRegistered() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();
        urlParameterMapper.getObjMapper().addSerializer(CustomObject.class, CustomObjectSerializer.class);

        TestObject testObject = new TestObject();
        testObject.setCustomObject(new CustomObject("customObjectValue"));

        Assert.assertEquals("primInt=0&primBoolean=false&customObject=*customObjectValue*", urlParameterMapper.serialize(testObject));
    }

    @Test(expected = ObjMapperException.class)
    public void test10DeserializeCustomWithoutDeserializer() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        urlParameterMapper.deserialize(TestObject.class, "customObject=_customObjectValue_");
    }

    @Test
    public void test11DeserializeCustomWithDeserializerByAnnotation() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = urlParameterMapper.deserialize(TestObject.class, "customObject2=*customObjectValue2*");

        Assert.assertEquals(new CustomObject("customObjectValue2"), testObject.getCustomObject2());
    }

    @Test
    public void test12DeserializeCustomWithDeserializerRegistered() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();
        urlParameterMapper.getObjMapper().addDeserializer(CustomObject.class, CustomObjectDeserializer.class);

        TestObject testObject = urlParameterMapper.deserialize(TestObject.class, "customObject=*customObjectValue*");

        Assert.assertEquals(new CustomObject("customObjectValue"), testObject.getCustomObject());
    }

    @Test
    public void test13DosWildcardSerializeModifier() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = new TestObject();
        testObject.setWildcard("ab?d*g");

        Assert.assertEquals("wildcard=*ab%3Fd*g*&primInt=0&primBoolean=false", urlParameterMapper.serialize(testObject));
    }

    @Test
    public void test14DosWildcardDeserializeModifier() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        TestObject testObject = urlParameterMapper.deserialize(TestObject.class, "wildcard=ab%3Fd*g");

        Assert.assertEquals("*ab?d*g*", testObject.getWildcard());
    }

    @Test
    public void test15CollectionSerializer() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();
        urlParameterMapper.getObjMapper().addSerializer(Collection.class, CollectionObjMapperSerializer.class);

        TestObject testObject = new TestObject();
        testObject.setCollection(Arrays.asList("one", "two", "", null, 5));
        testObject.setCollectionCustomObject(Arrays.asList(new CustomObject("one"), new CustomObject("two")));
        testObject.setCollectionCustomObject2(Arrays.asList(new CustomObject("three"), new CustomObject("four")));

        Assert.assertEquals("primInt=0&primBoolean=false&collection=one%2Ctwo%2C%2C%2C5&collectionCustomObject=_one_%2C_two_&collectionCustomObject2=*three*%2C*four*", urlParameterMapper.serialize(testObject));
    }

    @Test
    public void test16CollectionSerializer() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();
        urlParameterMapper.getObjMapper().addSerializer(Collection.class, CollectionObjMapperSerializer.class);
        urlParameterMapper.getObjMapper().addSerializer(CustomObject.class, CustomObjectSerializer.class);

        TestObject testObject = new TestObject();
        testObject.setCollection(Arrays.asList("one", "two"));
        testObject.setCollectionCustomObject(Arrays.asList(new CustomObject("one"), new CustomObject("two")));
        testObject.setCollectionCustomObject2(Arrays.asList(new CustomObject("three"), new CustomObject("four")));

        Assert.assertEquals("primInt=0&primBoolean=false&collection=one%2Ctwo&collectionCustomObject=*one*%2C*two*&collectionCustomObject2=*three*%2C*four*", urlParameterMapper.serialize(testObject));
    }

    @Test
    public void test17CollectionDeserializer() throws ObjMapperException {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();
        urlParameterMapper.getObjMapper().addDeserializer(List.class, CollectionObjMapperDeserializer.class);
        urlParameterMapper.getObjMapper().addDeserializer(CustomObject.class, CustomObjectDeserializer.class);

        TestObject testObject = urlParameterMapper.deserialize(TestObject.class, "collection=one%2Ctwo%2C%2C5&collectionCustomObject2=*three*%2C*four*");

        Assert.assertEquals(Arrays.asList("one", "two", "", "5"), testObject.getCollection());
        Assert.assertEquals(Arrays.asList(new CustomObject("three"), new CustomObject("four")), testObject.getCollectionCustomObject2());
    }

    public static class CustomObject {

        private final String customObjectVariable;

        public CustomObject(String customObjectValue) {
            this.customObjectVariable = customObjectValue;
        }

        public String getCustomObjectVariable() {
            return customObjectVariable;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            CustomObject that = (CustomObject) o;
            return Objects.equals(customObjectVariable, that.customObjectVariable);
        }

        @Override
        public int hashCode() {

            return Objects.hash(customObjectVariable);
        }

        @Override
        public String toString() {
            return "_" + customObjectVariable + "_";
        }
    }

    public static class TestObject {

        @ObjMapperField
        private String string;
        @ObjMapperField("customName")
        private String customVariable;
        @ObjMapperField(serializeModifier = DosWildcardObjMapperModifier.class, deserializeModifier = DosWildcardObjMapperModifier.class)
        private String wildcard;
        @ObjMapperField
        private Integer integer;
        @ObjMapperField
        private int primInt;
        @ObjMapperField
        private Boolean bool;
        @ObjMapperField
        private boolean primBoolean;
        @ObjMapperField
        private CustomObject customObject;
        @ObjMapperField(serializer = CustomObjectSerializer.class, deserializer = CustomObjectDeserializer.class)
        private CustomObject customObject2;
        @ObjMapperField
        private TestEnum testEnum;
        @ObjMapperField
        private List<Object> collection;
        @ObjMapperField
        private List<CustomObject> collectionCustomObject;
        @ObjMapperField
        @ObjMapperCollectionField(serializer = CustomObjectSerializer.class, deserializer = CustomObjectDeserializer.class)
        private List<CustomObject> collectionCustomObject2;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getCustomVariable() {
            return customVariable;
        }

        public void setCustomVariable(String customVariable) {
            this.customVariable = customVariable;
        }

        public String getWildcard() {
            return wildcard;
        }

        public void setWildcard(String wildcard) {
            this.wildcard = wildcard;
        }

        public Integer getInteger() {
            return integer;
        }

        public void setInteger(Integer integer) {
            this.integer = integer;
        }

        public int getPrimInt() {
            return primInt;
        }

        public void setPrimInt(int primInt) {
            this.primInt = primInt;
        }

        public Boolean getBoolean() {
            return bool;
        }

        public void setBoolean(Boolean bool) {
            this.bool = bool;
        }

        public boolean getPrimBoolean() {
            return primBoolean;
        }

        public void setPrimBoolean(boolean primBoolean) {
            this.primBoolean = primBoolean;
        }

        public CustomObject getCustomObject() {
            return customObject;
        }

        public void setCustomObject(CustomObject customObject) {
            this.customObject = customObject;
        }

        public CustomObject getCustomObject2() {
            return customObject2;
        }

        public void setCustomObject2(CustomObject customObject2) {
            this.customObject2 = customObject2;
        }

        public TestEnum getTestEnum() {
            return testEnum;
        }

        public void setTestEnum(TestEnum testEnum) {
            this.testEnum = testEnum;
        }

        public List<Object> getCollection() {
            return collection;
        }

        public void setCollection(List<Object> collection) {
            this.collection = collection;
        }

        public List<CustomObject> getCollectionCustomObject() {
            return collectionCustomObject;
        }

        public void setCollectionCustomObject(List<CustomObject> collectionCustomObject) {
            this.collectionCustomObject = collectionCustomObject;
        }

        public List<CustomObject> getCollectionCustomObject2() {
            return collectionCustomObject2;
        }

        public void setCollectionCustomObject2(List<CustomObject> collectionCustomObject2) {
            this.collectionCustomObject2 = collectionCustomObject2;
        }
    }

    public enum TestEnum {
        FIRST("1"),
        SECOND("2");

        private final String value;

        TestEnum(String value) {
            this.value = value;
        }

        @ObjMapperValue
        private String getValue() {
            return value;
        }

        @ObjMapperCreator
        private static TestEnum fromValue(String value) {
            return Arrays.stream(values()).filter(testEnum -> Objects.equals(testEnum.getValue(), value))
                    .findFirst()
                    .orElse(null);
        }
    }

    public static class CustomObjectSerializer implements IObjMapperSerializer<CustomObject> {

        @Override
        public String serialize(ObjMapper.ObjMapperContext objMapperContext, CustomObject value) throws ObjMapperException {
            return "*" + value.getCustomObjectVariable() + "*";
        }
    }

    public static class CustomObjectDeserializer implements IObjMapperDeserializer<CustomObject> {

        @Override
        public CustomObject deserialize(ObjMapper.ObjMapperContext objMapperContext, String value) throws ObjMapperException {
            return new CustomObject(value.substring(1, value.length() - 1));
        }
    }

    private Map<String, String[]> toMap(String... strings) {
        if (strings.length % 2 != 0) {
            throw new IllegalArgumentException("Should be even");
        }

        Map<String, String[]> map = new LinkedHashMap<>();

        for (int i = 0; i < strings.length; i += 2) {
            String key = strings[i];
            String value = strings[i + 1];

            String[] values = map.get(key);
            if (values == null) {
                map.put(key, new String[] { value });
            } else {
                String[] newValues = new String[values.length + 1];
                System.arraycopy(values, 0, newValues, 0, values.length);
                newValues[newValues.length - 1] = value;

                map.put(key, newValues);
            }
        }

        return map;
    }

    private void assertMapEquals(Map<String, String[]> expected, Map<String, String[]> actual) {
        if (expected != null || actual != null) {
            if (expected == null || actual == null || expected.size() != actual.size()) {
                Assert.fail("Not Equal because map key size is different");
            } else {
                Iterator<Map.Entry<String, String[]>> expectedIterator = expected.entrySet().iterator();
                Iterator<Map.Entry<String, String[]>> actualIterator = actual.entrySet().iterator();

                while (expectedIterator.hasNext() || actualIterator.hasNext()) {
                    Map.Entry<String, String[]> expectedEntry = expectedIterator.next();
                    Map.Entry<String, String[]> actualEntry = actualIterator.next();

                    Assert.assertEquals(expectedEntry.getKey(), actualEntry.getKey());
                    Assert.assertArrayEquals(expectedEntry.getValue(), actualEntry.getValue());
                }
            }
        }
    }
}
