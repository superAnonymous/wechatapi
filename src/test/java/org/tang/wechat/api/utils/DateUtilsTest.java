package org.tang.wechat.api.utils;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.tang.wechat.api.utils.DateUtils.format;

public class DateUtilsTest {
    @Test
    public void test() throws ParseException {
        System.out.println(DateUtils.format(new Date()));
        System.out.println(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.S"));
        System.out.println(DateUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
        System.out.println(DateUtils.format(new Date(), "yyyy-MM-dd"));
        System.out.println(DateUtils.format(new Date(), "HH:mm:ss"));
        System.out.println(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss E"));
        System.out.println(DateUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss E"));
        System.out.println(DateUtils.format(new Date(), "yyyy-MM-dd E"));
        System.out.println(DateUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
        System.out.println(DateUtils.format(new Date(), "yyyy年MM月dd日"));
        System.out.println(DateUtils.parse("2016-08-13 21:28:44.58"));
        System.out.println(DateUtils.parse("2016-08-13 21:28:44.58", "yyyy-MM-dd HH:mm:ss.S"));
        System.out.println(DateUtils.parse("2016/08/13 21:28:44.58", "yyyy/MM/dd HH:mm:ss"));
        System.out.println(DateUtils.parse("21:28:44", "HH:mm:ss"));
        System.out.println(DateUtils.parse("2016-08-13", "yyyy-MM-dd"));
    }
}
