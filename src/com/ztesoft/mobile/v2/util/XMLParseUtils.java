package com.ztesoft.mobile.v2.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrderDetail;

public class XMLParseUtils {

	public class WorkOrderHandler extends DefaultHandler {
		private WorkOrder workOrder = null;
		private List<WorkOrder> list = null;
		private String tag;
		private StringBuilder sb = new StringBuilder();  
		
		public List<WorkOrder> getWorkOrderList() {
			return this.list;
		}
		
		public void setWorkOrderList(List<WorkOrder> list) {
			this.list = list;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
			sb.append(ch, start, length);  
	        
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
//			super.endDocument();
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
						
			    String value = sb.toString();  
			
	        	if("ORDERID".equalsIgnoreCase(name)){
	            	workOrder.setOrderId(Long.valueOf(value));
	            }else if("ORDERTITLE".equalsIgnoreCase(name)){
	            	workOrder.setOrderTitle(value);
	            }else if("ORDERCODE".equalsIgnoreCase(name)){
	            	workOrder.setOrderCode(value);
	            }else if("WORKORDERID".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderId(Long.valueOf(value));
	            }else if("WORKORDERCODE".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderCode(value);
	            }else if("TACHENAME".equalsIgnoreCase(name)){
	            	workOrder.setTacheName(value);
	            }else if("SERVICENAME".equalsIgnoreCase(name)){
	            	workOrder.setServiceName(value);
	            }else if("CREATEDATE".equalsIgnoreCase(name)){
	            	workOrder.setCreateDate(value);
	            }else if("ACCNBR".equalsIgnoreCase(name)){
	            	workOrder.setAccNbr(value);
	            }else if("WORKORDERSTATE".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderState(value);
	            }else if("WORKORDERSTATENAME".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderStateName(value);
	            }else if("WORKORDERTYPENAME".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderTypeName(value);
	            }else if("ORDERTYPENAME".equalsIgnoreCase(name)){
	            	workOrder.setOrderTypeName(value);
	            }else if("ORDERPRIORITYNAME".equalsIgnoreCase(name)){
	            	workOrder.setOrderPriorityName(value);
	            }else if("LIMITDATE".equalsIgnoreCase(name)){
	            	workOrder.setLimitDate(value);
	            }else if("ORDERDETAIL".equalsIgnoreCase(name)){
	            	workOrder.setOrderDetail(value);
	            }else if("ISDISTILL".equalsIgnoreCase(name)){
	            	workOrder.setAccepted(value.equals("1")?true:false);
	            }else if("ISSTARTOFF".equalsIgnoreCase(name)){
	            	workOrder.setDeparted(value.equals("1")?true:false);
	            }else if("ISSIGNON".equalsIgnoreCase(name)){
	            	workOrder.setSignin(value.equals("1")?true:false);
	            }else if("DEPORDERDATE".equalsIgnoreCase(name)){
	            	workOrder.setDeporderDate(value);
	            }else if("CUSTNAME".equalsIgnoreCase(name)){
	            	workOrder.setCustName(value);
	            }

			
			if("ORDERINFO".equalsIgnoreCase(name)){
	            list.add(workOrder);
	            workOrder=null;
	        }
	        tag=null;
		}

