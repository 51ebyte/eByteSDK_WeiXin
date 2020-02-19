package com.ebyte.officialAccount.user;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.Factory;
import com.ebyte.weixin.util.Http;
import com.ebyte.officialAccount.ApiUrl;

public class Tags extends Factory {

	/**
	 * 创建标签
	 * 
	 * @param name 标签名称
	 * @return
	 * @throws Exception
	 */
	public JSONObject create(String name) throws Exception {
		String url = String.format(ApiUrl.tagsCreateUrl, this.getAccessToken());
		JSONObject p = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("name", name);
		p.put("tag", tag);
		JSONObject rs = this.resultFormat(Http.post(url, p.toJSONString()));
		return rs.getJSONObject("tag");
	}

	/**
	 * 获取标签
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONArray get() throws Exception {
		String url = String.format(ApiUrl.tagsGetUrl, this.getAccessToken());
		JSONObject rs = this.resultFormat(Http.get(url));
		return rs.getJSONArray("tags");
	}

	/**
	 * 修改标签
	 * 
	 * @param id   标签id
	 * @param name 标签名称
	 * @return
	 * @throws Exception
	 */
	public String update(int id, String name) throws Exception {
		String url = String.format(ApiUrl.tagsUpdateUrl, this.getAccessToken());
		JSONObject p = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("id", id);
		tag.put("name", name);
		p.put("tag", tag);
		JSONObject rs = this.resultFormat(Http.post(url, p.toJSONString()));
		return rs.getString("errmsg");
	}

	/**
	 * 删除标签
	 * 
	 * @param id 标签id
	 * @return
	 * @throws Exception
	 */
	public String delete(int id) throws Exception {
		String url = String.format(ApiUrl.tagsDeleteUrl, this.getAccessToken());
		JSONObject p = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("id", id);
		p.put("tag", tag);
		JSONObject rs = this.resultFormat(Http.post(url, p.toJSONString()));
		return rs.getString("errmsg");
	}

	/**
	 * 批量为用户打标签
	 * 
	 * @param tagid       标签id
	 * @param openid_list 用户列表
	 * @return
	 * @throws Exception
	 */
	public String members_batchtagging(int tagid, JSONArray openid_list) throws Exception {
		JSONObject tag = new JSONObject();
		tag.put("tagid", tagid);
		tag.put("openid_list", openid_list);
		return members_batchtagging(tag.toJSONString());
	}

	/**
	 * 批量为用户打标签
	 * 
	 * @param tagid       标签id
	 * @param openid_list 用户列表
	 * @return
	 * @throws Exception
	 */
	public String members_batchtagging(int tagid, ArrayList<String> openid_list) throws Exception {
		JSONObject tag = new JSONObject();
		tag.put("tagid", tagid);
		tag.put("openid_list", openid_list);
		return members_batchtagging(tag.toJSONString());
	}

	/**
	 * 批量为用户打标签
	 * 
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	private String members_batchtagging(String params) throws Exception {
		String url = String.format(ApiUrl.tagsMembersBatchtaggingUrl, this.getAccessToken());
		String rs = Http.post(url, params);
		return JSON.parseObject(rs).getString("errmsg");
	}

	/**
	 * 批量为用户取消标签
	 * 
	 * @param tagid       标签id
	 * @param openid_list 用户列表
	 * @return
	 * @throws Exception
	 */
	public String members_batchuntagging(int tagid, JSONArray openid_list) throws Exception {
		JSONObject tag = new JSONObject();
		tag.put("tagid", tagid);
		tag.put("openid_list", openid_list);
		return members_batchuntagging(tag.toJSONString());
	}

	/**
	 * 批量为用户取消标签
	 * 
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	public String members_batchuntagging(int tagid, ArrayList<String> list) throws Exception {
		JSONObject tag = new JSONObject();
		tag.put("tagid", tagid);
		tag.put("openid_list", list);
		return members_batchuntagging(tag.toJSONString());
	}

	/**
	 * 批量为用户取消标签
	 * 
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	private String members_batchuntagging(String params) throws Exception {
		String url = String.format(ApiUrl.tagsMembersBatchuntaggingUrl, this.getAccessToken());
		String rs = Http.post(url, params);
		return JSON.parseObject(rs).getString("errmsg");
	}

	/**
	 * 获取用户身上的标签列表
	 * 
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public JSONArray getidlist(String openid) throws Exception {
		JSONObject tag = new JSONObject();
		tag.put("openid", openid);
		String url = String.format(ApiUrl.tagsGetidlistUrl, this.getAccessToken());
		String rs = Http.post(url, tag.toJSONString());
		return JSON.parseObject(rs).getJSONArray("tagid_list");
	}

}
