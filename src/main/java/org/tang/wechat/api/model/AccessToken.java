package org.tang.wechat.api.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 微信接口token对象<br/>
 * 用于和官方接口进行握手认证。
 * 
 * @author
 */
public class AccessToken extends EntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String epid;
	private String appId;
	private String appSecret;
	private int type;
	private String token;
	private int expires;
	private Date createdTime;
	private boolean locked;
	private long lockedTime;

	public String getEpid() {
		return epid;
	}

	public void setEpid(String epid) {
		this.epid = epid;
	}

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpires() {
		return expires;
	}

	public void setExpires(int expires) {
		this.expires = expires;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public long getLockedTime() {
		return lockedTime;
	}

	public void setLockedTime(long lockedTime) {
		this.lockedTime = lockedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 判断当前token是否已经失效，在既定失效时间前的120秒主动失效。
	 * 
	 * @return true为当前token已经失效，false为有效
	 */
	public boolean expired() {
		if(createdTime == null ) {
			return false;
		}
		//System.out.println("***************[ "+createdTime.getTime()+" ]********************[ "+createdTime+" ]****************************************************");
		//System.out.println("***************[ "+new Date().getTime()+" ]************************************************************************");
		boolean isValue = ((System.currentTimeMillis() - createdTime.getTime()) / 1000 + 120) >= expires; 
		//System.out.println("[ token expired： ] "+isValue);
		return isValue;
	}
	
	public long leftTime() {
		return expires - (System.currentTimeMillis() - createdTime.getTime())/1000;
	}

	@Override
	public String toString() {
		return "[Expires in: " + leftTime() + ", Token: " + token + "]";
	}

	@Override
	public void makeBean(ResultSet resultSet) throws SQLException {
		epid = resultSet.getString("EPID");
		token = resultSet.getString("TOKEN");
		createdTime = resultSet.getTimestamp("CREATEDTIME");
		expires = resultSet.getInt("EXPIRED");
		locked = resultSet.getBoolean("LOCKED");
		lockedTime = resultSet.getLong("LOCKEDTIME");
	}

}
