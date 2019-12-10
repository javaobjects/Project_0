package com.kingdee.eas.st.common.rpt.client;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

public class BooleanFormatter implements IValueFormatter {

	public BooleanFormatter() {
	}

	public Object format(Object value) {
		int intValue = 0;
		if (STQMUtils.isNotNull(value)) {
			if (value instanceof Boolean) {
				intValue = (((Boolean) value).booleanValue()) ? 1 : 0;
			} else {
				intValue = Integer.parseInt(value.toString());
			}
		}
		return Boolean.valueOf((intValue == 1));
	}

}
