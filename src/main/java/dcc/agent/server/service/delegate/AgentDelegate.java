package dcc.agent.server.service.delegate;

import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.communication.ACLMessage;
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
    public ACLMessage agentMessage;

    public AgentDelegate(AgentServer agentServer)
    {
        this.agentServer=agentServer;

    }

    static public ResponseEntity<String>  doPost(ACLMessage aclMessage, String uri) throws AgentServerException, JSONException {
        log.info("Initialize the agent delegate for send agent message");
        // Initialize the agent delegate,
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity =new HttpEntity<String>(aclMessage.toString(),headers);
        ResponseEntity<String> response=restTemplate.exchange(uri, HttpMethod.POST,entity,String.class);
        if(response.getStatusCode()==HttpStatus.OK)
        {
            log.info("successful send agent message and prepare save local");

        }
        return response;
    }
    static public ACLMessage doDelegate(ACLMessage aclMessage, String uri) throws AgentServerException, JSONException {
        log.info("Initialize the agent delegate for send agent message");
        // Initialize the agent delegate,
        ACLMessage agentMessageR=null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity =new HttpEntity<String>(aclMessage.toString(),headers);
        ResponseEntity<String> response=restTemplate.exchange(uri, HttpMethod.POST,entity,String.class);
        if(response.getStatusCode()==HttpStatus.OK)
        {
            aclMessage.replyTo=response.getBody();
            log.info("successful send agent message and prepare save local");
            agentMessageR= aclMessage;
        }
        else
        {
            aclMessage.replyTo=response.getStatusCode().toString();
            agentMessageR= aclMessage;
        }
        return agentMessageR;
    }

   public ACLMessage readMessage(ACLMessage aclMessage) throws JSONException, AgentServerException
   {

      ACLMessage agentMessageR=null;

        if(!(aclMessage ==null))
        {
            String uri="http://"+ aclMessage.replyTo + "/agents/task";
            aclMessage.receivers= aclMessage.content.toString();
            agentMessageR=doDelegate(aclMessage, uri);
            }
        return agentMessageR;
    }

    public JSONObject toJson(ACLMessage aclMessage) throws AgentServerException {
        try {
            JSONObject messageJson = new JsonListMap();
             messageJson.put("sender",  aclMessage.sender == null ? "" : aclMessage.sender);
            messageJson.put("receiver",  aclMessage.receivers == null ? "":  aclMessage.receivers);
            messageJson.put("replyTo",  aclMessage.replyTo == null ? "" :  aclMessage.replyTo);
            messageJson.put("messageId",  aclMessage.conversationId == null ? "" :  aclMessage.conversationId);
            messageJson.put("content",  aclMessage.content == null ? "" :  aclMessage.content);
            messageJson.put("lenguage",  aclMessage.language == null ? "" :  aclMessage.language);
            messageJson.put("encoding",  aclMessage.enconding == null ? "" :  aclMessage.enconding);
            messageJson.put("ontology",  aclMessage.ontology == null ? "" :  aclMessage.ontology);
            messageJson.put("protocol",  aclMessage.protocol == null ? "" :  aclMessage.protocol);
            messageJson.put("replyWith",  aclMessage.replyWith == null ? "" :  aclMessage.replyWith);
            messageJson.put("inReplyTo",  aclMessage.inReplyTo == null ? "" :  aclMessage.inReplyTo);
            messageJson.put("replyBy",  aclMessage.replyBy == null ? "" : aclMessage. replyBy);
            messageJson.put("performative",  aclMessage.replyBy == null ? "" : aclMessage.performative);
            return messageJson;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            throw new AgentServerException("JSON exception in Message.toJson -"+e.getMessage());
        }
    }
}

