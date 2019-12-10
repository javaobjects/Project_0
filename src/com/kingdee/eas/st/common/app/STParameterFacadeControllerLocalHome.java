package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STParameterFacadeControllerLocalHome extends EJBLocalHome {
	STParameterFacadeControllerLocal create() throws CreateException;
}