package org.tang.wechat.api.outmessage.enterprise;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.message.Message;
import org.tang.wechat.api.utils.DSUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Out message tools
 * 
 * @author Kevin MOU
 * @since 1.3.2
 */
public class OetpmUtils {
    private final static Logger logger = LoggerFactory.getLogger(OetpmUtils.class);

	public static final String PKG_TOUSER = "ToUserName";
	public static final String PKG_FROMUSER = "FromUserName";
	public static final String PKG_CREATETIME = "CreateTime";
	public static final String PKG_MSGTYPE = "MsgType";
	public static final String PKG_TEXT_CONTENT = "Content";
	public static final String PKG_NEWS_ARTICLECOUNT = "ArticleCount";
	public static final String PKG_NEWS_ARTICLES = "Articles";
	public static final String PKG_NEWS_ITEM = "item";
	public static final String PKG_NEWS_TITLE = "Title";
	public static final String PKG_NEWS_DESCRIPTION = "Description";
	public static final String PKG_NEWS_PICURL = "PicUrl";
	public static final String PKG_NEWS_URL = "Url";
	public static final String PKG_MUSIC_CONTENT = "Music";
	public static final String PKG_MUSIC_TITLE = "Title";
	public static final String PKG_MUSIC_DESCRIPTION = "Description";
	public static final String PKG_MUSIC_URL = "MusicUrl";
	public static final String PKG_MUSIC_HQURL = "HQMusicUrl";
	public static final String PKG_MUSIC_THUMB = "ThumbMediaId";
	public static final String PKG_VIDEO_CONTENT = "Vedio";
	public static final String PKG_VIDEO_MEDIAID = "MediaId";
	public static final String PKG_VIDEO_THUMB = "ThumbMediaId";
	public static final String PKG_VOICE_CONTENT = "Voice";
	public static final String PKG_VOICE_MEDIAID = "MediaId";
	public static final String PKG_IMAGE_CONTENT = "Image";
	public static final String PKG_IMAGE_MEDIAID = "MediaId";
	public static final String PKG_FILE_CONTENT = "file";
	public static final String PKG_FILE_MEDIAID = "MsgId";
	public static final String PKG_FILE_TITLE = "title";
	public static final String PKG_FILE_DESCRIPTION = "description";
	public static final String PKG_FILE_KEY = "fileKey";
	public static final String PKG_FILE_MD5 = "fileMd5";
	public static final String PKG_FILE_LEN = "fileTotalLen";

	/**
	 * 创建回复消息
	 * 
	 * @param message
	 * @param content
	 * @param msgType
	 * @return OutMessage
	 */
	public static OutEnterpriseMessage createOutMessage(Message message, String content, String msgType) {
		if (msgType.equalsIgnoreCase(TextOutMessage.MSGTYPE)) {
			return createTextOutMessage(message, content);
		} else if (msgType.equalsIgnoreCase(NewsOutMessage.MSGTYPE)) {
			return createNewsOutMessage(message, content);
		} else if (msgType.equalsIgnoreCase(MusicOutMessage.MSGTYPE)) {
			return createMusicOutMessage(message, content);
		} else if (msgType.equalsIgnoreCase(ImageOutMessage.MSGTYPE)) {
			return createImageOutMessage(message, content);
		} else if (msgType.equalsIgnoreCase(VoiceOutMessage.MSGTYPE)) {
			return createVoiceOutMessage(message, content);
		} else if (msgType.equalsIgnoreCase(VideoOutMessage.MSGTYPE)) {
			return createVideoOutMessage(message, content);
		} else if (msgType.equalsIgnoreCase(FileOutMessage.MSGTYPE)) {
			return createFileOutMessage(message, content);
		} else if (msgType.equalsIgnoreCase(MCSOutMessage.MSGTYPE)) {
			return createMCSOutMessage(message);
		} else {
			return createTextOutMessage(message, "UNKNOW MSGTYPE");
		}
	}

	/**
	 * Create text out message
	 * 
	 * @param message
	 * @param content
	 * @return TextOutMessage
	 */
	public static TextOutMessage createTextOutMessage(Message message, String content) {
		TextOutMessage out = new TextOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setContent(content);
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");
		return out;
	}

	/**
	 * Create text out message
	 * 
	 * @param message
	 * @param content
	 * @return TextOutMessage
	 */
	public static MCSOutMessage createMCSOutMessage(Message message) {
		MCSOutMessage out = new MCSOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");
		return out;
	}

