package org.tang.wechat.api.outmessage.enterprise;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tang.wechat.api.outmessage.OmUtils;

import java.util.HashMap;
import java.util.Map;

public class FileOutMessage extends OutEnterpriseMessage {
	public static String MSGTYPE = "file";
	private String msgId;
	
	public FileOutMessage() {
		this.msgType = MSGTYPE;
	}
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	
	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OetpmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OetpmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OetpmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OetpmUtils.PKG_MSGTYPE).addCDATA(msgType);
		Element imageElement = root.addElement(OetpmUtils.PKG_FILE_CONTENT);
		imageElement.addElement(OetpmUtils.PKG_FILE_MEDIAID).addCDATA(msgId);
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(OmUtils.PKG_FILE_MEDIAID, msgId);
		return map;
	}

	@Override
	public boolean available() {
		return ! StringUtils.isEmpty(msgId);
	}


}
