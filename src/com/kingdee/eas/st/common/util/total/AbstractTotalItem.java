/*
 * @(#)AbstractTotalItem.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.Map;

/**
 * 
 * ����: ������.
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public abstract class AbstractTotalItem {

	/**
	 * hashCode��ʼֵ.
	 */
	protected int hashCode = 0;

	/**
	 * �Ƿ��ѽ���hash����
	 */
	private boolean isHash = false;

	/**
	 * ������ʶ
	 */
	protected Object itemName = null;

	/**
	 * �����ֵ
	 */
	protected Object itemValue = null;

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param name
	 *            ������ʶ
	 * @param value
	 *            �����ֵ
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	public AbstractTotalItem(Object name, Object value) {
		super();

		this.itemName = name;

		this.itemValue = value;
	}

	/**
	 * 
	 * ����������HashCode����
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
	 * ������Hash����ʵ�֣��������������ʵ�֡�
	 * 
	 * @param init
	 *            hashcode��ʼֵ
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	protected abstract void hash();

	/**
	 * 
	 * ��������������ж�.
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
	 * �������Ƿ����������ؼ���
	 * 
	 * @param key
	 *            ���ж�����.
	 * @return boolean
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * �������Ƿ�Ϸ��������ؼ���.
	 * 
	 * @param totalKey
	 *            �����ؼ���
	 * @return boolean
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * ��������ȷ����������ַ����ָ����ָ��ַ���.
	 * 
	 * @param totalKey
	 *            �����ؼ���
	 * @return String[]
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * �����������ֵ�����ָ��Map��¼��.
	 * 
	 * @param detail
	 *            ָ��Map��¼
	 * @author:daij ����ʱ�䣺2007-12-6
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
