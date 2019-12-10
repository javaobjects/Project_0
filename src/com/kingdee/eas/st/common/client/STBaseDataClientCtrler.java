package com.kingdee.eas.st.common.client;

import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.eas.basedata.framework.CUIDGetterFacadeFactory;
import com.kingdee.eas.basedata.framework.DataBaseDException;
import com.kingdee.eas.basedata.framework.ICUIDGetterFacade;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;

import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class STBaseDataClientCtrler {
	private CoreUI ui = null;
	private boolean isEditUI = false;
	ICoreBase iCoreBase = null;

	public STBaseDataClientCtrler(CoreUI coreUI, ICoreBase iCoreBase) {
		if (coreUI instanceof EditUI) {
			isEditUI = true;
		}
		this.ui = coreUI;
		this.iCoreBase = iCoreBase;
	};

	private ICoreBase getBizInterface() {
		return iCoreBase;
	}

	private String getId() {
		if (ui instanceof ListUI) {
			return ((ListUI) ui).getSelectedKeyForAll();
		} else if (ui instanceof EditUI) {
			return ((EditUI) ui).getSelectedKeyForAll();
		}
		return null;
	}

	/*
	 * by sxhong 2008/01/22 策略分类 中文名称 S1 集团维护，全局共享 S2 全局维护，全局共享 S3 创建者维护，全局共享 S4
	 * 对下共享，对上隔离 D1 管理者维护，创建者分配共享 D2 编码名称集团统一，分配后其他属性分别维护 D3
	 * 基本信息集团统一，分配后相关信息分别维护 I 绝对隔离，创建者私有
	 */
	public static final String CONTROLTYPE_S1 = "S1";
	public static final String CONTROLTYPE_S2 = "S2";
	public static final String CONTROLTYPE_S3 = "S3";
	public static final String CONTROLTYPE_S4 = "S4";
	public static final String CONTROLTYPE_I = "I";

	public static final String ACTION_ADDNEW = "ACTION_ADDNEW";
	public static final String ACTION_DELETE = "ACTION_DELETE";
	public static final String ACTION_MODIFY = "ACTION_MODIFY";
	/**
	 * 获得 基础资料 的 控制类型 (S2 S2 S3 S4 I)
	 * <p>
	 * 默认情况下 控制类型 从实体的扩展属性上 被读取
	 */
	private String controlType = null;

	public String getControlType() {
		if (controlType == null) {
			controlType = "";
			try {
				if (getBizInterface() == null) {
					return controlType;
				}
				EntityObjectInfo eoi = MetaDataLoaderFactory
						.getRemoteMetaDataLoader().getEntity(
								getBizInterface().getType());
				if (eoi.containsExtendedPropertyKey("controlType")) {
					controlType = eoi.getExtendedProperty("controlType");
				}
			} catch (Exception e) {
				ExceptionHandler.handle(e);
				SysUtil.abort();
			}
		}
		if (StringUtils.isEmpty(controlType)) {
			controlType = CONTROLTYPE_S1;
		}
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public void checkPermission(String action) throws Exception {
		if (getControlType().equalsIgnoreCase(CONTROLTYPE_S1)) {
			if (!getCurrentCUID().equals(OrgConstants.DEF_CU_ID)) {
				throwsExceptionForNoPermission(action);
			}
		} else if (getControlType().equalsIgnoreCase(CONTROLTYPE_S3)
				|| getControlType().equalsIgnoreCase(CONTROLTYPE_S4)) {
			if (!action.equals(ACTION_ADDNEW)) {
				if (!getCurrentCUID().equals(getCUIDFromBizobject())) {
					throwsExceptionForNoPermission(action);
				}
			}
		}
	}

	private void throwsExceptionForNoPermission(String action) throws Exception {
		if (action.equals(ACTION_ADDNEW)) {
			throw new DataBaseDException(DataBaseDException.CAN_NOT_ADD);
		} else if (action.equals(ACTION_DELETE)) {
			throw new DataBaseDException(DataBaseDException.CAN_NOT_DELETE);
		} else if (action.equals(ACTION_MODIFY)) {
			throw new DataBaseDException(DataBaseDException.CAN_NOT_UPDATE);
		}
	}

	private String currentCUID = null;

	private String getCurrentCUID() {
		if (currentCUID == null) {
			currentCUID = SysContext.getSysContext().getCurrentCtrlUnit()
					.getId().toString();
		}
		return currentCUID;
	}

	private String getCUIDFromBizobject() throws Exception {
		String id = getId();
		if (id == null) {
			return "";
		}
		return getICGF().getCUID(id);
	}

	private ICUIDGetterFacade getICGF() throws Exception {
		return CUIDGetterFacadeFactory.getRemoteInstance();
	}
}
