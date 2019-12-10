/*
 * @(#)AbstractTotalItem.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.Map;

/**
 * 
 * 描述: 组合项定义.
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public abstract class AbstractTotalItem {

	/**
	 * hashCode初始值.
	 */
	protected int hashCode = 0;

	/**
	 * 是否已进行hash运算
	 */
	private boolean isHash = false;

	/**
	 * 组合项标识
	 */
	protected Object itemName = null;

	/**
	 * 组合项值
	 */
	protected Object itemValue = null;

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param name
	 *            组合项标识
	 * @param value
	 *            组合项值
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public AbstractTotalItem(Object name, Object value) {
		super();

		this.itemName = name;

		this.itemValue = value;
	}

	/**
	 * 
	 * 描述：覆盖HashCode计算
	 * 
	 * @author:daij
	 * @see java.lang.Object#hashCode()
	 */
	public final int hashCode() {
		if (!isHash) {
			hash();
			isHash = true;
		}
		return hashCode;
	}

	/**
	 * 
	 * 描述：Hash运算实现，各组合项子类需实现。
	 * 
	 * @param init
	 *            hashcode初始值
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	protected abstract void hash();

	/**
	 * 
	 * 描述：覆盖相等判定.
	 * 
	 * @author:daij
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof AbstractTotalItem) {
			return (hashCode() == ((AbstractTotalItem) o).hashCode());
		} else {
			return super.equals(o);
		}
	}

	/**
	 * 
	 * 描述：是否属于组合项关键字
	 * 
	 * @param key
	 *            待判定对象.
	 * @return boolean
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public final boolean isTotalItem(Object key) {
		boolean isTrue = false;
		if (this.itemName instanceof Map) {
			isTrue = ((Map) this.itemName).containsKey(key);
		} else if (itemName instanceof String) {
			isTrue = this.itemName.toString().equals(key.toString());
		}
		return isTrue;
	}

	/**
	 * 
	 * 描述：是否合法的组合项关键字.
	 * 
	 * @param totalKey
	 *            组合项关键字
	 * @return boolean
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public final static boolean isEffectiveTotalItem(Object totalKey) {
		boolean isTrue = false;

		Object o = totalKey;
		if (o instanceof String) {
			o = splitTotalItemString(o.toString());
		}

		if (o instanceof String[]) {
			isTrue = (((String[]) o).length > 0);
		}
		return isTrue;
	}

	/**
	 * 
	 * 描述：按确定的组合项字符串分隔符分割字符串.
	 * 
	 * @param totalKey
	 *            组合项关键字
	 * @return String[]
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	protected static String[] splitTotalItemString(String totalKey) {
		String[] ls = null;
		if (totalKey != null && totalKey.length() > 0) {
			ls = totalKey.split(",");
		}
		return ls;
	}

	/**
	 * 
	 * 描述：组合项值输出到指定Map记录中.
	 * 
	 * @param detail
	 *            指定Map记录
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	protected final void to(Map detail) {
		Object[] ls = null;
		if (detail != null) {
			if (itemValue instanceof Object[]) {
				ls = (Object[]) itemValue;

				AbstractTotalItem item = null;
				for (int i = 0, size = ls.length; i < size; i++) {
					if (ls[i] instanceof AbstractTotalItem) {
						item = (AbstractTotalItem) ls[i];
						if (!detail.containsKey(item.itemName)) {
							detail.put(item.itemName, item.itemValue);
						}
					}
				}
			} else {
				if (!detail.containsKey(itemName)) {
					detail.put(itemName, itemValue);
				}
			}
		}
	}
}
