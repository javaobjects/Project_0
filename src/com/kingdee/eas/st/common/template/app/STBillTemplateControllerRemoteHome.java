package com.kingdee.eas.st.common.template.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface STBillTemplateControllerRemoteHome extends EJBHome {
	STBillTemplateControllerRemote create() throws CreateException,
			RemoteException;
}