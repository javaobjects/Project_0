/*
 * @(#)TotalEnumItem.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import com.kingdee.util.enums.Enum;

/**
 * 描述: 枚举组合项
 * 
 * @author daij date:2007-11-30
 *         <p>
 * @version EAS5.4
 */
public class TotalEnumItem extends AbstractTotalItem {
	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = 6497630522622986776L;

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param name
	 *            组合项标识
	 * @param value
	 *            组合项值
	 * @author:daij 创建时间：2007-12-3
	 *              <p>
	 */
	public TotalEnumItem(Object name, Object value) {
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
		if (this.itemValue instanceof Enum) {
			this.hashCode += ((Enum) itemValue).getName().toString().hashCode();
		}
	}
}
