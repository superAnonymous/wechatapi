package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信链接消息类
 * 
 * @author Kevin MOU
 */
public class LinkMessage extends Message {
	private final static Logger logger = LoggerFactory.getLogger(LinkMessage.class);
	public static final String MSGTYPE = "link";
	private static final String PKG_TITLE = "Title";
	private static final String PKG_DESCRIPTION = "Description";
	private static final String PKG_URL = "Url";

	private String title;
	private String description;
	private String url;

	public LinkMessage() {
		setMsgType(MSGTYPE);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescrition(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	protected void generateMessage(Element root) {
		title = root.element(PKG_TITLE).getTextTrim();
		description = root.element(PKG_DESCRIPTION).getTextTrim();
		url = root.element(PKG_URL).getTextTrim();
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		url = (String) map.get("url");
		description = (String) map.get("description");
		title =  (String) map.get("title");
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		map.put("title", title);
		map.put("description", description);
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return url;
		}
	}

}
