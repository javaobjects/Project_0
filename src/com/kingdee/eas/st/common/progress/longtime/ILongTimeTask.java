/*
 * @(#)ILongTimeTask.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.progress.longtime;

/**
 * 描述:
 * 
 * @author ryanzhou date:2005-10-12
 *         <p>
 * @version EAS5.0
 */
public interface ILongTimeTask {
	public Object exec() throws Exception;

	public void afterExec(Object result) throws Exception;
}
