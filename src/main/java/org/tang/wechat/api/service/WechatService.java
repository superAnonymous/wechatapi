package org.tang.wechat.api.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.configs.WechatApiConfig;
import org.tang.wechat.api.model.AccessToken;
import org.tang.wechat.api.model.Result;
import org.tang.wechat.api.model.token.BasicToken;
import org.tang.wechat.api.token.TokenManager;
import org.tang.wechat.api.token.WechatTokenManager;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WechatService implements BaseCommService {
    private final static Logger logger = LoggerFactory.getLogger(WechatService.class);
    private String baseUrl = "https://api.weixin.qq.com";
    public final static int COMM_TYPE = 0;
    public final static int COMMQY_TYPE = 1;
    protected WechatTokenManager tokenManager;

    private final String URL_CUSMENU_GET = "/cgi-bin/menu/get?access_token={0}";
    private final String URL_CUSMENU_POST = "/cgi-bin/menu/create?access_token={0}";
    private final String URL_MESSAGE_POST = "/cgi-bin/message/custom/send?access_token={0}";
    private final String URL_MESSAGE_QY_POST = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={0}";
    private final String URL_MESSAGE_TEMPLATE_POST = "/cgi-bin/message/template/send?access_token={0}";
    private final String URL_OAUTH = "/sns/oauth2/access_token";
    // private final String URL_OAUTH_REFRESH = "/sns/oauth2/refresh_token";
    private final String URL_QRCODE_TICKET = "/cgi-bin/qrcode/create?access_token={0}";
    private final String URL_QRCODE = "/cgi-bin/showqrcode?ticket={0}";

    private final String URL_MEDIA_COUNT = "/cgi-bin/material/get_materialcount?access_token={0}";
    private final String URL_MEDIA_UPLOAD = "/cgi-bin/material/add_material?access_token={0}&type={1}";
    private final String URL_MEDIA_DOWNLOAD = "/cgi-bin/material/get_material?access_token={0}";
    private final String URL_MEDIA_REMOVE = "/cgi-bin/material/del_material?access_token={0}";
    private final String URL_MEDIA_LIST = "/cgi-bin/material/batchget_material?access_token={0}";
    private final String URL_MEDIA_TEMP_UPLOAD = "/cgi-bin/media/upload?access_token={0}&type={1}";
    private final String URL_MEDIA_TEMP_DOWNLOAD = "/cgi-bin/media/get?access_token={0}&media_id={1}";

    private final String URL_NEWS_UPLOAD = "/cgi-bin/material/add_news?access_token={0}";
    private final String URL_NEWS_UPDATE = "/cgi-bin/material/update_news?access_token={0}";

    private final String URL_APPMSG_UPLOAD = "/cgi-bin/media/uploadnews?access_token={0}";
    private final String URL_APPMSG_BROADCAST = "/cgi-bin/message/mass/sendall?access_token={0}";
    private final String URL_APPMSG_PREVIEW = "/cgi-bin/message/mass/preview?access_token={0}";
    private final String URL_APPMSG_STATUS = "/cgi-bin/message/mass/get?access_token={0}";

    private final String URL_USER_GROUPS = "/cgi-bin/groups/get?access_token={0}";
    private final String URL_USER_GROUP_CREATE = "/cgi-bin/groups/create?access_token={0}";
    private final String URL_USER_GROUP_RENAME = "/cgi-bin/groups/update?access_token={0}";
    private final String URL_USER_GETLIST = "/cgi-bin/user/get?access_token={0}";
    private final String URL_USER_GETINFO = "/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN";
    private final String URL_USER_GETGROUP = "/cgi-bin/groups/getid?access_token={0}";
    private final String URL_USER_MOVEGROUP = "/cgi-bin/groups/members/update?access_token={0}";

    private final String URL_USER_GETLIST_BYTAG = "/cgi-bin/user/tag/get?access_token={0}";
    private final String URL_CALLBACKIP = "/cgi-bin/getcallbackip?access_token={0}";

    private final String URL_API_GET_AUTHORIZER_INFO = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token={0}";
    private final String URL_API_AUTHORIZER_TOKEN = "https:// api.weixin.qq.com /cgi-bin/component/api_authorizer_token={0}";


    /*添加微信多客服的功能*/
    private final String URL_CUSTOMERSERVER_GETKFLIST = "/cgi-bin/customservice/getkflist?access_token={0}";
    private final String URL_CUSTOMERSERVER_GETONLINE_KFLIST = "/cgi-bin/customservice/getonlinekflist?access_token={0}";

    //企业号
    private final String URL_ENTERPRISE_GETUSERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={0}";

    //迪斯尼相关的地址
    public final static String URL_DSN_MESSAGE_POST = "/disney/customer-service/send-customer-service-message";
    private final static String URL_DSN_MEDIA_TEMP_DOWNLOAD = "/disney/customer-service/download-media-material";
    private final String URL_DSN_USER_GETINFO = "/disney/customer-service/get-user-info";

    @Override
    public AccessToken getAccessToken(TokenManager tokenManager, String epid, String appId, String appSecret) throws Exception {
        return null;
    }

    @Override
    public AccessToken renewAccessToken(TokenManager tokenManager, AccessToken accessToken) throws Exception {
        return null;
    }

    @Override
    public Result getCallbackIp(AccessToken accessToken) {
        return null;
    }

    @Override
    public Result cusmenuGet(AccessToken accessToken) {
        return null;
    }

    @Override
    public Result cusmenuPost(AccessToken accessToken, String menu) {
        return null;
    }

    @Override
    public Result messageToQYHPost(AccessToken accessToken, String message) {
        return null;
    }

    @Override
    public Result messagePost(AccessToken accessToken, String message) {
        return null;
    }

    @Override
    public Result subscriberGet(AccessToken accessToken, String openId) {
        return null;
    }

    @Override
    public Result subscribersList(AccessToken accessToken, String nextOpenId) {
        return null;
    }

    @Override
    public Result subscriberGroupGet(AccessToken accessToken, String openId) {
        return null;
    }

    @Override
    public Result subscriberGroupMove(AccessToken accessToken, String openId, int groupId) {
        return null;
    }

    @Override
    public Result subscriberGroupMoveBatch(AccessToken accessToken, List<String> openIds, int groupId) {
        return null;
    }

    @Override
    public Result subscriberGroupsList(AccessToken accessToken) {
        return null;
    }

    @Override
    public Result subscriberGroupCreate(AccessToken accessToken, String groupName) {
        return null;
    }

    @Override
    public Result subscriberGroupRename(AccessToken accessToken, long groupId, String groupName) {
        return null;
    }

    @Override
    public Result subscribersListByTag(AccessToken accessToken, Integer tagId, String nextOpenId) {
        return null;
    }

    @Override
    public Result qrCodeGet(AccessToken accessToken, String sceneId, boolean temporary) {
        return null;
    }

    @Override
    public String getErrorMessage(int code) {
        return null;
    }

    @Override
    public Result getEnterpriseUserInfo(AccessToken accessToken, String code) {
        return null;
    }

    @Override
    public AccessToken getAccessToken(String epid) throws Exception {
        return null;
    }

    @Override
    public Result mediaTempUpload(AccessToken accessToken, byte[] data, String type, String filename) {
        return null;
    }

    @Override
    public Result mediaTempDownload2Local(AccessToken accessToken, String mediaId, String type) {
        return null;
    }

    @Override
    public Result getOAuth2OpenId(String code, String appId, String appSecret) {
        return null;
    }
}
