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
	private ArrayList botpField; // botp�ֶΣ��ڻ��ܺ�ɾ����¼ʱ�����ݴ��ֶΣ������Ƿ����ɾ��

	/**
	 * ���ܹ��� zhiwei_wang 2008-3-21
	 * 
	 * @param mergeTable
	 *            �����ܷ�¼
	 * @param detailTable
	 *            ����ϸ��¼
	 * @param joinFields
	 *            �����ܷ�¼����ϸ��¼���ж�Ӧ������
	 * @param analyseFields
	 *            �����ܷ�¼����ϸ��¼�������ݣ����ֶ�һ����һ������N�еĶ�ά���飬��һ��Ϊ���ܷ�¼�е��ֶ����ƣ��ڶ���Ϊ��ϸ��¼�е��ֶ���
	 * @param calcFields
	 *            �����ܷ�¼����ϸ��¼��Ҫ���ܵ��ֶΣ����ֶ�һ����һ������N�еĶ�ά���飬��һ��Ϊ���ܷ�¼�е��ֶ����ƣ��ڶ���Ϊ��ϸ��¼�е��ֶ���
	 * @param botpField
	 *            ,
	 *            botp�ֶΣ��ڻ��ܺ�ɾ����¼ʱ�����ݴ��ֶΣ������Ƿ����ɾ������Сһ��Ҫ����mergeTable���������������botp�ƹ�������
	 *            �����ӦֵΪ���ַ���("")
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
	 * ���л��� Ĭ�������������ɵ��������ڵĵ�Ԫ��
	 * 
	 * @return zhiwei_wang 2008-3-21
	 */
	public boolean total() {
		return total(true);
	}

	/**
	 * ���л���
	 * 
	 * @param boolean lockCell �Ƿ������������ɵ��������ڵĵ�Ԫ��
	 * @return zhiwei_wang 2008-3-21
	 */
	public boolean total(boolean lockCell) {
		// 0. У����������
		if (!checkData()) {
			return false;
		}
		// 1. ץȡ��ϸ��¼����
		List detailValueList = getDetailData();

		// 2. ������ϸ����
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
			// //���������������ֶαȽ����⣬��Ϊ���ֶ�����ϸ��¼��û�г��֣�������Ҫ����ܣ�ֻ�������ﴦ��
			try {
				caltulater.groupBy(detailInfo);
			} catch (Exception e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
		}

		// caltulater.iterator();
		Map total = caltulater.getTotal();
		Map joinKey = caltulater.getJoinKey();
		// 3. ץȡ���ܷ�¼����
		ArrayList mergeTableValueList = new ArrayList(mergeTable.getRowCount());
		for (int i = 0, size = mergeTable.getRowCount(); i < size; i++) {
			Map mergeValues = KDTableUtils.getMapFieldValues(mergeTable, i,
					joinFields[0]);
			AbstractTotalItem joinKey_TotalItem = TotalItem.instance(
					joinFields[0], mergeValues);

			mergeTableValueList.add(i, new TotalJoinInfo(joinKey_TotalItem, i));
		}

		// 4. �ѻ������ݺϲ������ܷ�¼
		// 4.1 ����������ܷ�¼�ϵ��ֶΣ���ɾ���У���ͬʱ��ǰ���������ֶν����������У�
		// 4.1 ��������ܷ�¼�ϵ��ֶΣ�ֻ�ǽ���
		for (int rowIndex = 0, rowCount = mergeTable.getRowCount(); rowIndex < rowCount; rowIndex++) {
			// �ѻ��������ֶ�����:��Щ���ݶ�����ֱ����������botp��������֮����Щ�ֶζ�Ҫ����
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
		// 4.2 �ϲ�����
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
						if (!totalJoinInfo.isUsed) { // �ҵ��˶�Ӧ����Ŀ��update���ܷ�¼
							int rowIndex = totalJoinInfo.index;
							updateMergeTable(rowIndex, detail, lockCell);
							totalJoinInfo.isUsed = true;
							break;
						}
					}
				}
				if (i == mergeTableValueList.size()) { // ������еĻ��ܷ�¼��Ŀ�� isUse==
														// false������Ҫ���Ʒ�¼
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

		// 5. ������ܷ�¼
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
				// �˴��������е���֣�����־ΰ����һ��
				// if(hash.get(totalJoinInfo.key) == null){
				// hash.put(totalJoinInfo.key, "Nothing");
				// }else{
				deleteList.add(new Integer(totalJoinInfo.index)); // 5.1
																	// �����ҵ���Ҫɾ������
				// }
			}
		}
		Collections.sort(deleteList, Collections.reverseOrder());// 5.2
																	// ���ɴ�С��˳������

		// 5.3 ɾ������ķ�¼
		for (int k = 0; k < deleteList.size(); k++) {
			// �ж��Ƿ�botp�ƹ�������:�������botp���ɵģ�������botp���ɣ��������������к�֮ǰ��������ͬ��Դ����¼id�������ɾ��
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

	// ����У��
	private boolean checkData() {
		// У�顰���ܷ�¼����ϸ��¼���ж�Ӧ�����ݡ�
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
		// У�顰���ܷ�¼����ϸ��¼�������ݡ�
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
		// У�顰���ܷ�¼����ϸ��¼��Ҫ���ܵ��ֶΡ�
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
		// У�顰���ܷ�¼��
		if (mergeTable == null) {
			return false;
		} else {
			lable1: for (int i = 0, size = mergeTable.getRowCount(); i < size; i++) {
				// for(int j = 0; j < joinFields[0].length; j++){
				// ���������ſ���Ϊ�գ�����ֻ����һ���ֶΣ������ֶΣ�
				if (joinFields[0].length > 0) {
					Object val = KDTableUtils.getFieldValue(mergeTable, i,
							joinFields[0][0]);
					if (val == null) {
						BeforeActionListener beforeAction = mergeTable
								.getBeforeAction();
						mergeTable.setBeforeAction(null);
						mergeTable.removeRow(i); // �������Ϊ�գ�����Ϊ�ǿհ��У�ɾ��
						mergeTable.setBeforeAction(beforeAction);
						size--;
						i--;
						continue lable1;
					}
				}
			}

			if (mergeTable.getRowCount() <= 0) { // �����ɾ����հ��к󣬻��ܷ�¼������Ϊ0�����˳�
				return false;
			}
		}
		// У��botp�ֶ�
		if (botpField == null || botpField.size() != mergeTable.getRowCount()) {
			return false;
		}
		// У����ϸ��¼
		if (detailTable == null) {
			return false;
		} else {
			lable2: for (int i = 0, size = detailTable.getRowCount(); i < size; i++) {
				// for(int j = 0; j < joinFields[1].length; j++){
				// ���������ſ���Ϊ�գ�����ֻ����һ���ֶΣ������ֶΣ�
				if (joinFields[0].length > 0) {
					Object val = KDTableUtils.getFieldValue(detailTable, i,
							joinFields[1][0]);
					if (val == null) {
						BeforeActionListener afterAction = detailTable
								.getAfterAction();
						detailTable.setAfterAction(null);
						detailTable.removeRow(i); // �������Ϊ�գ�����Ϊ�ǿհ��У�ɾ��
						detailTable.setAfterAction(afterAction);
						size--;
						i--;
						continue lable2;
					}
				}
			}

			// if(detailTable.getRowCount() <= 0){ // �����ɾ����հ��к󣬻��ܷ�¼������Ϊ0�����˳�
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
			// �ѻ��������ֶ�����:��Щ���ݶ�����ֱ����������botp��������֮����Щ�ֶζ�Ҫ����
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
