package com.smatechnologies.opcon.commons.util;

public class SystemUtil {
    private final static int NUMBER_OF_BYTES_IN_MEGA_BYTE = 1048576;

    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public static String getInfos() {
        StringBuilder stringBuilder = new StringBuilder("System Informations:" + LINE_SEPARATOR);
        stringBuilder.append("===================================" + LINE_SEPARATOR);

        stringBuilder.append("JVM Version: " + getJavaVersion() + LINE_SEPARATOR);
        stringBuilder.append("Cores: " + Runtime.getRuntime().availableProcessors() + LINE_SEPARATOR);
        stringBuilder.append("Used JVM Memory: " + getUsedMemoryInMo() + "Mo/" + getMaxMemoryInMo() + "Mo" + LINE_SEPARATOR);
        stringBuilder.append("Data Model: " + getDataModel() + LINE_SEPARATOR);
        stringBuilder.append("OS Architecture: " + getOsArch() + LINE_SEPARATOR);
        if (OSUtil.isJava32onNot32Windows()) {
            stringBuilder.append("WARNING: Java32 on not 32 Windows!" + LINE_SEPARATOR);
        }
        stringBuilder.append("OS Name: " + getOsName() + LINE_SEPARATOR);
        stringBuilder.append("OS Version: " + getOsVersion() + LINE_SEPARATOR);

        stringBuilder.append("===================================");
        return stringBuilder.toString();
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    public static String getDataModel() {
        return System.getProperty("sun.arch.data.model");
    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static long getFreeMemoryInMo() {
        return convertBytesToMbytes(Runtime.getRuntime().freeMemory());
    }

    public static long getMaxMemoryInMo() {
        return convertBytesToMbytes(Runtime.getRuntime().maxMemory());
    }

    public static long getAllocatedMemoryInMo() {
        return convertBytesToMbytes(Runtime.getRuntime().totalMemory());
    }

    public static long getUsedMemoryInMo() {
        return getAllocatedMemoryInMo() - getFreeMemoryInMo();
    }

    private static long convertBytesToMbytes(long bytes) {
        return bytes / NUMBER_OF_BYTES_IN_MEGA_BYTE;
    }
}
