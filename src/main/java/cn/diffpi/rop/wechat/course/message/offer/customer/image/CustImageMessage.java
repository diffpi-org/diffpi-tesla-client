package cn.diffpi.rop.wechat.course.message.offer.customer.image;

import cn.diffpi.rop.wechat.course.message.offer.BaseMedia;
import cn.diffpi.rop.wechat.course.message.offer.customer.CustomerBaseMessage;

/***
 *图片消息类
 * @author luoxun
 *
 */
public class CustImageMessage extends CustomerBaseMessage{
	private BaseMedia image;

	public BaseMedia getImage() {
		return image;
	}

	public void setImage(BaseMedia image) {
		this.image = image;
	}
}
