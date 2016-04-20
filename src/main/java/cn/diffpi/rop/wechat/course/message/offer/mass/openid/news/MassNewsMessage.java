package cn.diffpi.rop.wechat.course.message.offer.mass.openid.news;

import cn.diffpi.rop.wechat.course.message.offer.BaseMedia;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.UserBaseMessage;


/***
 * 群发图文消息
 * @author Boy
 *
 */
public class MassNewsMessage extends UserBaseMessage{
	private BaseMedia mpnews;

	public BaseMedia getMpnews() {
		return mpnews;
	}

	public void setMpnews(BaseMedia mpnews) {
		this.mpnews = mpnews;
	}
	
}
