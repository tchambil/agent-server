package dcc.agent.server.service.delegate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by teo on 27/05/15.
 */
public class AgentMessageList implements  Iterable<AgentMessage>
{
    List<AgentMessage> agentMessages =new ArrayList<AgentMessage>();
    public void add(AgentMessage agentMessage)
    {
        agentMessages.add(agentMessage);
    }
    public boolean containsKey(String agentMessageConversationId)
    {
        for(AgentMessage agentMessage:this)
        {
            if(agentMessage.conversationId.equals(agentMessageConversationId))
            {
                return true;
            }
        }
        return false;
    }
public AgentMessage get(String agentMessageConversationId )
{
    for(AgentMessage agentMessage:this)
    {
        if(agentMessage.conversationId.equals(agentMessageConversationId))
        {
            return agentMessage;
        }
    }
    return null;
}
    public AgentMessage get(int index)
    {
        return agentMessages.get(index);
    }
public AgentMessage put(AgentMessage agentMessage)
{
    if(!agentMessages.contains(agentMessage))
    {
        agentMessages.add(agentMessage);
    }
    return agentMessage;
}
    public void remove(String agentMessageConversationId)
    {
        AgentMessage agentMessage=get(agentMessageConversationId);
        if(agentMessage!=null)
        {
            remove(agentMessage);
        }
    }
    public void remove (AgentMessage agentMessage)
    {
        if(agentMessage!=null)
        {
            agentMessages.remove(agentMessage);
        }
    }


    public int size()
    {
        return agentMessages.size();
    }

    public Iterator<AgentMessage> iterator() {
        return agentMessages.iterator();
    }
}
