package dcc.agent.server.service.delegate;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.communication.ACLMessage;
import dcc.agent.server.service.communication.AgentSender;
import dcc.agent.server.service.communication.Performative;
import dcc.agent.server.service.script.runtime.ScriptState;
import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * Created by teo on 28/05/15.
 */
public class AgentDelegate {
    protected static Logger log = Logger.getLogger(AgentDelegate.class);
    public AgentServer agentServer;

    static public void doNautiLOD(ScriptState scriptState, AgentInstance agentInstanceS, AgentInstance agentInstanceR, String newcommand) throws AgentServerException, JSONException {
        log.info("Initialize the agent delegate for send agent message");
        ACLMessage aclMessage = new ACLMessage();
        aclMessage.setContent("web w; return w.nautilod('" + newcommand + "').xml;");
        aclMessage.setPerformative(Performative.REQUEST);
        aclMessage.setReceivers(agentInstanceR.aid);
        aclMessage.setSender(agentInstanceS.aid);
        aclMessage.agentServer = scriptState.agentServer;
        aclMessage.setDelegate(true);
        aclMessage.setStatus("new");
        AgentSender.send(scriptState.agentServer, aclMessage, true);
    }


}

