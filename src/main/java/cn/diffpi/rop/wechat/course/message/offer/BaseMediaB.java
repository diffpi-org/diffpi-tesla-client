package cn.diffpi.rop.wechat.course.message.offer;

import java.util.List;

import cn.diffpi.rop.wechat.course.message.offer.mass.openid.articles.ArticlesMessage;

/***
 * 媒体基类
 * @author luoxun
 *
 */
public class BaseMediaB {
	private List<ArticlesMessage> articles;

	public List<ArticlesMessage> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticlesMessage> articles) {
		this.articles = articles;
	}

}
