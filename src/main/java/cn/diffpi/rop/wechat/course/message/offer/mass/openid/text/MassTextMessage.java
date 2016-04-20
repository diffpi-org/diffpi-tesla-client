package cn.diffpi.rop.wechat.course.message.offer.mass.openid.text;

import cn.diffpi.rop.wechat.course.message.offer.mass.openid.MassBaseMessage;

/***
 * 群发文本消息
 * @author luoxun
 *
 */
public class MassTextMessage extends MassBaseMessage{
	private MassTextType text;//群发消息内容

	public MassTextType getText() {
		return text;
	}

	public void setText(MassTextType text) {
		this.text = text;
	}
}
