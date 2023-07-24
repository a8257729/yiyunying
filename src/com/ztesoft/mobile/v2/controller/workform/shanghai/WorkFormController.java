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
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.shanghai.MobileAccurateSubscribe;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrderDetail;
import com.ztesoft.mobile.v2.service.workform.shanghai.WorkOrderService;
import com.ztesoft.mobile.v2.util.XMLParseUtils;
import com.ztesoft.mobile.v2.util.XmlDataUtil;
import com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplServiceLocator;
import com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplSoapBindingStub;

@Controller("workformController")
public class WorkFormController extends BaseController {
    private static final Logger logger = Logger.getLogger(WorkFormController.class);

    public static final String COMMENTS_NODE ="comments";
    public static final String STYLE_NODE = "style";
    public static final String STAFF_ID_NODE = "staffId";
    public static final String MISSIONSTAFF_ID_NODE = "missionStaffId";
    public static final String MISSIONSTAFF_NAME_NODE = "missionStaffName";
    public static final String ALTER_DATE_NODE = "alterDate";
    public static final String LIMIT_DATE_NODE = "limitDate";
    public static final String WORKORDER_ID_NODE = "workOrderId";
    public static final String PHASEDATE_NODE = "phaseDate";
    public static final String FINISHDATE_NODE = "finishDate";
    public static final String PIC_USER_ID_NODE = "picUserId";
    
    public static final String CANCELDATE_NODE = "cancelDate";
    public static final String REASON_NODE = "reason";
    public static final String DEAL_RESULT = "dealResult";
    private static String FEEDBACK_URL = "";
    private static String CHOOSE_ASSISTANTPERSON_URL = "";
    private static String SUBMIT_ASSISTANTPERSON_URL = "";
    private static String SUBMIT_TECSUPPORT_URL = "";
    private static String ACCURATE_SUBSCRIBE_URL = "";
    private static String CANCEL_ORDER_URL = "";
    
    private static final int REPLY_ORDER = 1;
    private static final int ACCURATE_SUBSCRIBE = 2;
    private static final int SUBMIT_TECSUPPORT = 3;
    private static final int SUBMIT_ASSISTANTPERSON = 4;
    private static final int CHOOSE_ASSISTANTPERSON = 5;
    private static final int FEEDBACK = 6;
    private static final int CANCEL_ORDER = 7;
    private static final int DELAY_ORDER = 8;
    private static final int INIT_ACCURATE_SUBSCRIBE = 9;
  //Task:122859
    private static final int FTTH_RATE_TEST = 10;
    
    private WorkOrderService workOrderService;

    private WorkOrderService getWorkOrderService() {
        return workOrderService;
    }
    
    @Autowired(required = false)
    public void setWorkOrderService(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }
    
