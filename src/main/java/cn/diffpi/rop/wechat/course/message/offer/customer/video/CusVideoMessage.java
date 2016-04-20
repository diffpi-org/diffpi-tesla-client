package cn.diffpi.rop.wechat.course.message.offer.customer.video;

import cn.diffpi.rop.wechat.course.message.offer.customer.CustomerBaseMessage;

/***
 * 客户视频消息
 * @author luoxun
 *
 */
public class CusVideoMessage extends CustomerBaseMessage{
	private CusVideoMedia video;

	public CusVideoMedia getVideo() {
		return video;
	}

	public void setVideo(CusVideoMedia video) {
		this.video = video;
	}
}
