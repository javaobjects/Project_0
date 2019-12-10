/*
 * @(#)TotalCaltulater.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����: ���ܼ�����
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TotalCaltulater implements ITotalCaltulater {

	/**
	 * ���ܲ���
	 */
	private TotalStrategy totalStrategy = null;

	/**
	 * ��������
	 */
	private Map total = new HashMap();

	/**
	 * wangzhiwei 20080324
	 * һ��������map��key��totalһ��������totalItem����С��totalһ�£�value����joinKey���������һ����ֵ
	 */
	private Map join_field_key = new HashMap();

	/**
	 * ��������
	 */
	private Map join = new HashMap();

	/**
	 * ���ӹؼ���
	 */
	private Map joinKey = new HashMap();

	/**
	 * �����ӵļ�¼
	 */
	private Map joined = new HashMap();

	/**
	 * ���ܼ�����.
	 */
	private TotalCaltulater.ITotal[] caltulater = null;

	/**
	 * �Ƿ���Ҫ����.
	 */
	private boolean isNeedJoin = false;

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param totalStrategy
	 *            ���ܲ���
	 * @author:daij ����ʱ�䣺2007-12-4
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
	 * �������������
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

	// �������ʵ��
	private void groupBy(AbstractTotalItem totalItem, Map detail)
			throws Exception {
		if (totalItem != null && detail != null) {
			if (total.containsKey(totalItem)) {
				// ����
				Object o = total.get(totalItem);
				if (o instanceof Map) {
					// �����м�¼
					Map dt = (Map) o;

					Iterator itor = detail.keySet().iterator();
					while (itor.hasNext()) {
						// ��ֵKey
						o = itor.next();
						groupBy(totalItem, dt, o, detail.get(o));
					}
				}
			} else {
				// ����
				total.put(totalItem, detail);
				join_field_key.put(totalItem, getJoinFieldKey(detail));
			}
			Map dd = (Map) total.get(totalItem);
			// ������������м�¼
			totalItem.to(dd);
			// �����м�¼��JOIN�ؼ���.
			if (totalStrategy != null) {
				AbstractTotalItem it = totalStrategy.createJoinItem(dd);
				if (it != null) {
					joinKey.put(totalItem, it);
					// ��Ҫ��������.
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

	// ����
	private void groupBy(AbstractTotalItem totalItem, Map dt, Object key,
			Object right) throws Exception {
		// ���Ƿ����������.
		if (!totalItem.isTotalItem(key)) {
			Object result = null;
			if (dt.containsKey(key)) {
				// ���ܾ�ֵ
				Object left = dt.get(key);
				// ������ֵ
				for (int i = 0, size = caltulater.length; i < size; i++) {
					result = ((TotalCaltulater.ITotal) caltulater[i]).total(
							left, right);
					if (result != null)
						break;
				}
			}
			// ���»��ܼ�¼
			if (result != null) {
				dt.put(key, result);
			}
		}
	}

	/**
	 * 
	 * ���������ܼ�¼��Join Map������
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
				// ��Ҫ��������.
				this.isNeedJoin = true;
			}
		}
	}

	/**
	 * 
	 * ���������ܼ�¼��Join Map���кϲ�. ƥ���¼�����ӣ���ƥ���¼���ϲ�, �ϲ������ܼ�¼������.
	 * 
	 * @author:daij
	 * @throws Exception
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#merge()
	 */
	public void merge() throws Exception {
		if (isNeedJoin) {
			// ������
			leftJoin();
		}
		// �ϲ�
		AbstractTotalItem key = null;
		Iterator itor = join.keySet().iterator();
		while (itor.hasNext()) {
			key = (AbstractTotalItem) itor.next();
			// �ϲ������ܼ���
			if (!joined.containsKey(key)) {
				groupBy((Map) join.get(key));
			}
		}
		// �������ӱ�־
		isNeedJoin = false;
	}

	/**
	 * 
	 * ������ָ�����ܼ�¼��Join Map������
	 * 
	 * @param totalItems
	 * @author:daij ����ʱ�䣺2007-12-7
	 *              <p>
	 */
	protected void leftJoin(AbstractTotalItem[] totalItems) {
		if (totalItems != null && totalItems.length > 0) {
			for (int i = 0, size = totalItems.length; i < size; i++) {
				join(totalItems[i]);
			}
		}
	}

	// ������ʵ��
	private void leftJoin() {
		Iterator itor = total.keySet().iterator();
		while (itor.hasNext()) {
			join((AbstractTotalItem) itor.next());
		}
		// �������ӱ�־
		this.isNeedJoin = false;
	}

	// ����
	private void join(AbstractTotalItem totalItem) {
		if (totalItem != null && total.containsKey(totalItem)) {
			Object key = joinKey.get(totalItem);

			Map totalRecord = (Map) total.get(totalItem);
			if (join.containsKey(key)) {
				// ����Join��¼
				totalRecord.putAll((Map) join.get(key));

				int count = 1;
				if (joined.containsKey(key)) {
					count += ((Integer) joined.get(key)).intValue();
				}
				// ����Join���ü���
				joined.put(key, new Integer(count));
			}
		}
	}

	/**
	 * 
	 * ����������������ܼ�¼ ���ܼ�¼ Map = iterator.next
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.total.ITotalCaltulater#iterator()
	 */
	public Iterator iterator() {
		// ���û����������
		if (isNeedJoin) {
			// ������
			leftJoin();
		}
		return total.values().iterator();
	}

	/**
	 * 
	 * ����������ָ�����ܼ�¼
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
	 * ��������ջ��ܽ����¼.
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
	 * ������ɾ�����ܼ�¼
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

	// ɾ�����ܼ�¼ʵ��
	private void remove(AbstractTotalItem totalItem) {
		if (total.containsKey(totalItem)) {
			total.remove(totalItem);

			AbstractTotalItem key = (AbstractTotalItem) joinKey.get(totalItem);
			if (this.joined.containsKey(key)) {
				int count = ((Integer) joined.get(key)).intValue();
				// ����Join���ü���
				if (count == 1) {
					joined.remove(key);
				} else {
					joined.put(key, new Integer(--count));
				}
				// ɾ�����ӹؼ���
				joinKey.remove(totalItem);
			}
		}
	}

	/**
	 * 
	 * ���������»��ܼ�¼
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

	// ���»��ܼ�¼ʵ��
	private void update(AbstractTotalItem totalItem, Map detail)
			throws Exception {
		// ɾ���ɵĻ��ܼ�¼
		this.remove(totalItem);
		// ������ܼ�¼
		this.groupBy(totalItem, detail);
		// ����ָ���Ļ��ܼ�¼
		this.leftJoin(new AbstractTotalItem[] { totalItem });
	}

	/**
	 * 
	 * ��������ȡ���ܲ���
	 * 
	 * @return TotalStrategy ���ܲ���
	 * @author:daij ����ʱ�䣺2007-12-3
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
	 * ����: ������
	 * 
	 * @author daij date:2007-12-6
	 *         <p>
	 * @version EAS5.4
	 */
	private interface ITotal {
		/**
		 * 
		 * ���������ܼ���
		 * 
		 * @param left
		 *            ��ֵ
		 * @param right
		 *            ��ֵ
		 * @return result ���
		 * @throws Exception
		 * @author:daij ����ʱ�䣺2007-12-6
		 *              <p>
		 */
		Object total(Object left, Object right) throws Exception;
	}

	/**
	 * 
	 * ����:��ֵ���ܼ�����
	 * 
	 * @author daij date:2007-12-6
	 *         <p>
	 * @version EAS5.4
	 */
	private class NumberTotalCaltulater implements ITotal {

		// �������ʶ��
		private static final String VAR_LEFT = "left";
		// �ұ�����ʶ��
		private static final String VAR_RIGHT = "right";
		// �����ʶ��.
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
	 * ����: ����ֵ���ܼ�����
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
