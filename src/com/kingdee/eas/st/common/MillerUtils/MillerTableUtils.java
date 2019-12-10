package com.kingdee.eas.st.common.MillerUtils;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

public class MillerTableUtils {
	/**
	 * ���ñ��ĳһ�е������ֵ
	 */
	public static void setTableColumnMaxValue(KDTable table, String columnName,
			Comparable maxValue, int valueType) {
		KDFormattedTextField colTxtField = new KDFormattedTextField();
		colTxtField.setDataType(valueType);
		colTxtField.setMaximumValue(maxValue);
		IColumn column = table.getColumn(columnName);
		column.setEditor(new KDTDefaultCellEditor(colTxtField));
	}

	/**
	 * ���ñ��ĳһ�е������ֵ
	 */
	public static void setTableColumnMaxValueWithPrecision(KDTable table,
			String columnName, Comparable maxValue, int precision, int valueType) {
		KDFormattedTextField colTxtField = new KDFormattedTextField();
		colTxtField.setDataType(valueType);
		colTxtField.setMaximumValue(maxValue);
		colTxtField.setPrecision(precision);
		IColumn column = table.getColumn(columnName);
		column.setEditor(new KDTDefaultCellEditor(colTxtField));
	}

	/**
	 * ���ñ��ĳһ�е���С��ֵ
	 */
	public static void setTableColumnMinValueWithPrecision(KDTable table,
			String columnName, Comparable minValue, int precision, int valueType) {
		KDFormattedTextField colTxtField = new KDFormattedTextField();
		colTxtField.setDataType(valueType);
		colTxtField.setMinimumValue(minValue);
		colTxtField.setPrecision(precision);
		IColumn column = table.getColumn(columnName);
		column.setEditor(new KDTDefaultCellEditor(colTxtField));
	}

	/**
	 * ���ñ��ĳһ�е���С��ֵ
	 */
	public static void setTableColumnMinValue(KDTable table, String columnName,
			Comparable minValue, int valueType) {
		KDFormattedTextField colTxtField = new KDFormattedTextField();
		colTxtField.setDataType(valueType);
		colTxtField.setMinimumValue(minValue);
		IColumn column = table.getColumn(columnName);
		column.setEditor(new KDTDefaultCellEditor(colTxtField));
	}

	public static void setTableColumnMaxValue(KDTable table, String columnName,
			Comparable maxValue) {
		setTableColumnMaxValue(table, columnName, maxValue,
				KDFormattedTextField.BIGDECIMAL_TYPE);
	}

	public static void setTableColumnMinValue(KDTable table, String columnName,
			Comparable minValue) {
		setTableColumnMinValue(table, columnName, minValue,
				KDFormattedTextField.BIGDECIMAL_TYPE);
	}

	/**
	 * �����е�������볤��
	 */
	public static void setTableColumnMaxLength(KDTable table,
			String columnName, int maxLength) {
		KDTextField txtInput = new KDTextField();
		txtInput.setMaxLength(maxLength);
		table.getColumn(columnName).setEditor(
				new KDTDefaultCellEditor(txtInput));
	}
}
