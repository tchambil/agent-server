package dcc.agent.server.service.siebog.core.agentes_ejemplos;

import dcc.agent.server.service.siebog.core.Agents.RTAgent;

//import javax.realtime.RelativeTime;
//import Robot.TCPconexion;


public class AgenteControlador extends RTAgent {/*
    Pizarra pizarra;
	//TCPconexion conexion;
	
	String server ="158.42.185.131";
	
	int port_pizarra = 10021;
	int port_bind = 10020;
	
	Socket socket_pizarra,socket_bind;
	BufferedReader input_socket_pizarra;
	PrintWriter output_socket_pizarra;
	BufferedReader input_socket_bind;
	PrintWriter output_socket_bind;
	
	public AgenteControlador(Platform platform, String name,Pizarra Tabla) 
    {	
        super(platform,name);
        if (platform.Debug) System.out.println("INICIALIZANDO AGENTE CONTROLADOR....");
        pizarra = Tabla;
        
       try {
			// conexion= new TCPconexion(server,port);
			socket_pizarra = new Socket(server,port_pizarra);
		} catch (UnknownHostException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}	
		
	    try {
			// conexion= new TCPconexion(server,port);
			socket_bind = new Socket(server,port_bind);
		} catch (UnknownHostException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}	
		
		try {
			output_socket_pizarra = new PrintWriter(socket_pizarra.getOutputStream(),true);
			input_socket_pizarra = new BufferedReader(new InputStreamReader(socket_pizarra.getInputStream()));
		} catch (IOException e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		}	
		
		try {
			output_socket_bind = new PrintWriter(socket_bind.getOutputStream(),true);
			input_socket_bind = new BufferedReader(new InputStreamReader(socket_bind.getInputStream()));
		} catch (IOException e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		}
		
    }

	//Comportamiento para la subasta
	class Bind extends PeriodicBehaviours 
    {   
	         
	   public Bind(RelativeTime start,RelativeTime cost, RelativeTime deadline,int priority, RelativeTime period)
       {          
         super(start,cost,deadline,priority,period);    
       } 
	   
       public void Task()
       { 
       	 	long tiempo_coste,tiempo,tiempo_deadline;
       	 	RelativeTime deadline;    
        
       	 	tiempo_coste=tiempo =  System.currentTimeMillis();
       
       	 	if (platform.Kiwi)
       	 	{       	 
       	 	platform.kiwi.poner(tiempo,"START",1);
       	 	platform.kiwi.poner(tiempo,"READY-B",1); 
       	 	platform.kiwi.poner(tiempo,"EXEC-B",1); 
       	 	deadline = GetDeadline();
       	 	tiempo_deadline = deadline.getMilliseconds();
       	 	tiempo = tiempo + tiempo_deadline;
       	 	platform.kiwi.poner(tiempo,"DEADLINE",1);     	 	
       	 	}
       	 	
       	 	if (platform.Verbose) System.out.println("Ejecutando tarea BIND del agente CONTROLADOR .......");
       	 	ACLMessage msg,maxmsg;
       	 	ACLMessage[] lista_msg = new ACLMessage[20];
     	
       	 	boolean quedan_mensajes = true;
       	 	int i=0;
       	 	int j,indice = -1;
       	 	int bind =-1;
       	 	int maxbind = -1;
       	 	char caracter;
       	 	int posicion=1;
       	 	int mensajes = 0;
       	 	String contenido,accion = null,maxaccion = null;
       	 	StringBuffer buffer = new StringBuffer();
      		//mientras queden mensajes, leerlos y extraer el que tenga un bind mayor.
    	
       	 	while (quedan_mensajes)
       	 	{
       	 		msg = Receive();	
       	 		if (msg == null) 
       	 		{
       	 			quedan_mensajes = false;	
       	 			if (platform.Verbose) System.out.println("No quedan mas mensajes que tratar");
       	 		}
       	 		else 
       	 		{
       	 			mensajes++;
       	 			lista_msg[i]=msg;
       	 			contenido = msg.getContent();		
       	 			for (j=0;j<contenido.length();j++) 
       	 			{
       	 				caracter = contenido.charAt(j);
						if (caracter == ';')
						{
							switch(posicion)
							{
								case(1):
								{
									accion = buffer.toString();	
									buffer.delete(0,buffer.length());
									posicion=2;
									break;
								}
								case(2):
								{
									bind = Integer.parseInt(buffer.toString());
									buffer.delete(0,buffer.length());
									posicion=1;
									break;
								}
							}
						}
						else buffer.append(caracter);				
       	 			}//fin for
				    if (platform.Verbose) System.out.println("mensaje numero "+ i +" con la accion " + accion + " y el bind " + bind);
       	 			if (bind>maxbind)
       	 			{
       	 				maxbind = bind;
       	 				maxaccion = accion;
       	 				maxmsg = msg;
       	 				indice = i;
       	 			}
       	 			i++;
       	 		} //fin else	   
       	 	} //fin while
       	 	if (mensajes > 0)
       	 	{
       	 		if (platform.Verbose) System.out.println("Ganador de la puja el agente "+lista_msg[indice].getSender().getName() + " con un bind de "+maxbind + " con la siguiente accion " + maxaccion);
       	 		//responder al emisor del mensaje y realizar la accion
       	 		//ACLMessage msgconfirmacion = new ACLMessage(ACLMessage.REFUSE);		  
       	 		//msgconfirmacion.setContent("Bind bajo");
       	 		
       	 		/*  
       	 		 System.out.println("lista msg "+lista_msg.length);
       	 		 for (i=0;i<lista_msg.length;i++)
       	 		 {
       	 		 	if (i!=indice)
       	 		 	{
       	 		 		msgconfirmacion.setReceiver(lista_msg[i].getSender());
       	 		 		Send(msgconfirmacion);
       	 		    }
       	 		 }
       	 		 */
		   /*
       	 		msgconfirmacion.setPerformative(ACLMessage.AGREE);
       	 		msgconfirmacion.setContent("Aceptado");
       	 		msgconfirmacion.setReceiver(lista_msg[indice].getSender());
       	 		System.out.println("mensaje a enviar:");
       	 		System.out.println(lista_msg[indice]);
       	 		
       	 		Send(msgconfirmacion);

       	 		
       	 	 	//conexion.ProtocoloEnvioMensaje(maxaccion);  
       	 	    output_socket_bind.println(maxaccion);
       	 	
       	 		ACLMessage msgconfirmacion = new ACLMessage(ACLMessage.INFORM);
       	 		msgconfirmacion.setReceiver(lista_msg[indice].getSender());
       	 	    //msgconfirmacion.setPerformative(ACLMessage.INFORM);
       	 		msgconfirmacion.setContent("Hecho");
       	 		Send(msgconfirmacion);
       	 		if (lista_msg[indice].getSender().getName().equals("AgenteRecolector"))
       	 			pizarra.nextDestino();
       	 	}
       	 	else
       	 	{
       	 		System.out.println("No hay mensajes");
       	 	}
       	
       	 	if (platform.Kiwi)
       	 	{
       	 	
       	 	tiempo = System.currentTimeMillis();
       	 	platform.kiwi.poner(tiempo,"EXEC-E",1);
       	 	platform.kiwi.poner(tiempo,"READY-E",1); 
       	 	platform.kiwi.poner(tiempo,"STOP",1); 
       	 	}
       	 	
       	 	tiempo_coste = System.currentTimeMillis()-tiempo_coste;
       	 	System.out.println("coste de la tarea bind del agente controlador: " + tiempo_coste);
	    }//fin task
    } //fin comportamiento Bind
	
	//comportamiento para actualizar la pizarra
	class RellenarPizarra extends PeriodicBehaviours
	{
		public RellenarPizarra(RelativeTime start,RelativeTime cost, RelativeTime deadline,int priority, RelativeTime period)
        {          
          super(start,cost,deadline,priority,period);
        }  
		
        public void Task()
        { 
       	    long tiempo_coste,tiempokiwi,tiempo_deadline;
       	    RelativeTime deadline;    
       	    if (platform.Verbose) System.out.println("Ejecutando tarea RELLENARPIZARRA del agente CONTROLADOR .......");
       	    
       	    tiempo_coste = tiempokiwi =  System.currentTimeMillis();
       
       	    if (platform.Kiwi)
       	    {
       	    platform.kiwi.poner(tiempokiwi,"START",0);
       	    platform.kiwi.poner(tiempokiwi,"READY-B",0); 
       	    platform.kiwi.poner(tiempokiwi,"EXEC-B",0); 
       	    deadline = GetDeadline();
       	    tiempo_deadline = deadline.getMilliseconds();
       	    tiempokiwi = tiempokiwi + tiempo_deadline;
       	    platform.kiwi.poner(tiempokiwi,"DEADLINE",0);
       	    }     	    
       	    
            output_socket_pizarra.println("Pedir_Informacion");
       	    //String devuelve=null;
			try {
				String devuelve = input_socket_pizarra.readLine();
				pizarra.tratarCadena(devuelve);
			} catch (IOException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}			      	    
       	    if (platform.Kiwi)
       	    { 
       	    tiempokiwi = System.currentTimeMillis();
       	    platform.kiwi.poner(tiempokiwi,"EXEC-E",0);
       	    platform.kiwi.poner(tiempokiwi,"READY-E",0); 
       	    platform.kiwi.poner(tiempokiwi,"STOP",0); 
       	    }      	    
       	 tiempo_coste = System.currentTimeMillis() - tiempo_coste;
       	 System.out.println("tiempo coste tarea rellenar pizarra del agente controlador: "+ tiempo_coste);
        }
	} //fin comportamiento Rellenar Pizarra
	
	public void run() 
 	{
	     RellenarPizarra behaviour2 = new RellenarPizarra(  new RelativeTime(0,0),
	     													new RelativeTime(100,0),
															new RelativeTime(200,0),
															20,
															new RelativeTime(200,0));
         
	     this.AddRTBehaviour(behaviour2);
	  
	    Bind behaviour1 = new Bind( new RelativeTime(0,0),
	     							 new RelativeTime(200,0),
									 new RelativeTime(1000,0),
									 20,
									 new RelativeTime(1000,0));
         this.AddRTBehaviour(behaviour1);
         
     }*/
} //fin agente controlador

