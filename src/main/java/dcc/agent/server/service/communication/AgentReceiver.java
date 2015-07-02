package dcc.agent.server.service.communication;

import dcc.agent.server.service.agentserver.*;
import dcc.agent.server.service.script.intermediate.ScriptNode;
import dcc.agent.server.service.script.parser.ParserException;
import dcc.agent.server.service.script.parser.ScriptParser;
import dcc.agent.server.service.script.parser.tokenizer.TokenizerException;
import dcc.agent.server.service.script.runtime.ExceptionInfo;
import dcc.agent.server.service.script.runtime.ScriptRuntime;
import dcc.agent.server.service.script.runtime.value.NullValue;
import dcc.agent.server.service.script.runtime.value.Value;
import dcc.agent.server.service.util.NameValue;
import org.apache.log4j.Logger;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if ((content != null) && (content.indexOf("ping") != -1)) {
                    log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());
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

    public static Boolean onMessageId(AgentServer agentServer, String messageId) throws JSONException, AgentServerException {
        Boolean send = false;
        if (messageId != null) {
            ACLMessage message = receive(agentServer, messageId);
            if (message != null) {
                ACLMessage reply = message.createReply(agentServer);
                if (message.getPerformative() == Performative.REQUEST) {
                    String content = message.getContent();
                    if ((content != null) && (content.indexOf("ping") != -1)) {
                        log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());
                        reply.setStatus("response");
                        reply.setInReplyTo(content);
                        reply.setReplyBy(message.getReceivers());
                        reply.setContent("pong");
                        reply.setPerformative(Performative.INFORM);

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

    public static Boolean onMessage(AgentServer agentServer, ACLMessage message) throws Exception {
        Boolean send = false;
        if (message != null) {
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if (content != null) {
                    log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());
                    reply.setPerformative(Performative.INFORM);
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());
                    reply.setContent(parseScript(agentServer,content));
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

    public static Boolean onMessage(AgentServer agentServer, ACLMessage message, Boolean delivery) throws Exception {
        Boolean send = false;
        if ((message != null) && (!delivery)) {
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if ((content != null) && (content.indexOf("ping") != -1)) {
                    log.info("Agent " + message.getReceivers() + " - Received PING Request from " + message.getSender());
                    reply.setPerformative(Performative.INFORM);
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());
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

        } else if ((message != null) && (delivery)) {
            {
                Value value = delivery(agentServer, message);
                if (value != null) {
                    preparedelivery(agentServer, message, value);
                }
            }
        } else {
            return send;
        }
        return send;
    }

    private static void preparedelivery(AgentServer agentServer, ACLMessage message, Value value) throws JSONException, AgentServerException {
        Boolean send = false;
        if (message != null) {
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if ((value != null)) {
                    reply.setPerformative(Performative.INFORM);
                    reply.setContent(value.toJson());
                }
            }
            AgentSender.send(agentServer, reply);
        }
   }

    private static String parseScript(AgentServer agentServer, String scriptString) throws AgentServerException {
        String resultString = null;

        if ((scriptString.indexOf("ping") != -1)) {
            return "pong";
        }
        AgentDefinition dummyAgentDefinition = new AgentDefinition(agentServer);
        AgentInstance dummyAgentInstance = new AgentInstance(dummyAgentDefinition);
        ScriptParser parser = new ScriptParser(dummyAgentInstance);
        ScriptRuntime scriptRuntime = new ScriptRuntime(dummyAgentInstance);
        try {
            ScriptNode scriptNode = parser.parseScriptString(scriptString);
            Value valueNode = scriptRuntime.runScript(scriptString, scriptNode);
            resultString = valueNode.getStringValue();
        } catch (TokenizerException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return resultString;
    }
 private static Value delivery(AgentServer agentServer, ACLMessage message) throws Exception {
        AgentInstance agentName = getAid(agentServer, message);
        User user = agentName.user;
        List<Value> arguments = new ArrayList<Value>();
        String content = message.getContent();

        ///FALTA POR AQUI AUN ESTA CABLEADO DEBE SER AUTOMATICO

        //parse script.name(1,2)
        if ((content != null) && (content.indexOf("script") != -1)) {
            String scriptName = "";
            if (!agentServer.agentInstances.get(user.id).containsKey(agentName.name))
                if (scriptName == null) {
                    return NullValue.one;
                }
            ScriptDefinition scriptDefinition = agentServer.agentInstances.get(user.id).get(agentName.name).agentDefinition.scripts.get(scriptName);
            if (scriptDefinition == null) {
                return NullValue.one;
            }
            if (!scriptDefinition.publicAccess) {
                return NullValue.one;
            }
            log.info("Call a public script for agent instance " + agentName.name + " for user: " + agentName.user.id);
            AgentInstanceList agentMap = agentServer.agentInstances.get(user.id);
            AgentInstance agent = agentMap.get(agentName.name);
            // Call the script
            List<ExceptionInfo> exceptions = agent.exceptionHistory;
            int numExceptions = exceptions.size();
            Value returnValue = agent.runScript(scriptName, arguments);
            // Check for exceptions
            int numExceptionsAfter = exceptions.size();
            if (numExceptions != numExceptionsAfter) {
                //handleException(400, exceptions.get(numExceptions).exception);
            } else {
                // Done; successful
                return returnValue;
            }
        }
        return NullValue.one;
    }

    private static AgentInstance getAid(AgentServer agentServer, ACLMessage message) {
        return agentServer.getAgentInstances(message.getReceivers());
    }

    static public synchronized ACLMessage receive(AgentServer agentServer) {
        ACLMessage message = null;
        for (NameValue<ACLMessageList> messageListNameValue : agentServer.agentMessages) {
            for (ACLMessage agentMessage : agentServer.agentMessages.get(messageListNameValue.name)) {
                if (agentMessage.getStatus().equals("new")) {
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
                if ((agentMessage.getReceivers().equals(agent.name)) && (agentMessage.getStatus().equals("new"))) {
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
