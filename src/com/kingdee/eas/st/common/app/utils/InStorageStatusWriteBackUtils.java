package com.kingdee.eas.st.common.app.utils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class InStorageStatusWriteBackUtils {

	public static final int ACTION_AUDIT = 1;
	public static final int ACTION_UNAUDIT = 2;

	public static final String b = " \n ";

	/**
	 * 获取源单Id
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param targetPK
	 *            目标单表头id
	 * @param srcBOSType
	 *            源单表头bostype
	 * @return
	 * @throws BOSException
	 */
	private static HashSet getSrcObjectPKSet(Context ctx, IObjectPK targetPK,
			String srcBOSType) throws BOSException {
		HashSet set = new HashSet();
		StringBuffer sb = new StringBuffer();
		sb.append("select h.FSrcObjectId").append(b);
		sb.append("from T_BOT_Relation h").append(b);
		sb.append("where h.FDestObjectId=?").append(b);
		sb.append("and h.FSrcEntityId=?");
		IRowSet rs = DbUtil.executeQuery(ctx, sb.toString(), new Object[] {
				targetPK.toString(), srcBOSType });
		try {
			while (rs.next()) {
				set.add(rs.getString("FSrcObjectId"));
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return set;
	}

	// 执行反写
	private static void doWriteBackInStorageStatus(Context ctx, Set entryPKs,
			int action, String isInstorageFieldName, String headTableName)
			throws BOSException {
		if (entryPKs.size() > 0) {
			String sql = "update " + headTableName + " set "
					+ isInstorageFieldName + "=?" + " where FId in("
					+ STUtils.toIDString(entryPKs.iterator()) + ")";

			if (action == ACTION_AUDIT) {
				DbUtil.execute(ctx, sql, new Object[] { new Integer(1) });
			} else if (action == ACTION_UNAUDIT) {
				DbUtil.execute(ctx, sql, new Object[] { new Integer(0) });
			}
		}
	}

	// 执行反写
	private static void doWriteBackEntryInStorageStatus(Context ctx,
			Set entryPKs, int action, String isInstorageFieldName,
			String entryTableName) throws BOSException {
		doWriteBackEntryInStorageStatus(ctx, entryPKs, "FId", null, action,
				isInstorageFieldName, entryTableName);
	}

	/**
	 * 获取源单分录ID
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param targetPK
	 *            目标单表头id
	 * @param srcBOSType
	 *            源单表头bostype
	 * @return
	 * @throws BOSException
	 */
	private static HashSet getSrcEntryPKSet(Context ctx, IObjectPK targetPK,
			String srcBOSType) throws BOSException {
		HashSet set = new HashSet();
		StringBuffer sb = new StringBuffer();
		sb.append("select e.FSrcEntryId").append(b);
		sb.append("from T_BOT_RelationEntry e").append(b);
		sb.append("inner join T_BOT_Relation h on e.FKeyID=h.FId").append(b);
		sb.append("where e.FDestObjectId=?").append(b);
		sb.append("and h.FSrcEntityId=?");
		IRowSet rs = DbUtil.executeQuery(ctx, sb.toString(), new Object[] {
				targetPK.toString(), srcBOSType });
		try {
			while (rs.next()) {
				set.add(rs.getString("FSrcEntryId"));
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return set;
	}

	/**
	 * 反写质检报告（审核）
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param pk
	 *            目标单（如生产入库、其它入库）
	 * @param aInvBillBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void writeBackQIReport(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		// 获取质检报告分录id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_QIREPORT);
		// 54新增功能 许昭林 2009-8-26
		// 新增功能：报告已经通过分录控制，不需要再检查
		// 检查状态
		// checkInStorageStatus(ctx, srcObjectPKs, ACTION_AUDIT,
		// STConstant.BOSTYPE_QIREPORT,
		// "FIsInstorage", "T_ST_QIReportSeqDetail", "T_ST_QIReport");
		// 执行反写动作
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_AUDIT, "FIsInstorage",
				"T_ST_QIReportSeqDetail");
	}

	/**
	 * 反写质检报告（反审核）
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param pk
	 * @param aInvBillBaseInfo
	 * @throws BOSException
	 */
	public static void unWriteBackQIReport(Context ctx, IObjectPK pk)
			throws BOSException {

		// 54新增功能 许昭林 2009-8-26
		// 新增功能：从分录无法完整获取关系，所以从单头获取
		// 获取质检报告分录id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_QIREPORT);
		// 执行反写动作
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_UNAUDIT, "FIsInstorage",
				"T_ST_QIReportSeqDetail");
	}

	/**
	 * 反写改判通知单（审核）
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param pk
	 *            目标单（如生产入库、其它入库）
	 * @param aInvBillBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void writeBackProduceChangeNotice(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		// 获取质检报告分录id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_PRODUCECHANGENOTICEBILL);
		// 54新增功能 许昭林 2009-8-26
		// 新增功能：报告已经通过分录控制，不需要再检查
		// 检查状态
		// checkInStorageStatus(ctx, srcObjectPKs, ACTION_AUDIT,
		// STConstant.BOSTYPE_PRODUCECHANGENOTICEBILL,
		// "FIsInstorage", "T_ST_ProduceChangeNoticeEntry",
		// "T_ST_ProduceChangeNotice");
		// 执行反写动作
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_AUDIT, "FIsInstorage",
				"T_ST_ProduceChangeNoticeEntry");
	}

	/**
	 * 反写改判通知单（反审核）
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param pk
	 * @param aInvBillBaseInfo
	 * @throws BOSException
	 */
	public static void unWriteBackProduceChangeNotice(Context ctx, IObjectPK pk)
			throws BOSException {
		// 获取质检报告分录id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_PRODUCECHANGENOTICEBILL);
		// 执行反写动作
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_UNAUDIT, "FIsInstorage",
				"T_ST_ProduceChangeNoticeEntry");
	}

	/**
	 * 反写明细库调整单（反审核）
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param pk
	 * @param aInvBillBaseInfo
	 * @throws BOSException
	 */
	public static void unWriteBackDetailStorageAdjustBill(Context ctx,
			IObjectPK pk) throws BOSException {
		// 获取质检报告分录id
		HashSet srcEntryPKs = getSrcEntryPKSet(ctx, pk,
				STConstant.BOSTYPE_DETAILSTORAGEADJUSTBILL);
		// 执行反写动作
		doWriteBackInStorageStatus(ctx, srcEntryPKs, ACTION_UNAUDIT,
				"FIsInstorage", "T_ST_DetailSABE");
	}

	// 执行反写
	// 54新增功能 许昭林 2009-8-26
	// 新增功能：增加可传入条件PK对应字段
	private static void doWriteBackEntryInStorageStatus(Context ctx,
			Set entryPKs, String pkFieldName, String inStorageBillID,
			int action, String isInstorageFieldName, String entryTableName)
			throws BOSException {
		if (entryPKs.size() > 0) {

			if (action == ACTION_AUDIT) {
				// 54新增功能 许昭林 2009-8-25
				// 新增功能：增加反写改判后入库日期字段
				String sql = "update " + entryTableName + " set "
						+ isInstorageFieldName + "=?";
				// 只有质检报告关联产品明细需要加改判后入库日期
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					sql = sql + ",FGpInStorageDate = ?,FInStorageBillID = ? ";
				}
				sql = sql + "where (" + pkFieldName + " in("
						+ STUtils.toIDString(entryPKs.iterator())
						+ ")) AND FIsInstorage = 0";
				List param = new ArrayList();
				param.add(new Integer(1));
				// 只有质检报告关联产品明细需要加改判后入库日期
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					Calendar cal = Calendar.getInstance();
					Timestamp ts = new Timestamp(cal.getTimeInMillis());
					param.add(ts);
					param.add(inStorageBillID);
				}

				DbUtil.execute(ctx, sql, param.toArray());
			} else if (action == ACTION_UNAUDIT) {
				// 54新增功能 许昭林 2009-8-25
				// 新增功能：增加反写改判后入库日期字段
				String sql = "update " + entryTableName + " set "
						+ isInstorageFieldName + "=?";
				// 只有质检报告关联产品明细需要加改判后入库日期
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					sql = sql
							+ ",FGpInStorageDate = null,FInStorageBillID = null  ";
				}
				sql = sql + "where (" + pkFieldName + " in("
						+ STUtils.toIDString(entryPKs.iterator()) + "))";
				// 只有质检报告关联产品明细需要加改判后入库日期
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					sql = sql + " AND FInStorageBillID = ? ";
				}
				List param = new ArrayList();
				param.add(new Integer(0));
				// 只有质检报告关联产品明细需要加改判后入库日期
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					param.add(inStorageBillID);
				}
				DbUtil.execute(ctx, sql, param.toArray());
			}
		}
	}
}
