/**
 * output package name
 */
package com.kingdee.eas.custom.bill.client;

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
public abstract class AbstractTtaskListEditUI extends com.kingdee.eas.st.common.client.STBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTtaskListEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlastUpdateTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFICompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continstancy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcreateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlastUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox billStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtnumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtauditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkcreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pklastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFICompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox instancy;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkcreateDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pklastUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.eas.custom.bill.TtaskListInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractTtaskListEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTtaskListEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contbillStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contFICompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contexchangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continstancy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcreateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlastUpdateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.billStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtnumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkbizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtdescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtauditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkcreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtlastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pklastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtFICompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcurrency = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtexchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.instancy = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkcreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pklastUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contbillStatus.setName("contbillStatus");
        this.contauditTime.setName("contauditTime");
        this.contnumber.setName("contnumber");
        this.contbizDate.setName("contbizDate");
        this.contdescription.setName("contdescription");
        this.contauditor.setName("contauditor");
        this.contcreator.setName("contcreator");
        this.contcreateTime.setName("contcreateTime");
        this.contlastUpdateUser.setName("contlastUpdateUser");
        this.contlastUpdateTime.setName("contlastUpdateTime");
        this.kdtEntry.setName("kdtEntry");
        this.contFICompany.setName("contFICompany");
        this.contcompany.setName("contcompany");
        this.contcurrency.setName("contcurrency");
        this.contexchangeRate.setName("contexchangeRate");
        this.continstancy.setName("continstancy");
        this.contcreateDate.setName("contcreateDate");
        this.contlastUpdateDate.setName("contlastUpdateDate");
        this.contauditDate.setName("contauditDate");
        this.billStatus.setName("billStatus");
        this.pkauditTime.setName("pkauditTime");
        this.txtnumber.setName("txtnumber");
        this.pkbizDate.setName("pkbizDate");
        this.txtdescription.setName("txtdescription");
        this.prmtauditor.setName("prmtauditor");
        this.prmtcreator.setName("prmtcreator");
        this.pkcreateTime.setName("pkcreateTime");
        this.prmtlastUpdateUser.setName("prmtlastUpdateUser");
        this.pklastUpdateTime.setName("pklastUpdateTime");
        this.prmtFICompany.setName("prmtFICompany");
        this.txtcompany.setName("txtcompany");
        this.txtcurrency.setName("txtcurrency");
        this.txtexchangeRate.setName("txtexchangeRate");
        this.instancy.setName("instancy");
        this.pkcreateDate.setName("pkcreateDate");
        this.pklastUpdateDate.setName("pklastUpdateDate");
        this.pkauditDate.setName("pkauditDate");
        // CoreUI
        // contbillStatus		
        this.contbillStatus.setBoundLabelText(resHelper.getString("contbillStatus.boundLabelText"));		
        this.contbillStatus.setBoundLabelLength(100);		
        this.contbillStatus.setBoundLabelUnderline(true);		
        this.contbillStatus.setVisible(false);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);
        // contnumber		
        this.contnumber.setBoundLabelText(resHelper.getString("contnumber.boundLabelText"));		
        this.contnumber.setBoundLabelLength(100);		
        this.contnumber.setBoundLabelUnderline(true);		
        this.contnumber.setVisible(true);
        // contbizDate		
        this.contbizDate.setBoundLabelText(resHelper.getString("contbizDate.boundLabelText"));		
        this.contbizDate.setBoundLabelLength(100);		
        this.contbizDate.setBoundLabelUnderline(true);		
        this.contbizDate.setVisible(true);
        // contdescription		
        this.contdescription.setBoundLabelText(resHelper.getString("contdescription.boundLabelText"));		
        this.contdescription.setBoundLabelLength(100);		
        this.contdescription.setBoundLabelUnderline(true);		
        this.contdescription.setVisible(false);
        // contauditor		
        this.contauditor.setBoundLabelText(resHelper.getString("contauditor.boundLabelText"));		
        this.contauditor.setBoundLabelLength(100);		
        this.contauditor.setBoundLabelUnderline(true);		
        this.contauditor.setVisible(true);
        // contcreator		
        this.contcreator.setBoundLabelText(resHelper.getString("contcreator.boundLabelText"));		
        this.contcreator.setBoundLabelLength(100);		
        this.contcreator.setBoundLabelUnderline(true);		
        this.contcreator.setVisible(true);
        // contcreateTime		
        this.contcreateTime.setBoundLabelText(resHelper.getString("contcreateTime.boundLabelText"));		
        this.contcreateTime.setBoundLabelLength(100);		
        this.contcreateTime.setBoundLabelUnderline(true);		
        this.contcreateTime.setVisible(false);
        // contlastUpdateUser		
        this.contlastUpdateUser.setBoundLabelText(resHelper.getString("contlastUpdateUser.boundLabelText"));		
        this.contlastUpdateUser.setBoundLabelLength(100);		
        this.contlastUpdateUser.setBoundLabelUnderline(true);		
        this.contlastUpdateUser.setVisible(true);
        // contlastUpdateTime		
        this.contlastUpdateTime.setBoundLabelText(resHelper.getString("contlastUpdateTime.boundLabelText"));		
        this.contlastUpdateTime.setBoundLabelLength(100);		
        this.contlastUpdateTime.setBoundLabelUnderline(true);		
        this.contlastUpdateTime.setVisible(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"personnel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"destination\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"vehicle\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{personnel}</t:Cell><t:Cell>$Resource{destination}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{vehicle}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"seq","personnel","destination","startDate","endDate","vehicle","amount","remark"});


        this.kdtEntry.checkParsed();
        KDFormattedTextField kdtEntry_seq_TextField = new KDFormattedTextField();
        kdtEntry_seq_TextField.setName("kdtEntry_seq_TextField");
        kdtEntry_seq_TextField.setVisible(true);
        kdtEntry_seq_TextField.setEditable(true);
        kdtEntry_seq_TextField.setHorizontalAlignment(2);
        kdtEntry_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntry_seq_CellEditor = new KDTDefaultCellEditor(kdtEntry_seq_TextField);
        this.kdtEntry.getColumn("seq").setEditor(kdtEntry_seq_CellEditor);
        KDTextField kdtEntry_personnel_TextField = new KDTextField();
        kdtEntry_personnel_TextField.setName("kdtEntry_personnel_TextField");
        kdtEntry_personnel_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_personnel_CellEditor = new KDTDefaultCellEditor(kdtEntry_personnel_TextField);
        this.kdtEntry.getColumn("personnel").setEditor(kdtEntry_personnel_CellEditor);
        KDTextField kdtEntry_destination_TextField = new KDTextField();
        kdtEntry_destination_TextField.setName("kdtEntry_destination_TextField");
        kdtEntry_destination_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_destination_CellEditor = new KDTDefaultCellEditor(kdtEntry_destination_TextField);
        this.kdtEntry.getColumn("destination").setEditor(kdtEntry_destination_CellEditor);
        KDDatePicker kdtEntry_startDate_DatePicker = new KDDatePicker();
        kdtEntry_startDate_DatePicker.setName("kdtEntry_startDate_DatePicker");
        kdtEntry_startDate_DatePicker.setVisible(true);
        kdtEntry_startDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_startDate_CellEditor = new KDTDefaultCellEditor(kdtEntry_startDate_DatePicker);
        this.kdtEntry.getColumn("startDate").setEditor(kdtEntry_startDate_CellEditor);
        KDDatePicker kdtEntry_endDate_DatePicker = new KDDatePicker();
        kdtEntry_endDate_DatePicker.setName("kdtEntry_endDate_DatePicker");
        kdtEntry_endDate_DatePicker.setVisible(true);
        kdtEntry_endDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_endDate_CellEditor = new KDTDefaultCellEditor(kdtEntry_endDate_DatePicker);
        this.kdtEntry.getColumn("endDate").setEditor(kdtEntry_endDate_CellEditor);
        KDDatePicker kdtEntry_vehicle_DatePicker = new KDDatePicker();
        kdtEntry_vehicle_DatePicker.setName("kdtEntry_vehicle_DatePicker");
        kdtEntry_vehicle_DatePicker.setVisible(true);
        kdtEntry_vehicle_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_vehicle_CellEditor = new KDTDefaultCellEditor(kdtEntry_vehicle_DatePicker);
        this.kdtEntry.getColumn("vehicle").setEditor(kdtEntry_vehicle_CellEditor);
        KDFormattedTextField kdtEntry_amount_TextField = new KDFormattedTextField();
        kdtEntry_amount_TextField.setName("kdtEntry_amount_TextField");
        kdtEntry_amount_TextField.setVisible(true);
        kdtEntry_amount_TextField.setEditable(true);
        kdtEntry_amount_TextField.setHorizontalAlignment(2);
        kdtEntry_amount_TextField.setDataType(1);
        	kdtEntry_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntry_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntry_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_amount_CellEditor = new KDTDefaultCellEditor(kdtEntry_amount_TextField);
        this.kdtEntry.getColumn("amount").setEditor(kdtEntry_amount_CellEditor);
        // contFICompany		
        this.contFICompany.setBoundLabelText(resHelper.getString("contFICompany.boundLabelText"));		
        this.contFICompany.setBoundLabelLength(100);		
        this.contFICompany.setBoundLabelUnderline(true);		
        this.contFICompany.setVisible(false);
        // contcompany		
        this.contcompany.setBoundLabelText(resHelper.getString("contcompany.boundLabelText"));		
        this.contcompany.setBoundLabelLength(100);		
        this.contcompany.setBoundLabelUnderline(true);		
        this.contcompany.setVisible(true);
        // contcurrency		
        this.contcurrency.setBoundLabelText(resHelper.getString("contcurrency.boundLabelText"));		
        this.contcurrency.setBoundLabelLength(100);		
        this.contcurrency.setBoundLabelUnderline(true);		
        this.contcurrency.setVisible(true);
        // contexchangeRate		
        this.contexchangeRate.setBoundLabelText(resHelper.getString("contexchangeRate.boundLabelText"));		
        this.contexchangeRate.setBoundLabelLength(100);		
        this.contexchangeRate.setBoundLabelUnderline(true);		
        this.contexchangeRate.setVisible(true);
        // continstancy		
        this.continstancy.setBoundLabelText(resHelper.getString("continstancy.boundLabelText"));		
        this.continstancy.setBoundLabelLength(100);		
        this.continstancy.setBoundLabelUnderline(true);		
        this.continstancy.setVisible(true);
        // contcreateDate		
        this.contcreateDate.setBoundLabelText(resHelper.getString("contcreateDate.boundLabelText"));		
        this.contcreateDate.setBoundLabelLength(100);		
        this.contcreateDate.setBoundLabelUnderline(true);		
        this.contcreateDate.setVisible(true);
        // contlastUpdateDate		
        this.contlastUpdateDate.setBoundLabelText(resHelper.getString("contlastUpdateDate.boundLabelText"));		
        this.contlastUpdateDate.setBoundLabelLength(100);		
        this.contlastUpdateDate.setBoundLabelUnderline(true);		
        this.contlastUpdateDate.setVisible(true);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(false);
        // billStatus		
        this.billStatus.setVisible(false);		
        this.billStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.scm.common.BillBaseStatusEnum").toArray());		
        this.billStatus.setRequired(false);		
        this.billStatus.setEnabled(false);
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setRequired(true);		
        this.pkauditTime.setEnabled(false);
        // txtnumber		
        this.txtnumber.setVisible(true);		
        this.txtnumber.setHorizontalAlignment(2);		
        this.txtnumber.setMaxLength(80);		
        this.txtnumber.setRequired(false);
        // pkbizDate		
        this.pkbizDate.setVisible(true);		
        this.pkbizDate.setRequired(true);
        // txtdescription		
        this.txtdescription.setVisible(false);		
        this.txtdescription.setHorizontalAlignment(2);		
        this.txtdescription.setMaxLength(80);		
        this.txtdescription.setRequired(false);
        // prmtauditor		
        this.prmtauditor.setVisible(true);		
        this.prmtauditor.setEditable(true);		
        this.prmtauditor.setDisplayFormat("$name$");		
        this.prmtauditor.setEditFormat("$number$");		
        this.prmtauditor.setCommitFormat("$number$");		
        this.prmtauditor.setRequired(true);		
        this.prmtauditor.setEnabled(false);
        // prmtcreator		
        this.prmtcreator.setVisible(true);		
        this.prmtcreator.setEditable(true);		
        this.prmtcreator.setDisplayFormat("$name$");		
        this.prmtcreator.setEditFormat("$number$");		
        this.prmtcreator.setCommitFormat("$number$");		
        this.prmtcreator.setRequired(true);		
        this.prmtcreator.setEnabled(false);
        // pkcreateTime		
        this.pkcreateTime.setVisible(false);		
        this.pkcreateTime.setRequired(false);		
        this.pkcreateTime.setEnabled(false);
        // prmtlastUpdateUser		
        this.prmtlastUpdateUser.setVisible(true);		
        this.prmtlastUpdateUser.setEditable(true);		
        this.prmtlastUpdateUser.setDisplayFormat("$name$");		
        this.prmtlastUpdateUser.setEditFormat("$number$");		
        this.prmtlastUpdateUser.setCommitFormat("$number$");		
        this.prmtlastUpdateUser.setRequired(true);
        // pklastUpdateTime		
        this.pklastUpdateTime.setVisible(false);		
        this.pklastUpdateTime.setRequired(false);		
        this.pklastUpdateTime.setEnabled(false);
        // prmtFICompany		
        this.prmtFICompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery4AsstAcct");		
        this.prmtFICompany.setVisible(false);		
        this.prmtFICompany.setEditable(true);		
        this.prmtFICompany.setDisplayFormat("$name$");		
        this.prmtFICompany.setEditFormat("$number$");		
        this.prmtFICompany.setCommitFormat("$number$");		
        this.prmtFICompany.setRequired(true);
        		setOrgF7(prmtFICompany,com.kingdee.eas.basedata.org.OrgType.getEnum("Company"));
					
        // txtcompany		
        this.txtcompany.setVisible(true);		
        this.txtcompany.setHorizontalAlignment(2);		
        this.txtcompany.setMaxLength(100);		
        this.txtcompany.setRequired(true);
        // txtcurrency		
        this.txtcurrency.setVisible(true);		
        this.txtcurrency.setHorizontalAlignment(2);		
        this.txtcurrency.setMaxLength(100);		
        this.txtcurrency.setRequired(true);
        // txtexchangeRate		
        this.txtexchangeRate.setVisible(true);		
        this.txtexchangeRate.setHorizontalAlignment(2);		
        this.txtexchangeRate.setDataType(1);		
        this.txtexchangeRate.setSupportedEmpty(true);		
        this.txtexchangeRate.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtexchangeRate.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtexchangeRate.setPrecision(4);		
        this.txtexchangeRate.setRequired(true);
        // instancy		
        this.instancy.setVisible(true);		
        this.instancy.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.taskmng.instancyDegree").toArray());		
        this.instancy.setRequired(true);
        // pkcreateDate		
        this.pkcreateDate.setVisible(true);		
        this.pkcreateDate.setRequired(false);
        // pklastUpdateDate		
        this.pklastUpdateDate.setVisible(true);		
        this.pklastUpdateDate.setRequired(true);		
        this.pklastUpdateDate.setEnabled(false);
        // pkauditDate		
        this.pkauditDate.setVisible(false);		
        this.pkauditDate.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtcompany,txtcurrency,txtexchangeRate,instancy,pkcreateDate,pklastUpdateDate,pkauditDate}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contbillStatus.setBounds(new Rectangle(736, 70, 270, 19));
        this.add(contbillStatus, new KDLayout.Constraints(736, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contauditTime.setBounds(new Rectangle(732, 604, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(732, 604, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contnumber.setBounds(new Rectangle(10, 6, 270, 19));
        this.add(contnumber, new KDLayout.Constraints(10, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbizDate.setBounds(new Rectangle(736, 6, 270, 19));
        this.add(contbizDate, new KDLayout.Constraints(736, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contdescription.setBounds(new Rectangle(373, 70, 270, 19));
        this.add(contdescription, new KDLayout.Constraints(373, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditor.setBounds(new Rectangle(371, 604, 270, 19));
        this.add(contauditor, new KDLayout.Constraints(371, 604, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcreator.setBounds(new Rectangle(10, 578, 270, 19));
        this.add(contcreator, new KDLayout.Constraints(10, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcreateTime.setBounds(new Rectangle(371, 556, 270, 19));
        this.add(contcreateTime, new KDLayout.Constraints(371, 556, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlastUpdateUser.setBounds(new Rectangle(732, 578, 270, 19));
        this.add(contlastUpdateUser, new KDLayout.Constraints(732, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contlastUpdateTime.setBounds(new Rectangle(732, 556, 270, 19));
        this.add(contlastUpdateTime, new KDLayout.Constraints(732, 556, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntry.setBounds(new Rectangle(10, 98, 993, 455));
        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.custom.bill.TtaskListEntryInfo(),null,false);
        this.add(kdtEntry_detailPanel, new KDLayout.Constraints(10, 98, 993, 455, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contFICompany.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contFICompany, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcompany.setBounds(new Rectangle(373, 6, 270, 19));
        this.add(contcompany, new KDLayout.Constraints(373, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurrency.setBounds(new Rectangle(10, 38, 270, 19));
        this.add(contcurrency, new KDLayout.Constraints(10, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contexchangeRate.setBounds(new Rectangle(373, 38, 270, 19));
        this.add(contexchangeRate, new KDLayout.Constraints(373, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continstancy.setBounds(new Rectangle(736, 38, 270, 19));
        this.add(continstancy, new KDLayout.Constraints(736, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcreateDate.setBounds(new Rectangle(371, 578, 270, 19));
        this.add(contcreateDate, new KDLayout.Constraints(371, 578, 270, 19, 0));
        contlastUpdateDate.setBounds(new Rectangle(10, 604, 270, 19));
        this.add(contlastUpdateDate, new KDLayout.Constraints(10, 604, 270, 19, 0));
        contauditDate.setBounds(new Rectangle(10, 556, 270, 19));
        this.add(contauditDate, new KDLayout.Constraints(10, 556, 270, 19, 0));
        //contbillStatus
        contbillStatus.setBoundEditor(billStatus);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //contnumber
        contnumber.setBoundEditor(txtnumber);
        //contbizDate
        contbizDate.setBoundEditor(pkbizDate);
        //contdescription
        contdescription.setBoundEditor(txtdescription);
        //contauditor
        contauditor.setBoundEditor(prmtauditor);
        //contcreator
        contcreator.setBoundEditor(prmtcreator);
        //contcreateTime
        contcreateTime.setBoundEditor(pkcreateTime);
        //contlastUpdateUser
        contlastUpdateUser.setBoundEditor(prmtlastUpdateUser);
        //contlastUpdateTime
        contlastUpdateTime.setBoundEditor(pklastUpdateTime);
        //contFICompany
        contFICompany.setBoundEditor(prmtFICompany);
        //contcompany
        contcompany.setBoundEditor(txtcompany);
        //contcurrency
        contcurrency.setBoundEditor(txtcurrency);
        //contexchangeRate
        contexchangeRate.setBoundEditor(txtexchangeRate);
        //continstancy
        continstancy.setBoundEditor(instancy);
        //contcreateDate
        contcreateDate.setBoundEditor(pkcreateDate);
        //contlastUpdateDate
        contlastUpdateDate.setBoundEditor(pklastUpdateDate);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);

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
		dataBinder.registerBinding("entries.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.custom.bill.TtaskListEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entries.personnel", String.class, this.kdtEntry, "personnel.text");
		dataBinder.registerBinding("entries.destination", String.class, this.kdtEntry, "destination.text");
		dataBinder.registerBinding("entries.startDate", java.util.Date.class, this.kdtEntry, "startDate.text");
		dataBinder.registerBinding("entries.endDate", java.util.Date.class, this.kdtEntry, "endDate.text");
		dataBinder.registerBinding("entries.vehicle", java.util.Date.class, this.kdtEntry, "vehicle.text");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entries.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("billStatus", com.kingdee.eas.scm.common.BillBaseStatusEnum.class, this.billStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkauditTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtnumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkbizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtdescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtauditor, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtcreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkcreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtlastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pklastUpdateTime, "value");
		dataBinder.registerBinding("FICompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtFICompany, "data");
		dataBinder.registerBinding("company", String.class, this.txtcompany, "text");
		dataBinder.registerBinding("currency", String.class, this.txtcurrency, "text");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtexchangeRate, "value");
		dataBinder.registerBinding("instancy", com.kingdee.eas.cp.taskmng.instancyDegree.class, this.instancy, "selectedItem");
		dataBinder.registerBinding("createDate", java.util.Date.class, this.pkcreateDate, "value");
		dataBinder.registerBinding("lastUpdateDate", java.util.Date.class, this.pklastUpdateDate, "value");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.bill.app.TtaskListEditUIHandler";
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
        this.txtcompany.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.bill.TtaskListInfo)ov;
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Company");
		}

	protected KDBizPromptBox getMainBizOrg() {
		return prmtFICompany;
}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("entries.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.personnel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.destination", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.vehicle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FICompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("instancy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    		
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
    	sic.add(new SelectorItemInfo("entries.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entries.personnel"));
    	sic.add(new SelectorItemInfo("entries.destination"));
    	sic.add(new SelectorItemInfo("entries.startDate"));
    	sic.add(new SelectorItemInfo("entries.endDate"));
    	sic.add(new SelectorItemInfo("entries.vehicle"));
    	sic.add(new SelectorItemInfo("entries.amount"));
    	sic.add(new SelectorItemInfo("entries.remark"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FICompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("FICompany.id"));
        	sic.add(new SelectorItemInfo("FICompany.number"));
        	sic.add(new SelectorItemInfo("FICompany.name"));
		}
        sic.add(new SelectorItemInfo("company"));
        sic.add(new SelectorItemInfo("currency"));
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("instancy"));
        sic.add(new SelectorItemInfo("createDate"));
        sic.add(new SelectorItemInfo("lastUpdateDate"));
        sic.add(new SelectorItemInfo("auditDate"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.bill.client", "TtaskListEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.bill.client.TtaskListEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.bill.TtaskListFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.bill.TtaskListInfo objectValue = new com.kingdee.eas.custom.bill.TtaskListInfo();
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")) != null && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")).getBoolean("isBizUnit"))
			objectValue.put("FICompany",com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")));
 
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkauditTime.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"审核时间"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkbizDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"业务日期"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtauditor.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"审核人"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtcreator.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"创建者"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtlastUpdateUser.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"最后修改者"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtcompany.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"当前组织"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtcurrency.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"币种"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtexchangeRate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"汇率"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(instancy.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"紧急程度"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pklastUpdateDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"修改日期"});
		}
			super.beforeStoreFields(arg0);
		}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("instancy","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}