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
	 * �ر�ע�⣬botpӦ������Ϊ���浥�ݣ�����ʾ����
	 * 
	 * @throws BOSException
	 * @throws BTPException
	 */

	/**
	 * @param ctx
	 * @param botMappingID
	 *            BOTP ID
	 * @param srcBillIDs
	 *            ��Դ����ID����
	 * @param entriesNames
	 *            ��¼�ֶ����������������û��Ӱ��
	 * @param entriesKeys
	 *            ��¼ID
	 * @param srcBillBosType
	 *            ��Դ����BOStype
	 * @param destBillBosType
	 *            Ŀ�굥��BOSTYPE
	 * @param botpSelectors
	 *            BOTP����
	 * @return BTPTransformResult
	 * @throws BTPException
	 * @throws BOSException
	 */
	public static BTPTransformResult autoBOTP(Context ctx,
			IObjectPK botMappingID, String[] srcBillIDs, String[] entriesNames,
			List entriesKeys, String srcBillBosType, String destBillBosType,
			SelectorItemCollection botpSelectors) throws BTPException,
			BOSException {

		// �Զ�����BOTPת��
		BTPTransformResult result = BTPManagerFactory.getLocalInstance(ctx)
				.transformForBotp(srcBillIDs, entriesNames, entriesKeys,
						botpSelectors, destBillBosType, botMappingID,
						srcBillBosType);

		return result;
		// ��ȡת�����ID�б�
		/*
		 * Iterator it = result.getBills().iterator();
		 * 
		 * ArrayList allocationList = new ArrayList();
		 * 
		 * while(it.hasNext()){ //ѭ������Զ����ɵĵ���ID
		 * 
		 * 
		 * }
		 */
	}

}