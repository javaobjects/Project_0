package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillControllerRemoteHome extends EJBHome {
	STBillControllerRemote create() throws CreateException, RemoteException;
}