/*
 * @(#)FilterEnum.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.filter;

import java.awt.Component;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.ButtonStates;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxItem;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.scm.common.verify.VerifyItem;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.util.enums.DoubleEnum;
import com.kingdee.util.enums.FloatEnum;
import com.kingdee.util.enums.IntEnum;
import com.kingdee.util.enums.LongEnum;
import com.kingdee.util.enums.StringEnum;

/**
 * 描述:过滤元素抽象基类
 * 
 * 目前支持控件有: KDPromptBox, KDTextField, KDComboBox, KDDatePicker, KDCheckBox,
 * KDRadioButton
 * 
 * @author paul date:2006-8-30
 *         <p>
 * @version EAS5.2
 */
public abstract class FilterElement {
	private String id = null;
	private Object blankValue = null;
	private boolean isExclude = false;// 是否包含在FilterInfo结果中
	private Collection verifyItems = null;// 校验项

	public final static int DATESTART = 0;
	public final static int DATEEND = 1;
	public final static int NOTDATE = -1;

	// 返回元素的FilterInfo
	public abstract FilterInfo getFilterInfo();

	// 清空该元素内容
	public abstract void clear();

	// /**
	// * 描述：把元素内容设置到Map中
	// * @deprecated 采用新方案，已作废, 2006-10-14
	// */
	// public abstract void setParam(Map map);

	// /**
	// * 描述：从Map中取出对应的内容,并填充元素
	// * @deprecated 采用新方案，已作废, 2006-10-14
	// */
	// protected abstract void setData(Map paramMap, Map propMap);

	/**
	 * author: wangzhiwei
	 * 
	 * @param entityViewInfo
	 */
	protected abstract void setData(EntityViewInfo entityViewInfo);

	/**
	 * 描述：增加查询方案参数
	 */
	public abstract void addCustomerParam(CustomerParams cp);

	/**
	 * 描述：设置元素的内容-文本
	 */
	public abstract void setElementValue(CustomerParams cp);

	/**
	 * 描述：设置元素的内容-对象
	 */
	protected abstract void setElementData(CustomerParams cp, String key);

	/**
	 * 描述：增加查询方案参数－报表参数 可支持基本对象 2006-11-13
	 */
	public abstract void addRptParams(RptParams cp);

	/**
	 * 描述：设置元素的内容-报表参数 可支持基本对象 2006-11-13
	 */
	public abstract void setElementValue(RptParams cp);

	/**
	 * 描述：校验元素的内容
	 * 
	 * @param verifyItem
	 * @return
	 * @author:paul 创建时间：2006-10-17
	 *              <p>
	 */
	protected abstract boolean verify(VerifyItem verifyItem);

	/**
	 * 返回对应的组件，默认空实现。
	 * 
	 * @return
	 */
	public Component getComponent() {
		return null;
	}

	public abstract void setDefaultValue(Object value);

	public FilterElement() {
	}

	public FilterElement(String id) {
		this.id = id;
	}

	public FilterElement(String id, Object blankValue) {
		this.id = id;
		this.blankValue = blankValue;
	}

	/**
	 * 描述:@return 返回 blankValue。
	 */
	public Object getBlankValue() {
		return blankValue;
	}

	/**
	 * 描述:设置blankValue的值。
	 * 
	 * @param blankValue
	 */
	public void setBlankValue(Object blankValue) {
		this.blankValue = blankValue;
	}

	/**
	 * 描述:@return 返回 id。
	 */
	public String getId() {
		return id;
	}

