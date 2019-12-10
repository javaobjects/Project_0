/*
 * @(#)AbstractWriteBackProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述: 反写操作的方法分离器.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractWriteBackProcessor implements IWriteBack,
		IWriteBackAction {

	/**
	 * 服务端上下文.
	 */
	protected Context ctx = null;

	/**
	 * 发起反写操作的目标方信息.
	 */
	protected Object targetBillInfo = null;

	/**
	 * 
	 * 描述：反写操作.
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
			// 放入反写处理实现类
			writeBackable.acceptProcessor(this);
			// 转发反写操作前处理
			beforeWriteBack(ctx, targetBillInfo, action);
			// 激活反写操作
			writeBackable.fireAction();
			// 转发反写操作前处理
			afterWriteBack(ctx, targetBillInfo, action);
		}
	}

	/**
	 * 
	 * 描述：转发反写请求前处理,由子类覆盖.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param targetBillInfo
	 *            发起反写操作的目标方信息.
	 * @param action
	 *            激活反写操作的活动信息.
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-19
	 *              <p>
	 */
	protected void beforeWriteBack(Context ctx, Object targetBillInfo,
			Object action) throws BOSException, EASBizException {
	}

	/**
	 * 
	 * 描述：转发反写请求后处理,由子类覆盖.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param targetBillInfo
	 *            发起反写操作的目标方信息.
	 * @param action
	 *            激活反写操作的活动信息.
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-19
	 *              <p>
	 */
	protected void afterWriteBack(Context ctx, Object targetBillInfo,
			Object action) throws BOSException, EASBizException {
	}

	/**
	 * 
	 * 描述：适配保存时反写操作, 由子类覆盖.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.SaveWriteBackAction)
	 */
	public void writeBack(SaveWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * 描述：适配提交时反写操作, 由子类覆盖.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.SubmitWriteBackAction)
	 */
	public void writeBack(SubmitWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * 描述：适配审核时反写操作, 由子类覆盖.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.AuditWriteBackAction)
	 */
	public void writeBack(AuditWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * 描述：适配反审核时反写操作，由子类覆盖.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.UnAuditWriteBackAction)
	 */
	public void writeBack(UnAuditWriteBackAction action) throws BOSException,
			EASBizException {
	}

	/**
	 * 
	 * 描述：适配反审核时反写操作，由子类覆盖.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.action.IWriteBackAction#writeBack(com.kingdee.eas.st.common.app.botp.action.UnAuditWriteBackAction)
	 */
	public void writeBack(DeleteWriteBackAction action) throws BOSException,
			EASBizException {
	}
}
