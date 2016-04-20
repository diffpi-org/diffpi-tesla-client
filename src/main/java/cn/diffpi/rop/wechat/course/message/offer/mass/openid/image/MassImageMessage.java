package cn.diffpi.rop.wechat.course.message.offer.mass.openid.image;

import cn.diffpi.rop.wechat.course.message.offer.BaseMedia;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.MassBaseMessage;

/***
 * 群发图片消息
 * @author luoxun
 *
 */
public class MassImageMessage extends MassBaseMessage{
	private BaseMedia image;

	public BaseMedia getImage() {
		return image;
	}

	public void setImage(BaseMedia image) {
		this.image = image;
	}
	
	
}
