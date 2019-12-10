/*
 * @(#)IWriteBackable.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.action.IWriteBackAction;

/**
 * ����:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public interface IWriteBackable {

	/**
	 * 
	 * ���������뷴д����ʵ����ʵ��.
	 * 
	 * @param processor
	 *            ��д����ʵ����ʵ��
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public abstract void acceptProcessor(IWriteBackAction processor);

	/**
	 * 
	 * ���������д����.
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public abstract void fireAction() throws EASBizException, BOSException;
}
