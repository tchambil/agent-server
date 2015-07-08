package dcc.agent.server.service.communication;


import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.JsonUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by teo on 27/05/15.
 */
public class ACLMessage implements Serializable{
    static final Logger log= Logger.getLogger(ACLMessage.class);
    private static final long serialVersionUID=1L;
    public AgentServer agentServer;
   public String sender;
    public String receivers;
    public String replyTo;
    public String conversationId;
    public StringBuffer content;
    public String language;
    public String enconding;
    public String ontology;
    public String protocol;
    public String replyWith;
    public String inReplyTo;
    public String replyBy;
    public String status;
    public Boolean update;
    public Boolean delegate;
    public Performative performative;
    public AgentServerProperties agentServerProperties;
    public ACLMessage(){
        this(Performative.NOT_UNDERSTOOD);
    }
    public ACLMessage(Performative performative){
        this.performative=performative;
     }

    private boolean canReplyTo() {
        return sender!=null || replyTo!=null;
    }

    public ACLMessage(AgentServer agentServer,
                      Performative performative,
                      String sender,
                      String receivers,
                      String replyTo,
                      String conversationId,
                      StringBuffer content,
                      String language,
                      String enconding,
                      String ontology,
                      String protocol,
                      String replyWith,
                      String inReplyTo,
                      String replyBy,
                      String status,
                      Boolean update,
                      Boolean delegate
    )

    {
        this.performative=performative;
        this.delegate=delegate;
        this.update=update;
        this.agentServer = agentServer;
        this.sender=sender;
        this.receivers=receivers;
        this.replyTo=replyTo;
        this.conversationId =conversationId;
        this.content=content;
        this.language=language;
        this.enconding=enconding;
        this.ontology=ontology;
        this.protocol=protocol;
        this.replyWith=replyWith;
        this.inReplyTo=inReplyTo;
        this.replyBy=replyBy;
        this.status=status;



    }
    public ACLMessage createReply(AgentServer agentServer) throws JSONException, AgentServerException {
        if(!canReplyTo()){
            throw new IllegalArgumentException("There's no-one to receive the reply.");
        }
        ACLMessage m = new ACLMessage(getPerformative());
       // m.receivers=((replyTo != null ||(!(replyTo.equals("")))) ? replyTo:sender);
        m.receivers=(!(replyTo.equals("")) ? replyTo:sender);
        m.setSender(getReceivers());
        m.setReplyTo(getReplyTo());
        m.setlanguage(getlanguage());
        m.setOntology(getOntology());
        m.setProtocol(getProtocol());
        m.setInReplyTo(getInReplyTo());
        m.setReplyWith(getReplyWith() + java.lang.System.currentTimeMillis());
        m.setconversationId(getconversationId());
        m.setDelegate(false);
        updateMessage(agentServer, m.conversationId);
        return m;
    }

   public JSONObject toJson() throws AgentServerException {
        return toJson(true);
    }

    public JSONObject toJson(boolean includeState) throws AgentServerException {
        return toJson(includeState, -1);
    }

