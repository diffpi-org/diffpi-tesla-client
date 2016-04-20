package cn.diffpi.rop.wechat.course.message.resp.image;

import cn.diffpi.rop.wechat.course.message.resp.BaseMedia;
import cn.diffpi.rop.wechat.course.message.resp.BaseMessage;

/***
 * 图片消息
 * @author luoxun
 *
 */
public class ImageMessage extends BaseMessage{
	//图片
	private BaseMedia Image;

	public BaseMedia getImage() {
		return Image;
	}

	public void setImage(BaseMedia image) {
		Image = image;
	}
}
