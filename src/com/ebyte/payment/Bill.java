package com.ebyte.payment;

import java.util.HashMap;
import java.util.Map;

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
	 * @param date
	 * @param type
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
		return this.requsetMap(Url.downloadbillUrl, data);
	}

	/**
	 * 下载对账单
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> get(String date) throws Exception {
		return get(date, "ALL");
	}

}
