package com.kingdee.eas.st.common.util.total;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.agent.AgentState;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.util.PropertyContainer;
import com.kingdee.util.StringUtils;

public class TotalHelper {

	private KDTable mergeTable;
	private KDTable detailTable;
	private String[][] joinFields;
	private String[][] analyseFields;
	private String[][] calcFields;
	private DataBinder dataBinder;
	private ArrayList botpField; // botp字段，在汇总后，删除分录时，根据此字段，决定是否可以删除

	/**
	 * 汇总工具 zhiwei_wang 2008-3-21
	 * 
	 * @param mergeTable
	 *            ，汇总分录
	 * @param detailTable
	 *            ，明细分录
	 * @param joinFields
	 *            ，汇总分录余明细分录进行对应的依据
	 * @param analyseFields
	 *            ，汇总分录与明细分录汇总依据，该字段一定是一个两行N列的二维数组，第一行为汇总分录中的字段名称，第二行为明细分录中的字段名
	 * @param calcFields
	 *            ，汇总分录与明细分录需要汇总的字段，该字段一定是一个两行N列的二维数组，第一行为汇总分录中的字段名称，第二行为明细分录中的字段名
	 * @param botpField
	 *            ,
	 *            botp字段，在汇总后，删除分录时，根据此字段，决定是否可以删除，大小一定要等于mergeTable的行数，如果不是botp推过来的行
	 *            ，则对应值为空字符传("")
	 */
	public TotalHelper(KDTable mergeTable, KDTable detailTable,
			String[][] joinFields, String[][] analyseFields,
			String[][] calcFields, DataBinder dataBinder, ArrayList botpField) {
		this.mergeTable = mergeTable;
		this.detailTable = detailTable;
		this.joinFields = joinFields;
		this.analyseFields = analyseFields;
		this.calcFields = calcFields;
		this.dataBinder = dataBinder;
		this.botpField = botpField;
	}

	/**
	 * 进行汇总 默认锁定汇总生成的数据所在的单元格
	 * 
	 * @return zhiwei_wang 2008-3-21
	 */
	public boolean total() {
		return total(true);
	}

