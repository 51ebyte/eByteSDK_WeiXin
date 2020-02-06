package com.ebyte.payment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.ebyte.weixin.util.Config;
import com.ebyte.weixin.util.Util;

public class Factory {

	protected enum SignType {
		MD5, HMACSHA256
	}

	protected static final String FIELD_SIGN = "sign";
	protected static final String FIELD_SIGN_TYPE = "sign_type";

	protected static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	protected static final Random RANDOM = new SecureRandom();

	/**
	 * 生成签名
	 *
	 * @param data 待签名数据
	 * @return 签名
	 */
	protected String generateSignature(final Map<String, String> data) throws Exception {
		return generateSignature(data, Config.getMchkey(), SignType.MD5);
	}

	/**
	 * 生成签名
	 *
	 * @param data     待签名数据
	 * @param signType 签名方式
	 * @return 签名
	 */
	protected String generateSignature(final Map<String, String> data, SignType signType) throws Exception {
		return generateSignature(data, Config.getMchkey(), signType);
	}

	/**
	 * 生成签名
	 *
	 * @param data 待签名数据
	 * @param key  API密钥
	 * @return 签名
	 */
	protected String generateSignature(final Map<String, String> data, String key) throws Exception {
		return generateSignature(data, key, SignType.MD5);
	}

	/**
	 * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
	 *
	 * @param data     待签名数据
	 * @param key      API密钥
	 * @param signType 签名方式
	 * @return 签名
	 */
	protected String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
		Set<String> keySet = data.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		StringBuilder sb = new StringBuilder();
		for (String k : keyArray) {
			if (k.equals("sign")) {
				continue;
			}
			if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
				sb.append(k).append("=").append(data.get(k).trim()).append("&");
		}
		sb.append("key=").append(key);
		if (SignType.MD5.equals(signType)) {
			return MD5(sb.toString()).toUpperCase();
		} else if (SignType.HMACSHA256.equals(signType)) {
			return HMACSHA256(sb.toString(), key);
		} else {
			throw new Exception(String.format("签名类型无效: %s", signType));
		}
	}

	/**
	 * 数组ASCII升序
	 * 
	 * @param data
	 * @return
	 */
	protected String[] ASCIISort(Map<String, String> data) {
		Set<String> keySet = data.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		return keyArray;
	}

	/**
	 * 判断签名是否正确
	 *
	 * @param xmlStr XML格式数据
	 * @param key    API密钥
	 * @return 签名是否正确
	 * @throws Exception
	 */
	protected boolean isSignatureValid(String xmlStr, String key) throws Exception {
		Map<String, String> data = Util.xmlToMap(xmlStr);
		if (!data.containsKey(FIELD_SIGN)) {
			return false;
		}
		String sign = data.get(FIELD_SIGN);
		return generateSignature(data, key).equals(sign);
	}

	/**
	 * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
	 *
	 * @param data Map类型数据
	 * @param key  API密钥
	 * @return 签名是否正确
	 * @throws Exception
	 */
	protected boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
		return isSignatureValid(data, key, SignType.MD5);
	}

	/**
	 * 判断签名是否正确，必须包含sign字段，否则返回false。
	 *
	 * @param data     Map类型数据
	 * @param key      API密钥
	 * @param signType 签名方式
	 * @return 签名是否正确
	 * @throws Exception
	 */
	protected boolean isSignatureValid(Map<String, String> data, String key, SignType signType) throws Exception {
		if (!data.containsKey(FIELD_SIGN)) {
			return false;
		}
		String sign = data.get(FIELD_SIGN);
		return generateSignature(data, key, signType).equals(sign);
	}

	/**
	 * 生成 MD5
	 *
	 * @param data 待处理数据
	 * @return MD5结果
	 */
	protected String MD5(String data) throws Exception {
		java.security.MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 生成 HMACSHA256
	 * 
	 * @param data 待处理数据
	 * @param key  密钥
	 * @return 加密结果
	 * @throws Exception
	 */
	protected String HMACSHA256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 生成随机字符串
	 * 
	 * @return String 随机字符串
	 */
	protected String generateNonceStr() {
		char[] nonceChars = new char[32];
		for (int index = 0; index < nonceChars.length; ++index) {
			nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
		}
		return new String(nonceChars);
	}

	/**
	 * 获取当前时间戳，单位秒
	 * 
	 * @return
	 */
	protected long getCurrentTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取当前时间戳，单位毫秒
	 * 
	 * @return
	 */
	protected long getCurrentTimestampMs() {
		return System.currentTimeMillis();
	}

	/**
	 * 判断支付结果通知中的sign是否有效
	 *
	 * @param requset_data 向wxpay post的请求数据
	 * @return 签名是否有效
	 * @throws Exception
	 */
	@SuppressWarnings("unlikely-arg-type")
	protected boolean isPayResultNotifySignatureValid(Map<String, String> requset_data) throws Exception {
		String signTypeData = requset_data.get(FIELD_SIGN_TYPE);
		SignType signType;
		if (signTypeData == null) {
			signType = SignType.MD5;
		} else {
			signTypeData = signTypeData.trim();
			if (signTypeData.length() == 0) {
				signType = SignType.MD5;
			} else if (SignType.MD5.equals(signTypeData)) {
				signType = SignType.MD5;
			} else if (SignType.HMACSHA256.equals(signTypeData)) {
				signType = SignType.HMACSHA256;
			} else {
				throw new Exception(String.format("不支持sign_type: %s", signTypeData));
			}
		}
		return isSignatureValid(requset_data, Config.getMchkey(), signType);
	}

	/**
	 * 读取密钥内容
	 * 
	 * @return
	 * @throws IOException
	 */
	protected InputStream getCertStream() throws IOException {
		String certPath = "/path/to/apiclient_cert.p12";
		File file = new File(certPath);
		InputStream certStream = new FileInputStream(file);
		byte[] certData = new byte[(int) file.length()];
		certStream.read(certData);
		certStream.close();
		return certStream;
	}

	/**
	 * 发送POST请求,并且携带XML参数
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> requsetMap(String url, Map<String, String> data) throws Exception {
		Map<String, String> result = Util.xmlToMap(Util.httpPost(url, Util.mapToXml(data)));
		if (result.get("return_code").equals("FAIL")) {
			String message = result.get("return_msg") != null ? result.get("return_msg") : result.get("retmsg");
			throw new Exception(message);
		} else if (result.get("result_code").equals("FAIL")) {
			throw new Exception(result.get("err_code_des"));
		}
		return result;
	}

	/**
	 * 授权码查询openid
	 * @param authCode
	 * @return
	 * @throws Exception
	 */
	protected String authCodeOpenid(String authCode) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", Config.getAppid());
		map.put("mch_id", Config.getMchid());
		map.put("auth_code", authCode);
		map.put("nonce_str", generateNonceStr());
		map.put("sign", generateSignature(map));
		Map<String, String> rs = requsetMap(ApiUrl.authCodeOpenid, map);
		return rs.get("openid");
	}

}
