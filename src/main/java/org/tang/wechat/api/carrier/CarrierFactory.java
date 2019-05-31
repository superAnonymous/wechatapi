package org.tang.wechat.api.carrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.baseinterface.BaseCacheService;
import org.tang.wechat.api.configs.Account;
import org.tang.wechat.api.configs.WechatApiConfig;
import org.tang.wechat.api.exception.IllegallyLicenseException;
import org.tang.wechat.api.service.EnterpriseService;
import org.tang.wechat.api.service.WechatService;
import org.tang.wechat.api.servlet.WechatServlet;
import org.tang.wechat.api.session.LiveSessionFactory;
import org.tang.wechat.api.utils.DateUtils;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CarrierFactory {
    private static Logger logger = LoggerFactory.getLogger(CarrierFactory.class);
    private Map<String, Carrier> carriers;
    /**
     * 机器人构造名称
     */
    private LiveSessionFactory sessionFactory;
    /**
     * 许可证对象
     */
    private License license;


    private BaseCacheService baseCacheService;

    /**
     * 公众号交互
     * @return
     */
    private WechatService wechatService;
    /**
     * 企业号交互
     * @return
     */
    private EnterpriseService enterpriseService;


    public Map<String, Carrier> getCarriers() {
        return carriers;
    }

    public void setCarriers(Map<String, Carrier> carriers) {
        this.carriers = carriers;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public WechatService getWechatService() {
        return wechatService;
    }

    public void setWechatService(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    public EnterpriseService getEnterpriseService() {
        return enterpriseService;
    }

    public void setEnterpriseService(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    public BaseCacheService getBaseCacheService() {
        return baseCacheService;
    }

    public void setBaseCacheService(BaseCacheService baseCacheService) {
        this.baseCacheService = baseCacheService;
    }

    public LiveSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(LiveSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CarrierFactory() {
        carriers = new HashMap<String, Carrier>();
    }
    /**
     * 为EPID租户创建机器人
     *
     * @param epid
     *            Renter's id
     * @return WeChat service robot
     * @throws Exception
     */
    public Carrier getCarrier(String epid, URL url) throws Exception {
        epid = epid.toUpperCase();
        Carrier carrier;
        synchronized (carriers) {
            if (carriers.containsKey(epid)) {
                logger.debug("[@" + epid + "] 发现已经装载的消息携带者");
                carrier = carriers.get(epid);
                //获取携带者是不是已经更新了流程
                boolean isReload = carrier.getReloadFlag(epid);
                logger.debug("[@" + epid + "] 获取当前是否需要重载[" + isReload + "]");
                if (isReload) {
                    logger.debug("[@" + epid + "] 发现机器人流程加载标识[" + isReload + "]，重新装载");
                    carrier.initialize();
                }
            } else {
                carrier = createCarrier(epid, url);
                carriers.put(epid, carrier);
            }
        }
        return carrier;
    }

    private Carrier createCarrier(String epid, URL url) throws Exception {
        if (license.isExpired()) {
            String err = "该许可已于 " + DateUtils.format(license.getExpireTime(), DateUtils.ISO8601ChineseShortPattern) + " 之前过期，请联系服务商。";
            logger.error(err);
            throw new IllegallyLicenseException(err);
        }

        String host = url.getHost();
        logger.debug("验证许可在请求的域：" + host);
        if (license.verify(host)) {
            return createSignedRobot(epid, license.getKey(), false);
        } else {
            String err = "不合法的许可，未被授权使用在域名： " + host;
            logger.error(err);
            throw new IllegallyLicenseException(err);
        }
    }

    private Carrier createSignedRobot(String epid, String key, Boolean debug) throws Exception {
        Account account = WechatApiConfig.getInstance().getAccount(epid);
        if (account == null) {
            throw new NullPointerException("Cann't find account with code as " + epid);
        }
        logger.debug("Create carrierClass instance for EPID=" + epid + (debug ? "[TEST]" : ""));
        Carrier carrier = new Carrier(account, key);
        carrier.setDebug(debug);
        carrier.setBaseCacheService(baseCacheService);
        int type = account.getType();
        //初始化是企业号还是公众号
//        if (type == 2) {
//            carrier.setCommService(enterpriseService);
//        } else {
//            carrier.setCommService(wechatService);
//        }

        if (sessionFactory == null) {
            sessionFactory = new LiveSessionFactory();
        }
        carrier.setSessionFactory(sessionFactory);
        carrier.initialize();
        return carrier;
    }

}
