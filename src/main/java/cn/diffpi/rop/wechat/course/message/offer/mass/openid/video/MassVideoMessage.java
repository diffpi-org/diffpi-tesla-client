package cn.diffpi.rop.wechat.course.message.offer.mass.openid.video;

import cn.diffpi.rop.wechat.course.message.offer.VideoMedia;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.MassBaseMessage;

/***
 * 视频消息
 * @author luoxun
 *
 */
public class MassVideoMessage extends MassBaseMessage{
	private VideoMedia video;

	public VideoMedia getVideo() {
		return video;
	}

	public void setVideo(VideoMedia video) {
		this.video = video;
	}
}
