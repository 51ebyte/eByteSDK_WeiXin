package com.ebyte.officialAccount.qrcode;

import com.alibaba.fastjson.JSONObject;
import com.ebyte.officialAccount.Factory;
import com.ebyte.weixin.util.Http;
import com.ebyte.officialAccount.ApiUrl;

public class Qrcode extends Factory {

	private JSONObject ticket(String action_name, int expire_seconds, int scene_id) throws Exception {
		if (scene_id < 1 || scene_id > 100000) {
			throw new Exception("场景值ID无效");
		}
		JSONObject scene = new JSONObject();
		scene.put("scene_id", scene_id);
		return ticket(action_name, expire_seconds, scene);
	}

	private JSONObject ticket(String action_name, int expire_seconds, String scene_str) throws Exception {
		JSONObject scene = new JSONObject();
		scene.put("scene_str", scene_str);
		return ticket(action_name, expire_seconds, scene);
	}

	private JSONObject ticket(String action_name, int expire_seconds, JSONObject scene) throws Exception {
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.qrcodeTicketUrl, accessToken);
		JSONObject info = new JSONObject();
		info.put("scene", scene);
		JSONObject param = new JSONObject();
		param.put("expire_seconds", expire_seconds);
		param.put("action_name", action_name);
		param.put("action_info", info);
		return this.resultFormat(Http.post(url, param.toJSONString()));
	}

	private JSONObject ticket(String action_name, int scene_id) throws Exception {
		if (scene_id < 1 || scene_id > 100000) {
			throw new Exception("场景值ID无效");
		}
		JSONObject scene = new JSONObject();
		scene.put("scene_id", scene_id);
		return ticket(action_name, scene);
	}

	private JSONObject ticket(String action_name, String scene_str) throws Exception {
		JSONObject scene = new JSONObject();
		scene.put("scene_str", scene_str);
		return ticket(action_name, scene);
	}

	private JSONObject ticket(String action_name, JSONObject scene) throws Exception {
		String accessToken = this.getAccessToken();
		String url = String.format(ApiUrl.qrcodeTicketUrl, accessToken);
		JSONObject info = new JSONObject();
		info.put("scene", scene);
		JSONObject param = new JSONObject();
		param.put("action_name", action_name);
		param.put("action_info", info);
		return this.resultFormat(Http.post(url, param.toJSONString()));
	}

	/**
	 * 创建临时二维码
	 * 
	 * @param scene_id 二维码详细信息（场景值ID,32位非0整型）
	 * @param expire   二维码有效期
	 * @param type     输出参数（可选值：url|ticket|image）
	 * @return
	 * @throws Exception
	 */
	public String temporary(int scene_id, int expire, String type) throws Exception {
		if (expire < 1 || expire > 2592000) {
			throw new Exception("有效时间无效");
		}
		JSONObject rs = ticket("QR_SCENE", expire, scene_id);
		if (type.equals("url")) {
			return rs.getString("url");
		} else if (type.equals("ticket")) {
			return rs.getString("ticket");
		} else if (type.equals("image")) {
			return Http.get(String.format(ApiUrl.qrcodeShowUrl, rs.getString("ticket")));
		}
		return rs.toJSONString();
	}

	/**
	 * 创建临时二维码
	 * 
	 * @param scene_id 二维码详细信息（场景值ID,32位非0整型）
	 * @param expire   二维码有效期
	 * @return
	 * @throws Exception
	 */
	public JSONObject temporary(int scene_id, int expire) throws Exception {
		if (expire < 1 || expire > 2592000) {
			throw new Exception("有效时间无效");
		}
		return ticket("QR_SCENE", expire, scene_id);
	}

	/**
	 * 创建临时二维码
	 * 
	 * @param scene_str 二维码详细信息（场景值ID,字符串形式的ID，长度限制为1到64）
	 * @param expire    二维码有效期
	 * @param type      输出参数（可选值：url|ticket|image）
	 * @return
	 * @throws Exception
	 */
	public String temporary(String scene_str, int expire, String type) throws Exception {
		if (expire < 1 || expire > 2592000) {
			throw new Exception("有效时间无效");
		}
		JSONObject rs = ticket("QR_STR_SCENE", expire, scene_str);
		if (type.equals("url")) {
			return rs.getString("url");
		} else if (type.equals("ticket")) {
			return rs.getString("ticket");
		} else if (type.equals("image")) {
			return Http.get(String.format(ApiUrl.qrcodeShowUrl, rs.getString("ticket")));
		}
		return rs.toJSONString();

	}

	/**
	 * 创建临时二维码
	 * 
	 * @param scene_str 二维码详细信息（场景值ID,字符串形式的ID，长度限制为1到64）
	 * @param expire    二维码有效期
	 * @return
	 * @throws Exception
	 */
	public JSONObject temporary(String scene_str, int expire) throws Exception {
		if (expire < 1 || expire > 2592000) {
			throw new Exception("有效时间无效");
		}
		return ticket("QR_STR_SCENE", expire, scene_str);
	}

	/**
	 * 创建永久二维码
	 * 
	 * @param scene_id 二维码详细信息（场景值ID,32位非0整型）
	 * @param type     输出参数（可选值：url|ticket|image）
	 * @return
	 * @throws Exception
	 */
	public String forever(int scene_id, String type) throws Exception {
		JSONObject rs = ticket("QR_LIMIT_SCENE", scene_id);
		if (type.equals("url")) {
			return rs.getString("url");
		} else if (type.equals("ticket")) {
			return rs.getString("ticket");
		} else if (type.equals("image")) {
			return Http.get(String.format(ApiUrl.qrcodeShowUrl, rs.getString("ticket")));
		}
		return rs.toJSONString();
	}

	/**
	 * 创建永久二维码
	 * 
	 * @param scene_id 二维码详细信息（场景值ID,32位非0整型）
	 * @return
	 * @throws Exception
	 */
	public JSONObject forever(int scene_id) throws Exception {
		return ticket("QR_LIMIT_SCENE", scene_id);
	}

	/**
	 * 创建永久二维码
	 * 
	 * @param scene_str 二维码详细信息（场景值ID,字符串形式的ID，长度限制为1到64）
	 * @param type      输出参数（可选值：url|ticket|image）
	 * @return
	 * @throws Exception
	 */
	public String forever(String scene_str, String type) throws Exception {
		JSONObject rs = ticket("QR_LIMIT_STR_SCENE", scene_str);
		if (type.equals("url")) {
			return rs.getString("url");
		} else if (type.equals("ticket")) {
			return rs.getString("ticket");
		} else if (type.equals("image")) {
			return Http.get(String.format(ApiUrl.qrcodeShowUrl, rs.getString("ticket")));
		}
		return rs.toJSONString();
	}

	/**
	 * 创建永久二维码
	 * 
	 * @param scene_str 二维码详细信息（场景值ID,字符串形式的ID，长度限制为1到64）
	 * @return
	 * @throws Exception
	 */
	public JSONObject forever(String scene_str) throws Exception {
		return ticket("QR_LIMIT_STR_SCENE", scene_str);
	}

}
