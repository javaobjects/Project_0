package com.kingdee.eas.st.common;

public class STBizUnitDataVO {

	private String materialColName;
	private String unitColName;
	private String baseUnitColName;
	private String billTypeID;
	private String assistUnitColName;
	private String[] baseUnitQtyColumns;
	private String[] unitQtyColumns;
	private String[] assistUnitQtyColumns;

	// ���ݲ�Ʒ���ԣ����þ��ȣ�����2�����飬��һ����query�е��ֶ������ڶ�����Table�е�����
	private String[][] pdTypeFieldNames;

	public String getBillTypeID() {
		return billTypeID;
	}

	public void setBillTypeID(String billTypeID) {
		this.billTypeID = billTypeID;
	}

	public String getBaseUnitColName() {
		return baseUnitColName;
	}

	public void setBaseUnitColName(String baseUnitColName) {
		this.baseUnitColName = baseUnitColName;
	}

	public String[] getBaseUnitQtyColumns() {
		return baseUnitQtyColumns;
	}

	public void setBaseUnitQtyColumns(String[] baseUnitQtyColumns) {
		this.baseUnitQtyColumns = baseUnitQtyColumns;
	}

	public String getMaterialColName() {
		return materialColName;
	}

	public void setMaterialColName(String materialColName) {
		this.materialColName = materialColName;
	}

	public String getUnitColName() {
		return unitColName;
	}

	public void setUnitColName(String unitColName) {
		this.unitColName = unitColName;
	}

	public String[] getUnitQtyColumns() {
		return unitQtyColumns;
	}

	public void setUnitQtyColumns(String[] unitQtyColumns) {
		this.unitQtyColumns = unitQtyColumns;
	}

	public String getAssistUnitColName() {
		return assistUnitColName;
	}

	public void setAssistUnitColName(String assistUnitColName) {
		this.assistUnitColName = assistUnitColName;
	}

	public void setPdTypeFieldNames(String[][] pdTypeFieldNames) {
		this.pdTypeFieldNames = pdTypeFieldNames;
	}

	public String[][] getPdTypeFieldNames() {
		return pdTypeFieldNames;
	}

	public void setAssistUnitQtyColumns(String[] assistUnitQtyColumns) {
		this.assistUnitQtyColumns = assistUnitQtyColumns;
	}

	public String[] getAssistUnitQtyColumns() {
		return assistUnitQtyColumns;
	}

}
