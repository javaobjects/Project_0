/*
 * @(#)PermissionUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.st.common.ISTBillBase;

//根据BosType获取新增权限名
public class PermissionUtils {
	public static String getAddNewPermItemName(ICoreBase coreBase,
			String bosType) {

		String permItemName = null;

		if (coreBase instanceof ISTBillBase && STQMUtils.isNotNull(bosType)) {
			try {

				ISTBillBase iSTBillBase = (ISTBillBase) coreBase;

				permItemName = iSTBillBase.getAddNewPermItemName(bosType);

			} catch (Exception e) {

			}
		}
		return permItemName;
	}

	public static String getViewPermItemName(ICoreBase coreBase, String bosType) {

		String permItemName = null;

		if (coreBase instanceof ISTBillBase && STQMUtils.isNotNull(bosType)) {
			try {

				ISTBillBase iSTBillBase = (ISTBillBase) coreBase;

				permItemName = iSTBillBase.getViewPermItemName(bosType);

			} catch (Exception e) {

			}
		}
		return permItemName;
	}
}
