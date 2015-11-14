package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.ACL.ACLMessage;
import dcc.agent.server.service.script.intermediate.MessageTypeNode;
import dcc.agent.server.service.script.intermediate.StringTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;
import dcc.agent.server.service.script.runtime.ScriptState;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teo on 30/06/15.
 */
public class MessageValue extends Value {
    public boolean wait = true;
    protected static Logger log = Logger.getLogger(DelegateValue.class);
    public ACLMessage message;
    public Boolean process;
    public AgentServer agentServer;

    public MessageValue() {
    }

    public TypeNode getType() {
        return MessageTypeNode.one;
    }

    public Object getValue() {
        // TODO: Return response
        return NullValue.one;
    }

    public Value getNamedValue(ScriptState scriptState, String name) throws RuntimeException {
        log.info("Initialize the agent message for send/receive message");
        if (name.equals("length") || name.equals("size")) {
            return new IntegerValue(0);
        } else if (name.equals("clear")) {
            return NullValue.one;
        } else if (name.equals("keys")) {
            return new ListValue(StringTypeNode.one, new ArrayList<Value>());
        } else if (name.equals("process")) {
            if (message == null)
                return NullValue.one;
            else {
                boolean processMessage = true;
                processMessage(scriptState, message);
                if (!processMessage) {
                    return NullValue.one;
                } else {
                    return new StringValue("OK");
                }
            }
        } else if (name.equals("delivery")) {
            if (message == null)
                return NullValue.one;
            else {
                boolean processMessage = true;
                processMessage(scriptState, message);
                if (!processMessage) {
                    return NullValue.one;
                } else {
                    return new StringValue("OK");
                }
            }
        } else
            return super.getNamedValue(scriptState, name);

    }

    public Value getMethodValue(ScriptState scriptState, String name, List<Value> arguments) throws RuntimeException {
        int numArguments = arguments.size();
        if (name.equals("read") && (numArguments >= 1 || numArguments <= 4)) {
            // Get the status message
            String status = arguments.get(0).getStringValue();
            String rmessage = null;
            if (status.equals("all")) {
                rmessage = fetchMessageAll(scriptState);
            } else if (status.equals("only")) {
                rmessage = fetchMessageOnly(scriptState);
            }
            if (rmessage == null || rmessage.length() == 0) {
                return NullValue.one;
            } else {
                processMessage(scriptState, message);
                return new StringValue(rmessage);
            }
        } else {
            return super.getMethodValue(scriptState, name, arguments);
        }
    }

    public void processMessage(ScriptState scriptState, ACLMessage message) throws RuntimeException {
        try {

            scriptState.agentServer.processMessage(scriptState,message);
       } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message process exception: " + e);
        }

    }

    public String fetchMessageAll(ScriptState scriptState) throws RuntimeException {
        try {
            message = scriptState.agentServer.receive();
            if (message == null)
                return null;
            else
                return message.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message GET exception: " + e);
        }
    }

    public String fetchMessageOnly(ScriptState scriptState) throws RuntimeException {
        try {
            AgentInstance agent = scriptState.agentInstance;
            message = scriptState.agentServer.receive(agent);
            if (message == null)
                return null;
            else
                return message.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message GET exception: " + e);
        }
    }

}
