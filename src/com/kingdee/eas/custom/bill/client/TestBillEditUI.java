/**
 * output package name
 */
package com.kingdee.eas.custom.bill.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.custom.bill.TestBillEntryInfo;
 
/**  
* <p>Title: TestBillEditUI</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2017</p>  
* <p>Company: 钉琢</p>  
* @author yanb
* @date 2019-12-4  
* @version 1.0  
*/  
public class TestBillEditUI extends AbstractTestBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TestBillEditUI.class);
    
    /**
     * output class constructor
     */
    public TestBillEditUI() throws Exception
    {
        super();
    }
   
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.bill.TestBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.bill.TestBillInfo objectValue = new com.kingdee.eas.custom.bill.TestBillInfo();
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Storage")) != null && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Storage")).getBoolean("isBizUnit"))
			objectValue.put("storageOrgUnit",com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Storage")));
 
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
    

    /* (non-Javadoc)  
     * <p>Title: createNewDetailData</p>  
     * <p>
     * Description: 
     * 
     * </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: 钉琢</p>  
     * @author yanb
     * @date 2019-12-4  
     * @param table
     * @return  
     * @see com.kingdee.eas.st.common.client.STBillBaseEditUI#createNewDetailData(com.kingdee.bos.ctrl.kdf.table.KDTable)  
     */
    protected IObjectValue createNewDetailData(KDTable table) {
		//此处为new 一个单据分录的info对象
    	return new TestBillEntryInfo();
	}

    /* (non-Javadoc)  
     * <p>Title: getMainOrgUnit</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: 钉琢</p>  
     * @author yanb
     * @date 2019-12-4  
     * @return  
     * @see com.kingdee.eas.st.common.client.STBillBaseEditUI#getMainOrgUnit()  
     */
    public KDBizPromptBox getMainOrgUnit(){
    	//主业务组织的F7控件
    	return prmtstorageOrgUnit;
    }

    /* (non-Javadoc)  
     * <p>Title: getMainBizOrgType</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: 钉琢</p>  
     * @author yanb
     * @date 2019-12-4  
     * @return  
     * @see com.kingdee.eas.custom.bill.client.AbstractTestBillEditUI#getMainBizOrgType()  
     */
    protected OrgType getMainBizOrgType(){
    	//主业务组织的组织类型:请根据需求自行添加
    	
//    	OrgType.Admin     行政组织
//    	OrgType.Company   财务组织
//    	OrgType.ControlUnit 管理单元
//    	OrgType.CostCenter 成本中心
//    	OrgType.HRO       HR 组织
//    	OrgType.NONE      无组织属性
//    	OrgType.ProfitCenter 利润中心
//    	OrgType.Purchase    采购组织
//    	OrgType.Quality   质检组织
//    	OrgType.Sale      销售组织
//    	OrgType.Storage   库存组织
//    	OrgType.Transport 发运组织
//    	OrgType.UnionDebt 合并范围
    	
    	return OrgType.Storage;
    }

    //忽略组织过滤
    /* (non-Javadoc)  
     * <p>Title: setOrgF7</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: 钉琢</p>  
     * @author yanb
     * @date 2019-12-4  
     * @param f7
     * @param orgType
     * @throws Exception  
     * @see com.kingdee.eas.custom.bill.client.AbstractTestBillEditUI#setOrgF7(com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox, com.kingdee.eas.basedata.org.OrgType)  
     */
    protected void setOrgF7(KDBizPromptBox f7,OrgType orgType) throws Exception{
    	com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
    	f7.setFilterInfoProducer(oufip);
    }

}