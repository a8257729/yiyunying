package com.ztesoft.mobile.outsystem.helper;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.outsystem.dao.MobileXmlDocumentDAO;
import com.ztesoft.mobile.outsystem.dao.MobileXmlDocumentDAOImpl;
import com.ztesoft.mobile.outsystem.dao.MobileXmlElementDAO;
import com.ztesoft.mobile.outsystem.dao.MobileXmlElementDAOImpl;
public class XmlImportHelper extends VisitorSupport
{
	private Map<String, XmlImportHelper.Node> nodeMap = new LinkedHashMap<String, XmlImportHelper.Node>();

	private Document doc;
	private Map valueMap;
	
	public XmlImportHelper(Document doc, Map map) throws DocumentException  {
		this.doc = doc;
		this.valueMap = map;
	}	
	
	public XmlImportHelper(String xmlStr, Map map) throws DocumentException {
		this(DocumentHelper.parseText(xmlStr), map);
	}

	
	public XmlImportHelper(File file, Map map) throws DocumentException  {
		
	}
	
	private static class Node {
		
		private Long 		xmlElementId;
		private String 		elementName;
		private Integer 	elementDatatype;
		private String 		elementDesc;
		private Integer 	elementType;
		private String 		elementLength;
		private Integer 	elementIsnull;
		private Long 		parentId;
		private String		pathCode;
		private String		pathName;
		private Integer		state	=	new Integer(1);
		private Date		stateDate	= new Date();
		
		private String phash;
		
