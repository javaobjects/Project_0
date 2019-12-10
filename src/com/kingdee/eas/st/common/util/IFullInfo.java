/*
 * @(#)IFullInfo.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

/**
 * 描述: 规定外部系统扩充Info对象的接口.
 * 
 * @author daij date:2008-6-19
 *         <p>
 * @version EAS5.4
 */
public interface IFullInfo {

	/**
	 * 
	 * 描述：外部系统扩充标准对象的Selectors
	 * 
	 * @param selectors
	 *            com.kingdee.bos.metadata.entity.SelectorItemCollection
	 * @author:daij 创建时间：2008-6-19
	 *              <p>
	 */
	void putExtSelectors(SelectorItemCollection selectors);

	/**
	 * 
	 * 描述：外部系统扩充完整的对象Info 职责: 将当前的扩展Info放入目标对象中.
	 * 
	 * @param info
	 *            IObjectValue的实现类.
	 * @author:daij 创建时间：2008-6-19
	 *              <p>
	 */
	void toExtFullInfo(IObjectValue info);
}
