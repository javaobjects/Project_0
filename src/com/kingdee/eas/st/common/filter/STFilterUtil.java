package com.kingdee.eas.st.common.filter;

import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxItem;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.mm.qm.utils.CSMF7Utils;
import com.kingdee.eas.basedata.mm.qm.utils.STClientUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.st.common.client.OrgUnitClientUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @author zhiwei_wang
 * 
 */
public abstract class STFilterUtil {

	public final static KDComboBoxItem COMBO_NULL_ITEM = new KDComboBoxItem("");

	/**
	 * 单据状态
	 * 
	 * @param box
	 */
	public static final void setBillStatusCombo(KDComboBox box) {
		box.removeAllItems();
		box.addItem(COMBO_NULL_ITEM);
		box.addItems(BillBaseStatusEnum.getEnumList().toArray());
		// 去掉历史版本、变更中和Null三项
		box.removeItem(BillBaseStatusEnum.VERSION);
		box.removeItem(BillBaseStatusEnum.ALTERING);
		box.removeItem(BillBaseStatusEnum.NULL);
		box.setSelectedIndex(0);
	}

	/**
	 * 单据状态<b\><br>
	 * 传入值statusEnum请参考com.kingdee.eas.scm.common.BillBaseStatusEnum里
	 * 
	 * @param box
	 * @param statusEnum
	 * @author jiwei_xiao
	 */
	public static final void setBillStatusCombo(KDComboBox box, int[] statusEnum) {
		box.removeAllItems();
		box.addItem(COMBO_NULL_ITEM);
		for (int i = 0; i < statusEnum.length; i++) {
			box.addItem(BillBaseStatusEnum.getEnum(statusEnum[i]));
		}
	}

