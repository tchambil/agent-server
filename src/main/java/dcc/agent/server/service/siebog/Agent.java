package dcc.agent.server.service.siebog;

import java.io.Serializable;

/**
 * Created by teo on 27/04/15.
 */
public interface Agent extends Serializable{
   public void init(AID aid);
   public void stop();
   public void handleMessage(ACLMessage msg);
   public String ping();

}
