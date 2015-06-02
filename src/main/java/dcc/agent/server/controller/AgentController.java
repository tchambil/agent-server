package dcc.agent.server.controller;

import dcc.agent.server.service.agentserver.*;
import dcc.agent.server.service.appserver.AgentAppServerBadRequestException;
import dcc.agent.server.service.appserver.AgentAppServerException;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.delegate.AgentMessage;
import dcc.agent.server.service.notification.NotificationInstance;
import dcc.agent.server.service.script.intermediate.Symbol;
import dcc.agent.server.service.script.intermediate.SymbolValues;
import dcc.agent.server.service.script.runtime.ExceptionInfo;
import dcc.agent.server.service.script.runtime.value.Value;
import dcc.agent.server.service.util.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AgentController {
    protected static Logger logger = Logger.getLogger(AgentController.class);
    public AgentServer agentServer;
    public AgentServerProperties agentServerProperties;
    public Utils util = new Utils();


    @RequestMapping(value = "/users/{id}/agents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String postAgents(@PathVariable String id, HttpServletRequest request) throws Exception {
        logger.info("Create a running instance of new agent definition");
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();

        User user = agentServer.users.get(id);

        JSONObject agentInstanceJson = util.getJsonRequest(request);
        if (agentInstanceJson == null)
            throw new AgentAppServerBadRequestException(
                    "Invalid agent definition JSON object");
        logger.info("Adding new agent instance for user: " + user.id);

        // Parse and add the agent instance
        AgentInstance agentInstance = agentServer.addAgentInstance(user,
                agentInstanceJson);
        // Done
        JSONObject message = new JSONObject();
        message.put("message", "Add was successful");
        return message.toString();

    }


    @RequestMapping(value = "/users/{id}/agents/{name}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putAgents(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        JSONObject agentJson = util.getJsonRequest(request);
        ;

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");

        AgentInstance agent = agentServer.agentInstances.get(user.id).get(
                agentName);
        String agentDefinitionName = agent.agentDefinition.name;
        logger.info("Updating agent instance named: " + agentName
                + " with definition: " + agentDefinitionName
                + " for user: " + user.id);

        // Parse the updated agent instance info
        AgentInstance newAgentInstance = AgentInstance.fromJson(
                agentServer, user, agentJson, agent.agentDefinition, true);

        // Update the agent instance info
        agent.update(agentServer, newAgentInstance);

        // Update was successful
        JSONObject message = new JSONObject();
        message.put("message", "Update was successful");
        return message.toString();


    }

    @RequestMapping(value = "/users/{id}/agents/{name}/dismiss_exceptions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putAgentdismiss_exceptions(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");

        logger.info("Dismissing exceptions for agent instance " + agentName
                + " for user: " + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances
                .get(user.id);
        AgentInstance agent = agentMap.get(agentName);

        // Set the time of dismissal for exceptions
        agent.lastDismissedExceptionTime = System.currentTimeMillis();

        // Done
        return agent.toString();

    }

    @RequestMapping(value = "/users/{id}/agents/{name}/notifications/{notificationName}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putAgentsinstanceNotifications(@PathVariable String id, @PathVariable String name, @PathVariable String notificationName, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;

        String notificationNames = notificationName.length() >= 7 ? notificationName : null;

        String responseParam = request.getParameter("response");
        String responseChoice = request.getParameter("response_choice");
        String comment = request.getParameter("comment");

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");
        if (notificationNames == null)
            throw new AgentAppServerBadRequestException(
                    "Missing notification name path parameter");
        if (notificationNames.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty notification name path parameter");
        if (responseParam == null)
            throw new AgentAppServerBadRequestException(
                    "Missing response query parameter");
        if (responseParam.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty response query parameter");
        if (!NotificationInstance.responses.contains(responseParam))
            throw new AgentAppServerBadRequestException(
                    "Unknown response keyword query parameter");

        AgentInstanceList agentMap = agentServer.agentInstances
                .get(user.id);
        AgentInstance agent = agentMap.get(agentName);

        NotificationInstance notificationInstance = agent.notifications
                .get(notificationNames);
        if (notificationInstance == null)
            throw new AgentAppServerBadRequestException(
                    "Undefined notification name for agent instance '"
                            + agentName + "': " + notificationNames);
        if (!notificationInstance.pending)
            throw new AgentAppServerBadRequestException(
                    "Cannot respond to notification '" + notificationNames
                            + "' for agent instance '" + agentName
                            + "' since it is not pending");

        logger.info("Respond to a pending notification '" + notificationNames
                + "' for agent instance " + agentName + " for user: "
                + user.id);

        agent.respondToNotification(notificationInstance, responseParam,
                responseChoice, comment);

        // Done
        return null;

    }

    @RequestMapping(value = {"/users/{id}/agents/{name}/pause",
                            "/users/{id}/agents/{name}/disable"},
                            method = RequestMethod.PUT,
                            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putAgentpause_disable(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");

        logger.info("Disabling/pausing agent instance " + agentName
                + " for user: " + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances
                .get(user.id);
        AgentInstance agent = agentMap.get(agentName);

        // Disable/pause the agent
        agent.disable();

        // Done
        JSONObject message = new JSONObject();
        message.put("message", "Disabling/pausing agent instance");

        return message.toString();

    }

    @RequestMapping(value = {"/users/{id}/agents/{name}/resume",
                            "/users/{id}/agents/{name}/enable"},
                            method = RequestMethod.PUT,
                            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putAgentresume_enable(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");

        logger.info("Enabling/resuming agent instance " + agentName
                + " for user: " + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances
                .get(user.id);
        AgentInstance agent = agentMap.get(agentName);

        // Enable/resume the agent
        agent.enable();

        // Done
        return agent.toString();

    }

    @RequestMapping(value = "/users/{id}/agents/{name}/run_script/{scriptName}",
                            method = RequestMethod.PUT,
                            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putAgentRun_script(@PathVariable String id, @PathVariable String name, @PathVariable String scriptName, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        String message = "";
        // Capture and convert the arguments to be passed to the script
        List<Value> arguments = new ArrayList<Value>();
        String[] argumentStrings = request.getParameterValues("arg");
        if (argumentStrings != null) {
            int numArgs = argumentStrings.length;
            for (int i = 0; i < numArgs; i++) {
                String argumentString = argumentStrings[i];
                Value argumentValue = JsonUtils.parseJson(argumentString);
                arguments.add(argumentValue);
            }
        }

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");
        if (scriptName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance script name path parameter");
        if (scriptName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance script name path parameter");

        ScriptDefinition scriptDefinition = agentServer.agentInstances.get(
                user.id).get(agentName).agentDefinition.scripts
                .get(scriptName);
        if (scriptDefinition == null)
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Undefined public agent script for that user: "
                            + scriptName);
        if (!scriptDefinition.publicAccess)
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Undefined public agent script for that user: "
                            + scriptName);

        logger.info("Call a public script for agent instance " + agentName
                + " for user: " + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances
                .get(user.id);
        AgentInstance agent = agentMap.get(agentName);

        // Call the script
        List<ExceptionInfo> exceptions = agent.exceptionHistory;
        int numExceptions = exceptions.size();
        Value returnValue = agent.runScript(scriptName, arguments);

        // Check for exceptions
        int numExceptionsAfter = exceptions.size();
        if (numExceptions != numExceptionsAfter) {
            //handleException(400, exceptions.get(numExceptions).exception);
        } else {
            // Done; successful
            JSONObject returnValueObject = new JSONObject();
            returnValueObject.put("return_value",
                    returnValue.toJsonObject());
            message = returnValueObject.toString();
        }
        return message;
    }

    @RequestMapping(value = "/users/{id}/agents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgents(@PathVariable String id) throws Exception {
        logger.info("Getting list of all agent instances for a user");
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);

        JSONArray agentInstancesArrayJson = new JSONArray();
        for (AgentInstance agentInstance : agentServer.agentInstances.get(user.id)) {
            // Generate JSON for short summary of agent instance
            JSONObject agentInstanceJson = new JsonListMap();
            agentInstanceJson.put("user", agentInstance.user.id);
            agentInstanceJson.put("name", agentInstance.name);
            agentInstanceJson.put("definition", agentInstance.agentDefinition.name);
            // TODO: Add the SHA for this instance
            agentInstanceJson.put("description", agentInstance.description);
            agentInstancesArrayJson.put(agentInstanceJson);
        }
        JSONObject agentInstancesJson = new JSONObject();
        agentInstancesJson.put("agent_instances", agentInstancesArrayJson);

        return agentInstancesJson.toString(4);
    }

    @RequestMapping(value = "/users/{id}/agents/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentName(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        String stateString = request.getParameter("state");
        String countString = request.getParameter("count");
        boolean includeState = stateString != null && (stateString.equalsIgnoreCase("true") || stateString.equalsIgnoreCase("yes") || stateString.equalsIgnoreCase("on"));
        int count = -1;
        if (countString != null && countString.trim().length() > 0) {
            count = Integer.parseInt(countString);
        }

        if (agentName == null) {
            throw new AgentAppServerBadRequestException("Missing agent instance name path parameter");
        }

        if (agentName.trim().length() == 0) {
            throw new AgentAppServerBadRequestException("Empty agent instance name path parameter");
        }

        if (!agentServer.agentInstances.get(user.id).containsKey(agentName)) {
            throw new AgentAppServerException(HttpServletResponse.SC_FOUND, "No agent instance with that name for that user");
        }
        logger.info("Getting detail info for agent instances " + agentName + " for user " + user.id);

        AgentInstance agentInstance = agentServer.agentInstances.get(user.id).get(agentName);
        return agentInstance.toJson(includeState, count).toString();
    }

    @RequestMapping(value = "/users/{id}/agents/{name}/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentNameStatus(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        String stateString = request.getParameter("state");
        String countString = request.getParameter("count");
        boolean includeState = stateString != null && (stateString.equalsIgnoreCase("true") || stateString.equalsIgnoreCase("yes") || stateString.equalsIgnoreCase("on"));
        int count = -1;
        if (countString != null && countString.trim().length() > 0) {
            count = Integer.parseInt(countString);
        }

        if (agentName == null) {
            throw new AgentAppServerBadRequestException("Missing agent instance name path parameter");
        }

        if (agentName.trim().length() == 0) {
            throw new AgentAppServerBadRequestException("Empty agent instance name path parameter");
        }

        if (!agentServer.agentInstances.get(user.id).containsKey(agentName)) {
            throw new AgentAppServerException(HttpServletResponse.SC_FOUND, "No agent instance with that name for that user");
        }
        logger.info("Getting status for agent instance" + agentName + " for user:" + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances.get(user.id);

        AgentInstance agentInstance = agentMap.get(agentName);
        return agentInstance.toJson(includeState, count).toString();
    }

    @RequestMapping(value = "/users/{id}/agents/{name}/state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentNameState(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        String countString = request.getParameter("count");
        int count = -1;
        if (countString != null && countString.trim().length() > 0) {
            count = Integer.parseInt(countString);
        }

        if (agentName == null) {
            throw new AgentAppServerBadRequestException("Missing agent instance name path parameter");
        }

        if (agentName.trim().length() == 0) {
            throw new AgentAppServerBadRequestException("Empty agent instance name path parameter");
        }

        if (!agentServer.agentInstances.get(user.id).containsKey(agentName)) {
            throw new AgentAppServerException(HttpServletResponse.SC_FOUND, "No agent instance with that name for that user");
        }
        logger.info("Getting status for agent instance" + agentName + " for user:" + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances.get(user.id);

        AgentInstance agentInstance = agentMap.get(agentName);
        return agentInstance.toJson(true, count).toString();
    }

    @RequestMapping(value = "/users/{id}/agents/{name}/output_history", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentNameOutput_history(@PathVariable String id, @PathVariable String name, HttpServletRequest request) throws Exception {

        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.users.get(id);

        String agentName = name;
        String countString = request.getParameter("count");
        int count = -1;
        if (countString != null && countString.trim().length() > 0) {
            count = Integer.parseInt(countString);
        }

        if (agentName == null) {
            throw new AgentAppServerBadRequestException("Missing agent instance name path parameter");
        }

        if (agentName.trim().length() == 0) {
            throw new AgentAppServerBadRequestException("Empty agent instance name path parameter");
        }

        if (!agentServer.agentInstances.get(user.id).containsKey(agentName)) {
            throw new AgentAppServerException(HttpServletResponse.SC_FOUND, "No agent instance with that name for that user");
        }
        logger.info("Getting status for agent instance" + agentName + " for user:" + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances.get(user.id);

        AgentInstance agent = agentMap.get(agentName);
        if (count <= 0) {
            count = agent.defaultOutputCount;
        }
        if (count > agent.outputLimit) {
            count = agent.outputLimit;
        }
        int outupSize = agent.outputHistory.size();

        if (count > outupSize) {
            count = outupSize;
        }
        //compute starting history index
        int start = outupSize - count;
        int n = agent.outputHistory.size();
        if (n > 4) {
            SymbolValues s1 = agent.outputHistory.get(n - 2).output;
            SymbolValues s2 = agent.outputHistory.get(n - 1).output;
            boolean eq = s1.equals(s2);
        }
        //Build a JSON array of output rows
        JSONArray outputJson = new JSONArray();
        for (int i = start; i < outupSize; i++) {
            OutputRecord outputs = agent.outputHistory.get(i);
            outputJson.put(outputs.output.toJson());
        }

        // Wrap the array in an object since that is what output code
        // expects
        JSONObject outpHistory = new JsonListMap();
        outpHistory.put("output_history", outputJson);

        return outpHistory.toString();
    }

    @RequestMapping(value = "/users/{id}/agents/{name}/output", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentNameOutput(@PathVariable String id, @PathVariable String name) throws Exception {
        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User users = agentServer.getUser(id);

        String userId = users.id;
        String agentName = name;

        if (userId == null)
            throw new AgentAppServerBadRequestException(
                    "Missing user Id path parameter");
        if (userId.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty user Id path parameter");
        if (!agentServer.users.containsKey(userId))
            throw new AgentAppServerBadRequestException(
                    "Unknown user name or invalid password");
        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(userId).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");

        // Password not required for "public output" instances
        AgentInstance agent = agentServer.agentInstances.get(userId).get(
                agentName);
       /* User user = null;
        if (!agent.publicOutput)
            user = checkUserAccess(false);
        */
        logger.info("Getting output for agent instance " + agentName
                + " for user: " + userId);

        // Build a JSON object equivalent to map of output fields
        JSONObject outputJson = new JsonListMap();
        SymbolValues outputValues = agent.categorySymbolValues
                .get("outputs");
        for (Symbol outputSymbol : outputValues) {
            String fieldName = outputSymbol.name;
            outputJson.put(fieldName, agent.getOutput(fieldName)
                    .toJsonObject());
        }

        return outputJson.toString();
    }

    @RequestMapping(value = "/users/{id}/agents/{name}/notifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentsinstanceNotifications(@PathVariable String id, @PathVariable String name) throws Exception {
        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.getUser(id);
        String agentName = name;

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");

        logger.info("Getting current pending notification for agent instance "
                + agentName + " for user: " + user.id);
        AgentInstanceList agentMap = agentServer.agentInstances
                .get(user.id);
        AgentInstance agent = agentMap.get(agentName);

        // Build a JSON array of all pending notifications for agent
        JSONArray pendingNotificationsJson = new JSONArray();
        for (String notificationName : agent.notifications) {
            NotificationInstance notificationInstance = agent.notifications
                    .get(notificationName);
            if (notificationInstance.pending) {
                // Generate and return a summary of the notification
                JSONObject notificationSummaryJson = new JsonListMap();
                notificationSummaryJson.put("agent", agent.name);
                notificationSummaryJson.put("name",
                        notificationInstance.definition.name);
                notificationSummaryJson.put("description",
                        notificationInstance.definition.description);
                notificationSummaryJson.put("details",
                        notificationInstance.details.toJsonObject());
                notificationSummaryJson.put("type",
                        notificationInstance.definition.type);
                notificationSummaryJson.put("time", DateUtils
                        .toRfcString(notificationInstance.timeNotified));
                notificationSummaryJson.put("timeout",
                        notificationInstance.timeout);
                pendingNotificationsJson.put(notificationSummaryJson);
            }
        }

        // Build a wrapper object for the array
        JSONObject wrapperJson = new JSONObject();
        wrapperJson.put("pending_notifications", pendingNotificationsJson);

        // Return the wrapped list
        return wrapperJson.toString();
    }

    @RequestMapping(value = "/users/{id}/agents/{name}/notifications/{notificationNames}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentsinstanceNotificationsName(@PathVariable String id, @PathVariable String name, @PathVariable String notificationNames, HttpServletRequest request) throws Exception {

        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);
        String agentName = name;
        String notificationName = notificationNames.length() >= 7 ? notificationNames : null;
        String responseParam = request.getParameter("response");
        String responseChoice = request.getParameter("response_choice");
        String comment = request.getParameter("comment");
        String message = "";

        if (agentName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(agentName))
            throw new AgentAppServerException(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No agent instance with that name for that user");
        if (notificationName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing notification name path parameter");
        if (notificationName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty notification name path parameter");

        // Access the named notification
        AgentInstanceList agentMap = agentServer.agentInstances
                .get(user.id);
        AgentInstance agent = agentMap.get(agentName);
        NotificationInstance notificationInstance = agent.notifications
                .get(notificationName);
        if (notificationInstance == null)
            throw new AgentAppServerBadRequestException(
                    "Undefined notification name for agent instance '"
                            + agentName + "': " + notificationName);

        // If no response, simply return info about the notification
        if (responseParam == null) {
            // Generate and return a summary of the notification
            JSONObject notificationSummaryJson = new JsonListMap();
            notificationSummaryJson.put("agent", agent.name);
            notificationSummaryJson.put("name",
                    notificationInstance.definition.name);
            notificationSummaryJson.put("description",
                    notificationInstance.definition.description);
            notificationSummaryJson.put("details",
                    notificationInstance.details.toJsonObject());
            notificationSummaryJson.put("type",
                    notificationInstance.definition.type);
            notificationSummaryJson.put("time", DateUtils
                    .toRfcString(notificationInstance.timeNotified));
            notificationSummaryJson.put("timeout",
                    notificationInstance.timeout);
            message = notificationSummaryJson.toString();
        } else {
            if (responseParam.trim().length() == 0)
                throw new AgentAppServerBadRequestException(
                        "Empty response query parameter");
            if (!NotificationInstance.responses.contains(responseParam))
                throw new AgentAppServerBadRequestException(
                        "Unknown response keyword query parameter");
            if (!notificationInstance.pending)
                throw new AgentAppServerBadRequestException(
                        "Cannot respond to notification '"
                                + notificationName
                                + "' for agent instance '" + agentName
                                + "' since it is not pending");

            logger.info("Respond to a pending notification '"
                    + notificationName + "' for agent instance "
                    + agentName + " for user: " + user.id);

            agent.respondToNotification(notificationInstance,
                    responseParam, responseChoice, comment);
        }
        return message;
    }

    @RequestMapping(value = "/users/{id}/agents/{name}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String deleteAgentName(@PathVariable String id, @PathVariable String name) throws Exception
    {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);

        String agentInstanceName = name;

        if (agentInstanceName == null)
            throw new AgentAppServerBadRequestException(
                    "Missing agent instance name path parameter");
        if (agentInstanceName.trim().length() == 0)
            throw new AgentAppServerBadRequestException(
                    "Empty agent instance name path parameter");
        if (!agentServer.agentInstances.get(user.id).containsKey(
                agentInstanceName))
            throw new AgentAppServerBadRequestException(
                    "No agent definition with that name for that user");

        AgentInstance agentInstance = agentServer.getAgentInstance(user,
                agentInstanceName);
        if (agentInstance == null)
            throw new AgentAppServerBadRequestException(
                    "No agent instance named '" + agentInstanceName
                            + " for user '" + user.id + "'");
        logger.info("Deleting agent instance named: " + agentInstanceName
                + " of agent definition named "
                + agentInstance.agentDefinition.name + " for user: "
                + user.id);

        // Delete the instance
        agentServer.removeAgentInstance(agentInstance);

        JSONObject message = new JSONObject();
        message.put("message", "Deleting agent instance");
        return message.toString();
    }


    @RequestMapping(value = "/agents/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getagenttask(HttpServletRequest request) throws Exception {
        logger.info("Getting task of agent instances for all users");
        String message = "";
        ScriptDefinition scriptDefinition = null;
        AgentInstance agent = null;
        JSONObject retunValueObject = new JSONObject();

        JSONObject agentMessageson = util.getJsonRequest(request);
         if (agentMessageson == null) {
             throw new AgentAppServerBadRequestException(
                     "Invalid agent message JSON object");
         }
        //Get User Id
        User userId = agentServer.getUser(agentMessageson.optString("sender"));
        //Get Script Name/TASK
        AgentMessage agentMessage =agentServer.addAgentMessage(userId,agentMessageson);
        String scriptName = agentMessage.content.toString();

        // Capture and convert the arguments to be passed to the script
        List<Value> arguments = new ArrayList<Value>();
        String[] argumentStrings = request.getParameterValues("arg");
        if (argumentStrings != null) {
            int numArgs = argumentStrings.length;
            for (int i = 0; i < numArgs; i++) {
                String argumentString = argumentStrings[i];
                Value argumentValue = JsonUtils.parseJson(argumentString);
                arguments.add(argumentValue);
            }
        }
        // Get all user for plataform
        for (NameValue<User> userIdValue : agentServer.users) {
            User user = userIdValue.value;
            //Get all agent Instance for Users
            for (AgentInstance agentInstance : agentServer.agentInstances.get(user.id)) {
                //Get all ScriptDefinitions por agentInstance
                scriptDefinition = agentServer.agentInstances.get(user.id).get(agentInstance.name).agentDefinition.scripts.get(scriptName);
                if (!(scriptDefinition == null)) {
                    AgentInstanceList agenMap = agentServer.agentInstances.get(user.id);
                    agent = agenMap.get(agentInstance.name);
                }
            }
        }
        if (scriptDefinition == null)
        {
         AgentMessage agentMessageR=agentServer.addDelegateAgent(agentMessage);
         retunValueObject.put("Server Web ", agentServerProperties.agentServerHostName);
         retunValueObject.put("return_value: ", "Task delegate to " +agentMessageR.replyTo.toString());
         retunValueObject.put("Status: ", agentMessageR.replyTo.toString());
         message = retunValueObject.toString();
        } else {
            if (scriptDefinition.publicAccess) {
                //Call the script
                List<ExceptionInfo> exception = agent.exceptionHistory;
                int numExceptions = exception.size();
                Value retunValue = agent.runScript(scriptName, arguments);
                //Check for exceptions
                int numExceptionsAfter = exception.size();
                if (numExceptions != numExceptionsAfter) {
                    //handleException(400, exceptions.get(numExceptions).exception);
                } else {
                    retunValueObject.put("Server Web ", agentServerProperties.agentServerHostName);
                    retunValueObject.put("User ", agent.user.id);
                    retunValueObject.put("Agent Definition", agent.agentDefinition.name);
                    retunValueObject.put("Agent ", agent.name);
                    retunValueObject.put("return_value", retunValue.toJsonObject());
                    message = retunValueObject.toString();
                }
            } else {
                retunValueObject.put("return_value", "");
                message = retunValueObject.toString();
            }
        }
        return message;
    }

    @RequestMapping(value = "/users/{id}/messagetest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String postAgentDefinition(@PathVariable String id, HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get(id);

        JSONObject agentMessageson = util.getJsonRequest(request);
        if (agentMessageson == null)
            throw new AgentAppServerBadRequestException(
                    "Invalid agent message JSON object");
        logger.info("Adding new agent message for user: " + user.id);
        // Parse and add the agent definition
        AgentMessage agentMessage = agentServer.addAgentMessage(
                user, agentMessageson);
        // Done
        JSONObject message = new JSONObject();
        message.put("message", "Add was successful");
        return message.toString();
    }


    @RequestMapping(value = "/users/{id}/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentMessagey (@PathVariable String id) throws JSONException {
        PlataformController plataformController = new PlataformController();
        agentServer = plataformController.getAgentServer();
        User user = agentServer.users.get(id);

        logger.info("Getting list of all agent message for user Id: " + user.id);

        // Get all agents for this user
        JSONArray agentMessageArrayJson = new JSONArray();
        for (AgentMessage agentMessage : agentServer.agentMessages.get(user.id)) {
            // Generate JSON for short summary of agent definition
            logger.info("Getting list of all agent message for user Id: " + agentMessage.user.id);
            JSONObject agentDefinitionJson = new JsonListMap();
            agentDefinitionJson.put("user", agentMessage.user.id);
            agentDefinitionJson.put("conversationId", agentMessage.conversationId);
            agentDefinitionJson.put("sender", agentMessage.sender);
            agentDefinitionJson.put("receiver", agentMessage.receiver);
            agentDefinitionJson.put("replyTo", agentMessage.replyTo);

            agentMessageArrayJson.put(agentDefinitionJson);
        }
        JSONObject agentMessageJson = new JSONObject();
        agentMessageJson.put("agent_message", agentMessageArrayJson);
        return agentMessageJson.toString();

    }

}
