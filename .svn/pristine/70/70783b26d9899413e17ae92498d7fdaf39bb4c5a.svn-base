package com.ztesoft.eoms.web;

import java.io.File;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.helper.StringHelper;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

import com.ztesoft.eoms.web.dao.attachment.AttachmentDAO;
import com.ztesoft.eoms.web.dao.attachment.AttachmentDAOImpl;

public class CheckFileExistAction extends AbstractAction {

	 private static String eomsDocPath = AttachmentHelper.getEomsDocPath();
	 
	public String execute() throws Exception {
		Map param = DedicatedActionContext.getParams();
		Map paramMap = MapUtils.getMap(param, "parameter_1");
		
		String isExist = "N";		//Ä¬ÈÏ²»´æÔÚ
		try{
			String fileName = MapUtils.getString(paramMap,"fileName");
			String filePoint = null;
			if(!StringHelper.isNull(fileName)){
	    		filePoint = eomsDocPath + "/help/" + fileName;
			}else{
				 String fileKey = MapUtils.getString(paramMap,"key");
			     Map fileInfo = getFileUploadDAO().selByDocKey(fileKey);
			     
			     if (fileInfo != null) {
			    	 filePoint = eomsDocPath + "/" + MapUtils.getString(fileInfo, "point"); 
			     }
			}
			
			if(filePoint != null ){
				File file = new File(filePoint);
				if(file.exists()){
					isExist = "Y";
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}

		DedicatedActionContext.setResult(isExist);		
		
		return SUCCESS;
	}

    private AttachmentDAO getFileUploadDAO() {
        String daoName = AttachmentDAOImpl.class.getName();
        return (AttachmentDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
