/*
 * @(#)ProportionItem.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.basedata.mm.qm.utils.NumericUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 描述:
 * 
 * @author daij date:2008-7-14
 *         <p>
 * @version EAS5.4
 */
public abstract class ProportionItem {

	/**
	 * 被分摊字段别名(上级分摊字段汇总).
	 */
	protected final static String FIEDLNAME_PROPORTIONED = "FProportioned";

	/**
	 * 权重汇总字段名(本级权重字段汇总).
	 */
	protected final static String FIEDLNAME_RIGHTTOTAL = "FRightTotal";

	// 数据库表名
	private String tableName = null;
	// 权重字段名
	private String rightFieldName = null;
	// 分摊字段名
	private String proportionFieldName = null;
	// 上级关联表名.
	private String parentFieldName = null;
	// 待分摊的总值.
	private BigDecimal total = null;
	// 分摊的精度 - 分摊结果值的精度统一采用待分摊总值的精度.
	protected int scale = STConstant.PRECISION_DEFAULT_NUMBER;

	// 待分摊的业务对象Id
	private String bizObjectId = null;

	/**
	 * 前驱.
	 */
	protected ProportionItem usher = null;
	/**
	 * 后继.
	 */
	protected ProportionItem subsequence = null;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2008-7-14
	 *              <p>
	 */
	public ProportionItem() {
		super();
	}

	/**
	 * 
	 * 描述：放入下一个分摊项.
	 * 
	 * @param subsequence
	 *            分摊项
	 * @author:daij 创建时间：2008-7-15
	 *              <p>
	 */
	public void putNextProportionItem(ProportionItem item) {
		if (STUtils.isNotNull(item)) {
			// 放入后继
			this.subsequence = item;
			// 放入前驱
			item.usher = this;
			// 分摊结果值的精度要保持一致.
			item.scale = this.scale;
		}
	}

	/**
	 * 
	 * 描述：是否根
	 * 
	 * @return boolean
	 * @author:daij 创建时间：2008-7-15
	 *              <p>
	 */
	public boolean isRoot() {
		return STUtils.isNull(usher);
	}

