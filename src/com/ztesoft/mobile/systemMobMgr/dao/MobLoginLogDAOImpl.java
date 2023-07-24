package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.StringHelper;
public class MobLoginLogDAOImpl extends BaseDAOImpl implements MobLoginLogDAO {
private static final String TABLE_NAME = "MOBILE_LOGIN_INFO";
	/**
	 * 查询人员登录信息
	 * @param paramMap
	 * @return Map
	 */
	public Map selMobileLog(Map paramMap,int start,int limit) throws DataAccessException {
		StringBuffer sbf = new StringBuffer();
		sbf.append(" select a.ID as id,a.phone_model as phoneModel,u.staff_name as staffName, ");
		sbf.append(" a.staff_id as staffId,a.system_code as systemCode,");
		sbf.append(" to_char(a.start_date,'yyyy-mm-dd hh:mi:ss') as startDate,to_char(a.end_date,'yyyy-mm-dd hh:mi:ss') as endDate ");
		sbf.append(" from MOBILE_LOGIN_INFO a join uos_staff u on a.staff_id = u.staff_id ");
		sbf.append(" where u.state = 1 ");
		
		if (paramMap.containsKey("staffId")
				&& !StringHelper.isNull(MapUtils.getObject(paramMap,"staffId"))) {
			sbf.append(" and u.staff_id =  ").append(
					MapUtils.getObject(paramMap,"staffId"));
		}

		
		sbf.append(" ORDER BY start_date DESC");
		System.out.println("根据查询条件查询人员登录信息:"+sbf.toString());
		return (Map) populateQueryByMap(sbf, start, limit);
	}
	

	
	
	

}
