package cn.diffpi.rop.wechat.course.message.offer.customer.voice;

import cn.diffpi.rop.wechat.course.message.offer.BaseMedia;
import cn.diffpi.rop.wechat.course.message.offer.customer.CustomerBaseMessage;

/***
 * 客户语音消息
 * @author luoxun
 *
 */
public class CustVoiceMessage extends CustomerBaseMessage{
	private BaseMedia voice;

	public BaseMedia getVoice() {
		return voice;
	}

	public void setVoice(BaseMedia voice) {
		this.voice = voice;
	}

}
