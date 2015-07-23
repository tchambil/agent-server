package dcc.agent.server.service.groups;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.util.DateUtils;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.JsonUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by teo on 23/07/15.
 */
public class GroupAgentInstance {
    static final Logger log = Logger.getLogger(ServerGroup.class);
    public AgentServer agentServer;
    public String id;
    public ServerGroup group;
    public AgentInstance agentInstance;
    public String state;
    public long timeInstantiated;
    public long timeUpdated;
    public boolean update;

    public GroupAgentInstance(AgentServer agentServer,  ServerGroup group, AgentInstance agentInstance, String state,long timeInstantiated,
                              long timeUpdated, boolean update) {
        this.agentServer = agentServer;
        this.id=group.name+agentInstance.aid;
        this.group = group;
        this.agentInstance = agentInstance;
        this.state = state;
        this.timeInstantiated = timeInstantiated > 0 ? timeInstantiated : System.currentTimeMillis();
        this.timeUpdated = timeUpdated > 0 ? timeUpdated : 0;
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
            JSONObject agentJson = new JsonListMap();
            agentJson.put("group", group.name == null ? "" : group.name);
            agentJson.put("agentInstance", agentInstance.aid == null ? "" : agentInstance.aid);
            agentJson.put("state", state == null ? "" : state);
            agentJson.put("instantiated", DateUtils.toRfcString(timeInstantiated));
            agentJson.put("updated", timeUpdated > 0 ? DateUtils.toRfcString(timeUpdated) : "");
            return agentJson;
        } catch (JSONException e) {
            e.printStackTrace();
            ;
            throw new AgentServerException("JSON exception in Message.toJson" + e.getMessage());
        }
    }

    static public GroupAgentInstance fromJson(AgentServer agentServer, ServerGroup group,AgentInstance agentInstance, String agentJson) throws JSONException, AgentServerException {
        return fromJson(agentServer, group,agentInstance, new JSONObject(agentJson), false);
    }
    static public GroupAgentInstance fromJson(AgentServer agentServer, ServerGroup group,AgentInstance agentInstance,  JSONObject agentJson) throws AgentServerException {
        return fromJson(agentServer, group,agentInstance,agentJson, false);
    }
    static public GroupAgentInstance fromJson(AgentServer agentServer, ServerGroup group,AgentInstance agentInstance, JSONObject agentJson,  Boolean update) throws AgentServerException {
        log.info("If we have the user, ignore user from JSON");

        // If we have the group, ignore group from JSON
        if (group == null) {
            String groupname = agentJson.optString("group");
            if (groupname == null || groupname.trim().length() == 0)
                throw new AgentServerException("group name is missing");

        }
        // If we have the agentInstance, ignore agentInstance from JSON
        if (agentInstance == null) {
            String agentInstancename = agentJson.optString("agentInstance");
            if (agentInstancename == null || agentInstancename.trim().length() == 0)
                throw new AgentServerException(" agentInstance is missing");

        }
        // Parse the state
        String ServerState= agentJson.optString("state", null);
        if (!update && (ServerState == null) || ServerState.trim().length() == 0) {
            ServerState = "";
        }
        // Parse creation and modification timestamps
        String created = agentJson.optString("instantiated", null);
        long timeInstantiated = -1;
        try {
            timeInstantiated = created != null ? DateUtils.parseRfcString(created) : -1;
        } catch (ParseException e) {
            throw new AgentServerException("Unable to parse created date ('" + created + "') - " + e.getMessage());
        }
        String modified = agentJson.optString("updated", null);
        long timeUpdated = -1;
        try {
            timeUpdated = modified != null ? (modified.length() > 0 ? DateUtils.parseRfcString(modified) : 0) : -1;
        } catch (ParseException e) {
            throw new AgentServerException("Unable to parse updated date ('" + modified + "') - " + e.getMessage());
        }
        JsonUtils.validateKeys(agentJson, "Group Agent", new ArrayList<String>(Arrays.asList("id","group", "agentInstance", "state","instantiated","updated")));
        GroupAgentInstance groupAgentInstance = new GroupAgentInstance(agentServer, group, agentInstance, ServerState, timeInstantiated, timeUpdated,false);
        return groupAgentInstance;
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
