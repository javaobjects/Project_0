package com.kingdee.eas.st.common.listenerutils.base;

import com.kingdee.eas.st.common.listenerutils.ListenerManager;

public abstract class BaseDataListener implements IListenerExecute {

	private ListenerManager listenerManager = null;

	public ListenerManager getListenerManager() {
		return listenerManager;
	}

	public void setListenerManager(ListenerManager listenerManager) {
		this.listenerManager = listenerManager;
	}

}
