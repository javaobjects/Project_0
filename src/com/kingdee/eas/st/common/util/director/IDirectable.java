/*
 * @(#)IWishDirected.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.director;

/**
 * ����: XXX���������ģ�͵����Ͻӿ�
 * 
 * ְ��: ϣ��ʹ�÷���ģ�͵�XXX����ͨ��ʵ�ִ˽ӿڱ����Ը,��ʵ��: 1.ѡ��һ��Director��ʽ(TODO �ṩͳһ�ĵ��ݹ���)
 * 2.ʵ���������Director�ṹ.
 * 
 * Ŀ��: ʵ��ģ�Ͷ�XXX�������������, ͨ���˽ӿڼ��׵Ĳ��.
 * 
 * @author daij date:2006-11-6
 *         <p>
 * @version EAS5.2.0
 */
public interface IDirectable {

	/**
	 * 
	 * ���������÷���ģ�͵ĵ���. ְ��: 1.ѡ��Director��ʽ(TODO �ṩͳһ�ĵ��ݹ���) 2.ʵ���������Director�ṹ.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void setupDirector();
}