	/**
	 * Create news out message
	 * 
	 * @param message
	 * @param content
	 * @return NewsOutMessage
	 */
	public static NewsOutMessage createNewsOutMessage(Message message, String content) {
		NewsOutMessage out = new NewsOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setContent(content);
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");

		out.clear();
		try {
			Element el = DSUtils.parserXml(content);
			for (Element item : (List<Element>)el.elements()) {
				if (item.getName().equalsIgnoreCase(PKG_NEWS_ITEM)) {
					Article article = new Article();
					article.setTitle(item.elementTextTrim(PKG_NEWS_TITLE));
					article.setDescription(item.elementTextTrim(PKG_NEWS_DESCRIPTION));
					article.setPicUrl(item.elementTextTrim(PKG_NEWS_PICURL));
					article.setUrl(item.elementTextTrim(PKG_NEWS_URL));
					out.addArticle(article);
				}
			}
			return out;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Create music out message
	 * 
	 * @param message
	 * @param content
	 * @return MusicOutMessage
	 */
	public static MusicOutMessage createMusicOutMessage(Message message, String content) {
		MusicOutMessage out = new MusicOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setContent(content);
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");

		try {
			Music music = new Music();
			Element el = DSUtils.parserXml(content);
			music.setTitle(el.elementTextTrim(PKG_MUSIC_TITLE));
			music.setDescription(el.elementTextTrim(PKG_MUSIC_DESCRIPTION));
			music.setMusicUrl(el.elementTextTrim(PKG_MUSIC_URL));
			music.setHqMusicUrl(el.elementTextTrim(PKG_MUSIC_HQURL));
			music.setThumbMediaId(el.elementTextTrim(PKG_MUSIC_THUMB));
			out.setMusic(music);
			return out;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	/**
	 * Create image out message
	 * 
	 * @param message
	 * @param content
	 * @return ImageOutMessage
	 */
	public static ImageOutMessage createImageOutMessage(Message message, String content) {
		ImageOutMessage out = new ImageOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");
		out.setContent(content);
		out.setMediaId(content);
		return out;
	}

	/**
	 * Create voice out message
	 * 
	 * @param message
	 * @param content
	 * @return VoiceOutMessage
	 */
	public static VoiceOutMessage createVoiceOutMessage(Message message, String content) {
		VoiceOutMessage out = new VoiceOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");
		out.setContent(content);
		out.setMediaId(content);
		return out;
	}

	/**
	 * Create video out message
	 * 
	 * @param message
	 * @param content
	 * @return VideoOutMessage
	 */
	public static VideoOutMessage createVideoOutMessage(Message message, String content) {
		VideoOutMessage out = new VideoOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");
		out.setContent(content);
		out.setMediaId(content);
		return out;
	}

	/**
	 * Create video out message
	 * 
	 * @param message
	 * @param content
	 * @return VideoOutMessage
	 */
	public static FileOutMessage createFileOutMessage(Message message, String content) {
		FileOutMessage out = new FileOutMessage();
		out.setToUser(message.getFromUser());
		out.setFromUser(message.getToUser());
		out.setCreateTime(new Date());
		out.setAgentid(message.getAgentId());
		out.setTotag("");
		out.setSafe(0);
		out.setToparty("");
		out.setMsgId(content);
		out.setContent(content);
		return out;
	}

	/**
	 * 创建单个回复消息在消息队列中
	 * 
	 * @param message
	 * @param content
	 * @param msgType
	 * @return <code>List&lt;OutMessage&gt;</code>
	 */
	public static List<OutEnterpriseMessage> outSingleMessage(Message message, String content, String msgType) {
		if (msgType.equalsIgnoreCase(TextOutMessage.MSGTYPE)) {
			return outSingleTextMessage(message, content);
		} else if (msgType.equalsIgnoreCase(NewsOutMessage.MSGTYPE)) {
			return outSingleNewsMessage(message, content);
		} else if (msgType.equalsIgnoreCase(MusicOutMessage.MSGTYPE)) {
			return outSingleMusicMessage(message, content);
		} else if (msgType.equalsIgnoreCase(ImageOutMessage.MSGTYPE)) {
			return outSingleImageMessage(message, content);
		} else if (msgType.equalsIgnoreCase(VoiceOutMessage.MSGTYPE)) {
			return outSingleVoiceMessage(message, content);
		} else if (msgType.equalsIgnoreCase(VideoOutMessage.MSGTYPE)) {
			return outSingleVideoMessage(message, content);
		} else if (msgType.equalsIgnoreCase(FileOutMessage.MSGTYPE)) {
			return outSingleFileMessage(message, content);
		} else {
			return new ArrayList<OutEnterpriseMessage>();
		}

	}

	public static List<OutEnterpriseMessage> outSingleMessage(OutEnterpriseMessage out) {
		List<OutEnterpriseMessage> outs = new ArrayList<OutEnterpriseMessage>();
		outs.add(out);
		return outs;
	}

	public static List<OutEnterpriseMessage> outSingleTextMessage(Message message, String content) {
		OutEnterpriseMessage out = createTextOutMessage(message, content);
		return outSingleMessage(out);
	}

	public static List<OutEnterpriseMessage> outSingleNewsMessage(Message message, String content) {
		OutEnterpriseMessage out = createNewsOutMessage(message, content);
		return outSingleMessage(out);
	}

	public static List<OutEnterpriseMessage> outSingleMusicMessage(Message message, String content) {
		OutEnterpriseMessage out = createMusicOutMessage(message, content);
		return outSingleMessage(out);
	}

	public static List<OutEnterpriseMessage> outSingleImageMessage(Message message, String content) {
		OutEnterpriseMessage out = createImageOutMessage(message, content);
		return outSingleMessage(out);
	}

	public static List<OutEnterpriseMessage> outSingleVoiceMessage(Message message, String content) {
		OutEnterpriseMessage out = createVoiceOutMessage(message, content);
		return outSingleMessage(out);
	}

	public static List<OutEnterpriseMessage> outSingleVideoMessage(Message message, String content) {
		OutEnterpriseMessage out = createVideoOutMessage(message, content);
		return outSingleMessage(out);
	}

	public static List<OutEnterpriseMessage> outSingleFileMessage(Message message, String content) {
		OutEnterpriseMessage out = createFileOutMessage(message, content);
		return outSingleMessage(out);
	}

}
