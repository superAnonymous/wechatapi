package org.tang.wechat.api.outmessage.enterprise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tang.wechat.api.outmessage.OutMessage;

import java.util.HashMap;
import java.util.Map;

public abstract class OutEnterpriseMessage extends OutMessage {
	protected String toparty;
	protected String totag;
	protected int safe;
	protected int agentid;

	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getTotag() {
		return totag;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}

	public int getSafe() {
		return safe;
	}

	public void setSafe(int safe) {
		this.safe = safe;
	}

	@Override
	public String getContentJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(getEntity());
	}

	@Override
	public String toXmlString() {
		return null;
	}

	@Override
	public String toJsonString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", toUser);
		map.put("toparty", "");
		map.put("totag", "");
		map.put("safe", 0);
		map.put("msgtype", msgType);
		map.put(msgType, getEntity());
		map.put("agentid", agentid);
		try {
			return new ObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public abstract Object getEntity();

	@Override
	public abstract boolean available();
	
}
