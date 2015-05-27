package dcc.agent.server.service.message;


import dcc.agent.server.service.agentserver.AgentServer;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.script.intermediate.SymbolException;
import dcc.agent.server.service.script.parser.ParserException;
import dcc.agent.server.service.script.parser.tokenizer.TokenizerException;
import dcc.agent.server.service.util.JsonListMap;
import dcc.agent.server.service.util.JsonUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by teo on 27/05/15.
 */
public class AgentMessage {
    static final Logger log= Logger.getLogger(AgentMessage.class);
    public AgentServer agentServer;
    public User user;
   public String sender;
    public String receiver;
    public String replyTo;
    public String conversationId;
    public String content;
    public String lenguaje;
    public String enconding;
    public String ontology;
    public String protocol;
    public String replyWith;
    public String inReplyTo;
    public String replyBy;
    public Boolean update;
    public Boolean check;
   public AgentServerProperties agentServerProperties;

    public AgentMessage( AgentServer agentServer,
                         User user,
                         String sender,
                        String receiver,
                        String replyTo,
                        String conversationId,
                        String content,
                        String lenguaje,
                        String enconding,
                        String ontology,
                        String protocol,
                        String replyWith,
                        String inReplyTo,
                        String replyBy,
                        Boolean update,
                        Boolean check
                            )

