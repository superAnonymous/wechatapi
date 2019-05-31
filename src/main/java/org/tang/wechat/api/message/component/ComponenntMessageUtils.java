package org.tang.wechat.api.message.component;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author don
 *
 */
public class ComponenntMessageUtils {
	private static Logger logger = LoggerFactory.getLogger(ComponenntMessageUtils.class);
	
	public static BaseComponentMessage parse(Element root) {
		BaseComponentMessage message = null;
		// Create a new Message from wechat server POST
		String infoType = root.elementTextTrim(BaseComponentMessage.XML_INFO_TYPE);
		if (infoType.equals(ComponentMessage.XML_INFO_TYPE)) {
			message = new ComponentMessage();
		}
		else if (infoType.equals(AuthorizerOptionSuccessMessage.XML_INFO_TYPE)) {
			message = new AuthorizerOptionSuccessMessage();
		}
		else if (infoType.equals(AuthorizerOptionUpdateMessage.XML_INFO_TYPE)) {
			message = new AuthorizerOptionUpdateMessage();
		}
		else if (infoType.equals(UnauthorizerOptionMessage.XML_INFO_TYPE)) {
			message = new UnauthorizerOptionMessage();
		} else {
			logger.error("不能识别的消息类型: " + infoType + "\n" + root.asXML());
			return null;
		}
		message.generate(root);
		return message;
	}
}
