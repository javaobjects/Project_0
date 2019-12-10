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
 * ����ͨ������Դ�ṩ��
 */
public final class STCommonDataProvider implements BOSQueryDelegate {
	/**
	 * ��ӡ�ĵ���id����
	 */
	private Set ids = null;

	/**
	 * ��ѯ������
	 */
	private FilterInfo filter = null;

	/**
	 * ��ӡ�Ĳ�ѯ������
	 */
	private IMetaDataPK qpk = null;

	/**
	 * ������Ӧ�ĸ�������Դ���ֶ����ƣ�Ĭ��Ϊ����ID��ʶ
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
	 * ���ݱ�������Դid��ȡ��IRowSet
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
			// �Զ�����ö��Ϊ����
			exec.option().isAutoTranslateEnum = true;
			// �Զ������߼�ֵ
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
	 * ���ò�ѯ�Ĺ�����
	 * 
	 * @param filter
	 */
	public void setFilter(FilterInfo filter) {
		this.filter = filter;
	}
}
