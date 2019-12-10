/*
 * @(#)SaveWriteBackAction.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.action;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

/**
 * 描述: 保存时的反写操作.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class SaveWriteBackAction extends WriteBackAction {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = -6912426493342053944L;

	/**
	 * 描述：激活反写操作
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBackable#fireAction()
	 */
	public void fireAction() throws EASBizException, BOSException {
		this.processor.writeBack(this);
	}

}
