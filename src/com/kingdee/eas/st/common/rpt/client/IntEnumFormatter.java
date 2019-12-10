package com.kingdee.eas.st.common.rpt.client;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

public class IntEnumFormatter implements IValueFormatter {
	private Class intEnumClass;

	private int intValue = -12346;

	public IntEnumFormatter(Class clazz) {
		this.intEnumClass = clazz;
	}

	public Object format(Object value) {
		if (STQMUtils.isNotNull(value)) {
			intValue = Integer.parseInt(value.toString());
		}
		if (STQMUtils.isNotNull(MyIntEnum.getEnum(intEnumClass, intValue))) {
			return MyIntEnum.getEnumInstanceAlias(this.intEnumClass, intValue);
		} else {
			return value;
		}
	}

}
