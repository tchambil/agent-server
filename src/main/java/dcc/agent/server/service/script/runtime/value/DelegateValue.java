package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.script.intermediate.DelegateTypeNode;
import dcc.agent.server.service.script.intermediate.StringTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;
import dcc.agent.server.service.script.runtime.ScriptState;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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
     static public ResponseEntity<String>  delegate(ScriptState scriptState,List<Value> arguments)  {
        log.info("Initialize the agent delegate for send agent message");
        // Initialize the agent delegate,
         String server = arguments.get(0).getStringValue();
         String script = arguments.get(1).getStringValue();
         Integer iterator =arguments.get(2).getIntValue();

         String newscript= "delegate d; string s = w.get('server','2','false');" ;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity =new HttpEntity<String>(newscript.toString(),headers);
        ResponseEntity<String> response=restTemplate.exchange(server, HttpMethod.POST,entity,String.class);
         if(response.getStatusCode()==HttpStatus.OK)
         {
             log.info("successful send agent message and prepare save local");

         }
         return response;

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
            return NullValue.one;

        }
        else if (name.equals("get")&&(numArguments==3))
        {
            delegate(scriptState, arguments);

            return NullValue.one;

        }
        else
        {
            return super.getMethodValue(scriptState, name, arguments);
        }
    }

}
