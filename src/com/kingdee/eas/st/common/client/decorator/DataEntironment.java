/*
 * @(#)DataEntironment.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.decorator;

import java.util.Map;
import java.util.Observable;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * ����: ��װUI����ģ������Ҫ���������.
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public class DataEntironment extends Observable {

	/**
	 * �ϼ�UI�����UIContext.
	 */
	protected Map uiContext = null;

	/**
	 * ��XXXUI�󶨵�����ֵ����.
	 */
	protected IObjectValue editData = null;

	/**
	 * �����ε�UI Object���.
	 * 
	 * 1.���ڷ���UI Element 2.���ڷ����ص�(���ƻ�class��������,���Ƽ�)
	 */
	protected CoreUI uiObject = null;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public DataEntironment() {
		super();
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param uiContext
	 *            �ϼ�UI�����UIContext.
	 * @param editData
	 *            ��XXXUI�󶨵�����ֵ����.
	 * @param uiObject
	 *            �����ε�UI Object���.
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public DataEntironment(Map uiContext, IObjectValue editData, CoreUI uiObject) {

		super();

		this.editData = editData;

		this.uiContext = uiContext;

		this.uiObject = uiObject;
	}

	/**
	 * ����:@return ���� editData��
	 */
	public IObjectValue getEditData() {
		return editData;
	}

	/**
	 * ����:����editData��ֵ��
	 * 
	 * @param editData
	 */
	public void setEditData(IObjectValue editData) {
		this.editData = editData;

		this.dataEntironmentChanged();
	}

	/**
	 * ����:@return ���� uiContext��
	 */
	public Map getUIContext() {
		return uiContext;
	}

	/**
	 * ����:����uiContext��ֵ��
	 * 
	 * @param uiContext
	 */
	public void setUIContext(Map uiContext) {
		this.uiContext = uiContext;

		this.dataEntironmentChanged();
	}

	/**
	 * ����:@return ���� uiObject��
	 */
	public CoreUI getUIObject() {
		return uiObject;
	}

	/**
	 * ����:����uiObject��ֵ��
	 * 
	 * @param uiObject
	 */
	public void setUIObject(CoreUI uiObject) {
		this.uiObject = uiObject;

		this.dataEntironmentChanged();
	}

	/**
	 * 
	 * ��������ǰ��������ݰ������ĸı�.
	 * 
	 * @author:daij
	 * @see java.util.Observable#setChanged()
	 */
	protected void dataEntironmentChanged() {
		// ��ǵ�ǰ�������ݻ����Ѿ������仯
		setChanged();
		// ֪ͨ�������ݻ�����ʹ����
		notifyObservers(this);
	}
}
