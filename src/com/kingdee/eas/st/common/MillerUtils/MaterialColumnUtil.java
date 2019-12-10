package com.kingdee.eas.st.common.MillerUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.MultiMeasureUnitCollection;
import com.kingdee.eas.basedata.master.material.MultiMeasureUnitFactory;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.basedata.mm.qm.utils.CSMF7Utils;
import com.kingdee.eas.basedata.mm.qm.utils.QtyMultiMeasureUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.st.common.client.STBillBaseEditUI;
import com.kingdee.eas.st.common.client.STBillBaseListUI;
import com.kingdee.eas.util.client.MsgBox;

public class MaterialColumnUtil {
	/**
	 * �༭����KDTable���ϵ�λ���������ã����ڿ��������о��������϶�̬�ı�
	 * 
	 * @param ui
	 *            editUI����
	 * @param prmtOrgUnit
	 *            ��ҵ����֯
	 * @param table
	 *            kdtable�������
	 * @param materialColumn
	 *            ����ID��
	 * @param qtyColumns
	 *            ����������
	 * @param unitColumn
	 *            ������λID��
	 * @param baseQtyColumns
	 *            ��������
	 * @param baseUnitColumn
	 *            ��������ID��λ
	 */
	public static void setup(STBillBaseEditUI ui,
			final KDBizPromptBox prmtOrgUnit, final KDTable table,
			final String materialColumn, final String qtyColumn,
			final String unitColumn, final String baseQtyColumn,
			final String baseUnitColumn) {
		setup(ui, prmtOrgUnit, table, materialColumn,
				new String[] { qtyColumn }, unitColumn,
				new String[] { baseQtyColumn }, baseUnitColumn);
	}

