/*
 * @(#)LadingRightRecord.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:
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
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2008-7-18
	 *              <p>
	 */
	public QtyRightRecord() {
		super();
	}

	/**
	 * ���������캯��
	 * 
	 * @param rs
	 * @author:daij ����ʱ�䣺2008-7-18
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
	 * ���������캯��
	 * 
	 * @param pk
	 * @param right
	 * @param rightTotal
	 * @param groupId
	 * @param total
	 * @author:daij ����ʱ�䣺2008-7-18
	 *              <p>
	 */
	public QtyRightRecord(String pk, BigDecimal right, BigDecimal rightTotal,
			String groupId, BigDecimal total, int scale) {
		super(pk, right, rightTotal, groupId, total, scale);

	}

	/**
	 * 
	 * ��������������
	 * 
	 * @param baseQty
	 *            ��������.
	 * @return BigDecimal
	 * @author:daij ����ʱ�䣺2008-7-19
	 *              <p>
	 */
	public BigDecimal calculateQty(BigDecimal baseQty) {
		return QtyMultiMeasureUtils.baseQtyTo(NumericUtils
				.effectualNumeric(baseQty), getBaseConvsRate(),
				getQtyPrecision());
	}

	/**
	 * 
	 * ���������������ľ���.
	 * 
	 * @author:daij
	 * @throws SQLDataException
	 * @see com.kingdee.eas.st.common.app.RightRecord#getPrecision()
	 */
	protected int getBaseQtyPrecision() throws SQLDataException {
		return this.baseQtyPrecision;
	}

	/**
	 * ����:@return ���� baseConvsRate��
	 */
	public BigDecimal getBaseConvsRate() {
		return baseConvsRate;
	}

	/**
	 * ����:@return ���� qtyPrecision��
	 */
	public int getQtyPrecision() {
		return qtyPrecision;
	}
}
