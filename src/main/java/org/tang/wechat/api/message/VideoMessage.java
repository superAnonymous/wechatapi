package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class VideoMessage extends Message {
	public static final String MSGTYPE = "video";
	private static final String PKG_MEDIAID = "MediaId";
	private static final String PKG_THUMBMEDIAID = "ThumbMediaId";

	private String mediaId;
	private String thumbMediaId;
	private byte[] vedioBytes;
	
	public VideoMessage() {
		setMsgType(MSGTYPE);
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

	public byte[] getVedioBytes() {
		return vedioBytes;
	}

	@Override
	protected void generateMessage(Element root) {
		mediaId = root.element(PKG_MEDIAID).getTextTrim();
		thumbMediaId = root.element(PKG_THUMBMEDIAID).getTextTrim();
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		mediaId = (String) map.get("MediaId");
		thumbMediaId = (String) map.get("ThumbMediaId"); 
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mediaId", mediaId);
		map.put("thumbMediaId", thumbMediaId);
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return mediaId;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("VideoMessage[").append(getFromUser());
		buffer.append("]\n\tmediaId: ").append(mediaId);
		buffer.append("\n\tthumbMediaId:").append(thumbMediaId);
		buffer.append("\n\tmsgType:").append(MSGTYPE);
		return buffer.toString();
	}

}
