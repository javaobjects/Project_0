/*
 * @(#)TotalStringItem.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.total;

/**
 * 
 * ����: �ַ��������
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalStringItem extends AbstractTotalItem {

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
	public TotalStringItem(Object name, Object value) {
		super(name, value);
	}

	/**
	 * 
	 * ������ʵ��Hash����.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.AbstractTotalItem#hash(int)
	 */
	protected void hash() {
		if (this.itemValue != null) {
			this.hashCode += this.itemValue.toString().hashCode();
		}
	}

}
