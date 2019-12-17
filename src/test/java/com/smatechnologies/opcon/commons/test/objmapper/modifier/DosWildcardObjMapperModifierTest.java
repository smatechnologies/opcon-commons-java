package com.smatechnologies.opcon.commons.test.objmapper.modifier;

import com.smatechnologies.opcon.commons.objmapper.modifier.DosWildcardObjMapperModifier;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DosWildcardObjMapperModifierTest {

    private DosWildcardObjMapperModifier modifier = new DosWildcardObjMapperModifier();

    @Test
    public void test01NullOrEmpty() {
        Assert.assertEquals(null, modifier.modify(null));
        Assert.assertEquals(null, modifier.modify(""));
    }

    @Test
    public void test02() {
        Assert.assertEquals("*abcdef*", modifier.modify("abcdef"));
        Assert.assertEquals("*abc*def*", modifier.modify("abc*def"));
        Assert.assertEquals("*abc?def*", modifier.modify("abc?def"));

        Assert.assertEquals("abcdef", modifier.modify("=abcdef"));
        Assert.assertEquals("abc*def", modifier.modify("=abc*def"));
        Assert.assertEquals("abc?def", modifier.modify("=abc?def"));
    }
}
