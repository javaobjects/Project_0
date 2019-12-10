/*
 * @(#)QueryManager.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.cm.common.filter.CompositeFilterElement;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.st.common.util.STUtils;

/**
 * ����:��ѯ���˹�����
 * 
 * @author paul date:2006-8-29
 *         <p>
 * @version EAS5.2
 */
public class QueryFilterManager {
	private FilterElement element = null;

	private FilterElement mainOrgElement = null;

	// private SingleFilterElement mainOrgElement = null;

	public QueryFilterManager() {
	}

	public void setElement(FilterElement element) {
		this.element = element;
	}

	public void clearAll() {
		element.clear();
	}

	public FilterInfo getFilterInfo() {
		FilterInfo result = element.getFilterInfo();
		// TODO �Զ����ɷ������
		if (STUtils.isNotNull(result)) {
			FilterItemCollection currentFilterItemCollection = result
					.getFilterItems();
			if (mainOrgElement != null) {
				for (int i = 0; i < currentFilterItemCollection.size(); i++) {
					FilterItemInfo filterItemInfo = currentFilterItemCollection
							.get(i);
					if (mainOrgElement.getId().equals(
							filterItemInfo.getPropertyName())) {
						if (CompareType.EQUALS.equals(filterItemInfo
								.getCompareType())
								|| CompareType.LIKE.equals(filterItemInfo
										.getCompareType())) {
							// setNumber(component,
							// filterItemInfo.getCompareValue().toString());
							//currentFilterItemCollection.remove(filterItemInfo)
							// ;
							// FilterInfo f = new FilterInfo();
							if (mainOrgElement.getComponent() != null
									&& mainOrgElement.getComponent() instanceof KDBizPromptBox) {
								Object data = ((KDBizPromptBox) mainOrgElement
										.getComponent()).getValue();
								String orgId = null;
								if (data instanceof DataBaseInfo) {
									orgId = ((DataBaseInfo) data).getId()
											.toString();
								} else if (data instanceof CoreBillBaseInfo) {
									orgId = ((CoreBillBaseInfo) data).getId()
											.toString();
								}
								if (orgId != null) {
									filterItemInfo.setCompareValue(orgId);
									currentFilterItemCollection.set(i,
											filterItemInfo);
									// result.mergeFilter(f, "AND");
								}
							}
						} else if (CompareType.INCLUDE.equals(filterItemInfo
								.getCompareType())) {
							// �����in�����
							if (mainOrgElement.getComponent() != null
									&& mainOrgElement.getComponent() instanceof KDBizPromptBox) {
								Object data = ((KDBizPromptBox) mainOrgElement
										.getComponent()).getValue();
								// ��ID��Ϊ����
								String ids = null;
								if (data instanceof Object[]) {
									ids = getKeyIdList((Object[]) data, ",");
								}
								if (ids != null && ids.length() != 0) {
									filterItemInfo.setCompareValue(ids);
									currentFilterItemCollection.set(i,
											filterItemInfo);
								}
							}
						}
					}
				}
			}
		}

		return result;
	}

	// /**
	// * ���������÷�������
	// * @param map
	// * @author:paul
	// * @deprecated �����·�����������, 2006-10-14
	// * ����ʱ�䣺2006-10-12 <p>
	// */
	// public void setParam(Map map) {
	// element.setParam(map);
	// if(mainOrgElement != null && mainOrgElement instanceof
	// SingleFilterElement) {
	// mainOrgElement.setParam(map,
	// mainOrgId,
	// ((SingleFilterElement)mainOrgElement).getComponent());
	// }
	// }

	// /**
	// * ���������÷�������
	// * @param map
	// * @author:paul
	// * @deprecated �����·�����������, 2006-10-14
	// * ����ʱ�䣺2006-10-12 <p>
	// */
	// public void setData(Map map, EntityViewInfo entityViewInfo) {
	// FilterItemCollection currentFilterItemCollection = null;
	// Object value = null;
	// List list = null;
	// Map propMap = new HashMap();
	//        
	// if(entityViewInfo.getFilter()!=null)
	// {
	// currentFilterItemCollection =
	// entityViewInfo.getFilter().getFilterItems();
	// }
	// if(currentFilterItemCollection!=null)
	// {
	// for(int i = 0; i<currentFilterItemCollection.size(); i++)
	// {
	// String propName = currentFilterItemCollection.get(i).getPropertyName();
	// CompareType comp = currentFilterItemCollection.get(i).getCompareType();
	// if(propMap.containsKey(propName)) {
	// value = propMap.get(propName);
	// if(value instanceof List) {
	// list = (List)value;
	// }else {
	// list = new ArrayList();
	// list.add(propMap.get(propName));
	// }
	// list.add(comp);
	// propMap.put(propName, list);
	// }else {
	// propMap.put(propName, comp);
	// }
	// }
	//            
	// element.setData(map, propMap);
	// }
	// }

	/**
	 * @deprecated ���鲻��ʹ�ã��ĳ��µĽӿ�: setMainOrgElement(FilterElement element)<br>
	 * @author yangyong 2008-11-21
	 */
	// public void setMainOrgElement(CompositeFilterElement elements){
	// mainOrgElement = elements;
	// }
	/**
	 * ���⿪һ���ӿڡ�
	 * 
	 * @param element
	 */
	public void setMainOrgElement(FilterElement element) {
		this.mainOrgElement = element;
	}

	// public void setMainOrgElement(FilterElement element, String id) {
	// mainOrgElement = element;
	// mainOrgId = id;
	// }

