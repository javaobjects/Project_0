/*
 * @(#)AWriteBackProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.AbstractWriteBackProcessor;
import com.kingdee.eas.st.common.app.botp.action.AuditWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.SubmitWriteBackAction;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class AWriteBackProcessor extends AbstractWriteBackProcessor {

	public void writeBack(AuditWriteBackAction action) throws BOSException,
			EASBizException {
		super.writeBack(action);

		System.out
				.println("AWriteBackProcessor's AuditWriteBackAction is processed. \r\n");
	}

	public void writeBack(SubmitWriteBackAction action) throws BOSException,
			EASBizException {
		super.writeBack(action);

		System.out
				.println("AWriteBackProcessor's SubmitWriteBackAction is processed. \r\n");
	}
}
