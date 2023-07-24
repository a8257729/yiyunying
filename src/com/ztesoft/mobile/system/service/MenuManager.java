package com.ztesoft.mobile.system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zterc.uos.exception.SystemException;
import com.ztesoft.iom.funcmanager.dao.mymenu.MyMenuDAO;
import com.ztesoft.iom.funcmanager.dao.mymenu.MyMenuDAOFactory;
import com.ztesoft.iom.funcmanager.dto.MenuDto;

public class MenuManager {
	public MenuDto[] qryMyMenu(Long staffId) throws SystemException {
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
}
