/*
 * @(#)IValidateBizLogicThrowable.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.validate;

import com.kingdee.bos.dao.IObjectValue;

/**
 * 描述: 验证业务逻辑模板,定义验证业务逻辑的统一方法签名. 职责: 封装了针对业务实体形态的完整业务逻辑检查.
 * 
 * 注意: 1.数据性接口不依赖DataEntironment,只依赖接口参数 2.发生业务异常直接抛出.
 * 
 * @author daij date:2006-11-27
 *         <p>
 * @version EAS5.2.0
 */
public interface IValidateBizLogicThrowable {

	/**
	 * 
	 * 描述：验证业务逻辑.
	 * 
	 * @param data
	 *            待验证的值对象Info
	 * @throws Exception
	 * @author:daij 创建时间：2006-11-14
	 *              <p>
	 */
	public abstract void validateBizLogic(IObjectValue data) throws Exception;
}
