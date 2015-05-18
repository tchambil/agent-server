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

package dcc.agent.server.service.activities;


import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.notification.NotificationInstance;

public class AgentActivityNotification extends AgentActivity {
    public NotificationInstance notificationInstance;

    public AgentActivityNotification(AgentInstance agent, long when, NotificationInstance notificationInstance) throws RuntimeException {
        super(agent, when, notificationInstance.definition.description);
        this.notificationInstance = notificationInstance;
    }

    public boolean performActivity() throws AgentServerException {
        startActivity();

        // Perform the notification
        agent.notify(notificationInstance);

        finishActivity();
        return true;
    }


}
