package com.ztesoft.mobile.v2.dao.menu;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MobileMenuDAOImpl extends BaseDAOImpl implements MobileMenuDAO {

	private static final Logger logger = Logger
			.getLogger(MobileMenuDAOImpl.class);

	private static final String TABLE_NAME = "MOBILE_MENU";

	public Map insert(Map paramMap) throws DataAccessException {
		String patternStr = "MENU_ID:menuId,PARENT_ID:parentId,MENU_NAME:menuName,MENU_ICON_URI:menuIconUri,MENU_TYPE:menuType,MENU_INDEX:menuIndex,PATH_CODE:pathCode,PATH_NAME:pathName,OS_TYPE:osType,PRIV_CODE:privCode,STATE:state,MENU_INTRO:menuIntro,STATE_DATE:stateDate,IS_LEAF:isLeaf";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("menuId", nextId);
		paramMap.put("privCode", "MENU_" + nextId);// 权限代码自动生成
		paramMap.put("pathCode", paramMap.get("pathCode") + "/" + nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return paramMap;

	}

	/*
	 * public void insertBatch(List paramMapList) throws DataAccessException {
	 * String patternStr =
	 * "MENU_ID:menuId,PARENT_ID:parentId,MENU_NAME:menuName,MENU_ICON_URI:menuIconUri,MENU_TYPE:menuType,MENU_INDEX:menuIndex,PATH_CODE:pathCode,PATH_NAME:pathName,OS_TYPE:osType,PRIV_CODE:privCode,STATE:state,STATE_DATE:stateDate"
	 * ; long nextId = getPKFromMem(TABLE_NAME, 9,
	 * paramMapList.size()).longValue(); for (int i = 0; i <
	 * paramMapList.size(); i++) { ((Map) paramMapList.get(i)).put("menuId", new
	 * Long(nextId)); nextId--; } dynamicInsertBatchByMap(paramMapList,
	 * TABLE_NAME, patternStr); }
	 */

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "MENU_ID:menuId,PARENT_ID:parentId,MENU_NAME:menuName,MENU_ICON_URI:menuIconUri,MENU_TYPE:menuType,MENU_INDEX:menuIndex,PATH_CODE:pathCode,PATH_NAME:pathName,OS_TYPE:osType,MENU_INTRO:menuIntro,PRIV_CODE:privCode,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "MENU_ID:menuId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	/*
	 * public void updateBatch(List paramMapList) throws DataAccessException {
	 * String updatePatternStr =
	 * "MENU_ID:menuId,PARENT_ID:parentId,MENU_NAME:menuName,MENU_ICON_URI:menuIconUri,MENU_TYPE:menuType,MENU_INDEX:menuIndex,PATH_CODE:pathCode,PATH_NAME:pathName,OS_TYPE:osType,PRIV_CODE:privCode,STATE:state,STATE_DATE:stateDate"
	 * ; String wherePatternStr = "MENU_ID:menuId";
	 * dynamicUpdateBatchByMap(paramMapList, TABLE_NAME, updatePatternStr,
	 * wherePatternStr); }
	 */

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "MENU_ID:menuId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(paramMap);
		sqlStr.append(" MENU_ID = ? ");
		String wherePatternStr = "MENU_ID:menuId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap,
				wherePatternStr);
	}

	public StringBuffer buildSql(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(" SELECT MENU_ID AS menuId, ");
		sqlStr.append(" PARENT_ID AS parentId, ");
		sqlStr.append(" MENU_NAME AS menuName, ");
		sqlStr.append(" MENU_ICON_URI AS menuIconUri, ");
		sqlStr.append(" MENU_TYPE AS menuType, ");
		sqlStr.append(" MENU_INDEX AS menuIndex, ");
		sqlStr.append(" PATH_CODE AS pathCode, ");
		sqlStr.append(" PATH_NAME AS pathName, ");
		sqlStr.append(" OS_TYPE AS osType, ");
		sqlStr.append(" PRIV_CODE AS privCode, ");
		sqlStr.append(" STATE AS state, ");
		sqlStr.append(" STATE_DATE AS stateDate ");
		sqlStr.append(" FROM MOBILE_MENU WHERE STATE = 1 ");

		Integer osType = MapUtils.getInteger(paramMap, "osType");
		if (null != osType) {
			sqlStr.append("AND OS_TYPE = " + osType);
		}

		return sqlStr;
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public List queryMenuCatalog(Long staffId, Long jobId, Long defaultJobId,
			Integer osType) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(
				" select distinct m.menu_id       as menuCatalogId, ");
		sqlStr.append(" m.menu_name     AS catalogName, ");
		sqlStr.append(" m.menu_icon_uri as catalogIconUri, ");
		sqlStr.append(" m.menu_index    as catalogIndex ");
		sqlStr
				.append(" from mobile_staff_job_right b, uos_job_staff a, mobile_menu m ");
		sqlStr.append(" where a.job_id = b.job_id ");
		sqlStr.append(" and m.priv_code = b.priv_code ");
		sqlStr.append(" and m.parent_id = 0 ");
		sqlStr.append(" and m.state = 1 ");
		sqlStr.append(" and a.state = '1' ");
		sqlStr.append(" and a.staff_id = " + staffId);
		//sqlStr.append(" and m.os_type = " + osType);
		sqlStr.append(" and (a.job_id = " + jobId + " or a.job_id = "
				+ defaultJobId + ") ");
		sqlStr.append(" order by m.menu_index ");

		if (logger.isDebugEnabled()) {
			logger.debug("queryMenuCatalog打印SQL是：" + sqlStr.toString());
		}
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}
	
	public List querySubMenu(Long staffId, Long jobId, Long defaultJobId,
			Integer osType) throws DataAccessException {
		return querySubMenu(null, staffId, jobId, defaultJobId, osType);
	}

	public List querySubMenu(Long menuCataLogId, Long staffId, Long jobId, Long defaultJobId,
			Integer osType) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(
				" select distinct m.menu_id AS menuId, ");
		sqlStr.append(" m.menu_name     AS menuName, ");
		sqlStr.append(" m.menu_type     AS menuType, ");
		sqlStr.append(" m.menu_icon_uri AS menuIconUri, ");
		sqlStr.append(" m.menu_index    AS menuIndex, ");
		sqlStr.append(" m.parent_id     AS menuCatalogId ");
		sqlStr
				.append(" from mobile_staff_job_right b, uos_job_staff a, mobile_menu m ");
		sqlStr.append(" where a.job_id = b.job_id ");
		sqlStr.append(" and m.priv_code = b.priv_code ");
		
		sqlStr.append(" and m.state = 1 ");
		sqlStr.append(" and a.state = '1' ");
		sqlStr.append(" and a.staff_id = " + staffId);
		//sqlStr.append(" and m.os_type = " + osType);
		sqlStr.append(" and (a.job_id = " + jobId + " or a.job_id = "
				+ defaultJobId + ") ");
		
		if(null!= menuCataLogId) {
			sqlStr.append(" and m.parent_id =" + menuCataLogId);
		} else {
			sqlStr.append(" and m.parent_id != 0 ");
		}
		
		sqlStr.append(" order by m.menu_index ");

		if (logger.isDebugEnabled()) {
			logger.debug("querySubMenu：" + sqlStr.toString());
		}
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Map selClassByOsType(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf
				.append(" SELECT MENU_ID AS menuId,  PARENT_ID AS parentId,  MENU_NAME AS menuName,  MENU_ICON_URI AS menuIconUri,  MENU_TYPE AS menuType,  MENU_INDEX AS menuIndex,  PATH_CODE AS pathCode,  PATH_NAME AS pathName,  OS_TYPE AS osType,  PRIV_CODE AS privCode,  STATE AS state,  STATE_DATE AS stateDate ");
		sqlBuf.append(" FROM MOBILE_MENU    ");
		sqlBuf.append(" WHERE PARENT_ID = 0  ");

		if (MapUtils.getString(paramMap, "osType") != null
				&& !MapUtils.getString(paramMap, "osType").equals("")) {
			sqlBuf.append(" AND OS_TYPE ='"
					+ MapUtils.getString(paramMap, "osType") + "'");
		}

		sqlBuf.append(" order by MENU_INDEX desc ");

		System.out.println("selClassByOsType=" + sqlBuf.toString());

		return populateQueryByMap(sqlBuf, ((Integer) paramMap.get("pageIndex"))
				.intValue(), ((Integer) paramMap.get("pageSize")).intValue());

	}

	public Map selByDisplayInedx(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf
				.append("SELECT COUNT(*) AS icount  FROM MOBILE_MENU WHERE PARENT_ID = 0  ");

		if (MapUtils.getString(paramMap, "menuIndex") != null
				&& !MapUtils.getString(paramMap, "menuIndex").equals("")) {
			sqlBuf.append(" AND MENU_INDEX ="
					+ MapUtils.getLong(paramMap, "menuIndex"));
		}
		if (MapUtils.getString(paramMap, "osType") != null
				&& !MapUtils.getString(paramMap, "osType").equals("")) {
			sqlBuf.append(" AND OS_TYPE ='"
					+ MapUtils.getString(paramMap, "osType") + "'");
		}
		return dynamicQueryObjectByMap(sqlBuf.toString(), paramMap, null);

	}

	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT MENU_ID AS menuId,  PARENT_ID AS parentId,  MENU_NAME AS menuName,  MENU_ICON_URI AS menuIconUri,  MENU_TYPE AS menuType,  MENU_INDEX AS menuIndex,  PATH_CODE AS pathCode,  PATH_NAME AS pathName,  OS_TYPE AS osType,  PRIV_CODE AS privCode,  STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_MENU "
				+ "WHERE PARENT_ID="
				+ MapUtils.getString(paramMap, "parentId")
				+ " and MENU_NAME='"
				+ MapUtils.getString(paramMap, "menuName")
				+ "'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}

	// 查询某个节点下最大的排序编号

	public Map selByMenuIndex(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf
				.append("SELECT count(menu_index) AS cindex  FROM MOBILE_MENU WHERE PARENT_ID =   "
						+ MapUtils.getIntValue(paramMap, "parentId")
						+ " and MENU_INDEX= "
						+ MapUtils.getIntValue(paramMap, "menuIndex"));
		return dynamicQueryObjectByMap(sqlBuf.toString(), paramMap, null);
	}

	public List getMenuAppClass(Map param) throws DataAccessException {
		// TODO Auto-generated method stub
		String sql = "select ma.app_class as appClass, maf.app_func_id as appFuncId,maf.func_class as funcClass , ma.app_id       as appId"
				+ " from mobile_menu mm"
				+ " left join mobile_menu_config mmc on mm.menu_id = mmc.menu_id"
				+ " left join mobile_func_menu_rela mfmr on mmc.menu_config_id ="
				+ " mfmr.menu_config_id "
				+ " left join mobile_app_func maf on mfmr.app_func_id = maf.app_func_id"
				+ " left join mobile_app ma on maf.app_id = ma.app_id"
				+ " where 1=1";
		if (param.get("menuId") != null && !param.get("menuId").equals("")) {
			sql = sql + " and  mm.menu_id = " + param.get("menuId");
		}
		System.out.println("sql=" + sql);
		return dynamicQueryListByMap(sql, null, null);
	}

}