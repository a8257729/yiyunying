package com.ztesoft.android.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestCreate {

	public static void main(String[] args){
		
		JSONObject butObject = new JSONObject();
		
		 
		 JSONObject m1 = new JSONObject();
		 m1.put("L0", "1");   //������1��ֱ����2ˮƽ Ĭ��1
		 m1.put("L1", "1"); //ÿ�л�����ʾ�����ֶ� Ĭ��1
		 m1.put("L2", "3");       //��ʾλ��,��1����,2�ײ�,3Ĭ��
		 m1.put("L3", "userName");     //�ֶ���
		 m1.put("L4", "3");     //ѡ������1��,2ʱ��,3ɨ��
		 
		 JSONArray list2 = new JSONArray();
		 JSONObject m11 = new JSONObject();
		 m11.put("L0", "1");
		 m11.put("L1", "������Ϣ");
		 m11.put("L2", "page");
		 
		 JSONObject m21 = new JSONObject();
		 m21.put("L0", "1");
		 m21.put("L1", "��ϸ��Ϣ");
		 m21.put("L2", "page");
		 
		 list2.add(m11);
		 list2.add(m21);
		
		 JSONArray list3 = new JSONArray();         //�ֶ���Ϣ
		 JSONObject m111 = new JSONObject();
		 m111.put("L0", "userName");
		 m111.put("L1", "����");
		 m111.put("L2", "����");               //����
		 
		 JSONObject m211 = new JSONObject();
		 m211.put("L0", "userName");
		 m211.put("L1", "����");
		 m211.put("L2", "����");
		 
		 list3.add(m111);
		 list3.add(m211);

		 JSONObject m7 = new JSONObject();
		 m7.put("layout", m1);
		 m7.put("tabData", list2.toString());
		 m7.put("fileData", list3.toString());
		 
		 JSONObject holder = new JSONObject();
		 holder.put("result", "000");
		 holder.put("body", m7);
		System.out.println("njsonArray--> "+holder.toString());
		
	}
}
