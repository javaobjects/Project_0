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
	 * ��ȡԴ��Id
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param targetPK
	 *            Ŀ�굥��ͷid
	 * @param srcBOSType
	 *            Դ����ͷbostype
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

	// ִ�з�д
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

	// ִ�з�д
	private static void doWriteBackEntryInStorageStatus(Context ctx,
			Set entryPKs, int action, String isInstorageFieldName,
			String entryTableName) throws BOSException {
		doWriteBackEntryInStorageStatus(ctx, entryPKs, "FId", null, action,
				isInstorageFieldName, entryTableName);
	}

	/**
	 * ��ȡԴ����¼ID
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param targetPK
	 *            Ŀ�굥��ͷid
	 * @param srcBOSType
	 *            Դ����ͷbostype
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
	 * ��д�ʼ챨�棨��ˣ�
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param pk
	 *            Ŀ�굥����������⡢������⣩
	 * @param aInvBillBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void writeBackQIReport(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		// ��ȡ�ʼ챨���¼id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_QIREPORT);
		// 54�������� ������ 2009-8-26
		// �������ܣ������Ѿ�ͨ����¼���ƣ�����Ҫ�ټ��
		// ���״̬
		// checkInStorageStatus(ctx, srcObjectPKs, ACTION_AUDIT,
		// STConstant.BOSTYPE_QIREPORT,
		// "FIsInstorage", "T_ST_QIReportSeqDetail", "T_ST_QIReport");
		// ִ�з�д����
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_AUDIT, "FIsInstorage",
				"T_ST_QIReportSeqDetail");
	}

	/**
	 * ��д�ʼ챨�棨����ˣ�
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

		// 54�������� ������ 2009-8-26
		// �������ܣ��ӷ�¼�޷�������ȡ��ϵ�����Դӵ�ͷ��ȡ
		// ��ȡ�ʼ챨���¼id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_QIREPORT);
		// ִ�з�д����
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_UNAUDIT, "FIsInstorage",
				"T_ST_QIReportSeqDetail");
	}

	/**
	 * ��д����֪ͨ������ˣ�
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-20
	 * @param ctx
	 * @param pk
	 *            Ŀ�굥����������⡢������⣩
	 * @param aInvBillBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void writeBackProduceChangeNotice(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		// ��ȡ�ʼ챨���¼id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_PRODUCECHANGENOTICEBILL);
		// 54�������� ������ 2009-8-26
		// �������ܣ������Ѿ�ͨ����¼���ƣ�����Ҫ�ټ��
		// ���״̬
		// checkInStorageStatus(ctx, srcObjectPKs, ACTION_AUDIT,
		// STConstant.BOSTYPE_PRODUCECHANGENOTICEBILL,
		// "FIsInstorage", "T_ST_ProduceChangeNoticeEntry",
		// "T_ST_ProduceChangeNotice");
		// ִ�з�д����
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_AUDIT, "FIsInstorage",
				"T_ST_ProduceChangeNoticeEntry");
	}

	/**
	 * ��д����֪ͨ��������ˣ�
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
		// ��ȡ�ʼ챨���¼id
		HashSet srcObjectPKs = getSrcObjectPKSet(ctx, pk,
				STConstant.BOSTYPE_PRODUCECHANGENOTICEBILL);
		// ִ�з�д����
		doWriteBackEntryInStorageStatus(ctx, srcObjectPKs, "FParentID", pk
				.toString(), ACTION_UNAUDIT, "FIsInstorage",
				"T_ST_ProduceChangeNoticeEntry");
	}

	/**
	 * ��д��ϸ�������������ˣ�
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
		// ��ȡ�ʼ챨���¼id
		HashSet srcEntryPKs = getSrcEntryPKSet(ctx, pk,
				STConstant.BOSTYPE_DETAILSTORAGEADJUSTBILL);
		// ִ�з�д����
		doWriteBackInStorageStatus(ctx, srcEntryPKs, ACTION_UNAUDIT,
				"FIsInstorage", "T_ST_DetailSABE");
	}

	// ִ�з�д
	// 54�������� ������ 2009-8-26
	// �������ܣ����ӿɴ�������PK��Ӧ�ֶ�
	private static void doWriteBackEntryInStorageStatus(Context ctx,
			Set entryPKs, String pkFieldName, String inStorageBillID,
			int action, String isInstorageFieldName, String entryTableName)
			throws BOSException {
		if (entryPKs.size() > 0) {

			if (action == ACTION_AUDIT) {
				// 54�������� ������ 2009-8-25
				// �������ܣ����ӷ�д���к���������ֶ�
				String sql = "update " + entryTableName + " set "
						+ isInstorageFieldName + "=?";
				// ֻ���ʼ챨�������Ʒ��ϸ��Ҫ�Ӹ��к��������
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					sql = sql + ",FGpInStorageDate = ?,FInStorageBillID = ? ";
				}
				sql = sql + "where (" + pkFieldName + " in("
						+ STUtils.toIDString(entryPKs.iterator())
						+ ")) AND FIsInstorage = 0";
				List param = new ArrayList();
				param.add(new Integer(1));
				// ֻ���ʼ챨�������Ʒ��ϸ��Ҫ�Ӹ��к��������
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					Calendar cal = Calendar.getInstance();
					Timestamp ts = new Timestamp(cal.getTimeInMillis());
					param.add(ts);
					param.add(inStorageBillID);
				}

				DbUtil.execute(ctx, sql, param.toArray());
			} else if (action == ACTION_UNAUDIT) {
				// 54�������� ������ 2009-8-25
				// �������ܣ����ӷ�д���к���������ֶ�
				String sql = "update " + entryTableName + " set "
						+ isInstorageFieldName + "=?";
				// ֻ���ʼ챨�������Ʒ��ϸ��Ҫ�Ӹ��к��������
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					sql = sql
							+ ",FGpInStorageDate = null,FInStorageBillID = null  ";
				}
				sql = sql + "where (" + pkFieldName + " in("
						+ STUtils.toIDString(entryPKs.iterator()) + "))";
				// ֻ���ʼ챨�������Ʒ��ϸ��Ҫ�Ӹ��к��������
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					sql = sql + " AND FInStorageBillID = ? ";
				}
				List param = new ArrayList();
				param.add(new Integer(0));
				// ֻ���ʼ챨�������Ʒ��ϸ��Ҫ�Ӹ��к��������
				if (entryTableName.equalsIgnoreCase("T_ST_QIReportSeqDetail")) {
					param.add(inStorageBillID);
				}
				DbUtil.execute(ctx, sql, param.toArray());
			}
		}
	}
}
