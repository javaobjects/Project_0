package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillControllerLocalHome extends EJBLocalHome {
	STBillControllerLocal create() throws CreateException;
}