    public JSONObject toJson(boolean includeState, int stateCount) throws AgentServerException {
      try {
          JSONObject messageJson = new JsonListMap();
          messageJson.put("sender", sender == null ? "" : sender);
          messageJson.put("receiver", receivers == null ? "": receivers);
          messageJson.put("replyTo", replyTo == null ? "" : replyTo);
          messageJson.put("conversationId", conversationId == null ? "" : conversationId);
          messageJson.put("content", content == null ? "" : content);
          messageJson.put("language", language == null ? "" : language);
          messageJson.put("encoding", enconding == null ? "" : enconding);
          messageJson.put("ontology", ontology == null ? "" : ontology);
          messageJson.put("protocol", protocol == null ? "" : protocol);
          messageJson.put("replyWith", replyWith == null ? "" : replyWith);
          messageJson.put("inReplyTo", inReplyTo == null ? "" : inReplyTo);
          messageJson.put("replyBy", replyBy == null ? "" : replyBy);
          messageJson.put("status", status == null ? "" : status);
          messageJson.put("delegate", delegate == null ? "" : delegate);
          messageJson.put("performative", performative == null ? "" : performative);
          return messageJson;
      }
      catch (JSONException e)
      {
          e.printStackTrace();
          throw new AgentServerException("JSON exception in Message.toJson -"+e.getMessage());
      }
    }
    public void updateMessage(AgentServer agentServer, String messageId) throws AgentServerException, JSONException {
        ACLMessageList messageListNameValue = agentServer.agentMessages.get(messageId);
        ACLMessage agentMessage = messageListNameValue.get(messageId);
        agentMessage.setStatus("read");
        ACLMessage newagentMessage= ACLMessage.fromJson(agentServer, null, agentMessage.toJson(), true);
        agentMessage.update(agentServer, newagentMessage);
    }
    public void update(AgentServer agentServer, ACLMessage message) throws JSONException, AgentServerException {
        if(message.sender!=null)
        {
            this.sender=message.sender;
        }
        if(message.receivers!=null)
        {
            this.receivers=message.receivers;
        }
        if(message.replyTo!=null)
        {
            this.replyTo=message.replyTo;
        }
        if(message.conversationId !=null)
        {
            this.conversationId =message.conversationId;
        }
        if(message.content!=null)
        {
            this.content=message.content;
        }
        if(message.language!=null)
        {
            this.language=message.language;
        }
        if(message.enconding!=null)
        {
            this.enconding=message.enconding;
        }
        if(message.ontology!=null)
        {
            this.ontology=message.ontology;
        }

        if(message.protocol!=null)
        {
            this.protocol=message.protocol;
        }
        if(message.replyWith!=null)
        {
            this.replyWith=message.replyWith;
        }
        if(message.inReplyTo!=null)
        {
            this.inReplyTo=message.inReplyTo;
        }
        if(message.replyBy!=null) {
            this.replyBy = message.replyBy;
        }
        if(message.delegate!=null) {
            this.delegate = message.delegate;
        }
        if(message.status!=null) {
            this.status = message.status;
        }
        agentServer.persistence.put(this);
    }

    static public ACLMessage fromJson(AgentServer agentServer, String agentJsonSource) throws AgentServerException, JSONException {
        return fromJson(agentServer, null, new JSONObject(agentJsonSource), false);
    }

    static public ACLMessage fromJson(AgentServer agentServer, User user, JSONObject agentJson) throws AgentServerException, JSONException {
        return fromJson(agentServer, user, agentJson, false);
    }

    static public ACLMessage fromJson(AgentServer agentServer, JSONObject agentJson) throws AgentServerException, JSONException {
        return fromJson(agentServer, null, agentJson, false);
    }

