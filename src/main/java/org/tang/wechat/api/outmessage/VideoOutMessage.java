package org.tang.wechat.api.outmessage;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class VideoOutMessage extends OutMessage {
	public static String MSGTYPE = "video";
	private String mediaId;
	private String thumbMediaId;
	
	public VideoOutMessage() {
		this.msgType = MSGTYPE;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OmUtils.PKG_MSGTYPE).addCDATA(msgType);
		Element imageElement = root.addElement(OmUtils.PKG_VIDEO_CONTENT);
		imageElement.addElement(OmUtils.PKG_VIDEO_MEDIAID).addCDATA(mediaId);
		imageElement.addElement(OmUtils.PKG_VIDEO_THUMB).addCDATA(thumbMediaId);
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("media_id", mediaId);
		map.put("thumb_media_id", thumbMediaId);
		return map;
	}

	@Override
	public boolean available() {
		return ! StringUtils.isEmpty(mediaId);
	}


}
