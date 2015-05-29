package dcc.agent.server.service.delegate;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.util.JsonListMap;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by teo on 28/05/15.
 */
public class AgentDelegate {
    protected static Logger log = Logger.getLogger(AgentDelegate.class);
    public AgentServer agentServer;
    public AgentMessage agentMessage;

    public AgentDelegate(AgentServer agentServer)
    {
        this.agentServer=agentServer;

    }

    static public ResponseEntity<String>  doPost(AgentMessage agentMessage, String uri) throws AgentServerException, JSONException {
        log.info("Initialize the agent delegate for send agent message");
        // Initialize the agent delegate,
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity =new HttpEntity<String>(agentMessage.toString(),headers);
        ResponseEntity<String> response=restTemplate.exchange(uri, HttpMethod.POST,entity,String.class);
        if(response.getStatusCode()==HttpStatus.OK)
        {
            log.info("successful send agent message and prepare save local");

        }
        return response;
    }

   public ResponseEntity<String> readMessage(AgentMessage agentMessage) throws JSONException, AgentServerException
   {
       String uri="http://"+ agentMessage.replyTo + "/users/test-user-1/message";
       ResponseEntity<String> response=null;
        if(agentMessage.sender.equals("0001"))
        {
             response=doPost(agentMessage, uri);

        }
        return response;
    }

    public JSONObject toJson(AgentMessage agentMessage) throws AgentServerException {
        try {
            JSONObject messageJson = new JsonListMap();
            messageJson.put("user",  agentMessage.user.id);
            messageJson.put("sender",  agentMessage.sender == null ? "" : agentMessage.sender);
            messageJson.put("receiver",  agentMessage.receiver == null ? "":  agentMessage.receiver);
            messageJson.put("replyTo",  agentMessage.replyTo == null ? "" :  agentMessage.replyTo);
            messageJson.put("conversationId",  agentMessage.conversationId == null ? "" :  agentMessage.conversationId);
            messageJson.put("content",  agentMessage.content == null ? "" :  agentMessage.content);
            messageJson.put("lenguage",  agentMessage.lenguaje == null ? "" :  agentMessage.lenguaje);
            messageJson.put("encoding",  agentMessage.enconding == null ? "" :  agentMessage.enconding);
            messageJson.put("ontology",  agentMessage.ontology == null ? "" :  agentMessage.ontology);
            messageJson.put("protocol",  agentMessage.protocol == null ? "" :  agentMessage.protocol);
            messageJson.put("replyWith",  agentMessage.replyWith == null ? "" :  agentMessage.replyWith);
            messageJson.put("inReplyTo",  agentMessage.inReplyTo == null ? "" :  agentMessage.inReplyTo);
            messageJson.put("replyBy",  agentMessage.replyBy == null ? "" : agentMessage. replyBy);

            return messageJson;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            throw new AgentServerException("JSON exception in Message.toJson -"+e.getMessage());
        }
    }
}

