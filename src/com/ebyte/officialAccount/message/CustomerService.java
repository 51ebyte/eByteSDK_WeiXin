package com.ebyte.officialAccount.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.Factory;
import com.ebyte.weixin.util.Http;
import com.ebyte.weixin.util.WxException;
import com.ebyte.officialAccount.ApiUrl;

/**
 * 客服消息类
 * 
 * @author Administrator
 *
 */
public class CustomerService extends Factory {

	ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
	JSONObject message = new JSONObject();

	public CustomerService text(String text) {
		message.put("msgtype", "text");
		JSONObject map = new JSONObject();
		map.put("content", text);
		message.put("text", map);
		return this;
	}

	public CustomerService image(String media_id) {
		message.put("msgtype", "image");
		JSONObject map = new JSONObject();
		map.put("media_id", media_id);
		message.put("image", map);
		return this;
	}

	public CustomerService voice(String media_id) {
		message.put("msgtype", "voice");
		JSONObject map = new JSONObject();
		map.put("media_id", media_id);
		message.put("voice", map);
		return this;
	}

	public CustomerService video(String media_id, String thumb_media_id, String title, String description) {
		message.put("msgtype", "video");
		JSONObject map = new JSONObject();
		map.put("media_id", media_id);
		map.put("thumb_media_id", thumb_media_id);
		map.put("title", title);
		map.put("description", description);
		message.put("video", map);
		return this;
	}

	public CustomerService music(String title, String description, String musicurl, String hqmusicurl,
			String thumb_media_id) {
		message.put("msgtype", "music");
		JSONObject map = new JSONObject();
		map.put("title", title);
		map.put("description", description);
		map.put("musicurl", musicurl);
		map.put("hqmusicurl", hqmusicurl);
		map.put("thumb_media_id", thumb_media_id);
		message.put("music", map);
		return this;
	}

	public CustomerService news(String title, String description, String url, String picurl) {
		message.put("msgtype", "news");
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("title", title);
		item.put("description", description);
		item.put("url", url);
		item.put("picurl", picurl);

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(item);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("articles", list);
		message.put("news", map);
		return this;
	}

	public CustomerService mpnews(String media_id) {
		message.put("msgtype", "mpnews");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("media_id", media_id);
		message.put("mpnews", map);
		return this;
	}

	public CustomerService msgmenu(String head_content, String tail_content) {
		message.put("msgtype", "msgmenu");
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		HashMap<String, String> listItem = new HashMap<String, String>();
		listItem.put("id", "101");
		listItem.put("content", "不满意");
		HashMap<String, String> listItem1 = new HashMap<String, String>();
		listItem1.put("id", "102");
		listItem1.put("content", "一般");
		HashMap<String, String> listItem2 = new HashMap<String, String>();
		listItem2.put("id", "103");
		listItem2.put("content", "满意");
		HashMap<String, String> listItem3 = new HashMap<String, String>();
		listItem3.put("id", "104");
		listItem3.put("content", "非常满意");
		list.add(listItem);
		list.add(listItem1);
		list.add(listItem2);
		list.add(listItem3);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("head_content", head_content);
		map.put("tail_content", tail_content);
		map.put("list", list);
		message.put("msgmenu", map);
		return this;
	}

	public CustomerService to(String openid) {
		message.put("touser", openid);
		return this;
	}

	public CustomerService to(JSONArray openids) {
		message.put("touser", openids);
		return this;
	}

	public JSONObject send() throws Exception {
		String url = "";
		String accessToken = this.getAccessToken();
		if (message.get("openid") instanceof String) {
			url = String.format(ApiUrl.sendCustomMessageUrl, accessToken);
		} else if (message.get("openid") instanceof JSONArray) {
			url = String.format(ApiUrl.sendMassMessageUrl, accessToken);
		} else {
			throw new Exception("params 'to' abnormal");
		}
		String result = Http.post(url, message.toJSONString());
		JSONObject rs = JSON.parseObject(result);
		if (rs.getString("errcode") != "0") {
			throw new WxException(rs.getString("errcode"), rs.getString("errmsg"));
		} else {
			return rs;
		}
	}

	/**
	 * 添加客服
	 * 
	 * @param account  客服账号
	 * @param nickname 客服昵称
	 * @param password 客服密码
	 * @return
	 * @throws Exception
	 */
	public JSONObject add(String account, String nickname, String password) throws Exception {
		return exec(ApiUrl.addCustomServiceUrl, account, nickname, password);
	}

	/**
	 * 修改客服
	 * 
	 * @param account  客服账号
	 * @param nickname 客服昵称
	 * @param password 客服密码
	 * @return
	 * @throws Exception
	 */
	public JSONObject update(String account, String nickname, String password) throws Exception {
		return exec(ApiUrl.updateCustomServiceUrl, account, nickname, password);
	}

	/**
	 * 删除客服
	 * 
	 * @param account  客服账号
	 * @param nickname 客服昵称
	 * @param password 客服密码
	 * @return
	 * @throws Exception
	 */
	public JSONObject delete(String account, String nickname, String password) throws Exception {
		return exec(ApiUrl.delCustomServiceUrl, account, nickname, password);
	}

	/**
	 * 获取客服列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject getlist() throws Exception {
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.getCustomServiceListUrl, accessToken);
		String result = Http.get(url);
		JSONObject rs = JSON.parseObject(result);
		if (rs.getString("errcode") != "0") {
			throw new WxException(rs.getString("errcode"), rs.getString("errmsg"));
		} else {
			return rs;
		}
	}

	protected JSONObject exec(String url, String account, String nickname, String password) throws Exception {
		String accessToken = this.getAccessToken();
		JSONObject kf = new JSONObject();
		kf.put("kf_account", account);
		kf.put("nickname", nickname);
		kf.put("password", password);
		String result = Http.post(String.format(url, accessToken), message.toJSONString());
		JSONObject rs = JSON.parseObject(result);
		if (rs.getString("errcode") != ("0")) {
			throw new WxException(rs.getString("errcode"), rs.getString("errmsg"));
		} else {
			return rs;
		}
	}

	/**
	 * 输入状态下发（对用户下发“正在输入"状态）
	 * 
	 * @param openid 下发用户openId
	 * @return
	 * @throws Exception
	 */
	public JSONObject typing(String openid) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("touser", openid);
		obj.put("command", "Typing");
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.getCustomMessageTypingUrl, accessToken);
		String result = Http.post(url, obj.toJSONString());
		JSONObject rs = JSON.parseObject(result);
		if (rs.getString("errcode") != "0") {
			throw new WxException(rs.getString("errcode"), rs.getString("errmsg"));
		} else {
			return rs;
		}
	}

	/**
	 * 输入状态下发（取消）
	 * 
	 * @param openid 下发用户openId
	 * @param cancel 是否取消对用户的”正在输入"状态
	 * @return
	 * @throws Exception
	 */
	public JSONObject typing(String openid, boolean cancel) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("touser", openid);
		obj.put("command", cancel ? "CancelTyping" : "Typing");
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.getCustomMessageTypingUrl, accessToken);
		String result = Http.post(url, obj.toJSONString());
		JSONObject rs = JSON.parseObject(result);
		if (rs.getString("errcode") != "0") {
			throw new WxException(rs.getString("errcode"), rs.getString("errmsg"));
		} else {
			return rs;
		}
	}

}
