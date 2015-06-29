package dcc.agent.server.service.delegate;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.communication.ACLMessage;

/**
 * Created by teo on 28/06/15.
 */
public class MessageListener {
 public AgentServer agentServer;
    public void onMessage(ACLMessage aclMessage){
        processMessage(aclMessage);
    }

    private void processMessage(ACLMessage aclMessage) {

       AgentInstance agent= agentServer.getAgentInstance( aclMessage.receivers,  aclMessage.sender);
        deliverMessage(agent, aclMessage);

    }

    private void deliverMessage(AgentInstance agent, ACLMessage aclMessage) {

    }

}
