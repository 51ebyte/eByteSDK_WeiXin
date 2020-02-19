package com.ebyte.payment;

import java.util.HashMap;
import java.util.Map;

import com.ebyte.payment.bill.Bill;
import com.ebyte.payment.notify.Notify;
import com.ebyte.payment.order.Order;
import com.ebyte.payment.pay.Pay;
import com.ebyte.payment.refund.Refund;
import com.ebyte.weixin.util.*;

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
		data.put("nonce_str", generateNonceStr());
		data.put("sign", generateSignature(data));
		Map<String, String> result = Util.xmlToMap(Http.post(ApiUrl.getSandboxSignKeyUrl, Util.mapToXml(data)));
		Config.setMchkey(result.get("sandbox_signkey"));
		Config.setIsSANDBOX(true);
	}
}
