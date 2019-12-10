/*
 * @(#)TotalEnumItem.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.total;

import com.kingdee.util.enums.Enum;

/**
 * ����: ö�������
 * 
 * @author daij date:2007-11-30
 *         <p>
 * @version EAS5.4
 */
public class TotalEnumItem extends AbstractTotalItem {
	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = 6497630522622986776L;

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param name
	 *            ������ʶ
	 * @param value
	 *            �����ֵ
	 * @author:daij ����ʱ�䣺2007-12-3
	 *              <p>
	 */
	public TotalEnumItem(Object name, Object value) {
		super(name, value);
	}

	/**
	 * 
	 * ������Hash����
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.AbstractTotalItem#hash(int)
	 */
	protected void hash() {
		if (this.itemValue instanceof Enum) {
			this.hashCode += ((Enum) itemValue).getName().toString().hashCode();
		}
	}
}
