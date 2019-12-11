package com.kingdee.eas.Interfacetools.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.*;
import com.kingdee.bos.json.JSONObject;

import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.tools.datatask.DIETemplateCollection;
import com.kingdee.eas.tools.datatask.DIETemplateFactory;
import com.kingdee.eas.tools.datatask.DIETemplateInfo;
import com.kingdee.eas.tools.datatask.IDIETemplate;
import com.kingdee.eas.tools.datatask.IDataTransmission;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.tools.datatask.task.util.TaskRunnerException;
import java.lang.String;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class CommonInterfaceControllerBean extends AbstractCommonInterfaceControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.Interfacetools.app.CommonInterfaceControllerBean");
	/**
	 * 2018-4-12 18:53:53 zl_lixin
	 * @param 	easTemplateNumber 导入模板编码
	 * @param	jsonDatas 业务数据（使用json格式）
	 * @param	isUpdate 是否需要更新数据
	 */
	protected String[][] _onloadData(Context ctx, String easTemplateNumber,String[] jsonDatas, boolean isUpdate) throws BOSException {
		Map hsdataMap = new HashMap();
		Hashtable hsData = new Hashtable();
		String[][] result = new String[1][2];

		for (int i = 0; i < jsonDatas.length; ++i) {
			try {
				JSONObject json = new JSONObject(jsonDatas[i]);
				Iterator it = json.keys();
				while(it.hasNext()){
					DataToken dt = new DataToken();
					String key = it.next().toString();
					dt.data = json.get(key);
					dt.name = key;
					hsdataMap.put(key,dt);
				}
				hsData.put(i,hsdataMap);

				if(easTemplateNumber!=null && !easTemplateNumber.trim().equals("")){
					IDataTransmission trans = getTransObject(ctx,easTemplateNumber);
					trans.setContextParameter(new HashMap());
					trans.getContextParameter().put("DATATASKMODE", new Integer((isUpdate) ? 16 : 8));
					CoreBaseInfo info = trans.transmit(hsData,ctx);
					trans.submit(info, ctx);
					result[0][0] = "0000";
					result[0][1] = "导入数据成功";
				}else{
					result[0][0] = "0002";
					result[0][1] = "导入模板编码不能为空！";
				}
			} catch (Exception e) {
				result[0][0] = "0001";
				result[0][1] = "EAS程序错误，请检查："+e.getMessage();
			}
		}
		return result;
	}

	private IDataTransmission getTransObject(Context ctx, String templateNumber) throws BOSException, TaskRunnerException {
		IDIETemplate iTemplate = DIETemplateFactory.getLocalInstance(ctx);

		DIETemplateCollection coll = iTemplate.getDIETemplateCollection("where number = '" + templateNumber + "'");
		IDataTransmission iObject = null;
		if ((coll != null) && (coll.size() > 0)) {
			DIETemplateInfo info = coll.get(0);
			String impleName = info.getImplementClassName();

			try {
				Class classDefinition = Class.forName(impleName);
				iObject = (IDataTransmission)classDefinition.newInstance();
			} catch (Exception e) {
				throw new TaskRunnerException(4, e);
			}
		}
		return iObject;
	}
}