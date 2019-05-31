package org.tang.wechat.api.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.utils.PullWechatHttpService;
import org.tang.wechat.api.utils.StringUtils;

import java.util.List;

public class WechatApiConfig {
    private static final Logger loggger = LoggerFactory.getLogger(WechatApiConfig.class);
    private List<Account> accountList;
    private static WechatApiConfig instance;

    public static WechatApiConfig getInstance() {
        if (instance == null) {
            instance = new WechatApiConfig();
            instance.initialize();
        }
        return instance;
    }

    public static void setInstance(WechatApiConfig instance) {
        WechatApiConfig.instance = instance;
    }
    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public Account getAccount(String epid) {
        for (Account account: accountList) {
            if (StringUtils.isNoneEmpty(epid) && epid.equalsIgnoreCase(account.getCode())) {
                return account;
            }
        }
        return null;
    }

    public void initialize() {
        //初始化配置的数据
        boolean initConfigFinish = initConfig();
        loggger.info("初始化配置信息完成， 完成时间是{0}", System.currentTimeMillis());
        //初始化token数据
        boolean initAccesstoken = initAccessToken();
        loggger.info("初始化accesstoken信息完成， 完成时间是{0}", System.currentTimeMillis());
        //初始化 程序需要的一些信息
    }

    public boolean initConfig() {
        loggger.info("初始化配置信息，执行时间{0}", System.currentTimeMillis());

        return false;
    }

    /**
     * 需要把数据保存到mongo或者是redis中
     * @return
     */
    public boolean initAccessToken () {
        loggger.info("初始化access token 信息，执行时间{0}", System.currentTimeMillis());
        for (Account account : accountList) {
            String epid = account.getCode();
        }
        return false;
    }
}
