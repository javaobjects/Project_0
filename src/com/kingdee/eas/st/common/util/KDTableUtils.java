package com.kingdee.eas.st.common.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.metadata.query.PropertyUnitCollection;
import com.kingdee.bos.metadata.query.QueryFieldInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.ConstDataType;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.scm.common.client.helper.FormattedEditorFactory;
import com.kingdee.eas.scm.common.util.PrecisionUtil;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.jdbc.rowset.IRowSet;

public class KDTableUtils extends
		com.kingdee.eas.basedata.mm.qm.client.KDTableUtils {

	public static int LOAD_TYPE_APPEND = 1; // 数据加载方式－追加，不会清除原始的数据
	public static int LOAD_TYPE_RELOAD = 2; // 数据加载方式－重新加载，清除原始的数据
	private final static BigDecimal MAXVALUE = new BigDecimal(999999999);
	private final static BigDecimal NAVVALUE = new BigDecimal(-999999999);
	private final static BigDecimal MINVALUE = new BigDecimal(0.00000001);
	// 表格无尾零格式
	public static final String strTakeOutZeroFormat = "%{0.##########}f";

	/**
	 * 把IRowSet中的记录集，显示到kdtable中
	 * 
	 * @param tableColumns
	 *            二维字符串数组 例如：new String[][]{ {"number", "MESSAGE_NO", "电文序列号"},
	 *            {"name", "MESSAGE_CODE", "电文号"}} s[0][0] table的英文列名 s[0][1]
	 *            第三方数据库字段名（IRowSet中的字段名） s[0][2] table的中文列名
	 * @param rs
	 *            数据集
	 * @param renderMap
	 *            table中数据展示的转换类，通常用于枚举型数据的展示；如果无需转换，这里传入null
	 *            map中的key是参数table的列名，value是实现IValueReader接口的具体实现类
	 * @param loadType
	 *            数据加载方式，参见LOAD_TYPE_APPEND和LOAD_TYPE_RELOAD
	 */
	public static void loadDataToTable(KDTable table, String[][] tableColumns,
			IRowSet rs, int loadType, Map renderMap) {
		if (table == null || tableColumns == null || rs == null) {
			return;
		}
		if (loadType == LOAD_TYPE_RELOAD) {
			table.removeRows(false);
		}
		try {
			while (rs.next()) {
				IRow row = table.addRow();
				int rowIndex = row.getRowIndex();
				for (int cIndex = 0, cSize = tableColumns.length; cIndex < cSize; cIndex++) {
					Object value = rs.getObject(tableColumns[cIndex][1]);
					if (value instanceof oracle.sql.TIMESTAMP) {
						value = ((oracle.sql.TIMESTAMP) value).dateValue();
					}
					if (renderMap != null
							&& renderMap.keySet().contains(
									tableColumns[cIndex][0])) {
						Object render = renderMap.get(tableColumns[cIndex][0]);
						if (render instanceof IValueRender) {
							value = ((IValueRender) render).render(value);
						}
					}
					setFieldValue(table, rowIndex, tableColumns[cIndex][0],
							value);
				}
			}
		} catch (SQLException e) {
			// 吃掉异常
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏分录上的小按钮
	 * 
	 * @param kDTable
	 * @author jiwei_xiao
	 * @date 2010-02-26
	 */
	public static void hideEntryButton(KDTable kdTable[]) {
		// 隐藏按钮
		DetailPanel detailPanel = null;
		Component c = null;
		for (int i = 0; i < kdTable.length; i++) {
			c = kdTable[i].getParent().getParent();
			if (c instanceof DetailPanel) {
				detailPanel = (DetailPanel) kdTable[i].getParent().getParent();
				Component[] components = detailPanel.getComponents();
				Component component = components[0];
				if (component instanceof KDPanel) {
					KDPanel kdPanel = (KDPanel) component;
					kdPanel.setVisible(false);
				}
			}
		}
	}

	/**
	 * 设置detailPanel标题宽度
	 * 
	 * @param kDTable
	 * @author hai_zhong
	 * @date 2012-11-21
	 */
	public static void setDetailPanelType(DetailPanel detailPanel, int width) {
		KDPanel controlPanel = (KDPanel) detailPanel.getComponents()[0];
		KDContainer kdConTitle = ((KDContainer) controlPanel.getComponent(0));
		controlPanel.add(kdConTitle,
				new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(0, 5,
						width, 19, 5));
	}

	/**
	 * 隐藏分录上的小按钮
	 * 
	 * @param kDTable
	 * @author jiwei_xiao
	 * @date 2010-02-26
	 */
	public static void hideEntryButtonWithOutPanel(KDTable kdTable[]) {
		// 隐藏按钮
		DetailPanel detailPanel = null;
		Component c = null;
		for (int i = 0; i < kdTable.length; i++) {
			c = kdTable[i].getParent().getParent();
			if (c instanceof DetailPanel) {
				detailPanel = (DetailPanel) kdTable[i].getParent().getParent();
				Component[] components = detailPanel.getComponents();
				Component component = components[0];
				if (component instanceof KDPanel) {
					KDPanel kdPanel = (KDPanel) component;
					Component[] pcom = kdPanel.getComponents();
					for (int j = 0; j < pcom.length; j++) {

						if (pcom[j] instanceof KDWorkButton) {
							KDWorkButton butt = (KDWorkButton) pcom[j];
							butt.setVisible(false);
						}

					}

				}
			}
		}
	}

	/**
	 * 移除空白列
	 * 
	 * @param kdtTable
	 *            分录
	 * @param keyColumn
	 *            是否为空行的标志列，同时不为空某一行才不为空
	 * @author jiwei_xiao
	 * @throws Exception
	 * @date 2010-03-07
	 */
	public static boolean removeBlankRow(KDTable kdtable, String keyColumn[])
			throws Exception {
		boolean isRemoved = false;
		for (int rowIndex = kdtable.getRowCount() - 1; rowIndex >= 0; rowIndex--) {
			if (isRowBlankOnTable(kdtable, rowIndex, keyColumn)) {
				removeLine(kdtable, rowIndex);
				isRemoved = true;
			}
		}
		return isRemoved;
	}

	/**
	 * 按行格式化指定列的为BigDecimal Editor以及精度
	 * 
	 * @param precision
	 * @param table
	 * @param rowIndex
	 * @param fieldNames
	 * @param isSetEditor
	 * @author jiwei_xiao
	 */
	public static void setBigDecimalFieldsPrecision(int precision,
			KDTable table, int rowIndex, String[] fieldNames,
			boolean isSetEditor) {
		if (fieldNames != null) {
			for (int i = 0, size = fieldNames.length; i < size; i++) {
				setBigDecimalFieldsPrecisionByCell(getCell(table, rowIndex,
						fieldNames[i]), precision, isSetEditor);
			}
		}
	}

	/**
	 * 
	 * 描述：设置单元格的显示格式.
	 * 
	 * @param cell
	 *            单元格
	 * @param precision
	 *            指定精度
	 * @param isSetEditor
	 *            是否需要设置Editor
	 * @author jiwei_xiao
	 *         <p>
	 */
	public static void setBigDecimalFieldsPrecisionByCell(ICell cell,
			int precision, boolean isSetEditor) {
		if (cell != null) {
			if (cell.getValue() instanceof BigDecimal) {
				cell.setValue(((BigDecimal) cell.getValue()).setScale(
						precision, BigDecimal.ROUND_HALF_UP));
			}
			if (isSetEditor) {
				// 编辑器
				// cell.setEditor(new KDTDefaultCellEditor(
				// getBigDecimalTextField(precision)));
				//cell.setEditor(FormattedEditorFactory.getBigDecimalCellEditor(
				// precision,true));

				KDFormattedTextField kdFtf = FormattedEditorFactory
						.getBigDecimalFormattedTextField(precision, true);
				// KDFormattedTextField kdFtf = new KDFormattedTextField();
				// kdFtf.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
				// kdFtf.setPrecision(precision);
				// kdFtf.setMaximumValue(SCMClientUtils.MAXVAL);
				// kdFtf.setMinimumValue(SCMClientUtils.MINVAL);
				// kdFtf.setHorizontalAlignment(KDBigDecimalTextField.RIGHT);
				// kdFtf.setSupportedEmpty(true);
				// kdFtf.setRemoveingZeroInDispaly(false);
				// kdFtf.setNegatived(true);
				KDTDefaultCellEditor kdCellEditor = new KDTDefaultCellEditor(
						kdFtf);

				cell.setEditor(kdCellEditor);
			}
			// 显示格式
			cell.getStyleAttributes().setNumberFormat(
					PrecisionUtil.getFormatString(precision));
		}
	}

	/**
	 * 描述：检查分录中关键字段值是否重复
	 * 
	 * @modify by kangyu_zou 修改value类型为Object 修改异常类型jiwei_xiao copy from
	 *         KDTableCheckTool
	 */
	public static STBillException isDuplicateInColumn(KDTable kdTable,
			String colName) {

		STBillException exc = null;

		Map map = new HashMap();

		for (int i = 0; i < kdTable.getRowCount(); i++) {

			Object value = kdTable.getRow(i).getCell(colName).getValue();
			if (value instanceof CoreBaseInfo) {
				value = ((CoreBaseInfo) value).getId();
			}

			if (map.keySet().contains(value) && STUtils.isNotNull(value)) {

				int colIndex = kdTable.getColumnIndex(colName);
				String colHead = String.valueOf(kdTable.getHead().getRow(0)
						.getCell(colIndex).getValue());
				colHead = "[" + colHead + "]";

				return new STBillException(STBillException.ENTRY_DUPLICATE,
						new Object[] { colHead, map.get(value),
								String.valueOf(i + 1) });

			} else {

				map.put(value, String.valueOf(i + 1));
			}
		}

		return exc;
	}

	/**
	 * 
	 * 描述: 设定KDTable列的最大和最小值范围
	 * 
	 * @param table
	 *            :传入的表
	 * @param columnNames
	 *            :列及长度的Map
	 * @param maxValue
	 *            :最大长度
	 * @author: colin_xu 创建时间:2007-6-20
	 *          <p>
	 */
	public static void setKDTableColumnsRangeValue(KDTable table,
			String[] columnNames, BigDecimal minValue, BigDecimal maxValue) {

		if (STQMUtils.isNull(table) || STQMUtils.isNull(columnNames)) {
			return;
		}

		// 迭代处理所有的列
		for (int i = 0; i < columnNames.length; i++) {

			IColumn column = getColumn(table, columnNames[i]);

			KDFormattedTextField colTxtField = null;
			if (column.getEditor().getComponent() instanceof KDFormattedTextField)
				colTxtField = (KDFormattedTextField) column.getEditor()
						.getComponent();
			else
				colTxtField = new KDFormattedTextField();

			colTxtField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
			if (STQMUtils.isNotNull(minValue)) {
				colTxtField.setMinimumValue(minValue);
				colTxtField.setMaximumValue(maxValue);
			} else {
				colTxtField.setMinimumValue(MINVALUE);
				colTxtField.setMaximumValue(MAXVALUE);
			}

			if (column != null) {
				// 编辑器
				column.setEditor(new KDTDefaultCellEditor(colTxtField));
			}
		}
	}

	/**
	 * 描述 设置表格中指定列显示格式
	 * 
	 * @param kdtEntries
	 *            分录
	 * @param strArray
	 *            分录的字符串数组
	 * @return
	 */
	public static void setTableColumnsFormat(KDTable kdtEntries,
			String[] strArray, String strFormat) {
		if (strArray == null || strArray.length == 0) {
			return;
		}
		for (int i = 0; i < strArray.length; i++) {
			if (kdtEntries.getColumn(strArray[i]) != null) {
				kdtEntries.getColumn(strArray[i]).getStyleAttributes()
						.setNumberFormat(strFormat);
			}
		}

	}

	/**
	 * 隐藏分录列
	 * 
	 * @param table
	 * @param columns
	 */
	public static void hideColumns(KDTable table, String columns[]) {

		hideColumns(table, columns, true);
	}

	/**
	 * 隐藏分录列
	 * 
	 * @param table
	 * @param columns
	 * @param hide
	 *            [bool]
	 */
	public static void hideColumns(KDTable table, String columns[], boolean hide) {

		for (int i = 0; i < columns.length; i++)
			table.getColumn(columns[i]).getStyleAttributes().setHided(hide);
	}

	/**
	 * 设置数值型字段自动靠右
	 * 
	 * @param table
	 * @param queryInfo
	 * @throws BOSException
	 */
	public static void setTableAlign(KDTable table, QueryInfo queryInfo)
			throws BOSException {
		if (table != null && queryInfo != null) {
			QueryFieldInfo queryFieldInfo = new QueryFieldInfo();
			PropertyUnitCollection propertyUnitCollection = queryInfo
					.getUnits();
			int size = propertyUnitCollection.size();
			for (int i = 0; i < size; i++) {
				if (!(propertyUnitCollection.get(i) instanceof QueryFieldInfo))

					continue;
				queryFieldInfo = (QueryFieldInfo) propertyUnitCollection.get(i);

				ConstDataType ct = queryFieldInfo.getReturnType();
				if (ct.equals(ConstDataType.INT)) {
					if (null != table.getColumn(queryFieldInfo.getName())) {
						table.getColumn(queryFieldInfo.getName())
								.getStyleAttributes().setHorizontalAlign(
										HorizontalAlignment.RIGHT);
					}
				}

				if (ct.equals(ConstDataType.NUMBER)) {
					if (null != table.getColumn(queryFieldInfo.getName())) {
						table.getColumn(queryFieldInfo.getName())
								.getStyleAttributes().setHorizontalAlign(
										HorizontalAlignment.RIGHT);
						table.getColumn(queryFieldInfo.getName())
								.getStyleAttributes().setNumberFormat(
										"%{#.##########}f");
					}
				}
			}

		}
	}

	/**
	 * 设置列的显示格式
	 * 
	 * @param table
	 * @param colNames
	 * @param precision
	 * @date 2008-1-3
	 * @author wangyb
	 */
	public static void setBigDecimalColumnPrecision(KDTable table,
			String[] colNames, int precision) {
		// KDTDefaultCellEditor editor = new
		// KDTDefaultCellEditor(getBigDecimalTextField(precision));
		KDTDefaultCellEditor editor = FormattedEditorFactory
				.getBigDecimalCellEditor(precision, true);
		String sFormat = PrecisionUtil.getFormatString(precision);
		for (int i = 0; i < colNames.length; i++) {
			IColumn column = table.getColumn(colNames[i]);
			if (column != null) {
				column.setEditor(editor);
				column.getStyleAttributes().setNumberFormat(sFormat);
				column.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
			}
		}
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
	public static void setBigDecimalFieldsPrecisionByColumn(IColumn column,
			int precision, BigDecimal maxValue, BigDecimal minValue) {
		if (maxValue == null) {
			maxValue = MAXVALUE;
		}
		if (minValue == null) {
			minValue = MINVALUE;
		}

		if (column != null) {
			// 编辑器
			// column.setEditor(new KDTDefaultCellEditor(
			// getBigDecimalTextField(precision,maxValue,minValue)));
			column.setEditor(new KDTDefaultCellEditor(getBigDecimalTextField(
					precision, maxValue)));
			// 显示格式
			column.getStyleAttributes().setNumberFormat(
					PrecisionUtil.getFormatString(precision));
		}
	}
}
