package com.ztesoft.mobile.v2.controller.building;

import java.util.List;

/**
 * ǰ̨���ؽ������
 * @author Dell
 *
 */
public class PageResult {
	private String result = "success";//������������Ĭ�ϳɹ�
	private String message;//�û�������ʾ��Ϣ
	private List data;//��������
	private Pager pager;//��ҳ����
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	

}
