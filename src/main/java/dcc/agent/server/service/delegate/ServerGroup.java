package dcc.agent.server.service.delegate;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
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
    static final Logger log=Logger.getLogger(ServerGroup.class);
    public AgentServer agentServer;
    public String uri;
    public String HostName;
    public String ServerIp;
    public String ServerName;
    public AgentServerProperties agentServerProperties;
    public ServerGroup(AgentServer agentServer,String uri, String HostName, String ServerIp, String ServerName)
    {
        this.agentServer=agentServer;
        this.uri=uri;
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
          messageJson.put("uri",uri==null ?"": uri);
          messageJson.put("HostName",HostName==null ?"":HostName);
          messageJson.put("ServerIp",ServerIp==null ?"":ServerIp);
          messageJson.put("ServerName",ServerName==null ?"":ServerName);
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
        if(serverGroup.uri!=null)
        {
            this.uri=serverGroup.uri;
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
    static public ServerGroup fromJson(AgentServer agentServer, JSONObject agentJson, Boolean update) throws AgentServerException {
        log.info("If we have the user, ignore user from JSON");
        // Parse the message sender
        String ServerUri=agentJson.optString("uri",null);
        if(!update &&(ServerUri==null) || ServerUri.trim().length()==0)
        {
            ServerUri="";
        }
        String ServerHostName=agentJson.optString("HostName",null);
        if(!update &&(ServerHostName==null) || ServerHostName.trim().length()==0)
        {
            ServerHostName="";
        }
        String ServerServerIp=agentJson.optString("ServerIp",null);
        if(!update &&(ServerServerIp==null) || ServerServerIp.trim().length()==0)
        {
            ServerServerIp="";
        }

        String ServerServerName=agentJson.optString("ServerName",null);
        if(!update &&(ServerServerName==null) || ServerServerName.trim().length()==0)
        {
            ServerServerName="";
        }
        JsonUtils.validateKeys(agentJson,"Server Group", new ArrayList<String>(Arrays.asList("uri","HostName", "ServerIp","ServerIp","ServerName")));

        ServerGroup serverGroup =new ServerGroup(agentServer, ServerUri, ServerHostName, ServerServerIp, ServerServerName);
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
