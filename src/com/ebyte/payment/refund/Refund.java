package com.ebyte.payment.refund;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ebyte.payment.ApiUrl;
import com.ebyte.payment.Factory;
import com.ebyte.payment.Payment;
import com.ebyte.weixin.util.Config;
import com.ebyte.weixin.util.Http;
import com.ebyte.weixin.util.Util;

/**
 * 退款类
 * @author xiebing
 *
 */
public class Refund extends Factory{

	/**
	 * 执行退款
	 * @param isOutTradeNo
	 * @param orderId
	 * @param refundNumber
	 * @param totalFee
	 * @param refundFee
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> byRefund(Boolean isOutTradeNo, String orderId, String refundNumber, int totalFee,
			int refundFee, Map<String, String> config) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		data.put("sign_type", Payment.SignType.MD5.toString());
		if (isOutTradeNo == true) {
			data.put("out_trade_no", orderId);
		} else {
			data.put("transaction_id", orderId);
		}
		data.put("out_refund_no", refundNumber);
		data.put("total_fee", String.valueOf(totalFee));
		data.put("refund_fee", String.valueOf(refundFee));
		data.put("out_refund_no", refundNumber);
		if (config!=null) {
			Set<String> configSet = config.keySet();
			String[] configArray = configSet.toArray(new String[configSet.size()]);
			for (String k : configArray) {
				data.put(k, config.get(k));
			}
		}
		System.out.println("a");
		data.put("sign", this.generateSignature(data, Config.getMchkey()));
		Map<String, String> result = Util.xmlToMap(Http.post(ApiUrl.refundUrl, Util.mapToXml(data), true));
		if (result.get("return_code").equals("FAIL")) {
			throw new Exception(result.get("return_msg"));
		} else if (result.get("result_code").equals("FAIL")) {
			throw new Exception(result.get("err_code_des"));
		}
		return result;
	}

	/**
	 * 申请退款
	 * 
	 * @param transactionId 微信订单号
	 * @param refundNumber  商户退款单号
	 * @param totalFee      订单金额
	 * @param refundFee     退款金额
	 * @param config        其他配置
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> byTransactionId(String transactionId, String refundNumber, int totalFee, int refundFee,
			Map<String, String> config) throws Exception {
		return byRefund(false, transactionId, refundNumber, totalFee, refundFee, config);
	}

	/**
	 * 申请退款
	 * 
	 * @param outTradeNo   商户订单号
	 * @param refundNumber 商户退款单号
	 * @param totalFee     订单金额
	 * @param refundFee    退款金额
	 * @param config       其他配置
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> byOutTradeNumber(String outTradeNo, String refundNumber, int totalFee, int refundFee,
			Map<String, String> config) throws Exception {
		return byRefund(true, outTradeNo, refundNumber, totalFee, refundFee, config);
	}
	

	/**
	 * 查询退款
	 * @param orderId
	 * @param type
	 * @throws Exception
	 */
	private void query(String orderId, String type) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		data.put(type, orderId);
		data.put("sign", this.generateSignature(data, Config.getMchkey()));
	}

	/**
	 * 根据微信订单号查询退款
	 * 
	 * @param transactionId
	 * @throws Exception
	 */
	public void queryByTransactionId(String transactionId) throws Exception {
		query(transactionId, "transactionId");
	}

	/**
	 * 根据商户订单号查询退款
	 * 
	 * @param transactionId
	 * @throws Exception
	 */
	public void queryByOutTradeNumber(String outTradeNumber) throws Exception {
		query(outTradeNumber, "outTradeNumber");
	}

	/**
	 * 根据商户退款单号查询退款
	 * 
	 * @param transactionId
	 * @throws Exception
	 */
	public void queryByOutRefundNumber(String outRefundNumber) throws Exception {
		query(outRefundNumber, "outRefundNumber");
	}

	/**
	 * 根据微信退款单号查询退款
	 * 
	 * @param transactionId
	 * @throws Exception
	 */
	public void queryByRefundId(String refundId) throws Exception {
		query(refundId, "refundId");
	}

}
