/*
 * @(#)QMServerUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app;

import java.sql.SQLException;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.bot.IBOTRelation;
import com.kingdee.bos.metadata.data.ColumnInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * ����:
 * 
 * @author daij date:2007-1-19
 *         <p>
 * @version EAS5.2
 */
public abstract class QMServerUtils {
	// propertyName��������
	// tableName,����
	// strValue���ж�ֵ
	// ���ȫ���Ƿ�Ψһ
	public static void checkIsDuplicate(Context ctx, String tableName,
			String propertyName, String strValue) throws BOSException,
			EASBizException {
		StringBuffer sbSql = new StringBuffer(" select fid from ");
		sbSql.append(tableName);
		sbSql.append(" WHERE ");
		sbSql.append(propertyName);
		sbSql.append(" =?");
		Object[] param = { strValue };

		IRowSet iRowSet = DbUtil.executeQueryNoTx(ctx, sbSql.toString(), param);
		try {
			if (iRowSet.next()) {
				throw new EASBizException(EASBizException.CHECKDUPLICATED);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
	}

	/**
	 * 
	 * ������ͨ������Id,�ж��Ƿ�����Ψһ��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����BOSUUID
	 * @param propertyName
	 *            ������
	 * @param strValue
	 *            ����ֵ
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author williamwu ����ʱ�䣺2006-12-5
	 */
	public static void checkIsDuplicate(Context ctx, IObjectValue model,
			String propertyName) throws BOSException, EASBizException {

		String tableName = "";
		String fieldName = "";
		Object id = null;
		Object value = null;
		ColumnInfo field = null;
		Object[] param = null;
		String propertyAliasName = null;

		if (STUtils.isNotNull(model)) {
			value = model.get(propertyName);
			id = model.get("id");

			BOSObjectType bosType = model.getBOSType();

			// ��ȡ��Ӧ�ı�����
			IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
					.getLocalMetaDataLoader(ctx);
			if (STUtils.isNotNull(bosType)) {
				EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
				if (STUtils.isNotNull(entity)) {
					DataTableInfo updateTableInfo = entity.getTable();
					if (STUtils.isNotNull(updateTableInfo)) {
						tableName = updateTableInfo.getName();
					}
					PropertyInfo property = entity
							.getPropertyByName(propertyName);
					if (STUtils.isNotNull(property)) {
						propertyAliasName = property.getAlias(ctx.getLocale());
						field = property.getMappingField();
						if (STUtils.isNotNull(field)) {
							if (field.isMultilingual()) {

								StringBuffer fieldNameExt = new StringBuffer()
										.append(field.getName()).append("_")
										.append(
												ctx.getLocale().getLanguage()
														.toString());
								fieldName = fieldNameExt.toString();
							} else {
								fieldName = field.getName();
							}
						}
					}
				}
			}
		}

		if (StringUtils.isEmpty(tableName) || (StringUtils.isEmpty(fieldName))) {
			throw new BOSException("entity's table name or field name is Null!");
		} else {
			StringBuffer sbSql = new StringBuffer(" select fid from ");
			sbSql.append(tableName);
			sbSql.append(" WHERE ");
			sbSql.append(fieldName);
			sbSql.append(" =? ");
			if (STUtils.isNotNull(id)) {
				sbSql.append(" AND fid<>? ");
				param = new Object[] { value, id.toString() };
			} else {
				param = new Object[] { value };
			}

			IRowSet iRowSet = DbUtil.executeQueryNoTx(ctx, sbSql.toString(),
					param);
			try {
				if (iRowSet.next()) {
					throw new EASBizException(EASBizException.CHECKDUPLICATED,
							new Object[] { propertyAliasName });
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}

		}
	}

	/**
	 * ��������֮���botp��ϵ���Ӽ�ﵥ���ƹ�����
	 * 
	 * @author zhiwei_wang
	 * @date 2008-7-10
	 * @param ctx
	 * @param srcObjectId
	 * @param destObjectId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void createBotpRelation(Context ctx, String srcObjectId,
			String destObjectId) throws BOSException, EASBizException {

		// ,String srcEntryId,String destEntryId

		if (StringUtils.isEmpty(srcObjectId)
				|| StringUtils.isEmpty(destObjectId)) {
			return;
		}

		// ����Ƿ��Ѿ������ϵ
		StringBuffer sqlString = new StringBuffer();
		sqlString = sqlString.append("SELECT COUNT(1) FROM T_BOT_Relation ")
				.append("WHERE FSRCOBJECTID ='").append(srcObjectId).append(
						"' ").append("AND FDESTOBJECTID ='").append(
						destObjectId).append("' ");
		IRowSet relationCount = DbUtil.executeQuery(ctx, sqlString.toString());

		try {
			if (relationCount.next()) {
				if (relationCount.getInt(1) == 0) {
					// ��ϵֻ����һ��
					IBOTRelation iBOTRelation = BOTRelationFactory
							.getLocalInstance(ctx);
					BOTRelationInfo info = new BOTRelationInfo();
					String srcBosType = BOSUuid.read(srcObjectId).getType()
							.toString();
					String destBosType = BOSUuid.read(destObjectId).getType()
							.toString();
					info.setSrcEntityID(srcBosType);
					info.setDestEntityID(destBosType);
					info.setSrcObjectID(srcObjectId);
					info.setDestObjectID(destObjectId);
					info.setDate(new Date());
					info.setOperatorID("system");
					info.setIsEffected(true);
					info.setType(0);
					iBOTRelation.addnew(info);
				}
			}

		} catch (SQLException e) {
			throw new SQLDataException(e);

		}
	}

	/**
	 * ɾ������֮���botp��ϵ
	 * 
	 * @author zhiwei_wang
	 * @date 2008-7-10
	 * @param ctx
	 * @param srcObjectId
	 * @param destObjectId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void removeBotpRelation(Context ctx, String srcObjectId,
			String destObjectId) throws BOSException, EASBizException {

		if (StringUtils.isEmpty(srcObjectId)
				|| StringUtils.isEmpty(destObjectId)) {
			return;
		}

		// ����Ƿ��Ѿ������ϵ
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("delete from T_BOT_Relation ").append(
				"WHERE FSRCOBJECTID ='").append(srcObjectId).append("' ")
				.append("AND FDESTOBJECTID ='").append(destObjectId).append(
						"' ");
		;
		DbUtil.execute(ctx, sqlString.toString());
	}

	// 54�������� ������ 2009-9-1
	// �������ܣ�ɾ��Ŀ�굥�����ж�Ӧ��botp��ϵ
	public static void removeBotpRelation(Context ctx, String destObjectId)
			throws BOSException, EASBizException {

		if (StringUtils.isEmpty(destObjectId)) {
			return;
		}

		// ����Ƿ��Ѿ������ϵ
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("delete from T_BOT_Relation ").append(
				"WHERE FDESTOBJECTID ='").append(destObjectId).append("' ");
		;
		DbUtil.execute(ctx, sqlString.toString());
	}
}
