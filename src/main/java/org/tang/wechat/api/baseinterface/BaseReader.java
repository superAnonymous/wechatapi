package org.tang.wechat.api.baseinterface;

import org.tang.wechat.api.outmessage.OutMessage;
import org.tang.wechat.api.token.TokenManager;

public interface BaseReader {
    TokenManager tokenManager = null;
    public boolean sendMessage(OutMessage outMessage);
}
