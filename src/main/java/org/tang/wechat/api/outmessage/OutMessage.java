package org.tang.wechat.api.outmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class OutMessage {
	protected String fromUser;
	protected String toUser;
	protected String msgType;
	protected Date createTime;
	protected String content;

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContentJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(getEntity());
	}

	public String toXmlString() {
		return null;
	}
	
	public String toJsonString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", toUser);
		map.put("msgtype", msgType);
		map.put(msgType, getEntity());
		try {
			return new ObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public abstract Object getEntity();
	
	public abstract boolean available();
	
}
