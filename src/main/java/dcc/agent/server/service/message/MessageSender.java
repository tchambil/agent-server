package dcc.agent.server.service.message;

/**
 * Created by teo on 28/04/15.
 */
public class MessageSender {
/*
    public static AgentServerProperties Properties;
    public void sender(String message, boolean state)throws Exception{
        state=true;
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(Properties.Rabbitmq_Username);
    factory.setPassword(Properties.Rabbitmq_Password);
    factory.setHost(Properties.Rabbitmq_HostExtern);
    if(state)
    {
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(Properties.Rabbitmq_QueueName, false, false, false, null);
    channel.basicPublish("", Properties.Rabbitmq_QueueName, null, message.getBytes());
     channel.close();
    connection.close();
    }
  }*/

}
