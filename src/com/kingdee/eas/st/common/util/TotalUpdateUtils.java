/**
 * 
 */
package com.kingdee.eas.st.common.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.st.common.util.total.AbstractTotalItem;
import com.kingdee.eas.st.common.util.total.TotalItem;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.enums.StringEnum;

/**
 * ���ڿ�������ص��ݣ��޸Ļ��ܷ�¼��ɾ����¼���޸ĵ���ֿ⣩ʱ��ͬ��������ϸ��¼
 * 
 * @author zhiwei_wang
 * @date 2008-4-1
 */
public class TotalUpdateUtils {
	public static void invokeRemoveLine(KDTable mergeTable, int rowIndex,
			KDTable detailTable, String[] joinFields_mergeTable,
			String[][] analyseFields) {
		if (rowIndex < 0) {
			return;
		}

		List rowIndexList = new ArrayList();
		// 1. ��ȡ��Ӧ��¼
		List detailRows = getDetails(mergeTable, rowIndex, detailTable,
				joinFields_mergeTable, analyseFields);
		// 2. ȡ�ö�Ӧ�кţ�������
		Iterator it = detailRows.iterator();
		while (it.hasNext()) {
			rowIndexList.add(new Integer(((IRow) it.next()).getRowIndex()));
		}
		Collections.sort(rowIndexList, Collections.reverseOrder());
		// 3. ɾ����ϸ��¼
		for (int i = 0; i < rowIndexList.size(); i++) {
			Integer detailRowIndex = (Integer) rowIndexList.get(i);
			detailTable.removeRow(detailRowIndex.intValue());
			((AbstractObjectCollection) detailTable.getUserObject())
					.removeObject(detailRowIndex.intValue());
		}
	}

	public static void invokeUpdateLine(KDTable mergeTable, int rowIndex,
			KDTable detailTable, String[] joinFields_mergeTable,
			String[][] analyseFields, String detailUpdateField,
			Object updateValue) {
		// 1. ��ȡ��Ӧ��¼
		List detailRows = getDetails(mergeTable, rowIndex, detailTable,
				joinFields_mergeTable, analyseFields);
		// 2. ȡ�ö�Ӧ�кţ�������
		Iterator it = detailRows.iterator();
		while (it.hasNext()) {
			IRow detailRow = (IRow) it.next();
			KDTableUtils.setFieldValue(detailTable, detailRow.getRowIndex(),
					detailUpdateField, updateValue);
		}
	}

	public static void hideDetailTableRows(KDTable mergeTable,
			KDTable detailTable, String[] joinFields_mergeTable,
			String[][] analyseFields, boolean isShowAllDetail) {
		// ���û��ѡ�л��ܷ�¼���У�����ʾ������ϸ��¼
		int mergeTableSelectedRowIndex = mergeTable.getSelectManager()
				.getActiveRowIndex();
		if (mergeTableSelectedRowIndex < 0
				|| mergeTableSelectedRowIndex >= mergeTable.getRowCount()) {
			isShowAllDetail = true;
		}
		if (isShowAllDetail) {
			for (int i = 0, size = detailTable.getRowCount(); i < size; i++) {
				detailTable.getRow(i).getStyleAttributes().setHided(false);
			}
		} else {
			for (int i = 0, size = detailTable.getRowCount(); i < size; i++) {
				detailTable.getRow(i).getStyleAttributes().setHided(true);
			}
			List rows = TotalUpdateUtils.getDetails(mergeTable,
					mergeTableSelectedRowIndex, detailTable,
					joinFields_mergeTable, analyseFields);
			Iterator it = rows.iterator();
			while (it.hasNext()) {
				IRow row = (IRow) it.next();
				row.getStyleAttributes().setHided(false);
			}
		}
	}

