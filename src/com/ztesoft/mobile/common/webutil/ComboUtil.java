package com.ztesoft.mobile.common.webutil;

/**
 * 
 * @author lcq
 * createDate:2012-09-20
 * Discription:
 * <p>Get value for ComboBox</p>
 * <p> The need params is: <br/>
 * 1)from the html/jsp <br/>
 *   tbnameId
 *  2)from the file combo.xml <br/>
 *    table_name,display_field,value_field</p>
 * **/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;


import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.common.webutil.dao.UtilDAO;
import com.ztesoft.mobile.common.webutil.dao.UtilDAOImpl;
import com.ztesoft.mobile.common.xwork.execution.XMLParse;


public class ComboUtil  implements BaseAction {


	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws SQLException  {
		try {
			ParamUtils paramUtils= new ParamUtils();
			 Map paramMap=paramUtils.getMapByRequest(request);
			 String tbnameId=MapUtils.getString(paramMap, "tbnameId");
			
			String filePath= request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/combo.xml";
	        
			//读取xml文件;
			 Document  document=XMLParse.fromXML(new FileInputStream(filePath), "utf-8");
			 Element  root=document.getRootElement();
			 Element  tableElement=root.elementByID(tbnameId);
			 
			 //读取各个字段;
			 String tablename=tableElement.elementTextTrim("table_name");
			 String displayFieldName=tableElement.elementTextTrim("display_field");
			 String valueFiledName=tableElement.elementTextTrim("value_field");	
					
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			
			String jsonStr="";			
			List comboList=getUtilDAO().getTextValue(tablename, displayFieldName, valueFiledName);
			jsonStr=JsonUtil.getJsonByList(comboList);
			
			response.getWriter().write(jsonStr); 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	private UtilDAO getUtilDAO(){
		String daoName=UtilDAOImpl.class.getName();
		return (UtilDAO)BaseDAOFactory.getImplDAO(daoName);
	}

}
