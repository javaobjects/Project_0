package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillBaseEntryControllerLocalHome extends EJBLocalHome {
	STBillBaseEntryControllerLocal create() throws CreateException;
}