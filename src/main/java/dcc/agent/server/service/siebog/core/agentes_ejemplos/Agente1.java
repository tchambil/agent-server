/*
 * Creado el 11-may-2005
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */

/**
 * @author root
 *
 * TODO Para cambiar la plantilla de este comentario generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
package dcc.agent.server.service.siebog.core.agentes_ejemplos;

import dcc.agent.server.service.siebog.core.Agents.RTAgent;

//import javax.realtime.*;

public class Agente1 extends RTAgent {
    /*public Agente1(Platform platform, String name)
    {
        super(platform,name);    
      
    }
    class ciclico1 extends PeriodicBehaviours 
    {   
       public ciclico1(RelativeTime start,RelativeTime cost, RelativeTime deadline,int priority, RelativeTime period)
       {          
         super(start,cost,deadline,priority,period);
   
       }        
       public void Task()
       {   
         
         long tiempo,tiempo_deadline;
         RelativeTime deadline;    
         
         tiempo =  System.currentTimeMillis();
        
        //PINTAR KIWI
          platform.kiwi.poner(tiempo,"START",0);
          platform.kiwi.poner(tiempo,"READY-B",0); 
          platform.kiwi.poner(tiempo,"EXEC-B",0); 
          deadline = GetDeadline();
          tiempo_deadline = deadline.getMilliseconds();
          tiempo = tiempo + tiempo_deadline;
          platform.kiwi.poner(tiempo,"DEADLINE",0);
        //FIN PINTAR KIWI
       
       
          tiempo = cost.getMilliseconds();
        
          esperar(tiempo-200);
          
          ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
          
          msg.setSender(aidAgent);
          
          //buscamos el AID del agente2
                  AID receiver = platform.getAMS().Search("agente2"); 
                 
          msg.setReceiver(receiver);
          msg.setContent("Hola");
          System.out.println(receiver.toString());
          Send(msg);
          
          System.out.println("mensaje enviado por el agente 1");
             
          
            //PINTAR KIWI
            tiempo = System.currentTimeMillis();
            platform.kiwi.poner(tiempo,"EXEC-E",0);
            platform.kiwi.poner(tiempo,"READY-E",0); 
            platform.kiwi.poner(tiempo,"STOP",0); 
         //FIN PINTAR KIWI
     
       } //fin metodo task()         
    } //fin behaviour
    
    public void run() {
   
     ciclico1 behaviour = new ciclico1( new RelativeTime(0,0),
                                      new RelativeTime(300,0),
                                      new RelativeTime(3000,0),
                                      27,
                                      new RelativeTime(3000,0));
                                      
     this.AddRTBehaviour(behaviour);
    
    }*/
}
