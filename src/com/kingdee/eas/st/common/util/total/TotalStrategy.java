/*
 * @(#)TotalStrategy.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * 描述: 汇总策略
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalStrategy {

	/**
	 * 分组汇总关键字
	 */
	private static final Integer TOTAL_GROUPBY = new Integer("1");

	/**
	 * 条件关联关键字
	 */
	private static final Integer TOTAL_JOIN = new Integer("2");

	/**
	 * 汇总策略列表
	 */
	private Map strategy = new HashMap();

	/**
	 * 
	 * 描述：指定分组汇总和条件关联属性表.
	 * 
	 * @param groupKey
	 *            分组汇总属性表
	 * @param joinKey
	 *            条件关联属性表
	 * @author:daij 创建时间：2007-12-7
	 *              <p>
	 */
	public void putAnalyseString(String[] groupKey, String[] joinKey) {

		// 汇总分组串
		if (TotalItem.isEffectiveTotalItem(groupKey)) {
			this.strategy.put(TOTAL_GROUPBY, groupKey);
		}
		// 条件关联串
		if (TotalItem.isEffectiveTotalItem(joinKey)) {
			strategy.put(TOTAL_JOIN, joinKey);
		}
	}

	/**
	 * 
	 * 描述：指定分组汇总和条件关联属性表.
	 * 
	 * @param groupKey
	 *            分组汇总属性表
	 * @param joinKey
	 *            条件关联属性表
	 * @author:daij 创建时间：2007-12-7
	 *              <p>
	 */
	public void putAnalyseString(String groupKey, String joinKey) {

		// 汇总分组串
		if (TotalItem.isEffectiveTotalItem(groupKey)) {
			this.strategy.put(TOTAL_GROUPBY, TotalItem
					.splitTotalItemString(groupKey));
		}
		// 条件关联串
		if (TotalItem.isEffectiveTotalItem(joinKey)) {
			strategy.put(TOTAL_JOIN, TotalItem.splitTotalItemString(joinKey));
		}
	}

	/**
	 * 
	 * 描述：指定默认值.
	 * 
	 * @param key
	 *            属性名
	 * @param o
	 *            默认值
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public void putDefaultValue(String key, Object o) {
		String[] ls = TotalItem.splitTotalItemString(key);

		if (ls != null && ls.length != 0) {
			for (int i = 0, size = ls.length; i < size; i++) {
				strategy.put(ls[i], o);
			}
		} else {
			strategy.put(key, o);
		}
	}

	/**
	 * 
	 * 描述：指定默认值
	 * 
	 * @param defaultValues
	 *            默认值列表
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public void putDefaultValue(Map defaultValues) {
		this.strategy.putAll(defaultValues);
	}

	/**
	 * 
	 * 描述：获取默认值
	 * 
	 * @param detail
	 *            原值列表
	 * @return 默认值处理后的值列表.
	 * @author:daij 创建时间：2007-12-4
	 *              <p>
	 */
	public Map defaultValues(Map detail) {
		Map result = null;
		if (detail != null) {
			result = new HashMap();

			Object key = null;
			Object value = null;
			Iterator itor = detail.keySet().iterator();
			while (itor.hasNext()) {
				key = itor.next();

				value = detail.get(key);
				if (key != null && value == null) {
					value = defaultValue(key.toString());
				}
				result.put(key, value);
			}
		}
		return result;
	}

	/**
	 * 
	 * 描述：获取默认值
	 * 
	 * @param key
	 *            属性名
	 * @return 默认值
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public Object defaultValue(String key) {
		return strategy.get(key);
	}

	/**
	 * 
	 * 描述：按分组汇总属性表创建组合项
	 * 
	 * @param detail
	 *            分组汇总属性值表
	 * @return AbstractTotalItem
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public AbstractTotalItem createTotalItem(Map detail) {
		return TotalItem.instance((String[]) strategy.get(TOTAL_GROUPBY),
				detail);
	}

	/**
	 * 
	 * 描述：按分组汇总属性表创建组合项
	 * 
	 * @param detail
	 *            分组汇总属性值表
	 * @return AbstractTotalItem
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public AbstractTotalItem createJoinItem(Map detail) {
		return TotalItem.instance((String[]) strategy.get(TOTAL_JOIN), detail);
	}
}
