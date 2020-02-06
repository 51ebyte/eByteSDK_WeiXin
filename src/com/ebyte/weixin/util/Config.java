package com.ebyte.weixin.util;

public class Config {

	private static boolean debug;

	private static String appid;
	private static String secret;
	private static String scopes;
	private static String oauthUrl;
	private static String url;
	private static String token;
	private static String aeskey;

	private static String mchid;
	private static String mchkey;
	private static String notfyUrl;
	private static String certPath;
	private static String certSecret;
	private static Boolean isSANDBOX = false;
	
	public static String getCertSecret() {
		return certSecret;
	}

	public static void setCertSecret(String certSecret) {
		Config.certSecret = certSecret;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		Config.debug = debug;
	}

	public static String getAppid() {
		return appid;
	}

	public static void setAppid(String appid) {
		Config.appid = appid;
	}

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Config.secret = secret;
	}

	public static String getScopes() {
		return scopes;
	}

	public static void setScopes(String scopes) {
		Config.scopes = scopes;
	}

	public static String getOauthUrl() {
		return oauthUrl;
	}

	public static void setOauthUrl(String oauthUrl) {
		Config.oauthUrl = oauthUrl;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		Config.url = url;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		Config.token = token;
	}

	public static String getAeskey() {
		return aeskey;
	}

	public static void setAeskey(String aeskey) {
		Config.aeskey = aeskey;
	}

	public static String getMchid() {
		return mchid;
	}

	public static void setMchid(String mchid) {
		Config.mchid = mchid;
	}

	public static String getMchkey() {
		return mchkey;
	}

	public static void setMchkey(String mchkey) {
		Config.mchkey = mchkey;
	}

	public static String getNotfyUrl() {
		return notfyUrl;
	}

	public static void setNotfyUrl(String notfyUrl) {
		Config.notfyUrl = notfyUrl;
	}

	public static Boolean getIsSANDBOX() {
		return isSANDBOX;
	}

	public static void setIsSANDBOX(Boolean isSANDBOX) {
		Config.isSANDBOX = isSANDBOX;
	}

	public static String getCertPath() {
		return certPath;
	}

	public static void setCertPath(String certPath) {
		Config.certPath = certPath;
	}

}
