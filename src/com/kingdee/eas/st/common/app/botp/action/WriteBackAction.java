/*
 * @(#)WriteBackAction.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.action;

import java.io.Serializable;
import com.kingdee.eas.st.common.app.botp.IWriteBackable;

/**
 * 描述: 封装发生反写操作的时点Action
 * 
 * @author daij date:2006-11-30
 *         <p>
 * @version EAS5.2.0
 */
public abstract class WriteBackAction extends AbstractAction implements
		Serializable, IWriteBackable {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = -3963382297891787367L;

	/**
	 * 反写处理实现者的接口存根.
	 */
	protected IWriteBackAction processor = null;

	/**
	 * 
	 * 描述：填入反写处理实现者实例.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBackable#acceptProcessor(com.kingdee.eas.st.common.app.botp.action.IWriteBackAction)
	 */
	public final void acceptProcessor(IWriteBackAction processor) {
		this.processor = processor;
	}
}
