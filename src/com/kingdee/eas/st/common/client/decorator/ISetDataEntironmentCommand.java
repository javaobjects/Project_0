/*
 * @(#)ISetDataEntironmentCommand.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.decorator;

/**
 * ����: װ��DataEntironment���� ְ��: 1.��IUIDirector������������´��������.
 * ��ͳһ��DataEntironment�´ﵽ���������Builder��. 2.DataEntironment����:
 * UIContext,editData,UIObject.
 * 
 * @author daij date:2006-11-8
 *         <p>
 * @version EAS5.2.0
 */
public interface ISetDataEntironmentCommand {
	/**
	 * 
	 * ������װ��DataEntironment.
	 * 
	 * @param dataEntironment
	 *            ���ε����ݰ�.
	 * @author:daij ����ʱ�䣺2006-11-8
	 *              <p>
	 */
	public abstract void loadDataEntironment(DataEntironment dataEntironment);
}
