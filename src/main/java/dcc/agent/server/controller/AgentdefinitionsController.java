package dcc.agent.server.controller;

import dcc.agent.server.service.agentserver.AgentDefinition;
import dcc.agent.server.service.agentserver.AgentDefinitionList;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.appserver.AgentAppServerBadRequestException;
import dcc.agent.server.service.appserver.AgentAppServerException;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.Utils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AgentdefinitionsController {
    protected static Logger logger = Logger.getLogger(UsersController.class);
    public AgentServer agentServer;
    public Utils util = new Utils();


    @RequestMapping(value = "/users/{id}/agent_definitions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String postAgentDefinition(@PathVariable String id, HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);

        JSONObject agentDefinitionJson = util.getJsonRequest(request);
        if (agentDefinitionJson == null)
            throw new AgentAppServerBadRequestException(
                    "Invalid agent definition JSON object");
        logger.info("Adding new agent definition for user: " + user.id);
        // Parse and add the agent definition
        AgentDefinition agentDefinition = agentServer.addAgentDefinition(
                user, agentDefinitionJson);
        // Done
        JSONObject message = new JSONObject();
        message.put("message", "Add was successful");
        return message.toString();
    }

    @RequestMapping(value = "/users/{id}/agent_definitions/{name}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putAgentDefinition(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        JSONObject agentJson = util.getJsonRequest(request);
        logger.info("Information PUT agent_definitions:" + user.id + agentName);
        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent definition name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent definition name path parameter");
        if (!agentServer.users.containsKey(user.id))
            throw new AgentAppServerBadRequestException("Unknown user id");

        AgentDefinition agent = agentServer.agentDefinitions.get(user.id)
                .get(agentName);
        logger.info("Updating agent definition named: " + agentName
                + " for user: " + user.id);

        // Parse the updated agent definition info
        AgentDefinition newAgentDefinition = AgentDefinition.fromJson(
                agentServer, user, agentJson, true);

        // Update the agent definition info
        agent.update(agentServer, newAgentDefinition);

        // Update was successful
        JSONObject message = new JSONObject();
        message.put("message", "Update was successful");
        return message.toString();

    }

    @RequestMapping(value = "/users/{id}/agent_definitions/{name}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String deleteAgentDefinition(@PathVariable String id, @PathVariable String name ) throws Exception
    {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);

        String agentDefinitionName = name;

        if (agentDefinitionName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent definition name path parameter");
        if (agentDefinitionName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent definition name path parameter");
        if (!agentServer.agentDefinitions.get(user.id).containsKey(
                agentDefinitionName))
            throw new AgentAppServerBadRequestException(
                    "No agent definition with that name for that user");

        // Delete the agent definition
        logger.info("Deleting agent definition named: " + agentDefinitionName
                + " for user: " + user.id);
        AgentDefinition agentDefinition = agentServer.getAgentDefinition(
                user, agentDefinitionName);
        agentServer.removeAgentDefinition(agentDefinition);

        JSONObject message = new JSONObject();
        message.put("message", "Deleting agent definition");
        return message.toString();

    }

    @RequestMapping(value = "/users/{id}/agent_definitions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentDefinitionName(@PathVariable String id) throws Exception {
        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.users.get(id);

        logger.info("Getting list of all agent definitions for user Id: " + user.id);

        // Get all agents for this user
        JSONArray agentDefinitionsArrayJson = new JSONArray();
        for (AgentDefinition agentDefinition : agentServer.agentDefinitions.get(user.id)) {
            // Generate JSON for short summary of agent definition
            logger.info("Getting list of all agent definitions for user Id: " + agentDefinition.user.id);
            JSONObject agentDefinitionJson = new JsonListMap();
            agentDefinitionJson.put("user", agentDefinition.user.id);
            agentDefinitionJson.put("name", agentDefinition.name);
            agentDefinitionJson.put("description", agentDefinition.description);
            agentDefinitionsArrayJson.put(agentDefinitionJson);
        }
        JSONObject agentDefinitionsJson = new JSONObject();
        agentDefinitionsJson.put("agent_definitions", agentDefinitionsArrayJson);
        return agentDefinitionsJson.toString();

    }

    @RequestMapping(value = "/users/{id}/agent_definitions/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentDefinitions(@PathVariable String id, @PathVariable String name) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent definition name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent definition name path parameter");
        if (!agentServer.agentDefinitions.get(user.id).containsKey(
                agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent definition with that name for that user");

        logger.info("Getting definition for agent definition " + agentName
                + " for user: " + user.id);
        AgentDefinitionList agentMap = agentServer.agentDefinitions
                .get(user.id);
        AgentDefinition agentDefinition = agentMap.get(agentName);
        return agentDefinition.toJson().toString();

    }

}
