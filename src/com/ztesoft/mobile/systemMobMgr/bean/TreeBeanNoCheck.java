package com.ztesoft.mobile.systemMobMgr.bean;

import java.util.List;

public class TreeBeanNoCheck {
	private String id;
	private String text;
	private String privCode;
	private String parentId;
	private boolean leaf;
	private String pathCode;
	private List children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPrivCode() {
		return privCode;
	}
	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getPathCode() {
		return pathCode;
	}
	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}
}
