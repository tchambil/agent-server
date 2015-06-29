package dcc.agent.server.service.communication;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.util.NameValue;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by teo on 28/06/15.
 */
public class MessageManager {

    static final Logger log = Logger.getLogger(MessageManager.class);
    static public synchronized ACLMessage receive(AgentServer agentServer) {
        ACLMessage message = null;
        for (NameValue<ACLMessageList> messageListNameValue : agentServer.agentMessages) {
            // Get all  ACLmessage
            for (ACLMessage agentMessage : agentServer.agentMessages
                    .get(messageListNameValue.name)) {
                if (agentMessage.sender.equals("enable")) {
                    message = agentMessage;
                }
            }
        }
        return message;
    }
    static public synchronized ACLMessage receive(AgentServer agentServer,String messageId) {
        ACLMessageList messageListNameValue = agentServer.agentMessages.get(messageId);
        ACLMessage agentMessage = messageListNameValue.get(messageId);
        return agentMessage;
    }
    static public void send(AgentServer agentServer,ACLMessage message) throws AgentServerException, JSONException {
        AgentInstance agentInstance = agentServer.getAgentInstances(message.sender);
        if (agentInstance.type.equals("remote")) {
            SendRemote(message, agentInstance);
        } else {
            sendlocal(agentServer,message);
        }
    }
    static public void SendRemote(ACLMessage message, AgentInstance agentInstance) throws AgentServerException {
        log.info("Initialize the aclmessage for send");
        // Initialize the agent send,
        String webService =getAddresss(agentInstance);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(message.toJson().toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(webService, HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            message.replyTo = response.getBody();
            log.info("successful send agent message and prepare save local");
        }

    }

    static public void sendlocal(AgentServer agentServer,ACLMessage message) throws AgentServerException, JSONException {
        ACLMessage agentMessage = agentServer.addAgentMessage(null, message.toJson());
    }
    private static String getAddresss(AgentInstance agentInstance){
        String webService = null;
        URL tempUrl = null;
        try {
            tempUrl = new URL(agentInstance.addresses.toString());
            String protocol = tempUrl.getProtocol();
            String host = tempUrl.getHost();
            int port = tempUrl.getPort();
            webService = protocol + "://" + host + (port > 0 ? ":" + port : "") + "/users/message";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
     return webService;
    }
}
