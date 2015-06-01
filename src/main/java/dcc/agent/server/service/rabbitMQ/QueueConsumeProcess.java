package dcc.agent.server.service.rabbitMQ;

/**
 * Created by teo on 20/05/15.
 */

import org.springframework.stereotype.Component;

@Component
public class QueueConsumeProcess //implements MessageListener
{
/*
    @Autowired
    private CachingConnectionFactory connectionFactory;

    public static void main(String[] argv) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/config_rabbitmq.xml");
        QueueConsumeProcess consumer = context.getBean(QueueConsumeProcess.class);

        consumer.consume();

    }

    private void consume() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("test.queue.1", "test.queue.2", "test.queue.3", "test.queue.4", "test.queue.5");
        container.setMessageListener(new MessageListenerAdapter(this));
        container.start();
    }

    @Override
    public void onMessage(Message msg) {
        System.out.println(new String(msg.getBody()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
