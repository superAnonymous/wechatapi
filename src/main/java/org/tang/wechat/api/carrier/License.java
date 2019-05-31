package org.tang.wechat.api.carrier;

import java.util.Date;
import java.util.List;

class License {
	private List<String> allows;
	private String userType;
	private String concurrencys;
	private String key;
	private Date signedTime;
	private Date expireTime;

	public List<String> getAllows() {
		return allows;
	}

	public void setAllows(List<String> allows) {
		this.allows = allows;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getSignedTime() {
		return signedTime;
	}

	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	

	public String getConcurrencys() {
		return concurrencys;
	}

	public void setConcurrencys(String concurrencys) {
		this.concurrencys = concurrencys;
	}

	public boolean isExpired() {
		if (expireTime != null) {
			if (expireTime.before(new Date())) {
				return true;
			}
		}

		return false;
	}

	public boolean verify(String url) {
		if ("TRIAL".equalsIgnoreCase(userType)) {
			return true;
		}
		for (String s : allows) {
			if (url.endsWith(s.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
