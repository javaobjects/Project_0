package com.kingdee.eas.st.common.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Iterator;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.base.param.ParamItemCollection;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.st.common.util.param.STParamReader;

public class STParameterFacadeControllerBean extends
		AbstractSTParameterFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.st.common.app.STParameterFacadeControllerBean");

	protected STParamReader _getParameter(Context ctx, String paramNumber,
			EntityViewInfo viewInfo) throws BOSException, EASBizException {
		FilterInfo filter = viewInfo.getFilter();
		Iterator it = filter.getFilterItems().iterator();
		Object o1 = null;
		Object o2 = null;
		while (it.hasNext()) {
			FilterItemInfo item = (FilterItemInfo) it.next();
			if ("orgUnitID.id".equals(item.getPropertyName())) {
				o1 = item.getCompareValue();
			} else if ("keyID.number".equals(item.getPropertyName())) {
				o2 = item.getCompareValue();
			}
		}

		IObjectPK orgPK = null;
		String paramVal = "false";
		if (o1 instanceof String) {
			orgPK = new ObjectStringPK((String) o1);
		}
		if (o2 instanceof String) {
			String keyNumber = (String) o2;
			paramVal = ParamManager.getParamValue(ctx, orgPK, keyNumber);
		}

		// ParamItemCollection paramItems = ParamItemFactory.getLocalInstance(
		// ctx).getParamItemCollection(viewInfo);

		STParamReader reader = new STParamReader();
		reader.setParamNumber(paramNumber);
		// reader.setParamItems(paramItems);
		reader.setParamVlaue(paramVal);
		return reader;
	}
}