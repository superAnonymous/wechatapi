package org.tang.wechat.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyerUnits {
    public VerifyerUnits() {
    }

    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", 2);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isIp(String str) {
        Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isPhone(String str) {
        Pattern pattern = Pattern.compile("(\\(\\d{3,4}\\)|\\d{3,4})\\s?(-|)?\\d{3,4}(-|)?\\d{4}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("^1\\d{2}\\s?(-|)?\\d{4}\\s?(-|)?\\d{4}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isDate(String str) {
        Pattern pattern = Pattern.compile("\\d{4}\\/?(-|)?\\d{1,2}\\/?(-|)?\\d{1,2}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isTime(String str) {
        Pattern pattern = Pattern.compile("\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isPostCode(String str) {
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isDigit(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isAlphabetic(String str) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isNumberic(String str) {
        Pattern pattern = Pattern.compile("(\\-|)\\d+(\\.\\d+|)");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isUrl(String str) {
        Pattern pattern = Pattern.compile("(http:|https:|ftp:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*", 2);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isIdentify(String str) {
        Pattern pattern = Pattern.compile("^(^\\d{15}|\\d{18}|\\d{17}(\\d|X|x))$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
