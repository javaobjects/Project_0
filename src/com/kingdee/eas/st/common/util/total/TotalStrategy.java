/*
 * @(#)TotalStrategy.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * ����: ���ܲ���
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalStrategy {

	/**
	 * ������ܹؼ���
	 */
	private static final Integer TOTAL_GROUPBY = new Integer("1");

	/**
	 * ���������ؼ���
	 */
	private static final Integer TOTAL_JOIN = new Integer("2");

	/**
	 * ���ܲ����б�
	 */
	private Map strategy = new HashMap();

	/**
	 * 
	 * ������ָ��������ܺ������������Ա�.
	 * 
	 * @param groupKey
	 *            ����������Ա�
	 * @param joinKey
	 *            �����������Ա�
	 * @author:daij ����ʱ�䣺2007-12-7
	 *              <p>
	 */
	public void putAnalyseString(String[] groupKey, String[] joinKey) {

		// ���ܷ��鴮
		if (TotalItem.isEffectiveTotalItem(groupKey)) {
			this.strategy.put(TOTAL_GROUPBY, groupKey);
		}
		// ����������
		if (TotalItem.isEffectiveTotalItem(joinKey)) {
			strategy.put(TOTAL_JOIN, joinKey);
		}
	}

	/**
	 * 
	 * ������ָ��������ܺ������������Ա�.
	 * 
	 * @param groupKey
	 *            ����������Ա�
	 * @param joinKey
	 *            �����������Ա�
	 * @author:daij ����ʱ�䣺2007-12-7
	 *              <p>
	 */
	public void putAnalyseString(String groupKey, String joinKey) {

		// ���ܷ��鴮
		if (TotalItem.isEffectiveTotalItem(groupKey)) {
			this.strategy.put(TOTAL_GROUPBY, TotalItem
					.splitTotalItemString(groupKey));
		}
		// ����������
		if (TotalItem.isEffectiveTotalItem(joinKey)) {
			strategy.put(TOTAL_JOIN, TotalItem.splitTotalItemString(joinKey));
		}
	}

	/**
	 * 
	 * ������ָ��Ĭ��ֵ.
	 * 
	 * @param key
	 *            ������
	 * @param o
	 *            Ĭ��ֵ
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * ������ָ��Ĭ��ֵ
	 * 
	 * @param defaultValues
	 *            Ĭ��ֵ�б�
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	public void putDefaultValue(Map defaultValues) {
		this.strategy.putAll(defaultValues);
	}

	/**
	 * 
	 * ��������ȡĬ��ֵ
	 * 
	 * @param detail
	 *            ԭֵ�б�
	 * @return Ĭ��ֵ������ֵ�б�.
	 * @author:daij ����ʱ�䣺2007-12-4
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
	 * ��������ȡĬ��ֵ
	 * 
	 * @param key
	 *            ������
	 * @return Ĭ��ֵ
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	public Object defaultValue(String key) {
		return strategy.get(key);
	}

	/**
	 * 
	 * ������������������Ա��������
	 * 
	 * @param detail
	 *            �����������ֵ��
	 * @return AbstractTotalItem
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	public AbstractTotalItem createTotalItem(Map detail) {
		return TotalItem.instance((String[]) strategy.get(TOTAL_GROUPBY),
				detail);
	}

	/**
	 * 
	 * ������������������Ա��������
	 * 
	 * @param detail
	 *            �����������ֵ��
	 * @return AbstractTotalItem
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	public AbstractTotalItem createJoinItem(Map detail) {
		return TotalItem.instance((String[]) strategy.get(TOTAL_JOIN), detail);
	}
}
