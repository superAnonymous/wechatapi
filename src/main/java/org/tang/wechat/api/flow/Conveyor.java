package org.tang.wechat.api.flow;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.aes.AesException;
import org.tang.wechat.api.aes.WXBizMsgCrypt;
import org.tang.wechat.api.baseinterface.BaseMessageDao;
import org.tang.wechat.api.baseinterface.BaseReader;
import org.tang.wechat.api.baseinterface.MessageNotice;
import org.tang.wechat.api.message.Message;
import org.tang.wechat.api.message.MessageUtils;
import org.tang.wechat.api.outmessage.OutMessage;
import org.tang.wechat.api.model.Mojor;
import org.tang.wechat.api.reader.EnterpriceReader;
import org.tang.wechat.api.reader.MiniProReader;
import org.tang.wechat.api.reader.WechatReader;
import org.tang.wechat.api.utils.DomUtils;

import java.io.IOException;

/**
 * @author Tang
 */
public class Conveyor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Conveyor.class);

    private String appid;
    private String token;
    private String appsecret;
    private String encodingAESKey;
    private WXBizMsgCrypt wxcpt;
    private BaseMessageDao baseMessageDao;
    private MessageNotice messageScout;
    private BaseReader reader;

    public Conveyor(String appid, String appsecret, String stoken, String encodingAESKey, Mojor mojor) {
        this.appid = appid;
        this.appsecret = appsecret;
        this.encodingAESKey = encodingAESKey;
        try {
            wxcpt = new WXBizMsgCrypt(stoken, encodingAESKey, appid);
            switch (mojor) {
                case WECGAT:
                    reader = new WechatReader();
                    break;
                case ENTERPRISE:
                    reader = new EnterpriceReader();
                    break;
                case MINIPROGRAM:
                    reader = new MiniProReader();
                    break;
                default:
                    break;
            }
        } catch (AesException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Message conveyMessage(String reqMsgSig, String reqTimeStamp, String reqNonce, String reqData) {
        try {
            String encryptMsg = reqData;
            if (null != wxcpt) {
                 encryptMsg = wxcpt.decryptMsg(reqMsgSig, reqTimeStamp, reqNonce, reqData);
            }
            Element root = DomUtils.parser(encryptMsg, "UTF-8").getRootElement();
            Message message = MessageUtils.parse(root);
            boolean saveMessageResult = baseMessageDao.saveMessage(message);
            messageScout.noticeToYouMessageSaveResult(saveMessageResult);
            return  message;
        } catch (AesException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {

        }
        return null;
    };

    public boolean sendMessage(OutMessage outMessage) {
        return reader.sendMessage(outMessage);
    }
}
