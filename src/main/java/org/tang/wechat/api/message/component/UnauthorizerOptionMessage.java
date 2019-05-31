package org.tang.wechat.api.message.component;

import org.dom4j.Element;

/**
 * 取消授权
 * @author Administrator
 *
 */
public class UnauthorizerOptionMessage extends BaseComponentMessage {
	public static final String XML_INFO_TYPE = "unauthorized";
	public static final String AUTHORIZER_APPID = "AuthorizerAppid";
	
	private String authorizerAppid;
	
	public UnauthorizerOptionMessage() {
		setInfoType(XML_INFO_TYPE);
	}

	public String getAuthorizerAppid() {
		return authorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}

	@Override
	protected void generateMessage(Element root) {
		authorizerAppid = root.element(AUTHORIZER_APPID).getTextTrim();
		
	}
}
