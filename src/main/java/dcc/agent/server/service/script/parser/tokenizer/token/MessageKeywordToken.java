package dcc.agent.server.service.script.parser.tokenizer.token;

import dcc.agent.server.service.script.intermediate.MessageTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;

/**
 * Created by teo on 30/06/15.
 */
public class MessageKeywordToken  extends TypeKeywordToken{
    public String toString(){
        return "message";
    }
    public TypeNode getTypeNode(){
        return MessageTypeNode.one;
    }
}
