package dcc.agent.server.service.agentserver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by teo on 11/14/2015.
 */
public class NautiLOD {
       public String id;
        public String receiver;
        public String uri;

        public NautiLOD(String id, String receiver, String uri) throws AgentServerException {
            this.id = id;
            this.uri = uri;
            this.receiver=receiver;
        }
      static public NautiLOD fromJson(String ResultString) throws JSONException, AgentServerException {
            return fromJson(new JSONObject(ResultString));
        }

        static public NautiLOD fromJson(JSONObject ResultString) throws AgentServerException {
         String id = ResultString.optString("id", "");
         String uri = ResultString.optString("uri", "");
         String receiver = ResultString.optString("receiver", "");
         return new NautiLOD(id, receiver,uri);
        }

        public JSONObject toJson() throws JSONException {
            JSONObject ResultJson = new JSONObject();
            ResultJson.put("id", id);
            ResultJson.put("receiver", receiver);
            ResultJson.put("uri", uri);
            return ResultJson;
        }
    public JSONObject ReturnNodeJson(int group) throws JSONException {
        JSONObject ResultJson = new JSONObject();
        ResultJson.put("id", id);
         ResultJson.put("label", uri);
        ResultJson.put("group", group);
        return ResultJson;
    }
    public JSONObject ReturnedgesJson() throws JSONException {
        JSONObject ResultJson = new JSONObject();
        ResultJson.put("from", id);
        ResultJson.put("to", -1);
        return ResultJson;
    }
        public String toString() {
            return id + ": "  + receiver+ ": "  + uri;
        }


}
