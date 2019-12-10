/*
 * @(#)RightRecord.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app;

import java.math.BigDecimal;

import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.basedata.mm.qm.utils.NumericUtils;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:
 * 
 * @author daij date:2008-7-18
 *         <p>
 * @version EAS5.4
 */
public abstract class RightRecord {

	// ����Id
	private String pk = null;
	// Ȩ��
	private BigDecimal right = SysConstant.BIGZERO;
	// ��Ȩ��
	private BigDecimal rightTotal = SysConstant.BIGZERO;
	// ����Id
	private String groupId = null;
	// ����ֵ̯.
	private BigDecimal total = SysConstant.BIGZERO;
	// ��ֵ̯
	private BigDecimal proportion = SysConstant.BIGZERO;
	// ��̯�ľ��� - ��̯���ֵ�ľ���ͳһ���ô���̯��ֵ�ľ���.
	private int scale = STConstant.PRECISION_DEFAULT_NUMBER;

	/**
	 * ��ǰ���������ݼ�¼.
	 */
	protected IRowSet rs = null;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2008-7-16
	 *              <p>
	 */
	public RightRecord() {
		super();
	}

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2008-7-16
	 *              <p>
	 */
	public RightRecord(IRowSet rs) {
		super();
		this.rs = rs;
	}

	/**
	 * ���������캯��
	 * 
	 * @param right
	 * @param rightTotal
	 * @param groupId
	 * @param proportioned
	 * @param scale
	 * @author:daij ����ʱ�䣺2008-7-16
	 *              <p>
	 */
	public RightRecord(String pk, BigDecimal right, BigDecimal rightTotal,
			String groupId, BigDecimal total, int scale) {

		super();
		this.pk = pk;
		this.right = right;
		this.rightTotal = rightTotal;
		this.groupId = groupId;
		this.total = total;
		this.scale = scale;
	}

	/**
	 * 
	 * ���������һ�ʼ�¼����
	 * 
	 * @param proportioned
	 *            �ѷ�ֵ̯.
	 * @author:daij ����ʱ�䣺2008-7-18
	 *              <p>
	 */
	public void proportionByMirror(BigDecimal proportioned) {
		if (isNeedProportion(getTotal())) {
			proportion = NumericUtils.effectualNumeric(getTotal()).subtract(
					NumericUtils.effectualNumeric(proportioned));
		}
	}

	/**
	 * 
	 * ��������Ȩ�ؽ��з�̯ ��ʽ����̯���� = ����̯���� * Ȩ������/��(Ȩ������)
	 * 
	 * @author:daij ����ʱ�䣺2008-7-16
	 *              <p>
	 * @throws SQLDataException
	 */
	public void proportionByRight() throws SQLDataException {
		if (isNeedProportion(getTotal())) {
			if (!NumericUtils.equalsZero(getRightTotal())) {
				proportion = NumericUtils
						.effectualNumeric(getTotal())
						.multiply(
								NumericUtils
										.effectualNumeric(right)
										.divide(
												NumericUtils
														.effectualNumeric(getRightTotal()),
												STConstant.PRECISION_DEFAUL_DB,
												BigDecimal.ROUND_HALF_UP))
						.setScale(NumericUtils.effectualPrecision(getScale()),
								BigDecimal.ROUND_HALF_UP);
			}
		}
	}

	/**
	 * 
	 * ��������ȡ��ֵ̯�þ���.
	 * 
	 * @return int ��Ч�ľ���ֵ.
	 * @author:daij ����ʱ�䣺2008-7-18
	 *              <p>
	 * @throws SQLDataException
	 */
	public final int getScale() {
		return (this.scale > 0) ? this.scale
				: STConstant.PRECISION_DEFAULT_NUMBER;
	}

	// �Ƿ���Ҫ���з�̯
	private boolean isNeedProportion(BigDecimal weighQty) {
		return !NumericUtils.equalsZero(weighQty);
	}

	/**
	 * ����:@return ���� groupId��
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * ����:����groupId��ֵ��
	 * 
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * ����:@return ���� proportioned��
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * ����:����proportioned��ֵ��
	 * 
	 * @param proportioned
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * ����:@return ���� right��
	 */
	public BigDecimal getRight() {
		return right;
	}

	/**
	 * ����:����right��ֵ��
	 * 
	 * @param right
	 */
	public void setRight(BigDecimal right) {
		this.right = right;
	}

	/**
	 * ����:@return ���� rightTotal��
	 */
	public BigDecimal getRightTotal() {
		return rightTotal;
	}

	/**
	 * ����:����rightTotal��ֵ��
	 * 
	 * @param rightTotal
	 */
	public void setRightTotalQty(BigDecimal rightTotal) {
		this.rightTotal = rightTotal;
	}

	/**
	 * ����:@return ���� proportion��
	 */
	public BigDecimal getProportion() {
		return proportion;
	}

	/**
	 * ����:@return ���� pk��
	 */
	public String getPk() {
		return pk;
	}

	/**
	 * ����:����pk��ֵ��
	 * 
	 * @param pk
	 */
	public void setPk(String pk) {
		this.pk = pk;
	}

	/**
	 * ����:����scale��ֵ��
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
}
