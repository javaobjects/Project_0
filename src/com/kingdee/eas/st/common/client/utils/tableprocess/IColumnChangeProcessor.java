/*
 * @(#)IColumnChangeProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;

public interface IColumnChangeProcessor {

	public boolean process(KDTEditEvent e);
}
