/*
 * @(#)ITableProcessor.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;

public interface ITableProcessor {

	void beforeProcess(KDTEditEvent e) throws Exception;

	void afterProcess(KDTEditEvent e) throws Exception;
}