	// /**
	// * ���������ز�ѯ��������
	// * @return
	// * @author:paul
	// * ����ʱ�䣺2006-10-12 <p>
	// */
	// public CustomerParams getCustomerParams() {
	// CustomerParams cp = new CustomerParams();
	// element.addCustomerParam(cp);
	// if(mainOrgElement != null && mainOrgElement instanceof
	// SingleFilterElement) {
	// mainOrgElement.addCustomerParamWithPK(cp,
	// mainOrgId,
	// ((SingleFilterElement)mainOrgElement).getComponent());
	// }
	// return cp;
	// }
	//    
	// /**
	// * ���������÷����е�ֵ�����˿ؼ�
	// * @param cp
	// * @param isInBOTP
	// * @author:paul
	// * ����ʱ�䣺2006-10-14 <p>
	// */
	// public void setCustomerParams(CustomerParams cp, boolean isInBOTP) {
	//        
	// element.setElementValue(cp);
	//
	// if(cp != null && mainOrgElement instanceof SingleFilterElement ) {
	// String value = cp.getCustomerParam(mainOrgId);
	// if(value != null) {
	// if(isInBOTP) {
	// //��ʱע�͵�
	//mainOrgElement.setData(((SingleFilterElement)mainOrgElement).getComponent(
	// ),
	// SCMGroupClientUtils.getOrgUnitInfos(value));
	// }else {
	// if(value.indexOf(";") > -1) {
	// value = value.substring(0, value.indexOf(";"));
	// }
	//mainOrgElement.setData(((SingleFilterElement)mainOrgElement).getComponent(
	// ),
	// SCMGroupClientUtils.getOrgUnitInfo(value));
	// }
	// }
	// }
	// }

	public boolean verify() {
		return element.verify();
	}

	public RptParams getRptParams() {
		RptParams rp = new RptParams();

		element.addRptParams(rp);
		return rp;
	}

	public void setRptParams(RptParams rp) {
		element.setElementValue(rp);
	}

	public void setData(EntityViewInfo entityViewInfo) {
		element.setData(entityViewInfo);
		if (mainOrgElement != null) {
			FilterItemCollection currentFilterItemCollection = new FilterItemCollection();
			if (entityViewInfo != null && entityViewInfo.getFilter() != null) {
				currentFilterItemCollection = entityViewInfo.getFilter()
						.getFilterItems();
			}
			for (int i = 0; i < currentFilterItemCollection.size(); i++) {
				FilterItemInfo filterItemInfo = currentFilterItemCollection
						.get(i);
				if (mainOrgElement.getId().equals(
						filterItemInfo.getPropertyName())) {
					if (CompareType.EQUALS.equals(filterItemInfo
							.getCompareType())
							|| CompareType.LIKE.equals(filterItemInfo
									.getCompareType())) {
						setMainOrgUnitF7(filterItemInfo.getCompareValue()
								.toString());
					} else if (CompareType.INCLUDE.equals(filterItemInfo
							.getCompareType())) {
						// ���ÿؼ��Ķ���ֵ
						Object value = filterItemInfo.getCompareValue();
						StringBuffer compareValue = new StringBuffer();
						if (value instanceof String) {
							compareValue.append(value);
						} else if (value instanceof Set) {
							Set set = (Set) value;
							Iterator iter = set.iterator();
							boolean isFirst = true;
							while (iter.hasNext()) {
								if (!isFirst) {
									compareValue.append(";");
								}
								compareValue.append(iter.next());
								isFirst = false;
							}
							try {
								String[] ids = compareValue.toString().split(
										";"); // ��ס��ͨ���������ָ����������Ƿ���Ҫ����ת�ɱ���ַ�����ʱ��
								Object o = mainOrgElement.getComponent();
								if (o == null || !(o instanceof KDBizPromptBox)) {
									return;
								}
								if (ids == null || ids.length == 0) {
									((KDBizPromptBox) o).setValue(null);
									return;
								} else {
									ObjectUuidPK pk = null;
									BOSObjectType objType = null;
									BOSUuid uuid = null;
									IDynamicObject dynamicObject = DynamicObjectFactory
											.getRemoteInstance();
									ArrayList list = new ArrayList();
									for (int index = 0, size = ids.length; index < size; index++) {
										uuid = BOSUuid.read(ids[index]);
										pk = new ObjectUuidPK(uuid);
										objType = uuid.getType();
										list.add(dynamicObject.getValue(
												objType, pk)); // �Ѷ�����ӽ�ȥ
									}
									list.trimToSize();
									IObjectValue[] objectValue = new IObjectValue[list
											.size()];
									list.toArray(objectValue);
									((KDBizPromptBox) o).setData(objectValue);
								}
							} catch (BOSException e) {
								// do nothing
							}
						}
					}
				}
			}
		}
	}

	private void setMainOrgUnitF7(String orgId) {
		FullOrgUnitInfo orgInfo = null;
		try {
			IFullOrgUnit iOrgUnit = FullOrgUnitFactory.getRemoteInstance();
			orgInfo = iOrgUnit.getFullOrgUnitInfo(new ObjectStringPK(orgId));
			Object o = mainOrgElement.getComponent();
			if (o != null && o instanceof KDBizPromptBox) {
				((KDBizPromptBox) o).setValue(orgInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͨ���������ݣ�ȡ��IDList��
	 * 
	 * @param vos
	 *            ��������
	 * @param delim
	 *            �ָ��
	 * @return ����ID�ַ�����
	 */
	/**
	 * ͨ���������ݣ�ȡ��IDList��
	 * 
	 * @param vos
	 *            ��������
	 * @param delim
	 *            �ָ��
	 * @return ����ID�ַ�����
	 */
	public static String getKeyIdList(Object[] vos, String delim) {
		if (vos == null || vos.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < vos.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			if (vos[i] instanceof ObjectBaseInfo) {
				sb.append(((ObjectBaseInfo) vos[i]).getId().toString());
			}
		}
		return sb.toString();
	}
}
