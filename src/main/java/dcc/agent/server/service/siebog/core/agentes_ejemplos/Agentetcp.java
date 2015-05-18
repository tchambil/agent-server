package dcc.agent.server.service.siebog.core.agentes_ejemplos;

import dcc.agent.server.service.siebog.core.Agents.RTAgent;

/*
 * Creado el 20-abr-2005
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

/**********************************************************/
/*                                                        */
/*              AGENTE CLIENTE SIMULADOR WEBOT            */
/*                                                        */

/**
 * ******************************************************
 */

//import java.net.*;
//import java.io.*;

//import Behaviours.*;
//import javax.realtime.*;
//import java.lang.*;


public class Agentetcp extends RTAgent {
//	int port = 10020;
//    String server = "158.42.185.170"; // jason
//    //String server = "158.42.185.122"; // amenabar
//    Socket socket = null;
//    String lineToBeSent;
//    BufferedReader input_teclado;
//    BufferedReader input_socket;
//    PrintWriter output_pantalla;
//    PrintWriter output_socket;
//    int ERROR = 1;
//    
//    public Agentetcp(Platform platform,String name)
//    {
//	super(platform,name);
//		
//    }
//    
//    class kephera extends PeriodicBehaviours
//    {
//	public kephera(RelativeTime start,RelativeTime cost, RelativeTime deadline,int priority, RelativeTime period)
//	{          
//	    super(start,cost,deadline,priority,period);
//	} 
//    
//	public void Task()
//	{
//	   try {
//
//	       System.out.println("dentro de la tarea");
//	       String str;
//	       char c;
//	       int tam;
//	       int i,j;
//	       int[] sensores= new int[8];	       
//	       // String cadena;
//
//	       lineToBeSent = "N";
//	       output_socket.println(lineToBeSent);
//	       str = input_socket.readLine();
//	       output_pantalla.println(str);  
//	       
//	       tam = str.length();
//	       
//	       StringBuffer cadena = new StringBuffer();
//	       
//	       i=2;
//	       j=0;
//	       while (i < tam) {
//		   c = str.charAt(i);
//		   if (c!=',')
//		       {
//			   cadena.append(c);
//		       }
//		   else
//		       {
//			   String cad = cadena.toString();
//			   // System.out.println(cad); 
//			   sensores[j]=  Integer.parseInt(cad);
//			     j++;
//			  
//			   
//			   cadena.setLength(0);
//		       }
//		   i++;
//	       }
//	       
//       	    
//	      System.out.println("sensores almacenados");
//
//	      //System.out.println(sensores[3]);
//	      if ((sensores[2] > 400) || (sensores[1] > 400))
//		  lineToBeSent = "D,2,-2";
//	      else if ((sensores[3] > 400) || (sensores[4] > 400))
//		  lineToBeSent = "D,-2,2";
//	      else if( (sensores[2] >50) || ( sensores[3] > 50))
//		  lineToBeSent = "D,2,2";
//	      else
//		  lineToBeSent = "D,4,4";
//	      
//	      output_socket.println(lineToBeSent);
//		
//		
//		str = input_socket.readLine();
//		output_pantalla.println("respuesta: ");
//		output_pantalla.println(str);
//	    }
//	    catch (IOException e) {
//		System.out.println(e);
//	    }
//	    
//	}
//    }   
//    
//    public void setup(){
//
//
//
//	// connect to server
//	
//	try {
//	    this.socket = new Socket(this.server,this.port);
//	    System.out.println("Connected with server " +
//				   this.socket.getInetAddress() +
//				   ":" + this.socket.getPort());
//
//	    this.output_pantalla= new PrintWriter(System.out,true);
//	    this.output_socket = new PrintWriter(socket.getOutputStream(),true);
//	    this.input_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//	}
//	catch (UnknownHostException e) {
//	    System.out.println(e);
//	    System.exit(ERROR);
//	}
//	catch (IOException e) {
//	    System.out.println(e);
//	    System.exit(ERROR);
//	}
//
//    }
//
//
//    public void run() {
//        this.setup();
//	System.out.println("despues del setup");
//	kephera behaviour = new kephera(new RelativeTime(100,0),
//                                      new RelativeTime(200,0),
//                                      new RelativeTime(300,0),
//                                      27,
//                                      new RelativeTime(300,0));
//	System.out.println("despues de la definición del behaviour");
//
//	this.AddRTBehaviour(behaviour);
//
//	// this.takedown();
//
//    }
//
//    public void takedown(){
//	
//	try {
//	    this.socket.close();
//	}
//	catch (IOException e) {
//	    System.out.println(e);
//	}
//    }
}
