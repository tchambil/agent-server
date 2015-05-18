package dcc.agent.server.service.message.rcp;

/**
 * Created by teo on 26/04/15.
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";


public static int factorial(int n)
{   int x=1;
    for (int i = 1; i <=n ; i++) {
        x=x*i;
    }
    return x;

}
    public static void mmmmain(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("agent");
            factory.setPassword("159753");
            factory.setHost("192.168.50.19");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

            System.out.println(" [x] Awaiting RPC requests");

            while (true) {
                String response = null;

                QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                BasicProperties props = delivery.getProperties();
                System.out.println(props.getCorrelationId());

                BasicProperties replyProps = new BasicProperties
                        .Builder()
                        .correlationId(props.getCorrelationId())
                        .build();

                try {
                    String message = new String(delivery.getBody(),"UTF-8");
                    int n = Integer.parseInt(message);

                    System.out.println(" [.] fact("+ message + ")");
                    response = "" + factorial(n);
                }
                catch (Exception e){
                    System.out.println(" [.] " + e.toString());
                    response = "";
                }
                finally {
                    channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes("UTF-8"));

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            }
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (Exception ignore) {}
            }
        }
    }
}