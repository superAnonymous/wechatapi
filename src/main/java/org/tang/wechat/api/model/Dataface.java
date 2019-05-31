package org.tang.wechat.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tang.wechat.api.utils.DSUtils;
import org.tang.wechat.api.utils.RandomUtils;
import org.tang.wechat.api.utils.StringUtils;

public class Dataface extends EntityBean {
    private static Logger logger = LoggerFactory.getLogger(Dataface.class);

    public static final int TYPE_SQL = 0;
    public static final int TYPE_XHR_GET = 1;
    public static final int TYPE_XHR_POST = 2;
    public static final int TYPE_WS = 3;

    private String id;
    private String name;
    private int type;
    private int proxy;
    private String content;
    private int result;
    private String workflowId;

    /* Not from database */
    private List<DatafaceParam> params;

    public Dataface() {
        params = new ArrayList<DatafaceParam>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public List<DatafaceParam> getParams() {
        return params;
    }

    public void setParams(List<DatafaceParam> params) {
        this.params = params;
    }

    public int getProxy() {
        return proxy;
    }

    public void setProxy(int proxy) {
        this.proxy = proxy;
    }

    /**
     * @return
     */
    public String makeParamsAsXML() {
        if (params == null || params.size() == 0) {
            return "";
        }
        return makeParamsAsElement().asXML();
    }

    public Element makeParamsAsElement() {
        Element element = DocumentHelper.createElement("params");
        for (DatafaceParam param : params) {
            Element item = element.addElement("param");
            item.addAttribute("name", param.getName());
            item.addCDATA(param.getValue());
        }
        return element;
    }

    public void makeParamsFromElement(Element element) {
        params.clear();
        for (Element el : element.elements("param")) {
            String name = el.attributeValue("name");
            String value = el.getTextTrim();
            if (StringUtils.isEmpty(name)) {
                logger.error("Cann't make dataface parameter with empty name, DATAFACE=" + name);
            } else {
                DatafaceParam dp = new DatafaceParam();
                dp.setName(name);
                dp.setValue(value);
                params.add(dp);
            }
        }
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Element asElement() {
        Element el = DocumentHelper.createElement("dataface");
        el.addAttribute("name", name);
        el.addAttribute("type", String.valueOf(type));
        el.addAttribute("proxy", String.valueOf(proxy));
        el.addCDATA(notNullString(content));
        el.addAttribute("result", String.valueOf(result));
        el.add(makeParamsAsElement());
        return el;
    }

    public void fromElement(Element el) {
        id = RandomUtils.getGUID();
        type = StringUtils.getInt(el.attributeValue("type"), 0);
        proxy = StringUtils.getInt(el.attributeValue("proxy"), 0);
        name = el.attributeValue("name");
        makeParamsFromElement(el.element("params"));
        content = el.getTextTrim();
        result = StringUtils.getInt(el.attributeValue("result"), 0);
    }

    @Override
    public void makeBean(ResultSet resultSet) throws SQLException {
        id = resultSet.getString("ID");
        name = resultSet.getString("NAME");
        type = resultSet.getInt("TYPE");
        proxy = resultSet.getInt("proxy");
        content = notNullString(resultSet, "CONTENT");
        result = resultSet.getInt("RESULT");
        workflowId = resultSet.getString("WORKFLOWID");
        String xml = notNullString(resultSet, "PARAMS");

        params.clear();
        if (!StringUtils.isEmpty(xml)) {
            try {
                Element root = DSUtils.parserXml(xml);
                makeParamsFromElement(root);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
