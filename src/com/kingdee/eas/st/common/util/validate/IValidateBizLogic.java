/*
 * @(#)IValidateBizLogic.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.validate;

import java.util.Map;

import com.kingdee.bos.dao.IObjectValue;

/**
 * 描述: 验证业务逻辑模板,定义验证业务逻辑的统一方法签名. 职责: 封装了针对业务实体形态的完整业务逻辑检查.
 * 
 * 注意: 1.数据性接口不依赖DataEntironment,只依赖接口参数
 * 2.本接口对业务异常的处理方式为：依次验证所有的检查项目，并将业务异常收集到Map中，返回boolean = (Map.size() == 0)
 * 3.Map中只能放入系统可控的业务异常,验证中的run time异常必须通过throws抛出.
 * 
 * @author daij date:2006-11-14
 *         <p>
 * @version EAS5.2.0
 */
public interface IValidateBizLogic {

	/**
	 * 
	 * 描述：验证业务逻辑.
	 * 
	 * @param data
	 *            待验证的值对象Info
	 * @param bizLogicExceptions
	 *            业务逻辑异常
	 * @return boolean 是否验证通过
	 * @throws Exception
	 * @author:daij 创建时间：2006-11-14
	 *              <p>
	 */
	public abstract boolean validateBizLogic(IObjectValue data,
			Map bizLogicExceptions) throws Exception;
}
