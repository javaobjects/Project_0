package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.st.common.listenerutils.tool.FilterUtils;

/**
 * 此类是处理特殊情况，在sor为空时表示不再过滤_showAll
 * 
 * @author jiwei_xiao
 * @date 2010-03-24
 * 
 */
public class F7RelationDataChangeListener_SL extends
		F7RelationDataChangeListener {

	public F7RelationDataChangeListener_SL(KDBizPromptBox sor,
			KDBizPromptBox dest, String filedName) {
		super(sor, dest, filedName);
	}

	protected void addFilter(Object value) {
		String key = "";
		AbstractCoreBaseInfo baseInfo = (AbstractCoreBaseInfo) value;
		// 清除旧的过滤
		FilterUtils.removeFilter(dest, filedName);
		if (baseInfo != null) {
			key = baseInfo.getId().toString();
			FilterUtils.addFilter(dest, new FilterItemInfo(filedName, key,
					CompareType.EQUALS));
		}
	}
}
