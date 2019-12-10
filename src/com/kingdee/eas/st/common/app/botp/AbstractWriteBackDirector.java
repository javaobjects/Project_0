/*
 * @(#)WriteBackDirector.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述: 反写操作的实现类分离器.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractWriteBackDirector extends DirectorDynamicProxy
		implements IWriteBack, IDirectable {

	/**
	 * 代理的接口列表.
	 */
	protected static final Class[] PROXYINTERFACES = new Class[] { IWriteBack.class };

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public AbstractWriteBackDirector() {
		super(PROXYINTERFACES);

		setupDirector();

		setupProxyInstance();
	}

	/**
	 * 描述：反写操作.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBack#writeBack(com.kingdee.bos.Context,
	 *      java.lang.Object, java.lang.Object)
	 */
	public final void writeBack(Context ctx, Object targetBillInfo,
			final Object action) throws BOSException, EASBizException {
		Object at = action;

		// 从混合Action中解析出NamingWriteBackAction.
		if (action instanceof MixWriteBackAction) {
			at = ((MixWriteBackAction) action).namingWriteBackAcion();
		}

		if (at instanceof NamingWriteBackAction) {
			// 分类容器按反写操作的命名映射具体的反写处理实现类.
			if (director instanceof AssortDirector) {
				((AssortDirector) director)
						.changeBuildStrategy(((NamingWriteBackAction) at)
								.name());
			}
		}

		if (action instanceof String) {
			// 如果action不可识别那么制作NamingWriteBackAction
			at = new NamingWriteBackAction(action.toString());
		}

		// 转发反写操作.
		if (STQMUtils.isNotNull(proxyInstance)) {
			// 转发反写操作前处理
			beforeWriteBack(ctx, targetBillInfo, action);
			// 转发反写操作
			((IWriteBack) proxyInstance).writeBack(ctx, targetBillInfo, at);
			// 转发反写操作前处理
			afterWriteBack(ctx, targetBillInfo, action);
		}
	}

	/**
	 * 
	 * 描述：转发反写请求前处理，由子类覆盖.
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
	 * 描述：转发反写请求后处理，由子类覆盖.
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
}
