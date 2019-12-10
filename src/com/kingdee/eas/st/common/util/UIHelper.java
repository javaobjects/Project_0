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
			// tableName = "��¼";
			tableName = EASResource.getString(resource, "Entries");
		}
		IColumn column = null;
		ICell cell = null;
		int rows = table.getRowCount();
		if (isEntryNotNull && rows <= 0) {
			// MsgBox.showWarning(tableName + "����Ϊ��");
			MsgBox.showWarning(tableName
					+ EASResource.getString(resource, "IsNotNull"));
			SysUtil.abort();
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns.length; j++) {
				cell = table.getRow(i).getCell(columns[j]);
				if (cell != null && cell.getValue() == null) {
					// MsgBox.showWarning(ui, tableName + "��" + (i + 1) + "��" +
					// table.getHeadRow(0).getCell(columns[j]).getValue()
					// + "����Ϊ��");
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
	public static Color needColor = new Color(0xFC, 0xFB, 0xDF);// ���������ɫ

	/**
	 * У��kdtable��Ԫ���Ƿ����0
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param key
	 *            ����ĳһ��
	 */
	public static void verifyEntryGreaterZero(CoreUIObject ui,
			String tableName, KDTable kdtEntries, String key) {
		if (tableName == null) {
			// tableName = "��¼";
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
			// MsgBox.showWarning(ui, tableName+"��"+(j+1)+"��"+text+"�������0");
			// SysUtil.abort();
			// }
			if (value instanceof BigDecimal) {
				if (((BigDecimal) value).compareTo(new BigDecimal("0")) <= 0) {
					// MsgBox.showWarning(ui,
					// tableName+"��"+(j+1)+"��"+text+"�������0");
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
	 * У��kdtable��Ԫ���Ƿ����0
	 * 
	 * @param ui
	 * @param tableName
	 * @param kdtEntries
	 * @param keys
	 *            ��Ҫ������
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
	 * У���¼��A���ܴ�����B
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
			// tableName = "��¼";
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
				// String msg = tableName+"��"+(j+1)+"��"+aText+"���ܴ���"+bText ;
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
			// MsgBox.showWarning(ui,cName+"��Χ����["+min+","+max+"]֮�䣡");
			MsgBox.showWarning(ui, cName
					+ EASResource.getString(resource, "Range") + min + ","
					+ max + EASResource.getString(resource, "Between"));
			SysUtil.abort();
		}
	}

	/**
	 * У���¼����min,max֮��
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
			// tableName = "��¼";
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
					// String msg = tableName+"��"+(j+1)+"��"+aText+"����С��"+min ;
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
					// String msg = tableName+"��"+(j+1)+"��"+aText+"���ܴ���"+max ;
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
							// tableName+"��"+(j+1)+"��"+aText+"����С��"+min ;
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
							// tableName+"��"+(j+1)+"��"+aText+"���ܴ���"+max ;
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
						// String msg = tableName+"��"+(j+1)+"��"+aText+"����С��"+min
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
						// String msg = tableName+"��"+(j+1)+"��"+aText+"���ܴ���"+max
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
	// * �ж϶����Ƿ����0
	// */
	// private static void verifyGreaterZero(CoreUIObject ui,
	// KDFormattedTextField txt) {
	// BigDecimal value = txt.getBigDecimalValue();
	// String text = getCompLabelText(txt);
	// if (value == null ) {
	// MsgBox.showWarning(ui, text + "�������0");
	// SysUtil.abort();
	// }
	// if(value instanceof BigDecimal){
	// if(((BigDecimal)value).compareTo(new BigDecimal("0"))==0){
	// MsgBox.showWarning(ui, text + "�������0");
	// SysUtil.abort();
	// }
	// }
	// }

	// /**
	// * �������ж�A�Ƿ����B
	// * @param ui
	// * @param txtA
	// * @param txtB
	// * @author:wangweidong
	// * ����ʱ�䣺2007-5-8 <p>
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
	// String msg = aText + "���ܴ���" +bText ;
	// MsgBox.showWarning(ui, msg);
	// SysUtil.abort();
	// }
	// }

	/**
	 * �������ж�A�Ƿ����B
	 * 
	 * @param ui
	 * @param d1
	 * @param d2
	 * @author:wangweidong ����ʱ�䣺2007-5-8
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
				// String msg = aText + "��������" + bText;
				String msg = aText
						+ EASResource.getString(resource, "NotLater") + bText;
				MsgBox.showWarning(ui, msg);
				SysUtil.abort();
			}
		}
	}

	/**
	 * У���ı����Ƿ�Ϊ��
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
		else if (component instanceof KDTextArea)// �����kdtextarea�����Ǿ���jscrollpane�İ�װ
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
	 * У��f7�Ƿ�Ϊ��
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
	 *У�����ڿؼ��Ƿ�Ϊ��
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
	 * ������ķǿ���
	 * 
	 * �޸ģ���multiLangBox����KDComboBoxǰ�棬��ΪmultiLangBox�̳�KDComboBox
	 * 
	 * @param ui
	 */
	public static void verifyUIRequire(CoreUIObject ui) {
		UIHelper.verifyRequire(ui, ui);
	}

	/**
	 * ���ui�ڲ�panel��require��� TODO ��verifyRequire(CoreUIObject ui)�ϲ��ع�
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
	 * ������еı�������ڽ�������������Ϊ���� longbo 2010.6.11
	 * 
	 * @param ui
	 * @param table
	 */
	public static void verifyTableRequire(CoreUIObject ui, KDTable table) {
		verifyTableRequire(ui, null, table, false);
	}

	/**
	 * ������еı�������ڽ�������������Ϊ����
	 * 
	 * @param ui
	 * @param tableName
	 *            ��¼������
	 * @param table
	 *            ��
	 * @param isEntryNotNull
	 *            ��¼�Ƿ��¼
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
			// MsgBox.showWarning(tableName + "����Ϊ��");
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
						// MsgBox.showWarning(ui, tableName + "��" + (j + 1) +
						// "��"
						// + table.getHeadRow(0).getCell(i).getValue()
						// + "����Ϊ��");
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
	 * ������еı����������ɫ�Ƿ����ɫ�����
	 * 
	 * @param ui
	 * @param tableName
	 *            ��¼������
	 * @param table
	 *            ��
	 * @param fields
	 *            Ҫ�����ֶ�
	 * @param color
	 *            ����ɫ
	 * @author longbo
	 * @date 2010-12-23
	 */
	public static void verifyTableRequireByColor(CoreUIObject ui,
			String tableName, KDTable table, String[] fields, Color color) {
		if (ui == null || table == null) {
			return;
		}
		if (tableName == null) {
			// tableName = "��¼";
			tableName = EASResource.getString(resource, "Entries");
		}
		int rows = table.getRowCount();
		for (int i = 0, count = fields.length; i < count; i++) {
			for (int j = 0; j < rows; j++) {
				ICell cell = table.getCell(j, fields[i]);
				if (color.equals(cell.getStyleAttributes().getBackground())
						&& (cell == null || cell.getValue() == null)) {
					/*
					 * MsgBox.showWarning(ui, tableName + "��" + (j + 1) + "��" +
					 * table.getHeadRow(0).getCell(fields[i]).getValue() +
					 * "����Ϊ��");
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
	 * �޸�������EditUI�е�ȱ�ݴ��� ����.*ȡ������
	 * ���⿪���׶ζ�ʵ������getSelector()�Ĵ����޸Ĺ������������ɾ���ֶο��ܵ��µ���©
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
	 * ������ �ж�����A �Ƿ���� ����B;�����ڵ�ͷ���¼�������ֶεĻ�ϱȽ�;�Զ�ȡ�ֶλ��¼�е���������չʾ�ڵ�����Ϣ��
	 * �÷���8��������Ϊ1,3,3,1�����飬�ڶ��鴫������A�������鴫������B ���Ƚ��ֶ�Ϊ��ͷ���ڣ���
	 * KDDatePicker�ؼ�����,null,null,����ʽ���� ���Ƚ��ֶ�Ϊ��¼���ڣ��� null,��¼����,�����е�key,����ʽ����
	 * p.s. ��������forѭ���е��ã������ڰ˸����������к�i ��ֻ�Ƚϵ�ͷ��Ϣ��ֱ�ӵ������ط���
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
			// String msg = aText + "��������" + bText;
			String msg = aText + EASResource.getString(resource, "NotLater")
					+ bText;
			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	/**
	 * ��ø��������� ����1����ǰui���� ����2���������ϻ򵥾ݵ�id
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
	 * У��kdtable��Ԫ���Ƿ�Ǹ�
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param key
	 *            ����ĳһ��
	 */
	public static void verifyEntryNotLessThanZero(CoreUIObject ui,
			String tableName, KDTable kdtEntries, String key) {
		if (tableName == null) {
			// tableName = "��¼";
			tableName = EASResource.getString(resource, "Entries");
		}
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			IRow row = kdtEntries.getRow(j);
			BigDecimal value = (BigDecimal) row.getCell(key).getValue();
			String text = getHead(kdtEntries, key);

			if (value instanceof BigDecimal) {
				if (((BigDecimal) value).compareTo(new BigDecimal("0")) < 0) {
					// MsgBox.showWarning(ui,
					// tableName+"��"+(j+1)+"��"+text+"����Ǹ�");
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
	 * У��kdtable��Ԫ���Ƿ�Ǹ�
	 * 
	 * @param ui
	 * @param tableName
	 * @param kdtEntries
	 * @param keys
	 *            ��Ҫ������
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
	 * У���¼��A �Ƿ�С�� ��B
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
			// tableName = "��¼";
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
				// String msg = tableName+"��"+(j+1)+"��"+aText+"����С��"+bText ;
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
