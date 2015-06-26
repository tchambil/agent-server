package dcc.agent.server.service.script.parser.tokenizer.token;

import dcc.agent.server.service.script.intermediate.DelegateTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;

/**
 * Created by teo on 25/06/15.
 */
public class DelegateKeywordToken extends TypeKeywordToken {
    public String toString() {
        return "delegate";
    }

    public TypeNode getTypeNode() {
        return DelegateTypeNode.one;
    }
}
