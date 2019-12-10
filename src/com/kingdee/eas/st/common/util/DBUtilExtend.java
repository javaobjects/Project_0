package com.kingdee.eas.st.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.db.SQLUtils;

/***
 * This is the class for import
 * 
 * @author KG
 * 
 */
public class DBUtilExtend extends DbUtil {

	/**
	 * 批量插入数据
	 * 
	 * @param ctx
	 * @param sql
	 * @param paramsList
	 * @throws BOSException
	 */
	public static void _executeBatch(Context ctx, String sql, List paramsList,
			Map defaultVal, Map excelVal) throws BOSException {
		_executeBatchAndReturn(ctx, sql, paramsList, defaultVal, excelVal);
	}

	public static int[] _executeBatchAndReturn(Context ctx, String sql,
			List paramsList, Map defaultVal, Map excelVal) throws BOSException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = EJBFactory.getConnection(ctx);
			ps = conn.prepareStatement(sql);

			List lists = null;
			int k = 1;
			boolean flag = false;
			for (int i = 0; i < paramsList.size(); i++) {
				lists = (List) paramsList.get(i);
				for (int j = 0; j < lists.size(); j++) {
					if (lists.get(j) != null)
						ps.setObject(j + 1, lists.get(j));
					else {
						ps.setNull(j + 1, Types.VARCHAR);
					}
				}
				ps.addBatch();

				if (i == k * 8000) {
					flag = true;
				}
				if ((i > k * 8000) && (flag)) {
					ps.executeBatch();
					k++;
					flag = false;
					ps.clearBatch();
				}
			}

			if (!flag)
				return ps.executeBatch();
		} catch (SQLException exc) {
			throw new BOSException("Sql222 execute exception : " + sql, exc);
		} finally {
			SQLUtils.cleanup(ps, conn);
		}
		return new int[0];
	}
}
