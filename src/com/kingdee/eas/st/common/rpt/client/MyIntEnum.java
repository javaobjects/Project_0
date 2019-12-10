package com.kingdee.eas.st.common.rpt.client;

import com.kingdee.util.enums.Enum;
import com.kingdee.util.enums.IntEnum;

//该对象永远都不应该被实例化 为的是使 IntEnum中的getEnum public。
class MyIntEnum extends IntEnum {

	private MyIntEnum(String name, int value) {
		super(name, value);
		// TODO 自动生成构造函数存根
	}

	public static String getEnumInstanceAlias(Class clazz, int value) {
		return IntEnum.getEnum(clazz, value).getAlias();
	}

	public static Enum getEnum(Class enumClass, int value) {
		return IntEnum.getEnum(enumClass, value);
	}

}
