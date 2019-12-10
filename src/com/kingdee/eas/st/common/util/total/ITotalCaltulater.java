/*
 * @(#)ITotalCaltulater
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.total;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 * ����: ���ܼ�����
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public interface ITotalCaltulater {

	/**
	 * 
	 * �������������
	 * 
	 * @param detail
	 *            �����ܵ���ϸ��¼������Group By����ֵ.��
	 * @throws Exception
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	void groupBy(Map detail) throws Exception;

	/**
	 * 
	 * ���������ܼ�¼��Join Map������
	 * 
	 * @param detail
	 *            ����ܼ�¼�����ӵļ�¼�����������Joinֵ.��
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	void leftJoin(Map detail);

	/**
	 * 
	 * ���������ܼ�¼��Join Map���кϲ�. ƥ���¼�����ӣ���ƥ���¼���ϲ�, �ϲ������ܼ�¼������.
	 * 
	 * @author:daij ����ʱ�䣺2007-12-14
	 *              <p>
	 * @throws Exception
	 */
	void merge() throws Exception;

	/**
	 * 
	 * ����������������ܼ�¼
	 * 
	 * @return Iterator
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	Iterator iterator();

	/**
	 * 
	 * ����������ָ�����ܼ�¼
	 * 
	 * @param totalKey
	 *            Group By����ֵ.
	 * @return Map
	 * @author:daij ����ʱ�䣺2007-12-7
	 *              <p>
	 */
	Map record(Map totalKey);

	/**
	 * 
	 * ������������л��ܼ�¼
	 * 
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	void clear();

	/**
	 * 
	 * ������ɾ��ָ�����ܼ�¼
	 * 
	 * @param totalKey
	 *            Group By����ֵ.
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	void remove(Map totalKey);

	/**
	 * 
	 * ����������ָ�����ܼ�¼.
	 * 
	 * @param detail
	 *            Group By����ֵ.
	 * @throws Exception
	 * @author:daij ����ʱ�䣺2007-12-6
	 *              <p>
	 */
	void update(Map detail) throws Exception;

	/**
	 * �������ӹؼ���
	 * 
	 * @return zhiwei_wang 2008-3-24
	 */
	Map getJoinKey();

	/**
	 * ���ػ�������
	 * 
	 * @return zhiwei_wang 2008-3-24
	 */
	Map getTotal();
}
