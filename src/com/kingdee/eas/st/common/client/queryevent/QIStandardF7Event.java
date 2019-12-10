/*
 * @(#)QIStandardF7Event.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.queryevent;

import com.kingdee.bos.BOSException;

/**
 * 描述:
 * 
 * @author daij date:2007-4-19
 *         <p>
 * @version EAS5.2
 */
public class QIStandardF7Event extends AbstractQueryEvent {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = 4597075856931805291L;

	/**
	 * 描述：
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.queryevent.AbstractQueryEvent#fireEvent()
	 */
	public void fireEvent() throws BOSException {
		getProcessor().processQueryEditor(this);
	}
}