	/**
	 * 比较开始结束日期
	 * 
	 * @param pk0
	 * @param pk1
	 * @return 开始晚于结束日期返回true
	 * @author jiwei_xiao
	 */
	public static final boolean compareDate(KDDatePicker pk0, KDDatePicker pk1) {
		Date fromDate = (Date) pk0.getValue();
		Date toDate = (Date) pk1.getValue();
		if (fromDate != null && toDate != null) {
			if (fromDate.after(toDate)) {
				pk0.requestFocus();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 班组
	 * 
	 * @param box
	 */
	public static final void setAdminOrgUnit(KDBizPromptBox f7) {
		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setDisplayFormat("$number$");
	}

	/**
	 * 人员
	 * 
	 * @param box
	 */
	public static final void setPersonF7(KDBizPromptBox f7) {
		f7.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setDisplayFormat("$number$");
	}

	// /**
	// * 人员F7
	// * @param f7
	// * @param adminOrgUnit
	// * @param ui
	// */
	// public static final void setPersonF7(final KDBizPromptBox personF7, final
	// KDBizPromptBox adminOrgF7, final IUIObject ui){
	// //// if(!STClientUtils.hasFocusListener(personF7.getEditor())){
	// // personF7.getEditor().addFocusListener(new FocusListener(){
	// // public void focusGained(FocusEvent e) {
	// // if(STUtils.isNotNull(adminOrgF7) &&
	// STUtils.isNotNull(adminOrgF7.getValue()) && adminOrgF7.getValue()
	// instanceof OrgUnitInfo){
	// // PersonClientUtils.setPersonF7ByAdminOrgUnit(ui, adminOrgF7, personF7);
	// // personF7.setEditFormat("$number$");
	// // personF7.setCommitFormat("$number$");
	// // personF7.setDisplayFormat("$number$");
	// // }else{
	// // if(STUtils.isNotNull(adminOrgF7)){
	// // adminOrgF7.requestFocus();
	// // }
	// // MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
	// // SysUtil.abort();
	// // }
	// // }
	// //
	// // public void focusLost(FocusEvent e) {
	// // // Do Nothing
	// // }
	// // });
	// //// }
	// // if(STUtils.isNotNull(adminOrgF7)){
	// // DataChangeListener listener = new DataChangeListener(){
	// // public void dataChanged(DataChangeEvent eventObj) {
	// // if(eventObj.getNewValue() == null ||
	// (!eventObj.getNewValue().equals(eventObj.getOldValue()))){
	// // personF7.setData(null);
	// // }
	// // }
	// // };
	// // adminOrgF7.addDataChangeListener(listener);
	// // }
	//    	
	//    	
	//    	
	// // if(STUtils.isNull(f7) || STUtils.isNull(f7.getEditor())){
	// // return;
	// // }
	// // f7.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
	// //// if(!STClientUtils.hasFocusListener(f7)){
	// // f7.getEditor().addFocusListener(new FocusListener(){
	// //
	// // public void focusGained(FocusEvent e) {
	// // if(STUtils.isNotNull(adminOrgUnit) &&
	// STUtils.isNotNull(adminOrgUnit.getValue()) && adminOrgUnit.getValue()
	// instanceof OrgUnitInfo && STUtils.isNotNull(ui)){
	// // PersonClientUtils.setPersonF7ByAdminOrgUnit(ui, adminOrgUnit, f7);
	// // }else{
	// // f7.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
	// // }
	// // }
	// //
	// // public void focusLost(FocusEvent e) {
	// // // Do Nothing
	// // }
	// //
	// // });
	// //// }
	// // f7.setEditFormat("$number$");
	// // f7.setCommitFormat("$number$");
	// // f7.setDisplayFormat("$name$");
	// }
	/**
	 * 日期
	 * 
	 * @param from
	 * @param to
	 */
	public static final void setDatePicker(KDDatePicker from, KDDatePicker to) {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		from.setValue(c.getTime());
		to.setValue(now);
	}

	/**
	 * 设置物料f7，这里只需要为物料F7添加监听器
	 * 
	 * @param materialF7
	 * @param owner
	 * @param mainBizOrgF7
	 *            主业务组织f7
	 * @param mainBizOrgType
	 *            主业务组织类型
	 */
	public static final void setMaterialF7(final KDBizPromptBox materialF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final OrgType mainBizOrgType, final String displayFormat) {
		if (!STClientUtils.hasSelectListeners(materialF7.getEditor())) {
			materialF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (STUtils.isNotNull(mainBizOrgF7)
							&& STUtils.isNotNull(mainBizOrgF7.getValue())
							&& mainBizOrgF7.getValue() instanceof OrgUnitInfo) {
						CSMF7Utils.setBizMaterialTreeF7(materialF7, owner,
								mainBizOrgType, (OrgUnitInfo) mainBizOrgF7
										.getValue());
						materialF7.setEditFormat("$number$");
						// materialF7.setCommitFormat("$number$");
						// materialF7.setCommitParser(null);
						materialF7.setDisplayFormat(displayFormat);
					} else {
						if (STUtils.isNotNull(mainBizOrgF7)) {
							mainBizOrgF7.requestFocus();
						}
						MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
						SysUtil.abort();
					}
				}
			});
			// materialF7.getEditor().addFocusListener(new FocusListener(){
			// public void focusGained(FocusEvent e) {
			// if(STUtils.isNotNull(mainBizOrgF7) &&
			// STUtils.isNotNull(mainBizOrgF7.getValue()) &&
			// mainBizOrgF7.getValue() instanceof OrgUnitInfo){
			// CSMF7Utils.setBizMaterialTreeF7(materialF7, owner,
			// mainBizOrgType,
			// (OrgUnitInfo)mainBizOrgF7.getValue());
			// materialF7.setEditFormat("$number$");
			// materialF7.setCommitFormat("$number$");
			// materialF7.setDisplayFormat(displayFormat);
			// }else{
			// if(STUtils.isNotNull(mainBizOrgF7)){
			// mainBizOrgF7.requestFocus();
			// }
			// MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
			// SysUtil.abort();
			// }
			// }
			//    			
			// public void focusLost(FocusEvent e) {
			// // Do Nothing
			// }
			// });
		}
		if (STUtils.isNotNull(mainBizOrgF7)) {
			DataChangeListener listener = new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					if (eventObj.getNewValue() == null
							|| (!eventObj.getNewValue().equals(
									eventObj.getOldValue()))) {
						String text = materialF7.getText();
						materialF7.setData(null);
						materialF7.setText(text);
					}
				}
			};
			mainBizOrgF7.addDataChangeListener(listener);
		}
	}

	/**
	 * 设置物料F7,displayFormat为"$number$"
	 * 
	 * @param materialF7
	 * @param owner
	 * @param mainBizOrgF7
	 * @param mainBizOrgType
	 */
	public static final void setMaterialF7(final KDBizPromptBox materialF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final OrgType mainBizOrgType) {
		setMaterialF7(materialF7, owner, mainBizOrgF7, mainBizOrgType,
				"$number$");
	}

	/**
	 * 设置物料f7，这里只需要为物料F7添加监听器
	 * 
	 * @param materialF7
	 * @param owner
	 * @param mainBizOrgF7
	 *            主业务组织f7
	 * @param mainBizOrgType
	 *            主业务组织类型
	 */
	public static final void setCUMaterialF7(final KDBizPromptBox materialF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final String CUID, final String displayFormat) {
		if (!STClientUtils.hasSelectListeners(materialF7.getEditor())) {
			materialF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (STUtils.isNotNull(mainBizOrgF7)
							&& STUtils.isNotNull(mainBizOrgF7.getValue())) {
						CSMF7Utils.setBizMaterialCUF7(materialF7, false,
								((OrgUnitInfo) mainBizOrgF7.getValue()).getCU()
										.getId().toString());
						materialF7.setEditFormat("$number$");
						materialF7.setCommitParser(null);
						materialF7.setDisplayFormat(displayFormat);

					} else {
						if (STUtils.isNotNull(mainBizOrgF7)) {
							mainBizOrgF7.requestFocus();
						}
						MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
						SysUtil.abort();
					}
				}
			});
		}
		if (STUtils.isNotNull(mainBizOrgF7)) {
			DataChangeListener listener = new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					if (eventObj.getNewValue() == null
							|| (!eventObj.getNewValue().equals(
									eventObj.getOldValue()))) {
						String text = materialF7.getText();
						materialF7.setData(null);
						materialF7.setText(text);
					}
				}
			};
			mainBizOrgF7.addDataChangeListener(listener);
		}
	}

	/**
	 * 设置物料F7,displayFormat为"$number$"
	 * 
	 * @param materialF7
	 * @param owner
	 * @param mainBizOrgF7
	 * @param mainBizOrgType
	 */
	public static final void setCUMaterialF7(final KDBizPromptBox materialF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final String CUID) {
		mainBizOrgF7.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				setCUMaterialF7(materialF7, owner, mainBizOrgF7, CUID,
						"$number$");
			}
		});
		// setCUMaterialF7(materialF7, owner, mainBizOrgF7, CUID, "$number$");
	}

	/**
	 * 设置供应商f7
	 * 
	 * @param f7
	 * @param owner
	 * @param mainOrgInfo
	 *            主业务组织
	 * @param mainBizOrgType
	 *            主业务组织类型
	 * @param displayFormat
	 *            , e.g. "$number$"
	 */
	public static final void setCUSupplierF7(final KDBizPromptBox supplierF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final String displayFormat) {
		if (!STClientUtils.hasSelectListeners(supplierF7.getEditor())) {
			supplierF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (STUtils.isNotNull(mainBizOrgF7)
							&& STUtils.isNotNull(mainBizOrgF7.getValue())
							&& mainBizOrgF7.getValue() instanceof OrgUnitInfo) {
						CSMF7Utils.setBizSupplierCUF7(supplierF7, owner,
								(OrgUnitInfo) mainBizOrgF7.getValue());
						supplierF7.setEditFormat("$number$");
						// supplierF7.setCommitFormat("$number$");
						supplierF7.setCommitParser(null);
						supplierF7.setDisplayFormat(displayFormat);
					} else {
						if (STUtils.isNotNull(mainBizOrgF7)) {
							mainBizOrgF7.requestFocus();
						}
						MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
						SysUtil.abort();
					}
				}
			});
		}
		if (STUtils.isNotNull(mainBizOrgF7)) {
			DataChangeListener listener = new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					if (eventObj.getNewValue() == null
							|| (!eventObj.getNewValue().equals(
									eventObj.getOldValue()))) {
						// String text = supplierF7.getText();
						supplierF7.setValue(null);
						// supplierF7.setText(text);
					}
				}
			};
			mainBizOrgF7.addDataChangeListener(listener);
		}
	}

	/**
	 * 设置供应商f7, displayFormat为 "$number$"
	 * 
	 * @param supplierF7
	 * @param owner
	 * @param mainBizOrgF7
	 * @param mainBizOrgType
	 */
	public static final void setCUSupplierF7(final KDBizPromptBox supplierF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7) {
		setCUSupplierF7(supplierF7, owner, mainBizOrgF7, "$number$");
	}

	/**
	 * 设置供应商f7
	 * 
	 * @param f7
	 * @param owner
	 * @param mainOrgInfo
	 *            主业务组织
	 * @param mainBizOrgType
	 *            主业务组织类型
	 * @param displayFormat
	 *            , e.g. "$number$"
	 */
	public static final void setSupplierF7(final KDBizPromptBox supplierF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final OrgType mainBizOrgType, final String displayFormat) {
		if (!STClientUtils.hasSelectListeners(supplierF7.getEditor())) {
			supplierF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (STUtils.isNotNull(mainBizOrgF7)
							&& STUtils.isNotNull(mainBizOrgF7.getValue())
							&& mainBizOrgF7.getValue() instanceof OrgUnitInfo) {
						CSMF7Utils.setBizSupplierTreeF7(supplierF7, owner,
								mainBizOrgType, (OrgUnitInfo) mainBizOrgF7
										.getValue());
						supplierF7.setEditFormat("$number$");
						// supplierF7.setCommitFormat("$number$");
						supplierF7.setCommitParser(null);
						supplierF7.setDisplayFormat(displayFormat);
					} else {
						if (STUtils.isNotNull(mainBizOrgF7)) {
							mainBizOrgF7.requestFocus();
						}
						MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
						SysUtil.abort();
					}
				}
			});
		}
		if (STUtils.isNotNull(mainBizOrgF7)) {
			DataChangeListener listener = new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					if (eventObj.getNewValue() == null
							|| (!eventObj.getNewValue().equals(
									eventObj.getOldValue()))) {
						// String text = supplierF7.getText();
						supplierF7.setValue(null);
						// supplierF7.setText(text);
					}
				}
			};
			mainBizOrgF7.addDataChangeListener(listener);
		}
	}

	/**
	 * 设置供应商f7, displayFormat为 "$number$"
	 * 
	 * @param supplierF7
	 * @param owner
	 * @param mainBizOrgF7
	 * @param mainBizOrgType
	 */
	public static final void setSupplierF7(final KDBizPromptBox supplierF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final OrgType mainBizOrgType) {
		setSupplierF7(supplierF7, owner, mainBizOrgF7, mainBizOrgType,
				"$number$");
	}

	/**
	 * 设置客户f7,
	 * 
	 * @author zhiwei_wang
	 * @param customerF7
	 * @param owner
	 * @param mainBizOrgF7
	 * @param mainBizOrgType
	 * @param displayFormat
	 *            ,格式为 "$name$"
	 */
	public static final void setCUCustomerF7(final KDBizPromptBox customerF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final String displayFormat) {
		if (!STClientUtils.hasSelectListeners(customerF7.getEditor())) {
			customerF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (STUtils.isNotNull(mainBizOrgF7)
							&& STUtils.isNotNull(mainBizOrgF7.getValue())
							&& mainBizOrgF7.getValue() instanceof OrgUnitInfo) {

						OrgUnitInfo orgUnitInfo = null;
						orgUnitInfo = (OrgUnitInfo) mainBizOrgF7.getValue();

						CSMF7Utils.setBizCustomerCUF7(customerF7, owner,
								orgUnitInfo);

						customerF7.setEditFormat("$number$");
						// customerF7.setCommitFormat("$number$");
						customerF7.setCommitParser(null);
						customerF7.setDisplayFormat(displayFormat);
					} else {
						if (STUtils.isNotNull(mainBizOrgF7)) {
							mainBizOrgF7.requestFocus();
						}
						MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
						SysUtil.abort();
					}
				}
			});
		}
		if (STUtils.isNotNull(mainBizOrgF7)) {
			DataChangeListener listener = new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					if (eventObj.getNewValue() == null
							|| (!eventObj.getNewValue().equals(
									eventObj.getOldValue()))) {
						String text = customerF7.getText();
						customerF7.setData(null);
						customerF7.setText(text);
					}
				}
			};
			mainBizOrgF7.addDataChangeListener(listener);
		}
	}

	/**
	 * 设置客户f7, displayFormat为Name
	 * 
	 * @author zhiwei_wang
	 * @param f7
	 * @param owner
	 * @param mainOrgInfo
	 *            主业务组织
	 * @param mainBizOrgType
	 *            主业务组织类型
	 */
	public static final void setCUCustomerF7(final KDBizPromptBox customerF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7) {

		setCUCustomerF7(customerF7, owner, mainBizOrgF7, "$number$");
	}

	/**
	 * 设置客户f7,
	 * 
	 * @author zhiwei_wang
	 * @param customerF7
	 * @param owner
	 * @param mainBizOrgF7
	 * @param mainBizOrgType
	 * @param displayFormat
	 *            ,格式为 "$name$"
	 */
	public static final void setCustomerF7(final KDBizPromptBox customerF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final OrgType mainBizOrgType, final String displayFormat) {
		if (!STClientUtils.hasSelectListeners(customerF7.getEditor())) {
			customerF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (STUtils.isNotNull(mainBizOrgF7)
							&& STUtils.isNotNull(mainBizOrgF7.getValue())
							&& mainBizOrgF7.getValue() instanceof OrgUnitInfo) {

						OrgUnitInfo orgUnitInfo = null;
						OrgType orgType = null;

						// 获得库存组织所委托的财务组织
						if (mainBizOrgType.equals(OrgType.Storage)) {
							Object o = mainBizOrgF7.getValue();
							OrgUnitInfo mainOrgInfo = null;
							if (o instanceof OrgUnitInfo) {
								mainOrgInfo = (OrgUnitInfo) o;
								try {
									IOrgUnitRelation iUnitRel = OrgUnitRelationFactory
											.getRemoteInstance();
									OrgUnitCollection orgCol = iUnitRel
											.getToUnit(mainOrgInfo.getId()
													.toString(), mainBizOrgType
													.getValue(),
													OrgType.COMPANY_VALUE);
									if (orgCol != null && orgCol.size() > 0) {
										orgUnitInfo = (CompanyOrgUnitInfo) orgCol
												.get(0);
										orgType = OrgType.Company;
									}
								} catch (Exception e1) {
								}
							}
						} else {
							orgUnitInfo = (OrgUnitInfo) mainBizOrgF7.getValue();
							orgType = mainBizOrgType;
						}

						CSMF7Utils.setBizCustomerTreeF7(customerF7, owner,
								orgType, orgUnitInfo);

						customerF7.setEditFormat("$number$");
						// customerF7.setCommitFormat("$number$");
						// customerF7.setCommitParser(null);
						customerF7.setDisplayFormat(displayFormat);
					} else {
						if (STUtils.isNotNull(mainBizOrgF7)) {
							mainBizOrgF7.requestFocus();
						}
						MsgBox.showInfo(getResource("MAINBIZORG_NULL"));
						SysUtil.abort();
					}
				}
			});
		}
		if (STUtils.isNotNull(mainBizOrgF7)) {
			DataChangeListener listener = new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					if (eventObj.getNewValue() == null
							|| (!eventObj.getNewValue().equals(
									eventObj.getOldValue()))) {
						String text = customerF7.getText();
						customerF7.setData(null);
						customerF7.setText(text);
					}
				}
			};
			mainBizOrgF7.addDataChangeListener(listener);
		}
	}

	/**
	 * 设置客户f7, displayFormat为Name
	 * 
	 * @author zhiwei_wang
	 * @param f7
	 * @param owner
	 * @param mainOrgInfo
	 *            主业务组织
	 * @param mainBizOrgType
	 *            主业务组织类型
	 */
	public static final void setCustomerF7(final KDBizPromptBox customerF7,
			final CoreUIObject owner, final KDBizPromptBox mainBizOrgF7,
			final OrgType mainBizOrgType) {

		setCustomerF7(customerF7, owner, mainBizOrgF7, mainBizOrgType,
				"$number$");
	}

	/**
	 * 设置库存组织f7
	 * 
	 * @param f7
	 * @param mainBizOrgType
	 *            主业务组织，这里应该是库存组织 OrgType.Storage
	 */
	public static final void setStorageOrgUnitF7(KDBizPromptBox f7,
			OrgType mainBizOrgType, OrgUnitInfo mainOrgUnitInfo,
			String permissionName) {

		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.StorageItemQuery");
		OrgUnitClientUtils.setMainBizOrgF7(f7, mainBizOrgType,
				new String[] { permissionName });

		f7.setDisplayFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");

		f7.setValue(mainOrgUnitInfo);

	}

	// /**
	// * 设置库存组织f7
	// * @param f7
	// * @param mainBizOrgType 主业务组织，这里应该是库存组织 OrgType.Storage
	// */
	// public static final void setStorageOrgUnitF7(KDBizPromptBox f7, OrgType
	// mainBizOrgType){
	//    	
	// StorageOrgUnitInfo storageOrgUnitInfo =
	// com.kingdee.eas.common.client.SysContext
	// .getSysContext().getCurrentStorageUnit();
	// if (STUtils.isNotNull(storageOrgUnitInfo) &&
	// storageOrgUnitInfo.isIsBizUnit()) {
	// setStorageOrgUnitF7(f7,mainBizOrgType,storageOrgUnitInfo);
	// } else {
	// setStorageOrgUnitF7(f7,mainBizOrgType,null);
	// }
	// }

	/**
	 * 设置销售组织f7
	 * 
	 * @param f7
	 * @param mainBizOrgType
	 *            主业务组织，这里应该是销售组织 OrgType.Sale
	 */
	public static final void setSaleOrgUnitF7(KDBizPromptBox f7,
			OrgType mainBizOrgType, OrgUnitInfo mainOrgUnitInfo,
			String permissionName) {
		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleItemQuery");
		OrgUnitClientUtils.setMainBizOrgF7(f7, mainBizOrgType,
				new String[] { permissionName });

		f7.setDisplayFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");

		f7.setValue(mainOrgUnitInfo);

	}

	// /**
	// * 设置销售组织f7
	// * @param f7
	// * @param mainBizOrgType 主业务组织，这里应该是销售组织 OrgType.Sale
	// */
	// public static final void setSaleOrgUnitF7(KDBizPromptBox f7, OrgType
	// mainBizOrgType){
	//    	
	// SaleOrgUnitInfo saleOrgUnitInfo =
	// com.kingdee.eas.common.client.SysContext
	// .getSysContext().getCurrentSaleUnit();
	// if (STUtils.isNotNull(saleOrgUnitInfo) && saleOrgUnitInfo.isIsBizUnit())
	// {
	// setSaleOrgUnitF7(f7,mainBizOrgType,saleOrgUnitInfo);
	// } else {
	// setSaleOrgUnitF7(f7,mainBizOrgType,null);
	// }
	// }

	/**
	 * 设置财务组织f7
	 * 
	 * @param f7
	 * @param mainBizOrgType
	 *            主业务组织，这里应该是财务组织 OrgType.Company
	 */
	public static final void setCompanyF7(KDBizPromptBox f7,
			OrgType mainBizOrgType, OrgUnitInfo mainOrgUnitInfo,
			String permissionName) {
		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
		OrgUnitClientUtils.setMainBizOrgF7(f7, mainBizOrgType,
				new String[] { permissionName });

		f7.setValue(mainOrgUnitInfo);

	}

	/**
	 * 设置财务组织f7
	 * 
	 * @param f7
	 * @param mainBizOrgType
	 *            主业务组织，这里应该是财务组织 OrgType.Company
	 */
	public static final void setQualityOrgUnitF7(KDBizPromptBox f7,
			OrgType mainBizOrgType, OrgUnitInfo mainOrgUnitInfo,
			String permissionName) {
		f7.setQueryInfo("com.kingdee.eas.st.common.app.QualityOrgUnitQuery");
		OrgUnitClientUtils.setMainBizOrgF7(f7, mainBizOrgType,
				new String[] { permissionName });

		f7.setValue(mainOrgUnitInfo);

	}

	// /**
	// * 设置财务组织f7
	// * @param f7
	// * @param mainBizOrgType 主业务组织，这里应该是财务组织 OrgType.Company
	// */
	// public static final void setCompanyF7(KDBizPromptBox f7, OrgType
	// mainBizOrgType){
	//
	// CompanyOrgUnitInfo companyOrgUnitInfo =
	// (CompanyOrgUnitInfo)(com.kingdee.eas
	// .common.client.SysContext.getSysContext().getCurrentFIUnit());
	// if (companyOrgUnitInfo.isIsBizUnit()) {
	// setCompanyF7(f7,mainBizOrgType,companyOrgUnitInfo);
	// } else {
	// setCompanyF7(f7,mainBizOrgType,null);
	// }
	// }

	/**
	 * 取资源文件
	 * 
	 * @author wanglh
	 * @param strKey
	 * @return
	 */
	private static String getResource(String strKey) {
		if (strKey == null || strKey.trim().length() == 0) {
			return null;
		}
		return EASResource.getString("com.kingdee.eas.st.common.STResource",
				strKey);
	}
}
