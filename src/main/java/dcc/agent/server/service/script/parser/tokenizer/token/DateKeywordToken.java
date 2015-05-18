package dcc.agent.server.service.script.parser.tokenizer.token;

import dcc.agent.server.service.script.intermediate.DateTypeNode;
import dcc.agent.server.service.script.intermediate.TypeNode;


public class DateKeywordToken extends TypeKeywordToken {

    public String toString() {
        return "date";
    }

    public TypeNode getTypeNode() {
        return DateTypeNode.one;
    }

}
