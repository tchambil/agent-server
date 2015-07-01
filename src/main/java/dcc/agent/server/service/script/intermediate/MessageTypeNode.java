package dcc.agent.server.service.script.intermediate;

import dcc.agent.server.service.script.runtime.value.MessageValue;
import dcc.agent.server.service.script.runtime.value.Value;

import java.util.List;

/**
 * Created by teo on 30/06/15.
 */
public class MessageTypeNode extends TypeNode{
    public static MessageTypeNode one=new MessageTypeNode();
    public MessageTypeNode(){}
    public Value create(List<Value> argumentValues){
        return new MessageValue();

    }
    public Value getDefaultValue(){
        return new MessageValue();
    }
    public String toString(){
        return "message";
    }
    public boolean isCompatibleType(TypeNode other){
        return other instanceof MessageTypeNode || other.getClass() == ObjectTypeNode.class;
    }
}