	/**
	 * 
	 * 描述：是否尾.
	 * 
	 * @return boolean
	 * @author:daij 创建时间：2008-7-15
	 *              <p>
	 */
	public boolean isLeaf() {
		return STUtils.isNull(subsequence);
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param tableName
	 *            待分摊的数据表名
	 * @param totalFieldName
	 *            待分摊的总值字段.
	 * @param proportionFieldName
	 *            分摊字段名.
	 * @author:daij 创建时间：2008-7-14
	 *              <p>
	 */
	public ProportionItem(String tableName, String rightFieldName,
			String proportionFieldName, String parentFieldName) {

		super();

		this.tableName = tableName;
		this.rightFieldName = rightFieldName;
		this.proportionFieldName = proportionFieldName;
		this.parentFieldName = parentFieldName;
	}

	/**
	 * 描述:@return 返回 proportionFieldName。
	 */
	public String getProportionFieldName() {
		return proportionFieldName;
	}

	/**
	 * 描述:设置proportionFieldName的值。
	 * 
	 * @param proportionFieldName
	 */
	public void setProportionFieldName(String proportionFieldName) {
		this.proportionFieldName = proportionFieldName;
	}

	/**
	 * 描述:@return 返回 tableName。
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 描述:设置tableName的值。
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 描述:@return 返回 totalFieldName。
	 */
	public String getRightFieldName() {
		return rightFieldName;
	}

	/**
	 * 描述:设置totalFieldName的值。
	 * 
	 * @param totalFieldName
	 */
	public void setRightFieldName(String rightFieldName) {
		this.rightFieldName = rightFieldName;
	}

	/**
	 * 描述:@return 返回 parentFieldName。
	 */
	public String getParentFieldName() {
		return parentFieldName;
	}

	/**
	 * 描述:设置parentFieldName的值。
	 * 
	 * @param parentFieldName
	 */
	public void setParentFieldName(String parentFieldName) {
		this.parentFieldName = parentFieldName;
	}

	/**
	 * 描述:@return 返回 total。
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * 描述:设置total的值。
	 * 
	 * @param total
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;

		// 精度不在这个地方获取,可以通过外部传入
		// 直接通过这个地方取,会导致0.8000,只获得1
		// 分摊结果值的精度统一采用待分摊总值的精度.
		// this.scale = NumericUtils.effectualPrecision(
		// NumericUtils.effectualNumeric(total).scale(),
		// STConstant.PRECISION_DEFAULT_NUMBER);
	}

	/**
	 * 描述:@return 返回 bizObjectId。
	 */
	public String getBizObjectId() {
		return bizObjectId;
	}

	/**
	 * 描述:设置bizObjectId的值。
	 * 
	 * @param bizObjectId
	 */
	public void setBizObjectId(String bizObjectId) {
		this.bizObjectId = bizObjectId;
	}

	/**
	 * 描述:@return 返回 scale。
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * 描述:@return 返回 scale。
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * 
	 * 描述：分摊项是否合法
	 * 
	 * @return boolean
	 * @author:daij 创建时间：2008-7-16
	 *              <p>
	 */
	public boolean isEffective() {
		return (!StringUtils.isEmpty(getTableName())
				&& !StringUtils.isEmpty(getRightFieldName())
				&& !StringUtils.isEmpty(getParentFieldName()) && !StringUtils
				.isEmpty(getProportionFieldName()));
	}

	/**
	 * 
	 * 描述：输出更新分摊值的SQL语句.
	 * 
	 * @param pk
	 *            待更新的业务对象Id
	 * @param proportionValue
	 *            分摊给该业务对象的值.
	 * @return String
	 * @author:daij 创建时间：2008-7-14
	 *              <p>
	 */
	public final String proportionSQL() {
		StringBuffer strSQL = new StringBuffer();
		if (!StringUtils.isEmpty(tableName)
				&& !StringUtils.isEmpty(proportionFieldName)) {
			strSQL.append("Update ").append(tableName).append(STConstant.RN)
					.append("Set ").append(setFieldValuesSQLString()).append(
							STConstant.RN).append("Where FID = ? ");
		}
		return strSQL.toString();
	}

	/**
	 * 
	 * 描述：子类可覆盖此方法更新其他字段.
	 * 
	 * @return String
	 * @author:daij 创建时间：2008-7-19
	 *              <p>
	 */
	protected String setFieldValuesSQLString() {
		StringBuffer str = new StringBuffer();
		str.append(proportionFieldName).append(" = ? ");

		return str.toString();
	}

	/**
	 * 
	 * 描述：绑定分摊SQL参数值
	 * 
	 * @param pStmt
	 *            SQL执行环境
	 * @param record
	 *            分摊记录
	 * @throws SQLDataException
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 */
	public final void bindingProportionSQLParameter(PreparedStatement pStmt,
			RightRecord record) throws SQLDataException {
		if (STUtils.isNotNull(pStmt) && STUtils.isNotNull(record)) {
			try {
				pStmt.setString(bindingDetailParameter(pStmt, record), record
						.getPk());

				pStmt.addBatch();
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
	}

	/**
	 * 
	 * 描述：子类可覆盖此方法为需要更新的其他字段绑定参数值
	 * 
	 * @param pStmt
	 *            SQL执行环境
	 * @param record
	 *            分摊记录
	 * @return int 返回参数计数
	 * @throws SQLDataException
	 * @author:daij 创建时间：2008-7-19
	 *              <p>
	 */
	protected int bindingDetailParameter(PreparedStatement pStmt,
			RightRecord record) throws SQLDataException {
		int count = 1;
		try {
			pStmt.setBigDecimal(count, record.getProportion());
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return ++count;
	}

	/**
	 * 
	 * 描述：输出获取明细权重记录的SQL语句.
	 * 
	 * @return String
	 * @author:daij 创建时间：2008-7-15
	 *              <p>
	 */
	public String detailRightSQL(List params) {
		if (STUtils.isNull(params))
			params = new ArrayList();
		StringBuffer script = new StringBuffer();
		if (isEffective()) {
			script.append("Select d.* ,").append(
					field("s", FIEDLNAME_RIGHTTOTAL)).append(" From (").append(
					STConstant.RN).append(selectSQLString()).append(
					fromSQLString()).append(leftSQLString(params)).append(
					") d ").append(STConstant.RN).append("Left Outer Join (")
					.append(STConstant.RN).append(unionSQLString(params))
					.append(") s On ").append(field("d", parentFieldName))
					.append("=").append(field("s", parentFieldName)).append(
							STConstant.RN).append("Order By ").append(
							field("d", parentFieldName)).append(",d.FSeq Desc");
		}
		return script.toString();
	}

	/**
	 * 
	 * 描述：权重记录实例
	 * 
	 * @param IRowSet
	 *            行记录
	 * @return RightRecord
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 * @throws SQLDataException
	 */
	public abstract RightRecord rightRecordInstance(IRowSet rs)
			throws SQLDataException;

	private String selectSQLString() {
		StringBuffer script = new StringBuffer();
		if (isEffective()) {
			script.append(" Select ").append(field(rightFieldName)).append(",")
					.append(field(parentFieldName)).append(",");
			if (!isRoot()) {
				script
						.append(
								field(usher.tableName,
										usher.proportionFieldName)).append(
								" As ").append(FIEDLNAME_PROPORTIONED).append(
								",");
			} else {
				script.append(NumericUtils.effectualNumeric(getTotal()))
						.append(" As ").append(FIEDLNAME_PROPORTIONED).append(
								",");
			}
			script.append(field("FID")).append(",").append(field("FSeq "));

			if (!StringUtils.isEmpty(correlativeFieldSQLString())) {
				script.append(",").append(correlativeFieldSQLString()).append(
						STConstant.RN);
			} else {
				script.append(STConstant.RN);
			}
		}
		return script.toString();
	}

	/**
	 * 
	 * 描述：扩展相关的数据字段.
	 * 
	 * @return String
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 */
	protected abstract String correlativeFieldSQLString();

	private String fromSQLString() {
		StringBuffer script = new StringBuffer();
		if (isEffective()) {
			script.append(" From ").append(tableName).append(STConstant.RN);

			if (!StringUtils.isEmpty(correlativeTableSQLString())) {
				script.append(correlativeTableSQLString())
						.append(STConstant.RN);
			}
		}
		return script.toString();
	}

	/**
	 * 
	 * 描述：Left扩展相关数据表
	 * 
	 * @return String
	 * @author:daij 创建时间：2008-7-18
	 *              <p>
	 */
	protected abstract String correlativeTableSQLString();

	private String leftSQLString(List params) {
		StringBuffer script = new StringBuffer();
		if (isEffective()) {
			if (!isRoot()) {
				script.append(" Left outer join ").append(usher.tableName)
						.append(" On ").append(field(parentFieldName)).append(
								" = ").append(field(usher.tableName, "FID"))
						.append(STConstant.RN);
				// 增加前驱的关联.
				script.append(usher.leftSQLString(params));
			} else {
				script.append(" Where ").append(field(parentFieldName)).append(
						"= ?");
				params.add(getBizObjectId());
			}
		}
		return script.toString();
	}

	private String unionSQLString(List params) {
		StringBuffer script = new StringBuffer();
		if (isEffective()) {
			script.append(" Select Sum(").append(field(rightFieldName)).append(
					") As ").append(FIEDLNAME_RIGHTTOTAL).append(",").append(
					field(parentFieldName)).append(STConstant.RN).append(
					" From ").append(tableName).append(leftSQLString(params))
					.append(STConstant.RN).append(" Group By ").append(
							field(parentFieldName));
		}
		return script.toString();
	}

	protected String field(String table, String f) {
		StringBuffer script = new StringBuffer();
		if (!StringUtils.isEmpty(table) && !StringUtils.isEmpty(f)) {
			script.append(table).append(".").append(f);
		}
		return script.toString();
	}

	protected String field(String f) {
		StringBuffer script = new StringBuffer();
		if (!StringUtils.isEmpty(tableName) && !StringUtils.isEmpty(f)) {
			script.append(tableName).append(".").append(f);
		}
		return script.toString();
	}
}
