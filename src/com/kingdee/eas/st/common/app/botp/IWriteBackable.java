/*
 * @(#)IWriteBackable.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.action.IWriteBackAction;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public interface IWriteBackable {

	/**
	 * 
	 * 描述：填入反写处理实现者实例.
	 * 
	 * @param processor
	 *            反写处理实现者实例
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public abstract void acceptProcessor(IWriteBackAction processor);

	/**
	 * 
	 * 描述：激活反写操作.
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public abstract void fireAction() throws EASBizException, BOSException;
}
