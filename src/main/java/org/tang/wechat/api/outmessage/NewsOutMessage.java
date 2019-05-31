package org.tang.wechat.api.outmessage;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class NewsOutMessage extends OutMessage {
	public static String MSGTYPE = "news";
	private List<Article> articles;
	
	public NewsOutMessage() {
		this.msgType = MSGTYPE;
		articles = new ArrayList<Article>();
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticle(List<Article> articles) {
		this.articles = articles;
	}
	
	public void clear() {
		articles.clear();
	}
	
	public void addArticle(Article article) {
		articles.add(article);
	}
	
	public void addArticles(Collection<Article> articles) {
		articles.addAll(articles);
	}

	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OmUtils.PKG_MSGTYPE).addCDATA(msgType);
		root.addElement(OmUtils.PKG_NEWS_ARTICLECOUNT).addText(Integer.toString(articles.size()));
		Element articlesElement = root.addElement(OmUtils.PKG_NEWS_ARTICLES);
		for(Article article : articles) {
			Element item = articlesElement.addElement(OmUtils.PKG_NEWS_ITEM);
			if (! StringUtils.isEmpty(article.getTitle())) {
				item.addElement(OmUtils.PKG_NEWS_TITLE).addCDATA(article.getTitle());
			}
			if (! StringUtils.isEmpty(article.getDescription())) {
				item.addElement(OmUtils.PKG_NEWS_DESCRIPTION).addCDATA(article.getDescription());
			}
			if (! StringUtils.isEmpty(article.getPicUrl())) {
				item.addElement(OmUtils.PKG_NEWS_PICURL).addCDATA(article.getPicUrl());
			}
			if (! StringUtils.isEmpty(article.getUrl())) {
				item.addElement(OmUtils.PKG_NEWS_URL).addCDATA(article.getUrl());
			}
		}
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		List<Map<String, Object>> al = new ArrayList<Map<String,Object>>();
		for(Article article : articles) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("title", article.getTitle().replaceAll("\n", "<br/>"));
			item.put("description", article.getDescription().replaceAll("\n", "<br/>"));
			item.put("url", article.getUrl());
			item.put("picurl", article.getPicUrl());
			al.add(item);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articles", al);
		return map;
	}

	public void setProtocal(String contextPath) {
		for(Article article : articles) {
			String picUrl = article.getPicUrl().toLowerCase();
			if (! StringUtils.isEmpty(picUrl) && ! picUrl.startsWith("http")) {
				article.setPicUrl(contextPath + article.getPicUrl());
			}
			String url = article.getUrl().toLowerCase();
			if (! StringUtils.isEmpty(url) && ! url.startsWith("http")) {
				article.setUrl(contextPath + article.getUrl());
			}
			//System.out.println(article);
		}
	}

	@Override
	public boolean available() {
		return articles.size() > 0;
	}

}
