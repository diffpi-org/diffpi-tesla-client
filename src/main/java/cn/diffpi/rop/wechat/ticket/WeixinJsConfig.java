package cn.diffpi.rop.wechat.ticket;

/***
 * js配置信息
 * @author one__
 *
 */
public class WeixinJsConfig {
	//appid
	private String appId;
	//时间戳
	private String timestamp;
	//签名的随机串
	private String nonceStr;
	//签名
	private String signature;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
