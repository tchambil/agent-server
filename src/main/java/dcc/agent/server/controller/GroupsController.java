package dcc.agent.server.controller;

import dcc.agent.server.service.ACL.ACLMessage;
import dcc.agent.server.service.ACL.AgentSender;
import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.appserver.AgentAppServerBadRequestException;
import dcc.agent.server.service.appserver.AgentAppServerException;
import dcc.agent.server.service.groups.GroupAgentInstance;
import dcc.agent.server.service.groups.GroupAgentInstanceList;
import dcc.agent.server.service.groups.ServerGroup;
import dcc.agent.server.service.groups.ServerGroupList;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.NameValue;
import dcc.agent.server.service.util.Utils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teo on 12/1/2015.
 */
@RestController
public class GroupsController {
    public static AgentServer agentServer;
    protected static Logger logger = Logger.getLogger(GroupsController.class);
    public Utils util = new Utils();
    @RequestMapping(value = "/groups/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postServerGroup(@PathVariable String name, HttpServletRequest request) throws Exception {
        User user = agentServer.users.get("test-user-1");

        if (name == null) {
            throw new AgentAppServerBadRequestException("Missing group name path parameter");
        }
        if (name.trim().length() == 0) {
            throw new AgentAppServerBadRequestException("Empty group name path parameter");
        }
        if (!agentServer.serverGroups.get(user.id).containsKey(name)) {
            throw new AgentAppServerException(HttpServletResponse.SC_FOUND, "No group with that name for that user");
        }
        ServerGroupList serverGroupL = agentServer.serverGroups.get(user.id);
        ServerGroup serverGroup = serverGroupL.get(name);

        JSONObject serverJson = util.getJsonRequest(request);
        JSONArray ListNew = new JSONArray();
        if (serverGroup != null) {
            ListNew = (JSONArray) serverJson.get("agents");
        }

        if (serverJson == null)
            throw new AgentAppServerBadRequestException(
                    "Invalid ServerGroup JSON object");

        GroupAgentInstanceList groupMap = agentServer.groupAgents.get(name);
        JSONArray jsonArray = new JSONArray();
        JSONArray List = new JSONArray();
        if (groupMap != null) {
            GroupAgentInstance group = groupMap.get(name);
            JSONObject jsonObject = group.toJson();
            List = (JSONArray) jsonObject.get("agents");
            agentServer.removeGroupAgentInstance(name);
        }
        for (int i = 0; i < List.length(); i++) {
            JSONObject item = List.getJSONObject(i);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("aid", item.getString("aid"));
            jsonObj.put("user", item.getString("user"));
            jsonArray.put(jsonObj);
        }
        for (int i = 0; i < ListNew.length(); i++) {
            JSONObject item = ListNew.getJSONObject(i);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("aid", item.getString("aid"));
            jsonObj.put("user", item.getString("user"));
            jsonArray.put(jsonObj);
        }
        JSONObject chairJSON = new JSONObject();
        chairJSON.put("state", "active");
        chairJSON.put("agents", jsonArray);

        GroupAgentInstance groupAgentInstance = agentServer.addGroupAgentInstance(serverGroup, chairJSON);
        JSONObject message = new JSONObject();
        message.put("Group Agent", "Add was successful");
        return message.toString();
    }
    @RequestMapping(value = "/group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postServerGroup( HttpServletRequest request) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get("test-user-1");
        JSONObject serverJson = util.getJsonRequest(request);
        if (serverJson == null)
            throw new AgentAppServerBadRequestException(
                    "Invalid ServerGroup JSON object");
        // Parse and add the Server Group
        ServerGroup serverGroup = agentServer.addServerGroup(user, serverJson);
        // Done
        JSONObject message = new JSONObject();
        message.put("message", "Add was successful");
        return message.toString();
    }
    @RequestMapping(value = "/group/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAgents( @PathVariable String name) throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        logger.info("Getting list of all agents for a group");
        GroupAgentInstanceList groupMap = agentServer.groupAgents.get(name);
        GroupAgentInstance group = groupMap.get(name);
        return group.toJson().toString();

    }
    @RequestMapping(value = "/groupgeneral", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getgroupgeneral() throws Exception {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        JSONArray GroupArrayJson = new JSONArray();
        for (NameValue<ServerGroupList> groupListNameValue : agentServer.serverGroups) {
            // Get all  serverGroup
            for (ServerGroup serverGroup : agentServer.serverGroups
                    .get(groupListNameValue.name)) {
                // Generate JSON for short summary of serverGroup
                JSONObject groupJson = new JsonListMap();
                groupJson.put("name", serverGroup.name);
                groupJson.put("description", serverGroup.description);
                groupJson.put("type", serverGroup.type);
                groupJson.put("creator", serverGroup.user.id);
                GroupArrayJson.put(groupJson);
            }
        }
        JSONObject groupJson = new JSONObject();
        groupJson.put("ServerGroup", GroupArrayJson);
        return groupJson.toString();
    }
    @RequestMapping(value = "/groupsname", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getGroupsname(HttpServletRequest request) throws Exception {
        // String url="http://dbpedias.cloudapp.net/group";
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        JSONObject configJson = util.getJsonRequest(request);
        String uri = configJson.optString("server");
        String group = configJson.optString("group");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri+"/group/"+group, String.class);
        JSONObject jsonObject=new JSONObject(result);
        return jsonObject.toString();

    }
    @RequestMapping(value = "/groups", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getGroups(HttpServletRequest request) throws Exception {
        // String url="http://dbpedias.cloudapp.net/group";
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        JSONObject configJson = util.getJsonRequest(request);
        String uri = configJson.optString("server");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri+"/group", String.class);
        JSONObject jsonObject=new JSONObject(result);
        return jsonObject.toString();
    }
    @RequestMapping(value = "/suscribe", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String suscribe(HttpServletRequest request) throws Exception {
        // String url="http://dbpedias.cloudapp.net/group";
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        User user = agentServer.users.get("test-user-1");
        JSONObject configJson = util.getJsonRequest(request);
        String uri = configJson.optString("server");
        String group = configJson.optString("group");
        String agent = configJson.optString("agent");
        AgentInstance SuscriptorAgent= agentServer.getAgentInstanceId(agent);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri+"/group/"+group, String.class);
        JSONObject jsonObject=new JSONObject(result);
        JSONArray List = new JSONArray();
        List = (JSONArray) jsonObject.get("agents");
        for (int i = 0; i < List.length(); i++) {
            JSONObject item = List.getJSONObject(i);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("aid", item.getString("aid"));
            jsonObj.put("name", item.getString("name"));
            jsonObj.put("host", item.getString("host"));
            jsonObj.put("addresses", item.getString("addresses"));
            jsonObj.put("status", item.getString("status"));
            jsonObj.put("type","remote");
            jsonObj.put("description",item.getString("description"));
            jsonObj.put("definition",item.getString("definition"));
            AgentInstance RegisterAgent = agentServer.addAgentInstance(user,jsonObj);
            ACLMessage aclMessage =agentServer.addSuscribeAgent(SuscriptorAgent, RegisterAgent);
            AgentSender.send(agentServer, aclMessage, true);
        }
        return jsonObject.toString();
    }
    @RequestMapping(value = "/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getServerGroup() throws JSONException {
        PlataformController plataform = new PlataformController();
        agentServer = plataform.getAgentServer();
        JSONObject groupJson = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        // Get all serverGroup
        for (NameValue<ServerGroupList> groupListNameValue : agentServer.serverGroups) {
            // Get all  serverGroup
            for (ServerGroup serverGroup : agentServer.serverGroups
                    .get(groupListNameValue.name)) {
                JSONObject chairJSON = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                GroupAgentInstanceList groupMap = agentServer.groupAgents.get(serverGroup.name);
                if (groupMap != null) {
                    GroupAgentInstance group = groupMap.get(serverGroup.name);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = group.toJson();
                        JSONArray List = new JSONArray();
                        List = (JSONArray) jsonObject.get("agents");
                        for (int i = 0; i < List.length(); i++) {
                            JSONObject item = List.getJSONObject(i);
                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("aid", item.getString("aid"));
                            jsonObj.put("name", item.getString("name"));
                            jsonObj.put("host", item.getString("host"));
                            jsonObj.put("Addresses", item.getString("addresses"));
                            jsonObj.put("status", item.getString("status"));
                            jsonObj.put("type",item.getString("type"));
                            jsonObj.put("description",item.getString("description"));
                            jsonArray.put(jsonObj);
                        }
                        chairJSON.put("group",serverGroup.name);
                        chairJSON.put("agents", jsonArray);
                        jsonArray2.put(chairJSON);
                    } catch (AgentServerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        groupJson.put("groups", jsonArray2);
        return groupJson.toString();
    }
}
