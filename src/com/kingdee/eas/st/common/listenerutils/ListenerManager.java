package com.kingdee.eas.st.common.listenerutils;

import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.listenerutils.base.IListenerExecute;

/**
 * ��������������������ɴ�manager���ɵ����м���
 * 
 * @author mingming_fan
 * 
 */
public class ListenerManager {
	private boolean clearData = true;
	private boolean eanbleListener = true;

	/**
	 * �Ƿ��������ݣ�������ʵ�־���������ֻ�Ǳ�־λ
	 * 
	 * @return
	 */
	public boolean isClearData() {
		return clearData;
	}

	/**
	 * �Ƿ��������ݣ�������ʵ�־���������ֻ�Ǳ�־λ
	 * 
	 * @param clearData
	 */
	public void setClearData(boolean clearData) {
		this.clearData = clearData;
	}

	/**
	 * �����Ƿ����ã�������ʵ�־���������ֻ�Ǳ�־λ
	 * 
	 * @return
	 */
	public boolean isEanbleListener() {
		return eanbleListener;
	}

	/**
	 * �����Ƿ����ã�������ʵ�־���������ֻ�Ǳ�־λ
	 * 
	 * @param eanbleListener
	 */
	public void setEanbleListener(boolean eanbleListener) {
		this.eanbleListener = eanbleListener;
	}

	/**
	 * ��Ӽ���������ʼ������.������ע�ᵽ�����У��Ա������ͳһʹ���Լ���״̬�� ���磺�Ƿ��������ݣ������Ƿ����á�
	 * 
	 * @param baseDataListener
	 * @return �������ɵļ������������ɵļ���������죬�ʷ���Object��ʹ��������ת��
	 */
	public Object addListener(BaseDataListener baseDataListener) {
		baseDataListener.setListenerManager(this);
		return baseDataListener.excute();
	}

	/**
	 * ��Ӽ���������ʼ������.�����������������ܵ�manage����
	 * 
	 * @param executer
	 * @return
	 */
	public Object addListenerWithOutManager(IListenerExecute executer) {
		return addListenerWithOutManager(executer, false, true);
	}

	/**
	 * ��Ӽ���������ʼ������.�����������������ܵ�manage����
	 * 
	 * @param executer
	 * @return
	 */
	public Object addListenerWithOutManager(IListenerExecute executer,
			boolean isClearData, boolean isEnable) {
		if (executer instanceof BaseDataListener) {
			BaseDataListener b = (BaseDataListener) executer;
			ListenerManager listenerManager = new ListenerManager();
			b.setListenerManager(listenerManager);
			listenerManager.setClearData(isClearData);
			listenerManager.setEanbleListener(isEnable);
		}
		return executer.excute();
	}

}
