package org.tang.wechat.api;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.aes.AesException;
import org.tang.wechat.api.aes.WXBizMsgCrypt;
import org.tang.wechat.api.configs.Account;
import org.tang.wechat.api.configs.WechatApiConfig;
import org.tang.wechat.api.model.ProxyConfig;
import org.tang.wechat.api.utils.PullWechatHttpService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AppTest.class);
    private String sToken = "QDG6eK";
    private String sCorpID = "wx5823bf96d3bd56c7";
    private String sEncodingAESKey = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";
    // 解析出url上的参数值如下：
    // String sVerifyMsgSig = HttpUtils.ParseUrl("msg_signature");
    private String sVerifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
    // String sVerifyTimeStamp = HttpUtils.ParseUrl("timestamp");
    private String sVerifyTimeStamp = "1409659589";
    // String sVerifyNonce = HttpUtils.ParseUrl("nonce");
    private String sVerifyNonce = "263014780";
    // String sVerifyEchoStr = HttpUtils.ParseUrl("echostr");
    private String sVerifyEchoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";

    @Before
    public void initConfig() {
        //初始化http请求的对象
        PullWechatHttpService pullWechatHttpService = new PullWechatHttpService();
        pullWechatHttpService.setProtocol("");
//        ProxyConfig proxyConfig = new ProxyConfig();
//        proxyConfig.setHost("");
//        proxyConfig.setPassword("");
//        proxyConfig.setPort(8888);
//        proxyConfig.setUsername("");
//        pullWechatHttpService.setProxyConfig(proxyConfig);
        pullWechatHttpService.setProtocol("TLSv1.2");
        pullWechatHttpService.initialize();
        //初始化微信的信息
        WechatApiConfig wechatApiConfig = new WechatApiConfig();
        List<Account> accountList = new ArrayList<Account>();
        Account account = new Account();
        account.setCode("main");
        account.setType(0);
        account.setAppId("");
        account.setAppSecret("");
        account.setAccessToken("");
        account.setAccessTokenProxy(false);
        account.setEncodingAESKey("");
        account.setDescription("主公众号测试用的");
        account.setLastNodeid("");
        account.setMiniprogram(false);
        accountList.add(account);
        wechatApiConfig.setAccountList(accountList);

    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws AesException {

//        String requestStr = PullWechatHttpService.getInstance().get("http://www.baidu.com");
//        System.out.println(requestStr);
        assertTrue(true);
        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
        String sEchoStr; //需要返回的明文
        try {
            sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
            LOGGER.debug("verifyurl echostr: " + sEchoStr);
            // 验证URL成功，将sEchoStr返回
            // HttpUtils.SetResponse(sEchoStr);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }


        String sReqMsgSig = "477715d11cdb4164915debcba66cb864d751f3e6";
        // String sReqTimeStamp = HttpUtils.ParseUrl("timestamp");
        String sReqTimeStamp = "1409659813";
        // String sReqNonce = HttpUtils.ParseUrl("nonce");
        String sReqNonce = "1372623149";
        // post请求的密文数据
        // sReqData = HttpUtils.PostData();
        String sReqData = "<xml><ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt><AgentID><![CDATA[218]]></AgentID></xml>";
//        String sReqData = "<xml><AppId><![CDATA[wxb0f5a4f120488665]]></AppId><Encrypt><![CDATA[8Sc51rw/XKjuen1pa4+kjNNrqDZC9UvF99HJIvxA4TvRkrFyEfySHDMoTlcoMBwVxuRMIZgjM+mANYv6YXZFyEYLC4BpbXBeuvHXeql8FzViVax7GLcd50VrVlmOBM7vlg4mhJYZfoG2XU2UonbEDn8DHLeNSb/Ck2OBFUlXRs/6bJBMClWDyLOUukkQUvDIS7azHRjwMp9C0XKp9swBN9YnM2i9MIkOvn8JFe7ByZgASlauOHZMSCGgF0/6LDmtAwpz0lS4CNMDWqtrKX7ujTonjMHuor5WFxyHbpOhunZ2Vmblf+kYRdwoziI585ylIWWaYZy6Uy2QelBo62iXKSYx3Mp6t+4cN4uqATXbVOF07YXayi486zox1mbQ4LOKXw8xBJzARkYHH5XaWDuC1F0BSYkl1vl7l4lrGrzLesgRjWnXnfic5SaP0TA2CTuy26txIIfsZOwAY4HMJU2a9w==]]></Encrypt></xml>";

        try {
            String sMsg = wxcpt.decryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
            if (sMsg != null) {
                LOGGER.debug("after decrypt msg: " + sMsg);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                StringReader sr = new StringReader(sMsg);
                InputSource is = new InputSource(sr);
                Document document = db.parse(is);
                Element root = document.getDocumentElement();
                NodeList nodelist1 = root.getElementsByTagName("Content");
                String Content = nodelist1.item(0).getTextContent();
                LOGGER.debug("Content：" + Content);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        String sRespData = "<xml><ToUserName><![CDATA[mycreate]]></ToUserName><FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId><AgentID>128</AgentID></xml>";
        try {
            String sEncryptMsg = wxcpt.EncryptMsg(sRespData, sReqTimeStamp, sReqNonce);
            LOGGER.debug("after encrypt sEncrytMsg: " + sEncryptMsg);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}