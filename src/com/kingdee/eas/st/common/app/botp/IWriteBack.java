/*
 * @(#)IWriteBack.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;

/**
 * ����: ��д�ӿ�.
 * 
 * @author daij date:2006-11-29
 *         <p>
 * @version EAS5.2.0
 */
public interface IWriteBack {

	/**
	 * 
	 * ��������д����.
	 * 
	 * @param ctx
	 *            �����������
	 * @param targetBillInfo
	 *            ����д������Ŀ�귽��Ϣ.
	 * @param action
	 *            ���д�����Ļ��Ϣ.
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(Context ctx, Object targetBillInfo,
			Object action) throws BOSException, EASBizException;
}