	/**
	 * 描述:设置id的值。
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	protected void clear(Object obj) {
		if (obj instanceof KDPromptBox) {
			((KDPromptBox) obj).setValue(null);
		} else if (obj instanceof KDTextField) {
			((KDTextField) obj).setText(null);
		} else if (obj instanceof KDComboBox) {
			((KDComboBox) obj).setSelectedIndex(-1);
		} else if (obj instanceof KDDatePicker) {
			((KDDatePicker) obj).setValue(null);
		} else if (obj instanceof KDCheckBox) {
			((KDCheckBox) obj).setSelected(ButtonStates.UNSELECTED);
		}
	}

	/**
	 * 描述:获取元素的Number内容
	 */
	protected String getNumber(Object obj) {
		Object data = null;
		String result = null;

		if (isBlank(obj)) {
			return null;
		}

		if (obj instanceof KDPromptBox) {
			data = ((KDPromptBox) obj).getData();
			if (obj instanceof KDBizPromptBox) {
				// colin增加了空指针判断 2007-9-19
				IFormatter iFormatter = ((KDBizPromptBox) obj)
						.getDisplayFormatter();
				if (STUtils.isNotNull(iFormatter)) {
					if (iFormatter.toString().equalsIgnoreCase("$name$")) {
						if (data != null) {
							if (data instanceof Object[]) {
								result = getNumberList((Object[]) data);
							} else {
								if (data instanceof DataBaseInfo) {
									result = ((DataBaseInfo) data).getNumber()
											.toString();
								} else if (data instanceof CoreBillBaseInfo) {
									result = ((CoreBillBaseInfo) data)
											.getNumber().toString();
								}
							}
						}

					}
				}

			}

			if (result == null) {
				result = ((KDPromptBox) obj).getText().trim();
			}

			if (result.equals("") || (data != null && isBlank(data))) {
				return null;
			}

		} else if (obj instanceof KDTextField
				&& ((KDTextField) obj).getText() != null
				&& ((KDTextField) obj).getText().length() > 0) {
			data = ((KDTextField) obj).getText();
			result = (String) (isBlank(data) ? null : data);
		} else if (obj instanceof KDComboBox) {
			if (((KDComboBox) obj).getSelectedIndex() == -1) {
				return null;
			}
			if (((KDComboBox) obj).getSelectedItem().getClass().getName()
					.equals(KDComboBoxItem.class.getName())
					&& ((KDComboBoxItem) ((KDComboBox) obj).getSelectedItem())
							.toString().equals("")) {
				return null;
			}
			Object enumObj = ((KDComboBox) obj).getSelectedItem();
			if (enumObj instanceof IntEnum) {
				IntEnum itemEnum = (IntEnum) enumObj;
				if (!isBlank(itemEnum)) {
					result = String.valueOf(itemEnum.getValue());
				}
			} else if (enumObj instanceof StringEnum) {
				StringEnum itemEnum = (StringEnum) enumObj;
				if (!isBlank(itemEnum)) {
					result = itemEnum.getValue();
				}
			} else if (enumObj instanceof FloatEnum) {
				FloatEnum itemEnum = (FloatEnum) enumObj;
				if (!isBlank(itemEnum)) {
					result = String.valueOf(itemEnum.getValue());
				}
			} else if (enumObj instanceof LongEnum) {
				LongEnum itemEnum = (LongEnum) enumObj;
				if (!isBlank(itemEnum)) {
					result = String.valueOf(itemEnum.getValue());
				}
			} else if (enumObj instanceof DoubleEnum) {
				DoubleEnum itemEnum = (DoubleEnum) enumObj;
				if (!isBlank(itemEnum)) {
					result = String.valueOf(itemEnum.getValue());
				}
			} else {
				result = null;
			}
		} else if (obj instanceof KDDatePicker) {
			data = ((KDDatePicker) obj).getTimestamp();
			if (!isBlank(data)) {
				result = data.toString();
			}
		} else if (obj instanceof KDCheckBox) {
			if (((KDCheckBox) obj).isSelected()) {
				result = "1";
			} else {
				result = "0";
			}
			// result = String.valueOf(((KDCheckBox)obj).isSelected());
		} else if (obj instanceof KDRadioButton) {
			result = String.valueOf(((KDRadioButton) obj).isSelected());
		} else if (obj instanceof KDSpinner
				&& ((KDSpinner) obj).getValue() != null) {
			result = ((Integer) ((KDSpinner) obj).getValue()).toString();
		}

		return result;
	}

