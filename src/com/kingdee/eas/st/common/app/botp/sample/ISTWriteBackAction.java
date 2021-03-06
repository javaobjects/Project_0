/*
 * @(#)ISTWriteBackAction.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

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
public interface ISTWriteBackAction extends IWriteBackAction {

	/**
	 * 
	 * 描述：
	 * 
	 * @param action
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public abstract void writBack(CreateVouhcerWritBackAction action)
			throws EASBizException, BOSException;
}
