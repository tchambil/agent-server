package dcc.agent.server.service.delegate;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.field.Field;
import dcc.agent.server.service.field.FieldList;
import dcc.agent.server.service.script.intermediate.SymbolManager;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.JsonUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by teo on 01/06/15.
 */
public class ServerGroup {
    static final Logger log=Logger.getLogger(ServerGroup.class);
    public AgentServer agentServer;
    public String Url;
    public FieldList agentNames;
    public String HostName;
    public String ServerIp;
    public String ServerName;
    public AgentServerProperties agentServerProperties;
    public ServerGroup(AgentServer agentServer, FieldList agentNames, String url,String HostName, String ServerIp, String ServerName, boolean update)
    {
        this.agentServer=agentServer;
        this.Url=url;
        this.agentNames = agentNames;
        this.HostName=HostName;
        this.ServerIp=ServerIp;
        this.ServerName=ServerName;
    }
    public JSONObject toJson() throws AgentServerException {
        return toJson(true);
    }
    public JSONObject toJson(boolean includeState) throws AgentServerException {
        return toJson(includeState,-1);
    }
    public JSONObject toJson(boolean includeState, int StateCount) throws AgentServerException {
      try {
          JSONObject messageJson =new JsonListMap();
          messageJson.put("uri",Url==null ?"": Url);
          messageJson.put("HostName",HostName==null ?"":HostName);
          messageJson.put("ServerIp",ServerIp==null ?"":ServerIp);
          messageJson.put("ServerName",ServerName==null ?"":ServerName);
          // Output agents
          JSONArray detailArrayJson = new JSONArray();
          if (agentNames != null)
              for (Field field : agentNames)
                  detailArrayJson.put(field.toJson());
          messageJson.put("agentNames", detailArrayJson);

       return messageJson;
      }
      catch (JSONException e)
      {
          e.printStackTrace();;
          throw  new AgentServerException("JSON exception in Message.toJson"+e.getMessage());
      }
    }
    public void update(AgentServer agentServer, ServerGroup serverGroup)
    {
        if(serverGroup.Url!=null)
        {
            this.Url=serverGroup.Url;
        }
        if(serverGroup.HostName!=null)
        {
            this.HostName=serverGroup.HostName;
        }
        if(serverGroup.ServerIp!=null)
        {
            this.ServerIp=serverGroup.ServerIp;
        }
        if(serverGroup.ServerName!=null)
        {
            this.ServerName=serverGroup.ServerName;
        }
    }
    static public ServerGroup fromJson(AgentServer agentServer, String agentJsonSource) throws JSONException, AgentServerException {
        return fromJson(agentServer,new JSONObject(agentJsonSource),false);
    }
    static public ServerGroup fromJson(AgentServer agentServer, JSONObject agentJson) throws AgentServerException {
        return fromJson(agentServer, agentJson, false);
    }
    static public ServerGroup fromJson(AgentServer agentServer, JSONObject serverJson, Boolean update) throws AgentServerException {
        log.info("If we have the user, ignore user from JSON");
        // Parse 'notifications' list

        FieldList agentNames = null;
        if (serverJson.has("agentNames")) {
            SymbolManager symbolManager = new SymbolManager();
            JSONArray detailJson = serverJson.optJSONArray("agentNames");
            agentNames = new FieldList();
            int numdetail = detailJson.length();
            for (int i = 0; i < numdetail; i++) {
                JSONObject outputJson = detailJson.optJSONObject(i);
                Field field = Field.fromJsonx(symbolManager.getSymbolTable("agentNames"), outputJson);
                // TODO: give error for dup names
                agentNames.add(field);
            }
        }

        // Parse the message sender
        String ServerUri=serverJson.optString("uri",null);
        if(!update &&(ServerUri==null) || ServerUri.trim().length()==0)
        {
            ServerUri="";
        }
        String ServerHostName=serverJson.optString("HostName",null);
        if(!update &&(ServerHostName==null) || ServerHostName.trim().length()==0)
        {
            ServerHostName="";
        }
        String ServerServerIp=serverJson.optString("ServerIp",null);
        if(!update &&(ServerServerIp==null) || ServerServerIp.trim().length()==0)
        {
            ServerServerIp="";
        }

        String ServerServerName=serverJson.optString("ServerName",null);
        if(!update &&(ServerServerName==null) || ServerServerName.trim().length()==0)
        {
            ServerServerName="";
        }
        JsonUtils.validateKeys(serverJson,"Server Group", new ArrayList<String>(Arrays.asList("agentNames","uri","HostName", "ServerIp","ServerIp","ServerName")));

        ServerGroup serverGroup =new ServerGroup(agentServer, agentNames, ServerUri, ServerHostName, ServerServerIp, ServerServerName, false);
        return  serverGroup;
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
