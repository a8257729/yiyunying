package com.ztesoft.mobile.v2.service.workform.shanghai;

import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;

public interface WorkOrderService {
	
    /**
     * @param staffInfo
     * @param startTime	- ∏Ò Ω: yyyymmddhhmiss£¨»Á20130303121231
     * @param endTime
     * @param pageIndex
     * @param pageSize 
     * @return
     */
    public Result selWorkOrderByPage(StaffInfo staffInfo,String startTime, String endTime, int pageIndex, int pageSize);
    
    public Result selWorkOrderByPage(StaffInfo staffInfo,long latestTimestamp, int pageIndex, int pageSize);
    
    public Result selWorkOrder(StaffInfo staffInfo,String startTime, String endTime);
    
    public Result selWorkOrder(StaffInfo staffInfo,long latestTimestamp);
    
    public Result selWorkOrderDetail(long workOrderId);
    
    public Result acceptWorkOrder(Long staffId, WorkOrder workOrder);
    
    public Result departWorkOrder(long staffId, WorkOrder workOrder);
    
    public Result getCancelOrderReason();
}
