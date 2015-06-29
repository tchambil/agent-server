package dcc.agent.server.service.communication;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.communication.ACLMessage;
import dcc.agent.server.service.communication.Performative;
import dcc.agent.server.service.delegate.AgentDelegate;

/**
 * Created by teo on 29/06/15.
 */
public class Sender extends AgentDelegate {
    private String content;

    public Sender(AgentServer agentServer) {
        super(agentServer);
    }

    protected void onMessage(ACLMessage msg){
        if (msg.performative == Performative.REQUEST) {
           sendMsg();
        }

    }
    private void sendMsg() {
        ACLMessage msg = new ACLMessage(Performative.REQUEST);
        msg.sender = aid;
        msg.receivers=receivers;
        msg.content = content;
        msg.replyWith = System.currentTimeMillis() + "";

    }
}
