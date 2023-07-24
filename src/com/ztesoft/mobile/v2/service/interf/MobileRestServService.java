package com.ztesoft.mobile.v2.service.interf;

import java.util.List;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.entity.interf.MobileRestServLog;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;

public interface MobileRestServService {
	
	/** д��ƽ̨������־ */
	public void writeRestServLog(MobileRestServLog restServLog) throws Exception;

	/** ��¼�쳣��־��Ϣ */
	public void writeExceptionLog(Long restServiceId, String excepName,
			String excepMsg) throws Exception;
	
	public List<MobileRestService> getNormalRestServList() throws Exception;

	public List<MobileRestService> getForbiddenlRestServList() throws Exception;
	
	public List<MobileRestService> getRestServList() throws Exception;
	
	public List<MobileRestService> getRestServList(Integer restServStatus) throws Exception;
	
	public StaffControls getStaffControls(Long staffId) throws Exception;
	
	public List getRestServStatList() throws DataAccessException;
	
	public List getRestServFlowStatList() throws DataAccessException;
	
	public List getStaffFlowStatList() throws DataAccessException;
	
}
