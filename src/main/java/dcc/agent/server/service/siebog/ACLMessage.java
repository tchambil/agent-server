package dcc.agent.server.service.siebog;

/**
 * Created by teo on 27/04/15.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

public class ACLMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String USERARG_PREFIX = "X-";
    public Performative performative;
    public AID sender;
    public List<AID> receivers;
    public AID replyTo;
    public String content;
    public Serializable contentObj;
    public Map<String, Serializable> userArgs;
    public String language;
    public String encoding;
    public String ontology;
    public String protocol;
    public String conversationId;

    public String replyWith;
    public String inReplyTo;
    public long replyBy;

    public ACLMessage() {
        this(Performative.NOT_UNDERSTOOD);
    }

    public ACLMessage(Performative performative) {
        this.performative = performative;
        receivers = new ArrayList();
        userArgs = new HashMap();
    }

    @SuppressWarnings("unchecked")
    public ACLMessage(String jsonString) throws JSONException {
        JSONObject obj = new JSONObject(jsonString);
        performative = Performative.valueOf(obj.getString("performative").toUpperCase());
        String str = obj.optString("sender");
        if (str != null && !str.isEmpty())
            sender = new AID(str);
        receivers = new ArrayList();
        JSONArray arr = obj.optJSONArray("receivers");
        if (arr != null && arr.length() > 0)
            for (int i = 0; i < arr.length(); i++)
                receivers.add(new AID(arr.getString(i)));
        str = obj.optString("replyTo");
        if (str != null && !str.isEmpty())
            replyTo = new AID(str);
        content = obj.optString("content");
        language = obj.optString("language");
        encoding = obj.optString("encoding");
        ontology = obj.optString("ontology");
        protocol = obj.optString("protocol");
        conversationId = obj.optString("conversationId");
        replyWith = obj.optString("replyWith");
        inReplyTo = obj.optString("inReplyTo");
        replyBy = obj.optLong("replyBy");
        // user args
        userArgs = new HashMap();
        Iterator<String> i = obj.keys();
        while (i.hasNext()) {
            String key = i.next();
            if (key.startsWith(USERARG_PREFIX)) {
                String subKey = key.substring(USERARG_PREFIX.length());
                Serializable value = (Serializable) obj.get(key);
                userArgs.put(subKey, value);
            }
        }
    }

    public boolean canReplyTo() {
        return sender != null || replyTo != null;
    }
    public ACLMessage makeReply(Performative performative) {
        if (!canReplyTo())
            throw new IllegalArgumentException("There's no-one to receive the reply.");
        ACLMessage reply = new ACLMessage(performative);
        // receiver
        reply.receivers.add(replyTo != null ? replyTo : sender);
        // description of content
        reply.language = language;
        reply.ontology = ontology;
        reply.encoding = encoding;
        // control of conversation
        reply.protocol = protocol;
        reply.conversationId = conversationId;
        reply.inReplyTo = replyWith;
        return reply;
    }
    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
           obj.put("performative", performative);
            obj.put("sender", sender);
            JSONArray arr = new JSONArray(receivers);
            obj.put("receivers", arr);
            obj.put("replyTo", replyTo);
            obj.put("content", content);
            obj.put("language", language);
            obj.put("encoding", encoding);
            obj.put("ontology", ontology);
            obj.put("protocol", protocol);
            obj.put("conversationId", conversationId);
            obj.put("replyWith", replyWith);
            obj.put("inReplyTo", inReplyTo);
            obj.put("replyBy", replyBy);
            for (Entry<String, Serializable> e : userArgs.entrySet())
                obj.put(USERARG_PREFIX + e.getKey(), e.getValue());
        } catch (JSONException ex) {
        }
        return obj.toString();
    }
}
