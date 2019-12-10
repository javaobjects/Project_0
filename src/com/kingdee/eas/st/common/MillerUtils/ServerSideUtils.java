package com.kingdee.eas.st.common.MillerUtils;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����˹��߼�
 * 
 * @author miller_xiao
 * 
 */
public class ServerSideUtils {
	/**
	 * ��ͨ������ID����ȡ���ڱ���
	 * 
	 * @param ctx
	 * @param pk
	 * @return
	 */
	public static String getTableNameByPK(Context ctx, String pk) {
		BOSUuid id = BOSUuid.read(pk);
		BOSObjectType bosType = id.getType();
		IMetaDataLoader metaDataLoader = MetaDataLoaderFactory
				.getLocalMetaDataLoader(ctx);
		EntityObjectInfo entity = metaDataLoader.getEntity(bosType);
		DataTableInfo updateTableInfo = entity.getTable();
		String tableName = updateTableInfo.getName();

		return tableName;
	}

	/**
	 * ͨ������ID����ȡ��Ӧ�ı���
	 * 
	 * @param ctx
	 * @param pk
	 * @return
	 * @throws BOSException
	 */
	public static String getNumberById(Context ctx, String pk)
			throws BOSException {
		StringBuffer sql = new StringBuffer();
		sql.append("select FNumber from ").append(getTableNameByPK(ctx, pk))
				.append(" where fid = ? \r\n");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(),
				new Object[] { pk });
		try {
			if (rs.next()) {
				return rs.getString("FNumber");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return null;
	}
}
