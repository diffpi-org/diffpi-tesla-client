package cn.diffpi.rop.wechat.course.message.offer.mass.openid.text;

import cn.diffpi.rop.wechat.course.message.offer.mass.openid.UserBaseMessage;


/***
 * 群发消息类型
 * @author Justin Lin
 *
 */
public class OneTextType extends UserBaseMessage{
	private OneTextContent text;

	public OneTextContent getText() {
		return text;
	}

	public void setText(OneTextContent text) {
		this.text = text;
	}
	
	@Override
	public String getMsgtype(){
		return "text";
	}
}
