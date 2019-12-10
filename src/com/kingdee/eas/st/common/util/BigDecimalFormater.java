package com.kingdee.eas.st.common.util;

import java.math.BigDecimal;

import javax.swing.JTextField;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.eas.scm.common.util.PrecisionUtil;

public class BigDecimalFormater {

	public final static BigDecimal MAX_VALUE = new BigDecimal("999999999");
	public final static BigDecimal NAV_VALUE = new BigDecimal("-999999999");
	public final static BigDecimal MIN_VALUE = new BigDecimal("0.00000001");
	public final static BigDecimal ZERO_VALUE = new BigDecimal("0");

	/**
	 * @param table
	 * @param columns
	 * @param precision
	 *            精度
	 */
	public static void setPrecision(KDTable table, String[] columns,
			int precision) {
		setFormatAndRange(table, columns, precision, null, null);
	}

	/**
	 * 
	 * 描述：设置某列的所有行显示格式
	 * 
	 * @param column
	 * @param precision
	 * @param maxValue
	 *            最大输入值
	 * @param minValue
	 *            最小输入值
	 * @author:hai_zhong 创建时间：2006-1-4
	 *                   <p>
	 */
	public static void setFormatAndRange(KDTable table, String[] columns,
			int precision, BigDecimal minValue, BigDecimal maxValue) {

		if (minValue != null && maxValue != null) {
			if (minValue.compareTo(maxValue) > 0) {
				BigDecimal temp = maxValue;
				maxValue = minValue;
				minValue = temp;
			}
		}

		for (int i = 0; i < columns.length; i++) {
			try {
				IColumn column = table.getColumn(columns[i]);
				if (column != null) {
					// 编辑器
					column.setEditor(new KDTDefaultCellEditor(
							getBigDecimalTextField(precision, maxValue,
									minValue)));
					// 显示格式
					column.getStyleAttributes().setNumberFormat(
							PrecisionUtil.getFormatString(precision));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 描述：返回指定精度的KDFormattedTextField
	 * 
	 * @param precision
	 *            指定精度
	 * @return 格式化好的KDFormattedTextField
	 * @author:daij 创建时间：2006-1-4
	 *              <p>
	 */
	private static KDFormattedTextField getBigDecimalTextField(int precision,
			BigDecimal maxValue, BigDecimal minValue) {
		KDFormattedTextField colTxtField = new KDFormattedTextField();
		colTxtField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		colTxtField.setPrecision(precision);
		colTxtField.setHorizontalAlignment(JTextField.RIGHT);
		colTxtField.setSupportedEmpty(true);
		colTxtField.setRemoveingZeroInDispaly(false);

		if (minValue != null) {
			colTxtField.setMinimumValue(minValue);
		} else {
			colTxtField.setMinimumValue(NAV_VALUE);
		}

		if (maxValue != null) {
			colTxtField.setMaximumValue(maxValue);
		} else {
			colTxtField.setMaximumValue(MAX_VALUE);
		}

		return colTxtField;
	}
}
