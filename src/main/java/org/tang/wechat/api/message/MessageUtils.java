package org.tang.wechat.api.message;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageUtils {
	private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);

	public static Message parse(Element root) {
		Message message = null;
		// Create a new Message from wechat server POST
		String msgType = root.elementTextTrim(Message.PKG_MSGTYPE);
		if (msgType.equals(TextMessage.MSGTYPE)) {
			message = new TextMessage();
		} else if (msgType.equals(EventMessage.MSGTYPE)) {
			message = new EventMessage();
		} else if (msgType.equals(VoiceMessage.MSGTYPE)) {
			message = new VoiceMessage();
		} else if (msgType.equals(VideoMessage.MSGTYPE)) {
			message = new VideoMessage();
		} else if (msgType.equals(LocationMessage.MSGTYPE)) {
			message = new LocationMessage();
		} else if (msgType.equals(PictureMessage.MSGTYPE)) {
			message = new PictureMessage();
		} else if (msgType.equals(LinkMessage.MSGTYPE)) {
			message = new LinkMessage();
		} else if (msgType.equals(FileMessage.MSGTYPE)) {
			message = new FileMessage();
		} else {
			logger.error("不能识别的消息类型: " + msgType + "\n" + root.asXML());
			return null;
		}
		message.generate(root);
		return message;
	}
}
