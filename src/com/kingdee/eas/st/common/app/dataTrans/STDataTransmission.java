/*
 * STDataTransmission.java
 * 深圳市卓亮科技有限公司版权所有
 */
package com.kingdee.eas.st.common.app.dataTrans;

import java.util.Hashtable;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.tools.datatask.DIETemplateFactory;
import com.kingdee.eas.tools.datatask.DIETemplateInfo;
import com.kingdee.eas.tools.datatask.DefaultDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author haizi date: 2017-7-13 <br />
 * 
 */
abstract public class STDataTransmission extends DefaultDataTransmission {
	/**
	 * 
	 */
	public STDataTransmission() {
		super(ContextUtils.getContextFromSession());
		try {
			DIETemplateInfo templateInfo = DIETemplateFactory.getLocalInstance(
					getContext()).getDIETemplateCollection(
					"where number='" + getSolutionNumber() + "'").get(0);
			setDIETemplateInfo(templateInfo);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return 导入导出方案编码
	 */
	abstract protected String getSolutionNumber();

	/**
	 * @param ctx
	 */
	public STDataTransmission(Context ctx) {
		super(ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kingdee.eas.tools.datatask.AbstractDataTransmission#transmit(java
	 * .util.Map, com.kingdee.bos.Context)
	 */
	@Override
	public CoreBaseInfo transmit(Map hsData, Context ctx)
			throws TaskExternalException {
		return super.transmit(hsData, ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kingdee.eas.tools.datatask.AbstractDataTransmission#transmit(java
	 * .util.Hashtable, com.kingdee.bos.Context)
	 */
	@Override
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		return super.transmit(hsData, ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kingdee.eas.tools.datatask.AbstractDataTransmission#exportTransmit
	 * (com.kingdee.jdbc.rowset.IRowSet, com.kingdee.bos.Context)
	 */
	@Override
	public Map exportTransmit(IRowSet rs, Context ctx)
			throws TaskExternalException {
		return super.exportTransmit(rs, ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kingdee.eas.tools.datatask.AbstractDataTransmission#getExportQueryInfo
	 * (com.kingdee.bos.Context)
	 */
	@Override
	public String getExportQueryInfo(Context ctx) {
		return super.getExportQueryInfo(ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kingdee.eas.tools.datatask.AbstractDataTransmission#
	 * getExportFilterForQuery(com.kingdee.bos.Context)
	 */
	@Override
	public FilterInfo getExportFilterForQuery(Context ctx) {
		return super.getExportFilterForQuery(ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kingdee.eas.tools.datatask.AbstractDataTransmission#submit(com.kingdee
	 * .eas.framework.CoreBaseInfo, com.kingdee.bos.Context)
	 */
	@Override
	public void submit(CoreBaseInfo model, Context ctx)
			throws TaskExternalException {
		handleBeforeSubmit(ctx, model);
		super.submit(model, ctx);
	}

	/**
	 * 数据提交前处理。如字段携带，计算等逻辑可重写些方法
	 * 
	 * @param ctx
	 * @param model
	 */
	protected void handleBeforeSubmit(Context ctx, CoreBaseInfo model) {

	}
}
