/*
 * @(#)TotalStringItem.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

/**
 * 
 * 描述: 字符串组合项
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalStringItem extends AbstractTotalItem {

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
	public TotalStringItem(Object name, Object value) {
		super(name, value);
	}

	/**
	 * 
	 * 描述：实现Hash运算.
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
