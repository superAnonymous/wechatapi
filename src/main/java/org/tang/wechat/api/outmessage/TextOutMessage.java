package org.tang.wechat.api.outmessage;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class TextOutMessage extends OutMessage {
	public static String MSGTYPE = "text";

	public TextOutMessage() {
		this.msgType = MSGTYPE;
	}

	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OmUtils.PKG_MSGTYPE).addCDATA(msgType);
		root.addElement(OmUtils.PKG_TEXT_CONTENT).addCDATA(content);
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}

	@Override
	public boolean available() {
		return ! StringUtils.isEmpty(content);
	}
}
