package com.ztesoft.front;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.zterc.uos.UOSException;
import com.zterc.uos.exception.SystemException;
import com.zterc.uos.helpers.AbstractDAOImpl;
import com.zterc.uos.helpers.DAOSysException;
import com.zterc.uos.helpers.DAOUtils;
import com.zterc.uos.util.LongUtils;
import com.zterc.uos.util.StringUtils;
import com.ztesoft.iom.common.IOMDAOUtils;
import com.ztesoft.iom.funcmanager.dao.module.ModuleDAO;
import com.ztesoft.iom.funcmanager.dao.module.ModuleDAOFactory;
import com.ztesoft.iom.funcmanager.dao.mymenu.MyMenuDAO;
import com.ztesoft.iom.funcmanager.dao.mymenu.MyMenuDAOFactory;
import com.ztesoft.iom.funcmanager.dto.MenuDto;
import com.ztesoft.iom.funcmanager.dto.ModuleDto;

public class FrontManager extends AbstractDAOImpl {
	public MenuDto[] qryMyMenu(Long staffId) throws UOSException {
		MyMenuDAO myMenuDAO = MyMenuDAOFactory.getDAO();
		List myMenuList = new ArrayList();
		try {
			myMenuList = myMenuDAO.selectMyMenu(staffId);
			MenuDto[] myMenuDtos = (MenuDto[]) myMenuList
					.toArray(new MenuDto[myMenuList.size()]);
			return myMenuDtos;
		} catch (SQLException ex) {
			throw new SystemException(ex);
		}
	}

	public String qryMyPrivAllMenu(int staffId, Long jobId, Long specialJobId,
			long moduleId) throws UOSException {
		ModuleDAO moduledao = ModuleDAOFactory.getDAO();
		HashMap retMap = moduledao.getMenus(staffId, jobId, specialJobId,
				moduleId);

		if(staffId==0){
			Iterator ite = retMap.keySet().iterator();
			StringBuffer sqlStr = new StringBuffer("SELECT A.ID MODULE_ID,A.NAME MODULE_NAME," +
					"A.ICON_FILE_NAME MODULE_ICON_FILE,A.COMMENTS MODULE_COMMENTS,A.PARENT_ID PARENT_ID " +
					"FROM PB_MODULE A WHERE A.STATE='10A' AND A.PARENT_ID IS NOT NULL ");
			StringBuffer tempStr = new StringBuffer();
			tempStr.append("(");
			while(ite.hasNext()){
				tempStr.append((String)ite.next()+",");
			}
			if(tempStr.length()>2){
				sqlStr.append("AND A.ID IN "+tempStr.substring(0, tempStr.length()-1)+")");
			}	
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sqlStr.toString());
				rs = ps.executeQuery();
				while (rs.next()) {
					if(LongUtils.valueOf(rs.getString("PARENT_ID")).longValue()==-1){
						ModuleDto moduleDto = new ModuleDto();
						moduleDto.setId(LongUtils.valueOf(rs
								.getObject("MODULE_ID")));
						moduleDto.setName(rs.getString("MODULE_NAME"));
						moduleDto.setIconFileName(rs
								.getString("MODULE_ICON_FILE"));
						moduleDto.setComments(rs
								.getString("MODULE_COMMENTS"));
						if(retMap.get(rs.getString("PARENT_ID"))==null){
							ArrayList tmpList = new ArrayList();
							retMap.put(rs.getString("PARENT_ID"), tmpList);
							tmpList.add(moduleDto);
						}else{
							ArrayList tmpList = (ArrayList) retMap.get(rs
									.getString("PARENT_ID"));
							tmpList.add(moduleDto);
						}
					}
				}
			} catch (SQLException ex) {
				throw new DAOSysException(ex.getMessage());
			} finally {
				cleanUp(conn, null, ps, rs);
			}
		}
		return new Gson().toJson(retMap);
	}
}
