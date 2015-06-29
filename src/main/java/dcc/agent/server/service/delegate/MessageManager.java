package dcc.agent.server.service.delegate;

import dcc.agent.server.service.communication.ACLMessage;

/**
 * Created by teo on 28/06/15.
 */
public interface MessageManager {
    void post (ACLMessage aclMessage);
    void post (ACLMessage aclMessage, long delayMillesec);
    String ping();
}
