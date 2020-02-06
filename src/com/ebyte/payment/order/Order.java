package com.ebyte.payment.order;

import java.util.HashMap;
import java.util.Map;

import com.ebyte.payment.Factory;
import com.ebyte.payment.ApiUrl;
import com.ebyte.payment.order.reverse.Reverse;
import com.ebyte.weixin.util.Config;

/**
 * 订单类
 * 
 * @author xiebing
 *
 */
public class Order extends Factory {

	public Reverse reverse=new Reverse();

	/**
	 * 统一下单
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> unify(Map<String, String> data) throws Exception {
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		data.put("sign_type", Factory.SignType.MD5.toString());
		String signature = this.generateSignature(data);
		data.put("sign", signature);
		return this.requsetMap(ApiUrl.unifiedorderUrl, data);
	}


	/* 查询订单 */
	private Map<String, String> query(String orderId, boolean isOutTradeNo) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		if (isOutTradeNo) {
			data.put("out_trade_no", orderId);
		} else {
			data.put("transaction_id", orderId);
		}
		data.put("sign", this.generateSignature(data, Config.getMchkey()));
		return this.requsetMap(ApiUrl.orderqueryUrl, data);
	}

	/**
	 * 查询订单
	 * 
	 * @param outTradeNo 商户订单号
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryByOutTradeNumber(String outTradeNo) throws Exception {
		return query(outTradeNo, true);
	}

	/**
	 * 查询订单
	 * 
	 * @param transactionId 微信订单号
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryByTransactionId(String transactionId) throws Exception {
		return query(transactionId, false);
	}

	/**
	 * 关闭订单
	 * 
	 * @param outTradeNo 商户订单号
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> close(String outTradeNo) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		data.put("out_trade_no", outTradeNo);
		data.put("sign", this.generateSignature(data, Config.getMchkey()));
		return this.requsetMap(ApiUrl.closeorderUrl, data);
	}

}









