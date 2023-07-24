package com.ztesoft.mobile.v2.entity.workform.xinjiang;

import com.ztesoft.mobile.v2.core.Entity;

public class SimOrgInfo extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7738731664069884714L;
	
	public static final String AREA_ID_NODE = "areaId";
	
	public static final String ORG_ID_NODE = "orgId";
	
	public static final String ORG_NAME_NODE = "orgName";
	
	public static final String IS_LEAF_NODE = "isLeaf";
	
	public static final String NODE_TYPE_NODE = "nodeType";
	

	private Long areaId;
	
	private Long orgId;
	
	private String orgName;
	
	private String isLeaf;
	
	private String nodeType;

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
