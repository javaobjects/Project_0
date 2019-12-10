package com.kingdee.eas.st.common.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSLocaleUtil;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.STTreeBaseDataDInfo;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.st.common.MillerUtils.ServerSideUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class STTreeBaseDataDControllerBean extends
		AbstractSTTreeBaseDataDControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.st.common.app.STTreeBaseDataDControllerBean");

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		Object id = model.get("id");

		if (id != null) {
			String numbers = _queryState(ctx,
					new IObjectPK[] { new ObjectStringPK(id.toString()) }, 1);
			if (numbers.length() > 0)
				throw new STTreeException(STTreeException.CANNOT_SAVE,
						new String[] { numbers });
		}

		IObjectPK pk = super._submit(ctx, model);

		model.put("id", pk.toString());

		checkNameDuplicate(ctx, getOrgFieldName(), model);

		return pk;
	}

	protected void checkNameDuplicate(Context ctx, String orgFieldName,
			IObjectValue model) throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("select fid from ").append(
				getTableNameByPK(ctx, (String) model.get("id"))).append(
				" where FName_").append(
				BOSLocaleUtil.getShortCode(ContextUtils.getLocaleFromEnv()))
				.append(" = ? and fid <> ? ");

		if (orgFieldName != null) {
			String orgId = ((IObjectValue) model.get(orgFieldName)).get("id")
					.toString();
			sql.append("and ").append(orgFieldName).append(" = '")
					.append(orgId).append("'");
		}
		sql.append("\r\n");

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

	protected void _audit(Context ctx, IObjectPK[] pks) throws BOSException,
			EASBizException {
		String numbers = _queryState(ctx, pks, 1);
		if (numbers.length() > 0)
			throw new STTreeException(STTreeException.CANNOT_AUDIT,
					new String[] { numbers });

		// ��ȡ��Ҫ��˵��ݵ�ID��(���������ת����String)
		List param = new ArrayList();
		IObjectPK pk = null;

		if (STUtils.isNotNull(ctx) && STUtils.isNotNull(ctx.getCaller())) {
			pk = ctx.getCaller(); // �����ID
			param.add(pk.toString());
		}
		String idString = STUtils.toParamString(pks, param);

		// �ж�ctx�Ƿ�Ϊ��,�ж�������Ƿ�Ϊ��
		if (idString.length() != 0 && STUtils.isNotNull(ctx)
				&& STUtils.isNotNull(ctx.getCaller())) {
			pk = pks[0];
			if (STUtils.isNotNull(pk)) {
				StringBuffer sqlString = new StringBuffer();
				sqlString.setLength(0);

				sqlString.append(" Update ").append(
						getTableNameByPK(ctx, pk.toString())).append(
						" set FIsAudited = 1,FAuditUserID = ? ").append(
						" , FAuditTime = getDate() where fid in (").append(
						idString.toString()).append(")");

				// ִ��SQL���
				DbUtil.execute(ctx, sqlString.toString(), param.toArray());

			}
		}
	}

	protected void _unaudit(Context ctx, IObjectPK[] pks) throws BOSException,
			EASBizException {
		String numbers = _queryState(ctx, pks, 0);
		if (numbers.length() > 0)
			throw new STTreeException(STTreeException.CANNOT_UNAUDIT,
					new String[] { numbers });

		List param = new ArrayList();

		String idString = STUtils.toParamString(pks, param);

		if (idString.length() != 0 && STUtils.isNotNull(ctx)) {
			IObjectPK pk = pks[0];
			if (STUtils.isNotNull(pk)) {
				StringBuffer sqlString = new StringBuffer();
				sqlString.setLength(0);

				sqlString = sqlString.append(" Update ").append(
						getTableNameByPK(ctx, pk.toString())).append(
						" set FIsAudited = 0, FAuditUserID = null").append(
						", FAuditTime = null where fid in (").append(
						idString.toString()).append(")");

				// ִ��SQL���
				DbUtil.execute(ctx, sqlString.toString(), param.toArray());
			}
		}
	}

	protected String _queryState(Context ctx, IObjectPK[] pks, int queryState)
			throws BOSException, EASBizException {
		if (queryState > 1)
			return null;

		StringBuffer err = new StringBuffer();
		List param = new ArrayList();
		IObjectPK pk = null;

		String idString = STUtils.toParamString(pks, param);

		if (idString.length() != 0) {
			pk = pks[0];
			if (pk != null) {
				StringBuffer sqlString = new StringBuffer();
				sqlString.append(" SELECT FNumber FROM ").append(
						getTableNameByPK(ctx, pk.toString())).append(
						" WHERE FIsAudited = ").append(queryState).append(
						" AND Fid in (").append(idString.toString())
						.append(")");

				// ִ��SQL���
				IRowSet rs = DbUtil.executeQuery(ctx, sqlString.toString(),
						param.toArray());

				try {
					while (rs.next()) {
						err.append(rs.getString("FNumber")).append("��");
					}
				} catch (SQLException e) {
					throw new BOSException(e);
				}

				if (err.length() > 0) {
					err.deleteCharAt(err.length() - 1);
				}
			}
		}

		return err.toString();
	}

	protected boolean _checkHasItems(Context ctx, String id)
			throws BOSException {
		StringBuffer tbl = new StringBuffer(getTableNameByPK(ctx, id));
		tbl.delete(tbl.length() - 4, tbl.length());
		StringBuffer sql = new StringBuffer();
		sql.append("select count(FId) as Num from ").append(tbl.toString())
				.append(" where FTreeId ");
		IRowSet rs = null;
		if (id != null) {
			sql.append("= ? \r\n");
			rs = DbUtil.executeQuery(ctx, sql.toString(), new Object[] { id });
		} else {
			sql.append(" is null");
			rs = DbUtil.executeQuery(ctx, sql.toString());
		}

		try {
			if (rs.next()) {
				long num = rs.getLong("Num");
				if (num > 0)
					return true;
			}
		} catch (SQLException ex) {
			throw new SQLDataException(ex);
		}

		return false;
	}

	protected String _getTreeId(Context ctx, String id) throws BOSException {
		StringBuffer sql = new StringBuffer();
		sql.append("select FTreeID from ").append(
				ServerSideUtils.getTableNameByPK(ctx, id)).append(
				" where fid = ? ");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(),
				new Object[] { id });
		try {
			if (rs.next()) {
				return rs.getString("FTreeID");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}

		return null;
	}

	private String getTableNameByPK(Context ctx, String pk) {
		BOSUuid id = BOSUuid.read(pk);
		BOSObjectType bosType = id.getType();
		IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
				.getLocalMetaDataLoader(ctx);
		EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
		DataTableInfo updateTableInfo = entity.getTable();
		String tableName = updateTableInfo.getName();

		return tableName;
	}

	/**
	 * ���า�Ǵ˷�������������֯���ݿ��ֶ������磺FStorageOrgUnitID �����ж������ڸ���֯���Ƿ��ظ�
	 * 
	 * @return ���ݿ��ֶ���
	 */
	protected String getOrgFieldName() {
		return null;
	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		STTreeBaseDataDInfo treeBaseInfo = (STTreeBaseDataDInfo) model;

		if (treeBaseInfo.containsKey("tree")) {
			// �����ƶ��������
			if (treeBaseInfo.get("tree") == null)
				throw new STTreeException(STTreeException.CANNOT_MOVE_TO_ROOT);

			// �����ƶ�����Ҷ�ӽڵ�
			String treeTableName = ServerSideUtils.getTableNameByPK(ctx, pk
					.toString());
			IObjectValue tree = (IObjectValue) treeBaseInfo.get("tree");
			String treeid = tree.get("id").toString();
			if (!isLeafNode(ctx, treeid, treeTableName))
				throw new STTreeException(
						STTreeException.CANNOT_MOVE_TO_NOT_LEAF);
		}

		super._update(ctx, pk, model);
	}

	protected boolean isLeafNode(Context ctx, String treeid,
			String treeTableName) throws BOSException {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(FID) AS NUM from ").append(treeTableName)
				.append("Tree where fparentid = ? ");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(),
				new Object[] { treeid });
		try {
			if (rs.next()) {
				if (rs.getLong("NUM") > 0)
					return false;
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}

		return true;
	}
}