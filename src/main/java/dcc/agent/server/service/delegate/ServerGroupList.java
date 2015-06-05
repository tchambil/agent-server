package dcc.agent.server.service.delegate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by teo on 01/06/15.
 */
public class ServerGroupList implements Iterable<ServerGroup>{

    List<ServerGroup> serverGroups=new ArrayList<ServerGroup>();
    public void add(ServerGroup serverGroup)
    {
        serverGroups.add(serverGroup);
    }
    public boolean containsKey(String serverHostName)
    {
        for(ServerGroup serverGroup:this)
        {
            if(serverGroup.HostName.equals(serverHostName))
            {
                return true;
            }
        }
        return false;
    }

    public ServerGroup get(String serverHostName )
    {
        for(ServerGroup serverGroup:this)
        {
            if(serverGroup.HostName.equals(serverHostName))
            {
                return serverGroup;
            }
        }
        return null;
    }
    public ServerGroup get(int index)
    {
        return serverGroups.get(index);
    }

    public ServerGroup put(ServerGroup serverGroup)
    {
        if(!serverGroups.contains(serverGroup))
        {
            serverGroups.add(serverGroup);
        }
        return serverGroup;
    }

    public void remove(String serverHostName)
    {
        ServerGroup serverGroup=get(serverHostName);
        if(serverGroup!=null)
        {
            remove(serverGroup);
        }
    }
    public void remove (ServerGroup serverGroup)
    {
        if(serverGroup!=null)
        {
            serverGroups.remove(serverGroup);
        }
    }
    public int size()
    {
        return serverGroups.size();
    }

    public Iterator<ServerGroup> iterator() {
        return serverGroups.iterator();
    }



}
