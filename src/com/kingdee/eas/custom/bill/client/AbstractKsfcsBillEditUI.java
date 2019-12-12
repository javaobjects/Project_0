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
public abstract class AbstractKsfcsBillEditUI extends com.kingdee.eas.st.common.client.STBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractKsfcsBillEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpropertyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpropertyNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractSumCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbDefaultTxt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbDefaultMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaDefaultTxt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaDefaultMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremarks;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttaxRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamountPayable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamountContentious;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamountConfirmation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdueDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcostItem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsourceSystem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttotalSum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttotalTax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttotalNoTax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnoInvoiceSum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYesInvoiceSum;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea3;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpropertyName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpropertyNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsupplier;
    protected com.kingdee.bos.ctrl.swing.KDComboBox Status;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcontractSumCost;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbDefaultMoney;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtaDefaultMoney;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttaxRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamountPayable;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamountContentious;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamountConfirmation;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdueDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcostItem;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsourceSystem;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttotalSum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttotalTax;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttotalNoTax;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtnoInvoiceSum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtYesInvoiceSum;
    protected com.kingdee.eas.custom.bill.KsfcsBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractKsfcsBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractKsfcsBillEditUI.class.getName());
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
        this.contpropertyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpropertyNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractSumCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbDefaultTxt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbDefaultMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaDefaultTxt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaDefaultMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contremarks = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttaxRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamountPayable = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamountContentious = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamountConfirmation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdueDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcostItem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsourceSystem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttotalSum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttotalTax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttotalNoTax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnoInvoiceSum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYesInvoiceSum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTextArea2 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTextArea3 = new com.kingdee.bos.ctrl.swing.KDTextArea();
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
        this.prmtcompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtpropertyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpropertyNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtsupplier = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.Status = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtcontractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcontractSumCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcurrency = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbDefaultMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtaDefaultMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txttaxRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtamountPayable = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtamountContentious = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtamountConfirmation = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkdueDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtcostItem = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtsourceSystem = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttotalSum = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txttotalTax = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txttotalNoTax = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtnoInvoiceSum = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtYesInvoiceSum = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
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
        this.contpropertyName.setName("contpropertyName");
        this.contpropertyNumber.setName("contpropertyNumber");
        this.contsupplier.setName("contsupplier");
        this.contStatus.setName("contStatus");
        this.contcontractNumber.setName("contcontractNumber");
        this.contcontractSumCost.setName("contcontractSumCost");
        this.contcurrency.setName("contcurrency");
        this.contbDefaultTxt.setName("contbDefaultTxt");
        this.contbDefaultMoney.setName("contbDefaultMoney");
        this.contaDefaultTxt.setName("contaDefaultTxt");
        this.contaDefaultMoney.setName("contaDefaultMoney");
        this.contremarks.setName("contremarks");
        this.conttaxRate.setName("conttaxRate");
        this.contamountPayable.setName("contamountPayable");
        this.contamountContentious.setName("contamountContentious");
        this.contamountConfirmation.setName("contamountConfirmation");
        this.contdueDate.setName("contdueDate");
        this.contcostItem.setName("contcostItem");
        this.contsourceSystem.setName("contsourceSystem");
        this.conttotalSum.setName("conttotalSum");
        this.conttotalTax.setName("conttotalTax");
        this.conttotalNoTax.setName("conttotalNoTax");
        this.contnoInvoiceSum.setName("contnoInvoiceSum");
        this.contYesInvoiceSum.setName("contYesInvoiceSum");
        this.kDTextArea1.setName("kDTextArea1");
        this.kDTextArea2.setName("kDTextArea2");
        this.kDTextArea3.setName("kDTextArea3");
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
        this.prmtcompany.setName("prmtcompany");
        this.txtpropertyName.setName("txtpropertyName");
        this.txtpropertyNumber.setName("txtpropertyNumber");
        this.txtsupplier.setName("txtsupplier");
        this.Status.setName("Status");
        this.txtcontractNumber.setName("txtcontractNumber");
        this.txtcontractSumCost.setName("txtcontractSumCost");
        this.txtcurrency.setName("txtcurrency");
        this.txtbDefaultMoney.setName("txtbDefaultMoney");
        this.txtaDefaultMoney.setName("txtaDefaultMoney");
        this.txttaxRate.setName("txttaxRate");
        this.txtamountPayable.setName("txtamountPayable");
        this.txtamountContentious.setName("txtamountContentious");
        this.txtamountConfirmation.setName("txtamountConfirmation");
        this.pkdueDate.setName("pkdueDate");
        this.prmtcostItem.setName("prmtcostItem");
        this.txtsourceSystem.setName("txtsourceSystem");
        this.txttotalSum.setName("txttotalSum");
        this.txttotalTax.setName("txttotalTax");
        this.txttotalNoTax.setName("txttotalNoTax");
        this.txtnoInvoiceSum.setName("txtnoInvoiceSum");
        this.txtYesInvoiceSum.setName("txtYesInvoiceSum");
        // CoreUI
        // contbillStatus		
        this.contbillStatus.setBoundLabelText(resHelper.getString("contbillStatus.boundLabelText"));		
        this.contbillStatus.setBoundLabelLength(100);		
        this.contbillStatus.setBoundLabelUnderline(true);		
        this.contbillStatus.setVisible(true);
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
        this.contcreateTime.setVisible(true);
        // contlastUpdateUser		
        this.contlastUpdateUser.setBoundLabelText(resHelper.getString("contlastUpdateUser.boundLabelText"));		
        this.contlastUpdateUser.setBoundLabelLength(100);		
        this.contlastUpdateUser.setBoundLabelUnderline(true);		
        this.contlastUpdateUser.setVisible(true);
        // contlastUpdateTime		
        this.contlastUpdateTime.setBoundLabelText(resHelper.getString("contlastUpdateTime.boundLabelText"));		
        this.contlastUpdateTime.setBoundLabelLength(100);		
        this.contlastUpdateTime.setBoundLabelUnderline(true);		
        this.contlastUpdateTime.setVisible(true);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"seq"});


        this.kdtEntry.checkParsed();
        KDFormattedTextField kdtEntry_seq_TextField = new KDFormattedTextField();
        kdtEntry_seq_TextField.setName("kdtEntry_seq_TextField");
        kdtEntry_seq_TextField.setVisible(true);
        kdtEntry_seq_TextField.setEditable(true);
        kdtEntry_seq_TextField.setHorizontalAlignment(2);
        kdtEntry_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntry_seq_CellEditor = new KDTDefaultCellEditor(kdtEntry_seq_TextField);
        this.kdtEntry.getColumn("seq").setEditor(kdtEntry_seq_CellEditor);
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
        // contpropertyName		
        this.contpropertyName.setBoundLabelText(resHelper.getString("contpropertyName.boundLabelText"));		
        this.contpropertyName.setBoundLabelLength(100);		
        this.contpropertyName.setBoundLabelUnderline(true);		
        this.contpropertyName.setVisible(true);
        // contpropertyNumber		
        this.contpropertyNumber.setBoundLabelText(resHelper.getString("contpropertyNumber.boundLabelText"));		
        this.contpropertyNumber.setBoundLabelLength(100);		
        this.contpropertyNumber.setBoundLabelUnderline(true);		
        this.contpropertyNumber.setVisible(true);
        // contsupplier		
        this.contsupplier.setBoundLabelText(resHelper.getString("contsupplier.boundLabelText"));		
        this.contsupplier.setBoundLabelLength(100);		
        this.contsupplier.setBoundLabelUnderline(true);		
        this.contsupplier.setVisible(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setVisible(false);
        // contcontractNumber		
        this.contcontractNumber.setBoundLabelText(resHelper.getString("contcontractNumber.boundLabelText"));		
        this.contcontractNumber.setBoundLabelLength(100);		
        this.contcontractNumber.setBoundLabelUnderline(true);		
        this.contcontractNumber.setVisible(true);
        // contcontractSumCost		
        this.contcontractSumCost.setBoundLabelText(resHelper.getString("contcontractSumCost.boundLabelText"));		
        this.contcontractSumCost.setBoundLabelLength(100);		
        this.contcontractSumCost.setBoundLabelUnderline(true);		
        this.contcontractSumCost.setVisible(true);
        // contcurrency		
        this.contcurrency.setBoundLabelText(resHelper.getString("contcurrency.boundLabelText"));		
        this.contcurrency.setBoundLabelLength(100);		
        this.contcurrency.setBoundLabelUnderline(true);		
        this.contcurrency.setVisible(true);
        // contbDefaultTxt		
        this.contbDefaultTxt.setBoundLabelText(resHelper.getString("contbDefaultTxt.boundLabelText"));		
        this.contbDefaultTxt.setBoundLabelLength(100);		
        this.contbDefaultTxt.setVisible(true);
        // contbDefaultMoney		
        this.contbDefaultMoney.setBoundLabelText(resHelper.getString("contbDefaultMoney.boundLabelText"));		
        this.contbDefaultMoney.setBoundLabelLength(100);		
        this.contbDefaultMoney.setBoundLabelUnderline(true);		
        this.contbDefaultMoney.setVisible(true);
        // contaDefaultTxt		
        this.contaDefaultTxt.setBoundLabelText(resHelper.getString("contaDefaultTxt.boundLabelText"));		
        this.contaDefaultTxt.setBoundLabelLength(100);		
        this.contaDefaultTxt.setVisible(true);
        // contaDefaultMoney		
        this.contaDefaultMoney.setBoundLabelText(resHelper.getString("contaDefaultMoney.boundLabelText"));		
        this.contaDefaultMoney.setBoundLabelLength(100);		
        this.contaDefaultMoney.setBoundLabelUnderline(true);		
        this.contaDefaultMoney.setVisible(true);
        // contremarks		
        this.contremarks.setBoundLabelText(resHelper.getString("contremarks.boundLabelText"));		
        this.contremarks.setBoundLabelLength(100);		
        this.contremarks.setVisible(true);
        // conttaxRate		
        this.conttaxRate.setBoundLabelText(resHelper.getString("conttaxRate.boundLabelText"));		
        this.conttaxRate.setBoundLabelLength(100);		
        this.conttaxRate.setBoundLabelUnderline(true);		
        this.conttaxRate.setVisible(true);
        // contamountPayable		
        this.contamountPayable.setBoundLabelText(resHelper.getString("contamountPayable.boundLabelText"));		
        this.contamountPayable.setBoundLabelLength(100);		
        this.contamountPayable.setBoundLabelUnderline(true);		
        this.contamountPayable.setVisible(true);
        // contamountContentious		
        this.contamountContentious.setBoundLabelText(resHelper.getString("contamountContentious.boundLabelText"));		
        this.contamountContentious.setBoundLabelLength(100);		
        this.contamountContentious.setBoundLabelUnderline(true);		
        this.contamountContentious.setVisible(true);
        // contamountConfirmation		
        this.contamountConfirmation.setBoundLabelText(resHelper.getString("contamountConfirmation.boundLabelText"));		
        this.contamountConfirmation.setBoundLabelLength(100);		
        this.contamountConfirmation.setBoundLabelUnderline(true);		
        this.contamountConfirmation.setVisible(true);
        // contdueDate		
        this.contdueDate.setBoundLabelText(resHelper.getString("contdueDate.boundLabelText"));		
        this.contdueDate.setBoundLabelLength(100);		
        this.contdueDate.setBoundLabelUnderline(true);		
        this.contdueDate.setVisible(true);
        // contcostItem		
        this.contcostItem.setBoundLabelText(resHelper.getString("contcostItem.boundLabelText"));		
        this.contcostItem.setBoundLabelLength(100);		
        this.contcostItem.setBoundLabelUnderline(true);		
        this.contcostItem.setVisible(true);
        // contsourceSystem		
        this.contsourceSystem.setBoundLabelText(resHelper.getString("contsourceSystem.boundLabelText"));		
        this.contsourceSystem.setBoundLabelLength(100);		
        this.contsourceSystem.setBoundLabelUnderline(true);		
        this.contsourceSystem.setVisible(true);
        // conttotalSum		
        this.conttotalSum.setBoundLabelText(resHelper.getString("conttotalSum.boundLabelText"));		
        this.conttotalSum.setBoundLabelLength(100);		
        this.conttotalSum.setBoundLabelUnderline(true);		
        this.conttotalSum.setVisible(true);
        // conttotalTax		
        this.conttotalTax.setBoundLabelText(resHelper.getString("conttotalTax.boundLabelText"));		
        this.conttotalTax.setBoundLabelLength(100);		
        this.conttotalTax.setBoundLabelUnderline(true);		
        this.conttotalTax.setVisible(true);
        // conttotalNoTax		
        this.conttotalNoTax.setBoundLabelText(resHelper.getString("conttotalNoTax.boundLabelText"));		
        this.conttotalNoTax.setBoundLabelLength(100);		
        this.conttotalNoTax.setBoundLabelUnderline(true);		
        this.conttotalNoTax.setVisible(true);
        // contnoInvoiceSum		
        this.contnoInvoiceSum.setBoundLabelText(resHelper.getString("contnoInvoiceSum.boundLabelText"));		
        this.contnoInvoiceSum.setBoundLabelLength(100);		
        this.contnoInvoiceSum.setBoundLabelUnderline(true);		
        this.contnoInvoiceSum.setVisible(true);
        // contYesInvoiceSum		
        this.contYesInvoiceSum.setBoundLabelText(resHelper.getString("contYesInvoiceSum.boundLabelText"));		
        this.contYesInvoiceSum.setBoundLabelLength(100);		
        this.contYesInvoiceSum.setBoundLabelUnderline(true);		
        this.contYesInvoiceSum.setVisible(true);
        // kDTextArea1
        // kDTextArea2
        // kDTextArea3
        // billStatus		
        this.billStatus.setVisible(true);		
        this.billStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.scm.common.BillBaseStatusEnum").toArray());		
        this.billStatus.setRequired(true);
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setRequired(false);		
        this.pkauditTime.setEnabled(false);
        // txtnumber		
        this.txtnumber.setVisible(true);		
        this.txtnumber.setHorizontalAlignment(2);		
        this.txtnumber.setMaxLength(80);		
        this.txtnumber.setRequired(true);
        // pkbizDate		
        this.pkbizDate.setVisible(true);		
        this.pkbizDate.setRequired(true);
        // txtdescription		
        this.txtdescription.setVisible(true);		
        this.txtdescription.setHorizontalAlignment(2);		
        this.txtdescription.setMaxLength(80);		
        this.txtdescription.setRequired(false);
        // prmtauditor		
        this.prmtauditor.setVisible(true);		
        this.prmtauditor.setEditable(true);		
        this.prmtauditor.setDisplayFormat("$name$");		
        this.prmtauditor.setEditFormat("$number$");		
        this.prmtauditor.setCommitFormat("$number$");		
        this.prmtauditor.setRequired(false);		
        this.prmtauditor.setEnabled(false);
        // prmtcreator		
        this.prmtcreator.setVisible(true);		
        this.prmtcreator.setEditable(true);		
        this.prmtcreator.setDisplayFormat("$name$");		
        this.prmtcreator.setEditFormat("$number$");		
        this.prmtcreator.setCommitFormat("$number$");		
        this.prmtcreator.setRequired(false);		
        this.prmtcreator.setEnabled(false);
        // pkcreateTime		
        this.pkcreateTime.setVisible(true);		
        this.pkcreateTime.setRequired(false);		
        this.pkcreateTime.setEnabled(false);
        // prmtlastUpdateUser		
        this.prmtlastUpdateUser.setVisible(true);		
        this.prmtlastUpdateUser.setEditable(true);		
        this.prmtlastUpdateUser.setDisplayFormat("$name$");		
        this.prmtlastUpdateUser.setEditFormat("$number$");		
        this.prmtlastUpdateUser.setCommitFormat("$number$");		
        this.prmtlastUpdateUser.setRequired(false);		
        this.prmtlastUpdateUser.setEnabled(false);
        // pklastUpdateTime		
        this.pklastUpdateTime.setVisible(true);		
        this.pklastUpdateTime.setRequired(false);		
        this.pklastUpdateTime.setEnabled(false);
        // prmtFICompany		
        this.prmtFICompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery4AsstAcct");		
        this.prmtFICompany.setVisible(true);		
        this.prmtFICompany.setEditable(true);		
        this.prmtFICompany.setDisplayFormat("$name$");		
        this.prmtFICompany.setEditFormat("$number$");		
        this.prmtFICompany.setCommitFormat("$number$");		
        this.prmtFICompany.setRequired(true);
        		setOrgF7(prmtFICompany,com.kingdee.eas.basedata.org.OrgType.getEnum("Company"));
					
        // prmtcompany		
        this.prmtcompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmtcompany.setVisible(true);		
        this.prmtcompany.setEditable(true);		
        this.prmtcompany.setDisplayFormat("$taxNumber$");		
        this.prmtcompany.setEditFormat("$number$");		
        this.prmtcompany.setCommitFormat("$number$");		
        this.prmtcompany.setRequired(true);
        // txtpropertyName		
        this.txtpropertyName.setVisible(true);		
        this.txtpropertyName.setHorizontalAlignment(2);		
        this.txtpropertyName.setMaxLength(100);		
        this.txtpropertyName.setRequired(true);
        // txtpropertyNumber		
        this.txtpropertyNumber.setVisible(true);		
        this.txtpropertyNumber.setHorizontalAlignment(2);		
        this.txtpropertyNumber.setMaxLength(100);		
        this.txtpropertyNumber.setRequired(true);
        // txtsupplier		
        this.txtsupplier.setVisible(true);		
        this.txtsupplier.setHorizontalAlignment(2);		
        this.txtsupplier.setMaxLength(100);		
        this.txtsupplier.setRequired(true);
        // Status		
        this.Status.setVisible(true);		
        this.Status.addItems(EnumUtils.getEnumList("com.kingdee.eas.fm.fin.BillStatusEnum").toArray());		
        this.Status.setRequired(false);
        // txtcontractNumber		
        this.txtcontractNumber.setVisible(true);		
        this.txtcontractNumber.setHorizontalAlignment(2);		
        this.txtcontractNumber.setMaxLength(100);		
        this.txtcontractNumber.setRequired(true);
        // txtcontractSumCost		
        this.txtcontractSumCost.setVisible(true);		
        this.txtcontractSumCost.setHorizontalAlignment(2);		
        this.txtcontractSumCost.setDataType(1);		
        this.txtcontractSumCost.setSupportedEmpty(true);		
        this.txtcontractSumCost.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtcontractSumCost.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtcontractSumCost.setPrecision(2);		
        this.txtcontractSumCost.setRequired(true);
        // txtcurrency		
        this.txtcurrency.setVisible(true);		
        this.txtcurrency.setHorizontalAlignment(2);		
        this.txtcurrency.setMaxLength(100);		
        this.txtcurrency.setRequired(true);
        // txtbDefaultMoney		
        this.txtbDefaultMoney.setVisible(true);		
        this.txtbDefaultMoney.setHorizontalAlignment(2);		
        this.txtbDefaultMoney.setDataType(1);		
        this.txtbDefaultMoney.setSupportedEmpty(true);		
        this.txtbDefaultMoney.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtbDefaultMoney.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtbDefaultMoney.setPrecision(2);		
        this.txtbDefaultMoney.setRequired(false);
        // txtaDefaultMoney		
        this.txtaDefaultMoney.setVisible(true);		
        this.txtaDefaultMoney.setHorizontalAlignment(2);		
        this.txtaDefaultMoney.setDataType(1);		
        this.txtaDefaultMoney.setSupportedEmpty(true);		
        this.txtaDefaultMoney.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtaDefaultMoney.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtaDefaultMoney.setPrecision(2);		
        this.txtaDefaultMoney.setRequired(false);
        // txttaxRate		
        this.txttaxRate.setVisible(true);		
        this.txttaxRate.setHorizontalAlignment(2);		
        this.txttaxRate.setDataType(1);		
        this.txttaxRate.setSupportedEmpty(true);		
        this.txttaxRate.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txttaxRate.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txttaxRate.setPrecision(2);		
        this.txttaxRate.setRequired(true);
        // txtamountPayable		
        this.txtamountPayable.setVisible(true);		
        this.txtamountPayable.setHorizontalAlignment(2);		
        this.txtamountPayable.setDataType(1);		
        this.txtamountPayable.setSupportedEmpty(true);		
        this.txtamountPayable.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtamountPayable.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtamountPayable.setPrecision(2);		
        this.txtamountPayable.setRequired(true);
        // txtamountContentious		
        this.txtamountContentious.setVisible(true);		
        this.txtamountContentious.setHorizontalAlignment(2);		
        this.txtamountContentious.setDataType(1);		
        this.txtamountContentious.setSupportedEmpty(true);		
        this.txtamountContentious.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtamountContentious.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtamountContentious.setPrecision(2);		
        this.txtamountContentious.setRequired(false);
        // txtamountConfirmation		
        this.txtamountConfirmation.setVisible(true);		
        this.txtamountConfirmation.setHorizontalAlignment(2);		
        this.txtamountConfirmation.setDataType(1);		
        this.txtamountConfirmation.setSupportedEmpty(true);		
        this.txtamountConfirmation.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtamountConfirmation.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtamountConfirmation.setPrecision(2);		
        this.txtamountConfirmation.setRequired(true);
        // pkdueDate		
        this.pkdueDate.setVisible(true);		
        this.pkdueDate.setRequired(true);
        // prmtcostItem		
        this.prmtcostItem.setQueryInfo("com.kingdee.eas.basedata.scm.common.app.ExpenseItemQuery");		
        this.prmtcostItem.setVisible(true);		
        this.prmtcostItem.setEditable(true);		
        this.prmtcostItem.setDisplayFormat("$status$");		
        this.prmtcostItem.setEditFormat("$number$");		
        this.prmtcostItem.setCommitFormat("$number$");		
        this.prmtcostItem.setRequired(true);
        // txtsourceSystem		
        this.txtsourceSystem.setVisible(true);		
        this.txtsourceSystem.setHorizontalAlignment(2);		
        this.txtsourceSystem.setMaxLength(100);		
        this.txtsourceSystem.setRequired(false);		
        this.txtsourceSystem.setEnabled(false);
        // txttotalSum		
        this.txttotalSum.setVisible(true);		
        this.txttotalSum.setHorizontalAlignment(2);		
        this.txttotalSum.setDataType(1);		
        this.txttotalSum.setSupportedEmpty(true);		
        this.txttotalSum.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txttotalSum.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txttotalSum.setPrecision(2);		
        this.txttotalSum.setRequired(false);		
        this.txttotalSum.setEnabled(false);
        // txttotalTax		
        this.txttotalTax.setVisible(true);		
        this.txttotalTax.setHorizontalAlignment(2);		
        this.txttotalTax.setDataType(1);		
        this.txttotalTax.setSupportedEmpty(true);		
        this.txttotalTax.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txttotalTax.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txttotalTax.setPrecision(2);		
        this.txttotalTax.setRequired(false);		
        this.txttotalTax.setEnabled(false);
        // txttotalNoTax		
        this.txttotalNoTax.setVisible(true);		
        this.txttotalNoTax.setHorizontalAlignment(2);		
        this.txttotalNoTax.setDataType(1);		
        this.txttotalNoTax.setSupportedEmpty(true);		
        this.txttotalNoTax.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txttotalNoTax.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txttotalNoTax.setPrecision(2);		
        this.txttotalNoTax.setRequired(false);		
        this.txttotalNoTax.setEnabled(false);
        // txtnoInvoiceSum		
        this.txtnoInvoiceSum.setVisible(true);		
        this.txtnoInvoiceSum.setHorizontalAlignment(2);		
        this.txtnoInvoiceSum.setDataType(1);		
        this.txtnoInvoiceSum.setSupportedEmpty(true);		
        this.txtnoInvoiceSum.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtnoInvoiceSum.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtnoInvoiceSum.setPrecision(2);		
        this.txtnoInvoiceSum.setRequired(false);		
        this.txtnoInvoiceSum.setEnabled(false);
        // txtYesInvoiceSum		
        this.txtYesInvoiceSum.setVisible(true);		
        this.txtYesInvoiceSum.setHorizontalAlignment(2);		
        this.txtYesInvoiceSum.setDataType(1);		
        this.txtYesInvoiceSum.setSupportedEmpty(true);		
        this.txtYesInvoiceSum.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtYesInvoiceSum.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtYesInvoiceSum.setPrecision(2);		
        this.txtYesInvoiceSum.setRequired(false);		
        this.txtYesInvoiceSum.setEnabled(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtcompany,txtpropertyName,txtpropertyNumber,txtsupplier,Status,txtcontractNumber,txtcontractSumCost,txtcurrency,txtbDefaultMoney,txtaDefaultMoney,pklastUpdateTime,prmtlastUpdateUser,pkcreateTime,prmtcreator,prmtauditor,txtdescription,pkbizDate,txtnumber,billStatus,pkauditTime,prmtFICompany,kdtEntry,txttaxRate,txtamountPayable,txtamountContentious,txtamountConfirmation,pkdueDate,prmtcostItem,txtsourceSystem,txttotalSum,txttotalTax,txttotalNoTax,txtnoInvoiceSum,txtYesInvoiceSum}));
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
        contbillStatus.setBounds(new Rectangle(732, 32, 270, 19));
        this.add(contbillStatus, new KDLayout.Constraints(732, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contauditTime.setBounds(new Rectangle(372, 600, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(372, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contnumber.setBounds(new Rectangle(10, 5, 270, 19));
        this.add(contnumber, new KDLayout.Constraints(10, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbizDate.setBounds(new Rectangle(732, 5, 270, 19));
        this.add(contbizDate, new KDLayout.Constraints(732, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contdescription.setBounds(new Rectangle(10, 474, 270, 19));
        this.add(contdescription, new KDLayout.Constraints(10, 474, 270, 19, 0));
        contauditor.setBounds(new Rectangle(372, 578, 270, 19));
        this.add(contauditor, new KDLayout.Constraints(372, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcreator.setBounds(new Rectangle(10, 578, 270, 19));
        this.add(contcreator, new KDLayout.Constraints(10, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcreateTime.setBounds(new Rectangle(10, 600, 270, 19));
        this.add(contcreateTime, new KDLayout.Constraints(10, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlastUpdateUser.setBounds(new Rectangle(733, 578, 270, 19));
        this.add(contlastUpdateUser, new KDLayout.Constraints(733, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contlastUpdateTime.setBounds(new Rectangle(733, 600, 270, 19));
        this.add(contlastUpdateTime, new KDLayout.Constraints(733, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntry.setBounds(new Rectangle(10, 549, 993, 15));
        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.custom.bill.KsfcsBillEntryInfo(),null,false);
        this.add(kdtEntry_detailPanel, new KDLayout.Constraints(10, 549, 993, 15, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contFICompany.setBounds(new Rectangle(371, 474, 270, 19));
        this.add(contFICompany, new KDLayout.Constraints(371, 474, 270, 19, 0));
        contcompany.setBounds(new Rectangle(371, 5, 270, 19));
        this.add(contcompany, new KDLayout.Constraints(371, 5, 270, 19, 0));
        contpropertyName.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contpropertyName, new KDLayout.Constraints(10, 32, 270, 19, 0));
        contpropertyNumber.setBounds(new Rectangle(10, 399, 270, 19));
        this.add(contpropertyNumber, new KDLayout.Constraints(10, 399, 270, 19, 0));
        contsupplier.setBounds(new Rectangle(371, 32, 270, 19));
        this.add(contsupplier, new KDLayout.Constraints(371, 32, 270, 19, 0));
        contStatus.setBounds(new Rectangle(732, 474, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(732, 474, 270, 19, 0));
        contcontractNumber.setBounds(new Rectangle(10, 62, 270, 19));
        this.add(contcontractNumber, new KDLayout.Constraints(10, 62, 270, 19, 0));
        contcontractSumCost.setBounds(new Rectangle(371, 62, 270, 19));
        this.add(contcontractSumCost, new KDLayout.Constraints(371, 62, 270, 19, 0));
        contcurrency.setBounds(new Rectangle(732, 62, 270, 19));
        this.add(contcurrency, new KDLayout.Constraints(732, 62, 270, 19, 0));
        contbDefaultTxt.setBounds(new Rectangle(10, 99, 97, 19));
        this.add(contbDefaultTxt, new KDLayout.Constraints(10, 99, 97, 19, 0));
        contbDefaultMoney.setBounds(new Rectangle(732, 97, 270, 19));
        this.add(contbDefaultMoney, new KDLayout.Constraints(732, 97, 270, 19, 0));
        contaDefaultTxt.setBounds(new Rectangle(10, 181, 104, 19));
        this.add(contaDefaultTxt, new KDLayout.Constraints(10, 181, 104, 19, 0));
        contaDefaultMoney.setBounds(new Rectangle(732, 178, 270, 19));
        this.add(contaDefaultMoney, new KDLayout.Constraints(732, 178, 270, 19, 0));
        contremarks.setBounds(new Rectangle(10, 258, 72, 19));
        this.add(contremarks, new KDLayout.Constraints(10, 258, 72, 19, 0));
        conttaxRate.setBounds(new Rectangle(732, 275, 270, 19));
        this.add(conttaxRate, new KDLayout.Constraints(732, 275, 270, 19, 0));
        contamountPayable.setBounds(new Rectangle(10, 335, 270, 19));
        this.add(contamountPayable, new KDLayout.Constraints(10, 335, 270, 19, 0));
        contamountContentious.setBounds(new Rectangle(371, 335, 270, 19));
        this.add(contamountContentious, new KDLayout.Constraints(371, 335, 270, 19, 0));
        contamountConfirmation.setBounds(new Rectangle(732, 335, 270, 19));
        this.add(contamountConfirmation, new KDLayout.Constraints(732, 335, 270, 19, 0));
        contdueDate.setBounds(new Rectangle(10, 365, 270, 19));
        this.add(contdueDate, new KDLayout.Constraints(10, 365, 270, 19, 0));
        contcostItem.setBounds(new Rectangle(371, 365, 270, 19));
        this.add(contcostItem, new KDLayout.Constraints(371, 365, 270, 19, 0));
        contsourceSystem.setBounds(new Rectangle(732, 365, 270, 19));
        this.add(contsourceSystem, new KDLayout.Constraints(732, 365, 270, 19, 0));
        conttotalSum.setBounds(new Rectangle(371, 399, 270, 19));
        this.add(conttotalSum, new KDLayout.Constraints(371, 399, 270, 19, 0));
        conttotalTax.setBounds(new Rectangle(732, 399, 270, 19));
        this.add(conttotalTax, new KDLayout.Constraints(732, 399, 270, 19, 0));
        conttotalNoTax.setBounds(new Rectangle(10, 434, 270, 19));
        this.add(conttotalNoTax, new KDLayout.Constraints(10, 434, 270, 19, 0));
        contnoInvoiceSum.setBounds(new Rectangle(371, 434, 270, 19));
        this.add(contnoInvoiceSum, new KDLayout.Constraints(371, 434, 270, 19, 0));
        contYesInvoiceSum.setBounds(new Rectangle(732, 434, 270, 19));
        this.add(contYesInvoiceSum, new KDLayout.Constraints(732, 434, 270, 19, 0));
        kDTextArea1.setBounds(new Rectangle(110, 99, 530, 65));
        this.add(kDTextArea1, new KDLayout.Constraints(110, 99, 530, 65, 0));
        kDTextArea2.setBounds(new Rectangle(112, 177, 530, 65));
        this.add(kDTextArea2, new KDLayout.Constraints(112, 177, 530, 65, 0));
        kDTextArea3.setBounds(new Rectangle(113, 255, 530, 65));
        this.add(kDTextArea3, new KDLayout.Constraints(113, 255, 530, 65, 0));
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
        contcompany.setBoundEditor(prmtcompany);
        //contpropertyName
        contpropertyName.setBoundEditor(txtpropertyName);
        //contpropertyNumber
        contpropertyNumber.setBoundEditor(txtpropertyNumber);
        //contsupplier
        contsupplier.setBoundEditor(txtsupplier);
        //contStatus
        contStatus.setBoundEditor(Status);
        //contcontractNumber
        contcontractNumber.setBoundEditor(txtcontractNumber);
        //contcontractSumCost
        contcontractSumCost.setBoundEditor(txtcontractSumCost);
        //contcurrency
        contcurrency.setBoundEditor(txtcurrency);
        //contbDefaultMoney
        contbDefaultMoney.setBoundEditor(txtbDefaultMoney);
        //contaDefaultMoney
        contaDefaultMoney.setBoundEditor(txtaDefaultMoney);
        //conttaxRate
        conttaxRate.setBoundEditor(txttaxRate);
        //contamountPayable
        contamountPayable.setBoundEditor(txtamountPayable);
        //contamountContentious
        contamountContentious.setBoundEditor(txtamountContentious);
        //contamountConfirmation
        contamountConfirmation.setBoundEditor(txtamountConfirmation);
        //contdueDate
        contdueDate.setBoundEditor(pkdueDate);
        //contcostItem
        contcostItem.setBoundEditor(prmtcostItem);
        //contsourceSystem
        contsourceSystem.setBoundEditor(txtsourceSystem);
        //conttotalSum
        conttotalSum.setBoundEditor(txttotalSum);
        //conttotalTax
        conttotalTax.setBoundEditor(txttotalTax);
        //conttotalNoTax
        conttotalNoTax.setBoundEditor(txttotalNoTax);
        //contnoInvoiceSum
        contnoInvoiceSum.setBoundEditor(txtnoInvoiceSum);
        //contYesInvoiceSum
        contYesInvoiceSum.setBoundEditor(txtYesInvoiceSum);

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
		dataBinder.registerBinding("entries", com.kingdee.eas.custom.bill.KsfcsBillEntryInfo.class, this.kdtEntry, "userObject");
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
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtcompany, "data");
		dataBinder.registerBinding("propertyName", String.class, this.txtpropertyName, "text");
		dataBinder.registerBinding("propertyNumber", String.class, this.txtpropertyNumber, "text");
		dataBinder.registerBinding("supplier", String.class, this.txtsupplier, "text");
		dataBinder.registerBinding("Status", com.kingdee.eas.fm.fin.BillStatusEnum.class, this.Status, "selectedItem");
		dataBinder.registerBinding("contractNumber", String.class, this.txtcontractNumber, "text");
		dataBinder.registerBinding("contractSumCost", java.math.BigDecimal.class, this.txtcontractSumCost, "value");
		dataBinder.registerBinding("currency", String.class, this.txtcurrency, "text");
		dataBinder.registerBinding("bDefaultMoney", java.math.BigDecimal.class, this.txtbDefaultMoney, "value");
		dataBinder.registerBinding("aDefaultMoney", java.math.BigDecimal.class, this.txtaDefaultMoney, "value");
		dataBinder.registerBinding("taxRate", java.math.BigDecimal.class, this.txttaxRate, "value");
		dataBinder.registerBinding("amountPayable", java.math.BigDecimal.class, this.txtamountPayable, "value");
		dataBinder.registerBinding("amountContentious", java.math.BigDecimal.class, this.txtamountContentious, "value");
		dataBinder.registerBinding("amountConfirmation", java.math.BigDecimal.class, this.txtamountConfirmation, "value");
		dataBinder.registerBinding("dueDate", java.util.Date.class, this.pkdueDate, "value");
		dataBinder.registerBinding("costItem", com.kingdee.eas.basedata.scm.common.ExpenseItemInfo.class, this.prmtcostItem, "data");
		dataBinder.registerBinding("sourceSystem", String.class, this.txtsourceSystem, "text");
		dataBinder.registerBinding("totalSum", java.math.BigDecimal.class, this.txttotalSum, "value");
		dataBinder.registerBinding("totalTax", java.math.BigDecimal.class, this.txttotalTax, "value");
		dataBinder.registerBinding("totalNoTax", java.math.BigDecimal.class, this.txttotalNoTax, "value");
		dataBinder.registerBinding("noInvoiceSum", java.math.BigDecimal.class, this.txtnoInvoiceSum, "value");
		dataBinder.registerBinding("YesInvoiceSum", java.math.BigDecimal.class, this.txtYesInvoiceSum, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.bill.app.KsfcsBillEditUIHandler";
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
        this.prmtcompany.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.bill.KsfcsBillInfo)ov;
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
		getValidateHelper().registerBindProperty("propertyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("propertyNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractSumCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bDefaultMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aDefaultMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taxRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountPayable", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountContentious", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountConfirmation", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dueDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costItem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sourceSystem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalSum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalTax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalNoTax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("noInvoiceSum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("YesInvoiceSum", ValidateHelper.ON_SAVE);    		
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("company.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("company.id"));
        	sic.add(new SelectorItemInfo("company.number"));
        	sic.add(new SelectorItemInfo("company.name"));
        	sic.add(new SelectorItemInfo("company.taxNumber"));
		}
        sic.add(new SelectorItemInfo("propertyName"));
        sic.add(new SelectorItemInfo("propertyNumber"));
        sic.add(new SelectorItemInfo("supplier"));
        sic.add(new SelectorItemInfo("Status"));
        sic.add(new SelectorItemInfo("contractNumber"));
        sic.add(new SelectorItemInfo("contractSumCost"));
        sic.add(new SelectorItemInfo("currency"));
        sic.add(new SelectorItemInfo("bDefaultMoney"));
        sic.add(new SelectorItemInfo("aDefaultMoney"));
        sic.add(new SelectorItemInfo("taxRate"));
        sic.add(new SelectorItemInfo("amountPayable"));
        sic.add(new SelectorItemInfo("amountContentious"));
        sic.add(new SelectorItemInfo("amountConfirmation"));
        sic.add(new SelectorItemInfo("dueDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costItem.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costItem.id"));
        	sic.add(new SelectorItemInfo("costItem.number"));
        	sic.add(new SelectorItemInfo("costItem.name"));
        	sic.add(new SelectorItemInfo("costItem.status"));
		}
        sic.add(new SelectorItemInfo("sourceSystem"));
        sic.add(new SelectorItemInfo("totalSum"));
        sic.add(new SelectorItemInfo("totalTax"));
        sic.add(new SelectorItemInfo("totalNoTax"));
        sic.add(new SelectorItemInfo("noInvoiceSum"));
        sic.add(new SelectorItemInfo("YesInvoiceSum"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.bill.client", "KsfcsBillEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.bill.client.KsfcsBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.bill.KsfcsBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.bill.KsfcsBillInfo objectValue = new com.kingdee.eas.custom.bill.KsfcsBillInfo();
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")) != null && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")).getBoolean("isBizUnit"))
			objectValue.put("FICompany",com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")));
 
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(billStatus.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据状态"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtnumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkbizDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"业务日期"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtcompany.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"公司"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtpropertyName.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"物业项目"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtpropertyNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"物业项目编码"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtsupplier.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"服务供方"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtcontractNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"合同编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtcontractSumCost.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"合同总金额"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtcurrency.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"币别"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txttaxRate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"税率"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtamountPayable.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"合同本期应付金额"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtamountConfirmation.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"本期确认应付金额"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkdueDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"计划支付日期"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtcostItem.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"费用项目"});
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
		vo.put("Status","audited");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}