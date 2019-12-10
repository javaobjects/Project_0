/**
 * output package name
 */
package com.kingdee.eas.st.common;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class STBillException extends EASBizException
{
    private static final String MAINCODE = "01";

    public static final NumericExceptionSubItem EXC_METAS_LOADERR = new NumericExceptionSubItem("100", "EXC_METAS_LOADERR");
    public static final NumericExceptionSubItem EXC_BILL_NOT_SUBMIT = new NumericExceptionSubItem("101", "EXC_BILL_NOT_SUBMIT");
    public static final NumericExceptionSubItem EXC_BILL_NOT_AUDIT = new NumericExceptionSubItem("102", "EXC_BILL_NOT_AUDIT");
    public static final NumericExceptionSubItem EXE_BILL_NOT_DELETE = new NumericExceptionSubItem("103", "EXE_BILL_NOT_DELETE");
    public static final NumericExceptionSubItem EXC_BILL_DELETE_NOT_SUBMIT = new NumericExceptionSubItem("000", "EXC_BILL_DELETE_NOT_SUBMIT");
    public static final NumericExceptionSubItem EXC_BILL_AUDIT_NOT_SUBMIT = new NumericExceptionSubItem("001", "EXC_BILL_AUDIT_NOT_SUBMIT");
    public static final NumericExceptionSubItem EXC_BILL_CLOSE_NOT_SUBMIT = new NumericExceptionSubItem("002", "EXC_BILL_CLOSE_NOT_SUBMIT");
    public static final NumericExceptionSubItem EXC_BILL_SUBMIT_NOT_SAVE = new NumericExceptionSubItem("003", "EXC_BILL_SUBMIT_NOT_SAVE");
    public static final NumericExceptionSubItem EXC_BILL_DELTET_NOT_SAVE = new NumericExceptionSubItem("004", "EXC_BILL_DELTET_NOT_SAVE");
    public static final NumericExceptionSubItem EXC_BILL_AUDIT_NOT_SAVE = new NumericExceptionSubItem("005", "EXC_BILL_AUDIT_NOT_SAVE");
    public static final NumericExceptionSubItem EXC_BILL_CLOSE_NOT_SAVE = new NumericExceptionSubItem("006", "EXC_BILL_CLOSE_NOT_SAVE");
    public static final NumericExceptionSubItem EXC_BILL_DELETE_NOT_EDIT = new NumericExceptionSubItem("007", "EXC_BILL_DELETE_NOT_EDIT");
    public static final NumericExceptionSubItem EXC_BILL_AUDIT_NOT_EDIT = new NumericExceptionSubItem("008", "EXC_BILL_AUDIT_NOT_EDIT");
    public static final NumericExceptionSubItem EXC_BILL_CLOSE_NOT_EDIT = new NumericExceptionSubItem("009", "EXC_BILL_CLOSE_NOT_EDIT");
    public static final NumericExceptionSubItem EXC_BILL_NOT_MULT = new NumericExceptionSubItem("010", "EXC_BILL_NOT_MULT");
    public static final NumericExceptionSubItem NULL_LINEINFO = new NumericExceptionSubItem("011", "NULL_LINEINFO");
    public static final NumericExceptionSubItem UNAUDIT_MUSTSELECT_ONERECORD = new NumericExceptionSubItem("012", "UNAUDIT_MUSTSELECT_ONERECORD");
    public static final NumericExceptionSubItem CHECK_UNAUDIT = new NumericExceptionSubItem("013", "CHECK_UNAUDIT");
    public static final NumericExceptionSubItem HASDESTBILL_CANNOTUNAUDIT = new NumericExceptionSubItem("014", "HASDESTBILL_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem NOTEXISTAUDITEDBILL = new NumericExceptionSubItem("015", "NOTEXISTAUDITEDBILL");
    public static final NumericExceptionSubItem UNAUDITED_RESULT = new NumericExceptionSubItem("016", "UNAUDITED_RESULT");
    public static final NumericExceptionSubItem AUDIT_MUSTSELECT_ONERECORD = new NumericExceptionSubItem("017", "AUDIT_MUSTSELECT_ONERECORD");
    public static final NumericExceptionSubItem NOTEXISTUNAUDITEDBILL = new NumericExceptionSubItem("018", "NOTEXISTUNAUDITEDBILL");
    public static final NumericExceptionSubItem ABROGATE_CONDITION = new NumericExceptionSubItem("019", "ABROGATE_CONDITION");
    public static final NumericExceptionSubItem BILL_IN_WORKFLOW = new NumericExceptionSubItem("020", "BILL_IN_WORKFLOW");
    public static final NumericExceptionSubItem BILLMUSTSUBMIT = new NumericExceptionSubItem("017", "BILLMUSTSUBMIT");
    public static final NumericExceptionSubItem CHECK_ALTER = new NumericExceptionSubItem("021", "CHECK_ALTER");
    public static final NumericExceptionSubItem EXC_BILL_ALTER_NOT_EDIT = new NumericExceptionSubItem("022", "EXC_BILL_ALTER_NOT_EDIT");
    public static final NumericExceptionSubItem CHECK_PRODUCEDETAIL = new NumericExceptionSubItem("023", "CHECK_PRODUCEDETAIL");
    public static final NumericExceptionSubItem CHECK_INPUT_ERROR = new NumericExceptionSubItem("024", "CHECK_INPUT_ERROR");
    public static final NumericExceptionSubItem FLOORNUMBERMINISNOTONEFORSUBMIT = new NumericExceptionSubItem("025", "FloorNumberMinIsNotOneForSubmit");
    public static final NumericExceptionSubItem CHECK_ISEQUALLOCALTION_SUBMIT = new NumericExceptionSubItem("026", "CHECK_ISEQUALLOCALTION_SUBMIT");
    public static final NumericExceptionSubItem FLOORNUMBERNEEDSEQ = new NumericExceptionSubItem("027", "FLOORNUMBERNEEDSEQ");
    public static final NumericExceptionSubItem CHECK_NEWROWISEQUAL = new NumericExceptionSubItem("028", "CHECK_NEWROWISEQUAL");
    public static final NumericExceptionSubItem CHECK_ROWNUMBERBUTTRESSNUMBERISNOTNULL = new NumericExceptionSubItem("029", "CHECK_RowNumberButtressNumberIsNotNull");
    public static final NumericExceptionSubItem EXC_BILL_MAINORG_ISNULL = new NumericExceptionSubItem("030", "EXC_BILL_MAINORG_ISNULL");
    public static final NumericExceptionSubItem MULTMEASURE_MATERIAL_NOT_EXIST = new NumericExceptionSubItem("031", "MultMeasure_Material_Not_Exist");
    public static final NumericExceptionSubItem UNAUDIT_HAS_BUSINESS = new NumericExceptionSubItem("032", "UNAUDIT_HAS_BUSINESS");
    public static final NumericExceptionSubItem CHECK_PRODUCEDETAIL_DIFFERENT = new NumericExceptionSubItem("033", "CHECK_PRODUCEDETAIL_DIFFERENT");
    public static final NumericExceptionSubItem EXC_NO_PERIOD = new NumericExceptionSubItem("034", "EXC_NO_PERIOD");
    public static final NumericExceptionSubItem SELECT_REMOVELINE = new NumericExceptionSubItem("035", "SELECT_REMOVELINE");
    public static final NumericExceptionSubItem CHECK_NEW_ROWNUMBERBUTTRESSNUMBERISNOTNULL = new NumericExceptionSubItem("036", "CHECK_NEW_RowNumberButtressNumberIsNotNull");
    public static final NumericExceptionSubItem EXIST_SAME_FLOOR = new NumericExceptionSubItem("037", "EXIST_SAME_FLOOR");
    public static final NumericExceptionSubItem EXC_NO_COMPANY = new NumericExceptionSubItem("038", "EXC_NO_COMPANY");
    public static final NumericExceptionSubItem EXIST_DOWN_BILL = new NumericExceptionSubItem("039", "EXIST_DOWN_BILL");
    public static final NumericExceptionSubItem WEIGH_CHECK_DELETE = new NumericExceptionSubItem("035", "WEIGH_CHECK_DELETE");
    public static final NumericExceptionSubItem STORAGE_NOTEXIST_COMPANY = new NumericExceptionSubItem("040", "Storage_NotExist_Company");
    public static final NumericExceptionSubItem PERIOD_NOTEXIST_STARTPERIOD = new NumericExceptionSubItem("041", "Period_NotExist_StartPeriod");
    public static final NumericExceptionSubItem PERIOD_BIZDATE_BEFORE_START = new NumericExceptionSubItem("042", "Period_BizDate_Before_Start");
    public static final NumericExceptionSubItem PERIOD_HASSTART_MUST_UNSTART = new NumericExceptionSubItem("043", "Period_HasStart_Must_UnStart");
    public static final NumericExceptionSubItem PERIOD_BIZDATE_MUST_AFTER_START = new NumericExceptionSubItem("044", "Period_BizDate_Must_After_Start");
    public static final NumericExceptionSubItem STORAGE_NOTEXIST = new NumericExceptionSubItem("045", "Storage_NotExist");
    public static final NumericExceptionSubItem STORE_EARLY_THAN_DETAIL = new NumericExceptionSubItem("046", "STORE_EARLY_THAN_DETAIL");
    public static final NumericExceptionSubItem PERIOD_HAS_NOT_START = new NumericExceptionSubItem("047", "Period_Has_Not_Start");
    public static final NumericExceptionSubItem EXC_NO_STORAGE = new NumericExceptionSubItem("048", "EXC_NO_STORAGE");
    public static final NumericExceptionSubItem NULL_ENTRY = new NumericExceptionSubItem("049", "NULL_ENTRY");
    public static final NumericExceptionSubItem ENTRY_DUPLICATE = new NumericExceptionSubItem("050", "ENTRY_DUPLICATE");
    public static final NumericExceptionSubItem EXC_MUST_SELECTENTRY = new NumericExceptionSubItem("051", "EXC_MUST_SELECTENTRY");
    public static final NumericExceptionSubItem UNAUDIT_HAS_VERSION = new NumericExceptionSubItem("052", "UnAudit_Has_Version");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public STBillException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public STBillException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public STBillException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public STBillException(NumericExceptionSubItem info)
    {
        this(info, null, null);
    }

    /**
     * getMainCode function
     */
    public String getMainCode()
    {
        return MAINCODE;
    }
}