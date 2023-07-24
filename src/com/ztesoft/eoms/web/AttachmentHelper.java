package com.ztesoft.eoms.web;

import com.zterc.uos.client.ParameterManager;
import com.zterc.uos.model.ParameterDTO;

/**
 * <p>Title: �����ϴ����ظ�����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ZTEsoft</p>
 * @author: yan.hua4
 * @version 1.0
 */
public class AttachmentHelper {


    /**
     * ��ȡ���ݴ洢��ʽ
     * ��AttachmentServlet����
     * @return String
     */
    public static String getStorageType() {
        return getValueByKey("com.ztesoft.eoms.web.upload.StorageType");
    }

    /**
     * ��ѯ�ϴ������Ĵ�С����
     * ��AttachmentServlet �� attachmentUpload.jsp����
     * @return String
     */
    public static String getFileMaxSize() {
        return getValueByKey("com.ztesoft.eoms.web.upload.FileMaxSize");
    }   
   

    /**
     * ��ȡ�����ڷ�����Ӳ�̵�Ŀ¼
     * ��AttachmentServlet����
     * @return String
     */
    public static String getEomsDocPath() {
        return getValueByKey("com.ztesoft.eoms.web.upload.eomsDocPath");
    }

    /**
     * ��ȡcdma�����ڷ�����Ӳ�̵�Ŀ¼
     * ��CDMAAttachmentServlet����
     * @return String
     */
    public static String getCDMADocPath(){
    	return getValueByKey("com.ztesoft.eoms.web.upload.cdmaDocPath");
    }
    /**
     * �����תΪ����
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
