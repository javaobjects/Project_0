/*
 * @(#)RangeFilterEnum.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.filter;

import java.awt.Component;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.master.util.StringUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.scm.common.verify.VerifyItem;
import com.kingdee.eas.scm.common.verify.VerifyType;

/**
 * ����:��Χ����Ԫ����- FROM TO ��com.kingdee.eas.scm.common.filter�����ƹ�����
 * 
 * @author paul date:2006-8-30
 *         <p>
 * @version EAS5.2
 */
public class RangeFilterElement extends FilterElement {
	private Component fromComp = null;
	private Component toComp = null;
	private CompareType fromType = CompareType.GREATER_EQUALS;
	private CompareType toType = CompareType.LESS_EQUALS;

	private Object fromDefValue = null;
	private Object toDefValue = null;

	private String id1 = null;
	private String id2 = null;
	private String compareValue = null;

	/**
	 * ����:@return ���� fromType��
	 */
	public CompareType getFromType() {
		return fromType;
	}

	/**
	 * ����:����fromType��ֵ��
	 * 
	 * @param fromType
	 */
	public void setFromType(CompareType fromType) {
		this.fromType = fromType;
	}

	/**
	 * ����:@return ���� toType��
	 */
	public CompareType getToType() {
		return toType;
	}

	/**
	 * ����:����toType��ֵ��
	 * 
	 * @param toType
	 */
	public void setToType(CompareType toType) {
		this.toType = toType;
	}

	public RangeFilterElement(String id, Component from, Component to) {
		super(id);
		fromComp = from;
		toComp = to;
	}

	public RangeFilterElement(String id, Component from, Component to,
			Object blankValue) {
		super(id, blankValue);
		fromComp = from;
		toComp = to;
	}

