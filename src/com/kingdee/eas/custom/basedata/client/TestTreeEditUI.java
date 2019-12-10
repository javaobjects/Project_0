/**
 * output package name
 */
package com.kingdee.eas.custom.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * Java类描述：
 * <p>Title: </p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * @author DZ_yanb
 * @date 2019-12-10    
 * @version 1.0
 */
public class TestTreeEditUI extends AbstractTestTreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TestTreeEditUI.class);
    
    /**
     * output class constructor
     */
    public TestTreeEditUI() throws Exception
    {
        super();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.basedata.TestTreeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.basedata.TestTreeInfo objectValue = new com.kingdee.eas.custom.basedata.TestTreeInfo();
		
        return objectValue;
    }
  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@以下代码为修改原有方法或新增方法#############################################  
    private static String TABLE_NAME ="T_BAS_TestTREE";
     /**
      * output actionRemove_actionPerformed
      */
     public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
     	//判断当前选中的节点下是否有数据
     	if(this.editData!=null){
     		String sql = "select * from "+TABLE_NAME+" where FTreeID='"+this.editData.getId()+"'";
     		com.kingdee.jdbc.rowset.IRowSet rs = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
     		if(rs.next()){
     			com.kingdee.eas.util.client.MsgBox.showInfo("该节点下有数据，请先删除数据后再删除节点！");
     			return;
     		}
     	}

     	super.actionRemove_actionPerformed(e);
     }
}