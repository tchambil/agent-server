package dcc.agent.server.service.script.parser.tokenizer.token;

import dcc.agent.server.service.script.intermediate.FloatTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;


public class DoubleKeywordToken extends TypeKeywordToken {

    public String toString() {
        return "double";
    }

    public TypeNode getTypeNode() {
        return FloatTypeNode.one;
    }

}
