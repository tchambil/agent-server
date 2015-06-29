package dcc.agent.server.service.communication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by teo on 27/05/15.
 */
public class ACLMessageList implements  Iterable<ACLMessage>
{
    List<ACLMessage> aclMessages =new ArrayList<ACLMessage>();
    public void add(ACLMessage aclMessage)
    {
        aclMessages.add(aclMessage);
    }
    public boolean containsKey(String aclMessageId)
    {
        for(ACLMessage agentMessage:this)
        {
            if(agentMessage.messageId.equals(aclMessageId))
            {
                return true;
            }
        }
        return false;
    }
public ACLMessage get(String aclMessageId )
{
    for(ACLMessage aclMessage:this)
    {
        if(aclMessage.messageId.equals(aclMessageId))
        {
            return aclMessage;
        }
    }
    return null;
}
    public ACLMessage get(int index)
    {
        return aclMessages.get(index);
    }
public ACLMessage put(ACLMessage aclMessage)
{
    if(!aclMessages.contains(aclMessage))
    {
        aclMessages.add(aclMessage);
    }
    return aclMessage;
}
    public void remove(String aclMessageId)
    {
        ACLMessage aclMessage=get(aclMessageId);
        if(aclMessage!=null)
        {
            remove(aclMessage);
        }
    }
    public void remove (ACLMessage aclMessage)
    {
        if(aclMessage!=null)
        {
            aclMessages.remove(aclMessage);
        }
    }



    public int size()
    {
        return aclMessages.size();
    }

    public Iterator<ACLMessage> iterator() {
        return aclMessages.iterator();
    }
}
