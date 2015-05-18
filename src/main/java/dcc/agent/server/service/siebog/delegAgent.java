package dcc.agent.server.service.siebog;

/**
 * Created by teo on 27/04/15.
 */
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class delegAgent implements Agent {
    private static final long serialVersionUID = 1L;
    public static final long ACCESS_TIMEOUT = 5;
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected AID myAid;
    private AgentManager agm;
    private MessageManager msm;


    //@Override
    public void init(AID aid, AgentInitArgs args) {
        myAid = aid;
        onInit(args);
    }

    protected void onInit(AgentInitArgs args) {
    }

    @Override
    public void handleMessage(ACLMessage msg) {

        if (msg instanceof HeartbeatMessage) {
            boolean repeat = onHeartbeat(msg.content);
            if (repeat)
                ; // executor().signalHeartbeat(hbHandle);
            else
                ; // executor().cancelHeartbeat(hbHandle);
        } else {
            if (filter(msg)) {
                try {
                    onMessage(msg);
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "Error while delivering message " + msg, ex);
                }
            }
        }
    }

    protected abstract void onMessage(ACLMessage msg);

    protected boolean onHeartbeat(String content) {
        return false;
    }

    protected void onTerminate() {
    }

    @Override

    public void stop() {
        try {
            onTerminate();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error in onTerminate.", ex);
        }
    }

    protected ACLMessage receiveNoWait() {
        return null; // queue.poll(); // TODO : Implement receiveNoWait.
    }

    protected ACLMessage receiveWait(long timeout) {
        if (timeout < 0)
            throw new IllegalArgumentException("The timeout value cannot be negative.");
        ACLMessage msg = null;

        return msg;
    }

    @Override
    public int hashCode() {
        return myAid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return myAid.equals(((delegAgent) obj).myAid);
    }

    protected boolean filter(ACLMessage msg) {
        return true;
    }

    protected void registerHeartbeat(String content) {

    }

    protected void registerHeartbeat() {
        registerHeartbeat("");
    }

    public AID getAid() {
        return myAid;
    }

    protected String getNodeName() {
        return System.getProperty("jboss.node.name");
    }

    @Override
    public String ping() {
        return getNodeName();
    }

    protected AgentManager agm() {
        if (agm == null)
            agm = ObjectFactory.getAgentManager();
        return agm;
    }

    protected MessageManager msm() {
        if (msm == null)
            msm = ObjectFactory.getMessageManager();
        return msm;
    }
}
