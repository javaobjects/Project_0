/*
 * @(#)CreateVouhcerWritBackAction.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.action.WriteBackAction;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class CreateVouhcerWritBackAction extends WriteBackAction {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = -5883434278583773732L;

	/**
	 * 描述：
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBackable#fireAction()
	 */
	public void fireAction() throws EASBizException, BOSException {
		if (this.processor instanceof ISTWriteBackAction) {
			((ISTWriteBackAction) this.processor).writBack(this);
		}
	}
}
