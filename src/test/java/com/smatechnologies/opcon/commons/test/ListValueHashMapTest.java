package com.smatechnologies.opcon.commons.test;

import com.smatechnologies.opcon.commons.ListValueHashMap;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListValueHashMapTest {

    @Test
    public void test01() {
        ListValueHashMap<Integer, String> map = new ListValueHashMap<>();

        Assert.assertEquals(null, map.get(5));
        Assert.assertEquals(Arrays.asList(), map.getListItems(5));
        Assert.assertEquals(null, map.get(6));
        Assert.assertEquals(Arrays.asList(), map.getListItems(6));

        map.putListItem(5, "orange");
        Assert.assertEquals(Arrays.asList("orange"), map.get(5));
        Assert.assertEquals(Arrays.asList("orange"), map.getListItems(5));

        map.putListItem(6, "blue");
        Assert.assertEquals(Arrays.asList("blue"), map.get(6));
        Assert.assertEquals(Arrays.asList("blue"), map.getListItems(6));

        map.putListItem(6, "red");
        Assert.assertEquals(Arrays.asList("blue", "red"), map.get(6));
        Assert.assertEquals(Arrays.asList("blue", "red"), map.getListItems(6));

        map.removeListItem(6, "blue");
        Assert.assertEquals(Arrays.asList("red"), map.get(6));
        Assert.assertEquals(Arrays.asList("red"), map.getListItems(6));

        map.removeListItem(6, "red");
        Assert.assertEquals(null, map.get(6));
        Assert.assertEquals(Arrays.asList(), map.getListItems(6));
    }
}
