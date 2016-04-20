package cn.diffpi.rop.wechat.course.message.offer.customer.news;

import cn.diffpi.rop.wechat.course.message.offer.customer.CustomerBaseMessage;

/***
 * 客户消息图文
 * @author luoxun
 *
 */
public class CustNewsMessage extends CustomerBaseMessage{
	private CustArticles news;

	public CustArticles getNews() {
		return news;
	}

	public void setNews(CustArticles news) {
		this.news = news;
	}
}
