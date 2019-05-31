package org.tang.wechat.api.outmessage.enterprise;

import java.util.HashMap;
import java.util.Map;

public class EmptyOutMessage extends OutEnterpriseMessage {
	public static String MSGTYPE = "empty";

	public EmptyOutMessage() {
		this.msgType = MSGTYPE;
	}

	@Override
	public String toXmlString() {
		return "<xml></xml>";
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
