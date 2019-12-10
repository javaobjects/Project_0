package com.kingdee.eas.st.common.app.botp;

import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.btp.BTPException;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;

public class AutoBOTP {

	/**
	 * 特别注意，botp应该设置为保存单据，不显示界面
	 * 
	 * @throws BOSException
	 * @throws BTPException
	 */

	/**
	 * @param ctx
	 * @param botMappingID
	 *            BOTP ID
	 * @param srcBillIDs
	 *            来源单据ID数组
	 * @param entriesNames
	 *            分录字段名，如果整单下推没有影响
	 * @param entriesKeys
	 *            分录ID
	 * @param srcBillBosType
	 *            来源单据BOStype
	 * @param destBillBosType
	 *            目标单据BOSTYPE
	 * @param botpSelectors
	 *            BOTP过滤
	 * @return BTPTransformResult
	 * @throws BTPException
	 * @throws BOSException
	 */
	public static BTPTransformResult autoBOTP(Context ctx,
			IObjectPK botMappingID, String[] srcBillIDs, String[] entriesNames,
			List entriesKeys, String srcBillBosType, String destBillBosType,
			SelectorItemCollection botpSelectors) throws BTPException,
			BOSException {

		// 自动进行BOTP转换
		BTPTransformResult result = BTPManagerFactory.getLocalInstance(ctx)
				.transformForBotp(srcBillIDs, entriesNames, entriesKeys,
						botpSelectors, destBillBosType, botMappingID,
						srcBillBosType);

		return result;
		// 获取转换结果ID列表
		/*
		 * Iterator it = result.getBills().iterator();
		 * 
		 * ArrayList allocationList = new ArrayList();
		 * 
		 * while(it.hasNext()){ //循环审核自动生成的单据ID
		 * 
		 * 
		 * }
		 */
	}

}