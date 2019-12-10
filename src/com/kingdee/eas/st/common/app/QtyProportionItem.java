/*
 * @(#)LadingProportionItem.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app;

import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:
 * 
 * @author daij date:2008-7-18
 *         <p>
 * @version EAS5.4
 */
public abstract class QtyProportionItem extends ProportionItem {

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 */
	public QtyProportionItem() {
		super();
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param tableName
	 * @param rightFieldName
	 * @param proportionFieldName
	 * @param parentFieldName
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 */
	public QtyProportionItem(String tableName, String rightFieldName,
			String proportionFieldName, String parentFieldName) {
		super(tableName, rightFieldName, proportionFieldName, parentFieldName);
	}

	/**
	 * 描述：
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
	 * 描述：
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
	 * 描述：
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
