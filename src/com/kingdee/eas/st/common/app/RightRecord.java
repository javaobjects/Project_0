/*
 * @(#)RightRecord.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app;

import java.math.BigDecimal;

import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.basedata.mm.qm.utils.NumericUtils;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:
 * 
 * @author daij date:2008-7-18
 *         <p>
 * @version EAS5.4
 */
public abstract class RightRecord {

	// 分组Id
	private String pk = null;
	// 权重
	private BigDecimal right = SysConstant.BIGZERO;
	// 总权重
	private BigDecimal rightTotal = SysConstant.BIGZERO;
	// 分组Id
	private String groupId = null;
	// 待分摊值.
	private BigDecimal total = SysConstant.BIGZERO;
	// 分摊值
	private BigDecimal proportion = SysConstant.BIGZERO;
	// 分摊的精度 - 分摊结果值的精度统一采用待分摊总值的精度.
	private int scale = STConstant.PRECISION_DEFAULT_NUMBER;

	/**
	 * 当前包裹的数据记录.
	 */
	protected IRowSet rs = null;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2008-7-16
	 *              <p>
	 */
	public RightRecord() {
		super();
	}

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2008-7-16
	 *              <p>
	 */
	public RightRecord(IRowSet rs) {
		super();
		this.rs = rs;
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param right
	 * @param rightTotal
	 * @param groupId
	 * @param proportioned
	 * @param scale
	 * @author:daij 创建时间：2008-7-16
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
	 * 描述：最后一笔记录倒减
	 * 
	 * @param proportioned
	 *            已分摊值.
	 * @author:daij 创建时间：2008-7-18
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
	 * 描述：按权重进行分摊 公式：分摊重量 = 待分摊重量 * 权重重量/∑(权重重量)
	 * 
	 * @author:daij 创建时间：2008-7-16
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
	 * 描述：获取分摊值得精度.
	 * 
	 * @return int 有效的精度值.
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 * @throws SQLDataException
	 */
	public final int getScale() {
		return (this.scale > 0) ? this.scale
				: STConstant.PRECISION_DEFAULT_NUMBER;
	}

	// 是否需要进行分摊
	private boolean isNeedProportion(BigDecimal weighQty) {
		return !NumericUtils.equalsZero(weighQty);
	}

	/**
	 * 描述:@return 返回 groupId。
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * 描述:设置groupId的值。
	 * 
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * 描述:@return 返回 proportioned。
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * 描述:设置proportioned的值。
	 * 
	 * @param proportioned
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * 描述:@return 返回 right。
	 */
	public BigDecimal getRight() {
		return right;
	}

	/**
	 * 描述:设置right的值。
	 * 
	 * @param right
	 */
	public void setRight(BigDecimal right) {
		this.right = right;
	}

	/**
	 * 描述:@return 返回 rightTotal。
	 */
	public BigDecimal getRightTotal() {
		return rightTotal;
	}

	/**
	 * 描述:设置rightTotal的值。
	 * 
	 * @param rightTotal
	 */
	public void setRightTotalQty(BigDecimal rightTotal) {
		this.rightTotal = rightTotal;
	}

	/**
	 * 描述:@return 返回 proportion。
	 */
	public BigDecimal getProportion() {
		return proportion;
	}

	/**
	 * 描述:@return 返回 pk。
	 */
	public String getPk() {
		return pk;
	}

	/**
	 * 描述:设置pk的值。
	 * 
	 * @param pk
	 */
	public void setPk(String pk) {
		this.pk = pk;
	}

	/**
	 * 描述:设置scale的值。
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
}
