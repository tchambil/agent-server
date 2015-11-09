package dcc.agent.server.service.script.parser.tokenizer.token;

import dcc.agent.server.service.script.intermediate.NautiLODTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;

/**
 * Created by teo on 8/3/2015.
 */
public class NautiLODKeywordToken extends TypeKeywordToken {
    public String toString(){
        return "NautiLOD";
    }
    public TypeNode getTypeNode(){
        return NautiLODTypeNode.one;
    }
}

