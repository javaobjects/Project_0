package com.kingdee.eas.st.common.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.eas.st.common.util.param.STParamReader;
import com.kingdee.eas.st.common.util.param.STParamViewInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public abstract class STFloorCalServerUtils {

	private static String sp = " \r\n";

	/**
	 * 
	 * 描述：审核时，对层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新 更新包含：1先对原有单据进行计算
	 * 2根据原有单据最大层次去更新已经存在的明细库数据。 适用于：待检入库单等入库类型的单据。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForInBillProcess(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {

		if (isAudit) {
			if (billTableName.equals("T_ST_DetailSABE"))
				calFloorSeqForDetailAdjustBill(ctx, pk);
			else
				calFloorSeqForBill(ctx, billTableName, pk);
		}
		calFloorSeqForProduceDetail(ctx, billTableName, pk, isAudit);

		if (billTableName.equals("T_ST_LadingEntry")) {
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("update T_ST_ProduceDetail set ffloornumber= ")
					.append(sp);
			sqlString
					.append(
							"(select Distinct ffloornumber from T_ST_LadingEntry a  where a.FSequenceID=T_ST_ProduceDetail.Fid ")
					.append(sp);
			sqlString.append("and a.fparentid='").append(pk).append("') ")
					.append(sp);
			sqlString
					.append(
							" where Fid in (select FSEQUENCEid from T_ST_LadingEntry  where fparentid='")
					.append(pk).append("' )  ").append(sp);
			DbUtil.execute(ctx, sqlString.toString());
		}
	}

	/**
	 * 
	 * 描述：审核时，对层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新 更新包含：1先对原有单据进行计算
	 * 2根据原有单据最大层次去更新已经存在的明细库数据。 适用于：待检入库单等入库类型的单据。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForDetailAdjustBillProcess(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {

		String wareHouseFieldName = "FLocationID";

		StringBuffer otherSql = new StringBuffer();
		otherSql
				.append(
						" where fid  in (select Distinct b.fid from T_ST_ProduceDetail b")
				.append(sp);
		otherSql.append("right join  T_ST_DetailSABE a on ").append(sp);
		otherSql.append("a.fblocknumber=b.fblocknumber ").append(sp);
		otherSql.append("and a.FRowNumber=b.FRowNumber ").append(sp);
		otherSql.append("and a.FButtressNumber=b.FButtressNumber ").append(sp);
		otherSql
				.append(
						"where TO_INTEGER(a.ffloornumber)>=TO_INTEGER(b.ffloornumber) ")
				.append(sp);
		otherSql.append("and a.fparentid='").append(pk).append("')").append(sp);

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();

		// 先更新为最新的层号
		sqlString.setLength(0);
		sqlString.append("update T_ST_DetailSABE set ffloornumber=").append(sp);
		sqlString.append("(select ffloornumber from T_ST_ProduceDetail a ")
				.append(sp);
		sqlString.append("where a.fsequence=T_ST_DetailSABE.fsequence) ")
				.append(sp);
		sqlString.append("where fparentid='").append(pk).append("'").append(sp);
		DbUtil.execute(ctx, sqlString.toString());

		sqlString.setLength(0);
		sqlString.append("update T_ST_DetailSABE ").append(sp);
		sqlString
				.append(
						" set FContractNumber=FButtressNumber,FButtressNumber='st_chenyong_kingdee.com' ")
				.append(sp);
		sqlString.append("where fparentid='").append(pk).append(
				"' and FUnpackSequence  is  null ").append(sp);
		DbUtil.execute(ctx, sqlString.toString());

		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FHandlerid=FLocationid,FLocationid='").append(
				tempid).append("'").append(sp);
		sqlString.append(otherSql.toString());

		DbUtil.execute(ctx, sqlString.toString());

		if (isAudit)
			calFloorSeqForDetailAdjustBill(ctx, pk);

		calFloorSeqForProduceDetailMinAdjust(ctx, billTableName, pk, isAudit);

		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());

		sqlString.setLength(0);
		sqlString.append("update T_ST_DetailSABE ").append(sp);
		sqlString.append(
				" set FContractNumber='',FButtressNumber=FContractNumber ")
				.append(sp);
		sqlString.append("where fparentid='").append(pk).append(
				"' and FUnpackSequence  is  null ").append(sp);
		DbUtil.execute(ctx, sqlString.toString());

	}

	/**
	 * 
	 * 描述：反审核操作，应用于明细库调整单的拆分业务 更新包含： 适用于：
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForDetailAdjustBillUnAuditProcess(
			Context ctx, String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {
		StringBuffer otherSql = new StringBuffer();
		otherSql
				.append(
						" where fid  in (select Distinct b.fid from T_ST_ProduceDetail b")
				.append(sp);
		otherSql.append("right join  T_ST_DetailSABE a on ").append(sp);
		otherSql.append("a.fblocknumber=b.fblocknumber ").append(sp);
		otherSql.append("and a.FRowNumber=b.FRowNumber ").append(sp);
		otherSql.append("and a.FButtressNumber=b.FButtressNumber ").append(sp);
		otherSql
				.append(
						"where TO_INTEGER(a.ffloornumber)>=TO_INTEGER(b.ffloornumber) ")
				.append(sp);
		otherSql.append("and a.fparentid='").append(pk).append("')").append(sp);

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FHandlerid=FLocationid,FLocationid='").append(
				tempid).append("'").append(sp);
		sqlString.append(otherSql.toString());

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetailMax(ctx, billTableName, pk, isAudit);
		calFloorSeqForDetailAdjustBillMin(ctx, pk);

		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新，做为入库处理。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForBill(Context ctx, String billTableName,
			String pk) throws BOSException, EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update ").append(billTableName);
		sqlString.append(
				" set FFloorNumber =1+isnull((select count(a.fid) from ")
				.append(billTableName).append(" a ").append(sp);
		sqlString.append(" left join T_DB_Location b on a.").append(
				wareHouseFieldName).append("=b.fid ").append(sp);
		sqlString.append("where  a.").append(wareHouseFieldName).append("=")
				.append(billTableName).append(".").append(wareHouseFieldName)
				.append("   ").append(sp);
		sqlString.append("and a.FRowNumber=").append(billTableName).append(
				".FRowNumber   ").append(sp);
		sqlString.append("and a.FButtressNumber=").append(billTableName)
				.append(".FButtressNumber ").append(sp);
		sqlString.append("and ").append(billTableName)
				.append(".FSeq> a.FSeq  ").append(sp);
		sqlString.append("and  a.fparentid='").append(pk).append(
				"' and b.FisFloorControl=1),0) ").append(sp);
		sqlString.append("where fparentid='").append(pk).append("' ")
				.append(sp);
		sqlString.append(" and ").append(wareHouseFieldName).append(
				" in (select fid from T_DB_Location where FisFloorControl=1)")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新，做为入库处理。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForDetailAdjustBill(Context ctx,
			String billTableName, String pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update ").append(billTableName);
		sqlString.append(
				" set FFloorNumber =1+isnull((select count(a.fid) from ")
				.append(billTableName).append(" a ").append(sp);
		sqlString.append(" left join T_DB_Location b on a.").append(
				wareHouseFieldName).append("=b.fid ").append(sp);
		sqlString.append("where  a.").append(wareHouseFieldName).append("=")
				.append(billTableName).append(".").append(wareHouseFieldName)
				.append("   ").append(sp);
		sqlString.append("and a.FRowNumber=").append(billTableName).append(
				".FRowNumber   ").append(sp);
		sqlString.append("and a.FButtressNumber=").append(billTableName)
				.append(".FButtressNumber ").append(sp);
		sqlString.append("and ").append(billTableName)
				.append(".FSeq> a.FSeq  ").append(sp);
		sqlString.append("and  a.fparentid='").append(pk).append(
				"' and b.FisFloorControl=1),0) ").append(sp);
		sqlString.append("where fparentid='").append(pk).append("' ")
				.append(sp);
		sqlString.append(" and ").append(wareHouseFieldName).append(
				" in (select fid from T_DB_Location where FisFloorControl=1)")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新，做为入库处理。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForBillNew(Context ctx, String billTableName,
			String pk) throws BOSException, EASBizException {
		String wareHouseFieldName = "FNewLocationID";
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update ").append(billTableName);
		sqlString.append(
				" set FFloorNumber =1+isnull((select count(a.fid) from ")
				.append(billTableName).append(" a ").append(sp);
		sqlString.append(" left join T_DB_Location b on a.").append(
				wareHouseFieldName).append("=b.fid ").append(sp);
		sqlString.append("where  a.").append(wareHouseFieldName).append("=")
				.append(billTableName).append(".").append(wareHouseFieldName)
				.append("   ").append(sp);
		sqlString.append("and a.FNewRowNumber=").append(billTableName).append(
				".FNewRowNumber   ").append(sp);
		sqlString.append("and a.FNewButtressNum=").append(billTableName)
				.append(".FNewButtressNum ").append(sp);
		sqlString.append("and ").append(billTableName)
				.append(".FSeq> a.FSeq  ").append(sp);
		sqlString.append("and  a.fparentid='").append(pk).append(
				"' and b.FisFloorControl=1),0) ").append(sp);
		sqlString.append("where fparentid='").append(pk).append("' ")
				.append(sp);
		sqlString.append(" and ").append(wareHouseFieldName).append(
				" in (select fid from T_DB_Location where FisFloorControl=1)")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新，做为入库处理。用明细库的拆分序列号业务
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForDetailAdjustBill(Context ctx, String pk)
			throws BOSException, EASBizException {
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_DetailSABE set FFloorNumber =")
				.append(sp);
		sqlString.append("TO_INTEGER(FFloorNumber)").append(sp);
		sqlString.append(
				"+isnull((select count(a.fid) from T_ST_DetailSABE a  ")
				.append(sp);
		sqlString.append("left join T_DB_Location b on a.FLocationid=b.fid  ")
				.append(sp);
		sqlString
				.append("where  a.FLocationid=T_ST_DetailSABE.FLocationid    ")
				.append(sp);
		sqlString.append("and a.FRowNumber=T_ST_DetailSABE.FRowNumber    ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=T_ST_DetailSABE.FButtressNumber  ")
				.append(sp);
		sqlString.append("and T_ST_DetailSABE.FSeq> a.FSeq   ").append(sp);
		sqlString
				.append("and  a.fparentid='")
				.append(pk)
				.append(
						"' and a.FUnpackSequence  is not null and b.FisFloorControl=1),0)  ")
				.append(sp);
		sqlString.append("where fparentid='").append(pk).append("'  ").append(
				sp);
		sqlString
				.append(
						"and FLocationid in (select fid from T_DB_Location where FisFloorControl=1) and FUnpackSequence  is not null")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新，做为入库处理。用明细库的拆分序列号业务
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForDetailAdjustBillMin(Context ctx, String pk)
			throws BOSException, EASBizException {
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_DetailSABE set FFloorNumber =")
				.append(sp);
		sqlString
				.append(
						"isnull((select min(To_integer(FFloorNumber)) from T_ST_DetailSABE a  ")
				.append(sp);
		sqlString.append("left join T_DB_Location b on a.FLocationid=b.fid  ")
				.append(sp);
		sqlString
				.append("where  a.FLocationid=T_ST_DetailSABE.FLocationid    ")
				.append(sp);
		sqlString.append("and a.FRowNumber=T_ST_DetailSABE.FRowNumber    ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=T_ST_DetailSABE.FButtressNumber  ")
				.append(sp);
		sqlString
				.append("and  a.fparentid='")
				.append(pk)
				.append(
						"' and b.FisFloorControl=1 group by a.FLocationid,a.FRowNumber,a.FButtressNumber),0)  ")
				.append(sp);
		sqlString.append("where fparentid='").append(pk).append("'  ").append(
				sp);
		sqlString
				.append(
						"and FLocationid in (select fid from T_DB_Location where FisFloorControl=1) and FUnpackSequence  is not null")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新，做为入库处理。用明细库的拆分序列号业务
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForDetailAdjustBillMax(Context ctx, String pk)
			throws BOSException, EASBizException {
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_DetailSABE set FFloorNumber =")
				.append(sp);
		sqlString
				.append(
						"isnull((select max(To_integer(FFloorNumber)) from T_ST_DetailSABE a  ")
				.append(sp);
		sqlString.append("left join T_DB_Location b on a.FLocationid=b.fid  ")
				.append(sp);
		sqlString
				.append("where  a.FLocationid=T_ST_DetailSABE.FLocationid    ")
				.append(sp);
		sqlString.append("and a.FRowNumber=T_ST_DetailSABE.FRowNumber    ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=T_ST_DetailSABE.FButtressNumber  ")
				.append(sp);
		sqlString
				.append("and  a.fparentid='")
				.append(pk)
				.append(
						"' and b.FisFloorControl=1 group by a.FLocationid,a.FRowNumber,a.FButtressNumber),0)  ")
				.append(sp);
		sqlString.append("where fparentid='").append(pk).append("'  ").append(
				sp);
		sqlString
				.append(
						"and FLocationid in (select fid from T_DB_Location where FisFloorControl=1) and FUnpackSequence  is not null")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	public static void calFloorSeqForProduceDetail(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {
		calFloorSeqForProduceDetail(ctx, billTableName, null, pk, isAudit);
	}

	/**
	 * 
	 * 描述：审核时，对原有明细库的层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForProduceDetail(Context ctx,
			String billTableName, String locationFieldName, String pk,
			boolean isAudit) throws BOSException, EASBizException {

		// String wareHouseFieldName = null;
		// if (locationFieldName == null) {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);
		// } else {
		// wareHouseFieldName = locationFieldName;
		// }
		// wareHouseFieldName="FLocationID";
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);

		if (isAudit)
			sqlString.append(" set FFloorNumber =FFloorNumber+isnull(( ")
					.append(sp);
		else
			sqlString.append(" set FFloorNumber =FFloorNumber-isnull(( ")
					.append(sp);

		sqlString.append("select max(To_integer(FFloorNumber)) from ").append(
				billTableName).append(" a ").append(sp);

		sqlString.append("where   a.").append(wareHouseFieldName).append(
				"=T_ST_ProduceDetail.FLocationid  ").append(sp);
		sqlString.append("and a.FRowNumber=T_ST_ProduceDetail.FRowNumber ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=T_ST_ProduceDetail.FButtressNumber ")
				.append(sp);
		sqlString.append("and a.").append(wareHouseFieldName).append(
				" in   (select ").append(wareHouseFieldName).append(" from ")
				.append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1 and fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append("and a.FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and a.FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("') ").append(sp);
		sqlString.append("and a.fparentid='").append(pk).append(
				"'  group by a.").append(wareHouseFieldName).append(
				",a.FRowNumber,a.FButtressNumber").append(sp);
		sqlString.append("),0) ").append(sp);
		sqlString
				.append(
						"where FFloorNumber!='0' and FStatus!=1 and FLocationid in (select ")
				.append(wareHouseFieldName).append(" from ").append(
						billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对原有明细库的层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按顺序进行更新
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForProduceDetailSeq(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {

		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		StringBuffer sqlString = new StringBuffer();

		sqlString
				.append(
						"update t_st_producedetail set FFloorNumber =1+isnull((select count(a.fid) from t_st_producedetail a  ")
				.append(sp);
		sqlString
				.append(" left join T_DB_Location b on a.FLocationid=b.fid   ")
				.append(sp);
		sqlString
				.append(
						"where  fstatus=2 and a.FLocationid=t_st_producedetail.FLocationid     ")
				.append(sp);
		sqlString.append("and a.FRowNumber=t_st_producedetail.FRowNumber     ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=t_st_producedetail.FButtressNumber   ")
				.append(sp);
		sqlString.append(
				"and t_st_producedetail.FFloorNumber> a.FFloorNumber   ")
				.append(sp);
		sqlString.append(" and b.FisFloorControl=1),0)   ").append(sp);
		sqlString
				.append(
						"where  fstatus=2 and FLocationid in (select fid from T_DB_Location where FisFloorControl=1)  ")
				.append(sp);
		sqlString.append("and  FRowNumber in (select FRowNumber from ").append(
				billTableName).append(" where fparentid='").append(pk).append(
				"') ").append(sp);
		sqlString.append(
				"and  FButtressNumber in (select FButtressNumber from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("') ").append(sp);
		sqlString.append("and  FLocationid in (select ").append(
				wareHouseFieldName).append(" from ").append(billTableName)
				.append(" where fparentid='").append(pk).append("') ").append(
						sp);

		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对原有明细库的层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForProduceDetailMin(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {

		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);

		if (isAudit)
			sqlString.append(" set FFloorNumber =FFloorNumber+isnull(( ")
					.append(sp);
		else
			sqlString.append(" set FFloorNumber =FFloorNumber-isnull(( ")
					.append(sp);

		sqlString.append("select min(To_integer(FFloorNumber)) from ").append(
				billTableName).append(" a ").append(sp);

		sqlString.append("where   a.").append(wareHouseFieldName).append(
				"=T_ST_ProduceDetail.FLocationid  ").append(sp);
		sqlString.append("and a.FRowNumber=T_ST_ProduceDetail.FRowNumber ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=T_ST_ProduceDetail.FButtressNumber ")
				.append(sp);
		sqlString.append("and a.").append(wareHouseFieldName).append(
				" in   (select ").append(wareHouseFieldName).append(" from ")
				.append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1 and fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append("and a.FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and a.FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("') ").append(sp);
		sqlString.append("and a.fparentid='").append(pk).append(
				"'  group by a.").append(wareHouseFieldName).append(
				",a.FRowNumber,a.FButtressNumber").append(sp);
		sqlString.append("),0) ").append(sp);
		sqlString
				.append(
						"where FFloorNumber!='0' and FStatus!=1 and FLocationid in (select ")
				.append(wareHouseFieldName).append(" from ").append(
						billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对原有明细库的层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForProduceDetailMax(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {

		String wareHouseFieldName = "FLocationid";
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);

		if (isAudit)
			sqlString.append(" set FFloorNumber =FFloorNumber+isnull(( ")
					.append(sp);
		else
			sqlString.append(" set FFloorNumber =FFloorNumber-isnull(( ")
					.append(sp);

		sqlString
				.append(
						"select max(To_integer(FFloorNumber))-min(To_integer(FFloorNumber)) from ")
				.append(billTableName).append(" a ").append(sp);

		sqlString.append("where   a.").append(wareHouseFieldName).append(
				"=T_ST_ProduceDetail.FLocationid  ").append(sp);
		sqlString.append("and a.FRowNumber=T_ST_ProduceDetail.FRowNumber ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=T_ST_ProduceDetail.FButtressNumber ")
				.append(sp);
		sqlString.append("and a.").append(wareHouseFieldName).append(
				" in   (select ").append(wareHouseFieldName).append(" from ")
				.append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1 and fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append("and a.FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and a.FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("') ").append(sp);
		sqlString.append("and a.fparentid='").append(pk).append(
				"'  group by a.").append(wareHouseFieldName).append(
				",a.FRowNumber,a.FButtressNumber").append(sp);
		sqlString.append("),0) ").append(sp);
		sqlString
				.append(
						"where FFloorNumber!='0' and FStatus!=1 and FLocationid in (select ")
				.append(wareHouseFieldName).append(" from ").append(
						billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对原有明细库的层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForProduceDetailMinAdjust(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {

		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);

		if (isAudit)
			sqlString.append(
					" set FFloorNumber =To_integer(FFloorNumber)+isnull(( ")
					.append(sp);
		else
			sqlString.append(
					" set FFloorNumber =To_integer(FFloorNumber)-isnull(( ")
					.append(sp);

		sqlString
				.append(
						"select max(To_integer(FFloorNumber))-min(To_integer(FFloorNumber)) from ")
				.append(billTableName).append(" a ").append(sp);

		sqlString.append("where   a.FUnpackSequence  is not null and a.")
				.append(wareHouseFieldName).append(
						"=T_ST_ProduceDetail.FLocationid  ").append(sp);
		sqlString.append("and a.FRowNumber=T_ST_ProduceDetail.FRowNumber ")
				.append(sp);
		sqlString.append(
				"and a.FButtressNumber=T_ST_ProduceDetail.FButtressNumber ")
				.append(sp);
		sqlString.append("and a.").append(wareHouseFieldName).append(
				" in   (select ").append(wareHouseFieldName).append(" from ")
				.append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1 and fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append("and a.FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and a.FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("') ").append(sp);
		sqlString.append("and a.fparentid='").append(pk).append(
				"'  group by a.").append(wareHouseFieldName).append(
				",a.FRowNumber,a.FButtressNumber").append(sp);
		sqlString.append("),0) ").append(sp);
		sqlString
				.append(
						"where FFloorNumber!='0' and FStatus!=1 and FLocationid in (select ")
				.append(wareHouseFieldName).append(" from ").append(
						billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and FRowNumber  in   (select FRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and FButtressNumber  in   (select FButtressNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append("and FSequence not  in   (select FSequence  from ")
				.append(billTableName).append(
						" where FUnpackSequence  is not null and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString
				.append(
						"and FFloorNumber >= (select min(FFloorNumber)  from T_ST_DetailSABE where fparentid='")
				.append(pk).append("')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：审核时，对原有明细库的层数进行层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForProduceDetailNew(Context ctx,
			String billTableName, String pk, boolean isAudit)
			throws BOSException, EASBizException {

		String wareHouseFieldName = "FNewLocationID";
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);

		if (isAudit)
			sqlString.append(" set FFloorNumber =FFloorNumber+isnull(( ")
					.append(sp);
		else
			sqlString.append(" set FFloorNumber =FFloorNumber-isnull(( ")
					.append(sp);

		sqlString.append("select max(To_integer(FFloorNumber)) from ").append(
				billTableName).append(" a ").append(sp);

		sqlString.append("where   a.").append(wareHouseFieldName).append(
				"=T_ST_ProduceDetail.FLocationid  ").append(sp);
		sqlString.append("and a.FNewRowNumber=T_ST_ProduceDetail.FRowNumber ")
				.append(sp);
		sqlString.append(
				"and a.FNewButtressNum=T_ST_ProduceDetail.FButtressNumber ")
				.append(sp);
		sqlString.append("and a.").append(wareHouseFieldName).append(
				" in   (select ").append(wareHouseFieldName).append(" from ")
				.append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1 and fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and a.FNewRowNumber  in   (select FNewRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and a.FNewButtressNum in   (select FNewButtressNum  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("') ").append(sp);
		sqlString.append("and a.fparentid='").append(pk).append(
				"'  group by a.").append(wareHouseFieldName).append(
				",a.FNewRowNumber,a.FNewButtressNum").append(sp);
		sqlString.append("),0) ").append(sp);
		sqlString
				.append(
						"where  FFloorNumber!='0' and FStatus!=1 and FLocationid in (select ")
				.append(wareHouseFieldName).append(" from ").append(
						billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and FRowNumber  in   (select FNewRowNumber  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);
		sqlString.append(
				"and FButtressNumber  in   (select FNewButtressNum  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：反审核时，校验现有单据与明细库的层数是否相同，如果不相同则抛出异常不允许反审核，相同则允许反审核,该方法适用于存在批处理的方法中。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForBillUnAuditCheck(Context ctx,
			String billTableName, IObjectPK[] pkList, Vector errPks)
			throws BOSException, EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		int pkNum = pkList.length;/* 需要反审核的单据数 */
		IRowSet rowSet = null;
		StringBuffer numString = new StringBuffer();
		StringBuffer sqlString = new StringBuffer();
		for (int bigI = 0; bigI < pkNum; bigI++) {// 对pk列表进行循环
			sqlString.setLength(0);
			String pk = pkList[bigI].toString();
			sqlString
					.append(
							"select Distinct fsourcebillnumber from T_ST_ProduceDetail a ")
					.append(sp);
			sqlString.append("left join ").append(billTableName).append(
					" b on a.fsourcebillentryid=b.fid ").append(sp);
			sqlString.append("left join T_DB_Location c on b.").append(
					wareHouseFieldName).append("=c.fid").append(sp);
			sqlString
					.append("where b.fparentid='")
					.append(pk)
					.append(
							"' and a.FFloorNumber!=b.FFloorNumber  and c.FisFloorControl=1")
					.append(sp);

			// 检查反审核条件
			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			try {
				if (rowSet.next()) {
					numString.append(rowSet.getString("fsourcebillnumber"))
							.append(",");
					pkList[bigI] = new ObjectUuidPK(BOSUuid.create("F96ED1F3"));
					errPks.add(pk);
					continue;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}

	}

	/**
	 * 
	 * 描述：反审核时，校验现有单据与明细库的层数是否相同，如果不相同则抛出异常不允许反审核，相同则允许反审核,该方法适用于存在批处理的方法中。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForBillUnAuditCheckForDetailAdjust(
			Context ctx, String billTableName, IObjectPK[] pkList, Vector errPks)
			throws BOSException, EASBizException {
		String wareHouseFieldName = "FLocationid";
		int pkNum = pkList.length;/* 需要反审核的单据数 */
		IRowSet rowSet = null;
		StringBuffer numString = new StringBuffer();
		StringBuffer sqlString = new StringBuffer();
		for (int bigI = 0; bigI < pkNum; bigI++) {// 对pk列表进行循环
			sqlString.setLength(0);
			String pk = pkList[bigI].toString();
			sqlString
					.append(
							"select Distinct fsourcebillnumber from T_ST_ProduceDetail a ")
					.append(sp);
			sqlString.append("left join ").append(billTableName).append(
					" b on a.FSequence=b.FSequence ").append(sp);
			sqlString.append("left join T_DB_Location c on b.").append(
					wareHouseFieldName).append("=c.fid").append(sp);
			sqlString
					.append(
							"left join T_ST_DetailStorageAdjustBill d on d.fid=b.fparentid ")
					.append(sp);
			sqlString
					.append("where b.fparentid='")
					.append(pk)
					.append(
							"' and a.FFloorNumber!=b.FFloorNumber  and c.FisFloorControl=1 and (d.FBillBizType not in (1,4,5))")
					.append(sp);

			// 检查反审核条件
			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			try {
				if (rowSet.next()) {
					numString.append(rowSet.getString("fsourcebillnumber"))
							.append(",");
					pkList[bigI] = new ObjectUuidPK(BOSUuid.create("F96ED1F3"));
					errPks.add(pk);
					continue;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}

			sqlString.setLength(0);

			sqlString
					.append(
							"select Distinct fsourcebillnumber from T_ST_ProduceDetail a ")
					.append(sp);
			sqlString.append("left join ").append(billTableName).append(
					" b on a.FSequence=b.FUnpackSequence ").append(sp);
			sqlString.append("left join T_DB_Location c on b.").append(
					wareHouseFieldName).append("=c.fid").append(sp);
			sqlString
					.append(
							"left join T_ST_DetailStorageAdjustBill d on d.fid=b.fparentid ")
					.append(sp);
			sqlString
					.append("where b.fparentid='")
					.append(pk)
					.append(
							"' and a.FFloorNumber!=b.FFloorNumber  and c.FisFloorControl=1 and (d.FBillBizType=5)")
					.append(sp);

			// 检查反审核条件
			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			try {
				if (rowSet.next()) {
					numString.append(rowSet.getString("fsourcebillnumber"))
							.append(",");
					pkList[bigI] = new ObjectUuidPK(BOSUuid.create("F96ED1F3"));
					errPks.add(pk);
					continue;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}

		}

	}

	/**
	 * 
	 * 描述：反审核时，校验现有单据与明细库的层数是否相同，如果不相同则抛出异常不允许反审核，相同则允许反审核，该方法直接抛出异常。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void calFloorSeqForBillUnAuditCheck(Context ctx,
			String billTableName, IObjectPK[] pkList) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		int pkNum = pkList.length;/* 需要反审核的单据数 */
		IRowSet rowSet = null;
		StringBuffer numString = new StringBuffer();
		StringBuffer sqlString = new StringBuffer();
		for (int bigI = 0; bigI < pkNum; bigI++) {// 对pk列表进行循环
			sqlString.setLength(0);
			String pk = pkList[bigI].toString();
			sqlString
					.append(
							"select Distinct fsourcebillnumber from T_ST_ProduceDetail a ")
					.append(sp);
			sqlString.append("left join ").append(billTableName).append(
					" b on a.fsourcebillentryid=b.fid ").append(sp);
			sqlString.append("left join T_DB_Location c on b.").append(
					wareHouseFieldName).append("=c.fid").append(sp);
			sqlString
					.append("where b.fparentid='")
					.append(pk)
					.append(
							"' and a.FFloorNumber!=b.FFloorNumber  and c.FisFloorControl=1")
					.append(sp);

			// 检查反审核条件
			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			try {
				if (rowSet.next()) {
					numString.append(rowSet.getString("fsourcebillnumber"))
							.append(",");
					continue;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}

		if (numString.length() > 0) {
			numString.setLength(numString.length() - 1);
			throw new STBillException(STBillException.UNAUDIT_HAS_BUSINESS,
					new Object[] { numString.toString() });
		}
	}

	/**
	 * 
	 * 描述：查找分录中相同行垛的最小层号的单品，如果最小层号不为1，则提示用户“只能从首层依次出库，否则必须先进行倒垛处理！”
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void checkFloorNumberMinIsNotOneForSubmit(Context ctx,
			String billTableName, IObjectPK pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString.setLength(0);

		sqlString.append("select min(To_integer(FFloorNumber)) from ").append(
				billTableName);
		sqlString.append(" t1 left join t_db_location t2 on t1.").append(
				wareHouseFieldName).append("=t2.fid").append(sp);
		sqlString.append("where t2.FisFloorControl=1  and fparentid='").append(
				pk).append("'  group by ").append(wareHouseFieldName).append(
				",FRowNumber,FButtressNumber").append(sp);
		sqlString.append(" having min(To_integer(FFloorNumber))!=1; ");
		// 检查条件
		rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
		try {
			if (rowSet.next()) {
				throw new STBillException(
						STBillException.FLOORNUMBERMINISNOTONEFORSUBMIT);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

	}

	/**
	 * 
	 * 描述：检查分录是否行垛号为空，如果同时为空，则抛出异常
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void checkRowNumberButtressNumberIsNotNullForSubmit(
			Context ctx, String billTableName, IObjectPK pk)
			throws BOSException, EASBizException {

		String wareHouseFieldName = getWareHouseFieldName(billTableName);
		// wareHouseFieldName="FLocationid";
		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString.setLength(0);
		sqlString.append("select  B.FNAME_L2 AS FNAME, B.FID AS FID from ")
				.append(billTableName).append(" a").append(sp);
		sqlString.append("left join t_db_location b on a.").append(
				wareHouseFieldName).append("=b.fid ").append(sp);
		sqlString.append("where a.fparentid='").append(pk).append(
				"' and b.FisFloorControl=1").append(sp);
		sqlString.append("and (FRowNumber is null or FButtressNumber is null)")
				.append(sp);
		// 检查条件
		rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
		try {
			if (rowSet.next()) {
				throw new STBillException(
						STBillException.CHECK_ROWNUMBERBUTTRESSNUMBERISNOTNULL,
						new Object[] { rowSet.getString("FNAME"),
								rowSet.getString("FID") });
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

	}

	/**
	 * 
	 * 描述：检查分录是否 "新" 行垛号为空，如果同时为空，则抛出异常
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author miller_xiao 创建时间：2009-01-15
	 */
	public static void checkNewRowNumberButtressNumberIsNotNullForSubmit(
			Context ctx, String billTableName, IObjectPK pk)
			throws BOSException, EASBizException {

		String wareHouseFieldName = "FNewLocationid";
		// wareHouseFieldName="FLocationid";
		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString.setLength(0);
		sqlString.append("select  B.FNAME_L2 AS FNAME, B.FID AS FID from ")
				.append(billTableName).append(" a").append(sp);
		sqlString.append("left join t_db_location b on a.").append(
				wareHouseFieldName).append("=b.fid ").append(sp);
		sqlString.append("where a.fparentid='").append(pk).append(
				"' and b.FisFloorControl=1").append(sp);
		sqlString.append(
				"and (A.FNewRowNumber is null or A.FNewButtressNum is null)")
				.append(sp);
		// 检查条件
		rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
		try {
			if (rowSet.next()) {
				throw new STBillException(
						STBillException.CHECK_NEW_ROWNUMBERBUTTRESSNUMBERISNOTNULL,
						new Object[] { rowSet.getString("FNAME"),
								rowSet.getString("FID") });
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
	}

	/**
	 * 
	 * 描述：检查是否存在相同层号
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author miller_xiao 创建时间：2009-01-22
	 */
	public static void checkIsExistSameFloor(Context ctx, String billTableName,
			IObjectPK pk) throws BOSException, EASBizException {
		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString.setLength(0);

		sqlString.append("SELECT b.fSeq AS seq FROM T_ST_ProduceDetail a ");
		sqlString.append("LEFT JOIN ").append(billTableName).append(" b ");
		sqlString
				.append("ON a.FLocationId = b.FLocationId AND a.FRowNumber = b.FRowNumber AND a.FButtressNumber = b.FButtressNumber AND a.FFloorNumber = b.FFloorNumber ");
		sqlString
				.append("WHERE b.FParentid = ? AND a.FSequence != b.FSequence");

		ArrayList param = new ArrayList();
		param.add(pk.toString());

		// 检查条件
		rowSet = DbUtil
				.executeQuery(ctx, sqlString.toString(), param.toArray());
		try {
			if (rowSet.next()) {
				throw new STBillException(STBillException.EXIST_SAME_FLOOR,
						new Object[] { rowSet.getString("seq") });
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
	}

	/**
	 * 
	 * 描述：查找分录中相同行垛的最小层号的单品，如果最小层号不为1，则提示用户“只能从首层依次出库，否则必须先进行倒垛处理！”
	 * 该方法用于明细库库位调整单的提交
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-20
	 */
	public static void checkFloorNumberMinIsNotOneForAdjustSubmit(Context ctx,
			String billTableName, IObjectPK pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = "FLocationID";
		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString.setLength(0);

		sqlString.append("select min(To_integer(FFloorNumber)) from ").append(
				billTableName);
		sqlString.append(
				" t1 left join t_db_location t2 on t1.FLocationid=t2.fid")
				.append(sp);
		sqlString.append("where  t2.FisFloorControl=1 and fparentid='").append(
				pk).append("'  group by ").append(wareHouseFieldName).append(
				",FRowNumber,FButtressNumber").append(sp);
		sqlString.append(" having min(To_integer(FFloorNumber))!=1; ");
		// 检查条件
		rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
		try {
			if (rowSet.next()) {
				throw new STBillException(
						STBillException.FLOORNUMBERMINISNOTONEFORSUBMIT);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

		sqlString.setLength(0);
		sqlString.append("select fid from ").append(billTableName).append(sp);
		sqlString.append("where fparentid='").append(pk);
		sqlString
				.append(
						"' and FLocationID=FNewLocationID and FRowNumber=FNewRowNumber and FButtressNumber=FNewButtressNum ")
				.append(sp);
		// 检查条件
		rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
		try {
			if (rowSet.next()) {
				throw new STBillException(STBillException.CHECK_NEWROWISEQUAL);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

	}

	/**
	 * 
	 * 描述：一张单上只能有一条相同启用了层号控制的仓库下的单品，对于不同启用了层号控制的仓库允许有多条单品进行拆号。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-24
	 */
	public static void checkLocationEqualIsNotOneForAdjustSubmit(Context ctx,
			String billTableName, IObjectPK pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = "FLocationID";
		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString.setLength(0);
		sqlString.append("select count(*) from (");
		sqlString
				.append("select  fsequence,flocationid,count(*) as count from T_ST_DetailSABE ");
		sqlString.append("where (fparentid='").append(pk).append(
				"' and FUnpackSequence is not null) ");
		sqlString
				.append("group by fsequence,flocationid) as b group by flocationid ");
		sqlString.append("having count(*)>1");
		// 检查条件
		rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
		try {
			if (rowSet.next()) {
				throw new STBillException(
						STBillException.CHECK_ISEQUALLOCALTION_SUBMIT);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
	}

	public static void checkFloorNumberSeqForDetailAdjustSubmit(Context ctx,
			String billTableName, IObjectPK pk) throws BOSException,
			EASBizException {
		checkFloorNumberSeqForSubmit(ctx, billTableName, pk);

	}

	/**
	 * 
	 * 描述：校验n条单品层号不能断号
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void checkFloorNumberSeqForSubmit(Context ctx,
			String billTableName, IObjectPK pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		if (!billTableName.equals("T_IV_PurIWBDE"))
			checkFloorNumberMinIsNotOneForSubmit(ctx, billTableName, pk);

		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString
				.append("create table calFloorTemp( fseq int,FLocationid varchar(80),FRowNumber varchar(80)");
		sqlString
				.append(",FButtressNumber varchar(80),FFloorNumber varchar(80),FnewNumber VARChar(80))");
		String tempTableName = null;
		TempTablePool pool = TempTablePool.getInstance(ctx);
		try {
			tempTableName = pool.createTempTable(sqlString.toString());

			sqlString.setLength(0);
			sqlString.append(" select fid ").append(sp);
			sqlString.append("from ").append(billTableName).append(
					" where fparentid='").append(pk).append("' ").append(sp);
			sqlString
					.append("and ")
					.append(wareHouseFieldName)
					.append(
							" in (select fid from T_DB_Location where FisFloorControl=1) ")
					.append(sp);
			sqlString.append(" order by ").append(wareHouseFieldName).append(
					",FRowNumber,FButtressNumber,TO_Integer(FFloorNumber);");

			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			sqlString.setLength(0);
			int i = 0;
			while (rowSet.next()) {
				i = i + 1;
				sqlString.append("insert into  ").append(tempTableName).append(
						" select ").append(i).append(", ").append(sp);
				sqlString.append(wareHouseFieldName).append(
						",FRowNumber,FButtressNumber,FFloorNumber,'0' ")
						.append(sp);
				sqlString.append("from ").append(billTableName).append(
						" where fid='").append(rowSet.getString("fid")).append(
						"';").append(sp);
				;

			}

			sqlString.append("update ").append(tempTableName).append(" set ")
					.append(sp);
			sqlString.append("FnewNumber =1+isnull(( ").append(sp);
			sqlString.append("select count(fseq) from ").append(tempTableName)
					.append(" a where  a.FLocationid=").append(tempTableName)
					.append(".FLocationid ").append(sp);
			sqlString.append("and a.FRowNumber=").append(tempTableName).append(
					".FRowNumber   and a.FButtressNumber=").append(
					tempTableName).append(".FButtressNumber ").append(sp);
			sqlString.append("and ").append(tempTableName).append(
					".FSeq> a.FSeq ),0); ").append(sp);
			DbUtil.execute(ctx, sqlString.toString());
			sqlString.setLength(0);
			sqlString.append("select fseq from ").append(tempTableName).append(
					" where ffloornumber!=fnewnumber");
			// 检查条件
			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			// TODO 这个异常有待需求回来判断，要显示什么提示内容
			if (rowSet.next()) {
				throw new STBillException(STBillException.FLOORNUMBERNEEDSEQ);
			}
		} catch (EASBizException e) {
			throw new STBillException(STBillException.FLOORNUMBERNEEDSEQ);
		} catch (Exception e) {
			throw new BOSException(e);
		} finally {
			// 销毁临时表.
			try {
				if (tempTableName != null)
					pool.releaseTable(tempTableName);

			} catch (Exception e) {
				throw new BOSException(e);
			}

		}

	}

	private static String getWareHouseFieldName(String billTableName) {

		String wareHouseFieldName = "FLocationid";
		if (billTableName.equals("T_ST_DetailSABE")) {
			wareHouseFieldName = "FLocationid";
		} else if (billTableName.equals("T_IV_TransferOBDE") // 调拨订单
				|| billTableName.equals("T_IV_SaleIssueBillDetailEntry") // 销售出库
				|| billTableName.equals("T_IV_MoveIssueBillDetailEntry") // 调拨出库
				|| billTableName.equals("T_IV_StockTBDE") // 库存调拨单
		) {
			wareHouseFieldName = "FIssueLocationID";
		} else if (billTableName.equals("T_IV_PurIWBDE") // 采购入库
				|| billTableName.equals("T_IV_MoveIWBDE") // 调拨入库
		) {
			wareHouseFieldName = "FReceiptLocationID";
		}
		return wareHouseFieldName;
	}

	/**
	 * 
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,用于审核
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZero(Context ctx,
			String billTableName, String pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(
				" set FFloorNumber =0,FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("'").append(sp);
		sqlString.append(" where FLocationid in (select ").append(
				wareHouseFieldName).append(" from ").append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and fid  in   (select FSequenceID  from ").append(
				billTableName).append(" where fparentid='").append(pk).append(
				"')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetail(ctx, billTableName, pk, false);
		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,用于审核
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZeroBySequenceNumber(
			Context ctx, String billTableName, String pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(
				" set FFloorNumber =0,FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("'").append(sp);
		sqlString.append(" where FLocationid in (select ").append(
				wareHouseFieldName).append(" from ").append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and FSEQUENCE  in   (select FSEQUENCE  from ")
				.append(billTableName).append(" where fparentid='").append(pk)
				.append("')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetail(ctx, billTableName, pk, false);
		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/*
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,用于装运清单审核
	 * 
	 * @param ctx
	 * 
	 * @param pk 单据id
	 * 
	 * @param billTableName 明细库分录单据表名
	 * 
	 * @throws BOSException
	 * 
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZeroBySequenceNumberForLading(
			Context ctx, String billTableName, String pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(
				" set FFloorNumber =0,FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("'").append(sp);
		sqlString.append(" where FLocationid in (select ").append(
				wareHouseFieldName).append(" from ").append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and FID  in   (select FSEQUENCEID  from ").append(
				billTableName).append(" where fparentid='").append(pk).append(
				"')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetail(ctx, billTableName, pk, false);
		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,用于审核
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZeroNew(Context ctx,
			String billTableName, String pk) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(
				" set FFloorNumber =0,FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("'").append(sp);
		sqlString.append(" where FLocationid in (select ").append(
				wareHouseFieldName).append(" from ").append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and fid  in   (select FSequence  from ").append(
				billTableName).append(" where fparentid='").append(pk).append(
				"')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetail(ctx, billTableName, pk, false);
		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	public static void calFloorSeqForProduceDetailSetZeroNewForUnAudit(
			Context ctx, String billTableName, String pk) throws BOSException,
			EASBizException {
		calFloorSeqForProduceDetailSetZeroNewForUnAudit(ctx, billTableName,
				null, pk);
	}

	public static void calFloorSeqForProduceDetailSetZeroNewForUnAudit(
			Context ctx, String billTableName, String locationFieldName,
			String pk) throws BOSException, EASBizException {
		String wareHouseFieldName = null;
		if (locationFieldName == null) {
			wareHouseFieldName = getWareHouseFieldName(billTableName);
		} else {
			wareHouseFieldName = locationFieldName;
		}

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(
				" set FFloorNumber =0,FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("'").append(sp);
		sqlString.append(" where FLocationid in (select ").append(
				wareHouseFieldName).append(" from ").append(billTableName);
		sqlString.append("  a  left join T_DB_Location b on a.").append(
				wareHouseFieldName).append(
				"=b.fid where  b.FisFloorControl=1  and fparentid='")
				.append(pk).append("')").append(sp);
		sqlString.append("and fid  in   (select FSequence  from ").append(
				billTableName).append(" where fparentid='").append(pk).append(
				"')").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetail(ctx, billTableName, locationFieldName, pk,
				true);
		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,加这一层是为了处理相同ID的值。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZero(Context ctx,
			String billTableName, HashMap validPks) throws BOSException,
			EASBizException {

		if (billTableName.equals("T_ST_LadingEntry")) {
			// 如果为装运清单需判断该单是否允许断号出库，如果允许则要用另外的计算层号的层号进行处理
			IRowSet rowSet = null;
			Set set = validPks.entrySet();
			Iterator it = set.iterator();
			StringBuffer sqlString = new StringBuffer();
			// 现在这里只处理单组织参数的，在后续如果序时薄多组织的问题，则需重新调整。

			while (it.hasNext()) {
				Map.Entry temp = (Map.Entry) it.next();
				sqlString
						.append("select FStorageOrgUnitID from T_ST_Lading where fid='");
				sqlString.append(temp.getValue()).append("';");
				break;
			}
			if (sqlString.length() > 0) {
				rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
				try {
					if (rowSet.next()) {
						if (isFloor001(ctx, rowSet
								.getString("FStorageOrgUnitID"))) {
							set = validPks.entrySet();
							it = set.iterator();
							while (it.hasNext()) {
								Map.Entry temp = (Map.Entry) it.next();
								// 审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新。 xmcy
								// 2008-05-21
								STFloorCalServerUtils
										.calFloorSeqForProduceDetailSeq(ctx,
												"T_ST_LadingEntry", temp
														.getValue().toString(),
												true);
							}
						} else {
							set = validPks.entrySet();
							it = set.iterator();
							while (it.hasNext()) {
								Map.Entry temp = (Map.Entry) it.next();
								// 审核时，对层号进行自动计算，根据仓库，行号，垛号这三个，按录入顺序进行更新。 xmcy
								// 2008-05-21
								calFloorSeqForProduceDetailSetZeroBySequenceNumberForLading(
										ctx, "T_ST_LadingEntry", temp
												.getValue().toString());
							}
						}
						return;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		IRowSet rowSet = null;
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("create table detailzero(fid varchar(44))");
		String tempTableName = null;
		TempTablePool pool = TempTablePool.getInstance(ctx);
		try {
			tempTableName = pool.createTempTable(sqlString.toString());
			// 去掉重复的KEY
			sqlString.setLength(0);
			Set set = validPks.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry temp = (Map.Entry) it.next();
				sqlString.append("insert into ").append(tempTableName).append(
						" select '").append(temp.getValue()).append("';")
						.append(sp);
				// System.out.print(temp.getKey()+":"+temp.getValue()+" ");
			}
			// 存在没有明细分录的情况下。
			if (sqlString.length() > 0)
				DbUtil.execute(ctx, sqlString.toString());

			sqlString.setLength(0);
			sqlString.append(" select Distinct fid from ")
					.append(tempTableName);
			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			while (rowSet.next()) {
				calFloorSeqForProduceDetailSetZero(ctx, billTableName, rowSet
						.getString("fid"));
			}
		} catch (Exception e) {
			throw new BOSException(e);
		} finally {
			// 销毁临时表.
			try {
				if (tempTableName != null)
					pool.releaseTable(tempTableName);

			} catch (Exception e) {
				throw new BOSException(e);
			}

		}

	}

	/**
	 * 
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,用于反审核
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZeroForUnAudit(
			Context ctx, String billTableName, String pk) throws BOSException,
			EASBizException {

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail set ffloornumber=").append(
				sp);
		sqlString
				.append("(select Distinct ffloornumber from ")
				.append(billTableName)
				.append(
						" a  where a.FSequenceID=T_ST_ProduceDetail.fid and FSequenceID ")
				.append(sp);
		sqlString.append("in (select FSequenceID from ").append(billTableName)
				.append("  where fparentid='").append(pk).append(
						"') and a.fparentid='").append(pk).append("') ")
				.append(sp);
		sqlString.append(",FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("' ").append(sp);
		sqlString.append(" where fid in (select FSequenceID from ").append(
				billTableName).append("  where fparentid='").append(pk).append(
				"' ) ").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetail(ctx, billTableName, pk, true);

		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,用于反审核
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZeroForUnAuditBySequenceNumber(
			Context ctx, String billTableName, String pk) throws BOSException,
			EASBizException {

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail set ffloornumber=").append(
				sp);
		sqlString
				.append("(select Distinct ffloornumber from ")
				.append(billTableName)
				.append(
						" a  where a.FSequence=T_ST_ProduceDetail.FSEQUENCE and FSEQUENCE ")
				.append(sp);
		sqlString.append("in (select FSEQUENCE from ").append(billTableName)
				.append("  where fparentid='").append(pk).append(
						"') and a.fparentid='").append(pk).append("') ")
				.append(sp);
		sqlString.append(",FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("' ").append(sp);
		sqlString.append(" where FSEQUENCE in (select FSEQUENCE from ").append(
				billTableName).append("  where fparentid='").append(pk).append(
				"' ) ").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetail(ctx, billTableName, pk, true);

		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：分别将关联的单品层号置为0，查找此行垛的其它单品，将其层号减n,用于反审核
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZeroForUnAuditNew(
			Context ctx, String billTableName, String pk) throws BOSException,
			EASBizException {

		String tempid = new ObjectUuidPK(BOSUuid.create("F96ED1F3")).toString();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("update T_ST_ProduceDetail set ffloornumber=").append(
				sp);
		sqlString.append("(select Distinct ffloornumber from ").append(
				billTableName).append(
				" a  where a.FSequence=T_ST_ProduceDetail.fid and FSequence ")
				.append(sp);
		sqlString.append("in (select FSequence from ").append(billTableName)
				.append("  where fparentid='").append(pk).append(
						"') and a.fparentid='").append(pk).append("') ")
				.append(sp);
		sqlString.append(",FHandlerid=FLocationid,FLocationid='")
				.append(tempid).append("' ").append(sp);
		sqlString.append(" where fid in (select FSequence from ").append(
				billTableName).append("  where fparentid='").append(pk).append(
				"' ) ").append(sp);

		DbUtil.execute(ctx, sqlString.toString());

		calFloorSeqForProduceDetailNew(ctx, billTableName, pk, true);

		sqlString.setLength(0);
		sqlString.append("update T_ST_ProduceDetail ").append(sp);
		sqlString.append(" set FLocationid=FHandlerid,FHandlerid=''")
				.append(sp);
		sqlString.append(" where FLocationid='").append(tempid).append("'")
				.append(sp);
		DbUtil.execute(ctx, sqlString.toString());
	}

	/**
	 * 
	 * 描述：用于明细库库位调整单的库位调整审核操作，对于库位调整＝旧库位的出库+新库位的入库。
	 * 
	 * @param ctx
	 * @param pk
	 *            单据id
	 * @param billTableName
	 *            明细库分录单据表名
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static void calFloorSeqForProduceDetailForDetailStorageAdjustBill(
			Context ctx, String pk) throws BOSException, EASBizException {
		// 处理层次设为0 xmcy 2008-05-21

		calFloorSeqForProduceDetailSetZeroNew(ctx, "T_ST_DetailSABE", pk);

		// 审核时，对层号进行自动计算，根据库位，行号，垛号这三个，按录入顺序进行更新。 xmcy 2008-05-21
		calFloorSeqForBillNew(ctx, "T_ST_DetailSABE", pk);
		calFloorSeqForProduceDetailNew(ctx, "T_ST_DetailSABE", pk, true);
	}

	public static void calFloorSeqForProduceDetailForDetailStorageAdjustBill_Unaudit(
			Context ctx, String pk) throws BOSException, EASBizException {
		// 处理层次设为0 xmcy 2008-05-21
		calFloorSeqForProduceDetailSetZeroForUnAuditBySequenceNumber(ctx,
				"T_ST_DetailSABE", pk);
		// 审核时，对层号进行自动计算，根据库位，行号，垛号这三个，按录入顺序进行更新。 xmcy 2008-05-21
		calFloorSeqForBill(ctx, "T_ST_DetailSABE", pk);
		calFloorSeqForProduceDetailNew(ctx, "T_ST_DetailSABE", pk, false);
	}

	/**
	 * 
	 * 描述：装运清单中单品可从中间层号出库”，如果启用此参数，则系统中的装运清单可从中间层号出库，
	 * 且自动调整该行垛的其它未出库单品的层号，否则必须从首层现出原则出库。
	 * 
	 * @param ctx
	 * @param orgUnitID
	 *            组织的ID
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static boolean isFloor001(Context ctx, String orgUnitID)
			throws BOSException, EASBizException {

		boolean iFlag = STParamReader.getParameter(ctx, "STSYS_FLOOR001",
				STParamViewInfo.isInWarehouse(orgUnitID, "STSYS_FLOOR001"))
				.isAffirmParamValue();
		return iFlag;
	}

	/**
	 * 
	 * 描述：明细库调整单（库存调整）中是否允许手工修改层号”参数，如果启用此参数，则在明细库调整单的库存调整业务类型处理中可以修改层号，
	 * 并反写该层号到相应的产品明细库中，否则层号不允许修改。
	 * 
	 * @param ctx
	 * @param orgUnitID
	 *            组织的ID
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy 创建时间：2008-05-21
	 */
	public static boolean isFloor002(Context ctx, String orgUnitID)
			throws BOSException, EASBizException {

		boolean iFlag = STParamReader.getParameter(ctx, "STSYS_FLOOR002",
				STParamViewInfo.isInWarehouse(orgUnitID, "STSYS_FLOOR002"))
				.isAffirmParamValue();
		return iFlag;
	}

}
