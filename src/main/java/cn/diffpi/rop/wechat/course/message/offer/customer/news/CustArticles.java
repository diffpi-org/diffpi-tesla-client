package cn.diffpi.rop.wechat.course.message.offer.customer.news;

import java.util.List;

/***
 * 客户消息图文类
 * @author luoxun
 *
 */
public class CustArticles {
	private List<CustArticle> articles;//Article为响应消息图文类

	public List<CustArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<CustArticle> articles) {
		this.articles = articles;
	}
}
