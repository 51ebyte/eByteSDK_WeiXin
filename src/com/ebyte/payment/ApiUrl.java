package com.ebyte.payment;

import com.ebyte.weixin.util.Config;

/**
 * 接口地址类
 * 
 * @author xiebing
 *
 */
public class ApiUrl {

	private final static String SANDBOX = Config.getIsSANDBOX() ? "sandboxnew/" : "";

	public final static String getSandboxSignKeyUrl = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";

	private final static String DOMAMIN_API = "https://api.mch.weixin.qq.com/" + SANDBOX;

	/* 统一下单 */
	public final static String unifiedorderUrl = DOMAMIN_API + "pay/unifiedorder";
	/* 查询订单 */
	public final static String orderqueryUrl = DOMAMIN_API + "pay/orderquery";
	/* 关闭订单 */
	public final static String closeorderUrl = DOMAMIN_API + "pay/closeorder";
	/* 申请退款 */
	public final static String refundUrl = DOMAMIN_API + "secapi/pay/refund";
	/* 退款查询 */
	public final static String refundqueryUrl = DOMAMIN_API + "pay/refundquery";
	/* 对账单下载 */
	public final static String downloadbillUrl = DOMAMIN_API + "pay/downloadbill";
	/* 付款码支付 */
	public final static String micropayUrl = DOMAMIN_API + "pay/micropay";
	/* 授权码查询openid */
	public final static String authCodeOpenid = DOMAMIN_API + "tools/authcodetoopenid";
	/* 撤销订单 */
	public final static String reverseUrl = DOMAMIN_API + "secapi/pay/reverse";

}
