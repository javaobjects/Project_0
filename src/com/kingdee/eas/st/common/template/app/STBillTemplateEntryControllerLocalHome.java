package com.kingdee.eas.st.common.template.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillTemplateEntryControllerLocalHome extends EJBLocalHome {
	STBillTemplateEntryControllerLocal create() throws CreateException;
}