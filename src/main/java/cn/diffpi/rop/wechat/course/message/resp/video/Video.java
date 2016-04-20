package cn.diffpi.rop.wechat.course.message.resp.video;

import cn.diffpi.rop.wechat.course.message.resp.BaseMedia;

/***
 * 响应视频类
 * @author luoxun
 *
 */
public class Video extends BaseMedia{
	private String Title;
	private String Description;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
}
