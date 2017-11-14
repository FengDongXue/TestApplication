package com.lanwei.governmentstar.bean;

import java.io.Serializable;

public class BN_TDept implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 主键ID
	private String opId;
	// 名称
	private String opName;
	// 地址
	private String deptAddress;
	// 电话
	private String deptPhone;

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getDeptAddress() {
		return deptAddress;
	}

	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}

	public String getDeptPhone() {
		return deptPhone;
	}

	public void setDeptPhone(String deptPhone) {
		this.deptPhone = deptPhone;
	}

}
