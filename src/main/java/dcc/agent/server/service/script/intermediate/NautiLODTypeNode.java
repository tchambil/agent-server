package dcc.agent.server.service.script.intermediate;

import dcc.agent.server.service.script.runtime.value.NautiLODValue;
import dcc.agent.server.service.script.runtime.value.Value;

import java.util.List;

/**
 * Created by teo on 8/3/2015.
 */
public class NautiLODTypeNode extends TypeNode {
    public static NautiLODTypeNode one = new NautiLODTypeNode();
    public NautiLODTypeNode() {

    }
    public Value create(List<Value> argumentValues) {
        return new NautiLODValue();
    }

    public Value getDefaultValue() {
        return new NautiLODValue();
    }

    public String toString() {
        return "NautiLOD";
    }

    public boolean isCompatibleType(TypeNode other) {
        return other instanceof NautiLODTypeNode || other.getClass() == ObjectTypeNode.class;
    }
}
