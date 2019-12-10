package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillBaseControllerRemoteHome extends EJBHome {
	STBillBaseControllerRemote create() throws CreateException, RemoteException;
}