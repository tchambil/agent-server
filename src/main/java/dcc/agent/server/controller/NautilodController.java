package dcc.agent.server.controller;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.appserver.AgentAppServerBadRequestException;
import dcc.agent.server.service.swget.multithread.Navigator;
import dcc.agent.server.service.util.Utils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by teo on 11/11/2015.
 */
public class NautilodController {
    public static AgentServer agentServer;
    static public Thread shutdownThread;
    protected static Logger logger = Logger.getLogger(PlataformController.class);
    public Utils util = new Utils();

    public AgentServer getAgentServer()

    {
        return this.agentServer;
    }
    public Navigator navigator;
    @RequestMapping(value = "/users/nautilod", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postnautilod(HttpServletRequest request) throws Exception {

        JSONObject serverJson = util.getJsonRequest(request);
        if (serverJson == null)
            throw new AgentAppServerBadRequestException(
                    "Invalid ServerGroup JSON object");
        // Parse and add the Server Group



        return null;
    }
}
