/*
 * @(#)ITotalCaltulater
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 * 描述: 汇总计算器
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public interface ITotalCaltulater {

	/**
	 * 
	 * 描述：分组汇总
	 * 
	 * @param detail
	 *            待汇总的明细记录（包括Group By分组值.）
	 * @throws Exception
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	void groupBy(Map detail) throws Exception;

	/**
	 * 
	 * 描述：汇总记录与Join Map左连接
	 * 
	 * @param detail
	 *            与汇总记录左连接的记录（需包括条件Join值.）
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	void leftJoin(Map detail);

	/**
	 * 
	 * 描述：汇总记录与Join Map进行合并. 匹配记录做连接，不匹配记录做合并, 合并到汇总记录集合中.
	 * 
	 * @author:daij 创建时间：2007-12-14
	 *              <p>
	 * @throws Exception
	 */
	void merge() throws Exception;

	/**
	 * 
	 * 描述：迭代分组汇总记录
	 * 
	 * @return Iterator
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	Iterator iterator();

	/**
	 * 
	 * 描述：返回指定汇总记录
	 * 
	 * @param totalKey
	 *            Group By分组值.
	 * @return Map
	 * @author:daij 创建时间：2007-12-7
	 *              <p>
	 */
	Map record(Map totalKey);

	/**
	 * 
	 * 描述：清除所有汇总记录
	 * 
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	void clear();

	/**
	 * 
	 * 描述：删除指定汇总记录
	 * 
	 * @param totalKey
	 *            Group By分组值.
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	void remove(Map totalKey);

	/**
	 * 
	 * 描述：更新指定汇总记录.
	 * 
	 * @param detail
	 *            Group By分组值.
	 * @throws Exception
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	void update(Map detail) throws Exception;

	/**
	 * 返回连接关键字
	 * 
	 * @return zhiwei_wang 2008-3-24
	 */
	Map getJoinKey();

	/**
	 * 返回汇总数据
	 * 
	 * @return zhiwei_wang 2008-3-24
	 */
	Map getTotal();
}
