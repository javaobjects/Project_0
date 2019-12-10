/*
 * @(#)IloadData.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.decorator;

/**
 * ����: UI����װ��ģ��. ְ��: 1.ΪUI����װ���ṩ�����˳��ͷ���ǩ��.
 * 2.��ģ��ӿڿ�ֱ����XXXUIʵ�ֻ������ڷ���UI���ε�Builderʵ��.
 * 
 * ע��: 1. �ڱ�ģ���д���editData��UI Element�Ľ���.
 * 
 * @author daij date:2006-11-3
 *         <p>
 * @version EAS5.2.0
 */
public interface ILoadDataTemplate {

	/**
	 * 
	 * ����������UI�󶨵�����. ְ��: 1. ����Դ: UIContext, editData. 2.
	 * ��super.loadFieldsǰ��UI����װ�ص�editData���е���,����������editData. 3. �������: 3.1)
	 * ����UIContext�е���������Ϣ��editData���ݽ��е���. 3.2) ��editData����Ĭ��ֵ����.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-3
	 *              <p>
	 */
	public abstract void trimEditData() throws Exception;

	/**
	 * 
	 * ������ɾ��UI������Ŀؼ�Listener. ְ��: ������super.loadFields���ֹ���ؼ�������ʱ����ؼ��¼���Ӧ.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-3
	 *              <p>
	 */
	public abstract void deleteUIElementListeners() throws Exception;

	/**
	 * 
	 * ������װ��editData��UI Elemnet. ְ��: ������������
	 * 1.����super.loadFields��Ӧ�ÿ�ܵ�DataBinderװ��editData��UI Element.
	 * 2.û����DataBinderע����������ֹ�װ�ص�UI Element.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-3
	 *              <p>
	 */
	public abstract void editDataToUIElement() throws Exception;

	/**
	 * 
	 * �������ָ�UI������Ŀؼ�Listener.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-3
	 *              <p>
	 */
	public abstract void addUIElementListeners() throws Exception;
}
