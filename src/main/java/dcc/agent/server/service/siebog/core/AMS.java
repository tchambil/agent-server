package dcc.agent.server.service.siebog.core;

/**
 * Created by teo on 30/04/15.
 */

import java.util.Hashtable;

public class AMS {
    // agent list of the platform.
    private Hashtable agentlist;
    private boolean Debug;
    private boolean Verbose;

    public AMS(boolean Debug, boolean Verbose) {
        agentlist = new Hashtable();
        this.Debug = Debug;
        this.Verbose = Verbose;
    }

    public void Register(String name, AID agent) {
        if (Debug) System.out.println("DENTRO DEL METODO REGISTER DE LA CLASE AMS");
        if (!agentlist.containsKey(name)) {
            agentlist.put(name, agent);
            if (Verbose) System.out.println("El agente " + agent.toString() + " se incluyo en la lista del AMS.");
        } else {
            if (Verbose) System.out.println("El agente " + agent.toString() + " ya estaba en la lista del AMS.");
        }
    }

    public synchronized void Delete(AID agent) {
        if (Debug) System.out.println("DENTRO DEL METODO DELETE DE LA CLASE AMS");
        if (agentlist.contains(agent)) {
            agentlist.remove(agent);
            if (Verbose) System.out.println("El agente " + agent.toString() + " fue eliminado de la lista de agentes.");
        } else {
            if (Verbose) System.out.println("El agente " + agent.toString() + " no esta en la lista.");
        }
    }

    public synchronized AID Search(String name) {
        if (Debug) System.out.println("DENTRO DEL METODO SEARCH DE LA CLASE AMS");
        if (agentlist.containsKey(name)) {

            return (AID) agentlist.get(name);
        } else
            return null;
    }

    public synchronized void PrintAll() {
        System.out.println("Tabla Hash de los agentes en el AMS: ");
        System.out.println(agentlist.toString());
    }


}

