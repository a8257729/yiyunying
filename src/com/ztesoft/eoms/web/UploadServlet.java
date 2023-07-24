package com.ztesoft.eoms.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

import com.ztesoft.eoms.web.dao.attachment.AttachmentDAO;
import com.ztesoft.eoms.web.dao.attachment.AttachmentDAOImpl;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.configure.ConfigMgr;

public class UploadServlet
    extends HttpServlet {

  private static final long serialVersionUID = -1403107155781623883L;

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
      IOException {
    PrintWriter writer = response.getWriter();
    writer.println("Thank you using upload!");
  }

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
      IOException {
    try {
      MultipartResolver resolver = new CommonsMultipartResolver(
          getServletContext());
      if (!resolver.isMultipart(request)) {
        throw new ServletException("Not support none multipart request");
      }
      MultipartHttpServletRequest multiperHttpServletRequest = resolver
          .resolveMultipart(request);
      String fileIdsStr = multiperHttpServletRequest
          .getParameter("fileIdsStr");
      String fileNamesStr = "";
      String uploadInstance = multiperHttpServletRequest
          .getParameter("upload_instance");
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
                                        .getFile(fileName), fileIdArr[i++]);
          if (!fileMap.isEmpty()) {
            fileList.add(fileMap);
            fileNamesStr = (String) fileMap.get("fileName");
          }
        }
      }
      resolver.cleanupMultipart(multiperHttpServletRequest);

      String docIds = getFileUploadDAO().insertFileInfo(fileList);

      response.setContentType("text/html;charset=GBK");
      PrintWriter out = response.getWriter();
      out
          .println(
          "<html><meta http-equiv='Content-Type' content='text/html; charset=gb2312'>"
          + "<script language='javascript'>var uploadInstance=window.opener."
          + uploadInstance
          + ";uploadInstance.FileUpload('','"
          + fileNamesStr
          + "','"
          + fileIdsStr
          + "','"
          + docIds
          + "');uploadInstance.callback();uploadInstance.clear();window.close();</script></html>");
      out.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  protected Map processUploadedFile(MultipartFile item, String fileId) {

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
    try {
      String eomsDocPath = AttachmentHelper.getEomsDocPath();
      if (eomsDocPath == null || eomsDocPath.trim().equals("")) {
        eomsDocPath = getServletContext().getRealPath("/")
            + "/download";
      }
      f = File.createTempFile(prefix.toString(), postfix.toString(),
                              new File(eomsDocPath));
    }
    catch (IOException ex) {
      ex.printStackTrace();
      return Collections.EMPTY_MAP;
    }
    String tempFileName = f.getName();

    try {
      item.transferTo(f);
    }
    catch (Exception e) {
      e.printStackTrace();
      return Collections.EMPTY_MAP;
    }

    fileMap.put("contentType", contentType);
    fileMap.put("size", new Long(size));
    fileMap.put("fileName", fileName);
    fileMap.put("tempFileName", tempFileName);
    fileMap.put("fileKey", fileId);

    return fileMap;
  }

  private AttachmentDAO getFileUploadDAO() {
    String daoName = AttachmentDAOImpl.class.getName();
    return (AttachmentDAO) BaseDAOFactory.getImplDAO(daoName);
  }

}
