package com.ztesoft.mobile.v2.controller.upload;


import com.zterc.uos.helpers.JdbcExecutor;
import com.ztesoft.mobile.v2.controller.common.Hdf;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.controller.upload.dao.UploadDAO;
import com.ztesoft.mobile.v2.controller.upload.dao.UploadDAOImpl;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.common.CommonService;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO APP文件上传
 * @author: lck
 * @date: 2021年03月01日 15:07
 */


@Controller("uploadFileController")
@RequestMapping("/client/upload")
public class UploadFileController{

    private Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }


    private UploadDAO uploadDao = new UploadDAOImpl();



    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }


    @RequestMapping(value = "/see/image")
    @ResponseBody
    public Object seeImage(@RequestBody Map<String, Object> data){
        String staffId = (String) data.get("staffId");
        String select = "select PHOTO_PATH from UOS_STAFF_PHOTO_PATH where staff_id=? and TRAIN_RECODE_PATH is null and EXAM_RECODE_PATH is null and COMMITMENT_RECODE_PATH is null and PERFORMANCE_PATH is null";
        Map map= new HashMap();
        map.put("photo","");
        try {
            Map param = new HashMap();
            param.put("staffId",staffId);
            String wherePattern = "staff_id:staffId";

            Result result = getCommonService().commonQueryListBySql(select, param, wherePattern);
            List<Map<String,Object>> list = (List)((Map)(result.getResultData().get("data_info"))).get("dataList");
            if(list!=null){
                if(list.size()>0){
                    Map<String, Object> file = list.get(0);
                    String path=(String) file.get("PHOTO_PATH");
                    Object see = see(path);
                    map.put("photo",see);
                }else{
                    map.put("photo","");
                }
            }else{
                map.put("photo","");
            }
        }catch (Exception e){
            e.printStackTrace();
            return map;
        }
       return map;
    }



    public String see(String path){
        String result = "";
        BufferedReader in = null;
        try {
            HttpURLConnection conn = null;
            InputStream ins = null;
            ByteArrayOutputStream outStream = null;
            String urlNameString = "http://192.168.101.15:12085/gk/upload/see" + "?path=" + path;
            URL realUrl = new URL(urlNameString);
            conn=(HttpURLConnection)realUrl.openConnection();
            conn.setRequestProperty("Content-Type", "text/html");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ins = conn.getInputStream();
                outStream = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int count = -1;
                while ((count = ins.read(data, 0, 1024)) != -1) {
                    outStream.write(data, 0, count);
                }
                data = null;
                result = new String(outStream
                        .toByteArray(), "UTF-8");

            }

        } catch (Exception e) {
            logger.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping(value = "/image",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImage(@RequestBody Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String image = (String) data.get("image");
        String staffId = (String) data.get("staffId");
        image=image.replaceAll("data:image/png;base64,","");
        BASE64Decoder decoder =  new BASE64Decoder();
        byte[] imageByte = null;
        try{
            imageByte = decoder.decodeBuffer(image);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputStream in = new ByteArrayInputStream(imageByte);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        String upload = upload(bufferedInputStream, "png");

        if(StringUtils.isBlank(upload)){
            return false;
        }
        String select = "select PHOTO_PATH from UOS_STAFF_PHOTO_PATH where staff_id=? and TRAIN_RECODE_PATH is null and EXAM_RECODE_PATH is null and COMMITMENT_RECODE_PATH is null and PERFORMANCE_PATH is null";
        Map param = new HashMap();
        param.put("staffId",staffId);
        String wherePattern = "staff_id:staffId";

        Result result = getCommonService().commonQueryListBySql(select, param, wherePattern);
        List list = (List)((Map)(result.getResultData().get("data_info"))).get("dataList");
        int update = 0;
        if(list.size()>0){
            update = uploadDao.update(staffId,upload);
        }else{
            update = uploadDao.insert(staffId,upload);

        }
        return update>0?true:false;
    }

    public String upload(BufferedInputStream in, String filename) {
        try {
            URL url = new URL("http://192.168.101.15:12085/gk/upload/image?fileName="+filename);
            OutputStream out = null;
            HttpURLConnection conn = null;
            InputStream ins = null;
            ByteArrayOutputStream outStream = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/html");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.connect();
            conn.setConnectTimeout(10000);
            out = conn.getOutputStream();

            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
            out.flush();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ins = conn.getInputStream();
                outStream = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int count = -1;
                while ((count = ins.read(data, 0, 1024)) != -1) {
                    outStream.write(data, 0, count);
                }
                data = null;
                String s = new String(outStream
                        .toByteArray(), "UTF-8");
                return s;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "";
    }

}