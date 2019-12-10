package com.kingdee.eas.st.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SqlUtils {
	// �õ������ڱ������е�index
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

	// �õ�������Ӧ����
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
	 * �õ�sql����е�ѡȡ��,�������ֲ���F��ͷ������
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

	// ����From�Ӿ�֮����ַ��� �� "FROM ...."
	public static String getWhereSqlString(String sql) {
		if (sql.indexOf(" FROM ") > 0) {
			return sql.substring(sql.indexOf(" FROM "));
		} else {
			return "";
		}

	}

	// �����ö��Ÿ����ĸ��У������Ϊ�յĻ����򷵻ء���
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

			result = sb.toString().trim().substring(1);// ȥ���ֺ�
		}
		return result;

	}

}
