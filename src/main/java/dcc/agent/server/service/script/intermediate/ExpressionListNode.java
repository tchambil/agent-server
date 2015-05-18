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

import java.util.List;


import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.script.runtime.value.NullValue;
import dcc.agent.server.service.script.runtime.value.Value;
import dcc.agent.server.service.script.runtine.ScriptState;

public class ExpressionListNode extends ExpressionNode {
    public List<ExpressionNode> expressionNodes;

    public ExpressionListNode(List<ExpressionNode> expressionNodes) {
        this.expressionNodes = expressionNodes;
    }

    public Value evaluateExpression(ScriptState scriptState) throws AgentServerException {
        scriptState.countNodeExecutions();
        // Evaluate the full list of expressions
        Value lastValueNode = NullValue.one;
        for (ExpressionNode expressionNode : expressionNodes)
            // Evaluate next expression in list and remember value of last one we see
            lastValueNode = expressionNode.evaluateExpression(scriptState);

        // Return the value of the last expression we evaluated
        return lastValueNode;
    }

}
