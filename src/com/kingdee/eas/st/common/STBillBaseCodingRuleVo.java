package com.kingdee.eas.st.common;

import java.io.Serializable;

import com.kingdee.bos.dao.IObjectValue;

public class STBillBaseCodingRuleVo implements Serializable {

	private static final long serialVersionUID = 7983005497899167628L;

	private IObjectValue coreBillInfo;
	private String companyID;
	private String orgType;
	private String number;

	/** ���صı��� */
	private String sysNumber;

	/** �Ƿ���ڱ������ */
	private boolean isExist;

	/** �Ƿ������޸� */
	private boolean isModifiable;

	/** �Ƿ�������ʾ */
	private boolean isAddView;

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public IObjectValue getCoreBillInfo() {
		return coreBillInfo;
	}

	public void setCoreBillInfo(IObjectValue coreBillInfo) {
		this.coreBillInfo = coreBillInfo;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	public boolean isModifiable() {
		return isModifiable;
	}

	public void setModifiable(boolean isModifiable) {
		this.isModifiable = isModifiable;
	}

	/**
	 * �Ƿ�������ʾ��
	 * 
	 * @return ������ʾ���Ե�ֵ
	 */
	public boolean isAddView() {
		return this.isAddView;
	}

	public void setAddView(boolean isAddView) {
		this.isAddView = isAddView;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getSysNumber() {
		return sysNumber;
	}

	public void setSysNumber(String sysNumber) {
		this.sysNumber = sysNumber;
	}

}
