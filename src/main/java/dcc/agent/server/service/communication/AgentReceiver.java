package dcc.agent.server.service.communication;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.script.intermediate.DelegateTypeNode;
import dcc.agent.server.service.script.intermediate.ScriptNode;
import dcc.agent.server.service.script.parser.ParserException;
import dcc.agent.server.service.script.parser.ScriptParser;
import dcc.agent.server.service.script.parser.tokenizer.TokenizerException;
import dcc.agent.server.service.script.runtime.ScriptRuntime;
import dcc.agent.server.service.script.runtime.value.NullValue;
import dcc.agent.server.service.script.runtime.value.StringValue;
import dcc.agent.server.service.script.runtime.value.Value;
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
            send = AgentSender.send(agentServer, reply,false);
        }
        return send;
    }

    public static Boolean onMessage(AgentServer agentServer, ACLMessage message, Boolean process) throws Exception {
        Boolean send = false;
        if ((message != null)) {
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if (content != null) {
                    log.info("Agent " + message.getReceivers() + " - Received Request from " + message.getSender());
                    reply.setPerformative(Performative.INFORM);
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());
                    Value value = parseScript(agentServer, content);
                    if (value != NullValue.one) {
                        reply.setContent(value.getStringValue());
                    } else {
                        reply.setContent("nothing result");
                    }

                } else {
                    log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                    reply.setPerformative(Performative.REFUSE);
                    reply.setContent("( UnexpectedContent (" + content + "))");
                }
            } else {
                reply.setPerformative(Performative.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
            }
            send = AgentSender.send(agentServer, reply,false);
        }

        return send;
    }

    public static Boolean onMessageDelegate(AgentServer agentServer, ACLMessage message, Boolean stringResult) throws Exception {
        Boolean send = false;
        Value resultValue = NullValue.one;
        Boolean delegate = false;
        if ((message != null)) {
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if ((content != null)) {
                    log.info("Agent " + message.getReceivers() + " - Received Request from " + message.getSender());
                    reply.setPerformative(Performative.INFORM);
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());
                    {
                        resultValue = parseScript(agentServer, content);
                        if (resultValue != NullValue.one) {
                            if (resultValue.toString().equals(message.getReceivers())) {
                                message.setContent(message.getContent().replace("result", "evalue"));
                                // EVALUAR TODAVIA HAY PROBLEMAS CON EL TIPO DE RESULTADO SE REEMPLAZA EL CONTENT
                                onMessageDelegate(agentServer, message, true);
                                return true;
                            } else if ((message.delegate.toString().equals("true")) || (stringResult)) {
                                reply.setContent("Result: " + resultValue);
                            } else {
                                reply.setContent("task DELEGATED to agent: " + resultValue);
                                delegate = true;
                            }
                        } else {
                            reply.setContent("nothing result");
                        }
                    }
                } else {
                    log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                    reply.setPerformative(Performative.REFUSE);
                    reply.setContent("( UnexpectedContent (" + content + "))");
                }
            } else {
                reply.setPerformative(Performative.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
            }
            send = AgentSender.send(agentServer, reply,delegate);

            if ((resultValue != NullValue.one) && (delegate)) {
                delegate(agentServer, message, resultValue);
            }
            return send;
        }
        return null;
    }

    public static Boolean prepareMessage(AgentServer agentServer, ACLMessage message) throws Exception {
        Boolean delegate = false;
        if (message != null) {
            String content = message.getContent();
            if (content != null) {
                ScriptNode scriptNode = parseNode(agentServer, message);
                if (scriptNode.blockNode.localVariables.size() > 0) {
                    if (scriptNode.blockNode.localVariables.get(0).type instanceof DelegateTypeNode) {
                        delegate = true;
                        return onMessageDelegate(agentServer, message, false);

                    }
                }
                return onMessage(agentServer, message, true);
            }

        }
        return null;
    }

    private static ScriptNode parseNode(AgentServer agentServer, ACLMessage message) {

        try {
            AgentInstance dummyAgentInstance = getAgent(agentServer, message.getSender().toString());
            ScriptParser parser = new ScriptParser(dummyAgentInstance);
            ScriptNode scriptNode = parser.parseScriptString( message.getContent());
            return scriptNode;
        } catch (TokenizerException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void delegate(AgentServer agentServer, ACLMessage message, Value value) throws JSONException, AgentServerException {
        if (message != null) {
            ACLMessage newreply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                newreply.setPerformative(Performative.REQUEST);
                newreply.setSender(message.getReceivers());
                newreply.setReceivers(value.getStringValue());
                newreply.setReplyTo(message.getSender());
                newreply.setContent(message.getContent().replace("result", "evalue"));
                newreply.setReplyBy(null);
                newreply.setconversationId(null);
                newreply.setReplyWith(null);
                newreply.setStatus("new");
                newreply.setDelegate(true);
            }
            AgentSender.send(agentServer, newreply,true);
        }
    }

    private static Value parseScript(AgentServer agentServer, String scriptString) throws AgentServerException {
        Value value = null;
        if ((scriptString.indexOf("ping") != -1)) {
            return new StringValue("pong");
        }
        String agentaname = "agent1";
        AgentInstance dummyAgentInstance = getAgent(agentServer, agentaname);
        ScriptParser parser = new ScriptParser(dummyAgentInstance);
        ScriptRuntime scriptRuntime = new ScriptRuntime(dummyAgentInstance);
        try {
            ScriptNode scriptNode = parser.parseScriptString(scriptString);
            Value valueNode = scriptRuntime.runScript(scriptString, scriptNode);
            return valueNode;
        } catch (TokenizerException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return NullValue.one;
    }


    private static AgentInstance getAgent(AgentServer agentServer, String value) {
        return agentServer.getAgentInstances(value);
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

        for (NameValue<ACLMessageList> messageListNameValue : agentServer.agentMessages) {
            for (ACLMessage agentMessage : agentServer.agentMessages.get(messageListNameValue.name)) {
                if ((agentMessage.getReceivers().equals(agent.name)) && (agentMessage.getStatus().equals("new"))) {

                    return  agentMessage;

                }
            }
        }
        return null;
    }

    static public synchronized ACLMessage receive(AgentServer agentServer, String messageId) {
        ACLMessageList messageListNameValue = agentServer.agentMessages.get(messageId);
        ACLMessage message = messageListNameValue.get(messageId);
        return message;
    }

}
