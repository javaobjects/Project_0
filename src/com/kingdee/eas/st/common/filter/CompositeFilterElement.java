/*
 * @(#)CompositeFilterElement.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.filter;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.scm.common.verify.VerifyItem;

/**
 * ����:�����Ԫ����
 * 
 * @author paul date:2006-8-30
 *         <p>
 * @version EAS5.2
 */
public class CompositeFilterElement extends FilterElement {
	public static final int AND = 0;
	public static final int OR = 1;
	// private List elements = null;
	private Map elementMap = null;
	private int linkType = 0;

	public CompositeFilterElement(int linkType) {
		// elements = new ArrayList();
		elementMap = new HashMap();
		this.linkType = linkType;
	}

	public void add(FilterElement element) {
		// elements.add(element);
		elementMap.put(element.getId(), element);

	}

	public int size() {
		return elementMap.size();
	}

	public FilterElement get(String id) {
		return (FilterElement) elementMap.get(id);
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#getFilterInfo()
	 */
	public FilterInfo getFilterInfo() {
		FilterElement element = null;
		FilterInfo result = null;
		FilterInfo filterInfo = null;
		Object[] keys = (Object[]) elementMap.keySet().toArray();

		for (int i = 0; i < size(); i++) {
			element = (FilterElement) get((String) keys[i]);
			filterInfo = element.getFilterInfo();
			if (filterInfo != null && filterInfo.getFilterItems().size() > 0) {
				if (result == null) {
					result = filterInfo;
				} else {
					try {
						result.mergeFilter(filterInfo, linkType == 0 ? "AND"
								: "OR");
					} catch (BOSException e) {
						/* TODO �Զ����� catch �� */
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#clear()
	 */
	public void clear() {
		Object[] keys = (Object[]) elementMap.keySet().toArray();
		for (int i = 0; i < size(); i++) {
			get((String) keys[i]).clear();
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
	// Object[] keys = (Object[]) elementMap.keySet().toArray();
	// for(int i=0; i<size(); i++) {
	// get((String) keys[i]).setParam(map);
	// }
	// }

	public FilterElement getLeafElement(String id) {
		FilterElement element = (FilterElement) elementMap.get(id);
		if (element == null) {
			Object[] keys = (Object[]) elementMap.keySet().toArray();
			for (int i = 0; i < size(); i++) {
				element = (FilterElement) elementMap.get(keys[i]);
				if (element instanceof CompositeFilterElement) {
					return ((CompositeFilterElement) element)
							.getLeafElement(id);
				}
			}
		}

		return element;
	}

	// /**
	// * ������
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.util.FilterElement#setData(java.lang.Object,
	// com.kingdee.bos.metadata.query.util.CompareType)
	// */
	// public void setData(Object value, CompareType type)
	// {
	//               
	// }

	// /**
	// * ����������Ԫ������-����
	// * @author:paul
	// * @see
	// com.kingdee.eas.scm.common.filter.FilterElement#setData(java.util.Map,
	// java.util.Map)
	// */
	// protected void setData(Map paramMap, Map propMap)
	// {
	// Object[] keys = (Object[]) elementMap.keySet().toArray();
	// for(int i=0; i<size(); i++) {
	// get((String) keys[i]).setData(paramMap, propMap);
	// }
	// }

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addCustomerParam(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void addCustomerParam(CustomerParams cp) {
		Object[] keys = (Object[]) elementMap.keySet().toArray();
		for (int i = 0; i < size(); i++) {
			get((String) keys[i]).addCustomerParam(cp);
		}
	}

	/**
	 * ����������Ԫ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementValue(com.kingdee.eas.base.commonquery.client.CustomerParams,
	 *      java.util.Map)
	 */
	public void setElementValue(CustomerParams cp) {
		if (cp != null) {
			Object[] keys = (Object[]) elementMap.keySet().toArray();
			for (int i = 0; i < size(); i++) {
				get((String) keys[i]).setElementValue(cp);
			}
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
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#verify()
	 */
	protected boolean verify() {
		Object[] keys = (Object[]) elementMap.keySet().toArray();
		for (int i = 0; i < size(); i++) {
			if (!get((String) keys[i]).verify()) {
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

	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#addCustomerParam2(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void addRptParams(RptParams cp) {
		Object[] keys = (Object[]) elementMap.keySet().toArray();
		for (int i = 0; i < size(); i++) {
			get((String) keys[i]).addRptParams(cp);
		}
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#setElementValue2(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void setElementValue(RptParams cp) {
		if (cp != null) {
			Object[] keys = (Object[]) elementMap.keySet().toArray();
			for (int i = 0; i < size(); i++) {
				get((String) keys[i]).setElementValue(cp);
			}
		}
	}

	protected void setData(EntityViewInfo entityViewInfo) {
		// TODO �Զ����ɷ������
		Iterator it = elementMap.values().iterator();
		while (it.hasNext()) {
			((FilterElement) it.next()).setData(entityViewInfo);
		}
	}
}
