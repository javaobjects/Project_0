/*
 * @(#)MeasureUnitColumnChangeProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.mm.qm.utils.QtyMultiMeasureUtils;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.util.StringUtils;

public class MeasureUnitColumnChangeProcessor extends ColumnChangeProcessor {

	public MeasureUnitColumnChangeProcessor(KDTable table,
			String materialColumnName, String sourceColumnName,
			String[] targetColumnNames) {
		super(table, materialColumnName, sourceColumnName, targetColumnNames);
	}

	public boolean process(KDTEditEvent e) {

		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();

		int rowIndex = e.getRowIndex();

		if (!((oldValue != null) && (newValue != null) && newValue
				.equals(oldValue))) {
			try {
				MaterialInfo materialInfo = (MaterialInfo) table.getRow(
						rowIndex).getCell(materialColumnName).getValue();
				if ((oldValue instanceof MeasureUnitInfo)
						&& (newValue instanceof MeasureUnitInfo)) {
					MeasureUnitInfo oldMeasureUnitInfo = (MeasureUnitInfo) oldValue;
					MeasureUnitInfo newMeasureUnitInfo = (MeasureUnitInfo) newValue;

					// 设定精度
					int qtyPrecision = QtyMultiMeasureUtils.qtyPrecision(
							materialInfo, newMeasureUnitInfo);
					KDTableUtils.setBigDecimalFieldsPrecision(qtyPrecision,
							table, rowIndex, targetColumnNames);

					// 循环修改数量的精度\修改数量
					for (int i = 0; i < targetColumnNames.length; i++) {

						Object v = KDTableUtils.getFieldValue(table, rowIndex,
								targetColumnNames[i]);
						BigDecimal oldQty = SysConstant.BIGZERO;
						if (STQMUtils.isNotNull(v)
								&& !StringUtils.isEmpty(v.toString())) {
							oldQty = new BigDecimal(v.toString());
						}

						BigDecimal newQty = null;
						newQty = QtyMultiMeasureUtils.calculateQty(
								materialInfo, oldMeasureUnitInfo,
								newMeasureUnitInfo, oldQty);

						table.getRow(rowIndex).getCell(targetColumnNames[i])
								.setValue(newQty);
					}
				}
			} catch (Exception e1) {
				return false;
			}
		}
		return true;
	}

}
