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

import net.sf.json.JSONArray;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class UploadPhotoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("==========================UploadPhotoServlet");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			MultipartResolver resolver = new CommonsMultipartResolver(
					getServletContext());
			if (!resolver.isMultipart(request)) {
				throw new ServletException("Not support none multipart request");
			}
			MultipartHttpServletRequest multiperHttpServletRequest = resolver.resolveMultipart(request);
			String fileIdsStr = multiperHttpServletRequest.getParameter("fileIdsStr");
			System.out.println("=======================fileIdsStr===" + fileIdsStr);
			String type = multiperHttpServletRequest.getParameter("type");
			System.out.println("=======================type===" + type);
			String fileNamesStr = "";
			String fileIdArr[] = fileIdsStr.split(",");
			List fileList = new ArrayList();
			Map fileMap = null;
			MultipartFile item = null;
			Iterator itor = multiperHttpServletRequest.getFileNames();
			int i = 0;
			while (itor.hasNext()) {
				String userFile = (String) itor.next();
				System.out.println("======================userFile=" + userFile);
				item = multiperHttpServletRequest.getFile(userFile);
				if (!item.isEmpty()) {
					System.out.println("====================processUploadedFile===");
					fileMap = processUploadedFile(multiperHttpServletRequest.getFile(userFile), fileIdArr[i++], type, request, response);
					if (!fileMap.isEmpty()) {
						fileList.add(fileMap);
						fileNamesStr = (String) fileMap.get("fileName");
					}
				} else {
					System.out.println("=================item.isEmpty()============" + item.isEmpty());
				}
			}
			resolver.cleanupMultipart(multiperHttpServletRequest);

			String uploadPath = fileMap.get("uploadPath") + "";
			if ( type == null ||  type.equals("") ) {
                throw new ServletException();
			}

			Map map = new HashMap();
			map.put("success", true);
			map.put("fileURL", uploadPath);
			map.put("fileName", fileMap.get("fileName"));
			String jsondata = JSONArray.fromObject(map).toString();
			jsondata = jsondata.substring(1, jsondata.length() - 1);
			System.out.println("jsondata " + jsondata);
			response.getWriter().write(jsondata);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected Map processUploadedFile(MultipartFile item, String fileId, String type, HttpServletRequest request, HttpServletResponse response) {
		Map fileMap = new HashMap();
		String contentType = item.getContentType();
		System.out.println("=======================contentType==" + contentType);
		long size = item.getSize();
		String fileName = item.getOriginalFilename();
		System.out.println("=======================fileName=1=" + fileName);
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer postfix = new StringBuffer(256);
		fileName = postfix.append(fileId).append("_").append(s.format(new Date())).append(type).toString();

		System.out.println("=======================fileName=2=" + fileName);
		
		//System.out.println("=======================request.getContextPath()1111=" + getServletContext().getContextPath());
		System.out.println("=======================request.getContextPath()=" + request.getContextPath());
		File file = null;
		String uploadPath = null;
		if (type!=null && !type.equals("")) {
			//uploadPath = getServletContext().getContextPath() + "/" + "imagesmob/" + fileName;
			uploadPath = request.getContextPath() + "/" + "imagesmob/" + fileName;
		} else {
			return null;
		}

		try {
			System.out.println("uploadPath------------------------------>>  "
					+ uploadPath);
			String storePath = request.getRealPath("/") + "imagesmob"  + File.separator + fileName;
            // Print log
            System.out.println("�ϴ�ͼƬ��ʵ�ʴ洢λ�ã�" + storePath);
			file = new File(storePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
		} catch (IOException ex) {
			ex.printStackTrace();
			return Collections.EMPTY_MAP;
		}
		String tempFileName = file.getName();

		try {
			item.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.EMPTY_MAP;
		}

		fileMap.put("contentType", contentType);
		fileMap.put("size", new Long(size));
		fileMap.put("fileName", fileName);
		fileMap.put("fileKey", fileId);
		fileMap.put("uploadPath", uploadPath);

		return fileMap;
	}
}
