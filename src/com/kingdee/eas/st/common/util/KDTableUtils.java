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

	public static int LOAD_TYPE_APPEND = 1; // ���ݼ��ط�ʽ��׷�ӣ��������ԭʼ������
	public static int LOAD_TYPE_RELOAD = 2; // ���ݼ��ط�ʽ�����¼��أ����ԭʼ������
	private final static BigDecimal MAXVALUE = new BigDecimal(999999999);
	private final static BigDecimal NAVVALUE = new BigDecimal(-999999999);
	private final static BigDecimal MINVALUE = new BigDecimal(0.00000001);
	// �����β���ʽ
	public static final String strTakeOutZeroFormat = "%{0.##########}f";

	/**
	 * ��IRowSet�еļ�¼������ʾ��kdtable��
	 * 
	 * @param tableColumns
	 *            ��ά�ַ������� ���磺new String[][]{ {"number", "MESSAGE_NO", "�������к�"},
	 *            {"name", "MESSAGE_CODE", "���ĺ�"}} s[0][0] table��Ӣ������ s[0][1]
	 *            ���������ݿ��ֶ�����IRowSet�е��ֶ����� s[0][2] table����������
	 * @param rs
	 *            ���ݼ�
	 * @param renderMap
	 *            table������չʾ��ת���࣬ͨ������ö�������ݵ�չʾ���������ת�������ﴫ��null
	 *            map�е�key�ǲ���table��������value��ʵ��IValueReader�ӿڵľ���ʵ����
	 * @param loadType
	 *            ���ݼ��ط�ʽ���μ�LOAD_TYPE_APPEND��LOAD_TYPE_RELOAD
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
			// �Ե��쳣
			e.printStackTrace();
		}
	}

	/**
	 * ���ط�¼�ϵ�С��ť
	 * 
	 * @param kDTable
	 * @author jiwei_xiao
	 * @date 2010-02-26
	 */
	public static void hideEntryButton(KDTable kdTable[]) {
		// ���ذ�ť
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
	 * ����detailPanel������
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
	 * ���ط�¼�ϵ�С��ť
	 * 
	 * @param kDTable
	 * @author jiwei_xiao
	 * @date 2010-02-26
	 */
	public static void hideEntryButtonWithOutPanel(KDTable kdTable[]) {
		// ���ذ�ť
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
	 * �Ƴ��հ���
	 * 
	 * @param kdtTable
	 *            ��¼
	 * @param keyColumn
	 *            �Ƿ�Ϊ���еı�־�У�ͬʱ��Ϊ��ĳһ�вŲ�Ϊ��
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
	 * ���и�ʽ��ָ���е�ΪBigDecimal Editor�Լ�����
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
	 * ���������õ�Ԫ�����ʾ��ʽ.
	 * 
	 * @param cell
	 *            ��Ԫ��
	 * @param precision
	 *            ָ������
	 * @param isSetEditor
	 *            �Ƿ���Ҫ����Editor
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
				// �༭��
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
			// ��ʾ��ʽ
			cell.getStyleAttributes().setNumberFormat(
					PrecisionUtil.getFormatString(precision));
		}
	}

	/**
	 * ����������¼�йؼ��ֶ�ֵ�Ƿ��ظ�
	 * 
	 * @modify by kangyu_zou �޸�value����ΪObject �޸��쳣����jiwei_xiao copy from
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
	 * ����: �趨KDTable�е�������Сֵ��Χ
	 * 
	 * @param table
	 *            :����ı�
	 * @param columnNames
	 *            :�м����ȵ�Map
	 * @param maxValue
	 *            :��󳤶�
	 * @author: colin_xu ����ʱ��:2007-6-20
	 *          <p>
	 */
	public static void setKDTableColumnsRangeValue(KDTable table,
			String[] columnNames, BigDecimal minValue, BigDecimal maxValue) {

		if (STQMUtils.isNull(table) || STQMUtils.isNull(columnNames)) {
			return;
		}

		// �����������е���
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
				// �༭��
				column.setEditor(new KDTDefaultCellEditor(colTxtField));
			}
		}
	}

	/**
	 * ���� ���ñ����ָ������ʾ��ʽ
	 * 
	 * @param kdtEntries
	 *            ��¼
	 * @param strArray
	 *            ��¼���ַ�������
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
	 * ���ط�¼��
	 * 
	 * @param table
	 * @param columns
	 */
	public static void hideColumns(KDTable table, String columns[]) {

		hideColumns(table, columns, true);
	}

	/**
	 * ���ط�¼��
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
	 * ������ֵ���ֶ��Զ�����
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
	 * �����е���ʾ��ʽ
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
	 * ����������ĳ�е���������ʾ��ʽ
	 * 
	 * @param column
	 * @param precision
	 * @param maxValue
	 *            �������ֵ
	 * @param minValue
	 *            ��С����ֵ
	 * @author:hai_zhong ����ʱ�䣺2006-1-4
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
			// �༭��
			// column.setEditor(new KDTDefaultCellEditor(
			// getBigDecimalTextField(precision,maxValue,minValue)));
			column.setEditor(new KDTDefaultCellEditor(getBigDecimalTextField(
					precision, maxValue)));
			// ��ʾ��ʽ
			column.getStyleAttributes().setNumberFormat(
					PrecisionUtil.getFormatString(precision));
		}
	}
}
