/*
 * @(#)STWriteBackProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.AbstractWriteBackProcessor;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class STWriteBackProcessor extends AbstractWriteBackProcessor
		implements ISTWriteBackAction {

	/**
	 * 
	 * 描述：
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.sample.ISTWriteBackAction#writBack(com.kingdee.eas.st.common.app.botp.sample.CreateVouhcerWritBackAction)
	 */
	public void writBack(CreateVouhcerWritBackAction action)
			throws EASBizException, BOSException {
	}
}
