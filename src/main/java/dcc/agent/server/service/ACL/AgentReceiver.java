package dcc.agent.server.service.ACL;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.script.intermediate.ScriptNode;
import dcc.agent.server.service.script.intermediate.WebTypeNode;
import dcc.agent.server.service.script.parser.ParserException;
import dcc.agent.server.service.script.parser.ScriptParser;
import dcc.agent.server.service.script.parser.tokenizer.TokenizerException;
import dcc.agent.server.service.script.runtime.ScriptRuntime;
import dcc.agent.server.service.script.runtime.ScriptState;
import dcc.agent.server.service.script.runtime.value.NullValue;
import dcc.agent.server.service.script.runtime.value.StringValue;
import dcc.agent.server.service.script.runtime.value.Value;
import dcc.agent.server.service.swget.multithread.Navigator;
import dcc.agent.server.service.swget.regExpression.parser.ParseException;
import dcc.agent.server.service.util.NameValue;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by teo on 29/06/15.
 */
public class AgentReceiver {
    private static final long serialVersionUID = -5648061637952026195L;
    private static final Logger log = Logger.getLogger(AgentReceiver.class);
    public static Navigator navigator;

    public static Boolean onMessage(AgentServer agentServer) throws JSONException, AgentServerException, IOException {
        Boolean send = false;
        ACLMessage message = receive(agentServer);
        if (message != null) {
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                //   if ((content != null) && (content.indexOf("ping") != -1)) {
                if ((content != null)) {
                    System.out.println(content);
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
            AgentSender.send(agentServer, reply, false);
        }
        return send;
    }

    private static String ActionExec(ScriptState scriptState, String command, String comment) {
        navigator = new Navigator();
        try {
            String[] s = navigator.runCommand(scriptState, command, comment);

            return s[0].toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void onMessage(ScriptState scriptState, ACLMessage message) throws Exception {
        if ((message != null)) {
            ACLMessage reply = message.createReply(scriptState.agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                    /* read message for execution nautilod */
                String content = message.getContent();
                scriptState.message = message;
                if (content != null) {
                    log.info("Agent " + message.getReceivers() + " - Received Request from " + message.getSender());
                    reply.setPerformative(Performative.INFORM);
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());
                    if (content.indexOf("::exec") > 1) {
                        String query = content.substring(content.indexOf("::exec") + 7, content.lastIndexOf(")") - 1);
                        ActionExec(scriptState, query, "");
                    } else {
                        String query = content.substring(content.indexOf(",[") + 1, content.lastIndexOf("])") - 1);
                        List<String> items = Arrays.asList(query.split("\\s*,\\s*"));
                        Collection<String> collection = new ArrayList<String>(items);
                        if (!(items.get(0).equals(""))) {
                            if (collection.size() > 0) {
                                scriptState.agentServer.writeResult(scriptState, collection, scriptState.message.getEnconding());
                            }
                        }
                    }
                } else {
                    log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                    reply.setPerformative(Performative.REFUSE);
                    reply.setContent("( UnexpectedContent (" + content + "))");
                }
                if (!(message.getReceivers().equals(message.getSender()))) {
                    AgentSender.send(scriptState.agentServer, reply);
                }
            } else if (message.getPerformative() == Performative.SUBSCRIBE) {
                /* Suscribe agents in others serves */
                String content = message.getContent();
                scriptState.message = message;
                if (content != null) {
                    log.info("Agent " + message.getReceivers() + " - Received Request from " + message.getSender());
                    reply.setPerformative(Performative.PROPAGATE);
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());
                    User user = scriptState.agentServer.users.get("test-user-1");
                    JSONObject jsonObj = new JSONObject(content);
                    AgentInstance agentInstance = scriptState.agentServer.addAgentInstance(user,jsonObj);
                    reply.setContent("Suscription ok");
                } else {
                    log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                    reply.setPerformative(Performative.REFUSE);
                    reply.setContent("( UnexpectedContent (" + content + "))");
                }
                if (!(message.getReceivers().equals(message.getSender()))) {
                    AgentSender.send(scriptState.agentServer, reply);
                }
            } else {
                reply.setPerformative(Performative.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
                AgentSender.send(scriptState.agentServer, reply);
            }
        }
    }

    public static String parseMessage(AgentServer agentServer, ACLMessage message) throws Exception {
        if (message != null) {
            String content = message.getContent();
            if (content != null) {
                ScriptNode scriptNode = parseNode(agentServer, message);
                if (scriptNode.blockNode.localVariables.size() > 0) {
                    if (scriptNode.blockNode.localVariables.get(0).type instanceof WebTypeNode) {
                        return scriptNode.functionName;
                    }
                }

            }

        }
        return null;
    }
    private static ScriptNode parseNode(AgentServer agentServer, ACLMessage message) {
        try {
            AgentInstance dummyAgentInstance = getAgent(agentServer, message.getSender().toString());
            ScriptParser parser = new ScriptParser(dummyAgentInstance);
            ScriptNode scriptNode = parser.parseScriptString(message.getContent());
            return scriptNode;
        } catch (TokenizerException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Value parseScript(AgentServer agentServer, String scriptString, ACLMessage message) throws AgentServerException {
        Value value = null;
        if ((scriptString.indexOf("ping") != -1)) {
            return new StringValue("pong");
        }
        AgentInstance dummyAgentInstance = getAgent(agentServer, message.getReceivers().toString());
        ScriptParser parser = new ScriptParser(dummyAgentInstance);
        ScriptRuntime scriptRuntime = new ScriptRuntime(dummyAgentInstance);
        try {
            ScriptNode scriptNode = parser.parseScriptString(scriptString);
            Value valueNode = scriptRuntime.runScript(scriptString, scriptNode, message);
            return valueNode;
        } catch (TokenizerException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return NullValue.one;
    }
    private static AgentInstance getAgent(AgentServer agentServer, String value) {
        return agentServer.getAgentInstanceId(value);
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
                if ((agentMessage.getReceivers().equals(agent.aid)) && (agentMessage.getStatus().equals("new"))) {

                    return agentMessage;

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
