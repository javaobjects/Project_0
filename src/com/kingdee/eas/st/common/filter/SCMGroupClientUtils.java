package com.kingdee.eas.st.common.filter;

import java.util.LinkedHashSet;
import java.util.StringTokenizer;

import com.kingdee.eas.framework.ObjectBaseInfo;

/**
 * 描述:
 * 
 * @author paul date:2006-9-6
 *         <p>
 * @version EAS5.2
 */
public final class SCMGroupClientUtils {

	public static LinkedHashSet getKeyIdList(String ids) {
		return getKeyIdList(ids, ";");
	}

	public static LinkedHashSet getKeyIdList(String ids, String delim) {
		StringTokenizer stk = new StringTokenizer(ids, delim);
		LinkedHashSet result = new LinkedHashSet();
		while (stk.hasMoreTokens()) {
			result.add(String.valueOf(stk.nextToken()).trim());
		}
		return result;
	}

	public static String getKeyIdList(ObjectBaseInfo[] vos, String delim) {
		if (vos == null || vos.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < vos.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(vos[i].getId().toString());
		}
		return sb.toString();
	}

	/**
	 * 描述：单据ID列表，用于SQL中的条件IN
	 * 
	 * @param ids
	 * @return
	 * @author:paul 创建时间：2007-4-5
	 *              <p>
	 */
	public static String getKeyIdList(String[] ids) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append("'").append(ids[i]).append("'");
		}

		return sb.toString();
	}

}
