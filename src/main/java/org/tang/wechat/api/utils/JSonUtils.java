package org.tang.wechat.api.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.tang.wechat.api.exception.IllegalFormatException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSonUtils {
    public JSonUtils() {
    }

    public static String parse(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mapper.setDateFormat(format);
        return mapper.writeValueAsString(object);
    }

    public static Object read(String json) throws IOException, IllegalFormatException {
        if (json == null) {
            return null;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            if (json.trim().startsWith("[")) {
                return mapper.readValue(json.getBytes("UTF-8"), List.class);
            } else if (json.trim().startsWith("{")) {
                return mapper.readValue(json.getBytes("UTF-8"), Map.class);
            } else if (StringUtils.isEmpty(json)) {
                return new HashMap();
            } else {
                throw new IllegalFormatException("错误的JSON字符串格式:\n" + json);
            }
        }
    }

    public static <T> T read(String json, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
        return (new ObjectMapper()).readValue(json.getBytes("UTF-8"), valueType);
    }

    public static void main(String[] args) throws IOException, IllegalFormatException {
        read("[CODE:1][MESSAGE:{\"errcode\":40037,\"errmsg\":\"invalid template_id\"}]");
    }
}