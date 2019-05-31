package org.tang.wechat.api.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信文字消息类
 * 
 * @author Kevin MOU
 */
public class TextMessage extends Message {
	public static final String MSGTYPE = "text";
	private static final String PKG_CONTENT = "Content";

	private String content;

	public TextMessage() {
		setMsgType(MSGTYPE);
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected void generateMessage(Element root) {
		content = root.element(PKG_CONTENT).getText();
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		content = (String) map.get("text");
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}

}
