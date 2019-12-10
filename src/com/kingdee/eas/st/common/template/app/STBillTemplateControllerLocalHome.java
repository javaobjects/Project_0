package com.kingdee.eas.st.common.template.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillTemplateControllerLocalHome extends EJBLocalHome {
	STBillTemplateControllerLocal create() throws CreateException;
}