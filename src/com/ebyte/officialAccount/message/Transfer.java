package com.ebyte.officialAccount.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.Factory;
import com.ebyte.officialAccount.ApiUrl;
import com.ebyte.officialAccount.server.Server;
import com.ebyte.weixin.util.Http;
import com.ebyte.weixin.util.WxException;

/**
 * 消息转发
 * 
 * @author Administrator
 *
 */
public class Transfer extends Factory {
	ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
	JSONObject message = new JSONObject();

	public Transfer() throws Exception {
		String userName = Server.ReceiveMsg.get("FromUserName");
		if (userName != null) {
			new Transfer(userName);
		}
	}

	public Transfer(String openid) throws Exception {
		String url = String.format(ApiUrl.sendCustomMessageUrl, this.getAccessToken());
		message.put("touser", openid);
		switch (Server.ReceiveMsg.get("MsgType")) {
		case "text":
			text(Server.ReceiveMsg.get("Content"));
			break;
		case "image":
			image(Server.ReceiveMsg.get("MediaId"));
			break;
		case "voice":
			voice(Server.ReceiveMsg.get("MediaId"));
			break;
		case "video":
			video(Server.ReceiveMsg.get("MediaId"), Server.ReceiveMsg.get("ThumbMediaId"), "", "");
			break;
		}
		String result = Http.post(url, message.toJSONString());
		JSONObject rs = JSONObject.parseObject(result);
		if (rs.getString("errcode") != "0") {
			throw new WxException(rs.getString("errcode"), rs.getString("errmsg"));
		}
	}

	protected void text(String text) {
		message.put("msgtype", "text");
		JSONObject map = new JSONObject();
		map.put("content", text);
		message.put("text", map);
	}

	protected void image(String media_id) {
		message.put("msgtype", "image");
		JSONObject map = new JSONObject();
		map.put("media_id", media_id);
		message.put("image", map);
	}

	protected void voice(String media_id) {
		message.put("msgtype", "voice");
		JSONObject map = new JSONObject();
		map.put("media_id", media_id);
		message.put("voice", map);
	}

	protected void video(String media_id, String thumb_media_id, String title, String description) {
		message.put("msgtype", "video");
		JSONObject map = new JSONObject();
		map.put("media_id", media_id);
		map.put("thumb_media_id", thumb_media_id);
		map.put("title", title);
		map.put("description", description);
		message.put("video", map);
	}

	protected void music(String title, String description, String musicurl, String hqmusicurl, String thumb_media_id) {
		message.put("msgtype", "music");
		JSONObject map = new JSONObject();
		map.put("title", title);
		map.put("description", description);
		map.put("musicurl", musicurl);
		map.put("hqmusicurl", hqmusicurl);
		map.put("thumb_media_id", thumb_media_id);
		message.put("music", map);
	}

	protected void news(String title, String description, String url, String picurl) {
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
	}

	protected void mpnews(String media_id) {
		message.put("msgtype", "mpnews");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("media_id", media_id);
		message.put("mpnews", map);
	}

	protected void msgmenu(String media_id) {
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
		map.put("head_content", media_id);
		map.put("tail_content", media_id);
		map.put("list", list);
		message.put("msgmenu", map);
	}

}
