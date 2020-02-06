package com.ebyte.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ebyte.miniprogram.Miniprogram;
import com.ebyte.officialAccount.OfficialAccount;
import com.ebyte.payment.Payment;
import com.ebyte.weixin.util.Config;

public class Wechat {

	/**
	 * 初始化微信公众号
	 * 
	 * @param appid     开发凭证
	 * @param secret    开发密钥
	 * @param scopes    授权方式
	 * @param oauth_url 网页授权回调地址
	 * @return
	 */
	public static OfficialAccount officialAccount(String appid, String secret, String scopes, String oauth_url) {
		System.setProperty("appid", appid);
		System.setProperty("secret", secret);
		System.setProperty("scopes", scopes);
		System.setProperty("oauth_url", oauth_url);
		return new OfficialAccount();
	}

	/**
	 * 初始化微信公众号
	 * 
	 * @param appid     开发凭证
	 * @param secret    开发密钥
	 * @param scopes    授权方式
	 * @param oauth_url 网页授权回调地址
	 * @param url       服务器地址
	 * @param token     令牌
	 * @param aes_key   消息加解密密钥
	 * @return
	 */
	public static OfficialAccount officialAccount(String appid, String secret, String scopes, String oauth_url,
			String url, String token, String aes_key) {
		Config.setAppid(appid);
		Config.setSecret(secret);
		Config.setScopes(scopes);
		Config.setOauthUrl(oauth_url);
		Config.setUrl(url);
		Config.setToken(token);
		Config.setAeskey(aes_key);
		return new OfficialAccount();
	}

	/**
	 * 初始化微信公众号
	 * 
	 * @param ResourceName 配置文件名称
	 * @return
	 */
	public static OfficialAccount officialAccount(String ResourceName) {
		InputStream in = Wechat.class.getClassLoader().getResourceAsStream(ResourceName);
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Config.setAppid(props.getProperty("appid"));
		Config.setSecret(props.getProperty("secret"));
		Config.setScopes(props.getProperty("scopes"));
		Config.setOauthUrl(props.getProperty("oauth_url"));
		Config.setUrl(props.getProperty("url"));
		Config.setToken(props.getProperty("token"));
		Config.setAeskey(props.getProperty("aes_key"));
		return new OfficialAccount();
	}

	/**
	 * 初始化微信公众号
	 * 
	 * @return
	 */
	public static OfficialAccount officialAccount() {
		return officialAccount("weixin.properties");
	}

	/**
	 * 初始化微信支付
	 * 
	 * @param appid
	 * @param secret
	 * @param mchid
	 * @param mchkey
	 * @param notify_url
	 * @return
	 */
	public static Payment payment(String appid, String secret, String mchid, String mchkey, String cert_path, String notify_url) {
		Config.setAppid(appid);
		Config.setSecret(secret);
		Config.setMchid(mchid);
		Config.setMchkey(mchkey);
		Config.setCertPath(cert_path);
		Config.setCertSecret(mchid);
		Config.setNotfyUrl(notify_url);
		return new Payment();
	}
	
	/**
	 * 初始化微信支付
	 * 
	 * @param appid
	 * @param secret
	 * @param mchid
	 * @param mchkey
	 * @param notify_url
	 * @return
	 */
	public static Payment payment(String appid, String secret, String mchid, String mchkey, String cert_path,
			String cert_secret, String notify_url) {
		Config.setAppid(appid);
		Config.setSecret(secret);
		Config.setMchid(mchid);
		Config.setMchkey(mchkey);
		Config.setCertPath(cert_path);
		Config.setCertSecret(cert_secret);
		Config.setNotfyUrl(notify_url);
		return new Payment();
	}

	/**
	 * 初始化微信支付
	 * 
	 * @param ResourceName 配置文件名称
	 * @return
	 */
	public static Payment payment(String ResourceName) {
		InputStream in = Wechat.class.getClassLoader().getResourceAsStream(ResourceName);
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Config.setAppid(props.getProperty("appid"));
		Config.setSecret(props.getProperty("secret"));
		Config.setMchid(props.getProperty("mch_id"));
		Config.setMchkey(props.getProperty("mch_key"));
		Config.setCertPath(props.getProperty("cert_path"));
		Config.setCertSecret(props.getProperty("cert_secret"));
		Config.setNotfyUrl(props.getProperty("notify_url"));
		return new Payment();
	}

	/**
	 * 初始化微信支付
	 * 
	 * @return
	 */
	public static Payment payment() {
		return payment("weixin.properties");
	}

	public static Miniprogram MiniPogram(String appid, String secret) {
		Config.setAppid(appid);
		Config.setSecret(secret);
		return new Miniprogram();
	}

}
