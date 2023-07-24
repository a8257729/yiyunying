package com.ztesoft.mobile.v2.ws.handler;

import com.ztesoft.mobile.v2.ws.parser.XmlParser;

public abstract class AbstractWebServiceHandler implements WebServiceHandler {
	
	private XmlParser xmlParser;

	public String getRequestXml() {
		return null;
	}

	public String getResponseXml() {
		return null;
	}

	public String getParsedRequest() {
		String requestXml = "";
		return this.xmlParser.parseRequestXml(requestXml);
	}

	public String getParsedResponse() {
		String responseXml = "";
		return this.xmlParser.parseResponseXml(responseXml);
	}

	public XmlParser getXmlParser() {
		return xmlParser;
	}

	public void setXmlParser(XmlParser xmlParser) {
		this.xmlParser = xmlParser;
	}

}
