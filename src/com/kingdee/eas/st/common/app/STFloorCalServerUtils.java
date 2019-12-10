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
	 * ���������ʱ���Բ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��� ���°�����1�ȶ�ԭ�е��ݽ��м���
	 * 2����ԭ�е��������ȥ�����Ѿ����ڵ���ϸ�����ݡ� �����ڣ�������ⵥ��������͵ĵ��ݡ�
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ���Բ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��� ���°�����1�ȶ�ԭ�е��ݽ��м���
	 * 2����ԭ�е��������ȥ�����Ѿ����ڵ���ϸ�����ݡ� �����ڣ�������ⵥ��������͵ĵ��ݡ�
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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

		// �ȸ���Ϊ���µĲ��
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
	 * ����������˲�����Ӧ������ϸ��������Ĳ��ҵ�� ���°����� �����ڣ�
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��£���Ϊ��⴦��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��£���Ϊ��⴦��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��£���Ϊ��⴦��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��£���Ϊ��⴦������ϸ��Ĳ�����к�ҵ��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��£���Ϊ��⴦������ϸ��Ĳ�����к�ҵ��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��£���Ϊ��⴦������ϸ��Ĳ�����к�ҵ��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ����ԭ����ϸ��Ĳ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и���
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ����ԭ����ϸ��Ĳ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������˳����и���
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ����ԭ����ϸ��Ĳ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и���
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ����ԭ����ϸ��Ĳ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и���
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ����ԭ����ϸ��Ĳ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и���
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * ���������ʱ����ԭ����ϸ��Ĳ������в�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и���
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
	 * �����������ʱ��У�����е�������ϸ��Ĳ����Ƿ���ͬ���������ͬ���׳��쳣��������ˣ���ͬ���������,�÷��������ڴ���������ķ����С�
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
	 */
	public static void calFloorSeqForBillUnAuditCheck(Context ctx,
			String billTableName, IObjectPK[] pkList, Vector errPks)
			throws BOSException, EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		int pkNum = pkList.length;/* ��Ҫ����˵ĵ����� */
		IRowSet rowSet = null;
		StringBuffer numString = new StringBuffer();
		StringBuffer sqlString = new StringBuffer();
		for (int bigI = 0; bigI < pkNum; bigI++) {// ��pk�б����ѭ��
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

			// ��鷴�������
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
	 * �����������ʱ��У�����е�������ϸ��Ĳ����Ƿ���ͬ���������ͬ���׳��쳣��������ˣ���ͬ���������,�÷��������ڴ���������ķ����С�
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
	 */
	public static void calFloorSeqForBillUnAuditCheckForDetailAdjust(
			Context ctx, String billTableName, IObjectPK[] pkList, Vector errPks)
			throws BOSException, EASBizException {
		String wareHouseFieldName = "FLocationid";
		int pkNum = pkList.length;/* ��Ҫ����˵ĵ����� */
		IRowSet rowSet = null;
		StringBuffer numString = new StringBuffer();
		StringBuffer sqlString = new StringBuffer();
		for (int bigI = 0; bigI < pkNum; bigI++) {// ��pk�б����ѭ��
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

			// ��鷴�������
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

			// ��鷴�������
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
	 * �����������ʱ��У�����е�������ϸ��Ĳ����Ƿ���ͬ���������ͬ���׳��쳣��������ˣ���ͬ��������ˣ��÷���ֱ���׳��쳣��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
	 */
	public static void calFloorSeqForBillUnAuditCheck(Context ctx,
			String billTableName, IObjectPK[] pkList) throws BOSException,
			EASBizException {
		String wareHouseFieldName = getWareHouseFieldName(billTableName);

		int pkNum = pkList.length;/* ��Ҫ����˵ĵ����� */
		IRowSet rowSet = null;
		StringBuffer numString = new StringBuffer();
		StringBuffer sqlString = new StringBuffer();
		for (int bigI = 0; bigI < pkNum; bigI++) {// ��pk�б����ѭ��
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

			// ��鷴�������
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
	 * ���������ҷ�¼����ͬ�ж����С��ŵĵ�Ʒ�������С��Ų�Ϊ1������ʾ�û���ֻ�ܴ��ײ����γ��⣬��������Ƚ��е��⴦����
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
		// �������
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
	 * ����������¼�Ƿ��ж��Ϊ�գ����ͬʱΪ�գ����׳��쳣
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
		// �������
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
	 * ����������¼�Ƿ� "��" �ж��Ϊ�գ����ͬʱΪ�գ����׳��쳣
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author miller_xiao ����ʱ�䣺2009-01-15
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
		// �������
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
	 * ����������Ƿ������ͬ���
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author miller_xiao ����ʱ�䣺2009-01-22
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

		// �������
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
	 * ���������ҷ�¼����ͬ�ж����С��ŵĵ�Ʒ�������С��Ų�Ϊ1������ʾ�û���ֻ�ܴ��ײ����γ��⣬��������Ƚ��е��⴦����
	 * �÷���������ϸ���λ���������ύ
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-20
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
		// �������
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
		// �������
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
	 * ������һ�ŵ���ֻ����һ����ͬ�����˲�ſ��ƵĲֿ��µĵ�Ʒ�����ڲ�ͬ�����˲�ſ��ƵĲֿ������ж�����Ʒ���в�š�
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-24
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
		// �������
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
	 * ������У��n����Ʒ��Ų��ܶϺ�
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
			// �������
			rowSet = DbUtil.executeQuery(ctx, sqlString.toString());
			// TODO ����쳣�д���������жϣ�Ҫ��ʾʲô��ʾ����
			if (rowSet.next()) {
				throw new STBillException(STBillException.FLOORNUMBERNEEDSEQ);
			}
		} catch (EASBizException e) {
			throw new STBillException(STBillException.FLOORNUMBERNEEDSEQ);
		} catch (Exception e) {
			throw new BOSException(e);
		} finally {
			// ������ʱ��.
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
		} else if (billTableName.equals("T_IV_TransferOBDE") // ��������
				|| billTableName.equals("T_IV_SaleIssueBillDetailEntry") // ���۳���
				|| billTableName.equals("T_IV_MoveIssueBillDetailEntry") // ��������
				|| billTableName.equals("T_IV_StockTBDE") // ��������
		) {
			wareHouseFieldName = "FIssueLocationID";
		} else if (billTableName.equals("T_IV_PurIWBDE") // �ɹ����
				|| billTableName.equals("T_IV_MoveIWBDE") // �������
		) {
			wareHouseFieldName = "FReceiptLocationID";
		}
		return wareHouseFieldName;
	}

	/**
	 * 
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,�������
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,�������
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,����װ���嵥���
	 * 
	 * @param ctx
	 * 
	 * @param pk ����id
	 * 
	 * @param billTableName ��ϸ���¼���ݱ���
	 * 
	 * @throws BOSException
	 * 
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,�������
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,����һ����Ϊ�˴�����ͬID��ֵ��
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
	 */
	public static void calFloorSeqForProduceDetailSetZero(Context ctx,
			String billTableName, HashMap validPks) throws BOSException,
			EASBizException {

		if (billTableName.equals("T_ST_LadingEntry")) {
			// ���Ϊװ���嵥���жϸõ��Ƿ�����Ϻų��⣬���������Ҫ������ļ����ŵĲ�Ž��д���
			IRowSet rowSet = null;
			Set set = validPks.entrySet();
			Iterator it = set.iterator();
			StringBuffer sqlString = new StringBuffer();
			// ��������ֻ������֯�����ģ��ں��������ʱ������֯�����⣬�������µ�����

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
								// ���ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��¡� xmcy
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
								// ���ʱ���Բ�Ž����Զ����㣬���ݲֿ⣬�кţ��������������¼��˳����и��¡� xmcy
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
			// ȥ���ظ���KEY
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
			// ����û����ϸ��¼������¡�
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
			// ������ʱ��.
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
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,���ڷ����
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,���ڷ����
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * �������ֱ𽫹����ĵ�Ʒ�����Ϊ0�����Ҵ��ж��������Ʒ�������ż�n,���ڷ����
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * ������������ϸ���λ�������Ŀ�λ������˲��������ڿ�λ�������ɿ�λ�ĳ���+�¿�λ����⡣
	 * 
	 * @param ctx
	 * @param pk
	 *            ����id
	 * @param billTableName
	 *            ��ϸ���¼���ݱ���
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
	 */
	public static void calFloorSeqForProduceDetailForDetailStorageAdjustBill(
			Context ctx, String pk) throws BOSException, EASBizException {
		// ��������Ϊ0 xmcy 2008-05-21

		calFloorSeqForProduceDetailSetZeroNew(ctx, "T_ST_DetailSABE", pk);

		// ���ʱ���Բ�Ž����Զ����㣬���ݿ�λ���кţ��������������¼��˳����и��¡� xmcy 2008-05-21
		calFloorSeqForBillNew(ctx, "T_ST_DetailSABE", pk);
		calFloorSeqForProduceDetailNew(ctx, "T_ST_DetailSABE", pk, true);
	}

	public static void calFloorSeqForProduceDetailForDetailStorageAdjustBill_Unaudit(
			Context ctx, String pk) throws BOSException, EASBizException {
		// ��������Ϊ0 xmcy 2008-05-21
		calFloorSeqForProduceDetailSetZeroForUnAuditBySequenceNumber(ctx,
				"T_ST_DetailSABE", pk);
		// ���ʱ���Բ�Ž����Զ����㣬���ݿ�λ���кţ��������������¼��˳����и��¡� xmcy 2008-05-21
		calFloorSeqForBill(ctx, "T_ST_DetailSABE", pk);
		calFloorSeqForProduceDetailNew(ctx, "T_ST_DetailSABE", pk, false);
	}

	/**
	 * 
	 * ������װ���嵥�е�Ʒ�ɴ��м��ų��⡱��������ô˲�������ϵͳ�е�װ���嵥�ɴ��м��ų��⣬
	 * ���Զ��������ж������δ���ⵥƷ�Ĳ�ţ����������ײ��ֳ�ԭ����⡣
	 * 
	 * @param ctx
	 * @param orgUnitID
	 *            ��֯��ID
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
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
	 * ��������ϸ��������������������Ƿ������ֹ��޸Ĳ�š�������������ô˲�����������ϸ��������Ŀ�����ҵ�����ʹ����п����޸Ĳ�ţ�
	 * ����д�ò�ŵ���Ӧ�Ĳ�Ʒ��ϸ���У������Ų������޸ġ�
	 * 
	 * @param ctx
	 * @param orgUnitID
	 *            ��֯��ID
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @author xmcy ����ʱ�䣺2008-05-21
	 */
	public static boolean isFloor002(Context ctx, String orgUnitID)
			throws BOSException, EASBizException {

		boolean iFlag = STParamReader.getParameter(ctx, "STSYS_FLOOR002",
				STParamViewInfo.isInWarehouse(orgUnitID, "STSYS_FLOOR002"))
				.isAffirmParamValue();
		return iFlag;
	}

}
