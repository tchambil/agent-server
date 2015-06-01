package dcc.agent.server.service.message;

/**
 * Created by teo on 25/04/15.
 */
public class Receive {
    /*
    public static AgentServerProperties Properties;
    public static void mammmin(String[] args) throws Exception {

        ConnectionFactory factory=new ConnectionFactory();
        factory.setUsername(Properties.Rabbitmq_Username);
        factory.setPassword(Properties.Rabbitmq_Password);
        factory.setHost(Properties.Rabbitmq_HostExtern);
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        channel.queueDeclare(Properties.Rabbitmq_QueueName,false,false,false,null);
        System.out.println("[*] Waiting for messages. To exit press CTRL +C");
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(Properties.Rabbitmq_QueueName,true,consumer);
        while(true)
        {
            //   Thread.sleep(500);
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String message =new String(delivery.getBody());
            System.out.println("[x] Received '"+ message+"'");
            //Retrasmitiendo
           // ReSender reSender =new ReSender();
            //reSender.sender(message);
        }

    }*/
}