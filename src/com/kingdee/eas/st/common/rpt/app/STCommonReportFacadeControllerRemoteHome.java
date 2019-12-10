package com.kingdee.eas.st.common.rpt.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STCommonReportFacadeControllerRemoteHome extends EJBHome {
	STCommonReportFacadeControllerRemote create() throws CreateException,
			RemoteException;
}