/*
 * 创建日期 2006-2-6
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.kingdee.eas.st.common.client.utils;

import java.util.HashMap;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;

/**
 * @author wangyb 2006-2-6 TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ArApBillUIUtil {

	/**
	 * 默认显示当前CU下的人员，但是下面有一个复选框来决定是否显示全部CU的下的人员，还是本CU下的人员
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
		prmtPerson.setHasCUDefaultFilter(false);// 默认不cu隔离

	}

	/**
	 * 默认显示当前CU下的行政组织，根据复选框来可以切换到显示全集团下的所有行政组织
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