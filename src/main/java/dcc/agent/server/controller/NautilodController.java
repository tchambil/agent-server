package dcc.agent.server.controller;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.NautiLODList;
import dcc.agent.server.service.agentserver.NautiLODResult;
import dcc.agent.server.service.util.Utils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
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

    @RequestMapping(value = "/results/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getResults( @PathVariable String name,HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();

        NautiLODList Map = agentServer.nautiLODList.get(name);
        NautiLODResult result = Map.get(name);
        return result.toJson().toString();
    }

    @RequestMapping(value = "/result/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getResult( @PathVariable String name,HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();

             //    return result.toJson().toString();
        return agentServer.getResult(name).toString();
    }

    @RequestMapping(value = "/result/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String gettest(  HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        String datax= "{\"nodes\": [{\"id\": 1,  \"label\": \"agent1@dbpedias.cloudapp.net\","+
                " \"group\": \"users\"}, {\"id\": 2, \"label\": \"agent2@geonames.cloudapp.net\","+
                " \"group\": \"users\"},{\"id\": 3,\"label\": \"agent3@yago.cloudapp.net\","+
                "\"group\": \"users\"}],\"edges\": [{\"from\": 1,\"to\": 2},{\"from\": 2,\"to\": 3}]}";
        JSONObject agentMessageson = new JSONObject(datax);

        return agentMessageson.toString();
    }


}
