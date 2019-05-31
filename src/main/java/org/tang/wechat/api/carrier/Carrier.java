package org.tang.wechat.api.carrier;

import org.tang.wechat.api.baseinterface.BaseCacheService;
import org.tang.wechat.api.configs.Account;
import org.tang.wechat.api.model.Node;
import org.tang.wechat.api.model.Workflow;
import org.tang.wechat.api.session.LiveSessionFactory;

import java.util.HashMap;
import java.util.Map;

public class Carrier {
    private boolean debug;
    private LiveSessionFactory sessionFactory;
    private BaseCacheService baseCacheService;
    private Account account;
    protected int type;
    /**
     * 当前首选流程
     */
    protected Workflow activedWorkflow;
    /**
     * 系统内所有流程
     */
    protected Map<String, Workflow> workflowsMap;
    /**
     * 整个流程的节点影射图
     */
    protected Map<String, Node> nodesMap;
    /**
     * 邦定微信自定义菜单的流程快捷节点
     */
    protected Map<String, Node> eventNodesMap;
    /**
     * 当前授权许可
     */
    protected String licenseKey;
    /**
     * 机器人所在的WEB服务上下文
     */
    protected String contextPath;
//    /**
//     * 节点关键词搜索器
//     */
//    protected NodeSearcher nodeSearcher;


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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean getReloadFlag(String epid) {
        if (baseCacheService == null) {
            return false;
        }
        return baseCacheService.getReloadFlag(epid.toLowerCase());
    }
    public Carrier(Account account, String licenseKey) {
        this.account = account;
        this.type = account.getType();
        this.account.setCode(this.account.getCode().toLowerCase());
        this.workflowsMap = new HashMap<String, Workflow>();
        this.nodesMap = new HashMap<String, Node>();
        this.eventNodesMap = new HashMap<String, Node>();
        this.licenseKey = licenseKey;
        this.contextPath = "";
        // 构建节点关键词搜索器
//        nodeSearcher = new NodeSearcher();
    }


    public void initialize() {
    }
}
