package com.smatechnologies.opcon.commons.test.opcon.event;

import com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterException;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterList;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterMap;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterText;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpconEventParameterTest {

    @Test
    public void test01Text() throws OpconEventParameterException {
        OpconEventParameterText eventParameter = new OpconEventParameterText();

        Assert.assertEquals("", eventParameter.toString());
        Assert.assertEquals("", eventParameter.getValue());

        eventParameter.setString("test");
        Assert.assertEquals("test", eventParameter.toString());
        Assert.assertEquals("test", eventParameter.getValue());

        eventParameter.setString(null);
        Assert.assertEquals("", eventParameter.toString());
        Assert.assertEquals("", eventParameter.getValue());
    }

    @Test
    public void test02TextIsValid() throws OpconEventParameterException {
        OpconEventParameterText eventParameter = new OpconEventParameterText();

        assertOpconEventParameterValue(eventParameter, "value", true);
        assertOpconEventParameterValue(eventParameter, "", true);
        assertOpconEventParameterValue(eventParameter, "val[[,[] ]]ue", true);
        assertOpconEventParameterValue(eventParameter, "val[ue", true);
        assertOpconEventParameterValue(eventParameter, "val]ue", true);

        assertOpconEventParameterValue(eventParameter, "val,ue", false);
        assertOpconEventParameterValue(eventParameter, "val[[ue", false);
        assertOpconEventParameterValue(eventParameter, "val]]ue", false);
    }

    @Test
    public void test03TextIsValid() throws OpconEventParameterException {
        OpconEventParameterText eventParameter = new OpconEventParameterText();

        assertOpconEventParameterValue(eventParameter, "[[=substr([[$SCHEDULE DATE]]1,4]]", true);
        assertOpconEventParameterValue(eventParameter, "schedule_job[subschedule]", true);
        assertOpconEventParameterValue(eventParameter, "schedule_job[subschedule]_otherschedule[subsubotherschedule]", true);
    }

    @Test
    public void test04Map() throws OpconEventParameterException {
        OpconEventParameterMap eventParameter = new OpconEventParameterMap();

        Assert.assertEquals("", eventParameter.toString());
        Assert.assertEquals(new LinkedHashMap<String, String>(), eventParameter.getValue());

        eventParameter.setString("color1=blue;color2==white=black;color3=;color4=red[[,;=]]");
        Assert.assertEquals("color1=blue;color2==white=black;color3=;color4=red[[,;=]]", eventParameter.toString());
        Map<String, String> expectedMap = new LinkedHashMap<>();
        expectedMap.put("color1", "blue");
        expectedMap.put("color2", "=white=black");
        expectedMap.put("color3", "");
        expectedMap.put("color4", "red[[,;=]]");
        Assert.assertEquals(expectedMap, eventParameter.getValue());

        Map<String, String> actualMap = new LinkedHashMap<>();
        actualMap.put("name1", "francois");
        actualMap.put("name2", "=laurent=");
        actualMap.put("name3", "");
        actualMap.put("name4", "pierre[[,;=]]");
        eventParameter.setValue(actualMap);
        Assert.assertEquals("name1=francois;name2==laurent=;name3=;name4=pierre[[,;=]]", eventParameter.toString());
        Assert.assertEquals(actualMap, eventParameter.getValue());

        eventParameter.setString(null);
        Assert.assertEquals("", eventParameter.toString());
        Assert.assertEquals(new LinkedHashMap<String, String>(), eventParameter.getValue());
    }

    @Test
    public void test05MapIsValid() throws OpconEventParameterException {
        OpconEventParameterMap eventParameter = new OpconEventParameterMap();

        assertOpconEventParameterMapKey(eventParameter, "value", true);
        assertOpconEventParameterMapKey(eventParameter, "val ue", true);
        assertOpconEventParameterMapKey(eventParameter, "val[[,[];=|'()\\]]ue", true);
        assertOpconEventParameterMapKey(eventParameter, "val[ue", true);
        assertOpconEventParameterMapKey(eventParameter, "val]ue", true);

        assertOpconEventParameterMapKey(eventParameter, "", false);
        assertOpconEventParameterMapKey(eventParameter, " value", false);
        assertOpconEventParameterMapKey(eventParameter, "value ", false);
        assertOpconEventParameterMapKey(eventParameter, "val,ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val[[ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val]]ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val;ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val=ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val|ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val'ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val(ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val)ue", false);
        assertOpconEventParameterMapKey(eventParameter, "val\\ue", false);

        assertOpconEventParameterMapValue(eventParameter, "value", true);
        assertOpconEventParameterMapValue(eventParameter, "val ue", true);
        assertOpconEventParameterMapValue(eventParameter, "val[[,[];]]ue", true);
        assertOpconEventParameterMapValue(eventParameter, "val=ue", true);
        assertOpconEventParameterMapValue(eventParameter, "val|ue", true);
        assertOpconEventParameterMapValue(eventParameter, "val[ue", true);
        assertOpconEventParameterMapValue(eventParameter, "val]ue", true);

        assertOpconEventParameterMapValue(eventParameter, "", false);
        assertOpconEventParameterMapValue(eventParameter, " value", false);
        assertOpconEventParameterMapValue(eventParameter, "value ", false);
        assertOpconEventParameterMapValue(eventParameter, "val,ue", false);
        assertOpconEventParameterMapValue(eventParameter, "val[[ue", false);
        assertOpconEventParameterMapValue(eventParameter, "val]]ue", false);
        assertOpconEventParameterMapValue(eventParameter, "val;ue", false);
    }

    @Test
    public void test06MapIsValidEm() throws OpconEventParameterException {
        OpconEventParameterMap eventParameter = new OpconEventParameterMap();

        assertOpconEventParameterValue(eventParameter, "prop1=value1", true);
        assertOpconEventParameterValue(eventParameter, "prop2=value2", true);
        assertOpconEventParameterValue(eventParameter, "prop3=value3", true);
        assertOpconEventParameterValue(eventParameter, "prop1=value with spaces", true);
        assertOpconEventParameterValue(eventParameter, "key2=value2", true);
        assertOpconEventParameterValue(eventParameter, "key1==value1=", true);
        assertOpconEventParameterValue(eventParameter, "key1==", true);
        assertOpconEventParameterValue(eventParameter, "key1=val=ue1=", true);
        assertOpconEventParameterValue(eventParameter, "key1=value1=", true);
        assertOpconEventParameterValue(eventParameter, "key=value=value", true);
        assertOpconEventParameterValue(eventParameter, "key=value", true);
        assertOpconEventParameterValue(eventParameter, "key1=value1", true);
        assertOpconEventParameterValue(eventParameter, "key1=value1=value2", true);
        assertOpconEventParameterValue(eventParameter, "key=value1=abc value2=cde =", true);
        assertOpconEventParameterValue(eventParameter, "prop1=value1=value2", true);
        assertOpconEventParameterValue(eventParameter, "prop2=value3=value4", true);
        assertOpconEventParameterValue(eventParameter, "INST=01 DATE=050115 INCLUDE TYPE=PART", true);
        assertOpconEventParameterValue(eventParameter, "INST=05 INCLUDE FROMDATE=010115 CHART=05 REPORT=01 REPORT=07", true);
        assertOpconEventParameterValue(eventParameter, "INST=09 INCLUDE", true);
        assertOpconEventParameterValue(eventParameter, "ABC=[[=substr([[$SCHEDULE DATE]]1,4]]", true);
        assertOpconEventParameterValue(eventParameter, "XZY=[[=substr([[$SCHEDULE DATE]]5,2)]]", true);

        assertOpconEventParameterValue(eventParameter, "prop1=", false);
        assertOpconEventParameterValue(eventParameter, "=value1", false);
        assertOpconEventParameterValue(eventParameter, "ue2", false);
        assertOpconEventParameterValue(eventParameter, "Key2=", false);
        assertOpconEventParameterValue(eventParameter, "Key1=", false);
        assertOpconEventParameterValue(eventParameter, "='()\\,|=value", false);
        assertOpconEventParameterValue(eventParameter, "prop1=", false);
        assertOpconEventParameterValue(eventParameter, " prop1=value1 ", false);
        assertOpconEventParameterValue(eventParameter, "prop1 = value1", false);
        assertOpconEventParameterValue(eventParameter, "prop1= value1", false);
        assertOpconEventParameterValue(eventParameter, "prop1 =value1", false);
        assertOpconEventParameterValue(eventParameter, "prop1=value1 ", false);
        assertOpconEventParameterValue(eventParameter, " prop3=value3", false);
        assertOpconEventParameterValue(eventParameter, "key2=value2 ", false);
    }

    @Test
    public void test07List() throws OpconEventParameterException {
        OpconEventParameterList eventParameter = new OpconEventParameterList();

        Assert.assertEquals("", eventParameter.toString());
        Assert.assertEquals(new ArrayList<String>(), eventParameter.getValue());

        eventParameter.setString("blue;white;red=[[,;]]");
        Assert.assertEquals("blue;white;red=[[,;]]", eventParameter.toString());
        List<String> expectedList = new ArrayList<>();
        expectedList.add("blue");
        expectedList.add("white");
        expectedList.add("red=[[,;]]");
        Assert.assertEquals(expectedList, eventParameter.getValue());

        List<String> actualList = new ArrayList<>();
        actualList.add("francois");
        actualList.add("=laurent=");
        actualList.add("pierre=[[,;]]");
        eventParameter.setValue(actualList);
        Assert.assertEquals("francois;=laurent=;pierre=[[,;]]", eventParameter.toString());
        Assert.assertEquals(actualList, eventParameter.getValue());

        eventParameter.setString(null);
        Assert.assertEquals("", eventParameter.toString());
        Assert.assertEquals(new ArrayList<String>(), eventParameter.getValue());
    }

    @Test
    public void test08ListIsValid() throws OpconEventParameterException {
        OpconEventParameterList eventParameter = new OpconEventParameterList();

        assertOpconEventParameterListValue(eventParameter, "value", true);
        assertOpconEventParameterListValue(eventParameter, "val ue", true);
        assertOpconEventParameterListValue(eventParameter, "val[[,[];]]ue", true);
        assertOpconEventParameterListValue(eventParameter, "val=ue", true);
        assertOpconEventParameterListValue(eventParameter, "val|ue", true);
        assertOpconEventParameterListValue(eventParameter, "val[ue", true);
        assertOpconEventParameterListValue(eventParameter, "val]ue", true);

        assertOpconEventParameterListValue(eventParameter, "", false);
        assertOpconEventParameterListValue(eventParameter, "val,ue", false);
        assertOpconEventParameterListValue(eventParameter, "val[[ue", false);
        assertOpconEventParameterListValue(eventParameter, "val]]ue", false);
        assertOpconEventParameterListValue(eventParameter, "val;ue", false);
    }

    private <T> void assertOpconEventParameterValue(IOpconEventParameter<T> opconEventParameter, String value, boolean expected) {
        try {
            opconEventParameter.validateString(value);

            if (!expected) {
                Assert.fail("Should throw exception");
            }
        } catch (OpconEventParameterException e) {
            if (expected) {
                Assert.fail("Should not throw exception");
            }
        }
    }

    private void assertOpconEventParameterMapKey(OpconEventParameterMap opconEventParameterMap, String key, boolean expected) {
        try {
            opconEventParameterMap.validateMapKey(key);

            if (!expected) {
                Assert.fail("Should throw exception");
            }
        } catch (OpconEventParameterException e) {
            if (expected) {
                Assert.fail("Should not throw exception");
            }
        }
    }

    private void assertOpconEventParameterMapValue(OpconEventParameterMap opconEventParameterMap, String value, boolean expected) {
        try {
            opconEventParameterMap.validateMapValue(value);

            if (!expected) {
                Assert.fail("Should throw exception");
            }
        } catch (OpconEventParameterException e) {
            if (expected) {
                Assert.fail("Should not throw exception");
            }
        }
    }

    private void assertOpconEventParameterListValue(OpconEventParameterList opconEventParameterList, String value, boolean expected) {
        try {
            opconEventParameterList.validateListValue(value);

            if (!expected) {
                Assert.fail("Should throw exception");
            }
        } catch (OpconEventParameterException e) {
            if (expected) {
                Assert.fail("Should not throw exception");
            }
        }
    }
}
