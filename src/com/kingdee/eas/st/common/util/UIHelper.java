package com.kingdee.eas.st.common.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class UIHelper {
	static String resource = "com.kingdee.eas.st.common.HelperResource";

	public static void verifyTableRequire(CoreUIObject ui, String tableName,
			KDTable table, String[] columns, boolean isEntryNotNull) {
		if (ui == null || table == null) {
			return;
		}

		if (tableName == null) {
			// tableName = "分录";
			tableName = EASResource.getString(resource, "Entries");
		}
		IColumn column = null;
		ICell cell = null;
		int rows = table.getRowCount();
		if (isEntryNotNull && rows <= 0) {
			// MsgBox.showWarning(tableName + "不能为空");
			MsgBox.showWarning(tableName
					+ EASResource.getString(resource, "IsNotNull"));
			SysUtil.abort();
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns.length; j++) {
				cell = table.getRow(i).getCell(columns[j]);
				if (cell != null && cell.getValue() == null) {
					// MsgBox.showWarning(ui, tableName + "第" + (i + 1) + "行" +
					// table.getHeadRow(0).getCell(columns[j]).getValue()
					// + "不能为空");
					MsgBox.showWarning(ui, tableName
							+ EASResource.getString(resource, "First")
							+ (i + 1)
							+ EASResource.getString(resource, "Line")
							+ table.getHeadRow(0).getCell(columns[j])
									.getValue()
							+ EASResource.getString(resource, "IsNotNull"));
					SysUtil.abort();
				}
			}
		}
	}

	// private static String path = "com.kingdee.eas.fm.common.FMResource";
	// private static String CanNotBeNull = " " + EASResource.getString(path,
	// "CanNotBeNull")
	private static String CanNotBeNull = EASResource.getString(resource,
			"IsNotNull");
	public static Color needColor = new Color(0xFC, 0xFB, 0xDF);// 必填项的颜色

	/**
	 * 校验kdtable单元格是否大于0
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param key
	 *            具体某一列
	 */
	public static void verifyEntryGreaterZero(CoreUIObject ui,
			String tableName, KDTable kdtEntries, String key) {
		if (tableName == null) {
			// tableName = "分录";
			tableName = EASResource.getString(resource, "Entries");
		}
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			IRow row = kdtEntries.getRow(j);
			Object val = row.getCell(key).getValue();
			BigDecimal value;
			if (val instanceof Integer) {
				value = new BigDecimal((Integer) val);
			} else {
				value = (BigDecimal) val;
			}
			String text = getHead(kdtEntries, key);
			// if (value == null ) {
			// MsgBox.showWarning(ui, tableName+"第"+(j+1)+"行"+text+"必须大于0");
			// SysUtil.abort();
			// }
			if (value instanceof BigDecimal) {
				if (((BigDecimal) value).compareTo(new BigDecimal("0")) <= 0) {
					// MsgBox.showWarning(ui,
					// tableName+"第"+(j+1)+"行"+text+"必须大于0");
					MsgBox.showWarning(ui, tableName
							+ EASResource.getString(resource, "First")
							+ (j + 1) + EASResource.getString(resource, "Line")
							+ text
							+ EASResource.getString(resource, "MustGreater"));
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * 校验kdtable单元格是否大于0
	 * 
	 * @param ui
	 * @param tableName
	 * @param kdtEntries
	 * @param keys
	 *            需要检查的列
	 * @author longbo
	 * @date 2010-12-25
	 */
	public static void verifyEntryGreaterZero(CoreUIObject ui,
			String tableName, KDTable kdtEntries, String keys[]) {
		for (int i = 0, count = keys.length; i < count; i++) {
			String key = keys[i];
			verifyEntryGreaterZero(ui, tableName, kdtEntries, key);
		}
	}

	/**
	 * 校验分录列A不能大于列B
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param keyA
	 * @param keyB
	 * @author longbo
	 * @date 2010-12-15
	 */
	public static void verifyAGreaterThanB(CoreUIObject ui, String tableName,
			KDTable kdtEntries, String keyA, String keyB) {
		if (tableName == null) {
			// tableName = "分录";
			tableName = EASResource.getString(resource, "Entries");
		}
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			IRow row = kdtEntries.getRow(j);
			BigDecimal a = new BigDecimal(
					null != row.getCell(keyA).getValue() ? row.getCell(keyA)
							.getValue().toString() : "0.00");
			BigDecimal b = new BigDecimal(
					null != row.getCell(keyB).getValue() ? row.getCell(keyB)
							.getValue().toString() : "0.00");
			String aText = getHead(kdtEntries, keyA);
			String bText = getHead(kdtEntries, keyB);
			if (a.compareTo(b) > 0) {
				// String msg = tableName+"第"+(j+1)+"行"+aText+"不能大于"+bText ;
				String msg = tableName
						+ EASResource.getString(resource, "First") + (j + 1)
						+ EASResource.getString(resource, "Line") + aText
						+ EASResource.getString(resource, "NotBigger") + bText;
				MsgBox.showWarning(ui, msg);
				SysUtil.abort();
			}
		}

	}

	public static void verifyInRange(CoreUIObject ui, Component[] components,
			Comparable min, Comparable max) {
		for (int i = 0, count = components.length; i < count; i++) {
			Component component = components[i];
			verifyInRange(ui, component, min, max);
		}
	}

	public static void verifyInRange(CoreUIObject ui, Component component,
			Comparable min, Comparable max) {
		Comparable cO = null;

		String cName = getCompLabelText(component);

		if (component instanceof KDFormattedTextField) {
			KDFormattedTextField o = (KDFormattedTextField) component;
			cO = o.getBigDecimalValue();
		}

		if (null != cO && !(cO.compareTo(min) >= 0 && cO.compareTo(max) <= 0)) {
			// MsgBox.showWarning(ui,cName+"范围须在["+min+","+max+"]之间！");
			MsgBox.showWarning(ui, cName
					+ EASResource.getString(resource, "Range") + min + ","
					+ max + EASResource.getString(resource, "Between"));
			SysUtil.abort();
		}
	}

	/**
	 * 校验分录列在min,max之间
	 * 
	 * @param ui
	 * @param tableName
	 * @param kdtEntries
	 * @param keys
	 * @param min
	 * @param max
	 */
	public static void verifyEntryInRange(CoreUIObject ui, String tableName,
			KDTable kdtEntries, String keys[], Comparable min, Comparable max) {
		for (int i = 0, count = keys.length; i < count; i++) {
			String key = keys[i];
			verifyEntryInRange(ui, tableName, kdtEntries, key, min, max);
		}
	}

	public static void verifyEntryInRange(CoreUIObject ui, String tableName,
			KDTable kdtEntries, String key, Comparable min, Comparable max) {

		if (tableName == null) {
			// tableName = "分录";
			tableName = EASResource.getString(resource, "Entries");
		}
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			IRow row = kdtEntries.getRow(j);
			Comparable a = null != row.getCell(key).getValue() ? (Comparable) row
					.getCell(key).getValue()
					: BigDecimal.ZERO;
			String aText = getHead(kdtEntries, key);

			if (null != min) {
				if (a.compareTo(min) < 0) {
					// String msg = tableName+"第"+(j+1)+"行"+aText+"不能小于"+min ;
					String msg = tableName
							+ EASResource.getString(resource, "First")
							+ (j + 1) + EASResource.getString(resource, "Line")
							+ aText
							+ EASResource.getString(resource, "NotLess") + min;
					MsgBox.showWarning(ui, msg);
					SysUtil.abort();
				}
			}

			if (null != max) {
				if (a.compareTo(max) > 0) {
					// String msg = tableName+"第"+(j+1)+"行"+aText+"不能大于"+max ;
					String msg = tableName
							+ EASResource.getString(resource, "First")
							+ (j + 1) + EASResource.getString(resource, "Line")
							+ aText
							+ EASResource.getString(resource, "NotBigger")
							+ max;
					MsgBox.showWarning(ui, msg);
					SysUtil.abort();
				}
			}
		}
	}

	public static void verifyEntryInRange(CoreUIObject ui, String tableName,
			KDTable kdtEntries, String key, Comparable min, Comparable max,
			boolean checkNull) {

		if (tableName == null) {
			tableName = EASResource.getString(resource, "Entries");
		}
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			IRow row = kdtEntries.getRow(j);

			Object value = row.getCell(key).getValue();

			if (null == value) {
				if (checkNull) {
					Comparable a = null != row.getCell(key).getValue() ? (Comparable) row
							.getCell(key).getValue()
							: BigDecimal.ZERO;
					String aText = getHead(kdtEntries, key);

					if (null != min) {
						if (a.compareTo(min) < 0) {
							// String msg =
							// tableName+"第"+(j+1)+"行"+aText+"不能小于"+min ;
							String msg = tableName
									+ EASResource.getString(resource, "First")
									+ (j + 1)
									+ EASResource.getString(resource, "Line")
									+ aText
									+ EASResource
											.getString(resource, "NotLess")
									+ min;
							MsgBox.showWarning(ui, msg);
							SysUtil.abort();
						}
					}

					if (null != max) {
						if (a.compareTo(max) > 0) {
							// String msg =
							// tableName+"第"+(j+1)+"行"+aText+"不能大于"+max ;
							String msg = tableName
									+ EASResource.getString(resource, "First")
									+ (j + 1)
									+ EASResource.getString(resource, "Line")
									+ aText
									+ EASResource.getString(resource,
											"NotBigger") + max;
							MsgBox.showWarning(ui, msg);
							SysUtil.abort();
						}
					}
				}
			} else {
				Comparable a = null != row.getCell(key).getValue() ? (Comparable) row
						.getCell(key).getValue()
						: BigDecimal.ZERO;
				String aText = getHead(kdtEntries, key);

				if (null != min) {
					if (a.compareTo(min) < 0) {
						// String msg = tableName+"第"+(j+1)+"行"+aText+"不能小于"+min
						// ;
						String msg = tableName
								+ EASResource.getString(resource, "First")
								+ (j + 1)
								+ EASResource.getString(resource, "Line")
								+ aText
								+ EASResource.getString(resource, "NotLess")
								+ min;
						MsgBox.showWarning(ui, msg);
						SysUtil.abort();
					}
				}

				if (null != max) {
					if (a.compareTo(max) > 0) {
						// String msg = tableName+"第"+(j+1)+"行"+aText+"不能大于"+max
						// ;
						String msg = tableName
								+ EASResource.getString(resource, "First")
								+ (j + 1)
								+ EASResource.getString(resource, "Line")
								+ aText
								+ EASResource.getString(resource, "NotBigger")
								+ max;
						MsgBox.showWarning(ui, msg);
						SysUtil.abort();
					}
				}
			}
		}
	}

	private static String getHead(KDTable kdtEntries, String key) {
		return (String) kdtEntries.getHeadRow(0).getCell(key).getValue();
	}

	// /**
	// * 判断对象是否大于0
	// */
	// private static void verifyGreaterZero(CoreUIObject ui,
	// KDFormattedTextField txt) {
	// BigDecimal value = txt.getBigDecimalValue();
	// String text = getCompLabelText(txt);
	// if (value == null ) {
	// MsgBox.showWarning(ui, text + "必须大于0");
	// SysUtil.abort();
	// }
	// if(value instanceof BigDecimal){
	// if(((BigDecimal)value).compareTo(new BigDecimal("0"))==0){
	// MsgBox.showWarning(ui, text + "必须大于0");
	// SysUtil.abort();
	// }
	// }
	// }

	// /**
	// * 描述：判断A是否大于B
	// * @param ui
	// * @param txtA
	// * @param txtB
	// * @author:wangweidong
	// * 创建时间：2007-5-8 <p>
	// */
	// private static void verifyAGreaterThanB(CoreUIObject ui,
	// KDFormattedTextField txtA, KDFormattedTextField txtB) {
	// verifyEmpty(ui, txtA);
	// verifyEmpty(ui, txtB);
	// BigDecimal a = txtA.getBigDecimalValue();
	// BigDecimal b = txtB.getBigDecimalValue();
	// if (a.compareTo(b) < 1) {
	// txtA.requestFocus(true);
	// String aText = getCompLabelText(txtA);
	// String bText = getCompLabelText(txtB);
	// String msg = aText + "不能大于" +bText ;
	// MsgBox.showWarning(ui, msg);
	// SysUtil.abort();
	// }
	// }

	/**
	 * 描述：判断A是否大于B
	 * 
	 * @param ui
	 * @param d1
	 * @param d2
	 * @author:wangweidong 创建时间：2007-5-8
	 *                     <p>
	 */
	public static void verifyAGreatThanB(CoreUIObject ui, KDDatePicker d1,
			KDDatePicker d2) {

		if (d1.getValue() != null && d2.getValue() != null) {
			Timestamp a = d1.getTimestamp();
			Timestamp b = d2.getTimestamp();
			if (b.compareTo(a) < 0) {
				d1.requestFocus(true);
				String aText = getCompLabelText(d1);
				String bText = getCompLabelText(d2);
				// String msg = aText + "不能晚于" + bText;
				String msg = aText
						+ EASResource.getString(resource, "NotLater") + bText;
				MsgBox.showWarning(ui, msg);
				SysUtil.abort();
			}
		}
	}

	/**
	 * 校验文本框是否为空
	 */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath,
			KDTextField txtNumber, String msg) {
		String txt = txtNumber.getText();
		if (txt == null || txt.trim().equals("")) {
			txtNumber.requestFocus(true);
			MsgBox
					.showWarning(
							ui,
							((resourcePath == null || msg == null)) ? getMessage(txtNumber)
									: EASResource.getString(resourcePath, msg));
			SysUtil.abort();
		}
	}

	public static void verifyEmpty(CoreUIObject ui, KDTextField txtNumber) {
		verifyEmpty(ui, null, txtNumber, null);
	}

	public static void verifyEmpty(CoreUIObject ui, String resourcePath,
			JFormattedTextField txtField, String msg) {
		String txt = txtField.getText();
		if (txt == null || txt.trim().equals("")) {
			txtField.requestFocus(true);
			MsgBox
					.showWarning(
							ui,
							((resourcePath == null || msg == null)) ? getMessage(txtField)
									: EASResource.getString(resourcePath, msg));
			SysUtil.abort();
		}
	}

	public static void verifyEmpty(CoreUIObject ui,
			JFormattedTextField txtNumber) {
		verifyEmpty(ui, null, txtNumber, null);
	}

	/**
	 * @return
	 */
	public static String getMessage(Component component) {
		String text = getCompLabelText(component);
		return text + CanNotBeNull;
	}

	private static String getCompLabelText(Component component) {
		String text = "";
		if (component.getParent() instanceof KDLabelContainer) {
			text = ((KDLabelContainer) component.getParent())
					.getBoundLabelText();
		}
		// add by yangzk @ 2006-3-7
		else if (component instanceof KDTextArea)// 如果是kdtextarea并且是经过jscrollpane的包装
		{
			Container cont = component.getParent();
			if (cont != null) {
				Container cont2 = cont.getParent();
				if (cont2 instanceof KDScrollPane) {
					if (cont2.getParent() instanceof KDLabelContainer) {
						text = ((KDLabelContainer) cont2.getParent())
								.getBoundLabelText();
					}
				}
			}
		}
		return text;
	}

	/**
	 * 校验f7是否为空
	 */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath,
			KDBizPromptBox bizBox, String msg) {
		Object content = bizBox.getData();
		if (content == null) {
			bizBox.requestFocus(true);
			MsgBox.showWarning(ui,
					(resourcePath == null || msg == null) ? getMessage(bizBox)
							: EASResource.getString(resourcePath, msg));
			SysUtil.abort();
		}
	}

	public static void verifyEmpty(CoreUIObject ui, KDBizPromptBox bizBox) {
		verifyEmpty(ui, null, bizBox, null);
	}

	public static void verifyEmpty(CoreUIObject ui, KDComboBox comboBox) {
		verifyEmpty(ui, null, comboBox, null);
	}

	public static void verifyEmpty(CoreUIObject ui, String resourcePath,
			KDComboBox comboBox, String msg) {
		Object content = comboBox.getSelectedItem();
		if (content == null) {
			comboBox.requestFocus(true);
			MsgBox
					.showWarning(
							ui,
							(resourcePath == null || msg == null) ? getMessage(comboBox)
									: EASResource.getString(resourcePath, msg));
			SysUtil.abort();
		}
	}

	/**
	 *校验日期控件是否为空
	 */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath,
			KDDatePicker datePicker, String msg) {
		String content = datePicker.getText();
		if (content == null || content.equals("")) {
			datePicker.requestFocus(true);
			MsgBox
					.showWarning(
							ui,
							(resourcePath == null || msg == null) ? getMessage(datePicker)
									: EASResource.getString(resourcePath, msg));
			SysUtil.abort();
		}
	}

	public static void verifyEmpty(CoreUIObject ui, KDDatePicker datePicker) {
		verifyEmpty(ui, null, datePicker, null);
	}

	/**
	 * 检查界面的非空项
	 * 
	 * 修改：把multiLangBox放在KDComboBox前面，因为multiLangBox继承KDComboBox
	 * 
	 * @param ui
	 */
	public static void verifyUIRequire(CoreUIObject ui) {
		UIHelper.verifyRequire(ui, ui);
	}

	/**
	 * 检查ui内部panel的require检查 TODO 和verifyRequire(CoreUIObject ui)合并重构
	 * 
	 * @param ui
	 */
	public static void verifyRequire(CoreUIObject ui, JComponent panel) {
		Component[] comps = panel.getComponents();
		for (int i = 0; i < comps.length; i++) {
			Component comp = comps[i];
			if (comp instanceof KDLabelContainer) {
				KDLabelContainer ct = (KDLabelContainer) comp;
				JComponent editor = ct.getBoundEditor();
				if (editor instanceof KDTextField) {
					KDTextField txtEditor = (KDTextField) editor;
					if (txtEditor.isRequired()) {
						UIHelper.verifyEmpty(ui, txtEditor);
					}
				} else if (editor instanceof KDBizMultiLangBox) {
					KDBizMultiLangBox txtEditor = (KDBizMultiLangBox) editor;
					if (txtEditor.isRequired()) {
						UIHelper.verifyEmpty(ui, txtEditor);
					}
				} else if (editor instanceof KDComboBox) {
					KDComboBox txtEditor = (KDComboBox) editor;
					if (txtEditor.isRequired()) {
						UIHelper.verifyEmpty(ui, txtEditor);
					}
				} else if (editor instanceof KDDatePicker) {
					KDDatePicker txtEditor = (KDDatePicker) editor;
					if (txtEditor.isRequired()) {
						UIHelper.verifyEmpty(ui, txtEditor);
					}
				} else if (editor instanceof KDBizPromptBox) {
					KDBizPromptBox txtEditor = (KDBizPromptBox) editor;
					if (txtEditor.isRequired()) {
						UIHelper.verifyEmpty(ui, txtEditor);
					}
				} else if (editor instanceof KDFormattedTextField) {
					KDFormattedTextField txtEditor = (KDFormattedTextField) editor;
					if (txtEditor.isRequired()) {
						UIHelper.verifyEmpty(ui, txtEditor);
					}
				}

			} else if (comp instanceof KDPanel || comp instanceof KDScrollPane
					|| comp instanceof KDTabbedPane
					|| comp instanceof KDSplitPane
					|| comp instanceof KDContainer) {
				UIHelper.verifyRequire(ui, (JComponent) comp);
			} else if (comp instanceof KDTable) {
				UIHelper.verifyTableRequire(ui, (KDTable) comp);
			}
		}

	}

	/**
	 * 检查表格中的必填项，须在界面或代码中设置为必填 longbo 2010.6.11
	 * 
	 * @param ui
	 * @param table
	 */
	public static void verifyTableRequire(CoreUIObject ui, KDTable table) {
		verifyTableRequire(ui, null, table, false);
	}

	/**
	 * 检查表格中的必填项，须在界面或代码中设置为必填
	 * 
	 * @param ui
	 * @param tableName
	 *            分录表名称
	 * @param table
	 *            表
	 * @param isEntryNotNull
	 *            分录是否必录
	 * @author longbo
	 * @date 2010-12-15
	 */
	public static void verifyTableRequire(CoreUIObject ui, String tableName,
			KDTable table, boolean isEntryNotNull) {
		if (ui == null || table == null) {
			return;
		}
		if (tableName == null) {
			tableName = EASResource.getString(resource, "Entries");
		}
		IColumn column = null;
		ICell cell = null;
		int rows = table.getRowCount();
		if (isEntryNotNull && rows <= 0) {
			// MsgBox.showWarning(tableName + "不能为空");
			MsgBox.showWarning(tableName
					+ EASResource.getString(resource, "IsNotNull"));
			SysUtil.abort();
		}
		int columns = table.getColumnCount();
		for (int i = 0; i < columns; i++) {
			column = table.getColumn(i);
			if (column != null && column.isRequired()) {
				for (int j = 0; j < rows; j++) {
					cell = table.getCell(j, i);
					if (cell == null || cell.getValue() == null) {
						// MsgBox.showWarning(ui, tableName + "第" + (j + 1) +
						// "行"
						// + table.getHeadRow(0).getCell(i).getValue()
						// + "不能为空");
						MsgBox.showWarning(ui, tableName
								+ EASResource.getString(resource, "First")
								+ (j + 1)
								+ EASResource.getString(resource, "Line")
								+ table.getHeadRow(0).getCell(i).getValue()
								+ EASResource.getString(resource, "IsNotNull"));
						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * 检查表格中的必填项，按背景色是否必填色来检查
	 * 
	 * @param ui
	 * @param tableName
	 *            分录表名称
	 * @param table
	 *            表
	 * @param fields
	 *            要检查的字段
	 * @param color
	 *            必填色
	 * @author longbo
	 * @date 2010-12-23
	 */
	public static void verifyTableRequireByColor(CoreUIObject ui,
			String tableName, KDTable table, String[] fields, Color color) {
		if (ui == null || table == null) {
			return;
		}
		if (tableName == null) {
			// tableName = "分录";
			tableName = EASResource.getString(resource, "Entries");
		}
		int rows = table.getRowCount();
		for (int i = 0, count = fields.length; i < count; i++) {
			for (int j = 0; j < rows; j++) {
				ICell cell = table.getCell(j, fields[i]);
				if (color.equals(cell.getStyleAttributes().getBackground())
						&& (cell == null || cell.getValue() == null)) {
					/*
					 * MsgBox.showWarning(ui, tableName + "第" + (j + 1) + "行" +
					 * table.getHeadRow(0).getCell(fields[i]).getValue() +
					 * "不能为空");
					 */
					MsgBox.showWarning(ui, tableName
							+ EASResource.getString(resource, "First")
							+ (j + 1) + EASResource.getString(resource, "Line")
							+ table.getHeadRow(0).getCell(fields[i]).getValue()
							+ EASResource.getString(resource, "IsNotNull"));
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * 修复抽象类EditUI中的缺陷代码 属性.*取用问题
	 * 避免开发阶段对实现类中getSelector()的大量修改工作，与后期增删改字段可能导致的疏漏
	 * 
	 * @date 2013-01-07
	 */
	public static SelectorItemCollection fixSIC(SelectorItemCollection sic) {
		int size = sic.size();
		List fixedElement = new ArrayList();
		for (int i = size - 1; i >= 0; i--) {
			SelectorItemInfo sii = sic.get(i);
			String pName = sii.getPropertyName();
			if (checkSiiName(pName)) {
				pName = pName.substring(0, pName.length() - 1);
				fixedElement.add(pName + "id");
				fixedElement.add(pName + "number");
				fixedElement.add(pName + "name");
				sic.remove(sii);
			}
		}
		for (int i = 0; i < fixedElement.size(); i++) {
			sic.add(new SelectorItemInfo((String) fixedElement.get(i)));
		}
		return sic;
	}

	private static boolean checkSiiName(String s) {

		return "*".equals(s.substring(s.length() - 1));
	}

	/**
	 * 描述： 判断日期A 是否大于 日期B;可用于单头与分录日期类字段的混合比较;自动取字段或分录列的中文名，展示在弹出信息窗
	 * 用法：8个参数分为1,3,3,1共四组，第二组传入日期A，第三组传入日期B 若比较字段为单头日期，用
	 * KDDatePicker控件对象,null,null,的形式传参 若比较字段为分录日期，用 null,分录对象,日期列的key,的形式传参
	 * p.s. 将语句放入for循环中调用，并给第八个参数传入行号i 若只比较单头信息，直接调用重载方法
	 * verifyAGreatThanB(CoreUIObject ui, KDDatePicker d1, KDDatePicker d2)
	 * 
	 * @date 2013-01-07
	 */
	public static void verifyAGreatThanB(CoreUIObject ui,
			KDDatePicker d1orNull, KDTable EntryNameAorNull, String KeyAorNull,
			KDDatePicker d2orNull, KDTable EntryNameBorNull, String KeyBorNull,
			int rowNumber) {

		Timestamp a = null;
		Timestamp b = null;
		String aText = "";
		String bText = "";

		if (EntryNameAorNull == null) {
			a = d1orNull.getTimestamp();
			aText = getCompLabelText(d1orNull);
		} else {
			if (EntryNameAorNull.getCell(rowNumber, KeyAorNull).getValue() instanceof java.sql.Date) {
				java.sql.Date d = (java.sql.Date) EntryNameAorNull.getCell(
						rowNumber, KeyAorNull).getValue();
				a = new Timestamp(d.getTime());
			} else if (EntryNameAorNull.getCell(rowNumber, KeyAorNull)
					.getValue() instanceof java.util.Date) {
				java.util.Date d = (java.util.Date) EntryNameAorNull.getCell(
						rowNumber, KeyAorNull).getValue();
				a = new Timestamp(d.getTime());
			}

			aText = getHead(EntryNameAorNull, KeyAorNull);
		}

		if (EntryNameBorNull == null) {
			b = d2orNull.getTimestamp();
			bText = getCompLabelText(d2orNull);
		} else {
			if (EntryNameBorNull.getCell(rowNumber, KeyBorNull).getValue() instanceof java.sql.Date) {
				java.sql.Date d = (java.sql.Date) EntryNameBorNull.getCell(
						rowNumber, KeyBorNull).getValue();
				b = new Timestamp(d.getTime());
			} else if (EntryNameBorNull.getCell(rowNumber, KeyBorNull)
					.getValue() instanceof java.util.Date) {
				java.util.Date d = (java.util.Date) EntryNameBorNull.getCell(
						rowNumber, KeyBorNull).getValue();
				b = new Timestamp(d.getTime());
			}

			bText = getHead(EntryNameBorNull, KeyBorNull);

		}

		if (b.compareTo(a) < 0) {

			if (d1orNull != null) {
				d1orNull.requestFocus(true);
			} else {
				((KDDatePicker) EntryNameAorNull.getColumn(KeyAorNull)
						.getEditor().getComponent()).requestFocus(true);
			}
			// String msg = aText + "不能晚于" + bText;
			String msg = aText + EASResource.getString(resource, "NotLater")
					+ bText;
			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	/**
	 * 获得附件管理器 参数1：当前ui对象 参数2：基础资料或单据的id
	 */
	public static void showAttachmentManager(Object owner, String id) {
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		if (info.getBoID() == null || info.getBoID().trim().equals("")) {
			if (id == null)
				return;
			info.setBoID(id);
		}
		info.setEdit(true);
		acm.showAttachmentListUIByBoID(owner, info);
	}

	/**
	 * 校验kdtable单元格是否非负
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param key
	 *            具体某一列
	 */
	public static void verifyEntryNotLessThanZero(CoreUIObject ui,
			String tableName, KDTable kdtEntries, String key) {
		if (tableName == null) {
			// tableName = "分录";
			tableName = EASResource.getString(resource, "Entries");
		}
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			IRow row = kdtEntries.getRow(j);
			BigDecimal value = (BigDecimal) row.getCell(key).getValue();
			String text = getHead(kdtEntries, key);

			if (value instanceof BigDecimal) {
				if (((BigDecimal) value).compareTo(new BigDecimal("0")) < 0) {
					// MsgBox.showWarning(ui,
					// tableName+"第"+(j+1)+"行"+text+"必须非负");
					MsgBox.showWarning(ui, tableName
							+ EASResource.getString(resource, "First")
							+ (j + 1) + EASResource.getString(resource, "Line")
							+ text
							+ EASResource.getString(resource, "NotNegative"));
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * 校验kdtable单元格是否非负
	 * 
	 * @param ui
	 * @param tableName
	 * @param kdtEntries
	 * @param keys
	 *            需要检查的列
	 * @date 2010-12-25
	 */
	public static void verifyEntryNotLessThanZero(CoreUIObject ui,
			String tableName, KDTable kdtEntries, String keys[]) {
		for (int i = 0, count = keys.length; i < count; i++) {
			String key = keys[i];
			verifyEntryNotLessThanZero(ui, tableName, kdtEntries, key);
		}
	}

	/**
	 * 校验分录列A 是否小于 列B
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param keyA
	 * @param keyB
	 * @author longbo
	 * @date 2010-12-15
	 */
	public static void verifyALessThanB(CoreUIObject ui, String tableName,
			KDTable kdtEntries, String keyA, String keyB) {
		if (tableName == null) {
			// tableName = "分录";
			tableName = EASResource.getString(resource, "Entries");
		}
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			IRow row = kdtEntries.getRow(j);
			BigDecimal a = new BigDecimal(
					null != row.getCell(keyA).getValue() ? row.getCell(keyA)
							.getValue().toString() : "0.00");
			BigDecimal b = new BigDecimal(
					null != row.getCell(keyB).getValue() ? row.getCell(keyB)
							.getValue().toString() : "0.00");
			String aText = getHead(kdtEntries, keyA);
			String bText = getHead(kdtEntries, keyB);
			if (a.compareTo(b) >= 0) {
				// String msg = tableName+"第"+(j+1)+"行"+aText+"必须小于"+bText ;
				String msg = tableName
						+ EASResource.getString(resource, "First") + (j + 1)
						+ EASResource.getString(resource, "Line") + aText
						+ EASResource.getString(resource, "MustLess") + bText;
				MsgBox.showWarning(ui, msg);
				SysUtil.abort();
			}
		}

	}
}
