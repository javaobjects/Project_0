/*
 * @(#)QMServerUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述:
 * 
 * @author daij date:2007-1-19
 *         <p>
 * @version EAS5.2
 */
public abstract class QMServerUtils {
	// propertyName，属性名
	// tableName,表名
	// strValue，判断值
	// 检查全局是否唯一
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
	 * 描述：通过对象Id,判断是否属性唯一性
	 * 
	 * @param ctx
	 * @param pk
	 *            对象BOSUUID
	 * @param propertyName
	 *            属性名
	 * @param strValue
	 *            属性值
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author williamwu 创建时间：2006-12-5
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

			// 获取对应的表名称
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
	 * 创建单据之间的botp关系，从检斤单复制过来的
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

		// 检查是否已经插入关系
		StringBuffer sqlString = new StringBuffer();
		sqlString = sqlString.append("SELECT COUNT(1) FROM T_BOT_Relation ")
				.append("WHERE FSRCOBJECTID ='").append(srcObjectId).append(
						"' ").append("AND FDESTOBJECTID ='").append(
						destObjectId).append("' ");
		IRowSet relationCount = DbUtil.executeQuery(ctx, sqlString.toString());

		try {
			if (relationCount.next()) {
				if (relationCount.getInt(1) == 0) {
					// 关系只保存一遍
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
	 * 删除单据之间的botp关系
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

		// 检查是否已经插入关系
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("delete from T_BOT_Relation ").append(
				"WHERE FSRCOBJECTID ='").append(srcObjectId).append("' ")
				.append("AND FDESTOBJECTID ='").append(destObjectId).append(
						"' ");
		;
		DbUtil.execute(ctx, sqlString.toString());
	}

	// 54新增功能 许昭林 2009-9-1
	// 新增功能：删除目标单据所有对应的botp关系
	public static void removeBotpRelation(Context ctx, String destObjectId)
			throws BOSException, EASBizException {

		if (StringUtils.isEmpty(destObjectId)) {
			return;
		}

		// 检查是否已经插入关系
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("delete from T_BOT_Relation ").append(
				"WHERE FDESTOBJECTID ='").append(destObjectId).append("' ");
		;
		DbUtil.execute(ctx, sqlString.toString());
	}
}
