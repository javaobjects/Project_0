package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STParameterFacadeControllerRemoteHome extends EJBHome {
	STParameterFacadeControllerRemote create() throws CreateException,
			RemoteException;
}