package com.kingdee.eas.st.common.listenerutils.base;

public interface IListenerExecute {

	/**
	 * �ڴ˷����б��뽫����Դ���뵽�����У����ҳ�ʼ��ҲҪ���������
	 * 
	 * this.addFilter(sor.getValue()); // ��ʼ���׶�ΪĿ�����ӹ���
	 * sor.addDataChangeListener(this); // ���������õ�����Դ��
	 * 
	 * @return �������ɵļ���������������Ƶ��������Ҫ�û�����ת������
	 */
	public Object excute();

}
