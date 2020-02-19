package com.ebyte.officialAccount;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.customerservice.CustomerService;
import com.ebyte.officialAccount.join.Join;
import com.ebyte.officialAccount.menu.Menu;
import com.ebyte.officialAccount.message.Message;
import com.ebyte.officialAccount.oauth.Oauth;
import com.ebyte.officialAccount.qrcode.Qrcode;
import com.ebyte.officialAccount.server.Server;
import com.ebyte.officialAccount.user.Tags;
import com.ebyte.officialAccount.user.User;
import com.ebyte.weixin.util.Cache;
import com.ebyte.weixin.util.Config;
import com.ebyte.weixin.util.Http;
import com.ebyte.weixin.util.WxException;

public class OfficialAccount extends Factory {

	public Menu menu = new Menu();
	public Oauth oauth = new Oauth();
	public Server server = new Server();
	public Message message = new Message();
	public Tags tags = new Tags();
	public User user = new User();
	public Qrcode qrcode = new Qrcode();
	public com.ebyte.officialAccount.message.CustomerService customerServiceMessage = new com.ebyte.officialAccount.message.CustomerService();
	public CustomerService customerService = new CustomerService();


	/**
	 * 设置授权后重定向的回调链接地址
	 * @param redirect_url
	 */
	public void setOauthCallback(URL redirect_url) {
		Config.setOauthCallback(redirect_url.toString());;
	}
	/**
	 * 设置应用授权作用域
	 * @param scope 固定值snsapi_base,snsapi_userinfo
	 * @throws Exception
	 */
	public void setOauthScopes(String scope) throws Exception {
		if (scope!="snsapi_base" && scope!="snsapi_userinfo") {
			throw new Exception("应用授权作用域");
		}
		Config.setOauthScopes(scope);
	}
	/**
	 * 设置服务器令牌
	 * @param token
	 * @throws Exception
	 */
	public void setToken(String token) throws Exception {
		Config.setToken(token);;
	}
	
	/**
	 * 设置消息加解密密钥
	 * @param aes_key
	 * @throws Exception
	 */
	public void setAesKey(String aes_key) throws Exception {
		Config.setAeskey(aes_key);
	}
	
	
	/**
	 * 获取access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken() throws Exception {
		if (Cache.get("accessToken") != null) {
			return Cache.get("accessToken").toString();
		}
		String appid = Config.getAppid();
		String secret = Config.getSecret();
		String result = Http.get(String.format(ApiUrl.getAccessTokenUrl, appid, secret));
		JSONObject obj = JSON.parseObject(result);
		if (obj.getString("errcode") != null) {
			throw new WxException(obj.getString("errcode"), obj.getString("errmsg"));
		}
		String accessToken = obj.getString("access_token");
		Cache.set("access_token", accessToken);
		return accessToken;
	}

	/**
	 * 获取微信服务器ip地址
	 * 
	 * @return
	 * @throws Exception
	 */
	public Object getValidIps() throws Exception {
		String result =Http.get(String.format(ApiUrl.callbackIpUrl, getAccessToken()));
		return resultFormat(result).get("ip_list");
	}

	/**
	 * 网络检测
	 * 
	 * @param action 执行的检测动作，允许的值：dns（做域名解析）、ping（做ping检测）、all（dns和ping都做）
	 * @param operator 指定平台从某个运营商进行检测，允许的值：CHINANET（电信出口）、UNICOM（联通出口）、CAP（腾讯自建出口）、DEFAULT（根据ip来选择运营商）
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkNetwork(String action, String operator) throws Exception {
		if (action!="dns"&& action!="ping" && action!="all") {
			throw new Exception("执行的检测动作[action]参数错误") ;
		}
		
		if (operator!="CHINANET"&& operator!="UNICOM" && operator!="CAP" && operator!="DEFAULT") {
			throw new Exception("指定平台运营商[operator]参数错误");
		}
		
		JSONObject params = new JSONObject();
		params.put("action", action);
		params.put("check_operator", operator);
		String url = String.format(ApiUrl.checkNetworkUrl, getAccessToken());
		String result = Http.post(url, params.toJSONString());
		return resultFormat(result);
	}
	
	/**
	 * 网络检测
	 * @param action action 执行的检测动作，允许的值：dns（做域名解析）、ping（做ping检测）、all（dns和ping都做）
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkNetwork(String action) throws Exception {
		return checkNetwork(action, "DEFAULT");
	}

	/**
	 * 清理接口调用次数 <br/>
	 * 此接口官方有每月调用限制，不可随意调用
	 * 
	 * @return
	 * @throws Exception
	 */

	public String clearQuota() throws Exception {
		JSONObject params = new JSONObject();
		params.put("appid", Config.getAppid());
		String url = String.format(ApiUrl.clearQuotaUrl, getAccessToken());
		String result=Http.post(url, params.toJSONString());
		return resultFormat(result).getString("errmsg");
	}

	/**
	 * 账号迁移openid转换
	 * 
	 * @param appid 原帐号的appid
	 * @param openid_list 需要转换的openid
	 * @return
	 * @throws Exception
	 */
	public JSONArray changeOpenid(String appid, ArrayList<String> openid_list) throws Exception {
		JSONObject param = new JSONObject();
		param.put("from_appid", appid);
		param.put("openid_list", openid_list);
		String url = String.format(ApiUrl.changeOpenidUrl, getAccessToken());
		String result = Http.post(url, param.toJSONString());
		return resultFormat(result).getJSONArray("result_list");
	}

	/**
	 * 长链接转成短链接
	 * 
	 * @param url 长链接
	 * @return
	 * @throws Exception
	 */
	public String shorturl(URL url) throws Exception {
		JSONObject param = new JSONObject();
		param.put("action", "long2short");
		param.put("long_url", url.toString());
		String request_url = String.format(ApiUrl.shortUrl, getAccessToken());
		String result=Http.post(request_url, param.toJSONString());
		JSONObject rs = resultFormat(result);
		return rs.getString("short_url");
	}

	/**
	 * 接入微信服务器开发
	 * @param response
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param echostr
	 * @throws IOException
	 */
	public void join(HttpServletResponse response, String token, String timestamp, String nonce, String signature,
			String echostr) throws IOException {
		String sign = Join.signature(token, timestamp, nonce);
		boolean rs = sign != null ? sign.equals(signature.toUpperCase()) : false;
		response.getWriter().write(rs ? echostr : "验证失败：" + sign);
	}

	/**
	 * 接入微信服务器开发
	 * @param response
	 * @param request
	 * @param token
	 * @throws IOException
	 */
	public void join(HttpServletResponse response, HttpServletRequest request, String token) throws Exception {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if (signature==null || timestamp==null || nonce==null || echostr==null) {
			throw new Exception("参数异常");
		}
		join(response, token, timestamp, nonce, signature, echostr);
	}
}
