/*
 * @(#)SingleFilterEnum.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.filter;

import java.awt.Component;
import java.sql.Timestamp;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.scm.common.verify.VerifyItem;
import com.kingdee.eas.scm.common.verify.VerifyType;
import com.kingdee.eas.st.common.util.STUtils;

/**
 * ����:���ؼ�����Ԫ����
 * 
 * @author paul date:2006-8-30
 *         <p>
 * @version EAS5.2
 */
public class SingleFilterElement extends FilterElement {
	private Component component = null;
	private CompareType compareType = CompareType.EQUALS;
	private int dateType = NOTDATE;
	private Object defValue = null;
	/**
	 * �Ƿ�ʹ��id��ѯ��ֻ���F7�ؼ���Ч�����Ҫʹ��idƥ�䣬��Ҫ����Ϊtrue��Ĭ��F7�ؼ�ʹ��numberƥ��
	 */
	private boolean isUseId = false;

	public SingleFilterElement(String id, Component component) {
		super(id);
		this.component = component;
	}

	public SingleFilterElement(String id, KDBizPromptBox component,
			boolean isUseId) {
		this(id, component);
		this.isUseId = isUseId;
	}

	public SingleFilterElement(String id, Component component, Object blankValue) {
		super(id, blankValue);
		this.component = component;
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
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#getFilterInfo()
	 */
	public FilterInfo getFilterInfo() {
		FilterInfo filterInfo = null;
		if (!isUseId) {
			FilterItemInfo itemInfo = super.getFilterItemInfo(component,
					compareType);
			if (itemInfo != null) {
				filterInfo = new FilterInfo();
				if (dateType != NOTDATE) {
					itemInfo.setCompareValue(getTimestamp(Timestamp
							.valueOf((String) itemInfo.getCompareValue()),
							dateType));
				}
				filterInfo.getFilterItems().add(itemInfo);
			}
		} else {
			String data = null;
			Object info = ((KDBizPromptBox) component).getData();
			if (info instanceof DataBaseInfo) {
				data = ((DataBaseInfo) info).getId().toString();
			} else if (info instanceof CoreBillBaseInfo) {
				data = ((CoreBillBaseInfo) info).getId().toString();
			} else if (info instanceof CoreBillEntryBaseInfo) {
				data = ((CoreBillEntryBaseInfo) info).getId().toString();
			}

			if (data != null) {
				filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(
						new FilterItemInfo(getId(), data, compareType));
			}
		}

		return filterInfo;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#clear()
	 */
	public void clear() {
		super.clear(component);
		if (defValue != null) {
			this.setData(component, defValue);
		}
	}

	// /**
	// * ������
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.filter.FilterElement#setParam(java.util.Map)
	// */
	// public void setParam(Map map)
	// {
	// super.setParam(map, component);
	//        
	// }

	// /**
	// * ������
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.filter.FilterElement#setData(java.lang.Object,
	// com.kingdee.bos.metadata.query.util.CompareType)
	// */
	// private void setData(Object value, CompareType type)
	// {
	// super.setData(component, value);
	// }
	//
	// /**
	// * ������
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.filter.FilterElement#setData(java.util.Map,
	// java.util.Map)
	// */
	// protected void setData(Map paramMap, Map propMap)
	// {
	// if(paramMap.containsKey(getName(component))) {
	// CompareType type = (CompareType) propMap.get(getId());
	// if(type != null) {
	// setData(paramMap.get(getName(component)), type);
	// }
	// }
	//        
	// }

	/**
	 * ����:@return ���� compareType��
	 */
	public CompareType getCompareType() {
		return compareType;
	}

	/**
	 * ����:����compareType��ֵ��
	 * 
	 * @param compareType
	 */
	public void setCompareType(CompareType compareType) {
		this.compareType = compareType;
	}

	/**
	 * ����:����dateType��ֵ��
	 * 
	 * @param dateType
	 */
	public void setDateType(int dateType) {
		this.dateType = dateType;
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
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementData(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	protected void setElementData(CustomerParams cp, String key) {

	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#verify(com.kingdee.eas.scm.common.util.FilterElement.VerifyItem)
	 */
	protected boolean verify(VerifyItem verifyItem) {
		if (component instanceof KDPromptBox) {

			// try {
			// ((KDPromptBox)component).commitEdit();
			// } catch (ParseException e) {
			// ((KDPromptBox)component).setData(null);
			// }
		}
		if (verifyItem.getVerifyType() == VerifyType.REQUIRED_TYPE
				&& isNull(component)) {
			component.requestFocus();
			verifyItem.showError();
			return false;
		}
		return true;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setDefaultValue(java.lang.Object)
	 */
	public void setDefaultValue(Object value) {
		if (value != null) {
			defValue = value;
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
		if (component instanceof KDComboBox) {
			((KDComboBox) component).setSelectedIndex(0);
		}
		if (entityViewInfo.getFilter() == null)
			return;

		FilterItemCollection currentFilterItemCollection = entityViewInfo
				.getFilter().getFilterItems();
		for (int i = 0; i < currentFilterItemCollection.size(); i++) {
			FilterItemInfo filterItemInfo = currentFilterItemCollection.get(i);
			if (getId().equals(filterItemInfo.getPropertyName())) {
				if (CompareType.EQUALS.equals(filterItemInfo.getCompareType())
						|| CompareType.LIKE.equals(filterItemInfo
								.getCompareType())) {
					if (!isUseId) {
						setNumber(component, filterItemInfo.getCompareValue()
								.toString());
					} else {
						try {
							Object info = STUtils
									.getDynamicObject(null, filterItemInfo
											.getCompareValue().toString());
							((KDBizPromptBox) component).setValue(info);
						} catch (BOSException e) {
							// Do Nothing
						}
					}
				}
			}
		}
	}

}
