/*
 * @(#)CSubmitWriteBackProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.IWriteBack;
import com.kingdee.eas.st.common.app.botp.action.NamingWriteBackAction;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class CSubmitWriteBackProcessor implements IWriteBack {

	/**
	 * 描述：
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBack#writeBack(com.kingdee.bos.Context,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void writeBack(Context ctx, Object targetBillInfo, Object action)
			throws BOSException, EASBizException {
		System.out.println("CSubmitWriteBackProcessor's "
				+ ((NamingWriteBackAction) action).name()
				+ " is processing.  \r\n");
	}
}
