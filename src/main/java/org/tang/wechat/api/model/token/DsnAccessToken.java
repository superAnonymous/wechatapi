package org.tang.wechat.api.model.token;

import org.apache.commons.codec.digest.DigestUtils;

public class DsnAccessToken extends BasicToken {
	private String appId;
	private String appSecret;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	public String getSign(String sign) {
        String str2 = sign + "&appSecret=" + appSecret;
        return DigestUtils.md5Hex(str2).toLowerCase();
	}

}
