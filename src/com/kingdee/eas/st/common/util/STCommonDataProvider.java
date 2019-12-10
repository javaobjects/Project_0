package com.kingdee.eas.st.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 辅助通用数据源提供器
 */
public final class STCommonDataProvider implements BOSQueryDelegate {
	/**
	 * 打印的单据id集合
	 */
	private Set ids = null;

	/**
	 * 查询过滤器
	 */
	private FilterInfo filter = null;

	/**
	 * 打印的查询的名称
	 */
	private IMetaDataPK qpk = null;

	/**
	 * 参数对应的辅助数据源的字段名称，默认为主键ID标识
	 */
	private String paramName = "id";

	/**
	 * @param id
	 * @param qpk
	 */
	public STCommonDataProvider(Set id, IMetaDataPK qpk) {
		this.ids = id;
		this.qpk = qpk;
	}

	/**
	 * @param id
	 * @param qpk
	 */
	public STCommonDataProvider(List id, IMetaDataPK qpk) {
		this(new HashSet(id), qpk);
	}

	/**
	 * @param id
	 * @param qpk
	 * @param paramName
	 */
	public STCommonDataProvider(Set id, IMetaDataPK qpk, String paramName) {
		this(id, qpk);
		this.paramName = paramName;
	}

	/**
	 * @param id
	 * @param qpk
	 * @param paramName
	 */
	public STCommonDataProvider(List id, IMetaDataPK qpk, String paramName) {
		this(new HashSet(id), qpk, paramName);
	}

	/**
	 * 根据报表数据源id，取得IRowSet
	 */
	public IRowSet execute(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = (Variant) param.getValue();
		}

		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			// 自动翻译枚举为名称
			exec.option().isAutoTranslateEnum = true;
			// 自动翻译逻辑值
			exec.option().isAutoTranslateBoolean = true;
			EntityViewInfo ev = new EntityViewInfo();

			if (filter == null) {
				filter = new FilterInfo();
				if (ids.size() == 1) {
					filter.getFilterItems().add(
							new FilterItemInfo("id", ids.toArray()[0]
									.toString(), CompareType.EQUALS));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("id", ids, CompareType.INCLUDE));
				}
				filter.getFilterItems().add(
						new FilterItemInfo("billStatus", new Integer(
								BillBaseStatusEnum.ADD_VALUE),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("billStatus", new Integer(
								BillBaseStatusEnum.TEMPORARILYSAVED_VALUE),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("billStatus", new Integer(
								BillBaseStatusEnum.DELETED_VALUE),
								CompareType.NOTEQUALS));
				filter.setMaskString("#0 and #1 and #2 and #3");
			}

			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
		} catch (Exception e) {
			ExceptionHandler.handle((CoreUI) null, e);
		}
		return iRowSet;
	}

	/**
	 * 设置查询的过滤器
	 * 
	 * @param filter
	 */
	public void setFilter(FilterInfo filter) {
		this.filter = filter;
	}
}
