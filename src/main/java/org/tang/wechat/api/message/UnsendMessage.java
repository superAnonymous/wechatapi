package org.tang.wechat.api.message;

public class UnsendMessage {
	private String fromUser;
	private String msgType;
	private String content;
	
	
	/**
	 * @param fromUser
	 * @param msgType
	 * @param content
	 */
	public UnsendMessage(String fromUser, String msgType, String content) {
		this.fromUser = fromUser;
		this.msgType = msgType;
		this.content = content;
	}
	
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
