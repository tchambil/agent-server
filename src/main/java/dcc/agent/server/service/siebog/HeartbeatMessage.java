package dcc.agent.server.service.siebog;

/**
 * Created by teo on 27/04/15.
 */

    public class HeartbeatMessage extends ACLMessage {
        private static final long serialVersionUID = 1L;
        public long handle;

        public HeartbeatMessage(AID aid, long handle) {
            super(Performative.REQUEST);
            receivers.add(aid);
            this.handle = handle;
        }
    }

