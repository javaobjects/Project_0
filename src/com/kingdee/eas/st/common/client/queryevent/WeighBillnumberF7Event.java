package com.kingdee.eas.st.common.client.queryevent;

import com.kingdee.bos.BOSException;

public class WeighBillnumberF7Event extends AbstractQueryEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5716385294378939938L;

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