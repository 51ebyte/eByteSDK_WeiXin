package com.ebyte.officialAccount.server;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebyte.officialAccount.Factory;
import com.ebyte.officialAccount.message.Message;

public class Server extends Factory {

	public static Map<String, String> ReceiveMsg;

	/**
	 * 基本使用(闭包调用)
	 * 
	 * @param request
	 * @param response
	 * @param callback ServerCallback
	 * @throws Exception
	 */
	public void push(HttpServletRequest request, HttpServletResponse response, ServerCallback callback) throws Exception {
		ReceiveMsg=getInputXml(request, response);
		callback.push(ReceiveMsg);
		response.getWriter().write("SUCCESS");
	}

	/**
	 * 基本使用(闭包调用、消息回复)
	 * 
	 * @param request
	 * @param response
	 * @param callback ServerReplyCallback
	 * @throws Exception
	 */
	public void push(HttpServletRequest request, HttpServletResponse response, ServerReplyCallback callback) throws Exception {
		ReceiveMsg = getInputXml(request, response);
		String notify = callback.push(ReceiveMsg, new Message());
		if (notify == null) {
			response.getWriter().write("SUCCESS");
		} else {
			response.getWriter().write(notify);
		}
	}

	/**
	 * 接收事件消息(闭包调用)
	 * 
	 * @param request
	 * @param response
	 * @param callback ServerEventCallback
	 * @throws Exception
	 */
	public void push(HttpServletRequest request, HttpServletResponse response, ServerEventCallback callback) throws Exception {
		ReceiveMsg=getInputXml(request, response);
		if (ReceiveMsg.get("MsgType").equals("event")) {
			callback.push(ReceiveMsg);
		}
		response.getWriter().write("SUCCESS");
	}

	/**
	 * 接收事件消息(闭包调用)
	 * 
	 * @param request
	 * @param response
	 * @param callback ServerEventReplyCallback
	 * @throws Exception
	 */
	public void push(HttpServletRequest request, HttpServletResponse response, ServerEventReplyCallback callback) throws Exception {
		ReceiveMsg = getInputXml(request, response);
		if (ReceiveMsg.get("MsgType").equals("event")) {
			String notify = callback.push(ReceiveMsg, new Message());
			if (notify == null) {
				response.getWriter().write("SUCCESS");
			} else {
				response.getWriter().write(notify);
			}
		}
	}
	
	/**
	 * 接收地理位置消息(闭包调用)
	 * 
	 * @param request
	 * @param response
	 * @param callback ServerEventReplyCallback
	 * @throws Exception
	 */
	public void push(HttpServletRequest request, HttpServletResponse response, ServerLocationCallback callback) throws Exception {
		ReceiveMsg = getInputXml(request, response);
		if (ReceiveMsg.get("MsgType").toUpperCase().equals("LOCATION")) {
			callback.push(ReceiveMsg);
		}
		response.getWriter().write("SUCCESS");
	}

	public interface ServerCallback {
		void push(Map<String, String> message);
	}

	public interface ServerReplyCallback {
		String push(Map<String, String> message, Message reply);
	}

	public interface ServerEventCallback {
		void push(Map<String, String> message);
	}

	public interface ServerEventReplyCallback {
		String push(Map<String, String> message, Message reply);
	}
	
	public interface ServerLocationCallback {
		void push(Map<String, String> message);
	}
	

}
