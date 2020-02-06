package com.ebyte.officialAccount.user;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.Factory;
import com.ebyte.weixin.util.Util;
import com.ebyte.officialAccount.ApiUrl;

public class User extends Factory {

	/**
	 * 获取标签下粉丝列表
	 * 
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	public JSONObject gettag(int tagid) throws Exception {
		return gettag(tagid, "");
	}

	/**
	 * 获取标签下粉丝列表
	 * 
	 * @param tagid
	 * @param next_openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject gettag(int tagid, String next_openid) throws Exception {
		String url = String.format(ApiUrl.userGetTagUrl, this.getAccessToken());
		JSONObject tag = new JSONObject();
		tag.put("tagid", tagid);
		tag.put("next_openid", next_openid);
		return this.resultFormat(Util.httpPost(url, tag.toJSONString()));
	}

	/**
	 * 设置用户备注名
	 * 
	 * @param openid 用户标识
	 * @param remark 新的备注名，长度必须小于30字符
	 * @return
	 * @throws Exception
	 */
	public String updateremark(String openid, String remark) throws Exception {
		String url = String.format(ApiUrl.userUpdateremarkUrl, this.getAccessToken());
		JSONObject tag = new JSONObject();
		tag.put("openid", openid);
		tag.put("remark", remark);
		JSONObject rs = this.resultFormat(Util.httpPost(url, tag.toJSONString()));
		return rs.getString("errmsg");
	}

	/**
	 * 获取用户基本信息(UnionID机制)
	 * 
	 * @param openid 用户标识
	 * @return
	 * @throws Exception
	 */
	public JSONObject info(String openid) throws Exception {
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.userGetInfoUrl, accessToken, openid);
		return this.resultFormat(Util.httpGet(url));
	}

	/**
	 * 批量获取用户基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONArray infos(ArrayList<String> openids) throws Exception {
		JSONArray list = new JSONArray();
		for (String o : openids) {
			JSONObject item = new JSONObject();
			item.put("openid", o);
			item.put("lang", "zh_CN");
			list.add(item);
		}
		JSONObject params = new JSONObject();
		params.put("user_list", list);
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.userGetBatchInfoUrl, accessToken);
		JSONObject rs = this.resultFormat(Util.httpPost(url, params.toJSONString()));
		return rs.getJSONArray("user_info_list");
	}

	/**
	 * 获取用户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject list() throws Exception {
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.userGetListUrl, accessToken, "");
		JSONObject rs = this.resultFormat(Util.httpGet(url));
		return rs;
	}

	/**
	 * 获取用户列表
	 * 
	 * @param next_openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject list(String next_openid) throws Exception {
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.userGetListUrl, accessToken, next_openid);
		JSONObject rs = this.resultFormat(Util.httpGet(url));
		return rs;
	}

	/**
	 * 获取黑名单列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject blacklist() throws Exception {
		return blacklist("");
	}

	/**
	 * 获取黑名单列表
	 * 
	 * @param begin_openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject blacklist(String begin_openid) throws Exception {
		JSONObject param = new JSONObject();
		param.put("begin_openid", begin_openid);
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.userGetBlacklistUrl, accessToken);
		return this.resultFormat(Util.httpPost(url, param.toString()));
	}

	/**
	 * 拉黑用户
	 * 
	 * @param begin_openid
	 * @return
	 * @throws Exception
	 */
	public String black(ArrayList<String> openid_list) throws Exception {
		JSONObject param = new JSONObject();
		param.put("openid_list", openid_list);
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.userBlackUrl, accessToken);
		JSONObject rs = this.resultFormat(Util.httpPost(url, param.toString()));
		return rs.getString("errmsg");
	}

	/**
	 * 取消拉黑用户
	 * 
	 * @param openid_list
	 * @return
	 * @throws Exception
	 */
	public String unblack(ArrayList<String> openid_list) throws Exception {
		JSONObject param = new JSONObject();
		param.put("openid_list", openid_list);
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.userUnBlackUrl, accessToken);
		JSONObject rs = this.resultFormat(Util.httpPost(url, param.toString()));
		return rs.getString("errmsg");
	}

}
