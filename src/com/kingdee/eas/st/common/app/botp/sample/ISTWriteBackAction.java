/*
 * @(#)ISTWriteBackAction.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.sample;

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
public interface ISTWriteBackAction extends IWriteBackAction {

	/**
	 * 
	 * ������
	 * 
	 * @param action
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public abstract void writBack(CreateVouhcerWritBackAction action)
			throws EASBizException, BOSException;
}
