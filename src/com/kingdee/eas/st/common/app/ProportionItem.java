/*
 * @(#)ProportionItem.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:
 * 
 * @author daij date:2008-7-14
 *         <p>
 * @version EAS5.4
 */
public abstract class ProportionItem {

	/**
	 * ����̯�ֶα���(�ϼ���̯�ֶλ���).
	 */
	protected final static String FIEDLNAME_PROPORTIONED = "FProportioned";

	/**
	 * Ȩ�ػ����ֶ���(����Ȩ���ֶλ���).
	 */
	protected final static String FIEDLNAME_RIGHTTOTAL = "FRightTotal";

	// ���ݿ����
	private String tableName = null;
	// Ȩ���ֶ���
	private String rightFieldName = null;
	// ��̯�ֶ���
	private String proportionFieldName = null;
	// �ϼ���������.
	private String parentFieldName = null;
	// ����̯����ֵ.
	private BigDecimal total = null;
	// ��̯�ľ��� - ��̯���ֵ�ľ���ͳһ���ô���̯��ֵ�ľ���.
	protected int scale = STConstant.PRECISION_DEFAULT_NUMBER;

	// ����̯��ҵ�����Id
	private String bizObjectId = null;

	/**
	 * ǰ��.
	 */
	protected ProportionItem usher = null;
	/**
	 * ���.
	 */
	protected ProportionItem subsequence = null;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2008-7-14
	 *              <p>
	 */
	public ProportionItem() {
		super();
	}

	/**
	 * 
	 * ������������һ����̯��.
	 * 
	 * @param subsequence
	 *            ��̯��
	 * @author:daij ����ʱ�䣺2008-7-15
	 *              <p>
	 */
	public void putNextProportionItem(ProportionItem item) {
		if (STUtils.isNotNull(item)) {
			// ������
			this.subsequence = item;
			// ����ǰ��
			item.usher = this;
			// ��̯���ֵ�ľ���Ҫ����һ��.
			item.scale = this.scale;
		}
	}

	/**
	 * 
	 * �������Ƿ��
	 * 
	 * @return boolean
	 * @author:daij ����ʱ�䣺2008-7-15
	 *              <p>
	 */
	public boolean isRoot() {
		return STUtils.isNull(usher);
	}

	/**
	 * 
	 * �������Ƿ�β.
	 * 
	 * @return boolean
	 * @author:daij ����ʱ�䣺2008-7-15
	 *              <p>
	 */
	public boolean isLeaf() {
		return STUtils.isNull(subsequence);
	}

	/**
	 * ���������캯��
	 * 
	 * @param tableName
	 *            ����̯�����ݱ���
	 * @param totalFieldName
	 *            ����̯����ֵ�ֶ�.
	 * @param proportionFieldName
	 *            ��̯�ֶ���.
	 * @author:daij ����ʱ�䣺2008-7-14
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
	 * ����:@return ���� proportionFieldName��
	 */
	public String getProportionFieldName() {
		return proportionFieldName;
	}

	/**
	 * ����:����proportionFieldName��ֵ��
	 * 
	 * @param proportionFieldName
	 */
	public void setProportionFieldName(String proportionFieldName) {
		this.proportionFieldName = proportionFieldName;
	}

	/**
	 * ����:@return ���� tableName��
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * ����:����tableName��ֵ��
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * ����:@return ���� totalFieldName��
	 */
	public String getRightFieldName() {
		return rightFieldName;
	}

	/**
	 * ����:����totalFieldName��ֵ��
	 * 
	 * @param totalFieldName
	 */
	public void setRightFieldName(String rightFieldName) {
		this.rightFieldName = rightFieldName;
	}

	/**
	 * ����:@return ���� parentFieldName��
	 */
	public String getParentFieldName() {
		return parentFieldName;
	}

	/**
	 * ����:����parentFieldName��ֵ��
	 * 
	 * @param parentFieldName
	 */
	public void setParentFieldName(String parentFieldName) {
		this.parentFieldName = parentFieldName;
	}

	/**
	 * ����:@return ���� total��
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * ����:����total��ֵ��
	 * 
	 * @param total
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;

		// ���Ȳ�������ط���ȡ,����ͨ���ⲿ����
		// ֱ��ͨ������ط�ȡ,�ᵼ��0.8000,ֻ���1
		// ��̯���ֵ�ľ���ͳһ���ô���̯��ֵ�ľ���.
		// this.scale = NumericUtils.effectualPrecision(
		// NumericUtils.effectualNumeric(total).scale(),
		// STConstant.PRECISION_DEFAULT_NUMBER);
	}

	/**
	 * ����:@return ���� bizObjectId��
	 */
	public String getBizObjectId() {
		return bizObjectId;
	}

	/**
	 * ����:����bizObjectId��ֵ��
	 * 
	 * @param bizObjectId
	 */
	public void setBizObjectId(String bizObjectId) {
		this.bizObjectId = bizObjectId;
	}

	/**
	 * ����:@return ���� scale��
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * ����:@return ���� scale��
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * 
	 * ��������̯���Ƿ�Ϸ�
	 * 
	 * @return boolean
	 * @author:daij ����ʱ�䣺2008-7-16
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
	 * ������������·�ֵ̯��SQL���.
	 * 
	 * @param pk
	 *            �����µ�ҵ�����Id
	 * @param proportionValue
	 *            ��̯����ҵ������ֵ.
	 * @return String
	 * @author:daij ����ʱ�䣺2008-7-14
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
	 * ����������ɸ��Ǵ˷������������ֶ�.
	 * 
	 * @return String
	 * @author:daij ����ʱ�䣺2008-7-19
	 *              <p>
	 */
	protected String setFieldValuesSQLString() {
		StringBuffer str = new StringBuffer();
		str.append(proportionFieldName).append(" = ? ");

		return str.toString();
	}

	/**
	 * 
	 * �������󶨷�̯SQL����ֵ
	 * 
	 * @param pStmt
	 *            SQLִ�л���
	 * @param record
	 *            ��̯��¼
	 * @throws SQLDataException
	 * @author:daij ����ʱ�䣺2008-7-18
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
	 * ����������ɸ��Ǵ˷���Ϊ��Ҫ���µ������ֶΰ󶨲���ֵ
	 * 
	 * @param pStmt
	 *            SQLִ�л���
	 * @param record
	 *            ��̯��¼
	 * @return int ���ز�������
	 * @throws SQLDataException
	 * @author:daij ����ʱ�䣺2008-7-19
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
	 * �����������ȡ��ϸȨ�ؼ�¼��SQL���.
	 * 
	 * @return String
	 * @author:daij ����ʱ�䣺2008-7-15
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
	 * ������Ȩ�ؼ�¼ʵ��
	 * 
	 * @param IRowSet
	 *            �м�¼
	 * @return RightRecord
	 * @author:daij ����ʱ�䣺2008-7-18
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
	 * ��������չ��ص������ֶ�.
	 * 
	 * @return String
	 * @author:daij ����ʱ�䣺2008-7-18
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
	 * ������Left��չ������ݱ�
	 * 
	 * @return String
	 * @author:daij ����ʱ�䣺2008-7-18
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
				// ����ǰ���Ĺ���.
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