	/**
	 * 描述:判断元素的数据是否为空
	 */
	protected boolean isBlank(Object data) {
		// return STUtils.isNull(data);
		if (data == null || data.equals(blankValue)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 描述:构造元素的FilterItemInfo
	 */
	protected FilterItemInfo getFilterItemInfo(Object obj, CompareType type) {
		String data = null;
		Set sets = null;
		if (isExclude) {// 不包含在FilterInfo中
			return null;
		}

		data = getNumber(obj);

		if (data != null) {
			if (data.indexOf(";") > -1) {
				sets = STGroupClientUtils.getKeyIdList(data);

				if (sets.size() > 0) {
					return new FilterItemInfo(id, sets, CompareType.INCLUDE);
				}
			} else {
				return new FilterItemInfo(id, data, type);
			}
		}

		return null;
	}

	protected String getName(Object obj) {
		if (obj instanceof KDPromptBox) {
			return ((KDPromptBox) obj).getName();
		} else if (obj instanceof KDTextField) {
			return ((KDTextField) obj).getName();
		} else if (obj instanceof KDComboBox) {
			return ((KDComboBox) obj).getName();
		} else if (obj instanceof KDDatePicker) {
			return ((KDDatePicker) obj).getName();
		} else if (obj instanceof KDCheckBox) {
			return ((KDCheckBox) obj).getName();
		} else if (obj instanceof KDRadioButton) {
			return ((KDRadioButton) obj).getName();
		}

		return null;
	}

	// /**
	// * 描述:把元素内容设置到Map中
	// * @deprecated 采用新方案，已作废, 2006-10-14
	// */
	// protected void setParam(Map map, Object obj) {
	// String name = getName(obj);
	// setParam(map, name, obj);
	// }
	//    
	// /**
	// * 描述:把元素内容设置到Map中
	// * @deprecated 采用新方案，已作废, 2006-10-14
	// */
	// protected void setParam(Map map, String key, Object obj) {
	// Object data = null;
	//
	// if(key != null) {
	// data = getData(obj);
	//
	// if(data != null && !data.equals(blankValue)) {
	// map.put(key, data);
	// }
	// }
	// }

	/**
	 * 描述:根据编号数组，得到编号列表，以‘；’分割
	 */
	public final String getNumberList(Object[] datas) {
		Object data = null;
		StringBuffer sb = new StringBuffer();

		if (datas != null && datas.length > 0) {
			for (int i = 0; i < datas.length; i++) {
				data = datas[i];
				if (sb.length() > 0) {
					sb.append("; ");
				}
				if (data instanceof DataBaseInfo) {
					sb.append(((DataBaseInfo) data).getNumber().toString());
				} else if (data instanceof CoreBillBaseInfo) {
					sb.append(((CoreBillBaseInfo) data).getNumber().toString());
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 描述:根据Id数组，得到Id列表，以‘；’分割
	 */
	public final String getPKList(Object[] datas) {
		Object data = null;
		StringBuffer sb = new StringBuffer();

		if (datas != null && datas.length > 0) {
			for (int i = 0; i < datas.length; i++) {
				data = datas[i];
				if (sb.length() > 0) {
					sb.append("; ");
				}
				if (data instanceof ObjectBaseInfo) {
					sb.append(((ObjectBaseInfo) data).getId().toString());
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 描述:设置元素的内容 可支持基本对象 2006-11-13
	 */
	protected void setData(Component obj, Object value) {
		if (obj instanceof KDPromptBox) {
			// ((KDPromptBox)obj).setText((String) value);
			((KDPromptBox) obj).setData(value);
		} else if (obj instanceof KDTextField) {
			((KDTextField) obj).setText((String) value);
		} else if (obj instanceof KDComboBox) {
			// int eumValue = -1;
			if (value != null) {
				// if(value instanceof Integer){
				// eumValue = ((Integer)value).intValue();
				// }else if(value instanceof IntEnum){
				// eumValue = ((IntEnum)value).getValue();
				// }
				KDComboBox cobox = (KDComboBox) obj;
				cobox
						.setSelectedIndex(getIdxOfComboBox(cobox, value
								.toString()));
			}
		} else if (obj instanceof KDDatePicker) {
			((KDDatePicker) obj).setValue(value);
		} else if (obj instanceof KDCheckBox) {
			if (value instanceof Boolean) {
				if (((Boolean) value).booleanValue()) {
					((KDCheckBox) obj).setSelected(ButtonStates.SELECTED);
				} else {
					((KDCheckBox) obj).setSelected(ButtonStates.UNSELECTED);
				}
			} else {
				((KDCheckBox) obj).setSelected(ButtonStates.UNSELECTED);
			}
		} else if (obj instanceof KDRadioButton) {
			if (value instanceof Boolean) {
				((KDRadioButton) obj).setSelected(((Boolean) value)
						.booleanValue());
			} else {
				((KDRadioButton) obj).setSelected(false);
			}
		}
	}

	/**
	 * 描述：设置元素编码内容
	 * 
	 * @param obj
	 * @param value
	 * @author:paul 创建时间：2006-10-12
	 *              <p>
	 */
	protected void setNumber(Object obj, String value) {
		if (value == null && !(obj instanceof KDComboBox)) {
			return;
		}
		if (obj instanceof KDPromptBox) {
			((KDPromptBox) obj).setText((String) value);
			// 所有的F7（除主业务组织）都是按编码过滤，这里不再需要commit
			// 针对主业务组织F7，会有后续操作设置其值
			// try {
			// ((KDPromptBox)obj).commitEdit();
			// } catch (ParseException e) {
			// // TODO 自动生成 catch 块
			// e.printStackTrace();
			// }
			// ((KDPromptBox)obj).
		} else if (obj instanceof KDTextField) {
			((KDTextField) obj).setText(value.toString());
		} else if (obj instanceof KDComboBox) {
			// int eumValue = -1;
			if (value != null) {
				// eumValue = Integer.valueOf(value).intValue();
				KDComboBox cobox = (KDComboBox) obj;
				cobox.setSelectedIndex(getIdxOfComboBox(cobox, value));
				if (value.equals("")) {
					cobox.setSelectedIndex(0);
				}
			}
		} else if (obj instanceof KDDatePicker) {
			Timestamp time = Timestamp.valueOf(value);
			((KDDatePicker) obj).setValue(time);
		} else if (obj instanceof KDCheckBox) {
			// boolean isSel = Boolean.valueOf(value).booleanValue();
			boolean isSel = false;
			if (value != null && "1".equalsIgnoreCase(value.toString().trim())) {
				isSel = true;
			}
			if (isSel) {
				((KDCheckBox) obj).setSelected(ButtonStates.SELECTED);
			} else {
				((KDCheckBox) obj).setSelected(ButtonStates.UNSELECTED);
			}
		} else if (obj instanceof KDRadioButton && value != null) {
			boolean isSel = Boolean.valueOf(value).booleanValue();
			((KDRadioButton) obj).setSelected(isSel);
		}
	}

	/**
	 * 描述：查找KDComboBox的Index
	 * 
	 * @param cobox
	 * @param value
	 * @return
	 * @author:paul 创建时间：2007-4-4
	 *              <p>
	 */
	private int getIdxOfComboBox(KDComboBox cobox, String value) {
		for (int idx = 0; idx < cobox.getItemCount(); idx++) {
			Object item = cobox.getItemAt(idx);
			if (item != null) {
				if (item instanceof IntEnum) {
					int eumValue = Integer.valueOf(value).intValue();
					if (((IntEnum) item).getValue() == eumValue) {
						return idx;
					}
				} else if (item instanceof StringEnum) {
					if (((StringEnum) item).getValue().equals(value)) {
						return idx;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * 描述:获取元素的内容 可支持基本对象 2006-11-13
	 */
	protected Object getData(Object obj) {
		Object data = null;

		if (obj instanceof KDPromptBox) {
			data = ((KDPromptBox) obj).getData();
			String result = ((KDPromptBox) obj).getText().trim();
			if (result.equals("") || (data != null && isBlank(data))) {
				return null;
			}
			data = result;

		} else if (obj instanceof KDTextField) {
			data = ((KDTextField) obj).getText();
		} else if (obj instanceof KDComboBox) {
			IntEnum intEnum = (IntEnum) ((KDComboBox) obj).getSelectedItem();
			data = new Integer(intEnum.getValue());

		} else if (obj instanceof KDDatePicker) {
			data = ((KDDatePicker) obj).getTimestamp();
		} else if (obj instanceof KDCheckBox) {
			data = Boolean.valueOf(((KDCheckBox) obj).isSelected());
			if (data.equals(new Boolean(false))) {
				data = null;
			}
		} else if (obj instanceof KDRadioButton) {
			data = Boolean.valueOf(((KDRadioButton) obj).isSelected());
		}

		if (data != null && data.equals(blankValue)) {
			data = null;
		}
		return data;
	}

	/**
	 * 描述:获取元素的内容PK
	 */
	protected Object getDataPK(Object obj) {
		Object data = null;

		if (obj instanceof KDPromptBox) {
			data = ((KDPromptBox) obj).getData();
			if (data != null) {
				if (data instanceof ObjectBaseInfo) {
					data = ((ObjectBaseInfo) data).getId().toString();
				} else if (data instanceof ObjectBaseInfo[]) {
					ObjectBaseInfo[] objects = (ObjectBaseInfo[]) data;
					String[] result = new String[objects.length];
					for (int i = 0; i < objects.length; i++) {
						result[i] = objects[i].getId().toString();
					}

					data = result;
				}
			}

		} else if (obj instanceof KDTextField) {
			data = ((KDTextField) obj).getText();
		} else if (obj instanceof KDComboBox) {
			IntEnum enum1 = (IntEnum) ((KDComboBox) obj).getSelectedItem();
			if (enum1 != null) {
				data = String.valueOf(enum1.getValue());
			}
		} else if (obj instanceof KDDatePicker) {
			data = ((KDDatePicker) obj).getTimestamp();
		} else if (obj instanceof KDCheckBox) {
			data = new Boolean(((KDCheckBox) obj).isSelected());
		} else if (obj instanceof KDRadioButton) {
			data = new Boolean(((KDRadioButton) obj).isSelected());
		}

		if (data != null && data.equals(blankValue)) {
			data = null;
		}
		return data;
	}

	/**
	 * 描述:@return 返回 isExclude。
	 */
	public boolean isExclude() {
		return isExclude;
	}

	/**
	 * 描述:设置isExclude的值。
	 * 
	 * @param isExclude
	 */
	public void setExclude(boolean isExclude) {
		this.isExclude = isExclude;
	}

	/**
	 * 描述:按起止日前重新设置日前
	 */
	public Timestamp getTimestamp(Timestamp value, int dateType) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		if (dateType == DATESTART) {
			calendar.set(calendar.get(Calendar.YEAR), calendar
					.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
					0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} else {
			calendar.set(calendar.get(Calendar.YEAR), calendar
					.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
					23, 59, 59);
			calendar.set(Calendar.MILLISECOND, 998);
		}

		return new Timestamp(calendar.getTimeInMillis());

	}

	/**
	 * 描述：增加查询方案参数
	 * 
	 * @param cp
	 * @param obj
	 * @author:paul 创建时间：2006-10-12
	 *              <p>
	 */
	public void addCustomerParam(CustomerParams cp, Object obj) {
		String name = getName(obj);
		addCustomerParam(cp, name, obj);
	}

	/**
	 * 描述：增加查询方案参数-编码
	 * 
	 * @param cp
	 * @param key
	 * @param number
	 * @author:paul 创建时间：2006-10-12
	 *              <p>
	 */
	public void addCustomerParam(CustomerParams cp, String key, Object obj) {
		String number = getNumber(obj);
		if (key != null && number != null) {
			cp.addCustomerParam(key, number);
		}
	}

	/**
	 * 描述：增加报表方案参数-支持基本对象
	 * 
	 * @param cp
	 * @param key
	 * @param obj
	 * @author:paul 创建时间：2006-11-13
	 *              <p>
	 */
	public void addRptParams(RptParams cp, String key, Object obj) {
		// 报表参数统一传递Text即Number paul 2007-5-14
		String number = getNumber(obj);
		if (key != null && number != null) {
			cp.setObject(key, number);
		}
		// if(obj instanceof KDPromptBox) {
		// Object data = ((KDPromptBox)obj).getData();
		//
		// // String result = ((KDPromptBox)obj).getText().trim();
		// // if(result.equals("") || (data != null && isBlank(data))) {
		// // return;
		// // }
		// // cp.setString(key, result);
		// if(data == null || isBlank(data)) {
		// return;
		// }
		// cp.setObject(key, data);
		// }else if(obj instanceof KDTextField) {
		// String data = ((KDTextField)obj).getText();
		// if(!isBlank(data)) {
		// cp.setString(key, data);
		// }
		// }else if(obj instanceof KDComboBox) {
		// IntEnum intEnum = (IntEnum)((KDComboBox)obj).getSelectedItem();
		// Integer data = new Integer(intEnum.getValue());
		// if(!isBlank(data)) {
		// cp.setInt(key, intEnum.getValue());
		// }
		// }else if(obj instanceof KDDatePicker) {
		// Date data = (Date) ((KDDatePicker)obj).getTimestamp().clone();
		// if(!isBlank(data)) {
		// cp.setObject(key, data);
		// }
		// }else if(obj instanceof KDCheckBox) {
		// Boolean data = new Boolean(((KDCheckBox)obj).isSelected());
		// if(!isBlank(data)) {
		// cp.setBoolean(key, data.booleanValue());
		// }
		// }else if(obj instanceof KDRadioButton) {
		// Boolean data = new Boolean(((KDRadioButton)obj).isSelected());
		// if(!isBlank(data)) {
		// cp.setBoolean(key, data.booleanValue());
		// }
		// }
		// 
	}

	/**
	 * 描述：增加查询方案参数-Id
	 * 
	 * @param cp
	 * @param key
	 * @param number
	 * @author:paul 创建时间：2006-10-12
	 *              <p>
	 */
	public void addCustomerParamWithPK(CustomerParams cp, String key, Object obj) {
		String pk = getPK(obj);
		if (key != null && pk != null) {
			cp.addCustomerParam(key, pk);
		}
	}

	public final Object getElementData(String id) {
		Object result = null;

		result = BOSUuid.read(id).getType().toString();
		return result;
	}

	/**
	 * 获取元素的Number内容
	 */
	protected String getPK(Object obj) {
		Object data = null;
		String result = null;

		if (isBlank(obj)) {
			return null;
		}

		if (obj instanceof KDPromptBox) {
			data = ((KDPromptBox) obj).getData();
			if (!isBlank(data)) {
				if (data instanceof DataBaseInfo) {
					result = ((DataBaseInfo) data).getId().toString();
				} else if (data instanceof CoreBillBaseInfo) {
					result = ((CoreBillBaseInfo) data).getId().toString();
				} else if (data instanceof Object[]) {
					result = getPKList((Object[]) data);
				}
			}

		}
		return result;
	}

	/**
	 * 描述：增加元素的校验项
	 * 
	 * @param item
	 * @author:paul 创建时间：2006-10-17
	 *              <p>
	 */
	public void addVerifyItem(VerifyItem item) {
		if (verifyItems == null) {
			verifyItems = new ArrayList();
		}
		verifyItems.add(item);
	}

	/**
	 * 描述：校验元素内容
	 * 
	 * @return
	 * @author:paul 创建时间：2006-10-17
	 *              <p>
	 */
	protected boolean verify() {
		VerifyItem item = null;

		if (verifyItems != null) {
			Iterator iter = verifyItems.iterator();
			while (iter.hasNext()) {
				item = (VerifyItem) iter.next();
				if (!verify(item)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 描述:判断元素的数据是否为空
	 */
	protected boolean isNull(Component component) {
		Object data = this.getData(component);
		if (data == null
				|| (data instanceof Object[] && (((Object[]) data).length == 0 || ((Object[]) data)[0] == null))
				|| this.getNumber(component).trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
