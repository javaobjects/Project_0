/*
 * �������� 2006-12-3
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.kingdee.eas.st.common.app.botp.action;

import java.io.Serializable;

/**
 * 
 * ����: ��ϵķ�д����Action.
 * 
 * @author daij date:2006-12-4
 *         <p>
 * @version EAS5.2.0
 */
public final class MixWriteBackAction extends AbstractAction implements
		Serializable {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = -1864818507801807898L;

	/**
	 * WriteBackAction���
	 */
	private WriteBackAction writeBackAction = null;

	/**
	 * NamingWriteBackAction���
	 */
	private NamingWriteBackAction namingWriteBackAction = null;

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param writeBackAction
	 * @param namingWriteBackAction
	 * @author:daij ����ʱ�䣺2006-12-4
	 *              <p>
	 */
	public MixWriteBackAction(WriteBackAction writeBackAction,
			NamingWriteBackAction namingWriteBackAction) {
		super();

		this.writeBackAction = writeBackAction;
		this.namingWriteBackAction = namingWriteBackAction;
	}

	/**
	 * 
	 * ����������WriteBackAction���.
	 * 
	 * @return WriteBackAction
	 * @author:daij ����ʱ�䣺2006-12-4
	 *              <p>
	 */
	public WriteBackAction writeBackAcion() {
		if (this.writeBackAction != null) {
			this.writeBackAction.putAll(this);
		}
		return this.writeBackAction;
	}

	/**
	 * 
	 * ����������NamingWriteBackAction���
	 * 
	 * @return NamingWriteBackAction
	 * @author:daij ����ʱ�䣺2006-12-4
	 *              <p>
	 */
	public NamingWriteBackAction namingWriteBackAcion() {
		if (this.namingWriteBackAction != null) {
			this.namingWriteBackAction.putAll(this);
		}
		return this.namingWriteBackAction;
	}
}
