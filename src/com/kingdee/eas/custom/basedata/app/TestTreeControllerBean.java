package com.kingdee.eas.custom.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
/**
 * Java��������
 * <p>Title: </p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * @author DZ_yanb
 * @date 2019-12-10    
 * @version 1.0
 */
public class TestTreeControllerBean extends AbstractTestTreeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.basedata.app.TestTreeControllerBean");
    
	//com.kingdee.eas.custom.basedata.Test    ������������

    protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	//���ӱ�¼���У�飬���Ƿ��ظ�
    	com.kingdee.eas.custom.basedata.TestTreeInfo info = (com.kingdee.eas.custom.basedata.TestTreeInfo)model;
    	checkNumberBlank(ctx,info);
    	checkNameBlank(ctx,info);
    	checkNameDup(ctx,info);
    	return super._submit(ctx, model);
    }
}