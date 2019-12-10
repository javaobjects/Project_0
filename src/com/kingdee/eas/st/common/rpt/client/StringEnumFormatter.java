package com.kingdee.eas.st.common.rpt.client;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

public class StringEnumFormatter implements IValueFormatter {

	private Class stringEnumClass;

	private String intValue = null;

	public StringEnumFormatter(Class clazz) {
		this.stringEnumClass = clazz;
	}

	public Object format(Object value) {
		if (STQMUtils.isNotNull(value)) {
			intValue = (value.toString());
		}
		if (STQMUtils
				.isNotNull(MyStringEnum.getEnum(stringEnumClass, intValue))) {
			return MyStringEnum.getEnumInstanceAlias(this.stringEnumClass,
					intValue);
		} else {
			return value;
		}
	}

}
