package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信事件消息类
 * 
 * @author Kevin MOU
 */
public class EventMessage extends Message {
	private final static Logger logger = LoggerFactory.getLogger(EventMessage.class);
	
	public static final String MSGTYPE = "event";
	private static final String PKG_EVENT = "Event";
	private static final String PKG_EVENTKEY = "EventKey";
	private static final String PKG_TICKET = "Ticket";
	private static final String PKG_LATITUDE = "Latitude";
	private static final String PKG_LONGITUDE = "Longitude";
	private static final String PKG_PRECISION = "Precision";
	private static final String PKG_STATUS = "Status";
	private static final String PKG_TOTALCOUNT = "TotalCount";
	private static final String PKG_FILTERCOUNT = "FilterCount";
	private static final String PKG_SENTCOUNT = "SentCount";
	private static final String PKG_ERRORCOUNT = "ErrorCount";
	
	public static final String TYPE_SUBSCRIBE = "subscribe";
	public static final String TYPE_UNSUBSCRIBE = "unsubscribe";
	public static final String TYPE_CLICK = "click";
	public static final String TYPE_VIEW = "view";
	public static final String TYPE_SCANPUSH = "scancode_push";
	public static final String TYPE_SCANWAIT = "scancode_waitmsg";
	public static final String TYPE_PIC_SYSPHOTO = "pic_sysphoto";
	public static final String TYPE_PIC_ALBUM = "pic_photo_or_album";
	public static final String TYPE_PIC_WEIXIN = "pic_weixin";
	public static final String TYPE_LOCATION_SELECT = "location_select";
	public static final String TYPE_SCAN = "scan";
	public static final String TYPE_LOCATION = "location";
	public static final String TYPE_FILE = "file";
	public static final String TYPE_JOB_TEMPLATE = "TEMPLATESENDJOBFINISH";
	public static final String TYPE_JOB_BROADCAST = "MASSSENDJOBFINISH";
	public static final String TYPE_MCS_START = "kf_create_session";
	public static final String TYPE_MCS_END = "kf_close_session";
	public static final String TYPE_MCS_SWITCH = "kf_switch_session";

	private String event;
	private String key;
	private String ticket;
	private double latitude;
	private double longitude;
	private double precision;
	private String status;
	private int totalCount;
	private int filterCount;
	private int sentCount;
	private int errorCount;

	public EventMessage() {
		setMsgType(MSGTYPE);
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	@Override
	protected void generateMessage(Element root) {
		event = root.elementTextTrim(PKG_EVENT);
		if (event.equalsIgnoreCase(TYPE_CLICK)) {
			key = root.elementTextTrim(PKG_EVENTKEY);
		}
		else if (event.equalsIgnoreCase(TYPE_SUBSCRIBE)) {
			Element el = root.element(PKG_EVENTKEY);
			if (el != null) {
				key = root.elementTextTrim(PKG_EVENTKEY);
				if (key != null && key.startsWith("qrscene_")) {
					key = key.substring(8);
				}
				ticket = root.elementTextTrim(PKG_TICKET);
			}
		} else if (event.equalsIgnoreCase(TYPE_SCAN)) {
			key = root.elementTextTrim(PKG_EVENTKEY);
			ticket = root.elementTextTrim(PKG_TICKET);
		} else if (event.equalsIgnoreCase(TYPE_LOCATION)) {
			latitude = Double.parseDouble(root.elementTextTrim(PKG_LATITUDE));
			longitude = Double.parseDouble(root.elementTextTrim(PKG_LONGITUDE));
			precision = Double.parseDouble(root.elementTextTrim(PKG_PRECISION));
		} else if (event.equalsIgnoreCase(TYPE_JOB_TEMPLATE)) {
			status = root.elementTextTrim(PKG_STATUS);
		} else if (event.equalsIgnoreCase(TYPE_JOB_BROADCAST)) {
			totalCount = Integer.parseInt(root.elementTextTrim(PKG_TOTALCOUNT));
			sentCount = Integer.parseInt(root.elementTextTrim(PKG_SENTCOUNT));
			filterCount = Integer.parseInt(root.elementTextTrim(PKG_FILTERCOUNT));
			errorCount = Integer.parseInt(root.elementTextTrim(PKG_ERRORCOUNT));
		}
			
		setMsgId("");
	}


	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		event = (String) map.get("event");
		key = (String) map.get("key");
		ticket =  (String) map.get("ticket");
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PKG_EVENT, event);
		if (event.equalsIgnoreCase(TYPE_CLICK)) {
			map.put(PKG_EVENTKEY, key);
		} else if (event.equalsIgnoreCase(TYPE_SUBSCRIBE)) {
			if (! StringUtils.isEmpty(key)) {
				if (key.startsWith("qrscene_")) {
					key = key.substring(8);
					map.put(PKG_EVENTKEY, key);
					map.put(PKG_TICKET, ticket);
				} else {
					logger.error("Unkonw event key: " + key);
				}
			}
		} else if (event.equalsIgnoreCase(TYPE_SCAN)) {
			map.put(PKG_EVENTKEY, key);
			map.put(PKG_TICKET, ticket);
		} else if (event.equalsIgnoreCase(TYPE_LOCATION)) {
			map.put(PKG_LATITUDE, latitude);
			map.put(PKG_LONGITUDE, longitude);
			map.put(PKG_PRECISION, precision);
		} else if (event.equalsIgnoreCase(TYPE_JOB_TEMPLATE)) {
			map.put(PKG_STATUS, status);
		} else if (event.equalsIgnoreCase(TYPE_JOB_BROADCAST)) {
			map.put(PKG_TOTALCOUNT, totalCount);
			map.put(PKG_SENTCOUNT, sentCount);
			map.put(PKG_FILTERCOUNT, filterCount);
			map.put(PKG_ERRORCOUNT, errorCount);
		}
		
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return event;
		}
	}

}
