package com.kingdee.eas.st.common.filter;

import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.metadata.entity.FilterInfo;

public class CheckBoxFilterElement extends SingleFilterElement {
	KDCheckBox component;
	boolean isFilterWhenNotSelect;

	/**
	 * 
	 * @author zhiwei_wang
	 * @date 2008-10-10
	 * @param id
	 * @param component
	 * @param isFilterWhenNotSelect
	 *            在checkbox没有选中时，是否参与过滤
	 */
	public CheckBoxFilterElement(String id, KDCheckBox component,
			boolean isFilterWhenNotSelect) {
		super(id, component);
		this.component = component;
		this.isFilterWhenNotSelect = isFilterWhenNotSelect;
	}

	public FilterInfo getFilterInfo() {
		if (component.isSelected()) {
			return super.getFilterInfo();
		} else {
			if (isFilterWhenNotSelect) {
				return super.getFilterInfo();
			} else {
				return null;
			}
		}
	}
}
