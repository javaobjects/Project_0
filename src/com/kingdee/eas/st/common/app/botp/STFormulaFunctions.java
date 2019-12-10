/*
 * @(#)SCMFormulaFunctions.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.kscript.KScriptException;
import com.kingdee.bos.service.formula.api.IFormulaFunctions;
import com.kingdee.bos.service.formula.api.InvokeFunctionException;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUnitHelper;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.st.common.util.AsstActUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 描述: 封装ST Botp转换时使用的自定义函数.
 * 
 * @author daij date:2006-8-14
 *         <p>
 * @version EAS5.1.1
 */
public class STFormulaFunctions implements IFormulaFunctions {

	// 自定义函数的组名称
	public static final String CATEGORY_ST_OPTIONS = EASResource
			.getString("com.kingdee.eas.st.common.STResource.ST_BOTPFORMULA_CATEGORY");

	// 自定义函数列表
	private Vector funcInfos = new Vector();

	public STFormulaFunctions() {
		// 根据弱类型的往来户Id返回往来户Info
		funcInfos
				.add(new FuncInfo(
						"__BOTgetAsstActInfoBySysId",
						CATEGORY_ST_OPTIONS,
						EASResource
								.getString("com.kingdee.eas.st.common.STResource.ST_BOTPFORMULA_GEPURTINVOICEQTY")));
		funcInfos
				.add(new FuncInfo(
						"__BOTgetFullProperty",
						CATEGORY_ST_OPTIONS,
						EASResource
								.getString("com.kingdee.eas.st.common.STResource.ST_BOTPFORMULA_GETFULLPROPER")));
		funcInfos
				.add(new FuncInfo(
						"__BOTgetOrgUnitByFullOrg",
						CATEGORY_ST_OPTIONS,
						EASResource
								.getString("com.kingdee.eas.st.common.STResource.ST_BOTPFORMULA_GetOrgUnitByFullOrg")));
	}

	/**
	 * 
	 * 描述：获取所有自定义函数的名称.
	 * 
	 * @author:daij
	 * @see com.kingdee.bos.service.formula.api.IFormulaFunctions#getAllFuncNames()
	 */
	public String[] getAllFuncNames() {
		String[] funcNames = new String[funcInfos.size()];
		for (int i = 0; i < funcInfos.size(); i++) {
			funcNames[i] = ((FuncInfo) funcInfos.get(i)).funcName;
		}
		return funcNames;
	}

	/**
	 * 
	 * 描述：按函数名称获取自定义函数的组.
	 * 
	 * @author:daij
	 * @see com.kingdee.bos.service.formula.api.IFormulaFunctions#getFuncCategory(java.lang.String)
	 */
	public String getFuncCategory(String funcName) {
		if (funcName == null) {
			return null;
		}
		for (int i = 0; i < funcInfos.size(); i++) {
			if (funcName.equals(((FuncInfo) funcInfos.get(i)).funcName)) {
				return ((FuncInfo) funcInfos.get(i)).funcCategory;
			}
		}
		return null;
	}

	/**
	 * 
	 * 描述：按函数名称获取自定义函数的描述信息.
	 * 
	 * @author:daij
	 * @see com.kingdee.bos.service.formula.api.IFormulaFunctions#getFuncDesc(java.lang.String)
	 */
	public String getFuncDesc(String funcName) {
		if (funcName == null) {
			return null;
		}
		for (int i = 0; i < funcInfos.size(); i++) {
			if (funcName.equals(((FuncInfo) funcInfos.get(i)).funcName)) {
				return ((FuncInfo) funcInfos.get(i)).funcDesc;
			}
		}
		return null;
	}

