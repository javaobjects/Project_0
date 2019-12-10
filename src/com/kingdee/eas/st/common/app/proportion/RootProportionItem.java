package com.kingdee.eas.st.common.app.proportion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class RootProportionItem extends ProportionItem {

	private String pk;

	/**
	 * @alias 待分摊的数量
	 */
	private BigDecimal totalBaseQty;

	private BigDecimal totalRefBaseQty = SysConstant.BIGZERO;

	public RootProportionItem(Context ctx, String materialFields,
			String refQtyField, String baseQtyFields, String qtyFields,
			String asstQtyField, String tableName, String keyField,
			String parentField) {
		super(ctx, materialFields, refQtyField, baseQtyFields, qtyFields,
				asstQtyField, tableName, keyField, parentField);
	}

	public RootProportionItem(Context ctx, String materialFields,
			String refQtyField, String baseQtyFields, String qtyFields,
			String tableName, String keyField, String parentField) {
		super(ctx, materialFields, refQtyField, baseQtyFields, qtyFields,
				tableName, keyField, parentField);
	}

	public RootProportionItem(Context ctx, String materialFields,
			String refQtyField, String baseQtyFields, String tableName,
			String keyField, String parentField) {
		super(ctx, materialFields, refQtyField, baseQtyFields, tableName,
				keyField, parentField);
	}

	/**
	 * @throws BOSException
	 * @throws EASBizException
	 * @alias 分摊
	 */
	public void dispense() throws BOSException, EASBizException {
		// 对最底层的基本数量进行分摊
		dispenseBaseQtyForFloor();
		// 从倒数第二层，采用汇总的方式，计算基本数量
		HashSet currentIdSet = getIdSetByParent(pk);
		Iterator it = currentIdSet.iterator();
		while (it.hasNext()) {
			sumBaseQtyByChild((String) it.next());
		}
		// 递归每一层，根据基本数量，换算常用数量和辅助数量
		calcQtyByBase(pk);
	}

	/**
	 * 对最底层的基本数量进行分摊
	 * 
	 * @author zhiwei_wang
	 * @throws BOSException
	 * @date 2008-9-26
	 */
	protected void dispenseBaseQtyForFloor() throws BOSException {
		// 循环取得每条最底层的记录
		IRowSet rs = getFloorRowSet();
		Connection cn = null;
		PreparedStatement pStmt = null;
		try {
			cn = EJBFactory.getConnection(ctx);
			pStmt = cn.prepareStatement(getUpdateFloorSQL());
			BigDecimal tempTotalBaseQty = SysConstant.BIGZERO;
			String lastPK = "none";
			BigDecimal lastBaseQty = SysConstant.BIGZERO;
			while (rs.next()) {
				BigDecimal refBaseQty = rs.getBigDecimal(refQtyFields);
				if (refBaseQty == null) {
					refBaseQty = SysConstant.BIGZERO;
				}
				BigDecimal changeRate = new BigDecimal("1.0");
				if (totalRefBaseQty != null
						&& totalRefBaseQty.compareTo(SysConstant.BIGZERO) > 0) {
					changeRate = refBaseQty.divide(totalRefBaseQty,
							STConstant.PRECISION_DEFAUL_DB,
							BigDecimal.ROUND_HALF_UP);
				}
				BigDecimal baseQty = rs.getBigDecimal(baseQtyFields);
				if (baseQty == null) {
					baseQty = SysConstant.BIGZERO;
				}

				baseQty = baseQty.multiply(changeRate);
				lastBaseQty = baseQty.setScale(baseQtyPrecision);
				lastPK = rs.getString(getFloorItem().keyField);
				tempTotalBaseQty = tempTotalBaseQty.add(lastBaseQty);

				bindDataToSQL(pStmt, new Object[] { lastBaseQty, lastPK });
				pStmt.addBatch();

			}
			// 处理尾差
			BigDecimal warpBaseQty = totalRefBaseQty.subtract(tempTotalBaseQty); // 尾差
			bindDataToSQL(pStmt, new Object[] { lastBaseQty.add(warpBaseQty),
					lastPK });
			pStmt.addBatch();
			// 执行更新
			pStmt.executeBatch();
		} catch (SQLException e) {
			throw new SQLDataException(e);
		} finally {
			SQLUtils.cleanup(pStmt, cn);
		}
	}

	/**
	 * 取得最底层的id列表
	 * 
	 * @author zhiwei_wang
	 * @date 2008-9-26
	 * @return
	 * @throws BOSException
	 */
	protected HashSet getFloorIdSet() throws BOSException {
		HashSet floorIdSet = new HashSet();
		ProportionItem currentItem = this;
		HashSet currentIdSet = getIdSetByParent(pk);

		floorIdSet = currentIdSet;
		while (currentItem.getChildItem() != null) {
			currentIdSet = currentItem.getChildIdSet(currentIdSet);
			currentItem = currentItem.getChildItem();
			floorIdSet = currentIdSet;
		}
		return floorIdSet;
	}

	/**
	 * 初始化状态 需要处理基本计量单位精度,取得总参考数量
	 * 
	 * @author zhiwei_wang
	 * @throws BOSException
	 * @date 2008-9-26
	 */
	protected void initData() throws BOSException {
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(materialFields).append(",").append(
				getBaseUnitField()).append(",").append(refQtyFields).append(
				" from ").append(tableName).append(b);
		sb.append(" where ").append(keyField).append("='").append(pk).append(
				"'");
		IRowSet rs = DbUtil.executeQuery(ctx, sb.toString());
		try {
			if (rs.next()) {
				BigDecimal refQty = rs.getBigDecimal(refQtyFields);
				String materialId = rs.getString(materialFields);
				String baseUnitId = rs.getString(getBaseUnitField());
				if (refQty != null) {
					totalRefBaseQty = refQty;
				}
				sb.setLength(0);
				sb
						.append("select FQtyPrecision from T_BD_MultiMeasureUnit where FMaterialID=? and FMeasureUnitID=?");
				IRowSet rsTemp = DbUtil.executeQuery(ctx, sb.toString(),
						new Object[] { materialId, baseUnitId });
				if (rs.next()) {
					baseQtyPrecision = rsTemp.getInt("FQtyPrecision");
				}
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
	}

	protected IRowSet getFloorRowSet() throws BOSException {
		HashSet floorIdSet = getFloorIdSet();

		return DbUtil.executeQuery(ctx, getSelectSQLById(floorIdSet));
	}

	public BigDecimal getTotalBaseQty() {
		return totalBaseQty;
	}

	public void setTotalBaseQty(BigDecimal totalBaseQty) {
		this.totalBaseQty = totalBaseQty;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}
}
