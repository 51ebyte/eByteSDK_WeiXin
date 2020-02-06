package com.main;

import java.util.Arrays;

import com.ebyte.payment.Payment;
import com.ebyte.weixin.Wechat;

public class Index {
	static String appid = "wxc0ae6e64361f2df5";
	static String secret = "ead02f9b44240da485d71cb286f564ed";
	static String mchid = "1239809802";
	static String mchkey = "FC51C09DA4607844D6723B817E25822B";
	static String notify_url = "http://www.sunlue.com/weixin/pay/notify";
	static String cert_path = "apiclient_cert.p12";

	public static Payment payment = Wechat.payment(appid, secret, mchid, mchkey,cert_path, notify_url);
	public static void main(String[] args) throws Exception {
//		payment.setSANDBOX();
//		payment.refund.byTransactionId("4200000483202001173407011609", "4200000483202001173", 2, 1, null);
		
		String[] arr = new String[] { "token", "timestamp", "nonce" };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		System.out.println(content.toString());
		
	}
}
