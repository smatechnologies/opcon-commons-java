package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.Md5Util;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.security.NoSuchAlgorithmException;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Md5UtilTest {

    @Test
    public void test01hash() throws NoSuchAlgorithmException {
        Assert.assertEquals("d41d8cd98f00b204e9800998ecf8427e", Md5Util.hash(""));
        Assert.assertEquals("437b930db84b8079c2dd804a71936b5f", Md5Util.hash("something"));
    }
}
