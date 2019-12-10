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
* <p>Company: ����</p>  
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
     * <p>Company: ����</p>  
     * @author yanb
     * @date 2019-12-4  
     * @param table
     * @return  
     * @see com.kingdee.eas.st.common.client.STBillBaseEditUI#createNewDetailData(com.kingdee.bos.ctrl.kdf.table.KDTable)  
     */
    protected IObjectValue createNewDetailData(KDTable table) {
		//�˴�Ϊnew һ�����ݷ�¼��info����
    	return new TestBillEntryInfo();
	}

    /* (non-Javadoc)  
     * <p>Title: getMainOrgUnit</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: ����</p>  
     * @author yanb
     * @date 2019-12-4  
     * @return  
     * @see com.kingdee.eas.st.common.client.STBillBaseEditUI#getMainOrgUnit()  
     */
    public KDBizPromptBox getMainOrgUnit(){
    	//��ҵ����֯��F7�ؼ�
    	return prmtstorageOrgUnit;
    }

    /* (non-Javadoc)  
     * <p>Title: getMainBizOrgType</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: ����</p>  
     * @author yanb
     * @date 2019-12-4  
     * @return  
     * @see com.kingdee.eas.custom.bill.client.AbstractTestBillEditUI#getMainBizOrgType()  
     */
    protected OrgType getMainBizOrgType(){
    	//��ҵ����֯����֯����:����������������
    	
//    	OrgType.Admin     ������֯
//    	OrgType.Company   ������֯
//    	OrgType.ControlUnit ����Ԫ
//    	OrgType.CostCenter �ɱ�����
//    	OrgType.HRO       HR ��֯
//    	OrgType.NONE      ����֯����
//    	OrgType.ProfitCenter ��������
//    	OrgType.Purchase    �ɹ���֯
//    	OrgType.Quality   �ʼ���֯
//    	OrgType.Sale      ������֯
//    	OrgType.Storage   �����֯
//    	OrgType.Transport ������֯
//    	OrgType.UnionDebt �ϲ���Χ
    	
    	return OrgType.Storage;
    }

    //������֯����
    /* (non-Javadoc)  
     * <p>Title: setOrgF7</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: ����</p>  
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