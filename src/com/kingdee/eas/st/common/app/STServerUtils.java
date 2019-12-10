/**
 * @(#)STServerUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.data.ColumnInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.LinkPropertyInfo;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.RelationshipInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.scm.common.BillTypeInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.scm.common.util.SCMConstant;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 描述:
 * 
 * @author sylxz date:2006-12-5
 * @version EAS5.2
 */
public abstract class STServerUtils {

	/**
	 * 
	 * 描述：获取实体的别名.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param value
	 *            值对象
	 * @return String
	 * @author:daij 创建时间：2008-5-22
	 *              <p>
	 */
	public static String getEntityAlias(Context ctx, IObjectValue value) {
		String entityAlias = null;
		if (value != null) {
			IMetaDataLoader loader = MetaDataLoaderFactory
					.getMetaDataLoader(ctx);
			EntityObjectInfo entity = loader.getEntity(value.getBOSType());
			entityAlias = entity.getAlias();
		}
		return entityAlias;
	}

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

		IRowSet iRowSet = DbUtil.executeQuery(ctx, sbSql.toString(), param);
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

			if (model instanceof STBillBaseInfo
					&& !((STBillBaseInfo) model).isIsFromBOTP()
					&& (value == null || value.equals(""))) {
				throw new STException(STException.NULL_NUMBER);
			}

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
						if (ctx.getLocale() != null) {
							if (ctx.getLocale().getLanguage().equals("l1")) {
								propertyAliasName = null != property
										.getAlias(ctx.getLocale()) ? property
										.getAlias(ctx.getLocale()) : property
										.getAlias(java.util.Locale.ENGLISH);
							} else if (ctx.getLocale().getLanguage().equals(
									"l2")) {
								propertyAliasName = null != property
										.getAlias(ctx.getLocale()) ? property
										.getAlias(ctx.getLocale())
										: property
												.getAlias(java.util.Locale.SIMPLIFIED_CHINESE);
							} else if (ctx.getLocale().getLanguage().equals(
									"l3")) {
								propertyAliasName = null != property
										.getAlias(ctx.getLocale()) ? property
										.getAlias(ctx.getLocale()) : property
										.getAlias(java.util.Locale.CHINESE);
							} else {
								propertyAliasName = null != property
										.getAlias(ctx.getLocale()) ? property
										.getAlias(ctx.getLocale())
										: property
												.getAlias(java.util.Locale.SIMPLIFIED_CHINESE);
							}
						} else {
							propertyAliasName = property
									.getAlias(java.util.Locale.SIMPLIFIED_CHINESE);
						}

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
			throw new EASBizException(STBillException.EXC_METAS_LOADERR);
		} else {
			StringBuffer sbSql = new StringBuffer(" select fid from ");
			sbSql.append(tableName);
			sbSql.append(" WHERE ");
			sbSql.append(fieldName);
			sbSql.append(" =? ");
			if (STUtils.isNotNull(id)) {
				if (model instanceof STBillBaseInfo) {
					sbSql.append(" AND fid<>? and fbillstatus<>-3");
				} else {
					sbSql.append(" AND fid<>? ");
				}
				param = new Object[] { value, id.toString() };
			} else {
				param = new Object[] { value };
			}

			IRowSet iRowSet = DbUtil.executeQuery(ctx, sbSql.toString(), param);
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

	public static String getLocaleString(Context ctx) {
		StringBuffer s = new StringBuffer("_");
		if (STUtils.isNotNull(ctx.getLocale())) {
			s.append(ctx.getLocale().getLanguage());
			return s.toString();
		} else {
			return "_L2";
		}
	}

	/**
	 * 
	 * 描述：按单据Id获取分录对应的表名. 注意: 默认单头连接表体的属性名为: entries
	 * 
	 * @param ctx
	 *            服务端上下文.
	 * @param billId
	 *            单据Id
	 * @return String
	 * @author:daij 创建时间：2007-6-15
	 *              <p>
	 */
	public static String getEntryTableNameByBillId(Context ctx, String billId) {
		return getEntryTableNameByBillId(ctx, billId, "entries");
	}

	/**
	 * 
	 * 描述：按单据Id获取分录对应的表名. 注意: 如果是MD那么获取到表名与entriesPropertyName有关.
	 * 
	 * @param ctx
	 *            服务端上下文.
	 * @param billId
	 *            单据Id
	 * @param entriesPropertyName
	 *            单头连接分录的属性名.
	 * @return String
	 * @author:daij 创建时间：2007-6-15
	 *              <p>
	 */
	public static String getEntryTableNameByBillId(Context ctx, String billId,
			String entriesPropertyName) {
		String entryTableName = null;
		String bosTypeString = BOSUuid.read(billId).getType().toString();
		if (!StringUtils.isEmpty(bosTypeString)) {
			// 获取实体元数据
			IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
					.getLocalMetaDataLoader(ctx);
			EntityObjectInfo entity = metaDataLoader.getEntity(BOSObjectType
					.create(bosTypeString));
			// 获取分录实体元数据
			PropertyInfo p = null;
			if (STUtils.isNotNull(entity)) {
				p = entity.getPropertyByNameRuntime(entriesPropertyName);

				if (p instanceof LinkPropertyInfo) {
					LinkPropertyInfo prop = (LinkPropertyInfo) p;

					RelationshipInfo relation = prop.getRelationship();
					EntityObjectInfo entriesEntity = relation
							.getSupplierObject();

					// 分录实体对应的数据库表名
					if (STUtils.isNotNull(entriesEntity)) {
						DataTableInfo entrytableInfo = entriesEntity.getTable();

						if (STUtils.isNotNull(entrytableInfo)) {
							entryTableName = entrytableInfo.getName();
						}
					}
				}
			}
		}
		return entryTableName;
	}

	/**
	 * 提取合同类型，目前支持采购合同和销售合同
	 * 
	 * @param ctx
	 * @param billInfo
	 * @return
	 * @throws BOSException
	 */
	public static BillTypeInfo getContractType(Context ctx,
			CoreBillBaseInfo billInfo) throws BOSException {
		// 直接从携带的合同类型中提取
		BillTypeInfo contractType = (BillTypeInfo) billInfo.get("contractType");

		if (STUtils.isNull(contractType)
				|| STUtils.isNull(contractType.getId())) {
			// 从核心单据订单的来源单据提取.
			BillTypeInfo coreBillType = (BillTypeInfo) billInfo
					.get("coreBillType");
			if ((!StringUtils.isEmpty((String) billInfo.get("coreBillEntryId")))
					&& STUtils.isNotNull(coreBillType)) {
				IRowSet rs = null;
				if (SCMConstant.PURORDER_BILLTYPEID
						.equalsIgnoreCase(coreBillType.getId().toString())) {
					rs = DbUtil
							.executeQuery(
									ctx,
									"Select FSourceBillTypeID,FSourceBillEntryID,FSourceBillID,FSourceBillNumber From T_SM_PurOrderEntry Where FId = ? ",
									new Object[] { billInfo
											.get("coreBillEntryId") });
				} else if (SCMConstant.SALEORDER_BILLTYPEID
						.equalsIgnoreCase(coreBillType.getId().toString())) {
					rs = DbUtil
							.executeQuery(
									ctx,
									"Select FSourceBillTypeID,FSourceBillEntryID,FSourceBillID,FSourceBillNumber From T_SD_SaleOrderEntry Where FId = ? ",
									new Object[] { billInfo
											.get("coreBillEntryId") });
				}
				try {
					if (STUtils.isNotNull(rs) && rs.next()) {
						String contractTypeId = rs
								.getString("FSourceBillTypeID");
						contractType = new BillTypeInfo();
						contractType.setId(BOSUuid.read(contractTypeId));
						// 如果来源单据是合同且委托单缺少合同分录Id,则补充来源合同信息.
						if (StringUtils.isEmpty((String) billInfo
								.get("contractEntryId"))
								&& (SCMConstant.PURCONTRACT_BILLTYPEID
										.equalsIgnoreCase(contractTypeId) || SCMConstant.SALECONTRACT_BILLTYPEID
										.equalsIgnoreCase(contractTypeId))) {

							// delegateBill.setContractType(contractType);
							billInfo.put("contractType", contractType);
							// delegateBill.setContractId(rs.getString(
							// "FSourceBillID"));
							billInfo.setString("contractId", rs
									.getString("FSourceBillID"));
							// delegateBill.setContractEntryId(rs.getString(
							// "FSourceBillEntryID"));
							billInfo.setString("contractEntryId", rs
									.getString("FSourceBillEntryID"));
							// delegateBill.setContractNumber(rs.getString(
							// "FSourceBillNumber"));
							billInfo.setString("contractNumber", rs
									.getString("FSourceBillNumber"));
						}
					}
				} catch (SQLException e) {
					throw new SQLDataException(e);
				}
			}
		}
		return contractType;
	}
}