	/**
	 * 描述：按函数名称判断该自定义函数是否已经存在.
	 */
	public boolean existFunction(String funcName) {
		if (funcName == null) {
			return false;
		}
		for (int i = 0; i < funcInfos.size(); i++) {
			if (funcName.equals(((FuncInfo) funcInfos.get(i)).funcName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 描述：按函数名称和参数列表执行自定义函数.
	 * 
	 * @author:daij
	 * @see com.kingdee.bos.kscript.IFunctionProvider#evalFunction(java.lang.String,
	 *      java.util.List)
	 */
	public Object evalFunction(String funcName, List paramList)
			throws KScriptException {
		if (funcName == null) {
			return null;
		}

		try {
			// 解析出服务端上下文.
			if (paramList != null && paramList.size() > 0) {
				Context ctx = (Context) paramList.get(0);
				// 按自定义函数名分类执行.
				return _evalFunction(ctx, funcName, paramList);
			}
		} catch (InvokeFunctionException e) {
			throw new KScriptException(e.toString());
		} catch (BOSException e) {
			throw new KScriptException(e.toString());
		} catch (EASBizException e) {
			throw new KScriptException(e.toString());
		}
		return null;
	}

	// 按自定义函数名分类执行.
	private Object _evalFunction(Context ctx, String funcName, List paramList)
			throws InvokeFunctionException, BOSException, EASBizException {
		if ("__BOTgetAsstActInfoBySysId".equals(funcName)) {
			return this.getAsstActInfoBySysId(ctx, paramList);
		} else if ("__BOTgetFullProperty".equals(funcName)) {
			return this.getFullProperty(ctx, paramList);
		} else if ("__BOTgetOrgUnitByFullOrg".equals(funcName)) {
			return this.getOrgUnitByFullOrg(ctx, paramList);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 描述：根据弱类型的往来户Id返回往来户Info
	 * 
	 * @param paramList
	 *            参数列表
	 * 
	 * @param ctx
	 *            服务端上下文.
	 * @param acctActId
	 *            往来户的弱类型Id (String)
	 * @return Object AcctActInfo
	 * @throws InvokeFunctionException
	 * @throws BOSException
	 * @author:daij 创建时间：2007-2-3
	 *              <p>
	 */
	private Object getAsstActInfoBySysId(Context ctx, List paramList)
			throws InvokeFunctionException, BOSException {

		// 检查参数.
		if (paramList == null || paramList.size() != 2) {
			throw new InvokeFunctionException("__BOTgetAsstActInfoBySysId",
					InvokeFunctionException.LESS_PARAM);
		}

		Object o = paramList.get(1);

		String asstActId = null;
		if (o != null) {
			asstActId = o.toString();
		}

		IObjectValue info = null;
		if (!StringUtils.isEmpty(asstActId)) {

			info = STQMUtils.getDynamicObject(ctx, asstActId);

			String asstActTypeString = AsstActUtils
					.getAsstActTypeString(asstActId);
		}

		return info;
	}

	/**
	 * 描述：根据弱类型的对象Id返回对象Info
	 * 
	 * @param ctx
	 * @param paramList
	 * @return
	 * @throws InvokeFunctionException
	 * @throws BOSException
	 */
	private Object getFullProperty(Context ctx, List paramList)
			throws InvokeFunctionException, BOSException {

		// 检查参数.
		if (paramList == null || paramList.size() != 2) {
			throw new InvokeFunctionException("__BOTgetFullProperty",
					InvokeFunctionException.LESS_PARAM);
		}

		Object o = paramList.get(1);

		String asstActId = null;
		if (o != null) {
			if (o instanceof CoreBaseInfo) {
				asstActId = ((CoreBaseInfo) o).getId().toString();
			} else {
				asstActId = o.toString();
			}
		}

		IObjectValue info = null;
		if (!StringUtils.isEmpty(asstActId)) {
			info = STQMUtils.getDynamicObject(ctx, asstActId);
		}
		return info;
	}

	private OrgUnitInfo getOrgUnitByFullOrg(Context ctx, List paramList)
			throws InvokeFunctionException, EASBizException, BOSException {
		if (paramList.size() == 3) {
			Object orgNumberObj = paramList.get(1);
			Object orgTypeObj = paramList.get(2);
			if (orgNumberObj instanceof String && orgTypeObj instanceof OrgType) {
				String orgUnitId = null;
				String orgNumber = (String) orgNumberObj;
				String sql = "select fid from T_ORG_BaseUnit where fnumber='"
						+ orgNumber + "'";
				IRowSet rs = DbUtil.executeQuery(ctx, sql);
				try {
					if (rs.next()) {
						orgUnitId = rs.getString("fid");
					}
				} catch (SQLException e) {
					throw new SQLDataException(e);
				}
				if (!StringUtils.isEmpty(orgUnitId)) {
					return NewOrgUnitHelper.getTypedOrgUnit(ctx, orgUnitId,
							(OrgType) orgTypeObj);
				}
			}
		} else {
			throw new InvokeFunctionException("__BOTgetOrgUnitByFullOrg",
					InvokeFunctionException.LESS_PARAM);
		}

		return null;
	}

	/**
	 * 函数信息
	 */
	class FuncInfo {
		String funcName, funcCategory, funcDesc;

		public FuncInfo(String name, String category, String desc) {
			funcName = name;
			funcCategory = category;
			funcDesc = desc;
		}

		public FuncInfo() {
		}

		public void setFuncName(String name) {
			funcName = name;
		}

		public void setFuncDesc(String desc) {
			funcDesc = desc;
		}

		public void setFuncCatetory(String category) {
			funcCategory = category;
		}
	}
}
