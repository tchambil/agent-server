package dcc.agent.server.service.siebog.core.Communication;

/**
 * Created by teo on 30/04/15.
 */

import dcc.agent.server.service.siebog.core.AID;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ACLMessage {

    public static final int ACCEPT_PROPOSAL = 0;
    public static final int AGREE = 1;
    public static final int CANCEL = 2;
    public static final int CFP = 3;
    public static final int CONFIRM = 4;
    public static final int DISCONFIRM = 5;
    public static final int FAILURE = 6;
    public static final int INFORM = 7;
    public static final int INFORM_IF = 8;
    public static final int INFORM_REF = 9;
    public static final int NOT_UNDERSTOOD = 10;
    public static final int PROPOSE = 11;
    public static final int QUERY_IF = 12;
    public static final int QUERY_REF = 13;
    public static final int REFUSE = 14;
    public static final int REJECT_PROPOSAL = 15;
    public static final int REQUEST = 16;
    public static final int REQUEST_WHEN = 17;
    public static final int REQUEST_WHENEVER = 18;
    public static final int SUBSCRIBE = 19;
    public static final int PROXY = 20;
    public static final int PROPAGATE = 21;
    public static final int UNKNOWN = -1;
    private static List performatives = new ArrayList(22);

    static {
        performatives.add("ACCEPT-PROPOSAL");
        performatives.add("AGREE");
        performatives.add("CANCEL");
        performatives.add("CFP");
        performatives.add("CONFIRM");
        performatives.add("DISCONFIRM");
        performatives.add("FAILURE");
        performatives.add("INFORM");
        performatives.add("INFORM-IF");
        performatives.add("INFORM-REF");
        performatives.add("NOT-UNDERSTOOD");
        performatives.add("PROPOSE");
        performatives.add("QUERY-IF");
        performatives.add("QUERY-REF");
        performatives.add("REFUSE");
        performatives.add("REJECT-PROPOSAL");
        performatives.add("REQUEST");
        performatives.add("REQUEST-WHEN");
        performatives.add("REQUEST-WHENEVER");
        performatives.add("SUBSCRIBE");
        performatives.add("PROXY");
        performatives.add("PROPAGATE");
    }

    private int performative;
    private AID source = null;
    private AID receiver = null;
    private ArrayList dests = new ArrayList();
    private ArrayList reply_to = new ArrayList();
    private StringBuffer content = null;
    private StringBuffer reply_with = null;
    private StringBuffer in_reply_to = null;
    private StringBuffer encoding = null;
    private StringBuffer language = null;
    private StringBuffer ontology = null;
    private long reply_byInMillisec = 0;
    private StringBuffer protocol = null;
    private StringBuffer conversation_id = null;

    public ACLMessage() {
        performative = NOT_UNDERSTOOD;
    }

    public ACLMessage(int perf) {
        performative = perf;
    }

    public static String getPerformative(int perf) {
        try {
            return new String((String) performatives.get(perf));
        } catch (Exception e) {
            return new String((String) performatives.get(NOT_UNDERSTOOD));
        }
    }

    public static int getInteger(String perf) {
        return performatives.indexOf(perf.toUpperCase());
    }

    public void addReceiver(AID r) {
        if (r != null)
            dests.add(r);
    }

    public boolean removeReceiver(AID r) {
        if (r != null)
            return dests.remove(r);
        else
            return false;
    }

    public void clearAllReceiver() {
        dests.clear();
    }

    public void addReplyTo(AID dest) {
        if (dest != null)
            reply_to.add(dest);
    }

    public boolean removeReplyTo(AID dest) {
        if (dest != null)
            return reply_to.remove(dest);
        else
            return false;
    }

    public void clearAllReplyTo() {
        reply_to.clear();
    }

    public void setContent(String content) {
        if (content != null)
            this.content = new StringBuffer(content);
        else
            this.content = null;
    }

    public Iterator getAllReceiver() {
        return dests.iterator();
    }

    public Iterator getAllReplyTo() {
        return reply_to.iterator();
    }

    public AID getSender() {
        if (source != null)
            return (AID) source;
        else
            return null;
    }

    public void setSender(AID s) {
        if (s != null)
            source = s;
        else
            source = null;
    }

    public AID getReceiver() {
        if (receiver != null)
            return (AID) receiver;
        else
            return null;
    }

    public void setReceiver(AID s) {
        if (s != null)
            receiver = s;
        else
            receiver = null;
    }

    public int getPerformative() {
        return performative;
    }

    public void setPerformative(int perf) {
        performative = perf;
    }

    public String getContent() {
        if (content != null)
            return new String(content);
        else
            return null;
    }

    public void setContent(Object content) {
        if (content != null)
            this.content = (StringBuffer) content;
        else
            this.content = null;
    }

    public String getReplyWith() {
        if (reply_with != null)
            return new String(reply_with);
        else return null;
    }

    public void setReplyWith(String reply) {
        if (reply != null)
            reply_with = new StringBuffer(reply);
        else
            reply_with = null;
    }

    public String getInReplyTo() {
        if (in_reply_to != null)
            return new String(in_reply_to);
        else return null;
    }

    public void setInReplyTo(String reply) {
        if (reply != null)
            in_reply_to = new StringBuffer(reply);
        else
            in_reply_to = null;
    }

    public String getEncoding() {
        if (encoding != null)
            return new String(encoding);
        else
            return null;
    }

    public void setEncoding(String str) {
        if (str != null)
            encoding = new StringBuffer(str);
        else
            encoding = null;
    }

    public String getLanguage() {
        if (language != null)
            return new String(language);
        else
            return null;
    }

    public void setLanguage(String str) {
        if (str != null)
            language = new StringBuffer(str);
        else
            language = null;
    }

    public String getOntology() {
        if (ontology != null)
            return new String(ontology);
        else
            return null;
    }

    public void setOntology(String str) {
        if (str != null)
            ontology = new StringBuffer(str);
        else
            ontology = null;
    }

    public Date getReplyByDate() {
        if (reply_byInMillisec != 0)
            return new Date(reply_byInMillisec);
        else
            return null;
    }

    public String getProtocol() {
        if (protocol != null)
            return new String(protocol);
        else
            return null;
    }

    public void setProtocol(String str) {
        if (str != null)
            protocol = new StringBuffer(str);
        else
            protocol = null;
    }

    public String getConversationId() {
        if (conversation_id != null)
            return new String(conversation_id);
        else
            return null;
    }

    public void setConversationId(String str) {
        if (str != null)
            conversation_id = new StringBuffer(str);
        else
            conversation_id = null;
    }

    public void reset() {
        source = null;
        dests.clear();
        reply_to.clear();
        performative = NOT_UNDERSTOOD;
        content = null;
        reply_with = null;
        in_reply_to = null;
        encoding = null;
        language = null;
        ontology = null;
        reply_byInMillisec = 0;
        protocol = null;
        conversation_id = null;

    }


     /* public ACLMessage createReply() {
        ACLMessage m = (ACLMessage)clone();
	    m.clearAllReceiver();
	    Iterator it = reply_to.iterator();
	    while (it.hasNext())
	      m.addReceiver((AID)it.next());
	    if (reply_to.isEmpty())
	      m.addReceiver(getSender());
	    m.clearAllReplyTo();
	    m.setLanguage(getLanguage());
	    m.setOntology(getOntology());
	    m.setProtocol(getProtocol());
	    m.setSender(null);
	    m.setInReplyTo(getReplyWith());
	    if (source != null)
	      m.setReplyWith(source.getName() + java.lang.System.currentTimeMillis());
	    else
	      m.setReplyWith("X"+java.lang.System.currentTimeMillis());
	    m.setConversationId(getConversationId());
	    m.setReplyByDate(null);
	    m.setContent(null);
	    m.setEncoding(null);


	    return m;
	  }*/

    /**
     @return An Iterator over all the intended receivers of this
     message taking into account the Envelope ":intended-receiver"
     first, the Envelope ":to" second and the message ":receiver"
     last.
     */
	  /*public Iterator getAllIntendedReceiver() {
			Iterator it = null;
			//Envelope env = getEnvelope();
			if (env != null) {
				it = env.getAllIntendedReceiver();
				if (!it.hasNext()) {
					// The ":intended-receiver" field is empty --> try with the ":to" field
					it = env.getAllTo();
				}
			}
			if (it == null || !it.hasNext()) {
				// Both the ":intended-receiver" and the ":to" fields are empty -->
				// Use the ACLMessage receivers
				it = getAllReceiver();
			}
			return it;
	  }
	*/
}


