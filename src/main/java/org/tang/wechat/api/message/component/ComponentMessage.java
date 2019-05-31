package org.tang.wechat.api.message.component;

import org.dom4j.Element;

public class ComponentMessage extends BaseComponentMessage {
	public static final String XML_INFO_TYPE = "component_verify_ticket";
	static final String XML_COMPONENT_VERIFY_TICKET = "ComponentVerifyTicket";
	
	public ComponentMessage() {
		setInfoType(XML_INFO_TYPE);
	}
	
	private String componentVerifyTicket;
	
	public String getComponentVerifyTicket() {
		return componentVerifyTicket;
	}
	public void setComponentVerifyTicket(String componentVerifyTicket) {
		this.componentVerifyTicket = componentVerifyTicket;
	}
	@Override
	protected void generateMessage(Element root) {
		componentVerifyTicket = root.element(XML_COMPONENT_VERIFY_TICKET).getTextTrim();
	}
}
