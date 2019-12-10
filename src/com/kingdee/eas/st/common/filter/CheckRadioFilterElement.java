/*
 * @(#)CheckRadioFilterElement.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:
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
	 * ���������캯�� true or false
	 * 
	 * @param id
	 * @param component
	 * @author:paul ����ʱ�䣺2007-1-18
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
	 * ������
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
		} else {// ʹ��Key
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
	 * ������
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
	 * ���������ò���������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setParam(java.util.Map)
	 */
	public void setParam(Map map) {
		// super.setParam(map, component);
	}

	/**
	 * ����������Ԫ������-����
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
	 * ����:@return ���� component��
	 */
	public Component getComponent() {
		return component;
	}

	/**
	 * ����:����component��ֵ��
	 * 
	 * @param component
	 */
	public void setComponent(Component component) {
		this.component = component;
	}

	/**
	 * ����:@return ���� selectedFilterItem��
	 */
	public FilterItemInfo getSelectedFilterItem() {
		return selectedFilterItem;
	}

	/**
	 * ����:����selectedFilterItem��ֵ��
	 * 
	 * @param selectedFilterItem
	 */
	public void setSelectedFilterItem(FilterItemInfo selectedFilterItem) {
		this.selectedFilterItem = selectedFilterItem;
	}

	/**
	 * ����:@return ���� unselectedFilterItem��
	 */
	public FilterItemInfo getUnselectedFilterItem() {
		return unselectedFilterItem;
	}

	/**
	 * ����:����unselectedFilterItem��ֵ��
	 * 
	 * @param unselectedFilterItem
	 */
	public void setUnselectedFilterItem(FilterItemInfo unselectedFilterItem) {
		this.unselectedFilterItem = unselectedFilterItem;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addCustomerParam(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void addCustomerParam(CustomerParams cp) {
		super.addCustomerParam(cp, component);
	}

	/**
	 * ����������Ԫ������
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
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementData(com.kingdee.eas.base.commonquery.client.CustomerParams,
	 *      java.lang.String)
	 */
	protected void setElementData(CustomerParams cp, String key) {
		/* TODO �Զ����ɷ������ */

	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#verify(com.kingdee.eas.scm.common.verify.VerifyItem)
	 */
	protected boolean verify(VerifyItem verifyItem) {
		return true;
	}

	/**
	 * ������
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
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addCustomerParam2(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void addRptParams(RptParams cp) {
		super.addRptParams(cp, getId(), component);
	}

	/**
	 * ������
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
