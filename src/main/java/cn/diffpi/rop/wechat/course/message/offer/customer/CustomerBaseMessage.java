package cn.diffpi.rop.wechat.course.message.offer.customer;

import cn.diffpi.rop.wechat.course.message.offer.OfferBaseMessage;

/***
 * 客户消息基类
 * @author luoxun
 *
 */
public class CustomerBaseMessage extends OfferBaseMessage{
	private String touser;//接收方的openid

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}
}	
