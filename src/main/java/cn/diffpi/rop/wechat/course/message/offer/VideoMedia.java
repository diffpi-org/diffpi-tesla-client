package cn.diffpi.rop.wechat.course.message.offer;


/***
 * 视频消息媒体类
 * @author luoxun
 *
 */
public class VideoMedia extends BaseMedia{
	private String title;
	private String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
