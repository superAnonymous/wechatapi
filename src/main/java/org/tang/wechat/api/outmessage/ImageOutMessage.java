package org.tang.wechat.api.outmessage;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class ImageOutMessage extends OutMessage {
	public static String MSGTYPE = "image";
	private String mediaId;
	
	public ImageOutMessage() {
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
		root.addElement(OmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OmUtils.PKG_MSGTYPE).addCDATA(msgType);
		Element imageElement = root.addElement(OmUtils.PKG_IMAGE_CONTENT);
		imageElement.addElement(OmUtils.PKG_IMAGE_MEDIAID).addCDATA(mediaId);
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
