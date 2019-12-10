/*
 * @(#)AuditUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
			// 单据未提交不能审核
			throw new STBillException(STBillException.EXC_BILL_NOT_SUBMIT);
		}

		// TODO 检查单据以后可以对单据审核时增加

	}

	public static void checkUnAuditDetail(STBillBaseInfo info) throws Exception {

		// TODO 检查单据以后可以对单据审核时增加

		// 对下游单据判断迁移到钢铁基类，因为部分单据反审核不需要检查下游单据

	}

	/**
	 * 检查是否有生成下游单据了
	 */
	protected static boolean hasDestBill(String srcId) throws BOSException {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();

		// 单据ID过滤
		filterInfo.getFilterItems().add(
				new FilterItemInfo("srcObjectID", srcId, CompareType.EQUALS));
		viewInfo.setFilter(filterInfo);

		BOTRelationCollection collection = BOTRelationFactory
				.getRemoteInstance().getCollection(viewInfo);
		if (collection != null && collection.size() > 0) {
			// 有下游单据
			return true;
		}

		// 没有下游单据
		return false;
	}
}
