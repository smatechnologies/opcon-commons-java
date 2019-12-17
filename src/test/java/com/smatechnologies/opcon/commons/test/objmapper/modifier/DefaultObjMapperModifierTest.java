package com.smatechnologies.opcon.commons.test.objmapper.modifier;

import com.smatechnologies.opcon.commons.objmapper.modifier.DefaultObjMapperModifier;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DefaultObjMapperModifierTest {

    private DefaultObjMapperModifier modifier = new DefaultObjMapperModifier();

    @Test
    public void test01Null() {
        Assert.assertEquals(null, modifier.modify(null));
    }

    @Test
    public void test02Object() {
        Object object = new Object();
        Assert.assertEquals(object, modifier.modify(object));
    }
}
