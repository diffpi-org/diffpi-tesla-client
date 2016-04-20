package cn.diffpi.rop.wechat.course.message.offer.mass.openid;

import java.util.List;

import cn.diffpi.rop.wechat.course.message.offer.OfferBaseMessage;

/***
 * 群发消息基类(根据openId列表进行群发)
 * @author luoxun
 *
 */
public class MassBaseMessage extends OfferBaseMessage{
	private List<String> touser;//群发列表（一个openid列表）
	
	public List<String> getTouser() {
		return touser;
	}
	public void setTouser(List<String> touser) {
		this.touser = touser;
	}
	
}
