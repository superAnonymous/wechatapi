package org.tang.wechat.api.model;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Workflow extends EntityBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private String version;
    private boolean actived;
    private boolean published;
    private boolean locked;
    private String createdBy;
    private String createdName;
    private Date createdTime;
    private String modifiedBy;
    private Date modifiedTime;

    /* Not from database */
    private Node subscribeResponser, unsubscribeResponser, startResponser, voiceResponser, videoResponser, fileResponser, locationResponser, pictureResponser, linkResponser, scanResponser;
    private Map<String, Node> nodeMap;
    private List<Dataface> datafaces;

    public Workflow() {
        nodeMap = new HashMap<String, Node>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Node getFileResponser() {
        return fileResponser;
    }

    public void setFileResponser(Node fileResponser) {
        this.fileResponser = fileResponser;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Node getSubscribeResponser() {
        return subscribeResponser;
    }

    public void setSubscribeResponser(Node subscribeResponser) {
        this.subscribeResponser = subscribeResponser;
    }

    public Node getUnsubscribeResponser() {
        return unsubscribeResponser;
    }

    public void setUnsubscribeResponser(Node unsubscribeResponser) {
        this.unsubscribeResponser = unsubscribeResponser;
    }

    public Node getStartResponser() {
        return startResponser;
    }

    public void setStartResponser(Node startResponser) {
        this.startResponser = startResponser;
    }

    public Node getVoiceResponser() {
        return voiceResponser;
    }

    public void setVoiceResponser(Node voiceResponser) {
        this.voiceResponser = voiceResponser;
    }

    public Node getVideoResponser() {
        return videoResponser;
    }

    public void setVideoResponser(Node videoResponser) {
        this.videoResponser = videoResponser;
    }

    public Node getLocationResponser() {
        return locationResponser;
    }

    public void setLocationResponser(Node locationResponser) {
        this.locationResponser = locationResponser;
    }

    public Node getPictureResponser() {
        return pictureResponser;
    }

    public void setPictureResponser(Node pictureResponser) {
        this.pictureResponser = pictureResponser;
    }

    public Node getLinkResponser() {
        return linkResponser;
    }

    public void setLinkResponser(Node linkResponser) {
        this.linkResponser = linkResponser;
    }

    public Node getScanResponser() {
        return scanResponser;
    }

    public void setScanResponser(Node scanResponser) {
        this.scanResponser = scanResponser;
    }

    public Map<String, Node> getNodeMap() {
        return this.nodeMap;
    }

    public Collection<Node> getNodes() {
        return nodeMap.values();
    }

    public void addNode(Node node) {
        switch (node.getNodeType()) {
            case Node.TYPE_SUBSCRIBE:
                subscribeResponser = node;
                break;
            case Node.TYPE_START:
                startResponser = node;
                break;
            case Node.TYPE_VOICE:
                voiceResponser = node;
                break;
            case Node.TYPE_VIDEO:
                videoResponser = node;
                break;
            case Node.TYPE_LOCATION:
                locationResponser = node;
                break;
            case Node.TYPE_PICTURE:
                pictureResponser = node;
                break;
            case Node.TYPE_LINK:
                linkResponser = node;
                break;
            case Node.TYPE_SCAN:
                scanResponser = node;
                break;
            case Node.TYPE_FILE:
                fileResponser = node;
                break;
            case Node.TYPE_UNSUBSCRIBE:
                unsubscribeResponser = node;
                break;
            default:
                break;
        }

        node.setWorkflow(this);
        nodeMap.put(node.getId(), node);
    }

    public Node getNode(String id) {
        return nodeMap.get(id);
    }

    public List<Dataface> getDatafaces() {
        return datafaces;
    }

    public void setDatafaces(List<Dataface> datafaces) {
        this.datafaces = datafaces;
    }

    /**
     * 将Workflow对象转换为JSON对象，不适用第三方工具，以避免将nodes和connectors进行转换
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> getJsonMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        map.put("description", notNullString(description));
        map.put("version", notNullString(version));
        map.put("actived", actived);
        map.put("published", published);
        map.put("locked", locked);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("createdBy", createdBy);
        map.put("createdName", createdName);
        map.put("createdTime", sdf.format(createdTime));
        map.put("modifiedBy", modifiedBy);
        map.put("modifiedTime", sdf.format(modifiedTime));
        return map;
    }

    /**
     * Get Dom4j's element from workflow
     *
     * @return Element
     */
    public Element asElement() {
        Element el = DocumentHelper.createElement("workflow");
        el.addAttribute("id", id);
        el.addAttribute("name", name);
        el.addAttribute("desc", description);
        el.addAttribute("ver", version);
        el.addAttribute("createdBy", createdBy);
        el.addAttribute("createdTime", String.valueOf(createdTime.getTime()));
        el.addAttribute("modifiedBy", modifiedBy);
        el.addAttribute("modifiedTime", String.valueOf(modifiedTime.getTime()));
        return el;
    }

    /**
     * Make property from element of Dom4j
     *
     * @param Element
     */
    public void fromElement(Element el) {
        id = el.attributeValue("id");
        name = el.attributeValue("name");
        version = el.attributeValue("ver");
        description = el.attributeValue("desc");
        published = false;
        actived = false;
        locked = false;
        createdBy = el.attributeValue("createdBy");
        long t = Long.parseLong(el.attributeValue("createdTime"));
        createdTime = new Date(t);
        modifiedBy = el.attributeValue("modifiedBy");
        t = Long.parseLong(el.attributeValue("modifiedTime"));
        modifiedTime = new Date(t);
    }

    @Override
    public void makeBean(ResultSet resultSet) throws SQLException {
        id = resultSet.getString("ID");
        name = resultSet.getString("NAME");
        description = notNullString(resultSet, "DESCRIPTION");
        version = notNullString(resultSet, "VERSION");
        actived = resultSet.getBoolean("ACTIVED");
        published = resultSet.getBoolean("PUBLISHED");
        locked = resultSet.getBoolean("LOCKED");
        createdBy = resultSet.getString("CREATEDBY");
        createdName = resultSet.getString("CREATEDNAME");
        createdTime = resultSet.getTimestamp("CREATEDTIME");
        modifiedBy = resultSet.getString("MODIFIEDBY");
        modifiedTime = resultSet.getTimestamp("MODIFIEDTIME");
    }

}
