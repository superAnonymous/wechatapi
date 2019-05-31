package org.tang.wechat.api.model;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tang.wechat.api.utils.RandomUtils;
import org.tang.wechat.api.utils.StringUtils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector extends EntityBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String sourceId;
    private String targetId;
    private String expName;
    private int expMode;
    private String expRef;
    private int tabIndex;
    private String workflowId;

    /* not from database */
    private Node source;
    private Node target;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }


    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public int getExpMode() {
        return expMode;
    }

    public void setExpMode(int expMode) {
        this.expMode = expMode;
    }

    public String getExpRef() {
        return expRef;
    }

    public void setExpRef(String expRef) {
        this.expRef = expRef;
    }

    public Element asElement() {
        Element el = DocumentHelper.createElement("connector");
        el.addAttribute("source", sourceId);
        el.addAttribute("target", targetId);
        el.addAttribute("tabIndex", Integer.toString(tabIndex));
        el.addAttribute("expName", expName==null?"":expName);
        el.addAttribute("expMode", Integer.toString(expMode));
        el.addAttribute("expRef", expRef==null?"":expRef);
        return el;
    }

    public void fromElement(Element el) {
        id = RandomUtils.getGUID();
        sourceId = el.attributeValue("source");
        targetId = el.attributeValue("target");
        tabIndex = StringUtils.getInt(el.attributeValue("tabIndex"), 0);
        expName = el.attributeValue("expName");
        expMode = StringUtils.getInt(el.attributeValue("expMode"), 0);
        expRef = el.attributeValue("expRef");
    }

    @Override
    public void makeBean(ResultSet resultSet) throws SQLException {
        id = resultSet.getString("ID");
        sourceId = resultSet.getString("SOURCEID");
        targetId = resultSet.getString("TARGETID");
        tabIndex = resultSet.getInt("TABINDEX");
        expName = notNullString(resultSet, "EXPNAME");
        expMode = resultSet.getInt("EXPMODE");
        expRef = notNullString(resultSet, "EXPREF");
        workflowId = resultSet.getString("WORKFLOWID");
    }

}
