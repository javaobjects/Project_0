package com.kingdee.eas.st.common.client.queryevent;

import com.kingdee.bos.BOSException;

public class PersonF7Event extends AbstractQueryEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7371222603280409137L;

	/**
	 * √Ë ˆ£∫
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.queryevent.AbstractQueryEvent#fireEvent()
	 */
	public void fireEvent() throws BOSException {
		getProcessor().processQueryEditor(this);
	}

}