package com.kingdee.eas.st.common.filter;

import java.awt.Component;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.dao.IObjectValue;
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
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.StorageOrgUnit;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.scm.common.verify.VerifyItem;
import com.kingdee.eas.scm.common.verify.VerifyType;

/**
 * ����:���ؼ�����Ԫ���࣬������������ҵ����֯�Ķ����FilterItem��<br>
 * ���ݸ�����SingleFilterElement�����򱦿�����MultiOrgsFilterElement���������<br>
 * Ŀǰֻ�ʺ���KDBizPromptBox��Ӧ�ã��Ժ���Ը��졣<br>
 * 
 * @author yangyong date:2008-11-20
 *         <p>
 * @version EAS5.2
 */
public class MultiFilterElement extends FilterElement {
	private Component component = null;

	private CompareType compareType = CompareType.INCLUDE;

	private int dateType = NOTDATE;

	private Object defValue = null;

	/**
	 * �Ƿ�ʹ��id��ѯ��ֻ���F7�ؼ���Ч�����Ҫʹ��idƥ��<br>
	 * ��Ҫ����Ϊtrue��Ĭ��F7�ؼ�ʹ��numberƥ��<br>
	 */
	private boolean isUseId = false;

	public MultiFilterElement(String id, Component component) {
		super(id);
		this.component = component;
		init();
	}

	public MultiFilterElement(String id, KDBizPromptBox component,
			boolean isUseId) {
		this(id, component);
		this.isUseId = isUseId;
	}

	/**
	 * ��ʼ�������ÿ��Զ�ѡ���˷���ֻ��������Component�������á�
	 * 
	 */
	private void init() {
		if (component != null && component instanceof KDBizPromptBox) {
			((KDBizPromptBox) component).setEnabledMultiSelection(true);
		}
	}

	public MultiFilterElement(String id, Component component, Object blankValue) {
		super(id, blankValue);
		this.component = component;
		init();
	}

	/**
	 * ����:@return ���� component��
	 */
	public Component getComponent() {
		return component;
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
	 * ����:����component��ֵ��
	 * 
	 * @param component
	 */
	public void setComponent(Component component) {
		this.component = component;
	}

	/**
	 * ȡ�ù�����Ϣ��<br>
	 * ������Ҫ���ǣ� 1. �û����ò���ѡ�������
	 */
	public FilterInfo getFilterInfo() {
		FilterInfo filterInfo = null;
		if (!isUseId) {
			// Ĭ�ϰ�number��Ϊ��������
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
			// ��ID��Ϊ����
			String data = null;
			Object info = ((KDBizPromptBox) component).getData();

			if (info instanceof Object[]) {
				data = getKeyIdList((Object[]) info, ",");
			} else if (info instanceof DataBaseInfo) {
				data = ((DataBaseInfo) info).getId().toString();
			} else if (info instanceof CoreBillBaseInfo) {
				data = ((CoreBillBaseInfo) info).getId().toString();
			} else {
			}

			if (data != null) {
				filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(
						new FilterItemInfo(getId(), data, compareType));
			}
		}
		return filterInfo;
	}

	public void clear() {
		super.clear(component);
		if (defValue != null) {
			this.setData(component, defValue);
		}
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

	protected void setElementData(CustomerParams cp, String key) {
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#verify(com.kingdee.eas.scm.common.util.FilterElement.VerifyItem)
	 */
	protected boolean verify(VerifyItem verifyItem) {
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

	/**
	 * ����FilterInfo���ù������ݡ�
	 */
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
				/** ֻ��Ҫ��include����� */
				if (this.compareType.equals(filterItemInfo.getCompareType())) {
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
					}
					if (compareValue == null || compareValue.length() == 0) {
						((KDBizPromptBox) component).setValue(null);
						return;
					}
					if (!isUseId) {
						setNumber(component, compareValue.toString());
					} else {
						try {
							String[] ids = compareValue.toString().split(";"); // ��ס
																				// ��
																				// ͨ���������ָ�����
																				// ��
																				// ��
																				// �Ƿ���Ҫ����ת�ɱ���ַ�����ʱ��
																				// ��
							if (ids == null || ids.length == 0) {
								((KDBizPromptBox) component).setValue(null);
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
									list.add(dynamicObject
											.getValue(objType, pk)); // �Ѷ�����ӽ�ȥ
								}
								list.trimToSize();
								IObjectValue[] objectValue = new IObjectValue[list
										.size()];
								list.toArray(objectValue);
								((KDBizPromptBox) component)
										.setData(objectValue);
							}
						} catch (BOSException e) {
							// do nothing
						}
					}
				}
			}
		}
	}

	/**
	 * ����:@return ���� compareType��
	 */
	public CompareType getCompareType() {
		return compareType;
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
