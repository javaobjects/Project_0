/*
 * @(#)TotalCaltulater.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.kscript.ParserException;
import com.kingdee.bos.kscript.runtime.Interpreter;
import com.kingdee.bos.kscript.runtime.InterpreterException;

/**
 * 
 * 描述: 汇总计算器
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalCaltulater implements ITotalCaltulater {

	/**
	 * 汇总策略
	 */
	private TotalStrategy totalStrategy = null;

	/**
	 * 汇总数据
	 */
	private Map total = new HashMap();

	/**
	 * wangzhiwei 20080324
	 * 一个独立的map，key与total一样，都是totalItem，大小与total一致，value是由joinKey计算出来的一个数值
	 */
	private Map join_field_key = new HashMap();

	/**
	 * 连接数据
	 */
	private Map join = new HashMap();

	/**
	 * 连接关键字
	 */
	private Map joinKey = new HashMap();

	/**
	 * 已连接的记录
	 */
	private Map joined = new HashMap();

	/**
	 * 汇总计算器.
	 */
	private TotalCaltulater.ITotal[] caltulater = null;

	/**
	 * 是否需要连接.
	 */
	private boolean isNeedJoin = false;

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param totalStrategy
	 *            汇总策略
	 * @author:daij 创建时间：2007-12-4
	 *              <p>
	 */
	public TotalCaltulater(TotalStrategy totalStrategy) {
		super();

		this.totalStrategy = totalStrategy;

		caltulater = new TotalCaltulater.ITotal[] {
				new TotalCaltulater.NumberTotalCaltulater(),
				new TotalCaltulater.NonNumberTotalCaltulater() };
	}

	/**
	 * 
	 * 描述：分组汇总
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#groupBy(java.util.Map)
	 */
	public void groupBy(Map detail) throws Exception {
		if (this.totalStrategy != null) {
			Map dt = totalStrategy.defaultValues(detail);
			groupBy(totalStrategy.createTotalItem(dt), dt);
		}
	}

	// 分组汇总实现
	private void groupBy(AbstractTotalItem totalItem, Map detail)
			throws Exception {
		if (totalItem != null && detail != null) {
			if (total.containsKey(totalItem)) {
				// 叠加
				Object o = total.get(totalItem);
				if (o instanceof Map) {
					// 汇总行记录
					Map dt = (Map) o;

					Iterator itor = detail.keySet().iterator();
					while (itor.hasNext()) {
						// 新值Key
						o = itor.next();
						groupBy(totalItem, dt, o, detail.get(o));
					}
				}
			} else {
				// 新增
				total.put(totalItem, detail);
				join_field_key.put(totalItem, getJoinFieldKey(detail));
			}
			Map dd = (Map) total.get(totalItem);
			// 将汇总项放入行记录
			totalItem.to(dd);
			// 计算行记录的JOIN关键字.
			if (totalStrategy != null) {
				AbstractTotalItem it = totalStrategy.createJoinItem(dd);
				if (it != null) {
					joinKey.put(totalItem, it);
					// 需要重新连接.
					isNeedJoin = true;
				}
			}
		}
	}

	private Long getJoinFieldKey(Map detail) {
		long key = 0;

		// for(int j = 0; j < joinFields.length; j++){
		// Object o0 = KDTableUtils.getFieldValue(mergeTable, i, joinFields[j]);
		// if(o0 != null){
		// key = key + TotalUtils.getHashCode(o0);
		// }
		// }
		return new Long(key);
	}

	// 汇总
	private void groupBy(AbstractTotalItem totalItem, Map dt, Object key,
			Object right) throws Exception {
		// 不是分组项则汇总.
		if (!totalItem.isTotalItem(key)) {
			Object result = null;
			if (dt.containsKey(key)) {
				// 汇总旧值
				Object left = dt.get(key);
				// 汇总新值
				for (int i = 0, size = caltulater.length; i < size; i++) {
					result = ((TotalCaltulater.ITotal) caltulater[i]).total(
							left, right);
					if (result != null)
						break;
				}
			}
			// 更新汇总记录
			if (result != null) {
				dt.put(key, result);
			}
		}
	}

	/**
	 * 
	 * 描述：汇总记录与Join Map左连接
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#join(java.util.Map)
	 */
	public void leftJoin(Map detail) {
		if (this.totalStrategy != null) {
			Map dt = totalStrategy.defaultValues(detail);
			AbstractTotalItem key = totalStrategy.createJoinItem(dt);
			if (key != null) {
				this.join.put(key, dt);
				// 需要重新连接.
				this.isNeedJoin = true;
			}
		}
	}

	/**
	 * 
	 * 描述：汇总记录与Join Map进行合并. 匹配记录做连接，不匹配记录做合并, 合并到汇总记录集合中.
	 * 
	 * @author:daij
	 * @throws Exception
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#merge()
	 */
	public void merge() throws Exception {
		if (isNeedJoin) {
			// 左连接
			leftJoin();
		}
		// 合并
		AbstractTotalItem key = null;
		Iterator itor = join.keySet().iterator();
		while (itor.hasNext()) {
			key = (AbstractTotalItem) itor.next();
			// 合并到汇总集合
			if (!joined.containsKey(key)) {
				groupBy((Map) join.get(key));
			}
		}
		// 反置连接标志
		isNeedJoin = false;
	}

	/**
	 * 
	 * 描述：指定汇总记录与Join Map左连接
	 * 
	 * @param totalItems
	 * @author:daij 创建时间：2007-12-7
	 *              <p>
	 */
	protected void leftJoin(AbstractTotalItem[] totalItems) {
		if (totalItems != null && totalItems.length > 0) {
			for (int i = 0, size = totalItems.length; i < size; i++) {
				join(totalItems[i]);
			}
		}
	}

	// 左连接实现
	private void leftJoin() {
		Iterator itor = total.keySet().iterator();
		while (itor.hasNext()) {
			join((AbstractTotalItem) itor.next());
		}
		// 反置连接标志
		this.isNeedJoin = false;
	}

	// 连接
	private void join(AbstractTotalItem totalItem) {
		if (totalItem != null && total.containsKey(totalItem)) {
			Object key = joinKey.get(totalItem);

			Map totalRecord = (Map) total.get(totalItem);
			if (join.containsKey(key)) {
				// 填入Join记录
				totalRecord.putAll((Map) join.get(key));

				int count = 1;
				if (joined.containsKey(key)) {
					count += ((Integer) joined.get(key)).intValue();
				}
				// 重算Join引用计数
				joined.put(key, new Integer(count));
			}
		}
	}

	/**
	 * 
	 * 描述：迭代分组汇总记录 汇总记录 Map = iterator.next
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#iterator()
	 */
	public Iterator iterator() {
		// 如果没有做过连接
		if (isNeedJoin) {
			// 左连接
			leftJoin();
		}
		return total.values().iterator();
	}

	/**
	 * 
	 * 描述：返回指定汇总记录
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#record(java.util.Map)
	 */
	public Map record(Map totalKey) {
		Map record = null;
		if (this.totalStrategy != null) {
			Map dt = totalStrategy.defaultValues(totalKey);
			AbstractTotalItem key = totalStrategy.createTotalItem(dt);

			if (total.containsKey(key)) {
				if (isNeedJoin) {
					leftJoin(new AbstractTotalItem[] { key });
				}
				record = (Map) total.get(key);
			}
		}
		return record;
	}

	/**
	 * 
	 * 描述：清空汇总结果记录.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#clear()
	 */
	public void clear() {

		this.total.clear();

		this.join.clear();

		this.joinKey.clear();

		this.joined.clear();
	}

	/**
	 * 
	 * 描述：删除汇总记录
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#remove(java.util.Map)
	 */
	public void remove(Map totalKey) {
		if (this.totalStrategy != null) {
			Map dt = totalStrategy.defaultValues(totalKey);
			this.remove(totalStrategy.createTotalItem(dt));
		}
	}

	// 删除汇总记录实现
	private void remove(AbstractTotalItem totalItem) {
		if (total.containsKey(totalItem)) {
			total.remove(totalItem);

			AbstractTotalItem key = (AbstractTotalItem) joinKey.get(totalItem);
			if (this.joined.containsKey(key)) {
				int count = ((Integer) joined.get(key)).intValue();
				// 清理Join引用计数
				if (count == 1) {
					joined.remove(key);
				} else {
					joined.put(key, new Integer(--count));
				}
				// 删除连接关键字
				joinKey.remove(totalItem);
			}
		}
	}

	/**
	 * 
	 * 描述：更新汇总记录
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#update(java.util.Map)
	 */
	public void update(Map detail) throws Exception {
		if (this.totalStrategy != null) {
			Map dt = totalStrategy.defaultValues(detail);
			this.update(totalStrategy.createTotalItem(dt), dt);
		}
	}

	// 更新汇总记录实现
	private void update(AbstractTotalItem totalItem, Map detail)
			throws Exception {
		// 删除旧的汇总记录
		this.remove(totalItem);
		// 放入汇总记录
		this.groupBy(totalItem, detail);
		// 连接指定的汇总记录
		this.leftJoin(new AbstractTotalItem[] { totalItem });
	}

	/**
	 * 
	 * 描述：读取汇总策略
	 * 
	 * @return TotalStrategy 汇总策略
	 * @author:daij 创建时间：2007-12-3
	 *              <p>
	 */
	public TotalStrategy getTotalStrategy() {
		return totalStrategy;
	}

	public Map getJoinKey() {
		return joinKey;
	}

	public Map getTotal() {
		return total;
	}

	/**
	 * 
	 * 描述: 汇总器
	 * 
	 * @author daij date:2007-12-6
	 *         <p>
	 * @version EAS5.4
	 */
	private interface ITotal {
		/**
		 * 
		 * 描述：汇总计算
		 * 
		 * @param left
		 *            左值
		 * @param right
		 *            右值
		 * @return result 结果
		 * @throws Exception
		 * @author:daij 创建时间：2007-12-6
		 *              <p>
		 */
		Object total(Object left, Object right) throws Exception;
	}

	/**
	 * 
	 * 描述:数值汇总计算器
	 * 
	 * @author daij date:2007-12-6
	 *         <p>
	 * @version EAS5.4
	 */
	private class NumberTotalCaltulater implements ITotal {

		// 左变量标识串
		private static final String VAR_LEFT = "left";
		// 右变量标识串
		private static final String VAR_RIGHT = "right";
		// 运算标识串.
		private static final String PATTERN_STRING = "left + right";

		public Object total(Object left, Object right) throws Exception {
			Object result = null;
			if (left instanceof Number || right instanceof Number) {
				result = (left instanceof Number) ? left : right;
			}

			if (left instanceof Number && right instanceof Number) {
				Map params = new HashMap();

				params.put(VAR_LEFT, left);

				params.put(VAR_RIGHT, right);

				try {
					result = new Interpreter().eval(PATTERN_STRING, params);
				} catch (InterpreterException e) {
					throw e;
				} catch (ParserException e) {
					throw e;
				}
			}
			return result;
		}
	}

	/**
	 * 
	 * 描述: 非数值汇总计算器
	 * 
	 * @author daij date:2007-12-6
	 *         <p>
	 * @version EAS5.4
	 */
	private class NonNumberTotalCaltulater implements ITotal {

		public Object total(Object left, Object right) throws Exception {
			Object result = null;
			if (!(left instanceof Number && right instanceof Number)) {
				if (left != null || right != null) {
					result = (left != null) ? left : right;
				}
			}
			return result;
		}
	}

}
