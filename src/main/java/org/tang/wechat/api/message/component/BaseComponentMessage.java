package org.tang.wechat.api.message.component;

import org.dom4j.Element;

import java.util.Date;

public abstract class BaseComponentMessage {
	static final String XML_INFO_TYPE = "InfoType";
	static final String XML_APPID = "AppId";
	static final String XML_CREATE_TIME = "CreateTime";
	
	private Element root;
	private String appid;
	private Date createTime; 
	private String infoType;
	private Date postTime;
	
	
	public Element getRoot() {
		return root;
	}
	public void setRoot(Element root) {
		this.root = root;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	/**
	 * 根据XML报文，初始化赋值Message对象
		private String appid;
		private Date createTime; 
		private String infoType;
		private String epid;
	 * @param root
	 */
	public void generate(Element root) {
		this.root = root;
		// System.out.println(root.asXML());
		appid = root.element(XML_APPID).getTextTrim();
		long time = Long.parseLong(root.element(XML_CREATE_TIME).getTextTrim()) * 1000L;
		createTime = new Date(time);
		postTime = new Date();
		generateMessage(root);
	}
	
	/**
	 * 
	 */
	protected abstract void generateMessage(Element root);
}
