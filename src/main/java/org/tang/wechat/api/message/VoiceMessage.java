package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信文字消息类
 * 
 * @author Kevin MOU
 */
public class VoiceMessage extends Message {
	public static final String MSGTYPE = "voice";
	private static final String PKG_MEDIAID = "MediaId";
	private static final String PKG_FORMAT = "Format";
	private static final String PKG_RECOGNITION = "Recognition";

	private String mediaId;
	private String format;
	private String recognition;

	public VoiceMessage() {
		setMsgType(MSGTYPE);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	@Override
	protected void generateMessage(Element root) {
		System.out.println(root.asXML());
		mediaId = root.element(PKG_MEDIAID).getTextTrim();
		format = root.element(PKG_FORMAT).getTextTrim();
		if (null != root.element(PKG_RECOGNITION)) {
			System.out.println(root.element(PKG_RECOGNITION).asXML());
			recognition = root.element(PKG_RECOGNITION).getTextTrim();
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("VoiceMessage[").append(getFromUser());
		buffer.append("]\n\tmediaId: ").append(mediaId);
		buffer.append("\n\tformat:").append(format);
		buffer.append("\n\tRecognition:").append(recognition);
		return buffer.toString();
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		mediaId = "test-media-id";
		format = "test-format";
		recognition = (String) map.get("recognition");
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mediaId", mediaId);
		map.put("format", format);
		map.put("recognition", recognition);
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return recognition;
		}
	}
}
