package com.kingdee.eas.st.common.template;

import java.util.Enumeration;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.framework.agent.AgentManager;
import com.kingdee.bos.framework.agent.AgentState;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.bos.framework.agent.AgentContainerFactory;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.util.BOSObjectType;

public class STBillTemplateEntryAgent extends STBillTemplateEntryInfo implements
		IObjectValueAgent {
	public static final BOSObjectType bosType = new BOSObjectType("E424204A");
	private final AgentManager manager;

	private STBillTemplateEntryAgent() {
		manager = new AgentManager();
	}

	public static STBillTemplateEntryAgent copyOvToAgent(IObjectValue vo) {
		STBillTemplateEntryAgent agent = new STBillTemplateEntryAgent();
		return (STBillTemplateEntryAgent) agent.manager
				.copyOvToAgent(agent, vo);
	}

	public static STBillTemplateEntryAgent copyOvAsNewAgent(IObjectValue vo) {
		STBillTemplateEntryAgent agent = new STBillTemplateEntryAgent();
		return (STBillTemplateEntryAgent) agent.manager.copyOvToAgentAsNew(
				agent, vo);
	}

	public static STBillTemplateEntryAgent find(IObjectPK id)
			throws BOSException {
		STBillTemplateEntryAgent agent = new STBillTemplateEntryAgent();
		return (STBillTemplateEntryAgent) agent.manager.find(agent, id);
	}

	public static STBillTemplateEntryAgent create() throws BOSException {
		return (STBillTemplateEntryAgent) AgentManager
				.create(new STBillTemplateEntryAgent());
	}

	public void agentRemove() throws BOSException {
		AgentManager.remove(this);
	}

	public IObjectPK agentSave() throws BOSException {
		return AgentManager.save(this);
	}

	public AgentState getAgentState() {
		return manager.getAgentState();
	}

	public void setAgentState(AgentState state) {
		manager.setAgentState(state);
	}

	public void recursiveSetAgentState(AgentState state) {
		recursiveSetAgentState(state, new java.util.HashSet());
	}

	public void recursiveSetAgentState(AgentState state,
			java.util.Set handledSet) {
		if (handledSet == null) {
			throw new IllegalArgumentException(
					"The second argument should not be null.");
		}
		setAgentState(state);
		handledSet.add(new Integer(System.identityHashCode(this)));

		Enumeration enumer = this.keys();
		Object obj = null;
		AbstractObjectCollection collection = null;
		while (enumer.hasMoreElements()) {
			obj = this.get((String) enumer.nextElement());
			if (obj instanceof IObjectValueAgent) {
				if (!handledSet.contains(new Integer(System
						.identityHashCode(obj)))) {
					((IObjectValueAgent) obj).recursiveSetAgentState(state,
							handledSet);
				}
			} else if (obj instanceof AbstractObjectCollection) {
				collection = (AbstractObjectCollection) obj;
				IObjectValue vo = null;
				for (int i = 0, j = collection.size(); i < j; i++) {
					vo = collection.getObject(i);
					if (vo instanceof IObjectValueAgent) {
						if (!handledSet.contains(new Integer(System
								.identityHashCode(vo)))) {
							((IObjectValueAgent) vo).recursiveSetAgentState(
									state, handledSet);
						}
					}
				}
			}
		}
	}

	public IObjectValue getTarget() {
		return this;
	}

	public IObjectValue getInfoInstance() {
		return manager.getSourceInfo();
	}

}
