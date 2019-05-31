package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Element;

import java.util.Date;
import java.util.Map;

/**
 * 微信用户上传消息基础类
 * 
 * @author Kevin MOU
 */
public abstract class Message {
	public static final String PKG_TOUSER = "ToUserName";
	public static final String PKG_FROMUSER = "FromUserName";
	public static final String PKG_CREATETIME = "CreateTime";
	public static final String PKG_MSGID = "MsgId";
	public static final String PKG_MSGTYPE = "MsgType";
	public static final String PKG_AGENTID = "AgentID";

	private Element root;
	private String toUser;
	private String fromUser;
	private Date createTime;
	private String msgType;
	private String msgId;
	private Date postTime;
	private int agentId;

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	/**
	 * 根据XML报文，初始化赋值Message对象
	 * 
	 * @param root
	 */
	public void generate(Element root) {
		this.root = root;
		// System.out.println(root.asXML());
		toUser = root.element(PKG_TOUSER).getTextTrim();
		fromUser = root.element(PKG_FROMUSER).getTextTrim();
		agentId = root.numberValueOf(PKG_AGENTID).intValue();
		long time = Long.parseLong(root.element(PKG_CREATETIME).getTextTrim()) * 1000L;
		createTime = new Date(time);
		Element el = root.element(PKG_MSGID);
		if (el != null) {
			msgId = el.getTextTrim();
		}
		postTime = new Date();
		generateMessage(root);
	}

	/**
	 * 创建一个用于测试的消息
	 * 
	 * @param testerId
	 *            测试模拟器ID
	 * @param jsonMsg
	 *            测试用消息JSON对象
	 */
	public void generateTest(String testerId, String jsonMsg) {
		toUser = "testServer";
		fromUser = testerId;
		createTime = postTime = new Date();
		msgId = "test-msg-id";
		try {
			generateTestMessage(jsonMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	protected abstract void generateMessage(Element root);

	/**
	 * 
	 */
	protected abstract void generateTestMessage(String jsonMsg) throws Exception;

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[" + getFromUser() + "]: " + getContent();
	}

	public String getContentJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(getContentMap());
	}

	/**
	 * 
	 */
	public abstract Map<String, Object> getContentMap();

	/**
	 * 
	 */
	public abstract String getContent();

	public Element getRoot() {
		return root;
	}
	
}
