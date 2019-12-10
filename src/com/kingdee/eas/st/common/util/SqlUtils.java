package com.kingdee.eas.st.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SqlUtils {
	// 得到别名在别名组中的index
	public static int getIndexInRAWSQLCOLUMNALIAS(String alias,
			String[] columnAlias) {
		int i = 0;
		for (; i < columnAlias.length; i++) {
			if (columnAlias[i].trim().equals(alias.trim())) {
				break;
			}
		}
		if (i >= columnAlias.length) {
			return -1;
		} else {
			return i;
		}

	}

	// 得到别名对应的名
	public static String getColumnNamebyAlias(String alias,
			String[] columnAlias, List columnNameList) {
		int i = getIndexInRAWSQLCOLUMNALIAS(alias, columnAlias);
		if (i < 0) {
			return "";
		} else {
			return (String) columnNameList.get(i);
		}
	}

	/**
	 * 得到sql语句中的选取列,就是那种不已F开头的那种
	 * 
	 * @param sql
	 * @return
	 */
	public static List parseSqlForColumnName(String sql) {
		ArrayList columnNameList = new ArrayList();
		if (sql == null || sql.length() == 0 || sql.indexOf(" FROM ") <= 0) {
			throw new RuntimeException("sqlstring is invalid!");
		}

		String[] s = sql.substring(0, sql.indexOf(" FROM ") + 1).split(",");
		for (int i = 0; i < s.length; i++) {
			sql = s[i].substring(s[i].indexOf(" AS ") + 4).trim();
			columnNameList.add(sql);
		}
		return columnNameList;
	}

	// 返回From子句之后的字符串 如 "FROM ...."
	public static String getWhereSqlString(String sql) {
		if (sql.indexOf(" FROM ") > 0) {
			return sql.substring(sql.indexOf(" FROM "));
		} else {
			return "";
		}

	}

	// 返回用逗号隔开的各列，如果列为空的话，则返回“”
	public static String getFieldsStringSplittedByComma(
			Collection fiedsConllection) {
		Iterator ite = fiedsConllection.iterator();
		StringBuffer sb = new StringBuffer("");
		while (ite.hasNext()) {
			String temp = ite.next().toString().trim();
			if (temp.equals("")) {
				continue;
			}
			sb.append(",");
			sb.append(temp);
		}
		String result = "";
		if (!sb.toString().trim().equals("")) {

			result = sb.toString().trim().substring(1);// 去掉分号
		}
		return result;

	}

}
