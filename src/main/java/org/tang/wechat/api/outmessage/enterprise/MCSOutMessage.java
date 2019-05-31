package org.tang.wechat.api.outmessage.enterprise;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class MCSOutMessage extends OutEnterpriseMessage {
	public static String MSGTYPE = "transfer_customer_service";

	public MCSOutMessage() {
		this.msgType = MSGTYPE;
	}

	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OetpmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OetpmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OetpmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OetpmUtils.PKG_MSGTYPE).addCDATA(msgType);
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
