package dcc.agent.server.controller;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.NautiLODList;
import dcc.agent.server.service.agentserver.NautiLODResult;
import dcc.agent.server.service.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by teo on 11/11/2015.
 */
@RestController
public class NautilodController {
    public static AgentServer agentServer;
    protected static Logger logger = Logger.getLogger(PlataformController.class);
    public Utils util = new Utils();

    public AgentServer getAgentServer()

    {
        return this.agentServer;
    }

    @RequestMapping(value = "/result/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getResult( @PathVariable String name,HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();

        NautiLODList Map = agentServer.nautiLODList.get(name);
        NautiLODResult result = Map.get(name);
        return result.toJson().toString();
    }
}
