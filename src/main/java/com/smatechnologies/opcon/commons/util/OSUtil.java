package com.smatechnologies.opcon.commons.util;

public class OSUtil {

    private static Os os;

    public enum Os {
        WINDOWS,
        LINUX,
        MAC_OS_X,
        OTHER
    }

    public static Os getOs() {
        if (os == null) {
            String sysPropOsName = SystemUtil.getOsName();

            if (sysPropOsName.startsWith("Windows")) {
                os = Os.WINDOWS;
            } else if (sysPropOsName.startsWith("Linux") || sysPropOsName.startsWith("LINUX")) {
                os = Os.LINUX;
            } else if (sysPropOsName.startsWith("Mac OS X")) {
                os = Os.MAC_OS_X;
            } else {
                os = Os.OTHER;
            }
        }

        return os;
    }

    public static boolean isWindows() {
        return getOs() == Os.WINDOWS;
    }

    public static boolean isLinux() {
        return getOs() == Os.LINUX;
    }

    public static boolean isMacOSX() {
        return getOs() == Os.MAC_OS_X;
    }

    public static boolean isJava32onNot32Windows() {
        return isWindows() && SystemUtil.getDataModel().equals("32") && System.getenv("ProgramFiles(x86)") != null;
    }
}
