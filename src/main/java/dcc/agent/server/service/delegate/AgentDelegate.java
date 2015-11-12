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

import java.util.Collection;

/**
 * Created by teo on 28/05/15.
 */
public class AgentDelegate {
    protected static Logger log = Logger.getLogger(AgentDelegate.class);
    public AgentServer agentServer;

    static public void doNautiLOD(ScriptState scriptState, AgentInstance agentInstanceS, AgentInstance agentInstanceR, String newcommand, String comment) throws AgentServerException, JSONException {
        log.info("Initialize the agent delegate for send agent message");
        ACLMessage aclMessage = new ACLMessage();
        aclMessage.setContent("nautilod n; return n.get('" + newcommand + "').xml;");
        aclMessage.setPerformative(Performative.REQUEST);
        aclMessage.setReceivers(agentInstanceR.aid);
        aclMessage.setSender(agentInstanceS.aid);
        aclMessage.setReplyTo(scriptState.message.getReplyTo());
        aclMessage.agentServer = scriptState.agentServer;
        aclMessage.setOntology(comment);
        aclMessage.setDelegate(true);
        aclMessage.setStatus("new");
        AgentSender.send(scriptState.agentServer, aclMessage, true);
    }
    static public void doNautiLOD(ScriptState scriptState, Collection<String> r) {
        log.info("Initialize the agent delegate for send agent message");
        ACLMessage aclMessage = new ACLMessage();
        aclMessage.setContent("result"+r);
//        System.out.println(r);
        aclMessage.setPerformative(Performative.INFORM);
        aclMessage.setReceivers(scriptState.message.getReplyTo());
        aclMessage.setSender(scriptState.agentInstance.aid);
        aclMessage.agentServer = scriptState.agentServer;
        aclMessage.setInReplyTo(scriptState.message.getContent());
        aclMessage.setReplyBy(scriptState.agentInstance.aid);
        aclMessage.setDelegate(true);
        aclMessage.setStatus("new");
        try {
            AgentSender.send(scriptState.agentServer, aclMessage, true);
        } catch (AgentServerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

