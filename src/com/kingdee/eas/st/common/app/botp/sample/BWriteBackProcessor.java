/*
 * @(#)BWriteBackProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.action.AuditWriteBackAction;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class BWriteBackProcessor extends STWriteBackProcessor {

	/**
	 * 描述：
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.sample.STWriteBackProcessor#writBack(com.kingdee.eas.st.common.app.botp.sample.CreateVouhcerWritBackAction)
	 */
	public void writBack(CreateVouhcerWritBackAction action)
			throws EASBizException, BOSException {
		super.writBack(action);

		System.out.println("CreateVouhcerWritBackAction is processed. \r\n");
	}

	/**
	 * 描述：
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.AbstractWriteBackProcessor#writeBack(com.kingdee.eas.st.common.app.botp.action.AuditWriteBackAction)
	 */
	public void writeBack(AuditWriteBackAction action) throws BOSException,
			EASBizException {
		super.writeBack(action);

		System.out
				.println("BWriteBackProcessor's AuditWriteBackAction is processed. \r\n");
	}

}
