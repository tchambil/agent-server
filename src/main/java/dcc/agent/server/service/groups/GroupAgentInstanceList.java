package dcc.agent.server.service.groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by teo on 23/07/15.
 */
public class GroupAgentInstanceList implements Iterable<GroupAgentInstance> {

    List<GroupAgentInstance> GroupAgentInstances=new ArrayList<GroupAgentInstance>();


    public void add(GroupAgentInstance groupAgentInstance)
    {
        GroupAgentInstances.add(groupAgentInstance);
    }
    public boolean containsKey(String id)
    {
        for(GroupAgentInstance groupAgentInstance:this)
        {
            if(groupAgentInstance.id.equals(id))
            {
                return true;
            }
        }
        return false;
    }
    public GroupAgentInstance get(String id )
    {
        for(GroupAgentInstance groupAgentInstance:this)
        {
            if(groupAgentInstance.id.equals(id))
            {
                return groupAgentInstance;
            }
        }
        return null;
    }
    public GroupAgentInstance get(int index)
    {
        return GroupAgentInstances.get(index);
    }

    public GroupAgentInstance put(GroupAgentInstance groupAgentInstance)
    {
        if(!GroupAgentInstances.contains(groupAgentInstance))
        {
            GroupAgentInstances.add(groupAgentInstance);
        }
        return groupAgentInstance;
    }

    public void remove(String id)
    {
        GroupAgentInstance groupAgentInstance=get(id);
        if(groupAgentInstance!=null)
        {
            remove(groupAgentInstance);
        }
    }
    public void remove (GroupAgentInstance groupAgentInstance)
    {
        if(groupAgentInstance!=null)
        {
            GroupAgentInstances.remove(groupAgentInstance);
        }
    }
    public int size()
    {
        return GroupAgentInstances.size();
    }

    public Iterator<GroupAgentInstance> iterator() {
        return GroupAgentInstances.iterator();
    }
}
