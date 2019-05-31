package org.tang.wechat.api.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomUtils extends org.apache.commons.lang3.RandomUtils {
    public RandomUtils() {
    }

    public static String getNumber(int codeLength) {
        Random rd = new Random();
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < codeLength; ++i) {
            buf.append(rd.nextInt(10));
        }

        return buf.toString();
    }

    public static String getAlphabetic(int codeLength) {
        String CHOOSE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rd = new Random();
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < codeLength; ++i) {
            buf.append(String.valueOf("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(rd.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length()))));
        }

        return buf.toString();
    }

    public static String getString(int codeLength) {
        String CHOOSE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rd = new Random();
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < codeLength; ++i) {
            buf.append(String.valueOf("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(rd.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length()))));
        }

        return buf.toString();
    }

    public static String getGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    public static String getRandomId() {
        return DigestUtils.md5Hex(getGUID());
    }

    public static String getStringWithTimestamp(int codeLength) {
        Date nowtime = new Date();
        String filename = getString(codeLength) + "_" + nowtime.getTime();
        return filename;
    }
}