    {
    this.check=check;
        this.update=update;
        this.user=user==null?User.noUser:user;
        this.agentServer = agentServer;
        this.sender=sender;
        this.receiver=receiver;
        this.replyTo=replyTo;
        this.conversationId=conversationId;
        this.content=content;
        this.lenguaje=lenguaje;
        this.enconding=enconding;
        this.ontology=ontology;
        this.protocol=protocol;
        this.replyWith=replyWith;
        this.inReplyTo=inReplyTo;
        this.replyBy=replyBy;



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
          messageJson.put("user", user.id);
          messageJson.put("sender", sender == null ? "" : sender);
          messageJson.put("receiver", receiver == null ? "": receiver);
          messageJson.put("replyTo", replyTo == null ? "" : replyTo);
          messageJson.put("conversationId", conversationId == null ? "" : conversationId);
          messageJson.put("content", content == null ? "" : content);
          messageJson.put("lenguage", lenguaje == null ? "" : lenguaje);
          messageJson.put("encoding", enconding == null ? "" : enconding);
          messageJson.put("ontology", ontology == null ? "" : ontology);
          messageJson.put("protocol", protocol == null ? "" : protocol);
          messageJson.put("replyWith", replyWith == null ? "" : replyWith);
          messageJson.put("inReplyTo", inReplyTo == null ? "" : inReplyTo);
          messageJson.put("replyBy", replyBy == null ? "" : replyBy);

          return messageJson;
      }
      catch (JSONException e)
      {
          e.printStackTrace();
          throw new AgentServerException("JSON exception in Message.toJson -"+e.getMessage());
      }
    }

    public void update(AgentServer agentServer, AgentMessage message) throws JSONException, AgentServerException {
        if(message.sender!=null)
        {
            this.sender=message.sender;
        }
        if(message.receiver!=null)
        {
            this.receiver=message.receiver;
        }
        if(message.replyTo!=null)
        {
            this.replyTo=message.replyTo;
        }
        if(message.conversationId!=null)
        {
            this.conversationId=message.conversationId;
        }
        if(message.content!=null)
        {
            this.content=message.content;
        }
        if(message.lenguaje!=null)
        {
            this.lenguaje=message.lenguaje;
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
        agentServer.persistence.put(this);
    }

    static public AgentMessage fromJson(AgentServer agentServer, String agentJsonSource) throws AgentServerException, SymbolException, JSONException, ParseException, TokenizerException, ParserException {
        return fromJson(agentServer, null, new JSONObject(agentJsonSource), false);
    }

    static public AgentMessage fromJson(AgentServer agentServer, User user, JSONObject agentJson) throws AgentServerException, SymbolException {
        return fromJson(agentServer, user, agentJson, false);
    }

    static public AgentMessage fromJson(AgentServer agentServer, JSONObject agentJson) throws AgentServerException, SymbolException, JSONException, ParseException, TokenizerException, ParserException {
        return fromJson(agentServer, null, agentJson, false);
    }

    static public AgentMessage fromJson(AgentServer agentServer, User user, JSONObject agentJson, boolean update) throws AgentServerException {
        log.info("If we have the user, ignore user from JSON");

        if(user==null)
        {
            String userId=agentJson.optString("user");
            if(userId==null || userId.trim().length()==0)
            {
                throw new AgentServerException("Message user id ('user') is missing");
            }
            user = agentServer.getUser(userId);
            if(user==User.noUser)
            {
                throw  new AgentServerException("Message user id does not exist: '"+userId+"'");
            }
        }
        // Parse the message sender
        String messageSender=agentJson.optString("sender",null);
        if(!update &&(messageSender==null) || messageSender.trim().length()==0)
        {
            messageSender="";
        }
        // Parse the message receiver
        String messageReceiver=agentJson.optString("receiver",null);
        {
            if(!update &&(messageReceiver==null)|| messageReceiver.trim().length()==0)
            {
                messageReceiver="";
            }
        }
        // Parse the message replyTo
        String messageReplyTo=agentJson.optString("replyTo",null);
        {
            if(!update &&(messageReplyTo==null)||messageReplyTo.trim().length()==0)
            {
                messageReplyTo="";
            }
        }
        //Parse the message conversationId
        String messageConversationId=agentJson.optString("conversationId",null);
        {
            if(!update &&(messageConversationId==null) || messageConversationId.trim().length()==0)
            {
                messageConversationId="";
            }
        }
        //Parse the message content
        String messageContent=agentJson.optString("content",null);
        {
            if(!update &&(messageContent==null) || messageContent.trim().length()==0)
            {
                messageContent="";
            }
        }
        //Parse the message lenguaje
        String messageLenguaje=agentJson.optString("lenguaje",null);
        {
            if(!update &&(messageLenguaje==null) || messageLenguaje.trim().length()==0)
            {
                messageLenguaje="";
            }
        }

        //Parse the message enconding
        String messageEnconding=agentJson.optString("enconding",null);
        {
            if(!update &&(messageEnconding==null) || messageEnconding.trim().length()==0)
            {
                messageEnconding="";
            }
        }
        //Parse the message ontology
        String messageOntology=agentJson.optString("ontology",null);
        {
            if(!update &&(messageOntology==null) || messageOntology.trim().length()==0)
            {
                messageOntology="";
            }
        }
        //Parse the message protocol
        String messageProtocol=agentJson.optString("protocol",null);
        {
            if(!update &&(messageProtocol==null) || messageProtocol.trim().length()==0)
            {
                messageProtocol="";
            }
        }

        //Parse the message replyWith
        String messageReplyWith=agentJson.optString("replyWith",null);
        {
            if(!update &&(messageReplyWith==null) || messageReplyWith.trim().length()==0)
            {
                messageReplyWith="";
            }
        }

        //Parse the message inReplyTo
        String messageInReplyTo=agentJson.optString("inReplyTo",null);
        {
            if(!update &&(messageInReplyTo==null) || messageInReplyTo.trim().length()==0)
            {
                messageInReplyTo="";
            }
        }

        //Parse the message replyBy
        String messageReplyBy=agentJson.optString("replyBy",null);
        {
            if(!update &&(messageReplyBy==null) || messageReplyBy.trim().length()==0)
            {
                messageReplyBy="";
            }
        }
        // Validate keys

        JsonUtils.validateKeys(agentJson, "Agent Message", new ArrayList<String>(Arrays.asList(
                "user", "sender", "receiver", "replyTo", "conversationId",
                "content", "lenguage", "encoding", "ontology",
                "protocol", "replyWith", "inReplyTo", "replyBy")));

        AgentMessage agentMessage = new AgentMessage(agentServer,user,  messageSender, messageReceiver, messageReplyTo, messageConversationId,
                messageContent, messageLenguaje, messageEnconding, messageOntology, messageProtocol, messageReplyWith, messageInReplyTo,
                messageReplyBy, update, false);

        // Return the new agent instance
        return agentMessage;


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
