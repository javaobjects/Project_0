package com.kingdee.eas.st.common.app.proportion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.mm.qm.utils.QtyMultiMeasureUtils;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.db.SQLUtils;

public class ProportionItem {
	public String materialFields;
	public String refQtyFields;
	public String asstQtyFields;
	public String qtyFields;
	public String baseQtyFields;
	public String tableName;
	public String keyField;
	public String parentField;

	private ProportionItem childItem;

	public Context ctx;

	public int baseQtyPrecision = 4;

	public final String b = " \n ";

	public ProportionItem(Context ctx, String materialFields,
			String refQtyField, String baseQtyFields, String tableName,
			String keyField, String parentField) {
		this.ctx = ctx;
		this.materialFields = materialFields;
		this.refQtyFields = refQtyField;
		this.baseQtyFields = baseQtyFields;
		this.tableName = tableName;
		this.keyField = keyField;
		this.parentField = parentField;
	}

	public ProportionItem(Context ctx, String materialFields,
			String refQtyField, String baseQtyFields, String qtyFields,
			String tableName, String keyField, String parentField) {
		this(ctx, materialFields, refQtyField, baseQtyFields, tableName,
				keyField, parentField);
		this.qtyFields = qtyFields;
	}

	public ProportionItem(Context ctx, String materialFields,
			String refQtyField, String baseQtyFields, String qtyFields,
			String asstQtyField, String tableName, String keyField,
			String parentField) {
		this(ctx, materialFields, refQtyField, baseQtyFields, qtyFields,
				tableName, keyField, parentField);
		this.asstQtyFields = asstQtyField;
	}

	/**
	 * 辅助计量单位的数据库字段名，子类可覆盖
	 * 
	 * @author zhiwei_wang
	 * @date 2008-9-26
	 * @return
	 */
	public String getAssitUnitField() {
		return "FAsstUnitID";
	}

	/**
	 * 常用计量单位的数据库字段名，子类可覆盖
	 * 
	 * @author zhiwei_wang
	 * @date 2008-9-26
	 * @return
	 */
	public String getUnitField() {
		return "FUnitID";
	}

	/**
	 * 基本计量单位的数据库字段名，子类可覆盖
	 * 
	 * @author zhiwei_wang
	 * @date 2008-9-26
	 * @return
	 */
	public String getBaseUnitField() {
		return "FBaseUnitID";
	}

