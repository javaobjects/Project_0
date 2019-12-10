/**
 * 
 */
package com.kingdee.eas.st.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertOrUpdateSqlOrganizer {
	// public class InsertOrUpdateSqlOrganizerType{
	// public static InsertOrUpdateSqlOrganizerType INSERT=new
	// InsertOrUpdateSqlOrganizerType("INSERT");
	// public static InsertOrUpdateSqlOrganizerType UPDATE=new
	// InsertOrUpdateSqlOrganizerType("UPDATE");
	// private InsertOrUpdateSqlOrganizerType(String type){}
	//		
	// }

	boolean insertOrUpdate = true;// 默认设置为insert
	// 参数篓
	StringBuffer sql = new StringBuffer("");
	List params = new ArrayList();

	public boolean isInsertOrUpdate() {
		return insertOrUpdate;
	}

	public InsertOrUpdateSqlOrganizer(boolean insertOrUpdate) {
		this.insertOrUpdate = insertOrUpdate;
	}

	public InsertOrUpdateSqlOrganizer(boolean insertOrUpdate, String sql,
			List params) {
		this.insertOrUpdate = insertOrUpdate;
		if (sql != null) {
			this.sql.append(sql);
		}
		if (params != null) {
			this.params.addAll(params);
		}
	}

	public void insertFieldsAndArgs(String[] fields, List args) {
		if (this.insertOrUpdate == true) {// 为insert语句
			String temptemp = "";
			String temp = sql.substring(0, sql.indexOf(" VALUES"));

			temptemp = temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));

			List listFields = new ArrayList(Arrays.asList(temptemp.split(",")));
			for (int i = 0; i < fields.length; i++) {
				listFields.add(fields[i]);
			}
			String tempComma = sql.substring(sql.indexOf(" VALUES"));
			temptemp = tempComma.substring(tempComma.indexOf("(") + 1,
					tempComma.indexOf(")"));

			List listComma = new ArrayList(Arrays.asList(temptemp.split(",")));
			for (int i = 0; i < fields.length; i++) {
				listComma.add("?");
			}
			String sqlString = this.sql.substring(0, this.sql.indexOf("("))
					+ " ( "
					+ SqlUtils.getFieldsStringSplittedByComma(listFields)
					+ " ) VALUES ("
					+ SqlUtils.getFieldsStringSplittedByComma(listComma) + " )";
			this.sql = new StringBuffer(sqlString);

		} else {// 如果为update语句
			String temp = sql.substring(sql.indexOf(" SET ") + 4);
			if (temp.indexOf(" WHERE ") >= 0) {
				int lenField = temp.indexOf(" WHERE ");
				temp = temp.substring(0, lenField + 1);
			}
			List list = new ArrayList(Arrays.asList(temp.split(",")));
			for (int i = 0; i < fields.length; i++) {
				list.add(fields[i] + "=?");
			}
			String sqlString = this.sql.substring(0, sql.indexOf(" SET"))
					+ " SET " + SqlUtils.getFieldsStringSplittedByComma(list);
			if (this.sql.indexOf(" WHERE ") >= 0) {
				sqlString = sqlString
						+ this.sql.substring(this.sql.indexOf(" WHERE "));

			}
			this.sql = new StringBuffer(sqlString);
		}
		this.params.addAll(args);
	}

	public String getSqlString() {
		return sql.toString();
	}

	public List getSqlArgsParams() {
		return this.params;
	}

}