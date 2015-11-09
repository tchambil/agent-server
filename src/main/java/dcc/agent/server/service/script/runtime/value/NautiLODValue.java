package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.script.intermediate.NautiLODTypeNode;
import dcc.agent.server.service.script.intermediate.StringTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;
import dcc.agent.server.service.script.runtime.ScriptState;
import dcc.agent.server.service.swget.multithread.Navigator;
import dcc.agent.server.service.swget.regExpression.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teo on 8/3/2015.
 */
public class NautiLODValue extends Value {
    public boolean wait = true;
    public Navigator navigator;
    public NautiLODValue() {
    }
    public TypeNode getType() {
        return NautiLODTypeNode.one;
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

        } else {
            return super.getNamedValue(scriptState, name);
        }
    }
    public Value getMethodValue(ScriptState scriptState, String name, List<Value> arguments) throws RuntimeException {

        int numArguments = arguments.size();
      if (name.equals("get") && (numArguments >= 1 || numArguments <= 4)){
        // Get the URL of the web page to fetch
        String command = arguments.get(0).getStringValue();

        String comment = null;
        // Get the optional useCache flag


        // Fetch the specified web page
        String s = fetchNatilod(scriptState, command, comment);
        if (s == null || s.length() == 0)
            return NullValue.one;
        else
            return new StringValue(s);

    }  else {
            return super.getMethodValue(scriptState, name, arguments);
        }

    }
    private String fetchNatilod(ScriptState scriptState, String command, String comment)  {
        this.navigator = new Navigator();
        try {
            String[] s= navigator.runCommand(scriptState,  command,comment);

            return s[0].toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
