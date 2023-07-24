package com.ztesoft.mobile.v2.entity.workform.xinjiang;

import com.ztesoft.mobile.v2.core.Entity;

public class OrgStaffInfo extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7738731664069884714L;
	
	public static final String ID_NODE = "id";
	
	public static final String NAME_NODE = "name";
	
	public static final String HAS_PARENT_NODE = "mhasParent";
	
	public static final String HAS_CHILD_NODE = "mhasChild";
	
	public static final String PARENT_ID_NODE = "parent";
	
	public static final String LEVEL_NODE = "level";
	
	public static final String NODE_TYPE_NODE = "expanded";
	
	public static final String MARK_NODE = "partyType";
	

	private Long id;
	
	private String name;
	
	private String mhasParent;
	
	private String mhasChild;
	
	private String parent;

	private String level;
	
	private String expanded;
	
	private String partyType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMhasParent() {
		return mhasParent;
	}

	public void setMhasParent(String mhasParent) {
		this.mhasParent = mhasParent;
	}

	public String getMhasChild() {
		return mhasChild;
	}

	public void setMhasChild(String mhasChild) {
		this.mhasChild = mhasChild;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

}
