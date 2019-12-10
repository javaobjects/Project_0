package com.kingdee.eas.st.common.util;

import java.math.BigDecimal;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.common.EASBizException;

/**
 * 普通计量单位转换器（非物料多计量单位）
 * 
 * @author miller_xiao
 * 
 */
public class CommonUnitConvertor {
	/**
	 * 功能：已知 原单位，原数量，转换为目标单位的数量 算法：目标 = （原数量 * 原单位换算率 ）/ 目标单位换算率
	 * 
	 * @param srcUnit
	 *            原单位
	 * @param srcAmount
	 *            原数量
	 * @param tgtUnit
	 *            目标单位
	 * @return 目标数量
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static BigDecimal convertTo(MeasureUnitInfo srcUnit,
			BigDecimal srcAmount, MeasureUnitInfo tgtUnit)
			throws EASBizException, BOSException {
		if (srcUnit == null || srcAmount == null || tgtUnit == null)
			return null;

		// 如果缺少必要的信息，则重新获取
		if (srcUnit.getCoefficient() == null
				|| srcUnit.getMeasureUnitGroup() == null) {
			srcUnit = MeasureUnitFactory.getRemoteInstance()
					.getMeasureUnitInfo(
							new ObjectStringPK(srcUnit.getId().toString()));
		}

		// 如果缺少必要的信息，则重新获取
		if (tgtUnit.getCoefficient() == null
				|| tgtUnit.getMeasureUnitGroup() == null) {
			tgtUnit = MeasureUnitFactory.getRemoteInstance()
					.getMeasureUnitInfo(
							new ObjectStringPK(tgtUnit.getId().toString()));
		}

		// 如果两个计量单位不在同组
		if (STUtils.isDiffrent(srcUnit.getMeasureUnitGroup(), tgtUnit
				.getMeasureUnitGroup()))
			return null;

		// 开始计算
		BigDecimal result = srcAmount.multiply(srcUnit.getCoefficient());
		result = result.divide(tgtUnit.getCoefficient(),
				BigDecimal.ROUND_HALF_UP, tgtUnit.getQtyPrecision());

		return result;
	}
}
