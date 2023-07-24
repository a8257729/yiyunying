package com.ztesoft.mobile.v2.controller.workform.shanghai;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.controller.common.CommonController;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrderDetail;
import com.ztesoft.mobile.v2.service.workform.shanghai.WorkOrderService;
import com.ztesoft.mobile.v2.util.XMLParseUtils;
import com.ztesoft.mobile.v2.util.XmlDataUtil;
import com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplServiceLocator;
import com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplSoapBindingStub;

/**
 * @author Leemon
 *
 */
@Controller("workOrderController")
public class WorkOrderController extends WebConfigController {
	private static final Logger logger = Logger.getLogger(CommonController.class);
	public static final boolean DEBUG = false;
    private WorkOrderService workOrderService;

    private WorkOrderService getWorkOrderService() {
        return workOrderService;
    }
    
    @Autowired(required = false)
    public void setWorkOrderService(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }
    
    private static final int WORK_ORDER_LIST = 1;
    private static final int WORK_ORDER_DETAIL = 2;
    private static final int WORK_ORDER_ACCEPT = 3;
    private static final int WORK_ORDER_DEPART = 4;
    private static final int WORK_ORDER_PLAN = 5;
    private static final int WORK_ORDER_SIGNIN = 6;
    private static final int WORK_ORDER_DEPART_CANCEL = 7;
    
