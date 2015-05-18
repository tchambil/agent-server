package dcc.agent.server.service.siebog.core.Communication;

/**
 * Created by teo on 30/04/15.
 */


import dcc.agent.server.service.siebog.core.AID;

import java.util.Enumeration;
import java.util.Hashtable;


public class MessageQueue {
    private Hashtable table;
    private int maxSize;
    private int index;

    public MessageQueue() {
        table = new Hashtable();
        maxSize = 20;
    }

    public MessageQueue(int size) {
        maxSize = size;
        table = new Hashtable(maxSize);
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public int getMaxSize() {
        return maxSize;
    }

    /**
     * @return the number of messages
     * currently in the queue
     */
    public int size() {
        return table.size();
    }

    public synchronized void add(ACLMessage msg) {
        String St_index = "";
        table.put(String.valueOf(index), (ACLMessage) msg);
        index = index + 1;      //no se que hace, mirar
    }


    public synchronized ACLMessage get() {
        Enumeration e;
        e = table.keys();
        if (e.hasMoreElements()) {
            ACLMessage msg = (ACLMessage) table.remove(e.nextElement());
            return msg;
        } else
            return null;
    }

    public synchronized ACLMessage get(AID sender) {
        Enumeration e;
        e = table.keys();
        while (e.hasMoreElements()) {
            ACLMessage msg = (ACLMessage) table.get(e.nextElement());
            if (sender.compareTo(msg.getSender()) == 1) {
                table.remove(msg); //borrar la entrada de la tabla
                return msg;
            }
        }
        return null;
    }

    public synchronized void remove(String key) {
        table.remove(key);
    }

    private void PrintMessage() {
        Enumeration e;
        e = table.keys();
        int i = 0;
        while (e.hasMoreElements()) {
            ACLMessage msg = (ACLMessage) table.get(e.nextElement());
            System.out.println("Mensaje numero " + i + "  cuyo contenido es: " + msg.getContent());
        }
    }
}

