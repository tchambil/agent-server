package dcc.agent.server.service.siebog.core.Agents;

/**
 * Created by teo on 30/04/15.
 */


import dcc.agent.server.service.siebog.core.AID;
import dcc.agent.server.service.siebog.core.AMS;
import dcc.agent.server.service.siebog.core.Behaviours.Behaviours;
import dcc.agent.server.service.siebog.core.Communication.ACLMessage;
import dcc.agent.server.service.siebog.core.Communication.MessageQueue;
import dcc.agent.server.service.siebog.core.Dispatcher;
import dcc.agent.server.service.siebog.core.Platform;

public class Agent extends Thread {

    protected Behaviours currentBehaviour;
    protected ACLMessage currentMessage;
    protected MessageQueue ListMessage;
    protected AID aidAgent;
    protected Dispatcher dispatcher;
    protected AMS ams;
    protected Platform platform;
    protected int state_agent;

    public Agent() {
    }

    public Agent(Platform platform, String name) {
        this.platform = platform;
        dispatcher = platform.getDispatcher();
        ams = platform.getAMS();
        ListMessage = new MessageQueue();
        aidAgent = CreateAID(platform, name);
        RegisterInDispatcher(aidAgent, this);
        RegisterInAMS(name, aidAgent);
        state_agent = 1;
    }

    public void AddBehaviour(Behaviours b) {
    }

    public void RemoveBehaviour(Behaviours b) {
    }

    public ACLMessage Receive() {

        if (this.ListMessage.isEmpty()) {
            if (platform.Verbose) System.out.println("No hay ningun mensaje en la cola de mensajes");
            return null;
        } else {
             ACLMessage message = this.ListMessage.get();
            if (platform.Verbose)
                System.out.println("Mensaje recibido por el agente " + this.aidAgent.getName() + " enviado por el agente " + message.getSender().getName());
            return message;
        }
    }

    public ACLMessage Receive(AID sender) {
        return this.ListMessage.get(sender);
    }

    public void Send(ACLMessage message) {
        AID sender;
        Agent agent;
        AID receiver = (AID) message.getReceiver();

        agent = (Agent) dispatcher.get(receiver);

        if (agent != null) {
            agent.ListMessage.add(message);
            if (platform.Verbose)
                System.out.println("Mensaje enviado del agente " + this.aidAgent.getName() + " al agente" + agent.aidAgent.getName());
        } else {
            if (platform.Verbose) System.out.println("No existe el agente receptor de este mensaje");
        }

    }

    protected AID CreateAID(Platform platform, String name) {
        AID AIDAgent = new AID(name, platform.getidPlatform());
        AIDAgent.addAddresses(platform.getidPlatform());
        return AIDAgent;
    }

    protected void RegisterInDispatcher(AID aidAgent, Agent agent) {
        dispatcher.put(aidAgent, agent);
    }

    protected void RegisterInAMS(String name, AID aidAgent) {
        ams.Register(name, aidAgent);

    }

    public void run() {
    }

    protected void setup() {
    }

    protected void takedown() {
    }
}

