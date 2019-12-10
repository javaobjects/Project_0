/*
 * @(#)IFullInfo.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

/**
 * ����: �涨�ⲿϵͳ����Info����Ľӿ�.
 * 
 * @author daij date:2008-6-19
 *         <p>
 * @version EAS5.4
 */
public interface IFullInfo {

	/**
	 * 
	 * �������ⲿϵͳ�����׼�����Selectors
	 * 
	 * @param selectors
	 *            com.kingdee.bos.metadata.entity.SelectorItemCollection
	 * @author:daij ����ʱ�䣺2008-6-19
	 *              <p>
	 */
	void putExtSelectors(SelectorItemCollection selectors);

	/**
	 * 
	 * �������ⲿϵͳ���������Ķ���Info ְ��: ����ǰ����չInfo����Ŀ�������.
	 * 
	 * @param info
	 *            IObjectValue��ʵ����.
	 * @author:daij ����ʱ�䣺2008-6-19
	 *              <p>
	 */
	void toExtFullInfo(IObjectValue info);
}