		public Long getXmlElementId() {
			return xmlElementId;
		}
		public void setXmlElementId(Long xmlElementId) {
			this.xmlElementId = xmlElementId;
		}
		public String getElementName() {
			return elementName;
		}
		public void setElementName(String elementName) {
			this.elementName = elementName;
		}
		public Integer getElementDatatype() {
			return elementDatatype;
		}
		public void setElementDatatype(Integer elementDatatype) {
			this.elementDatatype = elementDatatype;
		}
		public String getElementDesc() {
			return elementDesc;
		}
		public void setElementDesc(String elementDesc) {
			this.elementDesc = elementDesc;
		}
		public Integer getElementType() {
			return elementType;
		}
		public void setElementType(Integer elementType) {
			this.elementType = elementType;
		}
		public String getElementLength() {
			return elementLength;
		}
		public void setElementLength(String elementLength) {
			this.elementLength = elementLength;
		}
		public Integer getElementIsnull() {
			return elementIsnull;
		}
		public void setElementIsnull(Integer elementIsnull) {
			this.elementIsnull = elementIsnull;
		}
		public Long getParentId() {
			return parentId;
		}
		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}
		public String getPathCode() {
			return pathCode;
		}
		public void setPathCode(String pathCode) {
			this.pathCode = pathCode;
		}
		public String getPathName() {
			return pathName;
		}
		public void setPathName(String pathName) {
			this.pathName = pathName;
		}
		public String getPhash() {
			return phash;
		}
		public void setPhash(String phash) {
			this.phash = phash;
		}
		
	}
	
	static StringBuilder responseXmlStrx = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
	static {
		responseXmlStrx.append("<Data>");
		responseXmlStrx.append("<QueryMethod>testMethod</QueryMethod>");
		responseXmlStrx.append("<Return>");
		responseXmlStrx.append("<ContentList>")
						.append("<Content>")
								.append("<Public>")
									.append("<WorkOrderID>").append("1113344").append("</WorkOrderID>")
									.append("<OrderCode>").append("ORDCODE").append("</OrderCode >")
									.append("<ServiceName>").append("SOS").append("</ServiceName>")
									.append("<AccNbr>").append("XT001").append("</AccNbr>")
									.append("<Address>").append("黄埔大道").append("</Address>")
									.append("<CustName>").append("李嘉诚").append("</CustName>")
									.append("<CustLinkPerson>").append("李超人").append("</CustLinkPerson>")
									.append("<CustLinkPhone>").append("18022394265").append("</CustLinkPhone>")
									.append("<SlaTime>").append("2012-01-01 12:13:43").append("</SlaTime>")
									.append("<TacheName>").append("node3").append("</TacheName>")
									.append("<CreateDate>").append("2012-11-21 12:13:43").append("</CreateDate>")
									.append("<WorkOrderType/>")
								.append("</Public>")
							.append("</Content>") 
						.append("</ContentList>")
						.append("</Return>") 
						.append("</Data>"); 
	}

	/*public void visit(Attribute node)
    {
        System.out.println("attribute : "+node.getName()+" = "+node.getValue());
    }
   
   
    public void visit(ProcessingInstruction node)
    {
        System.out.println("PI : "+node.getTarget()+" "+node.getText());
    }*/
   
   
    public void visit(Element node)
    {
    	Long pk = generateElementPK();
    	XmlImportHelper.Node nx = new XmlImportHelper.Node();
    	nx.setElementName(node.getName());
		nx.setPathName(node.getPath());
		nx.setXmlElementId(pk);
		
    	if(node.isRootElement()) {
    		nx.setParentId(-1L);
    		nx.setPathCode("" + pk);
    	} else {
    		//System.out.println(node.getPath());
          	XmlImportHelper.Node pn = nodeMap.get(node.getParent().getPath());	//父节点
        	nx.setParentId(pn.getXmlElementId());
        	nx.setPathCode(pn.getPathCode() + "." + pk.longValue());
    	}
        nodeMap.put(node.getPath(), nx);
    }
	
    public Map<String, XmlImportHelper.Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<String, XmlImportHelper.Node> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
	public void writeXml2DB() throws DataAccessException {
		this.doc.accept(this);
		
		Long xmlDocumentId = writeXmlDocument();
		
        Set<Map.Entry<String, XmlImportHelper.Node>> set = nodeMap.entrySet();
        int i=0;
        for(Map.Entry<String, XmlImportHelper.Node> e: set) {
        	XmlImportHelper.Node n = e.getValue();
        	Map paramMap = new HashMap();
        	paramMap.put("xmlElementId", n.getXmlElementId());
        	paramMap.put("elementName", n.getElementName());
        	paramMap.put("parentId", n.getParentId());
        	paramMap.put("pathCode", n.getPathCode());
        	paramMap.put("pathName", e.getKey());
        	paramMap.put("state", n.state);
        	paramMap.put("stateDate", n.stateDate);
        	paramMap.put("xmlDocumentId", xmlDocumentId);
        	paramMap.put("elementOrder", i++);
        	//
        	this.getMobileXmlElementDAO().insert(paramMap);
        }
        System.out.println("写入到mobile_xml_element表");
	}

	private Long  writeXmlDocument() throws DataAccessException {
		Long xmlDocumentId = MapUtils.getLong(valueMap, "xmlDocumentId");
		String xmlDocumentEncoding = MapUtils.getString(valueMap, "xmlDocumentEncoding", "utf-8");
		
		if(null == xmlDocumentId) {
			xmlDocumentId = this.generateDocumentPK();
		}
		Map paramMap = new HashMap();
		paramMap.put("xmlDocumentId", xmlDocumentId);
		paramMap.put("state", new Integer(1));
		paramMap.put("stateDate", new Date());
		paramMap.put("xmlDocumentEncoding", xmlDocumentEncoding);
		
		this.getMobileXmlDocumentDAO().insert(paramMap);
		
		System.out.println("写入到mobile_xml_document表");
		
		return xmlDocumentId;
	}
	
	private Long generateElementPK(){
		Long pk = -1L;
		try {
			pk = this.getMobileXmlElementDAO().generatePK();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return pk;
	}
	
	private Long generateDocumentPK(){
		Long pk = -1L;
		try {
			pk = this.getMobileXmlDocumentDAO().generatePK();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return pk;
	}
	
	private MobileXmlElementDAO getMobileXmlElementDAO() {
        String daoName = MobileXmlElementDAOImpl.class.getName();
        return (MobileXmlElementDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	
	private MobileXmlDocumentDAO getMobileXmlDocumentDAO() {
		String daoName = MobileXmlDocumentDAOImpl.class.getName();
	    return (MobileXmlDocumentDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	//////////////////////////////////////////////////////////////////////////
	/*public static void main(String[] args) throws Exception
    {
    	XmlImportHelper v = new XmlImportHelper(responseXmlStrx.toString(), new HashMap());
    	v.writeXml2DB();
    }*/
		
}