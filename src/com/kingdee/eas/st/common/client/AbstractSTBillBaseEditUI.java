/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractSTBillBaseEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSTBillBaseEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton workbtnBizProcess;
    protected com.kingdee.bos.ctrl.swing.KDSeparator sp_bizProcess;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLineCopy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCloseEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnCloseEntry;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCurrentStorage;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQueryByMaterial;
    protected com.kingdee.bos.ctrl.swing.KDSeparator currentStorageSeparator;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCloseEntry;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnCloseEntry;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuBizProcess;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuAuditOption;
    protected com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem chkAuditAndPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemLineCopy;
    protected javax.swing.JPopupMenu.Separator separator4;
    protected com.kingdee.eas.st.common.STBillBaseInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionLineCopy actionLineCopy = null;
    protected ActionClose actionClose = null;
    protected ActionUnClose actionUnClose = null;
    protected ActionCloseEntry actionCloseEntry = null;
    protected ActionUnCloseEntry actionUnCloseEntry = null;
    protected ActionSubmitAndPrint actionSubmitAndPrint = null;
    protected ActionAuditAndPrint actionAuditAndPrint = null;
    protected ActionCurrentStorage actionCurrentStorage = null;
    protected ActionQueryByMaterial actionQueryByMaterial = null;
    /**
     * output class constructor
     */
    public AbstractSTBillBaseEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSTBillBaseEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCalculator
        String _tempStr = null;
        actionCalculator.setEnabled(true);
        actionCalculator.setDaemonRun(false);

        actionCalculator.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F11"));
        _tempStr = resHelper.getString("ActionCalculator.SHORT_DESCRIPTION");
        actionCalculator.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.LONG_DESCRIPTION");
        actionCalculator.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.NAME");
        actionCalculator.putValue(ItemAction.NAME, _tempStr);
         this.actionCalculator.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLineCopy
        this.actionLineCopy = new ActionLineCopy(this);
        getActionManager().registerAction("actionLineCopy", actionLineCopy);
         this.actionLineCopy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClose
        this.actionClose = new ActionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnClose
        this.actionUnClose = new ActionUnClose(this);
        getActionManager().registerAction("actionUnClose", actionUnClose);
         this.actionUnClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCloseEntry
        this.actionCloseEntry = new ActionCloseEntry(this);
        getActionManager().registerAction("actionCloseEntry", actionCloseEntry);
         this.actionCloseEntry.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnCloseEntry
        this.actionUnCloseEntry = new ActionUnCloseEntry(this);
        getActionManager().registerAction("actionUnCloseEntry", actionUnCloseEntry);
         this.actionUnCloseEntry.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmitAndPrint
        this.actionSubmitAndPrint = new ActionSubmitAndPrint(this);
        getActionManager().registerAction("actionSubmitAndPrint", actionSubmitAndPrint);
         this.actionSubmitAndPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAuditAndPrint
        this.actionAuditAndPrint = new ActionAuditAndPrint(this);
        getActionManager().registerAction("actionAuditAndPrint", actionAuditAndPrint);
         this.actionAuditAndPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCurrentStorage
        this.actionCurrentStorage = new ActionCurrentStorage(this);
        getActionManager().registerAction("actionCurrentStorage", actionCurrentStorage);
         this.actionCurrentStorage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQueryByMaterial
        this.actionQueryByMaterial = new ActionQueryByMaterial(this);
        getActionManager().registerAction("actionQueryByMaterial", actionQueryByMaterial);
         this.actionQueryByMaterial.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.workbtnBizProcess = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.sp_bizProcess = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnLineCopy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCloseEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnCloseEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCurrentStorage = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQueryByMaterial = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.currentStorageSeparator = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCloseEntry = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnCloseEntry = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuBizProcess = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuAuditOption = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.chkAuditAndPrint = new com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem();
        this.menuItemLineCopy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator4 = new javax.swing.JPopupMenu.Separator();
        this.workbtnBizProcess.setName("workbtnBizProcess");
        this.sp_bizProcess.setName("sp_bizProcess");
        this.btnLineCopy.setName("btnLineCopy");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnClose.setName("btnClose");
        this.btnUnClose.setName("btnUnClose");
        this.btnCloseEntry.setName("btnCloseEntry");
        this.btnUnCloseEntry.setName("btnUnCloseEntry");
        this.menuItemCurrentStorage.setName("menuItemCurrentStorage");
        this.menuItemQueryByMaterial.setName("menuItemQueryByMaterial");
        this.currentStorageSeparator.setName("currentStorageSeparator");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemClose.setName("menuItemClose");
        this.menuItemUnClose.setName("menuItemUnClose");
        this.menuItemCloseEntry.setName("menuItemCloseEntry");
        this.menuItemUnCloseEntry.setName("menuItemUnCloseEntry");
        this.kDSeparator8.setName("kDSeparator8");
        this.menuBizProcess.setName("menuBizProcess");
        this.menuAuditOption.setName("menuAuditOption");
        this.chkAuditAndPrint.setName("chkAuditAndPrint");
        this.menuItemLineCopy.setName("menuItemLineCopy");
        this.separator4.setName("separator4");
        // CoreUI
        this.chkMenuItemSubmitAndPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitAndPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDSeparator6.setVisible(false);
        // workbtnBizProcess		
        this.workbtnBizProcess.setText(resHelper.getString("workbtnBizProcess.text"));		
        this.workbtnBizProcess.setToolTipText(resHelper.getString("workbtnBizProcess.toolTipText"));		
        this.workbtnBizProcess.setVisible(false);
        // sp_bizProcess		
        this.sp_bizProcess.setVisible(false);
        // btnLineCopy
        this.btnLineCopy.setAction((IItemAction)ActionProxyFactory.getProxy(actionLineCopy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLineCopy.setText(resHelper.getString("btnLineCopy.text"));		
        this.btnLineCopy.setVisible(false);		
        this.btnLineCopy.setToolTipText(resHelper.getString("btnLineCopy.toolTipText"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_auditing"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_fauditing"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));		
        this.btnClose.setToolTipText(resHelper.getString("btnClose.toolTipText"));		
        this.btnClose.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_close"));		
        this.btnClose.setVisible(false);		
        this.btnClose.setEnabled(false);
        // btnUnClose
        this.btnUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnClose.setText(resHelper.getString("btnUnClose.text"));		
        this.btnUnClose.setToolTipText(resHelper.getString("btnUnClose.toolTipText"));		
        this.btnUnClose.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));		
        this.btnUnClose.setEnabled(false);		
        this.btnUnClose.setVisible(false);
        // btnCloseEntry
        this.btnCloseEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCloseEntry.setText(resHelper.getString("btnCloseEntry.text"));		
        this.btnCloseEntry.setToolTipText(resHelper.getString("btnCloseEntry.toolTipText"));		
        this.btnCloseEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_close"));		
        this.btnCloseEntry.setEnabled(false);		
        this.btnCloseEntry.setVisible(false);
        // btnUnCloseEntry
        this.btnUnCloseEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnCloseEntry.setText(resHelper.getString("btnUnCloseEntry.text"));		
        this.btnUnCloseEntry.setToolTipText(resHelper.getString("btnUnCloseEntry.toolTipText"));		
        this.btnUnCloseEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));		
        this.btnUnCloseEntry.setEnabled(false);		
        this.btnUnCloseEntry.setVisible(false);
        // menuItemCurrentStorage
        this.menuItemCurrentStorage.setAction((IItemAction)ActionProxyFactory.getProxy(actionCurrentStorage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCurrentStorage.setText(resHelper.getString("menuItemCurrentStorage.text"));		
        this.menuItemCurrentStorage.setToolTipText(resHelper.getString("menuItemCurrentStorage.toolTipText"));
        // menuItemQueryByMaterial
        this.menuItemQueryByMaterial.setAction((IItemAction)ActionProxyFactory.getProxy(actionQueryByMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQueryByMaterial.setText(resHelper.getString("menuItemQueryByMaterial.text"));
        // currentStorageSeparator
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_auditing"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_fauditing"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));
        // menuItemClose
        this.menuItemClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClose.setText(resHelper.getString("menuItemClose.text"));		
        this.menuItemClose.setToolTipText(resHelper.getString("menuItemClose.toolTipText"));		
        this.menuItemClose.setMnemonic(67);		
        this.menuItemClose.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_close"));		
        this.menuItemClose.setEnabled(false);		
        this.menuItemClose.setVisible(false);
        // menuItemUnClose
        this.menuItemUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnClose.setText(resHelper.getString("menuItemUnClose.text"));		
        this.menuItemUnClose.setMnemonic(85);		
        this.menuItemUnClose.setToolTipText(resHelper.getString("menuItemUnClose.toolTipText"));		
        this.menuItemUnClose.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));		
        this.menuItemUnClose.setEnabled(false);		
        this.menuItemUnClose.setVisible(false);
        // menuItemCloseEntry
        this.menuItemCloseEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCloseEntry.setText(resHelper.getString("menuItemCloseEntry.text"));		
        this.menuItemCloseEntry.setToolTipText(resHelper.getString("menuItemCloseEntry.toolTipText"));		
        this.menuItemCloseEntry.setMnemonic(65);		
        this.menuItemCloseEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_close"));		
        this.menuItemCloseEntry.setEnabled(false);		
        this.menuItemCloseEntry.setVisible(false);
        // menuItemUnCloseEntry
        this.menuItemUnCloseEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnCloseEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnCloseEntry.setText(resHelper.getString("menuItemUnCloseEntry.text"));		
        this.menuItemUnCloseEntry.setToolTipText(resHelper.getString("menuItemUnCloseEntry.toolTipText"));		
        this.menuItemUnCloseEntry.setMnemonic(66);		
        this.menuItemUnCloseEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));		
        this.menuItemUnCloseEntry.setEnabled(false);		
        this.menuItemUnCloseEntry.setVisible(false);
        // kDSeparator8		
        this.kDSeparator8.setVisible(false);
        // menuBizProcess		
        this.menuBizProcess.setText(resHelper.getString("menuBizProcess.text"));		
        this.menuBizProcess.setToolTipText(resHelper.getString("menuBizProcess.toolTipText"));		
        this.menuBizProcess.setVisible(false);
        // menuAuditOption		
        this.menuAuditOption.setText(resHelper.getString("menuAuditOption.text"));		
        this.menuAuditOption.setVisible(false);
        // chkAuditAndPrint
        this.chkAuditAndPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionAuditAndPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.chkAuditAndPrint.setText(resHelper.getString("chkAuditAndPrint.text"));		
        this.chkAuditAndPrint.setVisible(false);		
        this.chkAuditAndPrint.setMnemonic(65);
        // menuItemLineCopy
        this.menuItemLineCopy.setAction((IItemAction)ActionProxyFactory.getProxy(actionLineCopy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemLineCopy.setText(resHelper.getString("menuItemLineCopy.text"));		
        this.menuItemLineCopy.setVisible(false);		
        this.menuItemLineCopy.setToolTipText(resHelper.getString("menuItemLineCopy.toolTipText"));		
        this.menuItemLineCopy.setMnemonic(76);
        // separator4		
        this.separator4.setEnabled(false);		
        this.separator4.setVisible(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemCurrentStorage);
        menuView.add(menuItemQueryByMaterial);
        menuView.add(currentStorageSeparator);
        menuView.add(menuItemTraceUp);
        menuView.add(kDSeparator7);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemClose);
        menuBiz.add(menuItemUnClose);
        menuBiz.add(menuItemCloseEntry);
        menuBiz.add(menuItemUnCloseEntry);
        menuBiz.add(kDSeparator6);
        menuBiz.add(kDSeparator8);
        menuBiz.add(menuBizProcess);
        menuBiz.add(menuAuditOption);
        //menuAuditOption
        menuAuditOption.add(chkAuditAndPrint);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        menuTable1.add(menuItemLineCopy);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        menuWorkflow.add(separator4);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(workbtnBizProcess);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(sp_bizProcess);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnLineCopy);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnClose);
        this.toolBar.add(btnUnClose);
        this.toolBar.add(btnCloseEntry);
        this.toolBar.add(btnUnCloseEntry);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.st.common.app.STBillBaseEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.st.common.STBillBaseInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        return sic;
    }        
    	

    /**
     * output actionCalculator_actionPerformed method
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLineCopy_actionPerformed method
     */
    public void actionLineCopy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnClose_actionPerformed method
     */
    public void actionUnClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCloseEntry_actionPerformed method
     */
    public void actionCloseEntry_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnCloseEntry_actionPerformed method
     */
    public void actionUnCloseEntry_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSubmitAndPrint_actionPerformed method
     */
    public void actionSubmitAndPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAuditAndPrint_actionPerformed method
     */
    public void actionAuditAndPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCurrentStorage_actionPerformed method
     */
    public void actionCurrentStorage_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQueryByMaterial_actionPerformed method
     */
    public void actionQueryByMaterial_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCalculator(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCalculator(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalculator() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionLineCopy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLineCopy() {
    	return false;
    }
	public RequestContext prepareActionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClose() {
    	return false;
    }
	public RequestContext prepareActionUnClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnClose() {
    	return false;
    }
	public RequestContext prepareActionCloseEntry(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCloseEntry() {
    	return false;
    }
	public RequestContext prepareActionUnCloseEntry(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnCloseEntry() {
    	return false;
    }
	public RequestContext prepareActionSubmitAndPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitAndPrint() {
    	return false;
    }
	public RequestContext prepareActionAuditAndPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAuditAndPrint() {
    	return false;
    }
	public RequestContext prepareActionCurrentStorage(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCurrentStorage() {
    	return false;
    }
	public RequestContext prepareActionQueryByMaterial(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQueryByMaterial() {
    	return false;
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionLineCopy class
     */     
    protected class ActionLineCopy extends ItemAction {     
    
        public ActionLineCopy()
        {
            this(null);
        }

        public ActionLineCopy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift L"));
            _tempStr = resHelper.getString("ActionLineCopy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLineCopy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLineCopy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionLineCopy", "actionLineCopy_actionPerformed", e);
        }
    }

    /**
     * output ActionClose class
     */     
    protected class ActionClose extends ItemAction {     
    
        public ActionClose()
        {
            this(null);
        }

        public ActionClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output ActionUnClose class
     */     
    protected class ActionUnClose extends ItemAction {     
    
        public ActionUnClose()
        {
            this(null);
        }

        public ActionUnClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionUnClose", "actionUnClose_actionPerformed", e);
        }
    }

    /**
     * output ActionCloseEntry class
     */     
    protected class ActionCloseEntry extends ItemAction {     
    
        public ActionCloseEntry()
        {
            this(null);
        }

        public ActionCloseEntry(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCloseEntry.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseEntry.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseEntry.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionCloseEntry", "actionCloseEntry_actionPerformed", e);
        }
    }

    /**
     * output ActionUnCloseEntry class
     */     
    protected class ActionUnCloseEntry extends ItemAction {     
    
        public ActionUnCloseEntry()
        {
            this(null);
        }

        public ActionUnCloseEntry(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnCloseEntry.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseEntry.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnCloseEntry.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionUnCloseEntry", "actionUnCloseEntry_actionPerformed", e);
        }
    }

    /**
     * output ActionSubmitAndPrint class
     */     
    protected class ActionSubmitAndPrint extends ItemAction {     
    
        public ActionSubmitAndPrint()
        {
            this(null);
        }

        public ActionSubmitAndPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSubmitAndPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitAndPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitAndPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionSubmitAndPrint", "actionSubmitAndPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionAuditAndPrint class
     */     
    protected class ActionAuditAndPrint extends ItemAction {     
    
        public ActionAuditAndPrint()
        {
            this(null);
        }

        public ActionAuditAndPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAuditAndPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditAndPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditAndPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionAuditAndPrint", "actionAuditAndPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionCurrentStorage class
     */     
    protected class ActionCurrentStorage extends ItemAction {     
    
        public ActionCurrentStorage()
        {
            this(null);
        }

        public ActionCurrentStorage(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl F12"));
            _tempStr = resHelper.getString("ActionCurrentStorage.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCurrentStorage.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCurrentStorage.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionCurrentStorage", "actionCurrentStorage_actionPerformed", e);
        }
    }

    /**
     * output ActionQueryByMaterial class
     */     
    protected class ActionQueryByMaterial extends ItemAction {     
    
        public ActionQueryByMaterial()
        {
            this(null);
        }

        public ActionQueryByMaterial(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
            _tempStr = resHelper.getString("ActionQueryByMaterial.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQueryByMaterial.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQueryByMaterial.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSTBillBaseEditUI.this, "ActionQueryByMaterial", "actionQueryByMaterial_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.st.common.client", "STBillBaseEditUI");
    }




}