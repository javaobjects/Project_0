package com.kingdee.eas.st.common.MillerUtils;

import java.util.ArrayList;
import java.util.HashMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.IMultiMeasureUnit;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.MultiMeasureUnitCollection;
import com.kingdee.eas.basedata.master.material.MultiMeasureUnitFactory;
import com.kingdee.eas.basedata.master.material.MultiMeasureUnitInfo;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.st.common.client.STBillBaseEditUI;

public class PrecisionManager {
	CoreUI stBill = null;
	HashMap precisionMap = new HashMap();
	ArrayList items = new ArrayList();

	public PrecisionManager(CoreUI ui) {
		stBill = ui;
	}

	public void addItem(KDTable table, String materialColumn,
			String[] qtyColumns, String unitColumn, String[] baseQtyColumns,
			String baseUnitColumn) {
		PrecisionItem item = new PrecisionItem();
		item.table = table;
		item.materialColumn = materialColumn;
		item.qtyColumns = qtyColumns;
		item.unitColumn = unitColumn;
		item.baseQtyColumns = baseQtyColumns;
		item.baseUnitColumn = baseUnitColumn;

		items.add(item);
	}

	public void updatePrecisionMap() {
		StringBuffer sb = new StringBuffer();
		ArrayList materialList = new ArrayList();
		ArrayList unitList = new ArrayList();
		HashMap newMap = new HashMap();

		for (int i = 0; i < items.size(); i++) {
			PrecisionItem item = (PrecisionItem) items.get(i);
			for (int j = 0; j < item.table.getRowCount(); j++) {
				if (item.table.getCell(j, item.materialColumn) == null
						|| item.table.getCell(j, item.materialColumn)
								.getValue() == null) {
					continue;
				}
				if (item.table.getCell(j, item.unitColumn) == null
						|| item.table.getCell(j, item.unitColumn).getValue() == null) {
					continue;
				}
				Object material = item.table.getCell(j, item.materialColumn)
						.getValue();
				Object unit = item.table.getCell(j, item.unitColumn).getValue();

				if (material instanceof MaterialInfo
						&& unit instanceof MeasureUnitInfo) {
					Object precision = precisionMap
							.get(((MaterialInfo) material).getId().toString()
									+ ((MeasureUnitInfo) unit).getId()
											.toString());
					if (precision == null) {
						precision = newMap.get(((MaterialInfo) material)
								.getId().toString()
								+ ((MeasureUnitInfo) unit).getId().toString());
					}
					if (precision == null) {
						newMap.put(((MaterialInfo) material).getId().toString()
								+ ((MeasureUnitInfo) unit).getId().toString(),
								"");
						materialList.add(((MaterialInfo) material).getId()
								.toString());
						unitList.add(((MeasureUnitInfo) unit).getId()
								.toString());
					}
				} else if (material instanceof String && unit instanceof String) {
					Object precision = precisionMap.get(((String) material)
							+ ((String) unit));
					if (precision == null) {
						precision = newMap.get(((String) material)
								+ ((String) unit));
					}
					if (precision == null) {
						newMap.put(((String) material) + ((String) unit), "");
						materialList.add(material);
						unitList.add(unit);
					}
				}

				if (item.table.getCell(j, item.baseUnitColumn) != null) {
					Object baseUnit = item.table
							.getCell(j, item.baseUnitColumn).getValue();

					if (material instanceof MaterialInfo
							&& baseUnit instanceof MeasureUnitInfo) {
						Object precision = precisionMap
								.get(((MaterialInfo) material).getId()
										.toString()
										+ ((MeasureUnitInfo) baseUnit).getId()
												.toString());
						if (precision == null) {
							precision = newMap.get(((MaterialInfo) material)
									.getId().toString()
									+ ((MeasureUnitInfo) baseUnit).getId()
											.toString());
						}
						if (precision == null) {
							newMap.put(((MaterialInfo) material).getId()
									.toString()
									+ ((MeasureUnitInfo) baseUnit).getId()
											.toString(), "");
							materialList.add(((MaterialInfo) material).getId()
									.toString());
							unitList.add(((MeasureUnitInfo) baseUnit).getId()
									.toString());
						}
					} else if (material instanceof String
							&& baseUnit instanceof String) {
						Object precision = precisionMap.get(((String) material)
								+ ((String) baseUnit));
						if (precision == null) {
							precision = newMap.get(((String) material)
									+ ((String) baseUnit));
						}
						if (precision == null) {
							newMap.put(((String) material)
									+ ((String) baseUnit), "");
							materialList.add(material);
							unitList.add(baseUnit);
						}
					}
				}
			}
		}

		for (int i = 0; i < materialList.size(); i++) {
			if (sb.length() > 0) {
				sb.append(" OR ");
			}
			sb.append("(material.id = '").append(materialList.get(i)).append(
					"' AND measureUnit.id = '").append(unitList.get(i)).append(
					"')");
		}

		try {
			if (sb.length() > 0) {
				String oql = "select material.id, measureUnit.id, qtyPrecision where "
						+ sb.toString();
				IMultiMeasureUnit imultiUnit = MultiMeasureUnitFactory
						.getRemoteInstance();
				MultiMeasureUnitCollection mulUnits = imultiUnit
						.getMultiMeasureUnitCollection(oql);
				for (int i = 0, size = mulUnits.size(); i < size; i++) {
					MultiMeasureUnitInfo mmu = mulUnits.get(i);
					String key = mmu.getMaterial().getId().toString()
							+ mmu.getMeasureUnit().getId().toString();
					precisionMap.put(key, new Integer(mmu.getQtyPrecision()));
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public void dealPrecision() {
		updatePrecisionMap();

		for (int i = 0; i < items.size(); i++) {
			PrecisionItem item = (PrecisionItem) items.get(i);

			for (int j = 0; j < item.qtyColumns.length; j++) {
				dealPrecision(item.table, item.materialColumn,
						item.qtyColumns[j], item.unitColumn);
			}

			for (int j = 0; j < item.baseQtyColumns.length; j++) {
				dealPrecision(item.table, item.materialColumn,
						item.baseQtyColumns[j], item.baseUnitColumn);
			}
		}
	}

	private void dealPrecision(KDTable table, String materialColumn,
			String qtyColumn, String unitColumn) {
		for (int i = 0; i < table.getRowCount(); i++) {

			if (table.getCell(i, materialColumn) == null
					|| table.getCell(i, materialColumn).getValue() == null) {
				continue;
			}
			if (table.getCell(i, unitColumn) == null
					|| table.getCell(i, unitColumn).getValue() == null) {
				continue;
			}
			Object material = table.getCell(i, materialColumn).getValue();
			Object unit = table.getCell(i, unitColumn).getValue();

			if (material instanceof MaterialInfo
					&& unit instanceof MeasureUnitInfo) {
				Object precision = precisionMap.get(((MaterialInfo) material)
						.getId().toString()
						+ ((MeasureUnitInfo) unit).getId().toString());

				if (null != precision && precision instanceof Integer)
					KDTableUtils.setBigDecimalFieldsPrecision(
							((Integer) precision).intValue(), table, i,
							new String[] { qtyColumn });
				else
					KDTableUtils.setBigDecimalFieldsPrecision(0, table, i,
							new String[] { qtyColumn });
			} else if (material instanceof String && unit instanceof String) {
				Object precision = precisionMap.get(((String) material)
						+ ((String) unit));

				if (null != precision && precision instanceof Integer)
					KDTableUtils.setBigDecimalFieldsPrecision(
							((Integer) precision).intValue(), table, i,
							new String[] { qtyColumn });
				else
					KDTableUtils.setBigDecimalFieldsPrecision(0, table, i,
							new String[] { qtyColumn });
			}
		}
	}
}

class PrecisionItem {
	KDTable table = null;
	String materialColumn = null;
	String[] qtyColumns = null;
	String unitColumn = null;
	String[] baseQtyColumns = null;
	String baseUnitColumn = null;
}