	/**
	 * 汇总生成上一级的基本数量
	 * 
	 * @author zhiwei_wang
	 * @throws BOSException
	 * @date 2008-9-26
	 */
	protected void sumBaseQtyByChild(String pk) throws BOSException {
		ProportionItem currentItem = this;
		if (currentItem.getChildItem() == null) {
			return;
		} else {
			HashSet childIdSet = new HashSet();
			IRowSet rs = DbUtil.executeQuery(ctx, currentItem
					.getChildSelectSQL(pk));
			try {
				while (rs.next()) {
					childIdSet.add(rs.getString(keyField));
				}
				rs = null;

				// 首先递归调用子对象的汇总
				Iterator it = childIdSet.iterator();
				while (it.hasNext()) {
					currentItem.getChildItem().sumBaseQtyByChild(
							(String) it.next());
				}
				// 重新取数并汇总
				rs = DbUtil.executeQuery(ctx, currentItem.getChildSumSQL(pk));
				BigDecimal baseQty = SysConstant.BIGZERO;
				if (rs.next()) {
					baseQty = baseQty.add(rs.getBigDecimal("totalBaseQty"));
				}
				rs = null;
				updateBaseQty(pk, baseQty);
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
	}

	protected String getChildSumSQL(String parentId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(").append(getChildItem().baseQtyFields).append(
				") as totalBaseQty").append(b);
		sb.append(getChildFromSubSQL());
		sb.append(getChildWhereSubSQL(parentId));

		return sb.toString();
	}

	protected String getChildSelectSQL(Set parentIdSet) {
		StringBuffer sb = new StringBuffer();
		sb.append(getChildSelectSubSQL());
		sb.append(getChildFromSubSQL());
		sb.append(getChildWhereSubSQL(parentIdSet));
		return sb.toString();
	}

	protected String getChildSelectSQL(String parentId) {
		StringBuffer sb = new StringBuffer();
		sb.append(getChildSelectSubSQL());
		sb.append(getChildFromSubSQL());
		sb.append(getChildWhereSubSQL(parentId));
		return sb.toString();
	}

	protected void updateBaseQty(String pk, BigDecimal baseQty)
			throws BOSException {
		String sql = "update " + tableName + " set " + baseQtyFields
				+ "=? where " + keyField + "=?";
		DbUtil.execute(ctx, sql, new Object[] { baseQty, pk });
	}

	/**
	 * 根据基本数量，换算常用数量和辅助数量
	 * 
	 * @author zhiwei_wang
	 * @throws BOSException
	 * @throws EASBizException
	 * @date 2008-9-26
	 */
	protected void calcQtyByBase(String parentPk) throws BOSException,
			EASBizException {
		HashSet idSet = getIdSetByParent(parentPk);
		calcQtyByBase(idSet, true);
	}

	/**
	 * 根据基本数量，换算常用数量和辅助数量
	 * 
	 * @author zhiwei_wang
	 * @date 2008-9-26
	 * @param includeChild
	 *            ，是否递归计算子对象
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void calcQtyByBase(Set idSet, boolean includeChild)
			throws BOSException, EASBizException {
		// TODO
		IRowSet rs = DbUtil.executeQuery(ctx, getSelectSQLById(idSet));
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = EJBFactory.getConnection(ctx);
			pStmt = con.prepareStatement(getUpdateSQL());
			while (rs.next()) {
				getUpdateParams(pStmt, rs);
				pStmt.addBatch();
			}
			pStmt.executeBatch();
		} catch (SQLException e) {
			throw new SQLDataException(e);
		} finally {
			SQLUtils.cleanup(pStmt, con);
		}
	}

	protected void getUpdateParams(PreparedStatement pStmt, IRowSet rs)
			throws SQLException, EASBizException, BOSException {
		BigDecimal baseQty = rs.getBigDecimal(baseQtyFields);
		MaterialInfo material = new MaterialInfo();
		String key = rs.getString(keyField);
		material.setId(BOSUuid.read(rs.getString(materialFields)));
		int index = 1;
		if (!StringUtils.isEmpty(qtyFields)) {
			MeasureUnitInfo unit = new MeasureUnitInfo();
			unit.setId(BOSUuid.read(rs.getString(getUnitField())));
			BigDecimal qty = QtyMultiMeasureUtils.baseQtyTo(ctx, baseQty,
					material, unit);
			pStmt.setBigDecimal(index++, qty);
		}
		if (!StringUtils.isEmpty(asstQtyFields)) {
			MeasureUnitInfo unit = new MeasureUnitInfo();
			unit.setId(BOSUuid.read(rs.getString(getAssitUnitField())));
			BigDecimal qty = QtyMultiMeasureUtils.baseQtyTo(ctx, baseQty,
					material, unit);
			pStmt.setBigDecimal(index++, qty);
		}
		pStmt.setString(index++, key);
	}

	protected String getUpdateSQL() {
		StringBuffer sb = new StringBuffer();
		sb.append("update").append(b);
		sb.append(tableName).append(b);
		sb.append("set ");
		if (!StringUtils.isEmpty(qtyFields)) {
			sb.append(qtyFields).append("=?").append(b);
			if (!StringUtils.isEmpty(asstQtyFields)) {
				sb.append(",").append(asstQtyFields).append("=?").append(b);
			}
		} else {
			if (!StringUtils.isEmpty(asstQtyFields)) {
				sb.append(asstQtyFields).append("=?").append(b);
			}
		}
		sb.append("where ").append(getFloorItem().keyField).append("=?");

		return sb.toString();
	}

	protected String getUpdateFloorSQL() {
		StringBuffer sb = new StringBuffer();
		sb.append("update").append(b);
		sb.append(getFloorItem().tableName).append(b);
		sb.append("set ").append(getFloorItem().baseQtyFields).append("=?")
				.append(b);
		sb.append("where ").append(getFloorItem().keyField).append("=?");

		return sb.toString();
	}

	protected void bindDataToSQL(PreparedStatement pStmt, Object[] params)
			throws SQLException {
		pStmt.setBigDecimal(1, (BigDecimal) params[0]);
		pStmt.setString(2, (String) params[1]);
	}

	protected ProportionItem getFloorItem() {
		ProportionItem currentItem = this;
		while (currentItem != null) {
			if (currentItem.getChildItem() == null) {
				return currentItem;
			} else {
				currentItem = currentItem.getChildItem();
			}
		}
		return null;
	}

	protected HashSet getIdSetByParent(String parentId) throws BOSException {
		HashSet currentIdSet = new HashSet();
		IRowSet rs = DbUtil.executeQuery(ctx, getSelectSQLByParent(parentId));
		try {
			while (rs.next()) {
				currentIdSet.add(rs.getString(keyField));
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

		return currentIdSet;
	}

	protected HashSet getChildIdSet(Set pkSet) throws BOSException {
		HashSet set = new HashSet();
		IRowSet rs = DbUtil.executeQuery(ctx, getChildSelectSQL(pkSet));
		try {
			while (rs.next()) {
				set.add(rs.getString(getChildItem().keyField));
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

		return set;
	}

	protected String getChildSelectSubSQL() {
		StringBuffer sb = new StringBuffer();
		sb.append("select " + getChildItem().keyField).append(",").append(
				getChildItem().baseQtyFields).append(b);

		return sb.toString();
	}

	protected String getChildFromSubSQL() {
		StringBuffer sb = new StringBuffer();
		sb.append("from ").append(getChildItem().tableName).append(b);
		return sb.toString();
	}

	protected String getChildWhereSubSQL(Set parentIdSet) {
		StringBuffer sb = new StringBuffer();
		sb.append("where ").append(getChildItem().parentField).append(" in(")
				.append(STQMUtils.toIDString(parentIdSet.iterator())).append(
						")").append(b);
		return sb.toString();
	}

	protected String getChildWhereSubSQL(String parentId) {
		StringBuffer sb = new StringBuffer();
		sb.append("where ").append(getChildItem().parentField).append(" ='")
				.append(parentId).append("'").append(b);
		return sb.toString();
	}

	protected String getSelectSQLById(Set idSet) {
		StringBuffer sb = new StringBuffer();
		sb.append(getSelectSubSQL());
		sb.append(getFromSubSQL());
		sb.append("where ").append(keyField).append(" in(").append(
				STQMUtils.toIDString(idSet.iterator())).append(")").append(b);

		return sb.toString();
	}

	protected String getSelectSQLByParent(String parentId) {
		StringBuffer sb = new StringBuffer();
		sb.append(getSelectSubSQL());
		sb.append(getFromSubSQL());
		sb.append("where ").append(parentField).append(" ='").append(parentId)
				.append("'").append(b);

		return sb.toString();
	}

	protected String getSelectSubSQL() {
		StringBuffer sb = new StringBuffer();
		sb.append("select " + keyField).append(",").append(baseQtyFields)
				.append(b);
		sb.append(",").append(materialFields).append(",").append(
				getBaseUnitField()).append(b);
		if (!StringUtils.isEmpty(qtyFields)) {
			sb.append(",").append(qtyFields).append(b);
		}
		if (!StringUtils.isEmpty(qtyFields)) {
			sb.append(",").append(getUnitField()).append(b);
		}
		if (!StringUtils.isEmpty(asstQtyFields)) {
			sb.append(",").append(asstQtyFields).append(b);
		}
		if (!StringUtils.isEmpty(asstQtyFields)) {
			sb.append(",").append(getAssitUnitField()).append(b);
		}
		return sb.toString();
	}

	protected String getFromSubSQL() {
		StringBuffer sb = new StringBuffer();
		sb.append("from ").append(tableName).append(b);
		return sb.toString();
	}

	public ProportionItem getChildItem() {
		return childItem;
	}

	public void setChildItem(ProportionItem childItem) {
		this.childItem = childItem;
	}

}
