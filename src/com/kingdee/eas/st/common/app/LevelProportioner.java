/*
 * @(#)LevelProportion.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.basedata.mm.qm.utils.NumericUtils;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.db.SQLUtils;

/**
 * 描述: 逐级分摊工具
 * 
 * @author daij date:2008-7-14
 *         <p>
 * @version EAS5.4
 */
public abstract class LevelProportioner {

	// 分摊计算
	public static void proportion(Context ctx, ProportionItem item)
			throws BOSException {
		ProportionItem currentItem = item;
		// 总的待分摊值必须不等于零
		if (isEffectiveItem(currentItem)
				&& isNeedProportion(currentItem.getTotal())) {
			// 一级业务对象表作为一批处理.
			while (isEffectiveItem(currentItem)) {
				// 处理每个级联分摊项`
				proportionByItem(ctx, currentItem);
				// 继续处理下一级分摊项
				currentItem = currentItem.subsequence;
			}
		}
	}

	private static void proportionByItem(Context ctx, ProportionItem item)
			throws BOSException {
		// 获取明细权重记录
		IRowSet rs = getDetailRightRs(ctx, item);
		if (STUtils.isNotNull(rs)) {
			Connection cn = null;
			PreparedStatement pStmt = null;
			try {
				int count = 0;
				// 已分摊值
				BigDecimal proportioned = SysConstant.BIGZERO;
				// 当前记录
				RightRecord current = null;
				// 前一记录
				RightRecord previous = null;

				cn = EJBFactory.getConnection(ctx);
				pStmt = cn.prepareStatement(item.proportionSQL());
				while (rs.next()) {
					if (count > 0) {
						previous = current;
					}
					current = item.rightRecordInstance(rs);
					// 对象PK
					current.setPk(rs.getString("FID"));
					// 分组Id
					current.setGroupId(rs.getString(item.getParentFieldName()));
					// 权重值
					current
							.setRight(rs
									.getBigDecimal(item.getRightFieldName()));
					// 总权重值
					current
							.setRightTotalQty(rs
									.getBigDecimal(ProportionItem.FIEDLNAME_RIGHTTOTAL));
					// 待分摊值.
					current
							.setTotal(rs
									.getBigDecimal(ProportionItem.FIEDLNAME_PROPORTIONED));

					if (isNextGroup(previous, current)) {
						// 表明是该分组第一条分录，进行倒算.
						if (STUtils.isNotNull(previous)) {
							// 扣减已分摊值.
							previous.proportionByMirror(proportioned);
							// 已分摊值归零.
							proportioned = SysConstant.BIGZERO;
							// 绑定分摊SQL参数值
							item.bindingProportionSQLParameter(pStmt, previous);
						}
					} else {
						// 表明不是该分组第一条分录，按权重分摊.
						if (STUtils.isNotNull(previous)) {
							// 按权重计算分摊值
							previous.proportionByRight();
							// 累计已分摊值
							proportioned = proportioned.add(previous
									.getProportion());
							// 绑定分摊SQL参数值
							item.bindingProportionSQLParameter(pStmt, previous);
						}
					}
					count++;
				}
				if (STUtils.isNotNull(current)) {
					// 扣减已分摊值.
					current.proportionByMirror(proportioned);
					// 已分摊值归零.
					proportioned = SysConstant.BIGZERO;
					// 绑定分摊SQL参数值
					item.bindingProportionSQLParameter(pStmt, current);
				}
				pStmt.executeBatch();
			} catch (SQLException e) {
				throw new SQLDataException(e);
			} finally {
				SQLUtils.cleanup(pStmt, cn);
			}
		}
	}

	// 判定是否下一个分组.
	private static boolean isNextGroup(RightRecord previous, RightRecord current) {
		boolean isTrue = false;
		String currentGroupId = current.getGroupId();
		if (STUtils.isNotNull(current) && !StringUtils.isEmpty(currentGroupId)) {
			isTrue = (STUtils.isNotNull(previous) && !currentGroupId
					.equals(previous.getGroupId()));
		}
		return isTrue;
	}

	// 获取明细权重记录
	private static IRowSet getDetailRightRs(Context ctx, ProportionItem item)
			throws BOSException {
		List params = new ArrayList();
		String strSQL = item.detailRightSQL(params);
		IRowSet rs = null;
		if (!StringUtils.isEmpty(strSQL)) {
			rs = DbUtil.executeQuery(ctx, strSQL, params.toArray());
		}
		return rs;
	}

	// 是否合法的分摊项
	private static boolean isEffectiveItem(ProportionItem item) {
		return STUtils.isNotNull(item) && item.isEffective();
	}

	// 是否需要进行分摊
	private static boolean isNeedProportion(BigDecimal weighQty) {
		return !NumericUtils.equalsZero(weighQty);
	}
}
