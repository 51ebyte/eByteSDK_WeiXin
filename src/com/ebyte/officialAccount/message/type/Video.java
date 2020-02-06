package com.ebyte.officialAccount.message.type;

import java.util.Map;

import com.ebyte.officialAccount.server.Server;
import com.ebyte.weixin.util.Util;

public class Video {

	public String setMediaId(String mediaId) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
				"<xml>\r\n" + 
				"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"></ToUserName>\r\n" + 
				"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"></FromUserName>\r\n" + 
				"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
				"  <MsgType>video</MsgType>\r\n" + 
				"  <Image>\r\n" + 
				"    <MediaId>"+mediaId+"</MediaId>\r\n" + 
				"  </Image>\r\n" + 
				"</xml>";
	}
	
	public String setVideo(String mediaId,String title) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
				"<xml>\r\n" + 
				"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"></ToUserName>\r\n" + 
				"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"></FromUserName>\r\n" + 
				"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
				"  <MsgType>video</MsgType>\r\n" + 
				"  <Image>\r\n" + 
				"    <MediaId>"+mediaId+"</MediaId>\r\n" + 
				"    <Title>"+title+"</Title>\r\n" + 
				"  </Image>\r\n" + 
				"</xml>";
	}
	
	public String setVideo(String mediaId,String title,String description) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
				"<xml>\r\n" + 
				"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"></ToUserName>\r\n" + 
				"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"></FromUserName>\r\n" + 
				"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
				"  <MsgType>video</MsgType>\r\n" + 
				"  <Image>\r\n" + 
				"    <MediaId>"+mediaId+"</MediaId>\r\n" + 
				"    <Title>"+title+"</Title>\r\n" + 
				"    <Description>"+description+"</Description>\r\n" + 
				"  </Image>\r\n" + 
				"</xml>";
	}

}
