package com.ebyte.officialAccount.message.type;

import java.util.ArrayList;
import java.util.Map;

import com.ebyte.officialAccount.server.Server;
import com.ebyte.weixin.util.Util;

public class News {

	public String set(ArrayList<Map<String, Object>> list) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		String xml= "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
			"<xml>\r\n" + 
			"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"</ToUserName>\r\n" + 
			"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"</FromUserName>\r\n" + 
			"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
			"  <MsgType>news</MsgType>\r\n" + 
			"  <ArticleCount>"+list.size()+"</ArticleCount>\r\n" + 
			"  <Articles>\r\n";
		
		for (Map<String, Object> item : list) {
			xml+="<item>";
            for (String key : item.keySet()) {
    			String value = item.get(key).toString().trim();
    			xml+="<"+key+">"+value+"</"+key+">";
    		}
            xml+="</item>";
        }
		xml+="  </Articles>\r\n" + 
			"</xml>";
		return xml;
	}

	public String set(Map<String, Object> item) throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(item);
		return set(list);
	}

}
