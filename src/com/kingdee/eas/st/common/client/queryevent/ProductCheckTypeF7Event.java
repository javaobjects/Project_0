package com.kingdee.eas.st.common.client.queryevent;

import com.kingdee.bos.BOSException;

public class ProductCheckTypeF7Event extends AbstractQueryEvent {

	public void fireEvent() throws BOSException {
		getProcessor().processQueryEditor(this);

	}

}
