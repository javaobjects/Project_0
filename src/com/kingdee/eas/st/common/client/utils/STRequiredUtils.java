package com.kingdee.eas.st.common.client.utils;

import java.math.BigDecimal;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.eas.basedata.mm.qm.utils.STClientUtils;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.util.StringUtils;

public class STRequiredUtils {

	public static STException checkRequiredItem(KDLabelContainer[] container) {

		STException exc = null;

		for (int i = 0, count = container.length; i < count; i++) {

			JComponent input = container[i].getBoundEditor();

			// 文本
			if ((input instanceof KDTextField)
					|| (input instanceof JFormattedTextField)) {
				exc = checkRequiredItem((JTextField) input, container[i]);
				if (STUtils.isNotNull(exc)) {
					return exc;
				}
			}

			// 多语言文本
			if (input instanceof KDBizMultiLangBox) {
				exc = checkRequiredItem((KDBizMultiLangBox) input, container[i]);
				if (STUtils.isNotNull(exc)) {
					return exc;
				}
			}

			// 日期
			if (input instanceof KDDatePicker) {
				exc = checkRequiredItem((KDDatePicker) input, container[i]);
				if (STUtils.isNotNull(exc)) {
					return exc;
				}
			}

			// F7
			if (input instanceof KDBizPromptBox) {
				exc = checkRequiredItem((KDBizPromptBox) input, container[i]);
				if (STUtils.isNotNull(exc)) {
					return exc;
				}
			}

			// 下拉框
			if (input instanceof KDComboBox) {
				exc = checkRequiredItem((KDComboBox) input, container[i]);
				if (STUtils.isNotNull(exc)) {
					return exc;
				}
			}

		}

		return exc;
	}

	public static STException checkRequiredItem(JTextField input,
			KDLabelContainer title) {

		STException exc = null;

		if (StringUtils.isEmpty(input.getText())) {
			input.requestFocus();
			exc = new STException(STException.NULL_TEXT_FIELD,
					new String[] { title.getBoundLabelText() });
		}

		return exc;
	}

	public static STException checkRequiredItem(KDBizMultiLangBox input,
			KDLabelContainer title) {

		STException exc = null;

		if (StringUtils.isEmpty(STClientUtils.getBizMultiLangBoxValue(input))) {
			input.requestFocus();
			exc = new STException(STException.NULL_MULTITEXT_FIELD,
					new String[] { title.getBoundLabelText() });
		}

		return exc;
	}

	public static STException checkRequiredItem(KDDatePicker input,
			KDLabelContainer title) {

		STException exc = null;

		if (STUtils.isNull(input.getValue())) {
			input.requestFocus();
			exc = new STException(STException.NULL_DATE_FIELD,
					new String[] { title.getBoundLabelText() });
		}

		return exc;
	}

	public static STException checkRequiredItem(KDBizPromptBox input,
			KDLabelContainer title) {

		STException exc = null;

		if (STUtils.isNull(input.getValue())) {
			input.requestFocus();
			exc = new STException(STException.NULL_F7_FIELD,
					new String[] { title.getBoundLabelText() });
		}

		return exc;
	}

	public static STException checkRequiredItem(KDComboBox input,
			KDLabelContainer title) {

		STException exc = null;

		if (STUtils.isNull(input.getSelectedItem())) {
			input.requestFocus();
			exc = new STException(STException.NULL_COMBO_FIELD,
					new String[] { title.getBoundLabelText() });
		}

		return exc;
	}

	public static STException checkEntryRequiredItem(KDTable kdtEntries,
			String[] checkColumnNames) throws Exception {

		return checkEntryRequiredItem(kdtEntries, "分录", checkColumnNames);
	}

	public static STException checkEntryRequiredItem(KDTable table,
			String entryName, String[] checkColumnNames) throws Exception {

		STException exc = null;

		// 计算列号
		int[] columnIndex = new int[checkColumnNames.length];
		for (int j = 0, count = checkColumnNames.length; j < count; j++) {
			columnIndex[j] = table.getColumnIndex(checkColumnNames[j]);
		}

		// 检查分录必填项
		for (int i = 0, size = table.getRowCount(); i < size; i++) {
			IRow row = table.getRow(i);
			for (int j = 0, count = checkColumnNames.length; j < count; j++) {
				Object o = row.getCell(checkColumnNames[j]).getValue();
				boolean isThrowable = false;
				if (STUtils.isNull(o)) {
					isThrowable = true;
				} else {
					if (StringUtils.isEmpty(o.toString())) {
						isThrowable = true;
					}
				}

				if (isThrowable) {
					table.getEditManager().editCellAt(i, columnIndex[j]);
					return new STException(STException.NULL_ENTRY_FIELD,
							new Object[] {
									"[" + entryName + "] ",
									new Integer(i + 1),
									table.getHead().getRow(0).getCell(
											columnIndex[j]).getValue() });
				}
			}

		}

		return exc;
	}

