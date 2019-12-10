/*
 * @(#)CheckRadioFilterElement.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.filter;

import java.awt.Component;
import java.util.Map;

import javax.swing.JToggleButton;

import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.scm.common.verify.VerifyItem;

/**
 * 描述:
 * 
 * @author paul date:2006-9-4
 *         <p>
 * @version EAS5.2
 */
public class CheckRadioFilterElement extends FilterElement {
	private Component component = null;
	private FilterItemInfo selectedFilterItem = null;
	private FilterItemInfo unselectedFilterItem = null;
	private Boolean defValue = null;

	public CheckRadioFilterElement(Component component,
			FilterItemInfo selFilterItem, FilterItemInfo unselFilterItem) {
		this.component = component;
		selectedFilterItem = selFilterItem;
		unselectedFilterItem = unselFilterItem;
	}

	/**
	 * 描述：构造函数 true or false
	 * 
	 * @param id
	 * @param component
	 * @author:paul 创建时间：2007-1-18
	 *              <p>
	 */
	public CheckRadioFilterElement(String id, Component component) {
		super(id);
		if (!(component instanceof JToggleButton)) {
			throw new UnsupportedOperationException(
					"The element must be extends JToggleButton, such as KDCheckBox or KDRadioButton.");
		}
		this.component = component;
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#getFilterInfo()
	 */
	public FilterInfo getFilterInfo() {
		FilterInfo filterInfo = new FilterInfo();
		Boolean isSelected;

		isSelected = isSelected();

		if (isSelected == null) {
			return null;
		}

		if (getId() == null) {
			if (isSelected.booleanValue() && selectedFilterItem != null) {
				filterInfo.getFilterItems().add(selectedFilterItem);
			} else if (!isSelected.booleanValue()
					&& unselectedFilterItem != null) {
				filterInfo.getFilterItems().add(unselectedFilterItem);
			}
		} else {// 使用Key
			filterInfo.getFilterItems()
					.add(
							new FilterItemInfo(getId(), isSelected,
									CompareType.EQUALS));
		}

		return filterInfo;
	}

	public Boolean isSelected() {
		if (component instanceof KDCheckBox) {
			return Boolean.valueOf(((KDCheckBox) component).isSelected());
		} else if (component instanceof KDCheckBox) {
			return Boolean.valueOf(((KDRadioButton) component).isSelected());
		}
		return null;
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#clear()
	 */
	public void clear() {
		if (defValue != null) {
			this.setData(component, defValue);
		}
	}

	/**
	 * 描述：设置参数，作废
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setParam(java.util.Map)
	 */
	public void setParam(Map map) {
		// super.setParam(map, component);
	}

	/**
	 * 描述：设置元素内容-作废
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setData(java.util.Map,
	 *      java.util.Map)
	 */
	protected void setData(Map paramMap, Map propMap) {
		if (paramMap.containsKey(getName(component))) {
			super.setData(component, paramMap.get(getName(component)));
		}
	}

	/**
	 * 描述:@return 返回 component。
	 */
	public Component getComponent() {
		return component;
	}

	/**
	 * 描述:设置component的值。
	 * 
	 * @param component
	 */
	public void setComponent(Component component) {
		this.component = component;
	}

	/**
	 * 描述:@return 返回 selectedFilterItem。
	 */
	public FilterItemInfo getSelectedFilterItem() {
		return selectedFilterItem;
	}

	/**
	 * 描述:设置selectedFilterItem的值。
	 * 
	 * @param selectedFilterItem
	 */
	public void setSelectedFilterItem(FilterItemInfo selectedFilterItem) {
		this.selectedFilterItem = selectedFilterItem;
	}

	/**
	 * 描述:@return 返回 unselectedFilterItem。
	 */
	public FilterItemInfo getUnselectedFilterItem() {
		return unselectedFilterItem;
	}

	/**
	 * 描述:设置unselectedFilterItem的值。
	 * 
	 * @param unselectedFilterItem
	 */
	public void setUnselectedFilterItem(FilterItemInfo unselectedFilterItem) {
		this.unselectedFilterItem = unselectedFilterItem;
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addCustomerParam(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void addCustomerParam(CustomerParams cp) {
		super.addCustomerParam(cp, component);
	}

	/**
	 * 描述：设置元素内容
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementValue(com.kingdee.eas.base.commonquery.client.CustomerParams,
	 *      java.util.Map)
	 */
	public void setElementValue(CustomerParams cp) {
		if (cp != null && cp.getCustomerParam(getName(component)) != null) {
			setNumber(component, cp.getCustomerParam(getName(component)));
		}
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementData(com.kingdee.eas.base.commonquery.client.CustomerParams,
	 *      java.lang.String)
	 */
	protected void setElementData(CustomerParams cp, String key) {
		/* TODO 自动生成方法存根 */

	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#verify(com.kingdee.eas.scm.common.verify.VerifyItem)
	 */
	protected boolean verify(VerifyItem verifyItem) {
		return true;
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setDefaultValue(java.lang.Object)
	 */
	public void setDefaultValue(Object value) {
		if (value instanceof Boolean) {
			defValue = (Boolean) value;
		}
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addCustomerParam2(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void addRptParams(RptParams cp) {
		super.addRptParams(cp, getId(), component);
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementValue2(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void setElementValue(RptParams cp) {
		if (cp != null && cp.getObject(getId()) != null) {
			setData(component, cp.getObject(getId()));
		}
	}

	protected void setData(EntityViewInfo entityViewInfo) {

	}

}
