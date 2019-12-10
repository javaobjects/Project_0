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
 * ����: �򻯰汾��ֻ�������������λ�����������λ
 * 
 * @author daij date:2008-7-18
 *         <p>
 * @version EAS5.4
 */
public class SimpleQtyRightRecord extends QtyRightRecord {

	private int baseQtyPrecision = STConstant.PRECISION_DEFAULT_NUMBER;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2008-7-18
	 *              <p>
	 */
	public SimpleQtyRightRecord() {
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
	public SimpleQtyRightRecord(IRowSet rs) throws SQLDataException {
		super();
		this.rs = rs;

		if (STUtils.isNotNull(rs)) {
			try {
				baseQtyPrecision = NumericUtils.effectualPrecision(rs
						.getInt("FBaseQtyPrecision"),
						STConstant.PRECISION_DEFAULT_NUMBER);

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
	public SimpleQtyRightRecord(String pk, BigDecimal right,
			BigDecimal rightTotal, String groupId, BigDecimal total, int scale) {
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
				.effectualNumeric(baseQty), getBaseConvsRate(), getScale());
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
}
