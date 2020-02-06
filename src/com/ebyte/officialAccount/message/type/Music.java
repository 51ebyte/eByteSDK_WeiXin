package com.ebyte.officialAccount.message.type;

import java.util.Map;

import com.ebyte.officialAccount.server.Server;
import com.ebyte.weixin.util.Util;

public class Music {

	public String setMediaId(String thumbMediaId) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
			"<xml>\r\n" + 
			"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"</ToUserName>\r\n" + 
			"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"</FromUserName>\r\n" + 
			"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
			"  <MsgType>music</MsgType>\r\n" + 
			"  <Music>\r\n" + 
			"    <ThumbMediaId>"+thumbMediaId+"</ThumbMediaId>\r\n" + 
			"  </Music>\r\n" + 
			"</xml>";
	}

	public String setMusic(String thumbMediaId, String title) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
			"<xml>\r\n" + 
			"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"</ToUserName>\r\n" + 
			"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"</FromUserName>\r\n" + 
			"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
			"  <MsgType>music</MsgType>\r\n" + 
			"  <Music>\r\n" + 
			"    <Title>"+title+"</Title>\r\n" + 
			"    <ThumbMediaId>"+thumbMediaId+"</ThumbMediaId>\r\n" + 
			"  </Music>\r\n" + 
			"</xml>";
	}
	public String setMusic(String thumbMediaId, String title, String description) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
			"<xml>\r\n" + 
			"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"</ToUserName>\r\n" + 
			"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"</FromUserName>\r\n" + 
			"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
			"  <MsgType>music</MsgType>\r\n" + 
			"  <Music>\r\n" + 
			"    <Title>"+title+"</Title>\r\n" + 
			"    <Description>"+description+"</Description>\r\n" + 
			"    <ThumbMediaId>"+thumbMediaId+"</ThumbMediaId>\r\n" + 
			"  </Music>\r\n" + 
			"</xml>";
	}
	public String setMusic(String thumbMediaId, String title, String description, String musicURL) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
			"<xml>\r\n" + 
			"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"</ToUserName>\r\n" + 
			"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"</FromUserName>\r\n" + 
			"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
			"  <MsgType>music</MsgType>\r\n" + 
			"  <Music>\r\n" + 
			"    <Title>"+title+"</Title>\r\n" + 
			"    <Description>"+description+"</Description>\r\n" + 
			"    <MusicUrl>"+musicURL+"</MusicUrl>\r\n" + 
			"    <ThumbMediaId>"+thumbMediaId+"</ThumbMediaId>\r\n" + 
			"  </Music>\r\n" + 
			"</xml>";
	}
	public String setMusic(String thumbMediaId, String title, String description, String musicURL, String hQMusicUrl) throws Exception {
		Map<String, String> ReceiveMsg = Server.ReceiveMsg;
		if (ReceiveMsg == null) {
			throw new Exception("接收消息异常");
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
				"<xml>\r\n" + 
				"  <ToUserName>"+ReceiveMsg.get("FromUserName")+"</ToUserName>\r\n" + 
				"  <FromUserName>"+ReceiveMsg.get("ToUserName")+"</FromUserName>\r\n" + 
				"  <CreateTime>"+String.valueOf(Util.getCurrentTimestamp())+"</CreateTime>\r\n" + 
				"  <MsgType>music</MsgType>\r\n" + 
				"  <Music>\r\n" + 
				"    <Title>"+title+"</Title>\r\n" + 
				"    <Description>"+description+"</Description>\r\n" + 
				"    <MusicUrl>"+musicURL+"</MusicUrl>\r\n" + 
				"    <HQMusicUrl>"+hQMusicUrl+"</HQMusicUrl>\r\n" + 
				"    <ThumbMediaId>"+thumbMediaId+"</ThumbMediaId>\r\n" + 
				"  </Music>\r\n" + 
				"</xml>";
	}


}
