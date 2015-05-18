package dcc.agent.server.service.siebog.core;

/**
 * Created by teo on 30/04/15.
 */

import dcc.agent.server.service.siebog.core.Agents.Agent;

import java.util.Hashtable;

public class Dispatcher {
    private Hashtable agents;
    private boolean Debug, Verbose;

    public Dispatcher(boolean Debug, boolean Verbose) {
        agents = new Hashtable();
        this.Debug = Debug;
        this.Verbose = Verbose;
    }

    public synchronized void put(AID aid, Agent a) {

        agents.put(aid, a);
    }

    public synchronized Agent get(AID aid) {

        if (agents.containsKey(aid)) {
            return (Agent) agents.get(aid);
        } else return null;
    }

    public synchronized void remove(AID aid) {

        agents.remove(aid);
    }

    public synchronized void clear() {

        agents.clear();
    }

    public synchronized boolean isEmpty() {

        return agents.isEmpty();
    }

    public synchronized void PrintAll() {
       System.out.println(agents.toString());
    }

}

