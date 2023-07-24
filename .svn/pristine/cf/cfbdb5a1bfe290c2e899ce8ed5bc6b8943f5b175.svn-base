package com.ztesoft.mobile.v2.controller.common;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.J;
import com.ztesoft.mobile.v2.dao.resource.ResourceDAO;
import com.ztesoft.mobile.v2.dao.resource.ResourceDAOImpl;
import com.ztesoft.mobile.v2.util.*;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.MobilePhotoRecord;
import com.ztesoft.mobile.v2.entity.common.MobileUploadLog;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;

/**
 * 上传控制器
 * User: heisonyee
 * Date: 13-3-18
 * Time: 下午1:13
 */
@Controller("uploadController")
public class UploadController  extends WebConfigController {

    private static final Logger logger = Logger.getLogger(UploadController.class);
    private static final String EXTENSION = ".jpg";

    private MobileCommonService mobileCommonService;
    private MobileRestServService mobileRestService;

    public MobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

    @Autowired(required = false)
	public void setMobileCommonService(MobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	public MobileRestServService getMobileRestService() {
		return mobileRestService;
	}

	@Autowired(required = false)
	public void setMobileRestService(MobileRestServService mobileRestService) {
		this.mobileRestService = mobileRestService;
	}

    /** 用于客户端的文件上传 */
	@RequestMapping(value = {"/client/upload/file"})
    public @ResponseBody Result uploadFileForClient(@RequestParam(value = "uploadFile", required = true) MultipartFile uploadFile,
    		@RequestParam(value = "staffId", required = false) Long staffId,
    		HttpServletRequest request, HttpServletRequest response) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("Call upload file service. ");
        }
        Result result = null;
        //判断是否用户连接限制
		StaffControls ctrl = this.getMobileRestService().getStaffControls(staffId);

