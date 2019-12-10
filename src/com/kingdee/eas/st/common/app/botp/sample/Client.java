/*
 * @(#)Client.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.app.botp.IWriteBack;
import com.kingdee.eas.st.common.app.botp.WriteBackProxy;
import com.kingdee.eas.st.common.app.botp.action.AuditWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.IWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.MixWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.NamingWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.SubmitWriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.WriteBackAction;
import com.kingdee.eas.st.common.app.botp.action.WriteBactActionTypeEnum;

/**
 * 描述:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class Client {

	/**
	 * 描述：
	 * 
	 * @param args
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public static void main(String[] args) {

		try {
			/*
			 * ==========================================================
			 * (一)测试AbstractWriteBackProcessor继承线索
			 * ==========================================================
			 */

			System.out.println("(一)测试AbstractWriteBackProcessor继承线索 \r\n");
			System.out
					.println("============================================================");

			// 测试WriteBackAction
			WriteBackAction action = new SubmitWriteBackAction();

			IWriteBackAction processor = new AWriteBackProcessor();

			action.acceptProcessor(processor);

			action.fireAction();

			// 测试WriteBackAction扩展CreateVouhcerWritBackAction

			System.out
					.println("测试WriteBackAction扩展CreateVouhcerWritBackAction \r\n");
			System.out
					.println("============================================================");
			action = new CreateVouhcerWritBackAction();

			processor = new BWriteBackProcessor();

			action.acceptProcessor(processor);

			action.fireAction();

			/*
			 * ==========================================================
			 * (二)测试WriteBackDirector继承线索
			 * ==========================================================
			 */
			System.out.println("(二)测试WriteBackDirector继承线索 \r\n");
			System.out
					.println("============================================================");

			IWriteBack director = new CWriteBackDirector();

			director.writeBack(null, null, new NamingWriteBackAction(
					WriteBactActionTypeEnum.SUBMIT_WRITEBACKACTION));

			/*
			 * ==========================================================
			 * (三)测试WriteBackProxy
			 * ==========================================================
			 */

			System.out.println("(三)测试WriteBackProxy \r\n");
			System.out
					.println("============================================================");

			XXXBillInfo wb = new XXXBillInfo();
			XXXBillEntryCollection ws = new XXXBillEntryCollection();

			XXXBillEntryInfo w = new XXXBillEntryInfo();
			w.setSourceBillId("O3MJUAEPEADgAAIBwKgSFYCfLLQ=");
			ws.add(w);

			w = new XXXBillEntryInfo();
			w.setSourceBillId("LYgwOAEPEADgAAHtwKgSQ9rGhVw=");
			ws.add(w);

			wb.getEntries().addCollection(ws);

			WriteBackAction writeBackAction = new AuditWriteBackAction();

			NamingWriteBackAction namingWriteBackAction = new NamingWriteBackAction(
					WriteBactActionTypeEnum.AUDIT_WRITEBACKACTION);

			WriteBackProxy.getInstance(ws).writeBack(
					null,
					wb,
					new MixWriteBackAction(writeBackAction,
							namingWriteBackAction));

		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
}