	/**
	 * ���ش���Ļ��ܷ�¼����Ӧ����ϸ��¼�����е�Ԫ��ΪIRow���� zhiwei_wang 2008-4-3
	 * 
	 * @param mergeTable
	 *            ���ܷ�¼
	 * @param rowIndex
	 *            ���ܷ�¼�к�
	 * @param detailTable
	 *            ��ϸ��¼
	 * @param joinFields_mergeTable
	 *            ���ܷ�¼�У���������ϸ��¼���ӵ��ֶΣ�Ҳ�����ж����ܷ�¼�Ƿ�Ϊ�հ���
	 * @param analyseFields
	 *            ������¼�Ļ����ֶΣ�һ��2��N�е�����
	 * @return
	 */
	public static List getDetails(KDTable mergeTable, int rowIndex,
			KDTable detailTable, String[] joinFields_mergeTable,
			String[][] analyseFields) {
		ArrayList rows = new ArrayList();

		if (checkInputForGetDetails(detailTable, mergeTable, rowIndex,
				joinFields_mergeTable, analyseFields)) {
			// 1. ������ܷ�¼�е�TotalItem
			Map mergeValues = KDTableUtils.getMapFieldValues(mergeTable,
					rowIndex, analyseFields[0]);
			AbstractTotalItem mergeTotalItem = TotalItem.instance(
					analyseFields[0], mergeValues);
			// 2. ����ϸ��¼����ѭ��
			for (int i = 0, size = detailTable.getRowCount(); i < size; i++) {
				Map detailValues = KDTableUtils.getMapFieldValues(detailTable,
						i, analyseFields[1]);
				AbstractTotalItem detailTotalItem = TotalItem.instance(
						analyseFields[1], detailValues);
				if (mergeTotalItem.equals(detailTotalItem)) {
					rows.add(detailTable.getRow(i));
				}
			}
		}

		return rows;
	}

	// ���������
	private static boolean checkInputForGetDetails(KDTable mergeTable,
			KDTable detailTable, int rowIndex, String[] joinFields_mergeTable,
			String[][] analyseFields) {
		// ����¼�ֶ���������һ����һ������N�е��ַ�������
		if (analyseFields == null || analyseFields.length != 2) {
			return false;
		} else {
			if (analyseFields[0] == null || analyseFields[1] == null) {
				return false;
			} else {
				if (analyseFields[0].length != analyseFields[1].length) {
					return false;
				} else {
					for (int i = 0; i < analyseFields[0].length; i++) {
						if (StringUtils.isEmpty(analyseFields[0][i])
								|| StringUtils.isEmpty(analyseFields[1][i])) {
							return false;
						}
					}
				}
			}
		}

		// У�顰���ܷ�¼��
		if (detailTable == null) {
			return false;
		} else {
			// lable1:
			// for(int i = 0, size = detailTable.getRowCount(); i < size; i++){
			// for(int j = 0; j < joinFields[0].length; j++){
			// Object val = KDTableUtils.getFieldValue(detailTable, i,
			// joinFields[0][j]);
			// if(val == null){
			// BeforeActionListener listener = detailTable.getBeforeAction();
			// detailTable.removeRow(i); // �������Ϊ�գ�����Ϊ�ǿհ��У�ɾ��
			// detailTable.setBeforeAction(listener);
			// size--;
			// i--;
			// continue lable1;
			// }
			// }
			// }
			// ������ܷ�¼����Ϊ�գ�����Ϊ�ǿհ���
			// for(int j = 0; j < joinFields_mergeTable.length; j++){
			// ���������ſ���Ϊ�գ�����ֻ����һ���ֶΣ������ֶΣ�
			if (joinFields_mergeTable.length > 0) {
				Object val = KDTableUtils.getFieldValue(detailTable, rowIndex,
						joinFields_mergeTable[0]);
				if (val == null) {
					return false;
				}
			}

			if (detailTable.getRowCount() <= 0) { // �����ɾ����հ��к󣬻��ܷ�¼������Ϊ0�����˳�
				return false;
			}
		}
		// У����ϸ��¼
		// TODO �����������table��row�Ƿ���ָ�����ֶ�
		if (mergeTable == null) {
			return false;
		}
		return true;
	}

