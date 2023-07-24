package com.ztesoft.mobile.common.xwork.execution;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.DocumentException;

public final class XMLParse {
	/**
	 * 创建Document
	 * 
	 * @return
	 */
	public static Document createDocument() {
		return DocumentFactory.getInstance().createDocument();
	}

	/**
	 * 创建element
	 * 
	 * @param name
	 * @return
	 */
	public static Element createElement(String name) {
		return DocumentFactory.getInstance().createElement(name);
	}

	/**
	 * 从element中获取属性数据
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public static String getAttribute(Element element, String attributeName) {
		return element.attributeValue(attributeName);
	}

	/**
	 * 设置element中属性数据
	 * 
	 * @param owner
	 * @param name
	 * @param value
	 */
	public static void setAttribute(Element owner, String name, String value) {
		Attribute attribute = createAttribute(owner, name, value);
		owner.add(attribute);
	}

	/**
	 * 创建属性
	 * 
	 * @param owner
	 * @param name
	 * @param value
	 * @return
	 */
	public static Attribute createAttribute(Element owner, String name,
			String value) {
		return DocumentFactory.getInstance()
				.createAttribute(owner, name, value);
	}

	/**
	 * 获得element下的子element
	 * 
	 * @param element
	 * @param nodeName
	 * @return
	 */
	public static List getChildElements(Element element, String nodeName) {
		return element.elements(nodeName);
	}

	/**
	 * 获取所有的子element
	 * 
	 * @param element
	 * @return
	 */
	public static List getChildElements(Element element) {
		return element.elements();
	}

	/**
	 * 从inputStream中获得document
	 * 
	 * @param inputStream
	 * @param encoding
	 * @return
	 * @throws DocumentException
	 */
	public static Document fromXML(InputStream inputStream, String encoding)
			throws DocumentException {
		Document doc = null;
		SAXReader reader = new SAXReader();
		doc = reader.read(inputStream, encoding);
		return doc;
	}

	/**
	 * 把doc写进outputStream
	 * 
	 * @param doc
	 * @param outputStream
	 * @param encoding
	 * @throws IOException
	 */
	public static void toXML(Document doc, OutputStream outputStream,
			String encoding) throws IOException {

		OutputFormat outFormat = OutputFormat.createCompactFormat();
		if (encoding != null) {
			outFormat.setEncoding(encoding);
		}
		XMLWriter xmlWriter = new XMLWriter(outputStream, outFormat);
		xmlWriter.write(doc);
		xmlWriter.flush();
	}

	/**
	 * 增加一个儿子element
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public static Element appendChild(Element parent, String name) {
		return parent.addElement(new QName(name, parent.getNamespace()));
	}
}
