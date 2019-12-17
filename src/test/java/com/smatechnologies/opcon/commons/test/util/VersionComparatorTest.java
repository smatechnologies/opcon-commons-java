package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.VersionComparator;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VersionComparatorTest {

    @Test
    public void test01equals() {
        List<List<String>> tests = Arrays.asList(
                Arrays.asList(null, null),
                Arrays.asList("19", "19"),
                Arrays.asList("19.0", "19.0"),
                Arrays.asList("19.0.0", "19.0.0"),
                Arrays.asList("19.0.0.0", "19.0.0.0"),
                Arrays.asList("19.0.0.0a", "19.0.0.0a")
        );

        tests.forEach(test -> Assert.assertEquals(0, new VersionComparator().compare(test.get(0), test.get(1))));
        tests.forEach(test -> Assert.assertEquals(0, new VersionComparator().compare(test.get(1), test.get(0))));
    }

    @Test
    public void test02previousAndNext() {
        List<List<String>> tests = Arrays.asList(
                Arrays.asList(null, "19"),
                Arrays.asList("9", "18"),
                Arrays.asList("8", "19"),
                Arrays.asList("19.8", "19.12"),
                Arrays.asList("19.8", "19.12a"),
                Arrays.asList("19.12", "19.8a"),
                Arrays.asList("18", "19"),
                Arrays.asList("18", "18a"),
                Arrays.asList("18", "1a"),
                Arrays.asList("18", "188a"),
                Arrays.asList("19.0", "19.1"),
                Arrays.asList("19.0", "20.0"),
                Arrays.asList("19.0", "19.0.1"),
                Arrays.asList("19.0", "19.0.1"),
                Arrays.asList("19.0", "19.1.0"),
                Arrays.asList("18.3.1", "19.0.0"),
                Arrays.asList("18.3.1", "19.1.0"),
                Arrays.asList("18.3.1.5", "19.0.0"),
                Arrays.asList("18.3.1.5", "19.1.0"),
                Arrays.asList("18.3.1", "19.0.0.5"),
                Arrays.asList("18.3.1", "19.1.0.5"),
                Arrays.asList("18.3.1", "19.1.0.5-SNAPSHOT"),
                Arrays.asList("18.3.1-SNAPSHOT", "19.1.0.5"),
                Arrays.asList("19.1.0.5", "19.1.0.5-SNAPSHOT")
        );

        tests.forEach(test -> Assert.assertTrue(new VersionComparator().compare(test.get(0), test.get(1)) < 0));
        tests.forEach(test -> Assert.assertTrue(new VersionComparator().compare(test.get(1), test.get(0)) > 0));
    }
}
