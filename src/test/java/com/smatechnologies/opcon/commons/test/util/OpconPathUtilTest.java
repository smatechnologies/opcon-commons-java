package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.OpconPathUtil;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.file.Paths;

import static com.smatechnologies.opcon.commons.util.OSUtil.isWindows;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpconPathUtilTest {

    @Before
    public void windowsOnly() {
        Assume.assumeTrue(isWindows());
    }

    @Test
    public void test01OsInfoNormal() {
        OpconPathUtil.OSInfo osInfo = new OpconPathUtil.OSInfo(Paths.get("C:\\"),
                Paths.get("C:\\Program Files"),
                Paths.get("C:\\Program Files (x86)"),
                Paths.get("C:\\ProgramData"));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Program Files\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Program Files (x86)\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files (x86)\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("D:\\Program Files\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Program Files (x86)\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("D:\\"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Solution Manager"), osInfo));
    }

    @Test
    public void test02OsInfoDriveChanged() {
        OpconPathUtil.OSInfo osInfo = new OpconPathUtil.OSInfo(Paths.get("D:\\"),
                Paths.get("D:\\Program Files"),
                Paths.get("D:\\Program Files (x86)"),
                Paths.get("D:\\ProgramData"));

        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\"), osInfo));
        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Program Files\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Program Files (x86)\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files (x86)\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("C:\\Program Files\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\Program Files (x86)\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("C:\\"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\"), osInfo));
        Assert.assertEquals(Paths.get("C:\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Solution Manager"), osInfo));
    }

    @Test
    public void test04OsInfoProgramDataChanged() {
        OpconPathUtil.OSInfo osInfo = new OpconPathUtil.OSInfo(Paths.get("C:\\"),
                Paths.get("C:\\Program Files"),
                Paths.get("C:\\Program Files (x86)"),
                Paths.get("D:\\MyProgramData"));

        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Program Files\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Program Files (x86)\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files (x86)\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("D:\\Program Files\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Program Files (x86)\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("D:\\"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Solution Manager"), osInfo));
    }

    @Test
    public void test05OsInfoProgramFilesChanged() {
        OpconPathUtil.OSInfo osInfo = new OpconPathUtil.OSInfo(Paths.get("C:\\"),
                Paths.get("D:\\My Program Files"),
                Paths.get("D:\\My Program Files (x86)"),
                Paths.get("C:\\ProgramData"));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\My Program Files\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\My Program Files\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\My Program Files (x86)\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\My Program Files (x86)\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\My Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\My Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Program Files\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\OpConXps\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Program Files (x86)\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files (x86)\\OpConXps\\Solution Manager"), osInfo));

        Assert.assertEquals(Paths.get("D:\\"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\OpConXps\\Somewhere\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Somewhere\\Solution Manager"), osInfo));
        Assert.assertEquals(Paths.get("D:\\OpConXps\\Solution Manager"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\OpConXps\\Solution Manager"), osInfo));
    }

    @Test
    public void test06TestsDuplicatedFromTestApi() {
        OpconPathUtil.OSInfo osInfo = new OpconPathUtil.OSInfo(Paths.get("C:\\"),
                Paths.get("C:\\Program Files"),
                Paths.get("C:\\Program Files (x86)"),
                Paths.get("C:\\ProgramData"));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\SAM"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files (x86)\\OpConXps\\SAM"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Program Files(x86)\\OpConXps\\SAM"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files(x86)\\OpConXps\\SAM"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConXps\\Agents\\123\\Some Deeper Folder"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\OpConXps\\Agents\\123\\Some Deeper Folder"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Program Files\\OpConXps\\Agents\\123\\Some Deeper Folder"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files\\OpConXps\\Agents\\123\\Some Deeper Folder"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConxps\\Agents\\123\\Some Deeper Folder"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Agents\\123\\Some Deeper Folder"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Agents\\123\\Some Deeper Folder"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Agents\\123\\Some Deeper Folder"), osInfo));
        Assert.assertEquals(Paths.get("\\\\MachineName\\OpConXps\\Agents\\123\\Some Deeper Folder"), OpconPathUtil.getVarDirectoryPath(Paths.get("\\\\MachineName\\OpConXps\\Agents\\123\\Some Deeper Folder"), osInfo));
        Assert.assertEquals(Paths.get("\\\\MachineName\\Abcd\\123\\Some Deeper Folder"), OpconPathUtil.getVarDirectoryPath(Paths.get("\\\\MachineName\\Abcd\\123\\Some Deeper Folder"), osInfo));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConxps\\MyOpCon\\MyOpConXps\\SAM"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\MyOpCon\\MyOpConXps\\SAM"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyOpCon\\MyOpConXps\\SAM"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\MyOpCon\\MyOpConXps\\SAM"), osInfo));

        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConxps\\SAM\\Log"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\OpConxps\\SAM").resolve("Log"), osInfo));
        Assert.assertEquals(Paths.get("C:\\ProgramData\\OpConxps\\MyApplications\\MyOpCon\\MSLSAM\\JobOutput"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\MyApplications\\MyOpCon\\MSLSAM").resolve("JobOutput"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Program Files\\OpConxps\\SAM\\Log\\SMASchedMan"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files\\OpConxps\\SAM").resolve("Log\\SMASchedMan"), osInfo));
        Assert.assertEquals(Paths.get("D:\\MyApplications\\MyOpCon\\MSLSAM\\JobOutput"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\MyApplications\\MyOpCon\\MSLSAM").resolve("JobOutput"), osInfo));

        Assert.assertEquals(Paths.get("D:\\MyApplications\\Abc\\JobOutput"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\MyApplications\\MyOpCon\\MSLSAM").resolve("..\\..\\Abc\\JobOutput"), osInfo));

        osInfo = new OpconPathUtil.OSInfo(Paths.get("C:\\"),
                Paths.get("C:\\Program Files"),
                Paths.get("C:\\Program Files (x86)"),
                Paths.get("C:\\\\MyOwnProgramData"));

        Assert.assertEquals(Paths.get("C:\\MyOwnProgramData\\OpConXps\\SAM"), OpconPathUtil.getVarDirectoryPath(Paths.get("C:\\Program Files\\OpConXps\\SAM"), osInfo));
        Assert.assertEquals(Paths.get("D:\\Program Files\\OpConXps\\SAM"), OpconPathUtil.getVarDirectoryPath(Paths.get("D:\\Program Files\\OpConXps\\SAM"), osInfo));
    }
}
