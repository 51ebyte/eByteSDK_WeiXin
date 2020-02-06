package com.ebyte.officialAccount;


public class ApiUrl {
	
	private final static String DOMAMIN_OPEN="https://open.weixin.qq.com/";
	private final static String DOMAMIN_API="https://api.weixin.qq.com/";
	private final static String DOMAMIN_MP="https://mp.weixin.qq.com/";
	
	//获取AccessToken
	public final static String getAccessTokenUrl=DOMAMIN_API+"cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	//获取微信服务器IP地址
	public final static String callbackIpUrl = DOMAMIN_API+"cgi-bin/getcallbackip?access_token=%s";
	//接口清零
	public final static String clearQuotaUrl=DOMAMIN_API+"cgi-bin/clear_quota?access_token=%s";
	//网络检测
	public final static String checkNetworkUrl=DOMAMIN_API+"cgi-bin/callback/check?access_token=%s";
	//账号迁移 openid 转换
	public final static String changeOpenidUrl = DOMAMIN_API+"cgi-bin/changeopenid?access_token=%s";
	//长链接转成短链接
	public final static String shortUrl = DOMAMIN_API+"cgi-bin/shorturl?access_token=%s";
		
	//网页授权
	public final static String oauthUrl = DOMAMIN_OPEN+"connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
	//授权令牌
	public final static String oauthAccessToken = DOMAMIN_API+"sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	//获取授权用户信息
	public final static String oauthUserInfo = DOMAMIN_API+"sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
	
	//创建菜单
	public final static String menuCreateUrl = DOMAMIN_API+"cgi-bin/menu/create?access_token=%s";
	//删除菜单
	public final static String menuDeleteUrl = DOMAMIN_API+"cgi-bin/menu/delete?access_token=%s";
	//获取自定义菜单
	public final static String getCurrentSelfMenuInfoUrl = DOMAMIN_API+"cgi-bin/get_current_selfmenu_info?access_token=%s&charset=utf-8";
	//创建标签
	public final static String tagsCreateUrl = DOMAMIN_API+"cgi-bin/tags/create?access_token=%s";
	//获取标签
	public final static String tagsGetUrl = DOMAMIN_API+"cgi-bin/tags/get?access_token=%s";
	//修改标签
	public final static String tagsUpdateUrl = DOMAMIN_API+"cgi-bin/tags/update?access_token=%s";
	//删除标签
	public final static String tagsDeleteUrl = DOMAMIN_API+"cgi-bin/tags/delete?access_token=%s";
	//获取标签下粉丝列表
	public final static String userGetTagUrl = DOMAMIN_API+"cgi-bin/user/tag/get?access_token=%s";
	//批量为用户打标签
	public final static String tagsMembersBatchtaggingUrl = DOMAMIN_API+"cgi-bin/tags/members/batchtagging?access_token=%s";
	//批量为用户取消标签
	public final static String tagsMembersBatchuntaggingUrl = DOMAMIN_API+"cgi-bin/tags/members/batchuntagging?access_token=%s";
	//获取用户身上的标签列表
	public final static String tagsGetidlistUrl = DOMAMIN_API+"cgi-bin/tags/getidlist?access_token=%s";
	//设置用户备注名
	public final static String userUpdateremarkUrl = DOMAMIN_API+"cgi-bin/user/info/updateremark?access_token=%s";
	//获取用户基本信息(UnionID机制)
	public final static String userGetInfoUrl = DOMAMIN_API+"cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
	//批量获取用户基本信息
	public final static String userGetBatchInfoUrl = DOMAMIN_API+"cgi-bin/user/info/batchget?access_token=%s&lang=zh_CN";
	//获取用户列表
	public final static String userGetListUrl = DOMAMIN_API+"cgi-bin/user/get?access_token=%s&next_openid=%s";
	//获取黑名单列表
	public final static String userGetBlacklistUrl = DOMAMIN_API+"cgi-bin/tags/members/getblacklist?access_token=%s";
	//拉黑用户
	public final static String userBlackUrl = DOMAMIN_API+"cgi-bin/tags/members/batchblacklist?access_token=%s";
	//取消拉黑用户
	public final static String userUnBlackUrl = DOMAMIN_API+"cgi-bin/tags/members/batchunblacklist?access_token=%s";
	
	
	//创建二维码ticket
	public final static String qrcodeTicketUrl = DOMAMIN_API+"cgi-bin/qrcode/create?access_token=%s";
	//通过ticket换取二维码
	public final static String qrcodeShowUrl = DOMAMIN_MP+"cgi-bin/showqrcode?ticket=%s";
	
	//客服接口-发消息
	public final static String sendCustomMessageUrl = DOMAMIN_API+"cgi-bin/message/custom/send?access_token=%s";
	//根据OpenID列表群发消息【订阅号不可用，服务号认证后可用】
	public final static String sendMassMessageUrl = DOMAMIN_API+"cgi-bin/message/mass/send?access_token=%s";
	//添加客服帐号
	public final static String addCustomServiceUrl = DOMAMIN_API+"customservice/kfaccount/add?access_token=%s";
	//修改客服帐号
	public final static String updateCustomServiceUrl = DOMAMIN_API+"customservice/kfaccount/update?access_token=%s";
	//删除客服帐号
	public final static String delCustomServiceUrl = DOMAMIN_API+"customservice/kfaccount/del?access_token=%s";
	//获取所有客服账号
	public final static String getCustomServiceListUrl = DOMAMIN_API+"cgi-bin/customservice/getkflist?access_token=%s";
	//客服输入状态
	public final static String getCustomMessageTypingUrl = DOMAMIN_API+"cgi-bin/message/custom/typing?access_token=%s";

}