	/**
	 * 进行汇总
	 * 
	 * @param boolean lockCell 是否锁定汇总生成的数据所在的单元格
	 * @return zhiwei_wang 2008-3-21
	 */
	public boolean total(boolean lockCell) {
		// 0. 校验输入数据
		if (!checkData()) {
			return false;
		}
		// 1. 抓取明细分录数据
		List detailValueList = getDetailData();

		// 2. 汇总明细数据
		TotalStrategy strategy = new TotalStrategy();
		ITotalCaltulater caltulater = new TotalCaltulater(strategy);
		strategy.putAnalyseString(analyseFields[1], joinFields[1]);
		for (int i = 0, size = detailValueList.size(); i < size; i++) {
			Map detailInfo = (Map) detailValueList.get(i);
			// Map map = new HashMap();
			// // String[] s = new String[analyseFieldNamesForSelect.length +
			// calcFieldNames.length];
			// // for(int j = 0; j < analyseFieldNamesForSelect.length; j++){
			// // s[j] = analyseFieldNamesForSelect[j];
			// // }
			// // for(int j = analyseFieldNamesForSelect.length; j < s.length;
			// j++){
			// // s[j] = calcFieldNames[j - analyseFieldNamesForSelect.length];
			// // }
			// for(int j = 0; j < allDetailFields.length; j++){
			// map.put(allDetailFields[j], detailInfo.get(allDetailFields[j]));
			// }
			// detailInfo.put("batchQty", new Integer(1));
			// //加入捆数，捆数字段比较特殊，因为该字段在明细分录中没有出现，但是又要求汇总，只能在这里处理
			try {
				caltulater.groupBy(detailInfo);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}

		// caltulater.iterator();
		Map total = caltulater.getTotal();
		Map joinKey = caltulater.getJoinKey();
		// 3. 抓取汇总分录数据
		ArrayList mergeTableValueList = new ArrayList(mergeTable.getRowCount());
		for (int i = 0, size = mergeTable.getRowCount(); i < size; i++) {
			Map mergeValues = KDTableUtils.getMapFieldValues(mergeTable, i,
					joinFields[0]);
			AbstractTotalItem joinKey_TotalItem = TotalItem.instance(
					joinFields[0], mergeValues);

			mergeTableValueList.add(i, new TotalJoinInfo(joinKey_TotalItem, i));
		}

		// 4. 把汇总数据合并到汇总分录
		// 4.1 首先清理汇总分录上的字段（不删除行），同时把前面锁定的字段解锁（所有行）
		// 4.1 不清理汇总分录上的字段，只是解锁
		for (int rowIndex = 0, rowCount = mergeTable.getRowCount(); rowIndex < rowCount; rowIndex++) {
			// 把汇总依据字段锁定:这些单据都不能直接新增，在botp关联生成之后，这些字段都要锁定
			// for(int i = 0, size = analyseFields[0].length; i < size; i++){
			// KDTableUtils.setFreezeCell(mergeTable, rowIndex,
			// analyseFields[0][i], false);
			// }
			for (int i = 0, size1 = calcFields[0].length; i < size1; i++) {
				// KDTableUtils.setFieldValue(mergeTable, rowIndex,
				// calcFields[0][i], null);
				KDTableUtils.setFreezeCell(mergeTable, rowIndex,
						calcFields[0][i], false);
			}
		}
		// 4.2 合并动作
		Iterator it = total.keySet().iterator();
		while (it.hasNext()) {
			AbstractTotalItem key = (AbstractTotalItem) it.next();
			Map detail = (Map) total.get(key);
			// Object detailKeyObj = detail.get(TotalUtils.JOIN_FIELD_KEY);
			Object detailKeyObj = joinKey.get(key);
			if (detailKeyObj instanceof AbstractTotalItem) {
				// long detailKey = ((Long)detailKeyObj).longValue();
				AbstractTotalItem detailKey = (AbstractTotalItem) detailKeyObj;
				int i = 0;
				int sameRowIndex = 0;
				for (int size = mergeTableValueList.size(); i < size; i++) {
					TotalJoinInfo totalJoinInfo = (TotalJoinInfo) mergeTableValueList
							.get(i);

					if (detailKey.equals(totalJoinInfo.key)) {
						sameRowIndex = i;
						if (!totalJoinInfo.isUsed) { // 找到了对应的项目，update汇总分录
							int rowIndex = totalJoinInfo.index;
							updateMergeTable(rowIndex, detail, lockCell);
							totalJoinInfo.isUsed = true;
							break;
						}
					}
				}
				if (i == mergeTableValueList.size()) { // 如果所有的汇总分录项目都 isUse==
														// false，则需要复制分录
					IRow newRow = mergeTable.addRow(mergeTable.getRowCount(),
							(IRow) mergeTable.getRow(sameRowIndex).clone());
					Object rowObj = ((PropertyContainer) mergeTable.getRow(
							sameRowIndex).getUserObject()).clone();
					if (rowObj instanceof AbstractCoreBaseInfo) {
						((AbstractCoreBaseInfo) rowObj).setId(null);
						if (rowObj instanceof IObjectValueAgent) {
							((IObjectValueAgent) rowObj)
									.setAgentState(AgentState.NEW);
						}

					}
					String idColumnName = getMergeTableIDColumnName();
					if (newRow.getCell(idColumnName) != null) {
						newRow.getCell(idColumnName).setValue(null);
					}
					newRow.setUserObject(rowObj);
					updateMergeTable(mergeTable.getRowCount() - 1, detail,
							lockCell);
				}
			}
		}

		// 5. 清理汇总分录
		int i = 0;
		Hashtable hash = new Hashtable();
		ArrayList deleteList = new ArrayList();
		// Collections.sort()
		for (int size = mergeTableValueList.size(); i < size; i++) {
			TotalJoinInfo totalJoinInfo = (TotalJoinInfo) mergeTableValueList
					.get(i);
			if (totalJoinInfo.isUsed) {
				hash.put(totalJoinInfo.key, "Nothing");
			} else {
				// TODO
				// 此处的条件有点奇怪，请王志伟分析一下
				// if(hash.get(totalJoinInfo.key) == null){
				// hash.put(totalJoinInfo.key, "Nothing");
				// }else{
				deleteList.add(new Integer(totalJoinInfo.index)); // 5.1
																	// 首先找到需要删除的行
				// }
			}
		}
		Collections.sort(deleteList, Collections.reverseOrder());// 5.2
																	// 按由大到小的顺序排序

		// 5.3 删除多余的分录
		for (int k = 0; k < deleteList.size(); k++) {
			// 判断是否botp推过来的行:如果不是botp生成的，或者是botp生成，但是在所给序列号之前，存在相同的源单分录id，则可以删除
			if (StringUtils.isEmpty((String) botpField
					.get(((Integer) deleteList.get(k)).intValue()))
					|| hasRepeatBotpFields(((Integer) deleteList.get(k))
							.intValue())) {
				BeforeActionListener listener = mergeTable.getBeforeAction();
				mergeTable.setBeforeAction(null);
				try {
					((IObjectCollection) mergeTable.getUserObject())
							.removeObject((IObjectValue) mergeTable.getRow(
									((Integer) deleteList.get(k)).intValue())
									.getUserObject());
				} catch (Exception e) {
					e.printStackTrace();
				}
				mergeTable.removeRow(((Integer) deleteList.get(k)).intValue());
				mergeTable.setBeforeAction(listener);
				//mergeTable.removeRow(((Integer)deleteList.get(k)).intValue());
			}
		}

		// dataBinder.bindTableToData(mergeTable,
		// (IObjectCollection)mergeTable.getUserObject());

		return true;
	}

	private boolean hasRepeatBotpFields(int index) {
		if (botpField == null) {
			return false;
		} else {
			Object o = botpField.get(index);
			if (o instanceof String) {
				String targetString = (String) o;
				int maxSize = index - 1;
				for (int i = 0; i <= maxSize; i++) {
					if (targetString.equals(botpField.get(i))) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private String getMergeTableIDColumnName() {
		String s = null;
		try {
			s = dataBinder.getDataComponentMap().getDetail(mergeTable)
					.toString()
					+ ".id";
			s = dataBinder.getDataComponentMap().getComponentProperty(s)
					.getPropertyName();
		} catch (Exception e) {

		}

		return s;
	}

	// 数据校验
	private boolean checkData() {
		// 校验“汇总分录余明细分录进行对应的依据”
		if (joinFields == null || joinFields.length != 2) {
			return false;
		} else {
			if (joinFields[0] == null || joinFields[1] == null) {
				return false;
			} else {
				if (joinFields[0].length != joinFields[1].length) {
					return false;
				} else {
					for (int i = 0; i < joinFields[0].length; i++) {
						if (StringUtils.isEmpty(joinFields[0][i])
								|| StringUtils.isEmpty(joinFields[1][i])) {
							return false;
						}
					}
				}
			}
		}
		// 校验“汇总分录与明细分录汇总依据”
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
		// 校验“汇总分录与明细分录需要汇总的字段”
		if (calcFields == null || calcFields.length != 2) {
			return false;
		} else {
			if (calcFields[0] == null || calcFields[1] == null) {
				return false;
			} else {
				if (calcFields[0].length != calcFields[1].length) {
					return false;
				} else {
					for (int i = 0; i < calcFields[0].length; i++) {
						if (StringUtils.isEmpty(calcFields[0][i])
								|| StringUtils.isEmpty(calcFields[1][i])) {
							return false;
						}
					}
				}
			}
		}
		// 校验“汇总分录”
		if (mergeTable == null) {
			return false;
		} else {
			lable1: for (int i = 0, size = mergeTable.getRowCount(); i < size; i++) {
				// for(int j = 0; j < joinFields[0].length; j++){
				// 由于熔炼号可以为空，这里只检查第一个字段（物料字段）
				if (joinFields[0].length > 0) {
					Object val = KDTableUtils.getFieldValue(mergeTable, i,
							joinFields[0][0]);
					if (val == null) {
						BeforeActionListener beforeAction = mergeTable
								.getBeforeAction();
						mergeTable.setBeforeAction(null);
						mergeTable.removeRow(i); // 如果物料为空，则认为是空白行，删除
						mergeTable.setBeforeAction(beforeAction);
						size--;
						i--;
						continue lable1;
					}
				}
			}

			if (mergeTable.getRowCount() <= 0) { // 如果在删除完空白行后，汇总分录的行数为0，则退出
				return false;
			}
		}
		// 校验botp字段
		if (botpField == null || botpField.size() != mergeTable.getRowCount()) {
			return false;
		}
		// 校验明细分录
		if (detailTable == null) {
			return false;
		} else {
			lable2: for (int i = 0, size = detailTable.getRowCount(); i < size; i++) {
				// for(int j = 0; j < joinFields[1].length; j++){
				// 由于熔炼号可以为空，这里只检查第一个字段（物料字段）
				if (joinFields[0].length > 0) {
					Object val = KDTableUtils.getFieldValue(detailTable, i,
							joinFields[1][0]);
					if (val == null) {
						BeforeActionListener afterAction = detailTable
								.getAfterAction();
						detailTable.setAfterAction(null);
						detailTable.removeRow(i); // 如果物料为空，则认为是空白行，删除
						detailTable.setAfterAction(afterAction);
						size--;
						i--;
						continue lable2;
					}
				}
			}

			// if(detailTable.getRowCount() <= 0){ // 如果在删除完空白行后，汇总分录的行数为0，则退出
			// return false;
			// }
		}
		return true;
	}

	private List getDetailData() {
		String[] allDetailFields = mergeString(analyseFields[1], calcFields[1]);
		List detailValueList = KDTableUtils.getMapFieldValues(detailTable,
				allDetailFields);
		return detailValueList;
	}

	private String[] mergeString(String[] s1, String[] s2) {
		if (s1 == null && s2 == null) {
			return new String[0];
		}
		if (s1 == null) {
			return s2;
		}
		if (s2 == null) {
			return s1;
		}
		String[] s = new String[s1.length + s2.length];
		for (int i = 0; i < s1.length; i++) {
			s[i] = s1[i];
		}
		for (int i = 0; i < s2.length; i++) {
			s[s1.length + i] = s2[i];
		}
		return s;
	}

	private void updateMergeTable(int rowIndex, Map detail, boolean lockCell) {
		for (int i = 0, size = analyseFields[0].length; i < size; i++) {
			KDTableUtils.setFieldValue(mergeTable, rowIndex,
					analyseFields[0][i], detail.get(analyseFields[1][i]));
			// 把汇总依据字段锁定:这些单据都不能直接新增，在botp关联生成之后，这些字段都要锁定
			KDTableUtils.setFreezeCell(mergeTable, rowIndex,
					analyseFields[0][i], lockCell);
		}
		for (int i = 0, size = calcFields[0].length; i < size; i++) {
			KDTableUtils.setFieldValue(mergeTable, rowIndex, calcFields[0][i],
					detail.get(calcFields[1][i]));
			KDTableUtils.setFreezeCell(mergeTable, rowIndex, calcFields[0][i],
					lockCell);
		}
	}

	class TotalJoinInfo {
		AbstractTotalItem key = null;
		int index = -1;
		boolean isUsed = false;

		public TotalJoinInfo(AbstractTotalItem key, int index) {
			this.key = key;
			this.index = index;
		}
	}
}
