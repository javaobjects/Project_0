package com.kingdee.eas.st.common.template;

import java.util.Set;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.framework.agent.IObjectCollectionAgent;
import com.kingdee.bos.framework.agent.ObjectCollectionAgentImpl;
import com.kingdee.bos.framework.agent.SerializableObjectCollectionAgent;

public class STBillTemplateEntryCollectionAgent extends
		STBillTemplateEntryCollection implements IObjectCollectionAgent {
	private final IObjectCollectionAgent agent;

	public STBillTemplateEntryCollectionAgent() {
		super();
		this.agent = new ObjectCollectionAgentImpl();
	}

	public void addOV(IObjectPK pk) {
		this.agent.addOV(pk);
	}

	public void deleteOV(IObjectPK pk) {
		this.agent.deleteOV(pk);
	}

	public Set getDeletedOVSet() {
		return this.agent.getDeletedOVSet();
	}

	public IObjectCollection getTargetOV() {
		return this;
	}

	public boolean isDeletedOV(IObjectPK pk) {
		return this.agent.isDeletedOV(pk);
	}

	public Object writeReplace() {
		if (agent.getDeletedOVSet().size() > 0) {
			return new SerializableObjectCollectionAgent(this, agent);
		} else {
			return new SerializableObjectCollectionAgent(this, null);
		}
	}

}