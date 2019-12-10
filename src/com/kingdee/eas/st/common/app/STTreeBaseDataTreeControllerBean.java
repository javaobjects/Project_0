package com.kingdee.eas.st.common.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSLocaleUtil;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.ISTTreeBaseData;
import com.kingdee.eas.st.common.STTreeBaseDataFactory;
import com.kingdee.eas.st.common.STTreeBaseDataTreeInfo;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.st.common.MillerUtils.ServerSideUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public abstract class STTreeBaseDataTreeControllerBean extends
		AbstractSTTreeBaseDataTreeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.st.common.app.STTreeBaseDataTreeControllerBean");

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		ISTTreeBaseData iSTTreeBaseData = STTreeBaseDataFactory
				.getLocalInstance(ctx);
		if (iSTTreeBaseData.checkHasItems(pk.toString()))
			throw new STTreeException(STTreeException.HAS_ITEMS,
					getPromptNames());

		super._delete(ctx, pk);
	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		ISTTreeBaseData iSTTreeBaseData = STTreeBaseDataFactory
				.getLocalInstance(ctx);
		STTreeBaseDataTreeInfo treeBaseInfo = (STTreeBaseDataTreeInfo) model;
		boolean ok = true;
		if (treeBaseInfo.innerGetParent() != null) {
			ok = !(iSTTreeBaseData.checkHasItems(treeBaseInfo.innerGetParent()
					.getId().toString()));
		}
		if (!ok)
			throw new STTreeException(STTreeException.CANNOT_ADDGROUP,
					getPromptNames());

		super._update(ctx, pk, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK pk = super._submit(ctx, model);

		model.put("id", pk.toString());

		checkNameDuplicate(ctx, model);

		return pk;
	}

	protected void checkNameDuplicate(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("select fid from ")
				.append(
						ServerSideUtils.getTableNameByPK(ctx, (String) model
								.get("id"))).append(" where FName_").append(
						BOSLocaleUtil.getShortCode(ContextUtils
								.getLocaleFromEnv())).append(
						" = ? and fid <> ? \r\n");

		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(), new Object[] {
					model.get("name"), model.get("id") });
			if (rs.next()) {
				throw new STTreeException(STTreeException.DUPLICATE_NAME);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		Object bak = model.get("parent");
		model.put("parent", null);
		super._checkNumberDup(ctx, model);
		model.put("parent", bak);
	}

	public String[] getPromptNames() {
		return new String[] { "节点", "条目" };
	}
}