package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信图片消息类
 * 
 * @author Kevin MOU
 */
public class PictureMessage extends Message {
	public static final String MSGTYPE = "image";
	private static final String PKG_PICURL = "PicUrl";
	private static final String PKG_MEDIAID = "MediaId";

	private String picUrl;
	private String mediaId;

	public PictureMessage() {
		setMsgType(MSGTYPE);
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	protected void generateMessage(Element root) {
		picUrl = root.elementTextTrim(PKG_PICURL);
		mediaId = root.elementTextTrim(PKG_MEDIAID);
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		picUrl = (String) map.get("picUrl");
		mediaId = "test-media-id";
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("picUrl", picUrl);
		map.put("mediaId", mediaId);
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return picUrl;
		}
	}

}
