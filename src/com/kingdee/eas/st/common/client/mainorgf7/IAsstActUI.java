package com.kingdee.eas.st.common.client.mainorgf7;

/**
 * �����������Ķ������޸������ͺ󣬴���asstActChanged�¼����ڸ��¼�������������������F7
 * 
 * @author zhiwei_wang
 * 
 */
public interface IAsstActUI {
	/**
	 * 
	 * @param oldType
	 *            һ�������ظ��ĳ���Ϊ8���ַ�����ʶҵ����󣨿ͻ�����Ӧ�̡����ţ�
	 * @param newType
	 *            һ�������ظ��ĳ���Ϊ8���ַ�����ʶҵ����󣨿ͻ�����Ӧ�̡����ţ� ����ȡ��������ֵ��
	 * 
	 *            STConstant.BOSTYPE_CUSTOMER = "BF0C040E";
	 * 
	 *            STConstant.BOSTYPE_SUPPLIER = "37C67DFC";
	 * 
	 *            STConstant.BOSTYPE_ADMINORGUNIT = "CCE7AED4";
	 */
	// ��������������
	public String getAsstActType();

	public void addAsstActChangeListener(AsstActChangeListener l);

	public void fireAsstActChange(String oldType, String newType);
}