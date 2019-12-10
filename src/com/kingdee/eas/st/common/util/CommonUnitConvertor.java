package com.kingdee.eas.st.common.util;

import java.math.BigDecimal;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.common.EASBizException;

/**
 * ��ͨ������λת�����������϶������λ��
 * 
 * @author miller_xiao
 * 
 */
public class CommonUnitConvertor {
	/**
	 * ���ܣ���֪ ԭ��λ��ԭ������ת��ΪĿ�굥λ������ �㷨��Ŀ�� = ��ԭ���� * ԭ��λ������ ��/ Ŀ�굥λ������
	 * 
	 * @param srcUnit
	 *            ԭ��λ
	 * @param srcAmount
	 *            ԭ����
	 * @param tgtUnit
	 *            Ŀ�굥λ
	 * @return Ŀ������
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static BigDecimal convertTo(MeasureUnitInfo srcUnit,
			BigDecimal srcAmount, MeasureUnitInfo tgtUnit)
			throws EASBizException, BOSException {
		if (srcUnit == null || srcAmount == null || tgtUnit == null)
			return null;

		// ���ȱ�ٱ�Ҫ����Ϣ�������»�ȡ
		if (srcUnit.getCoefficient() == null
				|| srcUnit.getMeasureUnitGroup() == null) {
			srcUnit = MeasureUnitFactory.getRemoteInstance()
					.getMeasureUnitInfo(
							new ObjectStringPK(srcUnit.getId().toString()));
		}

		// ���ȱ�ٱ�Ҫ����Ϣ�������»�ȡ
		if (tgtUnit.getCoefficient() == null
				|| tgtUnit.getMeasureUnitGroup() == null) {
			tgtUnit = MeasureUnitFactory.getRemoteInstance()
					.getMeasureUnitInfo(
							new ObjectStringPK(tgtUnit.getId().toString()));
		}

		// �������������λ����ͬ��
		if (STUtils.isDiffrent(srcUnit.getMeasureUnitGroup(), tgtUnit
				.getMeasureUnitGroup()))
			return null;

		// ��ʼ����
		BigDecimal result = srcAmount.multiply(srcUnit.getCoefficient());
		result = result.divide(tgtUnit.getCoefficient(),
				BigDecimal.ROUND_HALF_UP, tgtUnit.getQtyPrecision());

		return result;
	}
}
