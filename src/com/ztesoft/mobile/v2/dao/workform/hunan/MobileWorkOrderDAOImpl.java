package com.ztesoft.mobile.v2.dao.workform.hunan;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileWorkOrderDAOImpl extends BaseDAOImpl implements MobileWorkOrderDAO {
	//保存上门签到信息
	public void insertArriveSignInfo(Map paramMap)throws DataAccessException{
		String TABLE_NAME = "ARRIVE_SIGN";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("id", nextId);
		String patternStr = "SIGN_ID:id,WORK_ORDER_ID:workid,STAFF_ID:staffid,STAFF_NAME:staffname,ORG_ID:orgid,ORG_NAME:orgname,LONGITUDE:longitude,LATITUDE:latitude,SIGN_DATE:operateTime,CHECK_CODE:checkCode";
		System.out.println(" Call insertArriveSignInfo --patternStr:"+patternStr+"--paramMap:"+paramMap);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
}
