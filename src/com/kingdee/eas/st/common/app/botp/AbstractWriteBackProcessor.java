/*
 * @(#)AbstractWriteBackProcessor.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.action.AuditWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.DeleteWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.IWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.MixWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.SaveWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.SubmitWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.UnAuditWriteBackAction;

/**
 * ����: ��д�����ķ���������.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractWriteBackProcessor implements IWriteBack,
		IWriteBackAction {

	/**
	 * �����������.
	 */
	protected Context ctx = null;

	/**
	 * ����д������Ŀ�귽��Ϣ.
	 */
	protected Object targetBillInfo = null;

	/**
	 * 
	 * ��������д����.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBack#writeBack(com.kingdee.bos.Context,
	 *      java.lang.Object, java.lang.Object)
	 */
	public final void writeBack(Context ctx, Object targetBillInfo,
			Object action) throws BOSException, EASBizException {

		this.ctx = ctx;

		this.targetBillInfo = targetBillInfo;

		Object at = action;

		if (action instanceof MixWriteBackAction) {
			at = ((MixWriteBackAction) action).writeBackAcion();
		}

		if (at instanceof IWriteBackable) {
			IWriteBackable writeBackable = (IWriteBackable) at;
			// ���뷴д����ʵ����
			writeBackable.acceptProcessor(this);
			// ת����д����ǰ����
			beforeWriteBack(ctx, targetBillInfo, action);
			// ���д����
			writeBackable.fireAction();
			// ת����д����ǰ����
			afterWriteBack(ctx, targetBillInfo, action);
		}
	}

	/**
	 * 
	 * ������ת����д����ǰ����,�����า��.
	 * 
	 * @param ctx
	 *            �����������
	 * @param targetBillInfo
	 *            ����д������Ŀ�귽��Ϣ.
	 * @param action
	 *            ���д�����Ļ��Ϣ.
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	protected void beforeWriteBack(Context ctx, Object targetBillInfo,
			Object action) throws BOSException, EASBizException {
	}

	/**
	 * 
	 * ������ת����д�������,�����า��.
	 * 
	 * @param ctx
	 *            �����������
	 * @param targetBillInfo
	 *            ����д������Ŀ�귽��Ϣ.
	 * @param action
	 *            ���д�����Ļ��Ϣ.
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	protected void afterWriteBack(Context ctx, Object targetBillInfo,
			Object action) throws BOSException, EASBizException {
	}

	/**
	 * 
	 * ���������䱣��ʱ��д����, �����า��.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.SaveWriteBackAction)
	 */
	public void writeBack(SaveWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * �����������ύʱ��д����, �����า��.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.SubmitWriteBackAction)
	 */
	public void writeBack(SubmitWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * �������������ʱ��д����, �����า��.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.AuditWriteBackAction)
	 */
	public void writeBack(AuditWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * ���������䷴���ʱ��д�����������า��.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.UnAuditWriteBackAction)
	 */
	public void writeBack(UnAuditWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * ���������䷴���ʱ��д�����������า��.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.UnAuditWriteBackAction)
	 */
	public void writeBack(DeleteWriteBackAction action) throws BOSException,
			EASBizException {
	}
}
