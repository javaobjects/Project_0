/*
 * @(#)AuditUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.utils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.st.common.STBillException;

public class AuditUtils {

	public static void checkAuditDetail(STBillBaseInfo info) throws Exception {

		if (info == null || info.getId() == null
				|| !BillBaseStatusEnum.SUBMITED.equals(info.getBillStatus())) {
			// ����δ�ύ�������
			throw new STBillException(STBillException.EXC_BILL_NOT_SUBMIT);
		}

		// TODO ��鵥���Ժ���ԶԵ������ʱ����

	}

	public static void checkUnAuditDetail(STBillBaseInfo info) throws Exception {

		// TODO ��鵥���Ժ���ԶԵ������ʱ����

		// �����ε����ж�Ǩ�Ƶ��������࣬��Ϊ���ֵ��ݷ���˲���Ҫ������ε���

	}

	/**
	 * ����Ƿ����������ε�����
	 */
	protected static boolean hasDestBill(String srcId) throws BOSException {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();

		// ����ID����
		filterInfo.getFilterItems().add(
				new FilterItemInfo("srcObjectID", srcId, CompareType.EQUALS));
		viewInfo.setFilter(filterInfo);

		BOTRelationCollection collection = BOTRelationFactory
				.getRemoteInstance().getCollection(viewInfo);
		if (collection != null && collection.size() > 0) {
			// �����ε���
			return true;
		}

		// û�����ε���
		return false;
	}
}
