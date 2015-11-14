package dcc.agent.server.service.agentserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by teo on 11/14/2015.
 */
public class NautiLODList implements Iterable<NautiLODResult> {

    List<NautiLODResult> NautiLODInstances= new ArrayList<NautiLODResult>();
    public void add(NautiLODResult NautiLODI) {
        NautiLODInstances.add(NautiLODI);
    }
    public boolean containsKey(String NautiLODName) {
        // Scan all agent instances of this user for the name
        for (NautiLODResult NautiLODI : this)
            if (NautiLODI.name.equals(NautiLODName))
                // Found an instance with the specified name
                return true;
        // No matching instance for specified name
        return false;
    }
    public NautiLODResult get(String NautiLODName) {
        // Scan all agent instances of this user for the name
        for (NautiLODResult NautiLODI : this)
            if (NautiLODI.name.equals(NautiLODName))
                // Found an instance with the specified name
                return NautiLODI;
        // No matching instance for specified name
        return null;
    }
    public NautiLODResult get(int index) {
        return NautiLODInstances.get(index);
    }
    public NautiLODResult getByDefinitionName(String Name) {
        // Scan all agent instances of this user for an instance of the named definition
        // TODO: Handle multiples
        for (NautiLODResult NautiLODI  : this)
            if (NautiLODI.name.equals(Name))
                // Found an instance with the specified agent definition name
                return NautiLODI;
        // No matching instance for specified name
        return null;
    }

    public NautiLODResult put(NautiLODResult NautiLODI) throws AgentServerException {
        // No-op if agent instance is already on the list
        if (!NautiLODInstances.contains(NautiLODI)) {
            // Add the new instance to the list
            NautiLODInstances.add(NautiLODI);
        }
        // Return the instance
        return NautiLODI;
    }
    public Iterator<NautiLODResult> iterator() {
        return NautiLODInstances.iterator();
    }

    public void remove(String  NauName) {
        NautiLODResult NautiLODI = get(NauName);
        if (NautiLODI != null)
            remove(NautiLODI);
    }

    public void remove(      NautiLODResult NautiLODI ) {
        if (NautiLODI != null)
            NautiLODInstances.remove(NautiLODI);
    }

    public int size() {
        return NautiLODInstances.size();
    }
}
