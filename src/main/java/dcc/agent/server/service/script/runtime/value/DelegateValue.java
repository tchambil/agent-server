package dcc.agent.server.service.script.runtime.value;

import dcc.agent.server.service.script.intermediate.DelegateTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;

/**
 * Created by teo on 25/06/15.
 */
public class DelegateValue extends Value {
    public boolean wait = true;

    public DelegateValue(){
    }

    public TypeNode getType(){
        return DelegateTypeNode.one;
    }

    public Object getValue(){
        // TODO: Return response
        return NullValue.one;
    }
}
