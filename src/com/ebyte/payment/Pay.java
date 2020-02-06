package com.ebyte.payment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebyte.weixin.util.Config;
import com.ebyte.weixin.util.Util;

/**
 * 支付类
 * 
 * @author xiebing
 *
 */

public class Pay extends Factory {

	/**
	 * 微信公众号、小程序支付
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> JSAPI(Map<String, String> data) throws Exception {
		data.put("trade_type", "JSAPI");
		if (data.get("openid") == null) {
			throw new Exception("openID必须");
		}

		Map<String, String> result = new Order().unify(data);
		Map<String, String> results = new HashMap<String, String>();
		results.put("appId", result.get("appid"));
		results.put("timeStamp", String.valueOf(this.getCurrentTimestamp()));
		results.put("nonceStr", result.get("nonce_str"));
		results.put("package", "prepay_id=" + result.get("prepay_id"));
		results.put("signType", Payment.SignType.MD5.toString());
		results.put("paySign", this.generateSignature(results, Config.getMchkey()));
		return results;
	}

	/**
	 * H5支付(打开支付页面)
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param data     订单参数
	 * @throws Exception
	 */
	public void H5(HttpServletRequest request, HttpServletResponse response, Map<String, String> data)
			throws Exception {
		data.put("trade_type", "MWEB");
		data.put("spbill_create_ip", Util.getClientIp(request));
		Map<String, String> result = new Order().unify(data);
		response.sendRedirect(result.get("mweb_url"));
	}

	/**
	 * H5支付 (不打开支付页面，返回支付链接)
	 * 
	 * @param request HttpServletRequest
	 * @param data    订单参数
	 * @return
	 * @throws Exception
	 */
	public String H5(HttpServletRequest request, Map<String, String> data) throws Exception {
		data.put("trade_type", "MWEB");
		data.put("spbill_create_ip", Util.getClientIp(request));
		return new Order().unify(data).get("mweb_url");
	}

	/**
	 * NATIVE支付(模式二)
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String NATIVE(Map<String, String> data) throws Exception {
		data.put("trade_type", "NATIVE");
		if (data.get("product_id") == null) {
			throw new Exception("商品ID必须");
		}
		if (data.get("product_id").length() > 32) {
			throw new Exception("商品ID长度异常");
		}
		data.remove("openid");
		return new Order().unify(data).get("code_url");
	}

	/**
	 * NATIVE支付(模式一)
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String NATIVE(String productId) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("time_stamp", String.valueOf(this.getCurrentTimestamp()));
		data.put("nonce_str", this.generateNonceStr());
		data.put("product_id", productId);
		String[] keyArray = this.ASCIISort(data);
		StringBuilder sb = new StringBuilder();
		for (String k : keyArray) {
			if (data.get(k).trim().length() > 0) {
				sb.append(k).append("=").append(data.get(k).trim()).append("&");
			}
		}
		sb.append("sign=").append(this.generateSignature(data));
		return "weixin://wxpay/bizpayurl?" + sb.toString();
	}

	/**
	 * APP支付
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> APP(Map<String, String> data) throws Exception {
		data.put("trade_type", "APP");
		Map<String, String> result = new Order().unify(data);
		Map<String, String> results = new HashMap<String, String>();
		results.put("appId", Config.getAppid());
		results.put("partnerId", Config.getMchid());
		results.put("prepayId", result.get("prepay_id"));
		results.put("package", "Sign=WXPay");
		results.put("nonceStr", this.generateNonceStr());
		results.put("timeStamp", String.valueOf(this.getCurrentTimestamp()));
		results.put("sign", this.generateSignature(results));
		return results;
	}

	/**
	 * 付款码支付
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> MICROPAY(Map<String, String> data) throws Exception {
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		data.put("spbill_create_ip", Util.getClientIp());
		data.put("sign", this.generateSignature(data));
		Map<String, String> result = Util.xmlToMap(Util.httpPost(Url.micropayUrl, Util.mapToXml(data)));
		if (result.get("return_code").equals("FAIL")) {
			String message = result.get("return_msg") != null ? result.get("return_msg") : result.get("retmsg");
			throw new Exception(message);
		} else if (result.get("result_code").equals("FAIL")) {
			throw new Exception(result.get("err_code_des"));
		}
		return result;
	}
}
