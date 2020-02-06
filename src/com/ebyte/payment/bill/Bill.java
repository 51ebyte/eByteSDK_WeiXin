package com.ebyte.payment.bill;

import java.util.HashMap;
import java.util.Map;

import com.ebyte.payment.ApiUrl;
import com.ebyte.payment.Factory;
import com.ebyte.weixin.util.Config;

/**
 * 账单类
 * 
 * @author xiebing
 *
 */
public class Bill extends Factory{

	/**
	 * 下载对账单
	 * @param date 下载对账单的日期，格式：20140603
	 * @param type 账单类型ALL（默认值），返回当日所有订单信息（不含充值退款订单）<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SUCCESS，返回当日成功支付的订单（不含充值退款订单）<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;REFUND，返回当日退款订单（不含充值退款订单）<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RECHARGE_REFUND，返回当日充值退款订单
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> get(String date, String type) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", Config.getAppid());
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		data.put("bill_date", date);
		data.put("bill_type", type);
		data.put("sign", this.generateSignature(data));
		return this.requsetMap(ApiUrl.downloadbillUrl, data);
	}

	/**
	 * 下载对账单
	 * @param date 下载对账单的日期，格式：20140603
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> get(String date) throws Exception {
		return get(date, "ALL");
	}

}
