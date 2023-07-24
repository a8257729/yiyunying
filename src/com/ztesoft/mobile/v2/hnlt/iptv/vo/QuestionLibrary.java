package com.ztesoft.mobile.v2.hnlt.iptv.vo;

public class QuestionLibrary {
	private int question_id;//����ID
	private int class_id;//��������ID
	private String qustion_title;//��������
	private String qustion_content;//�����
	private int sort_id;//����ID
	private int is_show;//�Ƿ���ʾ��Ĭ��1����ʾ��   0������ʾ��
	private int use_cnt;//���ʴ���
	private int helpul_cnt;//����������
	private int unhelpul_cnt;//����δ�������
	
	
	public QuestionLibrary(){
		
	}
	
	
	public QuestionLibrary(int question_id, int class_id, String qustion_title, String qustion_content, int sort_id,
			int is_show, int use_cnt, int helpul_cnt, int unhelpul_cnt) {
		this.question_id = question_id;
		this.class_id = class_id;
		this.qustion_title = qustion_title;
		this.qustion_content = qustion_content;
		this.sort_id = sort_id;
		this.is_show = is_show;
		this.use_cnt = use_cnt;
		this.helpul_cnt = helpul_cnt;
		this.unhelpul_cnt = unhelpul_cnt;
	}
	
	
	
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getQustion_title() {
		return qustion_title;
	}
	public void setQustion_title(String qustion_title) {
		this.qustion_title = qustion_title;
	}
	public String getQustion_content() {
		return qustion_content;
	}
	public void setQustion_content(String qustion_content) {
		this.qustion_content = qustion_content;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sort_id) {
		this.sort_id = sort_id;
	}
	public int getIs_show() {
		return is_show;
	}
	public void setIs_show(int is_show) {
		this.is_show = is_show;
	}
	public int getUse_cnt() {
		return use_cnt;
	}
	public void setUse_cnt(int use_cnt) {
		this.use_cnt = use_cnt;
	}
	public int getHelpul_cnt() {
		return helpul_cnt;
	}
	public void setHelpul_cnt(int helpul_cnt) {
		this.helpul_cnt = helpul_cnt;
	}
	public int getUnhelpul_cnt() {
		return unhelpul_cnt;
	}
	public void setUnhelpul_cnt(int unhelpul_cnt) {
		this.unhelpul_cnt = unhelpul_cnt;
	}


	@Override
	public String toString() {
		return "QuestionLibrary [question_id=" + question_id + ", class_id=" + class_id + ", qustion_title="
				+ qustion_title + ", qustion_content=" + qustion_content + ", sort_id=" + sort_id + ", is_show="
				+ is_show + ", use_cnt=" + use_cnt + ", helpul_cnt=" + helpul_cnt + ", unhelpul_cnt=" + unhelpul_cnt
				+ "]";
	}
	
	
}
