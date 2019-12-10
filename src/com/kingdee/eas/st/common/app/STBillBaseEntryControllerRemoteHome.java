package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillBaseEntryControllerRemoteHome extends EJBHome {
	STBillBaseEntryControllerRemote create() throws CreateException,
			RemoteException;
}