package org.tang.wechat.api.outmessage;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tang.wechat.api.outmessage.enterprise.OetpmUtils;

import java.util.HashMap;
import java.util.Map;

public class FileOutMessage extends OutMessage {
	public static String MSGTYPE = "file";
	private String msgId;
	private String title;
	private String description;
	private String fileKey;
	private String fileMd5;
	private String fileTotalLen;
	
	public FileOutMessage() {
		this.msgType = MSGTYPE;
	}
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
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
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OetpmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OetpmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OetpmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OetpmUtils.PKG_MSGTYPE).addCDATA(msgType);
		Element imageElement = root.addElement(OetpmUtils.PKG_FILE_CONTENT);
		imageElement.addElement(OetpmUtils.PKG_FILE_MEDIAID).addCDATA(msgId);
		imageElement.addElement(OetpmUtils.PKG_FILE_TITLE).addCDATA(title);
		imageElement.addElement(OetpmUtils.PKG_FILE_DESCRIPTION).addCDATA(description);
		imageElement.addElement(OetpmUtils.PKG_FILE_KEY).addCDATA(fileKey);
		imageElement.addElement(OetpmUtils.PKG_FILE_MD5).addCDATA(fileMd5);
		imageElement.addElement(OetpmUtils.PKG_FILE_LEN).addCDATA(fileTotalLen);
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg_id", msgId);
		map.put("title", title);
		map.put("description", description);
		map.put("fileKey", fileKey);
		map.put("fileMd5", fileMd5);
		map.put("fileTotalLen", fileTotalLen);
		return map;
	}

	@Override
	public boolean available() {
		return ! StringUtils.isEmpty(msgId);
	}


}
