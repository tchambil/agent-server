package dcc.agent.server.service.groups;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.JsonUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by teo on 01/06/15.
 */
public class ServerGroup {
    static final Logger log = Logger.getLogger(ServerGroup.class);
    public AgentServer agentServer;
    public String name;
    public String description;
    public String type;
    public User user;
    public boolean update;
    public AgentServerProperties agentServerProperties;

    public ServerGroup(AgentServer agentServer, User user,String name, String description, String type, boolean update) {
        this.agentServer = agentServer;
        this.name = name;
        this.description = description;
        this.type = type;
        this.user=user;
        this.update = update;
    }
    public JSONObject toJson() throws AgentServerException {
        return toJson(true);
    }

    public JSONObject toJson(boolean includeState) throws AgentServerException {
        return toJson(includeState, -1);
    }

    public JSONObject toJson(boolean includeState, int StateCount) throws AgentServerException {
        try {
            JSONObject messageJson = new JsonListMap();
            messageJson.put("name", name == null ? "" : name);
            messageJson.put("description", description == null ? "" : description);
            messageJson.put("type", type == null ? "" : type);
            messageJson.put("creator", user.id == null ? "" :  user.id );
            return messageJson;
        } catch (JSONException e) {
            e.printStackTrace();
            ;
            throw new AgentServerException("JSON exception in Message.toJson" + e.getMessage());
        }
    }
    public void update(AgentServer agentServer, ServerGroup serverGroup) {
        if (serverGroup.name != null) {
            this.name = serverGroup.name;
        }
        if (serverGroup.description != null) {
            this.description = serverGroup.description;
        }
        if (serverGroup.type != null) {
            this.type = serverGroup.type;
        }
    }
    static public ServerGroup fromJson(AgentServer agentServer, String agentJsonSource) throws JSONException, AgentServerException {
        return fromJson(agentServer, null, new JSONObject(agentJsonSource), false);
    }
    static public ServerGroup fromJson(AgentServer agentServer, User user,  JSONObject agentJson) throws AgentServerException {
        return fromJson(agentServer, user,agentJson, false);
    }
    static public ServerGroup fromJson(AgentServer agentServer, User user,  JSONObject serverJson,  Boolean update) throws AgentServerException {
        log.info("If we have the user, ignore user from JSON");

        // If we have the user, ignore user from JSON
         if (user == null) {
            String userId = serverJson.optString("creator");
            if (userId == null || userId.trim().length() == 0)
                throw new AgentServerException("Group user id ('user') is missing");
             user = agentServer.getUser(userId);
             if (user == User.noUser)
                 throw new AgentServerException("Group user id does not exist: '" + userId + "'");
        }

        // Parse the message sender
        String ServerName = serverJson.optString("name", null);
        if (!update && (ServerName == null) || ServerName.trim().length() == 0) {
            ServerName = "";
        }
        String ServerDescription = serverJson.optString("description", null);
        if (!update && (ServerDescription == null) || ServerDescription.trim().length() == 0) {
            ServerDescription = "";
        }
        String ServerType = serverJson.optString("type", null);
        if (!update && (ServerType == null) || ServerType.trim().length() == 0) {
            ServerType = "";
        }
        JsonUtils.validateKeys(serverJson, "Server Group", new ArrayList<String>(Arrays.asList("creator","name", "description", "type")));
        ServerGroup serverGroup = new ServerGroup(agentServer, user, ServerName, ServerDescription, ServerType, false);
        return serverGroup;
    }
    public String toString() {
        try {
            return toJson().toString();
        } catch (AgentServerException e) {
            log.info("Unable to output ServerGroup as string - " + e.getMessage());
            e.printStackTrace();
            return "[ServerGroup: Unable to output ServerGroup as string - " + e.getMessage();
        }
    }

}
