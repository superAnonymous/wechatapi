package org.tang.wechat.api.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
    private static final Log logger = LogFactory.getLog(DateUtils.class);
    public static final String ISO8601ShortPattern = "yyyy-MM-dd";
    public static final String ISO8601ShortPatternWithDay = "yyyy-MM-dd E";
    public static final String ISO8601LongPattern = "yyyy-MM-dd HH:mm:ss";
    public static final String ISO8601LongPatternWithDay = "yyyy-MM-dd HH:mm:ss E";
    public static final String ISO8601LongestPattern = "yyyy-MM-dd HH:mm:ss.S";
    public static final String ISO8601SlatPattern = "yyyy/MM/dd HH:mm:ss";
    public static final String ISO8601SlatPatternWithDay = "yyyy/MM/dd HH:mm:ss E";
    public static final String ISO8601TimePattern = "HH:mm:ss";
    public static final String ISO8601ChineseLongPattern = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String ISO8601ChineseLongPatternWithDay = "yyyy年MM月dd日 HH时mm分ss秒 E";
    public static final String ISO8601ChineseShortPattern = "yyyy年MM月dd日";
    public static final String ISO8601ChineseShortPatternWithDay = "yyyy年MM月dd日 E";

    public DateUtils() {
    }

    public static Date[] getDayPeriod(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
            Date begin = cal.getTime();
            cal.set(11, 23);
            cal.set(12, 59);
            cal.set(13, 59);
            cal.set(14, 999);
            Date end = cal.getTime();
            return new Date[]{begin, end};
        }
    }

    public static Date[] getWeekPeriod(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);

            while(cal.get(7) != 2) {
                cal.add(5, -1);
            }

            Date begin = cal.getTime();
            cal.add(5, 6);
            cal.set(11, 23);
            cal.set(12, 59);
            cal.set(13, 59);
            cal.set(14, 999);
            Date end = cal.getTime();
            return new Date[]{begin, end};
        }
    }

    public static Date[] getMonthPeriod(Date date) {
        if (date == null) {
            return null;
        } else {
            int[] days = new int[]{30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            Calendar cal = Calendar.getInstance();
            cal.set(5, 1);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
            Date begin = cal.getTime();
            boolean leapyear = cal.get(1) % 4 == 0;
            int month = cal.get(2);
            if (month == 1 && leapyear) {
                cal.set(5, days[month] + 1);
            } else {
                cal.set(5, days[month]);
            }

            cal.set(11, 23);
            cal.set(12, 59);
            cal.set(13, 59);
            cal.set(14, 999);
            Date end = cal.getTime();
            return new Date[]{begin, end};
        }
    }

    public static boolean isSameDay(Date first, Date second) {
        Calendar calA = Calendar.getInstance();
        calA.setTime(first);
        Calendar calB = Calendar.getInstance();
        calB.setTime(second);
        return calA.get(1) == calB.get(1) && calA.get(6) == calB.get(6);
    }

    public static boolean isSameWeek(Date first, Date second) {
        Calendar calA = Calendar.getInstance();
        calA.setTime(first);
        Calendar calB = Calendar.getInstance();
        calB.setTime(second);
        return calA.get(1) == calB.get(1) && calA.get(3) == calB.get(3);
    }

    public static boolean isSameMonth(Date first, Date second) {
        Calendar calA = Calendar.getInstance();
        calA.setTime(first);
        Calendar calB = Calendar.getInstance();
        calB.setTime(second);
        return calA.get(1) == calB.get(1) && calA.get(2) == calB.get(2);
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return date == null ? "" : simpleDateFormat.format(date);
    }

    public static String format(Date date, boolean day, boolean time, Locale locale) {
        if (date == null) {
            return "&nbsp;";
        } else {
            String[] WEEK_CN = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            String[] WEEK_EN = new String[]{"Sun", "Mon.", "Tues.", "Wed", "Thurs", "Fri", "Sat"};
            String s = "";
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            if (locale.equals(Locale.CHINA)) {
                s = cal.get(1) + "年" + (cal.get(2) + 1) + "月" + cal.get(5) + "日";
                if (day) {
                    s = s + " " + WEEK_CN[cal.get(7) - 1];
                }
            } else {
                s = cal.get(1) + "-" + (cal.get(2) + 1) + "-" + cal.get(5);
                if (day) {
                    s = s + " " + WEEK_EN[cal.get(7) - 1];
                }
            }

            if (time) {
                try {
                    int hour = cal.get(11);
                    int min = cal.get(12);
                    int second = cal.get(13);
                    String sh = Integer.toString(hour);
                    String sm = Integer.toString(min);
                    String ss = Integer.toString(second);
                    if (sh.length() < 2) {
                        sh = "0" + sh;
                    }

                    if (sm.length() < 2) {
                        sm = "0" + sm;
                    }

                    if (ss.length() < 2) {
                        ss = "0" + ss;
                    }

                    if (locale.equals(Locale.CHINA)) {
                        s = s + " " + sh + "点" + sm + "分" + ss + "秒";
                    } else {
                        s = s + " " + sh + ":" + sm + ":" + ss;
                    }
                } catch (Exception var14) {
                    logger.error(var14);
                }
            }

            return s;
        }
    }

    public static Date parse(String source) throws ParseException {
        return parse(source, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parse(String source, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(source);
    }

    public static int compare(Date first, Date second) {
        Calendar source = new GregorianCalendar();
        source.setTime(first);
        Calendar target = new GregorianCalendar();
        target.setTime(second);
        return source.compareTo(target);
    }
}
