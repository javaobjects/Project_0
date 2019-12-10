/*
 * �������� 2006-2-6
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.kingdee.eas.st.common.client.utils;

import java.util.HashMap;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;

/**
 * @author wangyb 2006-2-6 TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ArApBillUIUtil {

	/**
	 * Ĭ����ʾ��ǰCU�µ���Ա������������һ����ѡ���������Ƿ���ʾȫ��CU���µ���Ա�����Ǳ�CU�µ���Ա
	 * 
	 * @param ui
	 * @param prmtPerson
	 *            2006-12-2 wangyb
	 */
	public static void setPersonAllCU(IUIObject ui, KDBizPromptBox prmtPerson) {
		HashMap map = new HashMap();
		prmtPerson.setEditable(true);
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$;$idNum$");
		map.put(PersonF7UI.SHOW_ALL_CHILDREN, "YES");
		PersonPromptBox select = new PersonPromptBox(ui, map);
		prmtPerson.setSelector(select);
		prmtPerson.setHasCUDefaultFilter(false);// Ĭ�ϲ�cu����

	}

	/**
	 * Ĭ����ʾ��ǰCU�µ�������֯�����ݸ�ѡ���������л�����ʾȫ�����µ�����������֯
	 * 
	 * @param ui
	 * @param prmtAdminOrgUnit
	 *            2006-12-2 wangyb
	 */
	public static void setAdminAllCU(IUIObject ui,
			KDBizPromptBox prmtAdminOrgUnit) {
		// change the AdminF7WithDataPerm, by youqh at 2006-12-11
		prmtAdminOrgUnit.setEditable(true);
		prmtAdminOrgUnit.setEditFormat("$number$");
		prmtAdminOrgUnit.setCommitFormat("$number$;$code$");
		AdminF7 adminF7 = new AdminF7();
		adminF7.disablePerm();
		adminF7.setIsCUFilter(true);
		adminF7.showCheckBoxOfShowingAllOUs();
		prmtAdminOrgUnit.setSelector(adminF7);
	}
}