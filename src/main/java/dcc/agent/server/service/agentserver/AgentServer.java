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

package dcc.agent.server.service.agentserver;

import dcc.agent.server.service.ACL.ACLMessage;
import dcc.agent.server.service.ACL.ACLMessageList;
import dcc.agent.server.service.ACL.AgentReceiver;
import dcc.agent.server.service.ACL.AgentSender;
import dcc.agent.server.service.appserver.AgentAppServer;
import dcc.agent.server.service.config.AgentServerConfig;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.config.AgentServerWebAccessConfig;
import dcc.agent.server.service.delegate.AgentDelegate;
import dcc.agent.server.service.groups.GroupAgentInstance;
import dcc.agent.server.service.groups.GroupAgentInstanceList;
import dcc.agent.server.service.groups.ServerGroup;
import dcc.agent.server.service.groups.ServerGroupList;
import dcc.agent.server.service.mailaccessmanager.MailAccessManager;
import dcc.agent.server.service.persistence.Persistence;
import dcc.agent.server.service.persistence.persistenfile.PersistentFileException;
import dcc.agent.server.service.scheduler.AgentScheduler;
import dcc.agent.server.service.script.intermediate.*;
import dcc.agent.server.service.script.parser.ParserException;
import dcc.agent.server.service.script.parser.tokenizer.TokenizerException;
import dcc.agent.server.service.script.runtime.ScriptState;
import dcc.agent.server.service.script.runtime.value.Value;
import dcc.agent.server.service.util.DateUtils;
import dcc.agent.server.service.util.ListMap;
import dcc.agent.server.service.util.NameValue;
import dcc.agent.server.service.util.NameValueList;
import dcc.agent.server.service.webaccessmanager.WebAccessManager;
import dcc.agent.server.service.webaccessmanager.WebPage;
import dcc.agent.server.service.webaccessmanager.WebSiteAccessConfig;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;


public class AgentServer {
    static final Logger log = Logger.getLogger(AgentServer.class);
    public AgentDelegate agentDelegate;
    public AgentAppServer agentAppServer;
    public static AgentServer singleton = null;
    public List<String> fieldTypes =
            Arrays.asList("string", "int", "float", "money", "option", "choice", "multi_choice", "date", "location", "text", "help");
    public long startTime = 0;
    public AgentScheduler agentScheduler;
    public Persistence persistence;
    public AgentServerConfig config;
    public NameValueList<User> users;
    public NameValueList<AgentDefinitionList> agentDefinitions;
    public NameValueList<AgentInstanceList> agentInstances;
    public NameValueList<ACLMessageList> agentMessages;
    public NameValueList<NautiLODList> nautiLODList;
    public NameValueList<ServerGroupList> serverGroups;
    public NameValueList<GroupAgentInstanceList> groupAgents;
    public AgentServerProperties agentServerProperties;
    public AgentReceiver agentreceiver;
    public AgentSender agentsender;
    public AgentServerWebAccessConfig webAccessConfig;
    public WebSiteAccessConfig webSiteAccessConfig;
    public WebAccessManager webAccessManager;
    public MailAccessManager mailAccessManager;

    public AgentServer(AgentAppServer agentAppServer) throws RuntimeException, AgentServerException, IOException, InterruptedException, PersistentFileException, ParseException, TokenizerException, ParserException {
        this(agentAppServer, true);
    }

