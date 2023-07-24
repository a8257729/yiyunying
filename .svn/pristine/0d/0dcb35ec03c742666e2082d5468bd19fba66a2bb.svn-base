package com.ztesoft.eoms.common.upload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.util.JsonUtil;
import com.ztesoft.eoms.util.StringUtil;
import com.ztesoft.eoms.web.dao.attachment.AttachmentDAO;
import com.ztesoft.eoms.web.dao.attachment.AttachmentDAOImpl;

public class AddFileInNewsServlet
    extends HttpServlet {

  private static final long serialVersionUID = -1403107155781623883L;

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
      IOException {
	  doPost(request,response);
  }

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
                        IOException {
	  request.setCharacterEncoding("utf-8");
	  response.setContentType("text/html;charset=utf-8");
	  try {
		  MultipartResolver resolver = new CommonsMultipartResolver(
				  getServletContext());
		  if (!resolver.isMultipart(request)) {
			  throw new ServletException("Not support none multipart request");
		  }
		  MultipartHttpServletRequest multiperHttpServletRequest = resolver
		  .resolveMultipart(request);
		  String fileIdsStr = multiperHttpServletRequest.getParameter("fileIdsStr");
		  String fileNamesStr = "";
		  String uploadInstance = multiperHttpServletRequest.getParameter("upload_instance");
		  System.out.println("uploadInstance  "+uploadInstance);
		  String fileIdArr[] = fileIdsStr.split(",");
		  List fileList = new ArrayList();
		  Map fileMap = null;
		  MultipartFile item = null;
		  Iterator itor = multiperHttpServletRequest.getFileNames();
		  int i = 0;
		  while (itor.hasNext()) {
			  String fileName = (String) itor.next();

			  item = multiperHttpServletRequest.getFile(fileName);
			  if (!item.isEmpty()) {
				  fileMap = processUploadedFile(multiperHttpServletRequest
						  .getFile(fileName), fileIdArr[i++],uploadInstance);
				  if (!fileMap.isEmpty()) {
					  fileList.add(fileMap);
					  fileNamesStr = (String) fileMap.get("fileName");
				  }
			  }
		  }
		  resolver.cleanupMultipart(multiperHttpServletRequest);

		  String docIds = getFileUploadDAO().insertFileInfo(fileList);
	      
		  //String eomsDocPath = StringUtil.createPath(getServletContext().getRealPath("/") + "/download/"+uploadInstance);
		  String eomsDocPath = fileMap.get("eomsDocPath")+"";
		  eomsDocPath = eomsDocPath.substring(eomsDocPath.indexOf("download"), eomsDocPath.length())+fileMap.get("tempFileName");
		  Map map = new HashMap();
		  map.put("success", true);
		  map.put("fileURL", eomsDocPath);
		  String jsondata = JsonUtil.getJsonData(map);
		  jsondata = jsondata.substring(1, jsondata.length()-1);
		  System.out.println("jsondata "+jsondata );
		  response.getWriter().write(jsondata); 
	  }
	  catch (Exception ex) {
		  ex.printStackTrace();
	  }
  }

  protected Map processUploadedFile(MultipartFile item, String fileId,String uploadInstance) {

    Map fileMap = new HashMap();

    String contentType = item.getContentType();
    long size = item.getSize();
    String fileName = item.getOriginalFilename();
    SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
    StringBuffer postfix = new StringBuffer(256);
    postfix.append('-').append(fileId);
    StringBuffer prefix = new StringBuffer(32);
    prefix.append(s.format(new Date())).append('-');

    File f = null;
    String eomsDocPath = StringUtil.createPath(getServletContext().getRealPath("/") + "/download/"+uploadInstance);
    try { 
    	
      //String eomsDocPath = AttachmentHelper.getEomsDocPath();
      //if (eomsDocPath == null || eomsDocPath.trim().equals("")) {
      // String eomsDocPath = getServletContext().getRealPath("/")
        //    + "/download";
      //}   	
      System.out.println("eomsDocPath------------------------------>>  "+eomsDocPath);
   /*   File fileDir = new File(eomsDocPath);
      if(!fileDir.getParentFile().exists()){
    	  fileDir.getParentFile().mkdirs();
      }*/ 
      
      System.out.println("prefix=====> " + prefix);
      
      System.out.println("postfix=====> " + postfix);
      
      f = File.createTempFile(prefix.toString(), postfix.toString(),
                              new File(eomsDocPath));
    }
    catch (IOException ex) {
      System.out.println("创建文件出错啦！" + ex.getMessage());
      ex.printStackTrace();
      return Collections.EMPTY_MAP;
    }
    String tempFileName = f.getName();

    try {
      item.transferTo(f);
    }
    catch (Exception e) {
      System.out.println("上传出错啦！" + e.getMessage());
      e.printStackTrace();
      return Collections.EMPTY_MAP;
    }

    fileMap.put("contentType", contentType);
    fileMap.put("size", new Long(size));
    fileMap.put("fileName", fileName);
    fileMap.put("tempFileName", tempFileName);
    fileMap.put("fileKey", fileId);
    fileMap.put("eomsDocPath", eomsDocPath);

    return fileMap;
  }

  private AttachmentDAO getFileUploadDAO() {
    String daoName = AttachmentDAOImpl.class.getName();
    return (AttachmentDAO) BaseDAOFactory.getImplDAO(daoName);
  }

}
