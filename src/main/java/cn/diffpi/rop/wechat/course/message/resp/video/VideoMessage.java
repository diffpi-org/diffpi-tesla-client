package cn.diffpi.rop.wechat.course.message.resp.video;

import cn.diffpi.rop.wechat.course.message.resp.BaseMessage;

public class VideoMessage extends BaseMessage{
	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
}
