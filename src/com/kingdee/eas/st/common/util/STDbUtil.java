package com.kingdee.eas.st.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.db.SQLUtils;

public class STDbUtil extends DbUtil {

	private static Logger logger = Logger.getLogger(STDbUtil.class);

	public static int executeUpdate(Context ctx, String sql, Object[] params)
			throws BOSException {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = EJBFactory.getConnection(ctx);
		} catch (SQLException exc) {
			SQLUtils.cleanup(conn);
			throw new BOSException(CONFIG_EXCEPTION, exc);
		}

		try {
			ps = conn.prepareStatement(sql);

			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}

			return ps.executeUpdate();
		} catch (SQLException exc) {
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < params.length; i++) {
				sb.append("param ").append(i).append(" is:").append(params[i]);
			}
			logger.error("222 sql is:" + sql + " param is:" + sb.toString(),
					exc);
			throw new BOSException("Sql222 execute exception : " + sql, exc);
		} finally {
			SQLUtils.cleanup(ps, conn);
		}
	}
}
