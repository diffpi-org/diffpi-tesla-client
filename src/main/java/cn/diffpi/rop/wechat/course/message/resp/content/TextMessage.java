package cn.diffpi.rop.wechat.course.message.resp.content;

import cn.diffpi.rop.wechat.course.message.resp.BaseMessage;

/**
 * 文本消息
 * 
 * @author luoxun
 */

public class TextMessage extends BaseMessage{
	// 回复的消息内容  
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	} 

}
