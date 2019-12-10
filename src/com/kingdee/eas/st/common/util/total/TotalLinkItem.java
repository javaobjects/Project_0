/*
 * @(#)TotalLinkItem.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import com.kingdee.bos.dao.IObjectValue;

/**
 * 
 * 描述: 连接对象组合项
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalLinkItem extends AbstractTotalItem {

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
	public TotalLinkItem(Object name, Object value) {
		super(name, value);
	}

	/**
	 * 
	 * 描述：Hash运算
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
