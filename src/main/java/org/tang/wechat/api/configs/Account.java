package org.tang.wechat.api.configs;

public class Account {
    public static final int TYPE_CUSSV = 0;
    public static final int TYPE_SUBS = 1;
    public static final int TYPE_QY = 2;

    private String code;
    private String description;
    private int type;
    private String appId;
    private String appSecret;
    private String encodingAESKey;
    private int agentId;
    private boolean miniprogram = false;
    private String lastNodeid;
    private String unsubscribeUrl;
    private String accessToken;
    private boolean accessTokenProxy; // 调用第三方的接口是否需要代理

    public Account() {
        type = TYPE_CUSSV;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLastNodeid() {
        return lastNodeid;
    }

    public void setLastNodeid(String lastNodeid) {
        this.lastNodeid = lastNodeid;
    }

    public String getUnsubscribeUrl() {
        return unsubscribeUrl;
    }

    public void setUnsubscribeUrl(String unsubscribeUrl) {
        this.unsubscribeUrl = unsubscribeUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isAccessTokenProxy() {
        return accessTokenProxy;
    }

    public void setAccessTokenProxy(boolean accessTokenProxy) {
        this.accessTokenProxy = accessTokenProxy;
    }

    public String getEncodingAESKey() {
        return encodingAESKey;
    }

    public void setEncodingAESKey(String encodingAESKey) {
        this.encodingAESKey = encodingAESKey;
    }

    public boolean isMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(boolean miniprogram) {
        this.miniprogram = miniprogram;
    }
}
