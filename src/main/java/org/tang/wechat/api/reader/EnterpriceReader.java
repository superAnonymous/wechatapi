package org.tang.wechat.api.reader;

import org.tang.wechat.api.baseinterface.BaseReader;
import org.tang.wechat.api.outmessage.OutMessage;

public class EnterpriceReader implements BaseReader {
    @Override
    public boolean sendMessage(OutMessage outMessage) {
        return false;
    }
}
