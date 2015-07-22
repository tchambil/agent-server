package dcc.agent.server.service.agentserver;

import dcc.agent.server.service.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by teo on 21/07/15.
 */
public class AgentMessage {
    public String name;
    public String interval;


    public AgentMessage(String name, String interval) {
        this.name = name;
        this.interval = interval;

    }

    static public AgentMessage fromJson(String timerString) throws JSONException, AgentServerException {
        return fromJson(new JSONObject(timerString));
    }

    static public AgentMessage fromJson(JSONObject conditionJson) throws AgentServerException {
        // TODO: Whether empty fields should be null or empty strings
        String name = conditionJson.optString("name", "");
        String interval = conditionJson.optString("interval");
         JsonUtils.validateKeys(conditionJson, "Agent condition", new ArrayList<String>(Arrays.asList(
                "name", "interval")));
        return new AgentMessage(name, interval);
    }

    public long getInterval(AgentInstance agentInstance) throws AgentServerException {
        return agentInstance.evaluateExpressionLong(interval);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject timerJson = new JSONObject();
        timerJson.put("name", name);
        timerJson.put("interval", interval);
        return timerJson;
    }

    public String toString() {
        return name + ": " + interval + " ms. ";
    }

}
