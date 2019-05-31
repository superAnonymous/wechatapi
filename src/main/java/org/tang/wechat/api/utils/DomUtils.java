package org.tang.wechat.api.utils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomUtils {
    private static final Logger logger = LoggerFactory.getLogger(DomUtils.class);

    public DomUtils() {
    }

    public static Document parser(URL url) throws IOException {
        if (url == null) {
            return null;
        } else {
            InputStream in = url.openStream();
            return parser(in);
        }
    }

    public static Document parser(InputStream in) throws IOException {
        if (in == null) {
            return null;
        } else {
            try {
                SAXReader sr = new SAXReader();
                Document document = sr.read(in);
                return document;
            } catch (DocumentException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static Document parser(String strXML) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(strXML.getBytes());
        Document document = parser((InputStream)inputStream);

        try {
            inputStream.close();
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
        }

        return document;
    }

    public static Document parser(String strXML, Charset charset) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(strXML.getBytes(charset));
        Document document = parser((InputStream)inputStream);

        try {
            inputStream.close();
        } catch (Exception var5) {
            logger.error(var5.getMessage(), var5);
        }

        return document;
    }

    public static Document parser(String strXML, String charsetName) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(strXML.getBytes(charsetName));
        Document document = parser((InputStream)inputStream);

        try {
            inputStream.close();
        } catch (Exception var5) {
            logger.error(var5.getMessage(), var5);
        }

        return document;
    }

    public static String prettyPrint(Document document) {
        try {
            StringWriter out = new StringWriter();
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
            String result = out.getBuffer().toString();
            out.close();
            return result;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String prettyPrint(Element element) {
        try {
            StringWriter out = new StringWriter();
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(element);
            writer.close();
            String result = out.getBuffer().toString();
            out.close();
            return result;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static void write(Document document, OutputStream os) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter xmlWriter = new XMLWriter(os, format);
        xmlWriter.write(document);
        xmlWriter.close();
    }

    public static void write(Document document, Writer writer) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        xmlWriter.write(document);
        xmlWriter.close();
    }
}

