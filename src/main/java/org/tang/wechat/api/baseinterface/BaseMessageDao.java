package org.tang.wechat.api.baseinterface;

import org.tang.wechat.api.message.Message;

public interface BaseMessageDao {

    public Boolean saveMessage(Message message);

    public Boolean UpdateMessage(Message message);
}
