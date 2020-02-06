package com.ebyte.payment.notify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

import com.ebyte.payment.Factory;
import com.ebyte.weixin.util.Config;
import com.ebyte.weixin.util.Util;

/**
 * 通知类
 * 
 * @author xiebing
 *
 */
public class Notify extends Factory {

	private Map<String, String> getNotifyXML(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		InputStream in = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		return Util.xmlToMap(result.toString());
	}

	/**
	 * 统一下单结果通知
	 * 
	 * @param request
	 * @param response
	 * @param callback
	 * @throws Exception
	 */
	public void unify(HttpServletRequest request, HttpServletResponse response, NotifyCallback callback)
			throws Exception {
		Map<String, String> notify = getNotifyXML(request, response);
		try {
			if (!this.isPayResultNotifySignatureValid(notify)) {
				reply(response, notify, callback);
			} else {
				throw new Exception("统一下单支付回调签名异常");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 退款查询
	 * 
	 * @param request
	 * @param response
	 * @param notifyCallback
	 * @throws Exception
	 */
	public void refund(HttpServletRequest request, HttpServletResponse response, NotifyCallback callback)
			throws Exception {
		Map<String, String> notify = getNotifyXML(request, response);
		String reqInfo = notify.get("req_info");
		byte[] reqInfoB = Base64.decodeBase64(reqInfo.getBytes());
		String key_ = this.MD5(Config.getMchkey()).toLowerCase();
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
		SecretKeySpec secretKeySpec = new SecretKeySpec(key_.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		String result = new String(cipher.doFinal(reqInfoB));
		reply(response, Util.xmlToMap(result), callback);
	}


	/**
	 * 回复处理
	 * 
	 * @param response
	 * @param notify
	 * @param callback
	 */
	private void reply(HttpServletResponse response, Map<String, String> notify, NotifyCallback callback) {
		Map<String, String> rs = new HashMap<String, String>();
		callback.push(notify, new NotifyReply() {
			@Override
			public void fail(String return_msg) {
				rs.put("return_code", "FAIL");
				rs.put("return_msg", return_msg);
				try {
					response.getWriter().write(Util.mapToXml(rs));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void success() {
				rs.put("return_code", "SUCCESS");
				try {
					response.getWriter().write(Util.mapToXml(rs));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public interface NotifyCallback {
		void push(Map<String, String> notify, NotifyReply notifyReply);
	}

	public interface NotifyReply {
		void fail(String return_msg);

		void success();
	}

}
