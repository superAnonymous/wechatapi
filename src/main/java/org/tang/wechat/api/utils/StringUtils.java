package org.tang.wechat.api.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;
    private static final String ABBREVIATE_SUFFIX = "...";
    public static final String LINE_BREAK = System.getProperty("line.separator");

    public StringUtils() {
    }

    public static String htmlEnc(String htmlStr) {
        int ln = htmlStr.length();

        for(int i = 0; i < ln; ++i) {
            char c = htmlStr.charAt(i);
            if (c == '<' || c == '>' || c == '&' || c == '"') {
                StringBuffer b = new StringBuffer(htmlStr.substring(0, i));
                switch(c) {
                    case '"':
                        b.append("&quot;");
                        break;
                    case '&':
                        b.append("&amp;");
                        break;
                    case '<':
                        b.append("&lt;");
                        break;
                    case '>':
                        b.append("&gt;");
                }

                ++i;

                int next;
                for(next = i; i < ln; ++i) {
                    c = htmlStr.charAt(i);
                    if (c == '<' || c == '>' || c == '&' || c == '"') {
                        b.append(htmlStr.substring(next, i));
                        switch(c) {
                            case '"':
                                b.append("&quot;");
                                break;
                            case '&':
                                b.append("&amp;");
                                break;
                            case '<':
                                b.append("&lt;");
                                break;
                            case '>':
                                b.append("&gt;");
                        }

                        next = i + 1;
                    }
                }

                if (next < ln) {
                    b.append(htmlStr.substring(next));
                }

                htmlStr = b.toString();
                break;
            }
        }

        return htmlStr;
    }

    public static String xmlEnc(String xmlStr) {
        int ln = xmlStr.length();

        for(int i = 0; i < ln; ++i) {
            char c = xmlStr.charAt(i);
            if (c == '<' || c == '>' || c == '&' || c == '"' || c == '\'') {
                StringBuffer b = new StringBuffer(xmlStr.substring(0, i));
                switch(c) {
                    case '"':
                        b.append("&quot;");
                        break;
                    case '&':
                        b.append("&amp;");
                        break;
                    case '\'':
                        b.append("&apos;");
                        break;
                    case '<':
                        b.append("&lt;");
                        break;
                    case '>':
                        b.append("&gt;");
                }

                ++i;

                int next;
                for(next = i; i < ln; ++i) {
                    c = xmlStr.charAt(i);
                    if (c == '<' || c == '>' || c == '&' || c == '"' || c == '\'') {
                        b.append(xmlStr.substring(next, i));
                        switch(c) {
                            case '"':
                                b.append("&quot;");
                                break;
                            case '&':
                                b.append("&amp;");
                                break;
                            case '\'':
                                b.append("&apos;");
                                break;
                            case '<':
                                b.append("&lt;");
                                break;
                            case '>':
                                b.append("&gt;");
                        }

                        next = i + 1;
                    }
                }

                if (next < ln) {
                    b.append(xmlStr.substring(next));
                }

                xmlStr = b.toString();
                break;
            }
        }

        return xmlStr;
    }

    public static String xmlEncNQ(String xmlStr) {
        int ln = xmlStr.length();

        for(int i = 0; i < ln; ++i) {
            char c = xmlStr.charAt(i);
            if (c == '<' || c == '>' || c == '&') {
                StringBuffer b = new StringBuffer(xmlStr.substring(0, i));
                switch(c) {
                    case '&':
                        b.append("&amp;");
                        break;
                    case '<':
                        b.append("&lt;");
                        break;
                    case '>':
                        b.append("&gt;");
                }

                ++i;

                int next;
                for(next = i; i < ln; ++i) {
                    c = xmlStr.charAt(i);
                    if (c == '<' || c == '>' || c == '&') {
                        b.append(xmlStr.substring(next, i));
                        switch(c) {
                            case '&':
                                b.append("&amp;");
                                break;
                            case '<':
                                b.append("&lt;");
                                break;
                            case '>':
                                b.append("&gt;");
                        }

                        next = i + 1;
                    }
                }

                if (next < ln) {
                    b.append(xmlStr.substring(next));
                }

                xmlStr = b.toString();
                break;
            }
        }

        return xmlStr;
    }

    public static List<String> getDelimitedValues(String delimitedString, String delim) {
        List<String> delimitedValues = new ArrayList();
        if (delimitedString != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(delimitedString, delim);

            while(stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken().toLowerCase().trim();
                if (nextToken.length() > 0) {
                    delimitedValues.add(nextToken);
                }
            }
        }

        return delimitedValues;
    }

    public static String duplicate(String srcString, int numberOfCopies) {
        StringBuffer result = new StringBuffer();
        if (numberOfCopies > 0) {
            for(int i = 1; i <= numberOfCopies; ++i) {
                result.append(srcString);
            }
        } else {
            result = new StringBuffer("");
        }

        return result.toString();
    }

    public static long getLong(String str) {
        return getLong(str, 0L);
    }

    public static long getLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public static int getInt(String str) {
        return getInt(str, 0);
    }

    public static int getInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception var3) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public static boolean getBoolean(String str, boolean defaultValue) {
        if (str == null) {
            return defaultValue;
        } else if (VerifyerUnits.isDigit(str)) {
            int x = Integer.parseInt(str);
            return x > 0;
        } else {
            return str.equals("true") || str.equals("yes");
        }
    }

    public static int validString(String srcString, String validTarget) {
        for(int i = 0; i < srcString.length(); ++i) {
            if (validTarget.indexOf(srcString.charAt(i)) == -1) {
                return i;
            }
        }

        return -1;
    }

    public static boolean hasChinese(String string) {
        String regEx = "[一-龥]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return m.find();
    }

    public static int charCount(String srcString, char searchChar) {
        int result = 0;
        if (srcString.length() > 0) {
            for(int i = 0; i < srcString.length(); ++i) {
                if (searchChar == srcString.charAt(i)) {
                    ++result;
                }
            }
        }

        return result;
    }

    public static String stripLeft(String srcString, String token) {
        if (srcString == null) {
            return null;
        } else {
            return srcString.startsWith(token) ? srcString.substring(token.length(), srcString.length()) : srcString;
        }
    }

    public static String stripTrailing(String srcString, String token) {
        if (srcString == null) {
            return null;
        } else {
            return srcString.endsWith(token) ? srcString.substring(0, srcString.lastIndexOf(token)) : "";
        }
    }

    public static String splitLeft(String srcString, String token) {
        if (srcString == null) {
            return null;
        } else {
            int split = srcString.lastIndexOf(token);
            return split == -1 ? srcString : srcString.substring(0, split);
        }
    }

    public static String splitTrailing(String srcString, String token) {
        if (srcString == null) {
            return null;
        } else {
            int split = srcString.lastIndexOf(token);
            return split == -1 ? "" : srcString.substring(split + 1, srcString.length());
        }
    }

    public static String encodeUnicode(String srcString) {
        if (srcString.trim().length() == 0) {
            return srcString;
        } else {
            StringBuffer encodedChars = new StringBuffer();

            for(int i = 0; i < srcString.length(); ++i) {
                String charInHex = Integer.toString(srcString.charAt(i), 16).toUpperCase();
                switch(charInHex.length()) {
                    case 1:
                        encodedChars.append("\\u000").append(charInHex);
                        break;
                    case 2:
                        encodedChars.append("\\u00").append(charInHex);
                        break;
                    case 3:
                        encodedChars.append("\\u0").append(charInHex);
                        break;
                    default:
                        encodedChars.append("\\u").append(charInHex);
                }
            }

            return encodedChars.toString();
        }
    }

    public static int getByteLength(String srcString) {
        return getByteLength(srcString, "UTF-8");
    }

    public static int getByteLength(String srcString, String encode) {
        if (srcString == null) {
            return 0;
        } else {
            try {
                return srcString.getBytes(encode).length;
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
                return 0;
            }
        }
    }

    public static String subStringBytes(String strString, int length) {
        if (getByteLength(strString) < length) {
            return strString;
        } else {
            int begin = 0;
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < strString.length(); ++i) {
                char c = strString.charAt(i);
                if (c / 128 == 0) {
                    ++begin;
                } else {
                    begin += 2;
                }

                if (begin > length) {
                    break;
                }

                sb.append(c);
            }

            return sb.toString();
        }
    }

    public static String ISO2GBK(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("ISO-8859-1"), "GBK");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String ISO2BIG(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("ISO-8859-1"), "BIG5");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String ISO2UTF(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String GBK2ISO(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("GBK"), "ISO-8859-1");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String GBK2BIG(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("GBK8"), "BIG5");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String GBK2UTF(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("GBK"), "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String UTF2ISO(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("UTF-8"), "ISO-8859-1");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String UTF2GBK(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("UTF-8"), "GBK");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String UTF2BIG(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("UTF-8"), "BIG5");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String BIG2ISO(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("BIG5"), "ISO-8859-1");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String BIG2UTF(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("BIG5"), "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String BIG2GBK(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return new String(str.getBytes("BIG5"), "GBK");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static String testEncode(String str) throws UnsupportedEncodingException {
        StringBuffer buf = new StringBuffer();
        buf.append("ISO-8859-1 to UTF-8      -->").append(new String(str.getBytes("ISO-8859-1"), "UTF-8")).append("\n");
        buf.append("ISO-8859-1 to GBK        -->").append(new String(str.getBytes("ISO-8859-1"), "GBK")).append("\n");
        buf.append("ISO-8859-1 to GB2312     -->").append(new String(str.getBytes("ISO-8859-1"), "GB2312")).append("\n");
        buf.append("ISO-8859-1 to BIG5       -->").append(new String(str.getBytes("ISO-8859-1"), "BIG5")).append("\n");
        buf.append("GBK        to UTF-8      -->").append(new String(str.getBytes("GBK"), "UTF-8")).append("\n");
        buf.append("GBK        to ISO-8859-1 -->").append(new String(str.getBytes("GBK"), "ISO-8859-1")).append("\n");
        buf.append("GBK        to GB2312     -->").append(new String(str.getBytes("GBK"), "GB2312")).append("\n");
        buf.append("GBK        to BIG5       -->").append(new String(str.getBytes("GBK"), "BIG5")).append("\n");
        buf.append("UTF-8      to ISO-8859-1 -->").append(new String(str.getBytes("UTF-8"), "ISO-8859-1")).append("\n");
        buf.append("UTF-8      to GBK        -->").append(new String(str.getBytes("UTF-8"), "GBK")).append("\n");
        buf.append("UTF-8      to GB2312     -->").append(new String(str.getBytes("UTF-8"), "GB2312")).append("\n");
        buf.append("UTF-8      to BIG5       -->").append(new String(str.getBytes("UTF-8"), "BIG5")).append("\n");
        return buf.toString();
    }

    public static String abbreviate(String str, int maxLength) {
        if (str == null) {
            return "";
        } else {
            int suffixLength = "...".length();
            if (str.getBytes().length < maxLength) {
                return str;
            } else {
                int newLength = maxLength - suffixLength;
                int begin = 0;
                StringBuffer sb = new StringBuffer();

                for(int i = 0; i < str.length(); ++i) {
                    char c = str.charAt(i);
                    sb.append(c);
                    if (c / 128 == 0) {
                        ++begin;
                    } else {
                        begin += 2;
                    }

                    if (begin >= newLength) {
                        break;
                    }
                }

                return sb.append("...").toString();
            }
        }
    }

    public static String upperCaseFirst(String s) {
        return s != null && s.length() >= 1 ? s.substring(0, 1).toUpperCase() + s.substring(1) : s;
    }

    public static String repeat(char ch, int repeat) {
        char[] buf = new char[repeat];

        for(int i = repeat - 1; i >= 0; --i) {
            buf[i] = ch;
        }

        return new String(buf);
    }

    public static String repeat(String str, int repeat) {
        if (str == null) {
            return null;
        } else if (repeat <= 0) {
            return "";
        } else {
            int inputLength = str.length();
            if (repeat != 1 && inputLength != 0) {
                if (inputLength == 1 && repeat <= 8192) {
                    return repeat(str.charAt(0), repeat);
                } else {
                    int outputLength = inputLength * repeat;
                    switch(inputLength) {
                        case 1:
                            return repeat(str.charAt(0), repeat);
                        case 2:
                            char ch0 = str.charAt(0);
                            char ch1 = str.charAt(1);
                            char[] output2 = new char[outputLength];

                            for(int i = repeat * 2 - 2; i >= 0; --i) {
                                output2[i] = ch0;
                                output2[i + 1] = ch1;
                                --i;
                            }

                            return new String(output2);
                        default:
                            StringBuilder buf = new StringBuilder(outputLength);

                            for(int i = 0; i < repeat; ++i) {
                                buf.append(str);
                            }

                            return buf.toString();
                    }
                }
            } else {
                return str;
            }
        }
    }

    public static void main(String[] argv) throws Exception {
        System.out.println(URLEncoder.encode("正在读取文件信息，请稍等...", "GBK"));
        System.out.println(duplicate("te.", 8));
        System.out.println(encodeUnicode("test我们3"));
        System.out.println(getByteLength("ASDF哈哈"));
        System.out.println(stripTrailing("test.haha", "ha"));
        System.out.println(stripLeft("test.haha", "tes"));
        System.out.println(splitLeft("test.haha", "."));
        System.out.println(splitTrailing("test.haha", "."));
        System.out.println(getBoolean("0"));
    }
}
