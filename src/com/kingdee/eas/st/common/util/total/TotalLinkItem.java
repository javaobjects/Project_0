/*
 * @(#)TotalLinkItem.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.total;

import com.kingdee.bos.dao.IObjectValue;

/**
 * 
 * ����: ���Ӷ��������
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalLinkItem extends AbstractTotalItem {

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
	public TotalLinkItem(Object name, Object value) {
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
		if (this.itemValue instanceof IObjectValue) {
			IObjectValue o = (IObjectValue) this.itemValue;

			if (o.containsKey("id") && o.get("id") != null) {
				this.hashCode += (o.get("id").toString().hashCode());
			}
		}
	}
}
