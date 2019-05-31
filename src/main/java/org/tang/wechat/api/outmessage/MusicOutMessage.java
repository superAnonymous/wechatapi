package org.tang.wechat.api.outmessage;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class MusicOutMessage extends OutMessage {
	public static String MSGTYPE = "music";
	private Music music;

	public MusicOutMessage() {
		this.msgType = MSGTYPE;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
	
	public void setThumbMediaId(String thumbMediaId) {
		if (music != null) {
			music.setThumbMediaId(thumbMediaId);
		}
	}
	
	public String getThumbMediaId() {
		if (music != null) {
			return music.getThumbMediaId();
		}
		return null;
	}
	
	@Override
	public String toXmlString() {
		Element root = DocumentHelper.createElement("xml");
		root.addElement(OmUtils.PKG_TOUSER).addCDATA(toUser);
		root.addElement(OmUtils.PKG_FROMUSER).addCDATA(fromUser);
		root.addElement(OmUtils.PKG_CREATETIME).addText(Long.toString(createTime.getTime()));
		root.addElement(OmUtils.PKG_MSGTYPE).addCDATA(msgType);
		Element musicElement = root.addElement(OmUtils.PKG_MUSIC_CONTENT);
		if (music != null) {
			musicElement.addElement(OmUtils.PKG_MUSIC_TITLE).addCDATA(music.getTitle());
			musicElement.addElement(OmUtils.PKG_MUSIC_DESCRIPTION).addCDATA(music.getDescription());
			musicElement.addElement(OmUtils.PKG_MUSIC_URL).addCDATA(music.getMusicUrl());
			musicElement.addElement(OmUtils.PKG_MUSIC_HQURL).addCDATA(music.getHqMusicUrl());
			musicElement.addElement(OmUtils.PKG_MUSIC_THUMB).addCDATA(music.getThumbMediaId());
		}
		return root.asXML();
	}

	@Override
	public Object getEntity() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (music != null) {
			map.put("title", music.getTitle());
			map.put("description", music.getDescription());
			map.put("musicurl", music.getMusicUrl());
			map.put("hqmusicurl", music.getHqMusicUrl());
			map.put("thumb_media_id", music.getThumbMediaId());
		}
		return map;
	}

	public void setProtocal(String contextPath) {
		if (music != null) {
			String url = music.getMusicUrl().toLowerCase();
			if (! StringUtils.isEmpty(url) && !url.startsWith("http")) {
				music.setMusicUrl(contextPath + music.getMusicUrl());
			}
			url = music.getHqMusicUrl().toLowerCase();
			if (! StringUtils.isEmpty(url) && !url.startsWith("http")) {
				music.setHqMusicUrl(contextPath + music.getHqMusicUrl());
			}
		}
	}

	@Override
	public boolean available() {
		return (music != null);
	}

}
