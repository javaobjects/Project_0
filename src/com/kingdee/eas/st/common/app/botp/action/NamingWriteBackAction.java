/*
 * @(#)NamingWriteBackAcition.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.action;

import java.io.Serializable;

/**
 * ����: ��װ�����ķ�д����
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public final class NamingWriteBackAction extends AbstractAction implements
		Serializable {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = 5796923672146472855L;

	/**
	 * Action������.
	 */
	private String name = NamingWriteBackAction.class.getName();

	/**
	 * 
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	private NamingWriteBackAction() {
		super();
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param actionName
	 *            Action������
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public NamingWriteBackAction(String actionName) {
		super();

		this.name = actionName;
	}

	/**
	 * ����:@return ���� name��
	 */
	public String name() {
		return name;
	}

	/**
	 * ���������Action������.
	 * 
	 * @author:daij
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name();
	}
}
