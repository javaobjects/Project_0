package com.kingdee.eas.st.common.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;

public class STBaseDataListUIHandler extends AbstractSTBaseDataListUIHandler {
	public void handleActionAudit(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request, response, context);
	}

	protected void _handleActionAudit(RequestContext request,
			ResponseContext response, Context context) throws Exception {
	}

	public void handleActionUnAudit(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request, response, context);
	}

	protected void _handleActionUnAudit(RequestContext request,
			ResponseContext response, Context context) throws Exception {
	}
}
