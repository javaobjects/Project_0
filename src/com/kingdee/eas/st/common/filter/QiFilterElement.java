package com.kingdee.eas.st.common.filter;

import java.awt.Component;

import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxItem;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.query.util.CompareType;

public class QiFilterElement extends SingleFilterElement {

	public QiFilterElement(String id, Component component) {
		super(id, component);
	}

	public QiFilterElement(String id, Component component, Object blankValue) {
		super(id, component, blankValue);
	}

	public FilterInfo getFilterInfo() {
		FilterInfo fInfo = super.getFilterInfo();

		if (null != fInfo
				&& null != fInfo.getFilterItems().get(0).getCompareValue()
				&& "вт╪Л".equalsIgnoreCase(fInfo.getFilterItems().get(0)
						.getCompareValue().toString())) {
			fInfo.getFilterItems().get(0).setCompareValue(null);
			fInfo.getFilterItems().get(0).setCompareType(CompareType.EMPTY);
		}

		return fInfo;
	}

	protected String getNumber(Object obj) {
		String str = super.getNumber(obj);
		if (null == str) {
			if (obj instanceof KDComboBox) {
				Object o = ((KDComboBox) obj).getSelectedItem();
				if (null != o && "вт╪Л".equalsIgnoreCase(o.toString()))
					str = o.toString();
			}
		}

		return str;
	}
}
