package com.kingdee.eas.st.common.app;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.st.common.app.QMServerUtils;
import com.kingdee.eas.basedata.mm.qm.utils.STCodingRuleUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.STBasedataException;
import com.kingdee.eas.st.common.STDataBaseDInfo;

import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.util.app.DbUtil;

public class STDataBaseDControllerBean extends
		AbstractSTDataBaseDControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.st.common.app.STDataBaseDControllerBean");

	/*
	 * @author 提交：被引用不允许删除 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		String number = null;

		// 如果存在编码,需要重新取
		number = STCodingRuleUtils.getNumberForBaseData(ctx, model);

		if (number != null) {
			model.setString("number", number);
		}
		// 如果编码为空,提示编码不能为空
		if (model.getString("number") == null) {
			throw new STException(STException.NULL_NUMBER);
		}

		QMServerUtils.checkIsDuplicate(ctx, model, "number");

		return super._submit(ctx, model);
	}

	/*
	 * @author 删除：被引用不允许删除 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// TODO 自动生成方法存根
		isSysData(ctx, pk.toString());
		try {
			this._isReferenced(ctx, pk);
		} catch (ObjectReferedException ex) {
			throw new STBasedataException(
					STBasedataException.DELETE_ISREFERENCE_FAIL);
		}
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			isSysData(ctx, arrayPK[i].toString());
			_isReferenced(ctx, arrayPK[i]);
		}
		super._delete(ctx, arrayPK);
	}

	/**
	 * 系统预设数据
	 * 
	 * @param id
	 * @return
	 */
	private boolean isSysData(Context ctx, String id) throws BOSException,
			EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", id);
		Set set = new HashSet();
		set.add(OrgConstants.SYS_CU_ID);
		// set.add(OrgConstants.DEF_CU_ID);

		filter.getFilterItems().add(
				new FilterItemInfo("CU.id", set, CompareType.INCLUDE));
		// filter.appendFilterItem("CU.id",OrgConstants.SYS_CU_ID);
		boolean isSys = false;
		if (_exists(ctx, filter)) {
			isSys = true;
		} else {
			isSys = false;
		}

		if (isSys) {
			throw new STBasedataException(STBasedataException.DELETE_SYSDATA);
		}
		return isSys;
	}

	protected boolean _disEnabled(Context ctx, IObjectPK ctPK,
			IObjectValue model) throws BOSException, EASBizException {
		// IFDCDataBase m=(IFDCDataBase)model;
		// FDCDataBaseInfo dataBaseInfo = m.getFDCDataBaseInfo(ctPK);
		// dataBaseInfo .setIsEnabled(false);
		// _update(ctx,ctPK,dataBaseInfo);
		return setEnable(ctx, ctPK, model, "FIsEnabled", false);
	}

	protected boolean _enabled(Context ctx, IObjectPK ctPK, IObjectValue model)
			throws BOSException, EASBizException {
		return setEnable(ctx, ctPK, model, "FIsEnabled", true);
	}

	private boolean setEnable(Context ctx, IObjectPK ctPK, IObjectValue model,
			String fieldName, boolean flag) throws EASBizException,
			BOSException {
		if (ctPK == null)
			return true;
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getLocalMetaDataLoader(ctx);
		STDataBaseDInfo m = (STDataBaseDInfo) model;
		String classname = m.getClass().getName();
		int x = classname.lastIndexOf("Info");
		if (x > 0)
			classname = classname.substring(0, x);
		classname = classname.substring(0, classname.lastIndexOf('.')) + ".app"
				+ classname.substring(classname.lastIndexOf('.'));
		IMetaDataPK pk = new MetaDataPK(classname);
		loader.getEntity(pk);
		EntityObjectInfo q = loader.getEntity(pk);
		String name = q.getTable().getName();
		String sql = "update " + name + " set  " + fieldName
				+ " = ?  where FID = ? ";
		Object[] params = new Object[] { new Integer(flag ? 1 : 0),
				ctPK.toString() };
		DbUtil.execute(ctx, sql, params);
		return true;
	}

	protected boolean _audit(Context ctx, IObjectPK ctPK, IObjectValue model)
			throws BOSException, EASBizException {
		if (ctPK == null)
			return true;
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getLocalMetaDataLoader(ctx);
		STDataBaseDInfo m = (STDataBaseDInfo) model;
		String classname = m.getClass().getName();
		int x = classname.lastIndexOf("Info");
		if (x > 0)
			classname = classname.substring(0, x);
		classname = classname.substring(0, classname.lastIndexOf('.')) + ".app"
				+ classname.substring(classname.lastIndexOf('.'));
		IMetaDataPK pk = new MetaDataPK(classname);
		loader.getEntity(pk);
		EntityObjectInfo q = loader.getEntity(pk);
		String name = q.getTable().getName();
		String time = m.getAuditTime().toString();
		int index = time.lastIndexOf(".");
		String sql = "update " + name + " set FIsAudited = 1 , FAuditUser = '"
				+ m.getAuditUser().getId() + "' , FAuditTime = {ts'"
				+ time.substring(0, index) + "'}  where FID = '"
				+ ctPK.toString() + "'";
		DbUtil.execute(ctx, sql);
		return true;
	}

	protected boolean _disAudit(Context ctx, IObjectPK ctPK, IObjectValue model)
			throws BOSException, EASBizException {
		if (ctPK == null)
			return true;
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getLocalMetaDataLoader(ctx);
		STDataBaseDInfo m = (STDataBaseDInfo) model;
		String classname = m.getClass().getName();
		int x = classname.lastIndexOf("Info");
		if (x > 0)
			classname = classname.substring(0, x);
		classname = classname.substring(0, classname.lastIndexOf('.')) + ".app"
				+ classname.substring(classname.lastIndexOf('.'));
		IMetaDataPK pk = new MetaDataPK(classname);
		loader.getEntity(pk);
		EntityObjectInfo q = loader.getEntity(pk);
		String name = q.getTable().getName();
		String sql = "update "
				+ name
				+ " set FIsAudited = 0 , FAuditUser = null , FAuditTime = null where FID = '"
				+ ctPK.toString() + "'";
		DbUtil.execute(ctx, sql);
		return true;
	}
}