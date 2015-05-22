package dcc.agent.server.service.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dcc.agent.server.service.config.AgentServerProperties;

/**
 * Created by teo on 25/04/15.
 */
public class sender {

    public static AgentServerProperties Properties;
    public static void mainmmmm(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(Properties.Rabbitmq_Username);
        factory.setPassword(Properties.Rabbitmq_Password);
        factory.setHost(Properties.Rabbitmq_HostLocal);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Properties.Rabbitmq_QueueName, false, false, false, null);
        String message = "";
        for (int i = 0; i < 100; i++) {
            message="message "+"["+i+"]";
            channel.basicPublish("", Properties.Rabbitmq_QueueName, null, message.getBytes());
            System.out.println(" ["+i+"] Sent '" + message + "'");
        }

        channel.close();
        connection.close();

    }
}
