package dcc.agent.server.controller;

import dcc.agent.server.service.agentserver.*;
import dcc.agent.server.service.appserver.AgentAppServer;
import dcc.agent.server.service.appserver.AgentAppServerShutdown;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.field.Field;
import dcc.agent.server.service.scheduler.AgentScheduler;
import dcc.agent.server.service.script.intermediate.*;
import dcc.agent.server.service.script.parser.ScriptParser;
import dcc.agent.server.service.script.runtime.ScriptRuntime;
import dcc.agent.server.service.script.runtime.value.Value;
import dcc.agent.server.service.util.DateUtils;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.NameValue;
import dcc.agent.server.service.util.Utils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

@RestController
public class PlataformController {
    public static AgentServer agentServer;
    static public Thread shutdownThread;
    protected static Logger logger = Logger.getLogger(PlataformController.class);
    public Utils util;



    public AgentServer getAgentServer() {
        return this.agentServer;
    }

    @RequestMapping(value = {"/status/start", "/status/start2"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String Start() throws Exception {

        AgentAppServer agentAppServer = new AgentAppServer();
        // Start the agent server.
        agentServer = new AgentServer(agentAppServer);
        agentServer.start();
        logger.info("- - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - ");
        logger.info("- - - - - - - - Starting agent Server successful - - - - - - ");
        logger.info("- - - - - - - - - - - - - - - - - - - - - - - - - - -- - - - ");


        AgentScheduler agentScheduler = AgentScheduler.singleton;
        logger.info("Starting Agent server");
        // Sleep a little to assure status reflects any recent operation
        Thread.sleep(100);
        JSONObject message = new JSONObject();
        message.put("status", agentScheduler == null ? "shutdown"
                : agentScheduler.getStatus());
       message.put("message", "Starting agent Server successful");
        return message.toString();

    }

    @RequestMapping(value="/get",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String getmain() throws Exception    {
        JSONObject message = new JSONObject();
        message.put("message", "Welcome to Agent Server");
        return message.toString();
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String getConfig() throws JSONException {
        JSONObject configJson = agentServer.config.toJson();
        return configJson.toString();
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String getAbout() throws JSONException {
        JSONObject aboutJson = new JsonListMap();
        aboutJson.put("Plataform", agentServer.config.get("Plataform"));
        aboutJson.put("software", agentServer.config.get("software"));
        aboutJson.put("version", agentServer.config.get("version"));
        aboutJson.put("description", agentServer.config.get("description"));
        aboutJson.put("website", agentServer.config.get("website"));
        aboutJson.put("contact", agentServer.config.get("contact"));
        return aboutJson.toString();
    }

    @RequestMapping(value = "/agent_definitions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAgentDefinitions() throws JSONException {
        logger.info("Getting list of agent definitions");
        JSONArray agentDefinitionsArrayJson = new JSONArray();
        // Get all agents for all users
        for (NameValue<AgentDefinitionList> userAgentDefinitions : agentServer.agentDefinitions) {
            // Get all agents for this user
            for (AgentDefinition agentDefinition : agentServer.agentDefinitions
                    .get(userAgentDefinitions.name)) {
                // Generate JSON for short summary of agent definition
                JSONObject agentDefinitionJson = new JsonListMap();
                agentDefinitionJson.put("user", agentDefinition.user.id);
                agentDefinitionJson.put("name", agentDefinition.name);
                agentDefinitionJson.put("description",
                        agentDefinition.description);
                agentDefinitionJson.put("enabled", agentDefinition.enabled);
                agentDefinitionsArrayJson.put(agentDefinitionJson);

            }
        }
        JSONObject agentDefinitionsJson = new JSONObject();
        agentDefinitionsJson
                .put("agent_definitions", agentDefinitionsArrayJson);

        return agentDefinitionsJson.toString();
    }
    @RequestMapping(value = "/evaluate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getevaluate(HttpServletRequest request) throws JSONException
    {  String resultString="";
        try {
            BufferedReader reader = request.getReader();
            String expressionString = null;
            try {
                StringBuilder builder = new StringBuilder();
                char[] buffer = new char[8192];
                int read;
                while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                    builder.append(buffer, 0, read);
                }
                expressionString = builder.toString();
            } catch (Exception e) {
                logger.info("Exception reading expression text : " + e);
            }

            logger.info("Evaluating expression: " + expressionString);
            AgentDefinition dummyAgentDefinition = new AgentDefinition(
                    agentServer);
            AgentInstance dummyAgentInstance = new AgentInstance(
                    dummyAgentDefinition);
            ScriptParser parser = new ScriptParser(dummyAgentInstance);
            ScriptRuntime scriptRuntime = new ScriptRuntime(
                    dummyAgentInstance);
            ExpressionNode expressionNode = parser
                    .parseExpressionString(expressionString);
            Value valueNode = scriptRuntime.evaluateExpression(
                    expressionString, expressionNode);
             resultString = valueNode.getStringValue();


        } catch (Exception e) {
            logger.info("Evaluate Exception: " + e);
        }
        return resultString;

    }

    @RequestMapping(value = "/agents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getagents() throws JSONException, SymbolException {
        logger.info("Getting list of agent instances for all users");
        JSONArray agentInstancesArrayJson = new JSONArray();
        // Get all agents for all users
        for (NameValue<AgentInstanceList> userAgentInstances : agentServer.agentInstances) {
            // Get all agents for this user
            for (AgentInstance agentInstance : agentServer.agentInstances
                    .get(userAgentInstances.name)) {
                // Generate JSON for short summary of agent instance
                JSONObject agentInstanceJson = new JsonListMap();
                agentInstanceJson.put("user", agentInstance.user.id);
                agentInstanceJson.put("name", agentInstance.name);
                agentInstanceJson.put("definition",
                        agentInstance.agentDefinition.name);
                agentInstanceJson.put("description", agentInstance.description);

                AgentInstance agent = agentServer.agentInstances.get( agentInstance.user.id).get(
                        agentInstance.name);
                SymbolValues outputValues = agent.categorySymbolValues
                        .get("outputs");


                for (Symbol outputSymbol : outputValues) {
                    String fieldName = outputSymbol.name;
                    agentInstanceJson.put("Outputs",agent.getOutput(fieldName));
                }
                agentInstancesArrayJson.put(agentInstanceJson);
            }
        }
        JSONObject agentInstancesJson = new JSONObject();
        agentInstancesJson.put("agent_instances", agentInstancesArrayJson);

        return agentInstancesJson.toString();
    }

    @RequestMapping(value = "/field_types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getFieldTypes() throws JSONException, AgentServerException {

        try {
            logger.info("Getting list of field types");
            JSONArray fieldTypesArrayJson = new JSONArray();
            for (String fieldType : Field.types)
                fieldTypesArrayJson.put(fieldType);
            JSONObject fieldTypesJson = new JSONObject();
            fieldTypesJson.put("field_types", fieldTypesArrayJson);
            return fieldTypesJson.toString(4);
        } catch (JSONException e) {
            throw new AgentServerException(
                    "JSON error generating JSON for agent definition status - "
                            + e);
        }
    }
    @RequestMapping(value = "/status/pause", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putStatusPause() throws Exception {

        // Request the agent scheduler to pause
        AgentScheduler.singleton.pause();

        // Sleep a little to assure status reflects any recent operation
        Thread.sleep(100);

        AgentScheduler agentScheduler = AgentScheduler.singleton;
        logger.info("Pause Agent server");
        JSONObject message = new JSONObject();
        message.put("status", agentScheduler == null ? "shutdown"
                : agentScheduler.getStatus());
        message.put("message", "Pause Agent server");
        return message.toString();

    }
    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getStatus() throws JSONException, InterruptedException, AgentServerException {

        logger.info("Getting status info");

        // Sleep a little to assure status reflects any recent operation
        Thread.sleep(100);

        // Get the status info
        JSONObject aboutJson = new JsonListMap();
        AgentScheduler agentScheduler = AgentScheduler.singleton;

        if((agentScheduler == null))
        {
             aboutJson.put("status","null");
             aboutJson.put("since","null");
             aboutJson.put("num_registered_users","null");
             aboutJson.put("num_active_users","null");
             aboutJson.put("num_active_users","null");
             aboutJson.put("num_registered_agents","null");
             aboutJson.put("num_active_agents","null");

        }
        else {
            aboutJson.put("status", agentScheduler == null ? "shutdown"
                    : agentScheduler.getStatus());

            aboutJson.put("since", DateUtils.toRfcString(agentServer.startTime));
            aboutJson.put("num_registered_users", agentServer.users.size());
            int numActiveUsers = 0;
            for (NameValue<AgentInstanceList> agentInstanceListNameValue : agentServer.agentInstances)
                if (agentInstanceListNameValue.value.size() > 0)
                    numActiveUsers++;
            aboutJson.put("num_active_users", numActiveUsers);
            int num_registered_agents = 0;
            for (NameValue<AgentDefinitionList> agentDefinitionListNameValue : agentServer.agentDefinitions)
                num_registered_agents += agentDefinitionListNameValue.value.size();
            aboutJson.put("num_registered_agents", num_registered_agents);
            int num_active_agents = 0;
            for (NameValue<AgentInstanceList> agentInstanceListNameValue : agentServer.agentInstances)
                num_active_agents += agentInstanceListNameValue.value.size();
            aboutJson.put("num_active_agents", num_active_agents);
        }
        AgentServerProperties agentServerProperties =new AgentServerProperties();
        aboutJson.put("HostName", agentServerProperties.agentServerHostName);
        aboutJson.put("IP", agentServerProperties.agentServerName);
        return aboutJson.toString(4);
    }



    @RequestMapping(value = "/config", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putConfig(HttpServletRequest request) throws Exception {
        JSONObject configJson = util.getJsonRequest(request);
        logger.info("Updating configuration settings");

        // Update the config settings as requested
        agentServer.config.update(configJson);

        // Update was successful
        return configJson.toString();

    }

    @RequestMapping(value = "/config/reset", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putConfigRest() throws Exception {


        logger.info("Resetting to original configuration settings");

        // Reset config settings to original defaults
        agentServer.config.restoreDefaults();
        logger.info("Reseted config Agent server");
        // Update was successful

        JSONObject message = new JSONObject();
        message.put("message", "Reseted config Agent server");
        return message.toString();

    }

    @RequestMapping(value = "/shutdown", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String putshutdown() throws Exception {
        // Request the agent app server to shutdown
        // TODO: Can we really do this here and still return a response?
        // Or do we need to set a timer, return, and shutdown independent of
        // current request

        // Spin up a separate thread to gracefully shutdown the server in a
        // timely manner


        AgentAppServerShutdown agentAppServerShutdown = new AgentAppServerShutdown(
                agentServer);
        shutdownThread = new Thread(agentAppServerShutdown);
        shutdownThread.start();
        logger.info("Shutdown Agent server");

        Thread.sleep(100);
        AgentScheduler agentScheduler = AgentScheduler.singleton;
         JSONObject message = new JSONObject();
        message.put("status", agentScheduler == null ? "shutdown"
                : agentScheduler.getStatus());
        message.put("message", "Shutdown agent Server successful");
        return message.toString();



    }



    @RequestMapping(value = "/run", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getRun(HttpServletRequest request) throws Exception {

        try {
            BufferedReader reader = request.getReader();
            String scriptString = null;
            try {
                StringBuilder builder = new StringBuilder();
                char[] buffer = new char[8192];
                int read;
                while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                    builder.append(buffer, 0, read);
                }
                scriptString = builder.toString();
            } catch (Exception e) {
                logger.info("Exception reading script text : " + e);
            }

            logger.info("Running script: " + scriptString);
            AgentDefinition dummyAgentDefinition = new AgentDefinition(
                    agentServer);
            AgentInstance dummyAgentInstance = new AgentInstance(
                    dummyAgentDefinition);
            ScriptParser parser = new ScriptParser(dummyAgentInstance);
            ScriptRuntime scriptRuntime = new ScriptRuntime(
                    dummyAgentInstance);
            ScriptNode scriptNode = parser.parseScriptString(scriptString);
            Value valueNode = scriptRuntime.runScript(scriptString,
                    scriptNode);
            String resultString = valueNode.getStringValue();
            logger.info("Script result: " + resultString);

        } catch (Exception e) {
            logger.info("Run Exception: " + e);
        }
        return null;

    }

    @RequestMapping(value = "/status/restart", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putStatusrestart() throws Exception {

        // Request the agent scheduler to shutdown
        AgentScheduler.singleton.shutdown();

        // Sleep a little to wait for shutdown to complete
        Thread.sleep(250);

        // Make sure scheduler is no longer running
        if (AgentScheduler.singleton != null)
            // Sleep a little longer to wait for shutdown
            Thread.sleep(250);

        // Force the scheduler to start
        AgentScheduler agentScheduler = new AgentScheduler(agentServer);
        logger.info("Restart Agent server");


        Thread.sleep(100);
        AgentScheduler agentScheduler2 = AgentScheduler.singleton;
        JSONObject message = new JSONObject();
        message.put("status", agentScheduler2 == null ? "shutdown"
                : agentScheduler2.getStatus());
        message.put("message", "Restart agent Server successful");
        return message.toString();





    }

    @RequestMapping(value = "/status/resume", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putStatusresume() throws Exception {

        // Request the agent scheduler to resume
        AgentScheduler.singleton.resume();
        logger.info("Resume Agent server");



        Thread.sleep(100);
        AgentScheduler agentScheduler2 = AgentScheduler.singleton;
        JSONObject message = new JSONObject();
        message.put("status", agentScheduler2 == null ? "shutdown"
                : agentScheduler2.getStatus());
        message.put("message", "Resume agent Server successful ");
        return message.toString();

    }

    @RequestMapping(value = "/status/shutdown", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putStatusShutdown() throws Exception {

        // Request the agent scheduler to shutdown
        logger.info("Shutting down agent server");
        AgentScheduler.singleton.shutdown();
        logger.info("Agent server shut down");


        Thread.sleep(100);
        AgentScheduler agentScheduler2 = AgentScheduler.singleton;
        JSONObject message = new JSONObject();
        message.put("status", agentScheduler2 == null ? "shutdown"
                : agentScheduler2.getStatus());
        message.put("message", "Agent server shut down");
        return message.toString();
    }

    @RequestMapping(value = "/status/start", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String putStatusStart() throws Exception {
        JSONObject message = new JSONObject();
        // Make sure scheduler is not already running
        if (AgentScheduler.singleton == null) {
            // Force the scheduler to start
            AgentScheduler agentScheduler = new AgentScheduler(agentServer);
            logger.info("Re-Start Agent server");

            message.put("message", "Re-Start Agent server");

        }
        return message.toString();

    }

    @RequestMapping(value = "/status/stop", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String putStatusStop() throws Exception {

        // Request the agent scheduler to shutdown
        logger.info("Shutting down agent server");
        AgentScheduler.singleton.shutdown();
        logger.info("Agent server shut down");



        Thread.sleep(100);
        AgentScheduler agentScheduler2 = AgentScheduler.singleton;
        JSONObject message = new JSONObject();
        message.put("status", agentScheduler2 == null ? "shutdown"
                : agentScheduler2.getStatus());
        message.put("message", "Stop Agent server");
        return message.toString();
    }
}
