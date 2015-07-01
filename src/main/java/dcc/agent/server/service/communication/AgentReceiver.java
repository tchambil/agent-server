package dcc.agent.server.service.communication;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.util.NameValue;
import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * Created by teo on 29/06/15.
 */
public class AgentReceiver {
    private static final long serialVersionUID = -5648061637952026195L;
    private static final Logger log = Logger.getLogger(AgentReceiver.class);

    public static Boolean onMessage(AgentServer agentServer) throws JSONException, AgentServerException {
        Boolean send = false;
        ACLMessage message = receive(agentServer);
        if (message != null) {
            ACLMessage reply = message.createReply();
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if ((content != null) && (content.indexOf("ping") != -1)) {
                    log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());

                    reply.setPerformative(Performative.INFORM);
                    reply.setContent("pong");
                } else {
                    log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                    reply.setPerformative(Performative.REFUSE);
                    reply.setContent("( UnexpectedContent (" + content + "))");
                }
            } else {
                reply.setPerformative(Performative.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
            }
            send = AgentSender.send(agentServer, reply);
        }
        return send;
    }

    public static Boolean onMessage(AgentServer agentServer, String messageId) throws JSONException, AgentServerException {
        Boolean send = false;
        if (messageId != null) {
            ACLMessage message = receive(agentServer, messageId);
            if (message != null) {
                ACLMessage reply = message.createReply();
                if (message.getPerformative() == Performative.REQUEST) {
                    String content = message.getContent();
                    if ((content != null) && (content.indexOf("ping") != -1)) {
                        log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());

                        reply.setPerformative(Performative.INFORM);
                        reply.setContent("pong");
                    } else {
                        log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                        reply.setPerformative(Performative.REFUSE);
                        reply.setContent("( UnexpectedContent (" + content + "))");
                    }
                } else {
                    reply.setPerformative(Performative.NOT_UNDERSTOOD);
                    reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
                }
                send = AgentSender.send(agentServer, reply);
            }
        }
        return send;
    }

    public static Boolean onMessage(AgentServer agentServer, ACLMessage message, Boolean delivery) throws JSONException, AgentServerException {
        Boolean send = false;
        if ((message != null) && (delivery)) {
            ACLMessage reply = message.createReply();
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if ((content != null) && (content.indexOf("ping") != -1)) {
                    log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());
                    reply.setPerformative(Performative.INFORM);
                    reply.setContent("pong");
                } else {
                    log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                    reply.setPerformative(Performative.REFUSE);
                    reply.setContent("( UnexpectedContent (" + content + "))");
                }
            } else {
                reply.setPerformative(Performative.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
            }
            send = AgentSender.send(agentServer, reply);

        } else if ((message != null) && (!delivery)) {
            {
                ACLMessage reply = message.createReply();
                if (message.getPerformative() == Performative.REQUEST) {
                    String content = message.getContent();
                    if ((content != null) && (content.indexOf("ping") != -1)) {
                        log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());
                        reply.setPerformative(Performative.INFORM);
                        reply.setContent("pong");
                    } else {
                        log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                        reply.setPerformative(Performative.REFUSE);
                        reply.setContent("( UnexpectedContent (" + content + "))");
                    }
                } else {
                    reply.setPerformative(Performative.NOT_UNDERSTOOD);
                    reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
                }
                send = AgentSender.send(agentServer, reply);
            }
        } else {
            return send;
        }
        return send;
    }

    private static AgentInstance getAid(AgentServer agentServer, ACLMessage message) {
        return agentServer.getAgentInstances(message.getReceivers());
    }

    static public synchronized ACLMessage receive(AgentServer agentServer) {
        ACLMessage message = null;
        for (NameValue<ACLMessageList> messageListNameValue : agentServer.agentMessages) {
            for (ACLMessage agentMessage : agentServer.agentMessages.get(messageListNameValue.name)) {
                if (agentMessage.getStatus().equals("enable")) {
                    message = agentMessage;
                    break;
                }
            }
        }
        return message;
    }

    static public synchronized ACLMessage receive(AgentServer agentServer, AgentInstance agent) {
        ACLMessage message = null;
        for (NameValue<ACLMessageList> messageListNameValue : agentServer.agentMessages) {
            for (ACLMessage agentMessage : agentServer.agentMessages.get(messageListNameValue.name)) {
                if ((agentMessage.getReceivers().equals(agent.name)) && (agentMessage.getStatus().equals("enable"))) {
                    message = agentMessage;
                    break;
                }
            }
        }
        return message;
    }

    static public synchronized ACLMessage receive(AgentServer agentServer, String messageId) {
        ACLMessageList messageListNameValue = agentServer.agentMessages.get(messageId);
        ACLMessage message = messageListNameValue.get(messageId);
        return message;
    }

}
