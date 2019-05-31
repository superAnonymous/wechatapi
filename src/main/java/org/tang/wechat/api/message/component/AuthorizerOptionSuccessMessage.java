package org.tang.wechat.api.message.component;

import org.dom4j.Element;

/**
 * 
 * @author don
 * 授权成功
 *
 */
public class AuthorizerOptionSuccessMessage extends BaseComponentMessage {
	public static final String XML_INFO_TYPE = "authorized";
	private static final String XML_AUTHORIZER_APPID = "AuthorizerAppid";
	private static final String XML_AUTHORIZER_CODE = "AuthorizationCode";
	private static final String XML_AUTHORIZER_CODE_EXPRICE_TIME = "AuthorizationCodeExpiredTime";
	private static final String XML_PRE_AUTH_CODE = "PreAuthCode";
	
	private String authorizerAppid;
	private String authorizerCode;
	private String authorizerCodeExpiredTime;
	private String preAuthCode;
	
	public AuthorizerOptionSuccessMessage() {
		setInfoType(XML_INFO_TYPE);
	}
	
	public String getAuthorizerAppid() {
		return authorizerAppid;
	}
	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}
	public String getAuthorizerCode() {
		return authorizerCode;
	}
	public void setAuthorizerCode(String authorizerCode) {
		this.authorizerCode = authorizerCode;
	}
	public String getAuthorizerCodeExpiredTime() {
		return authorizerCodeExpiredTime;
	}
	public void setAuthorizerCodeExpiredTime(String authorizerCodeExpiredTime) {
		this.authorizerCodeExpiredTime = authorizerCodeExpiredTime;
	}
	public String getPreAuthCode() {
		return preAuthCode;
	}
	public void setPreAuthCode(String preAuthCode) {
		this.preAuthCode = preAuthCode;
	}

	@Override
	protected void generateMessage(Element root) {
		authorizerAppid = root.element(XML_AUTHORIZER_APPID).getTextTrim();
		authorizerCode = root.element(XML_AUTHORIZER_CODE).getTextTrim();
		authorizerCodeExpiredTime = root.element(XML_AUTHORIZER_CODE_EXPRICE_TIME).getTextTrim();
		preAuthCode = root.element(XML_PRE_AUTH_CODE).getTextTrim();
	}
	
}
