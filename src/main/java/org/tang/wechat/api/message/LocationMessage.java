package org.tang.wechat.api.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信定位消息类
 * 
 * @author Kevin MOU
 */
public class LocationMessage extends Message {
	private static Logger logger = LoggerFactory.getLogger(LocationMessage.class);
	public static final String MSGTYPE = "location";
	private static final String PKG_LOCATION_X = "Location_X";
	private static final String PKG_LOCATION_Y = "Location_Y";
	private static final String PKG_SCALE = "Scale";
	private static final String PKG_LABEL = "Label";

	private double x;
	private double y;
	private int scale;
	private String label;

	public LocationMessage() {
		setMsgType(MSGTYPE);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	protected void generateMessage(Element root) {
		x = Double.parseDouble(root.element(PKG_LOCATION_X).getTextTrim());
		y = Double.parseDouble(root.element(PKG_LOCATION_Y).getTextTrim());
		scale = Integer.parseInt(root.element(PKG_SCALE).getTextTrim());
		label = root.element(PKG_LABEL).getTextTrim();
	}

	@Override
	protected void generateTestMessage(String jsonMsg) throws Exception {
		Map<?, ?> map = new ObjectMapper().readValue(jsonMsg, Map.class);
		x = (Double) map.get("x");
		y = (Double) map.get("y");
		scale = (Integer) map.get("scale");
		label = (String) map.get("label");
	}

	@Override
	public Map<String, Object> getContentMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", x);
		map.put("y", y);
		map.put("scale", scale);
		map.put("label", label);
		return map;
	}

	@Override
	public String getContent() {
		try {
			return getContentJson();
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return "(" + x + "," + y + "," + scale + ") [" + label + "]";
		}
	}

}
