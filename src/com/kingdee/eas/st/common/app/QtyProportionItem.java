/*
 * @(#)LadingProportionItem.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app;

import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:
 * 
 * @author daij date:2008-7-18
 *         <p>
 * @version EAS5.4
 */
public abstract class QtyProportionItem extends ProportionItem {

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2008-7-18
	 *              <p>
	 */
	public QtyProportionItem() {
		super();
	}

	/**
	 * ���������캯��
	 * 
	 * @param tableName
	 * @param rightFieldName
	 * @param proportionFieldName
	 * @param parentFieldName
	 * @author:daij ����ʱ�䣺2008-7-18
	 *              <p>
	 */
	public QtyProportionItem(String tableName, String rightFieldName,
			String proportionFieldName, String parentFieldName) {
		super(tableName, rightFieldName, proportionFieldName, parentFieldName);
	}

	/**
	 * ������
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.ProportionItem#correlativeFieldSQLString()
	 */
	public String correlativeFieldSQLString() {
		StringBuffer script = new StringBuffer();
		if (isEffective()) {
			script.append(
					field("baseUnit", "FQtyPrecision As FBaseQtyPrecision "))
					.append(",").append(
							field("T_BD_MultiMeasureUnit", "FQtyPrecision "))
					.append(",").append(
							field("T_BD_MultiMeasureUnit", "FBaseConvsRate "));
		}
		return script.toString();
	}

	/**
	 * ������
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.ProportionItem#correlativeTableSQLString()
	 */
	public String correlativeTableSQLString() {
		StringBuffer script = new StringBuffer();
		if (isEffective()) {
			script
					.append(
							" Left Outer Join T_BD_MultiMeasureUnit baseUnit On baseUnit.FMeasureUnitID = ")
					.append(field("FBaseUnitID"))
					.append(STConstant.RN)
					.append("		And baseUnit.FMaterialID = ")
					.append(field("FMaterialID"))
					.append(STConstant.RN)
					.append(
							" Left Outer Join T_BD_MultiMeasureUnit On T_BD_MultiMeasureUnit.FMeasureUnitID = ")
					.append(field("FUnitID")).append(STConstant.RN).append(
							"		And T_BD_MultiMeasureUnit.FMaterialID = ")
					.append(field("FMaterialID"));
		}
		return script.toString();
	}

	/**
	 * 
	 * ������
	 * 
	 * @author:daij
	 * @throws SQLDataException
	 * @see com.kingdee.eas.st.common.app.ProportionItem#rightRecordInstance()
	 */
	public RightRecord rightRecordInstance(IRowSet rs) throws SQLDataException {
		RightRecord rd = new QtyRightRecord(rs);
		rd.setScale(this.scale);
		return rd;
	}
}
