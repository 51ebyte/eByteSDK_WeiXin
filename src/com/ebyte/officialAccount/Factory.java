package com.ebyte.officialAccount;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebyte.weixin.util.Cache;
import com.ebyte.weixin.util.Util;
import com.ebyte.weixin.util.WxException;

public class Factory {

	/**
	 * 获取access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String getAccessToken() throws Exception {
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
	 * 请求结果验证
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	protected JSONObject resultFormat(String result) throws Exception {
		JSONObject obj = JSON.parseObject(result);
		if (obj.get("errcode") == null || obj.get("errcode").equals(0)) {
			return obj;
		} else {
			throw new WxException(obj.getString("errcode"), obj.getString("errmsg"));
		}
	}

	/**
	 * 获取POST RAW参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> getInputXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		InputStream in = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		try {
			return Util.xmlToMap(result.toString());
		} catch (Exception e) {
			throw e;
		}
	}

}