	// 针对行进行判断
	public static STException checkEntryRequiredItem(KDTable kdtEntries,
			String entryName, String[] checkColumnNames, int rowIndex)
			throws Exception {

		STException exc = null;

		// 计算列号
		int[] columnIndex = new int[checkColumnNames.length];
		for (int j = 0, count = checkColumnNames.length; j < count; j++) {
			columnIndex[j] = kdtEntries.getColumnIndex(checkColumnNames[j]);
		}

		// 检查分录必填项
		IRow row = kdtEntries.getRow(rowIndex);
		for (int j = 0, count = checkColumnNames.length; j < count; j++) {
			Object o = row.getCell(checkColumnNames[j]).getValue();
			boolean isThrowable = false;
			if (STUtils.isNull(o)) {
				isThrowable = true;
			} else {
				if (StringUtils.isEmpty(o.toString())) {
					isThrowable = true;
				}
			}

			if (isThrowable) {
				kdtEntries.getEditManager()
						.editCellAt(rowIndex, columnIndex[j]);
				throw new STException(STException.NULL_ENTRY_FIELD,
						new Object[] {
								"[" + entryName + "] ",
								new Integer(rowIndex + 1),
								kdtEntries.getHead().getRow(0).getCell(
										columnIndex[j]).getValue() });
			}
		}

		return exc;
	}

	public static STException checkEntryIsNull(KDTable table) {
		STException exc = null;
		if (table.getRowCount() < 1) {
			exc = new STException(STException.NULL_DATE_FIELD,
					new String[] { "分录" });
		}
		return exc;
	}

	/**
	 * 按顺序添加分录
	 * 
	 * @author jiwei_xiao
	 * @param table
	 * @return STException
	 */
	public static STException checkEntryIsNull(KDTable[] table) {
		STException exc = null;
		for (int i = 0; i < table.length; i++) {
			if (table[i].getRowCount() < 1) {
				exc = new STException(STException.NULL_DATE_FIELD,
						new String[] { "第" + (i + 1) + "个分录" });
				return exc;
			}
		}
		return exc;
	}

	/**
	 * 控件须为KDFormattedTextField
	 * 
	 * @param input
	 * @param title
	 * @param compare
	 *            (-1.<0;0.=0;1.>0)
	 * @return
	 */
	public static STException checkNumberItem(KDLabelContainer container[],
			int compare) {
		STException exc = null;
		int i = 0;
		for (int count = container.length; i < count; i++) {
			javax.swing.JComponent input = container[i].getBoundEditor();
			if (input instanceof KDFormattedTextField) {
				exc = checkRequiredItem((KDFormattedTextField) input,
						container[i]);
				if (STUtils.isNotNull(exc))
					return exc;
				exc = checkNumberItem((KDFormattedTextField) input,
						container[i], compare);
				if (STUtils.isNotNull(exc))
					return exc;
			}
		}

		return exc;
	}

	/**
	 * 
	 * @param input
	 * @param title
	 * @param compare
	 *            (-1.<0;0.=0;1.>0)
	 * @return
	 */
	protected static STException checkNumberItem(JTextField input,
			KDLabelContainer title, int compare) {
		STException exc = null;
		if (input instanceof KDFormattedTextField) {
			KDFormattedTextField fInput = (KDFormattedTextField) input;
			if (KDFormattedTextField.BIGDECIMAL_TYPE == fInput.getDataType()
					|| KDFormattedTextField.DECIMAL == fInput.getDataType()
					|| KDFormattedTextField.DOUBLE_TYPE == fInput.getDataType()
					|| KDFormattedTextField.FLOAT_TYPE == fInput.getDataType()
					|| KDFormattedTextField.INTEGER_TYPE == fInput
							.getDataType()
					|| KDFormattedTextField.LONG_TYPE == fInput.getDataType()
					|| KDFormattedTextField.SHORT_TYPE == fInput.getDataType()) {
				if (fInput.getBigDecimalValue().compareTo(SysConstant.BIGZERO) < compare) {
					fInput.requestFocus();
					exc = new STException(STException.NUMBER_COMPARE,
							new String[] {
									title.getBoundLabelText(),
									compare == -1 ? "小于" : compare == 0 ? "等于"
											: "大于" });
				}
			}
		}
		return exc;
	}
}
