package com.kingdee.eas.st.common.app;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class STBillCommonFacadeControllerBean extends
		AbstractSTBillCommonFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.st.common.app.STBillCommonFacadeControllerBean");

	protected Timestamp _getServerDate(Context ctx) throws BOSException {
		Calendar cal = Calendar.getInstance();
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		return ts;
	}

	protected IRowSet _executeQuery(Context ctx, String sql)
			throws BOSException {
		return DbUtil.executeQuery(ctx, sql);
	}

	protected IRowSet _executeQuery(Context ctx, String sql, Object[] params)
			throws BOSException {
		return DbUtil.executeQuery(ctx, sql, params);
	}

	protected String _getBillCodingRuleNumber(Context ctx, IObjectValue model,
			String orgID) throws BOSException, EASBizException {
		STBillBaseInfo aSTBillBaseInfo = null;

		if (model instanceof STBillBaseInfo) {
			aSTBillBaseInfo = (STBillBaseInfo) model;
		}

		return getNewNumber(ctx, aSTBillBaseInfo, orgID, "");
	}

	protected String getNewNumber(Context ctx, IObjectValue model,
			String strCompanyID, String customString) throws BOSException,
			EASBizException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);

		STBillBaseInfo aSTBillBaseInfo = (STBillBaseInfo) model;

		String number = "";

		number = iCodingRuleManager.getNumber(aSTBillBaseInfo, strCompanyID,
				customString);

		return number;
	}
}