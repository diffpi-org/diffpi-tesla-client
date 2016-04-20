package cn.diffpi.rop.wechat.course.message.offer.mass.openid.news;

import cn.diffpi.rop.wechat.course.message.offer.BaseMediaB;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.UserBaseMessage;


/***
 * 群发图文消息
 * @author Boy
 *
 */
public class MassNewsMessageB extends UserBaseMessage{
	private BaseMediaB mpnews;

	public BaseMediaB getMpnews() {
		return mpnews;
	}

	public void setMpnews(BaseMediaB mpnews) {
		this.mpnews = mpnews;
	}
	
}