	/**
	 * ������ϸ��¼FMergeTableId�ֶΣ�������ϸ��¼����ܷ�¼�Ķ�Ӧ��ϵ modify 20080517 zhiwei_wang
	 * ����״̬�ĵ��������ύʱ��select�����ĵ���״̬���ݴ棬�����޷�����mergeTableID�����ﲻ���жϵ���״̬���ɵ����߱�֤
	 * 
	 * @author zhiwei_wang
	 * @date 2008-4-5
	 * @param ctx
	 * @param tableNames
	 * @param billId
	 * @param analyseFields
	 * @throws BOSException
	 */
	public static void updateMergeTableId(Context ctx, String[] tableNames,
			String billId, String[][] analyseFields) throws BOSException {
		if (checkInputForSetMergeTableId(ctx, tableNames, billId, analyseFields)) {
			String b = " \n";
			StringBuffer sb = new StringBuffer();
			// sb.append(b)
			// .append("SELECT FBaseStatus From ").append(tableNames[0]).append(
			// " WHERE fid='").append(billId).append("'");
			//			
			// IRowSet rs = DbUtil.executeQuery(ctx, sb.toString());
			IRowSet rs;
			try {
				// if(rs.next()){
				// int billStatus = rs.getInt("FBaseStatus");
				// if(billStatus == BillBaseStatusEnum.SUBMITED_VALUE
				// || billStatus == BillBaseStatusEnum.AUDITED_VALUE){ //
				// ֻ�����ύ�����״̬�ĵ��ݣ�����״̬�ĵ��ݣ���Ϊ���ݿ��ܲ����������ܳ��ָ��´��������
				// 1. ��ѯ���ܷ�¼��ֵ
				sb.setLength(0);
				sb.append(b).append("SELECT fid, ").append(b);
				for (int i = 0; i < analyseFields[0].length; i++) {
					sb.append(analyseFields[0][i]).append(",").append(b);
				}
				sb.deleteCharAt(sb.length() - 3); // ɾ����Ķ���

				sb.append(b).append("FROM").append(b).append(tableNames[1])
						.append(b).append("WHERE fparentid='").append(billId)
						.append("'");
				rs = DbUtil.executeQuery(ctx, sb.toString());
				// 2. �ѻ��ܷ�¼��ֵ�����ڶ�ά�����У�����ĵ�һ���Ƿ�¼id
				String[][] mergeTableValues = new String[rs.size()][analyseFields[0].length + 1];
				int currentRow = 0;
				while (rs.next()) {
					mergeTableValues[currentRow][0] = rs.getString("fid");
					for (int i = 0; i < analyseFields[0].length; i++) {
						mergeTableValues[currentRow][i + 1] = rs
								.getString(analyseFields[0][i]);
					}
					currentRow++;
				}
				// 3. �Ի��ܷ�¼����ѭ����������Ӧ����ϸ��¼
				for (int i = 0; i < mergeTableValues.length; i++) {
					sb.setLength(0);
					sb.append(b).append("UPDATE").append(b).append(
							tableNames[2]).append(b).append(
							"SET FMergeTableId='").append(
							mergeTableValues[i][0]).append("'").append(b)
							.append("WHERE").append(b);

					for (int j = 0; j < analyseFields[0].length; j++) {
						if (StringUtils.isEmpty(mergeTableValues[i][j + 1])) { // ���Ŀ��ֵ��null����ַ���
							sb.append("(").append(analyseFields[1][j]).append(
									"='' OR ").append(analyseFields[1][j])
									.append(" is null)").append(b).append(
											"AND ");
						} else {
							sb.append(analyseFields[1][j]).append("='").append(
									mergeTableValues[i][j + 1]).append("'")
									.append(b).append("AND ");
						}
					}
					sb.append("fparentid='").append(billId).append("'").append(
							b);
					DbUtil.execute(ctx, sb.toString());
				}
				// }
				// }
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
	}

	private static boolean checkInputForSetMergeTableId(Context ctx,
			String[] tableNames, String billId, String[][] analyseFields) {
		if (ctx == null
				|| tableNames == null
				|| tableNames.length != 3
				|| StringUtils.isEmpty(billId)
				// || StringUtils.isEmpty(mergeTableField)
				// || StringUtils.isEmpty(detailTableField)
				|| analyseFields == null || analyseFields.length != 2
				|| analyseFields[0] == null || analyseFields[1] == null
				|| analyseFields[0].length <= 0
				|| analyseFields[0].length != analyseFields[1].length) {
			return false;
		}
		return true;
	}

	/**
	 * ���ݻ������ݣ����¶�ӦԴ����¼id����Ϊ�ڻ���ʱ�������Ϣ�Ѿ���ʧ���ƻ�
	 * 
	 * @author zhiwei_wang
	 * @date 2008-5-17
	 * @param ctx
	 * @param mergeEntryInfos
	 *            Ŀ�굥�ݻ��ܷ�¼info����
	 * @param mergeEntryPropNames
	 *            Ŀ�굥�ݻ��ܷ�¼�������ֶε���������ʵ���ϵ����֣�
	 * @param srcMergeDBNames
	 *            Դ�����ܷ�¼�������ֶε����ݿ��ֶ���
	 * @param srcBillId
	 *            Դ��id������Դ����¼id��
	 * @param srcMergeTableName
	 *            Դ�����ܷ�¼�����ݿ����
	 * @throws BOSException
	 */
	public static void updateSourceBillEntryID(Context ctx,
			AbstractObjectCollection mergeEntryInfos,
			String[] mergeEntryPropNames, String[] srcMergeDBNames,
			String srcBillId, String srcMergeTableName) throws BOSException {
		if (checkInput_UpdateSourceBillEntryID(ctx, mergeEntryInfos,
				mergeEntryPropNames, srcMergeDBNames, srcBillId,
				srcMergeTableName)) {

			String[] mergeTableValues = new String[mergeEntryPropNames.length];
			for (int i = 0, size = mergeEntryInfos.size(); i < size; i++) {
				IObjectValue entryInfo = mergeEntryInfos.getObject(i);

				// 1. �ѻ��ܷ�¼��ֵ���浽������
				for (int propIndex = 0; propIndex < mergeEntryPropNames.length; propIndex++) {
					mergeTableValues[propIndex] = getDBString(entryInfo
							.get(mergeEntryPropNames[propIndex]));
				}

				// 2. ƴװsql
				StringBuffer sb = new StringBuffer();
				String b = " \n";
				sb.setLength(0);
				sb.append("select FId from").append(b)
						.append(srcMergeTableName).append(b).append("Where")
						.append(b);
				for (int j = 0; j < srcMergeDBNames.length; j++) {
					if (StringUtils.isEmpty(mergeTableValues[j])) { // ���Ŀ��ֵ��null����ַ���
						sb.append("(").append(srcMergeDBNames[j]).append(
								"='' OR ").append(srcMergeDBNames[j]).append(
								" is null)").append(b).append("AND ");
					} else {
						sb.append(srcMergeDBNames[j]).append("='").append(
								mergeTableValues[j]).append("'").append(b)
								.append("AND ");
					}
				}
				sb.append("fparentid='").append(srcBillId).append("'")
						.append(b);
				IRowSet rs = DbUtil.executeQuery(ctx, sb.toString());

				// 3. ����Դ����¼id
				try {
					if (rs.next()) {
						String srcBillEntryId = rs.getString("FId");
						setSrcBillEntryId(entryInfo, srcBillEntryId);
					}
				} catch (SQLException e) {
					throw new SQLDataException(e);
				}
			}

		}
	}

	// ����������ݵĺϷ���
	private static boolean checkInput_UpdateSourceBillEntryID(Context ctx,
			AbstractObjectCollection mergeEntryInfos,
			String[] mergeEntryPropNames, String[] srcMergeDBNames,
			String srcBillId, String srcMergeTableName) {
		if (StringUtils.isEmpty(srcBillId)) {
			return false;
		}
		return true;
	}

	private static void setSrcBillEntryId(IObjectValue entryInfo,
			String srcBillEntryId) {
		if (entryInfo.containsKey("sourceBillEntryId")
				&& !StringUtils.isEmpty(srcBillEntryId)) {
			entryInfo.put("sourceBillEntryId", srcBillEntryId);
		}
	}

	private static String getDBString(Object o) {
		if (o == null) {
			return "";
		} else if (o instanceof StringEnum) {
			return ((StringEnum) o).getValue();
		} else if (o instanceof AbstractCoreBaseInfo) {
			return ((AbstractCoreBaseInfo) o).getId().toString();
		}

		return o.toString();
	}

}
