/*
 * @(#)QMCompareSignUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.utils;

import java.util.ArrayList;

import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.eas.base.commonquery.CompareSignStringEnum;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

//单一实例
public class QMCompareSignClientUtils {

	// 上限
	private static KDComboBox upCompareComboBox = new KDComboBox();
	// 下限
	private static KDComboBox lowerCompareComboBox = new KDComboBox();
	// 下限
	private static KDTDefaultCellEditor lowerCellEditor = new KDTDefaultCellEditor(
			QMCompareSignClientUtils.getLowerCompareComboBox());
	// 上限
	private static KDTDefaultCellEditor upCellEditor = new KDTDefaultCellEditor(
			QMCompareSignClientUtils.getUpCompareComboBox());
	static {
		// 上限
		upCompareComboBox.addItems(getUpCompare().toArray());
		// 下限
		lowerCompareComboBox.addItems(getLowerCompare().toArray());

	}

	private QMCompareSignClientUtils() {

	}

	/**
	 * 
	 * 描述:设置Table上的上限
	 * 
	 * @param kdTable
	 * @param columnNames
	 * @author: colin_xu 创建时间:2007-6-15
	 *          <p>
	 */
	public static void setUpCompares(KDTable kdTable, String[] columnNames) {
		if (STQMUtils.isNull(kdTable) || STQMUtils.isNull(columnNames)) {
			return;
		}
		for (int i = 0; i < columnNames.length; i++) {
			kdTable.getColumn(columnNames[i]).setEditor(upCellEditor);
		}
	}

	public static void setUpCompares(KDComboBox upCompare) {
		Object oldUp = upCompare.getSelectedItem();
		upCompare.removeAllItems();
		upCompare.addItems(getUpCompare().toArray());
		upCompare.setSelectedItem(oldUp);
	}

	/**
	 * 
	 * 描述:设置Table上的下限
	 * 
	 * @param kdTable
	 * @param columnNames
	 * @author: colin_xu 创建时间:2007-6-15
	 *          <p>
	 */
	public static void setLowerCompares(KDTable kdTable, String[] columnNames) {
		if (STQMUtils.isNull(kdTable) || STQMUtils.isNull(columnNames)) {
			return;
		}
		for (int i = 0; i < columnNames.length; i++) {
			kdTable.getColumn(columnNames[i]).setEditor(lowerCellEditor);
		}
	}

	public static void setLowerCompares(KDComboBox lowCompare) {
		Object oldLow = lowCompare.getSelectedItem();
		lowCompare.removeAllItems();
		lowCompare.addItems(getLowerCompare().toArray());
		lowCompare.setSelectedItem(oldLow);
	}

	public static ArrayList getUpCompare() {

		ArrayList arrayList = new ArrayList();
		arrayList.add(CompareSignStringEnum.EQUALSSIGN);
		arrayList.add(CompareSignStringEnum.SMALLSIGN);
		arrayList.add(CompareSignStringEnum.SMALLEQUALSSIGN);
		arrayList.add(CompareSignStringEnum.NULLSIGN);

		return arrayList;
	}

	public static ArrayList getLowerCompare() {

		ArrayList arrayList = new ArrayList();
		arrayList.add(CompareSignStringEnum.EQUALSSIGN);
		arrayList.add(CompareSignStringEnum.LARGESIGN);
		arrayList.add(CompareSignStringEnum.LARGEEQUALSSIGN);
		arrayList.add(CompareSignStringEnum.NULLSIGN);

		return arrayList;
	}

	/**
	 * 
	 * 描述: 获取下限ComboBox
	 * 
	 * @return
	 * @author: colin_xu 创建时间:2007-6-15
	 *          <p>
	 */
	public static KDComboBox getLowerCompareComboBox() {
		return lowerCompareComboBox;
	}

	/**
	 * 
	 * 描述: 获取上限ComboBox
	 * 
	 * @return
	 * @author: colin_xu 创建时间:2007-6-15
	 *          <p>
	 */
	public static KDComboBox getUpCompareComboBox() {
		return upCompareComboBox;
	}

}
