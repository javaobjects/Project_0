/*
 * @(#)IColumnChangeProcessor.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;

public interface IColumnChangeProcessor {

	public boolean process(KDTEditEvent e);
}