    private String getRequestXml(String reqXml)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>");
    	sb.append("<SERVICE>");
    	sb.append(reqXml);
    	sb.append("</SERVICE>");
    	return sb.toString();
    }
    
    private String callWebService(String reqXml,int optType) 
    {
    	reqXml = getRequestXml(reqXml);
    	System.out.println("reqXml==========="+reqXml);
    	String retXml = null;
    	
    	WfmToHandTerminalImplServiceLocator wttsl = new WfmToHandTerminalImplServiceLocator();
 		WfmToHandTerminalImplSoapBindingStub wttsb = null;
		try {
			wttsb = (WfmToHandTerminalImplSoapBindingStub)wttsl.getWfmToHandTerminalImpl();
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
 		
 		try {
 			switch (optType) {
 	    	case REPLY_ORDER:
 	    		retXml = wttsb.returnWorkOrder(reqXml);
 	    		break;
 	    	case ACCURATE_SUBSCRIBE:
 	    		retXml = wttsb.exectBesWorkOrder(reqXml);
 	    		break;
 	    	case CANCEL_ORDER:
 	    		retXml = wttsb.cancelWorkOrder(reqXml);
 	    		break;
 	    	case FEEDBACK:
 	    		retXml = wttsb.phaseWorkOrder(reqXml);
 	    		break;
 	    	case DELAY_ORDER:
 	    		retXml = wttsb.hangUpWorkOrder(reqXml);
 	    		break;
 	    	case INIT_ACCURATE_SUBSCRIBE:
 	    		retXml = wttsb.bestInitWorkOrder(reqXml);
 	    		break;
 	    	case CHOOSE_ASSISTANTPERSON:
 	    		retXml = wttsb.selAllSuppleStaffs(reqXml);
 	    		break;
 	    	case SUBMIT_ASSISTANTPERSON:
 	    	case SUBMIT_TECSUPPORT:
 	    		retXml = wttsb.addMissionWorkOrder(reqXml);
 	    		break;
 	    		//Task:122859
 			case FTTH_RATE_TEST:
 				retXml = wttsb.ftthRateTest(reqXml);
 				break;
 	    	}
 		} catch (Exception e) {
		}
    	
    	System.out.println("retXml==========="+retXml);
    	return retXml;
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
    
    
    private Result getAssistantPersonResult(String retXml)
    {
    	Result result = null;
		SAXReader reader = new SAXReader();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			System.out.println("seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			
			resultData.put("RETURNCODE", "returnCode");
			resultData.put("RETURNDETAIL", "returnDetail");
			
			List retList = new XMLParseUtils().parseAssistantPersonXML(retXml);
			
//			Map person = new HashMap();
//		    List list = new ArrayList();
//		    person.put("STAFFID","003");
//		    person.put("STAFFNAME","????");
//		    list.add(person);
//		    
//		    person = new HashMap();
//		    person.put("STAFFID","004");
//		    person.put("STAFFNAME","????");
//		    list.add(person);
//		    
//		    person = new HashMap();
//		    person.put("STAFFID","005");
//		    person.put("STAFFNAME","????");
//		    list.add(person);
//		    
//		    person = new HashMap();
//		    person.put("STAFFID","006");
//		    person.put("STAFFNAME","????");
//		    list.add(person);
		    
		    resultData.put("PERSON",retList);
		}catch(Exception e){
			e.printStackTrace();
		}
        result = Result.buildSuccess();
        result.setResultData(resultData);
    	return result;
    	
    }
    
    private Result getAssistantPersonResult2(String retXml)
    {
    	Result result = null;
		SAXReader reader = new SAXReader();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			System.out.println("seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			
			resultData.put("RETURNCODE", returnCode);
			resultData.put("RETURNDETAIL", returnDetail);
			
			List retList = new XMLParseUtils().parseAssistantPersonXML(retXml);
			
		}catch(Exception e){
			e.printStackTrace();
		}
        result = Result.buildSuccess();
        result.setResultData(resultData);
    	return result;
    }
    
    private Result getInitAccureateSubResult(String retXml)
    {
    	Result result = null;
		SAXReader reader = new SAXReader();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			System.out.println("seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			
			// ??????????????
			String bespDate = root.element("PREINFO").element("BESPDATE").getText();
			String eespDate = root.element("PREINFO").element("EESPDATE").getText();
			// ???????????????
			String bespDate2 = root.element("PREINFO").element("BESPDATE2").getText();
			String eespDate2 = root.element("PREINFO").element("EESPDATE2").getText();
					
			String custName = root.element("PREINFO").element("CUSTNAME").getText();
			String phone = root.element("PREINFO").element("PHONE").getText();
			String addrName = root.element("PREINFO").element("ADDRNAME").getText();
			
			MobileAccurateSubscribe mobileAccurateSub = new MobileAccurateSubscribe();
			mobileAccurateSub.setBespDate(bespDate);
			mobileAccurateSub.setEespDate(eespDate);
			mobileAccurateSub.setBespDate2(bespDate2);
			mobileAccurateSub.setEespDate2(eespDate2);
			mobileAccurateSub.setCustName(custName);
			mobileAccurateSub.setPhone(phone);
			mobileAccurateSub.setAddrName(addrName);
			
    		resultData.put(MobileAccurateSubscribe.ACCURATE_SUBSCRIBE_NODE, mobileAccurateSub);
    		result = Result.buildSuccess();
            result.setResultData(resultData);
		}catch(Exception e){
			e.printStackTrace();
			result = Result.buildServerError();
		}
    	return result;
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
    private String parseRetXml(String retXml)
    {
    	return "";
    }
    /**
     * ??¦Ç?????
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/phasefeedback/submit"})
    public @ResponseBody Result feedback(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call phasefeedback method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        String staffName = json.getString(StaffInfo.STAFF_NAME_NODE);
        String orgName = json.getString(JobInfo.ORG_NAME_NODE);
        String workOrderId = json.getString(WorkOrder.WORK_ORDER_ID_NODE);
        String orderId = json.getString(WorkOrder.ORDER_ID_NODE);
        String comments = json.getString(COMMENTS_NODE);
        String picUserId  = json.getString(PIC_USER_ID_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

//        staffId = 1361l;
//        staffName = "??????????????";
    	
        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<STAFFNAME>"+staffName+"</STAFFNAME>");
        sb.append("<ORGNAME>"+orgName+"</ORGNAME>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<COMMENTS>"+comments+"</COMMENTS>");
        sb.append("<PICTUREID>"+picUserId+"</PICTUREID>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,FEEDBACK);
       if (DEBUG){
     	   retXml=XmlDataUtil.getRemotelXmlData("submitReturn"); 
       } else {
     	   retXml = callWebService(reqXml,FEEDBACK);
       }
    	Result result = getRetResult(retXml);
    
        return result;
    }
    
    /**
     * ?????????
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/chooseassistantperson/query"})
    public @ResponseBody Result chooseassistantperson(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call assistantperson method ");
        }
        
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<SUPPLETYPE>1</SUPPLETYPE>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,CHOOSE_ASSISTANTPERSON);
        if (DEBUG){
       	   retXml=XmlDataUtil.getRemotelXmlData("chooseAssistantperson"); 
       } else {
       	  retXml = callWebService(reqXml,CHOOSE_ASSISTANTPERSON);
       }
    	Result result = getAssistantPersonResult(retXml);
    	
        return result;
    }
    /**
     * ???????????
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/submitassistantperson/submit"})
    public @ResponseBody Result submitassistantperson(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call submitassistantperson method ");
        }
        
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        String staffId = json.getString(StaffInfo.STAFF_ID_NODE);
        String workOrderId = json.getString(WorkOrder.WORK_ORDER_ID_NODE);
        String missionstaffId = json.getString(MISSIONSTAFF_ID_NODE);
        String missionstaffName = json.getString(MISSIONSTAFF_NAME_NODE);
        String comments = json.getString(COMMENTS_NODE);
        String alterDate = json.getString(ALTER_DATE_NODE);
        String limitDate = json.getString(LIMIT_DATE_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<MISSIONSTAFFID>"+missionstaffId+"</MISSIONSTAFFID>");
        sb.append("<MISSIONSTAFFNAME>"+missionstaffName+"</MISSIONSTAFFNAME>");
        sb.append("<COMMENTS>"+comments+"</COMMENTS>");
        sb.append("<ALERTDATE>"+alterDate+"</ALERTDATE>");
        sb.append("<LIMITDATE>"+limitDate+"</LIMITDATE>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,SUBMIT_ASSISTANTPERSON);
        if (DEBUG){
        	   retXml=XmlDataUtil.getRemotelXmlData("submitReturn"); 
        } else {
        	  retXml = callWebService(reqXml,SUBMIT_ASSISTANTPERSON);
        }
    	Result result = getRetResult(retXml);
        return result;
    }
    /**
     * ???
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/cancelorder/submit"})
    public @ResponseBody Result cancelorder(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call cancelOrder method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        Long jobId = json.getLong(JobInfo.JOB_ID_NODE);
        Long orgId = json.getLong(JobInfo.ORG_ID_NODE);
        String staffName = json.getString(StaffInfo.STAFF_NAME_NODE);
        String userName = json.getString(StaffInfo.USERNAME_NODE);
        String workOrderId = json.getString(WorkOrder.WORK_ORDER_ID_NODE);
        String orderId = json.getString(WorkOrder.ORDER_ID_NODE);
        String workOrderState = json.getString(WorkOrder.WORK_ORDER_STATE);
        String cancelReason = json.getString(REASON_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

//        staffId = 1361l;
    	
        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<STAFFNAME>"+staffName+"</STAFFNAME>");
        sb.append("<USERNAME>"+userName+"</USERNAME>");
        sb.append("<JOBID>"+jobId+"</JOBID>");
        sb.append("<ORGID>"+orgId+"</ORGID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<WORKORDERSTATE>"+workOrderState+"</WORKORDERSTATE>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<CANCELREASON>"+cancelReason+"</CANCELREASON>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,CANCEL_ORDER);
        if (DEBUG){
     	   retXml=XmlDataUtil.getRemotelXmlData("submitReturn"); 
       } else {
     	  retXml = callWebService(reqXml,CANCEL_ORDER);
       }
    	Result result = getRetResult(retXml);
        return result;
    }
    
    /**
     * ??????
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/cancelorder/reason"})
    public @ResponseBody Result cancelorderereason(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call cancelorderereason method ");
        }
        StringBuffer sb = new StringBuffer();
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,CANCEL_ORDER);
        retXml=XmlDataUtil.getRemotelXmlData("cancelorderReason"); 
        
    	Result result = getRetResult(retXml);
        return result;
    }
    /**
     * ???????
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping(value = {"/client/delayorder/submit"})
    public @ResponseBody Result delayorder(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call delayOrder method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        String staffName = json.getString(StaffInfo.STAFF_NAME_NODE);
        Long orgId = json.optLong(JobInfo.ORG_ID_NODE);
        Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
        Long workOrderId = json.optLong(WorkOrder.WORK_ORDER_ID_NODE);
        String workOrderState = json.optString(WorkOrder.WORK_ORDER_STATE);
        Long orderId = json.optLong(WorkOrder.ORDER_ID_NODE);
        String delayReason = json.getString(REASON_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

//        staffId = 1361l;
//        staffName = "??????????????";
//    	jobId = 1073l;
//    	orgId = 602l;
    	
        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<STAFFNAME>"+staffName+"</STAFFNAME>");
        sb.append("<ORGID>"+orgId+"</ORGID>");
        sb.append("<JOBID>"+jobId+"</JOBID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<WORKORDERSTATE>"+workOrderState+"</WORKORDERSTATE>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<COMMENTS>"+delayReason+"</COMMENTS>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,DELAY_ORDER);
        if (DEBUG){
      	   retXml=XmlDataUtil.getRemotelXmlData("submitReturn"); 
        } else {
      	  retXml = callWebService(reqXml,DELAY_ORDER);
        }
    	Result result = getRetResult(retXml);
        return result;
    }
    /**
     * ???
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/replyorder/submit"})
    public @ResponseBody Result replyorder(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call replyOrder method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        String staffName = json.getString(StaffInfo.STAFF_NAME_NODE);
        Long jobId = json.getLong(JobInfo.JOB_ID_NODE);
        Long orgId = json.getLong(JobInfo.ORG_ID_NODE);
        Long workOrderId = json.getLong(WorkOrder.WORK_ORDER_ID_NODE);
        String workOrderState = json.getString(WorkOrder.WORK_ORDER_STATE);
        String dealResult = json.getString(DEAL_RESULT);
        String finishDate = json.getString("finishDate");
      //Task:122859

        String style = json.getString(STYLE_NODE);
        
        String avgSpeed = "";
        String maxSpeed = "";
        String avgminpercent = "";
        String noFinishReason = "";
        if("IOM".equals(style))
        {
            avgSpeed = json.getString("avgSpeed");
            maxSpeed = json.getString("maxSpeed");
            avgminpercent = json.getString("avgminpercent");
            noFinishReason = json.getString("noFinishReason");
        }

        
        StringBuffer sb = new StringBuffer();
        
        System.out.println("staffId=>" + staffId + ",staffName=>" + staffName + ",jobId=>" + jobId 
        		+ ",orgId=>" + orgId + ",workOrderId=>" + workOrderId 
        		+ ",avgSpeed=>" + avgSpeed
        		+ ",maxSpeed=>" + maxSpeed
        		+ ",avgminpercent=>" + avgminpercent
        		+ ",noFinishReason=>" + noFinishReason
        		+ ",dealResult=>" + dealResult + ",style=>" + style);
        
//        staffId = 1361l;
//    	jobId = 1073l;
//    	orgId = 602l;
//    	workOrderId = 177803l;
    	
        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<STAFFNAME>"+staffName+"</STAFFNAME>");
        sb.append("<ORGID>"+orgId+"</ORGID>");
        sb.append("<JOBID>"+jobId+"</JOBID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<WORKORDERSTATE>"+workOrderState+"</WORKORDERSTATE>");
        sb.append("<WORKRETURNDATE>"+getSysdate()+"</WORKRETURNDATE>");
        sb.append("<DEALRESULT>"+dealResult+"</DEALRESULT>");
        sb.append("<OVERTIMETYPE>"+""+"</OVERTIMETYPE>");
        sb.append("<OVERTIMEREASON>"+""+"</OVERTIMEREASON>");
        sb.append("<AVGSPEED>"+avgSpeed+"</AVGSPEED>");
        sb.append("<MAXSPEED>"+maxSpeed+"</MAXSPEED>");
        sb.append("<AVGMINPERCENT>"+avgminpercent+"</AVGMINPERCENT>");
        sb.append("<FINISHDATE>"+finishDate+"</FINISHDATE>");
        //Task:122859
        sb.append("<NOFINISHREASON>"+noFinishReason+"</NOFINISHREASON>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,REPLY_ORDER);
        if (DEBUG){
       	   retXml=XmlDataUtil.getRemotelXmlData("submitReturn"); 
         } else {
       	  retXml = callWebService(reqXml,REPLY_ORDER);
         }
    	Result result = getRetResult(retXml);
    	
   //     Result result = Result.buildSuccess();
        return result;
    }

    /**
     * ??????? //Task:122859
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/ftthRate/submit"})
    public @ResponseBody Result ftthRate(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call ftthRate method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long workOrderId = json.getLong(WorkOrder.WORK_ORDER_ID_NODE);
        Long orderId = json.getLong(WorkOrder.ORDER_ID_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();
        
        System.out.println("workOrderId=>" + workOrderId 
        		+ ",ORDERID=>" + orderId + ",style=>" + style);
        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,FTTH_RATE_TEST);
        /**
        String retXml = 
        		"<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
        				"<SERVICE>\n" + 
        				"\t<SERINO>11111</SERINO>\n" + 
        				"\t<RETURNINFO>\n" + 
        				"\t    <RETURNCODE>000</RETURNCODE>\n" + 
        				"         <RETURNDETAIL></RETURNDETAIL>\n" + 
        				"\t</RETURNINFO>\n" + 
        				"     <ORDERINFO>\n" + 
        				"<AVGSPEED>2M</AVGSPEED>\n" + 
        				"<MAXSPEED>4M</MAXSPEED>\n" + 
        				"<AVGISSCART>0</AVGISSCART>\n" + 
        				"<MAXISSCART>1</MAXISSCART>\n" + 
        				"     </ORDERINFO>\n" + 
        				"     </SERVICE>";
        ;**/
        if (DEBUG){
        	   retXml=XmlDataUtil.getRemotelXmlData("ftthRateTest"); 
          } else {
        	  retXml = callWebService(reqXml,FTTH_RATE_TEST);
          }
    	Result result = getFtthRateResult(retXml);
   //     Result result = Result.buildSuccess();
        return result;
    }    
    
    /**
     * ???????
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/tecSupport/submit"})
    public @ResponseBody Result tecSupport(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call tecSupport method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        String staffId = json.getString(StaffInfo.STAFF_ID_NODE);
        String workOrderId = json.getString(WorkOrder.WORK_ORDER_ID_NODE);
        String missionstaffId = json.getString(MISSIONSTAFF_ID_NODE);
        String missionstaffName = json.getString(MISSIONSTAFF_NAME_NODE);
        String comments = json.getString(COMMENTS_NODE);
        String alterDate = json.getString(ALTER_DATE_NODE);
        String limitDate = json.getString(LIMIT_DATE_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<MISSIONSTAFFID>"+missionstaffId+"</MISSIONSTAFFID>");
        sb.append("<MISSIONSTAFFNAME>"+missionstaffName+"</MISSIONSTAFFNAME>");
        sb.append("<COMMENTS>"+comments+"</COMMENTS>");
        sb.append("<ALERTDATE>"+alterDate+"</ALERTDATE>");
        sb.append("<LIMITDATE>"+limitDate+"</LIMITDATE>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        String retXml = "";//callWebService(reqXml,SUBMIT_TECSUPPORT);
        if (DEBUG){
        	   retXml=XmlDataUtil.getRemotelXmlData("submitReturn"); 
          } else {
        	  retXml = callWebService(reqXml,SUBMIT_TECSUPPORT);
          }
    	Result result = getRetResult(retXml);
        return result;
    }
    
    /**
     * ?????
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/accurateSub/submit"})
    public @ResponseBody Result accureateSub(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call accurate method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        String staffName = json.getString(StaffInfo.STAFF_NAME_NODE);
        String workOrderId = json.getString(WorkOrder.WORK_ORDER_ID_NODE);
        String orderId = json.getString(WorkOrder.ORDER_ID_NODE);
        String besStartDate = json.getString("besStartDate");
        String besEndDate = json.getString("besEndDate");
        String reason = json.getString(REASON_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<STAFFID>"+staffId+"</STAFFID>");
        sb.append("<STAFFNAME>"+staffName+"</STAFFNAME>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<BESSTARTDATE>"+besStartDate+"</BESSTARTDATE>");
        sb.append("<BESENDDATE>"+besEndDate+"</BESENDDATE>");
        sb.append("<BESREASON>"+reason+"</BESREASON>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        //String retXml = callWebService(reqXml,ACCURATE_SUBSCRIBE);
        String retXml = "";
        if (DEBUG){
        	 retXml=XmlDataUtil.getRemotelXmlData("accurateSubscribe"); 
        } else {
        	retXml = callWebService(reqXml,ACCURATE_SUBSCRIBE);
        }
    	Result result = getRetResult(retXml);
        return result;
    }
    
    /**
     * ??????????
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/accurateSub/init"})
    public @ResponseBody Result initAccureateSub(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call accurate init method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        String workOrderId = json.getString(WorkOrder.WORK_ORDER_ID_NODE);
        String orderId = json.getString(WorkOrder.ORDER_ID_NODE);
        String style = json.getString(STYLE_NODE);
        StringBuffer sb = new StringBuffer();

        sb.append("<SERINO>"+getSeriNo()+"</SERINO>");
        sb.append("<OPDETAIL>");
        sb.append("<WORKORDERID>"+workOrderId+"</WORKORDERID>");
        sb.append("<ORDERID>"+orderId+"</ORDERID>");
        sb.append("<STYLE>"+style+"</STYLE>");
        sb.append("</OPDETAIL>");
        
        String reqXml = sb.toString();
        //String retXml = callWebService(reqXml,INIT_ACCURATE_SUBSCRIBE);
        String retXml = "";
        if (DEBUG){
        	 retXml=XmlDataUtil.getRemotelXmlData("initAccurateSubscribe"); 
        } else {
        	retXml = callWebService(reqXml,INIT_ACCURATE_SUBSCRIBE);
        }
    	Result result = getInitAccureateSubResult(retXml);
        return result;
    }
    
 // ????????????
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
			System.out.println("seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			
			if (returnCode != null && returnCode.equals(Constants.XmlResult.SUCCESS)) {
				result = Result.buildSuccess();
			} else {
				result = Result.buildWorkOrderError(returnDetail);
			}
			
    	} catch (Exception e) {
    		e.printStackTrace();
    		result = Result.buildServerError();
      }
      return result;
    }
    
    private Result getFtthRateResult(String retXml)
    {
    	Result result = null;
		SAXReader reader = new SAXReader();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			
			String seriNo = root.element("SERINO").getText();
			String returnCode = root.element("RETURNINFO").element("RETURNCODE").getText();
			String returnDetail = root.element("RETURNINFO").element("RETURNDETAIL").getText();
			System.out.println("seriNo==>" + seriNo + ",returnCode==>" + returnCode + ",returnDetail==>" + returnDetail);
			
			//
			String avgRate = root.element("ORDERINFO").element("AVGSPEED").getText();
			String maxRate = root.element("ORDERINFO").element("MAXSPEED").getText();
			String avgIsScart = root.element("ORDERINFO").element("AVGISSCART").getText();
			String maxIsScart = root.element("ORDERINFO").element("MAXISSCART").getText();
			String avgminpercent = root.element("ORDERINFO").element("AVGMINPERCENT").getText();
			System.out.println("avgRate==>" + avgRate + ",maxRate==>" + maxRate );
			
			resultData.put("returnCode", returnCode);
			resultData.put("returnDetail", returnDetail);
			resultData.put("avgRate", avgRate);
			resultData.put("maxRate", maxRate);
			resultData.put("avgIsScart", avgIsScart);
			resultData.put("maxIsScart", maxIsScart);
			resultData.put("avgminpercent", avgminpercent);
			
    		result = Result.buildSuccess();
            result.setResultData(resultData);
		}catch(Exception e){
			e.printStackTrace();
			result = Result.buildServerError();
		}
    	return result;
    }
}
