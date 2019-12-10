/*
 * @(#)IUIElementDecorator.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.decorator;

/**
 * ����: UI Elemnet����ģ��. ְ��: 1.ΪUI Element�����ṩͳһ�ķ���ǩ��.
 * 2.��ģ��ӿڿ�ֱ����XXXUIʵ�ֻ������ڷ���UI���ε�Builderʵ��.
 * 
 * ע��: 1. �ڱ�ģ����ֻ��UI Element�������κ͵���,���ܶ�editData�����κ�����/�Ķ��ʹ���.
 * 
 * @author daij date:2006-11-3
 *         <p>
 * @version EAS5.2.0
 */
public interface IUIElementDecorator {

	/**
	 * 
	 * �����������˵�����. ְ��: ���ݸ�ҵ������Բ˵����ֽ�������, ����.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void trimUIMenuBarLayout() throws Exception;

	/**
	 * 
	 * ��������������������. ְ��: ���ݸ�ҵ������Թ��������ֽ�������, ����.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void trimUIToolBarLayout() throws Exception;

	/**
	 * 
	 * ��������װUI Element. ��������F7������,KDTable�ϵ��п�, ��¼����ɫ, ��ֵ���ֶεľ������õȵ�.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void setupUIElement() throws Exception;

	/**
	 * 
	 * ���������ݸ�ҵ�������UI Element�Ŀɼ��Խ��е���.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void decorateUIVisible() throws Exception;

	/**
	 * 
	 * ���������ݸ�ҵ�������UI Element�Ŀ����Խ��е���.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void decorateUIEnable() throws Exception;
}
