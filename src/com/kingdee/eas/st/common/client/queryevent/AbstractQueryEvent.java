/*
 * @(#)AbstractQueryEvent.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.queryevent;

import java.util.HashMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.st.common.client.STCommonQueryProcessor;

/**
 * 描述:
 * 
 * @author daij date:2007-4-19
 *         <p>
 * @version EAS5.2
 */
public abstract class AbstractQueryEvent extends HashMap {

	/**
	 * 当前的编辑器.
	 */
	private Object editor = null;

	private STCommonQueryProcessor processor = null;

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2007-4-19
	 *              <p>
	 */
	public AbstractQueryEvent() {
		super();
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param processor
	 * @author:daij 创建时间：2007-4-19
	 *              <p>
	 */
	public AbstractQueryEvent(STCommonQueryProcessor processor) {
		super();

		if (STQMUtils.isNotNull(processor)) {
			this.setProcessor(processor);
		}
	}

	public abstract void fireEvent() throws BOSException;

	/**
	 * 描述:设置editor的值。
	 * 
	 * @param editor
	 */
	public void setEditor(Object editor) {
		this.editor = editor;
	}

	/**
	 * 描述:@return 返回 editor。
	 */
	public Object getEditor() {
		return editor;
	}

	public void setProcessor(STCommonQueryProcessor processor) {
		this.processor = processor;
	}

	public STCommonQueryProcessor getProcessor() {
		return (STQMUtils.isNull(processor)) ? new STCommonQueryProcessor()
				: this.processor;
	}

	public OrgUnitInfo getMainOrgUnit() {
		if (editor instanceof KDBizPromptBox) {
			KDBizPromptBox f7 = (KDBizPromptBox) editor;
			Object o = f7.getMainBizOrgs().get(0);
			if (o instanceof OrgUnitInfo) {
				return (OrgUnitInfo) o;
			}
		}
		return null;
	}

	public OrgType getMainBizOrgType() {
		return null;
	}
}
