package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.VersionValidator;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VersionValidatorTest {

    @Test(expected = NullPointerException.class)
    public void test01NullCaptureRegex() {
        new VersionValidator(null);
    }

    @Test
    public void test02NullCheckRules() {
        VersionValidator versionUtil = new VersionValidator("([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)");
        versionUtil.setCheckRules(null);
    }

    @Test
    public void test03NotMatch() {
        VersionValidator versionUtil = new VersionValidator("([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)");
        versionUtil.setCheckRules(null);
        Assert.assertFalse(versionUtil.isValid("1.2.3", "1.2.3"));
    }

    @Test
    public void test04Match() {
        VersionValidator versionUtil = new VersionValidator("([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)");
        versionUtil.setCheckRules(null);
        Assert.assertTrue(versionUtil.isValid("1.2.3.4", "1.2.3.4"));
        Assert.assertTrue(versionUtil.isValid("1.2.3.4.5", "1.2.3.4.5"));
    }

    @Test
    public void test05CheckRules() {
        VersionValidator versionUtil = new VersionValidator("([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)\\.([^.]*)");
        versionUtil.setCheckRules(VersionValidator.Rule.NONE, VersionValidator.Rule.MATCH, VersionValidator.Rule.EQUAL, VersionValidator.Rule.GREATER_OR_EQUAL, VersionValidator.Rule.GREATER);

        //Correct rules
        Assert.assertTrue(versionUtil.isValid("1.2.3.4.5", "1.2.3.4.4"));
        Assert.assertTrue(versionUtil.isValid("1.2.3.4.5", "a.2.03.3.3"));
        Assert.assertTrue(versionUtil.isValid("1.2.3.4.5", "1.2.3.3.6"));

        //Incorrect Match
        Assert.assertFalse(versionUtil.isValid("1.2.3.4.5", "1.02.3.4.4"));

        //Incorrect Equal
        Assert.assertFalse(versionUtil.isValid("1.2.3.4.5", "1.2.2.4.4"));
        Assert.assertFalse(versionUtil.isValid("1.2.3.4.5", "1.2.4.4.4"));
        Assert.assertFalse(versionUtil.isValid("1.2.3.4.5", "1.2.a.4.4"));

        //Incorrect Greater
        Assert.assertFalse(versionUtil.isValid("1.2.3.4.5", "1.2.3.5.4"));

        //Incorrect Greater or Equal
        Assert.assertFalse(versionUtil.isValid("1.2.3.4.5", "1.2.3.4.5"));
        Assert.assertFalse(versionUtil.isValid("1.2.3.4.5", "1.2.3.4.6"));
    }
}
