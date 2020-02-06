package com.ebyte.payment;

import java.util.HashMap;
import java.util.Map;

import com.ebyte.weixin.util.Config;
import com.ebyte.weixin.util.Util;

/**
 * 支付基类
 * 
 * @author Administrator
 *
 */
public class Payment extends Factory {

	public Bill bill = new Bill();
	public Pay pay = new Pay();
	public Order order = new Order();
	public Notify notify = new Notify();
	public Refund refund = new Refund();

	/**
	 * 开启/关闭沙箱状态
	 * 
	 * @param state
	 */
	public void setSANDBOX(boolean state) {
		Config.setIsSANDBOX(state);
	}

	/**
	 * 开启沙箱状态
	 * 
	 * @throws Exception
	 */
	public void setSANDBOX() throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("mch_id", Config.getMchid());
		data.put("nonce_str", this.generateNonceStr());
		data.put("sign", this.generateSignature(data));
		Map<String, String> result = Util.xmlToMap(Util.httpPost(Url.getSandboxSignKeyUrl, Util.mapToXml(data)));
		Config.setMchkey(result.get("sandbox_signkey"));
		Config.setIsSANDBOX(true);
	}
}
