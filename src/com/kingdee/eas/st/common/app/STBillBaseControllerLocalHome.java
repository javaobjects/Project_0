package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillBaseControllerLocalHome extends EJBLocalHome {
	STBillBaseControllerLocal create() throws CreateException;
}