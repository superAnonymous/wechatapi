package org.tang.wechat.api.outmessage;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class MCSOutMessage extends OutMessage {
	public static String MSGTYPE = "transfer_customer_service";

	public MCSOutMessage() {
		this.msgType = MSGTYPE;
	}

	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OmUtils.PKG_MSGTYPE).addCDATA(msgType);
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	@Override
	public boolean available() {
		return true;
	}
}
