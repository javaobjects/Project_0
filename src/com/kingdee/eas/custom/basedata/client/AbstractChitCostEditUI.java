/**
 * output package name
 */
package com.kingdee.eas.custom.basedata.client;

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
public abstract class AbstractChitCostEditUI extends com.kingdee.eas.st.common.client.STBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChitCostEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisEnabled;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPropertyCost;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPropertyCost_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.eas.custom.basedata.ChitCostInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractChitCostEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChitCostEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdtPropertyCost = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtDescription.setName("txtDescription");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.chkisEnabled.setName("chkisEnabled");
        this.kdtPropertyCost.setName("kdtPropertyCost");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnSave.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(false);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(false);		
        this.kDLabelContainer2.setForeground(new java.awt.Color(0,0,0));
        // txtDescription		
        this.txtDescription.setMaxLength(255);		
        this.txtDescription.setVisible(false);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));		
        this.txtDescription.setRequired(false);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(false);		
        this.kDLabelContainer3.setForeground(new java.awt.Color(0,0,0));
        // chkisEnabled		
        this.chkisEnabled.setText(resHelper.getString("chkisEnabled.text"));		
        this.chkisEnabled.setHorizontalAlignment(2);		
        this.chkisEnabled.setEnabled(false);		
        this.chkisEnabled.setForeground(new java.awt.Color(0,0,0));		
        this.chkisEnabled.setVisible(false);
        // kdtPropertyCost
		String kdtPropertyCostStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"projectName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changeCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"fixedCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"otherCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"totalCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"effectiveDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"expiryDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"lastUpdateUser\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{projectCode}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{changeCost}</t:Cell><t:Cell>$Resource{fixedCost}</t:Cell><t:Cell>$Resource{otherCost}</t:Cell><t:Cell>$Resource{totalCost}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{effectiveDate}</t:Cell><t:Cell>$Resource{expiryDate}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtPropertyCost.setFormatXml(resHelper.translateString("kdtPropertyCost",kdtPropertyCostStrXML));

                this.kdtPropertyCost.putBindContents("editData",new String[] {"seq","projectCode","projectName","changeCost","fixedCost","otherCost","totalCost","state","effectiveDate","expiryDate","creator","createTime","lastUpdateUser","lastUpdateTime"});


        this.kdtPropertyCost.checkParsed();
        KDFormattedTextField kdtPropertyCost_seq_TextField = new KDFormattedTextField();
        kdtPropertyCost_seq_TextField.setName("kdtPropertyCost_seq_TextField");
        kdtPropertyCost_seq_TextField.setVisible(true);
        kdtPropertyCost_seq_TextField.setEditable(true);
        kdtPropertyCost_seq_TextField.setHorizontalAlignment(2);
        kdtPropertyCost_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtPropertyCost_seq_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_seq_TextField);
        this.kdtPropertyCost.getColumn("seq").setEditor(kdtPropertyCost_seq_CellEditor);
        KDTextField kdtPropertyCost_projectCode_TextField = new KDTextField();
        kdtPropertyCost_projectCode_TextField.setName("kdtPropertyCost_projectCode_TextField");
        kdtPropertyCost_projectCode_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtPropertyCost_projectCode_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_projectCode_TextField);
        this.kdtPropertyCost.getColumn("projectCode").setEditor(kdtPropertyCost_projectCode_CellEditor);
        KDTextField kdtPropertyCost_projectName_TextField = new KDTextField();
        kdtPropertyCost_projectName_TextField.setName("kdtPropertyCost_projectName_TextField");
        kdtPropertyCost_projectName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtPropertyCost_projectName_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_projectName_TextField);
        this.kdtPropertyCost.getColumn("projectName").setEditor(kdtPropertyCost_projectName_CellEditor);
        KDFormattedTextField kdtPropertyCost_changeCost_TextField = new KDFormattedTextField();
        kdtPropertyCost_changeCost_TextField.setName("kdtPropertyCost_changeCost_TextField");
        kdtPropertyCost_changeCost_TextField.setVisible(true);
        kdtPropertyCost_changeCost_TextField.setEditable(true);
        kdtPropertyCost_changeCost_TextField.setHorizontalAlignment(2);
        kdtPropertyCost_changeCost_TextField.setDataType(1);
        	kdtPropertyCost_changeCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtPropertyCost_changeCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtPropertyCost_changeCost_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtPropertyCost_changeCost_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_changeCost_TextField);
        this.kdtPropertyCost.getColumn("changeCost").setEditor(kdtPropertyCost_changeCost_CellEditor);
        KDFormattedTextField kdtPropertyCost_fixedCost_TextField = new KDFormattedTextField();
        kdtPropertyCost_fixedCost_TextField.setName("kdtPropertyCost_fixedCost_TextField");
        kdtPropertyCost_fixedCost_TextField.setVisible(true);
        kdtPropertyCost_fixedCost_TextField.setEditable(true);
        kdtPropertyCost_fixedCost_TextField.setHorizontalAlignment(2);
        kdtPropertyCost_fixedCost_TextField.setDataType(1);
        	kdtPropertyCost_fixedCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtPropertyCost_fixedCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtPropertyCost_fixedCost_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtPropertyCost_fixedCost_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_fixedCost_TextField);
        this.kdtPropertyCost.getColumn("fixedCost").setEditor(kdtPropertyCost_fixedCost_CellEditor);
        KDFormattedTextField kdtPropertyCost_otherCost_TextField = new KDFormattedTextField();
        kdtPropertyCost_otherCost_TextField.setName("kdtPropertyCost_otherCost_TextField");
        kdtPropertyCost_otherCost_TextField.setVisible(true);
        kdtPropertyCost_otherCost_TextField.setEditable(true);
        kdtPropertyCost_otherCost_TextField.setHorizontalAlignment(2);
        kdtPropertyCost_otherCost_TextField.setDataType(1);
        	kdtPropertyCost_otherCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtPropertyCost_otherCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtPropertyCost_otherCost_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtPropertyCost_otherCost_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_otherCost_TextField);
        this.kdtPropertyCost.getColumn("otherCost").setEditor(kdtPropertyCost_otherCost_CellEditor);
        KDFormattedTextField kdtPropertyCost_totalCost_TextField = new KDFormattedTextField();
        kdtPropertyCost_totalCost_TextField.setName("kdtPropertyCost_totalCost_TextField");
        kdtPropertyCost_totalCost_TextField.setVisible(true);
        kdtPropertyCost_totalCost_TextField.setEditable(true);
        kdtPropertyCost_totalCost_TextField.setHorizontalAlignment(2);
        kdtPropertyCost_totalCost_TextField.setDataType(1);
        	kdtPropertyCost_totalCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtPropertyCost_totalCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtPropertyCost_totalCost_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtPropertyCost_totalCost_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_totalCost_TextField);
        this.kdtPropertyCost.getColumn("totalCost").setEditor(kdtPropertyCost_totalCost_CellEditor);
        KDComboBox kdtPropertyCost_state_ComboBox = new KDComboBox();
        kdtPropertyCost_state_ComboBox.setName("kdtPropertyCost_state_ComboBox");
        kdtPropertyCost_state_ComboBox.setVisible(true);
        kdtPropertyCost_state_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.base.ssc.StatusEnum").toArray());
        KDTDefaultCellEditor kdtPropertyCost_state_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_state_ComboBox);
        this.kdtPropertyCost.getColumn("state").setEditor(kdtPropertyCost_state_CellEditor);
        KDDatePicker kdtPropertyCost_effectiveDate_DatePicker = new KDDatePicker();
        kdtPropertyCost_effectiveDate_DatePicker.setName("kdtPropertyCost_effectiveDate_DatePicker");
        kdtPropertyCost_effectiveDate_DatePicker.setVisible(true);
        kdtPropertyCost_effectiveDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtPropertyCost_effectiveDate_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_effectiveDate_DatePicker);
        this.kdtPropertyCost.getColumn("effectiveDate").setEditor(kdtPropertyCost_effectiveDate_CellEditor);
        KDDatePicker kdtPropertyCost_expiryDate_DatePicker = new KDDatePicker();
        kdtPropertyCost_expiryDate_DatePicker.setName("kdtPropertyCost_expiryDate_DatePicker");
        kdtPropertyCost_expiryDate_DatePicker.setVisible(true);
        kdtPropertyCost_expiryDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtPropertyCost_expiryDate_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_expiryDate_DatePicker);
        this.kdtPropertyCost.getColumn("expiryDate").setEditor(kdtPropertyCost_expiryDate_CellEditor);
        final KDBizPromptBox kdtPropertyCost_creator_PromptBox = new KDBizPromptBox();
        kdtPropertyCost_creator_PromptBox.setQueryInfo("com.kingdee.eas.base.ssc.app.UserQuery");
        kdtPropertyCost_creator_PromptBox.setVisible(true);
        kdtPropertyCost_creator_PromptBox.setEditable(true);
        kdtPropertyCost_creator_PromptBox.setDisplayFormat("$number$");
        kdtPropertyCost_creator_PromptBox.setEditFormat("$number$");
        kdtPropertyCost_creator_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtPropertyCost_creator_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_creator_PromptBox);
        this.kdtPropertyCost.getColumn("creator").setEditor(kdtPropertyCost_creator_CellEditor);
        ObjectValueRender kdtPropertyCost_creator_OVR = new ObjectValueRender();
        kdtPropertyCost_creator_OVR.setFormat(new BizDataFormat("$password$"));
        this.kdtPropertyCost.getColumn("creator").setRenderer(kdtPropertyCost_creator_OVR);
        KDDatePicker kdtPropertyCost_createTime_DatePicker = new KDDatePicker();
        kdtPropertyCost_createTime_DatePicker.setName("kdtPropertyCost_createTime_DatePicker");
        kdtPropertyCost_createTime_DatePicker.setVisible(true);
        kdtPropertyCost_createTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtPropertyCost_createTime_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_createTime_DatePicker);
        this.kdtPropertyCost.getColumn("createTime").setEditor(kdtPropertyCost_createTime_CellEditor);
        final KDBizPromptBox kdtPropertyCost_lastUpdateUser_PromptBox = new KDBizPromptBox();
        kdtPropertyCost_lastUpdateUser_PromptBox.setQueryInfo("com.kingdee.eas.base.ssc.app.UserQuery");
        kdtPropertyCost_lastUpdateUser_PromptBox.setVisible(true);
        kdtPropertyCost_lastUpdateUser_PromptBox.setEditable(true);
        kdtPropertyCost_lastUpdateUser_PromptBox.setDisplayFormat("$number$");
        kdtPropertyCost_lastUpdateUser_PromptBox.setEditFormat("$number$");
        kdtPropertyCost_lastUpdateUser_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtPropertyCost_lastUpdateUser_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_lastUpdateUser_PromptBox);
        this.kdtPropertyCost.getColumn("lastUpdateUser").setEditor(kdtPropertyCost_lastUpdateUser_CellEditor);
        ObjectValueRender kdtPropertyCost_lastUpdateUser_OVR = new ObjectValueRender();
        kdtPropertyCost_lastUpdateUser_OVR.setFormat(new BizDataFormat("$password$"));
        this.kdtPropertyCost.getColumn("lastUpdateUser").setRenderer(kdtPropertyCost_lastUpdateUser_OVR);
        KDDatePicker kdtPropertyCost_lastUpdateTime_DatePicker = new KDDatePicker();
        kdtPropertyCost_lastUpdateTime_DatePicker.setName("kdtPropertyCost_lastUpdateTime_DatePicker");
        kdtPropertyCost_lastUpdateTime_DatePicker.setVisible(true);
        kdtPropertyCost_lastUpdateTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtPropertyCost_lastUpdateTime_CellEditor = new KDTDefaultCellEditor(kdtPropertyCost_lastUpdateTime_DatePicker);
        this.kdtPropertyCost.getColumn("lastUpdateTime").setEditor(kdtPropertyCost_lastUpdateTime_CellEditor);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(false);
        // txtName		
        this.txtName.setVisible(true);		
        this.txtName.setEnabled(true);		
        this.txtName.setForeground(new java.awt.Color(0,0,0));		
        this.txtName.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtDescription,txtNumber,txtName,chkisEnabled}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1425, 472));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(13, 11, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(336, 11, 270, 19));
        this.add(kDLabelContainer2, null);
        txtDescription.setBounds(new Rectangle(76, 48, 203, 17));
        this.add(txtDescription, null);
        kDLabelContainer3.setBounds(new Rectangle(15, 44, 44, 19));
        this.add(kDLabelContainer3, null);
        chkisEnabled.setBounds(new Rectangle(653, 11, 91, 19));
        this.add(chkisEnabled, null);
        kdtPropertyCost.setBounds(new Rectangle(8, 79, 1398, 358));
        kdtPropertyCost_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPropertyCost,new com.kingdee.eas.custom.basedata.ChitCostPropertyCostInfo(),null,false);
        this.add(kdtPropertyCost_detailPanel, null);
		kdtPropertyCost_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("state",new Integer(10));
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);

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
        this.menuBar.add(menuTool);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("isEnabled", boolean.class, this.chkisEnabled, "selected");
		dataBinder.registerBinding("PropertyCost.seq", int.class, this.kdtPropertyCost, "seq.text");
		dataBinder.registerBinding("PropertyCost", com.kingdee.eas.custom.basedata.ChitCostPropertyCostInfo.class, this.kdtPropertyCost, "userObject");
		dataBinder.registerBinding("PropertyCost.projectCode", String.class, this.kdtPropertyCost, "projectCode.text");
		dataBinder.registerBinding("PropertyCost.projectName", String.class, this.kdtPropertyCost, "projectName.text");
		dataBinder.registerBinding("PropertyCost.changeCost", java.math.BigDecimal.class, this.kdtPropertyCost, "changeCost.text");
		dataBinder.registerBinding("PropertyCost.fixedCost", java.math.BigDecimal.class, this.kdtPropertyCost, "fixedCost.text");
		dataBinder.registerBinding("PropertyCost.otherCost", java.math.BigDecimal.class, this.kdtPropertyCost, "otherCost.text");
		dataBinder.registerBinding("PropertyCost.totalCost", java.math.BigDecimal.class, this.kdtPropertyCost, "totalCost.text");
		dataBinder.registerBinding("PropertyCost.state", com.kingdee.util.enums.Enum.class, this.kdtPropertyCost, "state.text");
		dataBinder.registerBinding("PropertyCost.effectiveDate", java.util.Date.class, this.kdtPropertyCost, "effectiveDate.text");
		dataBinder.registerBinding("PropertyCost.expiryDate", java.util.Date.class, this.kdtPropertyCost, "expiryDate.text");
		dataBinder.registerBinding("PropertyCost.creator", java.lang.Object.class, this.kdtPropertyCost, "creator.text");
		dataBinder.registerBinding("PropertyCost.createTime", java.util.Date.class, this.kdtPropertyCost, "createTime.text");
		dataBinder.registerBinding("PropertyCost.lastUpdateUser", java.lang.Object.class, this.kdtPropertyCost, "lastUpdateUser.text");
		dataBinder.registerBinding("PropertyCost.lastUpdateTime", java.util.Date.class, this.kdtPropertyCost, "lastUpdateTime.text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.basedata.app.ChitCostEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtDescription.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.basedata.ChitCostInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEnabled", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.projectCode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.changeCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.fixedCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.otherCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.totalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.effectiveDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.expiryDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PropertyCost.lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtNumber.setEnabled(false);
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
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isEnabled"));
    	sic.add(new SelectorItemInfo("PropertyCost.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("PropertyCost.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("PropertyCost.projectCode"));
    	sic.add(new SelectorItemInfo("PropertyCost.projectName"));
    	sic.add(new SelectorItemInfo("PropertyCost.changeCost"));
    	sic.add(new SelectorItemInfo("PropertyCost.fixedCost"));
    	sic.add(new SelectorItemInfo("PropertyCost.otherCost"));
    	sic.add(new SelectorItemInfo("PropertyCost.totalCost"));
    	sic.add(new SelectorItemInfo("PropertyCost.state"));
    	sic.add(new SelectorItemInfo("PropertyCost.effectiveDate"));
    	sic.add(new SelectorItemInfo("PropertyCost.expiryDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("PropertyCost.creator.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("PropertyCost.creator.id"));
			sic.add(new SelectorItemInfo("PropertyCost.creator.password"));
			sic.add(new SelectorItemInfo("PropertyCost.creator.name"));
        	sic.add(new SelectorItemInfo("PropertyCost.creator.number"));
		}
    	sic.add(new SelectorItemInfo("PropertyCost.createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("PropertyCost.lastUpdateUser.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("PropertyCost.lastUpdateUser.id"));
			sic.add(new SelectorItemInfo("PropertyCost.lastUpdateUser.password"));
			sic.add(new SelectorItemInfo("PropertyCost.lastUpdateUser.name"));
        	sic.add(new SelectorItemInfo("PropertyCost.lastUpdateUser.number"));
		}
    	sic.add(new SelectorItemInfo("PropertyCost.lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.basedata.client", "ChitCostEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.basedata.client.ChitCostEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.basedata.ChitCostFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.basedata.ChitCostInfo objectValue = new com.kingdee.eas.custom.basedata.ChitCostInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtPropertyCost;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}