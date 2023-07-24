package com.ztesoft.eoms.web;

import com.zterc.uos.client.ParameterManager;
import com.zterc.uos.model.ParameterDTO;

/**
 * <p>Title: 附件上传下载辅助类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ZTEsoft</p>
 * @author: yan.hua4
 * @version 1.0
 */
public class AttachmentHelper {


    /**
     * 获取数据存储方式
     * 供AttachmentServlet调用
     * @return String
     */
    public static String getStorageType() {
        return getValueByKey("com.ztesoft.eoms.web.upload.StorageType");
    }

    /**
     * 查询上传附件的大小上限
     * 供AttachmentServlet 和 attachmentUpload.jsp调用
     * @return String
     */
    public static String getFileMaxSize() {
        return getValueByKey("com.ztesoft.eoms.web.upload.FileMaxSize");
    }   
   

    /**
     * 获取附件在服务器硬盘的目录
     * 供AttachmentServlet调用
     * @return String
     */
    public static String getEomsDocPath() {
        return getValueByKey("com.ztesoft.eoms.web.upload.eomsDocPath");
    }

    /**
     * 获取cdma附件在服务器硬盘的目录
     * 供CDMAAttachmentServlet调用
     * @return String
     */
    public static String getCDMADocPath(){
    	return getValueByKey("com.ztesoft.eoms.web.upload.cdmaDocPath");
    }
    /**
     * 传输后转为中文
     * @param s String
     * @return String
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }


    private static ParameterManager parameterManager = null;
    private static String getValueByKey(String _key) {
        String _value = null;
        if (parameterManager == null) {
            parameterManager = new ParameterManager(false);
        }
        try {
            ParameterDTO paraDto = parameterManager.findParameter(_key);
            if (paraDto != null) {
                _value = paraDto.getValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return _value;
    }
}
