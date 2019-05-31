package org.tang.wechat.api.model;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.util.HtmlUtils;
import org.tang.wechat.api.utils.StringUtils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node extends EntityBean implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int TYPE_NODE = 0;
    public static final int TYPE_START = 1;
    public static final int TYPE_SUBSCRIBE = 2;
    public static final int TYPE_VOICE = 3;
    public static final int TYPE_VIDEO = 4;
    public static final int TYPE_LOCATION = 5;
    public static final int TYPE_PICTURE = 6;
    public static final int TYPE_LINK = 7;
    public static final int TYPE_SCAN = 8;
    public static final int TYPE_FILE = 9;
    public static final int TYPE_FKEY = 90;
    public static final int TYPE_SWITCH = 91;
    public static final int TYPE_UNSUBSCRIBE = 99;

    private String id;
    private String name;
    private String keywords;
    private String eventKey;
    private String quote;
    private String content;
    private String msgType;
    private String workflowId;
    private int nodeType;
    private int x;
    private int y;
    private String foreignKey;
    private int control;
    private String ctlTip;
    private String ctlName;
    private boolean ctlForce;
    private int flag = 1;
    private boolean allowed;
    private boolean followed;
    private boolean visible;

    /* Not from database */
    /*
     * All child Connectors
     */
    private List<Connector> connectors;
    /*
     * All child Nodes
     */
    private List<Node> children;
    /*
     * Belong workflow
     */
    private Workflow workflow;

    public Node() {
        x = y = 0;
        control = 0;
        ctlTip = ctlName = "";
        ctlForce = false;
        keywords = eventKey = foreignKey = "";
        id = workflowId = name = "";
        allowed = false;
        followed = false;
        connectors = new ArrayList<Connector>();
        children = new ArrayList<Node>();
    }

    public boolean hasChild() {
        return children.size() > 0;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getChildById(String id) {
        for (Node child : children) {
            if (child.getId().equals(id)) {
                return child;
            }
        }

        return null;
    }

    /**
     * 获得第一个子节点，通常用于wic或者step操作
     *
     * @return
     */
    public Node getFirstChild() {
        if (children.size() > 0) {
            return children.get(0);
        }
        return null;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    /**
     * 增加一个子链路到当前节点
     *
     * @param connector
     */
    public void addConnector(Connector connector) {
        connectors.add(connector);
    }

    /**
     * Get connector by GUID
     *
     * @param id
     *            GUID string with 16 length
     * @return Connector
     */
    public Connector getConnectorById(String id) {
        for (Connector connector : connectors) {
            if (connector.getId().equals(id)) {
                return connector;
            }
        }
        return null;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
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

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public String getCtlTip() {
        return ctlTip;
    }

    public void setCtlTip(String ctlTip) {
        this.ctlTip = ctlTip;
    }

    public String getCtlName() {
        return ctlName;
    }

    public void setCtlName(String ctlName) {
        this.ctlName = ctlName;
    }

    public boolean isCtlForce() {
        return ctlForce;
    }

    public void setCtlForce(boolean ctlForce) {
        this.ctlForce = ctlForce;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    /**
     * 0 = removed, 1 = normal, 2 = new
     *
     * @return
     */
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    @Override
    public String toString() {
        return "[ID=" + id + ", NAME=" + name + "]";
    }

    public Map<String, Object> getJsonMap() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", id);
        model.put("name", name);
        model.put("keywords", notNullString(keywords));
        model.put("eventKey", notNullString(eventKey));
        model.put("msgType", msgType);
        model.put("quote", notNullString(quote));
        model.put("content", notNullString(content));
        model.put("nodeType", nodeType);
        model.put("workflowId", workflowId);
        model.put("x", x);
        model.put("y", y);
        model.put("control", control);
        model.put("ctlTip", notNullString(ctlTip));
        model.put("ctlName", notNullString(ctlName));
        model.put("ctlForce", ctlForce);
        model.put("foreignKey", notNullString(foreignKey));
        model.put("allowed", allowed);
        model.put("followed", followed);
        model.put("visible", visible);
        /**
         * 0 = removed, 1 = normal, 2 = new
         */
        model.put("flag", flag);
        return model;
    }

    public Element asElement() {
        Element el = DocumentHelper.createElement("node");
        el.addAttribute("id", id);
        el.addAttribute("name", notNullString(name));
        el.addAttribute("keywords", notNullString(keywords));
        el.addAttribute("eventKey", notNullString(eventKey));
        el.addAttribute("msgType", notNullString(msgType));
        el.addAttribute("x", String.valueOf(x));
        el.addAttribute("y", String.valueOf(y));
        el.addAttribute("nodeType", String.valueOf(nodeType));
        el.addAttribute("control", String.valueOf(control));
        el.addAttribute("ctlTip", notNullString(ctlTip));
        el.addAttribute("ctlName", notNullString(ctlName));
        el.addAttribute("ctlForce", ctlForce ? "1" : "0");
        el.addAttribute("foreignKey", notNullString(foreignKey).trim());
        el.addAttribute("allowed", allowed ? "1" : "0");
        el.addAttribute("followed", followed ? "1" : "0");
        el.addAttribute("visible", visible ? "1" : "0");
        el.addAttribute("quote", notNullString(quote));
        Element quoteEl = el.addElement("quote");
        quoteEl.addCDATA(HtmlUtils.htmlEscape(notNullString(quote)));
        Element contentEl = el.addElement("content");
        contentEl.addCDATA(HtmlUtils.htmlEscape(notNullString(content)));
        return el;
    }

    public void fromElement(Element el) {
        id = el.attributeValue("id");
        name = el.attributeValue("name");
        keywords = el.attributeValue("keywords");
        msgType = el.attributeValue("msgType");
        eventKey = el.attributeValue("eventKey");
        x = StringUtils.getInt(el.attributeValue("x"));
        y = StringUtils.getInt(el.attributeValue("y"));
        nodeType = StringUtils.getInt(el.attributeValue("nodeType"));
        control = StringUtils.getInt(el.attributeValue("control"));
        ctlName = el.attributeValue("ctlName");
        ctlTip = el.attributeValue("ctlTip");
        ctlForce = StringUtils.getBoolean(el.attributeValue("ctlForce"));
        foreignKey = el.attributeValue("foreignKey");
        allowed = StringUtils.getBoolean(el.attributeValue("allowed"));
        followed = StringUtils.getBoolean(el.attributeValue("followed"));
        visible = StringUtils.getBoolean(el.attributeValue("visible"));
        quote = HtmlUtils.htmlUnescape(el.element("quote").getTextTrim());
        content = HtmlUtils.htmlUnescape(el.element("content").getTextTrim());
    }

    @Override
    public void makeBean(ResultSet resultSet) throws SQLException {
        id = resultSet.getString("ID");
        name = resultSet.getString("NAME");
        keywords = notNullString(resultSet, "KEYWORDS");
        eventKey = notNullString(resultSet, "EVENTKEY");
        msgType = resultSet.getString("MSGTYPE");
        quote = notNullString(resultSet.getString("QUOTE"));
        content = notNullString(resultSet, "CONTENT");
        workflowId = resultSet.getString("WORKFLOWID");
        nodeType = resultSet.getInt("NODETYPE");
        x = resultSet.getInt("X");
        y = resultSet.getInt("Y");
        control = resultSet.getInt("CONTROL");
        ctlName = notNullString(resultSet, "CTLNAME");
        ctlTip = notNullString(resultSet, "CTLTIP");
        ctlForce = resultSet.getBoolean("CTLFORCE");
        foreignKey = notNullString(resultSet, "FOREIGNKEY");
        allowed = resultSet.getBoolean("ALLOWED");
        followed = resultSet.getBoolean("FOLLOWED");
        visible = resultSet.getBoolean("VISIBLE");
    }

}
