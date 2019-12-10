/*
 * @(#)WriteBackAction.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.action;

import java.io.Serializable;
import com.kingdee.eas.st.common.app.botp.IWriteBackable;

/**
 * ����: ��װ������д������ʱ��Action
 * 
 * @author daij date:2006-11-30
 *         <p>
 * @version EAS5.2.0
 */
public abstract class WriteBackAction extends AbstractAction implements
		Serializable, IWriteBackable {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = -3963382297891787367L;

	/**
	 * ��д����ʵ���ߵĽӿڴ��.
	 */
	protected IWriteBackAction processor = null;

	/**
	 * 
	 * ���������뷴д����ʵ����ʵ��.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBackable#acceptProcessor(com.kingdee.eas.st.common.app.botp.action.IWriteBackAction)
	 */
	public final void acceptProcessor(IWriteBackAction processor) {
		this.processor = processor;
	}
}
