package com.kingdee.eas.st.common.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IPurchaseOrgUnit;
import com.kingdee.eas.basedata.org.IQualityOrgUnit;
import com.kingdee.eas.basedata.org.ISaleOrgUnit;
import com.kingdee.eas.basedata.org.IStorageOrgUnit;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.QualityOrgUnitCollection;
import com.kingdee.eas.basedata.org.QualityOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitCollection;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.batchaction.BatchActionEnum;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.common.SCMBillBaseInfo;
import com.kingdee.eas.scm.common.SCMBillException;
import com.kingdee.eas.scm.common.app.SCMGroupServerUtils;
import com.kingdee.eas.scm.common.app.SCMServerUtils;
import com.kingdee.eas.scm.framework.bizflow.app.BizFlowServerHelper;
import com.kingdee.eas.scm.framework.util.CommonUtils;
import com.kingdee.eas.st.common.STBillBaseCodingRuleVo;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.eas.st.common.util.STQMUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.st.common.util.param.STParamConstant;
import com.kingdee.eas.st.common.util.param.STParamReader;
import com.kingdee.eas.st.common.util.param.STParamViewInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public abstract class STBillBaseControllerBean extends
		AbstractSTBillBaseControllerBean {
	private static String sp = " \r\n";

	protected Map _audit(Context ctx, IObjectPK[] pks) throws BOSException,
			EASBizException {
		// 获取需要审核单据的ID号(将数组对象转换成String)
		List param = new ArrayList();
		IObjectPK pk = null;

		if (STQMUtils.isNotNull(ctx) && STQMUtils.isNotNull(ctx.getCaller())) {
			pk = ctx.getCaller(); // 审核人ID
			param.add(pk.toString());
		}

		// 使用应用服务器的时间,保持一致 colin
		Calendar cal = Calendar.getInstance();
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		param.add(ts);

		String idString = STQMUtils.toParamString(pks, param);

		// 判断ctx是否为空,判断审核人是否为空
		if (idString.length() != 0 && STQMUtils.isNotNull(ctx)
				&& STQMUtils.isNotNull(ctx.getCaller())) {
			pk = pks[0];
			if (STQMUtils.isNotNull(pk)) {
				BOSUuid id = BOSUuid.read(pk.toString());
				BOSObjectType bosType = id.getType();

				// 获取需要审核单据对应的表名称
				IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
						.getLocalMetaDataLoader(ctx);
				EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
				DataTableInfo updateTableInfo = entity.getTable();
				String tableName = updateTableInfo.getName();

				StringBuffer sqlString = new StringBuffer();
				sqlString.setLength(0);

				sqlString = sqlString.append(" Update ").append(
						tableName.toString()).append(
						" set FBillStatus = 4,FAuditorId = ? ").append(
						" , FAuditTime = ? where fid in (").append(
						idString.toString()).append(")");

				// 执行SQL语句
				DbUtil.execute(ctx, sqlString.toString(), param.toArray());

			}
		}

		// ---------------------------------------------2016.04.13
		// _moerhao_审核反写上游单据_start---------------------------------------------
		IObjectValue model = getValue(ctx, pks[0]);
		if (UIRuleUtil.getBooleanValue(model.get("isFromBOTP"))) {
			if (isBizflowrelationNull(ctx, pks[0])) {
				BizFlowServerHelper.doProcessing(ctx, model,
						BatchActionEnum.AUDIT);
			}
		}
		// ---------------------------------------------2016.04.13
		// _moerhao_审核反写上游单据_end---------------------------------------------

		return new HashMap();
	}

	public boolean isBizflowrelationNull(Context ctx, IObjectPK pk) {
		boolean isBizflowrelationNull = false;
		BOSUuid id = BOSUuid.read(pk.toString());
		BOSObjectType bosType = id.getType();
		IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
				.getLocalMetaDataLoader(ctx);
		EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
		DataTableInfo updateTableInfo = entity.getTable();
		String tableName = updateTableInfo.getName();

		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT FBizflowrelation FROM " + tableName + "Entry")
				.append("\r\n");
		strBuf.append("WHERE 1=1 AND FParentID = '" + pk + "'").append("\r\n");
		IRowSet rs = null;
		try {
			rs = DbUtil.executeQuery(ctx, strBuf.toString());
			if (rs != null && rs.size() > 0) {
				isBizflowrelationNull = true;
			}
		} catch (BOSException e) {
			return false;
		}
		return isBizflowrelationNull;
	}

	protected Map _unAudit(Context ctx, IObjectPK[] pks) throws BOSException,
			EASBizException {
		// 获取需要审核单据的ID号(将数组对象转换成String)
		List param = new ArrayList();

		String idString = STQMUtils.toParamString(pks, param);

		// 判断ctx是否为空,判断审核人是否为空
		if (idString.length() != 0 && STQMUtils.isNotNull(ctx)) {
			IObjectPK pk = pks[0];
			if (STQMUtils.isNotNull(pk)) {
				BOSUuid id = BOSUuid.read(pk.toString());
				BOSObjectType bosType = id.getType();

				// 获取需要审核单据对应的表名称
				IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
						.getLocalMetaDataLoader(ctx);
				EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
				DataTableInfo updateTableInfo = entity.getTable();
				String tableName = updateTableInfo.getName();

				// 反审核检查
				// UnAuditReferenceUtils.getInstance().checkUnAudit(ctx,
				// tableName, pk);

				StringBuffer sqlString = new StringBuffer();
				sqlString.setLength(0);

				sqlString = sqlString.append(" Update ").append(
						tableName.toString()).append(
						" set FBillStatus = 1,FAuditorId = null ").append(
						" , FAuditTime = null where fid in (").append(
						idString.toString()).append(")");

				// 执行SQL语句
				DbUtil.execute(ctx, sqlString.toString(), param.toArray());
			}
		}

		// ---------------------------------------------2016.04.13
		// _moerhao_反审核反写上游单据_start---------------------------------------------
		IObjectValue model = getValue(ctx, pks[0]);
		if (UIRuleUtil.getBooleanValue(model.get("isFromBOTP"))) {
			if (isBizflowrelationNull(ctx, pks[0])) {
				BizFlowServerHelper.doProcessing(ctx, model,
						BatchActionEnum.UNAUDIT);
			}
		}
		// ---------------------------------------------2016.04.13
		// _moerhao_审核反写上游单据_end---------------------------------------------

		return new HashMap();
	}

	private String getLogDetailString(IRowSet set) {
		StringBuffer sb = new StringBuffer();
		try {
			if (set.next()) {
				sb.append(set.getString("fnumber"));
			}
			while (set.next()) {
				sb.append(", ").append(set.getString("fnumber"));
			}
		} catch (SQLException e) {
		}
		return sb.toString();
	}

	protected void _abrogate(Context ctx, IObjectPK[] pks) throws BOSException,
			EASBizException {
		// 获取需要审核单据的ID号(将数组对象转换成String)
		List param = new ArrayList();
		IObjectPK pk = null;

		// if(STUtils.isNotNull(ctx) && STUtils.isNotNull(ctx.getCaller())){
		// param.add(pk.toString());
		// }
		String idString = STQMUtils.toParamString(pks, param);

		// 判断ctx是否为空,判断审核人是否为空
		if (idString.length() != 0 && STQMUtils.isNotNull(ctx)
				&& STQMUtils.isNotNull(ctx.getCaller())) {
			pk = pks[0];
			if (STQMUtils.isNotNull(pk)) {
				BOSUuid id = BOSUuid.read(pk.toString());
				BOSObjectType bosType = id.getType();

				// 获取需要作废单据对应的表名称
				IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
						.getLocalMetaDataLoader(ctx);
				EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
				DataTableInfo abrogateTableInfo = entity.getTable();
				String tableName = abrogateTableInfo.getName();

				StringBuffer sqlString = new StringBuffer();
				sqlString.setLength(0);

				sqlString = sqlString.append(" Update ").append(
						tableName.toString()).append(" set FAbrogate = 1 ")
						.append(" where fid in (").append(idString.toString())
						.append(")");

				// 执行SQL语句
				DbUtil.execute(ctx, sqlString.toString(), param.toArray());
			}
		}
	}

	/**
	 * 
	 * 描述：判断是否是新增的单据
	 * 
	 * @param ctx
	 * @param model
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:ningyan-clive 创建时间：2005-9-9
	 *                       <p>
	 */
	protected boolean isAddNew(Context ctx, IObjectValue model)
			throws EASBizException, BOSException {
		boolean ret = false;
		STBillBaseInfo aSTBillBaseInfo = (STBillBaseInfo) model;
		if (!_exists(ctx, new ObjectUuidPK(aSTBillBaseInfo.getId()))) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	protected String getMainOrgUnitName(Context ctx, OrgType orgType) {

		// 根据主业务组织类型存储对应属性字段
		String mainOrgUnitName = null;
		switch (orgType.getValue()) {
		case OrgType.SALE_VALUE:
			mainOrgUnitName = "saleOrgUnit";
			break;
		case OrgType.ADMIN_VALUE:
			mainOrgUnitName = "adminOrgUnit";
			break;
		case OrgType.COMPANY_VALUE:
			mainOrgUnitName = "companyOrgUnit";
			break;
		case OrgType.STORAGE_VALUE:
			mainOrgUnitName = "storageOrgUnit";
			break;
		case OrgType.PURCHASE_VALUE:
			mainOrgUnitName = "purOrgUnit";
			break;
		case OrgType.QUALITY_VALUE:
			mainOrgUnitName = "qualityOrgUnit";
			break;
		}

		return mainOrgUnitName;
	}

	/**
	 * 
	 * 描述：设置单据暂存状态
	 * 
	 * @author:Brina
	 * @see com.kingdee.eas.framework.app.AbstractCoreBaseControllerBean#_save(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		STBillBaseInfo info = (STBillBaseInfo) model;

		// 如果model中没有CU则加上默认的CU
		if (info.getCU() == null)
			info.setCU(super.getCU(ctx, info));

		_checkAudit(ctx, model, true);

		// 设置状态
		// info.setBillStatus(BillBaseStatusEnum.TEMPORARILYSAVED);

		// 设置状态：如果是BOTP调用，设为“新增”；如果是用户界面调用，设为“保存”
		// colin_xu,2007/05/28
		if (info.isBotpCallSave()) {
			info.setBillStatus(BillBaseStatusEnum.ADD);
		} else {
			info.setBillStatus(BillBaseStatusEnum.TEMPORARILYSAVED);
		}

		// 是否新增，新增(ID为空)或ID在DB中不存在,后面几次用到，所以先取出来，以后就不取了。
		boolean isAddNew = isAddNew(ctx, info);

		// 是否新增时自动编码
		String strCompanyID = getBizUnitOrgID(ctx, info);

		// 为了性能问题改为在前台取编码后台其实不会在生成了 2006.04.15 wanglh
		if (isAddNew) {
			if (isUseIntermitNumber(ctx, info, strCompanyID)) {
				setBillNewNumber(ctx, info, strCompanyID);
			} else if (info.getNumber() == null
					|| info.getNumber().toString().equals("")) {
				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
						.getLocalInstance(ctx);
				if (iCodingRuleManager.isExist(info, strCompanyID)) {// 设置过编码规则
				// if (iCodingRuleManager.isAddView(info, strCompanyID)) {//
				// 新增显示（即前台生成）在这里没有生成还是要生成一下
					setBillNewNumber(ctx, info, strCompanyID);
					// }
				} else {// 没有设置过编码规则时，如果是botp生成过来，则出异常提示未设置过编码规则。否则正常校验编码为不为空
					if (STUtils.isFromBOTP(info) && !info.isBotpCallSave()) {// botp生成来
																				// ,
																				// 推式临时生成不判断
						IMetaDataLoader imeataLoader = MetaDataLoaderFactory
								.getLocalMetaDataLoader(ctx);
						// IMetaDataPK pk =
						// imeataLoader.getEntityObjectPK(info.getBOSType());
						EntityObjectInfo entityObjectInfo = imeataLoader
								.getEntity(info.getBOSType());
						String alias = entityObjectInfo.getAlias();
						String[] materialNames = new String[1];
						materialNames[0] = entityObjectInfo.getAlias();
						throw new SCMBillException(SCMBillException.NOCORDRULE,
								materialNames);
					}
				}
			}
		}

		// 判断单据编码字段唯一性
		STServerUtils.checkIsDuplicate(ctx, info, "number");
		_saveSTModifyAfter(ctx, info);
		IObjectPK objectPK = super._save(ctx, info);

		// ---------------------------------------------2016.04.13
		// _moerhao_保存生成流程串号_start---------------------------------------------
		BizFlowServerHelper.doProcessing(ctx, model, BatchActionEnum.SAVE);
		// ---------------------------------------------2016.04.13
		// _moerhao_保存生成流程串号_end---------------------------------------------

		return objectPK;
	}

	protected IObjectValue _saveSTModifyAfter(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		return model;
	}

	// ---------------------------------------------2016.04.13
	// _moerhao_单据BOTP匹配流程_start---------------------------------------------
	public void _newHandleAfterTransform(Context ctx, String action,
			IObjectValue objectValue, String mappingId,
			IObjectCollection relationCol) throws EASBizException, BOSException {
		BizFlowServerHelper.bizProcessParamBuilder(ctx, objectValue,
				relationCol, BatchActionEnum.SAVE);
		objectValue.put("mappingId", mappingId);
	}

	// ---------------------------------------------2016.04.13
	// _moerhao_单据BOTP匹配流程_end---------------------------------------------

	/**
	 * 
	 * 描述：设置提交单据状态
	 * 
	 * @author:Brina
	 * @see com.kingdee.eas.framework.app.AbstractCoreBaseControllerBean#_submit(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		STBillBaseInfo info = (STBillBaseInfo) model;

		// 如果model中没有CU则加上默认的CU
		if (info.getCU() == null)
			info.setCU(super.getCU(ctx, info));

		_checkAudit(ctx, model, true);
		// 设置状态
		info.setBillStatus(BillBaseStatusEnum.SUBMITED);

		// 是否新增，新增(ID为空)或ID在DB中不存在,后面几次用到，所以先取出来，以后就不取了。
		boolean isAddNew = isAddNew(ctx, info);

		// 是否新增时自动编码
		String strCompanyID = getBizUnitOrgID(ctx, info);

		// 为了性能问题改为在前台取编码后台其实不会在生成了 2006.04.15 wanglh
		if (isAddNew) {
			if (isUseIntermitNumber(ctx, info, strCompanyID)) {
				setBillNewNumber(ctx, info, strCompanyID);
			} else if (info.getNumber() == null
					|| info.getNumber().toString().equals("")) {
				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
						.getLocalInstance(ctx);
				if (iCodingRuleManager.isExist(info, strCompanyID)) {// 设置过编码规则
					if (iCodingRuleManager.isAddView(info, strCompanyID)) {// 新增显示
																			// （
																			// 即前台生成
																			// ）
																			// 在这里没有生成还是要生成一下
						setBillNewNumber(ctx, info, strCompanyID);
					}
				} else {// 没有设置过编码规则时，如果是botp生成过来，则出异常提示未设置过编码规则。否则正常校验编码为不为空
					if (STUtils.isFromBOTP(info)) {// botp生成来
						IMetaDataLoader imeataLoader = MetaDataLoaderFactory
								.getLocalMetaDataLoader(ctx);
						// IMetaDataPK pk =
						// imeataLoader.getEntityObjectPK(info.getBOSType());
						EntityObjectInfo entityObjectInfo = imeataLoader
								.getEntity(info.getBOSType());
						String alias = entityObjectInfo.getAlias();
						String[] materialNames = new String[1];
						materialNames[0] = entityObjectInfo.getAlias();
						throw new SCMBillException(SCMBillException.NOCORDRULE,
								materialNames);
					}
				}
			}
		}

		// 判断单据编码字段唯一性
		STServerUtils.checkIsDuplicate(ctx, info, "number");

		IObjectPK objectPK = super._submit(ctx, info);

		// ---------------------------------------------2016.04.13
		// _moerhao_若在消息中心流程串号丢失则提交生成流程串号_start
		// ---------------------------------------------
		boolean flag = false;
		if (SCMServerUtils.isHasBizFlowValue(ctx, model)) {
			flag = true;
		}
		if (!flag) {
			BizFlowServerHelper
					.doProcessing(ctx, model, BatchActionEnum.SUBMIT);
		}
		// ---------------------------------------------2016.04.13
		// _moerhao_若在消息中心流程串号丢失则提交生成流程串号_end
		// ---------------------------------------------

		return objectPK;
	}

	/**
	 * 钢铁的单据，提交校验都在_submit(ctx, model)方法里，这里把另一个提交入口也指向这里
	 * 在消息中心的多级审批时，会调用_submit(Context ctx , IObjectPK pk , IObjectValue model)方法
	 * 
	 * @param ctx
	 *            Context
	 * @param pk
	 *            IObjectPK
	 * @param coreBaseInfo
	 *            CoreBaseInfo
	 * @throws BOSException
	 */
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		_submit(ctx, model);
	}

	/**
	 * 校验单据已审核或未审核
	 * 
	 * @param ctx
	 * @param model
	 * @param isAudit
	 */
	protected boolean _checkAudit(Context ctx, IObjectValue model,
			boolean isAudit) throws EASBizException, BOSException {
		STBillBaseInfo aSTBillBaseInfo = (STBillBaseInfo) model;
		if (aSTBillBaseInfo != null && aSTBillBaseInfo.getId() != null) {
			BillBaseStatusEnum billDBStatus = checkBillStatus(ctx,
					aSTBillBaseInfo.getId().toString(),
					new BillBaseStatusEnum[] { BillBaseStatusEnum.AUDITED });
			if (isAudit) {
				if (billDBStatus == null) {
					// 单据已审核
					throw new SCMBillException(SCMBillException.CHECKAUDITEDOK,
							new Object[] { aSTBillBaseInfo.getString("number"),
									"" });
				}
			} else {
				if (billDBStatus != null) {
					// 单据未审核
					throw new SCMBillException(
							SCMBillException.CHECKAUDITEDNOTOK, new Object[] {
									aSTBillBaseInfo.getString("number"), "" });
				}
			}
		}
		return true;
	}

	/**
	 * 删除。
	 * 
	 * @param ctx
	 *            Context
	 * @param pk
	 *            IObjectPK
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {

		// 校验是否能删除 _
		deleteConditionCheck(ctx, pk);

		STBillBaseInfo aSTBillBaseInfo = this.getSTBillBaseInfo(ctx, pk);
		rolbackNumber(ctx, aSTBillBaseInfo);

		// ---------------------------------------------2016.04.13
		// _moerhao_若在消息中心流程串号丢失则提交生成流程串号_start
		// ---------------------------------------------
		boolean isExistsFlow = false;
		EntityObjectInfo entityInfo = CommonUtils.getEntityByBosType(BOSUuid
				.read(pk.toString()).getType().toString());
		if (entityInfo != null) {
			EntityObjectInfo entryEntity = CommonUtils
					.getEntryEntityObject(entityInfo);
			if ((entryEntity != null) && (entryEntity.getTable() != null)) {
				String tableName = entryEntity.getTable().getName();

				if ((!(StringUtils.isEmpty(tableName)))
						&& (SCMServerUtils.isExistsBizFlowField(ctx, tableName))
						&& (SCMServerUtils.isHasBizFlowValue(ctx, tableName, pk
								.toString()))) {
					isExistsFlow = true;
				}
			}
		}
		if (isExistsFlow) {
			SCMServerUtils.executeBizFlow(ctx, aSTBillBaseInfo,
					BatchActionEnum.DELETE);
		}
		// ---------------------------------------------2016.04.13
		// _moerhao_若在消息中心流程串号丢失则提交生成流程串号_start
		// ---------------------------------------------

		super._delete(ctx, pk);

	}

	/**
	 * 
	 * 描述：检查删除条件,子类可覆盖
	 * 
	 * @param ctx
	 * @param ov
	 * 
	 * @author williamwu 2006-12-27
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void deleteConditionCheck(Context ctx, IObjectPK pk)
			throws EASBizException, BOSException {
		// STBillBaseInfo info = getSTBillBaseInfo(ctx, pk);
		//if(info.getBillStatus().getValue()!=BillBaseStatusEnum.ADD.getValue()&&
		// info.getBillStatus().getValue()!=
		// BillBaseStatusEnum.TEMPORARILYSAVED.getValue() &&
		// info.getBillStatus().getValue()!=
		// BillBaseStatusEnum.SUBMITED.getValue()&&
		// info.getBillStatus().getValue()!=
		// BillBaseStatusEnum.NULL.getValue()){
		//
		// throw new STBillException (STBillException.EXE_BILL_NOT_DELETE );
		// }

		BillBaseStatusEnum billDBStatus = checkBillStatus(ctx, pk.toString(),
				new BillBaseStatusEnum[] { BillBaseStatusEnum.ADD,
						BillBaseStatusEnum.TEMPORARILYSAVED,
						BillBaseStatusEnum.SUBMITED, BillBaseStatusEnum.NULL });
		if (billDBStatus != null) {
			throw new STBillException(STBillException.EXE_BILL_NOT_DELETE);
		}
	}

	/**
	 * 由编码规则是否设置来看是否自动生成编码 ,是否新增显示,
	 * 
	 * @param ctx
	 * @param SCMBillBaseInfo
	 *            String
	 * @throws EASBizException
	 * @throws BOSException
	 * @return
	 */
	protected HashMap getNumberRuleSet(Context ctx,
			STBillBaseInfo aSTBillBaseInfo, String strCompanyID)
			throws EASBizException, BOSException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);

		HashMap hs = new HashMap();
		boolean isExist = false;
		boolean isNotAddView = false;

		if (iCodingRuleManager.isExist(aSTBillBaseInfo, strCompanyID)) {// 设置过编码规则
			isExist = true;
			if (!iCodingRuleManager.isAddView(aSTBillBaseInfo, strCompanyID)) {// 不是新增显示
																				// （
																				// 即后台生成
																				// ）
																				// ，
																				// 返回true
				isNotAddView = true;
				// return true;
			}
		}

		hs.put("isExist", Boolean.valueOf(isExist));
		hs.put("isNotAddView", Boolean.valueOf(isNotAddView));
		return hs;

	}

	/**
	 * 检查单据状态
	 * 
	 * @param ctx
	 * @param billId
	 * @param targetStatus
	 *            预计合法的单据状态数组
	 * @return 
	 *         如果数据库中没有该记录，则返回BillBaseStatusEnum.NULL；如果数据库中的单据状态在targetStatus数组中
	 *         ，则返回null；否则返回数据库中的单据状态
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected BillBaseStatusEnum checkBillStatus(Context ctx, String billId,
			BillBaseStatusEnum[] targetStatus) throws EASBizException,
			BOSException {
		if (StringUtils.isEmpty(billId) || targetStatus == null
				|| targetStatus.length == 0) {
			return null;
		} else {
			STBillBaseInfo info = null;
			try {
				info = getSTBillBaseInfo(ctx, new ObjectStringPK(billId));
			} catch (ObjectNotFoundException e) {
				return BillBaseStatusEnum.NULL;
			}
			if (info != null && info.getBillStatus() != null) {
				for (int i = 0; i < targetStatus.length; i++) {
					if (info.getBillStatus().equals(targetStatus[i])) {
						return null;
					}
				}
				return info.getBillStatus(); // 检查不合法，返回数据库中单据的状态
			}
			return null;
		}
	}

	/**
	 * 由编码规则是否设置来看是否自动由后台生成编码 ,不包括新增显示, 即如果设置的编码规则为新增显示，本方法返回false
	 * 只要未设置过编码规则，就不自动生成，返回fals;设置过如果是新增即显示，返回fase， 如果是断号支持，返回true。
	 * 即只有设置过编码规则，并且不为新增显示（那就是要后台生成），返回true， 其它返回false; _save和sumbit方法中调用
	 * 金蝶软件（中国）有限公司 创建日期 2005-5-17 创建者 wanglh
	 * 
	 * @param ctx
	 * @param SCMBillBaseInfo
	 *            String
	 * @throws EASBizException
	 * @throws BOSException
	 * @return
	 */
	protected boolean isRuleAutoNumber(Context ctx,
			SCMBillBaseInfo aSCMBillBaseInfo, String strCompanyID)
			throws EASBizException, BOSException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);

		if (iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID)) {// 设置过编码规则
			if (!iCodingRuleManager.isAddView(aSCMBillBaseInfo, strCompanyID)) {// 不是新增显示
																				// （
																				// 即后台生成
																				// ）
																				// ，
																				// 返回true
				return true;
			} else if (iCodingRuleManager.isUseIntermitNumber(aSCMBillBaseInfo,
					strCompanyID)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * 描述: 是否断号
	 * 
	 * @param ctx
	 * @param aSCMBillBaseInfo
	 * @param strCompanyID
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author: colin_xu 创建时间:2007-6-13
	 *          <p>
	 */
	protected boolean isUseIntermitNumber(Context ctx,
			STBillBaseInfo aSTBillBaseInfo, String strCompanyID)
			throws EASBizException, BOSException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);

		if (iCodingRuleManager.isExist(aSTBillBaseInfo, strCompanyID)) {// 设置过编码规则
			if (iCodingRuleManager.isUseIntermitNumber(aSTBillBaseInfo,
					strCompanyID)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * _save和_sumbit方法中给单据自动编码
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void setBillNewNumber(Context ctx, IObjectValue model,
			String strCompanyID) throws BOSException, EASBizException {

		STBillBaseInfo aSTBillBaseInfo = (STBillBaseInfo) model;
		aSTBillBaseInfo.setNumber(getNewNumber(ctx, aSTBillBaseInfo,
				strCompanyID, ""));

	}

	protected String getBizUnitOrgID(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		// 原这种方式取主组织有问题，会存在为空的问题，现加入根据实体上的扩展属性来获取主组织的ID xmcy 2008-05-14
		// 此处取登陆组织不正确，应该取实际选中的主业务组织 colin 2009-1-11
		OrgType orgType = getMainOrgType(ctx, model.getBOSType());
		String mainOrgUnitName = getMainOrgUnitName(ctx, orgType);
		OrgUnitInfo orgUnitInfo = null;
		if (mainOrgUnitName != null) {
			Object o = model.getObjectValue(mainOrgUnitName);

			if (o instanceof OrgUnitInfo) {
				orgUnitInfo = (OrgUnitInfo) o;
			}
		}
		if (orgUnitInfo != null) {
			return orgUnitInfo.getId().toString();
		} else {

			orgUnitInfo = (OrgUnitInfo) ctx.get(orgType);
			if (orgUnitInfo != null)
				return orgUnitInfo.getId().toString();
			else
				return null;
		}
	}

	private OrgType getMainOrgType(Context ctx, BOSObjectType bosType) {
		IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
				.getLocalMetaDataLoader(ctx);
		EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
		String orgType = entity.getExtendedProperty("OrgType");
		return OrgType.getEnum(orgType);
	}

	protected boolean rolbackNumber(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		STBillBaseInfo aSTBillBaseInfo = (STBillBaseInfo) model;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);

		String strCompanyID = getBizUnitOrgID(ctx, model);

		// 定义过，并且支持断号
		if (iCodingRuleManager.isExist(aSTBillBaseInfo, strCompanyID)
				&& iCodingRuleManager.isUseIntermitNumber(aSTBillBaseInfo,
						strCompanyID)) {
			return iCodingRuleManager.recycleNumber(aSTBillBaseInfo,
					strCompanyID, aSTBillBaseInfo.getNumber());
		}
		return false;
	}

	/**
	 * 判断单据中是否存在同公司同状态同编码的单据。
	 * 
	 * @param ctx
	 *            Context
	 * @param pk
	 *            IObjectPK
	 * @param billBaseInfo
	 *            BillBaseInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @return boolean 有时(true),无(false)
	 */
	protected boolean isSameNumber(Context ctx, IObjectPK pk, IObjectValue model)
			throws EASBizException, BOSException {
		STBillBaseInfo aSTBillBaseInfo = (STBillBaseInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo("number",
				aSTBillBaseInfo.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (aSTBillBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo("id", aSTBillBaseInfo.getId(),
					CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			// filter.setMaskString("#1 and #2");
		}
		if (aSTBillBaseInfo.getCU() != null) {
			filterItem = new FilterItemInfo("CU", aSTBillBaseInfo.getCU()
					.getId().toString(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
		} else {
			filterItem = new FilterItemInfo("CU", null, CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < filter.getFilterItems().size(); i++) {
			if (i != 0)
				sb.append(" and #" + i);
			else
				sb.append("#" + i);
		}
		filter.setMaskString(sb.toString());

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("id"));

		// if(pks.length>0)
		if (super._exists(ctx, filter)) {
			return true;
		}
		return false;
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
		aSTBillBaseInfo.setNumber(number);

		ObjectUuidPK pk = new ObjectUuidPK(aSTBillBaseInfo.getId());
		if (isSameNumber(ctx, pk, aSTBillBaseInfo)) {
			// 数据库中有此编码的卡片，说明编码被占用，要另生成,相当于跳号
			String newNumber = "";

			newNumber = iCodingRuleManager.getNumber(aSTBillBaseInfo,
					strCompanyID, customString);

			if (newNumber.equals(aSTBillBaseInfo.getNumber())) {//重新生成的编码和上次的一样。
				// 按编码规则定义生成的编码重复
				throw new SCMBillException(SCMBillException.NUMBERRULEERROR);
			}
			number = newNumber;
			aSTBillBaseInfo.setNumber(newNumber);
			if (isSameNumber(ctx, pk, aSTBillBaseInfo)) {
				return getNewNumber(ctx, aSTBillBaseInfo, strCompanyID,
						customString);
			}
		}
		return number;

	}

	protected String _getAddNewPermItemName(Context ctx, String objectType)
			throws BOSException {

		String permItemName = null;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select FName from t_pm_permitem where FObjectType = '")
				.append(objectType).append("' AND FOperationType = 'ADDNEW'");
		IRowSet permItemNameRowSet = DbUtil
				.executeQuery(ctx, strSQL.toString());
		try {
			if (permItemNameRowSet != null && permItemNameRowSet.next()) {
				permItemName = permItemNameRowSet.getString("FName");
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

		return permItemName;
	}

	protected String _getViewPermItemName(Context ctx, String objectType)
			throws BOSException {

		String permItemName = null;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select FName from t_pm_permitem where FObjectType = '")
				.append(objectType).append("' AND FOperationType = 'READ'");
		IRowSet permItemNameRowSet = DbUtil
				.executeQuery(ctx, strSQL.toString());
		try {
			if (permItemNameRowSet != null && permItemNameRowSet.next()) {
				permItemName = permItemNameRowSet.getString("FName");
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

		return permItemName;
	}

	protected String _getCodeRuleServer(Context ctx, IObjectValue coreBillInfo,
			String companyID) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		String sysNumber = null;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);
		if (iCodingRuleManager.isExist(coreBillInfo, companyID)) {
			if (iCodingRuleManager.isUseIntermitNumber(coreBillInfo, companyID))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				sysNumber = iCodingRuleManager.readNumber(coreBillInfo,
						companyID);
			} else {
				// 没有启用断号支持功能，此时获取了编码规则产生的编码
				sysNumber = iCodingRuleManager.getNumber(coreBillInfo,
						companyID);
			}
		}

		return sysNumber;
	}

	protected void _recycleNumberByOrg(Context ctx, IObjectValue editData,
			String orgType, String number) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		if (!StringUtils.isEmpty(number)) {
			String companyID = null;
			com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
					.getLocalInstance(ctx);
			if (!com.kingdee.util.StringUtils.isEmpty(orgType)
					&& !"NONE".equalsIgnoreCase(orgType)
					&& com.kingdee.eas.common.client.SysContext.getSysContext()
							.getCurrentOrgUnit(
									com.kingdee.eas.basedata.org.OrgType
											.getEnum(orgType)) != null) {
				companyID = com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentOrgUnit(
								com.kingdee.eas.basedata.org.OrgType
										.getEnum(orgType)).getString("id");
			} else if (com.kingdee.eas.common.client.SysContext.getSysContext()
					.getCurrentOrgUnit() != null) {
				companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentOrgUnit()).getString("id");
			}
			if (!StringUtils.isEmpty(companyID)
					&& iCodingRuleManager.isExist(editData, companyID)
					&& iCodingRuleManager.isUseIntermitNumber(editData,
							companyID)) {
				iCodingRuleManager.recycleNumber(editData, companyID, number);
			}

		}
	}

	protected STBillBaseCodingRuleVo _getCodeRuleBizVo(Context ctx,
			STBillBaseCodingRuleVo codeVo) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		codeVo.setSysNumber(_getCodeRuleServer(ctx, codeVo.getCoreBillInfo(),
				codeVo.getCompanyID()));
		// 提取编码规则设定的是否可以修改
		boolean isModifiable = false;
		boolean isExist = false;
		boolean isAddView = false; // 是否新增显示
		// 是否存在启用的编码规则
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);
		isExist = iCodingRuleManager.isExist(codeVo.getCoreBillInfo(), codeVo
				.getCompanyID());
		if (isExist) {
			isModifiable = iCodingRuleManager.isModifiable(codeVo
					.getCoreBillInfo(), codeVo.getCompanyID());
			isAddView = iCodingRuleManager.isAddView(codeVo.getCoreBillInfo(),
					codeVo.getCompanyID());
		}
		codeVo.setExist(isExist);
		codeVo.setModifiable(isModifiable);
		codeVo.setAddView(isAddView);
		return codeVo;
	}

	protected OrgUnitInfo[] _getBizOrgUnit(Context ctx, ObjectStringPK userID,
			OrgType orgType, String permissionItem) throws BOSException,
			EASBizException {
		// TODO 自动生成方法存根
		OrgUnitInfo[] mainOrgs = null;
		if (orgType != null) {
			// TODO 能否从SCM引用？？？
			FullOrgUnitCollection collection = getAuthOrgByPermItem(ctx,
					userID, orgType, permissionItem);
			if (collection != null) {
				mainOrgs = getOrgUnitInfosByType(ctx, collection, orgType);
			}
		}
		return mainOrgs;
	}

	public static FullOrgUnitCollection getAuthOrgByPermItem(Context ctx,
			IObjectPK user, OrgType orgType, String permItem)
			throws BOSException, EASBizException {
		FullOrgUnitCollection result = null;

		IPermission permission = PermissionFactory.getLocalInstance(ctx);
		result = permission.getAuthorizedOrg(user, orgType, null, permItem);

		return result;
	}

	public static OrgUnitInfo[] getOrgUnitInfosByType(Context ctx,
			FullOrgUnitCollection orgs, OrgType orgType) {
		OrgUnitInfo[] results = null;

		if (orgs == null || orgs.size() == 0) {
			return null;
		}

		LinkedHashSet keys = new LinkedHashSet();
		for (int i = 0; i < orgs.size(); i++) {
			keys.add(orgs.get(i).getId().toString());
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", keys, CompareType.INCLUDE));
		// 行政组织无虚体实体之分
		if (!orgType.equals(OrgType.Admin)) {
			filter.getFilterItems().add(
					new FilterItemInfo("isBizUnit", Boolean.valueOf(true),
							CompareType.EQUALS));
		}
		view.setFilter(filter);
		try {
			switch (orgType.getValue()) {
			case 0: // Admin
			{
				IAdminOrgUnit iAdmin = AdminOrgUnitFactory
						.getLocalInstance(ctx);

				AdminOrgUnitCollection collection = iAdmin
						.getAdminOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}

			case 1: // Company
			{
				ICompanyOrgUnit iCompany = CompanyOrgUnitFactory
						.getLocalInstance(ctx);
				CompanyOrgUnitCollection collection = iCompany
						.getCompanyOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 2: // Sale
			{
				ISaleOrgUnit iSale = SaleOrgUnitFactory.getLocalInstance(ctx);
				SaleOrgUnitCollection collection = iSale
						.getSaleOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 3: // Purchase
			{
				IPurchaseOrgUnit iPurchase = PurchaseOrgUnitFactory
						.getLocalInstance(ctx);
				PurchaseOrgUnitCollection collection = iPurchase
						.getPurchaseOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 4: // Storage
			{
				IStorageOrgUnit iStorage = StorageOrgUnitFactory
						.getLocalInstance(ctx);
				StorageOrgUnitCollection collection = iStorage
						.getStorageOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 32: // Quality
			{
				IQualityOrgUnit iQuality = QualityOrgUnitFactory
						.getLocalInstance(ctx);
				QualityOrgUnitCollection collection = iQuality
						.getQualityOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 钢铁单据初始化时，批量参数值取数。
	 * 
	 * @author yangyong
	 */
	protected HashMap _getSTBillParam(Context ctx, HashMap map)
			throws BOSException, EASBizException {
		HashMap returnMap = new HashMap();

		String bosType = (String) map.get("bostype");
		String infoId = (String) map.get("infoId");

		IBOTMapping iBOTMapping = BOTMappingFactory.getLocalInstance(ctx);
		String targetBillBosTypeAndAliasString = iBOTMapping
				.getTargetBillTypeList(bosType);

		returnMap.put("targetBillBosTypeAndAliasString",
				targetBillBosTypeAndAliasString);
		try {
			StringBuffer sqlString = new StringBuffer();
			// 取打印次数
			sqlString
					.append("select FConfigValue as FConfigValue from T_ST_STBillConfig where FBILLID=? and FConfigType='BillprintQty'");
			String id = null;
			if (infoId != null) {
				id = infoId;
				List param = new ArrayList();
				param.add(id);
				IRowSet rs = DbUtil.executeQuery(ctx, sqlString.toString(),
						param.toArray());
				if (rs.next())
					returnMap.put("BillprintQty", rs.getString("FConfigValue"));

			}
			sqlString.setLength(0);
			if (map.get("ui") != null) {
				sqlString
						.append("select FConfigValue from T_ST_STBillConfig where FBillClassName='");
				sqlString.append(map.get("ui"));
				sqlString.append("' and FConfigType='isSubmitPrint'");
				sqlString.append(" and FUserID='").append(
						ContextUtil.getCurrentUserInfo(ctx).getId().toString())
						.append("'");

				IRowSet rs = DbUtil.executeQuery(ctx, sqlString.toString());
				if (rs.next())
					returnMap
							.put("isSubmitPrint", rs.getString("FConfigValue"));

				sqlString.setLength(0);
				sqlString
						.append("select FConfigValue from T_ST_STBillConfig where FBillClassName='");
				sqlString.append(map.get("ui"));
				sqlString.append("' and FConfigType='isAuditPrint'");
				sqlString.append(" and FUserID='").append(
						ContextUtil.getCurrentUserInfo(ctx).getId().toString())
						.append("'");

				rs = DbUtil.executeQuery(ctx, sqlString.toString());
				if (rs.next())
					returnMap.put("isAuditPrint", rs.getString("FConfigValue"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMap;
	}

	protected HashMap _getSTBillVo(Context ctx, HashMap map)
			throws BOSException, EASBizException {
		// "codeRuleVo"
		HashMap returnMap = new HashMap();
		STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo = (STBillBaseCodingRuleVo) map
				.get("codeRuleVo");
		returnMap.put("codeRuleVo", _getCodeRuleBizVo(ctx,
				aSTBillBaseCodingRuleVo));
		// 获取相关的单据打印值（billPrintQty）。这个值是针对于具体单据的。
		// 及是否提交即打印的设置(isSubmitPrint)。这个值是针对于单据类型的。
		STBillBaseInfo aSTBillInfo = (STBillBaseInfo) aSTBillBaseCodingRuleVo
				.getCoreBillInfo();
		IBOTMapping iBOTMapping = BOTMappingFactory.getLocalInstance(ctx);
		String targetBillBosTypeAndAliasString = iBOTMapping
				.getTargetBillTypeList(aSTBillInfo.getBOSType().toString());
		returnMap.put("targetBillBosTypeAndAliasString",
				targetBillBosTypeAndAliasString);
		try {
			StringBuffer sqlString = new StringBuffer();
			// 取打印次数
			sqlString
					.append("select FConfigValue as FConfigValue from T_ST_STBillConfig where FBILLID=? and FConfigType='BillprintQty'");
			String id = null;
			if (aSTBillInfo.getId() != null) {
				id = aSTBillInfo.getId().toString();
				List param = new ArrayList();
				param.add(id);
				IRowSet rs = DbUtil.executeQuery(ctx, sqlString.toString(),
						param.toArray());
				if (rs.next())
					returnMap.put("BillprintQty", rs.getString("FConfigValue"));

			}
			sqlString.setLength(0);
			if (map.get("ui") != null) {
				sqlString
						.append("select FConfigValue from T_ST_STBillConfig where FBillClassName='");
				sqlString.append(map.get("ui"));
				sqlString.append("' and FConfigType='isSubmitPrint'");
				sqlString.append(" and FUserID='").append(
						ContextUtil.getCurrentUserInfo(ctx).getId().toString())
						.append("'");

				IRowSet rs = DbUtil.executeQuery(ctx, sqlString.toString());
				if (rs.next())
					returnMap
							.put("isSubmitPrint", rs.getString("FConfigValue"));

				sqlString.setLength(0);
				sqlString
						.append("select FConfigValue from T_ST_STBillConfig where FBillClassName='");
				sqlString.append(map.get("ui"));
				sqlString.append("' and FConfigType='isAuditPrint'");
				sqlString.append(" and FUserID='").append(
						ContextUtil.getCurrentUserInfo(ctx).getId().toString())
						.append("'");

				rs = DbUtil.executeQuery(ctx, sqlString.toString());
				if (rs.next())
					returnMap.put("isAuditPrint", rs.getString("FConfigValue"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnMap;
	}

	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {
		// 暂时屏蔽botp反写功能

	}

	protected List _getUnAuditBills(Context ctx, List idList)
			throws BOSException {
		if (idList == null) {
			return null;
		}

		String idListStr = "";
		String tableName = "";
		List unAuditBills = new ArrayList();

		try {
			for (int i = 0; i < idList.size(); i++) {
				if (i == 0) {
					String id = (String) idList.get(0);
					if (id != null) {
						BOSUuid uuId = BOSUuid.read(idList.get(0).toString());
						BOSObjectType bosType = uuId.getType();

						// 获取需要审核单据对应的表名称
						IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
								.getLocalMetaDataLoader(ctx);
						EntityObjectInfo entity = metaDataLoader
								.getEntity(bosType);
						DataTableInfo updateTableInfo = entity.getTable();
						tableName = updateTableInfo.getName();
					}
				}

				if (i == idList.size() - 1) {
					idListStr += "'" + idList.get(i) + "'";
				} else {
					idListStr += "'" + idList.get(i) + "',";
				}
			}

			StringBuffer sqlString = new StringBuffer();
			sqlString.append(
					"select FID from " + tableName + " where FID in("
							+ idListStr + ")").append(sp).append(
					"and FBillStatus = " + BillBaseStatusEnum.SUBMITED_VALUE);

			// 执行SQL语句
			IRowSet rs = DbUtil.executeQuery(ctx, sqlString.toString());

			if (rs.next()) {
				unAuditBills.add(rs.getString("FID"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return unAuditBills;
	}

	protected void auditAfterSubmit(Context ctx, IObjectValue model,
			StorageOrgUnitInfo storageOrgUnitInfo, String number)
			throws BOSException, EASBizException {
		boolean iFlag = false;

		STBillBaseInfo info = null;

		if (model instanceof STBillBaseInfo) {
			info = (STBillBaseInfo) model;
			if (STQMUtils.isNotNull(storageOrgUnitInfo)) {
				iFlag = STParamReader
						.getParameter(
								ctx,
								STParamConstant
										.getSTSysParamNumberByType(
												STParamConstant.STSYSPARAMS_KEYID_ISAUDITSUBMIT,
												number),
								STParamViewInfo.isAuditAfterSubmitViewInfo(
										storageOrgUnitInfo.getId().toString(),
										number)).IsAuditAfterSubmitparamVlaue();
			}
		}

		// 通知单执行的系统变量（提交即审核）
		if (iFlag) {
			IObjectPK pk = new ObjectUuidPK(info.getId());
			_audit(ctx, new IObjectPK[] { pk });
		}
	}

	/**
	 * 获得当前用户对当前单据有查询权限的所有主业务组织 2013-05-15
	 */
	protected OrgUnitCollection _getAuthOrgsByType(Context ctx,
			OrgType orgType, BOSObjectType bosType) throws BOSException,
			EASBizException {
		String permItem = getPermissionItem(ctx, bosType);

		OrgUnitCollection collection = SCMGroupServerUtils
				.getAuthOrgByPermItem(ctx, orgType, permItem);

		return SCMGroupServerUtils.getOrgUnitInfosByType(ctx, collection,
				orgType);
	}

	/**
	 * 获得当前用户在当前组织对当前单据的 查看权限名称 2013-05-15
	 */
	public String getPermissionItem(Context ctx, BOSObjectType bosType)
			throws BOSException, EASBizException {
		String currentUserId = ctx.getCaller().toString();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT pi.Fname FROM T_PM_UserOrgPerm as uop ");
		sql.append("INNER JOIN T_PM_PermItem as pi ");
		sql.append("ON uop.FPERMITEMID = pi.FID ");
		sql.append("WHERE uop.FOwner='").append(currentUserId).append("'");
		sql.append(" AND pi.FObjectType='").append(bosType.toString()).append(
				"'");
		sql.append(" AND pi.FOperationType='READ'");

		RowSet rowSet = DbUtil.executeQuery(ctx, sql.toString());

		try {
			if (rowSet.next()) {
				return rowSet.getString("Fname");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		return null;
	}
}