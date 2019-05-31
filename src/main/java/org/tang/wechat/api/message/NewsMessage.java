package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class NewsMessage extends Message {
	public static final String MSGTYPE = "news";
	private static final String PKG_ARTICLE_COUNT = "ArticleCount";

	private int articleCount;
	public NewsMessage() {
		setMsgType(MSGTYPE);
	}
	

	public int getArticleCount() {
		return articleCount;
	}


	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}


	@Override
	protected void generateMessage(Element root) {
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleCount", articleCount);
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("NewsMessage[").append(getFromUser());
		buffer.append("\n\tmsgType:").append(MSGTYPE);
		return buffer.toString();
	}

}
