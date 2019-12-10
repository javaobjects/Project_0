package com.kingdee.eas.st.common.template.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillTemplateEntryControllerRemoteHome extends EJBHome {
	STBillTemplateEntryControllerRemote create() throws CreateException,
			RemoteException;
}