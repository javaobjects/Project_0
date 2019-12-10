/*
 * @(#)WriteBackDirector.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.action.MixWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.NamingWriteBackAction;
import com.kingdee.eas.st.common.util.director.AssortDirector;
import com.kingdee.eas.st.common.util.director.DirectorDynamicProxy;
import com.kingdee.eas.st.common.util.director.IDirectable;

/**
 * ����: ��д������ʵ���������.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractWriteBackDirector extends DirectorDynamicProxy
		implements IWriteBack, IDirectable {

	/**
	 * ����Ľӿ��б�.
	 */
	protected static final Class[] PROXYINTERFACES = new Class[] { IWriteBack.class };

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public AbstractWriteBackDirector() {
		super(PROXYINTERFACES);

		setupDirector();

		setupProxyInstance();
	}

	/**
	 * ��������д����.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBack#writeBack(com.kingdee.bos.Context,
	 *      java.lang.Object, java.lang.Object)
	 */
	public final void writeBack(Context ctx, Object targetBillInfo,
			final Object action) throws BOSException, EASBizException {
		Object at = action;

		// �ӻ��Action�н�����NamingWriteBackAction.
		if (action instanceof MixWriteBackAction) {
			at = ((MixWriteBackAction) action).namingWriteBackAcion();
		}

		if (at instanceof NamingWriteBackAction) {
			// ������������д����������ӳ�����ķ�д����ʵ����.
			if (director instanceof AssortDirector) {
				((AssortDirector) director)
						.changeBuildStrategy(((NamingWriteBackAction) at)
								.name());
			}
		}

		if (action instanceof String) {
			// ���action����ʶ����ô����NamingWriteBackAction
			at = new NamingWriteBackAction(action.toString());
		}

		// ת����д����.
		if (STQMUtils.isNotNull(proxyInstance)) {
			// ת����д����ǰ����
			beforeWriteBack(ctx, targetBillInfo, action);
			// ת����д����
			((IWriteBack) proxyInstance).writeBack(ctx, targetBillInfo, at);
			// ת����д����ǰ����
			afterWriteBack(ctx, targetBillInfo, action);
		}
	}

	/**
	 * 
	 * ������ת����д����ǰ���������า��.
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
	 * ������ת����д������������า��.
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
}
