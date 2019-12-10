/*
 * @(#)Client.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class Client {

	/**
	 * ������
	 * 
	 * @param args
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public static void main(String[] args) {

		try {
			/*
			 * ==========================================================
			 * (һ)����AbstractWriteBackProcessor�̳�����
			 * ==========================================================
			 */

			System.out.println("(һ)����AbstractWriteBackProcessor�̳����� \r\n");
			System.out
					.println("============================================================");

			// ����WriteBackAction
			WriteBackAction action = new SubmitWriteBackAction();

			IWriteBackAction processor = new AWriteBackProcessor();

			action.acceptProcessor(processor);

			action.fireAction();

			// ����WriteBackAction��չCreateVouhcerWritBackAction

			System.out
					.println("����WriteBackAction��չCreateVouhcerWritBackAction \r\n");
			System.out
					.println("============================================================");
			action = new CreateVouhcerWritBackAction();

			processor = new BWriteBackProcessor();

			action.acceptProcessor(processor);

			action.fireAction();

			/*
			 * ==========================================================
			 * (��)����WriteBackDirector�̳�����
			 * ==========================================================
			 */
			System.out.println("(��)����WriteBackDirector�̳����� \r\n");
			System.out
					.println("============================================================");

			IWriteBack director = new CWriteBackDirector();

			director.writeBack(null, null, new NamingWriteBackAction(
					WriteBactActionTypeEnum.SUBMIT_WRITEBACKACTION));

			/*
			 * ==========================================================
			 * (��)����WriteBackProxy
			 * ==========================================================
			 */

			System.out.println("(��)����WriteBackProxy \r\n");
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
