package dcc.agent.server.service.rabbitMQ;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by teo on 20/05/15.
 */
public class message {
    public static void main (String[] args)
    {
        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("rabbitmq.xml");
        MessageQueueManager manager =applicationContext.getBean(MessageQueueManagerImpl.class);
       try {
            manager.createQueue("MyTestQueue");
            manager.sendMessage("MytestQueue","MyTestQueue");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
