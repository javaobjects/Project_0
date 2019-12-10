/*
 * �������� 2006-11-15
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.kingdee.eas.st.common.client.decorator;

import java.lang.reflect.InvocationHandler;
import java.util.Observable;
import java.util.Observer;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.st.common.util.director.DirectorDynamicProxy;

/**
 * 
 * ����: UI�������̵ķ�����. ְ��: ��UI�ϵ����̲�����ҵ���߼����뵽����ͷֲ����������еĸ���������.
 * 
 * ע��: 1. UIDirector��Ĭ�ϵķ�����ʵ��,�ṩILoadDataTemplate,IUIElementDecorator��ΪĬ�ϵķ���ģ��.
 * 2. �����ΪĬ�ϵ�ģ�巽��������,ϣ��ʹ���Զ���ķ���ģ��ӿ�,��ô��Ҫ���²���: 2.1)
 * ��������XXXUIDirector�̳�AbstractUIDirector. 2.2)
 * ����addProxyInterfaces���������Զ���ģ��ӿڵ�AbstractUIDirector����Ľӿ��б�. 2.3)
 * ʵ��decorateUIElement,
 * loadData���������Զ���ģ��ķ���(��ΪAbstractUIDirector�޷��õ�ǿ���͵��Զ������ӿ�,����ֻ���ӳٵ�����.) 2.4)
 * �ṩ����: ILoadDataTemplate loadDataTemplate()�ķ���,���Զ���ģ��Ľӿ���¶��ȥ. 3.
 * UIDirector����AbstractDirector(����ͷֲ�����)��Ϊ��̬����ķ���������,�����Զ��巽��������Handler ->
 * ͨ������setDirector�����滻Ϊ�Զ���������.
 * 
 * @author daij date:2006-11-16
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractUIDirector extends DirectorDynamicProxy implements
		IUIDirector, ISetDataEntironmentCommand, Observer {

	/**
	 * ���ε����ݰ�.
	 */
	private DataEntironment dataEntironment = null;

	/**
	 * ����Ľӿ��б�.
	 */
	public static final Class[] PROXYINTERFACES = new Class[] { ISetDataEntironmentCommand.class };

	/**
	 * 
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	public AbstractUIDirector() {
		super(PROXYINTERFACES);
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param proxyInterfaces
	 *            ����Ľӿ��б�.
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public AbstractUIDirector(Class[] proxyInterfaces) {
		super(PROXYINTERFACES);

		addProxyInterfaces(proxyInterfaces);
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param director
	 *            �ӿڴ���ķ���������Handler.
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	public AbstractUIDirector(InvocationHandler director) {
		super(PROXYINTERFACES, director);
	}

	/**
	 * ���������캯��
	 * 
	 * @param proxyInterfaces
	 *            ����Ľӿ��б�.
	 * @param director
	 *            �ӿڴ���ķ���������Handler.
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public AbstractUIDirector(Class[] proxyInterfaces,
			InvocationHandler director) {
		super(PROXYINTERFACES, director);

		addProxyInterfaces(proxyInterfaces);
	}

	/**
	 * 
	 * ������װ��DataEntironment.
	 * 
	 * @param dataEntironment
	 *            ���ε����ݰ�.
	 * @author:daij ����ʱ�䣺2006-11-8
	 *              <p>
	 */
	public final void loadDataEntironment(DataEntironment dataEntironment) {

		if (STQMUtils.isNull(dataEntironment))
			return;

		// �ӳٳ�ʼ��dataEntironment
		this.initializeDataEntironment();

		this.dataEntironment.editData = dataEntironment.getEditData();

		this.dataEntironment.uiContext = dataEntironment.getUIContext();

		this.dataEntironment.uiObject = dataEntironment.getUIObject();

		if (proxyInstance instanceof ISetDataEntironmentCommand) {
			((ISetDataEntironmentCommand) proxyInstance)
					.loadDataEntironment(this.dataEntironment);
		}
	}

	/**
	 * ����:@return ���� dataEntironment��
	 */
	public DataEntironment dataEntironment() {
		initializeDataEntironment();

		return this.dataEntironment;
	}

	private void initializeDataEntironment() {
		if (STQMUtils.isNull(this.dataEntironment)) {
			this.dataEntironment = new DataEntironment();

			this.dataEntironment.addObserver(this);
		}
	}

	/**
	 * 
	 * ���������ݻ��������ı䣬��Ҫ����װ��.
	 * 
	 * @author:daij
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (arg instanceof DataEntironment) {
			loadDataEntironment((DataEntironment) arg);
		}
	}
}