		if(null != ctrl) {
			if(Constants.StaffLimitType.CONNECT_LIMIT.equals(ctrl.getConnectLimit())) {
				if(logger.isDebugEnabled()) {
					logger.debug("STAFF_ID为: " + staffId + "的用户连接限制");
				}
				result = Result.buildLimitAccessError();

			} else if(Constants.StaffLimitType.CONNECT_NO_LIMIT.equals(ctrl.getConnectLimit())) {
				if(logger.isDebugEnabled()) {
					logger.debug("STAFF_ID为: " + staffId + "的用户连接正常");
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////
		        if(!uploadFile.isEmpty()) {
		        	//获取原始文件名
		        	String fileName = uploadFile.getOriginalFilename();
		        	//不带后缀的文件名
		        	String nFileName = BaseUtils.getFileNameWithoutSuffix(fileName);
		        	String fileType = BaseUtils.getFileSuffix(fileName);

		            if(logger.isDebugEnabled()) {
		                logger.debug("Original file name: " + fileName);
		            }

		        	String uploadSavePath = this.getConfigValue(Constants.ConfigCode.UPLOAD_FILE_SAVE_PATH);

		        	if(StringUtils.isBlank(uploadSavePath)) {
		        		result =  Result.buildFileUploadConfigError();
		        	}

		        	//实际存放路径
		        	String filePath = uploadSavePath + nFileName + "_" + System.currentTimeMillis() + "." + fileType;

		        	File saveFile = new File(uploadSavePath);
		        	if(!saveFile.exists()) {
		        		saveFile.mkdirs();
		        	}

		            File file = new File(filePath);
		            try {
		                FileOutputStream fout = new FileOutputStream(file);
		                fout.write(uploadFile.getBytes());
		                fout.flush();
		                fout.close();

		                //TODO bug: 要加入权限控制
//		                this.writeUploadLog(null, null, null, fileName, filePath, fileType);

		                //
		                result = Result.buildSuccess();

		            } catch (IOException e) {
		                e.printStackTrace();
		                result = Result.buildFileUploadError();
		            }
		        } else {
		        	result = Result.buildFileUploadError();
		        }
	            ///////////////////////////////////////////////////////////////////////////////////////////////////
			}
		} else {
			result = Result.buildLimitAccessNotConfigError();
		}


        return result;
    }
	/** 生成批次号，采用yyyyMMddHHmmss+staffId的方式组成 */
	private static String genBatchNo(Long staffId) {
		String current = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		return current + staffId;
	}
    /** 用于客户端的图片上传 */
	@RequestMapping(value = {"/client/upload/photo"})
	@FilterTag
    public @ResponseBody Result uploadPhoto(@RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
    		//@RequestParam(value="photoName", required=false) String photoName,
    		//@RequestParam(value="batchNo", required=false) String batchNo,
    		@RequestParam(value="staffId", required=false) Long staffId,
    		@RequestParam(value="workOrderId", required=false) Long workOrderId,
    		HttpServletRequest request, HttpServletRequest response) throws Exception {


        Result result = null;
		String batchNo = genBatchNo(staffId);
		//Task:132253
		String current = new SimpleDateFormat("yyyyMMddHHmmss")
			.format(new Date());
		String photoName = current + EXTENSION;

        if(logger.isDebugEnabled()) {
            logger.debug("Call client photo upload service. ");
            logger.debug("batchNo=="+batchNo);
            logger.debug("photoName=="+photoName);
            logger.debug("staffId=="+staffId);
            logger.debug("workOrderId=="+workOrderId);
        }
        //判断是否用户连接限制
		StaffControls ctrl = this.getMobileRestService().getStaffControls(staffId);

		if(null != ctrl) {
			if(Constants.StaffLimitType.CONNECT_LIMIT.equals(ctrl.getConnectLimit())) {
				if(logger.isDebugEnabled()) {
					logger.debug("STAFF_ID为: " + staffId + "的用户连接限制");
				}
				result = Result.buildLimitAccessError();

			} else if(Constants.StaffLimitType.CONNECT_NO_LIMIT.equals(ctrl.getConnectLimit())) {
				if(logger.isDebugEnabled()) {
					logger.debug("STAFF_ID为: " + staffId + "的用户连接正常");
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////
		        if(!photoFile.isEmpty()) {
		        	//获取原始文件名
		        	//String fileName = uploadFile.getOriginalFilename();
		        	//不带后缀的文件名
		        	//String nPhotoName = BaseUtils.getFileNameWithoutSuffix(photoName);
		        	String nFileType = BaseUtils.getFileSuffix(photoName);

		            if(logger.isDebugEnabled()) {
		                logger.debug("Original photo name: " + photoName);
		            }

		        	String uploadSavePath = this.getConfigValue(Constants.ConfigCode.UPLOAD_PHOTO_SAVE_PATH);
		        	 logger.debug("uploadSavePath: " + uploadSavePath);
		        	if(StringUtils.isBlank(uploadSavePath)) {
		        		result =  Result.buildFileUploadConfigError();
		        	}

		        	//判断存放的目录是否创建
		        	File savePath = new File(uploadSavePath);
		        	if(!savePath.exists()) {
		        		savePath.mkdirs();
		        	}

		        	//实际存放路径
		        	String filePath = uploadSavePath + photoName;
		        	 logger.debug("filePath: " + filePath);
		            File file = new File(filePath);
		            try {
		                FileOutputStream fout = new FileOutputStream(file);
		                fout.write(photoFile.getBytes());
		                fout.flush();
		                fout.close();

		                //TODO bug: 要加入权限控制
		                this.writeUploadLog(staffId, null, null, photoName, filePath, nFileType);

		                //
		                MobilePhotoRecord item = new MobilePhotoRecord();
		                item.setFilePath(filePath);
		                item.setPhotoOwner(staffId);
		                item.setUploadTime(new Date());
		                item.setBatchNo(batchNo);
		                item.setPhotoName(photoName);
		                item.setWorkOrderId(workOrderId);

		                result = this.getMobileCommonService().addPhotoRecord(item);

		            } catch (IOException e) {
		                e.printStackTrace();
		                result = Result.buildPhotoUploadError();
		            }
		        } else {
		        	result = Result.buildPhotoUploadError();
		        }
		        ///////////////////////////////////////////////////////////////////////////////////////////////////
			} else {
				if(logger.isDebugEnabled()) {
					logger.debug("STAFF_ID为: " + staffId + "的用户未配置连接限制");
				}
				result = Result.buildLimitAccessNotConfigError();
			}
		} else {
			result = Result.buildLimitAccessNotConfigError();
		}
        return result;
    }

    /** 用于客户端的上传APP服务
     * @throws Exception */
    @RequestMapping(value = {"/client/upload/app"})
    public @ResponseBody Result uploadAppForClient(@RequestParam(value = "appFile", required = true) MultipartFile appFile,
    		HttpServletRequest request, HttpServletRequest response) {
        if(logger.isDebugEnabled()) {
            logger.debug("Call uploadApp service. ");
        }

        Result result = null;
        if(!appFile.isEmpty()) {
        	//获取原始文件名
        	String fileName = appFile.getOriginalFilename();
        	//不带后缀的文件名
        	String nFileName = BaseUtils.getFileNameWithoutSuffix(fileName);
        	String fileType = BaseUtils.getFileSuffix(fileName);

            if(logger.isDebugEnabled()) {
                logger.debug("Original file name: " + fileName);
            }

        	String appSavePath = this.getConfigValue(Constants.ConfigCode.UPLOAD_APP_SAVE_PATH);

        	if(StringUtils.isBlank(appSavePath)) {
        		result = Result.buildAppUploadConfigError();
        	}

        	//实际存放路径
        	String filePath = appSavePath + nFileName + "_" + System.currentTimeMillis() + "." + fileType;

        	File saveFile = new File(appSavePath);
        	if(!saveFile.exists()) {
        		saveFile.mkdirs();
        	}

            File file = new File(filePath);
            try {
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(appFile.getBytes());
                fout.flush();
                fout.close();

                //TODO bug: 要加入权限控制
                this.writeUploadLog(null, null, null, fileName, filePath, fileType);

                //
                result = Result.buildSuccess();

            } catch (IOException e) {
                e.printStackTrace();
                result = Result.buildAppUploadError();
            }
        } else {
        	result = Result.buildAppUploadError();
        }
        return result;
    }

    /** 供服务器上传APP */
    @RequestMapping(value = {"/server/upload/file"},method=RequestMethod.POST)
    @FilterTag
    public void uploadAppForServer(@RequestParam(value = "appFile", required = false) MultipartFile appFile,
    		@RequestParam(value = "staffId", required = false) Long staffId,
    		@RequestParam(value = "staffName", required = false) String staffName,
    		@RequestParam(value = "username", required = false) String username,
    		HttpServletRequest request, HttpServletResponse response) throws IOException {

    	if(logger.isDebugEnabled()) {
    		logger.debug("Call uploadAppForServer method. ");
    	}
    	//
    	response.setContentType("text/html;charset=UTF-8");

    	Map<String, Object> resultMap = new HashMap<String, Object>();
        if(!appFile.isEmpty()) {
        	//获取原始文件名
        	String fileName = appFile.getOriginalFilename();

        	//不带后缀的文件名
        	String nFileName = BaseUtils.getFileNameWithoutSuffix(fileName);
        	String fileType = BaseUtils.getFileSuffix(fileName);

            if(logger.isDebugEnabled()) {
                logger.debug("Original file name: " + fileName);
            }

        	String appSavePath = this.getConfigValue(Constants.ConfigCode.UPLOAD_APP_SAVE_PATH);

        	if(StringUtils.isBlank(appSavePath)) {
        		resultMap.put("success", false);
        		resultMap.put("reason", "上传失败，应用存放路径未配置！");
        		String jsonStr = JSONObject.fromObject(resultMap).toString();
        		response.getWriter().write(jsonStr);
        	}

        	//实际存放路径
        	String filePath = appSavePath + nFileName + "_" + System.currentTimeMillis() + "." + fileType;
        	logger.debug("filePath============"+filePath);
        	File saveFile = new File(appSavePath);
        	if(!saveFile.exists()) {
        		saveFile.mkdirs();
        	}

            File file = new File(filePath);
            try {
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(appFile.getBytes());
                fout.flush();
                fout.close();

                //TODO bug: 要加入权限控制
                this.writeUploadLog(staffId, staffName, username, fileName, filePath, fileType);

                //
                resultMap.put("success", true);
        		resultMap.put("reason", "文件上传成功！");
        		resultMap.put("filePath", filePath);
        		resultMap.put("fileName", fileName);
        		resultMap.put("fileSize", appFile.getSize());
                logger.debug("resultMap===="+resultMap);
            } catch (IOException e) {
                e.printStackTrace();
                resultMap.put("success", false);
        		resultMap.put("reason", "文件上传失败！");

            }
        } else {
        	resultMap.put("success", false);
    		resultMap.put("reason", "文件上传失败！");
        }
        String jsonStr = JSONObject.fromObject(resultMap).toString();
        response.getWriter().write(jsonStr);
    }
   /**
    * 菜单图标上传
    */
    @RequestMapping(value = {"/server/upload/image"},method=RequestMethod.POST)
    @FilterTag
    public void uploadMenuIcon(@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,

    		HttpServletRequest request, HttpServletResponse response) throws IOException {

    	if(logger.isDebugEnabled()) {
    		logger.debug("Call serverUploadImgae method. ");
    	}
    	//
    	response.setContentType("text/html;charset=UTF-8");

    	Map<String, Object> resultMap = new HashMap<String, Object>();
        if(!imageFile.isEmpty()) {
        	//获取原始文件名
        	String fileName = imageFile.getOriginalFilename();

        	//不带后缀的文件名
        	String nFileName = BaseUtils.getFileNameWithoutSuffix(fileName);
        	String fileType = BaseUtils.getFileSuffix(fileName);

            if(logger.isDebugEnabled()) {
                logger.debug("Original file name: " + fileName);
            }

        	String uploadPath  	 = request.getRealPath("/") + "imagesmob/icon/";
        	String backPath=request.getContextPath()+"/"+ "imagesmob/icon/"; //返回路径
        	if(StringUtils.isBlank(uploadPath)) {
        		resultMap.put("success", false);
        		resultMap.put("reason", "上传失败，应用存放路径未配置！");
        		String jsonStr = JSONObject.fromObject(resultMap).toString();
        		response.getWriter().write(jsonStr);
        	}


        	String path=nFileName + "." + fileType;
        	//实际存放路径
        	String filePath = uploadPath + path;
        	 backPath=backPath+path;
        	File saveFile = new File(uploadPath);
        	if(!saveFile.exists()) {
        		saveFile.mkdirs();
        	}

            File file = new File(filePath);
            try {
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(imageFile.getBytes());
                fout.flush();
                fout.close();
                resultMap.put("success", true);
        		resultMap.put("reason", "文件上传成功！");
        		resultMap.put("filePath", backPath);
        		resultMap.put("fileName", fileName);
        		resultMap.put("fileSize", imageFile.getSize());

            } catch (IOException e) {
                e.printStackTrace();
                resultMap.put("success", false);
        		resultMap.put("reason", "文件上传失败！");

            }
        } else {
        	resultMap.put("success", false);
    		resultMap.put("reason", "文件上传失败！");
        }
        String jsonStr = JSONObject.fromObject(resultMap).toString();
        response.getWriter().write(jsonStr);
    }

    /** 写上传日志 */
    private void writeUploadLog(Long staffId, String staffName, String username,
    		String fileName, String filePath, String fileType) {
        //写上传日志
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(MobileUploadLog.FILE_NAME_NODE, fileName);
        paramMap.put(MobileUploadLog.FILE_PATH_NODE, filePath);
        paramMap.put(MobileUploadLog.FILE_TYPE_NODE, fileType);
        paramMap.put(MobileUploadLog.UPLOAD_TIME_NODE, new Date());
        paramMap.put(MobileUploadLog.UPLOAD_STAFF_ID_NODE, staffId);
        paramMap.put(MobileUploadLog.UPLOAD_STAFF_NAME_NODE, staffName);
        paramMap.put(MobileUploadLog.UPLOAD_STAFF_USERNAME_NODE, username);

        try {
			getMobileCommonService().writeUploadLog(paramMap);
		} catch (Exception e) {
			//若上传日志写入失败，打印错误堆栈，但不处理失败
			e.printStackTrace();
		}
    }

	/**
	 * obd图片上传
	 */
	@RequestMapping(value = {"/client/hn/obd/upload/photo"},method=RequestMethod.POST)
	@ResponseBody
	public JSONObject subFttpPhoto(@RequestBody String param) throws Exception {
		JSONObject res = new JSONObject();
		JSONObject request = JSONObject.fromObject(param);
		String orderId = request.getString("orderId");
		String mFile = request.getString("base64");
		String eqpId = request.getString("eqpId");
			try {
				HttpUtil.sendPostReq("http://192.168.101.175:8471/client/hn/obd/upload/photo", mFile,eqpId,orderId);
				res.put("code","200");
			} catch (Exception e) {
				e.printStackTrace();
				res.put("code","400");
			}
		return res;

//		Result result = null;
		// 复杂类型的request对象
//		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
//		// 获取文件名集合放入迭代器
//		Iterator<String> files = mRequest.getFileNames();
//		while (files.hasNext()) {
//			//获取上传文件的对象
//			MultipartFile mFile = mRequest.getFile(files.next());
//
//			String current = new SimpleDateFormat("yyyyMMddHHmmssSSS")
//					.format(new Date());
//			String photoName = current + EXTENSION;
//			if(!mFile.isEmpty()) {
//				Calendar calendar = Calendar.getInstance();
//				int year = calendar.get(Calendar.YEAR);
//				int month = calendar.get(Calendar.MONTH)+1;
//				int day = calendar.get(Calendar.DAY_OF_MONTH);
//				//生成图片存放目录
//				String phoPath = "/obd/"+year+"/";
//				if(month < 10)
//				{
//					phoPath+="0"+month+"/";
//				}else
//				{
//					phoPath+=month+"/";
//				}
//				if(day < 10)
//				{
//					phoPath+="0"+day+"/";
//				}else
//				{
//					phoPath +=day+"/";
//				}
//				String nFileType = BaseUtils.getFileSuffix(photoName);
//				String upPath = "/ftp_data/photo"+phoPath;
//				//实际存放路径
//				String filePath = upPath + photoName;
//				try {
//					InputStream inputStream = mFile.getInputStream();
//
//					boolean rs = FtpUtil.uploadFile("192.168.101.15", 22, "gkphoto", "zte123", "/ftp_data/photo", phoPath, photoName, inputStream);
//
////					boolean rs = FtpUtil.uploadFile("192.168.101.15", 21, "gkphoto", "zte123", "/ftp_data/photo", phoPath, photoName, inputStream);
//					ResourceDAO resourceDAO = new ResourceDAOImpl();
//					resourceDAO.writePathToOrderEqp(filePath,orderId,eqpId);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				logger.debug("filePath: " + filePath);
//				InputStream in = null;
//
//			} else {
//				result = Result.buildPhotoUploadError();
//			}
//		}


	}




	/** 用于开通工单回单上传图片 */
	@RequestMapping(value = {"/client/hn/web/wkorder/upload/photo"})
	@FilterTag
    public @ResponseBody Result webUploadWkOrderPhoto(@RequestParam(value = "photoFile", required = false) MultipartFile[] photoFiles,
    		@RequestParam(value="staffId", required=false) Long staffId,
    		@RequestParam(value="workOrderId", required=false) Long workOrderId,
    		@RequestParam(value="EqpID", required=false) String eqpId,
    		@RequestParam(value="PortID", required=false) String portId,
    		@RequestParam(value="codeBar", required=false) String codeBar,
    		HttpServletRequest request, HttpServletRequest response) throws Exception {
		Result result = null;
		for(MultipartFile photoFile : photoFiles)
		{

			String batchNo = genBatchNo(staffId);
			//Task:132253
			String current = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
			String photoName = current + EXTENSION;

	        if(logger.isDebugEnabled()) {
	            logger.debug("Call client photo upload service. ");
	            logger.debug("batchNo=="+batchNo);
	            logger.debug("photoName=="+photoName);
	            logger.debug("staffId=="+staffId);
	            logger.debug("workOrderId=="+workOrderId);
	            logger.debug("file Size=="+photoFile.getBytes().length);
	        }
	        //校验工单与条码
	        String checkFlag = this.getMobileCommonService().getWkorderCodeBarFlag(String.valueOf(workOrderId), codeBar);
	        if(!"1".equals(checkFlag))
	        {
	        	result = Result.buildCodeBarError("FTTH工单条形码不能为空，请扫描条形码后再进行提交操作");
	        	return result;
	        }
			///////////////////////////////////////////////////////////////////////////////////////////////////
	        if(!photoFile.isEmpty()) {
	        	Calendar calendar = Calendar.getInstance();
	        	int year = calendar.get(Calendar.YEAR);
	        	int month = calendar.get(Calendar.MONTH)+1;
	        	int day = calendar.get(Calendar.DAY_OF_MONTH);
	        	//生成图片存放目录
	        	String phoPath = "/"+year+"/";
	        	if(month < 10)
	        	{
	        		phoPath+="0"+month+"/";
	        	}else
	        	{
	        		phoPath+=month+"/";
	        	}
	        	if(day < 10)
	        	{
	        		phoPath+="0"+day+"/";
	        	}else
	        	{
	        		phoPath +=day+"/";
	        	}
	        	if(logger.isDebugEnabled()) {
	                logger.debug("Original photo savePath: " + phoPath);
	            }
	        	String nFileType = BaseUtils.getFileSuffix(photoName);

	            if(logger.isDebugEnabled()) {
	                logger.debug("Original photo name: " + photoName);
	            }
	            //获取FTP连接
	        	Map<String,String> ftpParams= new HashMap<String,String>();
	        //	String ftpPath = (String) ftpParams.get("UP_PATH");
	        	if(logger.isDebugEnabled()) {
	                logger.debug("configed ftp savePath: " + ftpParams);
	            }
	        	String upPath = "/ftp_data/photo"+phoPath;
	        	if(logger.isDebugEnabled()) {
	                logger.debug("destination photo path: " + upPath);
	            }
	        	ftpParams.remove("UP_PATH");
	        	ftpParams.put("UP_PATH", upPath);
	        	ftpParams.put("UP_IP_ADDR", "134.161.7.40");
	        	ftpParams.put("UP_FTP_USER", "gkphoto");
	        	ftpParams.put("UP_FTP_PASSWORD", "zte123");
	        	ftpParams.put("UP_PORT", "21");

	        	//判断存放的目录是否创建
	        	FTPUtils ftp = new FTPUtils(ftpParams);
	        	boolean cmdFlag = ftp.isCmdFlag();
	        	if(!cmdFlag)
	        	{
	        		logger.error("connect ftp server error or timeout,please reconnect!");
	        		ftp.closeServer();
	        		result = Result.buildPhotoUploadError();
	        		return result;
	        	}
	        	//实际存放路径
	        	String filePath = upPath + photoName;
	        	 logger.debug("filePath: " + filePath);
	        	 InputStream in = null;
	            try {

	            	byte[] data = photoFile.getBytes();
	            	logger.info("file content:"+data.length);
	            	//两种方式都不行
	            	//in = photoFile.getInputStream();
	            	in = new ByteArrayInputStream(data);
	            	boolean successFlag = ftp.storeFile(filePath, in);
	            	in.close();
	        		ftp.closeServer();
	        		if(!successFlag)
	            	{
	            		logger.error("upload photo failed,filePath:"+filePath);
	            		result = Result.buildPhotoUploadError();
		        		return result;
	            	}
	        		if(logger.isDebugEnabled()) {
		                logger.debug("upload photo success,filePath: " + filePath);
		            }
	            	/*//上传到本机测试
	        		File file = new File("E:/bea/user_projects/domains/base_domain_mobile/autodeploy/SH_MOBILE/Uploads/PHOTO/"+photoName);
	        		FileOutputStream fos = new FileOutputStream(file);
	        		fos.write(data);
	        		fos.flush();
	        		fos.close();*/

	                //记录上传日志
	                this.writeUploadLog(staffId, null, null, photoName, filePath, nFileType);

	                //将上传的照片信息保存到表中
	                MobilePhotoRecord item = new MobilePhotoRecord();
	                item.setFilePath(filePath);
	                item.setPhotoOwner(staffId);
	                item.setUploadTime(new Date());
	                item.setBatchNo(batchNo);
	                item.setPhotoName(photoName);
	                item.setWorkOrderId(workOrderId);
	                item.setResourceId(eqpId==null?"":eqpId);//设备ID
	                item.setResourceName(portId==null?"":portId);//端口ID
	                item.setFbId(codeBar==null?"":codeBar);
	                result = this.getMobileCommonService().addWkOrderPhotoRecord(item);

	            } catch (IOException e) {
	                e.printStackTrace();
	                in.close();
	                ftp.closeServer();
	                result = Result.buildPhotoUploadError();
	            }
	        } else {
	        	result = Result.buildPhotoUploadError();
	        }
		}

        return result;

    }

    /** 用于开通工单回单上传图片 */
	@RequestMapping(value = {"/client/hn/wkorder/upload/photo"})
	@FilterTag
    public @ResponseBody Result uploadWkOrderPhoto(@RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
    		@RequestParam(value="staffId", required=false) Long staffId,
    		@RequestParam(value="workOrderId", required=false) Long workOrderId,
    		@RequestParam(value="EqpID", required=false) String eqpId,
    		@RequestParam(value="PortID", required=false) String portId,
    		@RequestParam(value="codeBar", required=false) String codeBar,
    		HttpServletRequest request, HttpServletRequest response) throws Exception {


        Result result = null;
		String batchNo = genBatchNo(staffId);
		//Task:132253
		String current = new SimpleDateFormat("yyyyMMddHHmmssSSS")
			.format(new Date());
		String photoName = current + EXTENSION;

        if(logger.isDebugEnabled()) {
            logger.debug("Call client photo upload service. ");
            logger.debug("batchNo=="+batchNo);
            logger.debug("photoName=="+photoName);
            logger.debug("staffId=="+staffId);
            logger.debug("workOrderId=="+workOrderId);
            logger.debug("file Size=="+photoFile.getBytes().length);
        }
        //校验工单与条码
        String checkFlag = this.getMobileCommonService().getWkorderCodeBarFlag(String.valueOf(workOrderId), codeBar);
        if(!"1".equals(checkFlag))
        {
        	result = Result.buildCodeBarError("FTTH工单条形码不能为空，请扫描条形码后再进行提交操作");
        	return result;
        }
		///////////////////////////////////////////////////////////////////////////////////////////////////
        if(!photoFile.isEmpty()) {
        	Calendar calendar = Calendar.getInstance();
        	int year = calendar.get(Calendar.YEAR);
        	int month = calendar.get(Calendar.MONTH)+1;
        	int day = calendar.get(Calendar.DAY_OF_MONTH);
        	//生成图片存放目录
        	String phoPath = "/"+year+"/";
        	if(month < 10)
        	{
        		phoPath+="0"+month+"/";
        	}else
        	{
        		phoPath+=month+"/";
        	}
        	if(day < 10)
        	{
        		phoPath+="0"+day+"/";
        	}else
        	{
        		phoPath +=day+"/";
        	}
        	if(logger.isDebugEnabled()) {
                logger.debug("Original photo savePath: " + phoPath);
            }
        	String nFileType = BaseUtils.getFileSuffix(photoName);

            if(logger.isDebugEnabled()) {
                logger.debug("Original photo name: " + photoName);
            }
            //获取FTP连接
        	Map ftpParams = this.getConfigFTPPath(Constants.ConfigCode.UPLOAD_WKORDER_PHOTO_PATH);
        	String ftpPath = (String) ftpParams.get("UP_PATH");
        	if(logger.isDebugEnabled()) {
                logger.debug("configed ftp savePath: " + ftpPath);
            }
        	String upPath = ftpPath+phoPath;
        	if(logger.isDebugEnabled()) {
                logger.debug("destination photo path: " + upPath);
            }
        	ftpParams.remove("UP_PATH");
        	ftpParams.put("UP_PATH", upPath);

        	//判断存放的目录是否创建
        	FTPUtils ftp = new FTPUtils(ftpParams);
        	boolean cmdFlag = ftp.isCmdFlag();
        	if(!cmdFlag)
        	{
        		logger.error("connect ftp server error or timeout,please reconnect!");
        		ftp.closeServer();
        		result = Result.buildPhotoUploadError();
        		return result;
        	}
        	//实际存放路径
        	String filePath = upPath + photoName;
        	 logger.debug("filePath: " + filePath);
        	 InputStream in = null;
            try {
            	byte[] data = photoFile.getBytes();
            	logger.info("file content:"+data.length);
            	//两种方式都不行
            	//in = photoFile.getInputStream();
            	in = new ByteArrayInputStream(data);
            	boolean successFlag = ftp.storeFile(filePath, in);
            	in.close();
        		ftp.closeServer();
        		if(!successFlag)
            	{
            		logger.error("upload photo failed,filePath:"+filePath);
            		result = Result.buildPhotoUploadError();
	        		return result;
            	}
        		if(logger.isDebugEnabled()) {
	                logger.debug("upload photo success,filePath: " + filePath);
	            }
            	/*//上传到本机测试
        		File file = new File("E:/bea/user_projects/domains/base_domain_mobile/autodeploy/SH_MOBILE/Uploads/PHOTO/"+photoName);
        		FileOutputStream fos = new FileOutputStream(file);
        		fos.write(data);
        		fos.flush();
        		fos.close();*/

                //记录上传日志
                this.writeUploadLog(staffId, null, null, photoName, filePath, nFileType);

                //将上传的照片信息保存到表中
                MobilePhotoRecord item = new MobilePhotoRecord();
                item.setFilePath(filePath);
                item.setPhotoOwner(staffId);
                item.setUploadTime(new Date());
                item.setBatchNo(batchNo);
                item.setPhotoName(photoName);
                item.setWorkOrderId(workOrderId);
                item.setResourceId(eqpId==null?"":eqpId);//设备ID
                item.setResourceName(portId==null?"":portId);//端口ID
                item.setFbId(codeBar==null?"":codeBar);
                result = this.getMobileCommonService().addWkOrderPhotoRecord(item);

            } catch (IOException e) {
                e.printStackTrace();
                in.close();
                ftp.closeServer();
                result = Result.buildPhotoUploadError();
            }
        } else {
        	result = Result.buildPhotoUploadError();
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        return result;
    }

	@RequestMapping(value = {"/client/hn/wkorder/ios/upload/photo"})
	@FilterTag
    public @ResponseBody Result uploadWkOrderPhoto4IOS(@RequestParam(value = "photoFile", required = false) String photoFile,
    		@RequestParam(value="staffId", required=false) Long staffId,
    		@RequestParam(value="workOrderId", required=false) Long workOrderId,
    		@RequestParam(value="EqpID", required=false) String eqpId,
    		@RequestParam(value="PortID", required=false) String portId,
    		@RequestParam(value="codeBar", required=false) String codeBar,
    		HttpServletRequest request, HttpServletRequest response) throws Exception {


        Result result = null;
		String batchNo = genBatchNo(staffId);
		//Task:132253
		String current = new SimpleDateFormat("yyyyMMddHHmmssSSS")
			.format(new Date());
		String photoName = current + EXTENSION;

        if(logger.isDebugEnabled()) {
            logger.debug("Call client photo upload service. ");
            logger.debug("batchNo=="+batchNo);
            logger.debug("photoName=="+photoName);
            logger.debug("staffId=="+staffId);
            logger.debug("workOrderId=="+workOrderId);
            //logger.debug("file Size=="+photoFile.getBytes().length);
        }

        //校验工单与条码
        String checkFlag = this.getMobileCommonService().getWkorderCodeBarFlag(String.valueOf(workOrderId), String.valueOf(codeBar));
        if(!"1".equals(checkFlag))
        {
        	result = Result.buildCodeBarError("FTTH工单条形码不能为空，请扫描条形码后再进行提交操作");
        	return result;
        }
		///////////////////////////////////////////////////////////////////////////////////////////////////
      //  if(!photoFile.isFile()) {
        byte[] data = getFromBASE64(photoFile);
        if(data.length>0) {
        	Calendar calendar = Calendar.getInstance();
        	int year = calendar.get(Calendar.YEAR);
        	int month = calendar.get(Calendar.MONTH)+1;
        	int day = calendar.get(Calendar.DAY_OF_MONTH);
        	//生成图片存放目录
        	String phoPath = "/"+year+"/";
        	if(month < 10)
        	{
        		phoPath+="0"+month+"/";
        	}else
        	{
        		phoPath+=month+"/";
        	}
        	if(day < 10)
        	{
        		phoPath+="0"+day+"/";
        	}else
        	{
        		phoPath +=day+"/";
        	}
        	if(logger.isDebugEnabled()) {
                logger.debug("Original photo savePath: " + phoPath);
            }
        	String nFileType = BaseUtils.getFileSuffix(photoName);

            if(logger.isDebugEnabled()) {
                logger.debug("Original photo name: " + photoName);
            }
            //获取FTP连接
        	Map ftpParams = this.getConfigFTPPath(Constants.ConfigCode.UPLOAD_WKORDER_PHOTO_PATH);
        	String ftpPath = (String) ftpParams.get("UP_PATH");
        	if(logger.isDebugEnabled()) {
                logger.debug("configed ftp savePath: " + ftpPath);
            }
        	String upPath = ftpPath+phoPath;
        	if(logger.isDebugEnabled()) {
                logger.debug("destination photo path: " + upPath);
            }
        	ftpParams.remove("UP_PATH");
        	ftpParams.put("UP_PATH", upPath);

        	//判断存放的目录是否创建
        	FTPUtils ftp = new FTPUtils(ftpParams);
        	boolean cmdFlag = ftp.isCmdFlag();
        	if(!cmdFlag)
        	{
        		logger.error("connect ftp server error or timeout,please reconnect!");
        		ftp.closeServer();
        		result = Result.buildPhotoUploadError();
        		return result;
        	}
        	//实际存放路径
        	String filePath = upPath + photoName;
        	 logger.debug("filePath: " + filePath);
        	 InputStream in = null;
            try {
            	//byte[] data = photoFile.getBytes();
            	//logger.info("file content:"+data.length);
            	//两种方式都不行
            	//in = photoFile.getInputStream();
            	 in = new ByteArrayInputStream(data);
            	//in = new FileInputStream(photoFile);
            	boolean successFlag = ftp.storeFile(filePath, in);
            	in.close();
        		ftp.closeServer();
        		if(!successFlag)
            	{
            		logger.error("upload photo failed,filePath:"+filePath);
            		result = Result.buildPhotoUploadError();
	        		return result;
            	}
        		if(logger.isDebugEnabled()) {
	                logger.debug("upload photo success,filePath: " + filePath);
	            }
            	/*//上传到本机测试
        		File file = new File("E:/bea/user_projects/domains/base_domain_mobile/autodeploy/SH_MOBILE/Uploads/PHOTO/"+photoName);
        		FileOutputStream fos = new FileOutputStream(file);
        		fos.write(data);
        		fos.flush();
        		fos.close();*/

                //记录上传日志
                this.writeUploadLog(staffId, null, null, photoName, filePath, nFileType);

                //将上传的照片信息保存到表中
                MobilePhotoRecord item = new MobilePhotoRecord();
                item.setFilePath(filePath);
                item.setPhotoOwner(staffId);
                item.setUploadTime(new Date());
                item.setBatchNo(batchNo);
                item.setPhotoName(photoName);
                item.setWorkOrderId(workOrderId);
                item.setResourceId(eqpId==null?"":eqpId);//设备ID
                item.setResourceName(portId==null?"":portId);//端口ID
                item.setFbId(codeBar==null?"":String.valueOf(codeBar));

                result = this.getMobileCommonService().addWkOrderPhotoRecord(item);

            } catch (IOException e) {
                e.printStackTrace();
                in.close();
                ftp.closeServer();
                result = Result.buildPhotoUploadError();
            }
        } else {
        	result = Result.buildPhotoUploadError();
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        return result;
    }

	/**
	 * 将Base64编码的字符流解码成Byte数据
	 * @param  s
	 * @return b
	 */
	private static byte[] getFromBASE64(String s) {
	    byte[] b = null;
	    if (s != null) {
		    BASE64Decoder decoder = new BASE64Decoder();
		    try {
		     b = decoder.decodeBuffer(s);
		     return b;
		    } catch (Exception e) {
		     e.printStackTrace();
		    }
		 }
		 return b;
	}

}
