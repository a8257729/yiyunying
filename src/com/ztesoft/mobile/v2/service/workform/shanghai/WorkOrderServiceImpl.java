package com.ztesoft.mobile.v2.service.workform.shanghai;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.workform.shanghai.WorkOrderDAO;
import com.ztesoft.mobile.v2.dao.workform.shanghai.WorkOrderDAOImpl;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrderDetail;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("workOrderService")
public class WorkOrderServiceImpl extends BaseService implements
		WorkOrderService {
	
	private TransactionTemplate transactionTemplate;
	
    public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

    @Autowired(required = false)
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public Result selWorkOrderByPage(StaffInfo staffInfo,String startTime,
			String endTime, int pageIndex, int pageSize) {
		Result result = null;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(startTime)) {
            paramMap.put("startTime", startTime);
        }
        if(StringUtils.isNotBlank(endTime)) {
            paramMap.put("endTime", endTime);
        }
        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);
        
        System.out.println("paramMap==>" + paramMap);
        try {
//            Map resultMap = getMobileNotificationDAO().selByPage(paramMap);
            
        	Map resultMap = new HashMap();
//        	List dataList = getDataList();
        	List dataList = getWorkOrderList();
            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(WorkOrder.WORK_ORDER_LIST_NODE, dataList);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.buildServerError();
        }
        return result;
	}
	
    public Result selWorkOrderByPage(StaffInfo staffInfo,long latestTimestamp, int pageIndex, int pageSize) {
    	Date date = new Date(latestTimestamp);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return selWorkOrderByPage(staffInfo,df.format(date), null, pageIndex, pageSize);
    }

	public Result selWorkOrder(StaffInfo staffInfo,String startTime, String endTime) {
		Result result = null;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(startTime)) {
            paramMap.put("startTime", startTime);
        }
        if(StringUtils.isNotBlank(endTime)) {
            paramMap.put("endTime", endTime);
        }
        
        try {
//            List resultList = getMobileNotificationDAO().selAll(paramMap);
        	
        	List resultList = getDataList();
        	
            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(WorkOrder.WORK_ORDER_LIST_NODE, resultList);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.buildServerError();
        }
        
        return result;
	}

	/** 锟斤拷询锟斤拷锟斤拷 */
	public Result selWorkOrder(StaffInfo staffInfo,long latestTimestamp) {
		Date date = new Date(latestTimestamp);
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	return selWorkOrder(staffInfo,df.format(date), null);
	}
	
	public Result selWorkOrderDetail(long workOrderId) {
		System.out.println("selWorkOrderDetial workOrderId==>" + workOrderId);
		Result result = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        try {
        	List<WorkOrderDetail> list = getWorkOrderDetail(workOrderId);
        	
            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(WorkOrderDetail.WORK_ORDER_DETAIL_LIST_NODE, list);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.buildServerError();
        }
        
        return result;
	}
	
	//@Transactional(rollbackFor = {Throwable.class})
    public Result acceptWorkOrder(Long staffId, WorkOrder workOrder) {
        Result result = null;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        System.out.println("Accept workOrder's staffId is:" + staffId);
    	System.out.println("Accept workOrder's workOrderId is:" + workOrder.getWorkOrderId());
        result = Result.buildSuccess();
        
        return result;
    }
    
    public Result departWorkOrder(long staffId, WorkOrder workOrder) {
    	Result result = null;

        System.out.println("Depart workOrder's staffId is:" + staffId);
    	System.out.println("Depart workOrder's workOrderId is:" + workOrder.getWorkOrderId());
    	System.out.println("Depart workOrder's Time is:" + workOrder.getLimitDate());
        result = Result.buildSuccess();
        
        return result;
    }

	private List getDataList() {
		List resultList = new ArrayList();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	for (int i = 0; i < 5; i++) {
    		Map child = new HashMap<String,Object>();
    		child.put(WorkOrder.WORK_ORDER_ID_NODE, new Date().getTime() + i);
    		child.put(WorkOrder.WORK_ORDER_CODE_NODE, "No.20130303");
    		child.put(WorkOrder.SERVICE_NAME_NODE, "服务10M");
    		
    		child.put(WorkOrder.LIMIT_DATE_NODE, df.format(new Date()));
    		resultList.add(child);
    	}
    	return resultList;
	}
	
	private List<WorkOrder> getWorkOrderList() {
		List<WorkOrder> resultList = new ArrayList<WorkOrder>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < 5; i++) {
			WorkOrder workOrder = new WorkOrder();
			workOrder.setWorkOrderId(new Date().getTime() + i);
			workOrder.setWorkOrderCode("No.20130303");
			workOrder.setServiceName("服务10M");
			workOrder.setLimitDate(df.format(new Date()));
			resultList.add(workOrder);
		}
		return resultList;
	}
	
	private List<WorkOrderDetail> getWorkOrderDetail(long workOrderId) {
    	
    	List<WorkOrderDetail> list = new ArrayList<WorkOrderDetail>();
		 for (int i = 0; i < 40; i++) {
			 WorkOrderDetail child = new WorkOrderDetail();
			 child.setName("工单详情编码:");
			 child.setValue("ServDev.3322212");
			 list.add(child);
		 }
		 
		 return list;
	}
	/**
	 * 退单原因
	 */
	  public Result getCancelOrderReason()
	   {
		   Result result = null;


	        result = Result.buildSuccess();
	        
	        return result;
	   }
	  
		private WorkOrderDAO getWorkOrderDAO() {
	    	String daoName = WorkOrderDAOImpl.class.getName();
	        return (WorkOrderDAO) BaseDAOFactory.getImplDAO(daoName);
	    }
}
