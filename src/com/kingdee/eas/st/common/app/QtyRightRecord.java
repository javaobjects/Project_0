/*
 * @(#)LadingRightRecord.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.basedata.mm.qm.utils.NumericUtils;
import com.kingdee.eas.basedata.mm.qm.utils.QtyMultiMeasureUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:
 * 
 * @author daij date:2008-7-18
 *         <p>
 * @version EAS5.4
 */
public class QtyRightRecord extends RightRecord {

	private int baseQtyPrecision = STConstant.PRECISION_DEFAULT_NUMBER;

	private int qtyPrecision = STConstant.PRECISION_DEFAULT_NUMBER;

	private BigDecimal baseConvsRate = STConstant.BIGDECIMAL_ONE;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 */
	public QtyRightRecord() {
		super();
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param rs
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 * @throws SQLDataException
	 */
	public QtyRightRecord(IRowSet rs) throws SQLDataException {
		super(rs);

		if (STUtils.isNotNull(rs)) {
			try {
				baseQtyPrecision = NumericUtils.effectualPrecision(rs
						.getInt("FBaseQtyPrecision"),
						STConstant.PRECISION_DEFAULT_NUMBER);

				qtyPrecision = NumericUtils.effectualPrecision(rs
						.getInt("FQtyPrecision"),
						STConstant.PRECISION_DEFAULT_NUMBER);

				baseConvsRate = NumericUtils.effectualExchangeRate(rs
						.getBigDecimal("FBaseConvsRate"));

			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param pk
	 * @param right
	 * @param rightTotal
	 * @param groupId
	 * @param total
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 */
	public QtyRightRecord(String pk, BigDecimal right, BigDecimal rightTotal,
			String groupId, BigDecimal total, int scale) {
		super(pk, right, rightTotal, groupId, total, scale);

	}

	/**
	 * 
	 * 描述：计算数量
	 * 
	 * @param baseQty
	 *            基本数量.
	 * @return BigDecimal
	 * @author:daij 创建时间：2008-7-19
	 *              <p>
	 */
	public BigDecimal calculateQty(BigDecimal baseQty) {
		return QtyMultiMeasureUtils.baseQtyTo(NumericUtils
				.effectualNumeric(baseQty), getBaseConvsRate(),
				getQtyPrecision());
	}

	/**
	 * 
	 * 描述：基本数量的精度.
	 * 
	 * @author:daij
	 * @throws SQLDataException
	 * @see com.kingdee.eas.st.common.app.RightRecord#getPrecision()
	 */
	protected int getBaseQtyPrecision() throws SQLDataException {
		return this.baseQtyPrecision;
	}

	/**
	 * 描述:@return 返回 baseConvsRate。
	 */
	public BigDecimal getBaseConvsRate() {
		return baseConvsRate;
	}

	/**
	 * 描述:@return 返回 qtyPrecision。
	 */
	public int getQtyPrecision() {
		return qtyPrecision;
	}
}
