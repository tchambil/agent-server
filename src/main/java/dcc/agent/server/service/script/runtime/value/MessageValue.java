package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.communication.ACLMessage;
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
                boolean processMessage = processMessage(scriptState, message, false);
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
                boolean processMessage = processMessage(scriptState, message,true);
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
        if (name.equals("length") || name.equals("size") && numArguments == 0) {
            return new IntegerValue(0);
        } else if (name.equals("add") || name.equals("put") || name.equals("set") && numArguments == 2) {
            return TrueValue.one;
        } else if (name.equals("clear") && numArguments == 0) {
            return NullValue.one;
        } else if (name.equals("get") && (numArguments >= 1 || numArguments <= 4)) {
            // Get the status message
            String status = arguments.get(0).getStringValue();

            //  message.get("all")
            // Fetch the specified web page
            String s = fetchMessage(scriptState);
            if (s == null || s.length() == 0)
                return NullValue.one;
            else
                return new StringValue(s);
        } else if (name.equals("all") && (numArguments >= 1 || numArguments <= 4)) {
            // Get the status message
            String status = arguments.get(0).getStringValue();

            //  message.get("all")
            // Fetch the specified web page
            String message = fetchMessage(scriptState);
            if (message == null || message.length() == 0)
                return NullValue.one;
            else
                return new StringValue(message);
        } else {
            return super.getMethodValue(scriptState, name, arguments);
        }
    }

    public Boolean processMessage(ScriptState scriptState, ACLMessage message, Boolean delivery) throws RuntimeException {
        try {
            process = scriptState.agentServer.process(message,delivery);
            if (!process)
                return false;
            else
                return process;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message process exception: " + e);
        }
    }

    public Boolean processMessage(ScriptState scriptState, String messageId) throws RuntimeException {
        try {
            process = scriptState.agentServer.process(messageId);
            if (!process)
                return false;
            else
                return process;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message process exception: " + e);
        }
    }

    public String fetchMessage(ScriptState scriptState, AgentInstance agentInstance) throws RuntimeException {
        try {
            message = scriptState.agentServer.receive(agentInstance.name);
            if (message == null)
                return null;
            else
                return message.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message GET exception: " + e);
        }
    }

    public String fetchMessage(ScriptState scriptState, String messageId) throws RuntimeException {
        try {
            message = scriptState.agentServer.receive(messageId);
            if (message == null)
                return null;
            else
                return message.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message GET exception: " + e);
        }
    }

    public String fetchMessage(ScriptState scriptState) throws RuntimeException {
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

}
