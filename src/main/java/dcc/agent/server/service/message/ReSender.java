package dcc.agent.server.service.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dcc.agent.server.service.config.AgentServerProperties;

import java.net.InetAddress;
/**
 * Created by teo on 28/04/15.
 * */
public class ReSender {
 public static AgentServerProperties Properties;
 private final static String QUEUE_NAME="hello";

 public void sender(String message) throws Exception {
     InetAddress iAddress = InetAddress.getLocalHost();
     ConnectionFactory factory = new ConnectionFactory();
     factory.setUsername("agent");
     factory.setPassword("159753");
     factory.setHost("192.168.50.25");

     Connection connection = factory.newConnection();
     Channel channel = connection.createChannel();
     String newmessage = iAddress +"->"+ message;
     channel.queueDeclare(QUEUE_NAME, false, false, false, null);
     channel.basicPublish("", QUEUE_NAME, null, newmessage.getBytes());
     System.out.println(" -> Sent '" + newmessage + "'");
     channel.close();
     connection.close();
 }
 }

