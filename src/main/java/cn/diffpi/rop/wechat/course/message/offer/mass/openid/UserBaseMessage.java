package cn.diffpi.rop.wechat.course.message.offer.mass.openid;

import cn.diffpi.rop.wechat.course.message.offer.OfferBaseMessage;

/***
 * 群发消息基类(根据openId列表进行群发)
 * @author luoxun
 *
 */
public class UserBaseMessage extends OfferBaseMessage{
	private String touser;//群发列表（一个openid列表）
	private String agentid;
	private String toparty;
	private String totag;
	private String safe;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getTotag() {
		return totag;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}

	public String getSafe() {
		return safe;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}
	
	
}
