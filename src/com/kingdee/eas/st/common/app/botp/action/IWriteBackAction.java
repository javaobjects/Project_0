/*
 * @(#)IWriteBackAction.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.action;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

/**
 * 描述: 各时点反写操作的Visitor 注意：各业务系统扩展Action
 * 
 * 1. 各业务系统通过从IWriteBackAction 继承定义自己的IXXXWriteBackAction. 2.
 * 各业务系统通过从WriteBackAction 继承定义自己的反写操作.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public interface IWriteBackAction {

	/**
	 * 
	 * 描述：保存时的反写处理.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(SaveWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * 描述：提交时的反写处理.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(SubmitWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * 描述：审核时的反写处理.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public abstract void writeBack(AuditWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * 描述：反审核时的反写处理.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-10
	 *              <p>
	 */
	public abstract void writeBack(UnAuditWriteBackAction action)
			throws BOSException, EASBizException;

	/**
	 * 
	 * 描述：删除时的反写处理.
	 * 
	 * @param action
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-12-10
	 *              <p>
	 */
	public abstract void writeBack(DeleteWriteBackAction action)
			throws BOSException, EASBizException;
}