    static synchronized public ACLMessage fromJson(AgentServer agentServer, User user, JSONObject agentJson, boolean update) throws AgentServerException, JSONException {
        log.info("If we have the user, ignore user from JSON");
        // Parse the message sender
        Performative messageperformative=Performative.valueOf(agentJson.getString("performative").toUpperCase());

        // Parse the message sender
        String messageSender=agentJson.optString("sender",null);
        if((messageSender==null) || messageSender.trim().length()==0)
        {
            messageSender= "";
        }
        // Parse the message receiver
        String messageReceiver=agentJson.optString("receiver",null);
        {
            if((messageReceiver==null)|| messageReceiver.trim().length()==0)
            {
                messageReceiver="";
            }
        }
        // Parse the message replyTo
        String messageReplyTo=agentJson.optString("replyTo",null);
        {
            if((messageReplyTo==null)||messageReplyTo.trim().length()==0)
            {
                messageReplyTo="";
            }
        }
        //Parse the message messageId
        String messagemessageId=agentJson.optString("conversationId",null);
        {
            if((messagemessageId==null)||messagemessageId.trim().length()==0)
            {

                messagemessageId = agentServer.config.agentServerProperties.agentServerName+"-"+ Integer.toString(agentServer.agentMessages.size());
            }
        }
        //Parse the message content
        StringBuffer messageContent=new StringBuffer(agentJson.optString("content",null));
        {
            if((messageContent==null) || messageContent.length()==0)
            {
                messageContent=new StringBuffer("");
            }
        }
        //Parse the message lenguaje
        String messageLenguaje=agentJson.optString("lenguaje",null);
        {
            if((messageLenguaje==null) || messageLenguaje.trim().length()==0)
            {
                messageLenguaje="";
            }
        }
        //Parse the message enconding
        String messageEnconding=agentJson.optString("enconding",null);
        {
            if((messageEnconding==null) || messageEnconding.trim().length()==0)
            {
                messageEnconding="";
            }
        }
        //Parse the message ontology
        String messageOntology=agentJson.optString("ontology",null);
        {
            if((messageOntology==null) || messageOntology.trim().length()==0)
            {
                messageOntology="";
            }
        }
        //Parse the message protocol
        String messageProtocol=agentJson.optString("protocol",null);
        {
            if((messageProtocol==null) || messageProtocol.trim().length()==0)
            {
                messageProtocol="";
            }
        }
        //Parse the message replyWith
        String messageReplyWith=agentJson.optString("replyWith",null);
        {
            if((messageReplyWith==null) || messageReplyWith.trim().length()==0)
            {
                messageReplyWith="";
            }
        }
        //Parse the message inReplyTo
        String messageInReplyTo=agentJson.optString("inReplyTo",null);
        {
            if((messageInReplyTo==null) || messageInReplyTo.trim().length()==0)
            {
                messageInReplyTo="";
            }
        }

        //Parse the message replyBy
        String messageReplyBy=agentJson.optString("replyBy",null);
        {
            if((messageReplyBy==null) || messageReplyBy.trim().length()==0)
            {
                messageReplyBy="";
            }
        }
        //Parse the message status
        String messagestatus=agentJson.optString("status",null);
        {
            if(!update &&(messagestatus==null))
            {
                messagestatus="new";
            }
            else if(update &&(messagestatus==null))
            {
                messagestatus="update";
            }
        }

        //Parse the message delegate
        Boolean messagedelegate = agentJson.has("delegate") ? agentJson.optBoolean("delegate") : false;

        // Validate keys

        JsonUtils.validateKeys(agentJson, "Agent Message", new ArrayList<String>(Arrays.asList(
                 "sender", "receiver", "replyTo", "conversationId",
                "content", "language", "encoding", "ontology",
                "protocol", "replyWith", "inReplyTo", "replyBy","status","delegate","performative")));

        ACLMessage agentMessage = new ACLMessage(agentServer,messageperformative, messageSender, messageReceiver, messageReplyTo, messagemessageId,
                messageContent, messageLenguaje, messageEnconding, messageOntology, messageProtocol, messageReplyWith, messageInReplyTo,
                messageReplyBy, messagestatus,update, messagedelegate);

        // Return the new agent instance
        return agentMessage;
    }

    public void setContent(String content) {
        if (content != null) {
            this.content = new StringBuffer(content);
        }
        else {
            this.content = null;
        }
    }


    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceivers() {
        return receivers;
    }
    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getconversationId() {
        return conversationId;
    }

    public void setconversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    private byte[] byteSequenceContent = null;
    public String getContent() {
        if(content != null)
        {
            return new String(content);
        }
        else
            return null;
      }

    public String getlanguage() {
        return language;
    }

    public void setlanguage(String language) {
        this.language = language;
    }

    public String getEnconding() {
        return enconding;
    }

    public void setEnconding(String enconding) {
        this.enconding = enconding;
    }

    public String getOntology() {
        return ontology;
    }

    public void setOntology(String ontology) {
        this.ontology = ontology;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getReplyWith() {
        return replyWith;
    }

    public void setReplyWith(String replyWith) {
        this.replyWith = replyWith;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public String getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(String replyBy) {
        this.replyBy = replyBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }
    public void setPerformative(Performative perf) {
        performative = perf;
    }
    public Performative getPerformative() {
        return performative;
    }
    public Boolean getDelegate() {
        return delegate;
    }

    public void setDelegate(Boolean delegate) {
        this.delegate = delegate;
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
