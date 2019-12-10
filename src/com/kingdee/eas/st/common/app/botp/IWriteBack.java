/*
 * @(#)IWriteBack.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;

/**
 * 描述: 反写接口.
 * 
 * @author daij date:2006-11-29
 *         <p>
 * @version EAS5.2.0
 */
public interface IWriteBack {

	/**
	 * 
	 * 描述：反写操作.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param targetBillInfo
	 *            发起反写操作的目标方信息.
	 * @param action
	 *            激活反写操作的活动信息.
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(Context ctx, Object targetBillInfo,
			Object action) throws BOSException, EASBizException;
}
