package com.kingdee.eas.st.common.rpt.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STCommonReportFacadeControllerLocalHome extends EJBLocalHome {
	STCommonReportFacadeControllerLocal create() throws CreateException;
}