package com.smatechnologies.opcon.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author Pierre PINON
 */
public class Md5Util {

    private final static String HASH_ALGORITHM = "MD5";

    private Md5Util() {
    }

    public static String hash(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        StringBuffer stringBuffer = new StringBuffer();
        for (byte byt : messageDigest.digest(string.getBytes())) {
            stringBuffer.append(String.format("%02x", byt & 0xff));
        }

        return stringBuffer.toString();
    }
}
