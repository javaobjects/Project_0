package com.kingdee.eas.st.common.MillerUtils;

import java.awt.Component;
import java.lang.reflect.Field;

import com.kingdee.bos.appframework.databinding.ComponentProperty;
import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.util.client.MsgBox;

public class FilterDependencyManager {
	public static void addDependency(final CoreUIObject ui, final String field,
			final String dependedField) {
		DataBinder dataBinder = null;
		try {
			Field f = CoreUIObject.class.getDeclaredField("dataBinder");
			f.setAccessible(true);
			dataBinder = (DataBinder) f.get(ui);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object[] objs = getUI(dataBinder, field);
		final Component component = (Component) objs[0];
		final String outerFields = (String) objs[1];
		final String innerFields = (String) objs[2];

		objs = getUI(dataBinder, dependedField);
		final Component component2 = (Component) objs[0];
		final String dependedOuterFields = (String) objs[1];
		final String dependedInnerFields = (String) objs[2];

		if (component instanceof KDBizPromptBox
				&& component2 instanceof KDBizPromptBox) {
			final KDBizPromptBox prmtField = (KDBizPromptBox) component;
			final KDBizPromptBox prmtDependedField = (KDBizPromptBox) component2;

			prmtField.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (prmtDependedField.getData() == null) {
						String depentName = ((KDLabelContainer) prmtDependedField
								.getParent()).getBoundLabelText();
						MsgBox.showInfo("请先输入" + depentName);
						e.setCanceled(true);
					}
				}
			});

			prmtDependedField.addDataChangeListener(new DataChangeListener() {
				public void dataChanged(DataChangeEvent event) {
					Object oldValue = event.getOldValue();
					Object newValue = event.getNewValue();
					if (!STQMUtils.isDiffrent(oldValue, newValue))
						return;

					if (!"VIEW".equals(ui.getOprtState())) {
						prmtField.setValue(null);
					}

					setupFilter(prmtField, prmtDependedField.getData(),
							outerFields, dependedOuterFields);
				}
			});

			setupFilter(prmtField, prmtDependedField.getData(), outerFields,
					dependedOuterFields);
		}

		if (component instanceof KDTable
				&& component2 instanceof KDBizPromptBox) {
			final KDTable table = (KDTable) component;
			final KDBizPromptBox prmtDependedField = (KDBizPromptBox) component2;

			ComponentProperty compProp = dataBinder.getDataComponentMap()
					.getComponentProperty(innerFields);
			final String headerName = compProp.getPropertyName();

			table.addKDTEditListener(new KDTEditAdapter() {
				public void editStarting(KDTEditEvent e) {
					int colIndex = e.getColIndex();

					if (colIndex == table.getColumnIndex(headerName)) {

						Object dependedValue = prmtDependedField.getData();

						if (dependedValue == null) {
							String depentName = ((KDLabelContainer) prmtDependedField
									.getParent()).getBoundLabelText();
							MsgBox.showInfo("请先输入" + depentName);
							e.setCancel(true);
							return;
						}

						KDBizPromptBox prmtField = (KDBizPromptBox) table
								.getColumn(headerName).getEditor()
								.getComponent();
						setupFilter(prmtField, dependedValue, outerFields,
								dependedOuterFields);
					}

				}
			});

			prmtDependedField.addDataChangeListener(new DataChangeListener() {
				public void dataChanged(DataChangeEvent event) {
					Object oldValue = event.getOldValue();
					Object newValue = event.getNewValue();
					if (!STQMUtils.isDiffrent(oldValue, newValue))
						return;

					if (!"VIEW".equals(ui.getOprtState())) {
						for (int i = 0; i < table.getRowCount(); i++) {
							table.getCell(i, headerName).setValue(null);
						}
					}
				}
			});
		}
	}

	public static void setupFilter(KDBizPromptBox prmtField,
			Object dependedValue, String outerFields, String dependedOuterFields) {
		if (prmtField.getEntityViewInfo() == null) {
			prmtField.setEntityViewInfo(new EntityViewInfo());
		}

		EntityViewInfo ev = prmtField.getEntityViewInfo();

		if (ev.getFilter() == null) {
			ev.setFilter(new FilterInfo());
		}

		FilterInfo filter = ev.getFilter();

		FilterItemCollection filterCol = filter.getFilterItems();

		boolean tag = true;
		for (int i = 0; i < filterCol.size(); i++) {
			FilterItemInfo fi = filterCol.get(i);
			if (fi.getPropertyName().equals(outerFields)) {
				fi.setCompareValue(((IObjectValue) dependedValue)
						.get(dependedOuterFields));
				tag = false;
				break;
			}
		}

		if (tag && dependedValue != null) {
			filterCol.add(new FilterItemInfo(outerFields,
					((IObjectValue) dependedValue).get(dependedOuterFields),
					CompareType.EQUALS));
		}

		prmtField.setEntityViewInfo(prmtField.getEntityViewInfo());
	}

	public static Object[] getUI(DataBinder dataBinder, String field) {
		String innerField = field;
		int pos = field.length();
		Component component = null;
		String outerField = null;
		while (component == null && innerField.lastIndexOf(".") > 0) {
			pos = innerField.lastIndexOf(".");
			innerField = innerField.substring(0, pos);
			component = dataBinder.getComponetByField(innerField);
		}

		if (component != null)
			outerField = field.substring(pos + 1, field.length());

		return new Object[] { component, outerField, innerField };
	}
}
