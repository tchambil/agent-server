package dcc.agent.server.service.script.intermediate;

import dcc.agent.server.service.script.runtime.value.DelegateValue;
import dcc.agent.server.service.script.runtime.value.Value;

import java.util.List;

/**
 * Created by teo on 25/06/15.
 */
public class DelegateTypeNode  extends TypeNode {
    public static DelegateTypeNode one = new DelegateTypeNode();
    public DelegateTypeNode() {

    }
    public Value create(List<Value> argumentValues) {
        return new DelegateValue();
    }

    public Value getDefaultValue() {
        return new DelegateValue();
    }

    public String toString() {
        return "delegate";
    }

    public boolean isCompatibleType(TypeNode other) {
        return other instanceof DelegateTypeNode || other.getClass() == ObjectTypeNode.class;
    }

}
