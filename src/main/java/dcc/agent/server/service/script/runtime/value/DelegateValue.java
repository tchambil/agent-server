package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.*;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.communication.ACLMessage;
import dcc.agent.server.service.script.intermediate.DelegateTypeNode;
import dcc.agent.server.service.script.intermediate.StringTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;
import dcc.agent.server.service.script.runtime.ExceptionInfo;
import dcc.agent.server.service.script.runtime.ScriptState;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teo on 25/06/15.
 */
public class DelegateValue extends Value {
    public boolean wait = true;
    protected static Logger log = Logger.getLogger(DelegateValue.class);
    public ACLMessage message;
    public AgentInstance agent;
    public String scriptName;
    public AgentServer agentServer;
    public List<Value> newarguments = new ArrayList<Value>();
    public List<Value> newarguments1 = new ArrayList<Value>();
    public DelegateValue() {
    }

    public TypeNode getType() {
        return DelegateTypeNode.one;
    }

    public Object getValue() {
        // TODO: Return response
        return NullValue.one;
    }

    public Value getNamedValue(ScriptState scriptState, String name) throws RuntimeException {
        if (name.equals("length") || name.equals("size")) {
            return new IntegerValue(0);
        } else if (name.equals("clear")) {
            return NullValue.one;
        } else if (name.equals("keys")) {
            return new ListValue(StringTypeNode.one, new ArrayList<Value>());

        } else if (name.equals("result")) {
            // boolean processMessage = processMessage(scriptState, message, newarguments);
            if (agent == null) {
                return NullValue.one;
            } else {
                return new StringValue(agent.name);
            }

        } else if (name.equals("evalue")) {
            Value evalueMessage = evalue(scriptState);
            if (evalueMessage == null) {
                return NullValue.one;
            } else {
                return new StringValue(evalueMessage.toString());
            }

        } else {
            return super.getNamedValue(scriptState, name);
        }
    }

    public Value evalue(ScriptState scriptState) throws RuntimeException {
        AgentInstance agentName = agent;
        try {
            if (agentName == null) {
                log.info("Missing agent instance name path parameter");
                return NullValue.one;
            }
            User user = agentName.user;

            if (scriptName != null) {
                if (!scriptState.agentServer.agentInstances.get(user.id).containsKey(agentName.name)) {
                    log.info("No agent instance with that name for that user");
                    return NullValue.one;
                }
                ScriptDefinition scriptDefinition = scriptState.agentServer.agentInstances.get(user.id).get(agentName.name).agentDefinition.scripts.get(scriptName);
                if (scriptDefinition == null) {
                    log.info("Undefined public agent script for that user: " + scriptName);
                    return NullValue.one;
                }
                if (!scriptDefinition.publicAccess) {
                    log.info("Undefined public agent script for that user: " + scriptName);
                    return NullValue.one;
                }
                log.info("Call a public script for agent instance " + agentName.name + " for user: " + agentName.user.id);
                AgentInstanceList agentMap = scriptState.agentServer.agentInstances.get(user.id);
                AgentInstance agent = agentMap.get(agentName.name);
                // Call the script
                List<ExceptionInfo> exceptions = agent.exceptionHistory;
                int numExceptions = exceptions.size();

                Value returnValue = agent.runScript(scriptName, newarguments1);

                // Check for exceptions
                int numExceptionsAfter = exceptions.size();
                if (numExceptions != numExceptionsAfter) {
                    //handleException(400, exceptions.get(numExceptions).exception);
                } else {
                    // Done; successful
                    return returnValue;
                }
            }
        } catch (Exception e) {
            log.info("Run Exception: " + e);
        }
        return NullValue.one;
    }


    public Value getMethodValue(ScriptState scriptState, String name, List<Value> arguments) throws RuntimeException {
        this.newarguments = arguments;
        int numArguments = arguments.size();
        if (name.equals("get") && (numArguments >= 1 || numArguments <= 4)) {
            // Get the function of the action to fetch
            scriptName = arguments.get(0).getStringValue();
            AgentInstance result = fetchDelegate(scriptState, scriptName);
            if (result == null)
                return NullValue.one;
            else
                return new StringValue(result.name);
        } else {
            return super.getMethodValue(scriptState, name, arguments);
        }

    }

    private AgentInstance fetchDelegate(ScriptState scriptState, String scriptname) throws RuntimeException {

        try {
            agent = scriptState.agentServer.getAgentInstance(scriptname, true);
            if (agent == null)
                return null;
            else
                return agent;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AgentInstancce GET exception: " + e);
        }
    }


}