    private String getRequestXml(String reqXml)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>");
    	sb.append("<SERVICE>");
    	sb.append(reqXml);
    	sb.append("</SERVICE>");
    	return sb.toString();
    }
    
    private String callWebService(String reqXml,int reqType) throws Exception
    {
    	reqXml = getRequestXml(reqXml);
    	System.out.println("reqXml==========="+reqXml);
    	String retXml = null;
    	
    	WfmToHandTerminalImplServiceLocator wttsl = new WfmToHandTerminalImplServiceLocator();
 		WfmToHandTerminalImplSoapBindingStub wttsb = (WfmToHandTerminalImplSoapBindingStub)wttsl.getWfmToHandTerminalImpl();

 		switch (reqType) {
 		case WORK_ORDER_LIST:
 			retXml = wttsb.queryWorkOrderConf(reqXml);
 			break;
 		case WORK_ORDER_DETAIL:
 			retXml = wttsb.queryWorkOrder(reqXml);
 			break;
 		case WORK_ORDER_ACCEPT:
 			retXml = wttsb.distillWorkOrder(reqXml);
 			break;
 		case WORK_ORDER_DEPART:
 			retXml = wttsb.startOffWorkOrder(reqXml);
 			break;
 		case WORK_ORDER_SIGNIN:
 			retXml = wttsb.signOnWorkOrder(reqXml);
 			break;
 		case WORK_ORDER_PLAN:
 			retXml = wttsb.queryWorkOrderPlan(reqXml);
 			break;
 		case WORK_ORDER_DEPART_CANCEL:
 			retXml = wttsb.cancelStafOffWorkOrder(reqXml);
 			break;
 		}
 		
    	System.out.println("retXml==========="+retXml);    	
    	return retXml;
    }
    
    private String getSeriNo()
    {
    	Date currentTime = new Date();
    	DateFormat df= new SimpleDateFormat("yyyyMMddHHmmss"); 
    	return df.format(currentTime);
    }
    
    private String getSysdate()
    {
    	Date currentTime = new Date();
    	DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	return df.format(currentTime);
    }
    
    private Result getResult(String retXml)
    {
    	Result result = null;
		SAXReader reader = new SAXReader();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			//String returnCode = root.element("RETURNCODE").getText();
			//String message = root.element("MESSAGE").getText();
			resultData.put("RETURNCODE", "returnCode");
			resultData.put("MESSAGE", "message");
		}catch(Exception e){
			e.printStackTrace();
		}
        result = Result.buildSuccess();
        result.setResultData(resultData);
    	return result;
    	
    }
    
    private Result getWorkOrderResult(String retXml) {
    	
    	Result result = null;
    	SAXReader reader = new SAXReader();
    	try {
			
    		StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			String totalRecords = root.element("PAGEINFO").element("TOTALRECORDS").getText();
			System.out.println("getWorkOrderResult==>seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			System.out.println("pageIndex=>" + pageIndex + ",pageSize=>" + pageSize + ",totalRecords=>" + totalRecords);
			
    		Map resultMap = new HashMap();
    		List<WorkOrder> list = new ArrayList<WorkOrder>();
    		if ((pageIndex-1) * pageSize < Long.valueOf(totalRecords)) {
    			list = new XMLParseUtils().parseWorkOrderXML(retXml);
    		}
    		Map<Object, Object> resultData = new HashMap<Object, Object>();
    		resultData.put(WorkOrder.WORK_ORDER_LIST_NODE, list);
            int _totalRecords = Integer.parseInt(totalRecords);
            if (_totalRecords%pageSize==0)
            {
            	resultData.put("totalPage", String.valueOf(_totalRecords/pageSize));
            } else {
            	resultData.put("totalPage", String.valueOf(_totalRecords/pageSize+1));
            }
          
    		result = Result.buildSuccess();
    		result.setResultData(resultData);
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
    
    private Result getWorkOrderPlanResult(String retXml) {
    	
    	Result result = null;
    	SAXReader reader = new SAXReader();
    	try {
			
    		StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			String totalRecords = root.element("PAGEINFO").element("TOTALRECORDS").getText();
			System.out.println("getWorkOrderResult==>seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			System.out.println("pageIndex=>" + pageIndex + ",pageSize=>" + pageSize + ",totalRecords=>" + totalRecords);
			
    		Map resultMap = new HashMap();
    		List<WorkOrder> list = new ArrayList<WorkOrder>();
    		if ((pageIndex-1) * pageSize < Long.valueOf(totalRecords)) {
    			list = new XMLParseUtils().parseWorkOrderPlanXML(retXml);
    		}
    		Map<Object, Object> resultData = new HashMap<Object, Object>();
    		resultData.put(WorkOrder.WORK_ORDER_LIST_NODE, list);

    		
          
    		result = Result.buildSuccess();
    		result.setResultData(resultData);
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
    /**
     * 开通详情
     * @param retXml
     * @return
     */
    private Result getKtWorkOrderDetailResult(String retXml) {
    	
    	Result result = null;
    	SAXReader reader = new SAXReader();
    	try {

    		List<List<WorkOrderDetail>> childList = new ArrayList<List<WorkOrderDetail>>();
    		StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			
			//以下开始解析工单详情
			String orderCode = root.element("ORDERINFO").element("ORDERCODE").getText();
			String orderTitle = root.element("ORDERINFO").element("ORDERTITLE").getText();
			String orderState = root.element("ORDERINFO").element("ORDERSTATE").getText();
			
			List<WorkOrderDetail> list1 = new XMLParseUtils().parseWorkOrderDetailXML(retXml);
			list1.add(0, new WorkOrderDetail("订单状态", orderState));
			//Task:132967 list1.add(0, new WorkOrderDetail("订单状态", orderState));
			list1.add(0, new WorkOrderDetail("订单标题", orderTitle));
			list1.add(0, new WorkOrderDetail("订单编码", orderCode));
			
			childList.add(list1);
			
			//以下开始解析工单客户信息详情
			Element element = root.element("CUSTINFO");
			String custName = element.selectSingleNode("CUSTNAME")==null?"":element.element("CUSTNAME").getText();
			String phone = element.selectSingleNode("PHONE")==null?"":element.element("PHONE").getText(); 
			String addrName = element.selectSingleNode("ADDRNAME")==null?"":element.element("ADDRNAME").getText();
			
			List<WorkOrderDetail> list2 = new ArrayList<WorkOrderDetail>();
			
			list2.add(new WorkOrderDetail("客户姓名", custName));
			list2.add(new WorkOrderDetail("客户电话", phone));
			list2.add(new WorkOrderDetail("客户地址", addrName));
			
			// 客户信息放在最前面
			childList.add(0,list2);

			//以下开始解析工单资源信息详情
			List<WorkOrderDetail> list3 = new ArrayList<WorkOrderDetail>();
			
			element = root.element("RESOURCEINFO").element("NEWRESOURCEINFO");
			String nServiceAreaName = element.selectSingleNode("SERVICEAREANAME")==null?"":element.element("SERVICEAREANAME").getText();
			String nExchName = element.selectSingleNode("EXCHNAME")==null?"":element.element("EXCHNAME").getText();
			String nRefAddrName = element.selectSingleNode("REFADDRNAME")==null?"":element.element("REFADDRNAME").getText();
			String nDataResult = element.selectSingleNode("DATARESULT")==null?"":element.element("DATARESULT").getText();
			
			List<WorkOrderDetail> list4 = new ArrayList<WorkOrderDetail>();
			element = root.element("RESOURCEINFO").element("OLDRESOURCEINFO");
			String oServiceAreaName = element.selectSingleNode("SERVICEAREANAME")==null?"":element.element("SERVICEAREANAME").getText();
			String oExchName = element.selectSingleNode("EXCHNAME")==null?"":element.element("EXCHNAME").getText();
			String oRefAddrName = element.selectSingleNode("REFADDRNAME")==null?"":element.element("REFADDRNAME").getText();
			String oDataResult = element.selectSingleNode("DATARESULT")==null?"":element.element("DATARESULT").getText();
			
			// 用于ExpandableList
			list3.add(new WorkOrderDetail("用户服务区", nServiceAreaName));
			list3.add(new WorkOrderDetail("局向名称", nExchName));
			list3.add(new WorkOrderDetail("装机地址", nRefAddrName));
			list3.add(new WorkOrderDetail("数据结果", nDataResult));
			list4.add(new WorkOrderDetail("用户服务区", oServiceAreaName));
			list4.add(new WorkOrderDetail("局向名称", oExchName));
			list4.add(new WorkOrderDetail("装机地址", oRefAddrName));
			list4.add(new WorkOrderDetail("数据结果", oDataResult));
			
			childList.add(list3);
			childList.add(list4);

			//Task:130786
			System.out.println("Begin to get number config info...");
			element = root.element("RESOURCEINFO").element("NUMBERCONFIGS");
			if (element !=null){
				List nbrCfgList = element.selectNodes("NUMBERCONFIG");
				List<WorkOrderDetail> list4_5 = new ArrayList<WorkOrderDetail>();
				
				if(nbrCfgList!=null && nbrCfgList.size()>0)
				{
					for(int i=0;i<nbrCfgList.size();i++)
					{
						String serviceNum = ((Element)nbrCfgList.get(i)).element("SERVICENUM").getText();
						String grpNum = ((Element)nbrCfgList.get(i)).element("GROUPNUM").getText();
						String action = ((Element)nbrCfgList.get(i)).element("MOTION").getText();
						String nbrPrefix = ((Element)nbrCfgList.get(i)).element("NUMPREFIX").getText();
						String numType = ((Element)nbrCfgList.get(i)).element("NUMTYPE").getText();
						
						System.out.println("product" + (i + 1) +"," + serviceNum + "|" + grpNum + "|" + action + "|" + nbrPrefix+ "|" + numType);
						
						list4_5.add(new WorkOrderDetail("业务号码", serviceNum));
						list4_5.add(new WorkOrderDetail("群号", grpNum));
						list4_5.add(new WorkOrderDetail("动作", action));
						list4_5.add(new WorkOrderDetail("号码前缀", nbrPrefix));
						list4_5.add(new WorkOrderDetail("号码类型", numType));
	
					}
				}
				else
				{
					list4_5.add(new WorkOrderDetail("", ""));
				}
				childList.add(list4_5);
			}
			else
			{
				  List<WorkOrderDetail> list4_5 = new  ArrayList<WorkOrderDetail>();
				  list4_5.add(new WorkOrderDetail("", ""));
				  childList.add(list4_5);
			}
			
			
			element = root.element("DESIGNINFO");
			if (element !=null){
				if (element.selectSingleNode("COLUMN1")!= null){
				  List<WorkOrderDetail> list5 = new XMLParseUtils().parseDesignDetailXML(retXml);
				  childList.add(list5);
				}else{
				  List<WorkOrderDetail> list5 = new  ArrayList<WorkOrderDetail>();
			      list5.add(new WorkOrderDetail("", ""));
				  childList.add(list5);	
				}
			}else {
			  List<WorkOrderDetail> list5 = new  ArrayList<WorkOrderDetail>();
			  list5.add(new WorkOrderDetail("", ""));
			  childList.add(list5);				
			}
			
			element = root.element("DATABUSINFO").element("COLUMN2");
			if (element !=null){
				List<WorkOrderDetail> list6 = new XMLParseUtils().parseDataBusiDetailXML(retXml);
				childList.add(list6);
			}else {
				List<WorkOrderDetail> list6 = new  ArrayList<WorkOrderDetail>();
				list6.add(new WorkOrderDetail("", ""));
			    childList.add(list6);				
			}
			
			//Task:130786 增加主体产品信息
			System.out.println("Begin to get main product info...");
			element = root.element("PRODUCTINFO").element("MAINPRODUCTINFO");
			if (element !=null){
				List mainProdList = element.selectNodes("ONEMAININFO");
				List<WorkOrderDetail> list7 = new ArrayList<WorkOrderDetail>();
				if(mainProdList != null && mainProdList.size() > 0)
				{
					for(int i=0;i<mainProdList.size();i++)
					{
						String feaName = ((Element)mainProdList.get(i)).element("FEATURENAME").getText();
						String feaValue = ((Element)mainProdList.get(i)).element("FEATUREVALUE").getText();
						String oldFeaValue = ((Element)mainProdList.get(i)).element("ORIGFEATUREVALUE").getText();
						String action = ((Element)mainProdList.get(i)).element("MAINMOTION").getText();
						
						System.out.println("product" + (i + 1) +"," + feaName + "|" + feaValue + "|" + oldFeaValue + "|" + action);
						
						list7.add(new WorkOrderDetail(feaName, feaValue));
						if(oldFeaValue!=null&&!"".equals(oldFeaValue))
						list7.add(new WorkOrderDetail("原" + feaName , oldFeaValue));
						list7.add(new WorkOrderDetail("动作", action));
					}
				}
				else
				{
					list7.add(new WorkOrderDetail("", ""));
				}
				childList.add(list7);
			}
			else
			{
				  List<WorkOrderDetail> list7 = new  ArrayList<WorkOrderDetail>();
				  list7.add(new WorkOrderDetail("", ""));
				  childList.add(list7);
			}
			//增加附属产品信息
			System.out.println("Begin to get appdent product info...");
			element = root.element("PRODUCTINFO").element("APPPRODUCTINFO");
			if (element !=null){
				List appProdList = element.selectNodes("ONEAPPINFO");
				List<WorkOrderDetail> list8 = new ArrayList<WorkOrderDetail>();
				if(appProdList != null && appProdList.size() > 0)
				{
					for(int i=0;i<appProdList.size();i++)
					{
						String auxProd = ((Element)appProdList.get(i)).element("AUXPRODUCT").getText();
						String appMotion = ((Element)appProdList.get(i)).element("APPMOTION").getText();
						System.out.println("product" + (i + 1) +"," + auxProd + "|" + appMotion);
						list8.add(new WorkOrderDetail("产品名称", auxProd));
						list8.add(new WorkOrderDetail("动作", appMotion));
					}
				}
				else
				{
					list8.add(new WorkOrderDetail("", ""));
				}
				childList.add(list8);
			}
			else
			{
				  List<WorkOrderDetail> list8 = new  ArrayList<WorkOrderDetail>();
				  list8.add(new WorkOrderDetail("", ""));
				  childList.add(list8);
			}
			
			
    		Map resultMap = new HashMap();
    		
    		Map<Object, Object> resultData = new HashMap<Object, Object>();
//    		resultData.put(WorkOrderDetail.WORK_ORDER_DETAIL_LIST_NODE, list);
    		resultData.put(WorkOrderDetail.WORK_ORDER_DETAIL_LIST_NODE, childList);
    		
    		
    		result = Result.buildSuccess();
    		result.setResultData(resultData);
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
    
    // 签单
    private Result getAcceptWorkOrderResult(String retXml) {
    	
    	Result result = null;
    	SAXReader reader = new SAXReader();
    	try {
          
    		StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			System.out.println("seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			
    		result = Result.buildSuccess();
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
    
    /**
     * 保障详情
     * @param retXml
     * @return
     */
    private Result getBzWorkOrderDetailResult(String retXml) {
    	
    	Result result = null;
    	SAXReader reader = new SAXReader();
    	try {

    		List<List<WorkOrderDetail>> childList = new ArrayList<List<WorkOrderDetail>>();
    		StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			
			//以下开始解析工单详情
			String orderCode = root.element("ORDERINFO").element("ORDERCODE").getText();
			String orderTitle = root.element("ORDERINFO").element("ORDERTITLE").getText();
			String orderState = root.element("ORDERINFO").element("ORDERSTATE").getText();
			
			List<WorkOrderDetail> list1 = new ArrayList<WorkOrderDetail>();
			//以下开始解析投诉申告基本详情
			Element element = root.element("COMPBASEINFO");
			
			String faultOrderCode = element.selectSingleNode("FAULTORDERCODE")==null?"":element.element("FAULTORDERCODE").getText();
			String faultNum = element.selectSingleNode("FAULTNUM")==null?"":element.element("FAULTNUM").getText(); 
			String bureauName = element.selectSingleNode("BUREAUNAME")==null?"":element.element("BUREAUNAME").getText();
			String buildName = element.selectSingleNode("BUILDNAME")==null?"":element.element("BUILDNAME").getText();
			String businessType = element.selectSingleNode("BUSINESSTYPE")==null?"":element.element("BUSINESSTYPE").getText(); 
			String faultLevel = element.selectSingleNode("FAULTLEVEL")==null?"":element.element("FAULTLEVEL").getText();
			String custName = element.selectSingleNode("CUSTNAME")==null?"":element.element("CUSTNAME").getText();
			String custLinkMan = element.selectSingleNode("CUSTLINKMAN")==null?"":element.element("CUSTLINKMAN").getText(); 
			String custLinkNum = element.selectSingleNode("CUSTLINKNUM")==null?"":element.element("CUSTLINKNUM").getText();
			String timeOutDate = element.selectSingleNode("TIMEOUTDATE")==null?"":element.element("TIMEOUTDATE").getText();
			String alarmDate = element.selectSingleNode("ALARMDATE")==null?"":element.element("ALARMDATE").getText();
			String faultDuration = element.selectSingleNode("FAULTDURATION")==null?"":element.element("FAULTDURATION").getText(); 
			String handleDuration = element.selectSingleNode("HANDLEDURATION")==null?"":element.element("HANDLEDURATION").getText();
			String faultContent = element.selectSingleNode("FAULTCONTENT")==null?"":element.element("FAULTCONTENT").getText();
			
			list1.add(new WorkOrderDetail("故障单号", faultOrderCode));
			list1.add(new WorkOrderDetail("故障号码", faultNum));
			list1.add(new WorkOrderDetail("所属区局", bureauName));
			list1.add(new WorkOrderDetail("所属大楼", buildName));
			list1.add(new WorkOrderDetail("业务类型", businessType));
			list1.add(new WorkOrderDetail("故障等级", faultLevel));
			list1.add(new WorkOrderDetail("客户名称", custName));
			list1.add(new WorkOrderDetail("客户联系人", custLinkMan));
			list1.add(new WorkOrderDetail("客户联系方式", custLinkNum));
			list1.add(new WorkOrderDetail("超时时限", timeOutDate));
			list1.add(new WorkOrderDetail("预警时限", alarmDate));
			list1.add(new WorkOrderDetail("故障历时", faultDuration));
			list1.add(new WorkOrderDetail("处理历时", handleDuration));
			list1.add(new WorkOrderDetail("故障内容", faultContent));
			
			childList.add(list1);
			
			
			List<WorkOrderDetail> list2 = new ArrayList<WorkOrderDetail>();
			
			//申告附加信息
			element = root.element("SHENGAOADDINFO");
			 
			 custName = element.selectSingleNode("CUSTNAME")==null?"":element.element("CUSTNAME").getText();
			String circuitCode = element.selectSingleNode("CIRCUITCODE")==null?"":element.element("CIRCUITCODE").getText(); 
			String custAdd = element.selectSingleNode("CUSTADD")==null?"":element.element("CUSTADD").getText();
			String custLevel = element.selectSingleNode("CUSTLEVEL")==null?"":element.element("CUSTLEVEL").getText();
			 custLinkNum = element.selectSingleNode("CUSTLINKNUM")==null?"":element.element("CUSTLINKNUM").getText(); 
			String custLinkEmail = element.selectSingleNode("CUSTLINKEMAIL")==null?"":element.element("CUSTLINKEMAIL").getText();
			 custLinkMan = element.selectSingleNode("CUSTLINKMAN")==null?"":element.element("CUSTLINKMAN").getText();
			String custLinkMan2 = element.selectSingleNode("CUSTLINKMAN2")==null?"":element.element("CUSTLINKMAN2").getText(); 
			String custLinkNum2 = element.selectSingleNode("CUSTLINKNUM2")==null?"":element.element("CUSTLINKNUM2").getText();
			 buildName = element.selectSingleNode("BUILDNAME")==null?"":element.element("BUILDNAME").getText();
			String contractNo = element.selectSingleNode("CONTRACTNO")==null?"":element.element("CONTRACTNO").getText();

			list2.add(new WorkOrderDetail("客户姓名", custName));
			list2.add(new WorkOrderDetail("电路编码", circuitCode));
			list2.add(new WorkOrderDetail("客户接入地址", custAdd));
			list2.add(new WorkOrderDetail("客户服务等级", custLevel));
			list2.add(new WorkOrderDetail("客户联系电话", custLinkNum));
			list2.add(new WorkOrderDetail("客户联系人EMAIL", custLinkEmail));
			list2.add(new WorkOrderDetail("客户联系人", custLinkMan));
			list2.add(new WorkOrderDetail("客户第二联系人", custLinkMan2));
			list2.add(new WorkOrderDetail("客户第二联系电话", custLinkNum2));
			list2.add(new WorkOrderDetail("所属大楼", buildName));
			list2.add(new WorkOrderDetail("合同编号", contractNo));
			
			childList.add(list2);

			//投诉附加信息
			List<WorkOrderDetail> list3 = new ArrayList<WorkOrderDetail>();
			
			element = root.element("TOUSUADDINFO");
			
			String complainNum = element.selectSingleNode("COMPLAINNUM")==null?"":element.element("COMPLAINNUM").getText();
			String complainName = element.selectSingleNode("COMPLAINNAME")==null?"":element.element("COMPLAINNAME").getText(); 
			String complainPhone = element.selectSingleNode("COMPLAINPHONE")==null?"":element.element("COMPLAINPHONE").getText();
			String custOrderNum = element.selectSingleNode("CUSTORDERNUM")==null?"":element.element("CUSTORDERNUM").getText();
			String custAccTime = element.selectSingleNode("CUSTACCTIME")==null?"":element.element("CUSTACCTIME").getText(); 
			String userLevel = element.selectSingleNode("USERLEVEL")==null?"":element.element("USERLEVEL").getText();
			String urgency = element.selectSingleNode("URGENCY")==null?"":element.element("URGENCY").getText();
			String userType = element.selectSingleNode("USERTYPE")==null?"":element.element("USERTYPE").getText(); 
			String complainType = element.selectSingleNode("COMPLAINTYPE")==null?"":element.element("COMPLAINTYPE").getText();
			String sysAccTime = element.selectSingleNode("SYSACCTIME")==null?"":element.element("SYSACCTIME").getText();
			String sysReturnTime = element.selectSingleNode("SYSRETURNTIME")==null?"":element.element("SYSRETURNTIME").getText();
			String processType = element.selectSingleNode("PROCESSTYPE")==null?"":element.element("PROCESSTYPE").getText(); 
			String custUrgency = element.selectSingleNode("CUSTURGENCY")==null?"":element.element("CUSTURGENCY").getText();
			buildName = element.selectSingleNode("BUILDNAME")==null?"":element.element("BUILDNAME").getText();
			String address = element.selectSingleNode("ADRESSE")==null?"":element.element("ADRESSE").getText(); 
			String vorderProzess = element.selectSingleNode("VORDERPROZESS")==null?"":element.element("VORDERPROZESS").getText();
			String userSition = element.selectSingleNode("USERSITION")==null?"":element.element("USERSITION").getText();
			
			
			list3.add(new WorkOrderDetail("投诉号码", complainNum));
			list3.add(new WorkOrderDetail("投诉人", complainName));
			list3.add(new WorkOrderDetail("投诉人电话", complainPhone));
			list3.add(new WorkOrderDetail("客服系统工单号", custOrderNum));
			list3.add(new WorkOrderDetail("客服受理时间", custAccTime));
			list3.add(new WorkOrderDetail("用户级别", userLevel));
			list3.add(new WorkOrderDetail("紧急程度", urgency));
			list3.add(new WorkOrderDetail("用户类型", userType));
			list3.add(new WorkOrderDetail("投诉类型", complainType));
			list3.add(new WorkOrderDetail("系统接收时限", sysAccTime));
			list3.add(new WorkOrderDetail("系统回复时限", sysReturnTime));
			list3.add(new WorkOrderDetail("处理类型", processType));
			list3.add(new WorkOrderDetail("客服紧急程度", custUrgency));
			list3.add(new WorkOrderDetail("小区大楼名称", buildName));
			list3.add(new WorkOrderDetail("投诉人联系地址", address));
			list3.add(new WorkOrderDetail("前台处理过程", vorderProzess));
			list3.add(new WorkOrderDetail("周围用户使用情况", userSition));
			
			childList.add(list3);
			
			//故障信息
			List<WorkOrderDetail> list4 = new ArrayList<WorkOrderDetail>();
			
			element = root.element("FAULTBASEINFO");
			
			 faultOrderCode = element.selectSingleNode("FAULTORDERCODE")==null?"":element.element("FAULTORDERCODE").getText();
			String faultOrderTitle = element.selectSingleNode("FAULTORDERTITLE")==null?"":element.element("FAULTORDERTITLE").getText(); 
			 timeOutDate = element.selectSingleNode("TIMEOUTDATE")==null?"":element.element("TIMEOUTDATE").getText();
			String handleOutDate = element.selectSingleNode("HANDLEOUTDATE")==null?"":element.element("HANDLEOUTDATE").getText();
			 faultLevel = element.selectSingleNode("FAULTLEVEL")==null?"":element.element("FAULTLEVEL").getText(); 
			String isInterrupt = element.selectSingleNode("ISINTERRUPT")==null?"":element.element("ISINTERRUPT").getText();
			String hanppenDate = element.selectSingleNode("HANPPENDATE")==null?"":element.element("HANPPENDATE").getText();
			String speKindName = element.selectSingleNode("SPEKINDNAME")==null?"":element.element("SPEKINDNAME").getText(); 
			String speSubName = element.selectSingleNode("SPESUBNAME")==null?"":element.element("SPESUBNAME").getText();
			String faultDetail = element.selectSingleNode("FAULTDETAIL")==null?"":element.element("FAULTDETAIL").getText();
			String preHandle = element.selectSingleNode("PREHANDLE")==null?"":element.element("PREHANDLE").getText();

			
			
			list4.add(new WorkOrderDetail("故障单号", faultOrderCode));
			list4.add(new WorkOrderDetail("故障主题", faultOrderTitle));
			list4.add(new WorkOrderDetail("剩余时间", timeOutDate));
			list4.add(new WorkOrderDetail("处理剩余时间", handleOutDate));
			list4.add(new WorkOrderDetail("故障级别", faultLevel));
			list4.add(new WorkOrderDetail("是否中断业务", isInterrupt));
			list4.add(new WorkOrderDetail("故障发生时间", hanppenDate));
			list4.add(new WorkOrderDetail("专业分类", speKindName));
			list4.add(new WorkOrderDetail("专业细分", speSubName));
			list4.add(new WorkOrderDetail("故障描述", faultDetail));
			list4.add(new WorkOrderDetail("故障预处理情况", preHandle));
			
			
			childList.add(list4);
    		
    		Map<Object, Object> resultData = new HashMap<Object, Object>();
//    		resultData.put(WorkOrderDetail.WORK_ORDER_DETAIL_LIST_NODE, list);
    		resultData.put(WorkOrderDetail.WORK_ORDER_DETAIL_LIST_NODE, childList);
    		   		
    		result = Result.buildSuccess();
    		result.setResultData(resultData);
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
 // 出发
    private Result getDepartWorkOrderResult(String retXml) {
    	
    	Result result = null;
    	SAXReader reader = new SAXReader();
    	try {
          
    		StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			System.out.println("seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			
    		result = Result.buildSuccess();
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
    
    private int pageIndex;
    private int pageSize;
    /**
     * 分页查询工单
     * @param pageSize
     * @param staffInfo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/workorder/page"})
    public @ResponseBody Result selWorkOrderByPage(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	if(logger.isDebugEnabled()) {
    	    logger.debug(" Call selWorkOrderByPage method ,json====="+json);
    	}
        pageIndex = json.getInt("pageIndex");
        pageSize = json.getInt("pageSize");
        String style = json.getString("style");
        int orderByType = json.getInt("orderByType");
        //Task:127162 132967
        String isToday = "";
        String custName = "";
        String addNbr = "";
        if (style.equals("IOM"))
        {
        	isToday =  json.getString("isToday");
        	custName = json.getString("custName");
        	addNbr = json.getString("addNbr");
        }
       
        Long isHangup = json.getLong("isHangup");
        Long orgId = json.getLong(JobInfo.ORG_ID_NODE);
        Long jobId = json.getLong(JobInfo.JOB_ID_NODE);
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        String staffName = json.getString(StaffInfo.STAFF_NAME_NODE);
        StaffInfo staffInfo = new StaffInfo();
    	staffInfo.setStaffId(staffId);
    	staffInfo.setStaffName(staffName);
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<ORGID>"+orgId+"</ORGID>");
        sb.append("<JOBID>"+jobId+"</JOBID>");
        sb.append("<PAGESIZE>"+pageSize+"</PAGESIZE>");
        sb.append("<PAGEINDEX>"+pageIndex+"</PAGEINDEX>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("<ISHANDUP>"+isHangup+"</ISHANDUP>");
        sb.append("<ORDERBYTYPE>"+orderByType+"</ORDERBYTYPE>");
        sb.append("<ISTODAY>"+isToday+"</ISTODAY>");
        sb.append("<CUSTNAME>"+custName+"</CUSTNAME>");
        sb.append("<ADDNBR>"+addNbr+"</ADDNBR>");
        sb.append("</OPDETAIL>");
        String reqXml = sb.toString();
        System.out.println("reqXml===="+reqXml);
        String retXml = "";
        
        if (DEBUG){
        	if (style.equals("IOM"))
        	  retXml=XmlDataUtil.getRemotelXmlData("ktWorkOrderList");      	
        	else 
        	  retXml=XmlDataUtil.getRemotelXmlData("bzWorkOrderList");    
        } else {
        	retXml = callWebService(reqXml,WORK_ORDER_LIST);
        }
        logger.debug("retXml===== "+retXml);
//        
//        //解析返回的XML
        Result result = getWorkOrderResult(retXml);
        
        //Result result = getWorkOrderService().selWorkOrderByPage(staffInfo, new Date().getTime(), 1, 5);
        return result;
    }
    
    
    @RequestMapping(value = {"/client/workorder/accept"})
    public @ResponseBody Result acceptWorkOrder(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call acceptWorkOrder method ");
        }
        
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.optLong(StaffInfo.STAFF_ID_NODE);
        String staffName = json.optString(StaffInfo.STAFF_NAME_NODE);
        Long orgId = json.optLong(JobInfo.ORG_ID_NODE);
        Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
        Long workOrderId = json.optLong(WorkOrder.WORK_ORDER_ID_NODE);
        Long orderId = json.optLong(WorkOrder.ORDER_ID_NODE);
        String workorderState = json.optString(WorkOrder.WORK_ORDER_STATE);
        String orderCode = json.optString(WorkOrder.ORDER_CODE);
        String style = json.getString("style");
        
        StringBuffer sb = new StringBuffer();
    	sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<STAFFNAME>"+staffName+"</STAFFNAME>");
        sb.append("<ORGID>"+orgId+"</ORGID>");
        sb.append("<JOBID>"+jobId+"</JOBID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<WORKORDERSTATE>"+workorderState+"</WORKORDERSTATE>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<ORDERCODE>"+orderCode+"</ORDERCODE>");
        sb.append("<FINISHDATE>"+getSysdate()+"</FINISHDATE>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        String reqXml = sb.toString();
       // String retXml = callWebService(reqXml,WORK_ORDER_ACCEPT);
        
        String retXml = "";
        if (DEBUG){
        	 retXml=XmlDataUtil.getRemotelXmlData("workOrderAccept"); 
        } else {
        	retXml = callWebService(reqXml,WORK_ORDER_ACCEPT);
        }
        Result result = getRetResult(retXml);
        
        return result;
    }
    
    @RequestMapping(value = {"/client/workorder/detail"})
    public @ResponseBody Result selWorkOrderDetail(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call selWorkOrderDetial method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long orderId = json.optLong(WorkOrder.ORDER_ID_NODE);
        Long workOrderId = json.optLong(WorkOrder.WORK_ORDER_ID_NODE);
        String style = json.getString("style");
        
        StringBuffer sb = new StringBuffer();
    	sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        String reqXml = sb.toString();
        
        
        String retXml = "";//callWebService(reqXml,WORK_ORDER_DETAIL);
        Result result = null;
        if (DEBUG){
        	if (style.equals("IOM"))
        	  retXml=XmlDataUtil.getRemotelXmlData("ktWorkOrderDetail");      	
        	else 
        	  retXml=XmlDataUtil.getRemotelXmlData("bzWorkOrderDetail");    
        } else {
        	retXml = callWebService(reqXml,WORK_ORDER_DETAIL);
        }
        if (style.equals("IOM"))
            result = getKtWorkOrderDetailResult(retXml);
        else 
        	result = getBzWorkOrderDetailResult(retXml);
        
        return result;
    }
    
    @RequestMapping(value = {"/client/workorder/depart"})
	    public @ResponseBody Result departWorkOrder(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception{
	        if(logger.isDebugEnabled()) {
	        	logger.debug(" Call departWorkOrder method ");
	        }
	        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
	        Long staffId = json.optLong(StaffInfo.STAFF_ID_NODE);
	        Long workOrderId = json.optLong(WorkOrder.WORK_ORDER_ID_NODE);
	        String style = json.getString("style");
	        
	        StringBuffer sb = new StringBuffer();
	    	sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
	        sb.append("<OPDETAIL>");
	        sb.append("<STAFFID>"+staffId+"</STAFFID>");
	        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
	        sb.append("<STARTDATE>"+getSysdate()+"</STARTDATE>");
	        sb.append("<STYLE>"+style+"</STYLE>");
	        sb.append("</OPDETAIL>");
	        String reqXml = sb.toString();
	       // String retXml = callWebService(reqXml,WORK_ORDER_DEPART);
	        
	        String retXml = "";
	        if (DEBUG){
	        	 retXml=XmlDataUtil.getRemotelXmlData("workOrderDepart"); 
	        } else {
	        	retXml = callWebService(reqXml,WORK_ORDER_DEPART);
	        }
	        Result result = getRetResult(retXml);
	        
	        return result;
	    }

    @RequestMapping(value = {"/client/workorder/depart/cancel"})
    public @ResponseBody Result cancelDepartWorkOrder(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception{
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call cancelDepartWorkOrder method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.optLong(StaffInfo.STAFF_ID_NODE);
        Long workOrderId = json.optLong(WorkOrder.WORK_ORDER_ID_NODE);
        String style = json.getString("style");
        String cancelReason = json.getString("cancelReason");
        
        StringBuffer sb = new StringBuffer();
    	sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<COMMENTS>"+cancelReason+"</COMMENTS>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        String reqXml = sb.toString();
        //String retXml = callWebService(reqXml,WORK_ORDER_DEPART_CANCEL);
        String retXml = "";
        if (DEBUG){
        	 retXml=XmlDataUtil.getRemotelXmlData("workOrderDepartCancel"); 
        } else {
        	retXml = callWebService(reqXml,WORK_ORDER_DEPART_CANCEL);
        }
        Result result = getRetResult(retXml);
        
        return result;
    }
    
	@RequestMapping(value = {"/client/workorder/personalplan"})
    public @ResponseBody Result selPersonalPlan(@RequestBody  RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call selPersonalPlan method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        pageIndex = json.getInt("pageIndex");
        pageSize = json.getInt("pageSize");
        String style = json.getString("style");
        Long isHangup = json.getLong("isHangup");
        Long orgId = json.getLong(JobInfo.ORG_ID_NODE);
        Long jobId = json.getLong(JobInfo.JOB_ID_NODE);
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        String staffName = json.getString(StaffInfo.STAFF_NAME_NODE);
        StaffInfo staffInfo = new StaffInfo();
    	staffInfo.setStaffId(staffId);
    	staffInfo.setStaffName(staffName);
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<ORGID>"+orgId+"</ORGID>");
        sb.append("<JOBID>"+jobId+"</JOBID>");
        sb.append("<PAGESIZE>"+pageSize+"</PAGESIZE>");
        sb.append("<PAGEINDEX>"+pageIndex+"</PAGEINDEX>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("<ISHANDUP>"+isHangup+"</ISHANDUP>");
        sb.append("<ORDERBYTYPE>1</ORDERBYTYPE>");
        sb.append("</OPDETAIL>");
        String reqXml = sb.toString();
        String retXml = callWebService(reqXml,WORK_ORDER_PLAN);
        
        //解析返回的XML
        Result result = getWorkOrderPlanResult(retXml);
        return result;
    }
    
    @RequestMapping(value = {"/client/workorder/signin"})
    public @ResponseBody Result workOrderSignIn(@RequestBody  RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if(logger.isDebugEnabled()) {
        	logger.debug(" Call workOrderSignIn method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Result result = null;
        
        Long workOrderId = json.getLong(WorkOrder.WORK_ORDER_ID_NODE);
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        String gpsCoordinate = json.getString("gpsCoordinate");
        String address = json.getString("address");
        String style = json.getString("style");
        
        StringBuffer sb = new StringBuffer();
    	sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<SIGNINDATE>"+getSysdate()+"</SIGNINDATE>");
        sb.append("<GPSCOORDINATE>"+gpsCoordinate+"</GPSCOORDINATE>");
        sb.append("<ADDRESS>"+address+"</ADDRESS>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        //String retXml = callWebService(reqXml,WORK_ORDER_SIGNIN);
        String retXml = "";
        if (DEBUG){
        	 retXml=XmlDataUtil.getRemotelXmlData("workOrderSignin"); 
        } else {
        	retXml = callWebService(reqXml,WORK_ORDER_SIGNIN);
        }
        //解析返回的XML
    	result = getRetResult(retXml);
//        result = Result.buildSuccess();
        return result;
    }
    
 // 获取返回报文结果
    private Result getRetResult(String retXml) {
    	
    	Result result = null;
    	SAXReader reader = new SAXReader();
    	try {
    		StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			System.out.println("getRetResult==>seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			 
			if (returnCode != null && returnCode.equals(Constants.XmlResult.SUCCESS)) {
				result = Result.buildSuccess();
			} else {
//				result = Result.buildServerError();
				result = Result.buildWorkOrderError(returnDetail);
			}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
}
