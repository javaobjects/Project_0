/*
 * @(#)UnActionWriteBackAction.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.action;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

/**
 * ����: �����ʱ��д����.
 * 
 * @author daij date:2006-12-10
 *         <p>
 * @version EAS5.2.0
 */
public class UnAuditWriteBackAction extends WriteBackAction {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = -8671372800498761963L;

	/**
	 * ���������д����.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBackable#fireAction()
	 */
	public void fireAction() throws EASBizException, BOSException {
		this.processor.writeBack(this);
	}
}