	public static void setup(STBillBaseEditUI ui,
			final KDBizPromptBox prmtOrgUnit, final KDTable table,
			final String materialColumn, final String[] qtyColumns,
			final String unitColumn, final String[] baseQtyColumns,
			final String baseUnitColumn) {
		// �ѻ���������λ�ͻ���������ס
		for (int i = 0; i < baseQtyColumns.length; i++) {
			if (baseQtyColumns[i] != null)
				table.getColumn(baseQtyColumns[i]).getStyleAttributes()
						.setLocked(true);
		}
		if (table.getColumn(baseUnitColumn) != null)
			table.getColumn(baseUnitColumn).getStyleAttributes()
					.setLocked(true);

		// ���ϼ���
		table.addKDTEditListener(new KDTEditAdapter() {

			public void editStarting(KDTEditEvent e) {
				int rowIndex = e.getRowIndex();
				int colIndex = e.getColIndex();

				if (colIndex == table.getColumnIndex(materialColumn)) {
					if (prmtOrgUnit.getData() == null) {
						MsgBox.showInfo("��¼��ҵ����֯");
						e.setCancel(true);
						return;
					}

					KDBizPromptBox prmtMaterial = (KDBizPromptBox) table
							.getColumn(materialColumn).getEditor()
							.getComponent();
					CSMF7Utils.setBizMaterialTreeF7(prmtMaterial, null,
							OrgType.Storage, (OrgUnitInfo) prmtOrgUnit
									.getData());

					ObjectValueRender kdtEntries_productNumber_OVR = new ObjectValueRender();
					kdtEntries_productNumber_OVR.setFormat(new BizDataFormat(
							"$number$"));
					table.getColumn(materialColumn).setRenderer(
							kdtEntries_productNumber_OVR);
				}
			}

			public void editStopped(KDTEditEvent e) {
				try {
					int rowIndex = e.getRowIndex();
					int colIndex = e.getColIndex();

					if (colIndex == table.getColumnIndex(materialColumn)) {
						// Я������������λ�����û�����������
						MaterialInfo material = (MaterialInfo) table.getCell(
								rowIndex, colIndex).getValue();
						if (material != null) {
							MeasureUnitInfo baseUnit = null;

							EntityViewInfo ev2 = new EntityViewInfo();
							FilterInfo filter2 = new FilterInfo();
							SelectorItemCollection sc2 = new SelectorItemCollection();
							filter2.getFilterItems().add(
									new FilterItemInfo("material.id", material
											.getId().toString(),
											CompareType.EQUALS));
							filter2.getFilterItems().add(
									new FilterItemInfo("isBasicUnit",
											Boolean.TRUE, CompareType.EQUALS));
							sc2.add("*");
							sc2.add("measureUnit.*");
							ev2.setFilter(filter2);
							ev2.setSelector(sc2);
							MultiMeasureUnitCollection col2 = MultiMeasureUnitFactory
									.getRemoteInstance()
									.getMultiMeasureUnitCollection(ev2);

							if (col2.size() > 0) {
								baseUnit = col2.get(0).getMeasureUnit();
							}

							if (baseUnit != null && baseUnitColumn != null) {
								table.getCell(rowIndex, baseUnitColumn)
										.setValue(baseUnit);

								KDTableUtils.setBigDecimalFieldsPrecision(
										QtyMultiMeasureUtils.qtyPrecision(
												material,
												(MeasureUnitInfo) baseUnit),
										table, rowIndex, baseQtyColumns);
							}

							// Я��������λ��������������
							Object oldUnit = null;
							Object newUnit = null;
							Object materialID = UIRuleUtil.getProperty(
									(IObjectValue) table.getCell(rowIndex,
											colIndex).getValue(), "id");
							EntityViewInfo ev = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							SelectorItemCollection sc = new SelectorItemCollection();
							filter.getFilterItems().add(
									new FilterItemInfo("material.id",
											materialID, CompareType.EQUALS));
							filter.getFilterItems().add(
									new FilterItemInfo("isStatUnit",
											Boolean.TRUE, CompareType.EQUALS));
							sc.add("*");
							sc.add("measureUnit.*");
							ev.setFilter(filter);
							ev.setSelector(sc);
							MultiMeasureUnitCollection col = MultiMeasureUnitFactory
									.getRemoteInstance()
									.getMultiMeasureUnitCollection(ev);
							if (col.size() > 0) {
								oldUnit = table.getCell(rowIndex, unitColumn)
										.getValue();
								newUnit = col.get(0).getMeasureUnit();
								table.getCell(rowIndex, unitColumn).setValue(
										newUnit);

								KDTableUtils.setBigDecimalFieldsPrecision(col
										.get(0).getQtyPrecision(), table,
										rowIndex, qtyColumns);
								for (int i = 0; i < qtyColumns.length; i++) {
									if (qtyColumns[i] != null) {
										((KDFormattedTextField) table
												.getColumn(qtyColumns[i])
												.getEditor().getComponent())
												.setMinimumValue(new BigDecimal(
														0));
									}
								}
							}

							// ��������
							Object o = e.getValue();
							if (o instanceof MaterialInfo && oldUnit != null
									&& newUnit != null) {
								MaterialInfo materialInfo = (MaterialInfo) o;
								MeasureUnitInfo oldMeasureUnitInfo = (MeasureUnitInfo) oldUnit;
								MeasureUnitInfo newMeasureUnitInfo = (MeasureUnitInfo) newUnit;

								// ���þ���
								int qtyPrecision = QtyMultiMeasureUtils
										.qtyPrecision(materialInfo,
												newMeasureUnitInfo);
								KDTableUtils.setBigDecimalFieldsPrecision(
										qtyPrecision, table, rowIndex,
										qtyColumns);

								for (int i = 0; i < qtyColumns.length; i++) {
									if (qtyColumns[i] != null) {
										((KDFormattedTextField) table
												.getColumn(qtyColumns[i])
												.getEditor().getComponent())
												.setMinimumValue(new BigDecimal(
														0));

										// ���»���
										if (table.getRow(rowIndex).getCell(
												qtyColumns[i]).getValue() != null) {
											BigDecimal oldQty = (BigDecimal) table
													.getRow(rowIndex).getCell(
															qtyColumns[i])
													.getValue();
											BigDecimal newQty = QtyMultiMeasureUtils
													.calculateQty(materialInfo,
															oldMeasureUnitInfo,
															newMeasureUnitInfo,
															oldQty,
															qtyPrecision);
											if (newQty != null) {
												table.getRow(rowIndex).getCell(
														qtyColumns[i])
														.setValue(newQty);

												Object measureUnit = table
														.getRow(rowIndex)
														.getCell(baseUnitColumn)
														.getValue();
												if (measureUnit != null) {
													BigDecimal baseQty = QtyMultiMeasureUtils
															.toBaseQty(
																	(MaterialInfo) material,
																	(MeasureUnitInfo) measureUnit,
																	newQty);
													table
															.getRow(rowIndex)
															.getCell(
																	baseQtyColumns[i])
															.setValue(baseQty);
												}

											} else {
												table.getRow(rowIndex).getCell(
														qtyColumns[i])
														.setValue(null);
												table.getRow(rowIndex).getCell(
														baseQtyColumns[i])
														.setValue(null);
											}
										}
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// ������λ����
		table.addKDTEditListener(new KDTEditAdapter() {

			public void editStarting(KDTEditEvent e) {
				int rowIndex = e.getRowIndex();
				int colIndex = e.getColIndex();

				if (colIndex == table.getColumnIndex(unitColumn)) {
					Object m = table.getRow(rowIndex).getCell(materialColumn)
							.getValue();
					if (m != null) {
						EntityViewInfo ev = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						SelectorItemCollection sc = new SelectorItemCollection();
						filter.getFilterItems().add(
								new FilterItemInfo("material.id",
										((MaterialInfo) m).getId().toString(),
										CompareType.EQUALS));
						sc.add("*");
						sc.add("measureUnit.*");
						ev.setFilter(filter);
						ev.setSelector(sc);
						try {
							Set unitIDs = new HashSet();
							unitIDs.add("xiaoqi");
							MultiMeasureUnitCollection col = MultiMeasureUnitFactory
									.getRemoteInstance()
									.getMultiMeasureUnitCollection(ev);
							if (col.size() > 0) {
								for (int i = 0; i < col.size(); i++) {
									unitIDs.add(col.get(i).getMeasureUnit()
											.getId().toString());
								}
							}

							KDBizPromptBox f7 = (KDBizPromptBox) table
									.getColumn(unitColumn).getEditor()
									.getComponent();
							if (f7.getEntityViewInfo() == null) {
								EntityViewInfo ev2 = new EntityViewInfo();
								ev2.setFilter(new FilterInfo());
								f7.setEntityViewInfo(ev2);
							}
							f7.getEntityViewInfo().getFilter().getFilterItems()
									.clear();
							f7.getEntityViewInfo().getFilter().getFilterItems()
									.add(
											new FilterItemInfo("id", unitIDs,
													CompareType.INCLUDE));
							f7.setEntityViewInfo(f7.getEntityViewInfo());

						} catch (BOSException ex) {
							ex.printStackTrace();
						}
					} else {
						MsgBox.showInfo("������������");
					}

					return;
				}

				int i = isClickQtyColumn(table, colIndex, qtyColumns);
				if (i >= 0) {
					if (table.getRow(rowIndex).getCell(unitColumn).getValue() == null) {
						MsgBox.showInfo("�������������λ");
					}
				}
			}

			public void editStopped(KDTEditEvent e) {
				try {
					int rowIndex = e.getRowIndex();
					int colIndex = e.getColIndex();

					if (colIndex == table.getColumnIndex(unitColumn)) {
						Object o = table.getRow(rowIndex).getCell(
								materialColumn).getValue();

						if (e.getOldValue().equals(e.getValue())) {
							return;
						}

						if (o instanceof MaterialInfo & e.getOldValue() != null) {
							MaterialInfo materialInfo = (MaterialInfo) o;
							MeasureUnitInfo oldMeasureUnitInfo = (MeasureUnitInfo) e
									.getOldValue();
							MeasureUnitInfo newMeasureUnitInfo = (MeasureUnitInfo) e
									.getValue();

							// ���þ���
							int qtyPrecision = QtyMultiMeasureUtils
									.qtyPrecision(materialInfo,
											newMeasureUnitInfo);
							KDTableUtils.setBigDecimalFieldsPrecision(
									qtyPrecision, table, rowIndex, qtyColumns);
							for (int i = 0; i < qtyColumns.length; i++) {
								if (qtyColumns[i] != null) {
									((KDFormattedTextField) table.getColumn(
											qtyColumns[i]).getEditor()
											.getComponent())
											.setMinimumValue(new BigDecimal(0));

									// ���»���
									if (table.getRow(rowIndex).getCell(
											qtyColumns[i]).getValue() != null) {
										BigDecimal oldQty = (BigDecimal) table
												.getRow(rowIndex).getCell(
														qtyColumns[i])
												.getValue();
										BigDecimal newQty = QtyMultiMeasureUtils
												.calculateQty(materialInfo,
														oldMeasureUnitInfo,
														newMeasureUnitInfo,
														oldQty, qtyPrecision);
										table.getRow(rowIndex).getCell(
												qtyColumns[i]).setValue(newQty);
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// ��������
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				int rowIndex = e.getRowIndex();
				int colIndex = e.getColIndex();

				try {
					int i = isClickQtyColumn(table, colIndex, qtyColumns);
					if (i >= 0) {
						Object o = table.getCell(rowIndex, colIndex).getValue();
						if (o != null) {
							BigDecimal qty = (BigDecimal) o;
							Object material = table.getRow(rowIndex).getCell(
									materialColumn).getValue();
							Object measureUnit = table.getRow(rowIndex)
									.getCell(unitColumn).getValue();
							if (material != null && measureUnit != null) {
								BigDecimal baseQty = QtyMultiMeasureUtils
										.toBaseQty((MaterialInfo) material,
												(MeasureUnitInfo) measureUnit,
												qty);
								table.getRow(rowIndex).getCell(
										baseQtyColumns[i]).setValue(baseQty);
							}
						} else {
							table.getRow(rowIndex).getCell(baseQtyColumns[i])
									.setValue(null);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		if (ui.getPrecisionManager() == null)
			ui.setPrecisionManager(new PrecisionManager(ui));

		ui.getPrecisionManager().addItem(table, materialColumn, qtyColumns,
				unitColumn, baseQtyColumns, baseUnitColumn);

	}

	/**
	 * ���²�KDTable���ϵ�λ���������ã����ڿ��������о��������϶�̬�ı�
	 * 
	 * @param ui
	 *            listui����
	 * @param table
	 *            kdtable�������
	 * @param materialColumn
	 *            ����ID��
	 * @param qtyColumns
	 *            ������
	 * @param unitColumn
	 *            ������λID��
	 * @param baseQtyColumns
	 *            ��������
	 * @param baseUnitColumn
	 *            ��������ID��λ
	 */
	public static void setup(STBillBaseListUI ui, final KDTable table,
			final String materialColumn, final String[] qtyColumns,
			final String unitColumn, final String[] baseQtyColumns,
			final String baseUnitColumn) {
		if (ui.getPrecisionManager() == null)
			ui.setPrecisionManager(new PrecisionManager(ui));

		ui.getPrecisionManager().addItem(table, materialColumn, qtyColumns,
				unitColumn, baseQtyColumns, baseUnitColumn);
	}

	private static int isClickQtyColumn(KDTable table, int colIndex,
			String[] qtyColumns) {
		for (int i = 0; i < qtyColumns.length; i++)
			if (colIndex == table.getColumnIndex(qtyColumns[i]))
				return i;

		return -1;
	}
}
