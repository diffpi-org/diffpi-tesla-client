package cn.diffpi.rop.wechat.course.message.offer.customer.text;

import cn.diffpi.rop.wechat.course.message.offer.customer.CustomerBaseMessage;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.text.MassTextType;

/***
 * 客户文本消息
 * @author luoxun
 *
 */
public class CustTextMessage extends CustomerBaseMessage{
	private MassTextType text;//消息内容（此类与群发文本消息公用）

	public MassTextType getText() {
		return text;
	}

	public void setText(MassTextType text) {
		this.text = text;
	}
}
