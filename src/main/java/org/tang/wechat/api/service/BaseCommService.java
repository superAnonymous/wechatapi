package org.tang.wechat.api.service;

import org.tang.wechat.api.model.AccessToken;
import org.tang.wechat.api.model.Result;
import org.tang.wechat.api.token.TokenManager;

import java.util.List;

public interface BaseCommService {

    /**
     * 获取接口凭据
     *
     * @param epid
     * @param appId
     * @param appSecret
     * @return
     * @throws Exception
     */
    public AccessToken getAccessToken(TokenManager tokenManager, String epid, String appId, String appSecret) throws Exception;

    public AccessToken renewAccessToken(TokenManager tokenManager, AccessToken accessToken) throws Exception;

    public Result getCallbackIp(final AccessToken accessToken);

    /**
     * 向接口读入自定义菜单
     *
     * @param accessToken
     *            调用接口凭证
     * @return Result对象
     */
    public Result cusmenuGet(final AccessToken accessToken);

    /**
     * 向接口写入自定义菜单
     *
     * @param accessToken
     *            调用接口凭证
     * @param menu
     *            自定义菜单的脚本正文
     * @return Result对象
     */
    public Result cusmenuPost(final AccessToken accessToken, final String menu);

    /**
     * @param accessToken
     *            调用接口凭证
     * @param message
     *            消息主体内容，该消息格式参见各服务商说明
     * @return Result对象
     */
    public Result messageToQYHPost(final AccessToken accessToken, final String message);
    /**
     * @param accessToken
     *            调用接口凭证
     * @param message
     *            消息主体内容，该消息格式参见各服务商说明
     * @return Result对象
     */
    public Result messagePost(final AccessToken accessToken, final String message);

    /**
     * 获取单个订阅者的信息
     *
     * @param accessToken
     *            调用接口凭证
     * @param openId
     *            关注订阅者的OpenId
     * @return　Result对象，code=1时，value为Subscriber对象
     */
    public Result subscriberGet(final AccessToken accessToken, final String openId);

    /**
     * 获取订阅者列表
     *
     * @param accessToken
     *            调用接口凭证
     * @param nextOpenId
     *            第一个拉取的openId不填默认从头开始拉取
     * @return　Result对象
     */
    public Result subscribersList(final AccessToken accessToken, final String nextOpenId);

    /**
     * 获取订阅者所属组
     *
     * @param accessToken
     *            调用接口凭证
     * @param openId
     *            订阅者的OpenID
     * @return　Result对象
     */
    public Result subscriberGroupGet(final AccessToken accessToken, String openId);

    /**
     * 移动用户分组
     *
     * @param accessToken
     *            调用接口凭证
     * @param openId
     *            订阅者的OpenID
     * @param groupId
     *            移动后的分组id
     * @return Result对象
     */
    public Result subscriberGroupMove(final AccessToken accessToken, String openId, int groupId);
    public Result subscriberGroupMoveBatch(final AccessToken accessToken, List<String> openIds, int groupId);

    /**
     * 查询所有分组
     *
     * @param accessToken
     *            调用接口凭证
     * @return　Result对象
     */
    public Result subscriberGroupsList(final AccessToken accessToken);

    /**
     * 创建分组
     *
     * @param accessToken
     *            调用接口凭证
     * @param groupName
     *            分组名称
     * @return Result对象, if code = 0, value = {id, name}
     */
    public Result subscriberGroupCreate(final AccessToken accessToken, final String groupName);

    /**
     * 修改分组名
     *
     * @param accessToken
     *            调用接口凭证
     * @param groupId
     *            分组id
     * @param groupName
     *            分组名称(12个字符以内)
     * @return Result对象
     */
    public Result subscriberGroupRename(final AccessToken accessToken, final long groupId, final String groupName);

    /**
     * 获取订阅者列表
     *
     * @param accessToken
     *            调用接口凭证
     * @param nextOpenId
     *            第一个拉取的openId不填默认从头开始拉取
     * @return　Result对象
     */
    public Result subscribersListByTag(final AccessToken accessToken, Integer tagId, final String nextOpenId);

    /**
     * 获取二维码
     *
     * @param accessToken
     *            调用接口凭证
     * @param sceneId
     *            情景参数
     * @param temporary
     *            是否创建临时二维码
     * @return Result对象, if code=1, value=bytes of qrcode
     */
    public Result qrCodeGet(final AccessToken accessToken, String sceneId, boolean temporary);

    /**
     * 获取系统全局错误信息
     *
     * @param code
     * @return
     */
    public String getErrorMessage(int code);

    /**
     * 获取企业号用户信息
     * @param accessToken
     * @param code
     * @return
     */
    public Result getEnterpriseUserInfo(final AccessToken accessToken, String code);

    AccessToken getAccessToken(String epid) throws Exception;

    Result mediaTempUpload(AccessToken accessToken, byte[] data, String type, String filename);

    Result mediaTempDownload2Local(AccessToken accessToken, String mediaId, String type);

    Result getOAuth2OpenId(String code, String appId, String appSecret);


}