    public AgentServer(AgentAppServer agentAppServer, boolean start) throws RuntimeException, AgentServerException, IOException, InterruptedException, PersistentFileException, ParseException, TokenizerException, ParserException {
        log.info("Creation of AgentServer object");
        // Link back to app server
        this.agentAppServer = agentAppServer;
        // Singleton access is now permitted
        singleton = this;
        // Start the agent server
        // start(start);
    }
     public JSONObject getResult(String idResult) throws JSONException, IOException {
         JSONArray List=new JSONArray();
         JSONArray jsonArray = new JSONArray();
        for (NameValue<AgentInstanceList> userAgentInstances : this.agentInstances) {
            for (AgentInstance agentInstance : this.agentInstances
                    .get(userAgentInstances.name)) {
                String webService = getAddresss(agentInstance, idResult);
                RestTemplate restTemplate = new RestTemplate();
                   if (getHttpStatus(webService) == 200){
                       String result = restTemplate.getForObject(webService, String.class);
                       JSONObject jsonObject=new JSONObject(result);
                       List = (JSONArray) jsonObject.get("result");
                       for (int i=0; i<List.length(); i++) {
                           JSONObject item = List.getJSONObject(i);
                           JSONObject jsonObj= new JSONObject();
                           jsonObj.put("id", item.getString("id"));
                          // jsonObj.put("receiver", item.getString("receiver"));
                           jsonObj.put("receiver", agentInstance.aid);
                           jsonObj.put("uri", item.getString("uri"));
                           jsonArray.put(jsonObj);
                       }
                   }
            }
        }
         JSONObject chairJSON= new JSONObject();
         chairJSON.put("name", idResult);
         chairJSON.put("result",jsonArray );

         return chairJSON;
    }
    public static int getHttpStatus(String url) throws IOException {
        int StatusCode;
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            StatusCode = connection.getResponseCode();
        } catch (Exception e) {
            StatusCode = 0;
        }
        return StatusCode;
    }
    private static String getAddresss(AgentInstance agentInstance, String idResult) {
        String webService = null;
        URL tempUrl = null;
        try {
            tempUrl = new URL(agentInstance.addresses.toString());
            String protocol = tempUrl.getProtocol();
            String host = tempUrl.getHost();
            int port = tempUrl.getPort();
            webService = protocol + "://" + host + (port > 0 ? ":" + port : "") + "/result/"+idResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return webService;
    }
    public static AgentServer getSingleton() throws AgentServerException, InterruptedException, IOException, PersistentFileException, ParseException, TokenizerException, ParserException {
        if (singleton == null)
            return (singleton = new AgentServer(null, false));
            //throw new AgentServerException("Cannot access singleton of AgentServer until server is instantiated");
        else
            return singleton;
    }

    public AgentDefinition addAgentDefinition(AgentDefinition agentDefinition) throws AgentServerException {
        if (agentDefinition != null) {
            // Check if the user has any agents yet
            if (!agentDefinitions.containsKey(agentDefinition.user.id)) {
                // No, so create an empty agent table for user
                agentDefinitions.put(agentDefinition.user.id, new AgentDefinitionList());
            }
            // Get agent definition table for the user
            AgentDefinitionList usersAgentDefinitions = agentDefinitions.get(agentDefinition.user.id);
            // Store the new agent definition for the user
            usersAgentDefinitions.put(agentDefinition);

            // Persist the new agent definition
            persistence.put(agentDefinition);

        }
        // Return the agent definition itself
        return agentDefinition;
    }

    public AgentDefinition addAgentDefinition(String agentJsonString) throws SymbolException, RuntimeException, AgentServerException {
        if (agentJsonString == null || agentJsonString.trim().length() == 0)
            agentJsonString = "{}";
        try {
            return addAgentDefinition(new JSONObject(agentJsonString));
        } catch (JSONException e) {
            throw new AgentServerException("JSON parsing exception: " + e.getMessage());
        }
    }

    public AgentDefinition addAgentDefinition(JSONObject agentJson) throws SymbolException, RuntimeException, AgentServerException {
        return addAgentDefinition(null, agentJson);
    }

    public AgentDefinition addAgentDefinition(User user, JSONObject agentJson) throws SymbolException, RuntimeException, AgentServerException {
        // Parse the JSON for the agent definition
        log.info("Parse the JSON for the agent definition");
        AgentDefinition agentDefinition = AgentDefinition.fromJson(this, user, agentJson);
        // Add it to table of agent definitions
        addAgentDefinition(agentDefinition);
        // Return the new agent definition
        return agentDefinition;
    }
    public void writeResult( ScriptState scriptState,Collection<String> CResult , String messageId )
    {
        try {
            try {
                NautiLODList Map = nautiLODList.get(messageId);
                JSONArray jsonArray = new JSONArray();
                JSONArray List=new JSONArray();
                if(Map!=null){
                    NautiLODResult result = Map.get(messageId);
                    JSONObject jsonObject = result.toJson();
                    List = (JSONArray) jsonObject.get("result");
                    removeNautilod(messageId);
                }

                for (int i=0; i<List.length(); i++) {
                    JSONObject item = List.getJSONObject(i);
                    JSONObject jsonObj= new JSONObject();
                    jsonObj.put("id", item.getString("id"));
                    jsonObj.put("receiver", item.getString("receiver"));
                    jsonObj.put("uri", item.getString("uri"));
                    jsonArray.put(jsonObj);
                }
                int n=List.length();
                for (String r : CResult) {
                    JSONObject jsonObj= new JSONObject();
                    jsonObj.put("id", n);
                    jsonObj.put("receiver",scriptState.agentInstance.aid );
                    jsonObj.put("uri", r);
                    jsonArray.put(jsonObj);
                    n++;
                }
                JSONObject chairJSON= new JSONObject();
                chairJSON.put("name", messageId);
                chairJSON.put("result",jsonArray );
                NautiLODResult nautiLODResult = addNautilod(chairJSON);
            } catch (AgentServerException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public synchronized NautiLODResult addNautilood(NautiLODResult nautiLODResult) throws AgentServerException, JSONException {
        if (nautiLODResult != null) {
            // Check if the user has any agents yet
            if (!nautiLODList.containsKey(nautiLODResult.name)) {
                // No, so create an empty agent table for user
                nautiLODList.put(nautiLODResult.name, new NautiLODList());
            }
            // Get agent message table for the user

            NautiLODList nautiLODLis = nautiLODList.get(nautiLODResult.name);
            // Store the new agent message for the user
            nautiLODLis.put(nautiLODResult);
            // Persist the new agent message
            persistence.put(nautiLODResult);

        }
        return nautiLODResult;
    }
     public NautiLODResult addNautilod(JSONObject agentJson) throws JSONException, AgentServerException {
        // Parse the JSON for the agent definition
        log.info("Parse the JSON for the agent definition");
        NautiLODResult nautiLODResult = NautiLODResult.fromJson(this, agentJson);
        // Add it to table of agent definitions
        addNautilood(nautiLODResult);
        // Return the new agent definition
        return nautiLODResult;
    }

    public ACLMessage addAgentMessage(JSONObject agenJson) throws JSONException, AgentServerException, ParseException, ParserException, TokenizerException {
        return addAgentMessage(null, agenJson);
    }

    public synchronized ACLMessage addAgentMessage(ACLMessage agentMessage) throws AgentServerException, JSONException {
        if (agentMessage != null) {
            // Check if the user has any agents yet
            if (!agentMessages.containsKey(agentMessage.conversationId)) {
                // No, so create an empty agent table for user
                agentMessages.put(agentMessage.conversationId, new ACLMessageList());
            }
            // Get agent message table for the user

            ACLMessageList userAgentMessages = agentMessages.get(agentMessage.conversationId);
            // Store the new agent message for the user
            userAgentMessages.put(agentMessage);
            // Persist the new agent message
            persistence.put(agentMessage);

        }
        return agentMessage;
    }



    public ACLMessage getAgentMessage(User user, String agentMessageConversationId) {
        ACLMessageList agenMap = agentMessages.get(user.id);
        if (agenMap == null) {
            return null;
        } else {
            return agenMap.get(agentMessageConversationId);
        }
    }

    public synchronized void processMessage(ScriptState scriptState,ACLMessage message) {
        try {
            AgentReceiver.onMessage( scriptState, message);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (AgentServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized ACLMessage receive() {
        ACLMessage message = AgentReceiver.receive(this);
        return message;
    }

    public synchronized ACLMessage receive(List<Value> newarguments) {
        ACLMessage message = AgentReceiver.receive(this);
        return message;
    }

    public synchronized ACLMessage receive(AgentInstance agentInstance) {
        ACLMessage message = AgentReceiver.receive(this, agentInstance);
        return message;
    }

    public synchronized ACLMessage receive(String messageId) {
        ACLMessage aclMessage = AgentReceiver.receive(this, messageId);
        return aclMessage;
    }

    public void send(ACLMessage message) {
        try {
            AgentSender.send(this, message, false);
        } catch (AgentServerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public synchronized ACLMessage addAgentMessage(User user, JSONObject agentJson) throws AgentServerException, JSONException {
        // Parse the JSON for the agent definition
        log.info("Parse the JSON for the message");
        ACLMessage agentMessage = ACLMessage.fromJson(this, agentJson);
        // Add it to table of agent definitions
        try {
            addAgentMessage(agentMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // DelegateAgentMessage(agentMessage);
        // Return the new agent definition
        return agentMessage;
    }

    public ServerGroup getServerGroup(String groupName) {
        ServerGroup newgroup = null;
        for (NameValue<User> userIdValue : users) {
            User user = userIdValue.value;
            newgroup = serverGroups.get(user.id).get(groupName);
            break;
        }
        return newgroup;
    }

    public ServerGroup addServerGroup(ServerGroup serverGroup) throws AgentServerException {
        if (serverGroup != null) {
            if (!serverGroups.containsKey(serverGroup.user.id)) {
                serverGroups.put(serverGroup.user.id, new ServerGroupList());
            }
            ServerGroupList serverGroupList = serverGroups.get(serverGroup.user.id);
            serverGroupList.put(serverGroup);
            persistence.put(serverGroup);
        }
        return serverGroup;
    }

    public GroupAgentInstance addGroupAgentInstance(GroupAgentInstance GroupAgent) throws AgentServerException {
        if (GroupAgent != null) {
            if (!groupAgents.containsKey(GroupAgent.group.name)) {
                groupAgents.put(GroupAgent.group.name, new GroupAgentInstanceList());
            }
            GroupAgentInstanceList groupAgentInstanceList = groupAgents.get(GroupAgent.group.name);
            groupAgentInstanceList.put(GroupAgent);
            persistence.put(GroupAgent);
        }
        return GroupAgent;
    }

    public ServerGroup addServerGroup(User user, JSONObject agentJson) throws AgentServerException {
        log.info("Parse the JSON for the ServerGroup");
        ServerGroup serverGroup = ServerGroup.fromJson(this, user, agentJson);
        addServerGroup(serverGroup);
        return serverGroup;
    }

    public GroupAgentInstance addGroupAgentInstance(ServerGroup group, AgentInstance agentInstance, JSONObject agentJson) throws AgentServerException {
        log.info("Parse the JSON for the GroupAgentInstance");
        GroupAgentInstance groupAgent = GroupAgentInstance.fromJson(this, group, agentInstance, agentJson);
        addGroupAgentInstance(groupAgent);
        return groupAgent;
    }



    public AgentDefinition getAgentDefinition(User user, String agentDefinitionName) {
        AgentDefinitionList agentMap = agentDefinitions.get(user.id);
        if (agentMap == null)
            return null;
        else
            return agentMap.get(agentDefinitionName);
    }

    public void clearAgentDefinitions(String userId) throws AgentServerException {
        // Check if the user has any agents yet
        if (!agentDefinitions.containsKey(userId))
            // No, so nothing more to do
            return;

        // Get agent table for the user
        AgentDefinitionList usersAgents = agentDefinitions.get(userId);

        // Clear the user's agent list
        usersAgents.clear();
    }

    public void removeAgentDefinition(AgentDefinition agentDefinition) throws AgentServerException {
        String userId = agentDefinition.user.id;
        String agentName = agentDefinition.name;

        // Check if the user has any agents yet
        if (!agentDefinitions.containsKey(userId))
            throw new AgentServerException("Attempt to delete agent definition ('" + agentName + "') for a user ('" + userId + "') that has no agents");

        // Get agent table for the user
        AgentDefinitionList usersAgents = agentDefinitions.get(userId);

        // Check if that agent exists for user
        if (!usersAgents.containsKey(agentName))
            throw new AgentServerException("Attempt to delete agent definition ('" + agentName + "') that does not exist for user ('" + userId + "')");

        // Delete the named agent definition for the user
        usersAgents.remove(agentName);
    }

    public void removeAgentDefinition(String userId, String agentName) throws AgentServerException {
        // Check if the user has any agents yet
        if (!agentDefinitions.containsKey(userId))
            throw new AgentServerException("Attempt to delete agent definition ('" + agentName + "') for a user ('" + userId + "') that has no agents");

        // Get agent table for the user
        AgentDefinitionList usersAgents = agentDefinitions.get(userId);

        // Check if that agent exists for user
        if (!usersAgents.containsKey(agentName))
            throw new AgentServerException("Attempt to delete agent definition ('" + agentName + "') that does not exist for user ('" + userId + "')");

        // Delete the named agent definition for the user
        usersAgents.remove(agentName);
    }

    public AgentInstance addAgentInstance(AgentInstance agentInstance) throws AgentServerException, SymbolException, JSONException {
        // Get instance list for the user
        AgentInstanceList agentInstanceList = agentInstances.get(agentInstance.user.id);
        if (agentInstanceList == null) {
            // Need to do the initial creation of the instance list for this user
            agentInstanceList = new AgentInstanceList();
            agentInstances.put(agentInstance.user.id, agentInstanceList);
        }

        // Store the instance in the instance list for the user
        agentInstanceList.put(agentInstance);

        // Persist the new agent instance
        persistence.put(agentInstance);

        // Return the agent instance
        return agentInstance;
    }

    public AgentInstance addAgentInstance(User user, JSONObject agentInstanceJson) throws AgentServerException, SymbolException, JSONException, TokenizerException, ParserException {
        // Get instance list for the user
        AgentInstance agentInstance = getAgentInstance(user, agentInstanceJson);

        // Return the agent instance
        return agentInstance;
    }

    public AgentInstance getAgentInstanceId(String AgentId) {
        AgentInstance newagentInstance = null;
        for (NameValue<AgentInstanceList> userAgentInstances : this.agentInstances) {
            for (AgentInstance agentInstance : this.agentInstances
                    .get(userAgentInstances.name)) {
                if (agentInstance.aid.equals(AgentId)) {
                    newagentInstance = agentInstance;
                    break;
                }
            }
        }
        return newagentInstance;
    }
    public AgentInstance getAgentInstanceAddress(String host, String id) {
        AgentInstance newagentInstance = null;
        for (NameValue<AgentInstanceList> userAgentInstances : this.agentInstances) {
            for (AgentInstance agentInstance : this.agentInstances
                    .get(userAgentInstances.name)) {
                if (agentInstance.addresses.equals(host)) {
                    newagentInstance = agentInstance;
                    break;
                }
            }
        }
        return newagentInstance;
    }

    public AgentInstance getAgentInstances(String AgentInstanceName) {
        AgentInstance newagentInstance = null;
        for (NameValue<User> userIdValue : users) {
            User user = userIdValue.value;
            newagentInstance = agentInstances.get(user.id).get(AgentInstanceName);
            break;
        }
        return newagentInstance;
    }

    public AgentInstance getAgentInstance(String userId, String agentInstanceName) {
        return getAgentInstance(getUser(userId), agentInstanceName);
    }

    public AgentInstance getAgentInstance(User user, String agentInstanceName) {
        AgentInstanceList agentMap = agentInstances.get(user.id);
        if (agentMap == null)
            return null;
        else
            return agentMap.get(agentInstanceName);
    }

    public AgentInstance getAgentInstance(User user, AgentDefinition agentDefinition) throws RuntimeException, SymbolException, AgentServerException, JSONException, TokenizerException, ParserException {
        return getAgentInstance(user, agentDefinition, null, true);
    }

    public AgentInstance getAgentInstance(User user, AgentDefinition agentDefinition, SymbolValues parameters) throws RuntimeException, SymbolException, AgentServerException, JSONException, TokenizerException, ParserException {
        return getAgentInstance(user, agentDefinition, parameters, true);
    }

    public AgentInstance getAgentInstance(User user, AgentDefinition agentDefinition, SymbolValues parameters, boolean create) throws AgentServerException {
        // Get instance list for the user
        AgentInstanceList agentInstanceList = agentInstances.get(user.id);
        if (agentInstanceList == null) {
            // Need to do the initial creation of the instance list for this user
            agentInstanceList = new AgentInstanceList();
            agentInstances.put(user.id, agentInstanceList);
        }

        // Get the instance from the user's instance list (or add if it doesn't exist yet)
        return agentInstanceList.getAgentInstance(user, agentDefinition, parameters, create);
    }

    public AgentInstance getAgentInstance(String agentJsonString) throws SymbolException, RuntimeException, AgentServerException, TokenizerException, ParserException {
        if (agentJsonString == null || agentJsonString.trim().length() == 0)
            agentJsonString = "{}";
        try {
            return getAgentInstance(new JSONObject(agentJsonString));
        } catch (JSONException e) {
            throw new AgentServerException("JSON parsing exception: " + e.getMessage());
        }
    }

    public AgentInstance getAgentInstance(JSONObject agentJson) throws SymbolException, RuntimeException, AgentServerException, JSONException, TokenizerException, ParserException {
        return getAgentInstance(null, agentJson);
    }

    public AgentInstance getAgentInstance(User user, JSONObject agentJson) throws SymbolException, RuntimeException, AgentServerException, JSONException, TokenizerException, ParserException {
        // Parse the JSON for the agent instance

        // If we have the user, ignore user from JSON
        if (user == null) {
            String userId = agentJson.optString("user");
            if (userId == null || userId.trim().length() == 0)
                throw new AgentServerException("Agent instance user id ('user') is missing");
            user = getUser(userId);
            if (user == User.noUser)
                throw new AgentServerException("Agent instance user id does not exist: '" + userId + "'");
        }

        // Parse the agent instance name
        String agentInstanceName = agentJson.optString("name");
        if (agentInstanceName == null || agentInstanceName.trim().length() == 0)
            throw new AgentServerException("Agent instance name ('name') is missing");
        // Parse the agent instance name

        String agentHostName = agentJson.optString("host", null);
        // if (agentHostName == null || agentHostName.trim().length() == 0)
        //      throw new AgentServerException("Agent instance name ('host') is missing");
        // Parse the agent instance addresses
        String agentaddresses = agentJson.optString("addresses");
        if (agentaddresses == null || agentaddresses.trim().length() == 0)
            throw new AgentServerException("Agent instance definition name ('addresses') is missing");

        // Parse the agent definition name
        String agentDefinitionName = agentJson.optString("definition");
        if (agentDefinitionName == null || agentDefinitionName.trim().length() == 0)
            throw new AgentServerException("Agent instance definition name ('definition') is missing ");

        // Parse the agent type
        String agentType = agentJson.optString("type");
        if (agentType == null || agentType.trim().length() == 0) {
            agentType = "local";

        }

        String agentDescription = agentJson.optString("description");
        if (agentDescription == null || agentDescription.trim().length() == 0)
            agentDescription = "";
        // log.info("Adding new agent instance named: " + agentInstanceName + " for agent definition '" + agentDefinitionName + "' for user: " + user.id);
        AgentInstanceList agentMap = agentInstances.get(user.id);
        //  log.info("Init Print AgentMap:- - - - - " +agentMap);

        if (agentMap == null) {
            agentMap = new AgentInstanceList();
            agentInstances.add(user.id, agentMap);
        }


        // Check if referenced agent definition exists
        //
        AgentDefinitionList userAgentDefinitions = agentDefinitions.get(user.id);
        if (userAgentDefinitions == null)
            throw new AgentServerException("Agent instance '" + agentInstanceName + "' references agent definition '" + agentDefinitionName + "' which does not exist for user '" + user.id + "'");

        AgentDefinition agentDefinition = agentDefinitions.nameValueMap.get(user.id).value.get(agentDefinitionName);
        if (agentDefinition == null)
            throw new AgentServerException("Agent instance '" + agentInstanceName + "' references agent definition '" + agentDefinitionName + "' which does not exist for user '" + user.id + "'");

        // Parse the agent instance parameter values
        String invalidParameterNames = "";
        SymbolManager symbolManager = new SymbolManager();
        SymbolTable symbolTable = symbolManager.getSymbolTable("parameter_values");
        JSONObject parameterValuesJson = null;
        SymbolValues parameterValues = null;
        if (agentJson.has("parameter_values")) {
            // Parse the parameter values
            parameterValuesJson = agentJson.optJSONObject("parameter_values");
            parameterValues = SymbolValues.fromJson(symbolTable, parameterValuesJson);

            // Validate that they are all valid agent definition parameters
            Map<String, Value> treeMap = new TreeMap<String, Value>();
            for (Symbol symbol : parameterValues)
                treeMap.put(symbol.name, null);
            for (String parameterName : treeMap.keySet())
                if (!agentDefinition.parameters.containsKey(parameterName))
                    invalidParameterNames += (invalidParameterNames.length() > 0 ? ", " : "") + parameterName;
            if (invalidParameterNames.length() > 0)
                throw new AgentServerException("Parameter names for agent instance " + agentInstanceName + " are not defined for referenced agent definition " + agentDefinitionName + ": " + invalidParameterNames);
        }

        // Check if instance of named agent definition with specified parameters exists yet
        if (agentMap.getAgentInstance(user, agentDefinition, parameterValues) != null)
            throw new AgentServerException("Agent instance name already exists: '" + agentInstanceName + "' with paramters " + parameterValues.toString());

        String triggerIntervalExpression = agentJson.optString("trigger_interval", AgentDefinition.DEFAULT_TRIGGER_INTERVAL_EXPRESSION);
        String reportingIntervalExpression = agentJson.optString("reporting_interval", AgentDefinition.DEFAULT_REPORTING_INTERVAL_EXPRESSION);

        boolean publicOutput = agentJson.optBoolean("public_output", true);

        int limitInstanceStatesStored = agentJson.optInt("limit_instance_states_stored", -1);

        boolean enabled = agentJson.optBoolean("enabled", true);

        String created = agentJson.optString("created", null);
        long timeCreated = -1;
        try {
            timeCreated = created != null ? DateUtils.parseRfcString(created) : -1;
        } catch (ParseException e) {
            throw new AgentServerException("Unable to parse created date ('" + created + "') - " + e.getMessage());
        }
        String modified = agentJson.optString("modified", null);
        long timeModified = -1;
        try {
            timeModified = modified != null ? DateUtils.parseRfcString(modified) : -1;
        } catch (ParseException e) {
            throw new AgentServerException("Unable to parse modified date ('" + modified + "') - " + e.getMessage());
        }

        AgentInstance agentInstance = agentMap.put(user, agentDefinition, agentInstanceName, agentaddresses, agentHostName, agentType, agentDescription, parameterValues, triggerIntervalExpression, reportingIntervalExpression, publicOutput, limitInstanceStatesStored, enabled, timeCreated, timeModified);

        // Persist the new agent instance
        persistence.put(agentInstance);

        // Return the created/shared agent instance
        return agentInstance;
    }
    public AgentInstance getAgentInstance(String userId, String agentInstanceName, String dataSourceName) {
        AgentInstanceList agentMap = agentInstances.get(userId);
        if (agentMap == null)
            return null;

        AgentInstance agentInstance = agentMap.get(agentInstanceName);
        if (agentInstance == null)
            return null;
        return agentInstance.getDataSourceInstance(dataSourceName);
    }
    public String getAgentInstanceName(String userId, String agentInstanceName, String dataSourceName) {
        AgentInstance dataSourceInstance = getAgentInstance(userId, agentInstanceName, dataSourceName);
        if (dataSourceInstance != null)
            return dataSourceInstance.name;
        else
            return null;
    }
    public AgentInstance getAgentInstance(String scriptName, Boolean wait) {
        // Get all user for plataform
        ScriptDefinition scriptDefinition = null;
        AgentInstance agent = null;
        for (NameValue<User> userIdValue : users) {
            User user = userIdValue.value;
            //Get all agent Instance for Users
            for (AgentInstance agentInstance : agentInstances.get(user.id)) {
                //Get all ScriptDefinitions por agentInstance
                scriptDefinition = agentInstances.get(user.id).get(agentInstance.name).agentDefinition.scripts.get(scriptName);
                if (!(scriptDefinition == null)) {
                    AgentInstanceList agenMap = agentInstances.get(user.id);
                    agent = agenMap.get(agentInstance.name);
                }
            }
        }
        return agent;
    }
    public AgentInstance getAgentInstance(User user, String agentInstanceName, String dataSourceName) {
        AgentInstanceList agentMap = agentInstances.get(user.id);
        if (agentMap == null)
            return null;

        AgentInstance agentInstance = agentMap.get(agentInstanceName);
        if (agentInstance == null)
            return null;
        return agentInstance.getDataSourceInstance(dataSourceName);
    }
    public void removeAgentInstance(AgentInstance agentInstance) throws AgentServerException {
        String userId = agentInstance.user.id;
        String agentName = agentInstance.name;

        // Check if the user has any agents yet
        if (!agentInstances.containsKey(userId))
            throw new AgentServerException("Attempt to delete agent instance ('" + agentName + "') for a user ('" + userId + "') that has no agents");

        // Get agent table for the user
        AgentInstanceList usersAgents = agentInstances.get(userId);

        // Check if that agent exists for user
        if (!usersAgents.containsKey(agentName))
            throw new AgentServerException("Attempt to delete agent instance ('" + agentName + "') that does not exist for user ('" + userId + "')");

        // First step in deleting an agent instance is to pause it
        agentInstance.disable();

        // Wait a little for agent activity to settle down
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Ignore any non-problem here
        }

        // Flush any pending activities for this agent
        agentScheduler.flushAgentActivities(agentInstance);

        // De-reference any input agents
        agentInstance.deReferenceInputs();

        // Delete the named agent definition for the user
        usersAgents.remove(agentName);
    }
    public User addUser(String userId) throws AgentServerException {
        return addUser(new User(userId));
    }
    public User addUser(User user) throws AgentServerException {
        // Add the new user
        users.add(user.id, user);
        log.info("Create Users: Add the new user" + persistence);
        // Persist the new user

        persistence.put(user);

        // Return the new user
        return user;
    }
    public User getUser(String userId) {
        if (userId == null || userId.trim().length() == 0)
            return null;
        else if (userId.equals("*"))
            return User.allUser;
        else {
            User user = users.get(userId);
            return user == null ? User.noUser : user;
        }
    }
    public User recreateUser(User user) throws AgentServerException {
        // Add the new user
        users.add(user.id, user);
        log.info("Re-create Users: Add the new user");

        // Return the new user
        return user;
    }

    public void recreateUser(String userJsonSource) throws AgentServerException, JSONException {

        User newUser = User.fromJson(new JSONObject(userJsonSource));
        recreateUser(newUser);
    }

    public AgentDefinition recreateAgentDefinition(AgentDefinition agentDefinition) throws AgentServerException {
        if (agentDefinition != null) {
            // Check if the user has any agents yet
            if (!agentDefinitions.containsKey(agentDefinition.user.id)) {
                // No, so create an empty agent table for user
                agentDefinitions.put(agentDefinition.user.id, new AgentDefinitionList());
            }
            // Get agent definition table for the user
            AgentDefinitionList usersAgentDefinitions = agentDefinitions.get(agentDefinition.user.id);
            // Store the new agent definition for the user
            usersAgentDefinitions.put(agentDefinition);
        }
        // Return the agent definition itself
        return agentDefinition;
    }

    public void recreateAgentDefinition(String agentDefinitionJsonSource) throws AgentServerException, JSONException, SymbolException {
        AgentDefinition newAgentDefinition = AgentDefinition.fromJson(this, agentDefinitionJsonSource);
        recreateAgentDefinition(newAgentDefinition);
    }

    public void recreateAgentMessage(String agentMessage) throws AgentServerException, ParserException, ParseException, JSONException, TokenizerException {
        ACLMessage newAgentMessage = ACLMessage.fromJson(this, agentMessage);
        recreateAgentMessage(newAgentMessage);

    }
    public void removeNautilod(String name) throws AgentServerException {


        // Check if the user has any agents yet
        if (!nautiLODList.containsKey(name))
            throw new AgentServerException("Attempt to delete NautiLODResult ('" + name + "')   that has no NautiLODResult");

        // Get agent table for the user
        NautiLODList nautiLODLt= nautiLODList.get(name);

        // Check if that agent exists for user
        if (!nautiLODLt.containsKey(name))
            throw new AgentServerException("Attempt to delete NautiLODResult ('" + name + "')   that has no NautiLODResult");

        // Delete the named agent definition for the user
        nautiLODLt.remove(name);
    }

    public void recreateNautilod(String nautilod) throws AgentServerException, ParserException, ParseException, JSONException, TokenizerException {
        NautiLODResult nautiLODResult=NautiLODResult.fromJson(this, nautilod);
        recreateNautilod(nautiLODResult);

    }
    public NautiLODResult recreateNautilod(NautiLODResult nautiLODResult) throws AgentServerException {
        if (nautiLODResult != null) {
            // Check if the plataform has any agent message yet
            if (!nautiLODList.containsKey(nautiLODResult.name)) {
                // No, so create an empty agent message table for plataform
                nautiLODList.put(nautiLODResult.name, new NautiLODList());
            }
            // Get agent message  table for the plataform
            NautiLODList nautiLODLt = nautiLODList.get(nautiLODResult.name);
            // Store the new agentmessage
            nautiLODLt.put(nautiLODResult);
        }
        // Return the agent message  itself
        return nautiLODResult;
    }

    public ACLMessage recreateAgentMessage(ACLMessage agentMessage) throws AgentServerException {
        if (agentMessage != null) {
            // Check if the plataform has any agent message yet
            if (!agentMessages.containsKey(agentMessage.conversationId)) {
                // No, so create an empty agent message table for plataform
                agentMessages.put(agentMessage.conversationId, new ACLMessageList());
            }
            // Get agent message  table for the plataform
            ACLMessageList AgentMessages = agentMessages.get(agentMessage.conversationId);
            // Store the new agentmessage
            AgentMessages.put(agentMessage);
        }
        // Return the agent message  itself
        return agentMessage;
    }

    public void recreateServerGroup(String serverGroup) throws JSONException, AgentServerException {
        ServerGroup newServerGroup = ServerGroup.fromJson(this, serverGroup);
        recreateServerGroup(newServerGroup);
    }

    public void recreateGroupAgent(String GroupAgent) throws JSONException, AgentServerException {
        GroupAgentInstance newGroup = GroupAgentInstance.fromJson(this, GroupAgent);
        recreateGroupAgent(newGroup);

    }

    public GroupAgentInstance recreateGroupAgent(GroupAgentInstance GroupAgent) throws AgentServerException {
        if (GroupAgent != null) {
            // Check if the plataform has any server group yet
            if (!groupAgents.containsKey(GroupAgent.group.name)) {
                // No, so create an empty server group table for plataform
                groupAgents.put(GroupAgent.group.name, new GroupAgentInstanceList());
            }
            // Get server group  table for the plataform
            GroupAgentInstanceList AgentGroupsList = groupAgents.get(GroupAgent.group.name);
            // Store the new server group
            AgentGroupsList.put(GroupAgent);
        }
        // Return the server group itself
        return GroupAgent;
    }

    public ServerGroup recreateServerGroup(ServerGroup serverGroup) throws AgentServerException {
        if (serverGroup != null) {
            // Check if the plataform has any server group yet
            if (!serverGroups.containsKey(serverGroup.user.id)) {
                // No, so create an empty server group table for plataform
                serverGroups.put(serverGroup.user.id, new ServerGroupList());
            }
            // Get server group  table for the plataform
            ServerGroupList AgentGroups = serverGroups.get(serverGroup.user.id);
            // Store the new server group
            AgentGroups.put(serverGroup);
        }
        // Return the server group itself
        return serverGroup;
    }

    public AgentInstance recreateAgentInstance(AgentInstance agentInstance) throws AgentServerException, SymbolException, JSONException {
        // Get instance list for the user
        AgentInstanceList agentInstanceList = agentInstances.get(agentInstance.user.id);
        if (agentInstanceList == null) {
            // Need to do the initial creation of the instance list for this user
            agentInstanceList = new AgentInstanceList();
            agentInstances.put(agentInstance.user.id, agentInstanceList);
        }

        // Store the instance in the instance list for the user
        agentInstanceList.put(agentInstance);
        // Return the agent instance
        return agentInstance;
    }

    public void recreateAgentInstance(String agentInstanceJsonSource) throws AgentServerException, JSONException, SymbolException, ParseException, TokenizerException, ParserException {
        AgentInstance newAgentInstance = AgentInstance.fromJson(this, agentInstanceJsonSource);
        recreateAgentInstance(newAgentInstance);
    }

    public void shutdown() throws Exception {
        stop();
        // TODO: Should this do something else in addition to stop?
    }

    public void start() throws AgentServerException, InterruptedException, IOException, PersistentFileException, ParseException, TokenizerException, ParserException {
        start(true);
    }

    public void start(boolean start) throws AgentServerException, InterruptedException, IOException, PersistentFileException, ParseException, TokenizerException, ParserException {
        // No-op if already started

        if (startTime > 0) {
            return;
        }
        // Record start time for server
        startTime = System.currentTimeMillis();
        // Initialize members
        this.users = new NameValueList<User>();
        this.agentDefinitions = new NameValueList<AgentDefinitionList>();
        this.agentInstances = new NameValueList<AgentInstanceList>();
        this.agentMessages = new NameValueList<ACLMessageList>();
        this.serverGroups = new NameValueList<ServerGroupList>();
        this.groupAgents = new NameValueList<GroupAgentInstanceList>();
        this.nautiLODList=new NameValueList<NautiLODList>();



        // Initialize agent server properties
        agentServerProperties = new AgentServerProperties();

        // Initialize the agent scheduler, but keep it suspended for now
        if (agentScheduler == null)
            agentScheduler = new AgentScheduler(this, false);
        else
            agentScheduler.initialize();

        // Initialize persistence


        if (persistence == null) {
            persistence = new Persistence(this, getPersistentStorePath());
        } else {
            persistence.initialize();

        }
        // Force a reload of config
        if (config == null)
            config = new AgentServerConfig(this);
        config.load();
        if (webAccessConfig == null)
            webAccessConfig = new AgentServerWebAccessConfig(config);

        // Initialize the web site access control lists
        if (webSiteAccessConfig == null)
            webSiteAccessConfig = new WebSiteAccessConfig(this);
        webSiteAccessConfig.load();

        // Initialize the web access manager
        this.webAccessManager = new WebAccessManager(webAccessConfig, webSiteAccessConfig);

        // Initialize the web access manager
        this.mailAccessManager = new MailAccessManager(this);
        mailAccessManager.readConfig();

        // Optionally start scheduler
        if (start) {
            agentScheduler.start();
        }

    }

    public void stop() throws AgentServerException, InterruptedException, IOException {
        // First shut down the agent scheduler and wait for it to fully stop
        agentScheduler.shutdown();

        // Now close the persistent store
        persistence.close();

        // Indicate that the agent server is no longer running
        startTime = 0;
    }

    public String getStatus() {
        return agentScheduler.getStatus();
    }

    public String getDefaultReportingInterval() {
        return config.get("default_reporting_interval");
    }

    public String getDefaultTriggerInterval() {
        return config.get("default_trigger_interval");
    }

    public void addWebSiteAccessControls(User user, JSONObject accessControlsJson) throws JSONException, AgentServerException {
        webAccessManager.addWebSiteAccessControls(user.id, accessControlsJson);

    }

    public WebPage getWebPage(String userId, String url) {
        return webAccessManager.getWebPage(userId, url);
    }

    public WebPage getWebPage(String userId, String url, boolean useCache, long refreshInterval, boolean wait) {
        return webAccessManager.getWebPage(userId, url, useCache, refreshInterval, wait);
    }

    public WebPage postUrl(String userId, String url, String data) {
        return webAccessManager.postUrl(userId, url, data);
    }

    public WebPage postUrl(String userId, String url, String data, long refreshInterval, boolean wait) {
        return webAccessManager.postUrl(userId, url, data, refreshInterval, wait);
    }

    public ListMap<String, String> getWebSiteAccessControls(User user) throws JSONException, AgentServerException {
        return webAccessManager.getWebSiteAccessControls(user);
    }

    public String getAdminPassword() {
        return config.agentServerProperties.adminPassword;
    }

    public String getPersistentStorePath() {
        return agentServerProperties.persistent_store_dir + "/" + agentServerProperties.DEFAULT_PERSISTENT_STORE_FILE_NAME;
    }

    public long getMinimumTriggerInterval() {
        return config.getLong("minimum_trigger_interval");
    }

    public long getMinimumReportingInterval() {
        return config.getLong("minimum_reporting_interval");
    }
}
