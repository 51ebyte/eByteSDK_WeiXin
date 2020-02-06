package com.ebyte.payment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebyte.weixin.util.Util;

/**
 * 通知类
 * @author xiebing
 *
 */
public class Notify extends Factory {

	/**
	 * 统一下单回调
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> unify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		InputStream in = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		try {
			Map<String, String> notifyMap = Util.xmlToMap(result.toString());
			if (!this.isPayResultNotifySignatureValid(notifyMap)) {
				return notifyMap;
			} else {
				throw new Exception("统一下单支付回调签名异常");
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
