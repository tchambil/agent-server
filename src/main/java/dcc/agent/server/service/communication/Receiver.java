package dcc.agent.server.service.communication;

import dcc.agent.server.service.agentserver.AgentInstance;
import org.apache.log4j.Logger;

/**
 * Created by teo on 29/06/15.
 */
public class Receiver extends AgentInstance {
    private static final long serialVersionUID = -5648061637952026195L;
    private static final Logger log= Logger.getLogger(Receiver.class);
    protected AgentInstance myAid;
    private int primeLimit;
    protected void onMessage(ACLMessage msg){
        ACLMessage reply =msg.makeReply(Performative.INFORM);
        reply.sender=aid;
        reply.content=msg.content;
        
    }

}
