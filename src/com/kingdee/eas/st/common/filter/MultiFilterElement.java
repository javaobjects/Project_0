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
 * 描述:单控件过滤元素类，用于类似于主业务组织的多过滤FilterItem。<br>
 * 根据钢铁的SingleFilterElement和周万宝开发的MultiOrgsFilterElement改造过来。<br>
 * 目前只适合了KDBizPromptBox的应用，以后可以改造。<br>
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
	 * 是否使用id查询，只针对F7控件有效，如果要使用id匹配<br>
	 * 则要设置为true，默认F7控件使用number匹配<br>
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
	 * 初始化，设置可以多选，此方法只有设置了Component才起作用。
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
	 * 描述:@return 返回 component。
	 */
	public Component getComponent() {
		return component;
	}

	/**
	 * 描述:设置dateType的值。
	 * 
	 * @param dateType
	 */
	public void setDateType(int dateType) {
		this.dateType = dateType;
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
	 * 取得过滤信息。<br>
	 * 这里需要考虑： 1. 用户设置不多选的情况；
	 */
	public FilterInfo getFilterInfo() {
		FilterInfo filterInfo = null;
		if (!isUseId) {
			// 默认把number作为过滤条件
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
			// 把ID作为过滤
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

	protected void setElementData(CustomerParams cp, String key) {
	}

	/**
	 * 描述：
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
	 * 描述：
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

	/**
	 * 根据FilterInfo设置过滤数据。
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
				/** 只需要看include的情况 */
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
							String[] ids = compareValue.toString().split(";"); // 记住
																				// ，
																				// 通过逗号来分割条件
																				// （
																				// ？
																				// 是否需要考虑转成别的字符串的时候
																				// ）
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
											.getValue(objType, pk)); // 把对象添加进去
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
	 * 描述:@return 返回 compareType。
	 */
	public CompareType getCompareType() {
		return compareType;
	}

	/**
	 * 通过对象数据，取得IDList。
	 * 
	 * @param vos
	 *            对象数组
	 * @param delim
	 *            分割符
	 * @return 对象ID字符串。
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
