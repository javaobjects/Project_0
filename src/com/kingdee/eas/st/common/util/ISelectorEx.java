/*
 * @(#)ISelectorEx.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;

/**
 * ����: ���ο��������getSelectors
 * 
 * 1. ��EditUI��getSelectors�Ὣ���е�LinkedPropertyչ��Ϊ:LinkedProperty.*
 * ����ORMapping�ὫLinkedProperty.*����Ϊ�ñ����е��ֶΣ����ĳ�������αȽ����LinkedProperty
 * �Ƚ϶�ͻᵼ������ִ��getValue��SQL��䳬��. 2. ���ڴ�������������ࣺ ListUI.getBOTPSelectors ��
 * EditUI.getSelectors
 * 
 * a.)ListUI.getBOTPSelectors���Բ�����XXXListUIPIEX�и���getBOTPSelectors�������ñ��ӿڵľ���ʵ����.
 * b.)EditUI.getSelectors��Ϊ�ֳ����������Ƕ��ο�����AbstractXXXEditUI.getSelectors�����ã�
 * ���Զ���˽ӿ��ɶ��ο�����Ա��
 * AbstractXXXEditUI.getSelectors�е�LinkedProperty.*չ��Ϊ������Ҫ������(����:
 * LinkedProperty.p1,LinkedProperty.p2...)��
 * Ҳ����˵�ڱ��ӿ�ʵ������ԭ���ϲ��������LinkedProperty.*.
 * c.)��չ������Ա��PIEX�н���չ�������ӵ�LinkedProperty.*չ��
 * ,���ο�����Աͨ�����ӿڵ�ʵ�����չ�����ο������ӵ�LinkedProperty.*
 * 
 * 3.���ӿ�ͨ��Լ������ʵ����������ʵ������Լ����ʵ������������Ϊ:XXXInfo + SelectorEx��
 * �����磺com.kingdee.eas.scm.sd.sale.SaleOrderInfoSelectorEx
 * 
 * @author daij date:2008-6-3
 *         <p>
 * @version EAS5.4
 */
public interface ISelectorEx {

	/**
	 * 
	 * ��������ȡ���ο���
	 * 
	 * ��AbstractXXXEditUI.getSelectors�е�LinkedProperty.*չ��Ϊ������Ҫ������(����:
	 * LinkedProperty.p1,LinkedProperty.p2...)��
	 * Ҳ����˵�ڱ��ӿ�ʵ������ԭ���ϲ��������LinkedProperty.*
	 * 
	 * ע�⣺ ��Selectors�谴��ʵ���ϵ����������������������ǰ�Query.
	 * 
	 * @return SelectorItemCollection
	 * @author:daij ����ʱ�䣺2008-6-3
	 *              <p>
	 */
	SelectorItemCollection getExtSelectors();
}
