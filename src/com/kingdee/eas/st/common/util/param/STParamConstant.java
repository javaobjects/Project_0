/*
 * @(#)STParamConstant.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.param;

import com.kingdee.util.StringUtils;

/**
 * 描述: ST参数信息常量列表
 * 
 * 1. 参数名称. 2. 参数的固定Number
 * 
 * ST参数编号规则：
 * 
 * 1. 与单据类型相关的参数采取： A + B + C 规则：
 * 
 * A: ST系统参数前最缀 - STParamConstant.STSYSPARAMS_PRE B: 系统参数的表意标识串 - 各个参数不同，比如:
 * IsAuditAfterSubmit 是否保存后审核. C: 获取系统参数运行时传入当前单据的类型Number.
 * 
 * 读取满足ST参数编码规则的参数编号请调用：STParamConstant.getSTSysParamNumberByBillType方法.
 * 
 * @author daij date:2006-11-28
 *         <p>
 * @version EAS5.2.0
 */
public abstract class STParamConstant {

	// 检斤类型编码作为系统参数的标识Key

	public static final String STSYSPARAMS_PREFIX = "STSYS_";

	/**
	 * 是否提交即审核.
	 */
	public static final String STSYSPARAMS_KEYID_ISAUDITSUBMIT = "IsAuditAfterSubmit";

	/**
	 * 是否确认.
	 */
	public static final String STSYSPARAMS_KEYID_ISAFFIRM = "IsAffirm";

	/**
	 * 销售计划是否启用生效日期
	 */
	public static final String STSYSPARAMS_KEYID_ISEFFECTEDATE = "STSALEPLAN001";

	/**
	 * 
	 * 描述：按单据类型和参数表意标识串返回符合ST参数编号规则的参数编码.
	 * 
	 * ST参数编号规则：A + B + C
	 * 
	 * A: ST系统参数前最缀 - STParamConstant.STSYSPARAMS_PREFIX B: 系统参数的表意标识串 -
	 * 各个参数不同，比如: IsAuditAfterSubmit 是否保存后审核. C: 获取系统参数运行时传入的类型Number.
	 * 
	 * @param keyId
	 *            参数的标识串
	 * @param typeNumber
	 *            类型的Number
	 * @return ST系统系统参数的编码
	 * @author:daij 创建时间：2006-12-19
	 *              <p>
	 */
	public static final String getSTSysParamNumberByType(String keyId,
			String typeNumber) {
		StringBuffer sb = new StringBuffer();
		if (!StringUtils.isEmpty(keyId) && !StringUtils.isEmpty(typeNumber)) {
			sb.append(STSYSPARAMS_PREFIX).append(keyId).append("_").append(
					typeNumber);
		}
		return sb.toString();
	}
}
