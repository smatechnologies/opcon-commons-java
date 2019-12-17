package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.CollectionUtil;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CollectionUtilTest {

    @Test
    public void test01GetInsertIndexToKeepSorting() {
        List<String> list = Arrays.asList("b", "d", "f");

        Assert.assertEquals(0, CollectionUtil.getInsertIndexToKeepSorting(list, "a", String::compareTo));
        Assert.assertEquals(1, CollectionUtil.getInsertIndexToKeepSorting(list, "b", String::compareTo));
        Assert.assertEquals(1, CollectionUtil.getInsertIndexToKeepSorting(list, "c", String::compareTo));
        Assert.assertEquals(2, CollectionUtil.getInsertIndexToKeepSorting(list, "d", String::compareTo));
        Assert.assertEquals(2, CollectionUtil.getInsertIndexToKeepSorting(list, "e", String::compareTo));
        Assert.assertEquals(3, CollectionUtil.getInsertIndexToKeepSorting(list, "f", String::compareTo));
        Assert.assertEquals(3, CollectionUtil.getInsertIndexToKeepSorting(list, "g", String::compareTo));

        Assert.assertEquals(0, CollectionUtil.getInsertIndexToKeepSorting(list, "a"));
        Assert.assertEquals(1, CollectionUtil.getInsertIndexToKeepSorting(list, "b"));
        Assert.assertEquals(1, CollectionUtil.getInsertIndexToKeepSorting(list, "c"));
        Assert.assertEquals(2, CollectionUtil.getInsertIndexToKeepSorting(list, "d"));
        Assert.assertEquals(2, CollectionUtil.getInsertIndexToKeepSorting(list, "e"));
        Assert.assertEquals(3, CollectionUtil.getInsertIndexToKeepSorting(list, "f"));
        Assert.assertEquals(3, CollectionUtil.getInsertIndexToKeepSorting(list, "g"));
    }

    @Test
    public void test02ExcludeElements() {
        List<String> list = null;
        List<String> listToExclude = null;

        Assert.assertEquals(null, CollectionUtil.excludeElements(list, listToExclude));

        list = Arrays.asList("a", "b", "c");
        listToExclude = null;

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude));

        list = Arrays.asList("a", "b", "c");
        listToExclude = Collections.emptyList();

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude));

        list = null;
        listToExclude = Arrays.asList("b", "c", "d");

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude));

        list = Collections.emptyList();
        listToExclude = Arrays.asList("b", "c", "d");

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude));

        list = Arrays.asList("a", "b", "c");
        listToExclude = Arrays.asList("b", "c", "d");

        Assert.assertEquals(Arrays.asList("a"), CollectionUtil.excludeElements(list, listToExclude));
    }

    @Test
    public void test03ExcludeElements2() {
        CollectionUtil.ItemComparator<String, Integer> itemComparator = (o, o2) -> o.equals(String.valueOf(o2));

        List<String> list = null;
        List<Integer> listToExclude = null;

        Assert.assertEquals(null, CollectionUtil.excludeElements(list, listToExclude, itemComparator));

        list = Arrays.asList("1", "2", "3");
        listToExclude = null;

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude, itemComparator));

        list = Arrays.asList("1", "2", "3");
        listToExclude = Collections.emptyList();

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude, itemComparator));

        list = null;
        listToExclude = Arrays.asList(2, 3, 4);

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude, itemComparator));

        list = Collections.emptyList();
        listToExclude = Arrays.asList(2, 3, 4);

        Assert.assertEquals(list, CollectionUtil.excludeElements(list, listToExclude, itemComparator));

        list = Arrays.asList("1", "2", "3");
        listToExclude = Arrays.asList(2, 3, 4);

        Assert.assertEquals(Arrays.asList("1"), CollectionUtil.excludeElements(list, listToExclude, itemComparator));
    }

    @Test
    public void test04RetainAll() {
        List<String> list = null;
        List<String> list2 = null;

        Assert.assertEquals(null, CollectionUtil.retainAll(list, list2));

        list = Arrays.asList("a", "b", "c");
        list2 = null;

        Assert.assertEquals(null, CollectionUtil.retainAll(list, list2));

        list = Arrays.asList("a", "b", "c");
        list2 = Collections.emptyList();

        Assert.assertTrue(CollectionUtil.retainAll(list, list2).isEmpty());

        list = null;
        list2 = Arrays.asList("b", "c", "d");

        Assert.assertEquals(null, CollectionUtil.retainAll(list, list2));

        list = Collections.emptyList();
        list2 = Arrays.asList("b", "c", "d");

        Assert.assertTrue(CollectionUtil.retainAll(list, list2).isEmpty());

        list = Arrays.asList("a", "b", "c");
        list2 = Arrays.asList("b", "c", "d");

        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("b", "b");
        expectedMap.put("c", "c");

        Assert.assertEquals(expectedMap, CollectionUtil.retainAll(list, list2));
    }

    @Test
    public void test05RetainAll2() {
        CollectionUtil.ItemComparator<String, Integer> itemComparator = (o, o2) -> o.equals(String.valueOf(o2));

        List<String> list = null;
        List<Integer> list2 = null;

        Assert.assertEquals(null, CollectionUtil.retainAll(list, list2, itemComparator));

        list = Arrays.asList("a", "b", "c");
        list2 = null;

        Assert.assertEquals(null, CollectionUtil.retainAll(list, list2, itemComparator));

        list = Arrays.asList("a", "b", "c");
        list2 = Collections.emptyList();

        Assert.assertTrue(CollectionUtil.retainAll(list, list2, itemComparator).isEmpty());

        list = null;
        list2 = Arrays.asList(2, 3, 4);

        Assert.assertEquals(null, CollectionUtil.retainAll(list, list2, itemComparator));

        list = Collections.emptyList();
        list2 = Arrays.asList(2, 3, 4);

        Assert.assertTrue(CollectionUtil.retainAll(list, list2, itemComparator).isEmpty());

        list = Arrays.asList("1", "2", "3");
        list2 = Arrays.asList(2, 3, 4);

        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("2", 2);
        expectedMap.put("3", 3);

        Assert.assertEquals(expectedMap, CollectionUtil.retainAll(list, list2, itemComparator));
    }

    @Test
    public void test06SplitList() {
        CollectionUtil.ItemCounter<String> itemCounter = String::length;

        Assert.assertEquals(null, CollectionUtil.splitSet(null, 10, itemCounter));
        Assert.assertEquals(asSet(), CollectionUtil.splitSet(asSet(), 10, itemCounter));

        try {
            CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 10, null);
            Assert.fail("Should throw exception");
        } catch (NullPointerException e) {
        }

        try {
            CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), Integer.MIN_VALUE, itemCounter);
            Assert.fail("Should throw exception");
        } catch (IllegalArgumentException e) {
        }

        try {
            CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), -1, itemCounter);
            Assert.fail("Should throw exception");
        } catch (IllegalArgumentException e) {
        }

        try {
            CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 0, itemCounter);
            Assert.fail("Should throw exception");
        } catch (IllegalArgumentException e) {
        }

        try {
            CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 1, itemCounter);
            Assert.fail("Should throw exception");
        } catch (IllegalArgumentException e) {
        }

        try {
            CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 2, itemCounter);
            Assert.fail("Should throw exception");
        } catch (IllegalArgumentException e) {
        }

        Assert.assertEquals(asSet(asSet("ab1"), asSet("ab2"), asSet("ab3"), asSet("ab4"), asSet("ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 3, itemCounter));
        Assert.assertEquals(asSet(asSet("ab1"), asSet("ab2"), asSet("ab3"), asSet("ab4"), asSet("ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 5, itemCounter));

        Assert.assertEquals(asSet(asSet("ab1", "ab2"), asSet("ab3", "ab4"), asSet("ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 6, itemCounter));
        Assert.assertEquals(asSet(asSet("ab1", "ab2"), asSet("ab3", "ab4"), asSet("ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 8, itemCounter));

        Assert.assertEquals(asSet(asSet("ab1", "ab2", "ab3"), asSet("ab4", "ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 9, itemCounter));
        Assert.assertEquals(asSet(asSet("ab1", "ab2", "ab3"), asSet("ab4", "ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 11, itemCounter));

        Assert.assertEquals(asSet(asSet("ab1", "ab2", "ab3", "ab4"), asSet("ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 12, itemCounter));
        Assert.assertEquals(asSet(asSet("ab1", "ab2", "ab3", "ab4"), asSet("ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 14, itemCounter));

        Assert.assertEquals(asSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 15, itemCounter));
        Assert.assertEquals(asSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), 17, itemCounter));
        Assert.assertEquals(asSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5")), CollectionUtil.splitSet(asSet("ab1", "ab2", "ab3", "ab4", "ab5"), Integer.MAX_VALUE, itemCounter));
    }

    @Test
    public void test07IsNullOrEmpty() {
        Assert.assertEquals(true, CollectionUtil.isNullOrEmpty(null));

        Assert.assertEquals(true, CollectionUtil.isNullOrEmpty(Collections.emptyList()));
        Assert.assertEquals(false, CollectionUtil.isNullOrEmpty(Collections.singletonList("foo")));
        Assert.assertEquals(false, CollectionUtil.isNullOrEmpty(Arrays.asList("foo", "bar")));

        Assert.assertEquals(true, CollectionUtil.isNullOrEmpty(Collections.emptySet()));
        Assert.assertEquals(false, CollectionUtil.isNullOrEmpty(Collections.singleton("foo")));
        Assert.assertEquals(false, CollectionUtil.isNullOrEmpty(new HashSet<>(Arrays.asList("foo", "bar"))));
    }

    @Test
    public void test08nullToEmpty() {
        Assert.assertEquals(Collections.emptyList(), CollectionUtil.nullToEmpty((List) null));

        Assert.assertEquals(Collections.emptyList(), CollectionUtil.nullToEmpty(Collections.emptyList()));
        Assert.assertEquals(Collections.singletonList("foo"), CollectionUtil.nullToEmpty(Collections.singletonList("foo")));
        Assert.assertEquals(Arrays.asList("foo", "bar"), CollectionUtil.nullToEmpty(Arrays.asList("foo", "bar")));

        Assert.assertEquals(Collections.emptySet(), CollectionUtil.nullToEmpty(Collections.emptySet()));
        Assert.assertEquals(Collections.singleton("foo"), CollectionUtil.nullToEmpty(Collections.singleton("foo")));
        Assert.assertEquals(new HashSet<>(Arrays.asList("foo", "bar")), CollectionUtil.nullToEmpty(new HashSet<>(Arrays.asList("foo", "bar"))));

        Assert.assertEquals(Collections.emptyMap(), CollectionUtil.nullToEmpty(Collections.emptyMap()));
        Assert.assertEquals(Collections.singletonMap("foo", "bar"), CollectionUtil.nullToEmpty(Collections.singletonMap("foo", "bar")));
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("bar", "foo");
        Assert.assertEquals(map, CollectionUtil.nullToEmpty(map));
    }

    @Test
    public void test00emptyToNull() {
        Assert.assertEquals(null, CollectionUtil.emptyToNull(null));

        Assert.assertEquals(null, CollectionUtil.emptyToNull(Collections.emptyList()));
        Assert.assertEquals(Collections.singletonList("foo"), CollectionUtil.emptyToNull(Collections.singletonList("foo")));
        Assert.assertEquals(Arrays.asList("foo", "bar"), CollectionUtil.emptyToNull(Arrays.asList("foo", "bar")));

        Assert.assertEquals(null, CollectionUtil.emptyToNull(Collections.emptySet()));
        Assert.assertEquals(Collections.singleton("foo"), CollectionUtil.emptyToNull(Collections.singleton("foo")));
        Assert.assertEquals(new HashSet<>(Arrays.asList("foo", "bar")), CollectionUtil.emptyToNull(new HashSet<>(Arrays.asList("foo", "bar"))));
    }

    private static <T> Set<T> asSet(T... a) {
        return new LinkedHashSet<>(Arrays.asList(a));
    }
}
