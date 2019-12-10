/*
 * @(#)IWriteBackAction.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.action;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

/**
 * ����: ��ʱ�㷴д������Visitor ע�⣺��ҵ��ϵͳ��չAction
 * 
 * 1. ��ҵ��ϵͳͨ����IWriteBackAction �̳ж����Լ���IXXXWriteBackAction. 2.
 * ��ҵ��ϵͳͨ����WriteBackAction �̳ж����Լ��ķ�д����.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public interface IWriteBackAction {

	/**
	 * 
	 * ����������ʱ�ķ�д����.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(SaveWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * �������ύʱ�ķ�д����.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(SubmitWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * ���������ʱ�ķ�д����.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(AuditWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * �����������ʱ�ķ�д����.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-10
	 *              <p>
	 */
	public abstract void writeBack(UnAuditWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * ������ɾ��ʱ�ķ�д����.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-10
	 *              <p>
	 */
	public abstract void writeBack(DeleteWriteBackAction action)
			throws BOSException, EASBizException;
}
