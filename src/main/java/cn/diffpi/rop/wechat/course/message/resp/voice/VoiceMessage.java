package cn.diffpi.rop.wechat.course.message.resp.voice;

import cn.diffpi.rop.wechat.course.message.resp.BaseMedia;
import cn.diffpi.rop.wechat.course.message.resp.BaseMessage;

/***
 * 语音消息
 * @author luoxun
 *
 */
public class VoiceMessage extends BaseMessage{
	//语音
	private BaseMedia Voice;

	public BaseMedia getVoice() {
		return Voice;
	}

	public void setVoice(BaseMedia voice) {
		Voice = voice;
	}
}
