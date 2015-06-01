package dcc.agent.server.service.rabbitMQ;

/**
 * Created by teo on 28/04/15.
 */


//@Service("messageQueueManager")
public class MessageQueueManagerImpl implements MessageQueueManager
{/*
    protected static Logger logger = Logger.getLogger(MessageQueueManagerImpl.class);

    @Autowired
    private AmqpAdmin admin;

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private SimpleMessageListenerContainer container;

    @Override
    public String createQueue(String queueName) {

        logger.info("creating queue with name: " + queueName);

        //create queue
        Queue newQueue = new Queue(queueName,true,false,true);
        queueName = admin.declareQueue(newQueue);

        //create binding with exchange
        admin.declareBinding(new Binding(queueName, DestinationType.QUEUE, "directExchange", queueName, new HashMap<String,Object>()));

        logger.info("queue successfully created: " + queueName);

        //add queue to listener
        container.addQueues(newQueue);

        //start listener
        container.start();
        return queueName;
    }
    @Override
    public void sendMessage(String message,String destinationQueueName) throws Exception {
        template.convertAndSend("directExchange", destinationQueueName,   MessageBuilder.withBody(message.getBytes()).build());
    }

    @Override
    public void onMessage(Message message) {
            System.out.println("Received delegate: " + message);
            System.out.println("Text: " + new String(message.getBody()));


    }*/
}
