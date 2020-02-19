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
	 * @return 
	 * @throws Exception
	 */
	public String create(JSONArray menu) throws Exception {
		String url = String.format(ApiUrl.menuCreateUrl, getAccessToken());
		String result = Http.post(url, menu.toJSONString());
		return resultFormat(result).getString("errmsg");
	}

	/**
	 * 查询菜单
	 * @return
	 * @throws Exception
	 */
	public JSONArray query() throws Exception {
		String url = String.format(ApiUrl.getCurrentSelfMenuInfoUrl, getAccessToken());
		JSONObject selfmenu = resultFormat(Http.get(url));
		JSONObject button = JSON.parseObject(selfmenu.getString("selfmenu_info"));
		return JSON.parseArray(button.getString("button"));
	}
	/**
	 * 删除菜单
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String url = String.format(ApiUrl.menuDeleteUrl, getAccessToken());
		return resultFormat(Http.get(url)).getString("errmsg");
	}

}



