/*
 * @(#)QtyColumnChangeProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.mm.qm.utils.QtyMultiMeasureUtils;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.util.StringUtils;

public class QtyColumnChangeProcessor extends ColumnChangeProcessor {

	private String sourceMeasureUnitColumnName = null;
	private String[] targetMeasureUnitColumnNames = null;

	public QtyColumnChangeProcessor(KDTable table, String materialColumnName,
			String measureUnitColumnName, String sourceColumnName,
			String[] targetMeasureUnitColumnNames, String[] targetColumnNames) {
		super(table, materialColumnName, sourceColumnName, targetColumnNames);
		this.sourceMeasureUnitColumnName = measureUnitColumnName;
		this.targetMeasureUnitColumnNames = targetMeasureUnitColumnNames;

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

				if (STQMUtils.isNull(table.getRow(rowIndex).getCell(
						sourceMeasureUnitColumnName))) {
					return false;
				}
				Object o = table.getRow(rowIndex).getCell(
						sourceMeasureUnitColumnName).getValue();
				MeasureUnitInfo unitInfo = null;
				if (o instanceof MeasureUnitInfo) {
					unitInfo = (MeasureUnitInfo) o;
				}

				// 如果修改的是数量，则重新计算基本数量
				// 如果修改常量单位，基本单位数量不变，常用单位数量调整

				if (e.getColIndex() == table
						.getColumnIndex(sourceMeasureUnitColumnName)) {
					if (STQMUtils.isNotNull(materialInfo)
							&& STQMUtils.isNotNull(unitInfo)) {

						// TODO 目标与源混乱，需要调整
						// 取得源列的数量
						Object v = KDTableUtils.getFieldValue(table, rowIndex,
								targetColumnNames[0]);
						BigDecimal baseQty = SysConstant.BIGZERO;
						if (STQMUtils.isNotNull(v)
								&& !StringUtils.isEmpty(v.toString())) {
							baseQty = new BigDecimal(v.toString());
						}

						// 修改数量
						ICell cell = table.getRow(rowIndex).getCell(
								targetMeasureUnitColumnNames[0]);
						if (STQMUtils.isNotNull(cell)) {
							o = cell.getValue();
							MeasureUnitInfo baseUnitInfo = null;
							if (o instanceof MeasureUnitInfo) {
								baseUnitInfo = (MeasureUnitInfo) o;
								BigDecimal qty = null;
								qty = QtyMultiMeasureUtils.calculateQty(
										materialInfo, baseUnitInfo, unitInfo,
										baseQty);
								table.getRow(rowIndex)
										.getCell(sourceColumnName)
										.setValue(qty);
							}

						}
					}

				} else {
					if (STQMUtils.isNotNull(materialInfo)
							&& STQMUtils.isNotNull(unitInfo)) {

						// 取得源列的数量
						Object v = KDTableUtils.getFieldValue(table, rowIndex,
								sourceColumnName);
						BigDecimal qty = SysConstant.BIGZERO;
						if (STQMUtils.isNotNull(v)
								&& !StringUtils.isEmpty(v.toString())) {
							qty = new BigDecimal(v.toString());
						}

						// 循环修改数量
						for (int i = 0; i < targetColumnNames.length; i++) {

							ICell cell = table.getRow(rowIndex).getCell(
									targetMeasureUnitColumnNames[i]);
							if (STQMUtils.isNotNull(cell)) {
								o = cell.getValue();
								MeasureUnitInfo baseUnitInfo = null;
								if (o instanceof MeasureUnitInfo) {
									baseUnitInfo = (MeasureUnitInfo) o;
									BigDecimal baseQty = null;
									baseQty = QtyMultiMeasureUtils
											.calculateQty(materialInfo,
													unitInfo, baseUnitInfo, qty);
									table.getRow(rowIndex).getCell(
											targetColumnNames[i]).setValue(
											baseQty);
								}

							}
						}

					}
				}

			} catch (Exception e1) {
				return false;
			}
		}
		return true;

	}
}
