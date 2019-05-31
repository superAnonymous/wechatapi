package org.tang.wechat.api.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.exception.AccessTokenException;
import org.tang.wechat.api.exception.WechatException;
import org.tang.wechat.api.model.AccessToken;
import org.tang.wechat.api.model.token.BasicToken;
import java.sql.Timestamp;

public abstract class TokenManager {
    private static Logger logger = LoggerFactory.getLogger(TokenManager.class);
    protected String key;
    protected String url;
    private BasicToken simpleToken;

    public BasicToken getSimpleToken() {
        return simpleToken;
    }

    public void setSimpleToken(BasicToken simpleToken) {
        this.simpleToken = simpleToken;
    }

    public AccessToken getAccessToken(String appId, String appSecret) throws Exception {
        logger.debug("appId [" + appId + "], appsecret [" + appSecret + "]");
        AccessToken token = readAccessToken(appId, appSecret);

        if (token == null || token.expired() || (token.isLocked() && token.getLockedTime() - System.currentTimeMillis() > 1000 * 60)) {
            if (token == null) {
                if (!initAccessToken()) { // 初始化失败
                    throw new WechatException("[@" + key + "] initializing access_token faild, sleep 5's to continue.");
                }
                logger.debug("[@" + key + "] initializing access_token success");
            } else {
                if (!lockAccessToken()) { // 加锁失败代表有其他服务器正在进行token更新
                    throw new WechatException("[@" + key + "] lock access_token faild, sleep 5's to continue.");
                }
                logger.debug("[@" + key + "] lock access_token success");
            }

            try {
                token = requestAccessToken(appId, appSecret);
                updateAccessToken(token);
                logger.debug("[@" + key + "] update new access_token, " + token);
                return token;
            } catch (Exception e) {
                unlockAccessToken();
                logger.debug(e.getMessage(), e);
                throw e;
            }

        } else {
            if (token.isLocked()) {// token处于加锁状态，不可用
                throw new WechatException("[@" + key + "] access_token already locked, sleep 5's to continue.");
            }
            return token;
        }
    }


    /**
     * 读取数据库记录
     *
     * @param appId
     * @param appSecret
     * @return
     */
    private AccessToken readAccessToken(String appId, String appSecret) {
        return readAccessToken(appId, appSecret, false);
    }

    /**
     * 读取数据库记录
     *
     * @param appId
     * @param appSecret
     * @return
     */
    abstract AccessToken readAccessToken(String appId, String appSecret, boolean flag);

    abstract boolean initAccessToken();

    private boolean removeToken(String epid) {
//        DataService dataService = (DataService) SpringContextUtil.getBean("dataService");
//        return dataService.tokenRemove(epid) > 0;
        return false;
    }

    abstract boolean lockAccessToken();

    abstract boolean unlockAccessToken();

    abstract void updateAccessToken(AccessToken token);

    abstract void expiredAccessToken(AccessToken token);

    abstract String getTokenFromServer(String appId, String appSecret);

    private AccessToken requestAccessToken(String appId, String appSecret) throws Exception {
        // TODO: 判断token有效期， 如果过期，则对改记录加锁，并且前往WECHAT获取新token
        logger.debug("RequestAccessToken: appId=" + appId + ",  appSecret=" + appSecret + "[ url ]" + url);
        String resultString = getTokenFromServer(appId, appSecret);
        JSONObject JSONToken = JSON.parseObject(resultString);
        logger.debug("=======================================================================");
        logger.debug(JSONToken.toString());
        logger.debug("=======================================================================");
        Integer errcode = (Integer) JSONToken.getInteger("errcode");
        if (key == "WECHAT") {
            if (errcode == null || (errcode == 0 && ("ok".equals(JSONToken.getString("errmsg")) || "".equals(JSONToken.getString("errmsg"))))) {
                AccessToken token = new AccessToken();
                token.setToken(JSONToken.getString("access_token"));
                token.setExpires(JSONToken.getInteger("expires_in"));
                token.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                token.setAppId(appId);
                token.setAppSecret(appSecret);
                return token;
            } else {
                throw new AccessTokenException(JSONToken.getString("errmsg"));
            }
        }

        if (errcode == null) {
            AccessToken token = new AccessToken();
            token.setToken(JSONToken.getString("access_token"));
            token.setExpires(JSONToken.getInteger("expires_in"));
            token.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            token.setAppId(appId);
            token.setAppSecret(appSecret);
            return token;
        } else {
            throw new AccessTokenException(JSONToken.getString("errmsg"));
        }
    }

    /**
     * 刷新AccessToken
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    public AccessToken renewAccessToken(AccessToken accessToken) throws Exception {
        return getAccessToken(accessToken.getAppId(), accessToken.getAppSecret());
    }
}
