/*
 * @(#)TotalItem.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.util.enums.Enum;

/**
 * 
 * 描述: 组合项
 * 
 * @author daij date:2007-12-3
 *         <p>
 * @version EAS5.4
 */
public class TotalItem extends AbstractTotalItem {

	/**
	 * 非空值列表
	 */
	private Map noIsNull = new HashMap();

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
	private TotalItem(Object name, Object value) {
		super(name, value);

		to(noIsNull);
	}

	/**
	 * 
	 * 描述：明细组合项的键值是否为NULL
	 * 
	 * @param detailkey
	 *            组合项的键串
	 * @return boolean
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public boolean isDetialNull(Object detailkey) {
		return (!this.noIsNull.containsKey(detailkey));
	}

	/**
	 * 
	 * 描述：获取明细组合项的键值
	 * 
	 * @param detailkey
	 *            组合项的键串
	 * @return Object
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public Object getDetail(Object detailkey) {
		return this.noIsNull.get(detailkey);
	}

	/**
	 * 
	 * 描述：组合项实例
	 * 
	 * @param values
	 *            组合项键值对
	 * @return AbstractTotalItem
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public static AbstractTotalItem instance(Map values) {
		AbstractTotalItem t = null;
		if (values != null) {
			String[] keys = (String[]) values.keySet().toArray(
					new String[values.size()]);
			t = instance(keys, values);
		}
		return t;
	}

	/**
	 * 
	 * 描述：组合项实例
	 * 
	 * @param keys
	 *            组合项键串表
	 * @param values
	 *            组合项键值对
	 * @return AbstractTotalItem
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public static AbstractTotalItem instance(String[] keys, Map values) {
		AbstractTotalItem t = null;

		Map names = null;
		if (keys != null && keys.length != 0 && values != null) {

			String key = null;
			List vs = new ArrayList();
			names = new HashMap();
			for (int i = 0, size = keys.length; i < size; i++) {
				key = keys[i];
				if (key.length() != 0) {
					names.put(key, null);
					if (values.get(key) != null) {
						vs.add(constructTotalItem(key, values.get(key)));
					}
				}
			}
			if (vs.size() != 0) {
				t = new TotalItem(names, vs.toArray());
			}
		}
		return t;
	}

	/**
	 * 
	 * 描述：组合项实例
	 * 
	 * @param totalItemString
	 *            组合项键串表
	 * @param values
	 *            组合项键值对
	 * @return AbstractTotalItem
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public static AbstractTotalItem instance(String totalItemString, Map values) {

		AbstractTotalItem t = null;
		if (totalItemString != null && totalItemString.length() != 0) {
			t = instance(splitTotalItemString(totalItemString), values);
		}
		return t;
	}

	/**
	 * 
	 * 描述：构造组织项实例
	 * 
	 * @param key
	 *            组合项键串
	 * @param o
	 *            组合项键值
	 * @return AbstractTotalItem
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	protected static AbstractTotalItem constructTotalItem(String key, Object o) {
		if (o instanceof IObjectValue) {
			return new TotalLinkItem(key, o);
		} else if (o instanceof Enum) {
			return new TotalEnumItem(key, o);
		} else {
			return new TotalStringItem(key, o);
		}
	}

	/**
	 * 
	 * 描述：Hash运算
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.AbstractTotalItem#hash(int)
	 */
	protected void hash() {
		// Do Nothing 这里覆盖equals方法，hash算法失效 wangzhiwei 20080515
		// Object[] ls = null;
		// if(this.itemValue instanceof Object[]){
		// ls =(Object[])this.itemValue;
		//			
		// for(int i=0,size = ls.length;i < size;i++){
		// this.hashCode += ls[i].hashCode();
		// }
		// }
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		Object[] ls = null;
		if (this.itemValue instanceof Object[]) {
			ls = (Object[]) this.itemValue;
			for (int i = 0, size = ls.length; i < size; i++) {
				sb.append(String.valueOf(i) + "="
						+ String.valueOf(ls[i].hashCode()));
			}
		}
		return sb.toString();
	}

	// 修改hash算法，避免出现 w1+r1+p1+c1 = w1+p1+r1+c1 的情况 wangzhiwei 20080515
	public boolean equals(Object o) {
		if (o instanceof TotalItem) {
			if (this.toString().equals(o.toString())) {
				return true;
			}
		}
		return false;
	}
}
