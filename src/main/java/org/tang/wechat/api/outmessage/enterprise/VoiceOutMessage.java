package org.tang.wechat.api.outmessage.enterprise;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class VoiceOutMessage extends OutEnterpriseMessage {
	public static String MSGTYPE = "voice";
	private String mediaId;
	
	public VoiceOutMessage() {
		this.msgType = MSGTYPE;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OetpmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OetpmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OetpmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OetpmUtils.PKG_MSGTYPE).addCDATA(msgType);
		Element imageElement = root.addElement(OetpmUtils.PKG_VOICE_CONTENT);
		imageElement.addElement(OetpmUtils.PKG_VOICE_MEDIAID).addCDATA(mediaId);
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("media_id", mediaId);
		return map;
	}

	@Override
	public boolean available() {
		return ! StringUtils.isEmpty(mediaId);
	}


}
