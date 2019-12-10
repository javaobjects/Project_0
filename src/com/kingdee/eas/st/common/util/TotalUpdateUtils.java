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
 * 用于库存调拨相关单据，修改汇总分录（删除分录、修改调入仓库）时，同步更新明细分录
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
		// 1. 获取对应分录
		List detailRows = getDetails(mergeTable, rowIndex, detailTable,
				joinFields_mergeTable, analyseFields);
		// 2. 取得对应行号，并排序
		Iterator it = detailRows.iterator();
		while (it.hasNext()) {
			rowIndexList.add(new Integer(((IRow) it.next()).getRowIndex()));
		}
		Collections.sort(rowIndexList, Collections.reverseOrder());
		// 3. 删除明细分录
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
		// 1. 获取对应分录
		List detailRows = getDetails(mergeTable, rowIndex, detailTable,
				joinFields_mergeTable, analyseFields);
		// 2. 取得对应行号，并排序
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
		// 如果没有选中汇总分录的行，则显示所有明细分录
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
	 * 返回传入的汇总分录所对应的明细分录，其中的元素为IRow类型 zhiwei_wang 2008-4-3
	 * 
	 * @param mergeTable
	 *            汇总分录
	 * @param rowIndex
	 *            汇总分录行号
	 * @param detailTable
	 *            明细分录
	 * @param joinFields_mergeTable
	 *            汇总分录中，用于与明细分录连接的字段，也用于判定汇总分录是否为空白行
	 * @param analyseFields
	 *            两个分录的汇总字段，一个2行N列的数组
	 * @return
	 */
	public static List getDetails(KDTable mergeTable, int rowIndex,
			KDTable detailTable, String[] joinFields_mergeTable,
			String[][] analyseFields) {
		ArrayList rows = new ArrayList();

		if (checkInputForGetDetails(detailTable, mergeTable, rowIndex,
				joinFields_mergeTable, analyseFields)) {
			// 1. 计算汇总分录行的TotalItem
			Map mergeValues = KDTableUtils.getMapFieldValues(mergeTable,
					rowIndex, analyseFields[0]);
			AbstractTotalItem mergeTotalItem = TotalItem.instance(
					analyseFields[0], mergeValues);
			// 2. 对明细分录进行循环
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

	// 检查输入项
	private static boolean checkInputForGetDetails(KDTable mergeTable,
			KDTable detailTable, int rowIndex, String[] joinFields_mergeTable,
			String[][] analyseFields) {
		// 检查分录字段名，这里一定是一个两行N列的字符串数组
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

		// 校验“汇总分录”
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
			// detailTable.removeRow(i); // 如果物料为空，则认为是空白行，删除
			// detailTable.setBeforeAction(listener);
			// size--;
			// i--;
			// continue lable1;
			// }
			// }
			// }
			// 如果汇总分录物料为空，则认为是空白行
			// for(int j = 0; j < joinFields_mergeTable.length; j++){
			// 由于熔炼号可以为空，这里只检查第一个字段（物料字段）
			if (joinFields_mergeTable.length > 0) {
				Object val = KDTableUtils.getFieldValue(detailTable, rowIndex,
						joinFields_mergeTable[0]);
				if (val == null) {
					return false;
				}
			}

			if (detailTable.getRowCount() <= 0) { // 如果在删除完空白行后，汇总分录的行数为0，则退出
				return false;
			}
		}
		// 校验明细分录
		// TODO 检查输入项，检查table和row是否有指定的字段
		if (mergeTable == null) {
			return false;
		}
		return true;
	}

	/**
	 * 更新明细分录FMergeTableId字段，建立明细分录与汇总分录的对应关系 modify 20080517 zhiwei_wang
	 * 保存状态的调拨订单提交时，select出来的单据状态是暂存，导致无法更新mergeTableID，这里不再判断单据状态，由调用者保证
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
				// 只处理提交和审核状态的单据，保存状态的单据，因为数据可能不完整，可能出现更新错误的现象
				// 1. 查询汇总分录的值
				sb.setLength(0);
				sb.append(b).append("SELECT fid, ").append(b);
				for (int i = 0; i < analyseFields[0].length; i++) {
					sb.append(analyseFields[0][i]).append(",").append(b);
				}
				sb.deleteCharAt(sb.length() - 3); // 删除后的逗号

				sb.append(b).append("FROM").append(b).append(tableNames[1])
						.append(b).append("WHERE fparentid='").append(billId)
						.append("'");
				rs = DbUtil.executeQuery(ctx, sb.toString());
				// 2. 把汇总分录的值保存在二维数组中，数组的第一列是分录id
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
				// 3. 对汇总分录数组循环，更新相应的明细分录
				for (int i = 0; i < mergeTableValues.length; i++) {
					sb.setLength(0);
					sb.append(b).append("UPDATE").append(b).append(
							tableNames[2]).append(b).append(
							"SET FMergeTableId='").append(
							mergeTableValues[i][0]).append("'").append(b)
							.append("WHERE").append(b);

					for (int j = 0; j < analyseFields[0].length; j++) {
						if (StringUtils.isEmpty(mergeTableValues[i][j + 1])) { // 如果目标值是null或空字符串
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
	 * 根据汇总依据，重新对应源单分录id，因为在汇总时，这个信息已经丢失或破坏
	 * 
	 * @author zhiwei_wang
	 * @date 2008-5-17
	 * @param ctx
	 * @param mergeEntryInfos
	 *            目标单据汇总分录info集合
	 * @param mergeEntryPropNames
	 *            目标单据汇总分录，汇总字段的属性名（实体上的名字）
	 * @param srcMergeDBNames
	 *            源单汇总分录，汇总字段的数据库字段名
	 * @param srcBillId
	 *            源单id（不是源单分录id）
	 * @param srcMergeTableName
	 *            源单汇总分录的数据库表名
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

				// 1. 把汇总分录的值保存到数组中
				for (int propIndex = 0; propIndex < mergeEntryPropNames.length; propIndex++) {
					mergeTableValues[propIndex] = getDBString(entryInfo
							.get(mergeEntryPropNames[propIndex]));
				}

				// 2. 拼装sql
				StringBuffer sb = new StringBuffer();
				String b = " \n";
				sb.setLength(0);
				sb.append("select FId from").append(b)
						.append(srcMergeTableName).append(b).append("Where")
						.append(b);
				for (int j = 0; j < srcMergeDBNames.length; j++) {
					if (StringUtils.isEmpty(mergeTableValues[j])) { // 如果目标值是null或空字符串
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

				// 3. 更新源单分录id
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

	// 检查输入数据的合法性
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
