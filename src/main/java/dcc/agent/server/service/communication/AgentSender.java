package dcc.agent.server.service.communication;

import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by teo on 29/06/15.
 */
public class AgentSender {
    static final Logger log = Logger.getLogger(AgentSender.class);

    static public Boolean sendRemote(ACLMessage message, AgentInstance agentInstance) throws AgentServerException {
        log.info("Initialize the aclmessage for send");
        // Initialize the agent send,

        String webService = getAddresss(agentInstance);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(message.toJson().toString(), headers);
        if (message.performative.getMethod() == String.valueOf(HttpMethod.GET)) {
        }

        else if (message.performative.getMethod() == String.valueOf(HttpMethod.POST)) {
            ResponseEntity<String> response = restTemplate.exchange(webService, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return true;
            }
        }

        else if (message.performative.getMethod() == String.valueOf(HttpMethod.PUT)) {
        }
        else{
            return false;
        }



        return false;
    }

    static public Boolean sendlocal(AgentServer agentServer, ACLMessage message) throws AgentServerException, JSONException {
        ACLMessage newmessage=  agentServer.addAgentMessage(null, message.toJson());
        if(newmessage!=null)
        {
            if (newmessage.delegate.equals("true"));
            {
                return true;
            }
        }
        return null;
    }

    private static String getAddresss(AgentInstance agentInstance) {
        String webService = null;
        URL tempUrl = null;
        try {
            tempUrl = new URL(agentInstance.addresses.toString());
            String protocol = tempUrl.getProtocol();
            String host = tempUrl.getHost();
            int port = tempUrl.getPort();
            webService = protocol + "://" + host + (port > 0 ? ":" + port : "") + "/acl";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return webService;
    }

    static public Boolean send(AgentServer agentServer, ACLMessage message, Boolean delegate) throws AgentServerException, JSONException {
        AgentInstance agentInstance=null;
        if ((delegate)&&(message.delegate.toString().equals("true"))){
             agentInstance = agentServer.getAgentInstances(message.receivers);
        }
        else{
             agentInstance = agentServer.getAgentInstances(message.sender);
        }

        if (agentInstance.type.equals("remote")) {
           return sendRemote(message, agentInstance);
        } else {
            return sendlocal(agentServer, message);
        }
    }
}
