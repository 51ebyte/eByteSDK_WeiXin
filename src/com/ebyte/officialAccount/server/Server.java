package com.ebyte.officialAccount.server;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebyte.officialAccount.Factory;
import com.ebyte.officialAccount.message.Message;

public class Server extends Factory {

	private static final Message reply = new Message();
	public static Map<String, String> ReceiveMsg;

	/**
	 * 基本使用(闭包调用)
	 * 
	 * @param request
	 * @param response
	 * @param messageCallback
	 * @throws Exception
	 */
	public void push(HttpServletRequest request, HttpServletResponse response, ServerCallback callback)
			throws Exception {
		ReceiveMsg = getInputXml(request, response);
		String notify = callback.push(ReceiveMsg, reply);
		if (notify == null) {
			response.getWriter().write("SUCCESS");
		} else {
			response.getWriter().write(notify);
		}
	}

	/**
	 * 获取接收普通消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getInputXml(request, response);
	}

	public interface ServerCallback {
		String push(Map<String, String> message, Message reply);
	}

}
