package com.ztesoft.mobile.outsystem.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAO;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAOImpl;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAO;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAOImpl;

public class SelApkVerManagerAction implements BaseAction{
	
	public static String QRY_VER_BY_CODE="qryApkVerByCode";
	public static String DOWNLOAD_APK="downloadApk";
	
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		//获取参数 
		String actionType = request.getParameter("actionType")==null?"0":request.getParameter("actionType");
		int limit = request.getParameter("limit")==null?50:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			//request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			if(QRY_VER_BY_CODE.equals(actionType)){
				jsonStr=this.qryApkVerByCode(request);
				response.getWriter().write(jsonStr);
			}
			if(DOWNLOAD_APK.equals(actionType)){
				try {
					this.downloadFile(request,response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String qryApkVerByCode(HttpServletRequest request){
		String apkCode = request.getParameter("apkCode")==null?"0":request.getParameter("apkCode");
		int limit = request.getParameter("limit")==null?50:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			Map param = new HashMap();
			param.put("apkCode", apkCode);
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			System.out.println("=====[debug]======param: "+param.toString());
			Map paginResultMap =getApkVerManagerDAO().selByApkCode(param);

			//列表数据
			List list = (List) paginResultMap.get("resultList");
			List resultList = new ArrayList();

			if (list != null && list.size() > 0) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Map _map = (Map) iterator.next();
					_map.put("createDate", format(MapUtils.getString(_map, "createDate"),"-"));
					_map.put("stateDate", format(MapUtils.getString(_map, "stateDate"),"-"));
					resultList.add(_map);
				}
			}
			int totalSize = Integer.parseInt(paginResultMap.get("totalSize")+"");

			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	 public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String fileName = request.getParameter("fileName");
		if (fileName == null || fileName.length() == 0) {
			System.out.println("文件不存在! fileName is null !");
			// this.responseText("", response);
			return;
		}

		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();    
		if (path.indexOf("WEB-INF") > 0) {    
			path = path.substring(0, path.indexOf("WEB-INF"))+"/version/";    
		} 
		String svrFilePath = (path + fileName).replace(
				"\\", "/");

		File file = new File(svrFilePath);
		if (!file.exists()) {
			//log.info("文件不存在! path:" + file.getAbsolutePath());
		} else {
			FileInputStream in=null;
			OutputStream out=null;
			try {
				response.setContentType("application/force-download");   
				response.setHeader("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");   
				response.setHeader("Pragma", "No-cache");    
				response.setHeader("Cache-Control", "no-cache");    
				out = response.getOutputStream();
				in = new FileInputStream(file);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					out.write(b, 0, i);
				}
				//		
				in.close();
				out.flush();
				out.close();
				// 要加以下两句话，否则会报错
				// java.lang.IllegalStateException: getOutputStream() has
				// already been called for //this response
				//out.clear();
				//out = pageContext.pushBody();
			} catch (Exception e) {
				System.out.println("Error!");
				e.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
					in = null;
				}
			}
		}
	} 

	public static String format(String DateTime, String format) {
		String result = "";
		if(DateTime !=null && DateTime.length()>0){
			result = DateTime.substring(0, 19).replaceAll("-", format);
		}
		return result;
	}
	private ApkVerManagerDAO getApkVerManagerDAO() {
		String daoName = ApkVerManagerDAOImpl.class.getName();
		return (ApkVerManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}





}