		@Override
		public void startDocument() throws SAXException {
			 list=new ArrayList<WorkOrder>();
		}

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			sb.setLength(0);  
			if("ORDERINFO".equals(name)){
	            workOrder=new WorkOrder();
	        }
	        tag=name;
		}
		
	}
	
	public class WorkOrderPlanHandler extends DefaultHandler {
		private WorkOrder workOrder = null;
		private List<WorkOrder> list = null;
		private String tag;
		private StringBuilder sb = new StringBuilder();  
		
		public List<WorkOrder> getWorkOrderList() {
			return this.list;
		}
		
		public void setWorkOrderList(List<WorkOrder> list) {
			this.list = list;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
			sb.append(ch, start, length);  
			

		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
//			super.endDocument();
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			
			String value = sb.toString(); 


	        	if("ORDERID".equalsIgnoreCase(name)){
	            	workOrder.setOrderId(Long.valueOf(value));
	            }else if("ORDERTITLE".equalsIgnoreCase(name)){
	            	workOrder.setOrderTitle(value);
	            }else if("ORDERCODE".equalsIgnoreCase(name)){
	            	workOrder.setOrderCode(value);
	            }else if("WORKORDERID".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderId(Long.valueOf(value));
	            }else if("WORKORDERCODE".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderCode(value);
	            }else if("TACHENAME".equalsIgnoreCase(name)){
	            	workOrder.setTacheName(value);
	            }else if("SERVICENAME".equalsIgnoreCase(name)){
	            	workOrder.setServiceName(value);
	            }else if("CREATEDATE".equalsIgnoreCase(name)){
	            	workOrder.setCreateDate(value);
	            }else if("ACCNBR".equalsIgnoreCase(name)){
	            	workOrder.setAccNbr(value);
	            }else if("WORKORDERSTATE".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderState(value);
	            }else if("WORKORDERSTATENAME".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderStateName(value);
	            }else if("WORKORDERTYPENAME".equalsIgnoreCase(name)){
	            	workOrder.setWorkOrderTypeName(value);
	            }else if("ORDERTYPENAME".equalsIgnoreCase(name)){
	            	workOrder.setOrderTypeName(value);
	            }else if("ORDERPRIORITYNAME".equalsIgnoreCase(name)){
	            	workOrder.setOrderPriorityName(value);
	            }else if("LIMITDATE".equalsIgnoreCase(name)){
	            	workOrder.setLimitDate(value);
	            }else if("ORDERDETAIL".equalsIgnoreCase(name)){
	            	workOrder.setOrderDetail(value);
	            }else if("ISDISTILL".equalsIgnoreCase(name)){
	            	workOrder.setAccepted(value.equals("1")?true:false);
	            }else if("ISSTARTOFF".equalsIgnoreCase(name)){
	            	workOrder.setDeparted(value.equals("1")?true:false);
	            }else if("ISSIGNON".equalsIgnoreCase(name)){
	            	workOrder.setSignin(value.equals("1")?true:false);
	            }else if("BESPDATE".equalsIgnoreCase(name)){
	            	workOrder.setBespDate(value);
	            }else if("PREBESPDATE".equalsIgnoreCase(name)){
	            	workOrder.setPreBespDate(value);
	            }else if("DEPORDERDATE".equalsIgnoreCase(name)){
	            	workOrder.setDeporderDate(value);
	            }else if("CUSTNAME".equalsIgnoreCase(name)){
	            	workOrder.setCustName(value);
	            }
	        	
	        
			if("ORDERINFO".equalsIgnoreCase(name)){
	            list.add(workOrder);
	            workOrder=null;
	        }
	        tag=null;
		}

		@Override
		public void startDocument() throws SAXException {
			 list=new ArrayList<WorkOrder>();
		}

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			
			sb.setLength(0);  
			
			if("ORDERINFO".equals(name)){
	            workOrder=new WorkOrder();
	        }
	        tag=name;
		}
		
	}
	
	public class WorkOrderDetailHandler extends DefaultHandler {
		private WorkOrderDetail workOrderDetail = null;
		private List<WorkOrderDetail> list = null;
		private String tag;
		private StringBuilder sb = new StringBuilder();  
		
		public List<WorkOrderDetail> getWorkOrderDetailList() {
			return this.list;
		}
		
		public void setWorkOrderDetailList(List<WorkOrderDetail> list) {
			this.list = list;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
			sb.append(ch, start, length);  
			
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
//			super.endDocument();
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			
			String value = sb.toString();  

	        if("COLUMNNAME".equalsIgnoreCase(name)){
	           workOrderDetail.setName(value);
	        }else if("COLUMNVALUE".equalsIgnoreCase(name)){
	           workOrderDetail.setValue(value);
	        }
	        
			if("COLUMN".equalsIgnoreCase(name)){
	            list.add(workOrderDetail);
	            workOrderDetail=null;
	        }
	        tag=null;
		}

		@Override
		public void startDocument() throws SAXException {
			 list=new ArrayList<WorkOrderDetail>();
		}

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			
			sb.setLength(0);  
			
			if("COLUMN".equals(name)){
				workOrderDetail=new WorkOrderDetail();
	        }
	        tag=name;
		}
		
	}
	
	public class DesignDetailHandler extends DefaultHandler {
		private WorkOrderDetail workOrderDetail = null;
		private List<WorkOrderDetail> list = null;
		private String tag;
		private StringBuilder sb = new StringBuilder();  
		
		public List<WorkOrderDetail> getWorkOrderDetailList() {
			return this.list;
		}
		
		public void setWorkOrderDetailList(List<WorkOrderDetail> list) {
			this.list = list;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			sb.append(ch, start, length);  
	        
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
//			super.endDocument();
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			
			String value = sb.toString();  

	        if("COLUMNNAME1".equalsIgnoreCase(name)){
	            workOrderDetail.setName(value);
	        }else if("COLUMNVALUE1".equalsIgnoreCase(name)){
	            workOrderDetail.setValue(value);
	        }

			if("COLUMN1".equalsIgnoreCase(name)){
	            list.add(workOrderDetail);
	            workOrderDetail=null;
	        }
	        tag=null;
		}

		@Override
		public void startDocument() throws SAXException {
			 list=new ArrayList<WorkOrderDetail>();
		}

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			sb.setLength(0);  

			if("COLUMN1".equals(name)){
				workOrderDetail=new WorkOrderDetail();
	        }
	        tag=name;
		}
		
	}
	public class DataBusiDetailHandler extends DefaultHandler {
		private WorkOrderDetail workOrderDetail = null;
		private List<WorkOrderDetail> list = null;
		private String tag;
		private StringBuilder sb = new StringBuilder();  
		
		public List<WorkOrderDetail> getWorkOrderDetailList() {
			return this.list;
		}
		
		public void setWorkOrderDetailList(List<WorkOrderDetail> list) {
			this.list = list;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
			sb.append(ch, start, length);  
			
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
//			super.endDocument();
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			
			String value = sb.toString();  
	        if("COLUMNNAME2".equalsIgnoreCase(name)){
	            workOrderDetail.setName(value);
	        }else if("COLUMNVALUE2".equalsIgnoreCase(name)){
	            workOrderDetail.setValue(value);
	        }
	        
			if("COLUMN2".equalsIgnoreCase(name)){
	            list.add(workOrderDetail);
	            workOrderDetail=null;
	        }
	        tag=null;
		}

		@Override
		public void startDocument() throws SAXException {
			 list=new ArrayList<WorkOrderDetail>();
		}

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			sb.setLength(0);  
			if("COLUMN2".equals(name)){
				workOrderDetail=new WorkOrderDetail();
	        }
	        tag=name;
		}
		
	}
	
	public class AssistantPersonHandler extends DefaultHandler {
		private Map map = null;
		private List<Map> list = null;
		private String tag;
		private StringBuilder sb = new StringBuilder();  
		
		public List<Map> getAssistanPersonList() {
			return this.list;
		}
		
		public void setAssistanPersonList(List<Map> list) {
			this.list = list;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			sb.append(ch, start, length);  
			
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
//			super.endDocument();
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			
			 String data = sb.toString();  
	         if("STAFFID".equalsIgnoreCase(name)){
	            	map.put("STAFFID", data);
	         }else if("STAFFNAME".equalsIgnoreCase(name)){
	            	map.put("STAFFNAME", data);
	         }
	        
			if("STAFFINFO".equalsIgnoreCase(name)){
	            list.add(map);
	            map=null;
	        }
	        tag=null;
		}

		@Override
		public void startDocument() throws SAXException {
			 list=new ArrayList<Map>();
		}

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			sb.setLength(0);  
			if("STAFFINFO".equals(name)){
				map=new HashMap();
	        }
	        tag=name;
		}
		
	}
	
	public List<WorkOrder> parseWorkOrderXML(String retXml) throws Exception{
		InputStream strm = null;
		InputSource source = null;
        SAXParserFactory parserFactory=SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        WorkOrderHandler workOrderHandler=new WorkOrderHandler();
        
        strm =  new ByteArrayInputStream(retXml.getBytes());
        source = new InputSource(strm);
        
        parser.parse(source, workOrderHandler);
        List<WorkOrder> list = workOrderHandler.getWorkOrderList();
        return list;
    }
	
	public List<WorkOrder> parseWorkOrderPlanXML(String retXml) throws Exception{
		InputStream strm = null;
		InputSource source = null;
        SAXParserFactory parserFactory=SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        WorkOrderPlanHandler workOrderPlanHandler=new WorkOrderPlanHandler();
        
        strm =  new ByteArrayInputStream(retXml.getBytes());
        source = new InputSource(strm);
        
        parser.parse(source, workOrderPlanHandler);
        List<WorkOrder> list = workOrderPlanHandler.getWorkOrderList();
        return list;
    }
	
	public List<WorkOrderDetail> parseWorkOrderDetailXML(String retXml) throws Exception{
		InputStream strm = null;
		InputSource source = null;
        SAXParserFactory parserFactory=SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        WorkOrderDetailHandler handler = new WorkOrderDetailHandler();
        
        strm =  new ByteArrayInputStream(retXml.getBytes());
        source = new InputSource(strm);
        
        parser.parse(source, handler);
        return handler.getWorkOrderDetailList();
    }
	
	public List<WorkOrderDetail> parseDesignDetailXML(String retXml) throws Exception{
		InputStream strm = null;
		InputSource source = null;
        SAXParserFactory parserFactory=SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        DesignDetailHandler handler = new DesignDetailHandler();
        
        strm =  new ByteArrayInputStream(retXml.getBytes());
        source = new InputSource(strm);
        
        parser.parse(source, handler);
        return handler.getWorkOrderDetailList();
    }
	
	public List<WorkOrderDetail> parseDataBusiDetailXML(String retXml) throws Exception{
		InputStream strm = null;
		InputSource source = null;
        SAXParserFactory parserFactory=SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        DataBusiDetailHandler handler = new DataBusiDetailHandler();
        
        strm =  new ByteArrayInputStream(retXml.getBytes());
        source = new InputSource(strm);
        
        parser.parse(source, handler);
        return handler.getWorkOrderDetailList();
    }
	public List<Map> parseAssistantPersonXML(String retXml) throws Exception{
		InputStream strm = null;
		InputSource source = null;
        SAXParserFactory parserFactory=SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        AssistantPersonHandler handler = new AssistantPersonHandler();
        strm =  new ByteArrayInputStream(retXml.getBytes());
        source = new InputSource(strm);
        parser.parse(source, handler);
        return handler.getAssistanPersonList();
    }
}
