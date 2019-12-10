/*
 * @(#)TotalItem.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����: �����
 * 
 * @author daij date:2007-12-3
 *         <p>
 * @version EAS5.4
 */
public class TotalItem extends AbstractTotalItem {

	/**
	 * �ǿ�ֵ�б�
	 */
	private Map noIsNull = new HashMap();

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
	private TotalItem(Object name, Object value) {
		super(name, value);

		to(noIsNull);
	}

	/**
	 * 
	 * ��������ϸ�����ļ�ֵ�Ƿ�ΪNULL
	 * 
	 * @param detailkey
	 *            �����ļ���
	 * @return boolean
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	public boolean isDetialNull(Object detailkey) {
		return (!this.noIsNull.containsKey(detailkey));
	}

	/**
	 * 
	 * ��������ȡ��ϸ�����ļ�ֵ
	 * 
	 * @param detailkey
	 *            �����ļ���
	 * @return Object
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	public Object getDetail(Object detailkey) {
		return this.noIsNull.get(detailkey);
	}

	/**
	 * 
	 * �����������ʵ��
	 * 
	 * @param values
	 *            ������ֵ��
	 * @return AbstractTotalItem
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * �����������ʵ��
	 * 
	 * @param keys
	 *            ����������
	 * @param values
	 *            ������ֵ��
	 * @return AbstractTotalItem
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * �����������ʵ��
	 * 
	 * @param totalItemString
	 *            ����������
	 * @param values
	 *            ������ֵ��
	 * @return AbstractTotalItem
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * ������������֯��ʵ��
	 * 
	 * @param key
	 *            ��������
	 * @param o
	 *            ������ֵ
	 * @return AbstractTotalItem
	 * @author:daij ����ʱ�䣺2007-12-6
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
	 * ������Hash����
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.AbstractTotalItem#hash(int)
	 */
	protected void hash() {
		// Do Nothing ���︲��equals������hash�㷨ʧЧ wangzhiwei 20080515
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

	// �޸�hash�㷨��������� w1+r1+p1+c1 = w1+p1+r1+c1 ����� wangzhiwei 20080515
	public boolean equals(Object o) {
		if (o instanceof TotalItem) {
			if (this.toString().equals(o.toString())) {
				return true;
			}
		}
		return false;
	}
}
