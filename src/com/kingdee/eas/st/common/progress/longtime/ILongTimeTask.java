/*
 * @(#)ILongTimeTask.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.progress.longtime;

/**
 * ����:
 * 
 * @author ryanzhou date:2005-10-12
 *         <p>
 * @version EAS5.0
 */
public interface ILongTimeTask {
	public Object exec() throws Exception;

	public void afterExec(Object result) throws Exception;
}
