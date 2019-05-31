package org.tang.wechat.api.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.tang.wechat.api.exception.IllegalFormatException;

public class DSUtils {
    private static final String defaultCharestName = "UTF-8";

    public DSUtils() {
    }

    public static Object parserJson(String json) throws IOException, IllegalFormatException {
        return JSonUtils.read(json);
    }

    public static Element parserXml(String buffer) throws UnsupportedEncodingException, DocumentException {
        return parserXml(buffer, "UTF-8");
    }

    public static Element parserXml(String buffer, String charsetName) throws UnsupportedEncodingException, DocumentException {
        if (StringUtils.isEmpty(buffer)) {
            return null;
        } else {
            InputStream in = new ByteArrayInputStream(buffer.getBytes(charsetName));
            SAXReader reader = new SAXReader();
            return reader.read(in).getRootElement();
        }
    }

    public static Object getObjectFromXML(String xmlStr) throws Exception {
        return getObjectFromXML(xmlStr, "UTF-8");
    }

    public static Object getObjectFromXML(String xmlStr, String charsetName) throws Exception {
        if (xmlStr == null) {
            return null;
        } else {
            Element element = parserXml(xmlStr, charsetName);
            return getObjectFromDom(element);
        }
    }

    public static Object getObjectFromDom(Element element) {
        Boolean isSeq = false;
        Map<String, Object> map = new HashMap();
        if (element == null) {
            return map;
        } else {
            Iterator var4 = element.attributes().iterator();

            while(var4.hasNext()) {
                Attribute attribute = (Attribute)var4.next();
                map.put(attribute.getName(), attribute.getValue());
            }

            if (map.containsKey("type")) {
                isSeq = ((String)map.get("type")).equalsIgnoreCase("list");
            }

            List<Element> childList = element.elements();
            Iterator var5 = childList.iterator();

            while(var5.hasNext()) {
                Element child = (Element)var5.next();
                String name = child.getName();
                if (map.containsKey(name)) {
                    Object obj = map.get(name);
                    if (obj instanceof List) {
                        ((List)obj).add(getObjectFromDom(child));
                    } else if (obj instanceof Map) {
                        List<Object> list = new ArrayList();
                        list.add(obj);
                        list.add(getObjectFromDom(child));
                        map.put(name, list);
                    }
                } else if (isSeq) {
                    List<Object> list = new ArrayList();
                    list.add(getObjectFromDom(child));
                    map.put(name, list);
                } else {
                    map.put(name, getObjectFromDom(child));
                }
            }

            if (childList.size() == 0) {
                if (map.size() == 0) {
                    return element.getText();
                }

                if (!isSeq) {
                    map.put("text", element.getText());
                }
            }

            return map;
        }
    }

    public static Object getWebServiceEntity(Element element) {
        List<Element> children = element.elements();
        if (children.size() == 0) {
            return element.getText();
        } else {
            Map<String, Object> map = new HashMap();
            Iterator var4 = children.iterator();

            while(var4.hasNext()) {
                Element child = (Element)var4.next();
                String name = child.getName();
                if (map.containsKey(name)) {
                    Object obj = map.get(name);
                    if (obj instanceof List) {
                        ((List)obj).add(getWebServiceEntity(child));
                    } else {
                        List<Object> list = new ArrayList();
                        list.add(obj);
                        list.add(getWebServiceEntity(child));
                        map.put(name, list);
                    }
                } else {
                    map.put(name, getWebServiceEntity(child));
                }
            }

            return map;
        }
    }

    public static String verboseMap(Map<?, ?> map) {
        return verboseMap(map, 0);
    }

    private static String verboseMap(Map<?, ?> map, int indit) {
        Iterator<?> iter = map.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        while(iter.hasNext()) {
            Entry<?, ?> e = (Entry)iter.next();
            Object key = e.getKey();
            Object value = e.getValue();
            sb.append(StringUtils.repeat("\t", indit + 1)).append(key == map ? "(this Map)" : key);
            sb.append(" : ");
            sb.append(value == map ? "(this Map)" : printValue(value, indit + 1));
            sb.append(StringUtils.repeat("\t", indit + 1)).append("\n");
        }

        sb.append(StringUtils.repeat("\t", indit)).append("}\n");
        return sb.toString();
    }

    private static String printValue(Object value, int indit) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Map) {
            return verboseMap((Map)value, indit);
        } else {
            return value instanceof List ? verboseList((List)value, indit) : value.toString();
        }
    }

    private static String verboseList(List<?> list, int indit) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            Object item = var4.next();
            sb.append(StringUtils.repeat("\t", indit + 1));
            sb.append(printValue(item, indit + 1));
        }

        sb.append(StringUtils.repeat("\t", indit + 1)).append("]");
        return sb.toString();
    }
}
