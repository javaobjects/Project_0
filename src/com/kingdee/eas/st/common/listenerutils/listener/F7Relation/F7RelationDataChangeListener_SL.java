package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.st.common.listenerutils.tool.FilterUtils;

/**
 * �����Ǵ��������������sorΪ��ʱ��ʾ���ٹ���_showAll
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
		// ����ɵĹ���
		FilterUtils.removeFilter(dest, filedName);
		if (baseInfo != null) {
			key = baseInfo.getId().toString();
			FilterUtils.addFilter(dest, new FilterItemInfo(filedName, key,
					CompareType.EQUALS));
		}
	}
}
