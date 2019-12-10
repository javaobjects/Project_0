package com.kingdee.eas.st.common.util.total;

import com.kingdee.bos.dao.IObjectValue;

public class TotalUtils {

	public static String JOIN_FIELD_KEY = "JOIN_FIELD_KEY";

	public static int getHashCode(Object obj) {
		if (obj instanceof IObjectValue) {
			IObjectValue o = (IObjectValue) obj;

			if (o.containsKey("id") && o.get("id") != null) {
				return o.get("id").toString().hashCode();
			}
		} else if (obj != null) {
			return obj.toString().hashCode();
		}
		return 0;
	}
}
