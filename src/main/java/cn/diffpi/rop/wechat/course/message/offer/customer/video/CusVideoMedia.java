package cn.diffpi.rop.wechat.course.message.offer.customer.video;

import cn.diffpi.rop.wechat.course.message.offer.VideoMedia;

/***
 * 客户视频媒体
 * @author luoxun
 *
 */
public class CusVideoMedia extends VideoMedia{
	private String thumb_media_id;
	
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
}
