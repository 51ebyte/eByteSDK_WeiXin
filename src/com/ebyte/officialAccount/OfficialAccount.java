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
import com.ebyte.weixin.util.Util;
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
	 * 获取access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken() throws Exception {
		if (Cache.get("accessToken") != null) {
			return Cache.get("accessToken").toString();
		}
		String appid = Util.getWxConfig("appid");
		String secret = Util.getWxConfig("secret");
		String url = String.format(ApiUrl.getAccessTokenUrl, appid, secret);
		String result = Util.httpGet(url);
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
		Factory officialAccount = new Factory();
		String result = Util.httpGet(ApiUrl.callbackIpUrl);
		return officialAccount.resultFormat(result).get("ip_list");
	}

	/**
	 * 网络检测
	 * 
	 * @param action
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkNetwork(String action, String operator) throws Exception {
		JSONObject p = new JSONObject();
		p.put("action", action);
		p.put("check_operator", operator);
		String url = String.format(ApiUrl.checkNetworkUrl, getAccessToken());
		String result = Util.httpPost(url, p.toJSONString());
		return resultFormat(result);
	}

	/**
	 * 清理接口调用次数 <br/>
	 * 此接口官方有每月调用限制，不可随意调用
	 * 
	 * @return
	 * @throws Exception
	 */

	public JSONObject clearQuota() throws Exception {
		JSONObject p = new JSONObject();
		p.put("appid", Util.getWxConfig("secret"));
		String url = String.format(ApiUrl.clearQuotaUrl, getAccessToken());
		String result = Util.httpPost(url, p.toJSONString());
		return resultFormat(result);
	}

	/**
	 * 账号迁移openid转换
	 * 
	 * @param appid
	 * @param openid_list
	 * @return
	 * @throws Exception
	 */
	public JSONArray changeOpenid(String appid, ArrayList<String> openid_list) throws Exception {
		JSONObject param = new JSONObject();
		param.put("from_appid", appid);
		param.put("openid_list", openid_list);
		String url = String.format(ApiUrl.changeOpenidUrl, getAccessToken());
		String result = Util.httpPost(url, param.toJSONString());
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
		JSONObject rs = new Factory().resultFormat(Util.httpPost(request_url, param.toJSONString()));
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
	public void join(HttpServletResponse response, HttpServletRequest request, String token) throws IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		join(response, token, timestamp, nonce, signature, echostr);
	}

}
