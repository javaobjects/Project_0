/*
 * @(#)CWriteBackDirector.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.eas.st.common.app.botp.AbstractWriteBackDirector;
import com.kingdee.eas.st.common.app.botp.action.WriteBactActionTypeEnum;
import com.kingdee.eas.st.common.util.director.AssortDirector;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class CWriteBackDirector extends AbstractWriteBackDirector {

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public CWriteBackDirector() {
		super();
	}

	/**
	 * 描述：
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.director.IDirectable#setupDirector()
	 */
	public void setupDirector() {
		Map ps = new HashMap();

		ps.put(WriteBactActionTypeEnum.AUDIT_WRITEBACKACTION,
				new CAuditWriteBackProcessor());

		ps.put(WriteBactActionTypeEnum.SUBMIT_WRITEBACKACTION,
				new CSubmitWriteBackProcessor());

		this.director = new AssortDirector(ps);
	}

}