	public RangeFilterElement(String id1, String id2, String compareValue,
			Component from, Component to) {
		super(id1);
		fromComp = from;
		toComp = to;
		this.id1 = id1;
		this.id2 = id2;
		this.compareValue = compareValue;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#getFilterInfo()
	 */
	public FilterInfo getFilterInfo() {
		if (compareValue == null) {
			return getFilterInfo1();
		} else {
			return getFilterInfo2();
		}
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#getFilterInfo()
	 */
	public FilterInfo getFilterInfo1() {
		FilterInfo filterInfo = new FilterInfo();
		FilterItemInfo item1 = null;
		FilterItemInfo item2 = null;

		item1 = super.getFilterItemInfo(fromComp, fromType);
		item2 = super.getFilterItemInfo(toComp, toType);

		if (item1 != null) {
			if (fromComp instanceof KDDatePicker) {
				if (!((KDDatePicker) fromComp).isTimeEnabled())// ����������������������ʽ���д���
					item1.setCompareValue(getTimestamp((Timestamp
							.valueOf((String) item1.getCompareValue())),
							DATESTART));
			}
			if (fromComp instanceof KDPromptBox
					&& StringUtil.isEmpty(((KDPromptBox) fromComp).getText())) {
				item1.setCompareValue(((KDPromptBox) fromComp).getText());
			}
			filterInfo.getFilterItems().add(item1);
		}
		if (item2 != null) {
			if (toComp instanceof KDDatePicker) {
				if (!((KDDatePicker) toComp).isTimeEnabled())// ����������������������ʽ���д���
					item2.setCompareValue(getTimestamp((Timestamp
							.valueOf((String) item2.getCompareValue())),
							DATEEND));
			}
			if (toComp instanceof KDPromptBox
					&& StringUtil.isEmpty(((KDPromptBox) toComp).getText())) {
				item2.setCompareValue(((KDPromptBox) toComp).getText());
			}
			filterInfo.getFilterItems().add(item2);
		}
		if (filterInfo.getFilterItems().size() > 1) {
			filterInfo.setMaskString("(#0 and #1)");
		}
		return filterInfo.getFilterItems().size() > 0 ? filterInfo : null;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#getFilterInfo()
	 */
	public FilterInfo getFilterInfo2() {
		FilterInfo filterInfo = new FilterInfo();
		FilterItemInfo item1 = null;
		FilterItemInfo item2 = null;
		FilterItemInfo item3 = null;
		FilterItemInfo item4 = null;

		setId(id1);
		item1 = super.getFilterItemInfo(fromComp, fromType);
		item2 = super.getFilterItemInfo(toComp, toType);
		// ��һ���ֶ�
		setId(id2);
		item3 = super.getFilterItemInfo(fromComp, fromType);
		item4 = super.getFilterItemInfo(toComp, toType);

		if (item1 != null) {
			if (fromComp instanceof KDDatePicker) {
				item1
						.setCompareValue(getTimestamp((Timestamp
								.valueOf((String) item1.getCompareValue())),
								DATESTART));
			}
			if (fromComp instanceof KDPromptBox
					&& StringUtil.isEmpty(((KDPromptBox) fromComp).getText())) {
				item1.setCompareValue(((KDPromptBox) fromComp).getText());
			}
			filterInfo.getFilterItems().add(item1);
		}
		if (item2 != null) {
			if (toComp instanceof KDDatePicker) {
				item2.setCompareValue(getTimestamp((Timestamp
						.valueOf((String) item2.getCompareValue())), DATEEND));
			}
			if (toComp instanceof KDPromptBox
					&& StringUtil.isEmpty(((KDPromptBox) toComp).getText())) {
				item2.setCompareValue(((KDPromptBox) toComp).getText());
			}
			filterInfo.getFilterItems().add(item2);
		}
		if (item3 != null) {
			if (fromComp instanceof KDDatePicker) {
				item3
						.setCompareValue(getTimestamp((Timestamp
								.valueOf((String) item3.getCompareValue())),
								DATESTART));
			}
			if (fromComp instanceof KDPromptBox
					&& StringUtil.isEmpty(((KDPromptBox) fromComp).getText())) {
				item3.setCompareValue(((KDPromptBox) fromComp).getText());
			}
			filterInfo.getFilterItems().add(item3);
		}
		if (item4 != null) {
			if (toComp instanceof KDDatePicker) {
				item4.setCompareValue(getTimestamp((Timestamp
						.valueOf((String) item4.getCompareValue())), DATEEND));
			}
			if (toComp instanceof KDPromptBox
					&& StringUtil.isEmpty(((KDPromptBox) toComp).getText())) {
				item4.setCompareValue(((KDPromptBox) toComp).getText());
			}
			filterInfo.getFilterItems().add(item4);
		}
		if (filterInfo.getFilterItems().size() == 2) {
			filterInfo.setMaskString("(#0 " + compareValue + " #1)");
		} else if (filterInfo.getFilterItems().size() == 4) {
			filterInfo.setMaskString("(#0 and #1) " + compareValue
					+ " (#2 and #3)");
		}
		return filterInfo.getFilterItems().size() > 0 ? filterInfo : null;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#clear()
	 */
	public void clear() {
		super.clear(fromComp);
		super.clear(toComp);
		if (fromDefValue != null) {
			this.setData(fromComp, fromDefValue);
		}
		if (toDefValue != null) {
			this.setData(toComp, toDefValue);
		}
	}

	// /**
	// * ���������ò���������
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.filter.FilterElement#setParam(java.util.Map)
	// */
	// public void setParam(Map map)
	// {
	// super.setParam(map, fromComp);
	// super.setParam(map, toComp);
	// }

	// /**
	// * ������
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.util.FilterElement#setData(java.lang.Object,
	// com.kingdee.bos.metadata.query.util.CompareType)
	// */
	// public void setData(Object value, CompareType type)
	// {
	// if(type.equals(CompareType.GREATER_EQUALS)) {
	// super.setData(fromComp, value);
	// }else if(type.equals(CompareType.LESS_EQUALS)) {
	// super.setData(toComp, value);
	// }
	//        
	// }

	// /**
	// * ����������Ԫ������ - ����
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.filter.FilterElement#setData(java.util.Map,
	// java.util.Map)
	// */
	// protected void setData(Map paramMap, Map propMap)
	// {
	// Object type = propMap.get(getId());
	// CompareType aType = null;
	// if(type instanceof List) {
	// for(int i=0; i<((List)type).size(); i++) {
	// aType = (CompareType) ((List)type).get(i);
	// if(aType.equals(fromType)) {
	// setData(fromComp, paramMap.get(getName(fromComp)));
	// }else if(aType.equals(toType)) {
	// setData(toComp, paramMap.get(getName(toComp)));
	// }
	// }
	// }else{
	// if(type != null) {
	// if(type.equals(fromType)) {
	// setData(fromComp, paramMap.get(getName(fromComp)));
	// }else if(type.equals(toType)) {
	// setData(toComp, paramMap.get(getName(toComp)));
	// }
	// }
	// }
	//        
	// }

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addCustomerParam(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void addCustomerParam(CustomerParams cp) {
		super.addCustomerParam(cp, fromComp);
		super.addCustomerParam(cp, toComp);
	}

	/**
	 * ����������Ԫ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementValue(com.kingdee.eas.base.commonquery.client.CustomerParams,
	 *      java.util.Map)
	 */
	public void setElementValue(CustomerParams cp) {
		// Object type = propMap.get(getId());
		// CompareType aType = null;
		//        
		// if(cp != null) {
		// if(type instanceof List) {
		// for(int i=0; i<((List)type).size(); i++) {
		// aType = (CompareType) ((List)type).get(i);
		// if(aType.equals(fromType) && cp.getCustomerParam(getName(fromComp))
		// != null) {
		// setNumber(fromComp, cp.getCustomerParam(getName(fromComp)));
		// }else if(aType.equals(toType) && cp.getCustomerParam(getName(toComp))
		// != null) {
		// setNumber(toComp, cp.getCustomerParam(getName(toComp)));
		// }
		// }
		// }else{
		// if(type != null) {
		// if(type.equals(fromType) && cp.getCustomerParam(getName(fromComp)) !=
		// null) {
		// setNumber(fromComp, cp.getCustomerParam(getName(fromComp)));
		// }else if(type.equals(toType) && cp.getCustomerParam(getName(toComp))
		// != null) {
		// setNumber(toComp, cp.getCustomerParam(getName(toComp)));
		// }
		// }
		// }
		// }
		if (cp != null) {
			setNumber(fromComp, cp.getCustomerParam(getName(fromComp)));
			setNumber(toComp, cp.getCustomerParam(getName(toComp)));
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
		// �ڿ�ʼУ��ǰ,�����ύ
		if (fromComp instanceof KDPromptBox) {

			try {
				((KDPromptBox) fromComp).commitEdit();
			} catch (ParseException e) {
				((KDPromptBox) fromComp).setData(null);
			}
		}
		if (toComp instanceof KDPromptBox) {

			try {
				((KDPromptBox) toComp).commitEdit();
			} catch (ParseException e) {
				((KDPromptBox) toComp).setData(null);
			}
		}
		// ��ʼУ��
		if (verifyItem.getVerifyType() == VerifyType.LESS_EQUAL_TYPE) {
			String dataStart = this.getNumber(fromComp);
			String dataEnd = this.getNumber(toComp);
			if (dataStart != null && dataEnd != null
					&& dataStart.compareTo(dataEnd) > 0) {
				fromComp.requestFocus();
				verifyItem.showError();
				return false;
			}
		} else if (verifyItem.getVerifyType() == VerifyType.REQUIRED_TYPE) {
			String dataStart = this.getNumber(fromComp);
			String dataEnd = this.getNumber(toComp);
			if (dataStart == null || dataEnd == null) {
				verifyItem.showError();
				if (dataStart == null) {
					fromComp.requestFocus();
				} else {
					toComp.requestFocus();
				}
				return false;
			}
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
		if (value instanceof Object[]) {
			Object[] values = (Object[]) value;
			if (values.length > 0) {
				fromDefValue = values[0];
			}
			if (values.length > 1) {
				toDefValue = values[1];
			}
		}
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementValue2(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void setElementValue(RptParams cp) {
		/* TODO �Զ����ɷ������ */

	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addRptParams(com.kingdee.eas.framework.report.util.RptParams)
	 */
	public void addRptParams(RptParams cp) {
		/* TODO �Զ����ɷ������ */

	}

	protected void setData(EntityViewInfo entityViewInfo) {
		if (fromComp instanceof KDComboBox) {
			((KDComboBox) fromComp).setSelectedIndex(0);
		}
		if (toComp instanceof KDComboBox) {
			((KDComboBox) toComp).setSelectedIndex(0);
		}

		if (entityViewInfo.getFilter() == null)
			return;

		// TODO �Զ����ɷ������
		FilterItemCollection currentFilterItemCollection = entityViewInfo
				.getFilter().getFilterItems();
		// params.setHm(entityViewInfo.getFilter().getFilterItems().t)
		for (int i = 0; i < currentFilterItemCollection.size(); i++) {
			FilterItemInfo filterItemInfo = currentFilterItemCollection.get(i);
			if (getId().equals(filterItemInfo.getPropertyName())) {
				if (CompareType.GREATER_EQUALS.equals(filterItemInfo
						.getCompareType())
						|| CompareType.GREATER.equals(filterItemInfo
								.getCompareType())) {
					setNumber(fromComp, filterItemInfo.getCompareValue()
							.toString());
				} else if (CompareType.LESS_EQUALS.equals(filterItemInfo
						.getCompareType())
						|| CompareType.LESS.equals(filterItemInfo
								.getCompareType())) {
					setNumber(toComp, filterItemInfo.getCompareValue()
							.toString());
				}
				// filterItemInfo.isRefCrossTable();
				// filterItemInfo.getPropertyName();
				// filterItemInfo.getCompareType();
				// filterItemInfo.getCompareValue();
			}
		}
	}
}
