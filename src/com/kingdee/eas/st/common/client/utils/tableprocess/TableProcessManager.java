/*
 * @(#)TableProcessManager.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import java.util.ArrayList;
import java.util.List;

public class TableProcessManager {

	List tableChangeProcessor = new ArrayList();

	/**
	 * 
	 * ����: �����иı䴦����
	 * 
	 * @param processor
	 *            : ��������ϴ�����
	 * @author: colin_xu ����ʱ��:2007-6-10
	 *          <p>
	 */
	public void addProcessor(TableChangeProcessor processor) {
		tableChangeProcessor.add(processor);
	}

	/**
	 * 
	 * ����: �Ƴ��иı䴦����
	 * 
	 * @param processor
	 *            : ��������ϴ�����
	 * @author: colin_xu ����ʱ��:2007-6-10
	 *          <p>
	 */
	public void removeProcessor(TableChangeProcessor processor) {
		tableChangeProcessor.remove(processor);
	}

	/**
	 * 
	 * ����: ����иı䴦����
	 * 
	 * @author: colin_xu ����ʱ��:2007-6-10
	 *          <p>
	 */
	public void clear() {
		tableChangeProcessor.clear();
	}

}
