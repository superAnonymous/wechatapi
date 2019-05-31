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
public class FileMessage extends Message {
	public static final String MSGTYPE = "file";
	private static final String PKG_MEDIAID = "MsgId";
	private static final String PKG_TITLE= "Title";
	private static final String PKG_DESCRIPTION= "Description";
	private static final String PKG_FILEKEY = "FileKey";
	private static final String PKG_FILEMD5 = "FileMd5";
	private static final String PKG_FILETOTALLEN = "FileTotalLen";

	private String title;
	private String description;
	private String fileKey;
	private String fileMd5;
	private String fileTotalLen;

	public FileMessage() {
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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getFileTotalLen() {
		return fileTotalLen;
	}

	public void setFileTotalLen(String fileTotalLen) {
		this.fileTotalLen = fileTotalLen;
	}

	@Override
	protected void generateMessage(Element root) {
		title = root.element(PKG_TITLE).getTextTrim();
		description = root.element(PKG_DESCRIPTION).getTextTrim();
		fileKey = root.element(PKG_FILEKEY).getTextTrim();
		fileMd5 = root.element(PKG_FILEMD5).getTextTrim();
		fileTotalLen = root.element(PKG_FILETOTALLEN).getTextTrim();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("FileMessage[").append(getFromUser());
		buffer.append("\n\ttitle:").append(title);
		buffer.append("\n\tdescription:").append(description);
		buffer.append("\n\tfileKey:").append(fileKey);
		buffer.append("\n\tfileMd5:").append(fileMd5);
		buffer.append("\n\tfileTotalLen:").append(fileTotalLen);
		return buffer.toString();
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		title = "test-title";
		description = "test-description";
		fileKey = "test-fileKey";
		fileMd5 = "test-fileMd5";
		fileTotalLen = "100";
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("description", description);
		map.put("fileKey", fileKey);
		map.put("fileMd5", fileMd5);
		map.put("fileTotalLen", fileTotalLen);
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return title;
		}
	}
}
