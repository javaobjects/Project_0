package com.kingdee.eas.st.common.rpt.client;

import com.kingdee.util.enums.Enum;
import com.kingdee.util.enums.StringEnum;

public class MyStringEnum extends StringEnum {

	protected MyStringEnum(String name, String value) {
		super(name, value);
		// TODO �Զ����ɹ��캯�����
	}

	public static String getEnumInstanceAlias(Class clazz, String value) {
		return StringEnum.getEnum(clazz, value).getAlias();
	}

	public static Enum getEnum(Class enumClass, String value) {
		return StringEnum.getEnum(enumClass, value);
	}

}
