/**
 * Copyright 2012 John W. Krupansky d/b/a Base Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dcc.agent.server.service.script.intermediate;

import java.util.ArrayList;
import java.util.List;


import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.script.runtime.value.NullValue;
import dcc.agent.server.service.script.runtime.value.Value;
import dcc.agent.server.service.script.runtime.ScriptState;

public class NewNode extends ExpressionNode {
    public TypeNode type;
    public List<ExpressionNode> arguments;

    public NewNode(TypeNode type, List<ExpressionNode> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    public Value getValue(ScriptState scriptState) throws AgentServerException {
        List<Value> argumentValues = new ArrayList<Value>();
        for (ExpressionNode argumentExpressionNode : arguments)
            argumentValues.add(argumentExpressionNode.evaluateExpression(scriptState));
        Value valueNode = type.create(argumentValues);
        return valueNode;
    }

    public Value evaluateExpression(ScriptState scriptState) throws AgentServerException {
        scriptState.countNodeExecutions();
        Value valueNode = getValue(scriptState);
        if (valueNode == null)
            return NullValue.one;
        else
            return valueNode;
    }

}
