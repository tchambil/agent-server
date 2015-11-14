package dcc.agent.server.service.ACL;

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

    static public void sendRemote(AgentServer agentServer,ACLMessage message) throws AgentServerException {
        log.info("Initialize the aclmessage for send");
        // Initialize the agent send,
        AgentInstance agentInstance = agentServer.getAgentInstanceId(message.receivers);
        String webService = getAddresss(agentInstance);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(message.toJson().toString(), headers);
        if (message.performative.getMethod() == String.valueOf(HttpMethod.GET)) {
        } else if (message.performative.getMethod() == String.valueOf(HttpMethod.POST)) {
            ResponseEntity<String> response = restTemplate.exchange(webService, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
              log.info("Send message for method POST was successfu");
            }
        } else if (message.performative.getMethod() == String.valueOf(HttpMethod.PUT)) {
        } else {
            log.info("nothing exist method HTTP");
        }

    }
    static public void sendlocal(AgentServer agentServer, ACLMessage message) throws AgentServerException, JSONException {
        ACLMessage newmessage = agentServer.addAgentMessage(null, message.toJson());
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
    static public void send(AgentServer agentServer, ACLMessage message) throws AgentServerException, JSONException {
        AgentInstance agentInstance = agentServer.getAgentInstanceId(message.receivers);
        if (agentInstance != null) {
            if (agentInstance.type.equals("remote")) {
                 sendRemote(agentServer,message);
            }
            else{
                 sendlocal(agentServer, message);
            }
        }
    }
    static public void send(AgentServer agentServer, ACLMessage message, Boolean delegate) throws AgentServerException, JSONException {
        AgentInstance agentInstance = agentServer.getAgentInstanceId(message.receivers);
        if (agentInstance != null) {
            if (agentInstance.type.equals("remote")) {
                sendRemote(agentServer,message);
            }
            else{
                sendlocal(agentServer, message);
            }
        }
    }

    public static void onMessage(AgentServer agentServer, ACLMessage message) throws Exception {
        if ((message != null)) {
            ACLMessage reply = message.createReply(agentServer);
            if (message.getPerformative() == Performative.REQUEST) {
                String content = message.getContent();
                if (content != null) {
                    log.info("Agent " + message.getReceivers() + " - Received Request from " + message.getSender());
                    reply.setPerformative(Performative.INFORM);
                    reply.setStatus("response");
                    reply.setInReplyTo(content);
                    reply.setReplyBy(message.getReceivers());

                    if  (true){
                        reply.setContent("Nothing Result or Unexpected request");
                    }
                } else {
                    log.info("Agent " + message.getReceivers() + " - Unexpected request [" + content + "] received from " + message.getSender());
                    reply.setPerformative(Performative.REFUSE);
                    reply.setContent("( UnexpectedContent (" + content + "))");
                }
            } else {
                reply.setPerformative(Performative.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act " + (message.getPerformative()) + ") )");
            }
            AgentSender.send(agentServer, reply);
        }
    }
}
