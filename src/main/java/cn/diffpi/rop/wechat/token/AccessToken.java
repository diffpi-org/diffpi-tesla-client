package cn.diffpi.rop.wechat.token;


/**
 * 微信通用接口凭证
 * 
 * @author luoxun
 */
public class AccessToken {
	private String tokenId;
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;
	//凭证创建时间(时间戳)
	private long createtime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}
