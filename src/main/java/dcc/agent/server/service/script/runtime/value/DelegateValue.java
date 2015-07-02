package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.RuntimeException;
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
    public AgentServer agentServer;
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
        else
        {
            return super.getNamedValue(scriptState, name);
        }
    }


    public Value getMethodValue(ScriptState scriptState, String name, List<Value> arguments) throws RuntimeException {
        int numArguments =arguments.size();

        if(name.equals("length") || name.equals("size")&& numArguments==0)
        {
            return new IntegerValue(0);
        }
        else if(name.equals("add")|| name.equals("put")|| name.equals("set")&& numArguments==2){
            return TrueValue.one;
        }
        else if(name.equals("clear")&& numArguments==0){
            return NullValue.one;
        }
        else if (name.equals("get")&&(numArguments>=1||numArguments<=4))
        {
           // delegate(scriptState, arguments);

        }
         else
        {
            return super.getMethodValue(scriptState, name, arguments);
        }
        return null;
    }

}
