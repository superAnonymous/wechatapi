package org.tang.wechat.api.token;

import org.tang.wechat.api.model.AccessToken;

public class EnterpriseTokenManager extends TokenManager {
    @Override
    AccessToken readAccessToken(String appId, String appSecret, boolean flag) {
        return null;
    }

    @Override
    boolean initAccessToken() {
        return false;
    }

    @Override
    boolean lockAccessToken() {
        return false;
    }

    @Override
    boolean unlockAccessToken() {
        return false;
    }

    @Override
    void updateAccessToken(AccessToken token) {

    }

    @Override
    void expiredAccessToken(AccessToken token) {

    }

    @Override
    String getTokenFromServer(String appId, String appSecret) {
        return null;
    }
}
