package com.ebyte.officialAccount.message.type;

import java.util.HashMap;
import java.util.Map;

import com.ebyte.officialAccount.server.Server;
import com.ebyte.weixin.util.Util;

public class Text {

	public String setContent(String content) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("ToUserName", ReceiveMsg.get("FromUserName"));
		msg.put("FromUserName", ReceiveMsg.get("ToUserName"));
		msg.put("CreateTime", String.valueOf(Util.getCurrentTimestamp()));
		msg.put("MsgType", "text");
		msg.put("Content", content);
		return Util.mapToXml(msg);
	}
	
	public Map<String, String> setContent(String content,boolean CheckReceiveMsg) throws Exception {
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("CreateTime", String.valueOf(Util.getCurrentTimestamp()));
		msg.put("MsgType", "text");
		msg.put("Content", content);
		return msg;
	}
}
