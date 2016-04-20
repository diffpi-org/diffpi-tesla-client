package cn.diffpi.rop.wechat.course.message.offer.mass.openid.voice;

import cn.diffpi.rop.wechat.course.message.offer.BaseMedia;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.MassBaseMessage;

/***
 * 群发语音消息
 * @author luoxun
 *
 */
public class MassVoiceMessage extends MassBaseMessage{
	private BaseMedia voice;

	public BaseMedia getVoice() {
		return voice;
	}

	public void setVoice(BaseMedia voice) {
		this.voice = voice;
	}
	
}
