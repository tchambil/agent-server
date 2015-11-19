package dcc.agent.server.service.agentserver;

import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.JsonUtils;
import dcc.agent.server.service.util.NameValue;
import dcc.agent.server.service.util.NameValueList;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by teo on 11/14/2015.
 */
public class NautiLODResult {

    static final Logger log = Logger.getLogger(NautiLODResult.class);
    public String name;
    private NameValueList<NautiLOD> result;
    public AgentServer agentServer;

    public NautiLODResult(AgentServer agentServer, String name, NameValueList<NautiLOD> result){
    this.agentServer = agentServer;
    this.name=name;
    this.result=result;
    }
    public void update(AgentServer agentServer, NautiLODResult result) throws JSONException, AgentServerException {
        if (result.name != null) {
            this.name = result.name;
        }
        if (result.result != null) {
            this.result=result.result;
        }
        agentServer.persistence.put(this);
    }
    public JSONObject toJson() throws AgentServerException {
        return toJson(true);
    }

    public JSONObject toJson(boolean includeState) throws AgentServerException {
        return toJson(includeState, -1);
    }
    public JSONObject toJson(boolean includeState, int stateCount) throws AgentServerException {
        try {
            JSONObject resultJson = new JsonListMap();
            resultJson.put("name", name == null ? "" : name);
            JSONArray scriptsArrayJson = new JSONArray();
            for (NameValue<NautiLOD> scriptNameValue :result)
                scriptsArrayJson.put(scriptNameValue.value.toJson());
            resultJson.put("result", scriptsArrayJson);

            return resultJson;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new AgentServerException("JSON exception in Message.toJson -" + e.getMessage());
        }
    }
    public JSONObject ReturnJson() throws AgentServerException {
        try {
            JSONObject resultJson = new JsonListMap();
            resultJson.put("name", name == null ? "" : name);
            JSONArray scriptsArrayJson = new JSONArray();
            JSONArray scriptsArrayJson2 = new JSONArray();
            for (NameValue<NautiLOD> scriptNameValue :result){
                scriptsArrayJson.put(scriptNameValue.value.ReturnNodeJson());
                scriptsArrayJson2.put(scriptNameValue.value.ReturnedgesJson());
            }
            resultJson.put("nodes", scriptsArrayJson);
            resultJson.put("edges", scriptsArrayJson2);
            return resultJson;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new AgentServerException("JSON exception in Message.toJson -" + e.getMessage());
        }
    }

    static public NautiLODResult fromJson(AgentServer agentServer, String agentJsonSource) throws AgentServerException, JSONException {
        return fromJson(agentServer,  new JSONObject(agentJsonSource), false);
    }
    static public NautiLODResult fromJson(AgentServer agentServer, JSONObject agentJson) throws AgentServerException, JSONException {
        return fromJson(agentServer, agentJson, false);
    }


    static synchronized public NautiLODResult fromJson(AgentServer agentServer, JSONObject ResultJson, boolean update) throws AgentServerException, JSONException {
        log.info("If we have the user, ignore user from JSON");
        String resultname = ResultJson.optString("name", null);
        if ((resultname == null) || resultname.trim().length() == 0) {
            resultname = "";
        }
        NameValueList<NautiLOD> resultNautiLod = null;
        if (ResultJson.has("result")) {
            resultNautiLod = new NameValueList<NautiLOD>();
            JSONArray resultJson = ResultJson.optJSONArray("result");
            if (resultJson != null) {
                int numScripts = resultJson.length();
                for (int i = 0; i < numScripts; i++) {
                  JSONObject scriptJson = resultJson.optJSONObject(i);
                   if (!scriptJson.keys().hasNext())
                        continue;
                    NautiLOD result = NautiLOD.fromJson(scriptJson);
                    resultNautiLod.put(result.id, result);
                }
            }
        }
        JsonUtils.validateKeys(ResultJson, "result", new ArrayList<String>(Arrays.asList(
                "name","result")));
        NautiLODResult resultN = new NautiLODResult(agentServer,  resultname,resultNautiLod);
        // Return the new agent instance
        return resultN;
    }
    public String toString() {
        try {
            return toJson().toString();
        } catch (AgentServerException e) {
            log.info("Unable to output AgentMessage as string - " + e.getMessage());
            e.printStackTrace();
            return "[AgentMessage: Unable to output AgentMessage as string - " + e.getMessage();
        }
    }
  }
