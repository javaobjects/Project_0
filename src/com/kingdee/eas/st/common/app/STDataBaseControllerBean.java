package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.util.app.DbUtil;

public class STDataBaseControllerBean extends AbstractSTDataBaseControllerBean {

	@Override
	protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		EntityObjectInfo entity = getBOSEntity(ctx, pk.toString());

		String sql = "update " + entity.getTable().getName()
				+ " set FIsEnabled=1 where fid='" + pk.toString() + "'";
		DbUtil.execute(ctx, sql);
	}

	@Override
	protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		EntityObjectInfo entity = getBOSEntity(ctx, pk.toString());

		String sql = "update " + entity.getTable().getName()
				+ " set FIsEnabled=0 where fid='" + pk.toString() + "'";
		DbUtil.execute(ctx, sql);
	}

	@Override
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		String oql = "where isEnabled=1 and id='" + pk.toString() + "'";

		if (!exists(ctx, oql)) {
			super._delete(ctx, pk);
		}
	}

	@Override
	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			_delete(ctx, arrayPK[i]);
		}
	}

	protected EntityObjectInfo getBOSEntity(Context ctx, String pk) {
		BOSObjectType type = BOSUuid.read(pk).getType();
		EntityObjectInfo entity = getBOSEntity(ctx, type);
		return entity;
	}

	protected EntityObjectInfo getBOSEntity(Context ctx, BOSObjectType type) {
		IMetaDataLoader loader = MetaDataLoaderFactory.getMetaDataLoader(ctx);
		EntityObjectInfo entity = null;
		entity = loader.getEntity(type);
		return entity;
	}
}
