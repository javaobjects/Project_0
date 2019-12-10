package com.kingdee.eas.st.common.rpt.client;

import com.kingdee.util.enums.Enum;
import com.kingdee.util.enums.IntEnum;

//�ö�����Զ����Ӧ�ñ�ʵ���� Ϊ����ʹ IntEnum�е�getEnum public��
class MyIntEnum extends IntEnum {

	private MyIntEnum(String name, int value) {
		super(name, value);
		// TODO �Զ����ɹ��캯�����
	}

	public static String getEnumInstanceAlias(Class clazz, int value) {
		return IntEnum.getEnum(clazz, value).getAlias();
	}

	public static Enum getEnum(Class enumClass, int value) {
		return IntEnum.getEnum(enumClass, value);
	}

}
