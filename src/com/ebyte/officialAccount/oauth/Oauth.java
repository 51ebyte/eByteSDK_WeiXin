package com.ebyte.officialAccount.oauth;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.Factory;
import com.ebyte.weixin.util.Config;
import com.ebyte.weixin.util.Http;
import com.ebyte.officialAccount.ApiUrl;

public class Oauth extends Factory {

	public enum scopeType {
		BASE, USERINFO
	}

	public static final String SNSAPI_BASE = "snsapi_base";
	public static final String SNSAPI_USERINFO = "snsapi_userinfo";

	private String appid = Config.getAppid();
	private String secret = Config.getSecret();
	private String redirect = Config.getOauthCallback();
	private String scope = Config.getOauthScopes();
	private String state = "";
	private String code = "";

	public Oauth scopes(String scope) {
		this.scope = scope;
		return this;
	}

	public Oauth state(String state) {
		this.state = state;
		return this;
	}

	/**
	 * 重定向
	 * 
	 * @throws IOException
	 */
	public void redirect(HttpServletResponse response) throws IOException {
		String url = String.format(ApiUrl.oauthUrl, appid, redirect, scope != null ? scope : SNSAPI_BASE, state);
		response.sendRedirect(url);
	}

	/**
	 * 重定向
	 * 
	 * @throws IOException
	 */
	public void redirect(HttpServletResponse response, String url) throws IOException {
		this.redirect = url;
		redirect(response);
	}
	/**
	 * 重定向
	 * 
	 * @throws IOException
	 */
	public void redirect(HttpServletResponse response, URL url) throws IOException {
		this.redirect = url.toString();
		redirect(response);
	}

	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject user(HttpServletRequest request) throws Exception {
		Enumeration<String> enu = request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			params.put(key, request.getParameter(key));
		}
		this.code = params.get("code");
		return getUserInfo();
	}

	@SuppressWarnings("unlikely-arg-type")
	private JSONObject getUserInfo() throws Exception {
		JSONObject token = getOauthAccessToken();
		if (scope.equals(scopeType.USERINFO)) {
			String url = String.format(ApiUrl.oauthUserInfo, token.get("access_token"), token.get("openid"));
			return resultFormat(Http.get(url));
		}
		return token.getJSONObject("openid");
	}

	private JSONObject getOauthAccessToken() throws Exception {
		String url = String.format(ApiUrl.oauthAccessToken, appid, secret, code);
		return this.resultFormat(Http.get(url));
	}

}
