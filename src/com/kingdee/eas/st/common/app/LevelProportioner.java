/*
 * @(#)LevelProportion.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����: �𼶷�̯����
 * 
 * @author daij date:2008-7-14
 *         <p>
 * @version EAS5.4
 */
public abstract class LevelProportioner {

	// ��̯����
	public static void proportion(Context ctx, ProportionItem item)
			throws BOSException {
		ProportionItem currentItem = item;
		// �ܵĴ���ֵ̯���벻������
		if (isEffectiveItem(currentItem)
				&& isNeedProportion(currentItem.getTotal())) {
			// һ��ҵ��������Ϊһ������.
			while (isEffectiveItem(currentItem)) {
				// ����ÿ��������̯��`
				proportionByItem(ctx, currentItem);
				// ����������һ����̯��
				currentItem = currentItem.subsequence;
			}
		}
	}

	private static void proportionByItem(Context ctx, ProportionItem item)
			throws BOSException {
		// ��ȡ��ϸȨ�ؼ�¼
		IRowSet rs = getDetailRightRs(ctx, item);
		if (STUtils.isNotNull(rs)) {
			Connection cn = null;
			PreparedStatement pStmt = null;
			try {
				int count = 0;
				// �ѷ�ֵ̯
				BigDecimal proportioned = SysConstant.BIGZERO;
				// ��ǰ��¼
				RightRecord current = null;
				// ǰһ��¼
				RightRecord previous = null;

				cn = EJBFactory.getConnection(ctx);
				pStmt = cn.prepareStatement(item.proportionSQL());
				while (rs.next()) {
					if (count > 0) {
						previous = current;
					}
					current = item.rightRecordInstance(rs);
					// ����PK
					current.setPk(rs.getString("FID"));
					// ����Id
					current.setGroupId(rs.getString(item.getParentFieldName()));
					// Ȩ��ֵ
					current
							.setRight(rs
									.getBigDecimal(item.getRightFieldName()));
					// ��Ȩ��ֵ
					current
							.setRightTotalQty(rs
									.getBigDecimal(ProportionItem.FIEDLNAME_RIGHTTOTAL));
					// ����ֵ̯.
					current
							.setTotal(rs
									.getBigDecimal(ProportionItem.FIEDLNAME_PROPORTIONED));

					if (isNextGroup(previous, current)) {
						// �����Ǹ÷����һ����¼�����е���.
						if (STUtils.isNotNull(previous)) {
							// �ۼ��ѷ�ֵ̯.
							previous.proportionByMirror(proportioned);
							// �ѷ�ֵ̯����.
							proportioned = SysConstant.BIGZERO;
							// �󶨷�̯SQL����ֵ
							item.bindingProportionSQLParameter(pStmt, previous);
						}
					} else {
						// �������Ǹ÷����һ����¼����Ȩ�ط�̯.
						if (STUtils.isNotNull(previous)) {
							// ��Ȩ�ؼ����ֵ̯
							previous.proportionByRight();
							// �ۼ��ѷ�ֵ̯
							proportioned = proportioned.add(previous
									.getProportion());
							// �󶨷�̯SQL����ֵ
							item.bindingProportionSQLParameter(pStmt, previous);
						}
					}
					count++;
				}
				if (STUtils.isNotNull(current)) {
					// �ۼ��ѷ�ֵ̯.
					current.proportionByMirror(proportioned);
					// �ѷ�ֵ̯����.
					proportioned = SysConstant.BIGZERO;
					// �󶨷�̯SQL����ֵ
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

	// �ж��Ƿ���һ������.
	private static boolean isNextGroup(RightRecord previous, RightRecord current) {
		boolean isTrue = false;
		String currentGroupId = current.getGroupId();
		if (STUtils.isNotNull(current) && !StringUtils.isEmpty(currentGroupId)) {
			isTrue = (STUtils.isNotNull(previous) && !currentGroupId
					.equals(previous.getGroupId()));
		}
		return isTrue;
	}

	// ��ȡ��ϸȨ�ؼ�¼
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

	// �Ƿ�Ϸ��ķ�̯��
	private static boolean isEffectiveItem(ProportionItem item) {
		return STUtils.isNotNull(item) && item.isEffective();
	}

	// �Ƿ���Ҫ���з�̯
	private static boolean isNeedProportion(BigDecimal weighQty) {
		return !NumericUtils.equalsZero(weighQty);
	}
}
