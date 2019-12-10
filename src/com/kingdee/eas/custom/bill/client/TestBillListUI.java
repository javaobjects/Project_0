/**
 * output package name
 */
package com.kingdee.eas.custom.bill.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.framework.*;

 
/**  
* <p>Title: TestBillListUI</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2017</p>  
* <p>Company: ¶¤×Á</p>  
* @author yanb
* @date 2019-12-4  
* @version 1.0  
*/  
public class TestBillListUI extends AbstractTestBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TestBillListUI.class);
    
    /**
     * output class constructor
     */
    public TestBillListUI() throws Exception
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
		
        return objectValue;
    }
    
    /* (non-Javadoc)  
     * <p>Title: getMainBizOrgType</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: ¶¤×Á</p>  
     * @author yanb
     * @date 2019-12-4  
     * @return  
     * @see com.kingdee.eas.custom.bill.client.AbstractTestBillListUI#getMainBizOrgType()  
     */
    protected OrgType getMainBizOrgType() {
		return OrgType.Storage;
	}
    
    /* (non-Javadoc)  
     * <p>Title: getPropertyOfBizOrg</p>  
     * <p>Description: </p>  
     * <p>Copyright: Copyright (c) 2017</p>  
     * <p>Company: ¶¤×Á</p>  
     * @author yanb
     * @date 2019-12-4  
     * @param orgType
     * @return  
     * @see com.kingdee.eas.st.common.client.STBillBaseListUI#getPropertyOfBizOrg(com.kingdee.eas.basedata.org.OrgType)  
     */
    protected String getPropertyOfBizOrg(OrgType orgType){
    	return "storageOrgUnit.id";
    }

}