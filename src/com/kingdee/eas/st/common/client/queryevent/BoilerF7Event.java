package com.kingdee.eas.st.common.client.queryevent;

import com.kingdee.bos.BOSException;

public class BoilerF7Event extends AbstractQueryEvent {

	public void fireEvent() throws BOSException {
		getProcessor().processQueryEditor(this);

	}

}
