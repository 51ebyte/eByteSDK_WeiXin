package com.ebyte.officialAccount.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.Factory;
import com.ebyte.weixin.util.*;
import com.ebyte.officialAccount.ApiUrl;

public class Menu extends Factory{

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void create(JSONArray menu) throws Exception {
		String url = String.format(ApiUrl.menuCreateUrl, this.getAccessToken());
		String result = Util.httpPost(url, menu.toJSONString());
		System.out.println(this.resultFormat(result));
	}

	/**
	 * 查询菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONArray query() throws Exception {
		String url = String.format(ApiUrl.getCurrentSelfMenuInfoUrl, this.getAccessToken());
		JSONObject selfmenu = this.resultFormat(Util.httpGet(url));
		JSONObject button = JSON.parseObject(selfmenu.getString("selfmenu_info"));
		return JSON.parseArray(button.getString("button"));
	}
	/**
	 * 删除菜单
	 * @return
	 * @throws Exception
	 */
	public JSONObject delete() throws Exception {
		String url = String.format(ApiUrl.menuDeleteUrl, this.getAccessToken());
		return this.resultFormat(Util.httpGet(url));
	}

}



