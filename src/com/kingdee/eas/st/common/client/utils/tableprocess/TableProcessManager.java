/*
 * @(#)TableProcessManager.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import java.util.ArrayList;
import java.util.List;

public class TableProcessManager {

	List tableChangeProcessor = new ArrayList();

	/**
	 * 
	 * 描述: 加入列改变处理器
	 * 
	 * @param processor
	 *            : 多基础资料处理器
	 * @author: colin_xu 创建时间:2007-6-10
	 *          <p>
	 */
	public void addProcessor(TableChangeProcessor processor) {
		tableChangeProcessor.add(processor);
	}

	/**
	 * 
	 * 描述: 移除列改变处理器
	 * 
	 * @param processor
	 *            : 多基础资料处理器
	 * @author: colin_xu 创建时间:2007-6-10
	 *          <p>
	 */
	public void removeProcessor(TableChangeProcessor processor) {
		tableChangeProcessor.remove(processor);
	}

	/**
	 * 
	 * 描述: 清空列改变处理器
	 * 
	 * @author: colin_xu 创建时间:2007-6-10
	 *          <p>
	 */
	public void clear() {
		tableChangeProcessor.clear();
	}

}
