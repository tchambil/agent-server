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

package dcc.agent.server.service.appserver;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.RuntimeException;
import dcc.agent.server.service.util.ListMap;
import org.apache.log4j.Logger;

public class AgentAppServer {
    static final Logger log = Logger.getLogger(AgentAppServer.class);

    public static String appServerApiBaseUrl;
    public AgentServer agentServer;


    public AgentAppServer() throws RuntimeException, AgentServerException, Exception {
        this(true);
    }

    public AgentAppServer(boolean start) throws RuntimeException, AgentServerException, Exception {
        this(start, null);
    }

    public AgentAppServer(boolean start, ListMap<String, String> commandLineproperties) throws RuntimeException, AgentServerException, Exception {
        // Start the agent server.
        agentServer = new AgentServer(this);
        //  if (start)
        //  start();
    }

    public void restart() throws Exception {
        // Shutdown first
        shutdown();
        // And then start up again
        start();
    }

    public void shutdown() throws Exception {
        stop();
       }

    public void start() throws Exception {
        log.info("Starting agent server");
        try {
           // Start the agent server
            agentServer.start();
        } catch (Exception e) {
            log.info("Agent server start exception: " + e);
            e.printStackTrace();
            throw e;
        }
        log.info("Agent server started");
    }

    public void stop() throws Exception {
        log.info("Stopping agent server");
        try {
            // Stop the agent server
            agentServer.stop();
        } catch (Exception e) {
            log.info("Agent server stop exception: " + e);
            throw e;
        }
        log.info("Agent server stopped");
    }
}
