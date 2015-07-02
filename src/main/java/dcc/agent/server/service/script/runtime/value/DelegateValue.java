package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.communication.ACLMessage;
import dcc.agent.server.service.script.intermediate.DelegateTypeNode;
import dcc.agent.server.service.script.intermediate.StringTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;
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
    public Boolean process;
    public AgentServer agentServer;
    public List<Value> newarguments = new ArrayList<Value>();
    public DelegateValue(){
    }

    public TypeNode getType(){
        return DelegateTypeNode.one;
    }

    public Object getValue(){
        // TODO: Return response
        return NullValue.one;
    }
    public Value getNamedValue(ScriptState scriptState, String name) throws RuntimeException {
        if(name.equals("length") || name.equals("size"))
        {
            return new IntegerValue(0);
        }
        else if(name.equals("clear")){
            return NullValue.one;
        }
        else if (name.equals("keys")){
            return new ListValue(StringTypeNode.one, new ArrayList<Value>());

        }
        else if (name.equals("process")) {
            if (message == null)
                return NullValue.one;
            else {
                boolean processMessage = processMessage(scriptState, message, newarguments);
                if (!processMessage) {
                    return NullValue.one;
                } else {
                    return new StringValue("OK");
                }
            }
        }
        else
        {
            return super.getNamedValue(scriptState, name);
        }
    }
    public Boolean processMessage(ScriptState scriptState, ACLMessage message, List<Value> arguments) throws RuntimeException {
        try {
            process = scriptState.agentServer.process(message,arguments);
            if (!process)
                return false;
            else
                return process;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message process exception: " + e);
        }
    }


    public Value getMethodValue(ScriptState scriptState, String name, List<Value> arguments) throws RuntimeException {
        this.newarguments=arguments;
        int numArguments = arguments.size();
         if (name.equals("get")&& (numArguments >= 1 || numArguments <= 4))
          {
              // Get the function of the action to fetch
           String scriptname = arguments.get(0).getStringValue();
           String result = fetchDelegate(scriptState,scriptname);
            if (result == null || result.length() == 0)
                return NullValue.one;
            else
                return new StringValue(result);
        }
         else
        {
            return super.getMethodValue(scriptState, name, arguments);
        }

    }

    private String fetchDelegate(ScriptState scriptState, String scriptname) throws RuntimeException {

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
